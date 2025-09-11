package paquete;

import java.util.ArrayList;
import java.util.List;

public class Contenido {
     private String titulo;
     private String descripcion;
     private String director;
     private List<String> actores;
     private String genero;
     private List<String> restriccionesGeograficas;    
     private String tipoDeContenido;                    // por ej: documental,pelicula,etc.
     private long id;

    
     public Contenido() {
        this.restriccionesGeograficas = new ArrayList<>();
     }

    public Contenido(String titulo, String genero, long id) {
        this();
        this.titulo = titulo;
        this.genero = genero;
        this.id = id;
    }

    // --- Getters & Setters ---
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getEnlaceLink() { return enlaceLink; }
    public void setEnlaceLink(String enlaceLink) { this.enlaceLink = enlaceLink; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public List<String> getRestriccionesGeograficas() { return restriccionesGeograficas; }
    public void setRestriccionesGeograficas(List<String> restriccionesGeograficas) {
        this.restriccionesGeograficas = restriccionesGeograficas;
    }

    public String getTipoDeContenido() { return tipoDeContenido; }
    public void setTipoDeContenido(String tipoDeContenido) { this.tipoDeContenido = tipoDeContenido; }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    @Override
    public String toString() {
        return "Contenido{" +
                "titulo='" + titulo + '\'' +
                ", genero='" + genero + '\'' +
                ", id=" + id +
                '}';
    }
}

