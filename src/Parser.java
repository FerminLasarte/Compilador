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
   36,   37,   37,   38,   38,   41,   39,   40,   40,   42,
   42,   42,   42,   42,   44,   43,   33,   33,   45,   23,
   48,   23,   49,   24,   46,   50,   50,   50,   50,   50,
   50,   51,   47,   52,   47,   47,   25,   25,   54,   26,
   53,   53,
};
final static short yylen[] = {                            2,
    0,    0,    6,    0,    0,    5,    2,    1,    1,    1,
    2,    1,    2,    4,    1,    1,    1,    3,    1,    0,
    9,    0,    9,    3,    3,    3,    1,    3,    2,    2,
    2,    2,    2,    1,    1,    2,    1,    2,    3,    3,
    0,    2,    3,    3,    1,    3,    3,    1,    3,    3,
    1,    1,    1,    1,    1,    1,    4,    0,    5,    3,
    1,    3,    1,    1,    1,    0,    8,    1,    0,    2,
    1,    2,    1,    2,    0,    4,    1,    2,    4,    4,
    0,    7,    0,    8,    3,    1,    1,    1,    1,    1,
    1,    0,    4,    0,    3,    3,    4,    4,    0,    6,
    3,    1,
};
final static short yydefred[] = {                         0,
    0,    4,    0,    1,    0,    0,    0,    0,    0,   16,
    0,    0,   15,    0,   83,   17,    0,    8,    9,   10,
   12,    0,    0,    0,    0,    0,    0,    0,   34,   35,
    0,   37,    0,    0,    0,   11,    0,    0,    0,   99,
    0,    0,    0,    0,    5,    7,   13,    0,    0,    0,
   41,    0,    0,    0,   32,   33,   36,   38,    0,    0,
    2,   44,    0,   77,    0,    0,   54,    0,   53,   51,
    0,   52,   55,   56,    0,    0,    0,    0,    0,    0,
    6,    0,    0,   24,    0,    0,   18,    0,   25,    0,
    0,    0,   81,    0,    3,    0,    0,    0,   61,    0,
   65,    0,   78,   86,   87,   88,   89,    0,    0,   90,
   91,    0,    0,    0,   79,    0,    0,   97,   98,    0,
    0,    0,    0,    0,   27,    0,    0,   42,    0,   96,
    0,    0,   71,    0,   73,   95,    0,   80,    0,    0,
   59,    0,    0,    0,    0,    0,   49,   50,    0,    0,
    0,   30,   31,   29,    0,    0,    0,   43,    0,   74,
    0,   93,   70,   72,    0,    0,   60,   62,   57,    0,
  100,    0,   26,   20,   28,   22,    0,    0,    0,    0,
    0,    0,   76,   82,   66,   84,    0,    0,    0,   21,
   23,    0,    0,   67,
};
final static short yydgoto[] = {                          3,
   17,    6,   95,    5,   81,   18,   19,   20,   21,   22,
   67,   68,   24,   25,  124,  181,   26,  182,  125,  126,
   27,   28,   29,   30,   31,   32,   69,   85,   70,   86,
   71,   72,   73,   74,   38,   98,   99,  100,  101,  192,
  189,  134,  135,  161,   34,   75,   60,  137,   44,  112,
   91,   92,  117,   76,
};
final static short yysindex[] = {                       -82,
  -76,    0,    0,    0, -149, -149,   36, -209,   82,    0,
   83,  101,    0, -157,    0,    0, -107,    0,    0,    0,
    0,   88, -128,  -32,  -18,  -28,   94,  106,    0,    0,
  110,    0,  135,    4,  -79,    0,  -57,  161,    8,    0,
   17, -209,  -72,    4,    0,    0,    0,    8,  162, -183,
    0, -157,  166, -183,    0,    0,    0,    0,  -42,  -99,
    0,    0,    3,    0,  175,  -41,    0,  -22,    0,    0,
   51,    0,    0,    0,  177,    8,  178,   68,    8,  -52,
    0,  136, -178,    0,  179,    8,    0, -178,    0,   97,
 -187,   99,    0,  171,    0, -183,  136,   23,    0,  -44,
    0,    8,    0,    0,    0,    0,    0,    8,    8,    0,
    0,    8,    8,    8,    0,  136,   40,    0,    0,  136,
  194, -208,    6,   55,    0, -183,    8,    0,   60,    0,
  176,    0,    0,  -68,    0,    0,    4,    0,    7,    3,
    0, -157,  125,   51,   51,  136,    0,    0,    8,  193,
    8,    0,    0,    0, -178,  150,   19,    0,  154,    0,
   26,    0,    0,    0,   18,  238,    0,    0,    0,  136,
    0,  243,    0,    0,    0,    0,  226,  229,  167,  230,
 -149, -149,    0,    0,    0,    0,   58,   72, -187,    0,
    0,  168, -121,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,  -40,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -16,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   10,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -54,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -35,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  232,    0,    0,  235,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   -5,    0,    0,   80,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   87,    0,    0,    0,  236,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   41,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -30,  -10,  255,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  130,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  173,    0,
    0,    0,  174,    0,
};
final static short yygindex[] = {                         0,
   11,    0,    0,    0,    0,   20,    0,  -49,    0,    0,
  120,   27,    9,    0,  212,    0,    0,    0,  148,    0,
    0,    0,    0,    0,    0,    0,   39,    0,  -25,    0,
   24,    0,    0,    0,    0,    0,  164,    0,    0,    0,
    0,  116,  -74,    0,    0,  155,   14,    0,    0,    0,
    0,    0,    0,    0,
};
final static int YYTABLESIZE=341;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         58,
   45,   45,   45,   45,   45,   48,   45,   48,   48,   48,
   46,   50,   46,   46,   46,   54,   35,   45,   45,   45,
  108,   45,  109,   48,   48,   52,   48,   19,   46,   46,
   47,   46,   47,   47,   47,   64,   46,  111,   64,  110,
    2,  133,   96,   33,   33,   61,    4,   66,   47,   47,
   45,   47,   66,   45,   46,   33,  162,   80,   84,  164,
  128,   66,   89,  141,  152,  153,  140,   78,  131,    8,
   94,    9,   37,   33,   82,   11,   12,   10,  132,   15,
  150,   13,   10,  149,  163,   16,   13,  147,  148,   97,
   16,  123,  113,  122,   36,  156,  123,  114,  155,   42,
  159,  158,  116,  155,  139,  120,    7,    8,  119,    9,
  108,   10,  109,   11,   12,   13,   14,   15,  164,   16,
   63,   39,   40,   63,   23,   23,   59,  102,  143,   33,
  102,  144,  145,   43,  157,    8,   23,    9,  146,  133,
   41,   11,   12,  163,  132,   15,   47,   48,    7,    8,
  165,    9,   55,   10,   23,   11,   12,   13,   14,   15,
   93,   16,   94,  123,   56,  169,   97,  108,   57,  109,
  101,   87,   33,  101,    1,  170,    7,    8,  108,    9,
  109,   10,  190,   11,   12,   13,   14,   15,    8,   16,
    9,  187,  188,   58,   11,   12,  191,  132,   15,   62,
   63,   83,   92,   79,   92,   88,   46,   46,   92,   92,
   23,   92,   92,   90,  102,  121,  103,  115,  118,   33,
   33,  130,  127,  136,   49,   33,   33,   33,   53,  138,
   45,   33,  142,  151,  160,   45,   45,   45,   45,   45,
   45,   48,   48,   48,   48,   48,   46,   46,   46,   46,
   46,  171,   51,   23,   19,  104,  105,  106,  107,    8,
   64,  168,  154,  166,    8,   64,   47,   47,   47,   47,
   47,   64,  174,    8,   64,  175,  176,   65,  179,  178,
   45,  177,   65,  180,  183,   45,   77,  184,  186,  185,
   39,   65,  194,   40,   14,   85,   75,   69,   68,  129,
   23,   23,  173,  167,  193,  172,   23,   23,   23,    0,
    0,    0,   23,    7,    8,    0,    9,    0,   10,    0,
   11,   12,   13,   14,   15,    0,   16,    7,    8,    0,
    9,    0,   10,    0,   11,   12,   13,   14,   15,    0,
   16,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   41,   42,   43,   44,   45,   41,   47,   43,   44,   45,
   41,   44,   43,   44,   45,   44,    6,  125,   59,   60,
   43,   62,   45,   59,   60,   44,   62,   44,   59,   60,
   41,   62,   43,   44,   45,   41,   17,   60,   44,   62,
  123,   91,   40,    5,    6,  125,  123,   45,   59,   60,
   41,   62,   45,   44,   35,   17,  125,   44,   50,  134,
   86,   45,   54,   41,  273,  274,   44,   41,  256,  257,
  125,  259,  282,   35,   48,  263,  264,  261,  266,  267,
   41,  265,  261,   44,  134,  269,  265,  113,  114,   63,
  269,   83,   42,  272,   59,   41,   88,   47,   44,  257,
   41,  127,   76,   44,   96,   79,  256,  257,   41,  259,
   43,  261,   45,  263,  264,  265,  266,  267,  193,  269,
   41,   40,   40,   44,    5,    6,  123,   41,  102,   91,
   44,  108,  109,   14,  126,  257,   17,  259,  112,  189,
   40,  263,  264,  193,  266,  267,   59,  276,  256,  257,
  137,  259,   59,  261,   35,  263,  264,  265,  266,  267,
  260,  269,  262,  155,   59,   41,  140,   43,   59,   45,
   41,   52,  134,   44,  257,  149,  256,  257,   43,  259,
   45,  261,  125,  263,  264,  265,  266,  267,  257,  269,
  259,  181,  182,   59,  263,  264,  125,  266,  267,  257,
   40,   40,  257,  276,  259,   40,  187,  188,  263,  264,
   91,  266,  267,  256,   40,  268,  258,   41,   41,  181,
  182,  125,   44,  125,  257,  187,  188,  189,  257,   59,
  271,  193,  277,   40,   59,  276,  277,  278,  279,  280,
  281,  277,  278,  279,  280,  281,  277,  278,  279,  280,
  281,   59,  271,  134,  271,  278,  279,  280,  281,  257,
  258,  142,  257,  257,  257,  258,  277,  278,  279,  280,
  281,  277,  123,  257,  258,  257,  123,  275,   41,  262,
  271,  256,  275,   41,   59,  276,  270,   59,   59,  123,
   59,  275,  125,   59,   59,   41,  256,  125,  125,   88,
  181,  182,  155,  140,  189,  151,  187,  188,  189,   -1,
   -1,   -1,  193,  256,  257,   -1,  259,   -1,  261,   -1,
  263,  264,  265,  266,  267,   -1,  269,  256,  257,   -1,
  259,   -1,  261,   -1,  263,  264,  265,  266,  267,   -1,
  269,
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
"cuerpo_lambda :",
"sentencias_ejecutables_lista : sentencias_ejecutables_lista sentencia_ejecutable",
"sentencias_ejecutables_lista : sentencia_ejecutable",
"sentencias_ejecutables_lista : sentencias_ejecutables_lista declaracion_ilegal",
"sentencias_ejecutables_lista : declaracion_ilegal",
"sentencias_ejecutables_lista : error ';'",
"$$9 :",
"declaracion_ilegal : VAR $$9 error ';'",
"constante : CTE",
"constante : '-' CTE",
"if_encabezado : IF '(' condicion ')'",
"condicional_if : if_encabezado bloque_ejecutable ENDIF ';'",
"$$10 :",
"condicional_if : if_encabezado bloque_ejecutable ELSE $$10 bloque_ejecutable ENDIF ';'",
"$$11 :",
"condicional_do_while : DO $$11 bloque_ejecutable WHILE '(' condicion ')' ';'",
"condicion : expresion simbolo_comparacion expresion",
"simbolo_comparacion : MAYOR_IGUAL",
"simbolo_comparacion : MENOR_IGUAL",
"simbolo_comparacion : DISTINTO",
"simbolo_comparacion : IGUAL_IGUAL",
"simbolo_comparacion : '>'",
"simbolo_comparacion : '<'",
"$$12 :",
"bloque_ejecutable : '{' $$12 sentencias_ejecutables_lista '}'",
"$$13 :",
"bloque_ejecutable : '{' $$13 '}'",
"bloque_ejecutable : '{' error '}'",
"salida_pantalla : PRINT '(' CADENA_MULTILINEA ')'",
"salida_pantalla : PRINT '(' expresion ')'",
"$$14 :",
"retorno_funcion : RETURN '(' $$14 lista_expresiones ')' ';'",
"lista_expresiones : lista_expresiones ',' expresion",
"lista_expresiones : expresion",
};

//#line 1014 "gramatica.y"

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
static Stack<Integer> pilaSaltosFunciones = new Stack<Integer>();
static Stack<Boolean> pilaHuboRetorno = new Stack<Boolean>();
int yylex() {
    int token = al.yylex();
    String lexema = al.getLexema();
    int linea = al.getFilaToken();
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
   int lineaError = al.getFilaToken();
   erroresSintacticos.add("Linea " + lineaError + ": Error de sintaxis. Verifique la estructura del codigo.");
}

public static void main(String args[]){
    if(args.length == 1) {
        al = new AnalizadorLexico(args[0]);
        g = Generador.getInstance();
        g.setGeneracionHabilitada(true);
        g.setAnalizadorLexico(al);
        Parser par = new Parser(false);
        par.yyparse();

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

        if (par.erroresSintacticos.isEmpty() && al.getErroresSemanticos().isEmpty()) {
            g.imprimirTercetos();
        } else {
            System.out.println("\nNo se generaron tercetos debido a los errores encontrados.");
        }

        al.imprimirTablaSimbolos();

        System.out.println("=======================================================");
    } else {
        System.out.println("Error: Se requiere la ruta del archivo fuente como unico parametro.");
    }
}
//#line 539 "Parser.java"
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
{ yyval.sval = "float"; }
break;
case 17:
//#line 79 "gramatica.y"
{ yyval.sval = "lambda"; }
break;
case 18:
//#line 83 "gramatica.y"
{
                    listaVariables.add(val_peek(0).sval);
                }
break;
case 19:
//#line 88 "gramatica.y"
{
                    listaVariables.clear();
                    listaVariables.add(val_peek(0).sval);
                }
break;
case 20:
//#line 94 "gramatica.y"
{
            g.setGeneracionHabilitada(true);
            String nombreFuncion = val_peek(4).sval;
            String tipoRetorno = val_peek(5).sval;
            String nombreAmbito = nombreFuncion;
            ArrayList<ParametroInfo> parametros = g.getListaParametros();
            pilaInicioFuncion.push(g.getProximoTerceto());
            String jump = g.addTerceto("BI", "_", "_");
            pilaSaltosFunciones.push(Integer.parseInt(jump.substring(1, jump.length()-1)));

            ArrayList<String> tiposEsperados = new ArrayList<String>();
            tiposEsperados.add(tipoRetorno);
            pilaTiposRetorno.push(tiposEsperados);
            pilaErrorEnFuncion.push(false);
            pilaHuboRetorno.push(false);
            if (g.existeEnAmbitoActual(nombreFuncion)) {
                al.agregarErrorSemantico("Linea " + val_peek(4).ival + ": Error Semantico: Redeclaracion de funcion '" + nombreFuncion + "'.");
                g.setGeneracionHabilitada(false);
                nombreAmbito = "GARBAGE_" + nombreFuncion;
                g.addTerceto("FUNC_LABEL", nombreFuncion);
            } else {
                al.agregarLexemaTS(nombreFuncion);
                al.agregarAtributoLexema(nombreFuncion, "Uso", "funcion");
                al.agregarAtributoLexema(nombreFuncion, "Linea", val_peek(4).ival);
                al.agregarAtributoLexema(nombreFuncion, "Tipo", tipoRetorno);
                al.agregarAtributoLexema(nombreFuncion, "Parametros", parametros);
                al.agregarAtributoLexema(nombreFuncion, "RetornoMultiple", false);
                g.addTerceto("FUNC_LABEL", nombreFuncion);
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
//#line 135 "gramatica.y"
{
            g.cerrarAmbito();
            g.setGeneracionHabilitada(true);

            pilaTiposRetorno.pop();
            boolean huboError = pilaErrorEnFuncion.pop();
            boolean huboReturn = pilaHuboRetorno.pop();
            int inicioFunc = pilaInicioFuncion.pop();
            int jumpIdx = pilaSaltosFunciones.pop();
            if (!huboReturn) {
                 /* CORRECCION APLICADA: Usar $9.ival (linea de '}') en lugar de $2.ival */
                 al.agregarErrorSemantico("Linea " + val_peek(0).ival + ": Error Semantico: La funcion '" + val_peek(7).sval + "' debe retornar un valor de tipo " + val_peek(8).sval + ".");
                 huboError = true;
            }

            if (huboError) {
                al.eliminarLexemaTS(val_peek(7).sval);
                g.anularTercetosDesde(inicioFunc);
                al.eliminarUltimoAmbito();
            } else {
                g.modificarSaltoTerceto(jumpIdx, "[" + g.getProximoTerceto() + "]");
                String nombreFuncion = val_peek(7).sval;
                salida.add("Linea " + val_peek(7).ival + ": Declaracion de Funcion '" + val_peek(7).sval + "' con retorno simple.");
            }
        }
break;
case 22:
//#line 161 "gramatica.y"
{
            g.setGeneracionHabilitada(true);
            String nombreFuncion = val_peek(4).sval;
            String nombreAmbito = nombreFuncion;
            ArrayList<?> rawList = (ArrayList<?>) val_peek(5).obj;
            ArrayList<String> tiposRetorno = new ArrayList<String>();
            for (Object o : rawList) {
                tiposRetorno.add((String) o);
            }
            pilaInicioFuncion.push(g.getProximoTerceto());

            String jump = g.addTerceto("BI", "_", "_");
            pilaSaltosFunciones.push(Integer.parseInt(jump.substring(1, jump.length()-1)));

            pilaTiposRetorno.push(tiposRetorno);
            pilaErrorEnFuncion.push(false);
            pilaHuboRetorno.push(false);

            ArrayList<ParametroInfo> parametros = g.getListaParametros();
            if (g.existeEnAmbitoActual(nombreFuncion)) {
                al.agregarErrorSemantico("Linea " + val_peek(4).ival + ": Error Semantico: Redeclaracion de funcion '" + nombreFuncion + "'.");
                g.setGeneracionHabilitada(false);
                nombreAmbito = "GARBAGE_" + nombreFuncion;
                g.addTerceto("FUNC_LABEL", nombreFuncion);
            } else {
                al.agregarLexemaTS(nombreFuncion);
                al.agregarAtributoLexema(nombreFuncion, "Uso", "funcion");
                al.agregarAtributoLexema(nombreFuncion, "Tipo", "multiple");
                al.agregarAtributoLexema(nombreFuncion, "TiposRetorno", tiposRetorno);
                al.agregarAtributoLexema(nombreFuncion, "Parametros", parametros);
                al.agregarAtributoLexema(nombreFuncion, "RetornoMultiple", true);
                g.addTerceto("FUNC_LABEL", nombreFuncion);
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
//#line 205 "gramatica.y"
{
            g.cerrarAmbito();
            g.setGeneracionHabilitada(true);

            pilaTiposRetorno.pop();
            boolean huboError = pilaErrorEnFuncion.pop();
            boolean huboReturn = pilaHuboRetorno.pop();
            int inicioFunc = pilaInicioFuncion.pop();
            int jumpIdx = pilaSaltosFunciones.pop();
            if (!huboReturn) {
                 /* CORRECCION APLICADA: Usar $9.ival (linea de '}') en lugar de $2.ival */
                 al.agregarErrorSemantico("Linea " + val_peek(0).ival + ": Error Semantico: La funcion '" + val_peek(7).sval + "' tiene retorno multiple y debe retornar valores.");
                 huboError = true;
            }

            if (huboError) {
                al.eliminarLexemaTS(val_peek(7).sval);
                g.anularTercetosDesde(inicioFunc);
                al.eliminarUltimoAmbito();
            } else {
                g.modificarSaltoTerceto(jumpIdx, "[" + g.getProximoTerceto() + "]");
                String nombreFuncion = val_peek(7).sval;
                salida.add("Linea " + val_peek(7).ival + ": Declaracion de Funcion '" + val_peek(7).sval + "' con retorno multiple.");
            }
        }
break;
case 24:
//#line 233 "gramatica.y"
{
                                 ArrayList<String> lista = new ArrayList<String>();
                                 lista.add(val_peek(2).sval);
                                 lista.add(val_peek(0).sval);
                                 yyval.obj = lista;
                             }
break;
case 25:
//#line 241 "gramatica.y"
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
//#line 258 "gramatica.y"
{
                 g.apilarParametro(new ParametroInfo(val_peek(0).sval, val_peek(1).sval, val_peek(2).sval));
             }
break;
case 29:
//#line 263 "gramatica.y"
{
                 String pasajeDefault = "default_cv";
                 g.apilarParametro(new ParametroInfo(val_peek(0).sval, val_peek(1).sval, pasajeDefault));
             }
break;
case 30:
//#line 269 "gramatica.y"
{ yyval.sval = "cr_se"; }
break;
case 31:
//#line 271 "gramatica.y"
{ yyval.sval = "cr_le"; }
break;
case 39:
//#line 287 "gramatica.y"
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
                       Object retMultiple = al.getAtributoMangled(funcName, "RetornoMultiple");
                       if (retMultiple == null || !(Boolean)retMultiple) {
                            al.agregarErrorSemantico("Linea " + linea + ": Error Semantico: Asignacion de funcion '" + funcName + "' que no retorna 'multiple' a variable simple.");
                       } else {
                           Object rawObj = al.getAtributoMangled(funcName, "TiposRetorno");
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
//#line 356 "gramatica.y"
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
            Object retMultiple = al.getAtributoMangled(funcName, "RetornoMultiple");
            if (retMultiple == null || !(Boolean)retMultiple) {
                if (cantIzquierda == 1) {
                    String var = listaVariables.get(0);
                    String tipoVar = g.getTipo(var);
                    String tipoRet = (String) al.getAtributoMangled(funcName, "Tipo");
                    if (g.chequearAsignacion(tipoVar, tipoRet, Integer.parseInt(lineaActual))) {
                        g.addTerceto(":=", var, funcTerceto);
                    }
                } else {
                    al.agregarErrorSemantico("Linea " + lineaActual + ": Error Semantico: Asignacion multiple a funcion '" + funcName + "' que no tiene retorno multiple.");
                }
            } else {
                Object rawObj = al.getAtributoMangled(funcName, "TiposRetorno");
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
                        al.agregarWarning("Linea " + lineaActual + ": Warning (Tema 21): Funcion '" + funcName + "' retorna " + cantRetornos + " valores, pero solo se asignan " + cantIzquierda + ". Se descartan los sobrantes.");
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
//#line 445 "gramatica.y"
{ g.clearLadoDerecho();
                        }
break;
case 42:
//#line 447 "gramatica.y"
{
                              g.apilarLadoDerecho(g.desapilarOperando());
                              contadorLadoDerecho = 1;
                              yyval.ival = val_peek(0).ival;
                              yyval.sval = val_peek(0).sval;
                          }
break;
case 43:
//#line 455 "gramatica.y"
{
                              g.apilarLadoDerecho(g.desapilarOperando());
                              contadorLadoDerecho++;
                              yyval.ival = 0;
                          }
break;
case 44:
//#line 463 "gramatica.y"
{
                yyval.sval = val_peek(2).sval + "."
                + val_peek(0).sval;
                yyval.ival = val_peek(2).ival;
            }
break;
case 45:
//#line 470 "gramatica.y"
{
                yyval.sval = val_peek(0).sval;
                yyval.ival = val_peek(0).ival;
            }
break;
case 46:
//#line 477 "gramatica.y"
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
//#line 488 "gramatica.y"
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
//#line 498 "gramatica.y"
{ yyval.ival = val_peek(0).ival;
          }
break;
case 49:
//#line 503 "gramatica.y"
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
//#line 514 "gramatica.y"
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
//#line 523 "gramatica.y"
{ yyval.ival = val_peek(0).ival;
        }
break;
case 52:
//#line 528 "gramatica.y"
{
           yyval.ival = val_peek(0).ival;
       }
break;
case 53:
//#line 532 "gramatica.y"
{
           yyval.ival = val_peek(0).ival;
           yyval.sval = val_peek(0).sval;
           g.apilarOperando(val_peek(0).sval);
       }
break;
case 54:
//#line 540 "gramatica.y"
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
//#line 564 "gramatica.y"
{
                      g.apilarOperando(val_peek(0).sval);
                      yyval.ival = val_peek(0).ival;
                  }
break;
case 56:
//#line 570 "gramatica.y"
{ yyval.ival = val_peek(0).ival;
                  }
break;
case 57:
//#line 575 "gramatica.y"
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
//#line 594 "gramatica.y"
{ g.clearParametrosReales(); }
break;
case 59:
//#line 598 "gramatica.y"
{
                       String funcName = val_peek(4).sval;
                       int linea = val_peek(4).ival;
                       int cantReales = val_peek(1).ival;
                       ArrayList<ParametroRealInfo> reales = g.getListaParametrosReales(cantReales);
                       Object uso = al.getAtributo(funcName, "Uso");
                       String tipo = g.getTipo(funcName);
                       if (uso != null && (uso.toString().equals("parametro") || uso.toString().equals("parametro_lambda") || uso.toString().equals("variable")) && tipo.equals("lambda")) {
                           if (reales.size() != 1) {
                               al.agregarErrorSemantico("Linea " + linea + ": Error Semantico (Tema 28): Invocacion de lambda '" + funcName + "' con numero incorrecto de \n parametros. Esperado: 1, Obtenido: " + reales.size() + ".");
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
                                al.agregarErrorSemantico("Linea " + linea + ": Error Semantico: No se \n pudo recuperar la firma de la funcion '" + funcName + "'.");
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
                                           al.agregarErrorSemantico("Linea " + linea + ": \n Error Semantico (Tema 28): Se paso una expresion lambda al parametro '->" + real.nombreFormal + "'.");
                                           errorEnParametros = true;
                                       } else if (tipoFormal.equals("lambda") && !tipoReal.equals("lambda_expr")) {
                                           al.agregarErrorSemantico("Linea " + linea + ": Error Semantico (Tema 28): El parametro '->" + real.nombreFormal + "' espera una expresion 'lambda'.");
                                           errorEnParametros = true;
                                       } else if (!tipoReal.equals("error_tipo") && !g.chequearAsignacion(tipoFormal, tipoReal, linea)) {
                                           al.agregarErrorSemantico("Linea " + linea + ": Error Semantico: Invocacion a '" + funcName + "': tipo incompatible para '->" + real.nombreFormal + "'. Esperado: " + tipoFormal + ", Obtenido: " + tipoReal + ".");
                                           errorEnParametros = true;
                                       }
                                       if (!errorEnParametros) {
                                            String mangledFunc = al.getNombreMangled(funcName);
                                            String nombreParametro = formal.nombre;
                                            if (mangledFunc.contains(":")) {
                                                int firstColon = mangledFunc.indexOf(':');
                                                String name = mangledFunc.substring(0, firstColon);
                                                String scope = mangledFunc.substring(firstColon + 1);
                                                String scopeBody = scope + ":" + name;
                                                nombreParametro = formal.nombre + ":" + scopeBody;
                                            }
                                            g.addTerceto("PARAM", real.operando, nombreParametro);
                                       }
                                   }
                               }
                               if (!errorEnParametros) {

                                  String terceto = g.addTerceto("CALL", funcName);
                                  g.getTerceto(Integer.parseInt(terceto.substring(1, terceto.length()-1))).setTipo(al.getAtributo(funcName, "Tipo").toString());
                                  yyval.sval = terceto;

                                  for (ParametroRealInfo real : reales) {
                                      if (real.nombreFormal != null) {
                                          ParametroInfo formal = formales.stream().filter(f ->
                                          f.nombre.equals(real.nombreFormal)).findFirst().orElse(null);
                                          if (formal != null && (formal.pasaje.equals("cr_se") || formal.pasaje.equals("cr_le"))) {
                                              String mangledFunc = al.getNombreMangled(funcName);
                                              String nombreParametro = formal.nombre;
                                              if (mangledFunc.contains(":")) {
                                                  int firstColon = mangledFunc.indexOf(':');
                                                  String name = mangledFunc.substring(0, firstColon);
                                                  String scope = mangledFunc.substring(firstColon + 1);
                                                  String scopeBody = scope + ":" + name;
                                                  nombreParametro = formal.nombre + ":" + scopeBody;
                                              }
                                              g.addTerceto(":=", real.operando, nombreParametro);
                                          }
                                      }
                                  }


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
//#line 715 "gramatica.y"
{
                            yyval.ival = val_peek(2).ival + 1;
                        }
break;
case 61:
//#line 720 "gramatica.y"
{
                            yyval.ival = 1;
                        }
break;
case 62:
//#line 726 "gramatica.y"
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
//#line 738 "gramatica.y"
{
                   g.apilarParametroReal(new ParametroRealInfo(val_peek(0).sval, null));
                   yyval.ival = val_peek(0).ival;
               }
break;
case 64:
//#line 745 "gramatica.y"
{
                     yyval.sval = g.desapilarOperando();
                     yyval.ival = val_peek(0).ival;
                 }
break;
case 65:
//#line 751 "gramatica.y"
{
                     yyval.sval = val_peek(0).sval;
                     yyval.ival = val_peek(0).ival;
                 }
break;
case 66:
//#line 758 "gramatica.y"
{
                    pilaSaltosLambda.push(g.addTerceto("BI", "_", "_"));
                    int inicioLambda = g.getProximoTerceto();
                    yyval.sval = "L" + String.valueOf(inicioLambda);
                    g.abrirAmbito("lambda_" + inicioLambda);
                    al.agregarLexemaTS(val_peek(2).sval);
                    al.agregarAtributoLexema(val_peek(2).sval, "Uso", "parametro_lambda");
                    al.agregarAtributoLexema(val_peek(2).sval, "Tipo", val_peek(3).sval);
                    g.addTerceto("DEF_PARAM", val_peek(2).sval, "_");
                    pilaHuboRetorno.push(false);
                    yyval.ival = val_peek(4).ival;
                 }
break;
case 67:
//#line 771 "gramatica.y"
{
                    g.addTerceto("RET_LAMBDA", "_", "_");
                    int tercetoFin = g.getProximoTerceto();
                    String saltoIncondicional = pilaSaltosLambda.pop();
                    g.modificarSaltoTerceto(Integer.parseInt(saltoIncondicional.substring(1, saltoIncondicional.length()-1)), "[" + tercetoFin + "]");
                    g.cerrarAmbito();

                    boolean huboReturn = pilaHuboRetorno.pop();
                    if (!huboReturn) {
                        al.agregarErrorSemantico("Linea " + val_peek(7).ival + ": Error Semantico: La funcion lambda finaliza sin una sentencia de retorno valida.");
                    }

                    yyval.sval = val_peek(2).sval;
                    yyval.ival = val_peek(7).ival;
                 }
break;
case 75:
//#line 800 "gramatica.y"
{
                        erroresSintacticos.add("Linea " + al.getFilaToken() + ": Error sintactico: No se permiten declaraciones en bloques ejecutables.");
                   }
break;
case 77:
//#line 806 "gramatica.y"
{
                yyval.sval = val_peek(0).sval;
                yyval.ival = val_peek(0).ival;
            }
break;
case 78:
//#line 812 "gramatica.y"
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
case 79:
//#line 836 "gramatica.y"
{
                   String cond = g.desapilarOperando();
                   if (cond.equals("ERROR_CONDICION")) {
                       g.apilarControl(-1);
                   } else {
                       String bf = g.addTerceto("BF", cond, "_");
                       int bfIdx = Integer.parseInt(bf.substring(1, bf.length()-1));
                       g.apilarControl(bfIdx);
                   }
                   yyval.ival = val_peek(3).ival;
               }
break;
case 80:
//#line 850 "gramatica.y"
{
                   int bfIdx = g.desapilarControl();
                   if (bfIdx != -1) {
                       int finIf = g.getProximoTerceto();
                       g.modificarSaltoTerceto(bfIdx, "[" + finIf + "]");
                   }
                   salida.add("Linea " + val_peek(3).ival + ": Sentencia IF reconocida.");
               }
break;
case 81:
//#line 859 "gramatica.y"
{
                   int bfIdx = g.desapilarControl();
                   String bi = g.addTerceto("BI", "_", "_");
                   int biIdx = Integer.parseInt(bi.substring(1, bi.length()-1));
                   g.apilarControl(biIdx);
                   if (bfIdx != -1) {
                       int inicioElse = g.getProximoTerceto();
                       g.modificarSaltoTerceto(bfIdx, "[" + inicioElse + "]");
                   }
               }
break;
case 82:
//#line 869 "gramatica.y"
{
                   int biIdx = g.desapilarControl();
                   int finElse = g.getProximoTerceto();
                   g.modificarSaltoTerceto(biIdx, "[" + finElse + "]");

                   salida.add("Linea " + val_peek(6).ival + ": Sentencia IF-ELSE reconocida.");
               }
break;
case 83:
//#line 879 "gramatica.y"
{
                        g.apilarControl(g.getProximoTerceto());
                    }
break;
case 84:
//#line 883 "gramatica.y"
{
                        Object lineaObj = al.getAtributo("do", "Linea");
                        salida.add("Linea " + val_peek(1).ival + ": Sentencia DO-WHILE reconocida.");
                        String refCondicion = g.desapilarOperando();
                        int inicioBucle = g.desapilarControl();
                        if (refCondicion.equals("ERROR_CONDICION")) {
                             al.agregarErrorSemantico("Linea " + val_peek(1).ival + ": Error Semantico: No se genero el salto del DO-WHILE debido a una condicion invalida.");
                        } else {
                            String tercetoSalto = g.addTerceto("BT", refCondicion, "[" + inicioBucle + "]");
                        }
                    }
break;
case 85:
//#line 897 "gramatica.y"
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
case 86:
//#line 912 "gramatica.y"
{ g.apilarOperando(">="); }
break;
case 87:
//#line 914 "gramatica.y"
{ g.apilarOperando("<="); }
break;
case 88:
//#line 916 "gramatica.y"
{ g.apilarOperando("=!"); }
break;
case 89:
//#line 918 "gramatica.y"
{ g.apilarOperando("=="); }
break;
case 90:
//#line 920 "gramatica.y"
{ g.apilarOperando(">"); }
break;
case 91:
//#line 922 "gramatica.y"
{ g.apilarOperando("<"); }
break;
case 92:
//#line 925 "gramatica.y"
{ g.abrirAmbito("bloque_" + g.getProximoTerceto()); }
break;
case 93:
//#line 925 "gramatica.y"
{ g.cerrarAmbito();}
break;
case 94:
//#line 927 "gramatica.y"
{ g.abrirAmbito("bloque_" + g.getProximoTerceto()); }
break;
case 95:
//#line 927 "gramatica.y"
{ g.cerrarAmbito(); }
break;
case 97:
//#line 933 "gramatica.y"
{
                    salida.add("Linea " + val_peek(3).ival + ": PRINT con cadena multilinea.");
                    g.addTerceto("PRINT", val_peek(1).sval);
                }
break;
case 98:
//#line 939 "gramatica.y"
{
                    salida.add("Linea " + val_peek(3).ival + ": PRINT con expresion.");
                    g.addTerceto("PRINT", g.desapilarOperando());
                }
break;
case 99:
//#line 945 "gramatica.y"
{ enSentenciaReturn = true; }
break;
case 100:
//#line 946 "gramatica.y"
{
                enSentenciaReturn = false;
                if (pilaTiposRetorno.isEmpty()) {
                    al.agregarErrorSemantico("Linea " + val_peek(5).ival + ": Error Semantico: 'return' encontrado fuera de una funcion valida (o la declaracion de la funcion fallo por error sintactico previo).");
                } else {
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
                        if (!pilaHuboRetorno.isEmpty()) {
                            pilaHuboRetorno.pop();
                            pilaHuboRetorno.push(true);
                        }

                        salida.add("Linea " + val_peek(5).ival + ": Sentencia RETURN.");
                        int total = expresiones.size();
                        for (int i = 0; i < total; i++) {
                            g.addTerceto("RETURN", expresiones.get(i), i + "/" + total);
                        }
                    }
                }
            }
break;
case 101:
//#line 995 "gramatica.y"
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
case 102:
//#line 1006 "gramatica.y"
{
                  ArrayList<String> lista = new ArrayList<String>();
                  lista.add(g.desapilarOperando());
                  yyval.obj = lista;
              }
break;
//#line 1732 "Parser.java"
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
