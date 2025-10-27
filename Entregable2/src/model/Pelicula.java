package model;

import model.enums.Generos;
import model.enums.Idiomas;

public class Pelicula {
    //atributos de la clase Pelicula
    private int id;
    private String titulo;
    private String elenco;
    private String director;
    private Generos genero;
    private double duracion;
    private Idiomas audio;
    private Idiomas subtitulos;
    private String sinopsis;
    
    //Constructores
    public Pelicula (){}

    public Pelicula (String titulo, String elenco, String director, Generos genero, double duracion, Idiomas audio, Idiomas subtitulos, String sinopsis)  {
        this.titulo = titulo;
        this.elenco = elenco;
        this.director = director;
        this.genero = genero;
        this.duracion = duracion;
        this.audio = audio;
        this.sinopsis = sinopsis;
    }

    //Setters y getters
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

}