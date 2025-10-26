package model.Enums;

public enum Paises {
    ARGENTINA, BRASIL, EEUU, FRANCIA;
    
    @Override
    public String toString() {
        return name();
    }
}
