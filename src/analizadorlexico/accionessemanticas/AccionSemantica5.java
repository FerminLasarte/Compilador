package analizadorlexico.accionessemanticas;

import analizadorlexico.AtributosToken;
import analizadorlexico.Token;

public class AccionSemantica5 extends AccionSemantica {
    @Override
    public void ejecutar(Token token, char c) {
        AnalizadorLexico.indice_caracter_leer--;
        AtributosToken atributosToken = AnalizadorLexico.tablaSimbolos.get(token.getLexema());
        token.setId(atributosToken.getToken());
    }

    @Override
    public String toString() {
        return "AS5";
    }
}
