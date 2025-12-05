package dao.compar;

import java.util.Comparator;
import model.Pelicula;

public class ComparadorDuracion implements Comparator<Pelicula> {
    public static final ComparadorDuracion POR_DURACION = new ComparadorDuracion();
    
    private ComparadorDuracion() {} // Constructor privado para Singleton
    
    @Override
    public int compare(Pelicula p1, Pelicula p2) {
        return Double.compare(p1.getDuracion(), p2.getDuracion());
    }
}