// Archivo: Generador.java
// (Colocar en el mismo paquete que el resto de las clases)

import java.util.ArrayList;
import java.util.Stack;
import java.util.HashMap;

public class Generador {

    private ArrayList<Terceto> tercetos;
    private Stack<Integer> pilaSaltos; // Pila para IF, DO-WHILE [cite: 177, 186, 286]
    private AnalizadorLexico al; // Para acceder a la Tabla de Simbolos

    public Generador(AnalizadorLexico al) {
        this.tercetos = new ArrayList<>();
        this.pilaSaltos = new Stack<>();
        this.al = al;
    }

    public ArrayList<Terceto> getTercetos() {
        return this.tercetos;
    }

    /**
     * Devuelve el índice donde se insertará el próximo terceto.
     */
    public int getProximoNumero() {
        return this.tercetos.size();
    }

    public Terceto getTerceto(int index) {
        if (index >= 0 && index < tercetos.size()) {
            return tercetos.get(index);
        }
        return null;
    }

    /**
     * Crea un nuevo terceto (operación binaria) y lo agrega a la lista.
     * @return La referencia al terceto creado (ej: "[5]").
     */
    public String crearTerceto(String operador, String operando1, String operando2) {
        int numero = getProximoNumero();
        Terceto t = new Terceto(numero, operador, operando1, operando2);
        this.tercetos.add(t);
        return t.getReferencia();
    }

    /**
     * Crea un nuevo terceto (operación unaria) y lo agrega a la lista.
     * @return La referencia al terceto creado (ej: "[6]").
     */
    public String crearTerceto(String operador, String operando1) {
        return crearTerceto(operador, operando1, null);
    }

    // --- Manejo de Saltos (IF, DO-WHILE) ---

    /**
     * Apila el índice del último terceto creado (que suele ser un salto).
     */
    public void apilar() {
        this.pilaSaltos.push(this.tercetos.size() - 1);
    }

    /**
     * Apila un índice específico (ej. el inicio de un bloque DO).
     */
    public void apilar(int index) {
        this.pilaSaltos.push(index);
    }

    public int desapilar() {
        if (!pilaSaltos.isEmpty()) {
            return this.pilaSaltos.pop();
        }
        return -1; // Error
    }

    /**
     * Completa un terceto de salto (BF o BI) con el número de destino.
     * @param indexTerceto El índice del terceto de salto a modificar.
     * @param numeroDestino El número del terceto al que debe saltar.
     */
    public void completarSalto(int indexTerceto, int numeroDestino) {
        if (indexTerceto != -1 && indexTerceto < tercetos.size()) {
            Terceto t = tercetos.get(indexTerceto);
            // El destino se guarda como un String
            t.setOperando2(String.valueOf(numeroDestino));
        }
    }

    // --- Chequeo Semántico de Tipos ---

    /**
     * Chequea la compatibilidad de tipos para una operación.
     * @return El tipo resultante de la operación, o "ERROR".
     */
    public String chequearTipos(String op1Ref, String op2Ref, String operacion, ArrayList<String> errores) {
        String tipo1 = getTipo(op1Ref);
        String tipo2 = op2Ref != null ? getTipo(op2Ref) : "void"; // op2 puede ser null

        if (tipo1.equals("ERROR") || (op2Ref != null && tipo2.equals("ERROR"))) {
            return "ERROR";
        }

        // Tema 31: Conversion explicita TOUI(float) -> uint [cite: 579]
        if (operacion.equals("TOUI")) {
            if (tipo1.equals("float")) {
                return "uint";
            } else {
                errores.add("Linea " + (al.getContadorFila() + 1) + ": Error Semantico. TOUI solo se aplica a 'float', no a '" + tipo1 + "'.");
                return "ERROR";
            }
        }

        // Operaciones aritméticas y comparaciones
        if (operacion.equals("+") || operacion.equals("-") || operacion.equals("*") || operacion.equals("/") ||
                operacion.equals(">") || operacion.equals("<") || operacion.equals(">=") || operacion.equals("<=") ||
                operacion.equals("==") || operacion.equals("=!")) {

            if (tipo1.equals(tipo2)) {
                if (operacion.equals(">") || operacion.equals("<") || operacion.equals(">=") || operacion.equals("<=") ||
                        operacion.equals("==") || operacion.equals("=!")) {
                    return "boolean"; // El resultado de una comparación es booleano
                }
                return tipo1; // El resultado de aritmética es el mismo tipo
            } else {
                errores.add("Linea " + (al.getContadorFila() + 1) + ": Error Semantico. Tipos incompatibles: " + tipo1 + " y " + tipo2 + " en operacion '" + operacion + "'.");
                return "ERROR";
            }
        }

        // Asignación (se chequea en la regla 'asignacion')
        if (operacion.equals(":=")) {
            if (tipo1.equals(tipo2)) {
                return tipo1;
            }
            // Tema 31: No se permite float -> uint sin conversion [cite: 580]
            if (tipo1.equals("uint") && tipo2.equals("float")) {
                errores.add("Linea " + (al.getContadorFila() + 1) + ": Error Semantico. Asignacion incompatible. No se puede asignar 'float' a 'uint' sin conversion explicita TOUI.");
                return "ERROR";
            }
            // TODO: Logica de conversion implicita (si aplicara)
        }

        return "void"; // Default
    }

    /**
     * Helper para obtener el tipo de un operando, ya sea un ID, una CTE
     * o una referencia a un terceto (ej: "[5]").
     */
    public String getTipo(String ref) {
        if (ref == null) return "void";

        if (ref.startsWith("[")) { // Es una referencia a un terceto
            try {
                int index = Integer.parseInt(ref.substring(1, ref.length() - 1));
                return tercetos.get(index).getTipo();
            } catch (Exception e) {
                return "ERROR";
            }
        }

        if (al.getTablaSimbolos().containsKey(ref)) { // Es un ID o CTE
            HashMap<String, Object> atributos = al.getTablaSimbolos().get(ref);
            if (atributos.containsKey("Tipo")) {
                return (String) atributos.get("Tipo");
            }
            // Tema 9: Inferencia Obligatoria. Si se usa antes de inferirse.
            if (atributos.containsKey("Uso") && atributos.get("Uso").equals("Identificador")) {
                // Aún no tiene tipo, esto es un error (o se podría inferir "undefined")
                al.agregarError("Error Semantico: Variable '" + ref + "' usada antes de ser declarada/inferida.");
                return "ERROR";
            }
        }

        return "ERROR"; // No se encontró
    }
}