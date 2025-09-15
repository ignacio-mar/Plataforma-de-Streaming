package paquete;

/**
 * La clase Metraje representa un tipo de Contenido audiovisual.
 * Contiene información específica como la duración en minutos,
 * el idioma de audio y los subtítulos disponibles.
 *
 * Ejemplo de uso:
 * Metraje m = new Metraje();
 * m.setDuracion(120);
 * m.setAudio("Español");
 * m.setSubtitulos("Inglés");
 * System.out.println(m);
 *
 * @author Grupo32
 * @version 1.0
 */
public class Metraje extends Contenido {
    private Integer duracion;       // minutos
    private String audio;    
    private String subtitulos;   

    /**
     * Constructor por defecto de la clase Metraje.
     * Inicializa un objeto sin valores asignados.
     */
    public Metraje() {}

    /**
     * Devuelve la duración total del metraje en minutos.
     *
     * @return duración expresada en minutos.
     */
    public Integer getDuracion() { return duracion; }

    /**
     * Establece la duración total del metraje en minutos.
     *
     * @param duracion duración del contenido en minutos.
     */
    public void setDuracion(Integer duracion) { this.duracion = duracion; }

    /**
     * Obtiene el idioma de audio principal del metraje.
     *
     * @return idioma del audio disponible.
     */
    public String getAudio() { return audio; }

    /**
     * Define el idioma de audio principal del metraje.
     *
     * @param audio idioma del audio a configurar.
     */
    public void setAudio(String audio) { this.audio = audio; }

    /**
     * Recupera los subtítulos disponibles para el metraje.
     *
     * @return idioma o descripción de los subtítulos.
     */
    public String getSubtitulos() { return subtitulos; }

    /**
     * Configura los subtítulos disponibles para el metraje.
     *
     * @param subtitulos idioma o descripción de los subtítulos.
     */
    public void setSubtitulos(String subtitulos) { this.subtitulos = subtitulos; }

    /**
     * Devuelve una representación en forma de cadena del objeto Metraje,
     * mostrando duración, audio y subtítulos.
     *
     * @return una cadena con el formato:
     * Metraje{duracion=..., audio='...', subtitulos='...'}
     */
    @Override
    public String toString() {
        return "Metraje{" +
                "duracion=" + duracion +
                ", audio='" + audio + '\'' +
                ", subtitulos='" + subtitulos + '\'' +
                '}';
    }
}
