package service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import dao.DatosPersonalesDAO;
import dao.UsuariosDAO;
import dao.compar.ComparatorEmailUsuario;
import dao.compar.ComparatorNombreUsuario;
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


}
    
   /*public Usuario validarCredenciales(String nombreUsuario, String contrasenia) throws SQLException {
    // 1. Buscamos si el usuario existe en la BD
    Optional<Usuario> usuarioOpt = usuariosDao.buscarPorNombreUsuario(nombreUsuario);

    if (usuarioOpt.isPresent()) {
        Usuario usuario = usuarioOpt.get();
        
        // 2. Comparamos la contraseña de la BD con la que ingresó el usuario
        // IMPORTANTE: Usar .equals(), NO ==
        if (usuario.getContrasenia().equals(contrasenia)) {
            return usuario; // ¡Login Exitoso! Devolvemos el usuario
        }
    }
    
    return null; // Usuario no encontrado O contraseña incorrecta
}
}*/
