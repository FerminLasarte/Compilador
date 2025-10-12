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

// Definición de tokens adaptada a la consigna del TP2 y tus temas.
%token ID CTE IF ELSE ENDIF RETURN PRINT UINT FLOAT VAR DO WHILE LAMBDA CADENA_MULTILINEA ERROR CR SE LE TOUI

%%

programa : ID '{' sentencias '}'
      {
          salida.add("Linea " + (al.getContadorFila()+1) + ": " + "Programa '" + $1.sval + "' reconocido.");
          al.agregarAtributoLexema($1.sval, "Uso", "Programa");
          listaArboles.add(new Nodo($1.sval, $3.obj));
      }
     //ERRORES
     | '{' sentencias '}' {erroresSintacticos.add("Linea " + (al.getContadorFila()+1) + ": " + "Falta el nombre del programa.");}
     | ID sentencias '}' {erroresSintacticos.add("Linea " + (al.getContadorFila()+1) + ": " + "Falta el delimitador '{' al inicio del programa.");}
     | ID '{' sentencias {erroresSintacticos.add("Linea " + (al.getContadorFila()+1) + ": " + "Falta el delimitador '}' al final del programa.");}
     ;

/*
 * REGLA DE SENTENCIAS:
 * Permite una secuencia de sentencias declarativas o ejecutables.
 * Cada sentencia debe terminar en ';'
 */
sentencias : sentencias sentencia
           {
               // Agrupa las sentencias en el árbol sintáctico.
               $$.obj = new Nodo("S", $1.obj, $2.obj);
           }
           | sentencia
           {
               $$.obj = $1.obj;
           }
           ;

sentencia : sentencia_declarativa
          | sentencia_ejecutable
          //ERRORES
          | error ';' {erroresSintacticos.add("Linea " + (al.getContadorFila()+1) + ": " + "Error de sintaxis en la sentencia.");}
          ;

/*
 * SENTENCIAS DECLARATIVAS:
 * Pueden ser declaraciones de variables, funciones o inferencia de tipo.
 */
sentencia_declarativa : declaracion_tipica ';'
                      | funcion
                      | declaracion_var ';'
                      ;

declaracion_tipica : tipo lista_variables
            {
                // Mismo manejo semántico que en el código de referencia.
                for(String variable : listaVariables){
                   if(!al.getTablaSimbolos().containsKey(variable + ":" + ambito)){
                      al.reemplazarEnTS(variable, variable + ":" + ambito);
                      al.agregarAtributoLexema(variable + ":" + ambito, "Tipo", $1.sval);
                      al.agregarAtributoLexema(variable + ":" + ambito, "Uso", "Variable");
                   }
                   else {
                      erroresSemanticos.add("Linea " + (al.getContadorFila()+1) + ": " + "Variable '" + variable + "' redeclarada.");
                   }
                }
                listaVariables.clear();
            }
            ;

// TEMA 9: Inferencia de tipo obligatoria.
declaracion_var : VAR asignacion
                {
                    salida.add("Linea " + (al.getContadorFila()+1) + ": " + "Declaración por inferencia (var).");
                    // La lógica semántica de asignación de tipo se hará en etapas posteriores.
                    $$.obj = new Nodo("VAR", $2.obj);
                }
                ;

/*
 * TIPO DE DATO:
 * Se agregan los tipos uint y float.
 */
tipo : UINT { $$.sval = "uint"; }
     | FLOAT { $$.sval = "float"; }
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
 * FUNCIONES:
 * Sintaxis: <tipo> ID (<params>) '{' <cuerpo> '}'
 * Se permite más de un tipo de retorno para el TEMA 21.
 */
funcion : encabezado_funcion '{' sentencias '}'
        {
            salida.add("Linea " + (al.getContadorFila()+1) + ": " + "Declaración de Función.");
            // Lógica para cerrar el ámbito y validaciones de retorno...
            int indiceUltimoAmbito = ambito.lastIndexOf(':');
            if (indiceUltimoAmbito != -1) {
                ambito = ambito.substring(0, indiceUltimoAmbito);
            }
            listaArboles.add(new Nodo("Funcion " + $1.sval, $3.obj));
        }
        ;

encabezado_funcion : lista_tipos_retorno ID '(' lista_parametros_formales ')'
                   {
                        // Lógica semántica para registrar la función...
                        $$.sval = $2.sval; // Guardamos el nombre de la función.
                        ambito += ":" + $2.sval;
                        ultimaFuncion = $2.sval + ":" + ambito;
                   }
                   ;

lista_tipos_retorno : lista_tipos_retorno ',' tipo
                    {
                        // TEMA 21: Lógica para manejar múltiples tipos de retorno.
                    }
                    | tipo
                    ;

// TEMA 25: La función debe tener al menos un parámetro.
lista_parametros_formales : lista_parametros_formales ',' parametro_formal
                          | parametro_formal
                          ;

parametro_formal : sem_pasaje tipo ID
                 {
                    // Lógica semántica para registrar el parámetro.
                 }
                 ;

// TEMA 25: Semántica de pasaje de parámetros Copia-Resultado.
sem_pasaje : CR SE
           | CR LE
           | /* empty: semántica por defecto */
           ;

/*
 * SENTENCIAS EJECUTABLES
 */
sentencia_ejecutable : asignacion ';'
                     | asignacion_multiple ';'
                     | condicional_if
                     | condicional_do_while
                     | salida_pantalla ';'
                     | retorno_funcion ';'
                     ;

asignacion : variable ASIG expresion
           {
               salida.add("Linea " + (al.getContadorFila()+1) + ": " + "Asignación simple (:=).");
               $$.obj = new Nodo(":=", new Nodo($1.sval), $3.obj);
           }
           ;

// TEMA 19 y 21: Asignaciones múltiples con el operador '='. [cite: 138, 176]
asignacion_multiple : lista_variables ASIG_MULTIPLE lado_derecho_multiple
                    {
                        salida.add("Linea " + (al.getContadorFila()+1) + ": " + "Asignación múltiple (=).");
                        // Aquí, $1.obj sería el nodo para la lista de variables y $3.obj el de la derecha.
                    }
                    ;

lado_derecho_multiple : lista_elementos_restringidos
                      | invocacion_funcion
                      ;

// TEMA 19: El lado derecho puede ser una lista de variables, constantes o invocaciones. No expresiones.
lista_elementos_restringidos : lista_elementos_restringidos ',' factor
                             | factor
                             ;

// TEMA 23: Prefijado opcional en variables.
variable : ID
         {
             $$.sval = $1.sval;
         }
         | ID '.' ID
         {
             $$.sval = $1.sval + "." + $3.sval;
         }
         ;

/*
 * EXPRESIONES ARITMÉTICAS:
 * Se mantiene la estructura básica, pero sin anidamiento de paréntesis.
 */
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
       | toui_conversion // TEMA 31
       ;

// TEMA 31: Conversión explícita a uint.
toui_conversion : TOUI '(' expresion ')'
                {
                    salida.add("Linea " + (al.getContadorFila()+1) + ": " + "Conversión explícita a uint (toui).");
                    $$.obj = new Nodo("TOUI", $3.obj);
                }
                ;

invocacion_funcion : ID '(' lista_parametros_reales ')'
                   ;

lista_parametros_reales : lista_parametros_reales ',' parametro_real
                        | parametro_real
                        ;

// TEMA 28: Un parámetro real puede ser una expresión lambda.
parametro_real : expresion FLECHA ID
               | lambda_expresion FLECHA ID
               ;

// TEMA 28: Sintaxis de la expresión lambda como parámetro.
lambda_expresion : LAMBDA '(' tipo ID ')' '{' sentencias '}'
                 ;

constante : CTE
          | '-' CTE
          ;

/*
 * SENTENCIAS DE CONTROL
 */
condicional_if : IF '(' condicion ')' bloque_sentencias_ejecutables endif_opcional ENDIF ';'
               {
                   salida.add("Linea " + (al.getContadorFila()+1) + ": " + "Sentencia IF.");
               }
               ;

endif_opcional : ELSE bloque_sentencias_ejecutables
               | /* empty */
               ;

// TEMA 14: Bucle do-while.
condicional_do_while : DO bloque_sentencias_ejecutables WHILE '(' condicion ')' ';'
                     {
                         salida.add("Linea " + (al.getContadorFila()+1) + ": " + "Sentencia DO-WHILE.");
                     }
                     ;

condicion : expresion simbolo_comparacion expresion
          ;

simbolo_comparacion : '=' | DISTINTO | MAYOR_IGUAL | MENOR_IGUAL | '>' | '<'
                    ;

bloque_sentencias_ejecutables : sentencia_ejecutable
                              | '{' sentencias '}'
                              ;

/*
 * OTRAS SENTENCIAS EJECUTABLES
 */
// TEMA 8: Se adapta la salida a 'print' y se incluye CADENA_MULTILINEA.
salida_pantalla : PRINT '(' CADENA_MULTILINEA ')'
                {
                    salida.add("Linea " + (al.getContadorFila()+1) + ": " + "PRINT con cadena multilínea.");
                }
                | PRINT '(' expresion ')'
                {
                    salida.add("Linea " + (al.getContadorFila()+1) + ": " + "PRINT con expresión.");
                }
                ;

retorno_funcion : RETURN '(' lista_expresiones ')'
                {
                    salida.add("Linea " + (al.getContadorFila()+1) + ": " + "Sentencia RETURN.");
                }
                ;

lista_expresiones : lista_expresiones ',' expresion
                  | expresion
                  ;

%%
    // ... (El resto de tu código Java va aquí: yylex(), yyerror(), main(), etc.)
    // Asegúrate de que tu AnalizadorLexico esté instanciado en 'al'.
    // static AnalizadorLexico al;
    // ... (resto del código)