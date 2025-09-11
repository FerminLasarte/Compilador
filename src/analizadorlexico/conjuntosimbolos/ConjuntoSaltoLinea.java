package analizadorlexico.conjuntosimbolos;

public class ConjuntoSaltoLinea extends ConjuntoSimbolos {
    @Override
    public boolean contieneSimbolo(char simbolo) {
        return (simbolo == '\n');
    }
}
