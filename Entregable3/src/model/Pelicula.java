package model;

import model.enums.Generos;
import model.enums.Idiomas;

public class Pelicula {


    private int id;
    private String titulo;
    private String elenco;
    private String director;
    private Generos genero;
    private double duracion;
    private Idiomas audio;
    private Idiomas subtitulos;
    private String sinopsis;

    private double ratingPromedio;   
    private int anio;                
    private String posterUrl;        
    public Pelicula() {
    }

    public Pelicula(int id,
                    String titulo,
                    String elenco,
                    String director,
                    Generos genero,
                    double duracion,
                    Idiomas audio,
                    Idiomas subtitulos,
                    String sinopsis) {
        this.id = id;
        this.titulo = titulo;
        this.elenco = elenco;
        this.director = director;
        this.genero = genero;
        this.duracion = duracion;
        this.audio = audio;
        this.subtitulos = subtitulos;
        this.sinopsis = sinopsis;
    }

    public Pelicula(String titulo,
                    String elenco,
                    String director,
                    Generos genero,
                    double duracion,
                    Idiomas audio,
                    Idiomas subtitulos,
                    String sinopsis) {
        this(0, titulo, elenco, director, genero, duracion, audio, subtitulos, sinopsis);
    }


    public Pelicula(String titulo,
                    String elenco,
                    String director,
                    Generos genero,
                    double duracion,
                    Idiomas audio,
                    Idiomas subtitulos,
                    String sinopsis,
                    double ratingPromedio,
                    int anio,
                    String posterUrl) {
        this(0, titulo, elenco, director, genero, duracion, audio, subtitulos, sinopsis);
        this.ratingPromedio = ratingPromedio;
        this.anio = anio;
        this.posterUrl = posterUrl;
    }

   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getElenco() {
        return elenco;
    }

    public void setElenco(String elenco) {
        this.elenco = elenco;
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

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }


    @Override
    public String toString() {
        return "Pelicula{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", genero=" + genero +
                ", ratingPromedio=" + ratingPromedio +
                ", anio=" + anio +
                '}';
    }
}
