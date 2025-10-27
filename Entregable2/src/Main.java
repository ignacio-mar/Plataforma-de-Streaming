import dao.DatosPersonalesDAO;
import dao.impl.DatosPersonalesDAOimp;
import dao.UsuariosDAO;
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
            Scanner sc = new Scanner(System.in);
            Menu menu = new Menu(sc, datosPersonalesDAO, usuariosDAO);
            menu.iniciar(); // <<--- este método tiene el while y toda la lógica del menú

            sc.close();

        } catch (Exception e) {
            System.err.println("Error al iniciar la aplicación: " + e.getMessage());
        }
    }
}
