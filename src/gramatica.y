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
    import java.util.Stack;
%}

%token ID CTE IF ELSE FLOAT ENDIF RETURN PRINT UINT VAR DO WHILE LAMBDA CADENA_MULTILINEA ASIG_MULTIPLE CR SE LE TOUI ASIG FLECHA MAYOR_IGUAL MENOR_IGUAL DISTINTO IGUAL_IGUAL

%right ASIG ASIG_MULTIPLE
%left '+' '-'
%left '*' '/'
%right UMINUS

%%

programa : ID '{' sentencias '}'
      {
          salida.add("Linea " + (al.getContadorFila()+1) + ": Programa '" + $1.sval + "' reconocido.");
          al.agregarAtributoLexema($1.sval, "Uso", "Programa");
      }
     | '{' sentencias '}' {erroresSintacticos.add("Linea " + (al.getContadorFila()+1) + ": Error sintactico: Falta el nombre del programa.");}
     |
ID sentencias '}' {erroresSintacticos.add("Linea " + (al.getContadorFila()+1) + ": Error sintactico: Falta el delimitador '{' al inicio del programa.");}
     |
ID '{' sentencias {erroresSintacticos.add("Linea " + (al.getContadorFila()+1) + ": Error sintactico: Falta el delimitador '}' al final del programa.");}
     ;
sentencias : sentencias sentencia
           |
sentencia
           ;
sentencia : sentencia_declarativa
          |
sentencia_ejecutable
          | error ';'
{erroresSintacticos.add("Linea " + (al.getContadorFila()+1) + ": Error sintactico en la sentencia.");}
          ;
<<<<<<< Updated upstream
sentencia_declarativa : declaracion_tipica ';'
                      | funcion
                      |
declaracion_var ';'
                      ;

declaracion_tipica : tipo lista_variables
            {
                for (String var : listaVariables) {
                    if (al.getTablaSimbolos().containsKey(var) && al.getAtributo(var, "Uso") != null) {
                        erroresSemanticos.add("Linea " + (al.getContadorFila()+1) + ": Error Semantico. Redeclaracion de variable '" + var + "'.");
                    } else {
                        al.agregarAtributoLexema(var, "Tipo", $1.sval);
                        al.agregarAtributoLexema(var, "Uso", "Identificador");
                    }
                }
                salida.add("Linea " + (al.getContadorFila()+1) + ": Declaracion de variables de tipo '" + $1.sval + "'.");
                listaVariables.clear();
            }
            ;
=======

// BORRAR DECLARACION TIPICA [DONE]
sentencia_declarativa : funcion
                      | declaracion_var ';'
                      ;

// RETORNAR ERROR
>>>>>>> Stashed changes
declaracion_var : VAR variable ASIG expresion
                {
                    String tipoExp = gen.getTipo($4.sval);

                    if (!tipoExp.equals("ERROR")) {
                        if (al.getTablaSimbolos().containsKey($2.sval) && al.getAtributo($2.sval, "Uso") != null) {
                             erroresSemanticos.add("Linea " + (al.getContadorFila()+1) + ": Error Semantico (Tema 9). Redeclaracion de variable '" + $2.sval + "'.");
                        } else {
                             al.agregarAtributoLexema($2.sval, "Tipo", tipoExp);
                             al.agregarAtributoLexema($2.sval, "Uso", "Identificador");
                        }
                    }

                    gen.crearTerceto(":=", $2.sval, $4.sval);
                    salida.add("Linea " + (al.getContadorFila()+1) + ": Declaracion por inferencia (var).");
                }
                ;
tipo : UINT { $$.sval = "uint"; }
     | FLOAT { $$.sval = "float";
}
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
<<<<<<< Updated upstream
=======

// REHACER
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
                             |
lista_tipos_retorno_multiple ',' tipo
=======
                             | lista_tipos_retorno_multiple ',' tipo // A CHEQUEAR
>>>>>>> Stashed changes
                             ;
lista_parametros_formales : lista_parametros_formales ',' parametro_formal
                          |
parametro_formal
                          ;
parametro_formal : sem_pasaje tipo ID
                 {
                    al.agregarAtributoLexema($3.sval, "Tipo", $2.sval);
                    al.agregarAtributoLexema($3.sval, "Uso", "Parametro");
                    al.agregarAtributoLexema($3.sval, "Pasaje", $1.sval);
                 }
                 |
tipo ID
                 {
                    al.agregarAtributoLexema($2.sval, "Tipo", $1.sval);
                    al.agregarAtributoLexema($2.sval, "Uso", "Parametro");
                    al.agregarAtributoLexema($2.sval, "Pasaje", "CV");
                 }
                 ;
sem_pasaje : CR SE { $$.sval = "CR SE"; }
           |
CR LE { $$.sval = "CR LE"; }
           ;

sentencia_ejecutable : asignacion ';'
                     | asignacion_multiple ';'
| condicional_if
                     |
condicional_do_while
                     |
salida_pantalla ';'
                     | retorno_funcion
                     ;
<<<<<<< Updated upstream
=======

// FALTA VAR
>>>>>>> Stashed changes
asignacion : variable ASIG expresion
           {
               String tipoRes = gen.chequearTipos($1.sval, $3.sval, ":=", erroresSemanticos);
               gen.crearTerceto(":=", $1.sval, $3.sval);
               salida.add("Linea " + (al.getContadorFila()+1) + ": Asignacion simple (:=).");
}
           ;

<<<<<<< Updated upstream
asignacion_multiple : lista_variables ASIG_MULTIPLE lista_elementos_restringidos
=======
// CHEQUEO DE CANTIDAD DE TERMINOS DE CADA LADO
asignacion_multiple : lista_variables ASIG_MULTIPLE lado_derecho_multiple
>>>>>>> Stashed changes
                    {
                        if (listaVariables.size() != listaLadoDerecho.size()) {
                            erroresSemanticos.add("Linea " + (al.getContadorFila()+1) + ": Error Semantico (Tema 19). La asignacion multiple debe tener el mismo numero de elementos en ambos lados. Izquierda: " + listaVariables.size() + ", Derecha: " + listaLadoDerecho.size() + ".");
                        } else {
                            for (int i = 0; i < listaVariables.size(); i++) {
                                String var = listaVariables.get(i);
                                String exp = listaLadoDerecho.get(i);

                                gen.chequearTipos(var, exp, ":=", erroresSemanticos);
                                gen.crearTerceto(":=", var, exp);
                            }
                        }
                        listaVariables.clear();
                        listaLadoDerecho.clear();
                        salida.add("Linea " + (al.getContadorFila()+1) + ": Asignacion multiple (=).");
}
                    ;

lista_elementos_restringidos : lista_elementos_restringidos ',' factor
                             {
                                 listaLadoDerecho.add($3.sval);
                             }
                             |
factor
                             {
                                 listaLadoDerecho.clear();
                                 listaLadoDerecho.add($1.sval);
                             }
                             ;
<<<<<<< Updated upstream
variable : ID '.' ID {
                $$.sval = $1.sval + "." + $3.sval;
}
         | ID { $$.sval = $1.sval;
}
=======

// CHEQUEAR
variable : ID '.' ID { $$.sval = $1.sval + "." + $3.sval; }
         | ID { $$.sval = $1.sval; }
>>>>>>> Stashed changes
         ;
expresion : expresion '+' termino
          {
              String tipoRes = gen.chequearTipos($1.sval, $3.sval, "+", erroresSemanticos);
              $$.sval = gen.crearTerceto("+", $1.sval, $3.sval);
              gen.getTerceto(Integer.parseInt($$.sval.substring(1, $$.sval.length()-1))).setTipo(tipoRes);
          }
          |
expresion '-' termino
          {
              String tipoRes = gen.chequearTipos($1.sval, $3.sval, "-", erroresSemanticos);
              $$.sval = gen.crearTerceto("-", $1.sval, $3.sval);
              gen.getTerceto(Integer.parseInt($$.sval.substring(1, $$.sval.length()-1))).setTipo(tipoRes);
          }
          |
termino
          { $$ = $1;
}
          ;
termino : termino '*' factor
        {
            String tipoRes = gen.chequearTipos($1.sval, $3.sval, "*", erroresSemanticos);
            $$.sval = gen.crearTerceto("*", $1.sval, $3.sval);
            gen.getTerceto(Integer.parseInt($$.sval.substring(1, $$.sval.length()-1))).setTipo(tipoRes);
        }
        |
termino '/' factor
        {
            String tipoRes = gen.chequearTipos($1.sval, $3.sval, "/", erroresSemanticos);
            $$.sval = gen.crearTerceto("/", $1.sval, $3.sval);
            gen.getTerceto(Integer.parseInt($$.sval.substring(1, $$.sval.length()-1))).setTipo(tipoRes);
        }
        |
factor
        { $$ = $1;
}
        ;
factor : variable
       { $$ = $1;
}
       | constante
<<<<<<< Updated upstream
       { $$ = $1;
}
       |
invocacion_funcion
       { $$ = $1;
}
       | conversion_explicita
       { $$ = $1;
}
       | '(' expresion ')'
       { $$ = $2;
}
=======
       | invocacion_funcion
       | conversion_explicita // LEER TEMA 19
>>>>>>> Stashed changes
       ;
conversion_explicita : TOUI '(' expresion ')'
                {
                    String tipoRes = gen.chequearTipos($3.sval, null, "TOUI", erroresSemanticos);
                    $$.sval = gen.crearTerceto("TOUI", $3.sval);
                    gen.getTerceto(Integer.parseInt($$.sval.substring(1, $$.sval.length()-1))).setTipo(tipoRes);
                    salida.add("Linea " + (al.getContadorFila()+1) + ": Conversion explicita (toui).");
}
                ;
invocacion_funcion : ID '(' lista_parametros_reales ')'
                   ;
lista_parametros_reales : lista_parametros_reales ',' parametro_real
                        |
parametro_real
                        ;
parametro_real : parametro_simple FLECHA ID
               ;
parametro_simple : expresion
                 |
lambda_expresion
                 ;
lambda_expresion : '(' tipo ID ')' '{' cuerpo_lambda '}'
                 ;
<<<<<<< Updated upstream
=======

// CHEQUEAR CUERPO VACIO
>>>>>>> Stashed changes
cuerpo_lambda : sentencias_ejecutables_lista
              |
              ;
sentencias_ejecutables_lista : sentencias_ejecutables_lista sentencia_ejecutable
                             |
sentencia_ejecutable
                             ;
constante : CTE
          { $$ = $1;
}
          |
'-' CTE %prec UMINUS
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
                $$.sval = lexemaNegativo;
            }
          ;

<<<<<<< Updated upstream
condicional_if : IF '(' condicion ')' bloque_ejecutable ENDIF ';'
               {
                   String refBF = gen.crearTerceto("BF", $3.sval);
                   gen.apilar();

                   int indexBF = gen.desapilar();
                   gen.completarSalto(indexBF, gen.getProximoNumero());
               }
               |
IF '(' condicion ')' bloque_ejecutable ELSE bloque_ejecutable ENDIF ';'
               {
                   String refBF = gen.crearTerceto("BF", $3.sval);
                   int indexBF = gen.getProximoNumero() - 1;
                   gen.apilar(indexBF);

                   String refBI = gen.crearTerceto("BI", null);
                   int indexBI = gen.getProximoNumero() - 1;
                   gen.apilar(indexBI);
                   gen.completarSalto(indexBF, gen.getProximoNumero());

                   gen.completarSalto(indexBI, gen.getProximoNumero());

                   gen.desapilar();
                   gen.desapilar();
               }
               ;

condicional_do_while: DO marcador_inicio_do bloque_ejecutable WHILE '(' condicion ')' ';'
{
                        int inicioDo = gen.desapilar();
                        gen.crearTerceto("BT", $7.sval, String.valueOf(inicioDo));
=======
// CHEQUEAR PORQUE NO IMPRIME TODO
condicional_if : IF '(' condicion ')' bloque_ejecutable ENDIF ';' %prec IFX // CHEQUEAR PREC // RECONOCER SENTENCIA IF
               | IF '(' condicion ')' bloque_ejecutable ELSE bloque_ejecutable ENDIF ';'
               ;

// AGREGAR MAS ERRORES
condicional_do_while: DO bloque_ejecutable WHILE '(' condicion ')' ';'
                    {
>>>>>>> Stashed changes
                        salida.add("Linea " + (al.getContadorFila()+1) + ": Sentencia DO-WHILE reconocida.");
}
                    |
DO marcador_inicio_do bloque_ejecutable WHILE '(' condicion ')'
                    {
                        yyerror("Linea " + al.getContadorFila() + ": Error Sintactico. Falta punto y coma ';' al final de la sentencia DO-WHILE.");
                        gen.desapilar();
}
                    ;

marcador_inicio_do:
                    {
                        gen.apilar(gen.getProximoNumero());
                    }
                    ;

condicion : expresion simbolo_comparacion expresion
          {
              String tipoRes = gen.chequearTipos($1.sval, $3.sval, $2.sval, erroresSemanticos);
              $$.sval = gen.crearTerceto($2.sval, $1.sval, $3.sval);
              gen.getTerceto(Integer.parseInt($$.sval.substring(1, $$.sval.length()-1))).setTipo(tipoRes);
          }
          ;
simbolo_comparacion : MAYOR_IGUAL { $$.sval = ">="; }
                    |
MENOR_IGUAL { $$.sval = "<="; }
                    |
DISTINTO { $$.sval = "=!"; }
                    |
IGUAL_IGUAL { $$.sval = "=="; }
                    |
'>' { $$.sval = ">"; }
                    |
'<' { $$.sval = "<"; }
                    ;
bloque_ejecutable : '{' sentencias_ejecutables_lista '}'
<<<<<<< Updated upstream
                  |
sentencia_ejecutable
                  |
'{' error '}'
=======
                  | sentencia_ejecutable // BORRAR ?
                  | '{' error '}' // CHEQUEAR
>>>>>>> Stashed changes
                  ;
salida_pantalla : PRINT '(' CADENA_MULTILINEA ')'
                {
                    gen.crearTerceto("PRINT", $3.sval);
                    salida.add("Linea " + (al.getContadorFila()+1) + ": PRINT con cadena multilinea.");
}
                |
PRINT '(' expresion ')'
                {
                    gen.crearTerceto("PRINT", $3.sval);
                    salida.add("Linea " + (al.getContadorFila()+1) + ": PRINT con expresion.");
}
                ;
retorno_funcion : RETURN '(' lista_expresiones ')' ';'
                {
                    gen.crearTerceto("RETURN", $3.sval);
                    salida.add("Linea " + (al.getContadorFila()+1) + ": Sentencia RETURN.");
}
                ;
lista_expresiones : lista_expresiones ',' expresion
                  {
                      $$ = $3;
                  }
                  |
expresion
                  { $$ = $1;
}
                  ;
%%

static AnalizadorLexico al;
static Generador gen;
ArrayList<String> erroresSintacticos = new ArrayList<String>();
ArrayList<String> erroresSemanticos = new ArrayList<String>();
ArrayList<String> salida = new ArrayList<String>();
ArrayList<String> listaVariables = new ArrayList<String>();
ArrayList<String> listaLadoDerecho = new ArrayList<String>();

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
        gen = new Generador(al);
Parser par = new Parser(false);
        par.al = al;
        par.gen = gen;

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
        System.out.println("## TERCETOS GENERADOS ##");
        System.out.println("=======================================================");
        ArrayList<Terceto> tercetos = gen.getTercetos();
        if (tercetos.isEmpty()) {
            System.out.println("No se generaron tercetos.");
        } else {
            for (Terceto t : tercetos) {
                System.out.println(t.toString());
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
<<<<<<< Updated upstream
if (ts.isEmpty()) {
            System.out.println("La tabla de simbolos esta vacia.");
} else {
            for (Map.Entry<String, HashMap<String, Object>> entry : ts.entrySet()) {
                System.out.println("Lexema: " + entry.getKey());
for (Map.Entry<String, Object> atributos : entry.getValue().entrySet()) {
                    System.out.println("\t-> " + atributos.getKey() + ": " + atributos.getValue().toString());
}
=======

        if (ts.isEmpty()) {
            System.out.println("La tabla de simbolos esta vacia.");
        } else {
            // 1. Definir el formato de la tabla y las cabeceras
            // Ajusta los números (ej: %-30s) si necesitas más o menos espacio por columna
            String formatString = "| %-30s | %-12s | %-18s | %-10s | %-10s |%n";

            // 2. Imprimir la cabecera
            System.out.printf(formatString, "Lexema", "Reservada", "Uso", "Tipo", "Contador");
            System.out.println("|--------------------------------|--------------|--------------------|------------|------------|");

            // 3. Iterar e imprimir cada fila
            for (Map.Entry<String, HashMap<String, Object>> entry : ts.entrySet()) {
               String lexema = entry.getKey();
               HashMap<String, Object> atributos = entry.getValue();

               // Obtener cada atributo. Si no existe en el map, .get() devuelve null
               Object reservada = atributos.get("Reservada");
               Object uso = atributos.get("Uso");
               Object tipo = atributos.get("Tipo");
               Object contador = atributos.get("Contador");

               // 4. Imprimir la fila formateada
               // Usamos un ternario para imprimir "null" si el valor no existe
               System.out.printf(formatString,
                    lexema,
                    (reservada != null) ? reservada.toString() : "null",
                    (uso != null) ? uso.toString() : "null",
                    (tipo != null) ? tipo.toString() : "null",
                    (contador != null) ? contador.toString() : "null"
                );
>>>>>>> Stashed changes
            }
        }

        System.out.println("=======================================================");
} else {
        System.out.println("Error: Se requiere la ruta del archivo fuente como unico parametro.");
}
}