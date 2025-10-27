package dao.comparators;

import java.util.Comparator;
import model.Pelicula;

public class ComparadorGenero implements Comparator<Pelicula> {
    @Override
    //Implementación método compare() de la interfaz Comparator para comparar el género de dos películas
    public int compare(Pelicula p1, Pelicula p2) {
        return p1.getGenero().compareTo(p2.getGenero());
    }
}