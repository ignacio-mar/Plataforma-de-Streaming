package dao;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import model.Pelicula;

public interface PeliculasDAO {
    Pelicula guardar(Pelicula peli) throws SQLException;     
    List<Pelicula> listarTodos(Comparator<Pelicula> comparador) throws SQLException;
}