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
    2,    3,    0,    4,    5,    0,    6,    6,    1,    1,
    7,    7,    7,    8,    8,   11,   11,   14,   14,   14,
   15,   15,   16,   16,   18,   10,   20,   10,   19,   19,
   17,   17,   21,   21,   21,   22,   22,    9,    9,    9,
    9,    9,    9,    9,    9,   23,   23,   24,   25,   34,
   32,   32,   12,   12,   13,   13,   13,   35,   35,   35,
   33,   33,   36,   36,   36,   31,   38,   30,   39,   39,
   40,   40,   41,   41,   44,   42,   43,   43,   45,   45,
   45,   45,   46,   46,   37,   37,   47,   47,   49,   26,
   26,   26,   26,   26,   51,   27,   48,   52,   52,   52,
   52,   52,   52,   52,   53,   50,   54,   50,   50,   28,
   28,   56,   29,   55,   55,
};
final static short yylen[] = {                            2,
    0,    0,    6,    0,    0,    5,    1,    0,    2,    1,
    1,    1,    2,    1,    2,    4,    4,    1,    1,    1,
    1,    1,    3,    3,    0,    9,    0,    9,    3,    3,
    3,    1,    3,    2,    2,    2,    2,    2,    2,    2,
    1,    1,    2,    1,    2,    3,    3,    3,    3,    0,
    2,    3,    3,    1,    3,    3,    1,    3,    3,    1,
    1,    1,    1,    1,    1,    4,    0,    5,    3,    1,
    3,    1,    1,    1,    0,    8,    1,    0,    2,    1,
    2,    1,    5,    3,    1,    2,    4,    4,    1,    4,
    6,    4,    6,    3,    0,    8,    3,    1,    1,    1,
    1,    1,    1,    1,    0,    4,    0,    3,    3,    4,
    4,    0,    6,    3,    1,
};
final static short yydefred[] = {                         0,
    0,    4,    0,    1,    0,    0,    0,    0,    0,   19,
    0,    0,   18,    0,   95,   20,    0,    0,   10,   11,
   12,   14,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   41,   42,    0,   44,    0,    0,    0,    0,   13,
    0,    0,    0,  112,    0,    0,    0,    0,    0,    5,
    9,    7,   15,    0,    0,    0,    0,   50,   50,    0,
    0,    0,   38,   39,   40,   43,   45,    0,    0,    0,
    2,   53,    0,    0,   85,    0,   63,    0,   62,   65,
   60,    0,   61,   64,    0,    0,    0,    0,    0,    0,
    0,    0,    6,    0,   24,    0,   29,    0,    0,    0,
   23,    0,   30,    0,    0,    0,    0,   94,   89,    0,
    0,    3,    0,    0,    0,   70,    0,   74,   88,   86,
  104,   98,   99,  100,  101,    0,    0,  102,  103,    0,
    0,    0,   87,    0,    0,  110,  111,    0,   17,    0,
   66,    0,    0,    0,   32,    0,    0,   51,    0,  109,
    0,   80,    0,   82,  108,   92,   90,    0,    0,    0,
   68,    0,    0,    0,    0,   58,   59,    0,    0,    0,
   36,   37,   34,    0,    0,   35,    0,   52,    0,    0,
  106,   79,   81,    0,    0,   69,   71,    0,  113,    0,
   31,   25,   33,   27,    0,   84,   93,   91,    0,    0,
    0,    0,    0,   75,   96,    0,    0,   83,    0,   26,
   28,    0,    0,   76,
};
final static short yydgoto[] = {                          3,
   18,    6,  112,    5,   93,   53,   19,   20,   21,   22,
   23,   77,   78,   25,   26,   27,  144,  201,   28,  202,
  145,  146,   29,   30,   31,   32,   33,   34,   35,   79,
   80,   98,   81,   99,   82,   83,   84,   42,  115,  116,
  117,  118,  212,  209,  153,  154,   38,   85,  111,   70,
   48,  130,  106,  107,  135,   86,
};
final static short yysindex[] = {                      -105,
  -98,    0,    0,    0, -131, -131,  -27, -240,   29,    0,
   40,   54,    0, -139,    0,    0,   89, -108,    0,    0,
    0,    0,   72,  -30,  -35, -124,  -28,   15,   72,   72,
   72,    0,    0,   72,    0,   72, -116,   41,  -88,    0,
  -91,  130,   67,    0,   74, -240,  -48,   41,   78,    0,
    0,    0,    0,   78, -139,  132, -201,    0,    0, -139,
  142, -201,    0,    0,    0,    0,    0,   78,  -72, -147,
    0,    0,   25,  145,    0,  -66,    0,  -22,    0,    0,
    0,   19,    0,    0,  149,   78,  152,   60,   78,  -59,
  -67,   65,    0,   71,    0, -170,    0,  156,   78,  156,
    0, -170,    0,   71,   77,  -68,   79,    0,    0,  -53,
   41,    0, -201,   71,   34,    0,  -71,    0,    0,    0,
    0,    0,    0,    0,    0,   78,   78,    0,    0,   78,
   78,   78,    0,   71,   52,    0,    0,   71,    0,  172,
    0, -222,  -44,   83,    0, -207,   78,    0,   98,    0,
 -139,    0,  230,    0,    0,    0,    0,    8,  -42,   25,
    0, -139,   19,   19,   71,    0,    0,   78,   72,   78,
    0,    0,    0, -170,  109,    0,  -39,    0,  110,  -46,
    0,    0,    0,  -47,  193,    0,    0,   71,    0,  243,
    0,    0,    0,    0,   78,    0,    0,    0,  162,   72,
 -131, -131,    4,    0,    0,  262,  282,    0,  -68,    0,
    0,  161,  -68,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,  -40,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  302,   27,    0,    0,   39,    0,  302,  302,
  302,    0,    0,  302,    0,  302,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   63,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  329,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -14,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   95,    0,    0,    0,  135,    0,  155,
    0,    0,    0,  215,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -33,    0,    0,   99,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  121,    0,    0,    0,  247,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   12,   38,  255,    0,    0,    0,  302,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  344,
    0,    0,    0,    0,    0,    0,    0,  139,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  302,
    0,    0,  344,    0,    0,    0,    0,    0,  186,    0,
    0,    0,  187,    0,
};
final static short yygindex[] = {                         0,
    9,    0,    0,    0,    0,    5,  102,    0,  -63,    0,
    0,  428,  529,  -29,    0,    0,  219,    0,    0,    0,
  154,    0,    0,    0,    0,    0,    0,    0,    0,  438,
  447,  274,  -55,    0,  -38,    0,    0,    0,    0,  177,
    0,    0,    0,    0,  134, -127,    0,  171,    0,  -24,
    0,    0,    0,    0,    0,    0,
};
final static int YYTABLESIZE=724;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         67,
   54,   54,   54,   54,   54,  157,   54,   73,   57,   90,
   73,  198,   52,   55,   39,   60,   50,    2,   54,   54,
  126,   54,  127,   91,    4,  183,   57,   97,   57,   57,
   57,   40,  103,   63,   64,   65,   71,  129,   66,  128,
   67,   41,  152,  148,   57,   57,  126,   57,  127,  176,
  171,  172,   55,   10,   55,   55,   55,   13,   62,   10,
  131,   16,   52,   13,  113,  132,  143,   16,   43,   76,
   55,   55,  143,   55,  161,  166,  167,  160,   56,   44,
   56,   56,   56,  159,   54,  183,  158,  163,  164,  182,
   10,  178,  169,   45,   13,  168,   56,   56,   16,   56,
  137,  142,  126,   54,  127,  141,   54,  126,  108,  127,
   57,   76,  109,  126,  110,  127,  177,   46,   76,   51,
   54,   54,   76,  175,    7,    8,  174,    9,   49,   10,
   52,   11,   12,   13,   14,   15,   55,   16,  179,   72,
   51,  174,   72,   17,  143,  152,   58,    7,    8,  182,
    9,    1,   10,   46,   11,   12,   13,   14,   15,   68,
   16,  115,   56,   69,  115,   72,   17,    7,    8,   73,
    9,   96,   10,  189,   11,   12,   13,   14,   15,  114,
   16,  102,  114,  105,  196,  119,   17,   54,    8,  133,
    9,  120,  136,   48,   11,   12,  139,  151,   15,  147,
  140,  150,  156,  155,  205,  162,   17,  208,  197,  206,
  207,  170,  173,   49,  185,   54,   54,  193,   54,   46,
   54,   56,   54,   54,   54,   54,   54,   89,   54,  195,
   54,  192,  194,  199,   54,   54,   54,   54,   54,   54,
   54,   57,   57,   73,   57,   54,   57,   59,   57,   57,
   57,   57,   57,  121,   57,  122,  123,  124,  125,   48,
   57,   57,   57,   57,   57,   57,   57,   55,   55,  184,
   55,   61,   55,   47,   55,   55,   55,   55,   55,   49,
   55,    8,   75,  200,  204,  214,   55,   55,   55,   55,
   55,   55,   55,   56,   56,   97,   56,   21,   56,   17,
   56,   56,   56,   56,   56,   16,   56,   51,   51,   22,
   78,   77,   56,   56,   56,   56,   56,   56,   56,   54,
  149,   54,   74,    8,   75,   54,   54,  191,   54,   54,
    8,   75,  100,   54,    8,   75,  186,   54,   54,   47,
  190,   17,  213,   87,    0,    0,    0,    0,   17,    0,
   46,   46,   17,   46,  181,   46,    0,   46,   46,   46,
   46,   46,    0,   46,    0,    0,    0,    0,    0,   46,
    0,   16,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  210,    0,    0,    0,
   48,   48,    0,   48,    0,   48,    0,   48,   48,   48,
   48,   48,    0,   48,    0,    0,  211,    0,    0,   48,
   49,   49,    0,   49,    0,   49,    0,   49,   49,   49,
   49,   49,    0,   49,    0,    0,    8,    0,    0,   49,
    0,    0,   24,   24,    0,    0,    0,    0,    0,    0,
    0,   47,   36,   36,    0,   24,    0,    0,    0,    0,
    0,   37,   37,  107,    0,   36,    0,    0,    0,    0,
    0,    0,    0,    0,   37,    0,   24,    0,    8,    0,
   47,   47,    0,   47,    0,   47,   36,   47,   47,   47,
   47,   47,   95,   47,    0,   37,    8,  101,    9,   47,
    0,    0,   11,   12,    0,  151,   15,    0,    0,    0,
    0,    0,   16,   16,   17,   16,    0,   16,    0,   16,
   16,   16,   16,   16,    0,   16,    0,    7,    8,    0,
    9,   16,   10,    0,   11,   12,   13,   14,   15,    0,
   16,    0,    0,   24,    0,    0,   17,    7,    8,    0,
    9,    0,   10,   36,   11,   12,   13,   14,   15,    0,
   16,    0,   37,    0,    0,    0,   17,    8,    8,    0,
    8,    0,    8,    0,    8,    8,    8,    8,    8,    0,
    8,    0,    0,   88,    0,    0,    8,   92,  180,    0,
   24,    0,   94,    0,    0,  105,    0,  105,    0,  187,
   36,  105,  105,    0,  105,  105,  104,    0,    0,   37,
    8,  114,    8,  105,    0,    0,    8,    8,    0,    8,
    8,    0,    0,    0,  134,    0,    0,  138,    8,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   24,   24,
    0,    0,    0,   24,   24,    0,   24,    0,   36,   36,
   24,    0,    0,   36,   36,    0,   36,   37,   37,    0,
   36,    0,   37,   37,    0,   37,    0,    0,  165,   37,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  114,    0,
    0,    0,    0,    0,    0,    0,  188,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  203,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   41,   42,   43,   44,   45,   59,   47,   41,   44,   58,
   44,   59,   59,   44,    6,   44,  125,  123,   59,   60,
   43,   62,   45,   48,  123,  153,   41,   57,   43,   44,
   45,   59,   62,   29,   30,   31,  125,   60,   34,   62,
   36,  282,  106,   99,   59,   60,   43,   62,   45,  257,
  273,  274,   41,  261,   43,   44,   45,  265,   44,  261,
   42,  269,   59,  265,   40,   47,   96,  269,   40,   45,
   59,   60,  102,   62,   41,  131,  132,   44,   41,   40,
   43,   44,   45,  113,  125,  213,  111,  126,  127,  153,
  261,  147,   41,   40,  265,   44,   59,   60,  269,   62,
   41,  272,   43,   41,   45,   41,   44,   43,  256,   45,
  125,   45,  260,   43,  262,   45,  146,  257,   45,   18,
   58,   59,   45,   41,  256,  257,   44,  259,   40,  261,
   59,  263,  264,  265,  266,  267,  125,  269,   41,   41,
   39,   44,   44,  275,  174,  209,  271,  256,  257,  213,
  259,  257,  261,   59,  263,  264,  265,  266,  267,  276,
  269,   41,  125,  123,   44,  257,  275,  256,  257,   40,
  259,   40,  261,  169,  263,  264,  265,  266,  267,   41,
  269,   40,   44,  256,  180,   41,  275,  125,  257,   41,
  259,  258,   41,   59,  263,  264,  256,  266,  267,   44,
  268,  125,  256,  125,  200,  277,  275,  203,  256,  201,
  202,   40,  257,   59,  257,  256,  257,  257,  259,  125,
  261,  257,  263,  264,  265,  266,  267,  276,  269,  276,
  271,  123,  123,   41,  275,  276,  277,  278,  279,  280,
  281,  256,  257,  277,  259,  276,  261,  276,  263,  264,
  265,  266,  267,  276,  269,  278,  279,  280,  281,  125,
  275,  276,  277,  278,  279,  280,  281,  256,  257,  262,
  259,  257,  261,   59,  263,  264,  265,  266,  267,  125,
  269,  257,  258,   41,  123,  125,  275,  276,  277,  278,
  279,  280,  281,  256,  257,   41,  259,  271,  261,  275,
  263,  264,  265,  266,  267,   59,  269,  206,  207,  271,
  125,  125,  275,  276,  277,  278,  279,  280,  281,  257,
  102,  259,  256,  257,  258,  263,  264,  174,  266,  267,
  257,  258,   59,  271,  257,  258,  160,  275,  276,  125,
  170,  275,  209,  270,   -1,   -1,   -1,   -1,  275,   -1,
  256,  257,  275,  259,  125,  261,   -1,  263,  264,  265,
  266,  267,   -1,  269,   -1,   -1,   -1,   -1,   -1,  275,
   -1,  125,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  125,   -1,   -1,   -1,
  256,  257,   -1,  259,   -1,  261,   -1,  263,  264,  265,
  266,  267,   -1,  269,   -1,   -1,  125,   -1,   -1,  275,
  256,  257,   -1,  259,   -1,  261,   -1,  263,  264,  265,
  266,  267,   -1,  269,   -1,   -1,  125,   -1,   -1,  275,
   -1,   -1,    5,    6,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   14,    5,    6,   -1,   18,   -1,   -1,   -1,   -1,
   -1,    5,    6,  125,   -1,   18,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   18,   -1,   39,   -1,  125,   -1,
  256,  257,   -1,  259,   -1,  261,   39,  263,  264,  265,
  266,  267,   55,  269,   -1,   39,  257,   60,  259,  275,
   -1,   -1,  263,  264,   -1,  266,  267,   -1,   -1,   -1,
   -1,   -1,  256,  257,  275,  259,   -1,  261,   -1,  263,
  264,  265,  266,  267,   -1,  269,   -1,  256,  257,   -1,
  259,  275,  261,   -1,  263,  264,  265,  266,  267,   -1,
  269,   -1,   -1,  106,   -1,   -1,  275,  256,  257,   -1,
  259,   -1,  261,  106,  263,  264,  265,  266,  267,   -1,
  269,   -1,  106,   -1,   -1,   -1,  275,  256,  257,   -1,
  259,   -1,  261,   -1,  263,  264,  265,  266,  267,   -1,
  269,   -1,   -1,   45,   -1,   -1,  275,   49,  151,   -1,
  153,   -1,   54,   -1,   -1,  257,   -1,  259,   -1,  162,
  153,  263,  264,   -1,  266,  267,   68,   -1,   -1,  153,
  257,   73,  259,  275,   -1,   -1,  263,  264,   -1,  266,
  267,   -1,   -1,   -1,   86,   -1,   -1,   89,  275,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  201,  202,
   -1,   -1,   -1,  206,  207,   -1,  209,   -1,  201,  202,
  213,   -1,   -1,  206,  207,   -1,  209,  201,  202,   -1,
  213,   -1,  206,  207,   -1,  209,   -1,   -1,  130,  213,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  160,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  168,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  195,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=283;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,"':'","';'",
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
"fin_sentencia : ';'",
"fin_sentencia :",
"sentencias : sentencias sentencia",
"sentencias : sentencia",
"sentencia : sentencia_declarativa",
"sentencia : sentencia_ejecutable",
"sentencia : error ';'",
"sentencia_declarativa : funcion",
"sentencia_declarativa : declaracion_var fin_sentencia",
"declaracion_var : VAR variable ASIG expresion",
"declaracion_var : VAR variable ':' error",
"tipo : UINT",
"tipo : FLOAT",
"tipo : LAMBDA",
"lista_variables : variable",
"lista_variables : lista_strict_multiple",
"lista_strict_multiple : lista_strict_multiple ',' variable",
"lista_strict_multiple : variable ',' variable",
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
"parametro_formal : sem_pasaje ID",
"sem_pasaje : CR SE",
"sem_pasaje : CR LE",
"sentencia_ejecutable : asignacion fin_sentencia",
"sentencia_ejecutable : asignacion_multiple fin_sentencia",
"sentencia_ejecutable : asignacion_multiple_warning fin_sentencia",
"sentencia_ejecutable : condicional_if",
"sentencia_ejecutable : condicional_do_while",
"sentencia_ejecutable : salida_pantalla fin_sentencia",
"sentencia_ejecutable : retorno_funcion",
"sentencia_ejecutable : invocacion_funcion fin_sentencia",
"asignacion : variable ASIG expresion",
"asignacion : conversion_explicita ASIG expresion",
"asignacion_multiple : lista_variables ASIG_MULTIPLE lado_derecho_multiple",
"asignacion_multiple_warning : lista_strict_multiple ASIG lado_derecho_multiple",
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
"declaracion_ilegal : VAR variable ASIG expresion fin_sentencia",
"declaracion_ilegal : VAR variable fin_sentencia",
"constante : CTE",
"constante : '-' CTE",
"if_encabezado : IF '(' condicion ')'",
"if_encabezado : IF '(' error ')'",
"inicio_else : ELSE",
"condicional_if : if_encabezado bloque_ejecutable ENDIF ';'",
"condicional_if : if_encabezado bloque_ejecutable inicio_else bloque_ejecutable ENDIF ';'",
"condicional_if : if_encabezado bloque_ejecutable ENDIF error",
"condicional_if : if_encabezado bloque_ejecutable inicio_else bloque_ejecutable ENDIF error",
"condicional_if : if_encabezado bloque_ejecutable error",
"$$9 :",
"condicional_do_while : DO $$9 bloque_ejecutable WHILE '(' condicion ')' fin_sentencia",
"condicion : expresion simbolo_comparacion expresion",
"simbolo_comparacion : MAYOR_IGUAL",
"simbolo_comparacion : MENOR_IGUAL",
"simbolo_comparacion : DISTINTO",
"simbolo_comparacion : IGUAL_IGUAL",
"simbolo_comparacion : '>'",
"simbolo_comparacion : '<'",
"simbolo_comparacion : ASIG",
"$$10 :",
"bloque_ejecutable : '{' $$10 sentencias_ejecutables_lista '}'",
"$$11 :",
"bloque_ejecutable : '{' $$11 '}'",
"bloque_ejecutable : '{' error '}'",
"salida_pantalla : PRINT '(' CADENA_MULTILINEA ')'",
"salida_pantalla : PRINT '(' expresion ')'",
"$$12 :",
"retorno_funcion : RETURN '(' $$12 lista_expresiones ')' fin_sentencia",
"lista_expresiones : lista_expresiones ',' expresion",
"lista_expresiones : expresion",
};

//#line 1053 "gramatica.y"

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

/* METODO AUXILIAR PARA LA LOGICA DE ASIGNACION MULTIPLE */
private void procesarAsignacionMultiple(int linea) {
    String lineaActual = String.valueOf(linea);
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
                /* CORRECCION WARNIGNS TEMA 21 */
                if (cantRetornos != cantIzquierda) {
                     al.agregarWarning("Linea " + lineaActual + ": Warning (Tema 21): Funcion '" + funcName + "' retorna " + cantRetornos + " valores, pero se esperan " + cantIzquierda + ". Se asignaran los " + Math.min(cantRetornos, cantIzquierda) + " posibles.");
                }

                int minAsignaciones = Math.min(cantIzquierda, cantRetornos);
                for (int i = 0; i < minAsignaciones; i++) {
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
        /* AQUI ESTA EL CAMBIO: TEMA 19 AHORA ES ERROR */
        if (cantIzquierda != cantDerecha) {
            al.agregarErrorSemantico("Linea " + lineaActual + ": Error Semantico (Tema 19): Discrepancia en asignacion multiple. Izquierda: " + cantIzquierda + ", Derecha: " + cantDerecha + ".");
        }

        int minAsignaciones = Math.min(cantIzquierda, cantDerecha);
        for (int i = 0; i < minAsignaciones; i++) {
            String var = listaVariables.get(i);
            String expr = derechos.get(i);
            String tipoVar = g.getTipo(var);
            String tipoExpr = g.getTipo(expr);
            if (g.chequearAsignacion(tipoVar, tipoExpr, Integer.parseInt(lineaActual))) {
                g.addTerceto(":=", var, expr);
            }
        }
        salida.add("Linea " + lineaActual + ": Asignacion multiple (lista) reconocida.");
    }
    contadorLadoDerecho = 0;
    listaVariables.clear();
    g.clearLadoDerecho();
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

        // --- AGREGADO PARA IMPRIMIR ERRORES LEXICOS ---
        System.out.println("\n=======================================================");
        System.out.println("## ERRORES LEXICOS DETECTADOS ##");
        System.out.println("=======================================================");
        if (al.getErrores().isEmpty()) {
            System.out.println("No se encontraron errores lexicos.");
        } else {
            for (String s : al.getErrores()) {
                System.out.println(s);
            }
        }
        // ----------------------------------------------

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
//#line 740 "Parser.java"
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
case 7:
//#line 44 "gramatica.y"
{
              }
break;
case 8:
//#line 47 "gramatica.y"
{
                  /* Usa getLineaAnterior() para reportar la linea donde falto el ; */
                  al.agregarWarning("Linea " + al.getLineaAnterior() + ": Warning Sintactico: Falta punto y coma al final de la sentencia. Se asume ';' y se continua.");
              }
break;
case 16:
//#line 71 "gramatica.y"
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
case 17:
//#line 89 "gramatica.y"
{
                    erroresSintacticos.add("Linea " + al.getFilaToken() + ": Error Sintactico: Operador de asignacion incorrecto. Se encontro ':' pero se esperaba ':='.");
                }
break;
case 18:
//#line 94 "gramatica.y"
{ yyval.sval = "uint"; }
break;
case 19:
//#line 95 "gramatica.y"
{ yyval.sval = "float"; }
break;
case 20:
//#line 96 "gramatica.y"
{ yyval.sval = "lambda"; }
break;
case 21:
//#line 101 "gramatica.y"
{
                    listaVariables.clear();
                    listaVariables.add(val_peek(0).sval);
                }
break;
case 23:
//#line 110 "gramatica.y"
{
                          listaVariables.add(val_peek(0).sval);
                      }
break;
case 24:
//#line 115 "gramatica.y"
{
                          listaVariables.clear();
                          listaVariables.add(val_peek(2).sval);
                          listaVariables.add(val_peek(0).sval);
                      }
break;
case 25:
//#line 122 "gramatica.y"
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
case 26:
//#line 163 "gramatica.y"
{
            g.cerrarAmbito();
            g.setGeneracionHabilitada(true);

            pilaTiposRetorno.pop();
            boolean huboError = pilaErrorEnFuncion.pop();
            boolean huboReturn = pilaHuboRetorno.pop();
            int inicioFunc = pilaInicioFuncion.pop();
            int jumpIdx = pilaSaltosFunciones.pop();
            if (!huboReturn) {
                 al.agregarErrorSemantico("Linea " + val_peek(0).ival + ": Error Semantico: La funcion '" + val_peek(7).sval + "' debe retornar un valor de tipo " + val_peek(8).sval + ".");
                 huboError = true;
            }

            if (huboError) {
                al.eliminarLexemaTS(val_peek(7).sval);
                g.anularTercetosDesde(inicioFunc);
                al.eliminarUltimoAmbito();
            } else {
                g.modificarSaltoTerceto(jumpIdx, "[" + g.getProximoTerceto() + "]");
                salida.add("Linea " + val_peek(7).ival + ": Declaracion de Funcion '" + val_peek(7).sval + "' con retorno simple.");
            }
        }
break;
case 27:
//#line 187 "gramatica.y"
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
case 28:
//#line 231 "gramatica.y"
{
            g.cerrarAmbito();
            g.setGeneracionHabilitada(true);

            pilaTiposRetorno.pop();
            boolean huboError = pilaErrorEnFuncion.pop();
            boolean huboReturn = pilaHuboRetorno.pop();
            int inicioFunc = pilaInicioFuncion.pop();
            int jumpIdx = pilaSaltosFunciones.pop();
            if (!huboReturn) {
                 al.agregarErrorSemantico("Linea " + val_peek(0).ival + ": Error Semantico: La funcion '" + val_peek(7).sval + "' tiene retorno multiple y debe retornar valores.");
                 huboError = true;
            }

            if (huboError) {
                al.eliminarLexemaTS(val_peek(7).sval);
                g.anularTercetosDesde(inicioFunc);
                al.eliminarUltimoAmbito();
            } else {
                g.modificarSaltoTerceto(jumpIdx, "[" + g.getProximoTerceto() + "]");
                salida.add("Linea " + val_peek(7).ival + ": Declaracion de Funcion '" + val_peek(7).sval + "' con retorno multiple.");
            }
        }
break;
case 29:
//#line 257 "gramatica.y"
{
                                 ArrayList<String> lista = new ArrayList<String>();
                                 lista.add(val_peek(2).sval);
                                 lista.add(val_peek(0).sval);
                                 yyval.obj = lista;
                             }
break;
case 30:
//#line 265 "gramatica.y"
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
case 33:
//#line 282 "gramatica.y"
{
                 g.apilarParametro(new ParametroInfo(val_peek(0).sval, val_peek(1).sval, val_peek(2).sval));
             }
break;
case 34:
//#line 287 "gramatica.y"
{
                 String pasajeDefault = "default_cv";
                 g.apilarParametro(new ParametroInfo(val_peek(0).sval, val_peek(1).sval, pasajeDefault));
             }
break;
case 35:
//#line 294 "gramatica.y"
{
                 /* Generamos un warning en lugar de error sintactico */
                 al.agregarWarning("Linea " + val_peek(0).ival + ": Warning: Falta el tipo del parametro '" + val_peek(0).sval + "'. Se asume 'uint' por defecto y se continua.");

                 /* Asumimos 'uint' para poder continuar la compilacion y evitar errores de variable no declarada */
                 g.apilarParametro(new ParametroInfo(val_peek(0).sval, "uint", val_peek(1).sval));
             }
break;
case 36:
//#line 303 "gramatica.y"
{ yyval.sval = "cr_se"; }
break;
case 37:
//#line 305 "gramatica.y"
{ yyval.sval = "cr_le"; }
break;
case 46:
//#line 327 "gramatica.y"
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
                   /* ... (resto de tu lógica existente para asignación válida) ...*/
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
case 47:
//#line 393 "gramatica.y"
{
               /* REGLA AGREGADA PARA DETECTAR ERROR DE ASIGNACION A TOUI */
               erroresSintacticos.add("Linea " + val_peek(2).ival + ": Error de sintaxis: Intento de asignacion invalido. No se puede asignar un valor al resultado de una conversion 'toui'. Se espera una variable a la izquierda.");
           }
break;
case 48:
//#line 400 "gramatica.y"
{
    procesarAsignacionMultiple(val_peek(1).ival);
}
break;
case 49:
//#line 408 "gramatica.y"
{
    al.agregarWarning("Linea " + val_peek(1).ival + ": Warning: El operador ':=' es para asignaciones simples. Para asignaciones multiples se espera '='.");
    procesarAsignacionMultiple(val_peek(1).ival);
}
break;
case 50:
//#line 414 "gramatica.y"
{ g.clearLadoDerecho();
                          }
break;
case 51:
//#line 416 "gramatica.y"
{
                              g.apilarLadoDerecho(g.desapilarOperando());
                              contadorLadoDerecho = 1;
                              yyval.ival = val_peek(0).ival;
                              yyval.sval = val_peek(0).sval;
                          }
break;
case 52:
//#line 424 "gramatica.y"
{
                              g.apilarLadoDerecho(g.desapilarOperando());
                              contadorLadoDerecho++;
                              yyval.ival = 0;
                          }
break;
case 53:
//#line 432 "gramatica.y"
{
                yyval.sval = val_peek(2).sval + "." + val_peek(0).sval;
                yyval.ival = val_peek(2).ival;
            }
break;
case 54:
//#line 438 "gramatica.y"
{
                yyval.sval = val_peek(0).sval;
                yyval.ival = val_peek(0).ival;
            }
break;
case 55:
//#line 445 "gramatica.y"
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
case 56:
//#line 456 "gramatica.y"
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
case 57:
//#line 466 "gramatica.y"
{ yyval.ival = val_peek(0).ival; }
break;
case 58:
//#line 470 "gramatica.y"
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
case 59:
//#line 481 "gramatica.y"
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
case 60:
//#line 490 "gramatica.y"
{ yyval.ival = val_peek(0).ival; }
break;
case 61:
//#line 494 "gramatica.y"
{
           yyval.ival = val_peek(0).ival;
       }
break;
case 62:
//#line 498 "gramatica.y"
{
           yyval.ival = val_peek(0).ival;
           yyval.sval = val_peek(0).sval;
           g.apilarOperando(val_peek(0).sval);
       }
break;
case 63:
//#line 506 "gramatica.y"
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
case 64:
//#line 530 "gramatica.y"
{
                      g.apilarOperando(val_peek(0).sval);
                      yyval.ival = val_peek(0).ival;
                  }
break;
case 65:
//#line 536 "gramatica.y"
{ yyval.ival = val_peek(0).ival; }
break;
case 66:
//#line 540 "gramatica.y"
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
case 67:
//#line 559 "gramatica.y"
{ g.clearParametrosReales(); }
break;
case 68:
//#line 563 "gramatica.y"
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
                                       } else if (!tipoFormal.equals("lambda") && tipoReal.equals("lambda_expr")) {
                                           al.agregarErrorSemantico("Linea " + linea + ": Error Semantico (Tema 28): Se paso una expresion lambda al parametro '->" + real.nombreFormal + "'.");
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
                                          ParametroInfo formal = formales.stream().filter(f -> f.nombre.equals(real.nombreFormal)).findFirst().orElse(null);
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
case 69:
//#line 673 "gramatica.y"
{
                            yyval.ival = val_peek(2).ival + 1;
                        }
break;
case 70:
//#line 678 "gramatica.y"
{
                            yyval.ival = 1;
                        }
break;
case 71:
//#line 684 "gramatica.y"
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
case 72:
//#line 696 "gramatica.y"
{
                   g.apilarParametroReal(new ParametroRealInfo(val_peek(0).sval, null));
                   yyval.ival = val_peek(0).ival;
               }
break;
case 73:
//#line 703 "gramatica.y"
{
                     yyval.sval = g.desapilarOperando();
                     yyval.ival = val_peek(0).ival;
                 }
break;
case 74:
//#line 709 "gramatica.y"
{
                     yyval.sval = val_peek(0).sval;
                     yyval.ival = val_peek(0).ival;
                 }
break;
case 75:
//#line 716 "gramatica.y"
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
case 76:
//#line 729 "gramatica.y"
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
case 83:
//#line 761 "gramatica.y"
{
                        erroresSintacticos.add("Linea " + val_peek(3).ival + ": Error sintactico: No se permiten declaraciones en bloques ejecutables.");
                   }
break;
case 84:
//#line 765 "gramatica.y"
{
                        erroresSintacticos.add("Linea " + val_peek(1).ival + ": Error sintactico: No se permiten declaraciones en bloques ejecutables.");
                   }
break;
case 85:
//#line 771 "gramatica.y"
{
                yyval.sval = val_peek(0).sval;
                yyval.ival = val_peek(0).ival;
            }
break;
case 86:
//#line 777 "gramatica.y"
{
                String lexemaPositivo = val_peek(0).sval;
                String lexemaNegativo = "-" + lexemaPositivo;
                String resultado = lexemaNegativo;
                if (al.getAtributo(lexemaPositivo, "Tipo") != null) {
                    String tipo = (String) al.getAtributo(lexemaPositivo, "Tipo");
                    if (tipo != null) {
                        if (tipo.equals("uint")) {
                            al.agregarWarning("Linea " + val_peek(0).ival + ": Warning. El tipo 'uint' no admite valores negativos. Se elimino el signo menos y se usa: " + lexemaPositivo);
                            resultado = lexemaPositivo;
                        } else if (tipo.equals("float")) {
                            al.reemplazarEnTS(lexemaPositivo, lexemaNegativo);
                        }
                    }
                }
                yyval.sval = resultado;
                yyval.ival = val_peek(0).ival;
            }
break;
case 87:
//#line 797 "gramatica.y"
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
case 88:
//#line 809 "gramatica.y"
{
                    erroresSintacticos.add("Linea " + al.getFilaToken() + ": Error sintactico: Falta la condicion del IF o es invalida.");
                    g.apilarControl(-1);
                    yyval.ival = val_peek(3).ival;
               }
break;
case 89:
//#line 817 "gramatica.y"
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
case 90:
//#line 830 "gramatica.y"
{
                   int bfIdx = g.desapilarControl();
                   if (bfIdx != -1) {
                       int finIf = g.getProximoTerceto();
                       g.modificarSaltoTerceto(bfIdx, "[" + finIf + "]");
                   }
                   salida.add("Linea " + val_peek(3).ival + ": Sentencia IF reconocida.");
               }
break;
case 91:
//#line 840 "gramatica.y"
{
                   int biIdx = g.desapilarControl();
                   int finElse = g.getProximoTerceto();
                   g.modificarSaltoTerceto(biIdx, "[" + finElse + "]");

                   salida.add("Linea " + val_peek(5).ival + ": Sentencia IF-ELSE reconocida.");
               }
break;
case 92:
//#line 849 "gramatica.y"
{
                   al.agregarWarning("Linea " + al.getFilaToken() + ": Warning: Falta punto y coma despues de 'endif'.");

                   /* Limpiar error generico si existe */
                   if (!erroresSintacticos.isEmpty()) {
                        String ultimoError = erroresSintacticos.get(erroresSintacticos.size() - 1);
                        if (ultimoError.contains("Error de sintaxis. Verifique la estructura")) {
                            erroresSintacticos.remove(erroresSintacticos.size() - 1);
                        }
                   }

                   try {
                       int bfIdx = g.desapilarControl();
                       if (bfIdx != -1) {
                           int finIf = g.getProximoTerceto();
                           g.modificarSaltoTerceto(bfIdx, "[" + finIf + "]");
                       }
                   } catch (Exception e) { }
               }
break;
case 93:
//#line 870 "gramatica.y"
{
                   al.agregarWarning("Linea " + al.getFilaToken() + ": Warning: Falta punto y coma despues de 'endif'.");

                   /* Limpiar error generico si existe */
                   if (!erroresSintacticos.isEmpty()) {
                        String ultimoError = erroresSintacticos.get(erroresSintacticos.size() - 1);
                        if (ultimoError.contains("Error de sintaxis. Verifique la estructura")) {
                            erroresSintacticos.remove(erroresSintacticos.size() - 1);
                        }
                   }

                   try {
                       int biIdx = g.desapilarControl();
                       int finElse = g.getProximoTerceto();
                       g.modificarSaltoTerceto(biIdx, "[" + finElse + "]");
                   } catch (Exception e) { }
               }
break;
case 94:
//#line 890 "gramatica.y"
{
                   if (!erroresSintacticos.isEmpty()) {
                       String ultimoError = erroresSintacticos.get(erroresSintacticos.size() - 1);
                       if (ultimoError.contains("Error de sintaxis. Verifique la estructura")) {
                           erroresSintacticos.remove(erroresSintacticos.size() - 1);
                       }
                   }

                   al.agregarWarning("Linea " + al.getFilaToken() + ": Warning: Falta la palabra reservada 'endif' al final del bloque if. Se asume cierre del bloque.");

                   try {
                       int bfIdx = g.desapilarControl();
                       if (bfIdx != -1) {
                           int finIf = g.getProximoTerceto();
                           g.modificarSaltoTerceto(bfIdx, "[" + finIf + "]");
                       }
                   } catch (Exception e) { }
               }
break;
case 95:
//#line 911 "gramatica.y"
{
                        g.apilarControl(g.getProximoTerceto());
                    }
break;
case 96:
//#line 915 "gramatica.y"
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
case 97:
//#line 929 "gramatica.y"
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
case 98:
//#line 944 "gramatica.y"
{ g.apilarOperando(">="); }
break;
case 99:
//#line 946 "gramatica.y"
{ g.apilarOperando("<="); }
break;
case 100:
//#line 948 "gramatica.y"
{ g.apilarOperando("=!"); }
break;
case 101:
//#line 950 "gramatica.y"
{ g.apilarOperando("=="); }
break;
case 102:
//#line 952 "gramatica.y"
{ g.apilarOperando(">"); }
break;
case 103:
//#line 954 "gramatica.y"
{ g.apilarOperando("<"); }
break;
case 104:
//#line 956 "gramatica.y"
{
                        al.agregarWarning("Linea " + val_peek(0).ival + ": Warning: Se utilizo ':=' en una condicion. Se asume comparacion '=='.");
                        g.apilarOperando("==");
                    }
break;
case 105:
//#line 962 "gramatica.y"
{ g.abrirAmbito("bloque_" + g.getProximoTerceto()); }
break;
case 106:
//#line 962 "gramatica.y"
{ g.cerrarAmbito();}
break;
case 107:
//#line 964 "gramatica.y"
{ g.abrirAmbito("bloque_" + g.getProximoTerceto()); }
break;
case 108:
//#line 964 "gramatica.y"
{ g.cerrarAmbito(); }
break;
case 110:
//#line 971 "gramatica.y"
{
                    salida.add("Linea " + val_peek(3).ival + ": PRINT con cadena multilinea.");
                    g.addTerceto("PRINT", val_peek(1).sval);
                }
break;
case 111:
//#line 977 "gramatica.y"
{
                    salida.add("Linea " + val_peek(3).ival + ": PRINT con expresion.");
                    g.addTerceto("PRINT", g.desapilarOperando());
                }
break;
case 112:
//#line 984 "gramatica.y"
{ enSentenciaReturn = true; }
break;
case 113:
//#line 985 "gramatica.y"
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
case 114:
//#line 1034 "gramatica.y"
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
case 115:
//#line 1045 "gramatica.y"
{
                  ArrayList<String> lista = new ArrayList<String>();
                  lista.add(g.desapilarOperando());
                  yyval.obj = lista;
              }
break;
//#line 1963 "Parser.java"
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
