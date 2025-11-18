package service;

import dao.PeliculasDAO;
import dao.compar.ComparadorDuracion;
import dao.compar.ComparadorGenero;
import dao.compar.ComparadorTitulo;
import model.Pelicula;
import java.sql.SQLException;
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

    public Pelicula crearPelicula(String titulo, String elenco, String director, model.enums.Generos genero, double duracion,  model.enums.Idiomas audio, model.enums.Idiomas subtitulos, String sinopsis) throws SQLException {
        Pelicula pelicula = new Pelicula(titulo, elenco, director, genero, duracion, audio, subtitulos, sinopsis);
        return peliculasDao.guardar(pelicula);
    }

    public List<Pelicula> listarPorTitulo() throws SQLException {
        List<Pelicula> lista = peliculasDao.listarTodos();
        lista.sort(ComparadorTitulo.POR_TITULO);
        return lista;
    }

    public List<Pelicula> listarPorGenero() throws SQLException {
        List<Pelicula> lista = peliculasDao.listarTodos();
        lista.sort(ComparadorGenero.POR_GENERO);
        return lista;
    }

    public List<Pelicula> listarPorDuracion() throws SQLException {
        List<Pelicula> lista = peliculasDao.listarTodos();
        lista.sort(ComparadorDuracion.POR_DURACION);
        return lista;
    }

    public List<Pelicula> listarTodos() throws SQLException {
        return peliculasDao.listarTodos();
    }
}
