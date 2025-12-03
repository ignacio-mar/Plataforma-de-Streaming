package model;

public class Pelicula {
    private String titulo;
    private String genero;
    private String resumen;
    private String posterUrl;
    private int año;
    private double votacion;
    private int conteoVotos;

    public Pelicula(String titulo, String genero, String resumen, String posterUrl, int año, double votacion, int conteoVotos) {
        this.titulo = titulo;
        this.genero = genero;
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