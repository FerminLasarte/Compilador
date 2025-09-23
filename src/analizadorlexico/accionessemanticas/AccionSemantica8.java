package analizadorlexico.accionessemanticas;

import analizadorlexico.ParametrosToken;
import analizadorlexico.TipoToken;
import analizadorlexico.Token;
import analizadorlexico.AnalizadorLexico;

public class AccionSemantica8 extends AccionSemantica {
    @Override
    public void ejecutar(Token token, char c) {
        AnalizadorLexico.indiceCaracter--;

        ParametrosToken parametrosToken = AnalizadorLexico.tablaSimbolos.get(token.getLexema());
        if (parametrosToken != null) {
            token.setId(parametrosToken.getToken());
            parametrosToken.incrementarCantidadTokens();
        } else {
            try {
                int posicion_F = token.getLexema().indexOf('F');
                Double numero_punto_flotante = null;

                if (posicion_F != -1) {
                    // Con exponente
                    String base = token.getLexema().substring(0, posicion_F);
                    String exponente_str = token.getLexema().substring(posicion_F + 1);

                    numero_punto_flotante = Double.parseDouble(base);
                    Double exponente = Double.parseDouble(exponente_str);

                    // La fórmula correcta es: base * 10^exponente
                    numero_punto_flotante = numero_punto_flotante * Math.pow(10, exponente);
                } else {
                    // Sin exponente
                    numero_punto_flotante = Double.parseDouble(token.getLexema());
                }

                Boolean numero_valido = true;
                Double abs_numero = Math.abs(numero_punto_flotante);

                // Verificar rangos para float de 32 bits
                if (numero_punto_flotante != 0.0) {
                    if (!(abs_numero > 1.17549435E-38F && abs_numero < 3.40282347E+38F)) {
                        AnalizadorLexico.erroresWarnings.add("Linea " + AnalizadorLexico.nroLinea +
                                " / Posicion " + (AnalizadorLexico.indiceCaracter - token.getLexema().length()) +
                                " - ERROR: Constante de punto flotante fuera de rango");
                        numero_valido = false;
                    }
                }

                if (numero_valido) {
                    parametrosToken = new ParametrosToken(1, TipoToken.CTE_FLOAT);
                    parametrosToken.setUso(TiposDeUso.constantFloat);
                    parametrosToken.setValor(numero_punto_flotante); // Usar el valor ya calculado
                    parametrosToken.setNombre_var("cte_" + AnalizadorLexico.cantidadConstantes);
                    AnalizadorLexico.cantidadConstantes++;
                    AnalizadorLexico.tablaSimbolos.put(token.getLexema(), parametrosToken);
                    token.setId(parametrosToken.getToken());
                }

            } catch (NumberFormatException e) {
                // Si el formato del número no es válido
                AnalizadorLexico.erroresWarnings.add("Linea " + AnalizadorLexico.nroLinea +
                        " / Posicion " + (AnalizadorLexico.indiceCaracter - token.getLexema().length()) +
                        " - ERROR: Formato inválido para constante de punto flotante");
            }
        }
    }

    @Override
    public String toString() {
        return "AS8";
    }
}
