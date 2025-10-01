package analizadorlexico;
import analizadorlexico.accionessemanticas.*;
import analizadorlexico.conjuntosimbolos.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
                    columnaCaracter=20; //cuando es la columna de otros
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
            if(token != null && (token.equals("ID") || token.equals("CTE") || token.equals("CADENA") || token.equals("ERROR") || token.equals("ETIQUETA"))) {
                System.out.println(token + ": " + lexema.toString());
                return codigosTokens.get(token); //varios lexemas para un token
            }
            else{
                //System.out.print(codigosTokens.get(lexema.toString()));
                System.out.println(lexema.toString()+" ");
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

        public boolean dentroRangoPositivo() { /*el negativo ya esta chequeado en la accion semantica 5*/
            if (this.tablaSimbolos.get(this.lexema).get("Tipo") == "Integer") {
                BigDecimal bd = new BigDecimal(lexema);
                if(new BigDecimal("32767").compareTo(bd)<0) { //cuando es 32768 positivo
                    return false;
                }
            }
            return true;
        }

        public boolean dentroRangoNegativo() { /*el positivo ya esta chequeado en la accion semantica 7*/
            String valorOctal = this.lexema;
            if (this.tablaSimbolos.get(lexema).get("Tipo") == "Octal") {
                BigInteger valorDecimal = new BigInteger(valorOctal, 8);
                valorDecimal = valorDecimal.negate();
                System.out.println(valorDecimal);
                if(!(new BigInteger("-4096").compareTo(valorDecimal)<=0)) {
                    return false;
                }
            }
            //manejamos el valor negativo en la TS
            if (!this.tablaSimbolos.containsKey("-" + lexema)) {
                HashMap<String, Object> atributos = new HashMap<>(this.tablaSimbolos.get(lexema)); //hago una copia, no puede ser por referencia
                this.tablaSimbolos.put("-" + lexema, atributos);
                this.agregarAtributoLexema("-" + lexema, "Contador", 0);
            }
            tablaSimbolos.get("-" + lexema).put("Contador", ((int) tablaSimbolos.get("-" + lexema).get("Contador")) + 1);
            //ahora actualizo el valor positivo en la TS
            tablaSimbolos.get(lexema).put("Contador", ((int) tablaSimbolos.get(lexema).get("Contador") - 1));
            if((int)tablaSimbolos.get(lexema).get("Contador") == 0) { //si el positivo tiene 0 es porque todos sus usos son negativos
                this.tablaSimbolos.remove(lexema);
            }
            return true;
        }

        public int getContadorFila() {
            return this.contadorFila;
        }

        public void agregarWarning(String string) {
            if(this.warnings == null) {
                this.warnings = new ArrayList<String>();
            }
            warnings.add("Linea: "+ (contadorFila+1) + " - Columna: " + (this.contadorColumna - lexema.length()) + " - " + string);
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
                /*0*/{1,1,5,11,5,-2,-1,-1,-1,-1,2,-1,3,4,12,-2,13,-2,0,0,0,-1},
                /*1*/{1,1,1,1,1,1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                /*2*/{-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-1,-2,-2,-2,-2,-2,-2,-2,-2,-2,-1},
                /*3*/{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                /*4*/{-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-1,-2,-2,-2,-2,-2,-2,-2,-2,-2,-1},
                /*5*/{-1,-1,5,5,5,-1,-1,-1,-1,6,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                /*6*/{-2,-2,7,7,7,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-1},
                /*7*/{-1,8,7,7,7,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                /*8*/{-2,-2,10,10,10,-2,9,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-1},
                /*9*/{-2,-2,10,10,10,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,-1},
                /*10*/{-1,-1,10,10,10,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                /*11*/{-1,-1,11,11,5,-1,-1,-1,-1,6,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                /*12*/{12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,-1,12,12,-2,12,12,-1},
                /*13*/{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,14,-1,-1,-1,-1},
                /*14*/{14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,15,14,14,14,-1},
                /*15*/{14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,0,15,14,14,14,-1}
        };

        //inicializacion acciones semanticas
        AccionSemantica as1 = new AccionSemantica.AccionSemantica1();
        AccionSemantica as2 = new AccionSemantica.AccionSemantica2();
        AccionSemantica as3 = new AccionSemantica.AccionSemantica3();
        AccionSemantica as4 = new AccionSemantica.AccionSemantica4();
        AccionSemantica as5 = new AccionSemantica.AccionSemantica5();
        AccionSemantica as6 = new AccionSemantica.AccionSemantica6();
        AccionSemantica as7 = new AccionSemantica.AccionSemantica7();
        AccionSemantica ERROR = new AccionSemantica.AccionSemanticaError();

        matrizAccionesSemanticas = new AccionSemantica[][] {
                /*0*/{as1, as1, as1, as1, as1, ERROR, as1, as1, as1, ERROR, as1, as1, as1, as1, as1, ERROR, as1, ERROR, as10, as10, as10,as1},
                /*1*/{as2, as2, as2, as2, as2, as2, as3, as3, as3, as3, as3, as3, as3, as3, as3, as3, as3, as3, as3, as4, as3,as1},
                /*2*/{ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, as2, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR,as1},
                /*3*/{as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as2, as9, as9, as9, as9, as9, as9, as9, as9, as9,as1},
                /*4*/{ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, as2, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR,as1},
                /*5*/{as5, as5, as2, as2, as2, as5, as5, as5, as5, as2, as5, as5, as5, as5, as5, as5, as5, as5, as5, as5, as5, as1},
                /*6*/{ERROR, ERROR, as2, as2, as2, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, as1},
                /*7*/{as6, as2, as2, as2, as2, as6, as6, as6, as6, as6, as6, as6, as6, as6, as6, as6, as6, as6, as6, as6, as6, as1},
                /*8*/{ERROR, ERROR, as2, as2, as2, ERROR, as2, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, as1},
                /*9*/{ERROR, ERROR, as2, as2, as2, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR, ERROR,as1},
                /*10*/{as6, as6, as2, as2, as2, as6, as6, as6, as6, as6, as6, as6, as6, as6, as6, as6, as6, as6, as6, as6, as6,as1},
                /*11*/{as7, as7, as2, as2, as13, as7, as7, as7, as7, as12, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7, as7,as1},
                /*12*/{as2, as2, as2, as2, as2, as2, as2, as2, as2, as2, as2, as2, as2, as2, as2, as8, as2, as2, ERROR, as2, as2, as1},
                /*13*/{as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as9, as2, as9, as9, as9, as1},
                /*14*/{as2, as2, as2, as2, as2, as2, as2, as2, as2, as2, as2, as2, as2, as2, as2, as2, as2, as2, as2, as2, as2, as1},
                /*15*/{as2, as2, as2, as2, as2, as2, as2, as2, as2, as2, as2, as2, as2, as2, as2, as2, as10, as2, as2, as2, as2, as1}
        };

    }
    //mapeo de las columnas de las matrices a un entero asignado asi despues nos podemos mover con estos valores en las matrices
    columnaMatrices = new HashMap<Character, Integer>();
    char[] letras = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','t','u','v','w','x','y','z'};
		for(int i=0; i<letras.length; i++) {
        columnaMatrices.put(letras[i], 0);
        columnaMatrices.put(Character.toUpperCase(letras[i]), 0);
    }

		columnaMatrices.put('s', 1);
		columnaMatrices.put('S', 1);

    char[] digitos = {'1','2','3','4','5','6','7'};
		for(int i=0; i<digitos.length; i++) {
        columnaMatrices.put(digitos[i], 2);
    }

		columnaMatrices.put('0', 3);
		columnaMatrices.put('8', 4);
		columnaMatrices.put('9', 4);
		columnaMatrices.put('_', 5);
		columnaMatrices.put('+', 6);
		columnaMatrices.put('-', 6);
		columnaMatrices.put('*', 7);
		columnaMatrices.put('/', 16);
		columnaMatrices.put(':', 10);
		columnaMatrices.put('=', 11);
		columnaMatrices.put('<', 12);
		columnaMatrices.put('>', 12);
		columnaMatrices.put('!', 13);
		columnaMatrices.put('.', 9);
		columnaMatrices.put('#', 17);
		columnaMatrices.put('{', 14);
		columnaMatrices.put('}', 15);
		columnaMatrices.put('(', 8);
		columnaMatrices.put(')', 8);
		columnaMatrices.put('[', 8);
		columnaMatrices.put(']', 8);
		columnaMatrices.put(',', 8);
		columnaMatrices.put(';', 8);
		columnaMatrices.put(' ', 20);
		columnaMatrices.put('	', 20);
		columnaMatrices.put('\n', 18);
		columnaMatrices.put('$', 21);
		columnaMatrices.put('@', 19);

}

/*public class AnalizadorLexico {

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
    private AccionSemantica[][] matrizAccionSemantica;
    private HashMap<Character, Integer> columnaMatrices; //el caracter que se asocia a cada columna de las matrices

    private int[][] matrizTransicionEstados;
    private AccionSemantica[][] matrizAccionSemantica;
    public static HashMap<String, ParametrosToken> tablaSimbolos = new HashMap<>();
    private BufferedReader bufferedReader;
    private String linea;
    private static AnalizadorLexico instance = null;
    private String claveTablaSimbolos = null;

    public static int nroLinea = 1;
    public static int indiceCaracter;
    public static int estadoActual;
    public static int cantidadConstantes = 1;
    public static ArrayList<String> errores = new ArrayList<>();
    public static ArrayList<String> warnings = new ArrayList<>();
    private ArrayList<ConjuntoSimbolos> columnasMatrizTransicionEstados = new ArrayList<>();

    private AnalizadorLexico(String archivo) {
        matrizTransicionEstados = new int[14][17];
        matrizAccionSemantica = new AccionSemantica[14][17];
        try {
            this.bufferedReader = new BufferedReader(new FileReader(archivo));
            linea = bufferedReader.readLine();
            if (linea != null) {
                cargaDeMatrices();
                linea = linea + "\n";
                nroLinea = 1;
                cargarColumnaMatrizTransicionEstados();
                cargaDeMatrices();
                cargaDeTablaSimbolos();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void getSiguienteToker() {

    }

    public static AnalizadorLexico getInstance(String archivo) {
        if (instance == null)
            instance = new AnalizadorLexico(archivo);
        return instance;
    }

    // carga de matrices
    public void cargaDeMatrices() throws IOException {
        cargarMatrizAccionSemantica(""); // nombre archivo accion semantica
        cargarMatrizTransicionEstados(""); // nombre archivo transicion de estado
    }

    // Metodo que convierte un identificador de acción semántica en una instancia de la clase correspondiente
    private AccionSemantica obtenerAccionSemantica(String identificador) {
        switch (identificador) {
            case "AS1":
                return new AccionSemantica1();
            case "AS2":
                return new AccionSemantica2();
            case "AS3":
                return new AccionSemantica3();
            case "AS4":
                return new AccionSemantica4();
            case "AS5":
                return new AccionSemantica5();
            case "AS6":
                return new AccionSemantica6();
            case "AS7":
                return new AccionSemantica7();
            case "AS8":
                return new AccionSemantica8();
            case "AS9":
                return new AccionSemantica9();
            case "ASE":
                return new AccionSemanticaError();
            case "NULL":
                return null; // No hay acción semántica asociada
            default:
                throw new IllegalArgumentException("Identificador de acción semántica no reconocido: " + identificador);
        }
    }

    // Cargar la matriz de acciones semánticas desde un archivo
    private void cargarMatrizAccionSemantica(String archivo) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            int fila = 0;
            while ((linea = br.readLine()) != null && fila < 14) {
                String[] acciones = linea.split(" "); // agarramos toda la linea separada por espacios y guarda cada valor en una celda del arreglo
                for (int columna = 0; columna < acciones.length && columna < 17; columna++) { // analizamos toda la fila y  cuando termina suma fila y volvemos al linea.split
                    matrizAccionSemantica[fila][columna] = obtenerAccionSemantica(acciones[columna]);
                }
                fila++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarMatrizTransicionEstados(String archivo) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            int fila = 0;
            while ((linea = br.readLine()) != null && fila < 14) {
                String[] estados = linea.split(" "); // agarramos toda la linea separada por espacios y guarda cada valor en una celda del arreglo
                for (int columna = 0; columna < estados.length && columna < 17; columna++) { // analizamos toda la fila y  cuando termina suma fila y volvemos al linea.split
                    matrizTransicionEstados[fila][columna] = Integer.parseInt(estados[columna]);
                }
                fila++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarColumnaMatrizTransicionEstados() {
        columnasMatrizTransicionEstados.add(new ConjuntoMas());
        columnasMatrizTransicionEstados.add(new ConjuntoMenos());
        columnasMatrizTransicionEstados.add(new ConjuntoMayor());
        columnasMatrizTransicionEstados.add(new ConjuntoDosPuntos());
        columnasMatrizTransicionEstados.add(new ConjuntoMayorMenor());
        columnasMatrizTransicionEstados.add(new ConjuntoIgual());
        columnasMatrizTransicionEstados.add(new ConjuntoLetrasSinF());
        columnasMatrizTransicionEstados.add(new ConjuntoLetraF());
        columnasMatrizTransicionEstados.add(new ConjuntoAmpersand());
        columnasMatrizTransicionEstados.add(new ConjuntoDigito());
        columnasMatrizTransicionEstados.add(new ConjuntoPunto());
        columnasMatrizTransicionEstados.add(new ConjuntoArroba());
        columnasMatrizTransicionEstados.add(new ConjuntoExclamacion());
        columnasMatrizTransicionEstados.add(new ConjuntoPorcentaje());
        columnasMatrizTransicionEstados.add(new ConjuntoSaltoLinea());
        columnasMatrizTransicionEstados.add(new ConjuntoBlancoTAB());
        columnasMatrizTransicionEstados.add(new ConjuntoSignos());
    }
}*/