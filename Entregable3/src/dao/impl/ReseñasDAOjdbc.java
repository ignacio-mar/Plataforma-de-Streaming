package dao.impl;

import dao.ReseñasDAO;
import java.sql.*;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import db.Conexion;
import model.Reseña;
import java.time.LocalDateTime;

public class ReseñasDAOjdbc implements ReseñasDAO {

    private static final String INSERT_SQL = 
        "INSERT INTO RESENIA (CALIFICACION, COMENTARIO, APROBADO, FECHA_HORA, ID_USUARIO, ID_PELICULA) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = 
        "UPDATE RESENIA SET CALIFICACION = ?, COMENTARIO = ? WHERE ID = ?";
    private static final String DELETE_SQL = 
        "DELETE FROM RESENIA WHERE ID = ?";
    private static final String SELECT_BY_ID_SQL = 
        "SELECT * FROM RESENIA WHERE ID = ?";
    private static final String APPROVE_SQL = 
        "UPDATE RESENIA SET APROBADO = 1 WHERE ID = ?";
    
    private Connection getConnection() throws SQLException {
        return Conexion.getCon();
    }

    private Reseña resultSetToReseña(ResultSet rs) throws SQLException {
        Reseña reseña = new Reseña(
            rs.getInt("CALIFICACION"),
            rs.getString("COMENTARIO"),
            rs.getInt("ID_USUARIO"),
            rs.getInt("ID_PELICULA")
        );
        reseña.setId(rs.getInt("ID"));
        reseña.setAprobado(rs.getBoolean("APROBADO"));
        reseña.setFecha_hora(rs.getTimestamp("FECHA_HORA").toLocalDateTime());
        return reseña;
    }

    @Override
    public Reseña registrar(Reseña rese) throws SQLException {
        try (PreparedStatement ps = getConnection().prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, rese.getCalificacion());
            ps.setString(2, rese.getComentario());
            ps.setBoolean(3, false);  // Por defecto, no aprobado
            ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(5, rese.getId_usuario());
            ps.setInt(6, rese.getId_pelicula());
             
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    rese.setId(rs.getInt(1));
                }
            }
            return rese;
        }
    }

    @Override
    public Optional<Reseña> actualizar(Reseña rese) throws SQLException {
        try (PreparedStatement ps = getConnection().prepareStatement(UPDATE_SQL)) {
            ps.setInt(1, rese.getCalificacion());
            ps.setString(2, rese.getComentario());
            ps.setInt(3, rese.getId());
            
            return ps.executeUpdate() > 0 ? buscarPorId(rese.getId()) : Optional.empty();
        }
    }

    @Override
    public boolean eliminar(int id) throws SQLException {
        try (PreparedStatement ps = getConnection().prepareStatement(DELETE_SQL)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public Optional<Reseña> buscarPorId(int id) throws SQLException {
        try (PreparedStatement ps = getConnection().prepareStatement(SELECT_BY_ID_SQL)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(resultSetToReseña(rs)) : Optional.empty();
            }
        }
    }

    @Override
    public Optional<Reseña> aprobar(int id) throws SQLException {
        try (PreparedStatement ps = getConnection().prepareStatement(APPROVE_SQL)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0 ? buscarPorId(id) : Optional.empty();
        }
    }
    
    private static final String SELECT_PENDIENTES_SQL = 
        "SELECT * FROM RESENIA WHERE APROBADO = 0 ORDER BY FECHA_HORA ASC";
        
    @Override
    public List<Reseña> listarPendientesAprobacion() throws SQLException {
        List<Reseña> reseñas = new ArrayList<>();
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_PENDIENTES_SQL)) {
            while (rs.next()) {
                reseñas.add(resultSetToReseña(rs));
            }
        }
        return reseñas;
    }
        
    }
