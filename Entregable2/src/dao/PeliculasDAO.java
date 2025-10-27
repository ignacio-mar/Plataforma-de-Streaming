package dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import model.Pelicula;
import model.Enums.Generos;

public interface PeliculasDAO {
    Pelicula guardar(Pelicula peli) throws SQLException;     
    Optional<Pelicula> buscarPorGenero(Generos genero) throws SQLException;
    Optional<Pelicula> buscarPorTitulo(String Titulo) throws SQLException; 
    Optional<Pelicula> buscarPorDuracion(double duracion) throws SQLException; 
    List<Pelicula> listarTodos(Comparator<Pelicula> comparador) throws SQLException;
}