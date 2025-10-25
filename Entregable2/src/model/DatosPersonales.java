package model;

public class DatosPersonales {
    private Integer id;       
    private String nombres;
    private String apellido;
    private Integer dni;
    private String paisResidencia;
    private String numeroTelefono;

    public DatosPersonales() {}

    public DatosPersonales(String nombres, String apellido, Integer dni, String paisResidencia, String numeroTelefono) {
        this.nombres = nombres;
        this.apellido = apellido;
        this.dni = dni;
        this.paisResidencia = paisResidencia;
        this.numeroTelefono = numeroTelefono;
    }

    // Getters & Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public Integer getDni() { return dni; }
    public void setDni(Integer dni) { this.dni = dni; }
    public String getPaisResidencia() { return paisResidencia; }
    public void setPaisResidencia(String paisResidencia) { this.paisResidencia = paisResidencia; }
    public String getNumeroTelefono() { return numeroTelefono; }
    public void setNumeroTelefono(String numeroTelefono) { this.numeroTelefono = numeroTelefono; }

    @Override public String toString() {
        return "DatosPersonales{id=" + id + ", nombres='" + nombres + '\'' +
               ", apellido='" + apellido + '\'' + ", dni=" + dni + 
               ", paisResidencia='" + paisResidencia + '\'' +
               ", numeroTelefono='" + numeroTelefono + '\'' + '}';
    }
}
