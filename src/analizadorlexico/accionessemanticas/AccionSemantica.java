package analizadorlexico.accionessemanticas;

public abstract class AccionSemantica {
    public abstract void ejecutar(Token token, char c);
}
