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
    import java.util.Stack;
//#line 29 "Parser.java"




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
public final static short UMINUS=282;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    0,    1,    1,    2,    2,    2,    3,
    3,    3,    5,    7,    8,    8,    8,    9,    9,    6,
    6,   13,   13,   12,   12,   14,   14,   15,   15,    4,
    4,    4,    4,    4,    4,   16,   17,   22,   22,   10,
   10,   11,   11,   11,   24,   24,   24,   23,   23,   23,
   23,   23,   27,   26,   28,   28,   29,   30,   30,   31,
   32,   32,   33,   33,   25,   25,   18,   18,   19,   19,
   36,   34,   37,   37,   37,   37,   37,   37,   35,   35,
   35,   20,   20,   21,   38,   38,
};
final static short yylen[] = {                            2,
    4,    3,    3,    3,    2,    1,    1,    1,    2,    2,
    1,    2,    2,    4,    1,    1,    1,    3,    1,    8,
    8,    3,    3,    3,    1,    3,    2,    2,    2,    2,
    2,    1,    1,    2,    1,    3,    3,    3,    1,    3,
    1,    3,    3,    1,    3,    3,    1,    1,    1,    1,
    1,    3,    4,    4,    3,    1,    3,    1,    1,    7,
    1,    0,    2,    1,    1,    2,    7,    9,    8,    7,
    0,    3,    1,    1,    1,    1,    1,    1,    3,    1,
    3,    4,    4,    5,    3,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,   16,    0,    0,   15,
    0,   71,   17,    0,    0,    6,    7,    8,    0,   11,
    0,    0,    0,    0,    0,    0,    0,   32,   33,    0,
   35,    0,    9,    0,    0,    0,    0,    0,    0,    0,
    3,    5,   10,   12,    0,    0,    0,   19,    0,    0,
    0,    0,    0,   30,   31,   34,    2,   40,    0,   65,
    0,    0,    0,   48,    0,   47,    0,   49,   50,   51,
    0,    0,    0,    0,    0,    0,    0,   80,    0,    1,
    0,   22,    0,   39,   18,    0,    0,   23,    0,    0,
   66,    0,   73,   74,   75,   76,    0,    0,   77,   78,
    0,    0,    0,    0,    0,    0,   82,   83,    0,    0,
   64,    0,    0,    0,    0,    0,   25,    0,    0,    0,
    0,    0,    0,   56,    0,   59,    0,   52,    0,    0,
    0,   45,   46,    0,    0,   84,   81,   79,   63,    0,
   28,   29,   27,    0,    0,    0,   38,    0,    0,    0,
   54,    0,   53,    0,    0,    0,   24,    0,   26,    0,
    0,   55,   57,    0,   67,    0,    0,    0,    0,    0,
   69,   20,   21,    0,   68,    0,    0,   60,
};
final static short yydgoto[] = {                          3,
   15,   16,   17,   18,   19,   20,   21,   22,   23,   64,
   65,  116,   25,  117,  118,   26,   27,   28,   29,   30,
   31,   83,   66,   67,   68,   69,   70,  123,  124,  125,
  126,  176,  112,   71,   79,   39,  101,   73,
};
final static short yysindex[] = {                       -87,
 -105, -158,    0,  -32,   21,   24,    0,   76,   77,    0,
 -136,    0,    0, -158,  -70,    0,    0,    0,   70,    0,
   88,  -31,  -44, -127,  -27,   94,   96,    0,    0,   98,
    0,  -54,    0,  -94,  -21,  -21,   25, -111,  -43,   57,
    0,    0,    0,    0,    4, -151,  122,    0,  -21, -136,
  -21,  127, -151,    0,    0,    0,    0,    0,    5,    0,
  128,  -86,  -21,    0,   -2,    0,   37,    0,    0,    0,
  130,  103,   47,  132,   87,  -21, -181,    0,  -93,    0,
 -180,    0,  133,    0,    0,  103, -180,    0,   29,  -21,
    0,   92,    0,    0,    0,    0,  -21,  -21,    0,    0,
  -21,  -21,  -21,  -43,  -21,  115,    0,    0,  103,   51,
    0,   44,  139, -226,  -74,   53,    0, -151,  -21,   81,
   23,  103,   99,    0,  -89,    0,   93,    0,   37,   37,
  103,    0,    0, -160,  103,    0,    0,    0,    0,  -21,
    0,    0,    0, -180,   67,  -65,    0,   75,  -57,   29,
    0,  -51,    0,  -43,  158,  177,    0, -158,    0, -158,
  178,    0,    0,  -37,    0,  164,   83,   97,  105,  170,
    0,    0,    0, -144,    0,  106, -144,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,   18,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -15,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  232,
    0,    0,    0,    0,    2,    0,  174,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -39,    0,
    0,    0,    0,    0,    0,    0,  -34,    0,    0,    0,
    0,  100,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  175,    0,    0,  176,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  194,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -22,    0,    0,    0,    0,    0,    0,  -29,   -6,
  218,    0,    0,    0,  101,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    1,    0,    0,    0,    0,
    0,    0,    0,  160,    0,    0,  165,    0,
};
final static short yygindex[] = {                         0,
   20,   17,    0,   27,    0,    0,    0,    6,  269,  295,
  292,  206,    0,  155,    0,    0,    0,    0,    0,    0,
    0,    0,   -7,  -25,    0,    0,    0,    0,  152,    0,
    0,    0,  131,  172,  -64,    0,    0,    0,
};
final static int YYTABLESIZE=472;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         50,
   70,   41,   41,   41,   41,   41,   44,   41,   44,   44,
   44,   42,   46,   42,   42,   42,   53,   14,   63,   41,
   41,   32,   41,   62,   44,   44,   33,   44,   19,   42,
   42,   42,   42,   40,   43,    2,   43,   43,   43,  134,
   97,   84,   98,   81,   89,   41,  141,  142,   42,   34,
   34,   82,   43,   43,   41,   43,   42,  100,   88,   99,
   41,   41,   63,   35,   63,   78,   34,   62,  121,   62,
   57,  129,  130,   62,  110,    5,   41,    6,  102,   77,
    7,    8,    9,  103,   10,   12,  115,  106,   13,  164,
  105,  114,  115,  145,  132,  133,  144,    4,    5,  154,
    6,  155,    7,  111,    8,    9,   10,   11,   12,    7,
   13,  147,    5,   10,    6,   36,   37,   13,    8,    9,
    5,  148,   12,  146,  144,   70,  149,  108,   43,   97,
   78,   98,  128,  153,   97,   97,   98,   98,  139,  151,
   86,   85,  150,   86,   85,   97,   44,   98,   51,  115,
    4,    5,   54,    6,   55,    7,   56,    8,    9,   10,
   11,   12,   58,   13,   76,   50,   87,   90,  138,    1,
  104,   91,  107,  136,  113,  137,  119,  167,  140,  168,
   78,   80,  143,   42,   42,    4,    5,  152,    6,  158,
    7,  159,    8,    9,   10,   11,   12,  160,   13,  161,
  111,    4,    5,  139,    6,  163,    7,  172,    8,    9,
   10,   11,   12,    5,   13,    6,  165,  166,  169,    8,
    9,  173,  171,   12,  170,   45,   49,  174,  175,   52,
  178,    4,   13,   37,   36,   59,   60,   41,   41,   41,
   41,   41,   44,   44,   44,   44,   44,   42,   42,   42,
   42,   42,   14,   61,   58,   19,   70,   70,   72,   70,
   70,   70,   70,   70,   70,   70,   70,   70,   70,   70,
   43,   43,   43,   43,   43,   93,   94,   95,   96,   59,
   60,   59,   60,    7,   62,   59,   60,   10,   41,   61,
   47,   13,  120,   41,   74,   24,   24,   61,  157,   61,
    5,  162,    6,   61,  177,   38,    8,    9,   24,   24,
   12,  156,    4,    5,    0,    6,   48,    7,    0,    8,
    9,   10,   11,   12,    0,   13,   24,   72,   75,    0,
    0,    0,    0,   24,   24,    0,    0,    0,    4,    5,
    0,    6,   86,    7,   85,    8,    9,   10,   11,   12,
    0,   13,    4,    5,   92,    6,    0,    7,    0,    8,
    9,   10,   11,   12,    0,   13,    0,  109,    0,    0,
    0,   24,    0,    0,    0,    0,    0,    0,    0,    0,
  122,  127,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  131,    0,    0,    0,  135,    0,   24,    0,
    0,    0,    0,    0,    0,    0,   24,    0,    0,    0,
    0,    0,   92,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  122,    0,    0,    0,    0,    0,    0,   24,    0,
    0,    0,   24,    0,   24,    0,    0,    0,    0,    0,
    0,   24,   24,    0,    0,    0,    0,    0,   24,    0,
    0,   24,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         44,
    0,   41,   42,   43,   44,   45,   41,   47,   43,   44,
   45,   41,   44,   43,   44,   45,   44,  123,   40,   59,
   60,    2,   62,   45,   59,   60,   59,   62,   44,   59,
   60,   15,   62,   14,   41,  123,   43,   44,   45,  104,
   43,   49,   45,   40,   40,   44,  273,  274,   32,   46,
   46,   46,   59,   60,  125,   62,   40,   60,   53,   62,
   59,   44,   40,   40,   40,   39,   46,   45,   40,   45,
  125,   97,   98,   45,  256,  257,   59,  259,   42,  123,
  261,  263,  264,   47,  265,  267,   81,   41,  269,  154,
   44,  272,   87,   41,  102,  103,   44,  256,  257,  260,
  259,  262,  261,   77,  263,  264,  265,  266,  267,  261,
  269,  119,  257,  265,  259,   40,   40,  269,  263,  264,
  257,   41,  267,  118,   44,  125,  121,   41,   59,   43,
  104,   45,   41,   41,   43,   43,   45,   45,  112,   41,
   41,   41,   44,   44,   44,   43,   59,   45,  276,  144,
  256,  257,   59,  259,   59,  261,   59,  263,  264,  265,
  266,  267,  257,  269,  276,   44,   40,   40,  125,  257,
   41,  258,   41,   59,  268,  125,   44,  158,   40,  160,
  154,  125,  257,  167,  168,  256,  257,  277,  259,  123,
  261,  257,  263,  264,  265,  266,  267,  123,  269,  257,
  174,  256,  257,  177,  259,  257,  261,  125,  263,  264,
  265,  266,  267,  257,  269,  259,   59,   41,   41,  263,
  264,  125,   59,  267,  262,  257,  271,  123,   59,  257,
  125,    0,   59,   59,   59,  257,  258,  277,  278,  279,
  280,  281,  277,  278,  279,  280,  281,  277,  278,  279,
  280,  281,   59,  275,  277,  271,  256,  257,   41,  259,
  260,  261,  262,  263,  264,  265,  266,  267,  268,  269,
  277,  278,  279,  280,  281,  278,  279,  280,  281,  257,
  258,  257,  258,  261,  125,  257,  258,  265,  271,  125,
   22,  269,   87,  276,  270,    1,    2,  275,  144,  275,
  257,  150,  259,  275,  174,   11,  263,  264,   14,   15,
  267,  140,  256,  257,   -1,  259,   22,  261,   -1,  263,
  264,  265,  266,  267,   -1,  269,   32,   36,   37,   -1,
   -1,   -1,   -1,   39,   40,   -1,   -1,   -1,  256,  257,
   -1,  259,   51,  261,   50,  263,  264,  265,  266,  267,
   -1,  269,  256,  257,   63,  259,   -1,  261,   -1,  263,
  264,  265,  266,  267,   -1,  269,   -1,   76,   -1,   -1,
   -1,   77,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   89,   90,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  101,   -1,   -1,   -1,  105,   -1,  104,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  112,   -1,   -1,   -1,
   -1,   -1,  121,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  150,   -1,   -1,   -1,   -1,   -1,   -1,  154,   -1,
   -1,   -1,  158,   -1,  160,   -1,   -1,   -1,   -1,   -1,
   -1,  167,  168,   -1,   -1,   -1,   -1,   -1,  174,   -1,
   -1,  177,
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
"MENOR_IGUAL","DISTINTO","IGUAL_IGUAL","UMINUS",
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
"asignacion_multiple : lista_variables ASIG_MULTIPLE lista_elementos_restringidos",
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
"factor : '(' expresion ')'",
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
"condicional_do_while : DO marcador_inicio_do bloque_ejecutable WHILE '(' condicion ')' ';'",
"condicional_do_while : DO marcador_inicio_do bloque_ejecutable WHILE '(' condicion ')'",
"marcador_inicio_do :",
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

//#line 400 "gramatica.y"

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
if (ts.isEmpty()) {
            System.out.println("La tabla de simbolos esta vacia.");
} else {
            for (Map.Entry<String, HashMap<String, Object>> entry : ts.entrySet()) {
                System.out.println("Lexema: " + entry.getKey());
for (Map.Entry<String, Object> atributos : entry.getValue().entrySet()) {
                    System.out.println("\t-> " + atributos.getKey() + ": " + atributos.getValue().toString());
}
            }
        }
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
//#line 25 "gramatica.y"
{
          salida.add("Linea " + (al.getContadorFila()+1) + ": Programa '" + val_peek(3).sval + "' reconocido.");
          al.agregarAtributoLexema(val_peek(3).sval, "Uso", "Programa");
      }
break;
case 2:
//#line 29 "gramatica.y"
{erroresSintacticos.add("Linea " + (al.getContadorFila()+1) + ": Error sintactico: Falta el nombre del programa.");}
break;
case 3:
//#line 31 "gramatica.y"
{erroresSintacticos.add("Linea " + (al.getContadorFila()+1) + ": Error sintactico: Falta el delimitador '{' al inicio del programa.");}
break;
case 4:
//#line 33 "gramatica.y"
{erroresSintacticos.add("Linea " + (al.getContadorFila()+1) + ": Error sintactico: Falta el delimitador '}' al final del programa.");}
break;
case 9:
//#line 43 "gramatica.y"
{erroresSintacticos.add("Linea " + (al.getContadorFila()+1) + ": Error sintactico en la sentencia.");}
break;
case 13:
//#line 52 "gramatica.y"
{
                for (String var : listaVariables) {
                    if (al.getTablaSimbolos().containsKey(var) && al.getAtributo(var, "Uso") != null) {
                        erroresSemanticos.add("Linea " + (al.getContadorFila()+1) + ": Error Semantico. Redeclaracion de variable '" + var + "'.");
                    } else {
                        al.agregarAtributoLexema(var, "Tipo", val_peek(1).sval);
                        al.agregarAtributoLexema(var, "Uso", "Identificador");
                    }
                }
                salida.add("Linea " + (al.getContadorFila()+1) + ": Declaracion de variables de tipo '" + val_peek(1).sval + "'.");
                listaVariables.clear();
            }
break;
case 14:
//#line 66 "gramatica.y"
{
                    String tipoExp = gen.getTipo(val_peek(0).sval);

                    if (!tipoExp.equals("ERROR")) {
                        if (al.getTablaSimbolos().containsKey(val_peek(2).sval) && al.getAtributo(val_peek(2).sval, "Uso") != null) {
                             erroresSemanticos.add("Linea " + (al.getContadorFila()+1) + ": Error Semantico (Tema 9). Redeclaracion de variable '" + val_peek(2).sval + "'.");
                        } else {
                             al.agregarAtributoLexema(val_peek(2).sval, "Tipo", tipoExp);
                             al.agregarAtributoLexema(val_peek(2).sval, "Uso", "Identificador");
                        }
                    }

                    gen.crearTerceto(":=", val_peek(2).sval, val_peek(0).sval);
                    salida.add("Linea " + (al.getContadorFila()+1) + ": Declaracion por inferencia (var).");
                }
break;
case 15:
//#line 82 "gramatica.y"
{ yyval.sval = "uint"; }
break;
case 16:
//#line 83 "gramatica.y"
{ yyval.sval = "float";
}
break;
case 17:
//#line 85 "gramatica.y"
{ yyval.sval = "lambda"; }
break;
case 18:
//#line 88 "gramatica.y"
{
                    listaVariables.add(val_peek(0).sval);
                }
break;
case 19:
//#line 93 "gramatica.y"
{
                    listaVariables.clear();
                    listaVariables.add(val_peek(0).sval);
                }
break;
case 20:
//#line 99 "gramatica.y"
{
            salida.add("Linea " + (al.getContadorFila()+1) + ": Declaracion de Funcion '" + val_peek(6).sval + "' con retorno simple.");
}
break;
case 21:
//#line 103 "gramatica.y"
{
            salida.add("Linea " + (al.getContadorFila()+1) + ": Declaracion de Funcion '" + val_peek(6).sval + "' con retorno multiple.");
}
break;
case 26:
//#line 117 "gramatica.y"
{
                    al.agregarAtributoLexema(val_peek(0).sval, "Tipo", val_peek(1).sval);
                    al.agregarAtributoLexema(val_peek(0).sval, "Uso", "Parametro");
                    al.agregarAtributoLexema(val_peek(0).sval, "Pasaje", val_peek(2).sval);
                 }
break;
case 27:
//#line 124 "gramatica.y"
{
                    al.agregarAtributoLexema(val_peek(0).sval, "Tipo", val_peek(1).sval);
                    al.agregarAtributoLexema(val_peek(0).sval, "Uso", "Parametro");
                    al.agregarAtributoLexema(val_peek(0).sval, "Pasaje", "CV");
                 }
break;
case 28:
//#line 130 "gramatica.y"
{ yyval.sval = "CR SE"; }
break;
case 29:
//#line 132 "gramatica.y"
{ yyval.sval = "CR LE"; }
break;
case 36:
//#line 145 "gramatica.y"
{
               String tipoRes = gen.chequearTipos(val_peek(2).sval, val_peek(0).sval, ":=", erroresSemanticos);
               gen.crearTerceto(":=", val_peek(2).sval, val_peek(0).sval);
               salida.add("Linea " + (al.getContadorFila()+1) + ": Asignacion simple (:=).");
}
break;
case 37:
//#line 153 "gramatica.y"
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
break;
case 38:
//#line 172 "gramatica.y"
{
                                 listaLadoDerecho.add(val_peek(0).sval);
                             }
break;
case 39:
//#line 177 "gramatica.y"
{
                                 listaLadoDerecho.clear();
                                 listaLadoDerecho.add(val_peek(0).sval);
                             }
break;
case 40:
//#line 182 "gramatica.y"
{
                yyval.sval = val_peek(2).sval + "." + val_peek(0).sval;
}
break;
case 41:
//#line 185 "gramatica.y"
{ yyval.sval = val_peek(0).sval;
}
break;
case 42:
//#line 189 "gramatica.y"
{
              String tipoRes = gen.chequearTipos(val_peek(2).sval, val_peek(0).sval, "+", erroresSemanticos);
              yyval.sval = gen.crearTerceto("+", val_peek(2).sval, val_peek(0).sval);
              gen.getTerceto(Integer.parseInt(yyval.sval.substring(1, yyval.sval.length()-1))).setTipo(tipoRes);
          }
break;
case 43:
//#line 196 "gramatica.y"
{
              String tipoRes = gen.chequearTipos(val_peek(2).sval, val_peek(0).sval, "-", erroresSemanticos);
              yyval.sval = gen.crearTerceto("-", val_peek(2).sval, val_peek(0).sval);
              gen.getTerceto(Integer.parseInt(yyval.sval.substring(1, yyval.sval.length()-1))).setTipo(tipoRes);
          }
break;
case 44:
//#line 203 "gramatica.y"
{ yyval = val_peek(0);
}
break;
case 45:
//#line 207 "gramatica.y"
{
            String tipoRes = gen.chequearTipos(val_peek(2).sval, val_peek(0).sval, "*", erroresSemanticos);
            yyval.sval = gen.crearTerceto("*", val_peek(2).sval, val_peek(0).sval);
            gen.getTerceto(Integer.parseInt(yyval.sval.substring(1, yyval.sval.length()-1))).setTipo(tipoRes);
        }
break;
case 46:
//#line 214 "gramatica.y"
{
            String tipoRes = gen.chequearTipos(val_peek(2).sval, val_peek(0).sval, "/", erroresSemanticos);
            yyval.sval = gen.crearTerceto("/", val_peek(2).sval, val_peek(0).sval);
            gen.getTerceto(Integer.parseInt(yyval.sval.substring(1, yyval.sval.length()-1))).setTipo(tipoRes);
        }
break;
case 47:
//#line 221 "gramatica.y"
{ yyval = val_peek(0);
}
break;
case 48:
//#line 225 "gramatica.y"
{ yyval = val_peek(0);
}
break;
case 49:
//#line 228 "gramatica.y"
{ yyval = val_peek(0);
}
break;
case 50:
//#line 232 "gramatica.y"
{ yyval = val_peek(0);
}
break;
case 51:
//#line 235 "gramatica.y"
{ yyval = val_peek(0);
}
break;
case 52:
//#line 238 "gramatica.y"
{ yyval = val_peek(1);
}
break;
case 53:
//#line 242 "gramatica.y"
{
                    String tipoRes = gen.chequearTipos(val_peek(1).sval, null, "TOUI", erroresSemanticos);
                    yyval.sval = gen.crearTerceto("TOUI", val_peek(1).sval);
                    gen.getTerceto(Integer.parseInt(yyval.sval.substring(1, yyval.sval.length()-1))).setTipo(tipoRes);
                    salida.add("Linea " + (al.getContadorFila()+1) + ": Conversion explicita (toui).");
}
break;
case 65:
//#line 271 "gramatica.y"
{ yyval = val_peek(0);
}
break;
case 66:
//#line 275 "gramatica.y"
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
                yyval.sval = lexemaNegativo;
            }
break;
case 67:
//#line 301 "gramatica.y"
{
                   String refBF = gen.crearTerceto("BF", val_peek(4).sval);
                   gen.apilar();

                   int indexBF = gen.desapilar();
                   gen.completarSalto(indexBF, gen.getProximoNumero());
               }
break;
case 68:
//#line 310 "gramatica.y"
{
                   String refBF = gen.crearTerceto("BF", val_peek(6).sval);
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
break;
case 69:
//#line 328 "gramatica.y"
{
                        int inicioDo = gen.desapilar();
                        gen.crearTerceto("BT", val_peek(1).sval, String.valueOf(inicioDo));
                        salida.add("Linea " + (al.getContadorFila()+1) + ": Sentencia DO-WHILE reconocida.");
}
break;
case 70:
//#line 335 "gramatica.y"
{
                        yyerror("Linea " + al.getContadorFila() + ": Error Sintactico. Falta punto y coma ';' al final de la sentencia DO-WHILE.");
                        gen.desapilar();
}
break;
case 71:
//#line 342 "gramatica.y"
{
                        gen.apilar(gen.getProximoNumero());
                    }
break;
case 72:
//#line 348 "gramatica.y"
{
              String tipoRes = gen.chequearTipos(val_peek(2).sval, val_peek(0).sval, val_peek(1).sval, erroresSemanticos);
              yyval.sval = gen.crearTerceto(val_peek(1).sval, val_peek(2).sval, val_peek(0).sval);
              gen.getTerceto(Integer.parseInt(yyval.sval.substring(1, yyval.sval.length()-1))).setTipo(tipoRes);
          }
break;
case 73:
//#line 354 "gramatica.y"
{ yyval.sval = ">="; }
break;
case 74:
//#line 356 "gramatica.y"
{ yyval.sval = "<="; }
break;
case 75:
//#line 358 "gramatica.y"
{ yyval.sval = "=!"; }
break;
case 76:
//#line 360 "gramatica.y"
{ yyval.sval = "=="; }
break;
case 77:
//#line 362 "gramatica.y"
{ yyval.sval = ">"; }
break;
case 78:
//#line 364 "gramatica.y"
{ yyval.sval = "<"; }
break;
case 82:
//#line 373 "gramatica.y"
{
                    gen.crearTerceto("PRINT", val_peek(1).sval);
                    salida.add("Linea " + (al.getContadorFila()+1) + ": PRINT con cadena multilinea.");
}
break;
case 83:
//#line 379 "gramatica.y"
{
                    gen.crearTerceto("PRINT", val_peek(1).sval);
                    salida.add("Linea " + (al.getContadorFila()+1) + ": PRINT con expresion.");
}
break;
case 84:
//#line 385 "gramatica.y"
{
                    gen.crearTerceto("RETURN", val_peek(2).sval);
                    salida.add("Linea " + (al.getContadorFila()+1) + ": Sentencia RETURN.");
}
break;
case 85:
//#line 391 "gramatica.y"
{
                      yyval = val_peek(0);
                  }
break;
case 86:
//#line 396 "gramatica.y"
{ yyval = val_peek(0);
}
break;
//#line 1079 "Parser.java"
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
