package analizadorlexico.accionessemanticas;

import analizadorlexico.Token;

public class AccionSemantica1 extends AccionSemantica{

    @Override
    public void ejecutar(Token token, char c) {
        token.setLexema(String.valueOf(c));
    }

    @Override
    public String toString() {
        return "AS1";
    }
}
