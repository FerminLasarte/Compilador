package analizadorlexico;
import java.math.BigDecimal;
import java.math.BigInteger;


public abstract class AccionSemantica{
    public abstract String aplicarAS(AnalizadorLexico al, char c);

    public static class AccionSemantica1 extends AccionSemantica{
        //inciar lexema y agregar caracter
        public String aplicarAS(AnalizadorLexico al, char c) {
            al.inicializarLexema();
            al.agregarCaracterLexema(c);
            return null;
        }
    }

    public static class AccionSemantica2 extends AccionSemantica{
        //agregar caracter al lexema
        public String aplicarAS(AnalizadorLexico al, char c) {
            al.agregarCaracterLexema(c);
            return null;
        }
    }

    public static class AccionSemantica3 extends AccionSemantica{
        //para identificadores o palabras reservadas
        public String aplicarAS(AnalizadorLexico al, char c) {
            //Devolver caracter
            al.disminuirContador();
            //buscar en la TS
            if(al.getTablaSimbolos().containsKey(al.getLexema())){
                //si es reservada
                if((boolean)al.getTablaSimbolos().get(al.getLexema()).get("Reservada") == true){
                    return null; //el codigo de getToken se encarga de tomar el token.
                }
                else{
                    return "ID";
                }
            }
            else{
                if (al.getLexema().length() > 20){
                    al.agregarWarning("Identificador mayor a 20 caracteres, se trunco hasta esa cantidad");
                    al.setLexema(al.getLexema().substring(0,20));
                }
                al.agregarLexemaTS(al.getLexema());
                return "ID";
            }
        }
    }

    public static class AccionSemantica4 extends AccionSemantica{ //hay que correguir
        public String aplicarAS(AnalizadorLexico al, char c) {
            String uintConUI = al.getLexema().toString();
            String soloEnteros = uintConUI.substring(0, uintConUI.length() - 2); // elimina los últimos 2 (UI)
            BigDecimal bd = new BigDecimal(soloEnteros);
            BigDecimal limiteSuperior = new BigDecimal("65535"); //luego debo chequear que si es positivo debe ser un valor menos, es decir, se puede hasta 32767 positivo
            if(bd.compareTo(limiteSuperior) <= 0 || bd.compareTo(BigDecimal.ZERO) <= 0){
                if(al.getTablaSimbolos().containsKey(al.getLexema())){
                    al.getTablaSimbolos().get(uintConUI).put("Contador", (int) al.getTablaSimbolos().get(uintConUI).get("Contador") + 1);
                    return "CTE";
                }
                al.agregarLexemaTS(al.getLexema());
                al.agregarAtributoLexema(uintConUI, "Tipo", "uint");
                al.agregarAtributoLexema(uintConUI, "Contador", 1);
                al.agregarAtributoLexema(uintConUI, "Uso", "Constante");
                return "CTE";
            }
            al.agregarError(" Constante entera sin signo fuera de rango (0 a 65535)");
            return "ERROR";
        }
    }

    public static class AccionSemantica5 extends AccionSemantica{
        public String aplicarAS(AnalizadorLexico al, char c) {
            al.disminuirContador();
            return null;
        }
    }

    public static class AccionSemantica6 extends AccionSemantica {
        public String aplicarAS(AnalizadorLexico al, char c) {
            al.disminuirContador();// Retrocede un carácter para no consumir el que disparó la transición
            String valor = al.getLexema().replace('F', 'E');// Reemplazo de 'F' por 'E' (Java/BigDecimal usa notación 'E' para exponentes)
            try {
                BigDecimal bd = new BigDecimal(valor);
                // Límites positivos y negativos para Single (32 bits)
                BigDecimal limiteInferiorPositivo = new BigDecimal("1.17549435E-38");
                BigDecimal limiteSuperiorPositivo = new BigDecimal("3.40282347E+38");
                // Verificación de rango: positivo, negativo o cero
                boolean enRangoPositivo = bd.compareTo(limiteInferiorPositivo) >= 0 && bd.compareTo(limiteSuperiorPositivo) <= 0;
                boolean esCero = bd.compareTo(BigDecimal.ZERO) == 0;
                if (enRangoPositivo || esCero) {// Si ya existe en la tabla, incrementar contador
                    if (al.getTablaSimbolos().containsKey(al.getLexema())) {
                        al.getTablaSimbolos().get(al.getLexema()).put("Contador", (int) al.getTablaSimbolos().get(al.getLexema()).get("Contador") + 1);
                        return "CTE";
                    }
                    // Si no existe, agregarlo con sus atributos
                    al.agregarLexemaTS(al.getLexema());
                    al.agregarAtributoLexema(al.getLexema(), "Tipo", "float");
                    al.agregarAtributoLexema(al.getLexema(), "Contador", 1);
                    al.agregarAtributoLexema(al.getLexema(), "Uso", "Constante");
                    return "CTE";
                }
                // Caso fuera de rango
                al.agregarError("Constante flotante fuera de rango.");
                return "ERROR";
            } catch (NumberFormatException e) {
                // Si el lexema no puede convertirse a número (formato inválido)
                al.agregarError("Formato inválido de constante flotante.");
                return "ERROR";
            }
        }
    }

    public static class AccionSemantica7 extends AccionSemantica {
            public String aplicarAS(AnalizadorLexico al, char c) {
                al.agregarCaracterLexema(c);
                if (al.getTablaSimbolos().containsKey(al.getLexema())) {
                    return "CADENA_MULTILINEA";
                }
                al.agregarLexemaTS(al.getLexema());
                al.agregarAtributoLexema(al.getLexema(), "Uso", "CadenaMultilinea");
                return "CADENA_MULTILINEA";
            }
    }

        public static class AccionSemanticaError extends AccionSemantica {
            public String aplicarAS(AnalizadorLexico al, char c) {
                al.reiniciarLexema();
                al.agregarError("Caracter " + c + " invalido ");
                return "ERROR";
            }
        }
    }
}
