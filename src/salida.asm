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
_A_MAIN dd 0
_B_MAIN dd 0
_F1_MAIN dd 0
_F2_MAIN dd 0
_F3_MAIN dd 0
_X_MAIN dd 0
_Y_MAIN dd 0
_Z_MAIN dd 0
_E_MAIN dd 0
_CONT_MAIN dd 0
_W_MAIN_FUNCZ dd 0
_J_MAIN_FUNCZ dd 0
_A_MAIN_FUNCZ dd 0
_W_MAIN_FUNCZ_FUNCJ dd 0
_P_MAIN_FUNCZ_FUNCJ dd 0
_A_MAIN_FUNCZ_FUNCJ dd 0
_VARIABLEFERBO_MAIN_FUNCZ dd 0
_VARIABLEZ_MAIN dd 0
_AA_MAIN_FUNCIONX dd 0
_A_MAIN_FUNCIONX dd 0
_GCONTADOR_MAIN_FUNCIONX dd 0
_VALOR_MAIN dd 0
_OUT_MAIN_PROCESAR dd 0
_INOUT_MAIN_PROCESAR dd 0
_BASURA_MAIN dd 0
_OUT dd 0
_INOUT dd 0
_SS_MAIN_F dd 0
_X_MAIN_F dd 0
_XX_MAIN_lambda_154 dd 0
_SS dd 0
_MAX_MAIN dd 0
_OVER_MAIN dd 0
_GLOBAL_MAIN dd 0
_VARLOCAL_MAIN_FUNCION dd 0
_P1_MAIN_FUNCION dd 0
_R1_MAIN dd 0
_R2_MAIN dd 0
_P1 dd 0
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
str_66 db "MAIN.A TOMA VALOR DE A:", 0
str_68 db "IMPRIMO A DE FUNCZ:", 0
str_73 db "W DE FUNCJ:", 0
str_76 db "P DE FUNCJ:", 0
str_79 db "A DE FUNCJ:", 0
str_82 db "W DE FUNCJ TOMA VALOR DE MAIN.A:", 0
str_85 db "FUNCZ.A TOMA VALOR DE W DE FUNCJ:", 0
str_92 db "por entrar", 0
str_94 db "entro", 0
str_100 db "por entrar dos", 0
str_102 db "entro toda", 0
str_108 db "PRINT GCONTADOR ", 0
str_116 db "PRINT AA ", 0
str_122 db "PRINT A ", 0
str_126 db "PRINT FUNCIONX.A ", 0
str_131 db "chau chau", 0
str_139 db "chau chau", 0
str_157 db " PRINT AA ", 0
str_167 db "GLOBAL:", 0
str_174 db "MAIN.GLOBAL:", 0
str_177 db "VARLOCAL:", 0
str_181 db "MAIN.GLOBAL:", 0
str_189 db "R1:", 0
str_191 db "GLOBAL:", 0
str_193 db "Si ves esto, el control de overflow fallo", 0
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
MOV EAX, 65535
MOV _B_MAIN, EAX
Label4:
invoke crt_printf, addr MensajePrint, addr str_4
Label5:
invoke crt_printf, addr MensajePrintNum, _B_MAIN
Label6:
MOV EAX, 1125515264
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FSTP _F1_MAIN
Label7:
invoke crt_printf, addr MensajePrint, addr str_7
Label8:
FLD _F1_MAIN
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
FSTP _F2_MAIN
Label10:
invoke crt_printf, addr MensajePrint, addr str_10
Label11:
FLD _F2_MAIN
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
FSTP _F3_MAIN
Label13:
invoke crt_printf, addr MensajePrint, addr str_13
Label14:
FLD _F3_MAIN
sub esp, 8
fstp qword ptr [esp]
push offset MensajePrintFloat
call crt_printf
add esp, 12
Label15:
invoke crt_printf, addr MensajePrint, addr str_15
Label16:
MOV EAX, 10
MOV _X_MAIN, EAX
Label17:
invoke crt_printf, addr MensajePrint, addr str_17
Label18:
invoke crt_printf, addr MensajePrintNum, _X_MAIN
Label19:
MOV EAX, 1103626240
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FSTP _Y_MAIN
Label20:
invoke crt_printf, addr MensajePrint, addr str_20
Label21:
FLD _Y_MAIN
sub esp, 8
fstp qword ptr [esp]
push offset MensajePrintFloat
call crt_printf
add esp, 12
Label22:
FLD _Y_MAIN
FSTP _Z_MAIN
Label23:
invoke crt_printf, addr MensajePrint, addr str_23
Label24:
FLD _Z_MAIN
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
MOV _E_MAIN, EAX
Label28:
invoke crt_printf, addr MensajePrint, addr str_28
Label29:
invoke crt_printf, addr MensajePrintNum, _E_MAIN
Label30:
MOV EAX, 0
MOV _CONT_MAIN, EAX
Label31:
MOV EAX, _CONT_MAIN
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux31, EAX
Label32:
MOV EAX, @aux31
MOV _CONT_MAIN, EAX
Label33:
invoke crt_printf, addr MensajePrint, addr str_33
Label34:
invoke crt_printf, addr MensajePrintNum, _CONT_MAIN
Label35:
MOV EAX, _CONT_MAIN
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
MOV _B_MAIN, EAX
Label39:
MOV EAX, 10
MOV _A_MAIN, EAX
Label40:
invoke crt_printf, addr MensajePrint, addr str_40
Label41:
invoke crt_printf, addr MensajePrintNum, _A_MAIN
Label42:
invoke crt_printf, addr MensajePrint, addr str_42
Label43:
invoke crt_printf, addr MensajePrintNum, _B_MAIN
Label44:
invoke crt_printf, addr MensajePrint, addr str_44
Label45:
invoke crt_printf, addr MensajePrintNum, _A_MAIN
Label46:
JMP Label96
Label47:
__FUNCZ_MAIN:
Label48:
invoke crt_printf, addr MensajePrint, addr str_48
Label49:
invoke crt_printf, addr MensajePrintNum, _A_MAIN
Label50:
MOV EAX, 2
MOV _W_MAIN_FUNCZ, EAX
Label51:
invoke crt_printf, addr MensajePrint, addr str_51
Label52:
invoke crt_printf, addr MensajePrintNum, _W_MAIN_FUNCZ
Label53:
MOV EAX, 3
MOV _J_MAIN_FUNCZ, EAX
Label54:
invoke crt_printf, addr MensajePrint, addr str_54
Label55:
invoke crt_printf, addr MensajePrintNum, _J_MAIN_FUNCZ
Label56:
MOV EAX, 4
MOV _A_MAIN_FUNCZ, EAX
Label57:
invoke crt_printf, addr MensajePrint, addr str_57
Label58:
invoke crt_printf, addr MensajePrintNum, _A_MAIN_FUNCZ
Label59:
MOV EAX, _A_MAIN_FUNCZ
MOV _W_MAIN_FUNCZ, EAX
Label60:
invoke crt_printf, addr MensajePrint, addr str_60
Label61:
invoke crt_printf, addr MensajePrintNum, _W_MAIN_FUNCZ
Label62:
MOV EAX, _A_MAIN
MOV _W_MAIN_FUNCZ, EAX
Label63:
invoke crt_printf, addr MensajePrint, addr str_63
Label64:
invoke crt_printf, addr MensajePrintNum, _W_MAIN_FUNCZ
Label65:
MOV EAX, _A_MAIN_FUNCZ
MOV _A_MAIN, EAX
Label66:
invoke crt_printf, addr MensajePrint, addr str_66
Label67:
invoke crt_printf, addr MensajePrintNum, _A_MAIN
Label68:
invoke crt_printf, addr MensajePrint, addr str_68
Label69:
invoke crt_printf, addr MensajePrintNum, _A_MAIN_FUNCZ
Label70:
JMP Label88
Label71:
__FUNCJ_MAIN_FUNCZ:
Label72:
MOV EAX, 22
MOV _W_MAIN_FUNCZ_FUNCJ, EAX
Label73:
invoke crt_printf, addr MensajePrint, addr str_73
Label74:
invoke crt_printf, addr MensajePrintNum, _W_MAIN_FUNCZ_FUNCJ
Label75:
MOV EAX, 33
MOV _P_MAIN_FUNCZ_FUNCJ, EAX
Label76:
invoke crt_printf, addr MensajePrint, addr str_76
Label77:
invoke crt_printf, addr MensajePrintNum, _P_MAIN_FUNCZ_FUNCJ
Label78:
MOV EAX, 44
MOV _A_MAIN_FUNCZ_FUNCJ, EAX
Label79:
invoke crt_printf, addr MensajePrint, addr str_79
Label80:
invoke crt_printf, addr MensajePrintNum, _A_MAIN_FUNCZ_FUNCJ
Label81:
MOV EAX, _A_MAIN
MOV _W_MAIN_FUNCZ_FUNCJ, EAX
Label82:
invoke crt_printf, addr MensajePrint, addr str_82
Label83:
invoke crt_printf, addr MensajePrintNum, _W_MAIN_FUNCZ_FUNCJ
Label84:
MOV EAX, _W_MAIN_FUNCZ_FUNCJ
MOV _A_MAIN_FUNCZ, EAX
Label85:
invoke crt_printf, addr MensajePrint, addr str_85
Label86:
invoke crt_printf, addr MensajePrintNum, _W_MAIN_FUNCZ_FUNCJ
Label87:
RET
Label88:
MOV EAX, 0
MOV _VARIABLEFERBO_MAIN_FUNCZ, EAX
Label89:
MOV EAX, _VARIABLEFERBO_MAIN_FUNCZ
MOV _J_MAIN_FUNCZ, EAX
Label90:
CALL __FUNCJ_MAIN_FUNCZ
MOV @aux90, EAX
Label91:
MOV EAX, @aux90
MOV _VARIABLEFERBO_MAIN_FUNCZ, EAX
Label92:
invoke crt_printf, addr MensajePrint, addr str_92
Label93:
invoke crt_printf, addr MensajePrintNum, _VARIABLEFERBO_MAIN_FUNCZ
Label94:
invoke crt_printf, addr MensajePrint, addr str_94
Label95:
RET
Label96:
MOV EAX, 0
MOV _VARIABLEZ_MAIN, EAX
Label97:
MOV EAX, _VARIABLEZ_MAIN
MOV _Z_MAIN, EAX
Label98:
CALL __FUNCZ_MAIN
MOV @aux98, EAX
Label99:
MOV EAX, @aux98
MOV _VARIABLEZ_MAIN, EAX
Label100:
invoke crt_printf, addr MensajePrint, addr str_100
Label101:
invoke crt_printf, addr MensajePrintNum, _VARIABLEZ_MAIN
Label102:
invoke crt_printf, addr MensajePrint, addr str_102
Label103:
JMP Label131
Label104:
__FUNCIONX_MAIN:
Label105:
MOV EAX, 3
MOV _AA_MAIN_FUNCIONX, EAX
Label106:
MOV EAX, 3
MOV _A_MAIN_FUNCIONX, EAX
Label107:
MOV EAX, 0
MOV _GCONTADOR_MAIN_FUNCIONX, EAX
Label108:
invoke crt_printf, addr MensajePrint, addr str_108
Label109:
invoke crt_printf, addr MensajePrintNum, _GCONTADOR_MAIN_FUNCIONX
Label110:
MOV EAX, _GCONTADOR_MAIN_FUNCIONX
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux110, EAX
Label111:
MOV EAX, @aux110
MOV _GCONTADOR_MAIN_FUNCIONX, EAX
Label112:
MOV EAX, _GCONTADOR_MAIN_FUNCIONX
CMP EAX, 3
SETB AL
MOVZX EAX, AL
MOV @aux112, EAX
Label113:
MOV EAX, @aux112
CMP EAX, 1
JE Label108
Label114:
MOV EAX, _AA_MAIN_FUNCIONX
CMP EAX, 5
SETB AL
MOVZX EAX, AL
MOV @aux114, EAX
Label115:
MOV EAX, @aux114
CMP EAX, 0
JE Label119
Label116:
invoke crt_printf, addr MensajePrint, addr str_116
Label117:
invoke crt_printf, addr MensajePrintNum, _A_MAIN_FUNCIONX
Label118:
JMP Label120
Label119:
invoke crt_printf, addr MensajePrintNum, 100
Label120:
MOV EAX, _A_MAIN_FUNCIONX
CMP EAX, 5
SETB AL
MOVZX EAX, AL
MOV @aux120, EAX
Label121:
MOV EAX, @aux120
CMP EAX, 0
JE Label124
Label122:
invoke crt_printf, addr MensajePrint, addr str_122
Label123:
invoke crt_printf, addr MensajePrintNum, _A_MAIN_FUNCIONX
Label124:
MOV EAX, _A_MAIN_FUNCIONX
CMP EAX, 5
SETB AL
MOVZX EAX, AL
MOV @aux124, EAX
Label125:
MOV EAX, @aux124
CMP EAX, 0
JE Label129
Label126:
invoke crt_printf, addr MensajePrint, addr str_126
Label127:
invoke crt_printf, addr MensajePrintNum, _A_MAIN_FUNCIONX
Label128:
JMP Label130
Label129:
invoke crt_printf, addr MensajePrintNum, 100
Label130:
RET
Label131:
invoke crt_printf, addr MensajePrint, addr str_131
Label132:
MOV EAX, 10
MOV _VALOR_MAIN, EAX
Label133:
JMP Label139
Label134:
__PROCESAR_MAIN:
Label135:
MOV EAX, 99
MOV _OUT_MAIN_PROCESAR, EAX
Label136:
MOV EAX, _INOUT_MAIN_PROCESAR
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux136, EAX
Label137:
MOV EAX, @aux136
MOV _INOUT_MAIN_PROCESAR, EAX
Label138:
RET
Label139:
invoke crt_printf, addr MensajePrint, addr str_139
Label140:
MOV EAX, 0
MOV _BASURA_MAIN, EAX
Label141:
invoke crt_printf, addr MensajePrintNum, _BASURA_MAIN
Label142:
MOV EAX, _BASURA_MAIN
MOV _OUT, EAX
Label143:
MOV EAX, _VALOR_MAIN
MOV _INOUT, EAX
Label144:
CALL __PROCESAR_MAIN
MOV @aux144, EAX
Label145:
MOV EAX, _OUT
MOV _BASURA_MAIN, EAX
Label146:
MOV EAX, _INOUT
MOV _VALOR_MAIN, EAX
Label147:
invoke crt_printf, addr MensajePrintNum, _BASURA_MAIN
Label148:
invoke crt_printf, addr MensajePrintNum, _VALOR_MAIN
Label149:
JMP Label153
Label150:
__F_MAIN:
Label151:
Label152:
MOV EAX, _X_MAIN_F
CALL EAX
Label153:
JMP Label159
Label154:
Label155:
MOV EAX, _XX_MAIN_lambda_154
CMP EAX, 1
SETA AL
MOVZX EAX, AL
MOV @aux155, EAX
Label156:
MOV EAX, @aux155
CMP EAX, 0
JE Label158
Label157:
invoke crt_printf, addr MensajePrint, addr str_157
Label158:
RET
Label159:
MOV EAX, Label154
MOV _X_MAIN, EAX
Label160:
MOV EAX, 3
MOV _SS, EAX
Label161:
CALL __F_MAIN
MOV @aux161, EAX
Label162:
MOV EAX, 0
MOV _A_MAIN, EAX
Label163:
MOV EAX, 65535
MOV _MAX_MAIN, EAX
Label164:
MOV EAX, _MAX_MAIN
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux164, EAX
Label165:
MOV EAX, @aux164
MOV _OVER_MAIN, EAX
Label166:
MOV EAX, 100
MOV _GLOBAL_MAIN, EAX
Label167:
invoke crt_printf, addr MensajePrint, addr str_167
Label168:
invoke crt_printf, addr MensajePrintNum, _GLOBAL_MAIN
Label169:
JMP Label185
Label170:
__FUNCION_MAIN:
Label171:
MOV EAX, 200
MOV _VARLOCAL_MAIN_FUNCION, EAX
Label172:
MOV EAX, _GLOBAL_MAIN
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux172, EAX
Label173:
MOV EAX, @aux172
MOV _GLOBAL_MAIN, EAX
Label174:
invoke crt_printf, addr MensajePrint, addr str_174
Label175:
invoke crt_printf, addr MensajePrintNum, _GLOBAL_MAIN
Label176:
MOV EAX, _GLOBAL_MAIN
MOV _VARLOCAL_MAIN_FUNCION, EAX
Label177:
invoke crt_printf, addr MensajePrint, addr str_177
Label178:
invoke crt_printf, addr MensajePrintNum, _VARLOCAL_MAIN_FUNCION
Label179:
MOV EAX, _VARLOCAL_MAIN_FUNCION
ADD EAX, 3
CMP EAX, 65535
JA ErrorOverflow
MOV @aux179, EAX
Label180:
MOV EAX, @aux179
MOV _GLOBAL_MAIN, EAX
Label181:
invoke crt_printf, addr MensajePrint, addr str_181
Label182:
invoke crt_printf, addr MensajePrintNum, _GLOBAL_MAIN
Label183:
RET
Label184:
RET
Label185:
MOV EAX, 0
MOV _R1_MAIN, EAX
Label186:
MOV EAX, 0
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FSTP _R2_MAIN
Label187:
MOV EAX, 50
MOV _P1, EAX
Label188:
CALL __FUNCION_MAIN
MOV @aux188, EAX
Label189:
invoke crt_printf, addr MensajePrint, addr str_189
Label190:
invoke crt_printf, addr MensajePrintNum, _R1_MAIN
Label191:
invoke crt_printf, addr MensajePrint, addr str_191
Label192:
invoke crt_printf, addr MensajePrintNum, _GLOBAL_MAIN
Label193:
invoke crt_printf, addr MensajePrint, addr str_193
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
