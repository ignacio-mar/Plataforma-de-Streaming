package model;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private int id;
    private String nombreUsuario;
    private String email;
    private String contrasenia;
    private int dniPersona;

    private List<Integer> peliculasResenadas;
    private boolean primerAcceso;

    public Usuario(int id, String nombreUsuario, String email, String contrasenia, int dniPersona) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.contrasenia = contrasenia;
        this.dniPersona = dniPersona;
        this.peliculasResenadas = new ArrayList<>();
        this.primerAcceso = true; 
    }

    public Usuario(String nombreUsuario, String email, String contrasenia, int dniPersona) {
        this(0, nombreUsuario, email, contrasenia, dniPersona);
    }

    // --- getters/setters básicos ---

    public int getId() {
        return id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public int getDniPersona() {
        return dniPersona;
    }

    // --- nuevo: películas reseñadas ---

    public List<Integer> getPeliculasResenadas() {
        return peliculasResenadas;
    }

    public void setPeliculasResenadas(List<Integer> peliculasResenadas) {
        this.peliculasResenadas = (peliculasResenadas != null)
                ? peliculasResenadas
                : new ArrayList<>();
    }

    public void agregarPeliculaResenada(int idPelicula) {
        if (!peliculasResenadas.contains(idPelicula)) {
            peliculasResenadas.add(idPelicula);
        }
    }

    // helpers para persistir en la columna TEXT PELICULAS_RESENADAS (formato "1,5,8,...")

    public String getPeliculasResenadasComoTexto() {
        if (peliculasResenadas == null || peliculasResenadas.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < peliculasResenadas.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append(peliculasResenadas.get(i));
        }
        return sb.toString();
    }

    public void setPeliculasResenadasDesdeTexto(String texto) {
        peliculasResenadas = new ArrayList<>();
        if (texto == null || texto.isBlank()) {
            return;
        }
        String[] partes = texto.split(",");
        for (String p : partes) {
            try {
                int idPeli = Integer.parseInt(p.trim());
                peliculasResenadas.add(idPeli);
            } catch (NumberFormatException ignored) {
            }
        }
    }

    // --- nuevo: primer acceso ---

    public boolean isPrimerAcceso() {
        return primerAcceso;
    }

    public void setPrimerAcceso(boolean primerAcceso) {
        this.primerAcceso = primerAcceso;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", email='" + email + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", dniPersona=" + dniPersona +
                ", peliculasResenadas=" + peliculasResenadas +
                ", primerAcceso=" + primerAcceso +
                '}';
    }
}
