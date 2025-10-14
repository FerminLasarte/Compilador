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
public final static short IGUAL_IGUAL=281;
public final static short IFX=282;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    1,    1,    2,    2,    2,    3,
    3,    3,    5,    7,    8,    8,    8,    9,    9,    6,
    6,   13,   13,   12,   12,   14,   14,   15,   15,    4,
    4,    4,    4,    4,    4,   16,   17,   22,   23,   23,
   10,   10,   11,   11,   11,   25,   25,   25,   24,   24,
   24,   24,   28,   27,   29,   29,   30,   31,   31,   32,
   33,   33,   34,   34,   26,   26,   18,   18,   19,   35,
   37,   37,   37,   37,   37,   37,   36,   36,   36,   20,
   20,   21,   38,   38,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    2,    1,    1,    1,    2,    2,
    1,    2,    2,    4,    1,    1,    1,    3,    1,    8,
    8,    3,    3,    3,    1,    3,    2,    2,    2,    2,
    2,    1,    1,    2,    1,    3,    3,    1,    3,    1,
    3,    1,    3,    3,    1,    3,    3,    1,    1,    1,
    1,    1,    4,    4,    3,    1,    3,    1,    1,    7,
    1,    0,    2,    1,    1,    2,    7,    9,    7,    3,
    1,    1,    1,    1,    1,    1,    3,    1,    3,    4,
    4,    5,    3,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,   16,    0,    0,   15,
    0,    0,   17,    0,    0,    6,    7,    8,    0,   11,
    0,    0,    0,    0,    0,    0,    0,   32,   33,    0,
   35,    0,    9,    0,    0,    0,    0,    0,    0,   78,
    0,    0,    3,    5,   10,   12,    0,    0,    0,   19,
    0,    0,    0,    0,    0,   30,   31,   34,    2,   41,
    0,   65,    0,    0,   49,    0,   48,    0,   50,   51,
   52,    0,    0,    0,    0,    0,    0,    0,   64,    0,
    0,    1,    0,   22,   37,    0,   40,   18,    0,    0,
   23,    0,    0,   66,   71,   72,   73,   74,    0,    0,
   75,   76,    0,    0,    0,    0,    0,    0,   80,   81,
    0,   79,   77,   63,    0,    0,    0,    0,   25,    0,
    0,    0,    0,    0,    0,   56,    0,   59,    0,    0,
    0,    0,   46,   47,    0,    0,   82,    0,   28,   29,
   27,    0,    0,    0,   39,    0,    0,    0,   54,    0,
   53,    0,    0,    0,   24,    0,   26,    0,    0,   55,
   57,    0,   67,   69,    0,    0,    0,    0,   20,   21,
    0,   68,    0,    0,   60,
};
final static short yydgoto[] = {                          3,
   15,   16,   17,   18,   19,   20,   21,   22,   23,   24,
   66,  118,   25,  119,  120,   26,   27,   28,   29,   30,
   31,   85,   86,   67,   68,   69,   70,   71,  125,  126,
  127,  128,  173,   80,   72,   41,  103,   74,
};
final static short yysindex[] = {                       -85,
 -106, -125,    0,  -15,    8,   31,    0,   45,   55,    0,
 -184,  -83,    0, -125,  -62,    0,    0,    0,   30,    0,
   76,  -33,  -19, -139,  -29,   86,   95,    0,    0,  107,
    0,  -48,    0,  -89,    7,    7,    5, -107, -189,    0,
  -98,  -34,    0,    0,    0,    0,   42, -167,  127,    0,
    7, -184,    7,  137, -167,    0,    0,    0,    0,    0,
   46,    0,  139,  -76,    0,  -23,    0,   15,    0,    0,
    0,  142,   21,   74,  144,   60,    7,   61,    0,   48,
  147,    0, -182,    0,    0,  146,    0,    0,   21, -182,
    0,   -4,    7,    0,    0,    0,    0,    0,    7,    7,
    0,    0,    7,    7,    7,  -83,    7,  129,    0,    0,
   21,    0,    0,    0,    7, -109,  -61,   75,    0, -167,
    7,   79, -167,   21,   80,    0,  -79,    0,   63,   15,
   15,   21,    0,    0, -133,   21,    0,  159,    0,    0,
    0, -182,   83,  -47,    0,   89,  -43,   -4,    0,  -37,
    0,  -83,  167,  175,    0, -125,    0, -125,  210,    0,
    0,   -3,    0,    0,   22,   37,  138,  201,    0,    0,
 -150,    0,  149, -150,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    1,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    2,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  276,    0,    0,    0,    0,   17,    0,  231,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -41,    0,    0,    0,    0,    0,    0,  -36,    0,    0,
    0,    0,   84,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  233,    0,    0,  238,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  240,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   36,    0,    0,    0,    0,    0,  -31,
  -11,  275,    0,    0,    0,  102,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  189,    0,    0,  192,    0,
};
final static short yygindex[] = {                         0,
   33,   27,    0,    4,    0,    0,    0,   10,  296,  273,
   19,  229,    0,  178,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -24,   49,    0,    0,    0,    0,  173,
    0,    0,    0,  151,  208,  -53,    0,    0,
};
final static int YYTABLESIZE=421;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         42,
   42,   42,   42,   42,   45,   42,   45,   45,   45,   43,
   48,   43,   43,   43,   55,   40,   14,   42,   42,   99,
   42,  100,   45,   45,   52,   45,   87,   43,   43,   44,
   43,   44,   44,   44,   32,  123,  102,    2,  101,   39,
   64,   44,   79,   33,   42,   19,   42,   44,   44,   64,
   44,   64,  135,   34,   73,   76,  104,   84,   44,   42,
   42,  105,   43,   99,   91,  100,   78,    5,   44,    6,
   35,   89,    5,    8,    9,   42,   59,   12,    7,  133,
  134,   83,   10,  114,   36,   92,   13,   34,   45,  116,
   82,   34,  117,    7,   37,  111,  145,   10,  162,  117,
  110,   13,   99,  151,  100,   99,    5,  100,    6,   40,
  124,  129,    8,    9,  108,  143,   12,  107,  142,  146,
  149,  132,  142,  148,   84,  136,  152,   84,  153,  144,
    4,    5,  147,    6,   46,    7,   53,    8,    9,   10,
   11,   12,   83,   13,   56,   83,  169,  130,  131,    4,
    5,  117,    6,   57,    7,   40,    8,    9,   10,   11,
   12,  170,   13,  139,  140,   58,  124,   60,   77,   81,
   52,    1,  113,    5,   79,    6,   90,  114,   93,    8,
    9,   94,  106,   12,  109,  112,  115,  137,  165,  121,
  166,   44,   44,    4,    5,  141,    6,  150,    7,  154,
    8,    9,   10,   11,   12,  156,   13,    4,    5,  157,
    6,  158,    7,  159,    8,    9,   10,   11,   12,  161,
   13,    4,    5,   47,    6,  163,    7,   54,    8,    9,
   10,   11,   12,  164,   13,   42,   42,   42,   42,   42,
   45,   45,   45,   45,   45,   43,   43,   43,   43,   43,
  167,   51,   61,   62,   95,   96,   97,   98,  168,  172,
  171,   61,   62,   61,   62,   44,   44,   44,   44,   44,
   63,   42,   19,  175,   75,    4,   42,    4,    5,   63,
    6,   63,    7,   38,    8,    9,   10,   11,   12,   13,
   13,   38,    4,    5,   50,    6,   36,    7,   14,    8,
    9,   10,   11,   12,    5,   13,    6,   65,   65,   65,
    8,    9,   58,   62,   12,   70,   61,   49,  122,  155,
  160,  174,  138,   65,   88,   65,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   65,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   65,   65,    0,    0,    0,    0,
    0,   65,   65,    0,    0,   65,   65,   65,    0,   65,
    0,    0,    0,    0,    0,    0,    0,   65,    0,    0,
    0,    0,    0,   65,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   65,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   41,   47,   43,   44,   45,   41,
   44,   43,   44,   45,   44,   12,  123,   59,   60,   43,
   62,   45,   59,   60,   44,   62,   51,   59,   60,   41,
   62,   43,   44,   45,    2,   40,   60,  123,   62,  123,
   45,   15,   39,   59,   44,   44,   14,   59,   60,   45,
   62,   45,  106,   46,   36,   37,   42,   48,   32,   59,
   44,   47,  125,   43,   55,   45,  256,  257,   42,  259,
   40,   53,  257,  263,  264,   59,  125,  267,  261,  104,
  105,   40,  265,   80,   40,   40,  269,   46,   59,  272,
  125,   46,   83,  261,   40,   77,  121,  265,  152,   90,
   41,  269,   43,   41,   45,   43,  257,   45,  259,  106,
   92,   93,  263,  264,   41,   41,  267,   44,   44,   41,
   41,  103,   44,   44,   41,  107,  260,   44,  262,  120,
  256,  257,  123,  259,   59,  261,  276,  263,  264,  265,
  266,  267,   41,  269,   59,   44,  125,   99,  100,  256,
  257,  142,  259,   59,  261,  152,  263,  264,  265,  266,
  267,  125,  269,  273,  274,   59,  148,  257,  276,  268,
   44,  257,  125,  257,  171,  259,   40,  174,   40,  263,
  264,  258,   41,  267,   41,  125,   40,   59,  156,   44,
  158,  165,  166,  256,  257,  257,  259,  277,  261,   41,
  263,  264,  265,  266,  267,  123,  269,  256,  257,  257,
  259,  123,  261,  257,  263,  264,  265,  266,  267,  257,
  269,  256,  257,  257,  259,   59,  261,  257,  263,  264,
  265,  266,  267,   59,  269,  277,  278,  279,  280,  281,
  277,  278,  279,  280,  281,  277,  278,  279,  280,  281,
   41,  271,  257,  258,  278,  279,  280,  281,  262,   59,
  123,  257,  258,  257,  258,  277,  278,  279,  280,  281,
  275,  271,  271,  125,  270,    0,  276,  256,  257,  275,
  259,  275,  261,   11,  263,  264,  265,  266,  267,   59,
  269,   59,  256,  257,   22,  259,   59,  261,   59,  263,
  264,  265,  266,  267,  257,  269,  259,   35,   36,   37,
  263,  264,  277,  125,  267,   41,  125,   22,   90,  142,
  148,  171,  115,   51,   52,   53,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   77,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   92,   93,   -1,   -1,   -1,   -1,
   -1,   99,  100,   -1,   -1,  103,  104,  105,   -1,  107,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  115,   -1,   -1,
   -1,   -1,   -1,  121,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  148,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=282;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
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
"MENOR_IGUAL","DISTINTO","IGUAL_IGUAL","IFX",
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
"declaracion_var : VAR variable ASIG expresion",
"tipo : UINT",
"tipo : FLOAT",
"tipo : LAMBDA",
"lista_variables : lista_variables ',' variable",
"lista_variables : variable",
"funcion : tipo ID '(' lista_parametros_formales ')' '{' sentencias '}'",
"funcion : lista_tipos_retorno_multiple ID '(' lista_parametros_formales ')' '{' sentencias '}'",
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
"lado_derecho_multiple : lista_elementos_restringidos",
"lista_elementos_restringidos : lista_elementos_restringidos ',' factor",
"lista_elementos_restringidos : factor",
"variable : ID '.' ID",
"variable : ID",
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
"lambda_expresion : '(' tipo ID ')' '{' cuerpo_lambda '}'",
"cuerpo_lambda : sentencias_ejecutables_lista",
"cuerpo_lambda :",
"sentencias_ejecutables_lista : sentencias_ejecutables_lista sentencia_ejecutable",
"sentencias_ejecutables_lista : sentencia_ejecutable",
"constante : CTE",
"constante : '-' CTE",
"condicional_if : IF '(' condicion ')' bloque_ejecutable ENDIF ';'",
"condicional_if : IF '(' condicion ')' bloque_ejecutable ELSE bloque_ejecutable ENDIF ';'",
"condicional_do_while : DO bloque_ejecutable WHILE '(' condicion ')' ';'",
"condicion : expresion simbolo_comparacion expresion",
"simbolo_comparacion : MAYOR_IGUAL",
"simbolo_comparacion : MENOR_IGUAL",
"simbolo_comparacion : DISTINTO",
"simbolo_comparacion : IGUAL_IGUAL",
"simbolo_comparacion : '>'",
"simbolo_comparacion : '<'",
"bloque_ejecutable : '{' sentencias_ejecutables_lista '}'",
"bloque_ejecutable : sentencia_ejecutable",
"bloque_ejecutable : '{' error '}'",
"salida_pantalla : PRINT '(' CADENA_MULTILINEA ')'",
"salida_pantalla : PRINT '(' expresion ')'",
"retorno_funcion : RETURN '(' lista_expresiones ')' ';'",
"lista_expresiones : lista_expresiones ',' expresion",
"lista_expresiones : expresion",
};

//#line 228 "gramatica.y"

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
//#line 483 "Parser.java"
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
//#line 59 "gramatica.y"
{ yyval.sval = "lambda"; }
break;
case 18:
//#line 63 "gramatica.y"
{
                    listaVariables.add(val_peek(0).sval);
                }
break;
case 19:
//#line 67 "gramatica.y"
{
                    listaVariables.clear();
                    listaVariables.add(val_peek(0).sval);
                }
break;
case 20:
//#line 74 "gramatica.y"
{
            salida.add("Linea " + (al.getContadorFila()+1) + ": Declaracion de Funcion '" + val_peek(6).sval + "' con retorno simple.");
        }
break;
case 21:
//#line 78 "gramatica.y"
{
            salida.add("Linea " + (al.getContadorFila()+1) + ": Declaracion de Funcion '" + val_peek(6).sval + "' con retorno multiple.");
        }
break;
case 36:
//#line 108 "gramatica.y"
{
               salida.add("Linea " + (al.getContadorFila()+1) + ": Asignacion simple (:=).");
           }
break;
case 37:
//#line 114 "gramatica.y"
{
                        salida.add("Linea " + (al.getContadorFila()+1) + ": Asignacion multiple (=).");
                    }
break;
case 41:
//#line 126 "gramatica.y"
{ yyval.sval = val_peek(2).sval + "." + val_peek(0).sval; }
break;
case 42:
//#line 127 "gramatica.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 53:
//#line 147 "gramatica.y"
{
                    salida.add("Linea " + (al.getContadorFila()+1) + ": Conversion explicita (toui).");
                }
break;
case 69:
//#line 187 "gramatica.y"
{
                         salida.add("Linea " + (al.getContadorFila()+1) + ": Sentencia DO-WHILE.");
                     }
break;
case 80:
//#line 209 "gramatica.y"
{
                    salida.add("Linea " + (al.getContadorFila()+1) + ": PRINT con cadena multilinea.");
                }
break;
case 81:
//#line 213 "gramatica.y"
{
                    salida.add("Linea " + (al.getContadorFila()+1) + ": PRINT con expresion.");
                }
break;
case 82:
//#line 219 "gramatica.y"
{
                    salida.add("Linea " + (al.getContadorFila()+1) + ": Sentencia RETURN.");
                }
break;
//#line 755 "Parser.java"
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
