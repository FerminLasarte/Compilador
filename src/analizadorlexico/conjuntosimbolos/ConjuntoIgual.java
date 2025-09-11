package analizadorlexico.conjuntosimbolos;

public class ConjuntoIgual extends ConjuntoSimbolos {
    @Override
    public boolean contieneSimbolo(char simbolo) {
        return (simbolo == '=');
    }
}
