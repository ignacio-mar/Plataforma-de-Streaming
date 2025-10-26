package dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import model.DatosPersonales;

public interface DatosPersonalesDAO {
    DatosPersonales guardar(DatosPersonales dp) throws SQLException;     
    Optional<DatosPersonales> buscarPorId(int id) throws SQLException;
    Optional<DatosPersonales> buscarPorDni(int dni) throws SQLException; 
    List<DatosPersonales> listarTodos() throws SQLException;
    boolean actualizar(DatosPersonales dp) throws SQLException;
    boolean eliminar(int id) throws SQLException;
}
