package analizadorlexico.conjuntosimbolos;

public class ConjuntoMayorMenor extends ConjuntoSimbolos {
    @Override
    public boolean contieneSimbolo(char simbolo) {
        return (simbolo == '>' || simbolo == '<');
    }
}
