package dao.comparators;

import java.util.Comparator;
import model.Usuario;

public class ComparadorNombre implements Comparator<Usuario> {
    @Override
    //Implementación método compare() de la interfaz Comparator para comparar el nombre de dos usuarios
    public int compare(Usuario u1, Usuario u2) {
        return u1.getNombre().compareToIgnoreCase(u2.getNombre());
    }
}