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
@aux181 dd 0
@aux182 dd 0
@aux183 dd 0
@aux184 dd 0
@aux185 dd 0
@aux186 dd 0
@aux187 dd 0
@aux188 dd 0
@aux189 dd 0
@aux190 dd 0
@aux191 dd 0
@aux192 dd 0
@aux193 dd 0
@aux194 dd 0
@aux195 dd 0
@aux196 dd 0
@aux197 dd 0
@aux198 dd 0
@aux199 dd 0
@aux200 dd 0
@aux201 dd 0
@aux202 dd 0
@aux203 dd 0
@aux204 dd 0
@aux205 dd 0
@aux206 dd 0
@aux207 dd 0
@aux208 dd 0
@aux209 dd 0
@aux210 dd 0
@aux211 dd 0
@aux212 dd 0
@aux213 dd 0
@aux214 dd 0
@aux215 dd 0
@aux216 dd 0
@aux217 dd 0
@aux218 dd 0
@aux219 dd 0
@aux220 dd 0
@aux221 dd 0
@aux222 dd 0
@aux223 dd 0
@aux224 dd 0
@aux225 dd 0
@aux226 dd 0
@aux227 dd 0
@aux228 dd 0
@aux229 dd 0
@aux230 dd 0
@aux231 dd 0
@aux232 dd 0
@aux233 dd 0
_A_MAIN dd 0
_B_MAIN dd 0
_F1_MAIN dd 0
_F2_MAIN dd 0
_F3_MAIN dd 0
_X_MAIN dd 0
_XX_MAIN dd 0
_Y_MAIN dd 0
_Z_MAIN dd 0
_E_MAIN dd 0
_CONT_MAIN dd 0
_W_MAIN_FUNCZ dd 0
_J_MAIN_FUNCZ dd 0
_A_MAIN_FUNCZ dd 0
_error_tipo dd 0
_W_MAIN_FUNCZ_FUNCJ dd 0
_P_MAIN_FUNCZ_FUNCJ dd 0
_VARIABLEOTRA_MAIN_FUNCZ dd 0
_J_MAIN_FUNCZ_FUNCJ dd 0
_VARIABLEZ_MAIN dd 0
_Z_MAIN_FUNCZ dd 0
_AA_MAIN_FUNCIONX dd 0
_A_MAIN_FUNCIONX dd 0
_GCONTADOR_MAIN_FUNCIONX dd 0
_VARIABLEXX_MAIN_FUNCIONX dd 0
_VALOR_MAIN_FUNCIONX dd 0
_OUT_MAIN_FUNCIONX_PROCESAR dd 0
_INOUT_MAIN_FUNCIONX_PROCESAR dd 0
_BASURA_MAIN_FUNCIONX dd 0
_GLOBAL_MAIN_FUNCIONX dd 0
_VARLOCAL_MAIN_FUNCIONX_FUNCION dd 0
_R1_MAIN_FUNCIONX dd 0
_R2_MAIN_FUNCIONX dd 0
_R3_MAIN_FUNCIONX dd 0
_P1_MAIN_FUNCIONX_FUNCION dd 0
_VARPARALAMBDA_MAIN_FUNCIONX dd 0
_VARLOCALNOVISIBLE_MAIN_FUNCIONX dd 0
_SS_MAIN_FUNCIONX_F dd 0
_X_MAIN_FUNCIONX_F dd 0
_VARPARALAMBDA_MAIN_FUNCIONX_lambda_213 dd 0
_VARPARALAMBDA_MAIN_FUNCIONX_lambda_221 dd 0
_VARPARALAMBDA_MAIN_FUNCIONX_lambda_228 dd 0
str_3 db "B:", 0
str_6 db "F1:", 0
str_9 db "F2:", 0
str_12 db "F3:", 0
str_15 db "X:", 0
str_18 db "XX:", 0
str_21 db "Y:", 0
str_24 db "Z:", 0
str_32 db "E:", 0
str_37 db "CONT:", 0
str_43 db "CONT:", 0
str_45 db "#ASIGNACION MULTIPLE IGUAL DE LADOS#", 0
str_48 db "A:", 0
str_50 db "B:", 0
str_52 db "IMPRIME AAAAA:", 0
str_56 db "A DENTRO DE FUNCZ", 0
str_59 db "W DE FUNCZ:", 0
str_62 db "J DE FUNCZ:", 0
str_65 db "A DE FUNCZ:", 0
str_68 db "W DE FUNCZ TOMA VALOR DE A DE FUNCZ:", 0
str_71 db "W DE FUNCZ TOMA VALOR DE MAIN.A:", 0
str_74 db "MAIN.A TOMA VALOR DE A:", 0
str_76 db "IMPRIMO A DE FUNCZ:", 0
str_82 db "W DE FUNCJ:", 0
str_85 db "P DE FUNCJ:", 0
str_88 db "A DE FUNCJ:", 0
str_91 db "W DE FUNCJ TOMA VALOR DE MAIN.A:", 0
str_94 db "FUNCZ.A TOMA VALOR DE W DE FUNCJ:", 0
str_101 db "por entrar", 0
str_103 db "entro", 0
str_109 db "por entrar dos", 0
str_111 db "entro toda", 0
str_117 db "PRINT GCONTADOR ", 0
str_125 db "PRINT AA ", 0
str_131 db "PRINT A ", 0
str_141 db "por entrar FUNCION IF", 0
str_143 db "ENTRO FUNCION IF", 0
str_152 db "IMPRIME BASURA 1", 0
str_159 db "IMPRIME BASURA 2", 0
str_161 db "IMPRIME VALOR", 0
str_164 db "GLOBAL:", 0
str_171 db "MAIN.GLOBAL:", 0
str_174 db "VARLOCAL:", 0
str_178 db "MAIN.GLOBAL:", 0
str_197 db "R1:", 0
str_199 db "R2:", 0
str_201 db "GLOBAL:", 0
str_207 db "IMPRIMO SS LAMBDA", 0
str_214 db "IMPRIMO VARPARALAMBDA DE LAMBDA", 0
str_222 db "SOY FLOAT", 0
.code
start:
Label0:
MOV EAX, 0
MOV _A_MAIN, EAX
Label1:
invoke crt_printf, addr MensajePrintNum, _A_MAIN
Label2:
MOV EAX, 65535
MOV _B_MAIN, EAX
Label3:
invoke crt_printf, addr MensajePrint, addr str_3
Label4:
invoke crt_printf, addr MensajePrintNum, _B_MAIN
Label5:
MOV EAX, 1125515264
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FSTP _F1_MAIN
Label6:
invoke crt_printf, addr MensajePrint, addr str_6
Label7:
FLD _F1_MAIN
sub esp, 8
fstp qword ptr [esp]
push offset MensajePrintFloat
call crt_printf
add esp, 12
Label8:
MOV EAX, 1028443341
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FSTP _F2_MAIN
Label9:
invoke crt_printf, addr MensajePrint, addr str_9
Label10:
FLD _F2_MAIN
sub esp, 8
fstp qword ptr [esp]
push offset MensajePrintFloat
call crt_printf
add esp, 12
Label11:
MOV EAX, 1092616192
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FSTP _F3_MAIN
Label12:
invoke crt_printf, addr MensajePrint, addr str_12
Label13:
FLD _F3_MAIN
sub esp, 8
fstp qword ptr [esp]
push offset MensajePrintFloat
call crt_printf
add esp, 12
Label14:
MOV EAX, 10
MOV _X_MAIN, EAX
Label15:
invoke crt_printf, addr MensajePrint, addr str_15
Label16:
invoke crt_printf, addr MensajePrintNum, _X_MAIN
Label17:
MOV EAX, 1000
MOV _XX_MAIN, EAX
Label18:
invoke crt_printf, addr MensajePrint, addr str_18
Label19:
invoke crt_printf, addr MensajePrintNum, _XX_MAIN
Label20:
MOV EAX, 1103626240
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FSTP _Y_MAIN
Label21:
invoke crt_printf, addr MensajePrint, addr str_21
Label22:
FLD _Y_MAIN
sub esp, 8
fstp qword ptr [esp]
push offset MensajePrintFloat
call crt_printf
add esp, 12
Label23:
FLD _Y_MAIN
FSTP _Z_MAIN
Label24:
invoke crt_printf, addr MensajePrint, addr str_24
Label25:
FLD _Z_MAIN
sub esp, 8
fstp qword ptr [esp]
push offset MensajePrintFloat
call crt_printf
add esp, 12
Label26:
FLD _Y_MAIN
FISTP @aux26
Label27:
MOV EAX, @aux26
MOV _X_MAIN, EAX
Label28:
MOV EAX, 10
ADD EAX, 1103626240
CMP EAX, 65535
JA ErrorOverflow
MOV @aux28, EAX
Label29:
MOV EAX, 1103626240
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FISTP @aux29
Label30:
MOV EAX, 10
ADD EAX, @aux29
CMP EAX, 65535
JA ErrorOverflow
MOV @aux30, EAX
Label31:
MOV EAX, @aux30
MOV _E_MAIN, EAX
Label32:
invoke crt_printf, addr MensajePrint, addr str_32
Label33:
invoke crt_printf, addr MensajePrintNum, _E_MAIN
Label34:
MOV EAX, 0
MOV _CONT_MAIN, EAX
Label35:
MOV EAX, _CONT_MAIN
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux35, EAX
Label36:
MOV EAX, @aux35
MOV _CONT_MAIN, EAX
Label37:
invoke crt_printf, addr MensajePrint, addr str_37
Label38:
invoke crt_printf, addr MensajePrintNum, _CONT_MAIN
Label39:
MOV EAX, _CONT_MAIN
CMP EAX, 5
SETB AL
MOVZX EAX, AL
MOV @aux39, EAX
Label40:
MOV EAX, @aux39
CMP EAX, 1
JE Label35
Label41:
MOV EAX, _CONT_MAIN
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux41, EAX
Label42:
MOV EAX, @aux41
MOV _CONT_MAIN, EAX
Label43:
invoke crt_printf, addr MensajePrint, addr str_43
Label44:
invoke crt_printf, addr MensajePrintNum, _CONT_MAIN
Label45:
invoke crt_printf, addr MensajePrint, addr str_45
Label46:
MOV EAX, 20
MOV _B_MAIN, EAX
Label47:
MOV EAX, 10
MOV _A_MAIN, EAX
Label48:
invoke crt_printf, addr MensajePrint, addr str_48
Label49:
invoke crt_printf, addr MensajePrintNum, _A_MAIN
Label50:
invoke crt_printf, addr MensajePrint, addr str_50
Label51:
invoke crt_printf, addr MensajePrintNum, _B_MAIN
Label52:
invoke crt_printf, addr MensajePrint, addr str_52
Label53:
invoke crt_printf, addr MensajePrintNum, _A_MAIN
Label54:
JMP Label105
Label55:
__FUNCZ_MAIN:
Label56:
invoke crt_printf, addr MensajePrint, addr str_56
Label57:
invoke crt_printf, addr MensajePrintNum, _A_MAIN
Label58:
MOV EAX, 2
MOV _W_MAIN_FUNCZ, EAX
Label59:
invoke crt_printf, addr MensajePrint, addr str_59
Label60:
invoke crt_printf, addr MensajePrintNum, _W_MAIN_FUNCZ
Label61:
MOV EAX, 3
MOV _J_MAIN_FUNCZ, EAX
Label62:
invoke crt_printf, addr MensajePrint, addr str_62
Label63:
invoke crt_printf, addr MensajePrintNum, _J_MAIN_FUNCZ
Label64:
MOV EAX, 4
MOV _A_MAIN_FUNCZ, EAX
Label65:
invoke crt_printf, addr MensajePrint, addr str_65
Label66:
invoke crt_printf, addr MensajePrintNum, _A_MAIN_FUNCZ
Label67:
MOV EAX, _A_MAIN_FUNCZ
MOV _W_MAIN_FUNCZ, EAX
Label68:
invoke crt_printf, addr MensajePrint, addr str_68
Label69:
invoke crt_printf, addr MensajePrintNum, _W_MAIN_FUNCZ
Label70:
MOV EAX, _A_MAIN
MOV _W_MAIN_FUNCZ, EAX
Label71:
invoke crt_printf, addr MensajePrint, addr str_71
Label72:
invoke crt_printf, addr MensajePrintNum, _W_MAIN_FUNCZ
Label73:
MOV EAX, _A_MAIN_FUNCZ
MOV _A_MAIN, EAX
Label74:
invoke crt_printf, addr MensajePrint, addr str_74
Label75:
invoke crt_printf, addr MensajePrintNum, _A_MAIN
Label76:
invoke crt_printf, addr MensajePrint, addr str_76
Label77:
invoke crt_printf, addr MensajePrintNum, _A_MAIN_FUNCZ
Label78:
invoke crt_printf, addr MensajePrintNum, _error_tipo
Label79:
JMP Label97
Label80:
__FUNCJ_MAIN_FUNCZ:
Label81:
MOV EAX, 22
MOV _W_MAIN_FUNCZ_FUNCJ, EAX
Label82:
invoke crt_printf, addr MensajePrint, addr str_82
Label83:
invoke crt_printf, addr MensajePrintNum, _W_MAIN_FUNCZ_FUNCJ
Label84:
MOV EAX, 33
MOV _P_MAIN_FUNCZ_FUNCJ, EAX
Label85:
invoke crt_printf, addr MensajePrint, addr str_85
Label86:
invoke crt_printf, addr MensajePrintNum, _P_MAIN_FUNCZ_FUNCJ
Label87:
invoke crt_printf, addr MensajePrintNum, _error_tipo
Label88:
invoke crt_printf, addr MensajePrint, addr str_88
Label89:
invoke crt_printf, addr MensajePrintNum, _A_MAIN_FUNCZ
Label90:
MOV EAX, _A_MAIN
MOV _W_MAIN_FUNCZ_FUNCJ, EAX
Label91:
invoke crt_printf, addr MensajePrint, addr str_91
Label92:
invoke crt_printf, addr MensajePrintNum, _W_MAIN_FUNCZ_FUNCJ
Label93:
MOV EAX, _W_MAIN_FUNCZ_FUNCJ
MOV _A_MAIN_FUNCZ, EAX
Label94:
invoke crt_printf, addr MensajePrint, addr str_94
Label95:
invoke crt_printf, addr MensajePrintNum, _W_MAIN_FUNCZ_FUNCJ
Label96:
; -- RETURN --
MOV EAX, 55
RET
Label97:
MOV EAX, 0
MOV _VARIABLEOTRA_MAIN_FUNCZ, EAX
Label98:
MOV EAX, _VARIABLEOTRA_MAIN_FUNCZ
MOV _J_MAIN_FUNCZ_FUNCJ, EAX
Label99:
CALL __FUNCJ_MAIN_FUNCZ
MOV @aux99, EAX
Label100:
MOV EAX, @aux99
MOV _VARIABLEOTRA_MAIN_FUNCZ, EAX
Label101:
invoke crt_printf, addr MensajePrint, addr str_101
Label102:
invoke crt_printf, addr MensajePrintNum, _VARIABLEOTRA_MAIN_FUNCZ
Label103:
invoke crt_printf, addr MensajePrint, addr str_103
Label104:
; -- RETURN --
MOV EAX, 5
RET
Label105:
MOV EAX, 0
MOV _VARIABLEZ_MAIN, EAX
Label106:
MOV EAX, _VARIABLEZ_MAIN
MOV _Z_MAIN_FUNCZ, EAX
Label107:
CALL __FUNCZ_MAIN
MOV @aux107, EAX
Label108:
MOV EAX, @aux107
MOV _VARIABLEZ_MAIN, EAX
Label109:
invoke crt_printf, addr MensajePrint, addr str_109
Label110:
invoke crt_printf, addr MensajePrintNum, _VARIABLEZ_MAIN
Label111:
invoke crt_printf, addr MensajePrint, addr str_111
Label112:
JMP Label136
Label113:
__FUNCIONX_MAIN:
Label114:
MOV EAX, 3
MOV _AA_MAIN_FUNCIONX, EAX
Label115:
MOV EAX, 3
MOV _A_MAIN_FUNCIONX, EAX
Label116:
MOV EAX, 0
MOV _GCONTADOR_MAIN_FUNCIONX, EAX
Label117:
invoke crt_printf, addr MensajePrint, addr str_117
Label118:
invoke crt_printf, addr MensajePrintNum, _GCONTADOR_MAIN_FUNCIONX
Label119:
MOV EAX, _GCONTADOR_MAIN_FUNCIONX
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux119, EAX
Label120:
MOV EAX, @aux119
MOV _GCONTADOR_MAIN_FUNCIONX, EAX
Label121:
MOV EAX, _GCONTADOR_MAIN_FUNCIONX
CMP EAX, 3
SETB AL
MOVZX EAX, AL
MOV @aux121, EAX
Label122:
MOV EAX, @aux121
CMP EAX, 1
JE Label117
Label123:
MOV EAX, _AA_MAIN_FUNCIONX
CMP EAX, 5
SETB AL
MOVZX EAX, AL
MOV @aux123, EAX
Label124:
MOV EAX, @aux123
CMP EAX, 0
JE Label128
Label125:
invoke crt_printf, addr MensajePrint, addr str_125
Label126:
invoke crt_printf, addr MensajePrintNum, _AA_MAIN_FUNCIONX
Label127:
JMP Label129
Label128:
invoke crt_printf, addr MensajePrintNum, 100
Label129:
MOV EAX, _A_MAIN_FUNCIONX
CMP EAX, 5
SETB AL
MOVZX EAX, AL
MOV @aux129, EAX
Label130:
MOV EAX, @aux129
CMP EAX, 0
JE Label134
Label131:
invoke crt_printf, addr MensajePrint, addr str_131
Label132:
invoke crt_printf, addr MensajePrintNum, _A_MAIN_FUNCIONX
Label133:
; JMP UNRESOLVED (_)
Label134:
invoke crt_printf, addr MensajePrintNum, 100
Label135:
; -- RETURN --
MOV EAX, 1000
RET
Label136:
invoke crt_printf, addr MensajePrintNum, _GCONTADOR_MAIN_FUNCIONX
Label137:
MOV EAX, 0
MOV _VARIABLEXX_MAIN_FUNCIONX, EAX
Label138:
MOV EAX, _VARIABLEXX_MAIN_FUNCIONX
MOV _AA_MAIN_FUNCIONX, EAX
Label139:
CALL __FUNCIONX_MAIN
MOV @aux139, EAX
Label140:
MOV EAX, @aux139
MOV _VARIABLEXX_MAIN_FUNCIONX, EAX
Label141:
invoke crt_printf, addr MensajePrint, addr str_141
Label142:
invoke crt_printf, addr MensajePrintNum, _VARIABLEXX_MAIN_FUNCIONX
Label143:
invoke crt_printf, addr MensajePrint, addr str_143
Label144:
MOV EAX, 10
MOV _VALOR_MAIN_FUNCIONX, EAX
Label145:
JMP Label151
Label146:
__PROCESAR_MAIN_FUNCIONX:
Label147:
MOV EAX, 99
MOV _OUT_MAIN_FUNCIONX_PROCESAR, EAX
Label148:
MOV EAX, _INOUT_MAIN_FUNCIONX_PROCESAR
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux148, EAX
Label149:
MOV EAX, @aux148
MOV _INOUT_MAIN_FUNCIONX_PROCESAR, EAX
Label150:
; -- RETURN --
MOV EAX, 1
RET
Label151:
MOV EAX, 0
MOV _BASURA_MAIN_FUNCIONX, EAX
Label152:
invoke crt_printf, addr MensajePrint, addr str_152
Label153:
invoke crt_printf, addr MensajePrintNum, _BASURA_MAIN_FUNCIONX
Label154:
MOV EAX, _BASURA_MAIN_FUNCIONX
MOV _OUT_MAIN_FUNCIONX_PROCESAR, EAX
Label155:
MOV EAX, _VALOR_MAIN_FUNCIONX
MOV _INOUT_MAIN_FUNCIONX_PROCESAR, EAX
Label156:
CALL __PROCESAR_MAIN_FUNCIONX
MOV @aux156, EAX
Label157:
MOV EAX, _OUT_MAIN_FUNCIONX_PROCESAR
MOV _BASURA_MAIN_FUNCIONX, EAX
Label158:
MOV EAX, _INOUT_MAIN_FUNCIONX_PROCESAR
MOV _VALOR_MAIN_FUNCIONX, EAX
Label159:
invoke crt_printf, addr MensajePrint, addr str_159
Label160:
invoke crt_printf, addr MensajePrintNum, _BASURA_MAIN_FUNCIONX
Label161:
invoke crt_printf, addr MensajePrint, addr str_161
Label162:
invoke crt_printf, addr MensajePrintNum, _VALOR_MAIN_FUNCIONX
Label163:
MOV EAX, 100
MOV _GLOBAL_MAIN_FUNCIONX, EAX
Label164:
invoke crt_printf, addr MensajePrint, addr str_164
Label165:
invoke crt_printf, addr MensajePrintNum, _GLOBAL_MAIN_FUNCIONX
Label166:
JMP Label182
Label167:
__FUNCION_MAIN_FUNCIONX:
Label168:
MOV EAX, 200
MOV _VARLOCAL_MAIN_FUNCIONX_FUNCION, EAX
Label169:
MOV EAX, _GLOBAL_MAIN_FUNCIONX
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux169, EAX
Label170:
MOV EAX, @aux169
MOV _GLOBAL_MAIN_FUNCIONX, EAX
Label171:
invoke crt_printf, addr MensajePrint, addr str_171
Label172:
invoke crt_printf, addr MensajePrintNum, _GLOBAL_MAIN_FUNCIONX
Label173:
MOV EAX, _GLOBAL_MAIN_FUNCIONX
MOV _VARLOCAL_MAIN_FUNCIONX_FUNCION, EAX
Label174:
invoke crt_printf, addr MensajePrint, addr str_174
Label175:
invoke crt_printf, addr MensajePrintNum, _VARLOCAL_MAIN_FUNCIONX_FUNCION
Label176:
MOV EAX, _VARLOCAL_MAIN_FUNCIONX_FUNCION
ADD EAX, 3
CMP EAX, 65535
JA ErrorOverflow
MOV @aux176, EAX
Label177:
MOV EAX, @aux176
MOV _GLOBAL_MAIN_FUNCIONX, EAX
Label178:
invoke crt_printf, addr MensajePrint, addr str_178
Label179:
invoke crt_printf, addr MensajePrintNum, _GLOBAL_MAIN_FUNCIONX
Label180:
; -- RETURN --
MOV EAX, 12
RET
Label181:
; -- RETURN --
MOV EAX, 15
RET
Label182:
MOV EAX, 0
MOV _R1_MAIN_FUNCIONX, EAX
Label183:
MOV EAX, 0
MOV _R2_MAIN_FUNCIONX, EAX
Label184:
MOV EAX, 0
MOV _R3_MAIN_FUNCIONX, EAX
Label185:
MOV EAX, 50
MOV _P1_MAIN_FUNCIONX_FUNCION, EAX
Label186:
CALL __FUNCION_MAIN_FUNCIONX
MOV @aux186, EAX
Label187:
MOV EAX, @aux186
MOV @aux187, EAX
Label188:
MOV EAX, @aux187
MOV _R1_MAIN_FUNCIONX, EAX
Label189:
MOV EAX, 50
MOV _P1_MAIN_FUNCIONX_FUNCION, EAX
Label190:
CALL __FUNCION_MAIN_FUNCIONX
MOV @aux190, EAX
Label191:
MOV EAX, 50
MOV _P1_MAIN_FUNCIONX_FUNCION, EAX
Label192:
CALL __FUNCION_MAIN_FUNCIONX
MOV @aux192, EAX
Label193:
MOV EAX, @aux192
MOV @aux193, EAX
Label194:
MOV EAX, @aux193
MOV _R1_MAIN_FUNCIONX, EAX
Label195:
MOV EAX, @aux192
MOV @aux195, EAX
Label196:
MOV EAX, @aux195
MOV _R2_MAIN_FUNCIONX, EAX
Label197:
invoke crt_printf, addr MensajePrint, addr str_197
Label198:
invoke crt_printf, addr MensajePrintNum, _R1_MAIN_FUNCIONX
Label199:
invoke crt_printf, addr MensajePrint, addr str_199
Label200:
invoke crt_printf, addr MensajePrintNum, _R2_MAIN_FUNCIONX
Label201:
invoke crt_printf, addr MensajePrint, addr str_201
Label202:
invoke crt_printf, addr MensajePrintNum, _GLOBAL_MAIN_FUNCIONX
Label203:
MOV EAX, 5
MOV _VARPARALAMBDA_MAIN_FUNCIONX, EAX
Label204:
MOV EAX, 10
MOV _VARLOCALNOVISIBLE_MAIN_FUNCIONX, EAX
Label205:
JMP Label212
Label206:
__F_MAIN_FUNCIONX:
Label207:
invoke crt_printf, addr MensajePrint, addr str_207
Label208:
invoke crt_printf, addr MensajePrintNum, _SS_MAIN_FUNCIONX_F
Label209:
Label210:
MOV EAX, _X_MAIN_FUNCIONX_F
CALL EAX
Label211:
; -- RETURN --
MOV EAX, 111
RET
Label212:
JMP Label217
Label213:
Label214:
invoke crt_printf, addr MensajePrint, addr str_214
Label215:
invoke crt_printf, addr MensajePrintNum, _VARPARALAMBDA_MAIN_FUNCIONX_lambda_213
Label216:
; -- RETURN --
RET
Label217:
MOV EAX, Label213
MOV _X_MAIN_FUNCIONX_F, EAX
Label218:
MOV EAX, 33
MOV _SS_MAIN_FUNCIONX_F, EAX
Label219:
CALL __F_MAIN_FUNCIONX
MOV @aux219, EAX
Label220:
JMP Label224
Label221:
Label222:
invoke crt_printf, addr MensajePrint, addr str_222
Label223:
; -- RETURN --
RET
Label224:
MOV EAX, Label221
MOV _X_MAIN_FUNCIONX_F, EAX
Label225:
MOV EAX, 33
MOV _SS_MAIN_FUNCIONX_F, EAX
Label226:
CALL __F_MAIN_FUNCIONX
MOV @aux226, EAX
Label227:
JMP Label231
Label228:
Label229:
invoke crt_printf, addr MensajePrintNum, _VARLOCALNOVISIBLE_MAIN_FUNCIONX
Label230:
; -- RETURN --
RET
Label231:
MOV EAX, Label228
MOV _X_MAIN_FUNCIONX_F, EAX
Label232:
MOV EAX, 33
MOV _SS_MAIN_FUNCIONX_F, EAX
Label233:
CALL __F_MAIN_FUNCIONX
MOV @aux233, EAX
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
