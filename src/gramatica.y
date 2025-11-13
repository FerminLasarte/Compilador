%{
    import java.io.File;
    import java.io.IOException;
    import java.util.ArrayList;
    import java.io.PrintWriter;
    import java.io.FileWriter;
    import java.io.FileNotFoundException;
    import java.util.HashMap;
    import java.util.Map;
    import java.lang.StringBuilder;
    import java.math.BigDecimal;
%}

%token ID CTE IF ELSE FLOAT ENDIF RETURN PRINT UINT VAR DO WHILE LAMBDA CADENA_MULTILINEA ASIG_MULTIPLE CR SE LE TOUI ASIG FLECHA MAYOR_IGUAL MENOR_IGUAL DISTINTO IGUAL_IGUAL

%nonassoc IFX
%nonassoc ELSE

%%

programa : ID '{' sentencias '}'
      {
          String nombrePrograma = $1.sval;
          Object lineaObj = al.getAtributo(nombrePrograma, "Linea");
          String linea = (lineaObj != null) ? lineaObj.toString() : "?";

          salida.add("Linea " + linea + ": Programa '" + nombrePrograma + "' reconocido.");
          al.agregarAtributoLexema(nombrePrograma, "Uso", "Programa");

      }
     | '{' sentencias '}'
     {
         erroresSintacticos.add("Linea " + (al.getContadorFila()+1) + ": Error sintactico: Falta el nombre del programa.");

     }
     |
     ID sentencias '}'
     {
         erroresSintacticos.add("Linea " + (al.getContadorFila()+1) + ": Error sintactico: Falta el delimitador '{' al inicio del programa.");

     }
     |
     ID '{' sentencias
     {
         erroresSintacticos.add("Linea " + (al.getContadorFila()+1) + ": Error sintactico: Falta el delimitador '}' al final del programa.");
     }
     ;

sentencias : sentencias sentencia
           | sentencia
           ;

sentencia : sentencia_declarativa
          | sentencia_ejecutable
          | error ';'
          ;

// BORRAR DECLARACION TIPICA [DONE]
sentencia_declarativa : funcion
                      | declaracion_var ';'
                      ;

declaracion_var : VAR variable ASIG expresion
                {
                    salida.add("Linea " + (al.getContadorFila()+1) + ": Declaracion por inferencia (var).");
                }
                ;

tipo : UINT { $$.sval = "uint"; }
     | FLOAT { $$.sval = "float"; }
     | LAMBDA { $$.sval = "lambda"; }
     ;

lista_variables : lista_variables ',' variable
                {
                    listaVariables.add($3.sval);
                }
                | variable
                {
                    listaVariables.clear();
                    listaVariables.add($1.sval);
                }
                ;

// REHACER
funcion : tipo ID '(' lista_parametros_formales ')' '{' sentencias '}'
        {
            salida.add("Linea " + (al.getContadorFila()+1) + ": Declaracion de Funcion '" + $2.sval + "' con retorno simple.");
        }
        | lista_tipos_retorno_multiple ID '(' lista_parametros_formales ')' '{' sentencias '}'
        {
            salida.add("Linea " + (al.getContadorFila()+1) + ": Declaracion de Funcion '" + $2.sval + "' con retorno multiple.");
        }
        ;

lista_tipos_retorno_multiple : tipo ',' tipo
                             | lista_tipos_retorno_multiple ',' tipo // A CHEQUEAR
                             ;

lista_parametros_formales : lista_parametros_formales ',' parametro_formal
                          | parametro_formal
                          ;

parametro_formal : sem_pasaje tipo ID
                 | tipo ID
                 ;

sem_pasaje : CR SE
           | CR LE
           ;

sentencia_ejecutable : asignacion ';'
                     | asignacion_multiple ';'
                     | condicional_if
                     | condicional_do_while
                     | salida_pantalla ';'
                     | retorno_funcion
                     ;

// FALTA VAR
asignacion : variable ASIG expresion
           {
               salida.add("Linea " + (al.getContadorFila()+1) + ": Asignacion simple (:=).");
           }
           ;

// CHEQUEO DE CANTIDAD DE TERMINOS DE CADA LADO
asignacion_multiple : lista_variables ASIG_MULTIPLE lado_derecho_multiple
                    {
                        String lineaActual = String.valueOf(al.getContadorFila() + 1);

                        if (contadorLadoDerecho == 1 && $3.ival == 1) {
                            String funcName = $3.sval;
                            salida.add("Linea " + lineaActual + ": Asignacion multiple (funcion '" + funcName + "') reconocida.");
                        } else {
                            if (listaVariables.size() != contadorLadoDerecho) {
                                yyerror("Linea " + lineaActual + ": Error Sintactico: La asignacion multiple (lista) debe tener el mismo numero de elementos a la izquierda (" + listaVariables.size() + ") y a la derecha (" + contadorLadoDerecho + ").");
                            } else {
                                salida.add("Linea " + lineaActual + ": Asignacion multiple (lista) reconocida.");
                            }
                        }
                        contadorLadoDerecho = 0;
                    }
                    ;

lado_derecho_multiple : factor
                      {
                          contadorLadoDerecho = 1;
                          $$.ival = $1.ival;
                          $$.sval = $1.sval;
                      }
                      | lado_derecho_multiple ',' factor
                      {
                          contadorLadoDerecho++;
                          $$.ival = 0;
                      }
                      ;

// CHEQUEAR
variable : ID '.' ID { $$.sval = $1.sval + "." + $3.sval; }
         | ID { $$.sval = $1.sval; }
         ;

expresion : expresion '+' termino
          | expresion '-' termino
          | termino
          ;

termino : termino '*' factor
        | termino '/' factor
        | factor
        ;

factor : factor_no_funcion
       {
           $$.ival = 0;
       }
       | invocacion_funcion
       {
           $$.ival = 1;
           $$.sval = $1.sval;
       }
       ;

factor_no_funcion : variable
                  | constante
                  | conversion_explicita
                  ;

conversion_explicita : TOUI '(' expresion ')'
                {
                    salida.add("Linea " + (al.getContadorFila()+1) + ": Conversion explicita (toui).");
                }
                ;

invocacion_funcion : ID '(' lista_parametros_reales ')'
                   { $$.sval = $1.sval; }
                   ;

lista_parametros_reales : lista_parametros_reales ',' parametro_real
                        | parametro_real
                        ;

parametro_real : parametro_simple FLECHA ID
               ;

parametro_simple : expresion
                 | lambda_expresion
                 ;

lambda_expresion : '(' tipo ID ')' '{' cuerpo_lambda '}'
                 ;

// CHEQUEAR CUERPO VACIO
cuerpo_lambda : sentencias_ejecutables_lista
              |
              ;

sentencias_ejecutables_lista : sentencias_ejecutables_lista sentencia_ejecutable
                             | sentencia_ejecutable
                             ;

constante : CTE
          | '-' CTE
            {
                String lexemaPositivo = $2.sval;
                String lexemaNegativo = "-" + lexemaPositivo;

                if (al.getTablaSimbolos().containsKey(lexemaPositivo)) {
                    String tipo = (String) al.getAtributo(lexemaPositivo, "Tipo");

                    if (tipo != null) {
                        if (tipo.equals("uint")) {
                            erroresSemanticos.add("Linea " + (al.getContadorFila()+1) + ": Error Semantico. El tipo 'uint' no puede ser negativo. Valor: " + lexemaNegativo);

                            int contador = (int) al.getAtributo(lexemaPositivo, "Contador");

                            if (contador > 1) {
                                al.agregarAtributoLexema(lexemaPositivo, "Contador", contador - 1);
                            } else if (contador == 1) {
                                al.eliminarLexemaTS(lexemaPositivo);
                            }
                        } else if (tipo.equals("float")) {
                            al.reemplazarEnTS(lexemaPositivo, lexemaNegativo);
                        }
                    }
                }
            }
          ;

// CHEQUEAR PORQUE NO IMPRIME TODO
condicional_if : IF '(' condicion ')' bloque_ejecutable ENDIF ';' %prec IFX // CHEQUEAR PREC // RECONOCER SENTENCIA IF
               | IF '(' condicion ')' bloque_ejecutable ELSE bloque_ejecutable ENDIF ';'
               ;

// AGREGAR MAS ERRORES
condicional_do_while: DO bloque_ejecutable WHILE '(' condicion ')' ';'
                    {
                        Object lineaObj = al.getAtributo("do", "Linea");
                        String linea = (lineaObj != null) ? lineaObj.toString() : "?";
                        salida.add("Linea " + linea + ": Sentencia DO-WHILE reconocida.");
                    }
                    |
                    DO bloque_ejecutable WHILE '(' condicion ')'
                    {
                        Object lineaObj = al.getAtributo("do", "Linea");
                        String linea = (lineaObj != null) ? lineaObj.toString() : "?";
                        yyerror("Linea " + linea + ": Error Sintactico. Falta punto y coma ';' al final de la sentencia DO-WHILE.");
                    }
                    ;

condicion : expresion simbolo_comparacion expresion
          ;

simbolo_comparacion : MAYOR_IGUAL
                    | MENOR_IGUAL
                    | DISTINTO
                    | IGUAL_IGUAL
                    | '>'
                    | '<'
                    ;

bloque_ejecutable : '{' sentencias_ejecutables_lista '}'
                  | sentencia_ejecutable // BORRAR ?
                  | '{' error '}' // CHEQUEAR
                  ;

salida_pantalla : PRINT '(' CADENA_MULTILINEA ')'
                {
                    salida.add("Linea " + (al.getContadorFila()+1) + ": PRINT con cadena multilinea.");
                }
                | PRINT '(' expresion ')'
                {
                    salida.add("Linea " + (al.getContadorFila()+1) + ": PRINT con expresion.");
                }
                ;

retorno_funcion : RETURN '(' lista_expresiones ')' ';'
                {
                    salida.add("Linea " + (al.getContadorFila()+1) + ": Sentencia RETURN.");
                }
                ;

lista_expresiones : lista_expresiones ',' expresion
                  | expresion
                  ;
%%

static AnalizadorLexico al;
ArrayList<String> erroresSintacticos = new ArrayList<String>();
ArrayList<String> erroresSemanticos = new ArrayList<String>();
ArrayList<String> salida = new ArrayList<String>();
ArrayList<String> listaVariables = new ArrayList<String>();
int contadorLadoDerecho = 0;

int yylex() {
    int token = al.yylex();
    String lexema = al.getLexema();
    if (token == ID || token == CTE || token == CADENA_MULTILINEA) {
        yylval = new ParserVal(lexema);
    } else {
        yylval = new ParserVal(token);
    }
    return token;
}

public void yyerror(String e) {
   erroresSintacticos.add("Linea " + (al.getContadorFila() + 1) + ": Error de sintaxis. Verifique la estructura del codigo.");
}

public static void main(String args[]){
    if(args.length == 1) {
        al = new AnalizadorLexico(args[0]);
        Parser par = new Parser(false);
        par.yyparse();

        System.out.println("\n=======================================================");
        System.out.println("## ESTRUCTURAS SINTACTICAS RECONOCIDAS ##");
        System.out.println("=======================================================");
        if (par.salida.isEmpty()) {
            System.out.println("No se reconocieron estructuras sintacticas validas.");
        } else {
            for (String s : par.salida) {
                System.out.println(s);
            }
        }

        System.out.println("\n=======================================================");
        System.out.println("## ERRORES SINTACTICOS DETECTADOS ##");
        System.out.println("=======================================================");
        if (par.erroresSintacticos.isEmpty()) {
            System.out.println("No se encontraron errores sintacticos.");
        } else {
            for (String s : par.erroresSintacticos) {
                System.out.println(s);
            }
        }

        System.out.println("\n=======================================================");
        System.out.println("## ERRORES SEMANTICOS DETECTADOS ##");
        System.out.println("=======================================================");
        if (par.erroresSemanticos.isEmpty()) {
            System.out.println("No se encontraron errores semanticos.");
        } else {
            for (String s : par.erroresSemanticos) {
                System.out.println(s);
            }
        }

        System.out.println("\n=======================================================");
        System.out.println("## CONTENIDOS DE LA TABLA DE SIMBOLOS ##");
        System.out.println("=======================================================");
        HashMap<String, HashMap<String, Object>> ts = al.getTablaSimbolos();

        if (ts.isEmpty()) {
            System.out.println("La tabla de simbolos esta vacia.");
        } else {
            String formatString = "| %-30s | %-12s | %-18s | %-10s | %-10s |%n";

            System.out.printf(formatString, "Lexema", "Reservada", "Uso", "Tipo", "Contador");
            System.out.println("|--------------------------------|--------------|--------------------|------------|------------|");

            for (Map.Entry<String, HashMap<String, Object>> entry : ts.entrySet()) {
               String lexema = entry.getKey();
               HashMap<String, Object> atributos = entry.getValue();

               Object reservada = atributos.get("Reservada");
               Object uso = atributos.get("Uso");
               Object tipo = atributos.get("Tipo");
               Object contador = atributos.get("Contador");

               System.out.printf(formatString,
                    lexema,
                    (reservada != null) ? reservada.toString() : "null",
                    (uso != null) ? uso.toString() : "null",
                    (tipo != null) ? tipo.toString() : "null",
                    (contador != null) ? contador.toString() : "0"
                );
            }
        }

        System.out.println("=======================================================");
    } else {
        System.out.println("Error: Se requiere la ruta del archivo fuente como unico parametro.");
    }
}