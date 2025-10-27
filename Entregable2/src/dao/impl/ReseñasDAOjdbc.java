package dao.impl;
import dao.ReseñasDAO;
import java.sql.*;
import java.util.*;
import db.Conexion;
import model.Pelicula;
import model.Reseña;

public class ReseñasDAOjdbc implements ReseñasDAO {

    private static final String INSERT_SQL = "INSERT INTO RESENIA (CALIFICACION,COMENTARIO, FECHA_HORA,ID_PELICULA,ID_USUARIO) VALUES (?, ?, ?, ?,?)";  
    private static final String APPROVE_SQL = "UPDATE RESENIA SET APROBADO = ? WHERE ID = ?";
    
    private Connection getconnection() throws SQLException {
        return Conexion.getCon();
    }


    @Override
    public Reseña registrar(Reseña rese) throws SQLException {
        try (PreparedStatement ps = getconnection().prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, rese.getCalificacion());
            ps.setString(2, rese.getComentario());
            ps.setString(3, rese.getFecha_hora());
            ps.setInt(4, rese.getId_pelicula());
            ps.setInt(5, rese.getId_usuario())
             
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
    public Reseña aprobar(Reseña rese) throws SQLException {
        
    }