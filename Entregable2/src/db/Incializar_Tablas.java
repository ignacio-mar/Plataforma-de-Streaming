package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Incializar_Tablas {

    private void creacionDeTablasEnBD(Connection connection) throws SQLException {
        Statement stmt = connection.createStatement();

        String sql = "CREATE TABLE IF NOT EXISTS DATOS_PERSONALES (" +
                "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "DNI INTEGER NOT NULL UNIQUE," +
                "NOMBRE VARCHAR(20) NOT NULL," +
                "APELLIDO VARCHAR(15) NOT NULL," +
                "PAIS_RESIDENCIA VARCHAR(30) NOT NULL," +
                "NUMERO_TELEFONO TEXT" +
                ");";
        stmt.executeUpdate(sql);

        
        sql = "CREATE TABLE IF NOT EXISTS USUARIO (" +
        "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
        "NOMBRE_USUARIO TEXT NOT NULL UNIQUE," +
        "EMAIL TEXT NOT NULL UNIQUE," +
        "CONTRASENIA TEXT NOT NULL," +
        "DNI_PERSONA INTEGER NOT NULL," +
        "CONSTRAINT USUARIO_DATOS_PERSONALES_FK FOREIGN KEY (DNI_PERSONA) REFERENCES DATOS_PERSONALES(DNI)" +
        ");";
        stmt.executeUpdate(sql);
        
sql = "CREATE TABLE IF NOT EXISTS PELICULA (" +
      "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
      "TITULO TEXT(100) NOT NULL," +
      "ELENCO TEXT(500) NOT NULL," +
      "DIRECTOR TEXT(100) NOT NULL," +
      "GENERO TEXT(100) NOT NULL," +      
      "DURACION REAL NOT NULL," +         
      "AUDIO TEXT(100) NOT NULL," +       
      "SUBTITULOS TEXT(100) NOT NULL," +  
      "SINOPSIS TEXT(500)" +             
      ");";
stmt.executeUpdate(sql);

sql = "CREATE TABLE IF NOT EXISTS RESENIA (" +
      "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
      "CALIFICACION INTEGER NOT NULL," +
      "COMENTARIO TEXT(500)," +
      "APROBADO INTEGER DEFAULT (1) NOT NULL," +
      "FECHA_HORA DATETIME NOT NULL," +
      "ID_USUARIO INTEGER NOT NULL," +
      "ID_PELICULA INTEGER NOT NULL," +
      "CONSTRAINT RESENIA_USUARIO_FK FOREIGN KEY (ID_USUARIO) REFERENCES USUARIO(ID)," +
      "CONSTRAINT RESENIA_PELICULA_FK FOREIGN KEY (ID_PELICULA) REFERENCES PELICULA(ID)" + // <-- singular
      ");";
stmt.executeUpdate(sql);

        stmt.close();
    }

    public static void Incializar_Estructura(Connection connection) throws SQLException {
        new Incializar_Tablas().creacionDeTablasEnBD(connection);
    }
}
