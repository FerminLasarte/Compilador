package analizadorlexico.conjuntosimbolos;

public class ConjuntoPorcentaje extends ConjuntoSimbolos {
    @Override
    public boolean contieneSimbolo(char simbolo) {
        return (simbolo == '%');
    }
}
