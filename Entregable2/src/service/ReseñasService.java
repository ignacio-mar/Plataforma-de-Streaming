package service;

import dao.PeliculasDAO;
import dao.ReseñasDAO;
import dao.UsuariosDAO;
import dao.compar.ComparatorIdPelicula;
import model.Pelicula;
import model.Reseña;
import model.Usuario;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ReseñasService {

    private final ReseñasDAO reseñasDao;
    private final UsuariosDAO usuariosDao;
    private final PeliculasDAO peliculasDao;

    public ReseñasService(ReseñasDAO reseñasDao, UsuariosDAO usuariosDao, PeliculasDAO peliculasDao) {
        this.reseñasDao = reseñasDao;
        this.usuariosDao = usuariosDao;
        this.peliculasDao = peliculasDao;
    }

    public Optional<Usuario> autenticarUsuario(String nombreUsuario, String contrasenia) throws SQLException {
        Optional<Usuario> optUsuario = usuariosDao.buscarPorNombreUsuario(nombreUsuario);
        if (optUsuario.isPresent() && optUsuario.get().getContrasenia().equals(contrasenia)) {
            return optUsuario;
        }
        return Optional.empty();
    }

    public List<Pelicula> listarPeliculasDisponibles() throws SQLException {
        return peliculasDao.listarTodos(ComparatorIdPelicula.POR_ID);
    }

    public boolean validarCalificacion(int calificacion) {
        return calificacion >= 1 && calificacion <= 5;
    }

    public void registrarReseña(int calificacion, String comentario, int idUsuario, int idPelicula) 
            throws SQLException {
        Reseña nuevaReseña = new Reseña(calificacion, comentario, idUsuario, idPelicula);
        reseñasDao.registrar(nuevaReseña);
    }

    public List<Reseña> listarPendientesAprobacion() throws SQLException {
        return reseñasDao.listarPendientesAprobacion();
    }

    public Optional<Reseña> aprobarReseña(int idReseña) throws SQLException {
        return reseñasDao.aprobar(idReseña);
    }
}
