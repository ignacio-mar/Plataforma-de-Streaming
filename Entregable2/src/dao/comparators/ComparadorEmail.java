package dao.comparators;

import java.util.Comparator;
import model.Usuario;

public class ComparadorEmail implements Comparator<Usuario> {
    @Override
    //Implementación método compare() de la interfaz Comparator para comparar el email de dos usuarios
    public int compare(Usuario u1, Usuario u2) {
        return u1.getEmail().compareToIgnoreCase(u2.getEmail());
        //return u1.getEmail().compareTo(u2.getEmail());
    }
}