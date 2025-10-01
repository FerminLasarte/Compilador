package analizadorlexico.accionessemanticas;
import analizadorlexico.AnalizadorLexico;

public class AccionSemantica3 extends AccionSemantica{
    @Override
    public void ejecutar(Token token, char c) {
        AnalizadorLexico.indiceCaracter--;

        ParametrosToken parametrosToken;
        if(TipoToken.esReservada(token.getLexema())) {
            //Si es palabra reservada (sólo minuscula) aumenta uno la cantidad
            parametrosToken = AnalizadorLexico.tablaSimbolos.get(token.getLexema());
            parametrosToken.incrementarCantidadTokens();
            token.setId(parametrosToken.getToken());
        } else { //Es identificador
            if (token.getLexema().length() > 20) { //Se recorta si excede los 20 caracteres
                String identificador_cortado = token.getLexema().substring(0, 20);
                AnalizadorLexico.erroresWarnings.add("Linea " + AnalizadorLexico.nroLinea +
                        " / Posicion " + (AnalizadorLexico.indiceCaracter - token.getLexema().length()) +
                        " - WARNING: identificador muy largo, se considera solo: " + identificador_cortado);
                token.setLexema(identificador_cortado);
            }
            parametrosToken = AnalizadorLexico.tablaSimbolos.get(token.getLexema());
            if (parametrosToken != null) { //Esta en la TS
                token.setId(parametrosToken.getToken());
                parametrosToken.incrementarCantidadTokens();
            } else {
                parametrosToken = new ParametrosToken(1, TipoToken.IDENTIFICADOR);
                AnalizadorLexico.tablaSimbolos.put(token.getLexema(), parametrosToken);
            }
            token.setId(parametrosToken.getToken());
        }

    }

    @Override
    public String toString() {
        return "AS3";
    }
}
