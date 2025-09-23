package analizadorlexico;

import analizadorlexico.accionessemanticas.AccionSemantica;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AnalizadorLexico {

    private int[][] matrizTransicionEstados;
    private AccionSemantica[][] matrizAccionSemantica;
    private TablaSimbolos tablaSimbolos;
    private BufferedReader bufferedReader;
    private String linea;
    private static AnalizadorLexico instance = null;
    private static int nroLinea = 1;
    private static int indiceCaracter;
    private static int estadoActual;
    private static ArrayList<String> erroresWarnings = new ArrayList<>();
    private String claveTablaSimbolos = null;
    private int cantidadConstantes = 1;

    private AnalizadorLexico(String archivo) {
        matrizTransicionEstados = new int[14][17];
        matrizAccionSemantica = new AccionSemantica[14][17];
        try {
            this.bufferedReader = new BufferedReader(new FileReader(archivo));
            linea = bufferedReader.readLine();
            if (linea != null) {
                cargaDeMatrices();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static AnalizadorLexico getInstance(String archivo) {
        if (instance == null)
            instance = new AnalizadorLexico(archivo);
        return instance;
    }

    // carga de matrices
    public void cargaDeMatrices() throws IOException {
        cargarMatrizAccionSemantica(); // nombre archivo accion semantica
        cargarMatrizTransicionEstados(); // nombre archivo transicion de estado
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
                    matriz_acciones_semanticas[fila][columna] = obtenerAccionSemantica(acciones[columna]);
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
                    matriz_acciones_semanticas[fila][columna] = Integer.parseInt(estados[columna]);
                }
                fila++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public TablaSimbolos getTablaSimbolos() {
        return tablaSimbolos;
    }


}
