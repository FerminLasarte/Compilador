package analizadorlexico.conjuntosimbolos;

public class ConjuntoArroba extends ConjuntoSimbolos{
    @Override
    public boolean contieneSimbolo(char simbolo) {
        return (simbolo == '@');
    }
}
