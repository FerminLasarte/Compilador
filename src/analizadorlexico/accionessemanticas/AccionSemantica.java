package analizadorlexico.accionessemanticas;

import analizadorlexico.Token;

public abstract class AccionSemantica {
    public abstract void ejecutar(Token token, char c);
}
