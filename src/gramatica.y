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

programa : ID '{' {
             g.abrirAmbito($1.sval);
             al.agregarLexemaTS($1.sval);
             al.agregarAtributoLexema($1.sval, "Linea", $1.ival);
         } sentencias '}' { }
         {
             String nombrePrograma = $1.sval;
             Object lineaObj = al.getAtributo(nombrePrograma, "Linea");
             String linea = (lineaObj != null) ? lineaObj.toString() : "?";
             salida.add("Linea " + linea + ": Programa '" + nombrePrograma + "' reconocido.");
         }
     |
     '{' { g.abrirAmbito("MAIN"); } sentencias '}' { }
         {
             erroresSintacticos.add("Linea " + (al.getContadorFila()+1) + ": Error sintactico: Falta el nombre del programa.");
         }
     ;

sentencias : sentencias sentencia
           |
           sentencia
           ;

sentencia : sentencia_declarativa
          |
          sentencia_ejecutable
          | error ';'
          ;

sentencia_declarativa : funcion
                      |
                      declaracion_var ';'
                      ;

declaracion_var : VAR variable ASIG expresion
                {
                    String expr = g.desapilarOperando();
                    String tipoExpr = g.getTipo(expr);
                    String varNombre = $2.sval;
                    if (g.existeEnAmbitoActual(varNombre)) {
                        al.agregarErrorSemantico("Linea " + $1.ival + ": Error Semantico: Redeclaracion de variable '" + varNombre + "' en el mismo ambito (Tema 9).");
                    } else if (tipoExpr.equals("error_tipo") || tipoExpr.equals("indefinido")) {
                         al.agregarErrorSemantico("Linea " + $1.ival + ": Error Semantico: No se puede inferir el tipo de '" + varNombre + "' desde una expresion invalida (Tema 9).");
                    } else {
                        al.agregarLexemaTS(varNombre);
                        al.agregarAtributoLexema(varNombre, "Uso", "variable");
                        al.agregarAtributoLexema(varNombre, "Tipo", tipoExpr);
                        g.addTerceto(":=", varNombre, expr);
                        salida.add("Linea " + $1.ival + ": Declaracion por inferencia (var).");
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
                |
                variable
                {
                    listaVariables.clear();
                    listaVariables.add($1.sval);
                }
                ;

funcion : tipo ID '(' lista_parametros_formales ')' '{' {
            String nombreFuncion = $2.sval;
            String tipoRetorno = $1.sval;
            String nombreAmbito = nombreFuncion;
            ArrayList<ParametroInfo> parametros = g.getListaParametros();
            pilaInicioFuncion.push(g.getProximoTerceto());

            ArrayList<String> tiposEsperados = new ArrayList<String>();
            tiposEsperados.add(tipoRetorno);
            pilaTiposRetorno.push(tiposEsperados);
            pilaErrorEnFuncion.push(false);

            if (g.existeEnAmbitoActual(nombreFuncion)) {
                al.agregarErrorSemantico("Linea " + $2.ival + ": Error Semantico: Redeclaracion de funcion '" + nombreFuncion + "'.");
                g.setGeneracionHabilitada(false);
                nombreAmbito = "GARBAGE_" + nombreFuncion;
            } else {
                al.agregarLexemaTS(nombreFuncion);
                al.agregarAtributoLexema(nombreFuncion, "Uso", "funcion");
                al.agregarAtributoLexema(nombreFuncion, "Linea", $2.ival);
                al.agregarAtributoLexema(nombreFuncion, "Tipo", tipoRetorno);
                al.agregarAtributoLexema(nombreFuncion, "Parametros", parametros);
                al.agregarAtributoLexema(nombreFuncion, "RetornoMultiple", false);
            }
            g.abrirAmbito(nombreAmbito);
            for (ParametroInfo p : parametros) {
                if (g.existeEnAmbitoActual(p.nombre)) {
                     al.agregarErrorSemantico("Linea " + $2.ival + ": Error Semantico: Redeclaracion del parametro '" + p.nombre + "'.");
                } else {
                     al.agregarLexemaTS(p.nombre);
                     al.agregarAtributoLexema(p.nombre, "Uso", "parametro");
                     al.agregarAtributoLexema(nombreFuncion, "Linea", $2.ival);
                     al.agregarAtributoLexema(p.nombre, "Tipo", p.tipo);
                     al.agregarAtributoLexema(p.nombre, "Pasaje", p.pasaje);
                }
            }
        } sentencias '}' {
            g.cerrarAmbito();
            g.setGeneracionHabilitada(true);

            pilaTiposRetorno.pop();
            boolean huboError = pilaErrorEnFuncion.pop();
            int inicioFunc = pilaInicioFuncion.pop();

            if (huboError) {
                al.eliminarLexemaTS($2.sval);
                g.anularTercetosDesde(inicioFunc);
                al.eliminarUltimoAmbito();
            } else {
                String nombreFuncion = $2.sval;
                salida.add("Linea " + $2.ival + ": Declaracion de Funcion '" + $2.sval + "' con retorno simple.");
            }
        }
      |
      lista_tipos_retorno_multiple ID '(' lista_parametros_formales ')' '{' {
            String nombreFuncion = $2.sval;
            String nombreAmbito = nombreFuncion;
            ArrayList<?> rawList = (ArrayList<?>) $1.obj;
            ArrayList<String> tiposRetorno = new ArrayList<String>();
            for (Object o : rawList) {
                tiposRetorno.add((String) o);
            }
            pilaInicioFuncion.push(g.getProximoTerceto());

            pilaTiposRetorno.push(tiposRetorno);
            pilaErrorEnFuncion.push(false);

            ArrayList<ParametroInfo> parametros = g.getListaParametros();

            if (g.existeEnAmbitoActual(nombreFuncion)) {
                al.agregarErrorSemantico("Linea " + $2.ival + ": Error Semantico: Redeclaracion de funcion '" + nombreFuncion + "'.");
                g.setGeneracionHabilitada(false);
                nombreAmbito = "GARBAGE_" + nombreFuncion;
            } else {
                al.agregarLexemaTS(nombreFuncion);
                al.agregarAtributoLexema(nombreFuncion, "Uso", "funcion");
                al.agregarAtributoLexema(nombreFuncion, "Tipo", "multiple");
                al.agregarAtributoLexema(nombreFuncion, "TiposRetorno", tiposRetorno);
                al.agregarAtributoLexema(nombreFuncion, "Parametros", parametros);
                al.agregarAtributoLexema(nombreFuncion, "RetornoMultiple", true);
            }
            g.abrirAmbito(nombreAmbito);
            for (ParametroInfo p : parametros) {
                if (g.existeEnAmbitoActual(p.nombre)) {
                     al.agregarErrorSemantico("Linea " + $2.ival + ": Error Semantico: Redeclaracion del parametro '" + p.nombre + "'.");
                } else {
                     al.agregarLexemaTS(p.nombre);
                     al.agregarAtributoLexema(p.nombre, "Uso", "parametro");
                     al.agregarAtributoLexema(p.nombre, "Tipo", p.tipo);
                     al.agregarAtributoLexema(p.nombre, "Pasaje", p.pasaje);
                }
            }
        } sentencias '}' {
            g.cerrarAmbito();
            g.setGeneracionHabilitada(true);

            pilaTiposRetorno.pop();
            boolean huboError = pilaErrorEnFuncion.pop();
            int inicioFunc = pilaInicioFuncion.pop();

            if (huboError) {
                al.eliminarLexemaTS($2.sval);
                g.anularTercetosDesde(inicioFunc);
                al.eliminarUltimoAmbito();
            } else {
                String nombreFuncion = $2.sval;
                salida.add("Linea " + $2.ival + ": Declaracion de Funcion '" + $2.sval + "' con retorno multiple.");
            }
        }
      ;

lista_tipos_retorno_multiple : tipo ',' tipo
                             {
                                 ArrayList<String> lista = new ArrayList<String>();
                                 lista.add($1.sval);
                                 lista.add($3.sval);
                                 $$.obj = lista;
                             }
                         |
                         lista_tipos_retorno_multiple ',' tipo
                             {
                                 ArrayList<?> rawList = (ArrayList<?>) $1.obj;
                                 ArrayList<String> lista = new ArrayList<String>();
                                 for (Object o : rawList) {
                                     lista.add((String) o);
                                 }
                                 lista.add($3.sval);
                                 $$.obj = lista;
                             }
                         ;

lista_parametros_formales : lista_parametros_formales ',' parametro_formal
                          |
                          parametro_formal
                          ;

parametro_formal : sem_pasaje tipo ID
             {
                 g.apilarParametro(new ParametroInfo($3.sval, $2.sval, $1.sval));
             }
             |
             tipo ID
             {
                 String pasajeDefault = "default_cv";
                 g.apilarParametro(new ParametroInfo($2.sval, $1.sval, pasajeDefault));
             }
             ;

sem_pasaje : CR SE { $$.sval = "cr_se"; }
           |
           CR LE { $$.sval = "cr_le"; }
           ;

sentencia_ejecutable : asignacion ';'
                     | asignacion_multiple ';'
                     | condicional_if
                     |
                     condicional_do_while
                     |
                     salida_pantalla ';'
                     | retorno_funcion
                     |
                     invocacion_funcion ';'
                     ;

asignacion : variable ASIG expresion
           {
               salida.add("Linea " + $1.ival + ": Asignacion simple (:=).");
               String op2_terceto = g.desapilarOperando();
               String op1_var = $1.sval;
               String tipoVar = g.getTipo(op1_var);
               String tipoExpr = g.getTipo(op2_terceto);
               int linea = $1.ival;
               if (tipoVar.equals("indefinido")) {
                   if (op1_var.contains(".")) {
                       String[] parts = op1_var.split("\\.", 2);
                       al.agregarErrorSemantico("Linea " + linea + ": Error Semantico: Variable '" + parts[1] + "' no existe en el ambito '" + parts[0] + "' o el ambito no es visible (Tema 23).");
                   } else {
                       al.agregarErrorSemantico("Linea " + linea + ": Error Semantico: Variable '" + op1_var + "' no fue declarada (Regla de alcance).");
                   }
               }
               else if (tipoExpr.equals("multiple")) {
                   String funcName = "";
                   boolean esFuncionValida = true;
                   try {
                        funcName = g.getTerceto(Integer.parseInt(op2_terceto.substring(1, op2_terceto.length()-1))).getOperando1();
                   } catch (Exception e) {
                        esFuncionValida = false;
                   }

                   if (!esFuncionValida) {
                        if (g.chequearAsignacion(tipoVar, tipoExpr, linea)) {
                            g.addTerceto(":=", op1_var, op2_terceto);
                        }
                   } else {
                       Object retMultiple = al.getAtributo(funcName, "RetornoMultiple");
                       if (retMultiple == null || !(Boolean)retMultiple) {
                            al.agregarErrorSemantico("Linea " + linea + ": Error Semantico: Asignacion de funcion '" + funcName + "' que no retorna 'multiple' a variable simple.");
                       } else {
                           Object rawObj = al.getAtributo(funcName, "TiposRetorno");
                           ArrayList<String> tiposRetorno = new ArrayList<String>();
                           if (rawObj instanceof ArrayList) {
                               for (Object o : (ArrayList<?>) rawObj) {
                                   tiposRetorno.add((String) o);
                               }
                           }

                           if (tiposRetorno.isEmpty()) {
                               al.agregarErrorSemantico("Linea " + linea + ": Error Semantico: Funcion '" + funcName +
                               "' marcada como 'multiple' pero no tiene lista de TiposRetorno.");
                           } else {
                               String tipoPrimerRetorno = tiposRetorno.get(0);
                               if (g.chequearAsignacion(tipoVar, tipoPrimerRetorno, linea)) {
                                   String retTerceto = g.addTerceto("GET_RET", op2_terceto, "0");
                                   g.getTerceto(Integer.parseInt(retTerceto.substring(1, retTerceto.length()-1))).setTipo(tipoPrimerRetorno);
                                   g.addTerceto(":=", op1_var, retTerceto);

                                   if (tiposRetorno.size() > 1) {
                                       al.agregarWarning("Linea " + linea + ": Warning (Tema 21): Funcion '" + funcName + "' retorna " + tiposRetorno.size() + " valores, pero solo se asigna 1. Se descartan los sobrantes.");
                                   }
                               }
                           }
                       }

                   }
               } else {
                   if (g.chequearAsignacion(tipoVar, tipoExpr, linea)) {
                       g.addTerceto(":=", op1_var, op2_terceto);
                   }
               }
           }
           ;

asignacion_multiple : lista_variables ASIG_MULTIPLE lado_derecho_multiple
                        {
                            String lineaActual = String.valueOf($2.ival);
                            int cantIzquierda = listaVariables.size();
                            int cantDerecha = contadorLadoDerecho;
                            Stack<String> derechos = g.getPilaLadoDerecho();

                            boolean esFuncion = false;
                            if (cantDerecha == 1) {
                                String op = derechos.peek();
                                if (op.startsWith("[")) {
                                    try {
                                        Terceto t = g.getTerceto(Integer.parseInt(op.substring(1, op.length()-1)));
                                        if (t.getOperador().equals("CALL")) {
                                            esFuncion = true;
                                        }
                                    } catch (Exception e) {
                                        esFuncion = false;
                                    }
                                }
                            }

                            if (esFuncion) {

                                  String funcTerceto = derechos.pop();
                                  if (funcTerceto.equals("ERROR_CALL") || funcTerceto.equals("ERROR_CALL_PARAMS") || funcTerceto.equals("ERROR_CALL_LAMBDA")) {
                                  } else {
                                      String funcName = g.getTerceto(Integer.parseInt(funcTerceto.substring(1, funcTerceto.length()-1))).getOperando1();
                                      Object retMultiple = al.getAtributo(funcName, "RetornoMultiple");
                                      if (retMultiple == null || !(Boolean)retMultiple) {
                                          al.agregarErrorSemantico("Linea " + lineaActual + ": Error Semantico: Asignacion multiple a funcion '" + funcName + "' que no tiene retorno multiple.");
                                      } else {
                                          Object rawObj = al.getAtributo(funcName, "TiposRetorno");
                                          ArrayList<String> tiposRetorno = new ArrayList<String>();
                                          if (rawObj instanceof ArrayList) {
                                              for (Object o : (ArrayList<?>) rawObj) {

                                                  tiposRetorno.add((String) o);
                                              }
                                          }
                                          int cantRetornos = tiposRetorno.size();
                                          if (cantRetornos < cantIzquierda) {
                                              al.agregarErrorSemantico("Linea " + lineaActual + ": Error Semantico (Tema 21): Asignacion multiple a funcion '" + funcName + "'. Insuficientes valores de retorno. Esperados: " + cantIzquierda + ", Retornados: " + cantRetornos + ".");
                                          } else {
                                              if (cantRetornos > cantIzquierda) {
                                                  al.agregarWarning("Linea "
                                                  + lineaActual + ": Warning (Tema 21): Funcion '" + funcName + "' retorna " + cantRetornos + " valores, pero solo se asignan " + cantIzquierda + ". Se descartan los sobrantes.");
                                              }
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
                                  }


                              } else {
                                  if (cantIzquierda != cantDerecha) {
                                      al.agregarErrorSemantico("Linea " + lineaActual + ": Error Semantico (Tema 19): La asignacion multiple debe tener el mismo numero de elementos a la izquierda (" + cantIzquierda + ") y a la derecha (" + cantDerecha + ").");
                                  } else {
                                      for (int i = cantIzquierda - 1; i >= 0; i--) {
                                          String var = listaVariables.get(i);
                                          String expr = derechos.pop();
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

lado_derecho_multiple : { g.clearLadoDerecho(); } factor
                          {
                              g.apilarLadoDerecho(g.desapilarOperando());
                              contadorLadoDerecho = 1;
                              $$.ival = $2.ival;
                              $$.sval = $2.sval;
                          }
                          |
                          lado_derecho_multiple ',' factor
                          {
                              g.apilarLadoDerecho(g.desapilarOperando());
                              contadorLadoDerecho++;
                              $$.ival = 0;
                          }
                          ;

variable : ID PUNTO ID
            {
                $$.sval = $1.sval + "."
                + $3.sval;
                $$.ival = $1.ival;
            }
         |
         ID
            {
                $$.sval = $1.sval;
                $$.ival = $1.ival;
            }
         ;

expresion : expresion '+' termino
            {
                String op2 = g.desapilarOperando();
                String op1 = g.desapilarOperando();
                String tipo = g.chequearTipos("+", g.getTipo(op1), g.getTipo(op2), $1.ival);
                String terceto = g.addTerceto("+", op1, op2);
                g.getTerceto(Integer.parseInt(terceto.substring(1, terceto.length()-1))).setTipo(tipo);
                g.apilarOperando(terceto);
                $$.ival = $1.ival;
            }
          |
          expresion '-' termino
            {
                String op2 = g.desapilarOperando();
                String op1 = g.desapilarOperando();
                String tipo = g.chequearTipos("-", g.getTipo(op1), g.getTipo(op2), $1.ival);
                String terceto = g.addTerceto("-", op1, op2);
                g.getTerceto(Integer.parseInt(terceto.substring(1, terceto.length()-1))).setTipo(tipo);
                g.apilarOperando(terceto);
                $$.ival = $1.ival;
            }
          |
          termino { $$.ival = $1.ival;
          }
          ;

termino : termino '*' factor
            {
                String op2 = g.desapilarOperando();
                String op1 = g.desapilarOperando();
                String tipo = g.chequearTipos("*", g.getTipo(op1), g.getTipo(op2), $1.ival);
                String terceto = g.addTerceto("*", op1, op2);
                g.getTerceto(Integer.parseInt(terceto.substring(1, terceto.length()-1))).setTipo(tipo);
                g.apilarOperando(terceto);
                $$.ival = $1.ival;
            }
        |
        termino '/' factor
            {
                String op2 = g.desapilarOperando();
                String op1 = g.desapilarOperando();
                String tipo = g.chequearTipos("/", g.getTipo(op1), g.getTipo(op2), $1.ival);
                String terceto = g.addTerceto("/", op1, op2);
                g.getTerceto(Integer.parseInt(terceto.substring(1, terceto.length()-1))).setTipo(tipo);
                g.apilarOperando(terceto);
                $$.ival = $1.ival;
            }
        | factor { $$.ival = $1.ival;
        }
        ;

factor : factor_no_funcion
       {
           $$.ival = $1.ival;
       }
       | invocacion_funcion
       {
           $$.ival = $1.ival;
           $$.sval = $1.sval;
           g.apilarOperando($1.sval);
       }
       ;

factor_no_funcion : variable
                  {
                      String varNombre = $1.sval;
                      String tipoVar = g.getTipo(varNombre);
                      if (tipoVar.equals("indefinido")) {
                          if (varNombre.contains(".")) {
                              String[] parts = varNombre.split("\\.", 2);
                              al.agregarErrorSemantico("Linea " + $1.ival + ": Error Semantico: Variable '" + parts[1] + "' no existe en el ambito '" + parts[0] + "' o el ambito no es visible (Tema 23).");
                          } else {
                              al.agregarErrorSemantico("Linea " + $1.ival + ": Error Semantico: Variable '" + varNombre + "' no fue declarada (Regla de alcance).");
                          }
                          g.apilarOperando("error_tipo");
                      } else {
                          Object pasaje = al.getAtributo(varNombre, "Pasaje");
                          if (pasaje != null && pasaje.toString().equals("cr_se") && !enSentenciaReturn) {
                              al.agregarErrorSemantico("Linea " + $1.ival + ": Error Semantico: El parametro '" + varNombre + "' es de solo escritura (se) y no puede ser leido.");
                              g.apilarOperando("error_tipo");
                          } else {
                              g.apilarOperando(varNombre);
                          }
                      }
                      $$.ival = $1.ival;
                  }
                  |
                  constante
                  {
                      g.apilarOperando($1.sval);
                      $$.ival = $1.ival;
                  }
                  |
                  conversion_explicita
                  { $$.ival = $1.ival;
                  }
                  ;

conversion_explicita : TOUI '(' expresion ')'
                {
                    salida.add("Linea " + $1.ival + ": Conversion explicita (toui).");
                    String op1 = g.desapilarOperando();
                    String tipoOp1 = g.getTipo(op1);

                    if (tipoOp1.equals("float")) {
                        String terceto = g.addTerceto("TOUI", op1);
                        g.getTerceto(Integer.parseInt(terceto.substring(1, terceto.length()-1))).setTipo("uint");
                        g.apilarOperando(terceto);
                    } else if (tipoOp1.equals("indefinido") || tipoOp1.equals("error_tipo")) {
                        g.apilarOperando("error_tipo");
                    } else {
                        al.agregarErrorSemantico("Linea " + $1.ival + ": Error Semantico: 'toui' solo puede aplicarse a expresiones 'float', se obtuvo '" + tipoOp1 + "'.");
                        g.apilarOperando("error_tipo");
                    }
                    $$.ival = $1.ival;
                }
                ;

pre_invocacion : { g.clearParametrosReales(); }
               ;

invocacion_funcion : ID pre_invocacion '(' lista_parametros_reales ')'
                   {
                       String funcName = $1.sval;
                       int linea = $1.ival;
                       int cantReales = $4.ival;
                       ArrayList<ParametroRealInfo> reales = g.getListaParametrosReales(cantReales);
                       Object uso = al.getAtributo(funcName, "Uso");
                       String tipo = g.getTipo(funcName);
                       if (uso != null && (uso.toString().equals("parametro") || uso.toString().equals("parametro_lambda") || uso.toString().equals("variable")) && tipo.equals("lambda")) {
                           if (reales.size() != 1) {
                               al.agregarErrorSemantico("Linea " + linea + ": Error Semantico (Tema 28): Invocacion de lambda '" + funcName + "' con numero incorrecto de parametros. Esperado: 1, Obtenido: " + reales.size() + ".");
                               $$.sval = "ERROR_CALL_LAMBDA";
                           } else {
                               g.addTerceto("PARAM_LAMBDA", reales.get(0).operando, "_");
                               String terceto = g.addTerceto("CALL_LAMBDA", funcName, "_");
                               g.getTerceto(Integer.parseInt(terceto.substring(1, terceto.length()-1))).setTipo("void");
                               $$.sval = terceto;
                           }
                       }
                       else if (uso != null && uso.toString().equals("funcion")) {
                           Object rawObj = al.getAtributo(funcName, "Parametros");
                           ArrayList<ParametroInfo> formales = null;
                           if (rawObj instanceof ArrayList) {
                               formales = new ArrayList<ParametroInfo>();
                               for (Object o : (ArrayList<?>) rawObj) {
                                   formales.add((ParametroInfo) o);
                               }
                           }
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
                                       if (tipoReal.equals("error_tipo")) {
                                            errorEnParametros = true;
                                       } else if (tipoFormal.equals("lambda") && tipoReal.equals("lambda_expr")) {
                                       } else if (!tipoFormal.equals("lambda") && tipoReal.equals("lambda_expr")) {
                                           al.agregarErrorSemantico("Linea " + linea + ": Error Semantico (Tema 28): Se paso una expresion lambda al parametro '->" + real.nombreFormal + "' que no es de tipo 'lambda'.");
                                           errorEnParametros = true;
                                       } else if (tipoFormal.equals("lambda") && !tipoReal.equals("lambda_expr")) {
                                           al.agregarErrorSemantico("Linea " + linea + ": Error Semantico (Tema 28): El parametro '->" + real.nombreFormal + "' espera una expresion 'lambda'.");
                                           errorEnParametros = true;
                                       } else if (!tipoReal.equals("error_tipo") && !g.chequearAsignacion(tipoFormal, tipoReal, linea)) {
                                           al.agregarErrorSemantico("Linea " + linea + ": Error Semantico: Invocacion a '" + funcName + "': tipo incompatible para '->" + real.nombreFormal + "'. Esperado: " + tipoFormal + ", Obtenido: " + tipoReal + ".");
                                           errorEnParametros = true;
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
                       $$.ival = $1.ival;
                   }
                   ;

lista_parametros_reales : lista_parametros_reales ',' parametro_real
                        { $$.ival = $1.ival + 1;
                        }
                        |
                        parametro_real
                        { $$.ival = 1;
                        }
                        ;

parametro_real : parametro_simple FLECHA parametro_simple
               {
                   String op1 = $1.sval;
                   String op2 = $3.sval;
                   if (Character.isUpperCase(op2.charAt(0))) {
                       g.apilarParametroReal(new ParametroRealInfo(op1, op2));
                   } else if (Character.isUpperCase(op1.charAt(0))) {
                       g.apilarParametroReal(new ParametroRealInfo(op2, op1));
                   } else {
                       al.agregarErrorSemantico("Linea " + $1.ival + ": Error Semantico: Se requiere un identificador valido como nombre de parametro.");
                   }
                   $$.ival = $1.ival;
               }
               |
               parametro_simple
               {
                   al.agregarErrorSemantico("Linea " + $1.ival + ": Error Semantico: Se requiere asignacion explicita de parametro (-> ID).");
                   $$.ival = $1.ival;
               }
               ;

parametro_simple : expresion
                 {
                     $$.sval = g.desapilarOperando();
                     $$.ival = $1.ival;
                 }
                 |
                 lambda_expresion
                 {
                     $$.sval = $1.sval;
                     $$.ival = $1.ival;
                 }
                 ;

lambda_expresion : '(' tipo ID ')' '{' {
                         pilaSaltosLambda.push(g.addTerceto("BI", "_", "_"));
                         int inicioLambda = g.getProximoTerceto();
                         $$.sval = "L" + String.valueOf(inicioLambda);
                         g.abrirAmbito("lambda_" + inicioLambda);
                         al.agregarLexemaTS($3.sval);
                         al.agregarAtributoLexema($3.sval, "Uso", "parametro_lambda");
                         al.agregarAtributoLexema($3.sval, "Tipo", $2.sval);
                         g.addTerceto("DEF_PARAM", $3.sval, "_");
                         $$.ival = $1.ival;
                 } cuerpo_lambda '}' {
                         g.addTerceto("RET_LAMBDA", "_", "_");
                         int tercetoFin = g.getProximoTerceto();
                         String saltoIncondicional = pilaSaltosLambda.pop();
                         // Se agrega corchetes al destino del salto para mantener consistencia visual
                         g.modificarSaltoTerceto(Integer.parseInt(saltoIncondicional.substring(1, saltoIncondicional.length()-1)), "[" + tercetoFin + "]");
                         g.cerrarAmbito();
                 }
                 ;

cuerpo_lambda : sentencias_ejecutables_lista
              ;

sentencias_ejecutables_lista : sentencias_ejecutables_lista sentencia_ejecutable
                             |
                             sentencia_ejecutable
                             ;

constante : CTE
            {
                $$.sval = $1.sval;
                $$.ival = $1.ival;
            }
          |
          '-' CTE
            {
                String lexemaPositivo = $2.sval;
                String lexemaNegativo = "-" + lexemaPositivo;
                if (al.getAtributo(lexemaPositivo, "Tipo") != null) {
                    String tipo = (String) al.getAtributo(lexemaPositivo, "Tipo");
                    if (tipo != null) {
                        if (tipo.equals("uint")) {
                            al.agregarErrorSemantico("Linea " + $2.ival + ": Error Semantico. El tipo 'uint' no puede ser negativo. Valor: " + lexemaNegativo);
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
                $$.sval = lexemaNegativo;
                $$.ival = $2.ival;
            }
          ;

condicional_if : IF '(' condicion ')' bloque_ejecutable ENDIF %prec IFX ';'
               {
                    Object lineaObj = al.getAtributo("if", "Linea");
                    String linea = (lineaObj != null) ?
                    lineaObj.toString() : "?";
                    salida.add("Linea " + linea + ": Sentencia IF reconocida.");
               }
               |
               IF '(' condicion ')' bloque_ejecutable ELSE bloque_ejecutable ENDIF ';'
               {
                    Object lineaObj = al.getAtributo("if", "Linea");
                    String linea = (lineaObj != null) ? lineaObj.toString() : "?";
                    salida.add("Linea " + linea + ": Sentencia IF-ELSE reconocida.");
               }
               ;

condicional_do_while: DO { g.apilarControl(g.getProximoTerceto());
                    } bloque_ejecutable WHILE '(' condicion ')' ';'
                    {
                        Object lineaObj = al.getAtributo("do", "Linea");
                        salida.add("Linea " + $7.ival + ": Sentencia DO-WHILE reconocida.");
                        String refCondicion = g.desapilarOperando();
                        int inicioBucle = g.desapilarControl();
                        if (refCondicion.equals("ERROR_CONDICION")) {
                             al.agregarErrorSemantico("Linea " + $7.ival + ": Error Semantico: No se genero el salto del DO-WHILE debido a una condicion invalida.");
                        } else {
                            // Agregamos corchetes al destino del salto para consistencia con referencias
                            String tercetoSalto = g.addTerceto("BT", refCondicion, "[" + inicioBucle + "]");
                        }
                    }
                    ;

condicion : expresion simbolo_comparacion expresion
          {
                String op2 = g.desapilarOperando();
                String op = g.desapilarOperando();
                String op1 = g.desapilarOperando();
                String tipo = g.chequearTipos(op, g.getTipo(op1), g.getTipo(op2), $1.ival);
                if (!tipo.equals("error_tipo")) {
                    String terceto = g.addTerceto(op, op1, op2);
                    g.getTerceto(Integer.parseInt(terceto.substring(1, terceto.length()-1))).setTipo("boolean");
                    g.apilarOperando(terceto);
                } else {
                    g.apilarOperando("ERROR_CONDICION");
                }
          }
          ;

simbolo_comparacion : MAYOR_IGUAL { g.apilarOperando(">="); }
                    |
                    MENOR_IGUAL { g.apilarOperando("<="); }
                    |
                    DISTINTO { g.apilarOperando("=!"); }
                    |
                    IGUAL_IGUAL { g.apilarOperando("=="); }
                    |
                    '>' { g.apilarOperando(">"); }
                    |
                    '<' { g.apilarOperando("<"); }
                    ;

bloque_ejecutable : '{' { g.abrirAmbito("bloque_" + g.getProximoTerceto()); } sentencias_ejecutables_lista '}' { g.cerrarAmbito();
                  }
                  |
                  '{' error '}'
                  ;

salida_pantalla : PRINT '(' CADENA_MULTILINEA ')'
                {
                    salida.add("Linea " + $1.ival + ": PRINT con cadena multilinea.");
                    g.addTerceto("PRINT", $3.sval);
                }
                |
                PRINT '(' expresion ')'
                {
                    salida.add("Linea " + $1.ival + ": PRINT con expresion.");
                    g.addTerceto("PRINT", g.desapilarOperando());
                }
                ;

retorno_funcion : RETURN '(' { enSentenciaReturn = true; } lista_expresiones ')' ';'
            {
                enSentenciaReturn = false;
                ArrayList<String> tiposEsperados = pilaTiposRetorno.peek();
                ArrayList<?> rawList = (ArrayList<?>) $4.obj;
                ArrayList<String> expresiones = new ArrayList<String>();
                for (Object o : rawList) {
                    expresiones.add((String) o);
                }

                boolean error = false;
                if (tiposEsperados.size() != expresiones.size()) {
                    al.agregarErrorSemantico("Linea " + $1.ival + ": Error de Tipos: Cantidad de valores de retorno incorrecta. Esperado: " + tiposEsperados.size() + ", Encontrado: " + expresiones.size());
                    error = true;
                } else {
                    for (int i = 0; i < tiposEsperados.size(); i++) {
                        String tipoEsp = tiposEsperados.get(i);
                        String op = expresiones.get(i);
                        String tipoEnc = g.getTipo(op);

                        if (!tipoEnc.equals(tipoEsp) && !tipoEnc.equals("error_tipo")) {
                             al.agregarErrorSemantico("Linea " + $1.ival + ": Error de Tipos: Tipo de retorno incorrecto en la posicion " + (i+1) + ". Esperado: " + tipoEsp + ", Encontrado: " + tipoEnc);
                             error = true;
                        }
                    }
                }

                if (error) {
                    pilaErrorEnFuncion.pop();
                    pilaErrorEnFuncion.push(true);
                } else {
                    salida.add("Linea " + $1.ival + ": Sentencia RETURN.");
                    for (String exprTerceto : expresiones) {
                        g.addTerceto("RETURN", exprTerceto);
                    }
                }
            }
            ;

lista_expresiones : lista_expresiones ',' expresion
              {
                  ArrayList<?> rawList = (ArrayList<?>) $1.obj;
                  ArrayList<String> lista = new ArrayList<String>();
                  for (Object o : rawList) {
                      lista.add((String) o);
                  }
                  lista.add(g.desapilarOperando());
                  $$.obj = lista;
              }
              |
              expresion
              {
                  ArrayList<String> lista = new ArrayList<String>();
                  lista.add(g.desapilarOperando());
                  $$.obj = lista;
              }
              ;

%%

static AnalizadorLexico al;
static Generador g;
ArrayList<String> erroresSintacticos = new ArrayList<String>();
ArrayList<String> erroresSemanticos = new ArrayList<String>();
ArrayList<String> salida = new ArrayList<String>();
ArrayList<String> listaVariables = new ArrayList<String>();
int contadorLadoDerecho = 0;
Stack<String> pilaSaltosLambda = new Stack<String>();
static boolean enSentenciaReturn = false;
static Stack<ArrayList<String>> pilaTiposRetorno = new Stack<ArrayList<String>>();
static Stack<Boolean> pilaErrorEnFuncion = new Stack<Boolean>();
static Stack<Integer> pilaInicioFuncion = new Stack<Integer>();

int yylex() {
    int token = al.yylex();
    String lexema = al.getLexema();
    int linea = al.getContadorFila() + 1;
    if (token == ID || token == CTE || token == CADENA_MULTILINEA) {
        yylval = new ParserVal(lexema);
        yylval.ival = linea;
    } else {
        yylval = new ParserVal(token);
        yylval.ival = linea;
    }
    return token;
}

public void yyerror(String e) {
   erroresSintacticos.add("Linea " + (al.getContadorFila() + 1) + ": Error de sintaxis. Verifique la estructura del codigo.");
}

public static void main(String args[]){
    if(args.length == 1) {
        al = new AnalizadorLexico(args[0]);
        g = Generador.getInstance();
        g.setAnalizadorLexico(al);
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
        if (al.getErroresSemanticos().isEmpty()) {
            System.out.println("No se encontraron errores semanticos.");
        } else {
            for (String s : al.getErroresSemanticos()) {
                System.out.println(s);
            }
        }

        System.out.println("\n=======================================================");
        System.out.println("## WARNINGS DETECTADOS ##");
        System.out.println("=======================================================");
        if (al.getWarnings() == null || al.getWarnings().isEmpty()) {
            System.out.println("No se encontraron warnings.");
        } else {
            for (String s : al.getWarnings()) {
                System.out.println(s);
            }
        }

        g.imprimirTercetos();
        al.imprimirTablaSimbolos();

        System.out.println("=======================================================");
    } else {
        System.out.println("Error: Se requiere la ruta del archivo fuente como unico parametro.");
    }
}