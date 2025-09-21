package analizadorlexico;

public class Token {
    private String lexema;
    private int id;

    public Token(String lexema, int id) {
        this.lexema = lexema;
        this.id = id;
    }

    public String getLexema() {
        return lexema;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    @Override
    public String toString() {
    return lexema + '[' + id + ']';
    }
}
