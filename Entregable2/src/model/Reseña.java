package model;

import java.time.LocalDateTime;

public class Reseña{
    private int id;
    private int calificacion;
    private String comentario;
    private boolean aprobado;
    private LocalDateTime fecha_hora;
    private int id_usuario;
    private int id_pelicula;

    public Reseña(int calificacion, String comentario, int id_usuario, int id_pelicula) {
        this.calificacion = calificacion;
        if (calificacion < 1 || calificacion > 5) {
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5");
        }
        this.comentario = comentario;
        this.id_usuario = id_usuario;
        this.id_pelicula = id_pelicula;
        this.aprobado = false;                 
        this.fecha_hora = LocalDateTime.now();    
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        if (calificacion < 1 || calificacion > 5) {
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5");
        }
        this.calificacion = calificacion;
    }
    public String getComentario() {
        return comentario;
    }
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    public boolean isAprobado() {
        return aprobado;
    }
    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }
    public LocalDateTime getFecha_hora() { 
        return fecha_hora;
    }
    public void setFecha_hora(LocalDateTime fecha_hora) { 
        this.fecha_hora = fecha_hora;
    }
    public int getId_usuario() {
        return id_usuario;
    }
    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
    public int getId_pelicula() {
        return id_pelicula;
    }
    public void setId_pelicula(int id_pelicula) {
        this.id_pelicula = id_pelicula;
    }
}
