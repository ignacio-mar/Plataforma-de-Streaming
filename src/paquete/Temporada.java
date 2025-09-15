package paquete;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa una temporada de una serie, compuesta por una lista de capítulos (Metraje).
 * Proporciona métodos para buscar y devolver capítulos, así como para gestionar la cantidad de episodios.
 *
 * @author Grupo32
 * @version 1.0
 */
public class Temporada {
    private List<Metraje> capitulos;
    private Integer cantidadDeEpisodios;

    /**
     * Crea una temporada vacía con cero episodios registrados inicialmente.
     */
    public Temporada() {
        this.capitulos = new ArrayList<>();
        this.cantidadDeEpisodios = 0;
    }
    
    /**
     * Busca un capítulo por su título en la lista de capítulos.
     *
     * @param busqueda El título del capítulo a buscar.
     * @return true si se encuentra un capítulo con el título especificado, false en caso contrario.
     */
    public boolean buscarCapitulo(String busqueda) {
        if (busqueda == null || capitulos == null) {
            return false;
        }
        for (Metraje m : capitulos) {
            if (m != null && m.getTitulo() != null &&
                m.getTitulo().equalsIgnoreCase(busqueda)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Devuelve el capítulo correspondiente al número especificado.
     *
     * @param numeroCapitulo El número del capítulo (comenzando desde 1).
     * @return El objeto Metraje correspondiente al número de capítulo, o null si no existe.
     */
    public Metraje devolverCapitulo(int numeroCapitulo) {
        if (capitulos == null || numeroCapitulo <= 0) {
            return null;
        }
        int idx = numeroCapitulo - 1;
        if (idx < 0 || idx >= capitulos.size()) {
            return null;
        }
        return capitulos.get(idx);
    }


    /**
     * Obtiene la lista de capítulos de la temporada.
     *
     * @return Una lista de objetos Metraje que representan los capítulos de la temporada.
     */
    public List<Metraje> getCapitulos() { return capitulos; }

    /**
     * Reemplaza la lista completa de capítulos de la temporada y actualiza la cantidad registrada.
     *
     * @param capitulos nueva colección de capítulos para la temporada.
     */
    public void setCapitulos(List<Metraje> capitulos) {
        this.capitulos = capitulos;
        this.cantidadDeEpisodios = (capitulos != null) ? capitulos.size() : 0;
    }
    
    /**
     * Obtiene la cantidad de episodios de la temporada.
     *
     * @return El número total de episodios en la temporada.
     */
    public Integer getCantidadDeEpisodios() { return cantidadDeEpisodios; }

    /**
     * Establece la cantidad de episodios de la temporada.
     *
     * @param cantidadDeEpisodios El nuevo número total de episodios.
     */
    public void setCantidadDeEpisodios(Integer cantidadDeEpisodios) { this.cantidadDeEpisodios = cantidadDeEpisodios; }
}
