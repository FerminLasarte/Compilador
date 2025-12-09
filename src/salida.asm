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
_RET_VAL_0 dd 0
_RET_VAL_1 dd 0
_RET_VAL_2 dd 0
_RET_VAL_3 dd 0
_RET_VAL_4 dd 0
_RET_VAL_5 dd 0
_RET_VAL_6 dd 0
_RET_VAL_7 dd 0
_RET_VAL_8 dd 0
_RET_VAL_9 dd 0
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
_A_MAIN_FUNCZ_FUNCJ dd 0
_VARIABLEOTRA_MAIN_FUNCZ dd 0
_J_MAIN_FUNCZ_FUNCJ dd 0
_VARIABLEZ_MAIN dd 0
_Z_MAIN_FUNCZ dd 0
_AA_MAIN_FUNCIONX dd 0
_A_MAIN_FUNCIONX dd 0
_GCONTADOR_MAIN_FUNCIONX dd 0
_VARIABLEXX_MAIN dd 0
_VALOR_MAIN dd 0
_OUT_MAIN_PROCESAR dd 0
_INOUT_MAIN_PROCESAR dd 0
_BASURA_MAIN dd 0
_GLOBAL_MAIN dd 0
_VARLOCAL_MAIN_FUNCION dd 0
_R1_MAIN dd 0
_R2_MAIN dd 0
_R3_MAIN dd 0
_P1_MAIN_FUNCION dd 0
_VARPARALAMBDA_MAIN dd 0
str_1 db "A:", 0
str_4 db "B:", 0
str_6 db "cadena @11", 13, 10, "    multilinea", 0
str_8 db "F1:", 0
str_11 db "F2:", 0
str_14 db "F3:", 0
str_17 db "X:", 0
str_20 db "XX:", 0
str_23 db "Y:", 0
str_26 db "Z:", 0
str_30 db "Z:", 0
str_32 db "X CON CONVERSION EXPLICITA:", 0
str_37 db "E:", 0
str_42 db "CONT:", 0
str_48 db "CONT:", 0
str_50 db "#ASIGNACION MULTIPLE IGUAL DE LADOS#", 0
str_53 db "A:", 0
str_55 db "B:", 0
str_57 db "IMPRIME AAAAA:", 0
str_61 db "A DENTRO DE FUNCZ", 0
str_64 db "W DE FUNCZ:", 0
str_67 db "J DE FUNCZ:", 0
str_70 db "A DE FUNCZ:", 0
str_73 db "W DE FUNCZ TOMA VALOR DE A DE FUNCZ:", 0
str_76 db "W DE FUNCZ TOMA VALOR DE MAIN.A:", 0
str_79 db "MAIN.A TOMA VALOR DE A:", 0
str_81 db "IMPRIMO A DE FUNCZ:", 0
str_87 db "W DE FUNCJ:", 0
str_90 db "P DE FUNCJ:", 0
str_94 db "A DE FUNCJ:", 0
str_97 db "W DE FUNCJ TOMA VALOR DE MAIN.A:", 0
str_100 db "FUNCZ.A TOMA VALOR DE W DE FUNCJ:", 0
str_107 db "por entrar", 0
str_109 db "entro", 0
str_115 db "por entrar dos", 0
str_117 db "entro", 0
str_123 db "PRINT GCONTADOR ", 0
str_131 db "PRINT AA ", 0
str_137 db "PRINT A ", 0
str_147 db "por entrar FUNCION IF", 0
str_149 db "ENTRO FUNCION IF", 0
str_158 db "IMPRIME BASURA 1", 0
str_165 db "IMPRIME BASURA 2", 0
str_167 db "IMPRIME VALOR", 0
str_170 db "GLOBAL:", 0
str_177 db "MAIN.GLOBAL:", 0
str_180 db "VARLOCAL:", 0
str_184 db "MAIN.GLOBAL:", 0
str_203 db "R1:", 0
str_205 db "R2:", 0
str_207 db "GLOBAL:", 0
.code
start:
Label0:
MOV EAX, 0
MOV _A_MAIN, EAX
Label1:
invoke crt_printf, addr MensajePrint, addr str_1
Label2:
invoke crt_printf, addr MensajePrintNum, _A_MAIN
Label3:
MOV EAX, 1
MOV _B_MAIN, EAX
Label4:
invoke crt_printf, addr MensajePrint, addr str_4
Label5:
invoke crt_printf, addr MensajePrintNum, _B_MAIN
Label6:
invoke crt_printf, addr MensajePrint, addr str_6
Label7:
MOV EAX, 1125515264
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FSTP _F1_MAIN
Label8:
invoke crt_printf, addr MensajePrint, addr str_8
Label9:
FLD _F1_MAIN
sub esp, 8
fstp qword ptr [esp]
push offset MensajePrintFloat
call crt_printf
add esp, 12
Label10:
MOV EAX, 1028443341
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FSTP _F2_MAIN
Label11:
invoke crt_printf, addr MensajePrint, addr str_11
Label12:
FLD _F2_MAIN
sub esp, 8
fstp qword ptr [esp]
push offset MensajePrintFloat
call crt_printf
add esp, 12
Label13:
MOV EAX, 1092616192
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FSTP _F3_MAIN
Label14:
invoke crt_printf, addr MensajePrint, addr str_14
Label15:
FLD _F3_MAIN
sub esp, 8
fstp qword ptr [esp]
push offset MensajePrintFloat
call crt_printf
add esp, 12
Label16:
MOV EAX, 10
MOV _X_MAIN, EAX
Label17:
invoke crt_printf, addr MensajePrint, addr str_17
Label18:
invoke crt_printf, addr MensajePrintNum, _X_MAIN
Label19:
MOV EAX, 1000
MOV _XX_MAIN, EAX
Label20:
invoke crt_printf, addr MensajePrint, addr str_20
Label21:
invoke crt_printf, addr MensajePrintNum, _XX_MAIN
Label22:
MOV EAX, 1103626240
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FSTP _Y_MAIN
Label23:
invoke crt_printf, addr MensajePrint, addr str_23
Label24:
FLD _Y_MAIN
sub esp, 8
fstp qword ptr [esp]
push offset MensajePrintFloat
call crt_printf
add esp, 12
Label25:
FLD _Y_MAIN
FSTP _Z_MAIN
Label26:
invoke crt_printf, addr MensajePrint, addr str_26
Label27:
FLD _Z_MAIN
sub esp, 8
fstp qword ptr [esp]
push offset MensajePrintFloat
call crt_printf
add esp, 12
Label28:
FLD _Y_MAIN
FISTP @aux28
Label29:
MOV EAX, @aux28
MOV _X_MAIN, EAX
Label30:
invoke crt_printf, addr MensajePrint, addr str_30
Label31:
FLD _Z_MAIN
sub esp, 8
fstp qword ptr [esp]
push offset MensajePrintFloat
call crt_printf
add esp, 12
Label32:
invoke crt_printf, addr MensajePrint, addr str_32
Label33:
invoke crt_printf, addr MensajePrintNum, _X_MAIN
Label34:
MOV EAX, 1103626240
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FISTP @aux34
Label35:
MOV EAX, 10
ADD EAX, @aux34
CMP EAX, 65535
JA ErrorOverflow
MOV @aux35, EAX
Label36:
MOV EAX, @aux35
MOV _E_MAIN, EAX
Label37:
invoke crt_printf, addr MensajePrint, addr str_37
Label38:
invoke crt_printf, addr MensajePrintNum, _E_MAIN
Label39:
MOV EAX, 0
MOV _CONT_MAIN, EAX
Label40:
MOV EAX, _CONT_MAIN
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux40, EAX
Label41:
MOV EAX, @aux40
MOV _CONT_MAIN, EAX
Label42:
invoke crt_printf, addr MensajePrint, addr str_42
Label43:
invoke crt_printf, addr MensajePrintNum, _CONT_MAIN
Label44:
MOV EAX, _CONT_MAIN
CMP EAX, 5
SETB AL
MOVZX EAX, AL
MOV @aux44, EAX
Label45:
MOV EAX, @aux44
CMP EAX, 1
JE Label40
Label46:
MOV EAX, _CONT_MAIN
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux46, EAX
Label47:
MOV EAX, @aux46
MOV _CONT_MAIN, EAX
Label48:
invoke crt_printf, addr MensajePrint, addr str_48
Label49:
invoke crt_printf, addr MensajePrintNum, _CONT_MAIN
Label50:
invoke crt_printf, addr MensajePrint, addr str_50
Label51:
MOV EAX, 20
MOV _B_MAIN, EAX
Label52:
MOV EAX, 10
MOV _A_MAIN, EAX
Label53:
invoke crt_printf, addr MensajePrint, addr str_53
Label54:
invoke crt_printf, addr MensajePrintNum, _A_MAIN
Label55:
invoke crt_printf, addr MensajePrint, addr str_55
Label56:
invoke crt_printf, addr MensajePrintNum, _B_MAIN
Label57:
invoke crt_printf, addr MensajePrint, addr str_57
Label58:
invoke crt_printf, addr MensajePrintNum, _A_MAIN
Label59:
JMP Label111
Label60:
__FUNCZ_MAIN:
Label61:
invoke crt_printf, addr MensajePrint, addr str_61
Label62:
invoke crt_printf, addr MensajePrintNum, _A_MAIN
Label63:
MOV EAX, 2
MOV _W_MAIN_FUNCZ, EAX
Label64:
invoke crt_printf, addr MensajePrint, addr str_64
Label65:
invoke crt_printf, addr MensajePrintNum, _W_MAIN_FUNCZ
Label66:
MOV EAX, 3
MOV _J_MAIN_FUNCZ, EAX
Label67:
invoke crt_printf, addr MensajePrint, addr str_67
Label68:
invoke crt_printf, addr MensajePrintNum, _J_MAIN_FUNCZ
Label69:
MOV EAX, 4
MOV _A_MAIN_FUNCZ, EAX
Label70:
invoke crt_printf, addr MensajePrint, addr str_70
Label71:
invoke crt_printf, addr MensajePrintNum, _A_MAIN_FUNCZ
Label72:
MOV EAX, _A_MAIN_FUNCZ
MOV _W_MAIN_FUNCZ, EAX
Label73:
invoke crt_printf, addr MensajePrint, addr str_73
Label74:
invoke crt_printf, addr MensajePrintNum, _W_MAIN_FUNCZ
Label75:
MOV EAX, _A_MAIN
MOV _W_MAIN_FUNCZ, EAX
Label76:
invoke crt_printf, addr MensajePrint, addr str_76
Label77:
invoke crt_printf, addr MensajePrintNum, _W_MAIN_FUNCZ
Label78:
MOV EAX, _A_MAIN_FUNCZ
MOV _A_MAIN, EAX
Label79:
invoke crt_printf, addr MensajePrint, addr str_79
Label80:
invoke crt_printf, addr MensajePrintNum, _A_MAIN
Label81:
invoke crt_printf, addr MensajePrint, addr str_81
Label82:
invoke crt_printf, addr MensajePrintNum, _A_MAIN_FUNCZ
Label83:
invoke crt_printf, addr MensajePrintNum, _error_tipo
Label84:
JMP Label103
Label85:
__FUNCJ_MAIN_FUNCZ:
Label86:
MOV EAX, 22
MOV _W_MAIN_FUNCZ_FUNCJ, EAX
Label87:
invoke crt_printf, addr MensajePrint, addr str_87
Label88:
invoke crt_printf, addr MensajePrintNum, _W_MAIN_FUNCZ_FUNCJ
Label89:
MOV EAX, 33
MOV _P_MAIN_FUNCZ_FUNCJ, EAX
Label90:
invoke crt_printf, addr MensajePrint, addr str_90
Label91:
invoke crt_printf, addr MensajePrintNum, _P_MAIN_FUNCZ_FUNCJ
Label92:
invoke crt_printf, addr MensajePrintNum, _error_tipo
Label93:
MOV EAX, 44
MOV _A_MAIN_FUNCZ_FUNCJ, EAX
Label94:
invoke crt_printf, addr MensajePrint, addr str_94
Label95:
invoke crt_printf, addr MensajePrintNum, _A_MAIN_FUNCZ_FUNCJ
Label96:
MOV EAX, _A_MAIN
MOV _W_MAIN_FUNCZ_FUNCJ, EAX
Label97:
invoke crt_printf, addr MensajePrint, addr str_97
Label98:
invoke crt_printf, addr MensajePrintNum, _W_MAIN_FUNCZ_FUNCJ
Label99:
MOV EAX, _W_MAIN_FUNCZ_FUNCJ
MOV _A_MAIN_FUNCZ, EAX
Label100:
invoke crt_printf, addr MensajePrint, addr str_100
Label101:
invoke crt_printf, addr MensajePrintNum, _W_MAIN_FUNCZ_FUNCJ
Label102:
; -- RETURN --
MOV EAX, 55
MOV _RET_VAL_0, EAX
RET
Label103:
MOV EAX, 0
MOV _VARIABLEOTRA_MAIN_FUNCZ, EAX
Label104:
MOV EAX, _VARIABLEOTRA_MAIN_FUNCZ
MOV _J_MAIN_FUNCZ_FUNCJ, EAX
Label105:
CALL __FUNCJ_MAIN_FUNCZ
MOV @aux105, EAX
Label106:
MOV EAX, @aux105
MOV _VARIABLEOTRA_MAIN_FUNCZ, EAX
Label107:
invoke crt_printf, addr MensajePrint, addr str_107
Label108:
invoke crt_printf, addr MensajePrintNum, _VARIABLEOTRA_MAIN_FUNCZ
Label109:
invoke crt_printf, addr MensajePrint, addr str_109
Label110:
; -- RETURN --
MOV EAX, 5
MOV _RET_VAL_0, EAX
RET
Label111:
MOV EAX, 0
MOV _VARIABLEZ_MAIN, EAX
Label112:
MOV EAX, _VARIABLEZ_MAIN
MOV _Z_MAIN_FUNCZ, EAX
Label113:
CALL __FUNCZ_MAIN
MOV @aux113, EAX
Label114:
MOV EAX, @aux113
MOV _VARIABLEZ_MAIN, EAX
Label115:
invoke crt_printf, addr MensajePrint, addr str_115
Label116:
invoke crt_printf, addr MensajePrintNum, _VARIABLEZ_MAIN
Label117:
invoke crt_printf, addr MensajePrint, addr str_117
Label118:
JMP Label142
Label119:
__FUNCIONX_MAIN:
Label120:
MOV EAX, 3
MOV _AA_MAIN_FUNCIONX, EAX
Label121:
MOV EAX, 3
MOV _A_MAIN_FUNCIONX, EAX
Label122:
MOV EAX, 0
MOV _GCONTADOR_MAIN_FUNCIONX, EAX
Label123:
invoke crt_printf, addr MensajePrint, addr str_123
Label124:
invoke crt_printf, addr MensajePrintNum, _GCONTADOR_MAIN_FUNCIONX
Label125:
MOV EAX, _GCONTADOR_MAIN_FUNCIONX
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux125, EAX
Label126:
MOV EAX, @aux125
MOV _GCONTADOR_MAIN_FUNCIONX, EAX
Label127:
MOV EAX, _GCONTADOR_MAIN_FUNCIONX
CMP EAX, 3
SETB AL
MOVZX EAX, AL
MOV @aux127, EAX
Label128:
MOV EAX, @aux127
CMP EAX, 1
JE Label123
Label129:
MOV EAX, _AA_MAIN_FUNCIONX
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
invoke crt_printf, addr MensajePrintNum, _AA_MAIN_FUNCIONX
Label133:
JMP Label135
Label134:
invoke crt_printf, addr MensajePrintNum, 100
Label135:
MOV EAX, _A_MAIN_FUNCIONX
CMP EAX, 5
SETA AL
MOVZX EAX, AL
MOV @aux135, EAX
Label136:
MOV EAX, @aux135
CMP EAX, 0
JE Label140
Label137:
invoke crt_printf, addr MensajePrint, addr str_137
Label138:
invoke crt_printf, addr MensajePrintNum, _A_MAIN_FUNCIONX
Label139:
JMP Label141
Label140:
invoke crt_printf, addr MensajePrintNum, 100
Label141:
; -- RETURN --
MOV EAX, 1000
MOV _RET_VAL_0, EAX
RET
Label142:
invoke crt_printf, addr MensajePrintNum, _error_tipo
Label143:
MOV EAX, 0
MOV _VARIABLEXX_MAIN, EAX
Label144:
MOV EAX, _VARIABLEXX_MAIN
MOV _AA_MAIN_FUNCIONX, EAX
Label145:
CALL __FUNCIONX_MAIN
MOV @aux145, EAX
Label146:
MOV EAX, @aux145
MOV _VARIABLEXX_MAIN, EAX
Label147:
invoke crt_printf, addr MensajePrint, addr str_147
Label148:
invoke crt_printf, addr MensajePrintNum, _VARIABLEXX_MAIN
Label149:
invoke crt_printf, addr MensajePrint, addr str_149
Label150:
MOV EAX, 10
MOV _VALOR_MAIN, EAX
Label151:
JMP Label157
Label152:
__PROCESAR_MAIN:
Label153:
MOV EAX, 99
MOV _OUT_MAIN_PROCESAR, EAX
Label154:
MOV EAX, _INOUT_MAIN_PROCESAR
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux154, EAX
Label155:
MOV EAX, @aux154
MOV _INOUT_MAIN_PROCESAR, EAX
Label156:
; -- RETURN --
MOV EAX, 1
MOV _RET_VAL_0, EAX
RET
Label157:
MOV EAX, 0
MOV _BASURA_MAIN, EAX
Label158:
invoke crt_printf, addr MensajePrint, addr str_158
Label159:
invoke crt_printf, addr MensajePrintNum, _BASURA_MAIN
Label160:
MOV EAX, _BASURA_MAIN
MOV _OUT_MAIN_PROCESAR, EAX
Label161:
MOV EAX, _VALOR_MAIN
MOV _INOUT_MAIN_PROCESAR, EAX
Label162:
CALL __PROCESAR_MAIN
MOV @aux162, EAX
Label163:
MOV EAX, _OUT_MAIN_PROCESAR
MOV _BASURA_MAIN, EAX
Label164:
MOV EAX, _INOUT_MAIN_PROCESAR
MOV _VALOR_MAIN, EAX
Label165:
invoke crt_printf, addr MensajePrint, addr str_165
Label166:
invoke crt_printf, addr MensajePrintNum, _BASURA_MAIN
Label167:
invoke crt_printf, addr MensajePrint, addr str_167
Label168:
invoke crt_printf, addr MensajePrintNum, _VALOR_MAIN
Label169:
MOV EAX, 100
MOV _GLOBAL_MAIN, EAX
Label170:
invoke crt_printf, addr MensajePrint, addr str_170
Label171:
invoke crt_printf, addr MensajePrintNum, _GLOBAL_MAIN
Label172:
JMP Label188
Label173:
__FUNCION_MAIN:
Label174:
MOV EAX, 200
MOV _VARLOCAL_MAIN_FUNCION, EAX
Label175:
MOV EAX, _GLOBAL_MAIN
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux175, EAX
Label176:
MOV EAX, @aux175
MOV _GLOBAL_MAIN, EAX
Label177:
invoke crt_printf, addr MensajePrint, addr str_177
Label178:
invoke crt_printf, addr MensajePrintNum, _GLOBAL_MAIN
Label179:
MOV EAX, _GLOBAL_MAIN
MOV _VARLOCAL_MAIN_FUNCION, EAX
Label180:
invoke crt_printf, addr MensajePrint, addr str_180
Label181:
invoke crt_printf, addr MensajePrintNum, _VARLOCAL_MAIN_FUNCION
Label182:
MOV EAX, _VARLOCAL_MAIN_FUNCION
ADD EAX, 3
CMP EAX, 65535
JA ErrorOverflow
MOV @aux182, EAX
Label183:
MOV EAX, @aux182
MOV _GLOBAL_MAIN, EAX
Label184:
invoke crt_printf, addr MensajePrint, addr str_184
Label185:
invoke crt_printf, addr MensajePrintNum, _GLOBAL_MAIN
Label186:
; -- RETURN --
MOV EAX, 12
MOV _RET_VAL_0, EAX
Label187:
; -- RETURN --
MOV EAX, 12
MOV _RET_VAL_1, EAX
RET
Label188:
MOV EAX, 0
MOV _R1_MAIN, EAX
Label189:
MOV EAX, 0
MOV _R2_MAIN, EAX
Label190:
MOV EAX, 0
MOV _R3_MAIN, EAX
Label191:
MOV EAX, 50
MOV _P1_MAIN_FUNCION, EAX
Label192:
CALL __FUNCION_MAIN
MOV @aux192, EAX
Label193:
MOV EAX, _RET_VAL_0
MOV @aux193, EAX
Label194:
MOV EAX, @aux193
MOV _R1_MAIN, EAX
Label195:
MOV EAX, 50
MOV _P1_MAIN_FUNCION, EAX
Label196:
CALL __FUNCION_MAIN
MOV @aux196, EAX
Label197:
MOV EAX, 50
MOV _P1_MAIN_FUNCION, EAX
Label198:
CALL __FUNCION_MAIN
MOV @aux198, EAX
Label199:
MOV EAX, _RET_VAL_0
MOV @aux199, EAX
Label200:
MOV EAX, @aux199
MOV _R1_MAIN, EAX
Label201:
MOV EAX, _RET_VAL_1
MOV @aux201, EAX
Label202:
MOV EAX, @aux201
MOV _R2_MAIN, EAX
Label203:
invoke crt_printf, addr MensajePrint, addr str_203
Label204:
invoke crt_printf, addr MensajePrintNum, _R1_MAIN
Label205:
invoke crt_printf, addr MensajePrint, addr str_205
Label206:
invoke crt_printf, addr MensajePrintNum, _R2_MAIN
Label207:
invoke crt_printf, addr MensajePrint, addr str_207
Label208:
invoke crt_printf, addr MensajePrintNum, _GLOBAL_MAIN
Label209:
MOV EAX, 5
MOV _VARPARALAMBDA_MAIN, EAX
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
