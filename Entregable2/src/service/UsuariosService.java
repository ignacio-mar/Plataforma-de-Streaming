package service;

import dao.DatosPersonalesDAO;
import dao.UsuariosDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import model.Usuario;

public class UsuariosService {

    private final UsuariosDAO usuariosDao;
    private final DatosPersonalesDAO datosPersonalesDao;


    public UsuariosService(UsuariosDAO usuariosDao, DatosPersonalesDAO datosPersonalesDao) {
        this.usuariosDao = usuariosDao;
        this.datosPersonalesDao = datosPersonalesDao;
    }

    public boolean validarEmail(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }
        // Validación muy simple de formato
        if (!email.contains("@") || !email.contains(".")) {
            return false;
        }
        try {
            // Debe ser único
            return usuariosDao.buscarPorEmail(email).isEmpty();
        } catch (SQLException e) {
            e.printStackTrace();
            // Si hay error de BD, lo consideramos inválido
            return false;
        }
    }

    public boolean validarNombreUsuario(String nombreUsuario) {
        if (nombreUsuario == null || nombreUsuario.isBlank()) {
            return false;
        }
        // Por ejemplo, mínimo 4 caracteres
        if (nombreUsuario.length() < 4) {
            return false;
        }
        try {
            // Debe ser único
            return usuariosDao.buscarPorNombreUsuario(nombreUsuario).isEmpty();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Valida contraseña: regla básica de longitud mínima.
     * (Si tu TP tenía una política más compleja, podés ajustarla acá).
     */
    public boolean validarContrasenia(String contrasenia) {
        if (contrasenia == null || contrasenia.isBlank()) {
            return false;
        }
        // Por ejemplo, mínimo 6 caracteres
        return contrasenia.length() >= 6;
    }

    /**
     * Crea y persiste un usuario nuevo asociado al DNI dado.
     * Lanza SQLException si falla algo en la BD (DNI inexistente, duplicados, etc.).
     *
     * Esta firma coincide con la que el RegistroController está esperando:
     *  crearUsuario(String nombreUsuario, String email, String contrasenia, int dniPersona)
     */
    public Usuario crearUsuario(String nombreUsuario,
                                String email,
                                String contrasenia,
                                int dniPersona) throws SQLException {

        Usuario usuario = new Usuario(nombreUsuario, email, contrasenia, dniPersona);
        // Usa el método del DAO que ya valida DNI, duplicados, etc.
        return usuariosDao.asociarUsuario(usuario);
    }

    // =====================================================
    // ALTAS (API algo más general, por si la usás en otro lado)
    // =====================================================

    public Usuario registrarUsuario(Usuario usuario) throws SQLException {
        return usuariosDao.asociarUsuario(usuario);
    }

    // =====================================================
    // BÚSQUEDAS / CONSULTAS
    // =====================================================

    public Optional<Usuario> buscarPorId(int id) throws SQLException {
        return usuariosDao.buscarPorId(id);
    }

    public Optional<Usuario> buscarPorNombreUsuario(String nombreUsuario) throws SQLException {
        return usuariosDao.buscarPorNombreUsuario(nombreUsuario);
    }

    public Optional<Usuario> buscarPorEmail(String email) throws SQLException {
        return usuariosDao.buscarPorEmail(email);
    }

    public List<Usuario> listarTodos() throws SQLException {
        return usuariosDao.listarTodos();
    }

    public boolean existeNombreUsuario(String nombreUsuario) throws SQLException {
        return usuariosDao.buscarPorNombreUsuario(nombreUsuario).isPresent();
    }

    public boolean existeEmail(String email) throws SQLException {
        return usuariosDao.buscarPorEmail(email).isPresent();
    }

    public boolean existePersonaConDni(int dni) throws SQLException {
        return datosPersonalesDao.buscarPorDni(dni).isPresent();
    }

    // =====================================================
    // ACTUALIZACIÓN (incluye PRIMER_ACCESO y PELICULAS_RESENADAS)
    // =====================================================

    /**
     * Actualiza TODOS los datos del usuario en BD:
     *  - NOMBRE_USUARIO
     *  - EMAIL
     *  - CONTRASENIA
     *  - DNI_PERSONA
     *  - PELICULAS_RESENADAS
     *  - PRIMER_ACCESO
     *
     * IMPORTANTE: recibe el objeto Usuario YA MODIFICADO
     * (por ejemplo, con setPrimerAcceso(false)).
     */
    public boolean actualizarUsuario(Usuario usuario) throws SQLException {
        return usuariosDao.actualizar(usuario);
    }

    // =====================================================
    // BAJA
    // =====================================================

    public boolean eliminarUsuario(int id) throws SQLException {
        return usuariosDao.eliminar(id);
    }
}
