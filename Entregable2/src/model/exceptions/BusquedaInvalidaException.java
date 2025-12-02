package model.exceptions;

public class BusquedaInvalidaException extends RuntimeException {

    private final String textoIngresado

    public BusquedaInvalidaException(String input){
        super("El término de búsqueda es inválido o demasiado corto: " + input);
        this.textoIngresado = input;
    }

    public String getTextoIngresado() {
        return textoIngresado;
    }

    @Override
    public getMessage(); {
        return super.getmessage() + "[Valor ingresado: '" + textoIngresado + "']";
    }
    
}
