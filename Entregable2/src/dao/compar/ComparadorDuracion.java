package dao.compar;

import java.util.Comparator;
import model.Pelicula;

public class ComparadorDuracion implements Comparator<Pelicula> {
   //Implementación método compare() de la interfaz Comparator para comparar duración de dos películas
    public static final Comparator<Pelicula> POR_DURACION =
            (p1, p2) -> Double.compare(p1.getDuracion(), p2.getDuracion());

    @Override
    public int compare(Pelicula p1, Pelicula p2) {
        return POR_DURACION.compare(p1, p2);
    }
}