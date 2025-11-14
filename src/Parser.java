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
    2,    3,    0,    4,    5,    0,    0,    0,    1,    1,
    6,    6,    6,    7,    7,   10,   13,   13,   13,   14,
   14,   16,   17,    9,   19,   20,    9,   18,   18,   15,
   15,   21,   21,   22,   22,    8,    8,    8,    8,    8,
    8,   23,   24,   31,   29,   29,   11,   11,   12,   12,
   12,   32,   32,   32,   30,   30,   33,   33,   33,   36,
   37,   34,   38,   38,   39,   39,   40,   40,   43,   41,
   42,   44,   44,   35,   35,   25,   25,   47,   26,   26,
   45,   48,   48,   48,   48,   48,   48,   49,   46,   46,
   46,   27,   27,   28,   50,   50,
};
final static short yylen[] = {                            2,
    0,    0,    6,    0,    0,    5,    3,    3,    2,    1,
    1,    1,    2,    1,    2,    4,    1,    1,    1,    3,
    1,    0,    0,   10,    0,    0,   10,    3,    3,    3,
    1,    3,    2,    2,    2,    2,    2,    1,    1,    2,
    1,    3,    3,    0,    2,    3,    3,    1,    3,    3,
    1,    3,    3,    1,    1,    1,    1,    1,    1,    4,
    0,    5,    3,    1,    3,    1,    1,    1,    0,    8,
    1,    2,    1,    1,    2,    7,    9,    0,    8,    6,
    3,    1,    1,    1,    1,    1,    1,    0,    4,    1,
    3,    4,    4,    5,    3,    1,
};
final static short yydefred[] = {                         0,
    0,    4,    0,    0,    0,    0,   18,    0,    0,   17,
    0,    0,   19,    0,    0,   10,   11,   12,   14,    0,
    0,    0,    0,    0,    0,    0,   38,   39,    0,   41,
    0,   13,    0,    0,    0,    0,    0,    0,   90,    0,
    0,    0,    0,    7,    9,   15,    0,    0,    0,   44,
    0,    0,    0,   36,   37,   40,    0,   47,    0,   74,
    0,    0,   57,    0,   54,    0,   55,   56,   58,   59,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   28,    0,    0,   20,    0,   29,    5,
    0,    0,   75,   82,   83,   84,   85,    0,    0,   86,
   87,    0,    0,    0,    0,    0,    0,   92,   93,    0,
   91,   73,    0,    0,    0,    2,    0,    0,    0,   31,
    0,    0,   45,    0,    6,    0,    0,    0,    0,    0,
   52,   53,    0,    0,   94,   89,   72,    0,    0,    3,
   34,   35,   33,    0,    0,    0,   46,    0,    0,    0,
    0,   64,    0,   68,   60,    0,    0,   80,    0,   30,
   22,   32,   25,    0,    0,   62,    0,    0,   76,    0,
    0,    0,    0,   63,   65,    0,   79,    0,    0,    0,
   77,   23,   26,   69,   24,   27,    0,    0,    0,   70,
};
final static short yydgoto[] = {                          3,
   15,   43,  140,   31,  125,   16,   17,   18,   19,   20,
   21,   64,   22,   23,  119,  171,  185,   24,  172,  186,
  120,  121,   25,   26,   27,   28,   29,   30,   85,   65,
   86,   66,   67,   68,   69,   70,   91,  151,  152,  153,
  154,  188,  187,  113,   71,   40,   41,  102,   78,   73,
};
final static short yysindex[] = {                      -105,
  -79,    0,    0,  -12, -228,   35,    0,   48,   55,    0,
 -197,   20,    0,   88,  -54,    0,    0,    0,    0,   40,
 -171,  -32,  -18,  -28,   42,   61,    0,    0,   62,    0,
   88,    0, -143,   16,   16,    6, -144, -118,    0, -124,
   20,   88,   88,    0,    0,    0,   16,  108, -150,    0,
 -197,  109, -150,    0,    0,    0,  -33,    0, -228,    0,
  116,  -89,    0,  -22,    0,   15,    0,    0,    0,    0,
  133,   44,   36,  138,   59,   16,   56, -151,  143,  -77,
   33,   44, -175,    0,  149,   16,    0, -175,    0,    0,
  155,   16,    0,    0,    0,    0,    0,   16,   16,    0,
    0,   16,   16,   16,   20,   16,  147,    0,    0,   44,
    0,    0,   79,   16,  157,    0, -205,  -49,   95,    0,
 -150,   16,    0,  121,    0,    3,   81,   15,   15,   44,
    0,    0, -186,   44,    0,    0,    0,  175,   16,    0,
    0,    0,    0, -175,   94,  -39,    0,   96, -150,   44,
  122,    0,  -57,    0,    0,   20,  162,    0,  181,    0,
    0,    0,    0,   -3,    3,    0,    8,  -27,    0,  168,
   88,   88,  211,    0,    0,  203,    0,   47,   64,  152,
    0,    0,    0,    0,    0,    0, -151,  141, -151,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    9,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -16,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -117,    0,    0,
    0,  282,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -40,    0,
    0,    0,    0,    0,    0,  -35,    0,    0,    0,    0,
    0,  123,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  227,    0,    0,  229,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  234,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -30,  -10,  254,
    0,    0,    0,  127,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   -5,
    0,    0,  132,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  176,    0,
};
final static short yygindex[] = {                         0,
   27,    0,    0,    0,    0,   22,    0,    5,    0,    0,
   31,   49,   10,    0,  217,    0,    0,    0,    0,    0,
  163,    0,    0,    0,    0,    0,    0,    0,    0,  -31,
    0,   29,    0,    0,    0,    0,    0,    0,  144,    0,
    0,    0,    0,  128,  -58,    4,    0,    0,    0,    0,
};
final static int YYTABLESIZE=357;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         61,
   48,   48,   48,   48,   48,   51,   48,   51,   51,   51,
   49,   49,   49,   49,   49,   53,   39,    2,   48,   48,
   98,   48,   99,   51,   51,   51,   51,   21,   49,   49,
   50,   49,   50,   50,   50,   67,   45,  101,   67,  100,
   42,   37,  149,   14,   80,   39,   32,   62,   50,   50,
   62,   50,   48,   33,  123,  138,  103,   57,   84,    5,
   62,  104,   89,   45,   63,   63,   63,  141,  142,   81,
   44,  131,  132,  156,   34,  157,  107,   63,   45,  106,
  159,   87,  112,   72,   75,    7,   98,   35,   99,   10,
  147,   90,  118,   13,   36,   82,  117,  118,   46,  109,
   54,   98,   45,   99,   47,    5,   63,    6,  133,   39,
    7,    8,    9,   58,   10,   12,   63,  137,   13,   55,
   56,  155,   63,   98,  110,   99,  128,  129,   63,   63,
  146,   76,   63,   63,   63,  145,   63,   77,  144,   88,
  127,   88,   38,   79,   63,   88,   88,   83,   88,   88,
  130,    1,   63,  118,  134,   92,   63,  116,  164,  168,
   39,  148,  166,   96,  144,  165,   96,   95,   93,   63,
   95,  182,   66,  105,  150,   66,    4,    5,  108,    6,
  111,    7,  114,    8,    9,   10,   11,   12,  183,   13,
  115,  112,  122,  137,  126,   63,  139,  178,  179,   45,
   45,    4,    5,  136,    6,  135,    7,  143,    8,    9,
   10,   11,   12,  150,   13,  158,  161,  162,  163,  167,
  169,  170,    4,    5,   48,    6,  177,    7,   52,    8,
    9,   10,   11,   12,  176,   13,   48,   48,   48,   48,
   48,   51,   51,   51,   51,   51,   49,   49,   49,   49,
   49,  180,   50,  173,   21,   94,   95,   96,   97,   59,
   60,  181,   59,   60,  175,  190,   50,   50,   50,   50,
   50,   67,   59,   60,  184,   74,    5,   61,    6,   48,
   61,    8,    8,    9,   48,   42,   12,   43,    4,    5,
   61,    6,   16,    7,   81,    8,    9,   10,   11,   12,
   71,   13,    4,    5,  124,    6,  160,    7,  174,    8,
    9,   10,   11,   12,  189,   13,    0,    0,    0,    4,
    5,    0,    6,    0,    7,    0,    8,    9,   10,   11,
   12,    0,   13,    0,    0,    5,    0,    6,    0,    0,
    0,    8,    9,    4,    5,   12,    6,    0,    7,    0,
    8,    9,   10,   11,   12,    0,   13,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   41,   42,   43,   44,   45,   41,   47,   43,   44,   45,
   41,   44,   43,   44,   45,   44,   12,  123,   59,   60,
   43,   62,   45,   59,   60,   44,   62,   44,   59,   60,
   41,   62,   43,   44,   45,   41,   15,   60,   44,   62,
   14,   11,   40,  123,   41,   41,   59,   45,   59,   60,
   45,   62,   44,  282,   86,  114,   42,   31,   49,  257,
   45,   47,   53,   42,   34,   35,   36,  273,  274,   43,
  125,  103,  104,  260,   40,  262,   41,   47,   57,   44,
  139,   51,   78,   35,   36,  261,   43,   40,   45,  265,
  122,  125,   83,  269,   40,   47,  272,   88,   59,   41,
   59,   43,   81,   45,  276,  257,   76,  259,  105,  105,
  261,  263,  264,  257,  265,  267,   86,  113,  269,   59,
   59,   41,   92,   43,   76,   45,   98,   99,   98,   99,
  121,  276,  102,  103,  104,   41,  106,  256,   44,  257,
   92,  259,  123,  268,  114,  263,  264,   40,   40,  267,
  102,  257,  122,  144,  106,   40,  126,  125,  149,  156,
  156,   41,   41,   41,   44,   44,   44,   41,  258,  139,
   44,  125,   41,   41,  126,   44,  256,  257,   41,  259,
  125,  261,   40,  263,  264,  265,  266,  267,  125,  269,
  268,  187,   44,  189,   40,  165,   40,  171,  172,  178,
  179,  256,  257,  125,  259,   59,  261,  257,  263,  264,
  265,  266,  267,  165,  269,   41,  123,  257,  123,  277,
   59,   41,  256,  257,  257,  259,   59,  261,  257,  263,
  264,  265,  266,  267,  262,  269,  277,  278,  279,  280,
  281,  277,  278,  279,  280,  281,  277,  278,  279,  280,
  281,   41,  271,  257,  271,  278,  279,  280,  281,  257,
  258,   59,  257,  258,  257,  125,  277,  278,  279,  280,
  281,  277,  257,  258,  123,  270,  257,  275,  259,  271,
  275,    0,  263,  264,  276,   59,  267,   59,  256,  257,
  275,  259,   59,  261,   41,  263,  264,  265,  266,  267,
  125,  269,  256,  257,   88,  259,  144,  261,  165,  263,
  264,  265,  266,  267,  187,  269,   -1,   -1,   -1,  256,
  257,   -1,  259,   -1,  261,   -1,  263,  264,  265,  266,
  267,   -1,  269,   -1,   -1,  257,   -1,  259,   -1,   -1,
   -1,  263,  264,  256,  257,  267,  259,   -1,  261,   -1,
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
"programa : ID sentencias '}'",
"programa : ID '{' sentencias",
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
"condicional_do_while : DO bloque_ejecutable WHILE '(' condicion ')'",
"condicion : expresion simbolo_comparacion expresion",
"simbolo_comparacion : MAYOR_IGUAL",
"simbolo_comparacion : MENOR_IGUAL",
"simbolo_comparacion : DISTINTO",
"simbolo_comparacion : IGUAL_IGUAL",
"simbolo_comparacion : '>'",
"simbolo_comparacion : '<'",
"$$12 :",
"bloque_ejecutable : '{' $$12 sentencias_ejecutables_lista '}'",
"bloque_ejecutable : sentencia_ejecutable",
"bloque_ejecutable : '{' error '}'",
"salida_pantalla : PRINT '(' CADENA_MULTILINEA ')'",
"salida_pantalla : PRINT '(' expresion ')'",
"retorno_funcion : RETURN '(' lista_expresiones ')' ';'",
"lista_expresiones : lista_expresiones ',' expresion",
"lista_expresiones : expresion",
};

//#line 695 "gramatica.y"

static AnalizadorLexico al;
static Generador g;
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
//#line 530 "Parser.java"
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
{ g.abrirAmbito(val_peek(1).sval); }
break;
case 2:
//#line 24 "gramatica.y"
{ g.cerrarAmbito(); }
break;
case 3:
//#line 25 "gramatica.y"
{
          String nombrePrograma = val_peek(5).sval;
          Object lineaObj = al.getAtributo(nombrePrograma, "Linea");
          String linea = (lineaObj != null) ? lineaObj.toString() : "?";
          salida.add("Linea " + linea + ": Programa '" + nombrePrograma + "' reconocido.");
      }
break;
case 4:
//#line 32 "gramatica.y"
{ g.abrirAmbito("MAIN"); }
break;
case 5:
//#line 32 "gramatica.y"
{ g.cerrarAmbito(); }
break;
case 6:
//#line 33 "gramatica.y"
{
         erroresSintacticos.add("Linea " + (al.getContadorFila()+1) + ": Error sintactico: Falta el nombre del programa.");
     }
break;
case 7:
//#line 37 "gramatica.y"
{
         erroresSintacticos.add("Linea " + (al.getContadorFila()+1) + ": Error sintactico: Falta el delimitador '{' al inicio del programa.");
     }
break;
case 8:
//#line 41 "gramatica.y"
{
         erroresSintacticos.add("Linea " + (al.getContadorFila()+1) + ": Error sintactico: Falta el delimitador '}' al final del programa.");
     }
break;
case 16:
//#line 63 "gramatica.y"
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
case 17:
//#line 83 "gramatica.y"
{ yyval.sval = "uint"; }
break;
case 18:
//#line 84 "gramatica.y"
{ yyval.sval = "float"; }
break;
case 19:
//#line 85 "gramatica.y"
{ yyval.sval = "lambda"; }
break;
case 20:
//#line 89 "gramatica.y"
{
                    listaVariables.add(val_peek(0).sval);
                }
break;
case 21:
//#line 94 "gramatica.y"
{
                    listaVariables.clear();
                    listaVariables.add(val_peek(0).sval);
                }
break;
case 22:
//#line 100 "gramatica.y"
{
            String nombreFuncion = val_peek(4).sval;
            String tipoRetorno = val_peek(5).sval;
            ArrayList<ParametroInfo> parametros = g.getListaParametros();

            if (g.existeEnAmbitoActual(nombreFuncion)) {
                al.agregarErrorSemantico("Linea " + (al.getContadorFila()+1) + ": Error Semantico: Redeclaracion de funcion '" + nombreFuncion + "'.");
            } else {
                al.agregarLexemaTS(nombreFuncion);
                al.agregarAtributoLexema(nombreFuncion, "Uso", "funcion");
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
                     al.agregarAtributoLexema(p.nombre, "Tipo", p.tipo);
                     al.agregarAtributoLexema(p.nombre, "Pasaje", p.pasaje);
                }
            }
        }
break;
case 23:
//#line 126 "gramatica.y"
{ g.cerrarAmbito(); }
break;
case 24:
//#line 127 "gramatica.y"
{
            String nombreFuncion = val_peek(8).sval;
            Object lineaObj = al.getAtributo(nombreFuncion, "Linea");
            String linea = (lineaObj != null) ? lineaObj.toString() : "?";
            salida.add("Linea " + (linea) + ": Declaracion de Funcion '" + val_peek(8).sval + "' con retorno simple.");
        }
break;
case 25:
//#line 133 "gramatica.y"
{
            String nombreFuncion = val_peek(4).sval;
            ArrayList<String> tiposRetorno = (ArrayList<String>) val_peek(5).obj;
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
case 26:
//#line 160 "gramatica.y"
{ g.cerrarAmbito(); }
break;
case 27:
//#line 161 "gramatica.y"
{
            String nombreFuncion = val_peek(8).sval;
            Object lineaObj = al.getAtributo(nombreFuncion, "Linea");
            String linea = (lineaObj != null) ? lineaObj.toString() : "?";
            salida.add("Linea " + (linea) + ": Declaracion de Funcion '" + val_peek(8).sval + "' con retorno multiple.");
        }
break;
case 28:
//#line 170 "gramatica.y"
{
                                 ArrayList<String> lista = new ArrayList<String>();
                                 lista.add(val_peek(2).sval);
                                 lista.add(val_peek(0).sval);
                                 yyval.obj = lista;
                             }
break;
case 29:
//#line 178 "gramatica.y"
{
                                 ArrayList<String> lista = (ArrayList<String>) val_peek(2).obj;
                                 lista.add(val_peek(0).sval);
                                 yyval.obj = lista;
                             }
break;
case 32:
//#line 191 "gramatica.y"
{
                 g.apilarParametro(new ParametroInfo(val_peek(0).sval, val_peek(1).sval, val_peek(2).sval));
             }
break;
case 33:
//#line 196 "gramatica.y"
{
                 String pasajeDefault = "default_cv";
                 g.apilarParametro(new ParametroInfo(val_peek(0).sval, val_peek(1).sval, pasajeDefault));
             }
break;
case 34:
//#line 202 "gramatica.y"
{ yyval.sval = "cr_se"; }
break;
case 35:
//#line 204 "gramatica.y"
{ yyval.sval = "cr_le"; }
break;
case 42:
//#line 218 "gramatica.y"
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
case 43:
//#line 233 "gramatica.y"
{
                            String lineaActual = String.valueOf(al.getContadorFila() + 1);
                            int cantIzquierda = listaVariables.size();
                            int cantDerecha = contadorLadoDerecho;
                            Stack<String> derechos = g.getPilaLadoDerecho();
                            boolean esFuncion = (val_peek(0).ival == 1);

                            if (esFuncion) {
                                String funcTerceto = derechos.pop();
                                String funcName = g.getTerceto(Integer.parseInt(funcTerceto.substring(1, funcTerceto.length()-1))).getOperando1();
                                Object retMultiple = al.getAtributo(funcName, "RetornoMultiple");

                                if (retMultiple == null || !(Boolean)retMultiple) {
                                    al.agregarErrorSemantico("Linea " + lineaActual + ": Error Semantico: Asignacion multiple a funcion '" + funcName + "' que no tiene retorno multiple.");
                                } else {
                                    ArrayList<String> tiposRetorno = (ArrayList<String>) al.getAtributo(funcName, "TiposRetorno");
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

                            } else {
                                if (cantIzquierda != cantDerecha) {
                                    al.agregarErrorSemantico("Linea " + lineaActual + ": Error Semantico (Tema 19): La asignacion multiple debe tener el mismo numero de elementos a la izquierda (" + cantIzquierda + ") y a la derecha (" + cantDerecha + ").");
                                } else {
                                    for (int i = 0; i < cantIzquierda; i++) {
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
case 44:
//#line 297 "gramatica.y"
{ g.clearLadoDerecho(); }
break;
case 45:
//#line 298 "gramatica.y"
{
                              g.apilarLadoDerecho(g.desapilarOperando());
                              contadorLadoDerecho = 1;
                              yyval.ival = val_peek(0).ival;
                              yyval.sval = val_peek(0).sval;
                          }
break;
case 46:
//#line 306 "gramatica.y"
{
                              g.apilarLadoDerecho(g.desapilarOperando());
                              contadorLadoDerecho++;
                              yyval.ival = 0;
                          }
break;
case 47:
//#line 314 "gramatica.y"
{
                yyval.sval = val_peek(2).sval + "." + val_peek(0).sval;
                if (g.getTipo(yyval.sval).equals("indefinido")) {
                     al.agregarErrorSemantico("Linea " + (al.getContadorFila()+1) + ": Error Semantico: Variable '" + val_peek(0).sval + "' no existe en el ambito '" + val_peek(2).sval + "' o el ambito no es visible (Tema 23).");
                }
            }
break;
case 48:
//#line 322 "gramatica.y"
{
                yyval.sval = val_peek(0).sval;
                if (g.getTipo(yyval.sval).equals("indefinido")) {
                     al.agregarErrorSemantico("Linea " + (al.getContadorFila()+1) + ": Error Semantico: Variable '" + val_peek(0).sval + "' no fue declarada (Regla de alcance).");
                }
            }
break;
case 49:
//#line 331 "gramatica.y"
{
                String op2 = g.desapilarOperando();
                String op1 = g.desapilarOperando();
                String tipo = g.chequearTipos("+", g.getTipo(op1), g.getTipo(op2), al.getContadorFila()+1);
                String terceto = g.addTerceto("+", op1, op2);
                g.getTerceto(Integer.parseInt(terceto.substring(1, terceto.length()-1))).setTipo(tipo);
                g.apilarOperando(terceto);
            }
break;
case 50:
//#line 341 "gramatica.y"
{
                String op2 = g.desapilarOperando();
                String op1 = g.desapilarOperando();
                String tipo = g.chequearTipos("-", g.getTipo(op1), g.getTipo(op2), al.getContadorFila()+1);
                String terceto = g.addTerceto("-", op1, op2);
                g.getTerceto(Integer.parseInt(terceto.substring(1, terceto.length()-1))).setTipo(tipo);
                g.apilarOperando(terceto);
            }
break;
case 51:
//#line 350 "gramatica.y"
{ }
break;
case 52:
//#line 354 "gramatica.y"
{
                String op2 = g.desapilarOperando();
                String op1 = g.desapilarOperando();
                String tipo = g.chequearTipos("*", g.getTipo(op1), g.getTipo(op2), al.getContadorFila()+1);
                String terceto = g.addTerceto("*", op1, op2);
                g.getTerceto(Integer.parseInt(terceto.substring(1, terceto.length()-1))).setTipo(tipo);
                g.apilarOperando(terceto);
            }
break;
case 53:
//#line 363 "gramatica.y"
{
                String op2 = g.desapilarOperando();
                String op1 = g.desapilarOperando();
                String tipo = g.chequearTipos("/", g.getTipo(op1), g.getTipo(op2), al.getContadorFila()+1);
                String terceto = g.addTerceto("/", op1, op2);
                g.getTerceto(Integer.parseInt(terceto.substring(1, terceto.length()-1))).setTipo(tipo);
                g.apilarOperando(terceto);
            }
break;
case 54:
//#line 371 "gramatica.y"
{ }
break;
case 55:
//#line 375 "gramatica.y"
{
           yyval.ival = 0;
       }
break;
case 56:
//#line 379 "gramatica.y"
{
           yyval.ival = 1;
           yyval.sval = val_peek(0).sval;
           g.apilarOperando(val_peek(0).sval);
       }
break;
case 57:
//#line 387 "gramatica.y"
{ g.apilarOperando(val_peek(0).sval); }
break;
case 58:
//#line 390 "gramatica.y"
{ g.apilarOperando(val_peek(0).sval); }
break;
case 59:
//#line 393 "gramatica.y"
{ }
break;
case 60:
//#line 397 "gramatica.y"
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
case 61:
//#line 412 "gramatica.y"
{ g.clearParametrosReales(); }
break;
case 62:
//#line 416 "gramatica.y"
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
                           ArrayList<ParametroInfo> formales = (ArrayList<ParametroInfo>) al.getAtributo(funcName, "Parametros");

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

                                       if (tipoFormal.equals("lambda") && tipoReal.equals("lambda_expr")) {
                                       } else if (!tipoFormal.equals("lambda") && tipoReal.equals("lambda_expr")) {
                                           al.agregarErrorSemantico("Linea " + linea + ": Error Semantico (Tema 28): Se paso una expresion lambda al parametro '->" + real.nombreFormal + "' que no es de tipo 'lambda'.");
                                           errorEnParametros = true;
                                       } else if (tipoFormal.equals("lambda") && !tipoReal.equals("lambda_expr")) {
                                           al.agregarErrorSemantico("Linea " + linea + ": Error Semantico (Tema 28): El parametro '->" + real.nombreFormal + "' espera una expresion 'lambda'.");
                                           errorEnParametros = true;
                                       } else if (!tipoReal.equals("error_tipo") && !g.chequearAsignacion(tipoFormal, tipoReal, linea)) {
                                           al.agregarErrorSemantico("Linea " + linea + ": Error Semantico: Invocacion a '" + funcName + "': tipo incompatible para '->" + real.nombreFormal + "'. Esperado: " + tipoFormal + ", Obtenido: " + tipoReal + ".");
                                           errorEnParametros = true;
                                       } else if (tipoReal.equals("error_tipo")) {
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
case 63:
//#line 494 "gramatica.y"
{ yyval.ival = val_peek(2).ival + 1; }
break;
case 64:
//#line 497 "gramatica.y"
{ yyval.ival = 1; }
break;
case 65:
//#line 501 "gramatica.y"
{
                   g.apilarParametroReal(new ParametroRealInfo(val_peek(2).sval, val_peek(0).sval));
               }
break;
case 66:
//#line 506 "gramatica.y"
{
                   al.agregarErrorSemantico("Linea " + (al.getContadorFila()+1) + ": Error Semantico: Se requiere asignacion explicita de parametro (-> ID).");
               }
break;
case 67:
//#line 512 "gramatica.y"
{ yyval.sval = g.desapilarOperando(); }
break;
case 68:
//#line 515 "gramatica.y"
{
                     yyval.sval = val_peek(0).sval;
                 }
break;
case 69:
//#line 520 "gramatica.y"
{
                         String saltoIncondicional = g.addTerceto("BI", "_", "_");
                         int inicioLambda = g.getProximoTerceto();
                         yyval.sval = String.valueOf(inicioLambda);

                         g.abrirAmbito("lambda_" + inicioLambda);
                         al.agregarLexemaTS(val_peek(2).sval);
                         al.agregarAtributoLexema(val_peek(2).sval, "Uso", "parametro_lambda");
                         al.agregarAtributoLexema(val_peek(2).sval, "Tipo", val_peek(3).sval);

                         g.addTerceto("DEF_PARAM", val_peek(2).sval, "_");
                     }
break;
case 70:
//#line 531 "gramatica.y"
{
                         g.addTerceto("RET_LAMBDA", "_", "_");
                         int tercetoFin = g.getProximoTerceto();
                         g.modificarSaltoTerceto(Integer.parseInt(saltoIncondicional.substring(1, saltoIncondicional.length()-1)), String.valueOf(tercetoFin));

                         g.cerrarAmbito();
                     }
break;
case 74:
//#line 549 "gramatica.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 75:
//#line 552 "gramatica.y"
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
case 76:
//#line 579 "gramatica.y"
{
                    Object lineaObj = al.getAtributo("if", "Linea");
                    String linea = (lineaObj != null) ?
                    lineaObj.toString() : "?";
                    salida.add("Linea " + linea + ": Sentencia IF reconocida.");
                }
break;
case 77:
//#line 587 "gramatica.y"
{
                    Object lineaObj = al.getAtributo("if", "Linea");
                    String linea = (lineaObj != null) ? lineaObj.toString() : "?";
                    salida.add("Linea " + linea + ": Sentencia IF-ELSE reconocida.");
               }
break;
case 78:
//#line 594 "gramatica.y"
{ g.apilarControl(g.getProximoTerceto()); }
break;
case 79:
//#line 595 "gramatica.y"
{
                        Object lineaObj = al.getAtributo("do", "Linea");
                        String linea = (lineaObj != null) ? lineaObj.toString() : "?";
                        salida.add("Linea " + linea + ": Sentencia DO-WHILE reconocida.");

                        String refCondicion = g.desapilarOperando();
                        int inicioBucle = g.desapilarControl();

                        if (refCondicion.equals("ERROR_CONDICION")) {
                             al.agregarErrorSemantico("Linea " + linea + ": Error Semantico: No se genero el salto del DO-WHILE debido a una condicion invalida.");
                        } else {
                            String tercetoSalto = g.addTerceto("BT", refCondicion, String.valueOf(inicioBucle));
                        }
                    }
break;
case 80:
//#line 611 "gramatica.y"
{
                        Object lineaObj = al.getAtributo("do", "Linea");
                        String linea = (lineaObj != null) ? lineaObj.toString() : "?";
                        yyerror("Linea " + linea + ": Error Sintactico. Falta punto y coma ';' al final de la sentencia DO-WHILE.");
                        g.desapilarOperando();
                        g.desapilarControl();
                    }
break;
case 81:
//#line 621 "gramatica.y"
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
case 82:
//#line 637 "gramatica.y"
{ g.apilarOperando(">="); }
break;
case 83:
//#line 639 "gramatica.y"
{ g.apilarOperando("<="); }
break;
case 84:
//#line 641 "gramatica.y"
{ g.apilarOperando("=!"); }
break;
case 85:
//#line 643 "gramatica.y"
{ g.apilarOperando("=="); }
break;
case 86:
//#line 645 "gramatica.y"
{ g.apilarOperando(">"); }
break;
case 87:
//#line 647 "gramatica.y"
{ g.apilarOperando("<"); }
break;
case 88:
//#line 650 "gramatica.y"
{ g.abrirAmbito("bloque_" + g.getProximoTerceto()); }
break;
case 89:
//#line 650 "gramatica.y"
{ g.cerrarAmbito(); }
break;
case 92:
//#line 657 "gramatica.y"
{
                    salida.add("Linea " + (al.getContadorFila()+1) + ": PRINT con cadena multilinea.");
                    g.addTerceto("PRINT", val_peek(1).sval);
                }
break;
case 93:
//#line 663 "gramatica.y"
{
                    salida.add("Linea " + (al.getContadorFila()+1) + ": PRINT con expresion.");
                    g.addTerceto("PRINT", g.desapilarOperando());
                }
break;
case 94:
//#line 670 "gramatica.y"
{
                salida.add("Linea " + (al.getContadorFila()+1) + ": Sentencia RETURN.");
                ArrayList<String> expresiones = (ArrayList<String>) val_peek(2).obj;

                for (String exprTerceto : expresiones) {
                    g.addTerceto("RETURN", exprTerceto);
                }
            }
break;
case 95:
//#line 681 "gramatica.y"
{
                  ArrayList<String> lista = (ArrayList<String>) val_peek(2).obj;
                  lista.add(g.desapilarOperando());
                  yyval.obj = lista;
              }
break;
case 96:
//#line 688 "gramatica.y"
{
                  ArrayList<String> lista = new ArrayList<String>();
                  lista.add(g.desapilarOperando());
                  yyval.obj = lista;
              }
break;
//#line 1411 "Parser.java"
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
