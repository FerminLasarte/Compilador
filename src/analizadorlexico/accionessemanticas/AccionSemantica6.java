package analizadorlexico.accionessemanticas;

import analizadorlexico.AtributosToken;
import analizadorlexico.Token;

public class AccionSemantica6 extends AccionSemantica {
    @Override
    public void ejecutar(Token token, char c) {
        token.setLexema(token.getLexema() + c);
        AtributosToken atributosToken = AnalizadorLexico.tablaSimbolos.get(token.getLexema());
        token.setId(atributosToken.getToken());
        atributosToken.incrementarCantidad();
    }

    @Override
    public String toString() {
        return "AS6";
    }
}