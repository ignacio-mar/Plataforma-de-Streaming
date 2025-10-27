package dao.compar;

import java.util.Comparator;
import model.Usuario;

public class ComparatorEmailUsuario implements Comparator<Usuario> {
    public static final Comparator<Usuario> POR_EMAIL =
            (u1, u2) -> u1.getEmail().compareToIgnoreCase(u2.getEmail());
            
    @Override
    public int compare(Usuario u1, Usuario u2) {
        return POR_EMAIL.compare(u1, u2);
    }
}