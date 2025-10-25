package model;

import enums.Genero
import enums.Idiomas

public class Pelicula {
    private int id;
    private String titulo;
    private List<String> elenco;
    private String director;
    private Genero genero;
    private double duracion;
    private List<Idiomas> audio;
    private List<Idiomas> subtitulos;
    private String sinopsis;
    
    public Pelicula (){}

    public Pelicula (String titulo, List<String> elenco, String director, Genero genero, double duracion, List<Idiomas> audio, List<Idiomas> subtitulos, String sinopsis)  {
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

    public String getId() {
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

    public String getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public double getDuracion() {
        return duracion;
    }

    public void setDuracion(double duracion) {
        this.duracion = duracion;
    }

    public List<String> getAudio() {
        return audio;
    }

    public void setAudio(Idiomas audio) {
        this.audio = audio;
    }

    public List<String> getSubtitulos() {
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