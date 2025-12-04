package service;

import dao.DatosPersonalesDAO;
import dao.UsuariosDAO;
import dao.compar.ComparatorEmailUsuario;
import dao.compar.ComparatorNombreUsuario;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import model.DatosPersonales;
import model.Usuario;

public class UsuariosService {

    private final UsuariosDAO usuariosDao;
    private final DatosPersonalesDAO dpDao;

    public UsuariosService(UsuariosDAO usuariosDao, DatosPersonalesDAO dpDao) {
        this.usuariosDao = usuariosDao;
        this.dpDao = dpDao;
    }

    public boolean validarNombreUsuario(String nombreUsuario) throws SQLException {
        return !nombreUsuario.isEmpty() && usuariosDao.buscarPorNombreUsuario(nombreUsuario).isEmpty();
    }

    public boolean validarEmail(String email) {
        return !email.isEmpty() && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public boolean validarContrasenia(String contrasenia) {
        return contrasenia.length() >= 6;
    }

    public boolean personaExiste(int dni) throws SQLException {
        return dpDao.buscarPorDni(dni).isPresent();
    }
    // Nuevo método en UsuariosService
    public boolean emailExiste(String email) throws SQLException {
        return usuariosDao.buscarPorEmail(email).isPresent();
    }

    public Usuario crearUsuario(String nombreUsuario, String email, String contrasenia, int dni) 
            throws SQLException {
        Usuario usuario = new Usuario(nombreUsuario, email, contrasenia, dni);
        return usuariosDao.asociarUsuario(usuario);
    }

    public Optional<Usuario> buscarPorId(int id) throws SQLException {
        return usuariosDao.buscarPorId(id);
    }

    public Optional<Usuario> buscarPorNombreUsuario(String nombre) throws SQLException {
        return usuariosDao.buscarPorNombreUsuario(nombre);
    }

    public List<Usuario> listarTodosPorNombre() throws SQLException {
        List<Usuario> lista = usuariosDao.listarTodos();
        lista.sort(ComparatorNombreUsuario.POR_NOMBRE_USUARIO);
        return lista;
    }

    public List<Usuario> listarTodosPorEmail() throws SQLException {
        List<Usuario> lista = usuariosDao.listarTodos();
        lista.sort(ComparatorEmailUsuario.POR_EMAIL);
        return lista;
    }

    public List<Usuario> listarTodos() throws SQLException {
        return usuariosDao.listarTodos();
    }

    public boolean actualizarUsuario(int id, String nombreUsuario, String email, String contrasenia, int dniPersona) 
            throws SQLException {
        Usuario mod = new Usuario(id, nombreUsuario, email, contrasenia, dniPersona);
        return usuariosDao.actualizar(mod);
    }

    public boolean eliminarUsuario(int id) throws SQLException {
        return usuariosDao.eliminar(id);
    }

    public String obtenerNombreCompletoPersona(int dni) throws SQLException {
        Optional<DatosPersonales> persona = dpDao.buscarPorDni(dni);
        return persona.map(p -> p.getNombres() + " " + p.getApellidos())
                      .orElse("(persona no encontrada)");
    }
public void marcarPeliculaComoResenada(Usuario usuario, int idPelicula) throws SQLException {
    // 1. Actualiza el objeto en memoria (IMPORTANTE para no perder el dato en esta sesión)
    usuario.agregarPeliculaResenada(idPelicula);
    
    // 2. Llama al DAO para persistir el cambio (método pendiente de implementación)
    // usuariosDao.actualizarPeliculasResenadas(usuario); 
    // ^ Este es un NUEVO método que debes crear en tu UsuariosDAOjdbc.
}



}
