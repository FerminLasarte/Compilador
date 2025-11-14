import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.Iterator;
import java.util.Map;

public class AnalizadorLexico {
    //variables semi-estaticas
    private int contadorFila;
    private int contadorColumna;
    private int ultimoEstado;
    private File archivo;
    private String lexema; //es un string mutable

    //variables dinamicas
    private ArrayList<String> lineasArchivo;
    private ArrayList<String> errores;
    private ArrayList<String> warnings;
    private ArrayList<String> erroresSemanticos;

    // --- CAMBIO: Tabla de Símbolos Anidada (Pila de Ámbitos) ---
    private Stack<Pair<String, HashMap<String, HashMap<String, Object>>>> tablaSimbolos;
    // --- CAMBIO: Palabras Reservadas separadas ---
    private HashMap<String, Integer> palabrasReservadas;

    private HashMap<String, Integer> codigosTokens;
    private int[][] matrizTransicionEstados;
    private AccionSemantica[][] matrizAccionesSemanticas;
    private HashMap<Character, Integer> columnaMatrices;

    public AnalizadorLexico(String rutaArchivo) {
        //inicializacion de variables
        contadorFila = 0;
        contadorColumna = 0;

        // --- CAMBIO: Inicialización de nuevas estructuras ---
        palabrasReservadas = new HashMap<String, Integer>();
        tablaSimbolos = new Stack<Pair<String, HashMap<String, HashMap<String, Object>>>>();
        // --- FIN CAMBIO ---

        // Crear el archivo con la ruta proporcionada
        archivo = new File(rutaArchivo);
        if (archivo.exists()) {
            lineasArchivo = new ArrayList<String>();
            erroresSemanticos = new ArrayList<String>();
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
        codigosTokens.put(".", 282);
        codigosTokens.put("=", 271); // ASIG_MULTIPLE
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
        codigosTokens.put("PUNTO", 282);

        codigosTokens.put("ERROR", 290);
        codigosTokens.put("PALABRA_RESERVADA", 291);

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

        // --- CAMBIO: Mover Palabras Reservadas a su propio map ---
        palabrasReservadas.put("if", codigosTokens.get("IF"));
        palabrasReservadas.put("else", codigosTokens.get("ELSE"));
        palabrasReservadas.put("endif", codigosTokens.get("ENDIF"));
        palabrasReservadas.put("print", codigosTokens.get("PRINT"));
        palabrasReservadas.put("return", codigosTokens.get("RETURN"));
        palabrasReservadas.put("float", codigosTokens.get("FLOAT"));
        palabrasReservadas.put("uint", codigosTokens.get("UINT"));
        palabrasReservadas.put("var", codigosTokens.get("VAR"));
        palabrasReservadas.put("do", codigosTokens.get("DO"));
        palabrasReservadas.put("while", codigosTokens.get("WHILE"));
        palabrasReservadas.put("lambda", codigosTokens.get("LAMBDA"));
        palabrasReservadas.put("cr", codigosTokens.get("CR"));
        palabrasReservadas.put("se", codigosTokens.get("SE"));
        palabrasReservadas.put("le", codigosTokens.get("LE"));
        palabrasReservadas.put("toui", codigosTokens.get("TOUI"));
        // --- FIN CAMBIO ---

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

            // --- CAMBIO: Verificación de Palabra Reservada ---
            if (tokenString != null && tokenString.equals("ID") && esPalabraReservada(lexema)) {
                tokenString = lexema.toUpperCase(); // Sobrescribe "ID" con "IF", "ELSE", etc.
            }
            // --- FIN CAMBIO ---

            if (proximoCaracter == '\n') {
                this.aumentarContadorFila();
            }
        }

        if (tokenString != null) {
            if (codigosTokens.containsKey(tokenString)) {
                // System.out.println("Token: " + tokenString + ", Lexema: " + lexema + ", (" + codigosTokens.get(tokenString) + ")");
                return codigosTokens.get(tokenString);
            }
        }

        if (lexema != null && !lexema.isEmpty() && codigosTokens.containsKey(lexema)) {
            // System.out.println("Token: Operador, Lexema: " + lexema + ", (" + codigosTokens.get(lexema) + ")");
            return codigosTokens.get(lexema);
        }

        return -1;
    }

    // --- CAMBIO: Modificado para usar la pila ---
    public void eliminarLexemaTS(String lexema) {
        if (!tablaSimbolos.isEmpty()) {
            tablaSimbolos.peek().getValue().remove(lexema);
        }
    }

    // --- CAMBIO: Modificado para usar palabrasReservadas ---
    public boolean esPalabraReservada(String lexema) {
        return palabrasReservadas.containsKey(lexema);
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

    // --- CAMBIO: Modificado para usar la pila (ámbito actual) ---
    public void agregarLexemaTS(String lexema){
        if (!tablaSimbolos.isEmpty()) {
            HashMap<String, HashMap<String, Object>> ambitoActual = tablaSimbolos.peek().getValue();
            if (!ambitoActual.containsKey(lexema)) {
                ambitoActual.put(lexema, new HashMap<>());
                ambitoActual.get(lexema).put("Reservada", false);
            }
        }
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

    // --- METODOS AÑADIDOS (3) ---

    /**
     * Agrega un error semántico a la lista.
     */
    public void agregarErrorSemantico(String string) {
        if(this.erroresSemanticos == null) {
            this.erroresSemanticos = new ArrayList<String>();
        }
        erroresSemanticos.add(string);
    }

    public ArrayList<String> getErroresSemanticos() {
        return this.erroresSemanticos;
    }
    // --- FIN DE METODOS AÑADIDOS ---


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

    // --- CAMBIO: Modificado para usar la pila (ámbito actual) ---
    public void agregarAtributoLexema(String lexema, String key, Object valor) {
        if (!tablaSimbolos.isEmpty()) {
            HashMap<String, HashMap<String, Object>> ambitoActual = tablaSimbolos.peek().getValue();
            if (ambitoActual.containsKey(lexema)) {
                ambitoActual.get(lexema).put(key, valor);
            } else {
                // Opcional: ¿Error si no existe?
            }
        }
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

    // --- CAMBIO: Modificado para usar la pila (ámbito actual) ---
    public void reemplazarEnTS(String lexemaViejo, String lexemaNuevo){
        if (!tablaSimbolos.isEmpty()) {
            HashMap<String, Object> atributos = tablaSimbolos.peek().getValue().remove(lexemaViejo);
            if (atributos != null) {
                tablaSimbolos.peek().getValue().put(lexemaNuevo, atributos);
            }
        }
    }

    // --- CAMBIO: Modificado para buscar en pila (Tema 23) ---
    // Tema 23: Búsqueda sin prefijo (hacia arriba)
    public Object getAtributo(String lexema, String atributo) {
        // Corrección: Stack.iterator() va desde el fondo. Necesitamos ir desde el tope.
        for (int i = tablaSimbolos.size() - 1; i >= 0; i--) {
            HashMap<String, HashMap<String, Object>> ambito = tablaSimbolos.get(i).getValue();
            if (ambito.containsKey(lexema)) {
                return ambito.get(lexema).get(atributo);
            }
        }
        return null;
    }

    // --- MÉTODOS DE ÁMBITO (NUEVOS) ---

    public void abrirAmbito(String nombre) {
        // Asegura que el nombre del ámbito sea único si es un bloque anónimo
        String nombreUnico = nombre;
        int i = 0;
        // Evita colisiones de nombres de ámbito
        while (getAmbitoPorNombre(nombreUnico) != null) {
            i++;
            nombreUnico = nombre + "_" + i;
        }
        tablaSimbolos.push(new Pair<>(nombreUnico, new HashMap<>()));
    }

    public void cerrarAmbito() {
        if (!tablaSimbolos.isEmpty()) {
            tablaSimbolos.pop();
        }
    }

    public String getAmbitoActual() {
        if (!tablaSimbolos.isEmpty()) {
            return tablaSimbolos.peek().getKey();
        }
        return "GLOBAL_NULL"; // No debería pasar si 'programa' abre un ámbito
    }

    // Busca un ámbito por nombre en la pila
    private HashMap<String, HashMap<String, Object>> getAmbitoPorNombre(String nombre) {
        Iterator<Pair<String, HashMap<String, HashMap<String, Object>>>> iter = tablaSimbolos.iterator();
        while (iter.hasNext()) {
            Pair<String, HashMap<String, HashMap<String, Object>>> par = iter.next();
            if (par.getKey().equals(nombre)) {
                return par.getValue();
            }
        }
        return null;
    }

    // Chequea si existe SOLO en el ámbito actual (para redeclaraciones)
    public boolean existeEnAmbitoActual(String lexema) {
        if (tablaSimbolos.isEmpty()) return false;
        return tablaSimbolos.peek().getValue().containsKey(lexema);
    }

    // Tema 23: Búsqueda con prefijo (saltar a ámbito)
    public Object getAtributoConPrefijo(String prefijo, String lexema, String atributo) {
        HashMap<String, HashMap<String, Object>> ambito = getAmbitoPorNombre(prefijo);
        if (ambito != null && ambito.containsKey(lexema)) {
            return ambito.get(lexema).get(atributo);
        }
        // Chequear visibilidad (Tema 23) - Por ahora, si lo encuentra, es visible.
        return null;
    }

    // Sobrecarga para imprimir la nueva TS
    public void imprimirTablaSimbolos() {
        System.out.println("\n=======================================================");
        System.out.println("## CONTENIDOS DE LA TABLA DE SIMBOLOS ##");
        System.out.println("=======================================================");

        if (tablaSimbolos.isEmpty()) {
            System.out.println("La tabla de simbolos esta vacia.");
            return;
        }

        String formatString = "| %-20s | %-30s | %-12s | %-18s | %-10s | %-10s |%n";
        System.out.printf(formatString, "Ambito", "Lexema", "Reservada", "Uso", "Tipo", "Contador");
        System.out.println("|----------------------|--------------------------------|--------------|--------------------|------------|------------|");

        // Iteramos en el orden de la pila (del fondo a la cima)
        Iterator<Pair<String, HashMap<String, HashMap<String, Object>>>> iter = tablaSimbolos.iterator();
        while (iter.hasNext()) {
            Pair<String, HashMap<String, HashMap<String, Object>>> par = iter.next();
            String ambitoNombre = par.getKey();

            for (Map.Entry<String, HashMap<String, Object>> entry : par.getValue().entrySet()) {
                String lexema = entry.getKey();
                HashMap<String, Object> atributos = entry.getValue();

                Object reservada = atributos.get("Reservada");
                Object uso = atributos.get("Uso");
                Object tipo = atributos.get("Tipo");
                Object contador = atributos.get("Contador");

                System.out.printf(formatString,
                        ambitoNombre,
                        lexema,
                        (reservada != null) ? reservada.toString() : "null",
                        (uso != null) ? uso.toString() : "null",
                        (tipo != null) ? tipo.toString() : "null",
                        (contador != null) ? contador.toString() : "0"
                );
            }
            System.out.println("|----------------------|--------------------------------|--------------|--------------------|------------|------------|");
        }
        System.out.println("=======================================================");
    }
}