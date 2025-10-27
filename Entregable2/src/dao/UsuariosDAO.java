package dao;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import model.Usuario;

public interface UsuariosDAO {
    Usuario asociarUsuario(Usuario usuario) throws SQLException; // crear nuevo usuario y lo conecta con un DNI     
    Optional<Usuario> buscarPorId(int id) throws SQLException;
    Optional<Usuario> buscarPorNombreUsuario(String nombreUsuario) throws SQLException; 
    List<Usuario> listarTodos(Comparator<Usuario> comparador) throws SQLException; //Lista ordenada según el comparator (por nombre o email)
    boolean actualizar(Usuario usuario) throws SQLException;
    boolean eliminar(int id) throws SQLException;
}