package dao.impl;

import java.sql.*;
import java.util.*;
import dao.PeliculasDAO;
import db.Conexion;
import model.Pelicula;
import model.enums.Generos;
import model.enums.Idiomas;

public class PeliculasDAOjdbc implements PeliculasDAO {

    // Implementación de los métodos del DAO para Películas utilizando JDBC

    private static final String INSERT_SQL =
        "INSERT INTO PELICULAS (TITULO, ELENCO, DIRECTOR, GENERO, DURACION, AUDIO, SUBTITULOS, SINOPSIS) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_SQL =
        "SELECT ID, TITULO, ELENCO, DIRECTOR, GENERO, DURACION, AUDIO, SUBTITULOS, SINOPSIS FROM PELICULAS";
    


    private Connection getConnection() throws SQLException {
        return Conexion.getCon();
    }

    /*private Pelicula resultSetToPelicula(ResultSet rs) throws SQLException {
        Pelicula peli = new Pelicula(
            rs.getString("TITULO"),
            rs.getString("ELENCO"),
            rs.getString("DIRECTOR"),
            Generos.valueOf(rs.getString("GENERO")),
            rs.getDouble("DURACION"),
            Idiomas.valueOf(rs.getString("AUDIO")),
            Idiomas.valueOf(rs.getString("SUBTITULOS")),
            rs.getString("SINOPSIS")
        );
        return peli;*/
    private Pelicula retornarPelicula(ResultSet rs) throws SQLException {
        Pelicula peli = new Pelicula(rs.getInt("ID"),
            rs.getString("TITULO"),
            rs.getString("ELENCO"),
            rs.getString("DIRECTOR"),
            Generos.valueOf(rs.getString("GENERO")),
            rs.getDouble("DURACION"),
            Idiomas.valueOf(rs.getString("AUDIO")),
            Idiomas.valueOf(rs.getString("SUBTITULOS")),
            rs.getString("SINOPSIS"));

        peli.setId(rs.getInt("ID"));
        return peli;
    }

    @Override
    public Pelicula guardar(Pelicula peli) throws SQLException {
        try (PreparedStatement ps = getConnection().prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, peli.getTitulo());
            ps.setString(2, peli.getElenco());
            ps.setString(3, peli.getDirector());
            ps.setString(4, peli.getGenero().name());
            ps.setDouble(5, peli.getDuracion());
            ps.setString(6, peli.getAudio().name());
            ps.setString(7, peli.getSubtitulos().name());
            ps.setString(8, peli.getSinopsis());
            
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    peli.setId(rs.getInt(1));
                }
            }
        return peli;
        }
    }


    @Override
    public List<Pelicula> listarTodos(Comparator<Pelicula> comparador) throws SQLException {
        if (comparador == null) {
            throw new IllegalArgumentException("El comparador es obligatorio. Usar ComparadorTitulo.POR_TITULO, ComparadorDuracion.POR_DURACION, etc.");
        }
        
        List<Pelicula> peliculas = new ArrayList<>();
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_SQL)) {
                
            while (rs.next()) {
                peliculas.add(retornarPelicula(rs));
            }
             }
    
        // Ordenamos la lista según el comparador proporcionado
        peliculas.sort(comparador);
        return peliculas;
    }

}