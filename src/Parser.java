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
    4,    4,    4,   15,   15,   16,   23,   23,    7,    7,
   22,   21,    8,    8,    8,   25,   25,   25,   24,   24,
   26,   26,   26,   29,   27,   30,   30,   31,   32,   32,
   33,   34,   34,   35,   35,   28,   28,   17,   17,   18,
   18,   36,   38,   38,   38,   38,   38,   38,   37,   37,
   37,   19,   19,   20,   39,   39,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    2,    1,    1,    1,    2,    1,
    2,    4,    1,    1,    1,    3,    1,    8,    8,    3,
    3,    3,    1,    3,    2,    2,    2,    2,    2,    1,
    1,    2,    1,    3,    3,    3,    1,    3,    1,    1,
    1,    3,    3,    3,    1,    3,    3,    1,    1,    1,
    1,    1,    1,    4,    4,    3,    1,    3,    1,    1,
    7,    1,    0,    2,    1,    1,    2,    7,    9,    7,
    6,    3,    1,    1,    1,    1,    1,    1,    3,    1,
    3,    4,    4,    5,    3,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,   14,    0,    0,   13,
    0,    0,   15,    0,    0,    6,    7,    8,   10,    0,
   17,    0,    0,    0,    0,    0,   30,   31,    0,   33,
    0,    0,    0,    9,    0,    0,    0,    0,    0,   40,
   39,    0,   80,    0,    0,    3,    5,   11,    0,    0,
    0,    0,    0,    0,   28,   29,   32,    0,    0,    2,
   42,    0,   66,    0,    0,   51,    0,   48,    0,   49,
   50,   52,   53,    0,    0,    0,    0,    0,    0,    0,
   65,    0,    0,    1,    0,   20,    0,   37,   16,    0,
   21,    0,    0,    0,    0,   67,   73,   74,   75,   76,
    0,    0,   77,   78,    0,    0,    0,    0,    0,    0,
   82,   83,    0,   81,   79,   64,    0,    0,    0,    0,
   23,    0,    0,    0,    0,    0,    0,   57,    0,   60,
    0,    0,    0,    0,   46,   47,    0,    0,   84,    0,
   26,   27,   25,    0,    0,    0,   38,    0,    0,    0,
   55,    0,   54,    0,    0,    0,   22,    0,   24,    0,
    0,   56,   58,    0,   68,   70,    0,    0,    0,    0,
   18,   19,    0,   69,    0,    0,   61,
};
final static short yydgoto[] = {                          3,
   15,   16,   17,   18,   19,   20,   66,   67,   22,   23,
  120,   24,  121,  122,   25,   26,   27,   28,   29,   30,
   40,   41,   87,   68,   69,   70,   71,   72,   73,  127,
  128,  129,  130,  175,   82,   74,   44,  105,   76,
};
final static short yysindex[] = {                       -87,
   65,  114,    0,    4,   50,   63,    0,   80,   85,    0,
 -210,  -73,    0,  114,   81,    0,    0,    0,    0,   69,
    0,  -31,  -15,  -27,   72,   76,    0,    0,   87,    0,
 -129, -125,   96,    0, -104,   25,   25,   23, -122,    0,
    0, -166,    0, -105,  128,    0,    0,    0,  124, -150,
   25, -210,  125, -150,    0,    0,    0,   25,   25,    0,
    0,    5,    0,  126,  -91,    0,   -2,    0,   36,    0,
    0,    0,    0,  127,   29,   40,  130,   97,   25,   48,
    0, -107,  129,    0, -159,    0,  133,    0,    0, -159,
    0,   29,   29,  -21,   25,    0,    0,    0,    0,    0,
   25,   25,    0,    0,   25,   25,   25,  -73,   25,  113,
    0,    0,   29,    0,    0,    0,   25, -179,  -78,   44,
    0, -150,   25,   64, -150,   29,   68,    0,  -96,    0,
   98,   36,   36,   29,    0,    0, -128,   29,    0,  141,
    0,    0,    0, -159,   60,  -72,    0,   70,  -70,  -21,
    0,  -61,    0,  -73,  138,  143,    0,  114,    0,  114,
  159,    0,    0,  -59,    0,    0,  160,  174,   86,  145,
    0,    0, -188,    0,   91, -188,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,  -44,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -12,   13,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  208,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -39,    0,    0,    0,    0,    0,    0,  -34,    0,
    0,    0,    0,    0,   77,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  151,    0,    0,    0,
    0,    4,  153,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  158,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -58,    0,    0,    0,    0,
    0,  -29,   -6,  177,    0,    0,    0,   83,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    1,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   99,    0,    0,  100,    0,
};
final static short yygindex[] = {                         0,
   53,    7,    0,   22,    0,    0,  147,   28,   -8,    0,
  132,    0,   84,    0,    0,    0,    0,    0,    0,    0,
   47,  294,    0,   -7,   43,    0,    0,    0,    0,    0,
  136,    0,    0,    0,   58,  116,  -81,    0,    0,
};
final static int YYTABLESIZE=470;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         41,
   71,   41,   41,   41,   41,   41,   45,   41,   45,   45,
   45,   43,   50,   43,   43,   43,   54,  115,  125,   41,
   41,   47,   41,   65,   45,   45,  137,   45,   52,   43,
   43,   40,   43,   43,   44,    2,   44,   44,   44,   47,
  101,   86,  102,   88,   94,   91,    5,   31,   31,   42,
   35,   47,   44,   44,   33,   44,   39,  104,   31,  103,
   31,   31,   34,   81,   75,   78,   45,   65,    5,   65,
    6,  101,  164,  102,    8,    9,  119,  106,   12,   31,
  110,  119,  107,  109,  145,   92,   93,  144,   31,   80,
    5,   31,    6,  141,  142,   35,    8,    9,  135,  136,
   12,    7,   36,  116,  148,   10,  113,  144,  151,   13,
    7,  150,  118,  146,   10,  147,  149,   86,   13,   37,
   86,  126,  131,   85,   38,   71,   85,   48,   31,   43,
   55,  154,  134,  155,   56,  119,  138,  112,  153,  101,
  101,  102,  102,  132,  133,   57,   58,   21,   21,    5,
   59,    6,   61,   79,   31,    8,    9,   39,   21,   12,
   21,   21,   83,   85,   90,   95,   96,  108,  117,    1,
  111,  139,  114,   47,   47,   43,  123,  126,  143,   21,
  152,  156,  158,    5,  159,    6,  161,   14,   21,    8,
    9,   21,  160,   12,   81,  163,  165,  116,   89,  169,
   31,  166,  170,  174,   31,   46,   31,    4,  173,   36,
  167,   35,  168,   31,   31,  177,   12,   72,   59,   31,
   60,  124,   31,   63,   62,   49,   41,  157,   21,   53,
  176,   41,  140,    0,    0,   62,   63,   41,   41,   41,
   41,   41,   45,   45,   45,   45,   45,   43,   43,   43,
   43,   43,   84,   64,   21,   51,   71,   71,   40,   71,
   71,   71,   71,   71,   71,   71,   71,   71,   71,   71,
   44,   44,   44,   44,   44,   97,   98,   99,  100,   62,
   63,   62,   63,   39,  171,  162,    0,    0,    0,    0,
    0,    0,   77,    0,   32,   32,    0,   64,  172,   64,
   21,    0,    0,    0,   21,   32,   21,   32,   32,    0,
    0,    0,    0,   21,   21,    0,    0,    0,    0,   21,
    4,    5,   21,    6,    0,    7,   32,    8,    9,   10,
   11,   12,    0,   13,    0,   32,    4,    5,   32,    6,
    0,    7,    0,    8,    9,   10,   11,   12,    0,   13,
    0,    4,    5,    0,    6,    0,    7,    0,    8,    9,
   10,   11,   12,    0,   13,    0,    0,    0,    0,    4,
    5,    0,    6,    0,    7,   32,    8,    9,   10,   11,
   12,    0,   13,    4,    5,    0,    6,    0,    7,    0,
    8,    9,   10,   11,   12,    0,   13,    0,    0,    0,
    0,   32,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    4,    5,    0,    6,    0,
    7,    0,    8,    9,   10,   11,   12,    0,   13,    4,
    5,    0,    6,    0,    7,    0,    8,    9,   10,   11,
   12,    0,   13,    0,    0,    0,    0,   32,    0,    0,
    0,   32,    0,   32,    0,    0,    0,    0,    0,    0,
   32,   32,    0,    0,    0,    0,   32,    0,    0,   32,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         44,
    0,   41,   42,   43,   44,   45,   41,   47,   43,   44,
   45,   41,   44,   43,   44,   45,   44,  125,   40,   59,
   60,   15,   62,   45,   59,   60,  108,   62,   44,   59,
   60,   44,   62,   12,   41,  123,   43,   44,   45,   33,
   43,   50,   45,   51,   40,   54,  257,    1,    2,  123,
   46,   45,   59,   60,    2,   62,   44,   60,   12,   62,
   14,   15,   59,   42,   37,   38,   14,   45,  257,   45,
  259,   43,  154,   45,  263,  264,   85,   42,  267,   33,
   41,   90,   47,   44,   41,   58,   59,   44,   42,  256,
  257,   45,  259,  273,  274,   46,  263,  264,  106,  107,
  267,  261,   40,   82,   41,  265,   79,   44,   41,  269,
  261,   44,  272,  122,  265,  123,  125,   41,  269,   40,
   44,   94,   95,   41,   40,  125,   44,   59,   82,  108,
   59,  260,  105,  262,   59,  144,  109,   41,   41,   43,
   43,   45,   45,  101,  102,   59,  276,    1,    2,  257,
  276,  259,  257,  276,  108,  263,  264,   11,   12,  267,
   14,   15,  268,   40,   40,   40,  258,   41,   40,  257,
   41,   59,  125,  167,  168,  154,   44,  150,  257,   33,
  277,   41,  123,  257,  257,  259,  257,  123,   42,  263,
  264,   45,  123,  267,  173,  257,   59,  176,   52,   41,
  154,   59,  262,   59,  158,  125,  160,    0,  123,   59,
  158,   59,  160,  167,  168,  125,   59,   41,  277,  173,
  125,   90,  176,  125,  125,  257,  271,  144,   82,  257,
  173,  276,  117,   -1,   -1,  257,  258,  277,  278,  279,
  280,  281,  277,  278,  279,  280,  281,  277,  278,  279,
  280,  281,  125,  275,  108,  271,  256,  257,  271,  259,
  260,  261,  262,  263,  264,  265,  266,  267,  268,  269,
  277,  278,  279,  280,  281,  278,  279,  280,  281,  257,
  258,  257,  258,  271,  125,  150,   -1,   -1,   -1,   -1,
   -1,   -1,  270,   -1,    1,    2,   -1,  275,  125,  275,
  154,   -1,   -1,   -1,  158,   12,  160,   14,   15,   -1,
   -1,   -1,   -1,  167,  168,   -1,   -1,   -1,   -1,  173,
  256,  257,  176,  259,   -1,  261,   33,  263,  264,  265,
  266,  267,   -1,  269,   -1,   42,  256,  257,   45,  259,
   -1,  261,   -1,  263,  264,  265,  266,  267,   -1,  269,
   -1,  256,  257,   -1,  259,   -1,  261,   -1,  263,  264,
  265,  266,  267,   -1,  269,   -1,   -1,   -1,   -1,  256,
  257,   -1,  259,   -1,  261,   82,  263,  264,  265,  266,
  267,   -1,  269,  256,  257,   -1,  259,   -1,  261,   -1,
  263,  264,  265,  266,  267,   -1,  269,   -1,   -1,   -1,
   -1,  108,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  256,  257,   -1,  259,   -1,
  261,   -1,  263,  264,  265,  266,  267,   -1,  269,  256,
  257,   -1,  259,   -1,  261,   -1,  263,  264,  265,  266,
  267,   -1,  269,   -1,   -1,   -1,   -1,  154,   -1,   -1,
   -1,  158,   -1,  160,   -1,   -1,   -1,   -1,   -1,   -1,
  167,  168,   -1,   -1,   -1,   -1,  173,   -1,   -1,  176,
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
"asignacion : variable_con_prefijo ASIG expresion",
"asignacion : variable_sin_prefijo ASIG expresion",
"asignacion_multiple : lista_variables ASIG_MULTIPLE lado_derecho_multiple",
"lado_derecho_multiple : factor",
"lado_derecho_multiple : lado_derecho_multiple ',' factor",
"variable : variable_sin_prefijo",
"variable : variable_con_prefijo",
"variable_sin_prefijo : ID",
"variable_con_prefijo : ID '.' ID",
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

//#line 322 "gramatica.y"

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
//#line 121 "gramatica.y"
{
               salida.add("Linea " + (al.getContadorFila()+1) + ": Asignacion simple (:=).");
           }
break;
case 35:
//#line 125 "gramatica.y"
{
               salida.add("Linea " + (al.getContadorFila()+1) + ": Asignacion simple (:=).");
           }
break;
case 36:
//#line 132 "gramatica.y"
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
case 37:
//#line 150 "gramatica.y"
{
                          contadorLadoDerecho = 1;
                          yyval.ival = val_peek(0).ival;
                          yyval.sval = val_peek(0).sval;
                      }
break;
case 38:
//#line 156 "gramatica.y"
{
                          contadorLadoDerecho++;
                          yyval.ival = 0;
                      }
break;
case 41:
//#line 167 "gramatica.y"
{
                        yyval.sval = val_peek(0).sval;
                     }
break;
case 42:
//#line 173 "gramatica.y"
{
                        yyval.sval = val_peek(2).sval + "." + val_peek(0).sval;
                     }
break;
case 49:
//#line 189 "gramatica.y"
{
           yyval.ival = 0;
       }
break;
case 50:
//#line 193 "gramatica.y"
{
           yyval.ival = 1;
           yyval.sval = val_peek(0).sval;
       }
break;
case 54:
//#line 205 "gramatica.y"
{
                    salida.add("Linea " + (al.getContadorFila()+1) + ": Conversion explicita (toui).");
                }
break;
case 55:
//#line 211 "gramatica.y"
{ yyval.sval = val_peek(3).sval; }
break;
case 67:
//#line 239 "gramatica.y"
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
case 70:
//#line 272 "gramatica.y"
{
                        Object lineaObj = al.getAtributo("do", "Linea");
                        String linea = (lineaObj != null) ? lineaObj.toString() : "?";
                        salida.add("Linea " + linea + ": Sentencia DO-WHILE reconocida.");
                    }
break;
case 71:
//#line 279 "gramatica.y"
{
                        Object lineaObj = al.getAtributo("do", "Linea");
                        String linea = (lineaObj != null) ? lineaObj.toString() : "?";
                        yyerror("Linea " + linea + ": Error Sintactico. Falta punto y coma ';' al final de la sentencia DO-WHILE.");
                    }
break;
case 82:
//#line 303 "gramatica.y"
{
                    salida.add("Linea " + (al.getContadorFila()+1) + ": PRINT con cadena multilinea.");
                }
break;
case 83:
//#line 307 "gramatica.y"
{
                    salida.add("Linea " + (al.getContadorFila()+1) + ": PRINT con expresion.");
                }
break;
case 84:
//#line 313 "gramatica.y"
{
                    salida.add("Linea " + (al.getContadorFila()+1) + ": Sentencia RETURN.");
                }
break;
//#line 904 "Parser.java"
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
