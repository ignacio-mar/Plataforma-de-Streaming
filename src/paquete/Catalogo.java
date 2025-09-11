package paquete;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Catalogo {
 private List<Contenido> listaDeContenidos;  // List<Contenido>
    private int cantidadDeContenidos;           // integer

    public Catalogo() {
        this.listaDeContenidos = new ArrayList<>();
        this.cantidadDeContenidos = 0;
    }

    // --- Métodos del diagrama ---
    public Contenido buscarContenido(String titulo) {
        return listaDeContenidos.stream()
                .filter(c -> c.getTitulo() != null && c.getTitulo().equalsIgnoreCase(titulo))
                .findFirst()
                .orElse(null);
    }

    public List<Contenido> devolverListaDeContenidos(String genero) {
        return listaDeContenidos.stream()
                .filter(c -> c.getGenero() != null && c.getGenero().equalsIgnoreCase(genero))
                .collect(Collectors.toList());
    }

    // --- Utilidad básica ---
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

