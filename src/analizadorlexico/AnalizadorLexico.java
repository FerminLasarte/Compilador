package analizadorlexico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class AnalizadorLexico{
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
    private HashMap<String, HashMap<String, Object>> tablaSimbolos; //son dos hash map porque el primero tendra como clave el lexema y luego el segundo tendra como clave el atributo
    private HashMap<String, Integer> codigosTokens; //almacena los numero de token que se le asigna a cada lexema
    private int[][] matrizTransicionEstados;
    private AccionSemantica[][] matrizAccionesSemanticas;
    private HashMap<Character, Integer> columnaMatrices; //el caracter que se asocia a cada columna de las matrices

    public AnalizadorLexico(String rutaArchivo) {
        //inicializacion de variables
        contadorFila=0;
        contadorColumna=0;

        // Crear el archivo con la ruta proporcionada
        archivo = new File(rutaArchivo);

        // Verificar si el archivo existe
        if (archivo.exists()) {
            // Leer todas las líneas del archivo y colocarlas en una lista
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
        }
        else {
            System.out.println("El archivo no existe. Verifique la ruta proporcionada.");
        }

        //asignacion de los codigos de los tokens
        codigosTokens=new HashMap<String, Integer>();
        codigosTokens.put("$", 0);
        codigosTokens.put("(", 40);
        codigosTokens.put(")", 41);
        codigosTokens.put("*", 42);
        codigosTokens.put("+", 43);
        codigosTokens.put(",", 44);
        codigosTokens.put("-", 45);
        codigosTokens.put(".", 46);
        codigosTokens.put("/", 47);
        codigosTokens.put(";", 59);
        codigosTokens.put("<", 60);
        codigosTokens.put(">", 62);
        codigosTokens.put("[", 91);
        codigosTokens.put("]", 93);
        codigosTokens.put("=", 61);
        codigosTokens.put("ID", 257);
        codigosTokens.put("CTE", 258);
        codigosTokens.put("if", 259);
        codigosTokens.put("then", 260);
        codigosTokens.put("else", 261);
        codigosTokens.put("end_if", 262);
        codigosTokens.put("ret", 263);
        codigosTokens.put("integer", 264);
        codigosTokens.put("begin", 265);
        codigosTokens.put("single", 266);
        codigosTokens.put("while", 267);
        codigosTokens.put("struct", 268);
        codigosTokens.put("goto", 269);
        codigosTokens.put("end", 270);
        codigosTokens.put("float", 271);
        codigosTokens.put("outf", 272);
        codigosTokens.put("typedef", 273);
        codigosTokens.put("fun", 274);
        codigosTokens.put("CADENA", 275);
        codigosTokens.put("ERROR", 276);
        codigosTokens.put(">=", 277);
        codigosTokens.put("<=", 278);
        codigosTokens.put("!=", 279);
        codigosTokens.put(":=", 280);
        codigosTokens.put("ETIQUETA", 281);
        codigosTokens.put("tos", 282);
        codigosTokens.put("IF", 259);
        codigosTokens.put("THEN", 260);
        codigosTokens.put("ELSE", 261);
        codigosTokens.put("END_IF", 262);
        codigosTokens.put("RET", 263);
        codigosTokens.put("INTEGER", 264);
        codigosTokens.put("BEGIN", 265);
        codigosTokens.put("SINGLE", 266);
        codigosTokens.put("WHILE", 267);
        codigosTokens.put("STRUCT", 268);
        codigosTokens.put("GOTO", 269);
        codigosTokens.put("END", 270);
        codigosTokens.put("FLOAT", 271);
        codigosTokens.put("OUTF", 272);
        codigosTokens.put("TYPEDEF", 273);
        codigosTokens.put("FUN", 274);
        codigosTokens.put("TOS", 282);

        //mapeo de las columnas de las matrices a un entero asignado asi despues nos podemos mover con estos valores en las matrices
        columnaMatrices = new HashMap<Character, Integer>();
        columnaMatrices.put('+', 0);
        columnaMatrices.put('-', 1);
        columnaMatrices.put('>', 2);
        columnaMatrices.put(':', 3);
        columnaMatrices.put('<', 4);
        columnaMatrices.put('>', 4);
        columnaMatrices.put('=', 5);

        char[] letras = {'a','b','c','d','e','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        for(int i=0; i<letras.length; i++) {
            columnaMatrices.put(letras[i], 6);
        }

        columnaMatrices.put('f', 7);
        columnaMatrices.put('F', 7);
        columnaMatrices.put('&', 8);

        char[] digitos = {'0','1','2','3','4','5','6','7','8','9'};
        for(int i=0; i<digitos.length; i++) {
            columnaMatrices.put(digitos[i], 10);
        }

        columnaMatrices.put('.', 11);
        columnaMatrices.put('@', 12);
        columnaMatrices.put('!', 13);
        columnaMatrices.put('%', 14);
        columnaMatrices.put('\n', 15);
        columnaMatrices.put('\t', 16);
        columnaMatrices.put('\r', 16);
        columnaMatrices.put('(', 17);
        columnaMatrices.put(')', 17);
        columnaMatrices.put('{', 17);
        columnaMatrices.put('}', 17);
        columnaMatrices.put('_', 17);
        columnaMatrices.put(';', 17);
        columnaMatrices.put('*', 17);
        columnaMatrices.put('/', 17);
        columnaMatrices.put('U', 18);
        columnaMatrices.put('I', 19);

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

        //inicializacion matriz transicion de estados, el estado -1 es el final y -2 es error
        matrizTransicionEstados= new int[][] {
                /*0*/{-1,1,3,2,3,4,5,5,6,8,9,10,-2,-2,0,0,-1,5,5},
                /*1*/{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                /*2*/{-2,-2,-2,-2,-2,-1,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2},
                /*3*/{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                /*4*/{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                /*5*/{-1,-1,-1,-1,-1,-1,5,5,-1,5,-1,-1,-1,5,-1,-1,-1,5,5},
                /*6*/{6,6,6,6,6,6,6,6,-1,6,6,6,6,6,6,6,6,6,6},
                /*7*/{-2,-2,-2,-2,-2,-2,-2,-2,-2,7,8,-2,-2,-2,-2,-2,-2,14,-2},
                /*8*/{-1,-1,-1,-1,-1,-1,-1,-1,-1,10,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                /*9*/{9,9,9,9,9,9,9,9,9,9,9,9,9,9,0,9,9,9,9},
                /*10*/{-1,-1,-1,-1,-1,-1,-1,11,-1,10,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                /*11*/{12,12,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2},
                /*12*/{-2,-2,-2,-2,-2,-2,-2,-2,-2,13,-2,-2,-2,-2,-2,-2,-2,-2,-2},
                /*13*/{-1,-1,-1,-1,-1,-1,-1,-1,-1,13,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                /*14*/{-2,-2,-2,-2,-2,-2,-2,-2,-2,13,-2,-2,-2,-2,-2,-2,-2,-2,-1},
        };

        // Inicialización de acciones semánticas
        AccionSemantica as1 = new AccionSemantica.AccionSemantica1();
        AccionSemantica as2 = new AccionSemantica.AccionSemantica2();
        AccionSemantica as3 = new AccionSemantica.AccionSemantica3();
        AccionSemantica as4 = new AccionSemantica.AccionSemantica4();
        AccionSemantica as5 = new AccionSemantica.AccionSemantica5();
        AccionSemantica as6 = new AccionSemantica.AccionSemantica6();
        AccionSemantica as7 = new AccionSemantica.AccionSemantica7();
        AccionSemantica asE = new AccionSemantica.AccionSemanticaError();
        AccionSemantica asNull = null;

        matrizAccionesSemanticas = new AccionSemantica[][] {
                /*0*/ {as1, as1, as1, as1, as1, as1, as1, as1, as1, as1, as1, asE, asNull, asNull, as7},
                /*1*/ {as5, as5, as2, as5, as5, as5, as5, as5, as5, as5, as5, asE, as5, as5, as5},
                /*2*/ {asE, asE, asE, asE, asE, as2, asE, asE, asE, asE, asE, asE, asE, asE, asE},
                /*3*/ {as5, as5, as5, as5, as5, as5, as5, as5, as5, as5, as5, asE, as5, as5, as5},
                /*4*/ {as5, as5, as5, as5, as5, as2, as5, as5, as5, as5, as5, asE, as5, as5, as5},
                /*5*/ {as3, as3, as3, as3, as3, as2, as2, as2, as3, as3, as3, asE, as3, as3, as3},
                /*6*/ {as2, as2, as2, as2, as2, as2, as7, as2, as2, as2, as2, asE, as2, as2, as2},
                /*7*/ {as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, as4, asE, as4, as4, as4},
                /*8*/ {as6, as6, as6, as6, as6, as6, as6, as6, as6, as6, as6, asE, as6, as6, as6},
                /*9*/ {asNull, asNull, asNull, asNull, asNull, asNull, asNull, asNull, asNull, asNull, asNull, asE, asNull, asNull, asNull},
                /*10*/ {as6, as6, as6, as6, as6, as6, as2, as6, as6, as6, as6, asE, as6, as6, as6},
                /*11*/ {as2, as2, as2, as2, as2, as2, asE, as2, as2, as2, as2, asE, as2, as2, as2},
                /*12*/ {asE, asE, asE, asE, asE, asE, asE, asE, asE, asE, asE, asE, asE, asE, asE},
                /*13*/ {as6, as6, as6, as6, as6, as6, as6, as6, as6, as6, as6, asE, as6, as6, as6}
        };
    }

    public int yylex(){
        ultimoEstado = 0;
        String token = null;
        while(ultimoEstado != -1 && ultimoEstado != -2){
            char proximoCaracter;
            if(lineasArchivo.get(contadorFila).length() == contadorColumna){
                proximoCaracter = '\n'; //esto seria cuando es el fin de una linea
            }
            else{
                proximoCaracter = lineasArchivo.get(contadorFila).charAt(contadorColumna);
                if(proximoCaracter == '\\' && lineasArchivo.get(contadorFila).charAt(contadorColumna++) == 'n'){
                    proximoCaracter = '\n';
                    contadorColumna++;
                }
            }
            contadorColumna++;
            //ahora debo saber en que columna de la matriz debo ubicarme
            int columnaCaracter;
            if(columnaMatrices.containsKey(proximoCaracter)){
                columnaCaracter = columnaMatrices.get(proximoCaracter);
            }
            else{
                columnaCaracter = 20; //cuando es la columna de otros
            }
            if(matrizAccionesSemanticas[ultimoEstado][columnaCaracter] != null){
                token = matrizAccionesSemanticas[ultimoEstado][columnaCaracter].aplicarAS(this, proximoCaracter);
            }
            ultimoEstado = matrizTransicionEstados[ultimoEstado][columnaCaracter];
            if(proximoCaracter == '\n'){
                this.aumentarContadorFila(); //se hace aca para que no haya problemas al identificar la linea del error
            }
        }
        //retorno del token
        if (token != null && (token.equals("ID") || token.equals("CTE") || token.equals("ERROR") || token.equals("CADENA_MULTILINEA"))) {
            System.out.println(token + ": " + lexema.toString());
            return codigosTokens.get(token); //varios lexemas para un token
        }
        else {
            //System.out.print(codigosTokens.get(lexema.toString()));
            System.out.println(lexema.toString() + " ");
            return codigosTokens.get(lexema.toString()); //un lexema por token
        }
    }

    public void inicializarLexema(){
        this.lexema = new String();
    }

    public void setLexema(String lexema){
        this.lexema = lexema;
    }

    public String getLexema(){
        return this.lexema;
    }

    public void agregarLexemaTS(String lexema){
        tablaSimbolos.put(lexema, new HashMap<String,Object>());
        tablaSimbolos.get(lexema).put("Reservada", false);
    }

    public void agregarCaracterLexema(char c){
        this.lexema += c;
    }

    public void reiniciarLexema(){
        this.lexema = new String("");
    }

    //debemos preocuparnos por temas de no devolver listas originales o demas?
    public ArrayList<String> getErrores(){
        return this.errores;
    }

    public void agregarError(String string) {
        if(this.errores == null) {
            this.errores = new ArrayList<String>();
        }
        errores.add("Linea: "+ (contadorFila+1) + " - Columna: " + (this.contadorColumna - lexema.length()) + " - " + string);
    }

    //este metodo nos sirve para cuando leemos un caracter y hay que devolverlo a la entrada.
    public void disminuirContador() {
        if(contadorColumna!=0){
            contadorColumna--;
        }
        else{
            contadorFila--;
            contadorColumna=lineasArchivo.get(contadorFila).length();
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
        //tablaSimbolos.remove(lexemaViejo);
    }

    public Object getAtributo(String lexema, String atributo){
        return tablaSimbolos.get(lexema).get(atributo);
    }
}
