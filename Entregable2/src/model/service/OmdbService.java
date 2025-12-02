package model.service;

import model.exceptions.BusquedaInvalidaException;
import model.exceptions.ErrorConexionAPIException;
import model.exceptions.PeliculaNoEncontradaException;
import model.Pelicula;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class OmdbService {
    private static final String API_KEY = ""; // NO SE CUAL ES LA API KEY
    private static final String BASE_URL = "http://www.omdbapi.com/?apikey=" + API_KEY + "&t=";

    public Pelicula buscarPelicula(String titulo)
        throws ErrorConexionAPIException, PeliculaNoEncontradaException {

            String tituloCod = titulo.trim().replace(" ", "+");
            String urlString = BASE_URL + tituloCod;

            String jsonRespuesta = realizarLlamada(urlString);

            return parsearPeliculaDesdeJson(jsonRespuesta, titulo);
        }

        private String realizarLlamada(String urlString) throws ErrorConexionAPIException{

            StringBuilder jsonResponse = new StringBuilder();
            HttpURLConnection connection = null;

            try {
                URL url = new URL(urlString);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);

                int responseCode = connection.getResponseCode();
                if(responseCode != HttpURLConnection.HTTP_OK) {
                    throw new ErrorConexionAPIException("Error en la conexion con la API OMDB");
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
            return null;
        }

        private Pelicula parsearPeliculaDesdeJson(String json, String tituloBuscado) throws PeliculaNoEncontradaException {
            JSONObject jsonObject = new JSONObject(json);

            if (jsonObject.getString("Response").equals("False")) {
                throw new PeliculaNoEncontradaException(tituloBuscado);
            }

            String titulo = jsonObject.getString("Title");

            int anio = Integer.parseInt(jsonObject.getString("Year").substring(0,4));
            String director = jsonObject.getString("Director");
            String elenco = jsonObject.getString("Actors");
            String sinopsis = jsonObject.getString("Plot");

            double duracion = 0;
            try {
                String duracionStr = jsonObject.getString("Runtime").split(" ")[0];
                duracion = Double.parseDouble(duracionStr);
            } catch (Exception e) {
                duracion = 0;
            }

            return new Pelicula(titulo, anio, director, elenco, sinopsis, duracion);
        }
}