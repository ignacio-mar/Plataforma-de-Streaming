package db;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static Connection con = null;

    static {
        try {
            // Método 1: Intentar desde la ubicación del JAR/clase compilada
            URL classLocation = Conexion.class.getProtectionDomain().getCodeSource().getLocation();
            Path binDbPath = Paths.get(classLocation.toURI());
            
            System.out.println("=== DEBUG CONEXION ===");
            System.out.println("Class location URL: " + classLocation);
            System.out.println("Class location path: " + binDbPath.toAbsolutePath());
            
            // Navegar desde bin/db/ -> bin/ -> Entregable2/
            Path binPath = binDbPath.getParent(); // De bin/db/ a bin/
            // El padre de bin es la carpeta Entregable2
            Path entregable2Path = binPath; // bin está dentro de Entregable2
            
            System.out.println("Bin path: " + binPath.toAbsolutePath());
            System.out.println("Entregable2 path: " + entregable2Path.toAbsolutePath());
            
            // Crear carpeta db en Entregable2
            Path dbDir = entregable2Path.resolve("db");
            Files.createDirectories(dbDir);
            System.out.println("DB Dir created/verified: " + dbDir.toAbsolutePath());
            
            Path dbFile = dbDir.resolve("BaseDeDatos.db");
            String url = "jdbc:sqlite:" + dbFile.toString();
            
            System.out.println("DB URL = " + url);
            System.out.println("DB File will be at: " + dbFile.toAbsolutePath());
            System.out.println("=== FIN DEBUG ===");

            con = DriverManager.getConnection(url);
            System.out.println("✓ Conexión exitosa a la BD");

            db.Incializar_Tablas.Incializar_Estructura(con);
            System.out.println("✓ Tablas inicializadas correctamente");

        } catch (Exception e) {
            System.out.println("✗ Error inicializando conexión a la BD:");
            e.printStackTrace();
            con = null;
        }
    }

    public static Connection getCon() {
        return con;
    }

    private Conexion() {}

    public static void close() {
        if (con != null) {
            try {
                con.close();
                System.out.println("Conexión a la base de datos cerrada correctamente.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
