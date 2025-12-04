import db.Conexion;
import java.sql.Connection;

public class TestConexion {
    public static void main(String[] args) {
        System.out.println("Intentando conectar a la base de datos...");
        
        Connection con = Conexion.getCon();
        
        if (con != null) {
            System.out.println("✓ ÉXITO: Conexión establecida correctamente");
            System.out.println("✓ La base de datos fue creada en: Entregable2/db/BaseDeDatos.db");
        } else {
            System.out.println("✗ ERROR: La conexión falló");
        }
        
        Conexion.close();
    }
}
