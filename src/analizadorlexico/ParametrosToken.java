package analizadorlexico;

public class ParametrosToken {

    private int cantidadTokens;
    private int token;

    public ParametrosToken(int cantidadTokens, int token) {
        this.cantidadTokens = cantidadTokens;
        this.token = token;
    }

    public int getCantidadTokens() {
        return cantidadTokens;
    }

    public void setCantidadTokens(int cantidadTokens) {
        this.cantidadTokens = cantidadTokens;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public void incrementarCantidadTokens() {
        this.cantidadTokens++;
    }
}
