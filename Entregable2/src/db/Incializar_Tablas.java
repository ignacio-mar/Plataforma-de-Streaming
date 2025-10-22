package BD;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Incializar_Tablas{

    private void creacionDeTablasEnBD(Connection connection) throws SQLException{
        Statement stmt;
        stmt = connection.createStatement();
        String sql=" CREATE TABLE IF NOT EXISTS DATOS_PERSONALES ("+
        "DNI INTEGER NOT NULL PRIMARY KEY ,"+
        "NOMBRE VARCHAR(20) NOT NULL,"+
        "APELLIDO VARCHAR(15) NOT NULL,"+
        "PAIS_RESIDENCIA VARCHAR(30) NOT NULL"+    
        "NUMERO_TELEFONO BIGINT"+ ");";
        stmt.executeUpdate(sql);
        sql=" CREATE TABLE IF NOT EXISTS PELICULA ("+
        "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
        "GENERO TEXT(1) NOT NULL,"+
        "TITULO TEXT(100) NOT NULL,"+
        "RESUMEN TEXT(500),"+
        "DIRECTOR TEXT(100) NOT NULL,"+
        "DURACION REAL NOT NULL"+
        ");";
        stmt.executeUpdate(sql);
        sql=" CREATE TABLE IF NOT EXISTS USUARIO ("+
        "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
        "NOMBRE_USUARIO TEXT NOT NULL,"+
        "EMAIL TEXT NOT NULL,"+
        "CONTRASENIA TEXT NOT NULL,"+
        "ID_DATOS_PERSONALES INTEGER NOT NULL,"+
        "CONSTRAINT USUARIO_DATOS_PERSONALES_FK FOREIGN KEY (ID_DATOS_PERSONALES) REFERENCES DATOS_PERSONALES(ID)"+
        ");";
        stmt.executeUpdate(sql);
        
        sql=" CREATE TABLE IF NOT EXISTS RESENIA ("+
        "ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
        "CALIFICACION INTEGER NOT NULL,"+
        "COMENTARIO TEXT(500),"+
        "APROBADO INTEGER DEFAULT (1) NOT NULL,"+
        "FECHA_HORA DATETIME NOT NULL,"+
        "ID_USUARIO INTEGER NOT NULL,"+
        "ID_PELICULA INTEGER NOT NULL,"+
        "CONSTRAINT RESENIA_USUARIO_FK FOREIGN KEY (ID_USUARIO) REFERENCES USUARIO(ID),"+
        "CONSTRAINT RESENIA_PELICULA_FK FOREIGN KEY (ID_PELICULA) REFERENCES PELICULA(ID)"+
        ");";
        stmt.executeUpdate(sql);
        stmt.close();

    }

    public static void Incializar_Estructura(Connection connection) throws SQLException{
        new Incializar_Tablas().creacionDeTablasEnBD(connection);
    }   
        
}

