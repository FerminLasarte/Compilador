.data
ErrorDivCero db "Error: Division por cero", 0
ErrorOverflow db "Error: Overflow en operacion", 0
ErrorRestaNegativa db "Error: Resultado negativo en resta de enteros sin signo", 0
MensajePrint db "Salida: %s", 10, 0
MensajePrintNum db "Salida: %d", 10, 0
MensajePrintFloat db "Salida: %f", 10, 0
@aux0 dd 0
@aux1 dd 0
@aux2 dd 0
@aux3 dd 0
@aux4 dd 0
@aux5 dd 0
@aux6 dd 0
@aux7 dd 0
@aux8 dd 0
@aux9 dd 0
@aux10 dd 0
@aux11 dd 0
@aux12 dd 0
@aux13 dd 0
@aux14 dd 0
@aux15 dd 0
@aux16 dd 0
@aux17 dd 0
@aux18 dd 0
@aux19 dd 0
@aux20 dd 0
@aux21 dd 0
@aux22 dd 0
@aux23 dd 0
@aux24 dd 0
@aux25 dd 0
@aux26 dd 0
@aux27 dd 0
@aux28 dd 0
@aux29 dd 0
@aux30 dd 0
@aux31 dd 0
@aux32 dd 0
@aux33 dd 0
@aux34 dd 0
@aux35 dd 0
@aux36 dd 0
@aux37 dd 0
@aux38 dd 0
@aux39 dd 0
@aux40 dd 0
@aux41 dd 0
@aux42 dd 0
@aux43 dd 0
@aux44 dd 0
@aux45 dd 0
@aux46 dd 0
@aux47 dd 0
@aux48 dd 0
@aux49 dd 0
@aux50 dd 0
@aux51 dd 0
@aux52 dd 0
@aux53 dd 0
@aux54 dd 0
@aux55 dd 0
@aux56 dd 0
@aux57 dd 0
@aux58 dd 0
@aux59 dd 0
@aux60 dd 0
@aux61 dd 0
@aux62 dd 0
@aux63 dd 0
@aux64 dd 0
@aux65 dd 0
@aux66 dd 0
@aux67 dd 0
@aux68 dd 0
@aux69 dd 0
@aux70 dd 0
@aux71 dd 0
@aux72 dd 0
@aux73 dd 0
@aux74 dd 0
@aux75 dd 0
@aux76 dd 0
@aux77 dd 0
@aux78 dd 0
@aux79 dd 0
@aux80 dd 0
@aux81 dd 0
@aux82 dd 0
@aux83 dd 0
@aux84 dd 0
@aux85 dd 0
@aux86 dd 0
@aux87 dd 0
@aux88 dd 0
@aux89 dd 0
@aux90 dd 0
@aux91 dd 0
@aux92 dd 0
@aux93 dd 0
@aux94 dd 0
@aux95 dd 0
@aux96 dd 0
@aux97 dd 0
@aux98 dd 0
@aux99 dd 0
@aux100 dd 0
@aux101 dd 0
@aux102 dd 0
@aux103 dd 0
@aux104 dd 0
@aux105 dd 0
@aux106 dd 0
@aux107 dd 0
@aux108 dd 0
@aux109 dd 0
@aux110 dd 0
@aux111 dd 0
@aux112 dd 0
@aux113 dd 0
@aux114 dd 0
@aux115 dd 0
@aux116 dd 0
@aux117 dd 0
@aux118 dd 0
@aux119 dd 0
@aux120 dd 0
@aux121 dd 0
@aux122 dd 0
@aux123 dd 0
_A dd 0
_B dd 0
_C dd 0
_J dd 0
_FUNCR dd 0
_Z dd 0
_W dd 0
_Y dd 0
_GCONTADOR dd 0
_GCONTADOR1 dd 0
_GFACTOR dd 0
_X dd 0
_VARA dd 0
_VARF dd 0
_PLE dd 0
_PSE dd 0
_S dd 0
_FUNCA dd 0
_EVARBLOQUE dd 0
_AA dd 0
_SS dd 0
_XX dd 0
_F dd 0
_UOP1 dd 0
_UOP2 dd 0
_URESSUB dd 0
_URESADD dd 0
_FOP1 dd 0
_FOP2 dd 0
_FRESPROD dd 0
str_85 db " PRINT GCONTADOR ", 0
str_92 db " PRINT AA ", 0
str_96 db " PRINT A ", 0
str_99 db " PRINT MAIN.A ", 0
str_108 db " PRINT AA ", 0
str_113 db "
    Inicio de cadena
    Linea intermedia
    Fin de cadena
    ", 0
.386
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
.code
start:
Label0:
MOV EAX, 1
MOV _A, EAX
Label1:
MOV EAX, 2
MOV _B, EAX
Label2:
MOV EAX, 3
MOV _C, EAX
Label3:
MOV EAX, 30
MOV _C, EAX
Label4:
MOV EAX, 20
MOV _B, EAX
Label5:
MOV EAX, 10
MOV _A, EAX
Label6:
MOV EAX, 0
MOV _J, EAX
Label7:
MOV EAX, 1
MOV _J, EAX
Label8:
RET
Label9:
PUSH 11
Label10:
CALL _FUNCR
MOV @aux10, EAX
Label11:
MOV EAX, 20
MOV _B, EAX
Label12:
MOV EAX, @aux10
MOV _A, EAX
Label13:
PUSH 11
Label14:
CALL _FUNCR
MOV @aux14, EAX
Label15:
MOV EAX, 20
MOV _B, EAX
Label16:
MOV EAX, @aux14
MOV _A, EAX
Label17:
MOV EAX, 1
MOV _Z, EAX
Label18:
MOV EAX, 2
MOV _W, EAX
Label19:
MOV EAX, 3
MOV _Y, EAX
Label20:
MOV EAX, 4
MOV _A, EAX
Label21:
MOV EAX, _MAIN_A
MOV _W, EAX
Label22:
MOV EAX, _W
MOV _MAIN_A, EAX
Label23:
MOV EAX, 22
MOV _W, EAX
Label24:
MOV EAX, 33
MOV _Y, EAX
Label25:
MOV EAX, 44
MOV _A, EAX
Label26:
MOV EAX, _MAIN_A
MOV _W, EAX
Label27:
MOV EAX, _W
MOV _FUNCZ_A, EAX
Label28:
RET
Label29:
RET
Label30:
MOV EAX, 0
MOV _GCONTADOR, EAX
Label31:
MOV EAX, 1
MOV _GCONTADOR1, EAX
Label32:
MOV EAX, 1.0F+0
MOV _GFACTOR, EAX
Label33:
MOV EAX, _GCONTADOR
ADD EAX, _GCONTADOR1
MOV @aux33, EAX
Label34:
MOV EAX, @aux33
MOV _X, EAX
Label35:
FLD _GFACTOR
FISTP @aux35
Label36:
MOV EAX, _GCONTADOR
ADD EAX, @aux35
MOV @aux36, EAX
Label37:
MOV EAX, @aux36
MOV _X, EAX
Label38:
MOV EAX, 1
MOV _VARA, EAX
Label39:
MOV EAX, 1.0F+0
MOV _VARF, EAX
Label40:
FLD _VARF
FISTP @aux40
Label41:
MOV EAX, @aux40
MOV _VARA, EAX
Label42:
MOV EAX, _PLE
ADD EAX, 1
MOV @aux42, EAX
Label43:
MOV EAX, @aux42
MOV _PLE, EAX
Label44:
MOV EAX, 100
MOV _PSE, EAX
Label45:
MOV EAX, 100
MOV _PLE, EAX
Label46:
RET
Label47:
RET
Label48:
MOV EAX, _PLE
ADD EAX, 1
MOV @aux48, EAX
Label49:
MOV EAX, @aux48
MOV _PLE, EAX
Label50:
MOV EAX, 100
MOV _PSE, EAX
Label51:
MOV EAX, 100
MOV _PLE, EAX
Label52:
RET
Label53:
RET
Label54:
MOV EAX, 0
MOV _S, EAX
Label55:
RET
Label56:
RET
Label57:
RET
Label58:
PUSH 1
Label59:
CALL _FUNCA
MOV @aux59, EAX
Label60:
Label61:
MOV EAX, @aux60
MOV _A, EAX
Label62:
Label63:
MOV EAX, @aux62
MOV _B, EAX
Label64:
Label65:
MOV EAX, @aux64
MOV _C, EAX
Label66:
PUSH 1
Label67:
CALL _FUNCA
MOV @aux67, EAX
Label68:
Label69:
MOV EAX, @aux68
MOV _A, EAX
Label70:
Label71:
MOV EAX, @aux70
MOV _B, EAX
Label72:
MOV EAX, 0
MOV _VARA, EAX
Label73:
MOV EAX, _VARA
CMP EAX, 0
MOV @aux73, 0
Label74:
MOV EAX, @aux73
CMP EAX, 1
JE @aux72
Label75:
MOV EAX, 1
MOV _EVARBLOQUE, EAX
Label76:
MOV EAX, 1
MOV _EVARBLOQUE, EAX
Label77:
MOV EAX, _VARA
CMP EAX, 0
MOV @aux77, 0
Label78:
MOV EAX, @aux77
CMP EAX, 1
JE @aux76
Label79:
MOV EAX, 1
MOV _EVARBLOQUE, EAX
Label80:
MOV EAX, _EVARBLOQUE
CMP EAX, 0
MOV @aux80, 0
Label81:
MOV EAX, @aux80
CMP EAX, 1
JE @aux79
Label82:
MOV EAX, 3
MOV _AA, EAX
Label83:
MOV EAX, 3
MOV _A, EAX
Label84:
MOV EAX, 0
MOV _GCONTADOR, EAX
Label85:
invoke MessageBox, NULL, addr str_85, addr MensajePrint, MB_OK
Label86:
invoke MessageBox, NULL, addr _MAIN_GCONTADOR, addr MensajePrintNum, MB_OK
Label87:
MOV EAX, _GCONTADOR
ADD EAX, 1
MOV @aux87, EAX
Label88:
MOV EAX, @aux87
MOV _MAIN_GCONTADOR, EAX
Label89:
MOV EAX, _MAIN_GCONTADOR
CMP EAX, 3
MOV @aux89, 0
Label90:
MOV EAX, @aux89
CMP EAX, 1
JE @aux85
Label91:
MOV EAX, _AA
CMP EAX, 5
MOV @aux91, 0
Label92:
invoke MessageBox, NULL, addr str_92, addr MensajePrint, MB_OK
Label93:
invoke MessageBox, NULL, addr _MAIN_A, addr MensajePrintNum, MB_OK
Label94:
invoke MessageBox, NULL, addr 100, addr MensajePrintNum, MB_OK
Label95:
MOV EAX, _A
CMP EAX, 5
MOV @aux95, 0
Label96:
invoke MessageBox, NULL, addr str_96, addr MensajePrint, MB_OK
Label97:
invoke MessageBox, NULL, addr _A, addr MensajePrintNum, MB_OK
Label98:
MOV EAX, _MAIN_A
CMP EAX, 5
MOV @aux98, 0
Label99:
invoke MessageBox, NULL, addr str_99, addr MensajePrint, MB_OK
Label100:
invoke MessageBox, NULL, addr _MAIN_A, addr MensajePrintNum, MB_OK
Label101:
invoke MessageBox, NULL, addr 100, addr MensajePrintNum, MB_OK
Label102:
RET
Label103:
Label104:
MOV EAX, _X
CALL EAX
Label105:
JMP __
Label106:
Label107:
MOV EAX, _XX
CMP EAX, 1
MOV @aux107, 0
Label108:
invoke MessageBox, NULL, addr str_108, addr MensajePrint, MB_OK
Label109:
RET
Label110:
PUSH _L106
Label111:
PUSH 3
Label112:
CALL _F
MOV @aux112, EAX
Label113:
invoke MessageBox, NULL, addr str_113, addr MensajePrint, MB_OK
Label114:
MOV EAX, 50
MOV _UOP1, EAX
Label115:
MOV EAX, 20
MOV _UOP2, EAX
Label116:
MOV EAX, _UOP1
SUB EAX, _UOP2
MOV @aux116, EAX
Label117:
MOV EAX, @aux116
MOV _URESSUB, EAX
Label118:
MOV EAX, _UOP1
ADD EAX, _UOP2
MOV @aux118, EAX
Label119:
MOV EAX, @aux118
MOV _URESADD, EAX
Label120:
MOV EAX, 2.0F+10
MOV _FOP1, EAX
Label121:
MOV EAX, 3.0F+5
MOV _FOP2, EAX
Label122:
MOV EAX, _FOP1
MUL _FOP2
MOV @aux122, EAX
Label123:
MOV EAX, @aux122
MOV _FRESPROD, EAX
invoke ExitProcess, 0
Error_DivCero:
invoke MessageBox, NULL, addr ErrorDivCero, addr ErrorDivCero, MB_OK
invoke ExitProcess, 1
end start
