package impl;

import PeliculasDAO;
import db.Conexion;


public class PeliculasDAOjdbc implements PeliculasDAO {

    // Implementación de los métodos del DAO para Películas utilizando JDBC

    private static final String INSERT_SQL =
        "INSERT INTO PELICULAS (TITULO, ELENCO, DIRECTOR, GENERO, DURACION, AUDIO, SUBTITULOS, SINOPSIS) VALUES (?, ?, ?, ?)";
    private static final String SELECT_TITULO_SQL = 
        "SELECT ID, TITULO, ELENCO, DIRECTOR, GENERO, DURACION, AUDIO, SUBTITULOS, SINOPSIS FROM PELICULAS WHERE TITULO = ?";
    private static final String SELECT_GENERO_SQL =
        "SELECT ID, TITULO, ELENCO, DIRECTOR, GENERO, DURACION, AUDIO, SUBTITULOS, SINOPSIS FROM PELICULAS WHERE GENERO = ?";
    private static final String SELECT_ALL_SQL =
        "SELECT ID, TITULO, ELENCO, DIRECTOR, GENERO, DURACION, AUDIO, SUBTITULOS, SINOPSIS FROM PELICULAS ORDER BY TITULO";


}