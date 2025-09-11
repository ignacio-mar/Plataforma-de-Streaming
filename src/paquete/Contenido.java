package paquete;

import java.util.ArrayList;
import java.util.List;

public abstract class Contenido {
    private String titulo;
    private String descripcion;
    private String director;
    private List<String> actores;
    private String genero;
    private List<String> restriccionesGeograficas;
    private String tipoDeContenido;
    private long id;

    public Contenido() {
        this.actores = new ArrayList<>();
        this.restriccionesGeograficas = new ArrayList<>();
    }

    // --- Getters & Setters ---
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public List<String> getActores() { return actores; }
    public void setActores(List<String> actores) { this.actores = actores; }

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

    // --- Métodos para actores ---
    public void agregarActor(String actor) {
        if (actor != null && !actor.isEmpty() && !actores.contains(actor)) {
            actores.add(actor);
        }
    }

    public void eliminarActor(String actor) {
        actores.remove(actor);
    }

    // --- Métodos para restricciones geográficas ---
    public void agregarPaisDeRestriccionGeografica(String pais) {
        if (pais != null && !pais.isEmpty() && !restriccionesGeograficas.contains(pais)) {
            restriccionesGeograficas.add(pais);
        }
    }

    public void eliminarPaisDeRestriccionGeografica(String pais) {
        restriccionesGeograficas.remove(pais);
    }

    @Override
    public String toString() {
        return "Contenido{" +
                "titulo='" + titulo + '\'' +
                ", genero='" + genero + '\'' +
                ", id=" + id +
                '}';
    }
}

