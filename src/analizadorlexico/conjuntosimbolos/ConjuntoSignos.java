package analizadorlexico.conjuntosimbolos;

public class ConjuntoSignos extends ConjuntoSimbolos {
    @Override
    public boolean contieneSimbolo(char simbolo) {
        return (simbolo == '(' || simbolo == ')' || simbolo == '{' || simbolo == '}' ||
                simbolo == '_' || simbolo == ';' || simbolo == '/');
    }
}
