package dao.compar;

import model.Pelicula;
import java.util.Comparator;

public class ComparatorIdPelicula implements Comparator<Pelicula> {
    
    public static final ComparatorIdPelicula POR_ID = new ComparatorIdPelicula();
    
    private ComparatorIdPelicula() {} // Constructor privado para Singleton
    
    @Override
    public int compare(Pelicula p1, Pelicula p2) {
        return Integer.compare(p1.getId(), p2.getId());
    }
}
