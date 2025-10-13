//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "gramatica.y"
    import java.io.File;
    import java.io.IOException;
    import java.util.ArrayList;
    import java.io.PrintWriter;
    import java.io.FileWriter;
    import java.io.FileNotFoundException;
    import java.util.HashMap;
    import java.util.Map;
    import java.lang.StringBuilder;
//#line 27 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short ID=257;
public final static short CTE=258;
public final static short IF=259;
public final static short ELSE=260;
public final static short FLOAT=261;
public final static short ENDIF=262;
public final static short RETURN=263;
public final static short PRINT=264;
public final static short UINT=265;
public final static short VAR=266;
public final static short DO=267;
public final static short WHILE=268;
public final static short LAMBDA=269;
public final static short CADENA_MULTILINEA=270;
public final static short ASIG_MULTIPLE=271;
public final static short CR=272;
public final static short SE=273;
public final static short LE=274;
public final static short TOUI=275;
public final static short ASIG=276;
public final static short FLECHA=277;
public final static short MAYOR_IGUAL=278;
public final static short MENOR_IGUAL=279;
public final static short DISTINTO=280;
public final static short IFX=281;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    1,    1,    2,    2,    2,    3,
    3,    3,    5,    7,    8,    8,    9,    9,    6,    6,
   13,   13,   12,   12,   14,   15,   15,   15,    4,    4,
    4,    4,    4,    4,   10,   16,   22,   23,   23,   11,
   11,   21,   21,   21,   25,   25,   25,   24,   24,   24,
   24,   28,   27,   29,   29,   30,   31,   31,   32,   26,
   26,   17,   17,   18,   33,   35,   35,   35,   35,   35,
   35,   34,   19,   19,   20,   36,   36,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    2,    1,    1,    1,    2,    2,
    1,    2,    2,    2,    1,    1,    3,    1,    8,    8,
    3,    3,    3,    1,    3,    2,    2,    0,    2,    2,
    1,    1,    2,    1,    3,    3,    1,    3,    1,    1,
    3,    3,    3,    1,    3,    3,    1,    1,    1,    1,
    1,    4,    4,    3,    1,    3,    1,    1,    7,    1,
    2,    7,    9,    7,    3,    1,    1,    1,    1,    1,
    1,    3,    4,    4,    5,    3,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,   16,    0,    0,   15,
    0,    0,    0,    0,    6,    7,    8,    0,   11,    0,
    0,    0,    0,    0,    0,    0,   31,   32,    0,   34,
    0,    9,    0,    0,    0,    0,   14,    0,    0,    0,
    0,    3,    5,   10,   12,    0,    0,    0,   18,    0,
    0,   29,    0,    0,    0,   30,   33,    2,   41,    0,
   60,    0,    0,   48,    0,   47,    0,   49,   50,   51,
    0,    0,    0,    0,    0,    0,    0,    1,    0,   21,
   36,    0,   39,   17,    0,    0,   22,    0,    0,   61,
   66,   67,   68,    0,    0,   69,   70,   71,    0,    0,
    0,    0,    0,    0,   73,   74,   72,    0,    0,    0,
   24,    0,    0,    0,    0,    0,    0,   55,    0,   58,
    0,    0,    0,    0,   45,   46,    0,    0,   75,    0,
   26,   27,    0,    0,    0,   38,    0,    0,    0,   53,
    0,   52,    0,    0,    0,   23,    0,   25,    0,    0,
   54,   56,    0,   62,   64,    0,    0,    0,    0,   19,
   20,    0,   63,    0,   59,
};
final static short yydgoto[] = {                          3,
   14,   15,   16,   17,   18,   19,   20,   21,   22,   23,
   64,  110,   25,  111,  112,   26,   27,   28,   29,   30,
   65,   81,   82,   66,   67,   68,   69,   70,  117,  118,
  119,  120,   71,   40,   99,   73,
};
final static short yysindex[] = {                       -79,
  -90, -147,    0,   20,   18,   41,    0,   51,   75,    0,
 -122,   17, -147,  -66,    0,    0,    0,   83,    0,   85,
  -33,   -4,   87, -176,  -28,   88,    0,    0,   90,    0,
   23,    0, -116,    3,    3,  -30,    0, -176, -147, -123,
   35,    0,    0,    0,    0,   22, -218,  106,    0,    3,
 -122,    0,    3,  111, -218,    0,    0,    0,    0,   25,
    0,  112, -105,    0,   -6,    0,   36,    0,    0,    0,
  113,   68,   55,  114,   49,   47,  116,    0, -115,    0,
    0,  115,    0,    0,   68, -115,    0,  -23,    3,    0,
    0,    0,    0,    3,    3,    0,    0,    0,    3,    3,
    3,   17,    3,   99,    0,    0,    0,    3, -188,   80,
    0, -218,    3,   81, -218,   68,   82,    0, -114,    0,
   61,   36,   36,   68,    0,    0, -157,   68,    0,  120,
    0,    0, -115,   39,  -93,    0,   42,  -89,  -23,    0,
  -87,    0,   17,  121,  122,    0, -147,    0, -147,  141,
    0,    0,  -78,    0,    0,   60,   77,   63,  124,    0,
    0, -147,    0,   89,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  -17,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    5,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  187,    0,    0,    0,    0,    1,    0,  133,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -41,
    0,    0,    0,    0,    0,    0,  -36,    0,    0,    0,
    0,   86,    0,    0,    0,    0,    0,    0, -181,    0,
    0,  135,    0,    0,  145, -181,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -70,    0,    0,    0,    0,
    0,  -31,   -9,  168,    0,    0,    0,   92,    0,    0,
    0,    0, -181,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   59,   32,    0,    0,    0,    0,    0,   19,  189,  200,
   56,  129,    0,   84,    0,    0,    0,    0,    0,    0,
   40,    0,    0,  -12,   43,    0,    0,    0,    0,   91,
    0,    0,  108,  -61,    0,    0,
};
final static int YYTABLESIZE=356;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         40,
   40,   40,   40,   40,   44,   40,   44,   44,   44,   42,
   47,   42,   42,   42,   63,   55,  115,   40,   40,   40,
   40,   63,   44,   44,   44,   44,   40,   42,   42,   42,
   42,   43,   13,   43,   43,   43,   94,   83,   95,   51,
  127,   40,    7,    2,   40,   43,   10,   63,   18,   43,
   43,   43,   43,   98,   96,   97,   24,   24,   42,   40,
   31,   79,   43,   33,   88,   80,   38,   33,   24,   24,
   33,   41,   43,   87,   72,   75,   49,  100,   32,   28,
   34,  153,  101,   28,  131,  132,   24,  125,  126,  106,
   35,   94,   85,   95,   24,  104,   24,   76,  103,   53,
  136,  142,  143,   94,  144,   95,   84,   43,    4,    5,
   94,    6,   95,    7,   36,    8,    9,   10,   11,   12,
  134,  137,  140,  133,  133,  139,   77,  116,  121,   77,
  135,   24,   76,  138,    5,   76,  122,  123,  124,   39,
   59,   44,  128,   45,   77,   52,   56,   58,   57,   51,
   86,   89,   90,  102,  105,  108,  109,  129,  113,   78,
  145,  147,  141,  148,  149,    4,    5,  150,    6,  152,
    7,  107,    8,    9,   10,   11,   12,    1,  116,  154,
  155,  158,  163,  159,  160,  162,    4,   43,   43,    4,
    5,   13,    6,   37,    7,   43,    8,    9,   10,   11,
   12,  161,   24,   35,   24,  156,   57,  157,   65,   48,
   37,   24,   24,  165,  114,  130,  146,   24,    0,   24,
  164,    0,    0,   46,    0,    0,   60,   61,   54,  151,
    0,    0,    0,   60,   61,   40,   40,   40,   40,   74,
   44,   44,   44,   44,   62,   42,   42,   42,   42,    0,
    0,   62,    0,   40,    0,    0,    0,    0,   40,   60,
   61,    0,    0,    0,    0,    0,   50,   43,   43,   43,
   43,   91,   92,   93,    0,   18,    0,   62,    4,    5,
    0,    6,    0,    7,    0,    8,    9,   10,   11,   12,
    4,    5,    0,    6,    0,    7,    0,    8,    9,   10,
   11,   12,    4,    5,    0,    6,    0,    7,    0,    8,
    9,   10,   11,   12,    0,    4,    5,    0,    6,    0,
    7,    0,    8,    9,   10,   11,   12,    0,    0,    0,
    0,    0,    4,    5,    0,    6,    0,    7,    0,    8,
    9,   10,   11,   12,    4,    5,    0,    6,    0,    7,
    0,    8,    9,   10,   11,   12,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   41,   47,   43,   44,   45,   41,
   44,   43,   44,   45,   45,   44,   40,   59,   60,   61,
   62,   45,   59,   60,   61,   62,   44,   59,   60,   61,
   62,   41,  123,   43,   44,   45,   43,   50,   45,   44,
  102,   59,  261,  123,   44,   14,  265,   45,   44,   59,
   60,   61,   62,   60,   61,   62,    1,    2,  125,   59,
    2,   40,   31,   46,   40,   47,   11,   46,   13,   14,
   46,   13,   41,   55,   35,   36,   21,   42,   59,  261,
   40,  143,   47,  265,  273,  274,   31,  100,  101,   41,
   40,   43,   53,   45,   39,   41,   41,   39,   44,  276,
  113,   41,  260,   43,  262,   45,   51,   76,  256,  257,
   43,  259,   45,  261,   40,  263,  264,  265,  266,  267,
   41,   41,   41,   44,   44,   44,   41,   88,   89,   44,
  112,   76,   41,  115,  257,   44,   94,   95,   99,  123,
  257,   59,  103,   59,  268,   59,   59,  125,   59,   44,
   40,   40,  258,   41,   41,   40,  272,   59,   44,  125,
   41,  123,  277,  257,  123,  256,  257,  257,  259,  257,
  261,  125,  263,  264,  265,  266,  267,  257,  139,   59,
   59,   41,   59,  262,  125,  123,    0,  156,  157,  256,
  257,   59,  259,   59,  261,  164,  263,  264,  265,  266,
  267,  125,  147,   59,  149,  147,  277,  149,   41,   21,
   11,  156,  157,  125,   86,  108,  133,  162,   -1,  164,
  162,   -1,   -1,  257,   -1,   -1,  257,  258,  257,  139,
   -1,   -1,   -1,  257,  258,  277,  278,  279,  280,  270,
  277,  278,  279,  280,  275,  277,  278,  279,  280,   -1,
   -1,  275,   -1,  271,   -1,   -1,   -1,   -1,  276,  257,
  258,   -1,   -1,   -1,   -1,   -1,  271,  277,  278,  279,
  280,  278,  279,  280,   -1,  271,   -1,  275,  256,  257,
   -1,  259,   -1,  261,   -1,  263,  264,  265,  266,  267,
  256,  257,   -1,  259,   -1,  261,   -1,  263,  264,  265,
  266,  267,  256,  257,   -1,  259,   -1,  261,   -1,  263,
  264,  265,  266,  267,   -1,  256,  257,   -1,  259,   -1,
  261,   -1,  263,  264,  265,  266,  267,   -1,   -1,   -1,
   -1,   -1,  256,  257,   -1,  259,   -1,  261,   -1,  263,
  264,  265,  266,  267,  256,  257,   -1,  259,   -1,  261,
   -1,  263,  264,  265,  266,  267,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=281;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"ID","CTE","IF","ELSE","FLOAT","ENDIF",
"RETURN","PRINT","UINT","VAR","DO","WHILE","LAMBDA","CADENA_MULTILINEA",
"ASIG_MULTIPLE","CR","SE","LE","TOUI","ASIG","FLECHA","MAYOR_IGUAL",
"MENOR_IGUAL","DISTINTO","IFX",
};
final static String yyrule[] = {
"$accept : programa",
"programa : ID '{' sentencias '}'",
"programa : '{' sentencias '}'",
"programa : ID sentencias '}'",
"programa : ID '{' sentencias",
"sentencias : sentencias sentencia",
"sentencias : sentencia",
"sentencia : sentencia_declarativa",
"sentencia : sentencia_ejecutable",
"sentencia : error ';'",
"sentencia_declarativa : declaracion_tipica ';'",
"sentencia_declarativa : funcion",
"sentencia_declarativa : declaracion_var ';'",
"declaracion_tipica : tipo lista_variables",
"declaracion_var : VAR asignacion",
"tipo : UINT",
"tipo : FLOAT",
"lista_variables : lista_variables ',' variable",
"lista_variables : variable",
"funcion : tipo ID '(' lista_parametros_formales ')' '{' sentencias '}'",
"funcion : lista_tipos_retorno_multiple ID '(' lista_parametros_formales ')' '{' sentencias '}'",
"lista_tipos_retorno_multiple : tipo ',' tipo",
"lista_tipos_retorno_multiple : lista_tipos_retorno_multiple ',' tipo",
"lista_parametros_formales : lista_parametros_formales ',' parametro_formal",
"lista_parametros_formales : parametro_formal",
"parametro_formal : sem_pasaje tipo ID",
"sem_pasaje : CR SE",
"sem_pasaje : CR LE",
"sem_pasaje :",
"sentencia_ejecutable : asignacion ';'",
"sentencia_ejecutable : asignacion_multiple ';'",
"sentencia_ejecutable : condicional_if",
"sentencia_ejecutable : condicional_do_while",
"sentencia_ejecutable : salida_pantalla ';'",
"sentencia_ejecutable : retorno_funcion",
"asignacion : variable ASIG expresion",
"asignacion_multiple : lista_variables ASIG_MULTIPLE lado_derecho_multiple",
"lado_derecho_multiple : lista_elementos_restringidos",
"lista_elementos_restringidos : lista_elementos_restringidos ',' factor",
"lista_elementos_restringidos : factor",
"variable : ID",
"variable : ID '.' ID",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : variable",
"factor : constante",
"factor : invocacion_funcion",
"factor : conversion_explicita",
"conversion_explicita : TOUI '(' expresion ')'",
"invocacion_funcion : ID '(' lista_parametros_reales ')'",
"lista_parametros_reales : lista_parametros_reales ',' parametro_real",
"lista_parametros_reales : parametro_real",
"parametro_real : parametro_simple FLECHA ID",
"parametro_simple : expresion",
"parametro_simple : lambda_expresion",
"lambda_expresion : '(' tipo ID ')' '{' sentencias '}'",
"constante : CTE",
"constante : '-' CTE",
"condicional_if : IF '(' condicion ')' bloque_ejecutable ENDIF ';'",
"condicional_if : IF '(' condicion ')' bloque_ejecutable ELSE bloque_ejecutable ENDIF ';'",
"condicional_do_while : DO bloque_ejecutable WHILE '(' condicion ')' ';'",
"condicion : expresion simbolo_comparacion expresion",
"simbolo_comparacion : MAYOR_IGUAL",
"simbolo_comparacion : MENOR_IGUAL",
"simbolo_comparacion : DISTINTO",
"simbolo_comparacion : '='",
"simbolo_comparacion : '>'",
"simbolo_comparacion : '<'",
"bloque_ejecutable : '{' sentencias '}'",
"salida_pantalla : PRINT '(' CADENA_MULTILINEA ')'",
"salida_pantalla : PRINT '(' expresion ')'",
"retorno_funcion : RETURN '(' lista_expresiones ')' ';'",
"lista_expresiones : lista_expresiones ',' expresion",
"lista_expresiones : expresion",
};

//#line 227 "gramatica.y"

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
//#line 460 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 21 "gramatica.y"
{
          salida.add("Linea " + (al.getContadorFila()+1) + ": Programa '" + val_peek(3).sval + "' reconocido.");
          al.agregarAtributoLexema(val_peek(3).sval, "Uso", "Programa");
      }
break;
case 2:
//#line 25 "gramatica.y"
{erroresSintacticos.add("Linea " + (al.getContadorFila()+1) + ": Error sintactico: Falta el nombre del programa.");}
break;
case 3:
//#line 26 "gramatica.y"
{erroresSintacticos.add("Linea " + (al.getContadorFila()+1) + ": Error sintactico: Falta el delimitador '{' al inicio del programa.");}
break;
case 4:
//#line 27 "gramatica.y"
{erroresSintacticos.add("Linea " + (al.getContadorFila()+1) + ": Error sintactico: Falta el delimitador '}' al final del programa.");}
break;
case 9:
//#line 36 "gramatica.y"
{erroresSintacticos.add("Linea " + (al.getContadorFila()+1) + ": Error sintactico en la sentencia.");}
break;
case 13:
//#line 45 "gramatica.y"
{
                salida.add("Linea " + (al.getContadorFila()+1) + ": Declaracion de variables de tipo '" + val_peek(1).sval + "'.");
                listaVariables.clear();
            }
break;
case 14:
//#line 52 "gramatica.y"
{
                    salida.add("Linea " + (al.getContadorFila()+1) + ": Declaracion por inferencia (var).");
                }
break;
case 15:
//#line 57 "gramatica.y"
{ yyval.sval = "uint"; }
break;
case 16:
//#line 58 "gramatica.y"
{ yyval.sval = "float"; }
break;
case 17:
//#line 62 "gramatica.y"
{
                    listaVariables.add(val_peek(0).sval);
                }
break;
case 18:
//#line 66 "gramatica.y"
{
                    listaVariables.clear();
                    listaVariables.add(val_peek(0).sval);
                }
break;
case 19:
//#line 77 "gramatica.y"
{
            salida.add("Linea " + (al.getContadorFila()+1) + ": Declaracion de Funcion '" + val_peek(6).sval + "' con retorno simple.");
        }
break;
case 20:
//#line 81 "gramatica.y"
{
            salida.add("Linea " + (al.getContadorFila()+1) + ": Declaracion de Funcion '" + val_peek(6).sval + "' con retorno multiple.");
        }
break;
case 25:
//#line 96 "gramatica.y"
{
                 }
break;
case 35:
//#line 114 "gramatica.y"
{
               salida.add("Linea " + (al.getContadorFila()+1) + ": Asignacion simple (:=).");
           }
break;
case 36:
//#line 120 "gramatica.y"
{
                        salida.add("Linea " + (al.getContadorFila()+1) + ": Asignacion multiple (=).");
                    }
break;
case 40:
//#line 132 "gramatica.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 41:
//#line 133 "gramatica.y"
{ yyval.sval = val_peek(2).sval + "." + val_peek(0).sval; }
break;
case 52:
//#line 153 "gramatica.y"
{
                    salida.add("Linea " + (al.getContadorFila()+1) + ": Conversion explícita (toui).");
                }
break;
case 64:
//#line 188 "gramatica.y"
{
                         salida.add("Linea " + (al.getContadorFila()+1) + ": Sentencia DO-WHILE.");
                     }
break;
case 73:
//#line 208 "gramatica.y"
{
                    salida.add("Linea " + (al.getContadorFila()+1) + ": PRINT con cadena multilínea.");
                }
break;
case 74:
//#line 212 "gramatica.y"
{
                    salida.add("Linea " + (al.getContadorFila()+1) + ": PRINT con expresion.");
                }
break;
case 75:
//#line 218 "gramatica.y"
{
                    salida.add("Linea " + (al.getContadorFila()+1) + ": Sentencia RETURN.");
                }
break;
//#line 733 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
