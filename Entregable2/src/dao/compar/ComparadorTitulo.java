package dao.compar; 

import java.util.Comparator;
import model.Pelicula;

public class ComparadorTitulo implements Comparator<Pelicula> {
    public static final ComparadorTitulo POR_TITULO = new ComparadorTitulo();
    
    private ComparadorTitulo() {} // Constructor privado para Singleton
    
    @Override
    public int compare(Pelicula p1, Pelicula p2) {
        return p1.getTitulo().compareToIgnoreCase(p2.getTitulo());
    }
}