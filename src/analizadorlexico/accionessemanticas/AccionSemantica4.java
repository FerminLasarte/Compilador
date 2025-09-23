package analizadorlexico.accionessemanticas;
import analizadorlexico.ParametrosToken;
import analizadorlexico.TipoToken;
import analizadorlexico.Token;
import analizadorlexico.AnalizadorLexico;

public class AccionSemantica4 extends AccionSemantica {
    @Override
    public void ejecutar(Token token, char c) {
        AnalizadorLexico.indiceCaracter--;

        ParametrosToken parametrosToken = AnalizadorLexico.tablaSimbolos.get(token.getLexema());
        if (parametrosToken != null) { //Esta en la TS
            token.setId(parametrosToken.getToken());
            parametrosToken.incrementarCantidadTokens();
        } else {
            try {
                int numero_entero = Integer.parseInt(token.getLexema());

                // Verificar que el valor esté en el rango 0 a 2^16-1 (0 a 65535)
                if (numero_entero < 0 || numero_entero > 65535) {
                    AnalizadorLexico.erroresWarnings.add("Linea " + AnalizadorLexico.nroLinea +
                            " / Posicion " + (AnalizadorLexico.indiceCaracter - token.getLexema().length()) +
                            " - ERROR: Constante entera sin signo fuera de rango (0 a 65535)");
                } else { //Se agrega a la TS
                    parametrosToken = new ParametrosToken(1, TipoToken.CTE_ENTERO_SINSIGNO);
                    /*
                    atributosToken.setUso(TiposDeUso.constantUint);
                    atributosToken.setValor((double) numero_entero);
                    atributosToken.setNombre_var("cte_" + AnalizadorLexico.cant_constantes);
                    */
                    AnalizadorLexico.cantidadConstantes++;
                    AnalizadorLexico.tablaSimbolos.put(token.getLexema(), parametrosToken);
                    token.setId(parametrosToken.getToken());
                }
            } catch (NumberFormatException e) {
                // Si el número es demasiado grande para int, definitivamente está fuera del rango uint16
                AnalizadorLexico.erroresWarnings.add("Linea " + AnalizadorLexico.nroLinea +
                        " / Posicion " + (AnalizadorLexico.indiceCaracter - token.getLexema().length()) +
                        " - ERROR: Constante entera sin signo fuera de rango (0 a 65535)");
            }
        }
    }

    @Override
    public String toString() {
        return "AS4";
    }
}