package model.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import model.Pelicula;
import model.exceptions.BusquedaInvalidaException;
import model.exceptions.ErrorConexionAPIException;
import model.exceptions.PeliculaNoEncontradaException;

public class OmdbService {

    // API KEY obtenida en https://www.omdbapi.com/apikey.aspx
    private static final String API_KEY = "d751dcc3";
    private static final String BASE_URL = "https://www.omdbapi.com/?apikey=" + API_KEY + "&t=";

    /**
     * Busca una película por título en OMDb.
     *
     * @throws BusquedaInvalidaException      si el título viene vacío o muy corto.
     * @throws ErrorConexionAPIException      si hay problema de conexión/IO.
     * @throws PeliculaNoEncontradaException  si OMDb no encuentra la película.
     */
    public Pelicula buscarPelicula(String titulo)
            throws ErrorConexionAPIException, PeliculaNoEncontradaException {

        if (titulo == null || titulo.trim().length() < 2) {
            throw new BusquedaInvalidaException(titulo);
        }

        String tituloCod = titulo.trim().replace(" ", "+");
        String urlString = BASE_URL + tituloCod;

        String jsonRespuesta = realizarLlamada(urlString);

        return parsearPeliculaDesdeJson(jsonRespuesta, titulo);
    }

    // =======================================================
    // Llamada HTTP a la API de OMDb
    // =======================================================

    private String realizarLlamada(String urlString) throws ErrorConexionAPIException {

        StringBuilder jsonResponse = new StringBuilder();
        HttpURLConnection connection = null;

        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new ErrorConexionAPIException(
                        "Error en la conexión con la API OMDb. Código HTTP: " + responseCode);
            }

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()))) {

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    jsonResponse.append(inputLine);
                }
            }

            return jsonResponse.toString();
        } catch (IOException e) {
            throw new ErrorConexionAPIException("Error de I/O o conexión al consultar la API de OMDb.", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    // =======================================================
    // Parseo MUY SIMPLE del JSON a tu modelo Pelicula
    // (sin librerías externas)
    // =======================================================

    private Pelicula parsearPeliculaDesdeJson(String json, String tituloBuscado)
            throws PeliculaNoEncontradaException {

        // OMDb devuelve {"Response":"False","Error":"Movie not found!"} cuando no encuentra nada
        String response = extraerCampo(json, "Response");
        if (response == null || response.equalsIgnoreCase("False")) {
            throw new PeliculaNoEncontradaException(tituloBuscado);
        }

        // Campos básicos
        String titulo = extraerCampo(json, "Title");
        if (titulo == null || titulo.isBlank()) {
            titulo = tituloBuscado;
        }

        int anio = 0;
        String yearStr = extraerCampo(json, "Year");
        if (yearStr != null && !yearStr.isBlank()) {
            try {
                anio = Integer.parseInt(yearStr.substring(0, 4));
            } catch (Exception ignored) { }
        }

        String director = valorONoDisponible(extraerCampo(json, "Director"), "Desconocido");
        String elenco = valorONoDisponible(extraerCampo(json, "Actors"), "N/D");
        String sinopsis = valorONoDisponible(extraerCampo(json, "Plot"), "Sin sinopsis disponible.");

        double duracion = 0;
        String runtime = extraerCampo(json, "Runtime"); // ejemplo: "148 min"
        if (runtime != null && !runtime.isBlank() && !runtime.equalsIgnoreCase("N/A")) {
            try {
                String minutos = runtime.split(" ")[0];
                duracion = Double.parseDouble(minutos);
            } catch (Exception ignored) { }
        }

        double rating = 0;
        String ratingStr = extraerCampo(json, "imdbRating");
        if (ratingStr != null && !ratingStr.isBlank() && !ratingStr.equalsIgnoreCase("N/A")) {
            try {
                rating = Double.parseDouble(ratingStr);
            } catch (Exception ignored) { }
        }

        String posterUrl = extraerCampo(json, "Poster");
        if (posterUrl != null && posterUrl.equalsIgnoreCase("N/A")) {
            posterUrl = null;
        }

        // Construimos tu Pelicula usando setters
        Pelicula pelicula = new Pelicula();
        pelicula.setTitulo(titulo);
        pelicula.setAnio(anio);
        pelicula.setDirector(director);
        pelicula.setElenco(elenco);
        pelicula.setSinopsis(sinopsis);
        pelicula.setDuracion(duracion);
        pelicula.setRatingPromedio(rating);
        pelicula.setPosterUrl(posterUrl);

        return pelicula;
    }

    // =======================================================
    // Helpers de parseo
    // =======================================================

    /**
     * Extrae un campo string de un JSON muy simple del estilo:
     *  "clave":"valor"
     * No maneja todos los casos posibles, pero alcanza para OMDb.
     */
    private String extraerCampo(String json, String clave) {
        if (json == null || clave == null) return null;

        String pattern = "\"" + clave + "\":\"";
        int idx = json.indexOf(pattern);
        if (idx == -1) return null;

        idx += pattern.length();
        StringBuilder sb = new StringBuilder();

        for (int i = idx; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '\\') {
                // carácter escapado, agregamos el siguiente tal cual si existe
                if (i + 1 < json.length()) {
                    i++;
                    sb.append(json.charAt(i));
                }
            } else if (c == '\"') {
                break;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    private String valorONoDisponible(String valor, String porDefecto) {
        if (valor == null || valor.isBlank() || valor.equalsIgnoreCase("N/A")) {
            return porDefecto;
        }
        return valor;
    }
}
