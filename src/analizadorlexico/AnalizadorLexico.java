package analizadorlexico;
import analizadorlexico.accionessemanticas.*;
import analizadorlexico.conjuntosimbolos.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
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
}