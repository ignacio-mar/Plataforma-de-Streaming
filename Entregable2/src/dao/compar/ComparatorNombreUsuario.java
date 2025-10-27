package dao.compar;

import model.Usuario;
import java.util.Comparator;

public class ComparatorNombreUsuario implements Comparator<Usuario> {
    public static final Comparator<Usuario> POR_NOMBRE_USUARIO =
            (u1, u2) -> u1.getNombreUsuario().compareToIgnoreCase(u2.getNombreUsuario());
            
    @Override
    public int compare(Usuario u1, Usuario u2) {
        return POR_NOMBRE_USUARIO.compare(u1, u2);
    }
}
