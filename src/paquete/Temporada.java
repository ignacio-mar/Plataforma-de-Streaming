package paquete;

import java.util.ArrayList;
import java.util.List;

public class Temporada {
private List<Metraje> indiceCapitulos;   // List<Metraje>
    private Integer cantidadDeEpisodios;     // Integer

    public Temporada() {
        this.indiceCapitulos = new ArrayList<>();
        this.cantidadDeEpisodios = 0;
    }

    // --- Métodos del diagrama ---
    public boolean buscarCapitulo(String busqueda) {
        // Busca por título (Metraje hereda 'titulo' de Contenido)
        return indiceCapitulos.stream()
                .anyMatch(m -> m.getTitulo() != null &&
                               m.getTitulo().equalsIgnoreCase(busqueda));
    }

    public Metraje devolverCapitulo(int numeroCapitulo) { // 1..N
        int idx = numeroCapitulo - 1;
        if (idx >= 0 && idx < indiceCapitulos.size()) {
            return indiceCapitulos.get(idx);
        }
        return null;
    }

    // --- Getters & Setters ---
    public List<Metraje> getIndiceCapitulos() { return indiceCapitulos; }
    public void setIndiceCapitulos(List<Metraje> indiceCapitulos) {
        this.indiceCapitulos = indiceCapitulos;
        this.cantidadDeEpisodios = (indiceCapitulos != null) ? indiceCapitulos.size() : 0;
    }

    public Integer getCantidadDeEpisodios() { return cantidadDeEpisodios; }
    public void setCantidadDeEpisodios(Integer cantidadDeEpisodios) { this.cantidadDeEpisodios = cantidadDeEpisodios; }
}
