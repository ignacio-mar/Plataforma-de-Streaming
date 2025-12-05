package dao;

import java.sql.SQLException;
import java.util.List;
import model.Pelicula;

public interface PeliculasDAO {
    Pelicula guardar(Pelicula peli) throws SQLException;     
    List<Pelicula> listarTodos() throws SQLException;
}
