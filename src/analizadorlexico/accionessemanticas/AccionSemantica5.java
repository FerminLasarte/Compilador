package analizadorlexico.accionessemanticas;

import analizadorlexico.AnalizadorLexico;

public class AccionSemantica5 extends AccionSemantica {
    @Override
    public void ejecutar(Token token, char c) {
        AnalizadorLexico.indiceCaracter--;
        ParametrosToken parametrosToken = AnalizadorLexico.tablaSimbolos.get(token.getLexema());
        token.setId(parametrosToken.getToken());
    }

    @Override
    public String toString() {
        return "AS5";
    }
}
