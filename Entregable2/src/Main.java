<<<<<<< HEAD
import dao.DatosPersonalesDAO;
import dao.impl.DatosPersonalesDAOimp;
import db.Conexion;
import java.util.Scanner;
import ui.Menu;

public class Main {

=======
package model;
import db.Conexion;
public class Main  {
>>>>>>> e6552527140faf7d269962c83968fabcbba4253d
    public static void main(String[] args) {
        try {
            Conexion.getCon();
            DatosPersonalesDAO dao = new DatosPersonalesDAOimp();
            Scanner sc = new Scanner(System.in);
            Menu menu = new Menu(sc, dao);
            menu.iniciar(); // <<--- este método tiene el while y toda la lógica del menú

            sc.close();

        } catch (Exception e) {
            System.err.println("Error al iniciar la aplicación: " + e.getMessage());
        }
    }
}
