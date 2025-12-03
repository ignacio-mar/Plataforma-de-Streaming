package model;
<<<<<<< HEAD
=======

import model.enums.Generos;
import model.enums.Idiomas;
>>>>>>> 2d3b8ecc1289a3e38c85563135f5495093f0bdb7

public class Pelicula {
    private String titulo;
<<<<<<< HEAD
    private String genero;
    private String resumen;
    private String posterUrl;
    private int año;
    private double votacion;
    private int conteoVotos;

    public Pelicula(String titulo, String genero, String resumen, String posterUrl, int año, double votacion, int conteoVotos) {
=======
    private String elenco;
    private String director;
    private Generos genero;
    private double duracion;
    private Idiomas audio;
    private Idiomas subtitulos;
    private String sinopsis;
    private int anio;
    
    public Pelicula (){}

    public Pelicula (String titulo, int anio, String director, String elenco, String sinopsis, double duracion){
        this.titulo = titulo;
        this.anio = anio; 
        this.director = director;
        this.elenco = elenco;
        this.sinopsis = sinopsis;
        this.duracion = duracion;
        
        // Valores por defecto o null (OMDb no los da)
        this.id = 0; 
        this.genero = null; 
        this.audio = null;
        this.subtitulos = null;
    }
    
    public Pelicula (int id,String titulo, String elenco, String director, Generos genero, double duracion, Idiomas audio, Idiomas subtitulos, String sinopsis)  {
        this.id=id;

    private double ratingPromedio;   
    private int anio;                
    private String posterUrl;        
 
    public Pelicula(int id, String titulo, String elenco, String director,
                    Generos genero, double duracion,
                    Idiomas audio, Idiomas subtitulos, String sinopsis)  {
        this.id = id;
>>>>>>> 2d3b8ecc1289a3e38c85563135f5495093f0bdb7
        this.titulo = titulo;
        this.genero = genero;
<<<<<<< HEAD
        this.resumen = resumen;
        this.posterUrl = posterUrl;
        this.año = año;
        this.votacion = votacion;
        this.conteoVotos = conteoVotos;
    }

    public String getTitulo() { return titulo; }
    public String getGenero() { return genero; }
    public String getResumen() { return resumen; }
    public String getPosterUrl() { return posterUrl; }
    public int getAño() { return año; }
    public double getVotacion() { return votacion; }
    public int getConteoVotos() { return conteoVotos; }
}
=======
        this.duracion = duracion;
        this.audio = audio;
        this.subtitulos = subtitulos;
        this.sinopsis = sinopsis;
    }


    public Pelicula(String titulo, String elenco, String director,
                    Generos genero, double duracion,
                    Idiomas audio, Idiomas subtitulos, String sinopsis) {
        this.titulo = titulo;
        this.elenco = elenco;
        this.director = director;
        this.genero = genero;
        this.duracion = duracion;
        this.audio = audio;
        this.subtitulos = subtitulos;
        this.sinopsis = sinopsis;
    }

    public Pelicula(String titulo, String elenco, String director,
                    Generos genero, double duracion,
                    Idiomas audio, Idiomas subtitulos, String sinopsis,
                    double ratingPromedio, int anio, String posterUrl) {
        this.titulo = titulo;
        this.elenco = elenco;
        this.director = director;
        this.genero = genero;
        this.duracion = duracion;
        this.audio = audio;
        this.subtitulos = subtitulos;
        this.sinopsis = sinopsis;
        this.ratingPromedio = ratingPromedio;
        this.anio = anio;
        this.posterUrl = posterUrl;
    }

    // Getters / setters

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getElenco() {
        return elenco;
    }

    public void setElenco(String nuevoElenco) {
        this.elenco = nuevoElenco;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Generos getGenero() {
        return genero;
    }

    public void setGenero(Generos genero) {
        this.genero = genero;
    }

    public double getDuracion() {
        return duracion;
    }

    public void setDuracion(double duracion) {
        this.duracion = duracion;
    }

    public Idiomas getAudio() {
        return audio;
    }

    public void setAudio(Idiomas audio) {
        this.audio = audio;
    }

    public Idiomas getSubtitulos() {
        return subtitulos;
    }

    public void setSubtitulos(Idiomas subtitulos) {
        this.subtitulos = subtitulos;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }



    public double getRatingPromedio() {
        return ratingPromedio;
    }

    public void setRatingPromedio(double ratingPromedio) {
        this.ratingPromedio = ratingPromedio;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

}
    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
}
>>>>>>> 2d3b8ecc1289a3e38c85563135f5495093f0bdb7
