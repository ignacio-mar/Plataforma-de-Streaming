package dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import model.Pelicula;
import model.enums.Generos;

//Interfaz DAO para operaciones relacionadas con las peliculas
public interface PeliculasDAO {
    //Guardar una película en la bd
    Pelicula guardar(Pelicula peli) throws SQLException;     
    //Formas de buscar y listar películas
    Optional<Pelicula> buscarPorGenero(Generos genero) throws SQLException;
    Optional<Pelicula> buscarPorTitulo(String Titulo) throws SQLException; 
    Optional<Pelicula> buscarPorDuracion(double duracion) throws SQLException; 
    List<Pelicula> listarTodos() throws SQLException;
}