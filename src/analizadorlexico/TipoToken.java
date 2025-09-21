package analizadorlexico;

public class TipoToken {

    public static final int IDENTIFICADOR = 0;
    public static final int CTE_ENTERO_SINSIGNO = 0;
    public static final int IF = 0;
    public static final int ELSE = 0;
    public static final int ENDIF = 0;
    public static final int PRINT = 0;
    public static final int RETURN = 0;
    public static final int VAR = 0;
    public static final int DO = 0;
    public static final int WHILE = 0;
    public static final int LAMBDA = 0;

    public static boolean esReservada(String lexema) {
        if (lexema.equals("if") ||
                lexema.equals("else") ||
                lexema.equals("endif") ||
                lexema.equals("print") ||
                lexema.equals("return") ||
                lexema.equals("var") ||
                lexema.equals("do") ||
                lexema.equals("while") ||
                lexema.equals("lambda")
        ) return true;
        return false;
    }
}
