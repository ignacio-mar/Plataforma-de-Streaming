package paquete;

public class Metraje extends Contenido{
 private Integer duracion;       // minutos
    private Integer edadMinima;     // clasificación por edad (Integer)
    private String archivoVideo;    // ruta / nombre de archivo
    private int ancho;              // px
    private int alto;               // px

    public Metraje() {
        this.setTipoDeContenido("Metraje");
    }

    private void setTipoDeContenido(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setTipoDeContenido'");
    }

    // --- Getters & Setters ---
    public Integer getDuracion() { return duracion; }
    public void setDuracion(Integer duracion) { this.duracion = duracion; }

    public Integer getEdadMinima() { return edadMinima; }
    public void setEdadMinima(Integer edadMinima) { this.edadMinima = edadMinima; }

    public String getArchivoVideo() { return archivoVideo; }
    public void setArchivoVideo(String archivoVideo) { this.archivoVideo = archivoVideo; }

    public int getAncho() { return ancho; }
    public void setAncho(int ancho) { this.ancho = ancho; }

    public int getAlto() { return alto; }
    public void setAlto(int alto) { this.alto = alto; }

    public String getTitulo() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTitulo'");
    }
}
