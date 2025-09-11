package analizadorlexico.conjuntosimbolos;

public class ConjuntoMasMenos extends ConjuntoSimbolos {

    public ConjuntoMasMenos() {}

    @Override
    public boolean contieneSimbolo(char simbolo) {
        return (simbolo == '+' || simbolo == '-');
    }
}
