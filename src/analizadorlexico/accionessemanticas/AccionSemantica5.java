package analizadorlexico.accionessemanticas;

import analizadorlexico.ParametrosToken;
import analizadorlexico.Token;

public class AccionSemantica5 extends AccionSemantica {
    @Override
    public void ejecutar(Token token, char c) {
        AnalizadorLexico.indice_caracter_leer--;
        ParametrosToken parametrosToken = AnalizadorLexico.tablaSimbolos.get(token.getLexema());
        token.setId(parametrosToken.getToken());
    }

    @Override
    public String toString() {
        return "AS5";
    }
}
