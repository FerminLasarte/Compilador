package analizadorlexico.conjuntosimbolos;

public class ConjuntoExclamacion extends ConjuntoSimbolos {
    @Override
    public boolean contieneSimbolo(char simbolo) {
        return (simbolo == '!');
    }
}
