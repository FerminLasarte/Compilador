package analizadorlexico.accionessemanticas;
import analizadorlexico.AtributosToken;
import analizadorlexico.TipoToken;
import analizadorlexico.Token;
import analizadorlexico.AnalizadorLexico;

public class AccionSemantica4 extends AccionSemantica {
    @Override
    public void ejecutar(Token token, char c) {
        AnalizadorLexico.indice_caracter_leer--;

        AtributosToken atributosToken = AnalizadorLexico.tablaSimbolos.get(token.getLexema());
        if (atributosToken != null) { //Esta en la TS
            token.setId(atributosToken.getToken());
            atributosToken.incrementarCantidad();
        } else {
            try {
                int numero_entero = Integer.parseInt(token.getLexema());

                // Verificar que el valor esté en el rango 0 a 2^16-1 (0 a 65535)
                if (numero_entero < 0 || numero_entero > 65535) {
                    AnalizadorLexico.errores_y_warnings.add("Linea " + AnalizadorLexico.numero_linea +
                            " / Posicion " + (AnalizadorLexico.indice_caracter_leer - token.getLexema().length()) +
                            " - ERROR: Constante entera sin signo fuera de rango (0 a 65535)");
                } else { //Se agrega a la TS
                    atributosToken = new AtributosToken(1, TipoToken.CTE_ENTERO_SINSIGNO);
                    /*
                    atributosToken.setUso(TiposDeUso.constantUint);
                    atributosToken.setValor((double) numero_entero);
                    atributosToken.setNombre_var("cte_" + AnalizadorLexico.cant_constantes);
                    */
                    AnalizadorLexico.cant_constantes++;
                    AnalizadorLexico.tablaSimbolos.put(token.getLexema(), atributosToken);
                    token.setId(atributosToken.getToken());
                }
            } catch (NumberFormatException e) {
                // Si el número es demasiado grande para int, definitivamente está fuera del rango uint16
                AnalizadorLexico.errores_y_warnings.add("Linea " + AnalizadorLexico.numero_linea +
                        " / Posicion " + (AnalizadorLexico.indice_caracter_leer - token.getLexema().length()) +
                        " - ERROR: Constante entera sin signo fuera de rango (0 a 65535)");
            }
        }
    }

    @Override
    public String toString() {
        return "AS4";
    }
}