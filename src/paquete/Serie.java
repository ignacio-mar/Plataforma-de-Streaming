package paquete;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase Serie representa un tipo de Contenido compuesto por varias temporadas.
 * Cada temporada puede incluir distintos episodios u otras características
 * según se defina en la clase Temporada.
 *
 * Ejemplo de uso:
 * Serie s = new Serie();
 * s.setTitulo("Mi Serie Favorita");
 * s.getTemporadas().add(new Temporada(1));
 *
 * @author grupo32
 * @version 1.0


 */
public class Serie extends Contenido {

    private List<Temporada> temporadas;   // Lista de temporadas

    /**
     * Constructor por defecto de la clase Serie.
     * Inicializa la lista de temporadas como vacía.
     */
    public Serie() {
        this.temporadas = new ArrayList<>();
    }

    // --- Getters & Setters ---
    public List<Temporada> getTemporadas() { return temporadas; }
    public void setTemporadas(List<Temporada> temporadas) {
        this.temporadas = temporadas;
    }
}
