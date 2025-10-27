package dao.compar; 

import java.util.Comparator;
import model.Pelicula;

public class ComparadorTitulo implements Comparator<Pelicula> {
    public static final Comparator<Pelicula> POR_TITULO =
            (p1, p2) -> p1.getTitulo().compareToIgnoreCase(p2.getTitulo());
 
    @Override
    public int compare(Pelicula p1, Pelicula p2) {
        return POR_TITULO.compare(p1, p2);
    }
}