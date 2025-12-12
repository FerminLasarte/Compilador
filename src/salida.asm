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
str_6 db "cadena @Numero de linea: 11", 13, 10, "    multilinea", 0
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
str_44 db "CONT:", 0
str_46 db "#ASIGNACION MULTIPLE IGUAL DE LADOS#", 0
str_49 db "A:", 0
str_51 db "B:", 0
str_53 db "IMPRIME AAAAA:", 0
str_57 db "A DENTRO DE FUNCZ", 0
str_60 db "W DE FUNCZ:", 0
str_63 db "J DE FUNCZ:", 0
str_66 db "A DE FUNCZ:", 0
str_69 db "W DE FUNCZ TOMA VALOR DE A DE FUNCZ:", 0
str_72 db "W DE FUNCZ TOMA VALOR DE MAIN.A:", 0
str_75 db "MAIN.A TOMA VALOR DE A:", 0
str_77 db "IMPRIMO A DE FUNCZ:", 0
str_83 db "W DE FUNCJ:", 0
str_86 db "P DE FUNCJ:", 0
str_90 db "A DE FUNCJ:", 0
str_93 db "W DE FUNCJ TOMA VALOR DE MAIN.A:", 0
str_96 db "FUNCZ.A TOMA VALOR DE W DE FUNCJ:", 0
str_103 db "por entrar", 0
str_105 db "entro", 0
str_111 db "por entrar dos", 0
str_113 db "entro", 0
str_119 db "PRINT GCONTADOR ", 0
str_127 db "PRINT AA ", 0
str_133 db "PRINT A ", 0
str_143 db "por entrar FUNCION IF", 0
str_145 db "ENTRO FUNCION IF", 0
str_154 db "IMPRIME BASURA 1", 0
str_161 db "IMPRIME BASURA 2", 0
str_163 db "IMPRIME VALOR", 0
str_166 db "GLOBAL:", 0
str_173 db "MAIN.GLOBAL:", 0
str_176 db "VARLOCAL:", 0
str_180 db "MAIN.GLOBAL:", 0
str_199 db "R1:", 0
str_201 db "R2:", 0
str_203 db "GLOBAL:", 0
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
CMP EAX, 5
SETB AL
MOVZX EAX, AL
MOV @aux40, EAX
Label41:
MOV EAX, @aux40
CMP EAX, 1
JE Label40
Label42:
MOV EAX, _CONT_MAIN
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux42, EAX
Label43:
MOV EAX, @aux42
MOV _CONT_MAIN, EAX
Label44:
invoke crt_printf, addr MensajePrint, addr str_44
Label45:
invoke crt_printf, addr MensajePrintNum, _CONT_MAIN
Label46:
invoke crt_printf, addr MensajePrint, addr str_46
Label47:
MOV EAX, 20
MOV _B_MAIN, EAX
Label48:
MOV EAX, 10
MOV _A_MAIN, EAX
Label49:
invoke crt_printf, addr MensajePrint, addr str_49
Label50:
invoke crt_printf, addr MensajePrintNum, _A_MAIN
Label51:
invoke crt_printf, addr MensajePrint, addr str_51
Label52:
invoke crt_printf, addr MensajePrintNum, _B_MAIN
Label53:
invoke crt_printf, addr MensajePrint, addr str_53
Label54:
invoke crt_printf, addr MensajePrintNum, _A_MAIN
Label55:
JMP Label107
Label56:
__FUNCZ_MAIN:
Label57:
invoke crt_printf, addr MensajePrint, addr str_57
Label58:
invoke crt_printf, addr MensajePrintNum, _A_MAIN
Label59:
MOV EAX, 2
MOV _W_MAIN_FUNCZ, EAX
Label60:
invoke crt_printf, addr MensajePrint, addr str_60
Label61:
invoke crt_printf, addr MensajePrintNum, _W_MAIN_FUNCZ
Label62:
MOV EAX, 3
MOV _J_MAIN_FUNCZ, EAX
Label63:
invoke crt_printf, addr MensajePrint, addr str_63
Label64:
invoke crt_printf, addr MensajePrintNum, _J_MAIN_FUNCZ
Label65:
MOV EAX, 4
MOV _A_MAIN_FUNCZ, EAX
Label66:
invoke crt_printf, addr MensajePrint, addr str_66
Label67:
invoke crt_printf, addr MensajePrintNum, _A_MAIN_FUNCZ
Label68:
MOV EAX, _A_MAIN_FUNCZ
MOV _W_MAIN_FUNCZ, EAX
Label69:
invoke crt_printf, addr MensajePrint, addr str_69
Label70:
invoke crt_printf, addr MensajePrintNum, _W_MAIN_FUNCZ
Label71:
MOV EAX, _A_MAIN
MOV _W_MAIN_FUNCZ, EAX
Label72:
invoke crt_printf, addr MensajePrint, addr str_72
Label73:
invoke crt_printf, addr MensajePrintNum, _W_MAIN_FUNCZ
Label74:
MOV EAX, _A_MAIN_FUNCZ
MOV _A_MAIN, EAX
Label75:
invoke crt_printf, addr MensajePrint, addr str_75
Label76:
invoke crt_printf, addr MensajePrintNum, _A_MAIN
Label77:
invoke crt_printf, addr MensajePrint, addr str_77
Label78:
invoke crt_printf, addr MensajePrintNum, _A_MAIN_FUNCZ
Label79:
invoke crt_printf, addr MensajePrintNum, _error_tipo
Label80:
JMP Label99
Label81:
__FUNCJ_MAIN_FUNCZ:
Label82:
MOV EAX, 22
MOV _W_MAIN_FUNCZ_FUNCJ, EAX
Label83:
invoke crt_printf, addr MensajePrint, addr str_83
Label84:
invoke crt_printf, addr MensajePrintNum, _W_MAIN_FUNCZ_FUNCJ
Label85:
MOV EAX, 33
MOV _P_MAIN_FUNCZ_FUNCJ, EAX
Label86:
invoke crt_printf, addr MensajePrint, addr str_86
Label87:
invoke crt_printf, addr MensajePrintNum, _P_MAIN_FUNCZ_FUNCJ
Label88:
invoke crt_printf, addr MensajePrintNum, _error_tipo
Label89:
MOV EAX, 44
MOV _A_MAIN_FUNCZ_FUNCJ, EAX
Label90:
invoke crt_printf, addr MensajePrint, addr str_90
Label91:
invoke crt_printf, addr MensajePrintNum, _A_MAIN_FUNCZ_FUNCJ
Label92:
MOV EAX, _A_MAIN
MOV _W_MAIN_FUNCZ_FUNCJ, EAX
Label93:
invoke crt_printf, addr MensajePrint, addr str_93
Label94:
invoke crt_printf, addr MensajePrintNum, _W_MAIN_FUNCZ_FUNCJ
Label95:
MOV EAX, _W_MAIN_FUNCZ_FUNCJ
MOV _A_MAIN_FUNCZ, EAX
Label96:
invoke crt_printf, addr MensajePrint, addr str_96
Label97:
invoke crt_printf, addr MensajePrintNum, _W_MAIN_FUNCZ_FUNCJ
Label98:
; -- RETURN --
MOV EAX, 55
MOV _RET_VAL_0, EAX
RET
Label99:
MOV EAX, 0
MOV _VARIABLEOTRA_MAIN_FUNCZ, EAX
Label100:
MOV EAX, _VARIABLEOTRA_MAIN_FUNCZ
MOV _J_MAIN_FUNCZ_FUNCJ, EAX
Label101:
CALL __FUNCJ_MAIN_FUNCZ
MOV @aux101, EAX
Label102:
MOV EAX, @aux101
MOV _VARIABLEOTRA_MAIN_FUNCZ, EAX
Label103:
invoke crt_printf, addr MensajePrint, addr str_103
Label104:
invoke crt_printf, addr MensajePrintNum, _VARIABLEOTRA_MAIN_FUNCZ
Label105:
invoke crt_printf, addr MensajePrint, addr str_105
Label106:
; -- RETURN --
MOV EAX, 5
MOV _RET_VAL_0, EAX
RET
Label107:
MOV EAX, 0
MOV _VARIABLEZ_MAIN, EAX
Label108:
MOV EAX, _VARIABLEZ_MAIN
MOV _Z_MAIN_FUNCZ, EAX
Label109:
CALL __FUNCZ_MAIN
MOV @aux109, EAX
Label110:
MOV EAX, @aux109
MOV _VARIABLEZ_MAIN, EAX
Label111:
invoke crt_printf, addr MensajePrint, addr str_111
Label112:
invoke crt_printf, addr MensajePrintNum, _VARIABLEZ_MAIN
Label113:
invoke crt_printf, addr MensajePrint, addr str_113
Label114:
JMP Label138
Label115:
__FUNCIONX_MAIN:
Label116:
MOV EAX, 3
MOV _AA_MAIN_FUNCIONX, EAX
Label117:
MOV EAX, 3
MOV _A_MAIN_FUNCIONX, EAX
Label118:
MOV EAX, 0
MOV _GCONTADOR_MAIN_FUNCIONX, EAX
Label119:
invoke crt_printf, addr MensajePrint, addr str_119
Label120:
invoke crt_printf, addr MensajePrintNum, _GCONTADOR_MAIN_FUNCIONX
Label121:
MOV EAX, _GCONTADOR_MAIN_FUNCIONX
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux121, EAX
Label122:
MOV EAX, @aux121
MOV _GCONTADOR_MAIN_FUNCIONX, EAX
Label123:
MOV EAX, _GCONTADOR_MAIN_FUNCIONX
CMP EAX, 3
SETB AL
MOVZX EAX, AL
MOV @aux123, EAX
Label124:
MOV EAX, @aux123
CMP EAX, 1
JE Label119
Label125:
MOV EAX, _AA_MAIN_FUNCIONX
CMP EAX, 5
SETB AL
MOVZX EAX, AL
MOV @aux125, EAX
Label126:
MOV EAX, @aux125
CMP EAX, 0
JE Label130
Label127:
invoke crt_printf, addr MensajePrint, addr str_127
Label128:
invoke crt_printf, addr MensajePrintNum, _AA_MAIN_FUNCIONX
Label129:
JMP Label131
Label130:
invoke crt_printf, addr MensajePrintNum, 100
Label131:
MOV EAX, _A_MAIN_FUNCIONX
CMP EAX, 5
SETA AL
MOVZX EAX, AL
MOV @aux131, EAX
Label132:
MOV EAX, @aux131
CMP EAX, 0
JE Label136
Label133:
invoke crt_printf, addr MensajePrint, addr str_133
Label134:
invoke crt_printf, addr MensajePrintNum, _A_MAIN_FUNCIONX
Label135:
JMP Label137
Label136:
invoke crt_printf, addr MensajePrintNum, 100
Label137:
; -- RETURN --
MOV EAX, 1000
MOV _RET_VAL_0, EAX
RET
Label138:
invoke crt_printf, addr MensajePrintNum, _error_tipo
Label139:
MOV EAX, 0
MOV _VARIABLEXX_MAIN, EAX
Label140:
MOV EAX, _VARIABLEXX_MAIN
MOV _AA_MAIN_FUNCIONX, EAX
Label141:
CALL __FUNCIONX_MAIN
MOV @aux141, EAX
Label142:
MOV EAX, @aux141
MOV _VARIABLEXX_MAIN, EAX
Label143:
invoke crt_printf, addr MensajePrint, addr str_143
Label144:
invoke crt_printf, addr MensajePrintNum, _VARIABLEXX_MAIN
Label145:
invoke crt_printf, addr MensajePrint, addr str_145
Label146:
MOV EAX, 10
MOV _VALOR_MAIN, EAX
Label147:
JMP Label153
Label148:
__PROCESAR_MAIN:
Label149:
MOV EAX, 99
MOV _OUT_MAIN_PROCESAR, EAX
Label150:
MOV EAX, _INOUT_MAIN_PROCESAR
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux150, EAX
Label151:
MOV EAX, @aux150
MOV _INOUT_MAIN_PROCESAR, EAX
Label152:
; -- RETURN --
MOV EAX, 1
MOV _RET_VAL_0, EAX
RET
Label153:
MOV EAX, 0
MOV _BASURA_MAIN, EAX
Label154:
invoke crt_printf, addr MensajePrint, addr str_154
Label155:
invoke crt_printf, addr MensajePrintNum, _BASURA_MAIN
Label156:
MOV EAX, _BASURA_MAIN
MOV _OUT_MAIN_PROCESAR, EAX
Label157:
MOV EAX, _VALOR_MAIN
MOV _INOUT_MAIN_PROCESAR, EAX
Label158:
CALL __PROCESAR_MAIN
MOV @aux158, EAX
Label159:
MOV EAX, _OUT_MAIN_PROCESAR
MOV _BASURA_MAIN, EAX
Label160:
MOV EAX, _INOUT_MAIN_PROCESAR
MOV _VALOR_MAIN, EAX
Label161:
invoke crt_printf, addr MensajePrint, addr str_161
Label162:
invoke crt_printf, addr MensajePrintNum, _BASURA_MAIN
Label163:
invoke crt_printf, addr MensajePrint, addr str_163
Label164:
invoke crt_printf, addr MensajePrintNum, _VALOR_MAIN
Label165:
MOV EAX, 100
MOV _GLOBAL_MAIN, EAX
Label166:
invoke crt_printf, addr MensajePrint, addr str_166
Label167:
invoke crt_printf, addr MensajePrintNum, _GLOBAL_MAIN
Label168:
JMP Label184
Label169:
__FUNCION_MAIN:
Label170:
MOV EAX, 200
MOV _VARLOCAL_MAIN_FUNCION, EAX
Label171:
MOV EAX, _GLOBAL_MAIN
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux171, EAX
Label172:
MOV EAX, @aux171
MOV _GLOBAL_MAIN, EAX
Label173:
invoke crt_printf, addr MensajePrint, addr str_173
Label174:
invoke crt_printf, addr MensajePrintNum, _GLOBAL_MAIN
Label175:
MOV EAX, _GLOBAL_MAIN
MOV _VARLOCAL_MAIN_FUNCION, EAX
Label176:
invoke crt_printf, addr MensajePrint, addr str_176
Label177:
invoke crt_printf, addr MensajePrintNum, _VARLOCAL_MAIN_FUNCION
Label178:
MOV EAX, _VARLOCAL_MAIN_FUNCION
ADD EAX, 3
CMP EAX, 65535
JA ErrorOverflow
MOV @aux178, EAX
Label179:
MOV EAX, @aux178
MOV _GLOBAL_MAIN, EAX
Label180:
invoke crt_printf, addr MensajePrint, addr str_180
Label181:
invoke crt_printf, addr MensajePrintNum, _GLOBAL_MAIN
Label182:
; -- RETURN --
MOV EAX, 12
MOV _RET_VAL_0, EAX
Label183:
; -- RETURN --
MOV EAX, 12
MOV _RET_VAL_1, EAX
RET
Label184:
MOV EAX, 0
MOV _R1_MAIN, EAX
Label185:
MOV EAX, 0
MOV _R2_MAIN, EAX
Label186:
MOV EAX, 0
MOV _R3_MAIN, EAX
Label187:
MOV EAX, 50
MOV _P1_MAIN_FUNCION, EAX
Label188:
CALL __FUNCION_MAIN
MOV @aux188, EAX
Label189:
MOV EAX, _RET_VAL_0
MOV @aux189, EAX
Label190:
MOV EAX, @aux189
MOV _R1_MAIN, EAX
Label191:
MOV EAX, 50
MOV _P1_MAIN_FUNCION, EAX
Label192:
CALL __FUNCION_MAIN
MOV @aux192, EAX
Label193:
MOV EAX, 50
MOV _P1_MAIN_FUNCION, EAX
Label194:
CALL __FUNCION_MAIN
MOV @aux194, EAX
Label195:
MOV EAX, _RET_VAL_0
MOV @aux195, EAX
Label196:
MOV EAX, @aux195
MOV _R1_MAIN, EAX
Label197:
MOV EAX, _RET_VAL_1
MOV @aux197, EAX
Label198:
MOV EAX, @aux197
MOV _R2_MAIN, EAX
Label199:
invoke crt_printf, addr MensajePrint, addr str_199
Label200:
invoke crt_printf, addr MensajePrintNum, _R1_MAIN
Label201:
invoke crt_printf, addr MensajePrint, addr str_201
Label202:
invoke crt_printf, addr MensajePrintNum, _R2_MAIN
Label203:
invoke crt_printf, addr MensajePrint, addr str_203
Label204:
invoke crt_printf, addr MensajePrintNum, _GLOBAL_MAIN
Label205:
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
