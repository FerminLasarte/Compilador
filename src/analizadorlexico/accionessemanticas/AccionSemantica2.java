package analizadorlexico.accionessemanticas;

public class AccionSemantica2 extends AccionSemantica{

    @Override
    public void ejecutar(Token token, char c) {
        token.setLexema(token.getLexema() + c);
    }

    @Override
    public String toString() {
        return "AS2";
    }
}
