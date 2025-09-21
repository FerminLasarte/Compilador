package analizadorlexico.accionessemanticas;
import analizadorlexico.AnalizadorLexico;
import analizadorlexico.AtributosToken;
import analizadorlexico.TipoToken;
import analizadorlexico.Token;

public class AccionSemantica3 extends AccionSemantica{
    @Override
    public void ejecutar(Token token, char c) {
        AnalizadorLexico.indice_caracter_leer--;

        AtributosToken atributosToken;
        if(TipoToken.esReservada(token.getLexema())) {
            //Si es palabra reservada (sólo minuscula) aumenta uno la cantidad
            atributosToken = AnalizadorLexico.tablaSimbolos.get(token.getLexema());
            atributosToken.incrementarCantidad();
            token.setId(atributosToken.getToken());
        } else { //Es identificador
            if (token.getLexema().length() > 20) { //Se recorta si excede los 20 caracteres
                String identificador_cortado = token.getLexema().substring(0, 20);
                AnalizadorLexico.errores_y_warnings.add("Linea " + AnalizadorLexico.numero_linea +
                        " / Posicion " + (AnalizadorLexico.indice_caracter_leer - token.getLexema().length()) +
                        " - WARNING: identificador muy largo, se considera solo: " + identificador_cortado);
                token.setLexema(identificador_cortado);
            }
            atributosToken = AnalizadorLexico.tablaSimbolos.get(token.getLexema());
            if (atributosToken != null) { //Esta en la TS
                token.setId(atributosToken.getToken());
                atributosToken.incrementarCantidad();
            } else {
                atributosToken = new AtributosToken(1, TipoToken.IDENTIFICADOR);
                AnalizadorLexico.tablaSimbolos.put(token.getLexema(), atributosToken);
            }
            token.setId(atributosToken.getToken());
        }

    }

    @Override
    public String toString() {
        return "AS3";
    }
}
