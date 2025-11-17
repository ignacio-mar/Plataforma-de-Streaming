package service;

import dao.DatosPersonalesDAO;
import dao.UsuariosDAO;
import model.DatosPersonales;
import model.Usuario;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
        return usuariosDao.listarTodos((u1, u2) -> u1.getNombreUsuario().compareToIgnoreCase(u2.getNombreUsuario()));
    }

    public List<Usuario> listarTodosPorEmail() throws SQLException {
        return usuariosDao.listarTodos((u1, u2) -> u1.getEmail().compareToIgnoreCase(u2.getEmail()));
    }

    public List<Usuario> listarTodos(Comparator<Usuario> comparador) throws SQLException {
        return usuariosDao.listarTodos(comparador);
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
        return persona.map(p -> p.getNombres() + " " + p.getApellido())
                      .orElse("(persona no encontrada)");
    }
}
