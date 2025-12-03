package model.exceptions;

public class ErrorConexionAPIException extends Exception {
    public ErrorConexionAPIException(String message, Throwable causa){
        super(message, causa);
    }
 
    public ErrorConexionAPIException(String message) {
        super(message);
    }
}