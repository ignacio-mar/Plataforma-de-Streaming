package dao.comparators; 

import java.util.Comparator;
import model.Pelicula;

public class ComparadorTitulo implements Comparator<Pelicula> {
    @Override
    public int compare(Pelicula p1, Pelicula p2) {
        return p1.getTitulo().compareToIgnoreCase(p2.getTitulo());
    }
}