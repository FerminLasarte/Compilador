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
    import java.util.Collections;
    import java.util.Comparator;
    import java.util.Stack;
%}

%token ID CTE IF ELSE FLOAT ENDIF RETURN PRINT UINT VAR DO WHILE LAMBDA CADENA_MULTILINEA ASIG_MULTIPLE CR SE LE TOUI ASIG FLECHA MAYOR_IGUAL MENOR_IGUAL DISTINTO IGUAL_IGUAL PUNTO

%nonassoc IFX
%nonassoc ELSE

%%

programa : ID '{' { g.abrirAmbito($1.sval); } sentencias '}' { g.cerrarAmbito(); }
      {
          String nombrePrograma = $1.sval;
          Object lineaObj = al.getAtributo(nombrePrograma, "Linea");
          String linea = (lineaObj != null) ? lineaObj.toString() : "?";
          salida.add("Linea " + linea + ": Programa '" + nombrePrograma + "' reconocido.");
          // No se agrega a la TS
      }
     | '{' { g.abrirAmbito("MAIN"); } sentencias '}' { g.cerrarAmbito(); }
     {
         erroresSintacticos.add("Linea " + (al.getContadorFila()+1) + ": Error sintactico: Falta el nombre del programa.");
     }
     | ID sentencias '}'
     {
         erroresSintacticos.add("Linea " + (al.getContadorFila()+1) + ": Error sintactico: Falta el delimitador '{' al inicio del programa.");
     }
     | ID '{' sentencias
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

sentencia_declarativa : funcion
                      | declaracion_var ';'
                      ;

/* --- REGLA ACTUALIZADA (Tema 9) --- */
declaracion_var : VAR variable ASIG expresion
                {
                    salida.add("Linea " + (al.getContadorFila()+1) + ": Declaracion por inferencia (var).");

                    String expr = g.desapilarOperando();
                    String tipoExpr = g.getTipo(expr);
                    String varNombre = $2.sval; // $2.sval viene de 'variable'

                    // Tema 9: Chequear redeclaración en el ámbito actual
                    if (g.existeEnAmbitoActual(varNombre)) {
                        al.agregarErrorSemantico("Linea " + (al.getContadorFila()+1) + ": Error Semantico: Redeclaracion de variable '" + varNombre + "' en el mismo ambito (Tema 9).");
                    } else if (tipoExpr.equals("error_tipo") || tipoExpr.equals("indefinido")) {
                         al.agregarErrorSemantico("Linea " + (al.getContadorFila()+1) + ": Error Semantico: No se puede inferir el tipo de '" + varNombre + "' desde una expresion invalida (Tema 9).");
                    } else {
                        // Tema 9: Registrar en TS
                        al.agregarLexemaTS(varNombre); // Agrega al ámbito actual
                        al.agregarAtributoLexema(varNombre, "Uso", "variable");
                        al.agregarAtributoLexema(varNombre, "Tipo", tipoExpr);

                        // Tema 9: Generar código para la asignación
                        g.addTerceto(":=", varNombre, expr);
                    }
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

/* --- REGLA ACTUALIZADA (Tema 21 y 25) --- */
funcion : tipo ID '(' lista_parametros_formales ')' '{' {
            // Añadir la función al ámbito *padre* (el actual) ANTES de abrir el nuevo ámbito
            String nombreFuncion = $2.sval;
            String tipoRetorno = $1.sval;
            ArrayList<ParametroInfo> parametros = g.getListaParametros();

            if (g.existeEnAmbitoActual(nombreFuncion)) {
                al.agregarErrorSemantico("Linea " + (al.getContadorFila()+1) + ": Error Semantico: Redeclaracion de funcion '" + nombreFuncion + "'.");
            } else {
                al.agregarLexemaTS(nombreFuncion);
                al.agregarAtributoLexema(nombreFuncion, "Uso", "funcion");
                al.agregarAtributoLexema(nombreFuncion, "Tipo", tipoRetorno); // Tipo de retorno simple
                al.agregarAtributoLexema(nombreFuncion, "Parametros", parametros);
                al.agregarAtributoLexema(nombreFuncion, "RetornoMultiple", false);
            }

            g.abrirAmbito($2.sval);
            // Declarar parámetros en el nuevo ámbito de la función
            for (ParametroInfo p : parametros) {
                if (g.existeEnAmbitoActual(p.nombre)) {
                     al.agregarErrorSemantico("Linea " + (al.getContadorFila()+1) + ": Error Semantico: Redeclaracion del parametro '" + p.nombre + "'.");
                } else {
                     al.agregarLexemaTS(p.nombre);
                     al.agregarAtributoLexema(p.nombre, "Uso", "parametro");
                     al.agregarAtributoLexema(p.nombre, "Tipo", p.tipo);
                     al.agregarAtributoLexema(p.nombre, "Pasaje", p.pasaje);
                }
            }
        } sentencias '}' { g.cerrarAmbito(); }
        {
            String nombreFuncion = $2.sval;
            Object lineaObj = al.getAtributo(nombreFuncion, "Linea");
            String linea = (lineaObj != null) ? lineaObj.toString() : "?";
            salida.add("Linea " + (linea) + ": Declaracion de Funcion '" + $2.sval + "' con retorno simple.");
        }
      | lista_tipos_retorno_multiple ID '(' lista_parametros_formales ')' '{' {
            // Añadir la función al ámbito *padre* (el actual) ANTES de abrir el nuevo ámbito
            String nombreFuncion = $2.sval;
            ArrayList<String> tiposRetorno = (ArrayList<String>) $1.obj;
            ArrayList<ParametroInfo> parametros = g.getListaParametros();

            if (g.existeEnAmbitoActual(nombreFuncion)) {
                al.agregarErrorSemantico("Linea " + (al.getContadorFila()+1) + ": Error Semantico: Redeclaracion de funcion '" + nombreFuncion + "'.");
            } else {
                al.agregarLexemaTS(nombreFuncion);
                al.agregarAtributoLexema(nombreFuncion, "Uso", "funcion");
                al.agregarAtributoLexema(nombreFuncion, "Tipo", "multiple"); // Tipo de retorno
                al.agregarAtributoLexema(nombreFuncion, "TiposRetorno", tiposRetorno); // Lista de tipos
                al.agregarAtributoLexema(nombreFuncion, "Parametros", parametros);
                al.agregarAtributoLexema(nombreFuncion, "RetornoMultiple", true);
            }

            g.abrirAmbito($2.sval);
            // Declarar parámetros en el nuevo ámbito de la función
            for (ParametroInfo p : parametros) {
                if (g.existeEnAmbitoActual(p.nombre)) {
                     al.agregarErrorSemantico("Linea " + (al.getContadorFila()+1) + ": Error Semantico: Redeclaracion del parametro '" + p.nombre + "'.");
                } else {
                     al.agregarLexemaTS(p.nombre);
                     al.agregarAtributoLexema(p.nombre, "Uso", "parametro");
                     al.agregarAtributoLexema(p.nombre, "Tipo", p.tipo);
                     al.agregarAtributoLexema(p.nombre, "Pasaje", p.pasaje);
                }
            }
        } sentencias '}' { g.cerrarAmbito(); }
        {
            String nombreFuncion = $2.sval;
            Object lineaObj = al.getAtributo(nombreFuncion, "Linea");
            String linea = (lineaObj != null) ? lineaObj.toString() : "?";
            salida.add("Linea " + (linea) + ": Declaracion de Funcion '" + $2.sval + "' con retorno multiple.");
        }
      ;

/* --- REGLA ACTUALIZADA (Tema 21) --- */
lista_tipos_retorno_multiple : tipo ',' tipo
                             {
                                 ArrayList<String> lista = new ArrayList<String>();
                                 lista.add($1.sval);
                                 lista.add($3.sval);
                                 $$.obj = lista;
                             }
                         | lista_tipos_retorno_multiple ',' tipo
                             {
                                 ArrayList<String> lista = (ArrayList<String>) $1.obj;
                                 lista.add($3.sval);
                                 $$.obj = lista;
                             }
                         ;

lista_parametros_formales : lista_parametros_formales ',' parametro_formal
                          | parametro_formal
                          ;

/* --- REGLA ACTUALIZADA (Tema 25) --- */
parametro_formal : sem_pasaje tipo ID
             {
                 // $1=pasaje, $2=tipo, $3=nombre
                 // Apilar info para la firma de la función
                 g.apilarParametro(new ParametroInfo($3.sval, $2.sval, $1.sval));
             }
             | tipo ID
             {
                 // Tema 25: semántica por defecto: Copia-Valor
                 String pasajeDefault = "default_cv";
                 g.apilarParametro(new ParametroInfo($2.sval, $1.sval, pasajeDefault));
             }
             ;

/* --- REGLA ACTUALIZADA (Tema 25) --- */
sem_pasaje : CR SE { $$.sval = "cr_se"; } // Tema 25: solo escritura
           | CR LE { $$.sval = "cr_le"; } // Tema 25: lectura/escritura
           ;

sentencia_ejecutable : asignacion ';'
                     | asignacion_multiple ';'
                     | condicional_if
                     | condicional_do_while
                     | salida_pantalla ';'
                     | retorno_funcion
                     ;

/* --- REGLA ACTUALIZADA --- */
asignacion : variable ASIG expresion
           {
               salida.add("Linea " + (al.getContadorFila()+1) + ": Asignacion simple (:=).");
               String op2 = g.desapilarOperando(); // Lado derecho
               String op1 = $1.sval; // Lado izquierdo

               String tipoVar = g.getTipo(op1);
               String tipoExpr = g.getTipo(op2);

               if (g.chequearAsignacion(tipoVar, tipoExpr, al.getContadorFila()+1)) {
                   g.addTerceto(":=", op1, op2);
               }
           }
           ;

/* --- REGLA ACTUALIZADA (Tema 19 y 21) --- */
asignacion_multiple : lista_variables ASIG_MULTIPLE lado_derecho_multiple
                        {
                            String lineaActual = String.valueOf(al.getContadorFila() + 1);
                            int cantIzquierda = listaVariables.size();
                            int cantDerecha = contadorLadoDerecho;
                            Stack<String> derechos = g.getPilaLadoDerecho();
                            boolean esFuncion = ($3.ival == 1);

                            if (esFuncion) {
                                // TEMA 21: A,B := func()
                                String funcTerceto = derechos.pop(); // Es el [terceto_CALL]
                                // Obtenemos el nombre de la función desde el operando 1 del terceto CALL
                                String funcName = g.getTerceto(Integer.parseInt(funcTerceto.substring(1, funcTerceto.length()-1))).getOperando1();

                                Object retMultiple = al.getAtributo(funcName, "RetornoMultiple");

                                if (retMultiple == null || !(Boolean)retMultiple) {
                                    al.agregarErrorSemantico("Linea " + lineaActual + ": Error Semantico: Asignacion multiple a funcion '" + funcName + "' que no tiene retorno multiple.");
                                } else {
                                    ArrayList<String> tiposRetorno = (ArrayList<String>) al.getAtributo(funcName, "TiposRetorno");
                                    int cantRetornos = tiposRetorno.size();

                                    // Tema 21: Se permiten MÁS retornos que lados izquierdos
                                    if (cantRetornos < cantIzquierda) {
                                        al.agregarErrorSemantico("Linea " + lineaActual + ": Error Semantico (Tema 21): Asignacion multiple a funcion '" + funcName + "'. Insuficientes valores de retorno. Esperados: " + cantIzquierda + ", Retornados: " + cantRetornos + ".");
                                    } else {
                                        if (cantRetornos > cantIzquierda) {
                                            al.agregarWarning("Linea " + lineaActual + ": Warning (Tema 21): Funcion '" + funcName + "' retorna " + cantRetornos + " valores, pero solo se asignan " + cantIzquierda + ". Se descartan los sobrantes.");
                                        }

                                        // Generar N tercetos GET_RET y N tercetos ASIG
                                        for (int i = 0; i < cantIzquierda; i++) {
                                            String var = listaVariables.get(i);
                                            String tipoVar = g.getTipo(var);
                                            String tipoRet = tiposRetorno.get(i);

                                            if (g.chequearAsignacion(tipoVar, tipoRet, Integer.parseInt(lineaActual))) {
                                                String retTerceto = g.addTerceto("GET_RET", funcTerceto, String.valueOf(i));
                                                g.getTerceto(Integer.parseInt(retTerceto.substring(1, retTerceto.length()-1))).setTipo(tipoRet);
                                                g.addTerceto(":=", var, retTerceto);
                                            }
                                        }
                                        salida.add("Linea " + lineaActual + ": Asignacion multiple (funcion '" + funcName + "') reconocida.");
                                    }
                                }

                            } else {
                                // TEMA 19: A,B := C,D
                                if (cantIzquierda != cantDerecha) {
                                    al.agregarErrorSemantico("Linea " + lineaActual + ": Error Semantico (Tema 19): La asignacion multiple debe tener el mismo numero de elementos a la izquierda (" + cantIzquierda + ") y a la derecha (" + cantDerecha + ").");
                                } else {
                                    // Generar N tercetos ASIG (la pila está al revés, iteramos normal)
                                    for (int i = 0; i < cantIzquierda; i++) {
                                        String var = listaVariables.get(i);
                                        String expr = derechos.pop(); // Sacamos de la pila

                                        String tipoVar = g.getTipo(var);
                                        String tipoExpr = g.getTipo(expr);

                                        if (g.chequearAsignacion(tipoVar, tipoExpr, Integer.parseInt(lineaActual))) {
                                            g.addTerceto(":=", var, expr);
                                        }
                                    }
                                    salida.add("Linea " + lineaActual + ": Asignacion multiple (lista) reconocida.");
                                }
                            }
                            contadorLadoDerecho = 0;
                            listaVariables.clear();
                            g.clearLadoDerecho();
                        }
                        ;

/* --- REGLA ACTUALIZADA (Tema 19) --- */
lado_derecho_multiple : { g.clearLadoDerecho(); } factor
                          {
                              g.apilarLadoDerecho(g.desapilarOperando());
                              contadorLadoDerecho = 1;
                              $$.ival = $2.ival; // $2 viene de 'factor' (0=no_func, 1=func)
                              $$.sval = $2.sval; // $2 viene de 'factor' (terceto [CALL])
                          }
                          | lado_derecho_multiple ',' factor
                          {
                              g.apilarLadoDerecho(g.desapilarOperando());
                              contadorLadoDerecho++;
                              $$.ival = 0; // Es una lista, no una sola función
                          }
                          ;

/* --- REGLA ACTUALIZADA (Semantico) --- */
variable : ID PUNTO ID  // Tema 23
            {
                $$.sval = $1.sval + "." + $3.sval;
                if (g.getTipo($$.sval).equals("indefinido")) {
                     al.agregarErrorSemantico("Linea " + (al.getContadorFila()+1) + ": Error Semantico: Variable '" + $3.sval + "' no existe en el ambito '" + $1.sval + "' o el ambito no es visible (Tema 23).");
                }
            }
         | ID
            {
                $$.sval = $1.sval;
                if (g.getTipo($$.sval).equals("indefinido")) {
                     al.agregarErrorSemantico("Linea " + (al.getContadorFila()+1) + ": Error Semantico: Variable '" + $1.sval + "' no fue declarada (Regla de alcance).");
                }
            }
         ;

/* --- REGLA ACTUALIZADA --- */
expresion : expresion '+' termino
            {
                String op2 = g.desapilarOperando();
                String op1 = g.desapilarOperando();
                String tipo = g.chequearTipos("+", g.getTipo(op1), g.getTipo(op2), al.getContadorFila()+1);
                String terceto = g.addTerceto("+", op1, op2);
                g.getTerceto(Integer.parseInt(terceto.substring(1, terceto.length()-1))).setTipo(tipo);
                g.apilarOperando(terceto);
            }
          | expresion '-' termino
            {
                String op2 = g.desapilarOperando();
                String op1 = g.desapilarOperando();
                String tipo = g.chequearTipos("-", g.getTipo(op1), g.getTipo(op2), al.getContadorFila()+1);
                String terceto = g.addTerceto("-", op1, op2);
                g.getTerceto(Integer.parseInt(terceto.substring(1, terceto.length()-1))).setTipo(tipo);
                g.apilarOperando(terceto);
            }
          | termino { /* No hacer nada, 'termino' ya apiló su resultado */ }
          ;

/* --- REGLA ACTUALIZADA --- */
termino : termino '*' factor
            {
                String op2 = g.desapilarOperando();
                String op1 = g.desapilarOperando();
                String tipo = g.chequearTipos("*", g.getTipo(op1), g.getTipo(op2), al.getContadorFila()+1);
                String terceto = g.addTerceto("*", op1, op2);
                g.getTerceto(Integer.parseInt(terceto.substring(1, terceto.length()-1))).setTipo(tipo);
                g.apilarOperando(terceto);
            }
        | termino '/' factor
            {
                String op2 = g.desapilarOperando();
                String op1 = g.desapilarOperando();
                String tipo = g.chequearTipos("/", g.getTipo(op1), g.getTipo(op2), al.getContadorFila()+1);
                String terceto = g.addTerceto("/", op1, op2);
                g.getTerceto(Integer.parseInt(terceto.substring(1, terceto.length()-1))).setTipo(tipo);
                g.apilarOperando(terceto);
            }
        | factor { /* No hacer nada, 'factor' ya apiló su resultado */ }
        ;

/* --- REGLA ACTUALIZADA (Invocacion y T28) --- */
factor : factor_no_funcion
       {
           $$.ival = 0;
       }
       | { g.clearParametrosReales(); } invocacion_funcion // Limpia la pila antes de parsear
       {
           $$.ival = 1; // Es una función
           $$.sval = $2.sval; // El nombre/terceto de la función
           g.apilarOperando($2.sval); // Apila el terceto [CALL]
       }
       ;

/* --- REGLA ACTUALIZADA --- */
factor_no_funcion : variable
                  { g.apilarOperando($1.sval); }
                  | constante
                  { g.apilarOperando($1.sval); } // $1 viene de 'constante'
                  | conversion_explicita
                  { /* 'conversion_explicita' ya apiló su terceto */ }
                  ;

/* --- REGLA ACTUALIZADA --- */
conversion_explicita : TOUI '(' expresion ')'
                {
                    salida.add("Linea " + (al.getContadorFila()+1) + ": Conversion explicita (toui).");
                    String op1 = g.desapilarOperando();
                    String tipoOp1 = g.getTipo(op1);

                    if (!tipoOp1.equals("float")) {
                        al.agregarErrorSemantico("Linea " + (al.getContadorFila()+1) + ": Error Semantico: 'toui' solo puede aplicarse a expresiones 'float', se obtuvo '" + tipoOp1 + "'.");
                    }

                    String terceto = g.addTerceto("TOUI", op1);
                    g.getTerceto(Integer.parseInt(terceto.substring(1, terceto.length()-1))).setTipo("uint");
                    g.apilarOperando(terceto);
                }
                ;

/* --- REGLA ACTUALIZADA (Invocacion y T28) --- */
invocacion_funcion : ID '(' lista_parametros_reales ')'
                   {
                       String funcName = $1.sval;
                       int linea = al.getContadorFila() + 1;
                       int cantReales = $3.ival;
                       ArrayList<ParametroRealInfo> reales = g.getListaParametrosReales(cantReales);

                       Object uso = al.getAtributo(funcName, "Uso");
                       String tipo = g.getTipo(funcName);

                       // TEMA 28: Invocación de una variable lambda (ej: X(Y))
                       if (uso != null && (uso.toString().equals("parametro") || uso.toString().equals("parametro_lambda") || uso.toString().equals("variable")) && tipo.equals("lambda")) {

                           if (reales.size() != 1) {
                               al.agregarErrorSemantico("Linea " + linea + ": Error Semantico (Tema 28): Invocacion de lambda '" + funcName + "' con numero incorrecto de parametros. Esperado: 1, Obtenido: " + reales.size() + ".");
                               $$.sval = "ERROR_CALL_LAMBDA";
                           } else {
                               // TODO: Chequear tipo del parámetro real vs. el parámetro formal de la lambda
                               // (Requiere almacenar la firma de la lambda en la TS, omitido por simplicidad)

                               // 1. Generar PARAM
                               g.addTerceto("PARAM_LAMBDA", reales.get(0).operando, "_");
                               // 2. Generar CALL a la dirección almacenada en la variable 'funcName'
                               String terceto = g.addTerceto("CALL_LAMBDA", funcName, "_");
                               g.getTerceto(Integer.parseInt(terceto.substring(1, terceto.length()-1))).setTipo("void"); // Lambdas no retornan valor
                               $$.sval = terceto;
                           }
                       }
                       // Invocación de función normal
                       else if (uso != null && uso.toString().equals("funcion")) {
                           ArrayList<ParametroInfo> formales = (ArrayList<ParametroInfo>) al.getAtributo(funcName, "Parametros");

                           if (formales == null) {
                                al.agregarErrorSemantico("Linea " + linea + ": Error Semantico: No se pudo recuperar la firma de la funcion '" + funcName + "'.");
                                $$.sval = "ERROR_CALL";
                           } else if (formales.size() != reales.size()) {
                               al.agregarErrorSemantico("Linea " + linea + ": Error Semantico: Invocacion a '" + funcName + "' con numero incorrecto de parametros. Esperados: " + formales.size() + ", Obtenidos: " + reales.size() + ".");
                               $$.sval = "ERROR_CALL";
                           } else {
                                boolean errorEnParametros = false;
                               for (ParametroRealInfo real : reales) {
                                   ParametroInfo formal = formales.stream().filter(f -> f.nombre.equals(real.nombreFormal)).findFirst().orElse(null);
                                   if (formal == null) {
                                       al.agregarErrorSemantico("Linea " + linea + ": Error Semantico: Invocacion a '" + funcName + "': no existe el parametro formal '->" + real.nombreFormal + "'.");
                                       errorEnParametros = true;
                                   } else {
                                       String tipoReal = g.getTipo(real.operando);
                                       String tipoFormal = formal.tipo;

                                       if (tipoFormal.equals("lambda") && tipoReal.equals("lambda_expr")) {
                                            // OK (T28)
                                       } else if (!tipoFormal.equals("lambda") && tipoReal.equals("lambda_expr")) {
                                           al.agregarErrorSemantico("Linea " + linea + ": Error Semantico (Tema 28): Se paso una expresion lambda al parametro '->" + real.nombreFormal + "' que no es de tipo 'lambda'.");
                                           errorEnParametros = true;
                                       } else if (tipoFormal.equals("lambda") && !tipoReal.equals("lambda_expr")) {
                                           al.agregarErrorSemantico("Linea " + linea + ": Error Semantico (Tema 28): El parametro '->" + real.nombreFormal + "' espera una expresion 'lambda'.");
                                           errorEnParametros = true;
                                       } else if (!tipoReal.equals("error_tipo") && !g.chequearAsignacion(tipoFormal, tipoReal, linea)) { // T31
                                           al.agregarErrorSemantico("Linea " + linea + ": Error Semantico: Invocacion a '" + funcName + "': tipo incompatible para '->" + real.nombreFormal + "'. Esperado: " + tipoFormal + ", Obtenido: " + tipoReal + ".");
                                           errorEnParametros = true;
                                       } else if (tipoReal.equals("error_tipo")) {
                                            errorEnParametros = true; // Error ya reportado
                                       }

                                       if (!errorEnParametros) {
                                            g.addTerceto("PARAM", real.operando, formal.nombre);
                                       }
                                   }
                               }

                               if (!errorEnParametros) {
                                   String terceto = g.addTerceto("CALL", funcName);
                                   g.getTerceto(Integer.parseInt(terceto.substring(1, terceto.length()-1))).setTipo(al.getAtributo(funcName, "Tipo").toString());
                                   $$.sval = terceto;
                               } else {
                                   $$.sval = "ERROR_CALL_PARAMS";
                               }
                           }
                       }
                       else {
                           al.agregarErrorSemantico("Linea " + linea + ": Error Semantico: Invocacion a '" + funcName + "' que no es una funcion, variable lambda, o no fue declarada.");
                           $$.sval = "ERROR_CALL";
                       }
                   }
                   ;

/* --- REGLA ACTUALIZADA (Invocacion) --- */
lista_parametros_reales : lista_parametros_reales ',' parametro_real
                        { $$.ival = $1.ival + 1; } // Contar parámetros
                        | parametro_real
                        { $$.ival = 1; } // Primer parámetro
                        ;

/* --- REGLA ACTUALIZADA (Invocacion) --- */
parametro_real : parametro_simple FLECHA ID
               {
                   // $1.sval es el operando (resultado de expr o lambda)
                   // $3.sval es el nombre del parametro formal
                   g.apilarParametroReal(new ParametroRealInfo($1.sval, $3.sval));
               }
               | parametro_simple
               {
                   // El TP requiere flecha para asignación explícita
                   al.agregarErrorSemantico("Linea " + (al.getContadorFila()+1) + ": Error Semantico: Se requiere asignacion explicita de parametro (-> ID).");
               }
               ;

/* --- REGLA ACTUALIZADA (Invocacion y T28) --- */
parametro_simple : expresion
                 { $$.sval = g.desapilarOperando(); }
                 | lambda_expresion
                 {
                     // $1.sval es el Nro de terceto de inicio de la lambda
                     $$.sval = $1.sval;
                 }
                 ;

/* --- REGLA ACTUALIZADA (Tema 28) --- */
lambda_expresion : '(' tipo ID ')' '{' {
                         // 1. Saltar el cuerpo de la lambda (BI = Branch Inconditional)
                         String saltoIncondicional = g.addTerceto("BI", "_", "_");
                         // 2. Apilar la dirección de inicio del cuerpo
                         int inicioLambda = g.getProximoTerceto();
                         $$.sval = String.valueOf(inicioLambda); // Esto es lo que se pasa como parámetro

                         // 3. Abrir ámbito para el parámetro de la lambda
                         g.abrirAmbito("lambda_" + inicioLambda);
                         // 4. Declarar el parámetro en la TS de la lambda
                         al.agregarLexemaTS($3.sval);
                         al.agregarAtributoLexema($3.sval, "Uso", "parametro_lambda");
                         al.agregarAtributoLexema($3.sval, "Tipo", $2.sval);

                         // 5. Generar terceto 'DEF_PARAM' para recibir el valor
                         g.addTerceto("DEF_PARAM", $3.sval, "_");
                     } cuerpo_lambda '}' {
                         // 6. Fin del cuerpo
                         g.addTerceto("RET_LAMBDA", "_", "_");
                         // 7. Rellenar el salto inicial
                         int tercetoFin = g.getProximoTerceto();
                         g.modificarSaltoTerceto(Integer.parseInt(saltoIncondicional.substring(1, saltoIncondicional.length()-1)), String.valueOf(tercetoFin));

                         // 8. Cerrar ámbito
                         g.cerrarAmbito();
                     }
                 ;

// CHEQUEAR CUERPO VACIO
cuerpo_lambda : sentencias_ejecutables_lista
              ;

sentencias_ejecutables_lista : sentencias_ejecutables_lista sentencia_ejecutable
                             | sentencia_ejecutable
                             ;

/* --- REGLA ACTUALIZADA --- */
constante : CTE
            { $$.sval = $1.sval; }
          | '-' CTE
            {
                String lexemaPositivo = $2.sval;
                String lexemaNegativo = "-" + lexemaPositivo;

                if (al.getAtributo(lexemaPositivo, "Tipo") != null) { /* CAMBIO: chequeo de existencia */
                    String tipo = (String) al.getAtributo(lexemaPositivo, "Tipo");
                    if (tipo != null) {
                        if (tipo.equals("uint")) {
                            al.agregarErrorSemantico("Linea " + (al.getContadorFila()+1) + ": Error Semantico. El tipo 'uint' no puede ser negativo. Valor: " + lexemaNegativo);
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
                $$.sval = lexemaNegativo; // Asigna el lexema negativo
            }
          ;

// CHEQUEAR PORQUE NO IMPRIME TODO
condicional_if : IF '(' condicion ')' bloque_ejecutable ENDIF ';'
%prec IFX
                {
                    Object lineaObj = al.getAtributo("if", "Linea"); // Busca "if" en la TS (PR)
                    String linea = (lineaObj != null) ? lineaObj.toString() : "?";
                    salida.add("Linea " + linea + ": Sentencia IF reconocida.");
                }
               | IF '(' condicion ')' bloque_ejecutable ELSE bloque_ejecutable ENDIF ';'
               {
                    Object lineaObj = al.getAtributo("if", "Linea");
                    String linea = (lineaObj != null) ? lineaObj.toString() : "?";
                    salida.add("Linea " + linea + ": Sentencia IF-ELSE reconocida.");
               }
               ;

/* --- REGLA ACTUALIZADA (Tema 14) --- */
condicional_do_while: DO { g.apilarControl(g.getProximoTerceto()); } bloque_ejecutable WHILE '(' condicion ')' ';'
                    {
                        Object lineaObj = al.getAtributo("do", "Linea");
                        String linea = (lineaObj != null) ? lineaObj.toString() : "?";
                        salida.add("Linea " + linea + ": Sentencia DO-WHILE reconocida.");

                        String refCondicion = g.desapilarOperando();
                        int inicioBucle = g.desapilarControl();

                        if (refCondicion.equals("ERROR_CONDICION")) {
                             al.agregarErrorSemantico("Linea " + linea + ": Error Semantico: No se genero el salto del DO-WHILE debido a una condicion invalida.");
                        } else {
                            // Tema 14: Se ejecuta MIENTRAS sea verdadera
                            // Generamos un Salto por Verdadero (BT) al inicio del bucle.
                            String tercetoSalto = g.addTerceto("BT", refCondicion, String.valueOf(inicioBucle));
                        }
                    }
                    | DO bloque_ejecutable WHILE '(' condicion ')'
                    {
                        Object lineaObj = al.getAtributo("do", "Linea");
                        String linea = (lineaObj != null) ? lineaObj.toString() : "?";
                        yyerror("Linea " + linea + ": Error Sintactico. Falta punto y coma ';' al final de la sentencia DO-WHILE.");
                        // Desapilamos lo que sea que 'condicion' y 'DO' hayan apilado para limpiar las pilas.
                        g.desapilarOperando();
                        g.desapilarControl();
                    }
                    ;

/* --- REGLA ACTUALIZADA (Tema 14) --- */
condicion : expresion simbolo_comparacion expresion
          {
                String op2 = g.desapilarOperando();
                String op = g.desapilarOperando();
                String op1 = g.desapilarOperando();

                String tipo = g.chequearTipos(op, g.getTipo(op1), g.getTipo(op2), al.getContadorFila()+1);
                if (!tipo.equals("error_tipo")) {
                    String terceto = g.addTerceto(op, op1, op2);
                    g.getTerceto(Integer.parseInt(terceto.substring(1, terceto.length()-1))).setTipo("boolean");
                    g.apilarOperando(terceto);
                } else {
                    g.apilarOperando("ERROR_CONDICION"); // Pushear placeholder de error
                }
          }
          ;

/* --- REGLA ACTUALIZADA (Tema 14) --- */
simbolo_comparacion : MAYOR_IGUAL { g.apilarOperando(">="); }
                    | MENOR_IGUAL { g.apilarOperando("<="); }
                    | DISTINTO { g.apilarOperando("=!"); }
                    | IGUAL_IGUAL { g.apilarOperando("=="); }
                    | '>' { g.apilarOperando(">"); }
                    | '<' { g.apilarOperando("<"); }
                    ;

bloque_ejecutable : '{' { g.abrirAmbito("bloque_" + g.getProximoTerceto()); } sentencias_ejecutables_lista '}' { g.cerrarAmbito(); }
                  | sentencia_ejecutable // BORRAR ?
                  | '{' error '}' // CHEQUEAR
                  ;

/* --- REGLA ACTUALIZADA --- */
salida_pantalla : PRINT '(' CADENA_MULTILINEA ')'
                {
                    salida.add("Linea " + (al.getContadorFila()+1) + ": PRINT con cadena multilinea.");
                    g.addTerceto("PRINT", $3.sval);
                }
                | PRINT '(' expresion ')'
                {
                    salida.add("Linea " + (al.getContadorFila()+1) + ": PRINT con expresion.");
                    g.addTerceto("PRINT", g.desapilarOperando());
                }
                ;

/* --- REGLA ACTUALIZADA (Tema 21) --- */
retorno_funcion : RETURN '(' lista_expresiones ')' ';'
            {
                salida.add("Linea " + (al.getContadorFila()+1) + ": Sentencia RETURN.");
                // T21: La lista de expresiones se maneja en 'lista_expresiones'
                ArrayList<String> expresiones = (ArrayList<String>) $3.obj;

                // Aquí deberíamos chequear contra la firma de la función (guardada en la TS)
                // Por ahora, solo generamos los tercetos de RETURN
                for (String exprTerceto : expresiones) {
                    g.addTerceto("RETURN", exprTerceto);
                }
            }
            ;

/* --- REGLA ACTUALIZADA (Tema 21) --- */
lista_expresiones : lista_expresiones ',' expresion
              {
                  ArrayList<String> lista = (ArrayList<String>) $1.obj;
                  lista.add(g.desapilarOperando());
                  $$.obj = lista;
              }
              | expresion
              {
                  ArrayList<String> lista = new ArrayList<String>();
                  lista.add(g.desapilarOperando());
                  $$.obj = lista;
              }
              ;
%%

static AnalizadorLexico al;
static Generador g; // --- AÑADIDO ---
ArrayList<String> erroresSintacticos = new ArrayList<String>();
ArrayList<String> erroresSemanticos = new ArrayList<String>(); // Obsoleto, se usa al.getErroresSemanticos()
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
        // --- AÑADIDO ---
        g = Generador.getInstance();
        g.setAnalizadorLexico(al);
        // --- FIN AÑADIDO ---
        Parser par = new Parser(false);
        par.yyparse();

        System.out.println("\n=======================================================");
        System.out.println("## ESTRUCTURAS SINTACTICAS RECONOCIDAS ##");
        System.out.println("=======================================================");
        if (par.salida.isEmpty()) {
            System.out.println("No se reconocieron estructuras sintacticas validas.");
        } else {

            Comparator<String> comparadorPorLinea = new Comparator<String>() {
                @Override
                public int compare(String s1, String s2) {
                    try {

                        int linea1 = Integer.parseInt(s1.substring(6, s1.indexOf(':')).trim());
                        int linea2 = Integer.parseInt(s2.substring(6, s2.indexOf(':')).trim());
                        return Integer.compare(linea1, linea2);
                    } catch (Exception e) {
                        return s1.compareTo(s2);
                    }
                }
            };
            Collections.sort(par.salida, comparadorPorLinea);

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
        // --- CAMBIO: Usar al.getErroresSemanticos() ---
        if (al.getErroresSemanticos().isEmpty()) {
            System.out.println("No se encontraron errores semanticos.");
        } else {
            for (String s : al.getErroresSemanticos()) {
                System.out.println(s);
            }
        }
        // --- FIN CAMBIO ---

        // --- CAMBIO: Reemplazar impresión de TS ---
        g.imprimirTercetos();
        al.imprimirTablaSimbolos(); // Usamos el nuevo método

        System.out.println("=======================================================");
        // --- FIN CAMBIO ---

    } else {
        System.out.println("Error: Se requiere la ruta del archivo fuente como unico parametro.");
    }
}
}