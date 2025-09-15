package paquete;

/**
 * Representa un catálogo de contenidos multimedia.
 * Permite buscar contenidos, filtrar por género y agregar nuevos contenidos.
 * 
 * @author Grupo32
 */
import java.util.ArrayList;
import java.util.List;

public class Catalogo {
    private List<Contenido> listaDeContenidos;  
    private int cantidadDeContenidos;          

    /**
     * Constructor que inicializa la lista de contenidos y la cantidad.
     */
    public Catalogo() {
        this.listaDeContenidos = new ArrayList<>();
        this.cantidadDeContenidos = 0;
    }

    /**
     * Busca un contenido por su título en el catálogo.
     * 
     * @param titulo El título del contenido a buscar.
     * @return El contenido encontrado o null si no existe.
     */
    public Contenido buscarContenido(String titulo) {
        int i = 0;
        while (i < listaDeContenidos.size()) {
            Contenido c = listaDeContenidos.get(i);
            if (c.getTitulo() != null && c.getTitulo().
            equalsIgnoreCase(titulo)) {
                return c;
            }
            i++;
        }
        return null;
    }

    /**
     * Devuelve una lista de contenidos que coinciden con el género especificado.
     * 
     * @param genero El género por el que filtrar los contenidos.
     * @return Lista de contenidos del género solicitado.
     */
    public List<Contenido> devolverListaDeContenidos(String genero) {
        List<Contenido> resultado = new ArrayList<>();
        for (Contenido c : listaDeContenidos) {
            if (c.getGenero() != null && c.getGenero().equalsIgnoreCase(genero)) {
                resultado.add(c);
            }
        }
        return resultado;
    }

    /**
     * Agrega un contenido al catálogo y actualiza la cantidad total.
     * 
     * @param contenido El contenido a agregar.
     */
    public void agregarContenido(Contenido contenido) {
        this.listaDeContenidos.add(contenido);
        this.cantidadDeContenidos = this.listaDeContenidos.size();
    }

    // --- Getters & Setters ---
    public List<Contenido> getListaDeContenidos() { return listaDeContenidos; }
    public void setListaDeContenidos(List<Contenido> listaDeContenidos) {
        this.listaDeContenidos = listaDeContenidos;
        this.cantidadDeContenidos = (listaDeContenidos != null) ? listaDeContenidos.size() : 0;
    }

    public int getCantidadDeContenidos() { return cantidadDeContenidos; }
    public void setCantidadDeContenidos(int cantidadDeContenidos) { this.cantidadDeContenidos = cantidadDeContenidos; }
}
