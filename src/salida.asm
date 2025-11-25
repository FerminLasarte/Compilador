.386
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
include \masm32\include\msvcrt.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib
includelib \masm32\lib\msvcrt.lib
.data
MsgErrorDivCero db "Error: Division por cero", 10, 0
MsgErrorOverflow db "Error: Overflow en operacion", 10, 0
MsgErrorRestaNegativa db "Error: Resultado negativo en resta de enteros sin signo", 10, 0
MensajePrint db "Salida: %s", 10, 0
MensajePrintNum db "Salida: %d", 10, 0
MensajePrintFloat db "Salida: %f", 10, 0
MaxFloatValue dd 2139095039
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
@aux124 dd 0
@aux125 dd 0
@aux126 dd 0
@aux127 dd 0
@aux128 dd 0
@aux129 dd 0
@aux130 dd 0
@aux131 dd 0
@aux132 dd 0
@aux133 dd 0
@aux134 dd 0
@aux135 dd 0
@aux136 dd 0
@aux137 dd 0
@aux138 dd 0
@aux139 dd 0
@aux140 dd 0
@aux141 dd 0
@aux142 dd 0
@aux143 dd 0
@aux144 dd 0
@aux145 dd 0
@aux146 dd 0
@aux147 dd 0
@aux148 dd 0
@aux149 dd 0
@aux150 dd 0
@aux151 dd 0
@aux152 dd 0
@aux153 dd 0
@aux154 dd 0
@aux155 dd 0
@aux156 dd 0
@aux157 dd 0
@aux158 dd 0
@aux159 dd 0
@aux160 dd 0
@aux161 dd 0
@aux162 dd 0
@aux163 dd 0
@aux164 dd 0
@aux165 dd 0
@aux166 dd 0
@aux167 dd 0
@aux168 dd 0
@aux169 dd 0
@aux170 dd 0
@aux171 dd 0
@aux172 dd 0
@aux173 dd 0
@aux174 dd 0
@aux175 dd 0
@aux176 dd 0
@aux177 dd 0
@aux178 dd 0
@aux179 dd 0
@aux180 dd 0
_A dd 0
_B dd 0
_F1 dd 0
_F2 dd 0
_F3 dd 0
_X dd 0
_Y dd 0
_Z dd 0
_E dd 0
_CONT dd 0
_W dd 0
_J dd 0
_MAIN_A dd 0
_P dd 0
_FUNCZ_A dd 0
_AA dd 0
_GCONTADOR dd 0
_FUNCIONX_A dd 0
_VALOR dd 0
_OUT dd 0
_INOUT dd 0
_BASURA dd 0
_SS dd 0
_XX dd 0
_MAX dd 0
_OVER dd 0
_GLOBAL dd 0
_VARLOCAL dd 0
_P1 dd 0
_R1 dd 0
_R2 dd 0
str_1 db "A:", 0
str_4 db "B:", 0
str_7 db "F1:", 0
str_10 db "F2:", 0
str_13 db "F3:", 0
str_15 db "cadena", 13, 10, "    multilinea", 0
str_17 db "X:", 0
str_20 db "Y:", 0
str_23 db "Z:", 0
str_28 db "E:", 0
str_33 db "CONT:", 0
str_37 db "#ASIGNACION MULTIPLE IGUAL DE LADOS#", 0
str_40 db "A:", 0
str_42 db "B:", 0
str_44 db "IMPRIME AAAAA:", 0
str_48 db "A DENTRO DE FUNCZ", 0
str_51 db "W DE FUNCZ:", 0
str_54 db "J DE FUNCZ:", 0
str_57 db "A DE FUNCZ:", 0
str_60 db "W DE FUNCZ TOMA VALOR DE A DE FUNCZ:", 0
str_63 db "W DE FUNCZ TOMA VALOR DE MAIN.A:", 0
str_66 db "MAIN.A TOMA VALOR DE W:", 0
str_68 db "IMPRIMO A DE FUNCZ:", 0
str_73 db "W DE FUNCJ:", 0
str_76 db "P DE FUNCJ:", 0
str_79 db "A DE FUNCJ:", 0
str_82 db "W DE FUNCJ TOMA VALOR DE MAIN.A:", 0
str_85 db "FUNCZ.A TOMA VALOR DE W DE FUNCJ:", 0
str_89 db "intermedio entre funciones", 0
str_95 db "PRINT GCONTADOR ", 0
str_103 db "PRINT AA ", 0
str_109 db "PRINT A ", 0
str_113 db "PRINT FUNCIONX.A ", 0
str_118 db "chau chau", 0
str_126 db "chau chau", 0
str_142 db " PRINT AA ", 0
str_152 db "GLOBAL:", 0
str_159 db "MAIN.GLOBAL:", 0
str_162 db "VARLOCAL:", 0
str_166 db "MAIN.GLOBAL:", 0
str_176 db "R1:", 0
str_178 db "GLOBAL:", 0
str_180 db "Si ves esto, el control de overflow fallo", 0
.code
start:
Label0:
MOV EAX, 0
MOV _A, EAX
Label1:
invoke crt_printf, addr MensajePrint, addr str_1
Label2:
invoke crt_printf, addr MensajePrintNum, _A
Label3:
MOV EAX, 65535
MOV _B, EAX
Label4:
invoke crt_printf, addr MensajePrint, addr str_4
Label5:
invoke crt_printf, addr MensajePrintNum, _B
Label6:
MOV EAX, 1125515264
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FSTP _F1
Label7:
invoke crt_printf, addr MensajePrint, addr str_7
Label8:
FLD _F1
sub esp, 8
fstp qword ptr [esp]
push offset MensajePrintFloat
call crt_printf
add esp, 12
Label9:
MOV EAX, 1028443341
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FSTP _F2
Label10:
invoke crt_printf, addr MensajePrint, addr str_10
Label11:
FLD _F2
sub esp, 8
fstp qword ptr [esp]
push offset MensajePrintFloat
call crt_printf
add esp, 12
Label12:
MOV EAX, 1092616192
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FSTP _F3
Label13:
invoke crt_printf, addr MensajePrint, addr str_13
Label14:
FLD _F3
sub esp, 8
fstp qword ptr [esp]
push offset MensajePrintFloat
call crt_printf
add esp, 12
Label15:
invoke crt_printf, addr MensajePrint, addr str_15
Label16:
MOV EAX, 10
MOV _X, EAX
Label17:
invoke crt_printf, addr MensajePrint, addr str_17
Label18:
invoke crt_printf, addr MensajePrintNum, _X
Label19:
MOV EAX, 1103626240
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FSTP _Y
Label20:
invoke crt_printf, addr MensajePrint, addr str_20
Label21:
FLD _Y
sub esp, 8
fstp qword ptr [esp]
push offset MensajePrintFloat
call crt_printf
add esp, 12
Label22:
FLD _Y
FSTP _Z
Label23:
invoke crt_printf, addr MensajePrint, addr str_23
Label24:
FLD _Z
sub esp, 8
fstp qword ptr [esp]
push offset MensajePrintFloat
call crt_printf
add esp, 12
Label25:
MOV EAX, 1103626240
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FISTP @aux25
Label26:
MOV EAX, 10
ADD EAX, @aux25
CMP EAX, 65535
JA ErrorOverflow
MOV @aux26, EAX
Label27:
MOV EAX, @aux26
MOV _E, EAX
Label28:
invoke crt_printf, addr MensajePrint, addr str_28
Label29:
invoke crt_printf, addr MensajePrintNum, _E
Label30:
MOV EAX, 0
MOV _CONT, EAX
Label31:
MOV EAX, _CONT
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux31, EAX
Label32:
MOV EAX, @aux31
MOV _CONT, EAX
Label33:
invoke crt_printf, addr MensajePrint, addr str_33
Label34:
invoke crt_printf, addr MensajePrintNum, _CONT
Label35:
MOV EAX, _CONT
CMP EAX, 5
SETB AL
MOVZX EAX, AL
MOV @aux35, EAX
Label36:
MOV EAX, @aux35
CMP EAX, 1
JE Label31
Label37:
invoke crt_printf, addr MensajePrint, addr str_37
Label38:
MOV EAX, 20
MOV _B, EAX
Label39:
MOV EAX, 10
MOV _A, EAX
Label40:
invoke crt_printf, addr MensajePrint, addr str_40
Label41:
invoke crt_printf, addr MensajePrintNum, _A
Label42:
invoke crt_printf, addr MensajePrint, addr str_42
Label43:
invoke crt_printf, addr MensajePrintNum, _B
Label44:
invoke crt_printf, addr MensajePrint, addr str_44
Label45:
invoke crt_printf, addr MensajePrintNum, _A
Label46:
JMP Label89
Label47:
__FUNCZ:
Label48:
invoke crt_printf, addr MensajePrint, addr str_48
Label49:
invoke crt_printf, addr MensajePrintNum, _A
Label50:
MOV EAX, 2
MOV _W, EAX
Label51:
invoke crt_printf, addr MensajePrint, addr str_51
Label52:
invoke crt_printf, addr MensajePrintNum, _W
Label53:
MOV EAX, 3
MOV _J, EAX
Label54:
invoke crt_printf, addr MensajePrint, addr str_54
Label55:
invoke crt_printf, addr MensajePrintNum, _J
Label56:
MOV EAX, 4
MOV _A, EAX
Label57:
invoke crt_printf, addr MensajePrint, addr str_57
Label58:
invoke crt_printf, addr MensajePrintNum, _A
Label59:
MOV EAX, _A
MOV _W, EAX
Label60:
invoke crt_printf, addr MensajePrint, addr str_60
Label61:
invoke crt_printf, addr MensajePrintNum, _W
Label62:
MOV EAX, _MAIN_A
MOV _W, EAX
Label63:
invoke crt_printf, addr MensajePrint, addr str_63
Label64:
invoke crt_printf, addr MensajePrintNum, _W
Label65:
MOV EAX, _W
MOV _MAIN_A, EAX
Label66:
invoke crt_printf, addr MensajePrint, addr str_66
Label67:
invoke crt_printf, addr MensajePrintNum, _MAIN_A
Label68:
invoke crt_printf, addr MensajePrint, addr str_68
Label69:
invoke crt_printf, addr MensajePrintNum, _A
Label70:
JMP Label88
Label71:
__FUNCJ:
Label72:
MOV EAX, 22
MOV _W, EAX
Label73:
invoke crt_printf, addr MensajePrint, addr str_73
Label74:
invoke crt_printf, addr MensajePrintNum, _W
Label75:
MOV EAX, 33
MOV _P, EAX
Label76:
invoke crt_printf, addr MensajePrint, addr str_76
Label77:
invoke crt_printf, addr MensajePrintNum, _P
Label78:
MOV EAX, 44
MOV _A, EAX
Label79:
invoke crt_printf, addr MensajePrint, addr str_79
Label80:
invoke crt_printf, addr MensajePrintNum, _A
Label81:
MOV EAX, _MAIN_A
MOV _W, EAX
Label82:
invoke crt_printf, addr MensajePrint, addr str_82
Label83:
invoke crt_printf, addr MensajePrintNum, _W
Label84:
MOV EAX, _W
MOV _FUNCZ_A, EAX
Label85:
invoke crt_printf, addr MensajePrint, addr str_85
Label86:
invoke crt_printf, addr MensajePrintNum, _W
Label87:
RET
Label88:
RET
Label89:
invoke crt_printf, addr MensajePrint, addr str_89
Label90:
JMP Label118
Label91:
__FUNCIONX:
Label92:
MOV EAX, 3
MOV _AA, EAX
Label93:
MOV EAX, 3
MOV _A, EAX
Label94:
MOV EAX, 0
MOV _GCONTADOR, EAX
Label95:
invoke crt_printf, addr MensajePrint, addr str_95
Label96:
invoke crt_printf, addr MensajePrintNum, _GCONTADOR
Label97:
MOV EAX, _GCONTADOR
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux97, EAX
Label98:
MOV EAX, @aux97
MOV _GCONTADOR, EAX
Label99:
MOV EAX, _GCONTADOR
CMP EAX, 3
SETB AL
MOVZX EAX, AL
MOV @aux99, EAX
Label100:
MOV EAX, @aux99
CMP EAX, 1
JE Label95
Label101:
MOV EAX, _AA
CMP EAX, 5
SETB AL
MOVZX EAX, AL
MOV @aux101, EAX
Label102:
MOV EAX, @aux101
CMP EAX, 0
JE Label106
Label103:
invoke crt_printf, addr MensajePrint, addr str_103
Label104:
invoke crt_printf, addr MensajePrintNum, _FUNCIONX_A
Label105:
JMP Label107
Label106:
invoke crt_printf, addr MensajePrintNum, 100
Label107:
MOV EAX, _A
CMP EAX, 5
SETB AL
MOVZX EAX, AL
MOV @aux107, EAX
Label108:
MOV EAX, @aux107
CMP EAX, 0
JE Label111
Label109:
invoke crt_printf, addr MensajePrint, addr str_109
Label110:
invoke crt_printf, addr MensajePrintNum, _A
Label111:
MOV EAX, _FUNCIONX_A
CMP EAX, 5
SETB AL
MOVZX EAX, AL
MOV @aux111, EAX
Label112:
MOV EAX, @aux111
CMP EAX, 0
JE Label116
Label113:
invoke crt_printf, addr MensajePrint, addr str_113
Label114:
invoke crt_printf, addr MensajePrintNum, _FUNCIONX_A
Label115:
; JMP UNRESOLVED (_)
Label116:
invoke crt_printf, addr MensajePrintNum, 100
Label117:
RET
Label118:
invoke crt_printf, addr MensajePrint, addr str_118
Label119:
MOV EAX, 10
MOV _VALOR, EAX
Label120:
JMP Label126
Label121:
__PROCESAR:
Label122:
MOV EAX, 99
MOV _OUT, EAX
Label123:
MOV EAX, _INOUT
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux123, EAX
Label124:
MOV EAX, @aux123
MOV _INOUT, EAX
Label125:
RET
Label126:
invoke crt_printf, addr MensajePrint, addr str_126
Label127:
MOV EAX, 0
MOV _BASURA, EAX
Label128:
invoke crt_printf, addr MensajePrintNum, _BASURA
Label129:
MOV EAX, _BASURA
MOV _OUT, EAX
Label130:
MOV EAX, _VALOR
MOV _INOUT, EAX
Label131:
CALL __PROCESAR
MOV @aux131, EAX
Label132:
invoke crt_printf, addr MensajePrintNum, _BASURA
Label133:
invoke crt_printf, addr MensajePrintNum, _VALOR
Label134:
JMP Label138
Label135:
__F:
Label136:
Label137:
MOV EAX, _X
CALL EAX
Label138:
JMP Label144
Label139:
Label140:
MOV EAX, _XX
CMP EAX, 1
SETA AL
MOVZX EAX, AL
MOV @aux140, EAX
Label141:
MOV EAX, @aux140
CMP EAX, 0
JE Label143
Label142:
invoke crt_printf, addr MensajePrint, addr str_142
Label143:
RET
Label144:
MOV EAX, Label139
MOV _X, EAX
Label145:
MOV EAX, 3
MOV _SS, EAX
Label146:
CALL __F
MOV @aux146, EAX
Label147:
MOV EAX, 0
MOV _A, EAX
Label148:
MOV EAX, 65535
MOV _MAX, EAX
Label149:
MOV EAX, _MAX
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux149, EAX
Label150:
MOV EAX, @aux149
MOV _OVER, EAX
Label151:
MOV EAX, 100
MOV _GLOBAL, EAX
Label152:
invoke crt_printf, addr MensajePrint, addr str_152
Label153:
invoke crt_printf, addr MensajePrintNum, _GLOBAL
Label154:
JMP Label170
Label155:
__FUNCION:
Label156:
MOV EAX, 200
MOV _VARLOCAL, EAX
Label157:
MOV EAX, _GLOBAL
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux157, EAX
Label158:
MOV EAX, @aux157
MOV _GLOBAL, EAX
Label159:
invoke crt_printf, addr MensajePrint, addr str_159
Label160:
invoke crt_printf, addr MensajePrintNum, _GLOBAL
Label161:
MOV EAX, _GLOBAL
MOV _VARLOCAL, EAX
Label162:
invoke crt_printf, addr MensajePrint, addr str_162
Label163:
invoke crt_printf, addr MensajePrintNum, _VARLOCAL
Label164:
MOV EAX, _VARLOCAL
ADD EAX, 3
CMP EAX, 65535
JA ErrorOverflow
MOV @aux164, EAX
Label165:
MOV EAX, @aux164
MOV _GLOBAL, EAX
Label166:
invoke crt_printf, addr MensajePrint, addr str_166
Label167:
invoke crt_printf, addr MensajePrintNum, _GLOBAL
Label168:
RET
Label169:
RET
Label170:
MOV EAX, 0
MOV _R1, EAX
Label171:
MOV EAX, 0
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FSTP _R2
Label172:
MOV EAX, 50
MOV _P1, EAX
Label173:
CALL __FUNCION
MOV @aux173, EAX
Label174:
Label175:
MOV EAX, @aux174
MOV _R1, EAX
Label176:
invoke crt_printf, addr MensajePrint, addr str_176
Label177:
invoke crt_printf, addr MensajePrintNum, _R1
Label178:
invoke crt_printf, addr MensajePrint, addr str_178
Label179:
invoke crt_printf, addr MensajePrintNum, _GLOBAL
Label180:
invoke crt_printf, addr MensajePrint, addr str_180
invoke ExitProcess, 0
Error_DivCero:
invoke crt_printf, addr MsgErrorDivCero
invoke ExitProcess, 1
ErrorOverflow:
invoke crt_printf, addr MsgErrorOverflow
invoke ExitProcess, 1
ErrorRestaNegativa:
invoke crt_printf, addr MsgErrorRestaNegativa
invoke ExitProcess, 1
end start
