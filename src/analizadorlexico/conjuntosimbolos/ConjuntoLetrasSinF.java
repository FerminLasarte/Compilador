package analizadorlexico.conjuntosimbolos;

public class ConjuntoLetrasSinF extends ConjuntoSimbolos {

    @Override
    public boolean contieneSimbolo(char simbolo) {
        return (Character.isAlphabetic(simbolo) && simbolo != 'F');
    }
}
