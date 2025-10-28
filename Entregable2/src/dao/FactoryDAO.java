package dao;

import dao.impl.DatosPersonalesDAOimp;
import dao.impl.PeliculasDAOjdbc;
import dao.impl.ReseñasDAOjdbc;
import dao.impl.UsuariosDAOjdbc;
import java.sql.Connection;

public class FactoryDAO {
    public static UsuariosDAO getUsuariosDAO(Connection conexion, DatosPersonalesDAO datosPersonalesDAO) {
        return new UsuariosDAOjdbc(conexion, datosPersonalesDAO);
    }
    public static DatosPersonalesDAO getDatosPersonalesDAO() {
        return new DatosPersonalesDAOimp();
    }
    public static PeliculasDAO getPeliculasDAO() {
        return new PeliculasDAOjdbc();
    }
    public static ReseñasDAO getReseñasDAO() {
        return new ReseñasDAOjdbc();
    }
}