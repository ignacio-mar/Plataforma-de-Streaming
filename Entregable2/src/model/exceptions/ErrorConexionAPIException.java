package model.exceptions;

public class ErrorConexionAPIException extends Exception {

    private static final long serialVersionUID = 1L;

    public ErrorConexionAPIException(String message, Throwable causa) {
        super(message, causa);
    }

    public ErrorConexionAPIException(String message) {
        super(message);
    }
}
