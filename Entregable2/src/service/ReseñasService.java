package service;

import dao.PeliculasDAO;
import dao.ReseñasDAO;
import dao.UsuariosDAO;

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

    public Usuario autenticarUsuario(String nombreUsuario, String contrasenia) throws SQLException {
      Optional<Usuario> usuarioOpt = usuariosDao.buscarPorNombreUsuario(nombreUsuario);

        // 2. Comprobar si el contenedor está lleno
        if (usuarioOpt.isPresent()) {
            
            // 3. ¡CORRECCIÓN! Usar .get() para extraer el objeto del contenedor.
            Usuario usuario = usuarioOpt.get(); 
            
            // 4. Continuar con la verificación de la contraseña
            if (usuario.getContrasenia().equals(contrasenia)) {
                return usuario; // Retornamos el objeto Usuario real
            }
        }

        // Si la Optional estaba vacía o la contraseña falló, retorna null
        return null;
        }

    public List<Pelicula> listarPeliculasDisponibles() throws SQLException {
        return peliculasDao.listarTodos();
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
