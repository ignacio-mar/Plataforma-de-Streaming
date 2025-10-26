package model;

import model.Enums.Generos;
import model.Enums.Idiomas;
import java.util.List;

public class Pelicula {
    private int id;
    private String titulo;
    private List<String> elenco;
    private String director;
    private Generos genero;
    private double duracion;
    private List<Idiomas> audio;
    private List<Idiomas> subtitulos;
    private String sinopsis;
    
    public Pelicula (){}

    public Pelicula (String titulo, List<String> elenco, String director, Generos genero, double duracion, List<Idiomas> audio, List<Idiomas> subtitulos, String sinopsis)  {
        this.titulo = titulo;
        this.elenco = elenco;
        this.director = director;
        this.genero = genero;
        this.duracion = duracion;
        this.audio = audio;
        this.sinopsis = sinopsis;
    }

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
    
    public List<String> getElenco() {
        return elenco;
    }

    public void setElenco(List<String> nuevoElenco) {
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

    public List<Idiomas> getAudio() {
        return audio;
    }

    public void setAudio(List<Idiomas> audio) {
        this.audio = audio;
    }

    public List<Idiomas> getSubtitulos() {
        return subtitulos;
    }

    public void setSubtitulos(List<Idiomas> subtitulos) {
        this.subtitulos = subtitulos;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

}