package analizadorlexico.conjuntosimbolos;

public class ConjuntoBlancoTAB extends ConjuntoSimbolos {
    @Override
    public boolean contieneSimbolo(char simbolo) {
        return (simbolo == ' ' || simbolo == '\t');
    }
}
