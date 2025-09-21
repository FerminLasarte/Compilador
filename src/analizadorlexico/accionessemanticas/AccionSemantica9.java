package analizadorlexico.accionessemanticas;

import analizadorlexico.AtributosToken;
import analizadorlexico.Token;

public class AccionSemantica9 extends AccionSemantica {
    @Override
    public void ejecutar(Token token, char c) {
        AtributosToken atributosToken = AnalizadorLexico.tablaSimbolos.get(token.getLexema());
        if (atributosToken != null) {
            atributosToken.incrementarCantidad();
            token.setId(atributosToken.getCantidad());
        } else {
            atributosToken = new AtributosToken(1, TipoToken.CADENAS);
            atributosToken.setUso(TiposDeUso.cadena);
            token.setId(atributosToken.getToken());
            AnalizadorLexico.tablaSimbolos.put(token.getLexema(), atributosToken);
        }
    }

    @Override
    public String toString() {
        return "AS9";
    }
}
