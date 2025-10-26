package dao.impl;

import dao.DatosPersonalesDAO;
import dao.db.Conexion;
import dao.model.DatosPersonales;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DatosPersonalesDAOimp implements DatosPersonalesDAO {

    private static final String INSERT_SQL =
        "INSERT INTO DATOS_PERSONALES (DNI, NOMBRE, APELLIDO, PAIS_RESIDENCIA, NUMERO_TELEFONO) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ID_SQL =
        "SELECT ID, DNI, NOMBRE, APELLIDO, PAIS_RESIDENCIA, NUMERO_TELEFONO FROM DATOS_PERSONALES WHERE ID = ?";
    private static final String SELECT_DNI_SQL =
        "SELECT ID, DNI, NOMBRE, APELLIDO, PAIS_RESIDENCIA, NUMERO_TELEFONO FROM DATOS_PERSONALES WHERE DNI = ?";
    private static final String SELECT_ALL_SQL =
        "SELECT ID, DNI, NOMBRE, APELLIDO, PAIS_RESIDENCIA, NUMERO_TELEFONO FROM DATOS_PERSONALES ORDER BY APELLIDO, NOMBRE";
    private static final String UPDATE_SQL =
        "UPDATE DATOS_PERSONALES SET DNI = ?, NOMBRE = ?, APELLIDO = ?, PAIS_RESIDENCIA = ?, NUMERO_TELEFONO = ? WHERE ID = ?";
    private static final String DELETE_SQL =
        "DELETE FROM DATOS_PERSONALES WHERE ID = ?";
        
    private Connection getConnection() throws SQLException {
        return Conexion.getCon();
    }

    private DatosPersonales resultSetToDatosPersonales(ResultSet rs) throws SQLException {
        DatosPersonales dp = new DatosPersonales(
            rs.getString("NOMBRE"),
            rs.getString("APELLIDO"),
            rs.getInt("DNI"),
            rs.getString("PAIS_RESIDENCIA"),
            rs.getString("NUMERO_TELEFONO")
        );
        dp.setId(rs.getInt("ID"));
        return dp;
    }

    @Override
    public DatosPersonales guardar(DatosPersonales dp) throws SQLException {
        try (PreparedStatement ps = getConnection().prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, dp.getDni());
            ps.setString(2, dp.getNombres());
            ps.setString(3, dp.getApellido());
            ps.setString(4, dp.getPaisResidencia());
            ps.setString(5, dp.getNumeroTelefono());
            
            ps.executeUpdate();
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    dp.setId(rs.getInt(1));
                }
            }
            return dp;
        }
    }

    @Override
    public Optional<DatosPersonales> buscarPorId(int id) throws SQLException {
        try (PreparedStatement ps = getConnection().prepareStatement(SELECT_ID_SQL)) {
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(resultSetToDatosPersonales(rs));
                }
                return Optional.empty();
            }
        }
    }

    @Override
    public Optional<DatosPersonales> buscarPorDni(int dni) throws SQLException {
        try (PreparedStatement ps = getConnection().prepareStatement(SELECT_DNI_SQL)) {
            ps.setInt(1, dni);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(resultSetToDatosPersonales(rs));
                }
                return Optional.empty();
            }
        }
    }

    @Override
    public List<DatosPersonales> listarTodos() throws SQLException {
        List<DatosPersonales> lista = new ArrayList<>();
        
        try (Statement st = getConnection().createStatement();
             ResultSet rs = st.executeQuery(SELECT_ALL_SQL)) {
            
            while (rs.next()) {
                lista.add(resultSetToDatosPersonales(rs));
            }
        }
        return lista;
    }

    @Override
    public boolean actualizar(DatosPersonales dp) throws SQLException {
        try (PreparedStatement ps = getConnection().prepareStatement(UPDATE_SQL)) {
            ps.setInt(1, dp.getDni());
            ps.setString(2, dp.getNombres());
            ps.setString(3, dp.getApellido());
            ps.setString(4, dp.getPaisResidencia());
            ps.setString(5, dp.getNumeroTelefono());
            ps.setInt(6, dp.getId());
            
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(int id) throws SQLException {
        try (PreparedStatement ps = getConnection().prepareStatement(DELETE_SQL)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}

    @Override
    public DatosPersonales guardar(DatosPersonales dp) throws SQLException {
        try (PreparedStatement ps = getConnection().prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, dp.getDni());
            ps.setString(2, dp.getNombres());
            ps.setString(3, dp.getApellido());
            ps.setString(4, dp.getPaisResidencia());
            ps.setString(5, dp.getNumeroTelefono());
            
            ps.executeUpdate();
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    dp.setId(rs.getInt(1));
                }
            }
            return dp;
        }
    }

    @Override
    public Optional<DatosPersonales> buscarPorId(int id) throws SQLException {
        try (PreparedStatement ps = getConnection().prepareStatement(SELECT_ID_SQL)) {
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(resultSetToDatosPersonales(rs));
                }
                return Optional.empty();
            }
        }
    }

    @Override
    public Optional<DatosPersonales> buscarPorDni(int dni) throws SQLException {
        try (PreparedStatement ps = getConnection().prepareStatement(SELECT_DNI_SQL)) {
            ps.setInt(1, dni);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(resultSetToDatosPersonales(rs));
                }
                return Optional.empty();
            }
        }
    }

    @Override
    public List<DatosPersonales> listarTodos() throws SQLException {
        List<DatosPersonales> lista = new ArrayList<>();
        
        try (Statement st = getConnection().createStatement();
             ResultSet rs = st.executeQuery(SELECT_ALL_SQL)) {
            
            while (rs.next()) {
                lista.add(resultSetToDatosPersonales(rs));
            }
        }
        return lista;
    }

    @Override
    public boolean actualizar(DatosPersonales dp) throws SQLException {
        try (PreparedStatement ps = getConnection().prepareStatement(UPDATE_SQL)) {
            ps.setInt(1, dp.getDni());
            ps.setString(2, dp.getNombres());
            ps.setString(3, dp.getApellido());
            ps.setString(4, dp.getPaisResidencia());
            ps.setString(5, dp.getNumeroTelefono());
            ps.setInt(6, dp.getId());
            
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(int id) throws SQLException {
        try (PreparedStatement ps = getConnection().prepareStatement(DELETE_SQL)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }