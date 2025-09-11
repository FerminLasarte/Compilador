package analizadorlexico.conjuntosimbolos;

public class ConjuntoEspacioBlanco extends ConjuntoSimbolos {

    @Override
    public boolean contieneSimbolo(char simbolo) {
        return (simbolo == '\n');
    }
}
