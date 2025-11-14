/**
 * Clase para representar un Terceto (Triple) del código intermedio.
 * Almacena un operador y hasta dos operandos.
 * También almacena el tipo de dato del resultado de la operación.
 */
public class Terceto {
    private String operador;
    private String operando1;
    private String operando2;
    private String tipo; // Tipo del resultado (ej: "uint", "float", "void")

    // Constructor para operaciones binarias (ej: +, -, :=)
    public Terceto(String operador, String operando1, String operando2) {
        this.operador = operador;
        this.operando1 = operando1;
        this.operando2 = operando2;
        this.tipo = "void"; // Se define después del chequeo semántico
    }

    // Constructor para operaciones unarias (ej: toui, RETURN)
    public Terceto(String operador, String operando1) {
        this.operador = operador;
        this.operando1 = operando1;
        this.operando2 = null; // Sin segundo operando
        this.tipo = "void";
    }

    // Constructor para saltos o etiquetas (ej: BI, LABEL)
    public Terceto(String operador) {
        this.operador = operador;
        this.operando1 = null;
        this.operando2 = null;
        this.tipo = "void";
    }

    @Override
    public String toString() {
        // Muestra '_' para operandos nulos, facilitando la lectura
        String op1 = (operando1 != null) ? operando1 : "_";
        String op2 = (operando2 != null) ? operando2 : "_";
        return "(" + operador + ", " + op1 + ", " + op2 + ")";
    }

    // --- Getters y Setters ---

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
}
