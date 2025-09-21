package analizadorlexico.accionessemanticas;
import analizadorlexico.Token;

public class AccionSemanticaError extends AccionSemantica {

    @Override
    public void ejecutar(Token token, char c) {
        switch (AnalizadorLexico.estado_actual) {
            case 0:
                AnalizadorLexico.errores_y_warnings.add("Linea " + AnalizadorLexico.numero_linea +
                        " / Posicion " + (AnalizadorLexico.indice_caracter_leer - 1) +
                        " - ERROR: '" + c + "' no es un caracter valido dentro del lenguaje.");
                break;
            case 2:
                AnalizadorLexico.errores_y_warnings.add("Linea " + AnalizadorLexico.numero_linea +
                        " / Posicion " + (AnalizadorLexico.indice_caracter_leer - 1) +
                        " - ERROR: Formato de asignacion invalido. Luego de dos puntos (:), debe seguir un igual (=).");
                break;
            case 11:
                AnalizadorLexico.errores_y_warnings.add("Linea " + AnalizadorLexico.numero_linea +
                        " / Posicion " + (AnalizadorLexico.indice_caracter_leer - 1) +
                        " - ERROR: Formato de constante numerica flotante invalido. Luego del caracter F, debe seguir el signo (+, -).");
                break;
            case 12:
                AnalizadorLexico.errores_y_warnings.add("Linea " + AnalizadorLexico.numero_linea +
                        " / Posicion " + (AnalizadorLexico.indice_caracter_leer - 1) +
                        " - ERROR: Formato de constante numerica flotante invalido. Luego del signo, debe ir el valor numerico del exponente.");
                break;
        }
    }

    @Override
    public String toString() {
        return "ASE";
    }
}