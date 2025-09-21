package analizadorlexico.accionessemanticas;

import analizadorlexico.AtributosToken;
import analizadorlexico.Token;

public class AccionSemantica7 extends AccionSemantica {
    @Override
    public void ejecutar(Token token, char c) {
        token.setLexema(String.valueOf(c));
        AtributosToken atributosToken = AnalizadorLexico.tablaSimbolos.get(token.getLexema());
        atributosToken.incrementarCantidad();
        token.setId(atributosToken.getToken());
    }

    @Override
    public String toString() {
        return "AS7";
    }
}