import dao.DatosPersonalesDAO;
import dao.PeliculasDAO;
import dao.ReseñasDAO;
import dao.UsuariosDAO;
import dao.impl.DatosPersonalesDAOimp;
import dao.impl.PeliculasDAOjdbc;
import dao.impl.ReseñasDAOjdbc;
import dao.impl.UsuariosDAOjdbc;
import db.Conexion;
import java.util.Scanner;
import ui.Menu;

public class Main {

    public static void main(String[] args) {
        try {
            Conexion.getCon();
            DatosPersonalesDAO datosPersonalesDAO = new DatosPersonalesDAOimp();
            UsuariosDAO usuariosDAO = new UsuariosDAOjdbc(Conexion.getCon(), datosPersonalesDAO);
            PeliculasDAO peliculasDAO = new PeliculasDAOjdbc();
            ReseñasDAO reseñasDAO = new ReseñasDAOjdbc();
            Scanner sc = new Scanner(System.in);
            Menu menu = new Menu(sc, datosPersonalesDAO, usuariosDAO, reseñasDAO, peliculasDAO);
            menu.iniciar();

            sc.close();

        } catch (Exception e) {
            System.err.println("Error al iniciar la aplicación: " + e.getMessage());
        }
    }
}
