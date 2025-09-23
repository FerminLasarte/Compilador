package analizadorlexico.conjuntosimbolos;

public class ConjuntoMas extends ConjuntoSimbolos {

    public ConjuntoMas() {}

    @Override
    public boolean contieneSimbolo(char simbolo) {
        return (simbolo == '+');
    }
}
