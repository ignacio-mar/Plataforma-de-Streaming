package dao.impl;

import java.sql.*;
import java.util.*;
import dao.PeliculasDAO;
import db.Conexion;
import model.Pelicula;
import model.Usuario;
import model.Enums.Generos;
import model.Enums.Idiomas;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Comparator;

public class PeliculasDAOjdbc implements PeliculasDAO {

    // Implementación de los métodos del DAO para Películas utilizando JDBC

    private static final String INSERT_SQL =
        "INSERT INTO PELICULAS (TITULO, DIRECTOR, DURACION, SINOPSIS) VALUES (?, ?, ?, ?)";
    private static final String SELECT_TITULO_SQL = 
        "SELECT ID, TITULO, ELENCO, DIRECTOR,DURACION, SINOPSIS FROM PELICULAS WHERE TITULO = ?";
    private static final String SELECT_GENERO_SQL =
        "SELECT ID, TITULO, DIRECTOR, GENERO, DURACION, SINOPSIS FROM PELICULAS WHERE GENERO = ?";
    private static final String SELECT_ALL_SQL =
        "SELECT ID, TITULO, DIRECTOR, GENERO, DURACION,AUDIO, SUBTITULOS,SINOPSIS FROM PELICULAS ORDER BY TITULO";
    


    private Connection getConnection() throws SQLException {
        return Conexion.getCon();
    }

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

    @Override
    public Optional<Pelicula> buscarPorDuracion(double duracion) throws SQLException {
        try (PreparedStatement ps = getConnection().prepareStatement(SELECT_TITULO_SQL)) {
            ps.setDouble(1, duracion);

            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(resultSetToPelicula(rs));
                }
                return Optional.empty();
            }
        }
    }

    
    @Override
    public List<Pelicula> listarTodos(Comparator<Pelicula> comparador) throws SQLException {
        if (comparador == null) {
            throw new IllegalArgumentException("El comparador es obligatorio. Usar ComparatorNombreUsuario.POR_NOMBRE_USUARIO o ComparatorEmailUsuario.POR_EMAIL");
        }
        List<Pelicula> peliculas = new ArrayList<>();
        try (Statement stmt = getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_SQL)) {
                
            while (rs.next()) {
                Pelicula pelicula = new Pelicula(rs.getInt("ID"), rs.getString("titulo"),rs.getString("elenco"),rs.getString("director"),rs.getString("genero"),rs.getDouble("duracion"),rs.getString("idiomas"),rs.getString("subtitulos"),rs.getString("sinopsis"));
                peliculas.add(pelicula);
            }
             }
    
        // Ordenamos la lista según el comparador proporcionado
        peliculas.sort(comparador);
        return peliculas;
    }


}
