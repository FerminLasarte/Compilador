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
//#line 28 "Parser.java"




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
    3,    6,    9,    9,    9,   10,   10,    5,    5,   12,
   12,   11,   11,   13,   13,   14,   14,    4,    4,    4,
    4,    4,    4,   15,   16,   21,   21,    7,    7,    8,
    8,    8,   23,   23,   23,   22,   22,   24,   24,   24,
   27,   25,   28,   28,   29,   30,   30,   31,   32,   32,
   33,   33,   26,   26,   17,   17,   18,   18,   34,   36,
   36,   36,   36,   36,   36,   35,   35,   35,   19,   19,
   20,   37,   37,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    2,    1,    1,    1,    2,    1,
    2,    4,    1,    1,    1,    3,    1,    8,    8,    3,
    3,    3,    1,    3,    2,    2,    2,    2,    2,    1,
    1,    2,    1,    3,    3,    1,    3,    3,    1,    3,
    3,    1,    3,    3,    1,    1,    1,    1,    1,    1,
    4,    4,    3,    1,    3,    1,    1,    7,    1,    0,
    2,    1,    1,    2,    7,    9,    7,    6,    3,    1,
    1,    1,    1,    1,    1,    3,    1,    3,    4,    4,
    5,    3,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,   14,    0,    0,   13,
    0,    0,   15,    0,    0,    6,    7,    8,   10,    0,
    0,    0,    0,    0,    0,    0,   30,   31,    0,   33,
    0,    9,    0,    0,    0,    0,    0,    0,   77,    0,
    0,    3,    5,   11,    0,    0,    0,    0,    0,    0,
    0,   28,   29,   32,    2,   38,    0,   63,    0,    0,
   48,    0,   45,    0,   46,   47,   49,   50,    0,    0,
    0,    0,    0,    0,    0,   62,    0,    0,    1,    0,
    0,   20,    0,   36,   16,    0,   21,    0,    0,   64,
   70,   71,   72,   73,    0,    0,   74,   75,    0,    0,
    0,    0,    0,    0,   79,   80,    0,   78,   76,   61,
    0,    0,    0,    0,   23,    0,    0,    0,    0,    0,
    0,   54,    0,   57,    0,    0,    0,    0,   43,   44,
    0,    0,   81,    0,   26,   27,   25,    0,    0,    0,
   37,    0,    0,    0,   52,    0,   51,    0,    0,    0,
   22,    0,   24,    0,    0,   53,   55,    0,   65,   67,
    0,    0,    0,    0,   18,   19,    0,   66,    0,    0,
   58,
};
final static short yydgoto[] = {                          3,
   15,   16,   17,   18,   19,   20,   21,   62,   22,   23,
  114,   24,  115,  116,   25,   26,   27,   28,   29,   30,
   83,   63,   64,   65,   66,   67,   68,  121,  122,  123,
  124,  169,   77,   69,   40,   99,   71,
};
final static short yysindex[] = {                       -78,
  -72, -128,    0,   -9,   18,   50,    0,   65,   78,    0,
 -127,  -47,    0, -128,  -98,    0,    0,    0,    0,   73,
 -142,  -31,  -15,  -27,   83,   88,    0,    0,   89,    0,
  -58,    0, -108,   25,   25,   23, -126, -185,    0, -117,
   45,    0,    0,    0,   25,  113, -174,   25, -127,  115,
 -174,    0,    0,    0,    0,    0,   35,    0,  117,  -96,
    0,   -2,    0,   19,    0,    0,    0,    0,  119,   54,
    5,  123,   69,   25,   52,    0,   27,  135,    0,   54,
 -192,    0,  134,    0,    0, -192,    0,  -21,   25,    0,
    0,    0,    0,    0,   25,   25,    0,    0,   25,   25,
   25,  -47,   25,  121,    0,    0,   54,    0,    0,    0,
   25, -189,  -76,   63,    0, -174,   25,   72, -174,   54,
   79,    0,  -89,    0,   76,   19,   19,   54,    0,    0,
 -197,   54,    0,  149,    0,    0,    0, -192,   90,  -61,
    0,   91,  -55,  -21,    0,  -53,    0,  -47,  156,  159,
    0, -128,    0, -128,  178,    0,    0,  -41,    0,    0,
   61,   75,  100,  163,    0,    0, -161,    0,  103, -161,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  -44,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -12,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  224,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -39,    0,    0,    0,
    0,    0,    0,  -34,    0,    0,    0,    0,    0,   81,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  166,
    0,    0,  170,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  172,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -43,
    0,    0,    0,    0,    0,  -29,   -6,  192,    0,    0,
    0,   99,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    1,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  110,    0,    0,  128,
    0,
};
final static short yygindex[] = {                         0,
   20,   21,    0,    6,    0,    0,  312,   12,    8,    0,
  169,    0,  147,    0,    0,    0,    0,    0,    0,    0,
    0,   -8,   49,    0,    0,    0,    0,    0,  143,    0,
    0,    0,  122,  177,  -60,    0,    0,
};
final static int YYTABLESIZE=456;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         39,
   68,   39,   39,   39,   39,   39,   42,   39,   42,   42,
   42,   40,   47,   40,   40,   40,   51,   39,  119,   39,
   39,   31,   39,   60,   42,   42,   42,   42,   49,   40,
   40,   17,   40,   41,   41,   43,   41,   41,   41,   84,
   95,  131,   96,   76,    2,  104,   70,   73,  103,   32,
   14,   43,   41,   41,   82,   41,   80,   98,   87,   97,
  100,   43,  148,   33,  149,  101,   55,   60,    7,   60,
   75,    5,   10,    6,   88,   38,   13,    8,    9,  112,
   33,   12,  110,  135,  136,  107,    7,  158,  113,   34,
   10,  129,  130,  113,   13,    5,   95,    6,   96,  120,
  125,    8,    9,  139,   35,   12,  138,   39,  141,  106,
  128,   95,  142,   96,  132,  138,  147,   36,   95,  145,
   96,   83,  144,  140,   83,   68,  143,    4,    5,    5,
    6,   44,    7,   45,    8,    9,   10,   11,   12,   82,
   13,   52,   82,  126,  127,  113,   53,   54,   56,   74,
   78,  109,   81,   39,   86,  120,   89,    4,    5,  102,
    6,   90,    7,  105,    8,    9,   10,   11,   12,   79,
   13,  161,   76,  162,  111,  110,  108,  117,    1,  133,
  137,   43,   43,    4,    5,  165,    6,  146,    7,  150,
    8,    9,   10,   11,   12,  153,   13,    4,    5,  166,
    6,  155,    7,  157,    8,    9,   10,   11,   12,    5,
   13,    6,  152,  154,  159,    8,    9,  160,  163,   12,
  164,  168,  167,    4,   34,   46,   39,  171,   35,   50,
   12,   39,   69,   56,   60,   57,   58,   39,   39,   39,
   39,   39,   42,   42,   42,   42,   42,   40,   40,   40,
   40,   40,   59,   59,  118,   48,   68,   68,   17,   68,
   68,   68,   68,   68,   68,   68,   68,   68,   68,   68,
   41,   41,   41,   41,   41,   91,   92,   93,   94,   57,
   58,   57,   58,    5,  151,    6,  156,  134,  170,    8,
    9,    0,   72,   12,    0,    0,    0,   59,    0,   59,
    4,    5,    0,    6,    0,    7,    0,    8,    9,   10,
   11,   12,    0,   13,    0,    0,    4,    5,    0,    6,
    0,    7,   37,    8,    9,   10,   11,   12,    0,   13,
    4,    5,    0,    6,    0,    7,    0,    8,    9,   10,
   11,   12,    0,   13,    0,   61,   61,   61,    0,    0,
    0,    0,    0,    0,    0,    0,   61,    0,    0,   61,
   85,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   61,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   61,
   61,    0,    0,    0,    0,    0,   61,   61,    0,    0,
   61,   61,   61,    0,   61,    0,    0,    0,    0,    0,
    0,    0,   61,    0,    0,    0,    0,    0,   61,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   61,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         44,
    0,   41,   42,   43,   44,   45,   41,   47,   43,   44,
   45,   41,   44,   43,   44,   45,   44,   12,   40,   59,
   60,    2,   62,   45,   59,   60,  125,   62,   44,   59,
   60,   44,   62,   14,   41,   15,   43,   44,   45,   48,
   43,  102,   45,   38,  123,   41,   35,   36,   44,   59,
  123,   31,   59,   60,   47,   62,   45,   60,   51,   62,
   42,   41,  260,   46,  262,   47,  125,   45,  261,   45,
  256,  257,  265,  259,   40,  123,  269,  263,  264,  272,
   46,  267,   77,  273,  274,   74,  261,  148,   81,   40,
  265,  100,  101,   86,  269,  257,   43,  259,   45,   88,
   89,  263,  264,   41,   40,  267,   44,  102,  117,   41,
   99,   43,   41,   45,  103,   44,   41,   40,   43,   41,
   45,   41,   44,  116,   44,  125,  119,  256,  257,  257,
  259,   59,  261,  276,  263,  264,  265,  266,  267,   41,
  269,   59,   44,   95,   96,  138,   59,   59,  257,  276,
  268,  125,   40,  148,   40,  144,   40,  256,  257,   41,
  259,  258,  261,   41,  263,  264,  265,  266,  267,  125,
  269,  152,  167,  154,   40,  170,  125,   44,  257,   59,
  257,  161,  162,  256,  257,  125,  259,  277,  261,   41,
  263,  264,  265,  266,  267,  257,  269,  256,  257,  125,
  259,  257,  261,  257,  263,  264,  265,  266,  267,  257,
  269,  259,  123,  123,   59,  263,  264,   59,   41,  267,
  262,   59,  123,    0,   59,  257,  271,  125,   59,  257,
   59,  276,   41,  277,  125,  257,  258,  277,  278,  279,
  280,  281,  277,  278,  279,  280,  281,  277,  278,  279,
  280,  281,  125,  275,   86,  271,  256,  257,  271,  259,
  260,  261,  262,  263,  264,  265,  266,  267,  268,  269,
  277,  278,  279,  280,  281,  278,  279,  280,  281,  257,
  258,  257,  258,  257,  138,  259,  144,  111,  167,  263,
  264,   -1,  270,  267,   -1,   -1,   -1,  275,   -1,  275,
  256,  257,   -1,  259,   -1,  261,   -1,  263,  264,  265,
  266,  267,   -1,  269,   -1,   -1,  256,  257,   -1,  259,
   -1,  261,   11,  263,  264,  265,  266,  267,   -1,  269,
  256,  257,   -1,  259,   -1,  261,   -1,  263,  264,  265,
  266,  267,   -1,  269,   -1,   34,   35,   36,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   45,   -1,   -1,   48,
   49,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   74,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   88,
   89,   -1,   -1,   -1,   -1,   -1,   95,   96,   -1,   -1,
   99,  100,  101,   -1,  103,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  111,   -1,   -1,   -1,   -1,   -1,  117,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  144,
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
"sentencia_declarativa : funcion",
"sentencia_declarativa : declaracion_var ';'",
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
"lado_derecho_multiple : factor",
"lado_derecho_multiple : lado_derecho_multiple ',' factor",
"variable : ID '.' ID",
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
"condicional_do_while : DO bloque_ejecutable WHILE '(' condicion ')'",
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

//#line 308 "gramatica.y"

static AnalizadorLexico al;
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

        if (ts.isEmpty()) {
            System.out.println("La tabla de simbolos esta vacia.");
        } else {
            String formatString = "| %-30s | %-12s | %-18s | %-10s | %-10s |%n";

            System.out.printf(formatString, "Lexema", "Reservada", "Uso", "Tipo", "Contador");
            System.out.println("|--------------------------------|--------------|--------------------|------------|------------|");

            for (Map.Entry<String, HashMap<String, Object>> entry : ts.entrySet()) {
               String lexema = entry.getKey();
               HashMap<String, Object> atributos = entry.getValue();

               Object reservada = atributos.get("Reservada");
               Object uso = atributos.get("Uso");
               Object tipo = atributos.get("Tipo");
               Object contador = atributos.get("Contador");

               System.out.printf(formatString,
                    lexema,
                    (reservada != null) ? reservada.toString() : "null",
                    (uso != null) ? uso.toString() : "null",
                    (tipo != null) ? tipo.toString() : "null",
                    (contador != null) ? contador.toString() : "0"
                );
            }
        }

        System.out.println("=======================================================");
    } else {
        System.out.println("Error: Se requiere la ruta del archivo fuente como unico parametro.");
    }
}
//#line 534 "Parser.java"
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
//#line 22 "gramatica.y"
{
          String nombrePrograma = val_peek(3).sval;
          Object lineaObj = al.getAtributo(nombrePrograma, "Linea");
          String linea = (lineaObj != null) ? lineaObj.toString() : "?";

          salida.add("Linea " + linea + ": Programa '" + nombrePrograma + "' reconocido.");
          al.agregarAtributoLexema(nombrePrograma, "Uso", "Programa");

      }
break;
case 2:
//#line 32 "gramatica.y"
{
         erroresSintacticos.add("Linea " + (al.getContadorFila()+1) + ": Error sintactico: Falta el nombre del programa.");

     }
break;
case 3:
//#line 38 "gramatica.y"
{
         erroresSintacticos.add("Linea " + (al.getContadorFila()+1) + ": Error sintactico: Falta el delimitador '{' al inicio del programa.");

     }
break;
case 4:
//#line 44 "gramatica.y"
{
         erroresSintacticos.add("Linea " + (al.getContadorFila()+1) + ": Error sintactico: Falta el delimitador '}' al final del programa.");
     }
break;
case 12:
//#line 64 "gramatica.y"
{
                    salida.add("Linea " + (al.getContadorFila()+1) + ": Declaracion por inferencia (var).");
                }
break;
case 13:
//#line 69 "gramatica.y"
{ yyval.sval = "uint"; }
break;
case 14:
//#line 70 "gramatica.y"
{ yyval.sval = "float"; }
break;
case 15:
//#line 71 "gramatica.y"
{ yyval.sval = "lambda"; }
break;
case 16:
//#line 75 "gramatica.y"
{
                    listaVariables.add(val_peek(0).sval);
                }
break;
case 17:
//#line 79 "gramatica.y"
{
                    listaVariables.clear();
                    listaVariables.add(val_peek(0).sval);
                }
break;
case 18:
//#line 87 "gramatica.y"
{
            salida.add("Linea " + (al.getContadorFila()+1) + ": Declaracion de Funcion '" + val_peek(6).sval + "' con retorno simple.");
        }
break;
case 19:
//#line 91 "gramatica.y"
{
            salida.add("Linea " + (al.getContadorFila()+1) + ": Declaracion de Funcion '" + val_peek(6).sval + "' con retorno multiple.");
        }
break;
case 34:
//#line 122 "gramatica.y"
{
               salida.add("Linea " + (al.getContadorFila()+1) + ": Asignacion simple (:=).");
           }
break;
case 35:
//#line 129 "gramatica.y"
{
                        String lineaActual = String.valueOf(al.getContadorFila() + 1);

                        if (contadorLadoDerecho == 1 && val_peek(0).ival == 1) {
                            String funcName = val_peek(0).sval;
                            salida.add("Linea " + lineaActual + ": Asignacion multiple (funcion '" + funcName + "') reconocida.");
                        } else {
                            if (listaVariables.size() != contadorLadoDerecho) {
                                yyerror("Linea " + lineaActual + ": Error Sintactico: La asignacion multiple (lista) debe tener el mismo numero de elementos a la izquierda (" + listaVariables.size() + ") y a la derecha (" + contadorLadoDerecho + ").");
                            } else {
                                salida.add("Linea " + lineaActual + ": Asignacion multiple (lista) reconocida.");
                            }
                        }
                        contadorLadoDerecho = 0;
                    }
break;
case 36:
//#line 147 "gramatica.y"
{
                          contadorLadoDerecho = 1;
                          yyval.ival = val_peek(0).ival;
                          yyval.sval = val_peek(0).sval;
                      }
break;
case 37:
//#line 153 "gramatica.y"
{
                          contadorLadoDerecho++;
                          yyval.ival = 0;
                      }
break;
case 38:
//#line 160 "gramatica.y"
{ yyval.sval = val_peek(2).sval + "." + val_peek(0).sval; }
break;
case 39:
//#line 161 "gramatica.y"
{ yyval.sval = val_peek(0).sval; }
break;
case 46:
//#line 175 "gramatica.y"
{
           yyval.ival = 0;
       }
break;
case 47:
//#line 179 "gramatica.y"
{
           yyval.ival = 1;
           yyval.sval = val_peek(0).sval;
       }
break;
case 51:
//#line 191 "gramatica.y"
{
                    salida.add("Linea " + (al.getContadorFila()+1) + ": Conversion explicita (toui).");
                }
break;
case 52:
//#line 197 "gramatica.y"
{ yyval.sval = val_peek(3).sval; }
break;
case 64:
//#line 225 "gramatica.y"
{
                String lexemaPositivo = val_peek(0).sval;
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
            }
break;
case 67:
//#line 258 "gramatica.y"
{
                        Object lineaObj = al.getAtributo("do", "Linea");
                        String linea = (lineaObj != null) ? lineaObj.toString() : "?";
                        salida.add("Linea " + linea + ": Sentencia DO-WHILE reconocida.");
                    }
break;
case 68:
//#line 265 "gramatica.y"
{
                        Object lineaObj = al.getAtributo("do", "Linea");
                        String linea = (lineaObj != null) ? lineaObj.toString() : "?";
                        yyerror("Linea " + linea + ": Error Sintactico. Falta punto y coma ';' al final de la sentencia DO-WHILE.");
                    }
break;
case 79:
//#line 289 "gramatica.y"
{
                    salida.add("Linea " + (al.getContadorFila()+1) + ": PRINT con cadena multilinea.");
                }
break;
case 80:
//#line 293 "gramatica.y"
{
                    salida.add("Linea " + (al.getContadorFila()+1) + ": PRINT con expresion.");
                }
break;
case 81:
//#line 299 "gramatica.y"
{
                    salida.add("Linea " + (al.getContadorFila()+1) + ": Sentencia RETURN.");
                }
break;
//#line 889 "Parser.java"
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
