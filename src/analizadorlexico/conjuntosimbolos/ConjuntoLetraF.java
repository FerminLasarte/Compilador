package analizadorlexico.conjuntosimbolos;

public class ConjuntoLetraF extends ConjuntoSimbolos {
    @Override
    public boolean contieneSimbolo(char simbolo) {
        return (simbolo == 'F');
    }
}
