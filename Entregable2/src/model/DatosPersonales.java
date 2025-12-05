package model;

import model.enums.Paises;

public class DatosPersonales {
    private Integer id;       
    private String nombres;
    private String apellido;
    private Integer dni;
    private Paises paisResidencia;
    private String numeroTelefono;

    // Constructor con ID (para actualizar)
    public DatosPersonales(int id, String nombres, String apellido, int dni, Paises paisResidencia, String numeroTelefono) {
        this.id = id;
        this.nombres = nombres;
        this.apellido = apellido;
        this.dni = dni;
        this.paisResidencia = paisResidencia;
        this.numeroTelefono = numeroTelefono;
    }

    // Constructor sin ID (para crear nuevo)
    public DatosPersonales(String nombres, String apellido, Integer dni, Paises paisResidencia, String numeroTelefono) {
        this.nombres = nombres;
        this.apellido = apellido;
        this.dni = dni;
        this.paisResidencia = paisResidencia;
        this.numeroTelefono = numeroTelefono;
    }

    // New Getters
    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellido;
    }

    public int getDni() {
        return dni;
    }

    public String getPais() {
        return paisResidencia != null ? paisResidencia.name() : null;
    }

    public String getTelefono() {
        return numeroTelefono;
    }

   
    // New Setters
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setApellidos(String apellidos) {
        this.apellido = apellidos;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public void setPais(String pais) {
        this.paisResidencia = Paises.valueOf(pais);
    }

    public void setTelefono(String telefono) {
        this.numeroTelefono = telefono;
    }

    @Override public String toString() {
        return "DatosPersonales{id=" + id + ", nombres='" + nombres + '\'' +
               ", apellido='" + apellido + '\'' + ", dni=" + dni + 
               ", paisResidencia='" + paisResidencia + '\'' +
               ", numeroTelefono='" + numeroTelefono + '\'' + '}';
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    

    // El DAO busca 'getApellido', asegúrate de tenerlo así:
    public String getApellido() {
        return apellido;
    }


    // El DAO busca 'getPaisResidencia', asegúrate de tenerlo así:
    public Paises getPaisResidencia() {
        return paisResidencia;
    }

    // El DAO busca 'getNumeroTelefono', asegúrate de tenerlo así:
    public String getNumeroTelefono() {
        return numeroTelefono;
    }
}

