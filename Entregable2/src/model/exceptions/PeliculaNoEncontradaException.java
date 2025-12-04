package model.exceptions;

public class PeliculaNoEncontradaException extends RuntimeException {
    public PeliculaNoEncontradaException(String titulo){
        super("No fue posible hallar el contenido con el título: " + titulo + ".");
    }   
}
