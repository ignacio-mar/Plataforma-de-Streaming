package dao.compar;

import java.util.Comparator;
import model.Usuario;

public class ComparatorEmailUsuario implements Comparator<Usuario> {
    public static final ComparatorEmailUsuario POR_EMAIL = new ComparatorEmailUsuario();
    
    private ComparatorEmailUsuario() {} // Constructor privado para Singleton
    
    @Override
    public int compare(Usuario u1, Usuario u2) {
        return u1.getEmail().compareToIgnoreCase(u2.getEmail());
    }
}
