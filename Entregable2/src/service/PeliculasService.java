package service;

import dao.PeliculasDAO;
import dao.compar.ComparadorDuracion;
import dao.compar.ComparadorGenero;
import dao.compar.ComparadorTitulo;
import model.Pelicula;
import model.enums.Generos;
import model.enums.Idiomas;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class PeliculasService {

    private final PeliculasDAO peliculasDao;

    public PeliculasService(PeliculasDAO peliculasDao) {
        this.peliculasDao = peliculasDao;
    }

    public boolean validarTitulo(String titulo) {
        return !titulo.isEmpty();
    }

    public boolean validarElenco(String elenco) {
        return !elenco.isEmpty() && elenco.contains(",");
    }

    public boolean validarDirector(String director) {
        return !director.isEmpty() && director.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$");
    }

    public boolean validarDuracion(double duracion) {
        return duracion > 0;
    }

    public Pelicula crearPelicula(String titulo, String elenco, String director, Generos genero, 
                                  double duracion, Idiomas audio, Idiomas subtitulos, String sinopsis) 
            throws SQLException {
        Pelicula pelicula = new Pelicula(titulo, elenco, director, genero, duracion, audio, subtitulos, sinopsis);
        return peliculasDao.guardar(pelicula);
    }

    public List<Pelicula> listarPorTitulo() throws SQLException {
        return peliculasDao.listarTodos(ComparadorTitulo.POR_TITULO);
    }

    public List<Pelicula> listarPorGenero() throws SQLException {
        return peliculasDao.listarTodos(ComparadorGenero.POR_GENERO);
    }

    public List<Pelicula> listarPorDuracion() throws SQLException {
        return peliculasDao.listarTodos(ComparadorDuracion.POR_DURACION);
    }

    public List<Pelicula> listarTodos(Comparator<Pelicula> comparador) throws SQLException {
        return peliculasDao.listarTodos(comparador);
    }
}
