package service;

import dao.PeliculasDAO;
import dao.compar.ComparadorDuracion;
import dao.compar.ComparadorGenero;
import dao.compar.ComparadorTitulo;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import model.Pelicula;
import model.enums.Generos;
import model.enums.Idiomas;

public class PeliculasService {

    private final PeliculasDAO peliculasDao;
    private final List<Pelicula> cachePeliculas = new ArrayList<>();

    public PeliculasService(PeliculasDAO peliculasDao) {
        this.peliculasDao = peliculasDao;
    }

    public boolean validarTitulo(String titulo) {
        return titulo != null && !titulo.isEmpty();
    }

    public boolean validarElenco(String elenco) {
        return elenco != null && !elenco.isEmpty() && elenco.contains(",");
    }

    public boolean validarDirector(String director) {
        return director != null &&
               !director.isEmpty() &&
               director.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$");
    }

    public boolean validarDuracion(double duracion) {
        return duracion > 0;
    }

    public Pelicula crearPelicula(String titulo,
                                  String elenco,
                                  String director,
                                  Generos genero,
                                  double duracion,
                                  Idiomas audio,
                                  Idiomas subtitulos,
                                  String sinopsis) throws SQLException {

        Pelicula pelicula = new Pelicula(
                titulo,
                elenco,
                director,
                genero,
                duracion,
                audio,
                subtitulos,
                sinopsis
        );

        return peliculasDao.guardar(pelicula);
    }

    public List<Pelicula> listarPorTitulo() throws SQLException {
        List<Pelicula> lista = peliculasDao.listarTodos();
        lista.sort(ComparadorTitulo.POR_TITULO);
        return lista;
    }

    public List<Pelicula> listarPorGenero() throws SQLException {
        List<Pelicula> lista = peliculasDao.listarTodos();
        lista.sort(ComparadorGenero.POR_GENERO);
        return lista;
    }

    public List<Pelicula> listarPorDuracion() throws SQLException {
        List<Pelicula> lista = peliculasDao.listarTodos();
        lista.sort(ComparadorDuracion.POR_DURACION);
        return lista;
    }

    public List<Pelicula> listarTodos() throws SQLException {
        return peliculasDao.listarTodos();
    }

    public void importarDesdeCsv(String rutaCsv) throws IOException, SQLException {
        importarDesdeCsv(rutaCsv, null);
    }

    public void importarDesdeCsv(String rutaCsv, Consumer<Integer> onProgress) throws IOException, SQLException {
        cachePeliculas.clear();

        int total = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(rutaCsv))) {
            while (br.readLine() != null) {
                total++;
            }
        }
        if (total > 0) {
            total--;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(rutaCsv))) {
            String linea;
            boolean esPrimera = true;
            int count = 0;

            while ((linea = br.readLine()) != null) {
                if (esPrimera) {
                    esPrimera = false;
                    continue;
                }

                List<String> campos = parseLineCsv(linea);
                if (campos.size() < 9) {
                    continue;
                }

                String releaseDate      = campos.get(0);
                String title            = campos.get(1);
                String overview         = campos.get(2);
                String voteAverageStr   = campos.get(5);
                String originalLangCode = campos.get(6);
                String genreRaw         = campos.get(7);
                String posterUrl        = campos.get(8);

                int anio = extraerAnio(releaseDate);
                double ratingPromedio = parsearDoubleSeguro(voteAverageStr);

                Generos genero = mapearGeneroDesdeCsv(genreRaw);
                Idiomas idiomaOriginal = mapearIdiomaDesdeCodigo(originalLangCode);

                String elenco = "Elenco no disponible";
                String director = "Director no disponible";
                double duracion = 0.0;
                Idiomas audio = idiomaOriginal;
                Idiomas subtitulos = Idiomas.CASTELLANO;

                Pelicula pelicula = new Pelicula(
                        title,
                        elenco,
                        director,
                        genero,
                        duracion,
                        audio,
                        subtitulos,
                        overview,
                        ratingPromedio,
                        anio,
                        posterUrl
                );

                peliculasDao.guardar(pelicula);
                cachePeliculas.add(pelicula);

                count++;
                if (onProgress != null && total > 0) {
                    int porc = (count * 100) / total;
                    onProgress.accept(Math.min(porc, 100));
                }
            }
        }

        ordenarCachePorRatingDesc();
    }

    public void importarDesdeCsvAsync(String rutaCsv,
                                      Runnable onSuccess,
                                      Consumer<Exception> onError) {
        importarDesdeCsvAsync(rutaCsv, onSuccess, onError, null);
    }

    public void importarDesdeCsvAsync(String rutaCsv,
                                      Runnable onSuccess,
                                      Consumer<Exception> onError,
                                      Consumer<Integer> onProgress) {
        new Thread(() -> {
            try {
                importarDesdeCsv(rutaCsv, onProgress);
                if (onSuccess != null) {
                    onSuccess.run();
                }
            } catch (Exception e) {
                if (onError != null) {
                    onError.accept(e);
                } else {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public List<Pelicula> obtenerTop10PorRating() throws SQLException {
        if (cachePeliculas.isEmpty()) {
            cachePeliculas.addAll(peliculasDao.listarTodos());
            ordenarCachePorRatingDesc();
        }
        int n = Math.min(10, cachePeliculas.size());
        return new ArrayList<>(cachePeliculas.subList(0, n));
    }

    private List<String> parseLineCsv(String line) {
        List<String> campos = new ArrayList<>();
        if (line == null || line.isEmpty()) {
            return campos;
        }

        StringBuilder actual = new StringBuilder();
        boolean dentroDeComillas = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '\"') {
                dentroDeComillas = !dentroDeComillas;
            } else if (c == ',' && !dentroDeComillas) {
                campos.add(actual.toString());
                actual.setLength(0);
            } else {
                actual.append(c);
            }
        }

        campos.add(actual.toString());
        return campos;
    }

    private int extraerAnio(String releaseDate) {
        if (releaseDate == null || releaseDate.length() < 4) {
            return 0;
        }
        try {
            return Integer.parseInt(releaseDate.substring(0, 4));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private double parsearDoubleSeguro(String s) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private Generos mapearGeneroDesdeCsv(String genreRaw) {
        if (genreRaw == null || genreRaw.isBlank()) {
            return Generos.FICCION;
        }

        String primerGenero = genreRaw.split(",")[0].trim();

        switch (primerGenero) {
            case "Action":
            case "Adventure":
            case "War":
            case "Western":
            case "Crime":
                return Generos.ACCION;
            case "Comedy":
                return Generos.COMEDIA;
            case "Horror":
            case "Thriller":
                return Generos.TERROR;
            case "Romance":
                return Generos.ROMANTICA;
            case "Science Fiction":
            case "Fantasy":
            case "Animation":
            case "Family":
            case "Mystery":
            case "Drama":
            default:
                return Generos.FICCION;
        }
    }

    private Idiomas mapearIdiomaDesdeCodigo(String code) {
        if (code == null) return Idiomas.CASTELLANO;

        switch (code) {
            case "es":
                return Idiomas.CASTELLANO;
            case "en":
                return Idiomas.INGLES;
            case "pt":
                return Idiomas.PORTUGUES;
            case "fr":
                return Idiomas.FRANCES;
            default:
                return Idiomas.INGLES;
        }
    }

    private void ordenarCachePorRatingDesc() {
        cachePeliculas.sort(Comparator.comparingDouble(Pelicula::getRatingPromedio).reversed());
    }
}
