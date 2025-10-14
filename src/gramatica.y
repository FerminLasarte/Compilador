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
%}

%token ID CTE IF ELSE FLOAT ENDIF RETURN PRINT UINT VAR DO WHILE LAMBDA CADENA_MULTILINEA ASIG_MULTIPLE CR SE LE TOUI ASIG FLECHA MAYOR_IGUAL MENOR_IGUAL DISTINTO

%nonassoc IFX
%nonassoc ELSE

%%

programa : ID '{' sentencias '}'
      {
          salida.add("Linea " + (al.getContadorFila()+1) + ": Programa '" + $1.sval + "' reconocido.");
          al.agregarAtributoLexema($1.sval, "Uso", "Programa");
      }
     | '{' sentencias '}' {erroresSintacticos.add("Linea " + (al.getContadorFila()+1) + ": Error sintactico: Falta el nombre del programa.");}
     | ID sentencias '}' {erroresSintacticos.add("Linea " + (al.getContadorFila()+1) + ": Error sintactico: Falta el delimitador '{' al inicio del programa.");}
     | ID '{' sentencias {erroresSintacticos.add("Linea " + (al.getContadorFila()+1) + ": Error sintactico: Falta el delimitador '}' al final del programa.");}
     ;

sentencias : sentencias sentencia
           | sentencia
           ;

sentencia : sentencia_declarativa
          | sentencia_ejecutable
          | error ';' {erroresSintacticos.add("Linea " + (al.getContadorFila()+1) + ": Error sintactico en la sentencia.");}
          ;

sentencia_declarativa : declaracion_tipica ';'
                      | funcion
                      | declaracion_var ';'
                      ;

declaracion_tipica : tipo lista_variables
            {
                salida.add("Linea " + (al.getContadorFila()+1) + ": Declaracion de variables de tipo '" + $1.sval + "'.");
                listaVariables.clear();
            }
            ;

declaracion_var : VAR asignacion
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

/*
 * REGLAS DE FUNCION CORREGIDAS PARA ELIMINAR AMBIGÜEDAD
 * Se separa en dos casos: retorno unico y retorno multiple.
 */
funcion : tipo ID '(' lista_parametros_formales ')' '{' sentencias '}' /* Funcion con retorno unico */
        {
            salida.add("Linea " + (al.getContadorFila()+1) + ": Declaracion de Funcion '" + $2.sval + "' con retorno simple.");
        }
        | lista_tipos_retorno_multiple ID '(' lista_parametros_formales ')' '{' sentencias '}' /* Funcion con retorno multiple */
        {
            salida.add("Linea " + (al.getContadorFila()+1) + ": Declaracion de Funcion '" + $2.sval + "' con retorno multiple.");
        }
        ;

/* La lista de tipos de retorno ahora debe tener como minimo dos elementos */
lista_tipos_retorno_multiple : tipo ',' tipo
                             | lista_tipos_retorno_multiple ',' tipo
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

asignacion : variable ASIG expresion
           {
               salida.add("Linea " + (al.getContadorFila()+1) + ": Asignacion simple (:=).");
           }
           ;

asignacion_multiple : lista_variables ASIG_MULTIPLE lado_derecho_multiple
                    {
                        salida.add("Linea " + (al.getContadorFila()+1) + ": Asignacion multiple (=).");
                    }
                    ;

lado_derecho_multiple : lista_elementos_restringidos
                      ;

lista_elementos_restringidos : lista_elementos_restringidos ',' factor
                             | factor
                             ;

variable : ID { $$.sval = $1.sval; }
         | ID '.' ID { $$.sval = $1.sval + "." + $3.sval; }
         ;

expresion : expresion '+' termino
          | expresion '-' termino
          | termino
          ;

termino : termino '*' factor
        | termino '/' factor
        | factor
        ;

factor : variable
       | constante
       | invocacion_funcion
       | conversion_explicita
       ;

conversion_explicita : TOUI '(' expresion ')'
                {
                    salida.add("Linea " + (al.getContadorFila()+1) + ": Conversion explicita (toui).");
                }
                ;

invocacion_funcion : ID '(' lista_parametros_reales ')'
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

cuerpo_lambda : sentencias_ejecutables_lista
              |
              ;

sentencias_ejecutables_lista : sentencias_ejecutables_lista sentencia_ejecutable
                             | sentencia_ejecutable
                             ;

constante : CTE
          | '-' CTE
          ;

condicional_if
    : IF '(' condicion ')' bloque_ejecutable ENDIF ';' %prec IFX
    | IF '(' condicion ')' bloque_ejecutable ELSE bloque_ejecutable ENDIF ';'
    ;

condicional_do_while : DO bloque_ejecutable WHILE '(' condicion ')' ';'
                     {
                         salida.add("Linea " + (al.getContadorFila()+1) + ": Sentencia DO-WHILE.");
                     }
                     ;

condicion : expresion simbolo_comparacion expresion
          ;

simbolo_comparacion : MAYOR_IGUAL
                    | MENOR_IGUAL
                    | DISTINTO
                    | '='
                    | '>'
                    | '<'
                    ;

bloque_ejecutable : '{' sentencias '}'
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
        par.yyparse(); // Se ejecuta el analisis sintactico

        // ---- AÑADIR ESTE BLOQUE PARA IMPRIMIR LOS RESULTADOS ----

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
        System.out.println("=======================================================");
    } else {
        System.out.println("Error: Se requiere la ruta del archivo fuente como unico parametro.");
    }
}
