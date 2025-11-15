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
   17,    9,   19,   20,    9,   18,   18,   15,   15,   21,
   21,   22,   22,    8,    8,    8,    8,    8,    8,    8,
   23,   24,   32,   30,   30,   11,   11,   12,   12,   12,
   33,   33,   33,   31,   31,   34,   34,   34,   36,   37,
   29,   38,   38,   39,   39,   40,   40,   43,   41,   42,
   44,   44,   35,   35,   25,   25,   47,   26,   45,   48,
   48,   48,   48,   48,   48,   49,   46,   46,   27,   27,
   28,   50,   50,
};
final static short yylen[] = {                            2,
    0,    0,    6,    0,    0,    5,    2,    1,    1,    1,
    2,    1,    2,    4,    1,    1,    1,    3,    1,    0,
    0,   10,    0,    0,   10,    3,    3,    3,    1,    3,
    2,    2,    2,    2,    2,    1,    1,    2,    1,    2,
    3,    3,    0,    2,    3,    3,    1,    3,    3,    1,
    3,    3,    1,    1,    1,    1,    1,    1,    4,    0,
    5,    3,    1,    3,    1,    1,    1,    0,    8,    1,
    2,    1,    1,    2,    7,    9,    0,    8,    3,    1,
    1,    1,    1,    1,    1,    0,    4,    3,    4,    4,
    5,    3,    1,
};
final static short yydefred[] = {                         0,
    0,    4,    0,    1,    0,    0,    0,    0,    0,   16,
    0,    0,   15,    0,   77,   17,    0,    8,    9,   10,
   12,    0,    0,    0,    0,    0,    0,    0,   36,   37,
    0,   39,    0,    0,   11,    0,    0,    0,    0,    0,
    0,    0,    0,    5,    7,   13,    0,    0,    0,   43,
    0,    0,    0,   34,   35,   38,   40,    2,   46,    0,
   73,    0,    0,   56,    0,   55,   53,    0,   54,   57,
   58,    0,    0,    0,    0,    0,    0,    0,    0,    6,
    0,    0,   26,    0,    0,   18,    0,   27,    3,    0,
    0,    0,   63,    0,   67,    0,   74,   80,   81,   82,
   83,    0,    0,   84,   85,    0,    0,    0,    0,    0,
    0,   89,   90,    0,    0,    0,    0,    0,    0,    0,
   29,    0,    0,   44,    0,    0,    0,   61,    0,    0,
    0,    0,    0,   51,   52,    0,    0,   91,   88,   72,
    0,    0,   32,   33,   31,    0,    0,    0,   45,    0,
    0,   62,   64,   59,    0,    0,   87,   71,    0,   28,
   20,   30,   23,    0,    0,   75,    0,    0,    0,   68,
    0,   78,    0,    0,    0,   76,   21,   24,    0,    0,
   22,   25,   69,
};
final static short yydgoto[] = {                          3,
   17,    6,   89,    5,   80,   18,   19,   20,   21,   22,
   64,   65,   24,   25,  120,  168,  181,   26,  169,  182,
  121,  122,   27,   28,   29,   30,   31,   32,   66,   84,
   67,   85,   68,   69,   70,   71,   37,   92,   93,   94,
   95,  179,  175,  141,   72,   79,   43,  106,  116,   74,
};
final static short yysindex[] = {                      -105,
  -68,    0,    0,    0, -148, -148,   13, -217,   44,    0,
   45,   72,    0, -164,    0,    0,   33,    0,    0,    0,
    0,   64, -144,  -32,  -18,  -28,   92,   98,    0,    0,
  103,    0,  105,   47,    0, -103,  119,   16,   16,    6,
 -217, -116,   42,    0,    0,    0,   16,  121, -183,    0,
 -164,  123, -183,    0,    0,    0,    0,    0,    0,    3,
    0,  126,  -91,    0,  -22,    0,    0,   24,    0,    0,
    0,  127,  104,   51,  129,   38,   16,  -85,  -95,    0,
  104, -192,    0,  130,   16,    0, -192,    0,    0, -183,
  104,   84,    0, -102,    0,   16,    0,    0,    0,    0,
    0,   16,   16,    0,    0,   16,   16,   16,   42,   16,
  117,    0,    0,  104,   52, -157,  138, -232,  -75,   95,
    0, -183,   16,    0,   96,  -74,    3,    0,  -73,   90,
   24,   24,  104,    0,    0, -112,  104,    0,    0,    0,
  -62,   16,    0,    0,    0, -192,   62,  -70,    0,   65,
  148,    0,    0,    0,   42,  131,    0,    0,  150,    0,
    0,    0,    0,   69,  -69,    0,  137, -148, -148,    0,
  139,    0,   61,   75, -157,    0,    0,    0,   74, -157,
    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,  -40,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -16,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    9,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -35,    0,    0,
    0,    0,  100,    0,    0,    0,    0, -137,    0,    0,
  147,    0,    0,  151,    0,    0,    0,    0,    0,    0,
   -5,    0,    0,  101,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  152,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -30,  -10,  166,    0,    0,    0,  102,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   87,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
   11,    0,    0,    0,    0,   30,    0,  -79,    0,    0,
   40,   28,    7,    0,  132,    0,    0,    0,    0,    0,
   70,    0,    0,    0,    0,    0,    0,    0,   53,    0,
   -9,    0,    1,    0,    0,    0,    0,    0,   91,    0,
    0,    0,    0,   48,   82,  -65,    0,    0,    0,    0,
};
final static int YYTABLESIZE=344;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         60,
   47,   47,   47,   47,   47,   50,   47,   50,   50,   50,
   48,   49,   48,   48,   48,   53,   34,    2,   47,   47,
  102,   47,  103,   50,   50,   51,   50,   19,   48,   48,
   49,   48,   49,   49,   49,   66,  140,  105,   66,  104,
  143,  144,   90,  136,   23,   23,   45,   63,   49,   49,
   63,   49,   47,   42,    4,   83,   23,   33,   33,   88,
   63,  158,  157,   45,   36,  107,   73,   76,   10,   33,
  108,   35,   13,   23,   81,  124,   16,   10,  113,  118,
  102,   13,  103,   38,   39,   16,   33,   91,  119,  165,
   86,  111,   41,  119,  110,  140,  126,  134,  135,    8,
  158,    9,  131,  132,  114,   11,   12,    7,    8,   15,
    9,   40,   10,  149,   11,   12,   13,   14,   15,   86,
   16,   86,   46,  130,  128,   86,   86,  127,  148,   86,
  154,   47,  102,  133,  103,  147,  150,  137,  146,  146,
   93,   65,   92,   93,   65,   92,  102,  155,  103,  156,
   54,    1,  119,   59,   91,   23,   55,   44,   60,   77,
   82,   56,   87,   57,   78,   96,   97,  109,   33,  112,
  115,   58,  117,  123,  129,  138,  139,  142,  173,  174,
   23,  145,  151,  153,  161,  177,  162,  163,  164,  166,
  167,  170,  171,   33,    8,  172,    9,  176,  183,  178,
   11,   12,   45,   45,   15,   41,   79,   23,   23,   42,
   14,   70,   23,   23,   23,  160,    0,  152,  125,   23,
   33,   33,  180,  159,   48,   33,   33,   33,   52,    0,
   47,    0,   33,    0,    0,   47,   47,   47,   47,   47,
   47,   50,   50,   50,   50,   50,   48,   48,   48,   48,
   48,    0,   50,    0,   19,   98,   99,  100,  101,    8,
   61,    0,    8,   61,    0,    0,   49,   49,   49,   49,
   49,   66,    8,   61,    0,   75,    0,   62,    0,   47,
   62,    0,    0,    0,   47,    0,    0,    0,    7,    8,
   62,    9,    0,   10,    0,   11,   12,   13,   14,   15,
    0,   16,    7,    8,    0,    9,    0,   10,    0,   11,
   12,   13,   14,   15,    0,   16,    7,    8,    0,    9,
    0,   10,    0,   11,   12,   13,   14,   15,    0,   16,
    7,    8,    0,    9,    0,   10,    0,   11,   12,   13,
   14,   15,    0,   16,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   41,   42,   43,   44,   45,   41,   47,   43,   44,   45,
   41,   44,   43,   44,   45,   44,    6,  123,   59,   60,
   43,   62,   45,   59,   60,   44,   62,   44,   59,   60,
   41,   62,   43,   44,   45,   41,  116,   60,   44,   62,
  273,  274,   40,  109,    5,    6,   17,   45,   59,   60,
   45,   62,   44,   14,  123,   49,   17,    5,    6,   53,
   45,  141,  125,   34,  282,   42,   39,   40,  261,   17,
   47,   59,  265,   34,   47,   85,  269,  261,   41,  272,
   43,  265,   45,   40,   40,  269,   34,   60,   82,  155,
   51,   41,  257,   87,   44,  175,   90,  107,  108,  257,
  180,  259,  102,  103,   77,  263,  264,  256,  257,  267,
  259,   40,  261,  123,  263,  264,  265,  266,  267,  257,
  269,  259,   59,   96,   41,  263,  264,   44,  122,  267,
   41,  276,   43,  106,   45,   41,   41,  110,   44,   44,
   41,   41,   41,   44,   44,   44,   43,  260,   45,  262,
   59,  257,  146,  257,  127,  116,   59,  125,   40,  276,
   40,   59,   40,   59,  123,   40,  258,   41,  116,   41,
  256,  125,  268,   44,  277,   59,  125,   40,  168,  169,
  141,  257,  257,  257,  123,  125,  257,  123,   41,   59,
   41,  123,  262,  141,  257,   59,  259,   59,  125,  125,
  263,  264,  173,  174,  267,   59,   41,  168,  169,   59,
   59,  125,  173,  174,  175,  146,   -1,  127,   87,  180,
  168,  169,  175,  142,  257,  173,  174,  175,  257,   -1,
  271,   -1,  180,   -1,   -1,  276,  277,  278,  279,  280,
  281,  277,  278,  279,  280,  281,  277,  278,  279,  280,
  281,   -1,  271,   -1,  271,  278,  279,  280,  281,  257,
  258,   -1,  257,  258,   -1,   -1,  277,  278,  279,  280,
  281,  277,  257,  258,   -1,  270,   -1,  275,   -1,  271,
  275,   -1,   -1,   -1,  276,   -1,   -1,   -1,  256,  257,
  275,  259,   -1,  261,   -1,  263,  264,  265,  266,  267,
   -1,  269,  256,  257,   -1,  259,   -1,  261,   -1,  263,
  264,  265,  266,  267,   -1,  269,  256,  257,   -1,  259,
   -1,  261,   -1,  263,  264,  265,  266,  267,   -1,  269,
  256,  257,   -1,  259,   -1,  261,   -1,  263,  264,  265,
  266,  267,   -1,  269,
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
"$$6 :",
"funcion : tipo ID '(' lista_parametros_formales ')' '{' $$5 sentencias '}' $$6",
"$$7 :",
"$$8 :",
"funcion : lista_tipos_retorno_multiple ID '(' lista_parametros_formales ')' '{' $$7 sentencias '}' $$8",
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
"$$9 :",
"lado_derecho_multiple : $$9 factor",
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
"parametro_real : parametro_simple FLECHA ID",
"parametro_real : parametro_simple",
"parametro_simple : expresion",
"parametro_simple : lambda_expresion",
"$$10 :",
"lambda_expresion : '(' tipo ID ')' '{' $$10 cuerpo_lambda '}'",
"cuerpo_lambda : sentencias_ejecutables_lista",
"sentencias_ejecutables_lista : sentencias_ejecutables_lista sentencia_ejecutable",
"sentencias_ejecutables_lista : sentencia_ejecutable",
"constante : CTE",
"constante : '-' CTE",
"condicional_if : IF '(' condicion ')' bloque_ejecutable ENDIF ';'",
"condicional_if : IF '(' condicion ')' bloque_ejecutable ELSE bloque_ejecutable ENDIF ';'",
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
"bloque_ejecutable : '{' error '}'",
"salida_pantalla : PRINT '(' CADENA_MULTILINEA ')'",
"salida_pantalla : PRINT '(' expresion ')'",
"retorno_funcion : RETURN '(' lista_expresiones ')' ';'",
"lista_expresiones : lista_expresiones ',' expresion",
"lista_expresiones : expresion",
};

//#line 677 "gramatica.y"

static AnalizadorLexico al;
static Generador g;
ArrayList<String> erroresSintacticos = new ArrayList<String>();
ArrayList<String> erroresSemanticos = new ArrayList<String>();
ArrayList<String> salida = new ArrayList<String>();
ArrayList<String> listaVariables = new ArrayList<String>();
int contadorLadoDerecho = 0;
Stack<String> pilaSaltosLambda = new Stack<String>();

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

        g.imprimirTercetos();
        al.imprimirTablaSimbolos();

        System.out.println("=======================================================");
    } else {
        System.out.println("Error: Se requiere la ruta del archivo fuente como unico parametro.");
    }
}
//#line 527 "Parser.java"
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
//#line 56 "gramatica.y"
{
                    salida.add("Linea " + (al.getContadorFila()+1) + ": Declaracion por inferencia (var).");
                    String expr = g.desapilarOperando();
                    String tipoExpr = g.getTipo(expr);
                    String varNombre = val_peek(2).sval;
                    if (g.existeEnAmbitoActual(varNombre)) {
                        al.agregarErrorSemantico("Linea " + (al.getContadorFila()+1) + ": Error Semantico: Redeclaracion de variable '" + varNombre + "' en el mismo ambito (Tema 9).");
                    } else if (tipoExpr.equals("error_tipo") || tipoExpr.equals("indefinido")) {
                         al.agregarErrorSemantico("Linea " + (al.getContadorFila()+1) + ": Error Semantico: No se puede inferir el tipo de '" + varNombre + "' desde una expresion invalida (Tema 9).");
                    } else {
                        al.agregarLexemaTS(varNombre);
                        al.agregarAtributoLexema(varNombre, "Uso", "variable");
                        al.agregarAtributoLexema(varNombre, "Tipo", tipoExpr);
                        g.addTerceto(":=", varNombre, expr);
                    }
                }
break;
case 15:
//#line 74 "gramatica.y"
{ yyval.sval = "uint"; }
break;
case 16:
//#line 75 "gramatica.y"
{ yyval.sval = "float"; }
break;
case 17:
//#line 76 "gramatica.y"
{ yyval.sval = "lambda"; }
break;
case 18:
//#line 80 "gramatica.y"
{
                    listaVariables.add(val_peek(0).sval);
                }
break;
case 19:
//#line 85 "gramatica.y"
{
                    listaVariables.clear();
                    listaVariables.add(val_peek(0).sval);
                }
break;
case 20:
//#line 91 "gramatica.y"
{
            String nombreFuncion = val_peek(4).sval;
            String tipoRetorno = val_peek(5).sval;
            ArrayList<ParametroInfo> parametros = g.getListaParametros();
            if (g.existeEnAmbitoActual(nombreFuncion)) {
                al.agregarErrorSemantico("Linea " + (al.getContadorFila()+1) + ": Error Semantico: Redeclaracion de funcion '" + nombreFuncion + "'.");
            } else {
                al.agregarLexemaTS(nombreFuncion);
                al.agregarAtributoLexema(nombreFuncion, "Uso", "funcion");
                al.agregarAtributoLexema(nombreFuncion, "Linea", al.getContadorFila()+1);
                al.agregarAtributoLexema(nombreFuncion, "Tipo", tipoRetorno);
                al.agregarAtributoLexema(nombreFuncion, "Parametros", parametros);
                al.agregarAtributoLexema(nombreFuncion, "RetornoMultiple", false);
            }
            g.abrirAmbito(val_peek(4).sval);
            for (ParametroInfo p : parametros) {
                if (g.existeEnAmbitoActual(p.nombre)) {
                     al.agregarErrorSemantico("Linea " + (al.getContadorFila()+1) + ": Error Semantico: Redeclaracion del parametro '" + p.nombre + "'.");
                } else {
                     al.agregarLexemaTS(p.nombre);
                     al.agregarAtributoLexema(p.nombre, "Uso", "parametro");
                     al.agregarAtributoLexema(nombreFuncion, "Linea", al.getContadorFila()+1);
                     al.agregarAtributoLexema(p.nombre, "Tipo", p.tipo);
                     al.agregarAtributoLexema(p.nombre, "Pasaje", p.pasaje);
                }
            }
        }
break;
case 21:
//#line 117 "gramatica.y"
{ g.cerrarAmbito(); }
break;
case 22:
//#line 118 "gramatica.y"
{
            String nombreFuncion = val_peek(8).sval;
            salida.add("Linea " + (al.getContadorFila()+1) + ": Declaracion de Funcion '" + val_peek(8).sval + "' con retorno simple.");
        }
break;
case 23:
//#line 122 "gramatica.y"
{
            String nombreFuncion = val_peek(4).sval;
            ArrayList<?> rawList = (ArrayList<?>) val_peek(5).obj;
            ArrayList<String> tiposRetorno = new ArrayList<String>();
            for (Object o : rawList) {
                tiposRetorno.add((String) o);
            }
            ArrayList<ParametroInfo> parametros = g.getListaParametros();
            if (g.existeEnAmbitoActual(nombreFuncion)) {
                al.agregarErrorSemantico("Linea " + (al.getContadorFila()+1) + ": Error Semantico: Redeclaracion de funcion '" + nombreFuncion + "'.");
            } else {
                al.agregarLexemaTS(nombreFuncion);
                al.agregarAtributoLexema(nombreFuncion, "Uso", "funcion");
                al.agregarAtributoLexema(nombreFuncion, "Tipo", "multiple");
                al.agregarAtributoLexema(nombreFuncion, "TiposRetorno", tiposRetorno);
                al.agregarAtributoLexema(nombreFuncion, "Parametros", parametros);
                al.agregarAtributoLexema(nombreFuncion, "RetornoMultiple", true);
            }
            g.abrirAmbito(val_peek(4).sval);
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
        }
break;
case 24:
//#line 151 "gramatica.y"
{ g.cerrarAmbito(); }
break;
case 25:
//#line 152 "gramatica.y"
{
            String nombreFuncion = val_peek(8).sval;
            salida.add("Linea " + (al.getContadorFila()+1) + ": Declaracion de Funcion '" + val_peek(8).sval + "' con retorno multiple.");
        }
break;
case 26:
//#line 159 "gramatica.y"
{
                                 ArrayList<String> lista = new ArrayList<String>();
                                 lista.add(val_peek(2).sval);
                                 lista.add(val_peek(0).sval);
                                 yyval.obj = lista;
                             }
break;
case 27:
//#line 167 "gramatica.y"
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
case 30:
//#line 183 "gramatica.y"
{
                 g.apilarParametro(new ParametroInfo(val_peek(0).sval, val_peek(1).sval, val_peek(2).sval));
             }
break;
case 31:
//#line 188 "gramatica.y"
{
                 String pasajeDefault = "default_cv";
                 g.apilarParametro(new ParametroInfo(val_peek(0).sval, val_peek(1).sval, pasajeDefault));
             }
break;
case 32:
//#line 194 "gramatica.y"
{ yyval.sval = "cr_se"; }
break;
case 33:
//#line 196 "gramatica.y"
{ yyval.sval = "cr_le"; }
break;
case 41:
//#line 209 "gramatica.y"
{
               salida.add("Linea " + (al.getContadorFila()+1) + ": Asignacion simple (:=).");
               String op2 = g.desapilarOperando();
               String op1 = val_peek(2).sval;
               String tipoVar = g.getTipo(op1);
               String tipoExpr = g.getTipo(op2);
               if (g.chequearAsignacion(tipoVar, tipoExpr, al.getContadorFila()+1)) {
                   g.addTerceto(":=", op1, op2);
               }
           }
break;
case 42:
//#line 222 "gramatica.y"
{
                            String lineaActual = String.valueOf(al.getContadorFila() + 1);
                            int cantIzquierda = listaVariables.size();
                            int cantDerecha = contadorLadoDerecho;
                            Stack<String> derechos = g.getPilaLadoDerecho();
                            boolean esFuncion = (val_peek(0).ival == 1);
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
case 43:
//#line 287 "gramatica.y"
{ g.clearLadoDerecho(); }
break;
case 44:
//#line 288 "gramatica.y"
{
                              g.apilarLadoDerecho(g.desapilarOperando());
                              contadorLadoDerecho = 1;
                              yyval.ival = val_peek(0).ival;
                              yyval.sval = val_peek(0).sval;
                          }
break;
case 45:
//#line 296 "gramatica.y"
{
                              g.apilarLadoDerecho(g.desapilarOperando());
                              contadorLadoDerecho++;
                              yyval.ival = 0;
                          }
break;
case 46:
//#line 304 "gramatica.y"
{
                yyval.sval = val_peek(2).sval + "." + val_peek(0).sval;
            }
break;
case 47:
//#line 309 "gramatica.y"
{
                yyval.sval = val_peek(0).sval;
            }
break;
case 48:
//#line 315 "gramatica.y"
{
                String op2 = g.desapilarOperando();
                String op1 = g.desapilarOperando();
                String tipo = g.chequearTipos("+", g.getTipo(op1), g.getTipo(op2), al.getContadorFila()+1);
                String terceto = g.addTerceto("+", op1, op2);
                g.getTerceto(Integer.parseInt(terceto.substring(1, terceto.length()-1))).setTipo(tipo);
                g.apilarOperando(terceto);
            }
break;
case 49:
//#line 325 "gramatica.y"
{
                String op2 = g.desapilarOperando();
                String op1 = g.desapilarOperando();
                String tipo = g.chequearTipos("-", g.getTipo(op1), g.getTipo(op2), al.getContadorFila()+1);
                String terceto = g.addTerceto("-", op1, op2);
                g.getTerceto(Integer.parseInt(terceto.substring(1, terceto.length()-1))).setTipo(tipo);
                g.apilarOperando(terceto);
            }
break;
case 50:
//#line 334 "gramatica.y"
{ }
break;
case 51:
//#line 338 "gramatica.y"
{
                String op2 = g.desapilarOperando();
                String op1 = g.desapilarOperando();
                String tipo = g.chequearTipos("*", g.getTipo(op1), g.getTipo(op2), al.getContadorFila()+1);
                String terceto = g.addTerceto("*", op1, op2);
                g.getTerceto(Integer.parseInt(terceto.substring(1, terceto.length()-1))).setTipo(tipo);
                g.apilarOperando(terceto);
            }
break;
case 52:
//#line 347 "gramatica.y"
{
                String op2 = g.desapilarOperando();
                String op1 = g.desapilarOperando();
                String tipo = g.chequearTipos("/", g.getTipo(op1), g.getTipo(op2), al.getContadorFila()+1);
                String terceto = g.addTerceto("/", op1, op2);
                g.getTerceto(Integer.parseInt(terceto.substring(1, terceto.length()-1))).setTipo(tipo);
                g.apilarOperando(terceto);
            }
break;
case 53:
//#line 355 "gramatica.y"
{ }
break;
case 54:
//#line 359 "gramatica.y"
{
           yyval.ival = 0;
       }
break;
case 55:
//#line 363 "gramatica.y"
{
           yyval.ival = 1;
           yyval.sval = val_peek(0).sval;
           g.apilarOperando(val_peek(0).sval);
       }
break;
case 56:
//#line 371 "gramatica.y"
{
                      String varNombre = val_peek(0).sval;
                      String tipoVar = g.getTipo(varNombre);
                      if (tipoVar.equals("indefinido")) {
                          if (varNombre.contains(".")) {
                              String[] parts = varNombre.split("\\.", 2);
                              al.agregarErrorSemantico("Linea " + (al.getContadorFila()+1) + ": Error Semantico: Variable '" + parts[1] + "' no existe en el ambito '" + parts[0] + "' o el ambito no es visible (Tema 23).");
                          } else {
                              al.agregarErrorSemantico("Linea " + (al.getContadorFila()+1) + ": Error Semantico: Variable '" + varNombre + "' no fue declarada (Regla de alcance).");
                          }
                      }
                      g.apilarOperando(varNombre);
                  }
break;
case 57:
//#line 386 "gramatica.y"
{ g.apilarOperando(val_peek(0).sval); }
break;
case 58:
//#line 389 "gramatica.y"
{ }
break;
case 59:
//#line 393 "gramatica.y"
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
break;
case 60:
//#line 406 "gramatica.y"
{ g.clearParametrosReales(); }
break;
case 61:
//#line 410 "gramatica.y"
{
                       String funcName = val_peek(4).sval;
                       int linea = al.getContadorFila() + 1;
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
                   }
break;
case 62:
//#line 488 "gramatica.y"
{ yyval.ival = val_peek(2).ival + 1; }
break;
case 63:
//#line 491 "gramatica.y"
{ yyval.ival = 1; }
break;
case 64:
//#line 495 "gramatica.y"
{
                   g.apilarParametroReal(new ParametroRealInfo(val_peek(2).sval, val_peek(0).sval));
               }
break;
case 65:
//#line 500 "gramatica.y"
{
                   al.agregarErrorSemantico("Linea " + (al.getContadorFila()+1) + ": Error Semantico: Se requiere asignacion explicita de parametro (-> ID).");
               }
break;
case 66:
//#line 506 "gramatica.y"
{ yyval.sval = g.desapilarOperando(); }
break;
case 67:
//#line 509 "gramatica.y"
{
                     yyval.sval = val_peek(0).sval;
                 }
break;
case 68:
//#line 514 "gramatica.y"
{
                         pilaSaltosLambda.push(g.addTerceto("BI", "_", "_"));
                         int inicioLambda = g.getProximoTerceto();
                         yyval.sval = "L" + String.valueOf(inicioLambda);
                         g.abrirAmbito("lambda_" + inicioLambda);
                         al.agregarLexemaTS(val_peek(2).sval);
                         al.agregarAtributoLexema(val_peek(2).sval, "Uso", "parametro_lambda");
                         al.agregarAtributoLexema(val_peek(2).sval, "Tipo", val_peek(3).sval);
                         g.addTerceto("DEF_PARAM", val_peek(2).sval, "_");
                 }
break;
case 69:
//#line 523 "gramatica.y"
{
                         g.addTerceto("RET_LAMBDA", "_", "_");
                         int tercetoFin = g.getProximoTerceto();
                         String saltoIncondicional = pilaSaltosLambda.pop();
                         g.modificarSaltoTerceto(Integer.parseInt(saltoIncondicional.substring(1, saltoIncondicional.length()-1)), String.valueOf(tercetoFin));
                         g.cerrarAmbito();
                 }
break;
case 73:
//#line 540 "gramatica.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 74:
//#line 543 "gramatica.y"
{
                String lexemaPositivo = val_peek(0).sval;
                String lexemaNegativo = "-" + lexemaPositivo;
                if (al.getAtributo(lexemaPositivo, "Tipo") != null) {
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
                yyval.sval = lexemaNegativo;
            }
break;
case 75:
//#line 568 "gramatica.y"
{
                    Object lineaObj = al.getAtributo("if", "Linea");
                    String linea = (lineaObj != null) ?
                    lineaObj.toString() : "?";
                    salida.add("Linea " + linea + ": Sentencia IF reconocida.");
                }
break;
case 76:
//#line 576 "gramatica.y"
{
                    Object lineaObj = al.getAtributo("if", "Linea");
                    String linea = (lineaObj != null) ? lineaObj.toString() : "?";
                    salida.add("Linea " + linea + ": Sentencia IF-ELSE reconocida.");
               }
break;
case 77:
//#line 583 "gramatica.y"
{ g.apilarControl(g.getProximoTerceto()); }
break;
case 78:
//#line 584 "gramatica.y"
{
                        Object lineaObj = al.getAtributo("do", "Linea");
                        salida.add("Linea " + (al.getContadorFila()+1) + ": Sentencia DO-WHILE reconocida.");
                        String refCondicion = g.desapilarOperando();
                        int inicioBucle = g.desapilarControl();
                        if (refCondicion.equals("ERROR_CONDICION")) {
                             al.agregarErrorSemantico("Linea " + (al.getContadorFila()+1) + ": Error Semantico: No se genero el salto del DO-WHILE debido a una condicion invalida.");
                        } else {
                            String tercetoSalto = g.addTerceto("BT", refCondicion, String.valueOf(inicioBucle));
                        }
                    }
break;
case 79:
//#line 598 "gramatica.y"
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
                    g.apilarOperando("ERROR_CONDICION");
                }
          }
break;
case 80:
//#line 613 "gramatica.y"
{ g.apilarOperando(">="); }
break;
case 81:
//#line 615 "gramatica.y"
{ g.apilarOperando("<="); }
break;
case 82:
//#line 617 "gramatica.y"
{ g.apilarOperando("=!"); }
break;
case 83:
//#line 619 "gramatica.y"
{ g.apilarOperando("=="); }
break;
case 84:
//#line 621 "gramatica.y"
{ g.apilarOperando(">"); }
break;
case 85:
//#line 623 "gramatica.y"
{ g.apilarOperando("<"); }
break;
case 86:
//#line 626 "gramatica.y"
{ g.abrirAmbito("bloque_" + g.getProximoTerceto()); }
break;
case 87:
//#line 626 "gramatica.y"
{ g.cerrarAmbito(); }
break;
case 89:
//#line 632 "gramatica.y"
{
                    salida.add("Linea " + (al.getContadorFila()+1) + ": PRINT con cadena multilinea.");
                    g.addTerceto("PRINT", val_peek(1).sval);
                }
break;
case 90:
//#line 638 "gramatica.y"
{
                    salida.add("Linea " + (al.getContadorFila()+1) + ": PRINT con expresion.");
                    g.addTerceto("PRINT", g.desapilarOperando());
                }
break;
case 91:
//#line 645 "gramatica.y"
{
                salida.add("Linea " + (al.getContadorFila()+1) + ": Sentencia RETURN.");
                ArrayList<?> rawList = (ArrayList<?>) val_peek(2).obj;
                ArrayList<String> expresiones = new ArrayList<String>();
                for (Object o : rawList) {
                    expresiones.add((String) o);
                }
                for (String exprTerceto : expresiones) {
                    g.addTerceto("RETURN", exprTerceto);
                }
            }
break;
case 92:
//#line 659 "gramatica.y"
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
case 93:
//#line 670 "gramatica.y"
{
                  ArrayList<String> lista = new ArrayList<String>();
                  lista.add(g.desapilarOperando());
                  yyval.obj = lista;
              }
break;
//#line 1392 "Parser.java"
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
