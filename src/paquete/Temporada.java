package paquete;

import java.util.ArrayList;
import java.util.List;

public class Temporada {
    private List<Metraje> capitulos;   
    private Integer cantidadDeEpisodios;     
    public Temporada() {
        this.capitulos = new ArrayList<>();
        this.cantidadDeEpisodios = 0;
    }

    
    public boolean buscarCapitulo(String busqueda) {
       
        return capitulos.stream()
                .anyMatch(m -> m.getTitulo() != null &&
                               m.getTitulo().equalsIgnoreCase(busqueda));
    }

    public Metraje devolverCapitulo(int numeroCapitulo) { // 1..N
        int idx = numeroCapitulo - 1;
        if (idx >= 0 && idx < capitulos.size()) {
            return capitulos.get(idx);
        }
        return null;
    }

    // --- Getters & Setters ---
    public List<Metraje> getCapitulos() { return capitulos; }
    public void setCapitulos(List<Metraje> capitulos) {
        this.capitulos = capitulos;
        this.cantidadDeEpisodios = (capitulos != null) ? capitulos.size() : 0;
    }

    public Integer getCantidadDeEpisodios() { return cantidadDeEpisodios; }
    public void setCantidadDeEpisodios(Integer cantidadDeEpisodios) { this.cantidadDeEpisodios = cantidadDeEpisodios; }
}
