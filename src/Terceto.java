// Archivo: Terceto.java
// (Colocar en el mismo paquete que el resto de las clases)

public class Terceto {
    private int numero;
    private String operador;
    private String operando1;
    private String operando2;
    private String tipo; // Para chequeo semántico de tipos

    /**
     * Constructor para un terceto.
     * @param numero El índice de este terceto en la lista.
     * @param operador La operación (ej: "+", ":=", "BF").
     * @param operando1 Referencia a variable, constante o terceto (ej: "VarA", "[2]").
     * @param operando2 Referencia a variable, constante o terceto (ej: "10UI", "[3]").
     */
    public Terceto(int numero, String operador, String operando1, String operando2) {
        this.numero = numero;
        this.operador = operador;
        this.operando1 = operando1;
        this.operando2 = operando2;
        this.tipo = "void"; // Se debe setear luego del chequeo semantico
    }

    // --- Getters y Setters ---

    public int getNumero() {
        return numero;
    }

    public String getOperador() {
        return operador;
    }

    public String getOperando1() {
        return operando1;
    }

    public String getOperando2() {
        return operando2;
    }

    public String getTipo() {
        return tipo;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public void setOperando1(String operando1) {
        this.operando1 = operando1;
    }

    public void setOperando2(String operando2) {
        this.operando2 = operando2;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Devuelve la referencia a este terceto para ser usada en otro.
     * @return String con el formato "[numero]".
     */
    public String getReferencia() {
        return "[" + this.numero + "]";
    }

    /**
     * Genera la representación en String del terceto para imprimir.
     * Formato: [20] (+, a, b) [cite: 878]
     */
    @Override
    public String toString() {
        return String.format("[%d] (%s, %s, %s)",
                this.numero,
                this.operador,
                this.operando1 != null ? this.operando1 : "-",
                this.operando2 != null ? this.operando2 : "-");
    }
}