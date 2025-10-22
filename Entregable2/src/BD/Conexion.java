package BD;
import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
private static Connection con = null;
static {
try {
con = DriverManager.getConnection("jdbc:sqlite:BaseDeDatos.db");
Incializar_Tablas.Incializar_Estructura(con);
} catch (java.sql.SQLException e) {
System.out.println("Error de SQL: "+e.getMessage());
}
}

public static Connection getCon() {
return con;
}
private Conexion() {
}
}


