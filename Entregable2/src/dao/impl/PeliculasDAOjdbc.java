package dao.impl;

import java.sql.*;
import java.sql.SQLException;
import java.util.*;
import java.util.Optional;
import dao.PeliculasDAO;
import db.Conexion;
import model.Pelicula;
import model.enums.Generos;
import model.enums.Idiomas;


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
    
    private Connection getconnection() throws SQLException {
        return Conexion.getCon();
    }

    //Implementación del resultset para una instancia Pelicula
    private Pelicula resultSetToPelicula(ResultSet rs) throws SQLException {
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
        peli.setId(rs.getInt("ID"));
        return peli;
    }

    //Implementación método guardar para subir una película a la base de datos
    @Override
    public Pelicula guardar(Pelicula peli) throws SQLException {
        try (PreparedStatement ps = getconnection().prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
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

    //Implementación método buscar por género
    @Override
    public Optional<Pelicula> buscarPorGenero(Generos genero) throws SQLException {
        try (PreparedStatement ps = getconnection().prepareStatement(SELECT_GENERO_SQL)) {
            ps.setString(1, genero.name());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(resultSetToPelicula(rs));
                }
                return Optional.empty();
            }
        }
    }

    //Implementación búsqueda por título de una película
    @Override
    public Optional<Pelicula> buscarPorTitulo(String Titulo) throws SQLException {
        try (PreparedStatement ps = getconnection().prepareStatement(SELECT_TITULO_SQL)) {
            ps.setString(1, Titulo);

            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(resultSetToPelicula(rs));
                }
                return Optional.empty();
            }
        }
    }

    //Implementación búsqueda por duración de una película
    @Override
    public Optional<Pelicula> buscarPorDuracion(double duracion) throws SQLException {
        try (PreparedStatement ps = getconnection().prepareStatement(SELECT_TITULO_SQL)) {
            ps.setDouble(1, duracion);

            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(resultSetToPelicula(rs));
                }
                return Optional.empty();
            }
        }
    }

    //Implementación del listado de todas las películas
    @Override
    public List<Pelicula> listarTodos() throws SQLException {
        List<Pelicula> lista = new ArrayList<>();
        
        try (Statement st = getconnection().createStatement();
             ResultSet rs = st.executeQuery(SELECT_ALL_SQL)) {
            
            while (rs.next()) {
                lista.add(resultSetToPelicula(rs));
            }
        }
        return lista;
    }
}
