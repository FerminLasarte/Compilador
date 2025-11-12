import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AnalizadorLexico {
    //variables semi-estaticas
    private int contadorFila;
    private int contadorColumna;
    private int ultimoEstado;
    private File archivo;
    private String lexema; //es un string mutable
    private String lexemaProcesadoRecientemente = null;
    private boolean esNuevoIngreso = false;

    //variables dinamicas
    private ArrayList<String> lineasArchivo;
    private ArrayList<String> errores;
    private ArrayList<String> warnings;
    private HashMap<String, HashMap<String, Object>> tablaSimbolos;
    private HashMap<String, Integer> codigosTokens;
    private int[][] matrizTransicionEstados;
    private AccionSemantica[][] matrizAccionesSemanticas;
    private HashMap<Character, Integer> columnaMatrices;

    public AnalizadorLexico(String rutaArchivo) {
        //inicializacion de variables
        contadorFila = 0;
        contadorColumna = 0;

        // Crear el archivo con la ruta proporcionada
        archivo = new File(rutaArchivo);
        if (archivo.exists()) {
            lineasArchivo = new ArrayList<String>();
            BufferedReader lector;
            try {
                lector = new BufferedReader(new FileReader(archivo));
                String linea = lector.readLine();
                while (linea != null) {
                    lineasArchivo.add(linea);
                    linea = lector.readLine();
                }
                lector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("El archivo no existe. Verifique la ruta proporcionada.");
        }

        // Asignación de los códigos de los tokens para que coincidan con la gramática
        codigosTokens = new HashMap<String, Integer>();

        // Tokens de un solo carácter (el parser los usa por su código ASCII)
        codigosTokens.put("+", 43);
        codigosTokens.put("-", 45);
        codigosTokens.put("*", 42);
        codigosTokens.put("/", 46);
        codigosTokens.put(".", 47);
        codigosTokens.put("=", 271);
        codigosTokens.put("<", 60);
        codigosTokens.put(">", 62);
        codigosTokens.put("(", 40);
        codigosTokens.put(")", 41);
        codigosTokens.put("{", 123);
        codigosTokens.put("}", 125);
        codigosTokens.put(";", 59);
        codigosTokens.put(",", 44);

        // Tokens definidos en la gramática
        codigosTokens.put("ID", 257);
        codigosTokens.put("CTE", 258);
        codigosTokens.put("IF", 259);
        codigosTokens.put("ELSE", 260);
        codigosTokens.put("FLOAT", 261);
        codigosTokens.put("ENDIF", 262);
        codigosTokens.put("RETURN", 263);
        codigosTokens.put("PRINT", 264);
        codigosTokens.put("UINT", 265);
        codigosTokens.put("VAR", 266);
        codigosTokens.put("DO", 267);
        codigosTokens.put("WHILE", 268);
        codigosTokens.put("LAMBDA", 269);
        codigosTokens.put("CADENA_MULTILINEA", 270);
        codigosTokens.put("ASIG_MULTIPLE", 271);
        codigosTokens.put("CR", 272);
        codigosTokens.put("SE", 273);
        codigosTokens.put("LE", 274);
        codigosTokens.put("TOUI", 275);
        codigosTokens.put("ASIG", 276);
        codigosTokens.put("FLECHA", 277);
        codigosTokens.put("MAYOR_IGUAL", 278);
        codigosTokens.put("MENOR_IGUAL", 279);
        codigosTokens.put("DISTINTO", 280);
        codigosTokens.put("IGUAL_IGUAL", 281);
        codigosTokens.put("ERROR", 282);
        codigosTokens.put("PALABRA_RESERVADA", 283);

        // Mapeo de lexemas compuestos a sus tokens
        codigosTokens.put(":=", 276);
        codigosTokens.put("->", 277);
        codigosTokens.put(">=", 278);
        codigosTokens.put("<=", 279);
        codigosTokens.put("=!", 280);
        codigosTokens.put("==", 281);

        // Mapeo de caracteres para la matriz de transición
        columnaMatrices = new HashMap<Character, Integer>();
        columnaMatrices.put('+', 0);
        columnaMatrices.put('-', 1);
        columnaMatrices.put('>', 2);
        columnaMatrices.put(':', 3);
        columnaMatrices.put('<', 4);
        columnaMatrices.put('=', 5);
        char[] letras = {'a','b','c','d','e','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        for(char letra : letras) {
            columnaMatrices.put(letra, 6);
            columnaMatrices.put(Character.toUpperCase(letra), 6);
        }
        columnaMatrices.put('f', 7);
        columnaMatrices.put('F', 7);
        columnaMatrices.put('&', 8);
        char[] digitos = {'0','1','2','3','4','5','6','7','8','9'};
        for (char digito : digitos) {
            columnaMatrices.put(digito, 9);
        }
        columnaMatrices.put('.', 10);
        columnaMatrices.put('@', 11);
        columnaMatrices.put('!', 12);
        columnaMatrices.put('%', 13);
        columnaMatrices.put('\n', 14);
        columnaMatrices.put('\t', 15);
        columnaMatrices.put(' ', 15);
        columnaMatrices.put('(', 16);
        columnaMatrices.put(')', 16);
        columnaMatrices.put('{', 16);
        columnaMatrices.put('}', 16);
        columnaMatrices.put('_', 16);
        columnaMatrices.put(';', 16);
        columnaMatrices.put('*', 16);
        columnaMatrices.put('/', 16);
        columnaMatrices.put(',', 16);
        columnaMatrices.put('U', 17);
        columnaMatrices.put('I', 18);

        // Tabla de Símbolos con palabras reservadas
        tablaSimbolos = new HashMap<String, HashMap<String, Object>>();
        tablaSimbolos.put("if", new HashMap<String, Object>());
        tablaSimbolos.get("if").put("Reservada", true);
        tablaSimbolos.put("else", new HashMap<String, Object>());
        tablaSimbolos.get("else").put("Reservada", true);
        tablaSimbolos.put("endif", new HashMap<String, Object>());
        tablaSimbolos.get("endif").put("Reservada", true);
        tablaSimbolos.put("print", new HashMap<String, Object>());
        tablaSimbolos.get("print").put("Reservada", true);
        tablaSimbolos.put("return", new HashMap<String, Object>());
        tablaSimbolos.get("return").put("Reservada", true);
        tablaSimbolos.put("float", new HashMap<String, Object>());
        tablaSimbolos.get("float").put("Reservada", true);
        tablaSimbolos.put("uint", new HashMap<String, Object>());
        tablaSimbolos.get("uint").put("Reservada", true);
        tablaSimbolos.put("var", new HashMap<String, Object>());
        tablaSimbolos.get("var").put("Reservada", true);
        tablaSimbolos.put("do", new HashMap<String, Object>());
        tablaSimbolos.get("do").put("Reservada", true);
        tablaSimbolos.put("while", new HashMap<String, Object>());
        tablaSimbolos.get("while").put("Reservada", true);
        tablaSimbolos.put("lambda", new HashMap<String, Object>());
        tablaSimbolos.get("lambda").put("Reservada", true);
        tablaSimbolos.put("cr", new HashMap<String, Object>());
        tablaSimbolos.get("cr").put("Reservada", true);
        tablaSimbolos.put("se", new HashMap<String, Object>());
        tablaSimbolos.get("se").put("Reservada", true);
        tablaSimbolos.put("le", new HashMap<String, Object>());
        tablaSimbolos.get("le").put("Reservada", true);
        tablaSimbolos.put("toui", new HashMap<String, Object>());
        tablaSimbolos.get("toui").put("Reservada", true);

        // Matriz de Transición de Estados (sin cambios)
        matrizTransicionEstados = new int[][] {
                /*0*/ {-1, 1, 3, 2, 3, 4, 5, 5, 6, 7, 16, 9, -2, -2, 0, 0, -1, 5, 5},
                /*1*/ {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                /*2*/ {-2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2},
                /*3*/ {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                /*4*/ {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                /*5*/ {-1, -1, -1, -1, -1, -1, 5, 5, -1, 5, -1, -1, -1, 5, -1, -1, -1, 5, 5},
                /*6*/ {6, 6, 6, 6, 6, 6, 6, 6, -1, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6},
                /*7*/ {-2, -2, -2, -2, -2, -2, -2, -2, -2, 7, 8, -2, -2, -2, -2, -2, -2, 14, -2},
                /*8*/ {-1, -1, -1, -1, -1, -1, -1, 11, -1, 10, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                /*9*/ {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 0, 9, 9, 9, 9},
                /*10*/{-1, -1, -1, -1, -1, -1, -1, 11, -1, 10, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                /*11*/{12, 12, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2},
                /*12*/{-2, -2, -2, -2, -2, -2, -2, -2, -2, 13, -2, -2, -2, -2, -2, -2, -2, -2, -2},
                /*13*/{-1, -1, -1, -1, -1, -1, -1, -1, -1, 13, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                /*14*/{-2, -2, -2, -2, -2, -2, -2, -2, -2, 13, -2, -2, -2, -2, -2, -2, -2, -2, 15},
                /*15*/{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
                /*16*/{-1, -1, -1, -1, -1, -1, -1, -1, -1, 10, -1, -1, -1, -1, -1, -1, -1, -1, -1},
        };

        // Matriz de Acciones Semánticas (sin cambios)
        AccionSemantica as1 = new AccionSemantica.AccionSemantica1();
        AccionSemantica as2 = new AccionSemantica.AccionSemantica2();
        AccionSemantica as3 = new AccionSemantica.AccionSemantica3();
        AccionSemantica as4 = new AccionSemantica.AccionSemantica4();
        AccionSemantica as5 = new AccionSemantica.AccionSemantica5();
        AccionSemantica as6 = new AccionSemantica.AccionSemantica6();
        AccionSemantica as7 = new AccionSemantica.AccionSemantica7();
        AccionSemantica asE = new AccionSemantica.AccionSemanticaError();
        AccionSemantica asNull = new AccionSemantica.AccionSemanticaNull();
        matrizAccionesSemanticas = new AccionSemantica[][]{
                /*0*/ {as1, as1, as1, as1, as1, as1, as1, as1, as1, as1, as1, as1, asE, asE, asNull, asNull, as1, as1, as1},
                /*1*/ {as5, as5, as2, as5, as5, as5, as5, as5, as5, as5, as5, as5, as5, as5, as5, as5, as5, as5, as5},
                /*2*/ {asE, asE, asE, asE, asE, as2, asE, asE, asE, asE, asE, asE, asE, asE, asE, asE, asE, asE, asE},
                /*3*/ {as5, as5, as5, as5, as5, as2, as5, as5, as5, as5, as5, as5, as5, as5, as5, as5, as5, as5, as5},
                /*4*/ {as5, as5, as5, as5, as5, as2, as5, as5, as5, as5, as5, as5, as2, as5, as5, as5, as5, as5, as5},
                /*5*/ {as3, as3, as3, as3, as3, as3, as2, as2, as3, as2, as3, as3, as3, as2, as3, as3, as3, as2, as2},
                /*6*/ {as2, as2, as2, as2, as2, as2, as2, as2, as7, as2, as2, as2, as2, as2, as2, as2, as2, as2, as2},
                /*7*/ {asE, asE, asE, asE, asE, asE, asE, asE, asE, as2, as2, asE, asE, asE, asE, asE, asE, as2, asE},
                /*8*/ {as6, as6, as6, as6, as6, as6, as6, as2, as6, as2, as6, as6, as6, as6, as6, as6, as6, as6, as6},
                /*9*/ {asNull, asNull, asNull, asNull, asNull, asNull, asNull, asNull, asNull, asNull, asNull, asNull, asNull, asNull, asNull, asNull, asNull, asNull, asNull},
                /*10*/{as6, as6, as6, as6, as6, as6, as6, as2, as6, as2, as6, as6, as6, as6, as6, as6, as6, as6, as6},
                /*11*/{as2, as2, asE, asE, asE, asE, asE, asE, asE, asE, asE, asE, asE, asE, asE, asE, asE, asE, asE},
                /*12*/{asE, asE, asE, asE, asE, asE, asE, asE, asE, as2, asE, asE, asE, asE, asE, asE, asE, asE, asE},
                /*13*/{as6, as6, as6, as6, as6, as6, as6, as6, as6, as2, as6, as6, as6, as6, as6, as6, as6, as6, as6},
                /*14*/{asE, asE, asE, asE, asE, asE, asE, asE, asE, asE, asE, asE, asE, asE, asE, asE, asE, asE, as2},
                /*15*/{as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4},
                /*16*/{as5, as5, as5, as5, as5, as5, as5, as5, as5, as2, as5, as5, as5, as5, as5, as5, as5, as5, as5},
        };
    }

    public int yylex() {
        ultimoEstado = 0;
        String tokenString = null;

        while (ultimoEstado != -1 && ultimoEstado != -2) {
            char proximoCaracter;
            if (contadorFila >= lineasArchivo.size()) {
                return 0;
            }
            String currentLine = lineasArchivo.get(contadorFila);
            if(currentLine.length() == contadorColumna){
                proximoCaracter = '\n';
            }
            else{
                proximoCaracter = currentLine.charAt(contadorColumna);
            }
            contadorColumna++;

            int columnaCaracter;
            if (columnaMatrices.containsKey(proximoCaracter)) {
                columnaCaracter = columnaMatrices.get(proximoCaracter);
            } else {
                columnaCaracter = 15;
            }

            if (matrizAccionesSemanticas[ultimoEstado][columnaCaracter] != null) {
                tokenString = matrizAccionesSemanticas[ultimoEstado][columnaCaracter].aplicarAS(this, proximoCaracter);
            }

            ultimoEstado = matrizTransicionEstados[ultimoEstado][columnaCaracter];

            if (proximoCaracter == '\n') {
                this.aumentarContadorFila();
            }
        }

        if (tokenString != null) {
            if (codigosTokens.containsKey(tokenString)) {
                System.out.println("Token: " + tokenString + ", Lexema: " + lexema + ", (" + codigosTokens.get(tokenString) + ")");
                return codigosTokens.get(tokenString);
            }
        }

        if (lexema != null && !lexema.isEmpty() && codigosTokens.containsKey(lexema)) {
            System.out.println("Token: Operador, Lexema: " + lexema + ", (" + codigosTokens.get(lexema) + ")");
            return codigosTokens.get(lexema);
        }

        return -1;
    }

    public void limpiarUltimaModificacionTS() {
        this.lexemaProcesadoRecientemente = null;
        this.esNuevoIngreso = false;
    }

    public void setUltimaModificacionTS(String lexema, boolean esNuevo) {
        this.lexemaProcesadoRecientemente = lexema;
        this.esNuevoIngreso = esNuevo;
    }

    public void revertirUltimaModificacionTS() {
        if (lexemaProcesadoRecientemente == null) {
            return; // No hay nada que revertir
        }

        HashMap<String, Object> atributos = tablaSimbolos.get(lexemaProcesadoRecientemente);
        if (atributos == null) {
            return;
        }

        if (esNuevoIngreso) {
            if (atributos.containsKey("Reservada") && !(boolean)atributos.get("Reservada")) {
                tablaSimbolos.remove(lexemaProcesadoRecientemente);
            }
        } else {
            Object contadorObj = atributos.get("Contador");
            if (contadorObj != null) {
                int contador = (int) contadorObj;
                if (contador > 1) {
                    atributos.put("Contador", contador - 1);
                } else {
                    // Si el contador era 1, lo quitamos.
                    atributos.remove("Contador");
                }
            }
        }

        // Limpiamos las banderas DESPUÉS de revertir
        limpiarUltimaModificacionTS();
    }

    public void eliminarLexemaTS(String lexema) {
        if (tablaSimbolos.containsKey(lexema)) {
            tablaSimbolos.remove(lexema);
        }
    }

    public boolean esPalabraReservada(String lexema) {
        return tablaSimbolos.containsKey(lexema) && (boolean) tablaSimbolos.get(lexema).get("Reservada");
    }

    public void inicializarLexema(){
        this.lexema = "";
    }

    public void setLexema(String lexema){
        this.lexema = lexema;
    }

    public String getLexema(){
        return this.lexema;
    }

    public boolean isAllLowerCase(String str) {
        return str != null && str.equals(str.toLowerCase());
    }

    public boolean isAllUpperCase(String str) {
        return str != null && str.equals(str.toUpperCase());
    }

    public void agregarLexemaTS(String lexema){
        tablaSimbolos.put(lexema, new HashMap<String,Object>());
        tablaSimbolos.get(lexema).put("Reservada", false);
    }

    public void agregarCaracterLexema(char c){
        this.lexema += c;
    }

    public void reiniciarLexema(){
        this.lexema = "";
    }

    public ArrayList<String> getErrores(){
        return this.errores;
    }

    public void agregarError(String string) {
        if(this.errores == null) {
            this.errores = new ArrayList<String>();
        }
        errores.add("Linea: "+ (contadorFila+1) + " - Columna: " + (this.contadorColumna - lexema.length()) + " - " + string);
    }

    public void disminuirContador() {
        if(contadorColumna!=0){
            contadorColumna--;
        }
        else{
            if (contadorFila > 0) {
                contadorFila--;
                contadorColumna=lineasArchivo.get(contadorFila).length();
            }
        }
    }

    public void aumentarContadorFila() {
        contadorFila++;
        contadorColumna = 0;
    }

    public HashMap<String, HashMap<String, Object>> getTablaSimbolos() {
        return tablaSimbolos;
    }

    public void agregarAtributoLexema(String lexema, String key, Object valor) {
        tablaSimbolos.get(lexema).put(key, valor);
    }

    public int getContadorFila() {
        return this.contadorFila;
    }

    public void agregarWarning(String string) {
        if(this.warnings == null) {
            this.warnings = new ArrayList<String>();
        }
        warnings.add("Linea: " + (contadorFila + 1) + " - Columna: " + (this.contadorColumna - lexema.length()) + " - " + string);
    }

    public ArrayList<String> getWarnings(){
        return this.warnings;
    }

    public void reemplazarEnTS(String lexemaViejo, String lexemaNuevo){
        agregarLexemaTS(lexemaNuevo);
        HashMap<String, Object> atributos = new HashMap<>(tablaSimbolos.get(lexemaViejo));
        tablaSimbolos.put(lexemaNuevo, atributos);
    }

    public Object getAtributo(String lexema, String atributo){
        return tablaSimbolos.get(lexema).get(atributo);
    }
}