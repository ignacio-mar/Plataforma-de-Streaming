package dao.impl;

import dao.DatosPersonalesDAO;
import dao.UsuariosDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import model.DatosPersonales;
import model.Usuario;

public class UsuariosDAOjdbc implements UsuariosDAO {

    private static final String INSERT_SQL =
        "INSERT INTO USUARIO " +
        "(NOMBRE_USUARIO, EMAIL, CONTRASENIA, DNI_PERSONA, PELICULAS_RESENADAS, PRIMER_ACCESO) " +
        "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_SQL =
        "UPDATE USUARIO SET " +
        "NOMBRE_USUARIO = ?, " +
        "EMAIL = ?, " +
        "CONTRASENIA = ?, " +
        "DNI_PERSONA = ?, " +
        "PELICULAS_RESENADAS = ?, " +
        "PRIMER_ACCESO = ? " +
        "WHERE ID = ?";

    private static final String DELETE_SQL =
        "DELETE FROM USUARIO WHERE ID = ?";

    private static final String SELECT_BY_ID_SQL =
        "SELECT * FROM USUARIO WHERE ID = ?";

    private static final String SELECT_BY_USERNAME_SQL =
        "SELECT * FROM USUARIO WHERE NOMBRE_USUARIO = ?";

    private static final String SELECT_BY_DNI_SQL =
        "SELECT * FROM USUARIO WHERE DNI_PERSONA = ?";

    private static final String SELECT_ALL_SQL =
        "SELECT * FROM USUARIO";

    private static final String SELECT_BY_EMAIL_SQL =
        "SELECT * FROM USUARIO WHERE EMAIL = ?";

    private final Connection conexion;
    private final DatosPersonalesDAO datosPersonalesDAO;

    public UsuariosDAOjdbc(Connection conexion, DatosPersonalesDAO datosPersonalesDAO) {
        this.conexion = conexion;
        this.datosPersonalesDAO = datosPersonalesDAO;
    }

    // ========== Métodos privados de ayuda ==========

    private Usuario resultSetToUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario(
                rs.getInt("ID"),
                rs.getString("NOMBRE_USUARIO"),
                rs.getString("EMAIL"),
                rs.getString("CONTRASENIA"),
                rs.getInt("DNI_PERSONA")
        );
        // columnas nuevas
        String pelisTxt = rs.getString("PELICULAS_RESENADAS");
        usuario.setPeliculasResenadasDesdeTexto(pelisTxt);
        int primerAccesoInt = rs.getInt("PRIMER_ACCESO");
        usuario.setPrimerAcceso(primerAccesoInt == 1);
        return usuario;
    }

    // ========== Implementación interface ==========

    @Override
    public Usuario asociarUsuario(Usuario usuario) throws SQLException {
        // 1) Verificar que exista la persona con ese DNI
        Optional<DatosPersonales> datosPersonales =
                datosPersonalesDAO.buscarPorDni(usuario.getDniPersona());
        if (!datosPersonales.isPresent()) {
            throw new SQLException("No existe una persona con el DNI: " + usuario.getDniPersona());
        }

        // 2) Verificar que el nombre de usuario no esté ya en uso
        if (buscarPorNombreUsuario(usuario.getNombreUsuario()).isPresent()) {
            throw new SQLException(
                    "El nombre de usuario '" + usuario.getNombreUsuario() + "' ya está en uso");
        }

        // 3) Verificar que el DNI no tenga ya un usuario asociado
        try (PreparedStatement checkStmt = conexion.prepareStatement(SELECT_BY_DNI_SQL)) {
            checkStmt.setInt(1, usuario.getDniPersona());
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    throw new SQLException(
                            "La persona con DNI " + usuario.getDniPersona() + " ya tiene un usuario asociado");
                }
            }
        }

        // 4) Insertar
        try (PreparedStatement stmt =
                     conexion.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, usuario.getNombreUsuario());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getContrasenia());
            stmt.setInt(4, usuario.getDniPersona());
            stmt.setString(5, usuario.getPeliculasResenadasComoTexto());
            stmt.setInt(6, usuario.isPrimerAcceso() ? 1 : 0);

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    // devolvemos el usuario con el ID asignado
                    return new Usuario(
                            rs.getInt(1),
                            usuario.getNombreUsuario(),
                            usuario.getEmail(),
                            usuario.getContrasenia(),
                            usuario.getDniPersona()
                    );
                } else {
                    throw new SQLException("Error al obtener el ID generado para el usuario");
                }
            }
        }
    }

    @Override
    public Optional<Usuario> buscarPorId(int id) throws SQLException {
        try (PreparedStatement stmt = conexion.prepareStatement(SELECT_BY_ID_SQL)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(resultSetToUsuario(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> buscarPorNombreUsuario(String nombreUsuario) throws SQLException {
        try (PreparedStatement stmt = conexion.prepareStatement(SELECT_BY_USERNAME_SQL)) {
            stmt.setString(1, nombreUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(resultSetToUsuario(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) throws SQLException {
        try (PreparedStatement stmt = conexion.prepareStatement(SELECT_BY_EMAIL_SQL)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(resultSetToUsuario(rs));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Usuario> listarTodos() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_SQL)) {
            while (rs.next()) {
                usuarios.add(resultSetToUsuario(rs));
            }
        }
        return usuarios;
    }

    @Override
    public boolean actualizar(Usuario usuario) throws SQLException {
        // 1) Verificar que exista la persona
        if (!datosPersonalesDAO.buscarPorDni(usuario.getDniPersona()).isPresent()) {
            throw new SQLException("No existe una persona con el DNI: " + usuario.getDniPersona());
        }

        // 2) Verificar que el nuevo nombre de usuario no esté en uso por otro usuario
        Optional<Usuario> existente = buscarPorNombreUsuario(usuario.getNombreUsuario());
        if (existente.isPresent() && existente.get().getId() != usuario.getId()) {
            throw new SQLException(
                    "El nombre de usuario '" + usuario.getNombreUsuario() + "' ya está en uso");
        }

        // 3) Verificar que el nuevo DNI no tenga ya un usuario asociado (si se cambia el DNI)
        Optional<Usuario> actual = buscarPorId(usuario.getId());
        if (actual.isPresent() && actual.get().getDniPersona() != usuario.getDniPersona()) {
            String sqlDni = "SELECT * FROM USUARIO WHERE DNI_PERSONA = ? AND ID <> ?";
            try (PreparedStatement checkStmt = conexion.prepareStatement(sqlDni)) {
                checkStmt.setInt(1, usuario.getDniPersona());
                checkStmt.setInt(2, usuario.getId());
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        throw new SQLException("La persona con DNI " + usuario.getDniPersona()
                                + " ya tiene un usuario asociado");
                    }
                }
            }
        }

        // 4) Actualizar
        try (PreparedStatement stmt = conexion.prepareStatement(UPDATE_SQL)) {
            stmt.setString(1, usuario.getNombreUsuario());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getContrasenia());
            stmt.setInt(4, usuario.getDniPersona());
            stmt.setString(5, usuario.getPeliculasResenadasComoTexto());
            stmt.setInt(6, usuario.isPrimerAcceso() ? 1 : 0);
            stmt.setInt(7, usuario.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(int id) throws SQLException {
        try (PreparedStatement stmt = conexion.prepareStatement(DELETE_SQL)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}
