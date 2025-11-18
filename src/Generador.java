import java.util.ArrayList;
import java.util.Stack;

public class Generador {

    private static volatile Generador instance;
    private ArrayList<Terceto> tercetos;
    private Stack<String> pilaOperandos;
    private Stack<Integer> pilaControl;
    private Stack<ParametroInfo> pilaParametros;
    private Stack<ParametroRealInfo> pilaParametrosReales;
    private Stack<String> pilaLadoDerecho;
    private AnalizadorLexico al;

    // MODIFICACION: Bandera para controlar la generación y terceto dummy
    private boolean generacionHabilitada = true;
    private final Terceto dummyTerceto = new Terceto("DUMMY", "_", "_");

    private Generador() {
        this.tercetos = new ArrayList<Terceto>();
        this.pilaOperandos = new Stack<>();
        this.pilaControl = new Stack<>();
        this.pilaParametros = new Stack<>();
        this.pilaParametrosReales = new Stack<>();
        this.pilaLadoDerecho = new Stack<>();
    }

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

    public void setAnalizadorLexico(AnalizadorLexico al) {
        this.al = al;
    }

    // MODIFICACION: Setter para habilitar/deshabilitar generación
    public void setGeneracionHabilitada(boolean habilitada) {
        this.generacionHabilitada = habilitada;
    }

    public String addTerceto(String operador, String operando1, String operando2) {
        // MODIFICACION: Si la generación está deshabilitada, retornar índice dummy (-1)
        if (!generacionHabilitada) {
            return "[-1]";
        }
        Terceto t = new Terceto(operador, operando1, operando2);
        this.tercetos.add(t);
        return "[" + (this.tercetos.size() - 1) + "]";
    }

    public String addTerceto(String operador, String operando1) {
        return addTerceto(operador, operando1, null);
    }

    public String addTerceto(String operador) {
        return addTerceto(operador, null, null);
    }

    public int getProximoTerceto() {
        return this.tercetos.size();
    }

    public Terceto getTerceto(int index) {
        // MODIFICACION: Retornar dummy si el índice es -1
        if (index == -1) {
            return dummyTerceto;
        }
        if (index >= 0 && index < this.tercetos.size()) {
            return this.tercetos.get(index);
        }
        return null;
    }

    public void modificarSaltoTerceto(int index, String saltoDestino) {
        // MODIFICACION: Ignorar si el índice es dummy
        if (index == -1) return;

        if (index >= 0 && index < this.tercetos.size()) {
            this.tercetos.get(index).setOperando2(saltoDestino);
        }
    }

    public void imprimirTercetos() {
        System.out.println("\n=======================================================");
        System.out.println("## CODIGO INTERMEDIO (TERCETOS) ##");
        System.out.println("=======================================================");
        if (tercetos.isEmpty()) {
            System.out.println("No se genero codigo intermedio.");
        } else {
            for (int i = 0; i < tercetos.size(); i++) {
                System.out.println(i + ":\t" + tercetos.get(i).toString());
            }
        }
    }

    public void apilarOperando(String operando) {
        this.pilaOperandos.push(operando);
    }

    public String desapilarOperando() {
        if (!this.pilaOperandos.isEmpty()) {
            return this.pilaOperandos.pop();
        }
        return null;
    }

    public void apilarControl(int tercetoIndex) {
        this.pilaControl.push(tercetoIndex);
    }

    public int desapilarControl() {
        if (!this.pilaControl.isEmpty()) {
            return this.pilaControl.pop();
        }
        return -1;
    }

    public void abrirAmbito(String nombre) { al.abrirAmbito(nombre); }
    public void cerrarAmbito() { al.cerrarAmbito(); }
    public boolean existeEnAmbitoActual(String lexema) { return al.existeEnAmbitoActual(lexema); }
    public String getAmbitoActual() { return al.getAmbitoActual(); }


    public String getTipo(String operando) {
        if (operando == null) return "void";

        if (operando.startsWith("L")) {
            try {
                Integer.parseInt(operando.substring(1));
                return "lambda_expr";
            } catch (NumberFormatException e) {
            }
        }

        if (operando.startsWith("[")) {
            try {
                int index = Integer.parseInt(operando.substring(1, operando.length() - 1));
                // MODIFICACION: Usar el método getTerceto para manejar el -1
                Terceto t = getTerceto(index);
                if (t != null) {
                    return t.getTipo();
                } else {
                    return "error_tipo";
                }
            } catch (Exception e) {
                return "error_tipo";
            }
        }

        Object tipo;
        if (operando.contains(".")) {
            String[] parts = operando.split("\\.", 2);
            if (parts.length == 2) {
                tipo = al.getAtributoConPrefijo(parts[0], parts[1], "Tipo");
                if (tipo != null) return tipo.toString();
            }
        }

        tipo = al.getAtributo(operando, "Tipo");
        if (tipo != null) {
            return tipo.toString();
        }

        if (operando.endsWith("UI")) {
            return "uint";
        }
        if (operando.contains(".")) {
            return "float";
        }

        return "indefinido";
    }

    public String chequearTipos(String op, String tipo1, String tipo2, int linea) {
        if (tipo1.equals(tipo2) && !tipo1.equals("indefinido")) {
            return tipo1;
        }

        if (tipo1.equals("indefinido") || tipo2.equals("indefinido")) {
            return "error_tipo";
        }

        if ((tipo1.equals("uint") && tipo2.equals("float")) || (tipo1.equals("float") && tipo2.equals("uint"))) {
            al.agregarErrorSemantico("Linea " + linea + ": Error de Tipos: No se puede operar ("+op+") entre 'uint' y 'float' sin conversion explicita 'toui' (Tema 31).");
            return "error_tipo";
        }

        al.agregarErrorSemantico("Linea " + linea + ": Error de Tipos: Operacion ("+op+") entre tipos incompatibles: " + tipo1 + " y " + tipo2);
        return "error_tipo";
    }

    public boolean chequearAsignacion(String tipoVar, String tipoExpr, int linea) {
        if (tipoVar.equals(tipoExpr)) {
            return true;
        }

        if (tipoVar.equals("indefinido") || tipoExpr.equals("indefinido")) {
            return false;
        }

        if (tipoVar.equals("uint") && tipoExpr.equals("float")) {
            al.agregarErrorSemantico("Linea " + linea + ": Error de Tipos: Asignacion incompatible. No se puede asignar 'float' a 'uint' sin 'toui' (Tema 31).");
            return false;
        }

        if (tipoVar.equals("float") && tipoExpr.equals("uint")) {
            al.agregarErrorSemantico("Linea " + linea + ": Error de Tipos: Asignacion incompatible. No se puede asignar 'uint' a 'float' (Tema 31).");
            return false;
        }

        al.agregarErrorSemantico("Linea " + linea + ": Error de Tipos: Asignacion incompatible. No se puede asignar '" + tipoExpr + "' a '" + tipoVar + "'.");
        return false;
    }

    public void apilarParametro(ParametroInfo p) { this.pilaParametros.push(p); }

    public ArrayList<ParametroInfo> getListaParametros() {
        ArrayList<ParametroInfo> lista = new ArrayList<>(pilaParametros);
        pilaParametros.clear();
        return lista;
    }

    public void apilarParametroReal(ParametroRealInfo p) { this.pilaParametrosReales.push(p); }

    public ArrayList<ParametroRealInfo> getListaParametrosReales(int count) {
        ArrayList<ParametroRealInfo> lista = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            if (!pilaParametrosReales.isEmpty()) {
                lista.add(0, pilaParametrosReales.pop());
            }
        }
        return lista;
    }

    public int getCountParametrosReales() { return pilaParametrosReales.size(); }
    public void clearParametrosReales() { pilaParametrosReales.clear(); }

    public void apilarLadoDerecho(String s) { this.pilaLadoDerecho.push(s); }
    public Stack<String> getPilaLadoDerecho() { return this.pilaLadoDerecho; }
    public void clearLadoDerecho() { this.pilaLadoDerecho.clear(); }
}