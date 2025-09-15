package paquete;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase abstracta que representa un contenido audiovisual.
 * Contiene atributos comunes como título, descripción, director, actores,
 * género, restricciones geográficas, tipo de contenido e identificador.
 *
 * Esta clase sirve como base para distintos tipos de contenidos,
 * por ejemplo películas, series o documentales.
 *
 * @author grupo32
 * @version 1.0
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

    /**
     * Obtiene el título del contenido.
     *
     * @return título asignado al contenido.
     */
    public String getTitulo() { return titulo; }

    /**
     * Define el título del contenido.
     *
     * @param titulo nuevo título a establecer.
     */
    public void setTitulo(String titulo) { this.titulo = titulo; }

    /**
     * Recupera la descripción general del contenido.
     *
     * @return descripción textual del contenido.
     */
    public String getDescripcion() { return descripcion; }

    /**
     * Establece la descripción del contenido.
     *
     * @param descripcion texto descriptivo a asignar.
     */
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    /**
     * Devuelve el nombre del director asociado al contenido.
     *
     * @return director responsable de la obra.
     */
    public String getDirector() { return director; }

    /**
     * Asigna el nombre del director del contenido.
     *
     * @param director nombre del director a almacenar.
     */
    public void setDirector(String director) { this.director = director; }

    /**
     * Proporciona la lista de actores participantes.
     *
     * @return lista de nombres de actores.
     */
    public List<String> getActores() { return actores; }

    /**
     * Reemplaza la lista de actores asociados al contenido.
     *
     * @param actores nueva lista de nombres de actores.
     */
    public void setActores(List<String> actores) { this.actores = actores; }

    /**
     * Obtiene el género del contenido.
     *
     * @return nombre del género principal.
     */
    public String getGenero() { return genero; }

    /**
     * Actualiza el género del contenido.
     *
     * @param genero etiqueta de género a establecer.
     */
    public void setGenero(String genero) { this.genero = genero; }

    /**
     * Devuelve la lista de países en los que existe alguna restricción de visualización.
     *
     * @return lista de códigos o nombres de países restringidos.
     */
    public List<String> getRestriccionesGeograficas() { return restriccionesGeograficas; }

    /**
     * Establece las restricciones geográficas asociadas al contenido.
     *
     * @param restriccionesGeograficas colección de países restringidos.
     */
    public void setRestriccionesGeograficas(List<String> restriccionesGeograficas) {
        this.restriccionesGeograficas = restriccionesGeograficas;
    }

    /**
     * Obtiene el tipo de contenido representado (por ejemplo, serie o metraje).
     *
     * @return tipo de contenido configurado.
     */
    public String getTipoDeContenido() { return tipoDeContenido; }

    /**
     * Define el tipo de contenido representado.
     *
     * @param tipoDeContenido descripción del tipo de contenido.
     */
    public void setTipoDeContenido(String tipoDeContenido) { this.tipoDeContenido = tipoDeContenido; }

    /**
     * Obtiene el identificador único del contenido.
     *
     * @return identificador numérico asignado.
     */
    public long getId() { return id; }

    /**
     * Establece el identificador único del contenido.
     *
     * @param id identificador a asignar.
     */
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


