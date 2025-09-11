package analizadorlexico.conjuntosimbolos;

public class ConjuntoAmpersand extends ConjuntoSimbolos {
    @Override
    public boolean contieneSimbolo(char simbolo) {
        return (simbolo == '&');
    }
}
