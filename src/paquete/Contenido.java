package paquete;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase abstracta que representa un contenido audiovisual.
 * Contiene atributos comunes como título, descripción, director, actores,
 * género, restricciones geográficas, tipo de contenido e identificador.
 *
 * Esta clase sirve como base para distintos tipos de contenidos,
 * por ejemplo películas, series o metrajes.
 *
 * Ejemplo de uso (a través de una subclase):
 * Contenido c = new Metraje();
 * c.setTitulo("Ejemplo");
 * c.agregarActor("Actor Principal");
 *
 * @author grupo32

 */
public abstract class Contenido {
    private String titulo;
    private String descripcion;
    private String director;
    private List<String> actores;
    private String genero;
    private List<String> restriccionesGeograficas;
    private String tipoDeContenido;
    private long id;

    /**
     * Constructor por defecto de la clase Contenido.
     * Inicializa las listas de actores y de restricciones geográficas vacías.
     */
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

    /**
     * Agrega un actor a la lista si no está repetido ni es nulo o vacío.
     *
     * @param actor nombre del actor a agregar
     */
    public void agregarActor(String actor) {
        if (actor != null && !actor.isEmpty() && !actores.contains(actor)) {
            actores.add(actor);
        }
    }

    /**
     * Elimina un actor de la lista si existe.
     *
     * @param actor nombre del actor a eliminar
     */
    public void eliminarActor(String actor) {
        actores.remove(actor);
    }

    // --- Métodos para restricciones geográficas ---

    /**
     * Agrega un país a la lista de restricciones geográficas
     * si no está repetido ni es nulo o vacío.
     *
     * @param pais nombre del país a agregar como restricción
     */
    public void agregarPaisDeRestriccionGeografica(String pais) {
        if (pais != null && !pais.isEmpty() && !restriccionesGeograficas.contains(pais)) {
            restriccionesGeograficas.add(pais);
        }
    }

    /**
     * Elimina un país de la lista de restricciones geográficas si existe.
     *
     * @param pais nombre del país a eliminar de las restricciones
     */
    public void eliminarPaisDeRestriccionGeografica(String pais) {
        restriccionesGeograficas.remove(pais);
    }

    /**
     * Devuelve una representación en cadena del objeto Contenido,
     * mostrando el título, género e identificador.
     *
     * @return una cadena con el formato:
     * Contenido{titulo='...', genero='...', id=...}
     */
    @Override
    public String toString() {
        return "Contenido{" +
                "titulo='" + titulo + '\'' +
                ", genero='" + genero + '\'' +
                ", id=" + id +
                '}';
    }
}


