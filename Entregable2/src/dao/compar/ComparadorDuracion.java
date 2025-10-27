package dao.comparators;

import java.util.Comparator;
import model.Pelicula;

public class ComparadorDuracion implements Comparator<Pelicula> {
    //Implementación método compare() de la interfaz Comparator para comparar duración de dos películas
    public int compare(Pelicula p1, Pelicula p2) {
        return Double.compare(p1.getDuracion(), p2.getDuracion());
    }
}