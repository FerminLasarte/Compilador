package analizadorlexico.accionessemanticas;

import analizadorlexico.ParametrosToken;
import analizadorlexico.Token;
import analizadorlexico.AnalizadorLexico;

public class AccionSemantica6 extends AccionSemantica {
    @Override
    public void ejecutar(Token token, char c) {
        token.setLexema(token.getLexema() + c);
        ParametrosToken parametrosToken = AnalizadorLexico.tablaSimbolos.get(token.getLexema());
        token.setId(parametrosToken.getToken());
        parametrosToken.incrementarCantidadTokens();
    }

    @Override
    public String toString() {
        return "AS6";
    }
}