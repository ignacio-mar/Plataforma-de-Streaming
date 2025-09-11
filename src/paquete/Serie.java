package paquete;
import java.util.ArrayList;
import java.util.List;
public class Serie extends Contenido{

private List<Temporada> temporadas;   // List<Temporada>

    public Serie() {
        this.temporadas=new ArrayList<>();
    }

    

    // --- Getters & Setters ---
    public List<Temporada> getTemporadas() { return temporadas; }
    public void setTemporadas(List<Temporada> temporadas) {
        this.temporadas = temporadas;
    }
}

