package dao;

import model.Reseña;
import java.sql.SQLException;
import java.util.Optional;
import java.util.List;

public interface ReseñasDAO {
    Reseña registrar(Reseña rese) throws SQLException;  
    Optional<Reseña> actualizar(Reseña rese) throws SQLException;
    boolean eliminar(int id) throws SQLException;
    Optional<Reseña> buscarPorId(int id) throws SQLException;
    Optional<Reseña> aprobar(int id) throws SQLException;
    List<Reseña> listarPendientesAprobacion() throws SQLException;
}