package dao.impl;

import dao.PeliculasDAO;
import db.Conexion;
import java.sql.*;
import java.util.*;
import model.Pelicula;
import model.enums.Generos;
import model.enums.Idiomas;

public class PeliculasDAOjdbc implements PeliculasDAO {

    // Implementación de los métodos del DAO para Películas utilizando JDBC

    private static final String INSERT_SQL =
        "INSERT INTO PELICULA (" +
        "TITULO, ELENCO, DIRECTOR, GENERO, DURACION, AUDIO, SUBTITULOS, SINOPSIS, " +
        "RATING_PROMEDIO, ANIO, POSTER" +
        ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_ALL_SQL =
        "SELECT ID, TITULO, ELENCO, DIRECTOR, GENERO, DURACION, AUDIO, SUBTITULOS, " +
        "SINOPSIS, RATING_PROMEDIO, ANIO, POSTER FROM PELICULA";

    private Connection getConnection() throws SQLException {
        return Conexion.getCon();
    }

    private Pelicula retornarPelicula(ResultSet rs) throws SQLException {
        Pelicula peli = new Pelicula(
            rs.getInt("ID"),
            rs.getString("TITULO"),
            rs.getString("ELENCO"),
            rs.getString("DIRECTOR"),
            Generos.valueOf(rs.getString("GENERO")),
            rs.getDouble("DURACION"),
            Idiomas.valueOf(rs.getString("AUDIO")),
            Idiomas.valueOf(rs.getString("SUBTITULOS")),
            rs.getString("SINOPSIS")
        );

        // campos nuevos
        peli.setRatingPromedio(rs.getDouble("RATING_PROMEDIO"));
        peli.setAnio(rs.getInt("ANIO"));
        peli.setPosterUrl(rs.getString("POSTER"));

        return peli;
    }

    @Override
    public Pelicula guardar(Pelicula peli) throws SQLException {
        try (PreparedStatement ps = getConnection().prepareStatement(
                INSERT_SQL,
                Statement.RETURN_GENERATED_KEYS
        )) {
            ps.setString(1, peli.getTitulo());
            ps.setString(2, peli.getElenco());
            ps.setString(3, peli.getDirector());
            ps.setString(4, peli.getGenero().name());
            ps.setDouble(5, peli.getDuracion());
            ps.setString(6, peli.getAudio().name());
            ps.setString(7, peli.getSubtitulos().name());
            ps.setString(8, peli.getSinopsis());

            // nuevos campos
            ps.setDouble(9, peli.getRatingPromedio());
            ps.setInt(10, peli.getAnio());
            ps.setString(11, peli.getPosterUrl());

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
    public List<Pelicula> listarTodos() throws SQLException {
        List<Pelicula> peliculas = new ArrayList<>();

        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_SQL)) {

            while (rs.next()) {
                peliculas.add(retornarPelicula(rs));
            }
        }

        return peliculas;
    }
}
