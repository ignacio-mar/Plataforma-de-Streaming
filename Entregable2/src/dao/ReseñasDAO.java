package dao;

import model.Reseña;
import java.sql.SQLException;

public interface ReseñasDAO {
    Reseña registrar(Reseña rese) throws SQLException;  
    Reseña aprobar(Reseña rese) throws SQLException;  
    
}