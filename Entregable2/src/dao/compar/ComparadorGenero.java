package dao.compar;

import java.util.Comparator;
import model.Pelicula;

public class ComparadorGenero implements Comparator<Pelicula> {
    public static final Comparator<Pelicula> POR_GENERO =
            (p1, p2) -> p1.getGenero().compareTo(p2.getGenero());
            
    //Implementación método compare() de la interfaz Comparator para comparar el género de dos películas
    @Override
    public int compare(Pelicula p1, Pelicula p2) {
        return POR_GENERO.compare(p1, p2);
    }
}
/*
public class ComparadorGenero implements Comparator<Pelicula> {
    @Override
    //Implementación método compare() de la interfaz Comparator para comparar el género de dos películas
    public int compare(Pelicula p1, Pelicula p2) {
        return p1.getGenero().compareTo(p2.getGenero());
    }
}
*/