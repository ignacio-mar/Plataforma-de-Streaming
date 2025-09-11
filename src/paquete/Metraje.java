package paquete;

public class Metraje extends Contenido{
    private Integer duracion;       // minutos
    private String audio;    
    private String subtitulos;   

    public Metraje() {}

    public Integer getDuracion() { return duracion; }
    public void setDuracion(Integer duracion) { this.duracion = duracion; }

    public String getAudio() { return audio; }
    public void setAudio(String audio) { this.audio = audio; }

    public String getSubtitulos() { return subtitulos; }
    public void setSubtitulos(String subtitulos) { this.subtitulos = subtitulos; }

    @Override
    public String toString() {
        return "Metraje{" +
                "duracion=" + duracion +
                ", audio='" + audio + '\'' +
                ", subtitulos='" + subtitulos + '\'' +
                '}';
    }
}
