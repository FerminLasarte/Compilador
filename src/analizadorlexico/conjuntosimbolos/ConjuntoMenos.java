package analizadorlexico.conjuntosimbolos;

public class ConjuntoMenos extends ConjuntoSimbolos {

    public ConjuntoMenos() {}

    @Override
    public boolean contieneSimbolo(char simbolo) {
        return (simbolo == '-');
    }
}
