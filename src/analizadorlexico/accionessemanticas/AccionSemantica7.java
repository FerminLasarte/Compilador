package analizadorlexico.accionessemanticas;

import analizadorlexico.AnalizadorLexico;

public class AccionSemantica7 extends AccionSemantica {
    @Override
    public void ejecutar(Token token, char c) {
        token.setLexema(String.valueOf(c));
        ParametrosToken parametrosToken = AnalizadorLexico.tablaSimbolos.get(token.getLexema());
        parametrosToken.incrementarCantidadTokens();
        token.setId(parametrosToken.getToken());
    }

    @Override
    public String toString() {
        return "AS7";
    }
}