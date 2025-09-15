package paquete;

import java.util.ArrayList;
import java.util.List;

public class Catalogo {
 private List<Contenido> listaDeContenidos;  
    private int cantidadDeContenidos;          
    public Catalogo() {
        this.listaDeContenidos = new ArrayList<>();
        this.cantidadDeContenidos = 0;
    }

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

    public List<Contenido> devolverListaDeContenidos(String genero) {
        List<Contenido> resultado = new ArrayList<>();
        for (Contenido c : listaDeContenidos) {
            if (c.getGenero() != null && c.getGenero().equalsIgnoreCase(genero)) {
                resultado.add(c);
            }
        }
        return resultado;
    }

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
