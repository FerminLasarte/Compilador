import java.math.BigDecimal;

public abstract class AccionSemantica {
    public abstract String aplicarAS(AnalizadorLexico al, char c);

    public static class AccionSemantica1 extends AccionSemantica {
        public String aplicarAS(AnalizadorLexico al, char c) {
            al.inicializarLexema();
            al.agregarCaracterLexema(c);
            return null;
        }
    }

    public static class AccionSemantica2 extends AccionSemantica {
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
            if (al.isAllLowerCase(lexemaActual)) {
                if (al.esPalabraReservada(lexemaActual)) {
                    return "ID";
                } else {
                    al.agregarError("El identificador '" + lexemaActual + "' es invalido. Debe comenzar con mayuscula o ser una palabra reservada en minusculas.");
                    return "ERROR";
                }
            }
            char primerChar = lexemaActual.charAt(0);
            if (Character.isUpperCase(primerChar)) {
                for (int i = 1; i < lexemaActual.length(); i++) {
                    char ch = lexemaActual.charAt(i);
                    if (!(Character.isUpperCase(ch) && Character.isLetter(ch)) && !Character.isDigit(ch) && ch != '%') {
                        al.agregarError("Identificador '" + lexemaActual + "' contiene un caracter invalido ('" + ch + "'). Solo se permiten letras mayusculas, digitos y '%'.");
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
            al.agregarError("Identificador '" + lexemaActual + "' mal formado. Debe comenzar con mayuscula o ser una palabra reservada en minusculas.");
            return "ERROR";
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
                    al.agregarLexemaTS(lexemaConSufijo);
                    al.agregarAtributoLexema(lexemaConSufijo, "Tipo", "uint");
                    return "CTE";
                } else {
                    al.agregarError("Constante uint fuera del rango permitido (0 a 65535): " + soloEnteros);
                    return "ERROR";
                }
            } catch (NumberFormatException e) {
                al.agregarError("Formato de número inválido para constante uint: " + soloEnteros);
                return "CTE";
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
                BigDecimal cero = BigDecimal.ZERO;

                if (bd.compareTo(cero) == 0) {
                    return "CTE";
                }

                if (bd.compareTo(limiteSuperiorPositivo) > 0) {
                    String nuevoLexema = "3.40282347F+38";
                    al.agregarWarning("Constante flotante fuera de rango (overflow). El valor " + lexemaActual + " fue truncado a " + nuevoLexema);
                    al.setLexema(nuevoLexema);
                    al.agregarLexemaTS(nuevoLexema);
                    al.agregarAtributoLexema(nuevoLexema, "Tipo", "float");
                    return "CTE";
                }

                if (bd.compareTo(limiteInferiorPositivo) < 0) {
                    String nuevoLexema = "1.17549435F-38";
                    al.agregarWarning("Constante flotante fuera de rango (underflow). El valor " + lexemaActual + " fue truncado a " + nuevoLexema);
                    al.setLexema(nuevoLexema);
                    al.agregarLexemaTS(nuevoLexema);
                    al.agregarAtributoLexema(nuevoLexema, "Tipo", "float");
                    return "CTE";
                }

                al.agregarLexemaTS(lexemaActual);
                al.agregarAtributoLexema(lexemaActual, "Tipo", "float");
                return "CTE";

            } catch (NumberFormatException e) {
                al.agregarError("Formato inválido de constante flotante: " + lexemaActual);
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

    // --- NUEVAS ACCIONES PARA CORRECCION DE ERRORES ---

    // Maneja casos como ':+', ': ', reemplazando por ':='
    public static class AccionSemanticaCorreccion extends AccionSemantica {
        public String aplicarAS(AnalizadorLexico al, char c) {
            // Lógica corregida:
            // 1. Si es letra o dígito, devolvemos el caracter (disminuirContador) y retornamos ':' normal.
            if (Character.isLetterOrDigit(c)) {
                al.disminuirContador();
                return ":";
            }

            // 2. Si NO es letra o dígito, asumimos que es basura/error (ej: espacio, ')', '+').
            //    NO disminuimos el contador -> CONSUMIMOS el caracter inválido.
            al.agregarWarning("Caracter invalido '" + c + "' despues de ':'. Se asume ':=' y se continua.");
            al.setLexema(":=");
            return ":=";
        }
    }

    // Maneja casos como ':==', consumiendo el extra '=' y reemplazando por ':='
    public static class AccionSemanticaCorreccionExtra extends AccionSemantica {
        public String aplicarAS(AnalizadorLexico al, char c) {
            // Aquí NO disminuimos contador porque queremos "comer" el '=' extra
            al.agregarWarning("Caracter invalido '" + c + "' detectado en asignacion (posible ':=='). Se asume ':='.");
            al.setLexema(":=");
            return ":=";
        }
    }

    // Finaliza la asignacion normal ':=', devolviendo el puntero (lookahead)
    public static class AccionSemanticaFinalizar extends AccionSemantica {
        public String aplicarAS(AnalizadorLexico al, char c) {
            al.disminuirContador(); // Devolvemos el caracter leido (ej. espacio o digito)
            return ":=";
        }
    }
}