import java.math.BigDecimal;

public abstract class AccionSemantica{
    public abstract String aplicarAS(AnalizadorLexico al, char c);

    public static class AccionSemantica1 extends AccionSemantica{
        public String aplicarAS(AnalizadorLexico al, char c) {
            al.inicializarLexema();
            al.agregarCaracterLexema(c);
            return null;
        }
    }

    public static class AccionSemantica2 extends AccionSemantica{
        public String aplicarAS(AnalizadorLexico al, char c) {
            al.agregarCaracterLexema(c);
            return null;
        }
    }

    public static class AccionSemantica3 extends AccionSemantica {
        @Override
        public String aplicarAS(AnalizadorLexico al, char c) {
            al.disminuirContador();
            String lexemaActual = al.getLexema();
            int lineaActual = al.getContadorFila() + 1;

            char primerChar = lexemaActual.charAt(0);
            if (!Character.isLetter(primerChar) || !Character.isUpperCase(primerChar)) {
                al.agregarError("Identificador '" + lexemaActual + "' debe comenzar con una letra mayuscula.");
                return "ERROR";
            }

            for (int i = 1; i < lexemaActual.length(); i++) {
                char ch = lexemaActual.charAt(i);
                if (!(Character.isUpperCase(ch) && Character.isLetter(ch)) && !Character.isDigit(ch) && ch != '%') {
                    al.agregarError("Identificador '" + lexemaActual + "' contiene un caracter invalido ('" + ch + "'). Solo se permiten letras mayusculas, dígitos y '%'.");
                    return "ERROR";
                }
            }

            if (lexemaActual.length() > 20) {
                String original = lexemaActual;
                lexemaActual = lexemaActual.substring(0, 20);
                al.setLexema(lexemaActual);
                al.agregarWarning("El identificador '" + original + "' fue truncado a 20 caracteres: '" + lexemaActual + "'.");
            }

            return "ID";
        }
    }

    public static class AccionSemantica4 extends AccionSemantica {
        public String aplicarAS(AnalizadorLexico al, char c) {
            al.disminuirContador();
            String lexemaConSufijo = al.getLexema();

            if (!lexemaConSufijo.endsWith("UI")) {
                al.agregarError("Constante mal formada, se esperaba el sufijo 'UI': " + lexemaConSufijo);
                return "ERROR";
            }

            String soloEnteros = lexemaConSufijo.substring(0, lexemaConSufijo.length() - 2);

            try {
                BigDecimal bd = new BigDecimal(soloEnteros);
                BigDecimal limiteSuperior = new BigDecimal("65536"); // 2^16

                if (bd.compareTo(BigDecimal.ZERO) >= 0 && bd.compareTo(limiteSuperior) < 0) {
                    return "CTE";
                } else {
                    al.agregarError("Constante uint fuera del rango permitido (0 a 65535). Valor encontrado: " + soloEnteros);
                    return "ERROR";
                }
            } catch (NumberFormatException e) {
                al.agregarError("Formato de número inválido para constante uint: " + soloEnteros);
                return "ERROR";
            }
        }
    }

    public static class AccionSemantica5 extends AccionSemantica {
        public String aplicarAS(AnalizadorLexico al, char c) {
            al.disminuirContador();
            return null;
        }
    }

    public static class AccionSemantica6 extends AccionSemantica {
        public String aplicarAS(AnalizadorLexico al, char c) {
            al.disminuirContador();
            String lexemaActual = al.getLexema();
            String valor = lexemaActual.replace('F', 'E');

            try {
                BigDecimal bd = new BigDecimal(valor);
                BigDecimal limiteInferiorPositivo = new BigDecimal("1.17549435E-38");
                BigDecimal limiteSuperiorPositivo = new BigDecimal("3.40282347E+38");

                boolean enRangoPositivo = bd.compareTo(limiteInferiorPositivo) >= 0 && bd.compareTo(limiteSuperiorPositivo) <= 0;
                boolean esCero = bd.compareTo(BigDecimal.ZERO) == 0;

                if (enRangoPositivo || esCero) {
                    return "CTE";
                }

                al.agregarError("Constante flotante fuera de rango.");
                return "ERROR";

            } catch (NumberFormatException e) {
                al.agregarError("Formato inválido de constante flotante.");
                return "ERROR";
            }
        }
    }

    public static class AccionSemantica7 extends AccionSemantica {
        public String aplicarAS(AnalizadorLexico al, char c) {
            al.agregarCaracterLexema(c);
            return "CADENA_MULTILINEA";
        }
    }

    public static class AccionSemanticaNull extends AccionSemantica {
        public String aplicarAS(AnalizadorLexico al, char c) {
            al.reiniciarLexema();
            return null;
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