import java.util.ArrayList;
import java.util.Stack;
import java.util.ArrayList;

public class Generador {

    private static volatile Generador instance;
    private ArrayList<Terceto> tercetos;
    private Stack<String> pilaOperandos;
    private Stack<Integer> pilaControl;
    private Stack<ParametroInfo> pilaParametros;
    private Stack<ParametroRealInfo> pilaParametrosReales;
    private Stack<String> pilaLadoDerecho;
    private AnalizadorLexico al;

    // Constructor privado para el Singleton
    private Generador() {
        this.tercetos = new ArrayList<Terceto>();
        this.pilaOperandos = new Stack<>();
        this.pilaControl = new Stack<>();
        this.pilaParametros = new Stack<>();
        this.pilaParametrosReales = new Stack<>();
        this.pilaLadoDerecho = new Stack<>();
    }

    /**
     * Obtiene la instancia única del Generador (Singleton).
     */
    public static Generador getInstance() {
        Generador result = instance;
        if (result == null) {
            synchronized (Generador.class) {
                result = instance;
                if (result == null) {
                    instance = result = new Generador();
                }
            }
        }
        return result;
    }

    /**
     * Establece la referencia al Analizador Léxico para acceder a la Tabla de Símbolos.
     */
    public void setAnalizadorLexico(AnalizadorLexico al) {
        this.al = al;
    }

    // --- MANEJO DE TERCETOS ---

    /**
     * Agrega un terceto a la lista y devuelve su índice como referencia (ej: "[0]").
     */
    public String addTerceto(String operador, String operando1, String operando2) {
        Terceto t = new Terceto(operador, operando1, operando2);
        this.tercetos.add(t);
        // Devuelve el índice como un String "[i]" para usarlo como operando
        return "[" + (this.tercetos.size() - 1) + "]";
    }

    public String addTerceto(String operador, String operando1) {
        return addTerceto(operador, operando1, null);
    }

    public String addTerceto(String operador) {
        return addTerceto(operador, null, null);
    }

    /**
     * Obtiene el número del próximo terceto que se va a crear.
     * Útil para backpatching (saber a dónde saltar).
     */
    public int getProximoTerceto() {
        return this.tercetos.size();
    }

    public Terceto getTerceto(int index) {
        if (index >= 0 && index < this.tercetos.size()) {
            return this.tercetos.get(index);
        }
        return null;
    }

    /**
     * Modifica el operando 2 de un terceto (para rellenar saltos de IF/WHILE).
     */
    public void modificarSaltoTerceto(int index, String saltoDestino) {
        if (index >= 0 && index < this.tercetos.size()) {
            // Los saltos (BI, BF) se rellenan en el operando 2
            this.tercetos.get(index).setOperando2(saltoDestino);
        }
    }

    /**
     * Imprime todos los tercetos generados, cumpliendo el formato del TP03.
     */
    public void imprimirTercetos() {
        System.out.println("\n=======================================================");
        System.out.println("## CODIGO INTERMEDIO (TERCETOS) ##");
        System.out.println("=======================================================");
        if (tercetos.isEmpty()) {
            System.out.println("No se genero codigo intermedio.");
        } else {
            // Imprime [i]: (operador, op1, op2)
            for (int i = 0; i < tercetos.size(); i++) {
                System.out.println(i + ":\t" + tercetos.get(i).toString());
            }
        }
    }

    // --- MANEJO DE PILAS (para expresiones y control) ---

    public void apilarOperando(String operando) {
        this.pilaOperandos.push(operando);
    }

    public String desapilarOperando() {
        if (!this.pilaOperandos.isEmpty()) {
            return this.pilaOperandos.pop();
        }
        return null; // Error, pila vacia
    }

    public void apilarControl(int tercetoIndex) {
        this.pilaControl.push(tercetoIndex);
    }

    public int desapilarControl() {
        if (!this.pilaControl.isEmpty()) {
            return this.pilaControl.pop();
        }
        return -1; // Error, pila vacia
    }

    public void abrirAmbito(String nombre) { al.abrirAmbito(nombre); }
    public void cerrarAmbito() { al.cerrarAmbito(); }
    public boolean existeEnAmbitoActual(String lexema) { return al.existeEnAmbitoActual(lexema); }
    public String getAmbitoActual() { return al.getAmbitoActual(); }


    /**
     * Obtiene el tipo de un operando (var, cte, o resultado de terceto).
     */
    public String getTipo(String operando) {
        if (operando == null) return "void";

        // 1. Es una referencia a una lambda (ej: "16")
        // Corrección: se escapa la barra -> "\d+"
        if (operando.matches("\\d+")) {
            return "lambda_expr";
        }

        // 2. Es un resultado de un terceto (ej: "[5]")
        if (operando.startsWith("[")) {
            try {
                int index = Integer.parseInt(operando.substring(1, operando.length() - 1));
                Terceto t = this.tercetos.get(index);
                if (t != null) {
                    return t.getTipo();
                } else {
                    return "error_tipo";
                }
            } catch (Exception e) {
                return "error_tipo";
            }
        }

        // 3. Es una constante literal (ej: "10UI", "1.5F+1")
        if (operando.endsWith("UI")) {
            return "uint";
        }
        if (operando.contains("F")) {
            return "float";
        }

        // 4. Es una variable o función (búsqueda en Tabla de Símbolos)
        Object tipo;
        // Tema 23: Check por prefijo
        if (operando.contains(".")) {
            // Corrección: se escapa el punto -> "\."
            String[] parts = operando.split("\\.", 2);
            if (parts.length == 2) {
                tipo = al.getAtributoConPrefijo(parts[0], parts[1], "Tipo");
                if (tipo != null) return tipo.toString();
            }
        }

        // Tema 23: Búsqueda sin prefijo (hacia arriba en la pila)
        tipo = al.getAtributo(operando, "Tipo");
        if (tipo != null) {
            return tipo.toString();
        }

        // No se encontró
        return "indefinido";
    }

    /**
     * TEMA 31: Chequea tipos en operaciones.
     * Solo permite operaciones entre tipos iguales. Prohíbe uint vs float.
     * Devuelve el tipo resultante o "error_tipo".
     */
    public String chequearTipos(String op, String tipo1, String tipo2, int linea) {
        if (tipo1.equals(tipo2) && !tipo1.equals("indefinido")) {
            return tipo1; // Mismo tipo, todo OK.
        }

        if (tipo1.equals("indefinido") || tipo2.equals("indefinido")) {
            return "error_tipo";
        }

        // Tema 31: No se permiten operaciones entre uint y float
        if ((tipo1.equals("uint") && tipo2.equals("float")) || (tipo1.equals("float") && tipo2.equals("uint"))) {
            al.agregarErrorSemantico("Linea " + linea + ": Error de Tipos: No se puede operar ("+op+") entre 'uint' y 'float' sin conversion explicita 'toui' (Tema 31).");
            return "error_tipo";
        }

        al.agregarErrorSemantico("Linea " + linea + ": Error de Tipos: Operacion ("+op+") entre tipos incompatibles: " + tipo1 + " y " + tipo2);
        return "error_tipo";
    }

    /**
     * TEMA 31: Chequea tipos en asignacion (:=).
     */
    public boolean chequearAsignacion(String tipoVar, String tipoExpr, int linea) {
        if (tipoVar.equals(tipoExpr)) {
            return true; // Mismo tipo, OK.
        }

        if (tipoVar.equals("indefinido") || tipoExpr.equals("indefinido")) {
            return false;
        }

        // Tema 31: Permite asignar float a uint SOLO con `toui`
        if (tipoVar.equals("uint") && tipoExpr.equals("float")) {
            al.agregarErrorSemantico("Linea " + linea + ": Error de Tipos: Asignacion incompatible. No se puede asignar 'float' a 'uint' sin 'toui' (Tema 31).");
            return false;
        }

        // Tema 31: No dice nada de uint -> float, asi que lo prohibimos.
        if (tipoVar.equals("float") && tipoExpr.equals("uint")) {
            al.agregarErrorSemantico("Linea " + linea + ": Error de Tipos: Asignacion incompatible. No se puede asignar 'uint' a 'float' (Tema 31).");
            return false;
        }

        al.agregarErrorSemantico("Linea " + linea + ": Error de Tipos: Asignacion incompatible. No se puede asignar '" + tipoExpr + "' a '" + tipoVar + "'.");
        return false;
    }

    public void apilarParametro(ParametroInfo p) { this.pilaParametros.push(p); }

    public ArrayList<ParametroInfo> getListaParametros() {
        // Devuelve la lista en el orden correcto (FIFO)
        ArrayList<ParametroInfo> lista = new ArrayList<>(pilaParametros);
        pilaParametros.clear();
        return lista;
    }

    public void apilarParametroReal(ParametroRealInfo p) { this.pilaParametrosReales.push(p); }

    public ArrayList<ParametroRealInfo> getListaParametrosReales(int count) {
        ArrayList<ParametroRealInfo> lista = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            if (!pilaParametrosReales.isEmpty()) {
                // Los vamos dando vuelta para que queden en orden de declaración
                lista.add(0, pilaParametrosReales.pop());
            }
        }
        return lista;
    }

    public int getCountParametrosReales() { return pilaParametrosReales.size(); }
    public void clearParametrosReales() { pilaParametrosReales.clear(); }

    // --- MANEJO DE PILA LADO DERECHO (NUEVO) ---
    public void apilarLadoDerecho(String s) { this.pilaLadoDerecho.push(s); }
    public Stack<String> getPilaLadoDerecho() { return this.pilaLadoDerecho; }
    public void clearLadoDerecho() { this.pilaLadoDerecho.clear(); }
}