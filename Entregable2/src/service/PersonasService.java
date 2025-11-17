package service;

import dao.DatosPersonalesDAO;
import model.DatosPersonales;
import model.enums.Paises;
import java.sql.SQLException;
import java.util.Optional;

public class PersonasService {

    private final DatosPersonalesDAO dpDao;

    public PersonasService(DatosPersonalesDAO dpDao) {
        this.dpDao = dpDao;
    }

    public boolean validarDni(int dni) {
        return dni > 0;
    }

    public boolean dniExiste(int dni) throws SQLException {
        return dpDao.buscarPorDni(dni).isPresent();
    }

    public boolean validarNombre(String nombre) {
        return !nombre.isEmpty() && nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$");
    }

    public boolean validarApellido(String apellido) {
        return !apellido.isEmpty() && apellido.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$");
    }

    public boolean validarTelefono(String telefono) {
        return !telefono.isEmpty();
    }

    public DatosPersonales crearPersona(String nombre, String apellido, int dni, Paises pais, String telefono) 
            throws SQLException {
        DatosPersonales dp = new DatosPersonales(nombre, apellido, dni, pais, telefono);
        return dpDao.guardar(dp);
    }

    public Optional<DatosPersonales> buscarPorId(int id) throws SQLException {
        return dpDao.buscarPorId(id);
    }

    public Optional<DatosPersonales> buscarPorDni(int dni) throws SQLException {
        return dpDao.buscarPorDni(dni);
    }

    public java.util.List<DatosPersonales> listarTodas() throws SQLException {
        return dpDao.listarTodos();
    }

    public boolean actualizarPersona(int id, String nombre, String apellido, int dni, Paises pais, String telefono) 
            throws SQLException {
        DatosPersonales mod = new DatosPersonales(nombre, apellido, dni, pais, telefono);
        mod.setId(id);
        return dpDao.actualizar(mod);
    }

    public boolean eliminarPersona(int id) throws SQLException {
        return dpDao.eliminar(id);
    }
}
