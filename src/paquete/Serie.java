package paquete;

import java.util.ArrayList;
import java.util.List;

public class Serie Extends Contenido {
private List<Temporada> indiceTemporadas;   // List<Temporada>

    public Serie() {
        this.indiceTemporadas = new ArrayList<>();
        this.setTipoDeContenido("Serie");
    }

    private void setTipoDeContenido(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setTipoDeContenido'");
    }

    // --- Getters & Setters ---
    public List<Temporada> getIndiceTemporadas() { return indiceTemporadas; }
    public void setIndiceTemporadas(List<Temporada> indiceTemporadas) {
        this.indiceTemporadas = indiceTemporadas;
    }
}

