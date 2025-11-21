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
_AA dd 0
_SS dd 0
_XX dd 0
_F dd 0
str_70 db " PRINT GCONTADOR ", 0
str_77 db " PRINT AA ", 0
str_81 db " PRINT A ", 0
str_84 db " PRINT MAIN.A ", 0
str_93 db " PRINT AA ", 0
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
MOV EAX, 20
MOV _B, EAX
Label14:
MOV EAX, 1
MOV _Z, EAX
Label15:
MOV EAX, 2
MOV _W, EAX
Label16:
MOV EAX, 3
MOV _Y, EAX
Label17:
MOV EAX, 4
MOV _A, EAX
Label18:
MOV EAX, _MAIN_A
MOV _W, EAX
Label19:
MOV EAX, _W
MOV _MAIN_A, EAX
Label20:
MOV EAX, 22
MOV _W, EAX
Label21:
MOV EAX, 33
MOV _Y, EAX
Label22:
MOV EAX, 44
MOV _A, EAX
Label23:
MOV EAX, _MAIN_A
MOV _W, EAX
Label24:
MOV EAX, _W
MOV _FUNCZ_A, EAX
Label25:
RET
Label26:
RET
Label27:
MOV EAX, 0
MOV _GCONTADOR, EAX
Label28:
MOV EAX, 1
MOV _GCONTADOR1, EAX
Label29:
MOV EAX, 1.0F+0
MOV _GFACTOR, EAX
Label30:
MOV EAX, _GCONTADOR
ADD EAX, _GCONTADOR1
MOV @aux30, EAX
Label31:
MOV EAX, @aux30
MOV _X, EAX
Label32:
MOV EAX, _GCONTADOR
ADD EAX, _GFACTOR
MOV @aux32, EAX
Label33:
MOV EAX, 1
MOV _VARA, EAX
Label34:
MOV EAX, 1.0F+0
MOV _VARF, EAX
Label35:
FLD _VARF
FISTP @aux35
Label36:
MOV EAX, @aux35
MOV _VARA, EAX
Label37:
MOV EAX, _PLE
ADD EAX, 1
MOV @aux37, EAX
Label38:
MOV EAX, @aux37
MOV _PLE, EAX
Label39:
MOV EAX, 100
MOV _PSE, EAX
Label40:
RET
Label41:
RET
Label42:
MOV EAX, 0
MOV _S, EAX
Label43:
RET
Label44:
RET
Label45:
RET
Label46:
PUSH 1
Label47:
CALL _FUNCA
MOV @aux47, EAX
Label48:
Label49:
MOV EAX, @aux48
MOV _A, EAX
Label50:
Label51:
MOV EAX, @aux50
MOV _B, EAX
Label52:
Label53:
MOV EAX, @aux52
MOV _C, EAX
Label54:
PUSH 1
Label55:
CALL _FUNCA
MOV @aux55, EAX
Label56:
Label57:
MOV EAX, @aux56
MOV _A, EAX
Label58:
Label59:
MOV EAX, @aux58
MOV _B, EAX
Label60:
PUSH 1
Label61:
CALL _FUNCA
MOV @aux61, EAX
Label62:
MOV EAX, 0
MOV _VARA, EAX
Label63:
MOV EAX, _VARA
CMP EAX, 0
MOV @aux63, 0
Label64:
MOV EAX, @aux63
CMP EAX, 1
JE @aux62
Label65:
MOV EAX, _VARA
CMP EAX, 0
MOV @aux65, 0
Label66:
MOV EAX, @aux65
CMP EAX, 1
JE @aux65
Label67:
MOV EAX, 3
MOV _AA, EAX
Label68:
MOV EAX, 3
MOV _A, EAX
Label69:
MOV EAX, 0
MOV _GCONTADOR, EAX
Label70:
invoke MessageBox, NULL, addr str_70, addr MensajePrint, MB_OK
Label71:
invoke MessageBox, NULL, addr _MAIN_GCONTADOR, addr MensajePrintNum, MB_OK
Label72:
MOV EAX, _GCONTADOR
ADD EAX, 1
MOV @aux72, EAX
Label73:
MOV EAX, @aux72
MOV _MAIN_GCONTADOR, EAX
Label74:
MOV EAX, _MAIN_GCONTADOR
CMP EAX, 3
MOV @aux74, 0
Label75:
MOV EAX, @aux74
CMP EAX, 1
JE @aux70
Label76:
MOV EAX, _AA
CMP EAX, 5
MOV @aux76, 0
Label77:
invoke MessageBox, NULL, addr str_77, addr MensajePrint, MB_OK
Label78:
invoke MessageBox, NULL, addr _MAIN_A, addr MensajePrintNum, MB_OK
Label79:
invoke MessageBox, NULL, addr 100, addr MensajePrintNum, MB_OK
Label80:
MOV EAX, _A
CMP EAX, 5
MOV @aux80, 0
Label81:
invoke MessageBox, NULL, addr str_81, addr MensajePrint, MB_OK
Label82:
invoke MessageBox, NULL, addr _A, addr MensajePrintNum, MB_OK
Label83:
MOV EAX, _MAIN_A
CMP EAX, 5
MOV @aux83, 0
Label84:
invoke MessageBox, NULL, addr str_84, addr MensajePrint, MB_OK
Label85:
invoke MessageBox, NULL, addr _MAIN_A, addr MensajePrintNum, MB_OK
Label86:
invoke MessageBox, NULL, addr 100, addr MensajePrintNum, MB_OK
Label87:
RET
Label88:
Label89:
MOV EAX, _X
CALL EAX
Label90:
JMP __
Label91:
Label92:
MOV EAX, _XX
CMP EAX, 1
MOV @aux92, 0
Label93:
invoke MessageBox, NULL, addr str_93, addr MensajePrint, MB_OK
Label94:
RET
Label95:
PUSH _L91
Label96:
PUSH 3
Label97:
CALL _F
MOV @aux97, EAX
invoke ExitProcess, 0
Error_DivCero:
invoke MessageBox, NULL, addr ErrorDivCero, addr ErrorDivCero, MB_OK
invoke ExitProcess, 1
end start
