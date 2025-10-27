package dao.compar;

import java.util.Comparator;
import model.Usuario;

public class ComparatorNombreUsuario implements Comparator<Usuario> {
    public static final ComparatorNombreUsuario POR_NOMBRE_USUARIO = new ComparatorNombreUsuario();
    
    private ComparatorNombreUsuario() {} // Constructor privado para Singleton
    
    @Override
    public int compare(Usuario u1, Usuario u2) {
        return u1.getNombreUsuario().compareToIgnoreCase(u2.getNombreUsuario());
    }
}
