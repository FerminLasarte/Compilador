package analizadorlexico.accionessemanticas;

import analizadorlexico.AnalizadorLexico;

public class AccionSemantica9 extends AccionSemantica {
    @Override
    public void ejecutar(Token token, char c) {
        ParametrosToken parametrosToken = AnalizadorLexico.tablaSimbolos.get(token.getLexema());
        if (parametrosToken != null) {
            parametrosToken.incrementarCantidadTokens();
            token.setId(parametrosToken.getCantidadTokens());
        } else {
            parametrosToken = new ParametrosToken(1, TipoToken.CADENA_MULTILINEA);
            parametrosToken.setUso(TiposDeUso.cadena);
            token.setId(parametrosToken.getToken());
            AnalizadorLexico.tablaSimbolos.put(token.getLexema(), parametrosToken);
        }
    }

    @Override
    public String toString() {
        return "AS9";
    }
}
