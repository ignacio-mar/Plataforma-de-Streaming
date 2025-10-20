import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
     public static Connection getConection(){
        Connection miconexion = null;
        var base = "BaseDeDatos";
        var url = "jdbc:sqlite:"+base;
    
        try {
Class.forName("org.sqlite.JDBC");
miconexion = DriverManager.getConnection(url);
        }catch (Exception e){
 System.out.println("Error conectado a la base" + e);
        }
    return miconexion;
    }

}




   