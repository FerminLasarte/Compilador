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
    import java.math.BigDecimal;
    import java.util.Collections;
    import java.util.Comparator;
    import java.util.Stack;
//#line 31 "Parser.java"




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
public final static short IGUAL_IGUAL=281;
public final static short PUNTO=282;
public final static short IFX=283;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    2,    3,    0,    4,    5,    0,    1,    1,    6,    6,
    6,    7,    7,   10,   13,   13,   13,   14,   14,   16,
    9,   18,    9,   17,   17,   15,   15,   19,   19,   20,
   20,    8,    8,    8,    8,    8,    8,    8,   21,   22,
   30,   28,   28,   11,   11,   12,   12,   12,   31,   31,
   31,   29,   29,   32,   32,   32,   34,   35,   27,   36,
   36,   37,   37,   38,   38,   41,   39,   40,   42,   42,
   33,   33,   23,   23,   45,   24,   43,   46,   46,   46,
   46,   46,   46,   47,   44,   44,   25,   25,   49,   26,
   48,   48,
};
final static short yylen[] = {                            2,
    0,    0,    6,    0,    0,    5,    2,    1,    1,    1,
    2,    1,    2,    4,    1,    1,    1,    3,    1,    0,
    9,    0,    9,    3,    3,    3,    1,    3,    2,    2,
    2,    2,    2,    1,    1,    2,    1,    2,    3,    3,
    0,    2,    3,    3,    1,    3,    3,    1,    3,    3,
    1,    1,    1,    1,    1,    1,    4,    0,    5,    3,
    1,    3,    1,    1,    1,    0,    8,    1,    2,    1,
    1,    2,    7,    9,    0,    8,    3,    1,    1,    1,
    1,    1,    1,    0,    4,    3,    4,    4,    0,    6,
    3,    1,
};
final static short yydefred[] = {                         0,
    0,    4,    0,    1,    0,    0,    0,    0,    0,   16,
    0,    0,   15,    0,   75,   17,    0,    8,    9,   10,
   12,    0,    0,    0,    0,    0,    0,    0,   34,   35,
    0,   37,    0,    0,   11,    0,    0,    0,   89,    0,
    0,    0,    0,    5,    7,   13,    0,    0,    0,   41,
    0,    0,    0,   32,   33,   36,   38,    2,   44,    0,
   71,    0,    0,   54,    0,   53,   51,    0,   52,   55,
   56,    0,    0,    0,    0,    0,    0,    0,    6,    0,
    0,   24,    0,    0,   18,    0,   25,    3,    0,    0,
    0,   61,    0,   65,    0,   72,   78,   79,   80,   81,
    0,    0,   82,   83,    0,    0,    0,    0,    0,    0,
   87,   88,    0,    0,    0,    0,    0,    0,    0,   27,
    0,    0,   42,    0,    0,    0,   59,    0,    0,    0,
    0,    0,   49,   50,    0,    0,    0,   86,   70,    0,
    0,   30,   31,   29,    0,    0,    0,   43,    0,    0,
   60,   62,   57,    0,    0,    0,   90,   85,   69,    0,
   26,   20,   28,   22,    0,    0,   73,    0,    0,    0,
   66,    0,   76,    0,    0,    0,   74,   21,   23,    0,
    0,   67,
};
final static short yydgoto[] = {                          3,
   17,    6,   88,    5,   79,   18,   19,   20,   21,   22,
   64,   65,   24,   25,  119,  169,   26,  170,  120,  121,
   27,   28,   29,   30,   31,   32,   66,   83,   67,   84,
   68,   69,   70,   71,   37,   91,   92,   93,   94,  180,
  176,  140,   72,   78,   43,  105,  115,  110,   73,
};
final static short yysindex[] = {                      -106,
  -82,    0,    0,    0, -150, -150,  -14, -223,   37,    0,
   48,   53,    0, -186,    0,    0,  -81,    0,    0,    0,
    0,   22, -176,  -32,  -18,  -28,   45,   51,    0,    0,
   65,    0,   75,   32,    0, -145,  100,    8,    0,   17,
 -223, -130,   29,    0,    0,    0,    8,  115, -193,    0,
 -186,  126, -193,    0,    0,    0,    0,    0,    0,    3,
    0,  127,  -88,    0,  -22,    0,    0,   19,    0,    0,
    0,  131,    8,  132,   80,    8,  -79, -100,    0,   44,
 -187,    0,  130,    8,    0, -187,    0,    0, -193,   44,
   57,    0,  -98,    0,    8,    0,    0,    0,    0,    0,
    8,    8,    0,    0,    8,    8,    8,   29,   44,   91,
    0,    0,   44,   62, -173,  150, -111,  -66,   95,    0,
 -193,    8,    0,   97,  -64,    3,    0, -186,   88,   19,
   19,   44,    0,    0, -102,    8,  136,    0,    0,  -65,
    8,    0,    0,    0, -187,   73,  -60,    0,   81,  164,
    0,    0,    0,   29,  147,   44,    0,    0,    0,  166,
    0,    0,    0,    0,   85,  -53,    0,  153, -150, -150,
    0,  159,    0,   64,   78, -173,    0,    0,    0,   94,
 -173,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,  -40,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -16,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   10,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -35,    0,    0,
    0,    0,    0,    0,    0,    0, -137,    0,    0,  161,
    0,    0,  162,    0,    0,    0,    0,    0,    0,   -5,
    0,    0,  103,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  104,    0,
    0,    0,  165,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -30,
  -10,  182,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  109,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  101,    0,
};
final static short yygindex[] = {                         0,
   31,    0,    0,    0,    0,   39,    0,  -73,    0,    0,
   41,   23,   16,    0,  141,    0,    0,    0,   83,    0,
    0,    0,    0,    0,    0,    0,  137,    0,  -27,    0,
   63,    0,    0,    0,    0,    0,  106,    0,    0,    0,
    0,   54,   92,  -90,    0,    0,    0,    0,    0,
};
final static int YYTABLESIZE=347;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         58,
   45,   45,   45,   45,   45,   48,   45,   48,   48,   48,
   46,   49,   46,   46,   46,   53,    2,  135,   45,   45,
  101,   45,  102,   48,   48,   51,   48,   19,   46,   46,
   47,   46,   47,   47,   47,   64,   34,  104,   64,  103,
    4,  139,   89,   44,   35,   23,   23,   63,   47,   47,
   45,   47,   63,   45,   42,   45,  123,   23,   36,  158,
  106,   63,   75,  166,   82,  107,  159,   10,   87,   80,
   41,   13,   45,   10,   23,   16,   38,   13,  133,  134,
   46,   16,   90,    8,  117,    9,  101,   39,  102,   11,
   12,   85,   40,   15,  148,  109,  118,  127,  113,   47,
  126,  118,  139,   54,  125,    7,    8,  159,    9,   55,
   10,   59,   11,   12,   13,   14,   15,  129,   16,   84,
  112,   84,  101,   56,  102,   84,   84,  132,  153,   84,
  101,  137,  102,   57,  136,  146,  147,  149,  145,   60,
  145,   33,   33,   63,   92,   76,   63,   92,   90,   91,
    1,   77,   91,   33,   81,   23,   58,  154,  156,  155,
  118,  142,  143,  130,  131,   86,   95,  116,  152,   96,
   33,  108,  111,  122,    7,    8,  114,    9,  128,   10,
   23,   11,   12,   13,   14,   15,  138,   16,  178,  141,
  144,    8,  150,    9,  157,  162,  163,   11,   12,  174,
  175,   15,  179,  164,  165,  167,  168,  171,  172,   23,
   23,  173,   45,   45,   23,   23,   23,  177,  182,   39,
   40,   23,   77,   14,   48,   68,  124,  161,   52,  181,
   45,  151,  160,    0,    0,   45,   45,   45,   45,   45,
   45,   48,   48,   48,   48,   48,   46,   46,   46,   46,
   46,   33,   50,    0,   19,   97,   98,   99,  100,    8,
   61,    0,    0,    0,    8,   61,   47,   47,   47,   47,
   47,   64,    0,    8,   61,    0,   33,   62,    0,    0,
   45,    0,   62,    0,    0,   45,   74,    7,    8,    0,
    9,   62,   10,    0,   11,   12,   13,   14,   15,    0,
   16,    0,    0,    0,    0,   33,   33,    0,    0,    0,
   33,   33,   33,    0,    0,    0,    0,   33,    0,    7,
    8,    0,    9,    0,   10,    0,   11,   12,   13,   14,
   15,    0,   16,    7,    8,    0,    9,    0,   10,    0,
   11,   12,   13,   14,   15,    0,   16,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   41,   42,   43,   44,   45,   41,   47,   43,   44,   45,
   41,   44,   43,   44,   45,   44,  123,  108,   59,   60,
   43,   62,   45,   59,   60,   44,   62,   44,   59,   60,
   41,   62,   43,   44,   45,   41,    6,   60,   44,   62,
  123,  115,   40,  125,   59,    5,    6,   45,   59,   60,
   41,   62,   45,   44,   14,   17,   84,   17,  282,  125,
   42,   45,   40,  154,   49,   47,  140,  261,   53,   47,
  257,  265,   34,  261,   34,  269,   40,  265,  106,  107,
   59,  269,   60,  257,  272,  259,   43,   40,   45,  263,
  264,   51,   40,  267,  122,   73,   81,   41,   76,  276,
   44,   86,  176,   59,   89,  256,  257,  181,  259,   59,
  261,  257,  263,  264,  265,  266,  267,   95,  269,  257,
   41,  259,   43,   59,   45,  263,  264,  105,   41,  267,
   43,   41,   45,   59,   44,   41,  121,   41,   44,   40,
   44,    5,    6,   41,   41,  276,   44,   44,  126,   41,
  257,  123,   44,   17,   40,  115,  125,  260,  136,  262,
  145,  273,  274,  101,  102,   40,   40,  268,  128,  258,
   34,   41,   41,   44,  256,  257,  256,  259,  277,  261,
  140,  263,  264,  265,  266,  267,  125,  269,  125,   40,
  257,  257,  257,  259,   59,  123,  257,  263,  264,  169,
  170,  267,  125,  123,   41,   59,   41,  123,  262,  169,
  170,   59,  174,  175,  174,  175,  176,   59,  125,   59,
   59,  181,   41,   59,  257,  125,   86,  145,  257,  176,
  271,  126,  141,   -1,   -1,  276,  277,  278,  279,  280,
  281,  277,  278,  279,  280,  281,  277,  278,  279,  280,
  281,  115,  271,   -1,  271,  278,  279,  280,  281,  257,
  258,   -1,   -1,   -1,  257,  258,  277,  278,  279,  280,
  281,  277,   -1,  257,  258,   -1,  140,  275,   -1,   -1,
  271,   -1,  275,   -1,   -1,  276,  270,  256,  257,   -1,
  259,  275,  261,   -1,  263,  264,  265,  266,  267,   -1,
  269,   -1,   -1,   -1,   -1,  169,  170,   -1,   -1,   -1,
  174,  175,  176,   -1,   -1,   -1,   -1,  181,   -1,  256,
  257,   -1,  259,   -1,  261,   -1,  263,  264,  265,  266,
  267,   -1,  269,  256,  257,   -1,  259,   -1,  261,   -1,
  263,  264,  265,  266,  267,   -1,  269,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=283;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
"'<'",null,"'>'",null,null,null,null,null,null,null,null,null,null,null,null,
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
"MENOR_IGUAL","DISTINTO","IGUAL_IGUAL","PUNTO","IFX",
};
final static String yyrule[] = {
"$accept : programa",
"$$1 :",
"$$2 :",
"programa : ID '{' $$1 sentencias '}' $$2",
"$$3 :",
"$$4 :",
"programa : '{' $$3 sentencias '}' $$4",
"sentencias : sentencias sentencia",
"sentencias : sentencia",
"sentencia : sentencia_declarativa",
"sentencia : sentencia_ejecutable",
"sentencia : error ';'",
"sentencia_declarativa : funcion",
"sentencia_declarativa : declaracion_var ';'",
"declaracion_var : VAR variable ASIG expresion",
"tipo : UINT",
"tipo : FLOAT",
"tipo : LAMBDA",
"lista_variables : lista_variables ',' variable",
"lista_variables : variable",
"$$5 :",
"funcion : tipo ID '(' lista_parametros_formales ')' '{' $$5 sentencias '}'",
"$$6 :",
"funcion : lista_tipos_retorno_multiple ID '(' lista_parametros_formales ')' '{' $$6 sentencias '}'",
"lista_tipos_retorno_multiple : tipo ',' tipo",
"lista_tipos_retorno_multiple : lista_tipos_retorno_multiple ',' tipo",
"lista_parametros_formales : lista_parametros_formales ',' parametro_formal",
"lista_parametros_formales : parametro_formal",
"parametro_formal : sem_pasaje tipo ID",
"parametro_formal : tipo ID",
"sem_pasaje : CR SE",
"sem_pasaje : CR LE",
"sentencia_ejecutable : asignacion ';'",
"sentencia_ejecutable : asignacion_multiple ';'",
"sentencia_ejecutable : condicional_if",
"sentencia_ejecutable : condicional_do_while",
"sentencia_ejecutable : salida_pantalla ';'",
"sentencia_ejecutable : retorno_funcion",
"sentencia_ejecutable : invocacion_funcion ';'",
"asignacion : variable ASIG expresion",
"asignacion_multiple : lista_variables ASIG_MULTIPLE lado_derecho_multiple",
"$$7 :",
"lado_derecho_multiple : $$7 factor",
"lado_derecho_multiple : lado_derecho_multiple ',' factor",
"variable : ID PUNTO ID",
"variable : ID",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : factor_no_funcion",
"factor : invocacion_funcion",
"factor_no_funcion : variable",
"factor_no_funcion : constante",
"factor_no_funcion : conversion_explicita",
"conversion_explicita : TOUI '(' expresion ')'",
"pre_invocacion :",
"invocacion_funcion : ID pre_invocacion '(' lista_parametros_reales ')'",
"lista_parametros_reales : lista_parametros_reales ',' parametro_real",
"lista_parametros_reales : parametro_real",
"parametro_real : parametro_simple FLECHA variable",
"parametro_real : parametro_simple",
"parametro_simple : expresion",
"parametro_simple : lambda_expresion",
"$$8 :",
"lambda_expresion : '(' tipo ID ')' '{' $$8 cuerpo_lambda '}'",
"cuerpo_lambda : sentencias_ejecutables_lista",
"sentencias_ejecutables_lista : sentencias_ejecutables_lista sentencia_ejecutable",
"sentencias_ejecutables_lista : sentencia_ejecutable",
"constante : CTE",
"constante : '-' CTE",
"condicional_if : IF '(' condicion ')' bloque_ejecutable ENDIF ';'",
"condicional_if : IF '(' condicion ')' bloque_ejecutable ELSE bloque_ejecutable ENDIF ';'",
"$$9 :",
"condicional_do_while : DO $$9 bloque_ejecutable WHILE '(' condicion ')' ';'",
"condicion : expresion simbolo_comparacion expresion",
"simbolo_comparacion : MAYOR_IGUAL",
"simbolo_comparacion : MENOR_IGUAL",
"simbolo_comparacion : DISTINTO",
"simbolo_comparacion : IGUAL_IGUAL",
"simbolo_comparacion : '>'",
"simbolo_comparacion : '<'",
"$$10 :",
"bloque_ejecutable : '{' $$10 sentencias_ejecutables_lista '}'",
"bloque_ejecutable : '{' error '}'",
"salida_pantalla : PRINT '(' CADENA_MULTILINEA ')'",
"salida_pantalla : PRINT '(' expresion ')'",
"$$11 :",
"retorno_funcion : RETURN '(' $$11 lista_expresiones ')' ';'",
"lista_expresiones : lista_expresiones ',' expresion",
"lista_expresiones : expresion",
};

//#line 879 "gramatica.y"

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
//#line 541 "Parser.java"
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
//#line 24 "gramatica.y"
{
             g.abrirAmbito(val_peek(1).sval);
             al.agregarLexemaTS(val_peek(1).sval);
             al.agregarAtributoLexema(val_peek(1).sval, "Linea", val_peek(1).ival);
         }
break;
case 2:
//#line 28 "gramatica.y"
{ }
break;
case 3:
//#line 29 "gramatica.y"
{
             String nombrePrograma = val_peek(5).sval;
             Object lineaObj = al.getAtributo(nombrePrograma, "Linea");
             String linea = (lineaObj != null) ? lineaObj.toString() : "?";
             salida.add("Linea " + linea + ": Programa '" + nombrePrograma + "' reconocido.");
         }
break;
case 4:
//#line 36 "gramatica.y"
{ g.abrirAmbito("MAIN"); }
break;
case 5:
//#line 36 "gramatica.y"
{ }
break;
case 6:
//#line 37 "gramatica.y"
{
             erroresSintacticos.add("Linea " + (al.getContadorFila()+1) + ": Error sintactico: Falta el nombre del programa.");
         }
break;
case 14:
//#line 59 "gramatica.y"
{
                    String expr = g.desapilarOperando();
                    String tipoExpr = g.getTipo(expr);
                    String varNombre = val_peek(2).sval;
                    if (g.existeEnAmbitoActual(varNombre)) {
                        al.agregarErrorSemantico("Linea " + val_peek(3).ival + ": Error Semantico: Redeclaracion de variable '" + varNombre + "' en el mismo ambito (Tema 9).");
                    } else if (tipoExpr.equals("error_tipo") || tipoExpr.equals("indefinido")) {
                         al.agregarErrorSemantico("Linea " + val_peek(3).ival + ": Error Semantico: No se puede inferir el tipo de '" + varNombre + "' desde una expresion invalida (Tema 9).");
                    } else {
                        al.agregarLexemaTS(varNombre);
                        al.agregarAtributoLexema(varNombre, "Uso", "variable");
                        al.agregarAtributoLexema(varNombre, "Tipo", tipoExpr);
                        g.addTerceto(":=", varNombre, expr);
                        salida.add("Linea " + val_peek(3).ival + ": Declaracion por inferencia (var).");
                    }
                }
break;
case 15:
//#line 77 "gramatica.y"
{ yyval.sval = "uint"; }
break;
case 16:
//#line 78 "gramatica.y"
{ yyval.sval = "float";
     }
break;
case 17:
//#line 80 "gramatica.y"
{ yyval.sval = "lambda"; }
break;
case 18:
//#line 84 "gramatica.y"
{
                    listaVariables.add(val_peek(0).sval);
                }
break;
case 19:
//#line 89 "gramatica.y"
{
                    listaVariables.clear();
                    listaVariables.add(val_peek(0).sval);
                }
break;
case 20:
//#line 95 "gramatica.y"
{
            String nombreFuncion = val_peek(4).sval;
            String tipoRetorno = val_peek(5).sval;
            String nombreAmbito = nombreFuncion;
            ArrayList<ParametroInfo> parametros = g.getListaParametros();
            pilaInicioFuncion.push(g.getProximoTerceto());

            ArrayList<String> tiposEsperados = new ArrayList<String>();
            tiposEsperados.add(tipoRetorno);
            pilaTiposRetorno.push(tiposEsperados);
            pilaErrorEnFuncion.push(false);

            if (g.existeEnAmbitoActual(nombreFuncion)) {
                al.agregarErrorSemantico("Linea " + val_peek(4).ival + ": Error Semantico: Redeclaracion de funcion '" + nombreFuncion + "'.");
                g.setGeneracionHabilitada(false);
                nombreAmbito = "GARBAGE_" + nombreFuncion;
            } else {
                al.agregarLexemaTS(nombreFuncion);
                al.agregarAtributoLexema(nombreFuncion, "Uso", "funcion");
                al.agregarAtributoLexema(nombreFuncion, "Linea", val_peek(4).ival);
                al.agregarAtributoLexema(nombreFuncion, "Tipo", tipoRetorno);
                al.agregarAtributoLexema(nombreFuncion, "Parametros", parametros);
                al.agregarAtributoLexema(nombreFuncion, "RetornoMultiple", false);
            }
            g.abrirAmbito(nombreAmbito);
            for (ParametroInfo p : parametros) {
                if (g.existeEnAmbitoActual(p.nombre)) {
                     al.agregarErrorSemantico("Linea " + val_peek(4).ival + ": Error Semantico: Redeclaracion del parametro '" + p.nombre + "'.");
                } else {
                     al.agregarLexemaTS(p.nombre);
                     al.agregarAtributoLexema(p.nombre, "Uso", "parametro");
                     al.agregarAtributoLexema(nombreFuncion, "Linea", val_peek(4).ival);
                     al.agregarAtributoLexema(p.nombre, "Tipo", p.tipo);
                     al.agregarAtributoLexema(p.nombre, "Pasaje", p.pasaje);
                }
            }
        }
break;
case 21:
//#line 131 "gramatica.y"
{
            g.cerrarAmbito();
            g.setGeneracionHabilitada(true);

            pilaTiposRetorno.pop();
            boolean huboError = pilaErrorEnFuncion.pop();
            int inicioFunc = pilaInicioFuncion.pop();
            if (huboError) {
                al.eliminarLexemaTS(val_peek(7).sval);
                g.anularTercetosDesde(inicioFunc);
                al.eliminarUltimoAmbito();
            } else {
                String nombreFuncion = val_peek(7).sval;
                salida.add("Linea " + val_peek(7).ival + ": Declaracion de Funcion '" + val_peek(7).sval + "' con retorno simple.");
            }
        }
break;
case 22:
//#line 148 "gramatica.y"
{
            String nombreFuncion = val_peek(4).sval;
            String nombreAmbito = nombreFuncion;
            ArrayList<?> rawList = (ArrayList<?>) val_peek(5).obj;
            ArrayList<String> tiposRetorno = new ArrayList<String>();
            for (Object o : rawList) {
                tiposRetorno.add((String) o);
            }
            pilaInicioFuncion.push(g.getProximoTerceto());

            pilaTiposRetorno.push(tiposRetorno);
            pilaErrorEnFuncion.push(false);

            ArrayList<ParametroInfo> parametros = g.getListaParametros();
            if (g.existeEnAmbitoActual(nombreFuncion)) {
                al.agregarErrorSemantico("Linea " + val_peek(4).ival + ": Error Semantico: Redeclaracion de funcion '" + nombreFuncion + "'.");
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
                     al.agregarErrorSemantico("Linea " + val_peek(4).ival + ": Error Semantico: Redeclaracion del parametro '" + p.nombre + "'.");
                } else {
                     al.agregarLexemaTS(p.nombre);
                     al.agregarAtributoLexema(p.nombre, "Uso", "parametro");
                     al.agregarAtributoLexema(p.nombre, "Tipo", p.tipo);
                     al.agregarAtributoLexema(p.nombre, "Pasaje", p.pasaje);
                }
            }
        }
break;
case 23:
//#line 185 "gramatica.y"
{
            g.cerrarAmbito();
            g.setGeneracionHabilitada(true);

            pilaTiposRetorno.pop();
            boolean huboError = pilaErrorEnFuncion.pop();
            int inicioFunc = pilaInicioFuncion.pop();
            if (huboError) {
                al.eliminarLexemaTS(val_peek(7).sval);
                g.anularTercetosDesde(inicioFunc);
                al.eliminarUltimoAmbito();
            } else {
                String nombreFuncion = val_peek(7).sval;
                salida.add("Linea " + val_peek(7).ival + ": Declaracion de Funcion '" + val_peek(7).sval + "' con retorno multiple.");
            }
        }
break;
case 24:
//#line 204 "gramatica.y"
{
                                 ArrayList<String> lista = new ArrayList<String>();
                                 lista.add(val_peek(2).sval);
                                 lista.add(val_peek(0).sval);
                                 yyval.obj = lista;
                             }
break;
case 25:
//#line 212 "gramatica.y"
{
                                 ArrayList<?> rawList = (ArrayList<?>) val_peek(2).obj;
                                 ArrayList<String> lista = new ArrayList<String>();
                                 for (Object o : rawList) {
                                     lista.add((String) o);
                                 }
                                 lista.add(val_peek(0).sval);
                                 yyval.obj = lista;
                             }
break;
case 28:
//#line 229 "gramatica.y"
{
                 g.apilarParametro(new ParametroInfo(val_peek(0).sval, val_peek(1).sval, val_peek(2).sval));
             }
break;
case 29:
//#line 234 "gramatica.y"
{
                 String pasajeDefault = "default_cv";
                 g.apilarParametro(new ParametroInfo(val_peek(0).sval, val_peek(1).sval, pasajeDefault));
             }
break;
case 30:
//#line 240 "gramatica.y"
{ yyval.sval = "cr_se"; }
break;
case 31:
//#line 242 "gramatica.y"
{ yyval.sval = "cr_le"; }
break;
case 39:
//#line 258 "gramatica.y"
{
               salida.add("Linea " + val_peek(2).ival + ": Asignacion simple (:=).");
               String op2_terceto = g.desapilarOperando();
               String op1_var = val_peek(2).sval;
               String tipoVar = g.getTipo(op1_var);
               String tipoExpr = g.getTipo(op2_terceto);
               int linea = val_peek(2).ival;
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
break;
case 40:
//#line 326 "gramatica.y"
{
                            String lineaActual = String.valueOf(val_peek(1).ival);
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
break;
case 41:
//#line 407 "gramatica.y"
{ g.clearLadoDerecho(); }
break;
case 42:
//#line 408 "gramatica.y"
{
                              g.apilarLadoDerecho(g.desapilarOperando());
                              contadorLadoDerecho = 1;
                              yyval.ival = val_peek(0).ival;
                              yyval.sval = val_peek(0).sval;
                          }
break;
case 43:
//#line 416 "gramatica.y"
{
                              g.apilarLadoDerecho(g.desapilarOperando());
                              contadorLadoDerecho++;
                              yyval.ival = 0;
                          }
break;
case 44:
//#line 424 "gramatica.y"
{
                yyval.sval = val_peek(2).sval + "." + val_peek(0).sval;
                yyval.ival = val_peek(2).ival;
            }
break;
case 45:
//#line 430 "gramatica.y"
{
                yyval.sval = val_peek(0).sval;
                yyval.ival = val_peek(0).ival;
            }
break;
case 46:
//#line 437 "gramatica.y"
{
                String op2 = g.desapilarOperando();
                String op1 = g.desapilarOperando();
                String tipo = g.chequearTipos("+", g.getTipo(op1), g.getTipo(op2), val_peek(2).ival);
                String terceto = g.addTerceto("+", op1, op2);
                g.getTerceto(Integer.parseInt(terceto.substring(1, terceto.length()-1))).setTipo(tipo);
                g.apilarOperando(terceto);
                yyval.ival = val_peek(2).ival;
            }
break;
case 47:
//#line 448 "gramatica.y"
{
                String op2 = g.desapilarOperando();
                String op1 = g.desapilarOperando();
                String tipo = g.chequearTipos("-", g.getTipo(op1), g.getTipo(op2), val_peek(2).ival);
                String terceto = g.addTerceto("-", op1, op2);
                g.getTerceto(Integer.parseInt(terceto.substring(1, terceto.length()-1))).setTipo(tipo);
                g.apilarOperando(terceto);
                yyval.ival = val_peek(2).ival;
            }
break;
case 48:
//#line 458 "gramatica.y"
{ yyval.ival = val_peek(0).ival;
          }
break;
case 49:
//#line 463 "gramatica.y"
{
                String op2 = g.desapilarOperando();
                String op1 = g.desapilarOperando();
                String tipo = g.chequearTipos("*", g.getTipo(op1), g.getTipo(op2), val_peek(2).ival);
                String terceto = g.addTerceto("*", op1, op2);
                g.getTerceto(Integer.parseInt(terceto.substring(1, terceto.length()-1))).setTipo(tipo);
                g.apilarOperando(terceto);
                yyval.ival = val_peek(2).ival;
            }
break;
case 50:
//#line 474 "gramatica.y"
{
                String op2 = g.desapilarOperando();
                String op1 = g.desapilarOperando();
                String tipo = g.chequearTipos("/", g.getTipo(op1), g.getTipo(op2), val_peek(2).ival);
                String terceto = g.addTerceto("/", op1, op2);
                g.getTerceto(Integer.parseInt(terceto.substring(1, terceto.length()-1))).setTipo(tipo);
                g.apilarOperando(terceto);
                yyval.ival = val_peek(2).ival;
            }
break;
case 51:
//#line 483 "gramatica.y"
{ yyval.ival = val_peek(0).ival;
        }
break;
case 52:
//#line 488 "gramatica.y"
{
           yyval.ival = val_peek(0).ival;
       }
break;
case 53:
//#line 492 "gramatica.y"
{
           yyval.ival = val_peek(0).ival;
           yyval.sval = val_peek(0).sval;
           g.apilarOperando(val_peek(0).sval);
       }
break;
case 54:
//#line 500 "gramatica.y"
{
                      String varNombre = val_peek(0).sval;
                      String tipoVar = g.getTipo(varNombre);
                      if (tipoVar.equals("indefinido")) {
                          if (varNombre.contains(".")) {
                              String[] parts = varNombre.split("\\.", 2);
                              al.agregarErrorSemantico("Linea " + val_peek(0).ival + ": Error Semantico: Variable '" + parts[1] + "' no existe en el ambito '" + parts[0] + "' o el ambito no es visible (Tema 23).");
                          } else {
                              al.agregarErrorSemantico("Linea " + val_peek(0).ival + ": Error Semantico: Variable '" + varNombre + "' no fue declarada (Regla de alcance).");
                          }
                          g.apilarOperando("error_tipo");
                      } else {
                          Object pasaje = al.getAtributo(varNombre, "Pasaje");
                          if (pasaje != null && pasaje.toString().equals("cr_se") && !enSentenciaReturn) {
                              al.agregarErrorSemantico("Linea " + val_peek(0).ival + ": Error Semantico: El parametro '" + varNombre + "' es de solo escritura (se) y no puede ser leido.");
                              g.apilarOperando("error_tipo");
                          } else {
                              g.apilarOperando(varNombre);
                          }
                      }
                      yyval.ival = val_peek(0).ival;
                  }
break;
case 55:
//#line 524 "gramatica.y"
{
                      g.apilarOperando(val_peek(0).sval);
                      yyval.ival = val_peek(0).ival;
                  }
break;
case 56:
//#line 530 "gramatica.y"
{ yyval.ival = val_peek(0).ival;
                  }
break;
case 57:
//#line 535 "gramatica.y"
{
                    salida.add("Linea " + val_peek(3).ival + ": Conversion explicita (toui).");
                    String op1 = g.desapilarOperando();
                    String tipoOp1 = g.getTipo(op1);

                    if (tipoOp1.equals("float")) {
                        String terceto = g.addTerceto("TOUI", op1);
                        g.getTerceto(Integer.parseInt(terceto.substring(1, terceto.length()-1))).setTipo("uint");
                        g.apilarOperando(terceto);
                    } else if (tipoOp1.equals("indefinido") || tipoOp1.equals("error_tipo")) {
                        g.apilarOperando("error_tipo");
                    } else {
                        al.agregarErrorSemantico("Linea " + val_peek(3).ival + ": Error Semantico: 'toui' solo puede aplicarse a expresiones 'float', se obtuvo '" + tipoOp1 + "'.");
                        g.apilarOperando("error_tipo");
                    }
                    yyval.ival = val_peek(3).ival;
                }
break;
case 58:
//#line 554 "gramatica.y"
{ g.clearParametrosReales(); }
break;
case 59:
//#line 558 "gramatica.y"
{
                       String funcName = val_peek(4).sval;
                       int linea = val_peek(4).ival;
                       int cantReales = val_peek(1).ival;
                       ArrayList<ParametroRealInfo> reales = g.getListaParametrosReales(cantReales);
                       Object uso = al.getAtributo(funcName, "Uso");
                       String tipo = g.getTipo(funcName);
                       if (uso != null && (uso.toString().equals("parametro") || uso.toString().equals("parametro_lambda") || uso.toString().equals("variable")) && tipo.equals("lambda")) {
                           if (reales.size() != 1) {
                               al.agregarErrorSemantico("Linea " + linea + ": Error Semantico (Tema 28): Invocacion de lambda '" + funcName + "' con numero incorrecto de parametros. Esperado: 1, Obtenido: " + reales.size() + ".");
                               yyval.sval = "ERROR_CALL_LAMBDA";
                           } else {
                               g.addTerceto("PARAM_LAMBDA", reales.get(0).operando, "_");
                               String terceto = g.addTerceto("CALL_LAMBDA", funcName, "_");
                               g.getTerceto(Integer.parseInt(terceto.substring(1, terceto.length()-1))).setTipo("void");
                               yyval.sval = terceto;
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
                                yyval.sval = "ERROR_CALL";
                           } else if (formales.size() != reales.size()) {
                               al.agregarErrorSemantico("Linea " + linea + ": Error Semantico: Invocacion a '" + funcName + "' con numero incorrecto de parametros. Esperados: " + formales.size() + ", Obtenidos: " + reales.size() + ".");
                               yyval.sval = "ERROR_CALL";
                           } else {
                                boolean errorEnParametros = false;
                                for (ParametroRealInfo real : reales) {
                                   if (real.nombreFormal == null) {
                                       al.agregarErrorSemantico("Linea " + linea + ": Error Semantico: Se requiere asignacion explicita de parametro (-> ID).");
                                       errorEnParametros = true;
                                       continue;
                                   }
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
                                   yyval.sval = terceto;
                               } else {
                                   yyval.sval = "ERROR_CALL_PARAMS";
                               }
                           }
                       }
                       else {
                           al.agregarErrorSemantico("Linea " + linea + ": Error Semantico: Invocacion a '" + funcName + "' que no es una funcion, variable lambda, o no fue declarada.");
                           yyval.sval = "ERROR_CALL";
                       }
                       yyval.ival = val_peek(4).ival;
                   }
break;
case 60:
//#line 642 "gramatica.y"
{
                            yyval.ival = val_peek(2).ival + 1;
                        }
break;
case 61:
//#line 647 "gramatica.y"
{
                            yyval.ival = 1;
                        }
break;
case 62:
//#line 653 "gramatica.y"
{
                   String op1 = val_peek(2).sval;
                   String op2 = val_peek(0).sval;
                   if (Character.isUpperCase(op2.charAt(0))) {
                       g.apilarParametroReal(new ParametroRealInfo(op1, op2));
                   } else {
                       al.agregarErrorSemantico("Linea " + val_peek(2).ival + ": Error Semantico: Se requiere un identificador valido como nombre de parametro.");
                   }
                   yyval.ival = val_peek(2).ival;
               }
break;
case 63:
//#line 665 "gramatica.y"
{
                   g.apilarParametroReal(new ParametroRealInfo(val_peek(0).sval, null));
                   yyval.ival = val_peek(0).ival;
               }
break;
case 64:
//#line 672 "gramatica.y"
{
                     yyval.sval = g.desapilarOperando();
                     yyval.ival = val_peek(0).ival;
                 }
break;
case 65:
//#line 678 "gramatica.y"
{
                     yyval.sval = val_peek(0).sval;
                     yyval.ival = val_peek(0).ival;
                 }
break;
case 66:
//#line 684 "gramatica.y"
{
                         pilaSaltosLambda.push(g.addTerceto("BI", "_", "_"));
                         int inicioLambda = g.getProximoTerceto();
                         yyval.sval = "L" + String.valueOf(inicioLambda);
                         g.abrirAmbito("lambda_" + inicioLambda);
                         al.agregarLexemaTS(val_peek(2).sval);
                         al.agregarAtributoLexema(val_peek(2).sval, "Uso", "parametro_lambda");
                         al.agregarAtributoLexema(val_peek(2).sval, "Tipo", val_peek(3).sval);
                         g.addTerceto("DEF_PARAM", val_peek(2).sval, "_");
                         yyval.ival = val_peek(4).ival;
                 }
break;
case 67:
//#line 694 "gramatica.y"
{
                         g.addTerceto("RET_LAMBDA", "_", "_");
                         int tercetoFin = g.getProximoTerceto();
                         String saltoIncondicional = pilaSaltosLambda.pop();
                         /* Se agrega corchetes al destino del salto para mantener consistencia visual*/
                         g.modificarSaltoTerceto(Integer.parseInt(saltoIncondicional.substring(1, saltoIncondicional.length()-1)), "[" + tercetoFin + "]");
                         g.cerrarAmbito();
                         yyval.sval = val_peek(2).sval;
                         yyval.ival = val_peek(7).ival;
                 }
break;
case 71:
//#line 715 "gramatica.y"
{
                yyval.sval = val_peek(0).sval;
                yyval.ival = val_peek(0).ival;
            }
break;
case 72:
//#line 721 "gramatica.y"
{
                String lexemaPositivo = val_peek(0).sval;
                String lexemaNegativo = "-" + lexemaPositivo;
                if (al.getAtributo(lexemaPositivo, "Tipo") != null) {
                    String tipo = (String) al.getAtributo(lexemaPositivo, "Tipo");
                    if (tipo != null) {
                        if (tipo.equals("uint")) {
                            al.agregarErrorSemantico("Linea " + val_peek(0).ival + ": Error Semantico. El tipo 'uint' no puede ser negativo. Valor: " + lexemaNegativo);
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
                yyval.sval = lexemaNegativo;
                yyval.ival = val_peek(0).ival;
            }
break;
case 73:
//#line 746 "gramatica.y"
{
                   salida.add("Linea " + val_peek(6).ival + ": Sentencia IF reconocida.");
               }
break;
case 74:
//#line 751 "gramatica.y"
{
                   salida.add("Linea " + val_peek(8).ival + ": Sentencia IF-ELSE reconocida.");
               }
break;
case 75:
//#line 756 "gramatica.y"
{ g.apilarControl(g.getProximoTerceto());
                    }
break;
case 76:
//#line 758 "gramatica.y"
{
                        Object lineaObj = al.getAtributo("do", "Linea");
                        salida.add("Linea " + val_peek(1).ival + ": Sentencia DO-WHILE reconocida.");
                        String refCondicion = g.desapilarOperando();
                        int inicioBucle = g.desapilarControl();
                        if (refCondicion.equals("ERROR_CONDICION")) {
                             al.agregarErrorSemantico("Linea " + val_peek(1).ival + ": Error Semantico: No se genero el salto del DO-WHILE debido a una condicion invalida.");
                        } else {
                            /* Agregamos corchetes al destino del salto para consistencia con referencias*/
                            String tercetoSalto = g.addTerceto("BT", refCondicion, "[" + inicioBucle + "]");
                        }
                    }
break;
case 77:
//#line 773 "gramatica.y"
{
                String op2 = g.desapilarOperando();
                String op = g.desapilarOperando();
                String op1 = g.desapilarOperando();
                String tipo = g.chequearTipos(op, g.getTipo(op1), g.getTipo(op2), val_peek(2).ival);
                if (!tipo.equals("error_tipo")) {
                    String terceto = g.addTerceto(op, op1, op2);
                    g.getTerceto(Integer.parseInt(terceto.substring(1, terceto.length()-1))).setTipo("boolean");
                    g.apilarOperando(terceto);
                } else {
                    g.apilarOperando("ERROR_CONDICION");
                }
          }
break;
case 78:
//#line 788 "gramatica.y"
{ g.apilarOperando(">="); }
break;
case 79:
//#line 790 "gramatica.y"
{ g.apilarOperando("<="); }
break;
case 80:
//#line 792 "gramatica.y"
{ g.apilarOperando("=!"); }
break;
case 81:
//#line 794 "gramatica.y"
{ g.apilarOperando("=="); }
break;
case 82:
//#line 796 "gramatica.y"
{ g.apilarOperando(">"); }
break;
case 83:
//#line 798 "gramatica.y"
{ g.apilarOperando("<"); }
break;
case 84:
//#line 801 "gramatica.y"
{ g.abrirAmbito("bloque_" + g.getProximoTerceto()); }
break;
case 85:
//#line 801 "gramatica.y"
{ g.cerrarAmbito();
                  }
break;
case 87:
//#line 808 "gramatica.y"
{
                    salida.add("Linea " + val_peek(3).ival + ": PRINT con cadena multilinea.");
                    g.addTerceto("PRINT", val_peek(1).sval);
                }
break;
case 88:
//#line 814 "gramatica.y"
{
                    salida.add("Linea " + val_peek(3).ival + ": PRINT con expresion.");
                    g.addTerceto("PRINT", g.desapilarOperando());
                }
break;
case 89:
//#line 820 "gramatica.y"
{ enSentenciaReturn = true; }
break;
case 90:
//#line 821 "gramatica.y"
{
                enSentenciaReturn = false;
                ArrayList<String> tiposEsperados = pilaTiposRetorno.peek();
                ArrayList<?> rawList = (ArrayList<?>) val_peek(2).obj;
                ArrayList<String> expresiones = new ArrayList<String>();
                for (Object o : rawList) {
                    expresiones.add((String) o);
                }

                boolean error = false;
                if (tiposEsperados.size() != expresiones.size()) {
                    al.agregarErrorSemantico("Linea " + val_peek(5).ival + ": Error de Tipos: Cantidad de valores de retorno incorrecta. Esperado: " + tiposEsperados.size() + ", Encontrado: " + expresiones.size());
                    error = true;
                } else {
                    for (int i = 0; i < tiposEsperados.size(); i++) {
                        String tipoEsp = tiposEsperados.get(i);
                        String op = expresiones.get(i);
                        String tipoEnc = g.getTipo(op);

                        if (!tipoEnc.equals(tipoEsp) && !tipoEnc.equals("error_tipo")) {
                             al.agregarErrorSemantico("Linea " + val_peek(5).ival + ": Error de Tipos: Tipo de retorno incorrecto en la posicion " + (i+1) + ". Esperado: " + tipoEsp + ", Encontrado: " + tipoEnc);
                             error = true;
                        }
                    }
                }

                if (error) {
                    pilaErrorEnFuncion.pop();
                    pilaErrorEnFuncion.push(true);
                } else {
                    salida.add("Linea " + val_peek(5).ival + ": Sentencia RETURN.");
                    for (String exprTerceto : expresiones) {
                        g.addTerceto("RETURN", exprTerceto);
                    }
                }
            }
break;
case 91:
//#line 860 "gramatica.y"
{
                  ArrayList<?> rawList = (ArrayList<?>) val_peek(2).obj;
                  ArrayList<String> lista = new ArrayList<String>();
                  for (Object o : rawList) {
                      lista.add((String) o);
                  }
                  lista.add(g.desapilarOperando());
                  yyval.obj = lista;
              }
break;
case 92:
//#line 871 "gramatica.y"
{
                  ArrayList<String> lista = new ArrayList<String>();
                  lista.add(g.desapilarOperando());
                  yyval.obj = lista;
              }
break;
//#line 1596 "Parser.java"
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
