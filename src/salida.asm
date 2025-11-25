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
_W_MAIN_FUNCZ_FUNCJ dd 0
_P_MAIN_FUNCZ_FUNCJ dd 0
_A_MAIN_FUNCZ_FUNCJ dd 0
_VARIABLEFERBO_MAIN_FUNCZ dd 0
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
_VARPARALAMBDA_MAIN dd 0
_SS_MAIN_F dd 0
_X_MAIN_F dd 0
_VARPARALAMBDA_MAIN_lambda_170 dd 0
_GLOBAL_MAIN dd 0
_P1_MAIN_FUNCION dd 0
_VARLOCAL_MAIN_FUNCION dd 0
_R1_MAIN dd 0
str_1 db "A:", 0
str_4 db "B:", 0
str_7 db "F1:", 0
str_10 db "F2:", 0
str_13 db "F3:", 0
str_15 db "cadena", 13, 10, "    multilinea", 0
str_17 db "X:", 0
str_20 db "XX:", 0
str_23 db "Y:", 0
str_26 db "Z:", 0
str_31 db "E:", 0
str_36 db "CONT:", 0
str_40 db "#ASIGNACION MULTIPLE IGUAL DE LADOS#", 0
str_43 db "A:", 0
str_45 db "B:", 0
str_47 db "IMPRIME AAAAA:", 0
str_51 db "A DENTRO DE FUNCZ", 0
str_54 db "W DE FUNCZ:", 0
str_57 db "J DE FUNCZ:", 0
str_60 db "A DE FUNCZ:", 0
str_63 db "W DE FUNCZ TOMA VALOR DE A DE FUNCZ:", 0
str_66 db "W DE FUNCZ TOMA VALOR DE MAIN.A:", 0
str_69 db "MAIN.A TOMA VALOR DE A:", 0
str_71 db "IMPRIMO A DE FUNCZ:", 0
str_76 db "W DE FUNCJ:", 0
str_79 db "P DE FUNCJ:", 0
str_82 db "A DE FUNCJ:", 0
str_85 db "W DE FUNCJ TOMA VALOR DE MAIN.A:", 0
str_88 db "FUNCZ.A TOMA VALOR DE W DE FUNCJ:", 0
str_95 db "por entrar", 0
str_97 db "entro", 0
str_103 db "por entrar dos", 0
str_105 db "entro toda", 0
str_111 db "PRINT GCONTADOR ", 0
str_119 db "PRINT AA ", 0
str_125 db "PRINT A ", 0
str_129 db "PRINT A ", 0
str_138 db "por entrar FUNCION IF", 0
str_140 db "ENTRO FUNCION IF", 0
str_148 db "chau chau", 0
str_150 db "IMPRIME BASURA 1", 0
str_157 db "IMPRIME BASURA 2", 0
str_159 db "IMPRIME VALOR", 0
str_164 db "IMPRIMO SS LAMBDA", 0
str_171 db "IMPRIMO VARPARALAMBDA DE LAMBDA", 0
str_178 db "GLOBAL:", 0
str_186 db "MAIN.GLOBAL:", 0
str_189 db "VARLOCAL:", 0
str_193 db "MAIN.GLOBAL:", 0
str_200 db "R1:", 0
str_202 db "GLOBAL:", 0
str_204 db "Si ves esto TERMINO EL PROGRAMA", 0
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
MOV EAX, 1103626240
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FISTP @aux28
Label29:
MOV EAX, 10
ADD EAX, @aux28
CMP EAX, 65535
JA ErrorOverflow
MOV @aux29, EAX
Label30:
MOV EAX, @aux29
MOV _E_MAIN, EAX
Label31:
invoke crt_printf, addr MensajePrint, addr str_31
Label32:
invoke crt_printf, addr MensajePrintNum, _E_MAIN
Label33:
MOV EAX, 0
MOV _CONT_MAIN, EAX
Label34:
MOV EAX, _CONT_MAIN
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux34, EAX
Label35:
MOV EAX, @aux34
MOV _CONT_MAIN, EAX
Label36:
invoke crt_printf, addr MensajePrint, addr str_36
Label37:
invoke crt_printf, addr MensajePrintNum, _CONT_MAIN
Label38:
MOV EAX, _CONT_MAIN
CMP EAX, 5
SETB AL
MOVZX EAX, AL
MOV @aux38, EAX
Label39:
MOV EAX, @aux38
CMP EAX, 1
JE Label34
Label40:
invoke crt_printf, addr MensajePrint, addr str_40
Label41:
MOV EAX, 20
MOV _B_MAIN, EAX
Label42:
MOV EAX, 10
MOV _A_MAIN, EAX
Label43:
invoke crt_printf, addr MensajePrint, addr str_43
Label44:
invoke crt_printf, addr MensajePrintNum, _A_MAIN
Label45:
invoke crt_printf, addr MensajePrint, addr str_45
Label46:
invoke crt_printf, addr MensajePrintNum, _B_MAIN
Label47:
invoke crt_printf, addr MensajePrint, addr str_47
Label48:
invoke crt_printf, addr MensajePrintNum, _A_MAIN
Label49:
JMP Label99
Label50:
__FUNCZ_MAIN:
Label51:
invoke crt_printf, addr MensajePrint, addr str_51
Label52:
invoke crt_printf, addr MensajePrintNum, _A_MAIN
Label53:
MOV EAX, 2
MOV _W_MAIN_FUNCZ, EAX
Label54:
invoke crt_printf, addr MensajePrint, addr str_54
Label55:
invoke crt_printf, addr MensajePrintNum, _W_MAIN_FUNCZ
Label56:
MOV EAX, 3
MOV _J_MAIN_FUNCZ, EAX
Label57:
invoke crt_printf, addr MensajePrint, addr str_57
Label58:
invoke crt_printf, addr MensajePrintNum, _J_MAIN_FUNCZ
Label59:
MOV EAX, 4
MOV _A_MAIN_FUNCZ, EAX
Label60:
invoke crt_printf, addr MensajePrint, addr str_60
Label61:
invoke crt_printf, addr MensajePrintNum, _A_MAIN_FUNCZ
Label62:
MOV EAX, _A_MAIN_FUNCZ
MOV _W_MAIN_FUNCZ, EAX
Label63:
invoke crt_printf, addr MensajePrint, addr str_63
Label64:
invoke crt_printf, addr MensajePrintNum, _W_MAIN_FUNCZ
Label65:
MOV EAX, _A_MAIN
MOV _W_MAIN_FUNCZ, EAX
Label66:
invoke crt_printf, addr MensajePrint, addr str_66
Label67:
invoke crt_printf, addr MensajePrintNum, _W_MAIN_FUNCZ
Label68:
MOV EAX, _A_MAIN_FUNCZ
MOV _A_MAIN, EAX
Label69:
invoke crt_printf, addr MensajePrint, addr str_69
Label70:
invoke crt_printf, addr MensajePrintNum, _A_MAIN
Label71:
invoke crt_printf, addr MensajePrint, addr str_71
Label72:
invoke crt_printf, addr MensajePrintNum, _A_MAIN_FUNCZ
Label73:
JMP Label91
Label74:
__FUNCJ_MAIN_FUNCZ:
Label75:
MOV EAX, 22
MOV _W_MAIN_FUNCZ_FUNCJ, EAX
Label76:
invoke crt_printf, addr MensajePrint, addr str_76
Label77:
invoke crt_printf, addr MensajePrintNum, _W_MAIN_FUNCZ_FUNCJ
Label78:
MOV EAX, 33
MOV _P_MAIN_FUNCZ_FUNCJ, EAX
Label79:
invoke crt_printf, addr MensajePrint, addr str_79
Label80:
invoke crt_printf, addr MensajePrintNum, _P_MAIN_FUNCZ_FUNCJ
Label81:
MOV EAX, 44
MOV _A_MAIN_FUNCZ_FUNCJ, EAX
Label82:
invoke crt_printf, addr MensajePrint, addr str_82
Label83:
invoke crt_printf, addr MensajePrintNum, _A_MAIN_FUNCZ_FUNCJ
Label84:
MOV EAX, _A_MAIN
MOV _W_MAIN_FUNCZ_FUNCJ, EAX
Label85:
invoke crt_printf, addr MensajePrint, addr str_85
Label86:
invoke crt_printf, addr MensajePrintNum, _W_MAIN_FUNCZ_FUNCJ
Label87:
MOV EAX, _W_MAIN_FUNCZ_FUNCJ
MOV _A_MAIN_FUNCZ, EAX
Label88:
invoke crt_printf, addr MensajePrint, addr str_88
Label89:
invoke crt_printf, addr MensajePrintNum, _W_MAIN_FUNCZ_FUNCJ
Label90:
; -- RETURN --
MOV EAX, 55
RET
Label91:
MOV EAX, 0
MOV _VARIABLEFERBO_MAIN_FUNCZ, EAX
Label92:
MOV EAX, _VARIABLEFERBO_MAIN_FUNCZ
MOV _J_MAIN_FUNCZ_FUNCJ, EAX
Label93:
CALL __FUNCJ_MAIN_FUNCZ
MOV @aux93, EAX
Label94:
MOV EAX, @aux93
MOV _VARIABLEFERBO_MAIN_FUNCZ, EAX
Label95:
invoke crt_printf, addr MensajePrint, addr str_95
Label96:
invoke crt_printf, addr MensajePrintNum, _VARIABLEFERBO_MAIN_FUNCZ
Label97:
invoke crt_printf, addr MensajePrint, addr str_97
Label98:
; -- RETURN --
MOV EAX, 5
RET
Label99:
MOV EAX, 0
MOV _VARIABLEZ_MAIN, EAX
Label100:
MOV EAX, _VARIABLEZ_MAIN
MOV _Z_MAIN_FUNCZ, EAX
Label101:
CALL __FUNCZ_MAIN
MOV @aux101, EAX
Label102:
MOV EAX, @aux101
MOV _VARIABLEZ_MAIN, EAX
Label103:
invoke crt_printf, addr MensajePrint, addr str_103
Label104:
invoke crt_printf, addr MensajePrintNum, _VARIABLEZ_MAIN
Label105:
invoke crt_printf, addr MensajePrint, addr str_105
Label106:
JMP Label134
Label107:
__FUNCIONX_MAIN:
Label108:
MOV EAX, 3
MOV _AA_MAIN_FUNCIONX, EAX
Label109:
MOV EAX, 3
MOV _A_MAIN_FUNCIONX, EAX
Label110:
MOV EAX, 0
MOV _GCONTADOR_MAIN_FUNCIONX, EAX
Label111:
invoke crt_printf, addr MensajePrint, addr str_111
Label112:
invoke crt_printf, addr MensajePrintNum, _GCONTADOR_MAIN_FUNCIONX
Label113:
MOV EAX, _GCONTADOR_MAIN_FUNCIONX
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux113, EAX
Label114:
MOV EAX, @aux113
MOV _GCONTADOR_MAIN_FUNCIONX, EAX
Label115:
MOV EAX, _GCONTADOR_MAIN_FUNCIONX
CMP EAX, 3
SETB AL
MOVZX EAX, AL
MOV @aux115, EAX
Label116:
MOV EAX, @aux115
CMP EAX, 1
JE Label111
Label117:
MOV EAX, _AA_MAIN_FUNCIONX
CMP EAX, 5
SETB AL
MOVZX EAX, AL
MOV @aux117, EAX
Label118:
MOV EAX, @aux117
CMP EAX, 0
JE Label122
Label119:
invoke crt_printf, addr MensajePrint, addr str_119
Label120:
invoke crt_printf, addr MensajePrintNum, _AA_MAIN_FUNCIONX
Label121:
JMP Label123
Label122:
invoke crt_printf, addr MensajePrintNum, 100
Label123:
MOV EAX, _A_MAIN_FUNCIONX
CMP EAX, 5
SETB AL
MOVZX EAX, AL
MOV @aux123, EAX
Label124:
MOV EAX, @aux123
CMP EAX, 0
JE Label127
Label125:
invoke crt_printf, addr MensajePrint, addr str_125
Label126:
invoke crt_printf, addr MensajePrintNum, _A_MAIN_FUNCIONX
Label127:
MOV EAX, _A_MAIN_FUNCIONX
CMP EAX, 5
SETA AL
MOVZX EAX, AL
MOV @aux127, EAX
Label128:
MOV EAX, @aux127
CMP EAX, 0
JE Label132
Label129:
invoke crt_printf, addr MensajePrint, addr str_129
Label130:
invoke crt_printf, addr MensajePrintNum, _A_MAIN_FUNCIONX
Label131:
JMP Label133
Label132:
invoke crt_printf, addr MensajePrintNum, 100
Label133:
; -- RETURN --
MOV EAX, 1000
RET
Label134:
MOV EAX, 0
MOV _VARIABLEXX_MAIN, EAX
Label135:
MOV EAX, _VARIABLEXX_MAIN
MOV _AA_MAIN_FUNCIONX, EAX
Label136:
CALL __FUNCIONX_MAIN
MOV @aux136, EAX
Label137:
MOV EAX, @aux136
MOV _VARIABLEXX_MAIN, EAX
Label138:
invoke crt_printf, addr MensajePrint, addr str_138
Label139:
invoke crt_printf, addr MensajePrintNum, _VARIABLEXX_MAIN
Label140:
invoke crt_printf, addr MensajePrint, addr str_140
Label141:
MOV EAX, 10
MOV _VALOR_MAIN, EAX
Label142:
JMP Label148
Label143:
__PROCESAR_MAIN:
Label144:
MOV EAX, 99
MOV _OUT_MAIN_PROCESAR, EAX
Label145:
MOV EAX, _INOUT_MAIN_PROCESAR
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux145, EAX
Label146:
MOV EAX, @aux145
MOV _INOUT_MAIN_PROCESAR, EAX
Label147:
; -- RETURN --
MOV EAX, 1
RET
Label148:
invoke crt_printf, addr MensajePrint, addr str_148
Label149:
MOV EAX, 0
MOV _BASURA_MAIN, EAX
Label150:
invoke crt_printf, addr MensajePrint, addr str_150
Label151:
invoke crt_printf, addr MensajePrintNum, _BASURA_MAIN
Label152:
MOV EAX, _BASURA_MAIN
MOV _OUT_MAIN_PROCESAR, EAX
Label153:
MOV EAX, _VALOR_MAIN
MOV _INOUT_MAIN_PROCESAR, EAX
Label154:
CALL __PROCESAR_MAIN
MOV @aux154, EAX
Label155:
MOV EAX, _OUT_MAIN_PROCESAR
MOV _BASURA_MAIN, EAX
Label156:
MOV EAX, _INOUT_MAIN_PROCESAR
MOV _VALOR_MAIN, EAX
Label157:
invoke crt_printf, addr MensajePrint, addr str_157
Label158:
invoke crt_printf, addr MensajePrintNum, _BASURA_MAIN
Label159:
invoke crt_printf, addr MensajePrint, addr str_159
Label160:
invoke crt_printf, addr MensajePrintNum, _VALOR_MAIN
Label161:
MOV EAX, 5
MOV _VARPARALAMBDA_MAIN, EAX
Label162:
JMP Label169
Label163:
__F_MAIN:
Label164:
invoke crt_printf, addr MensajePrint, addr str_164
Label165:
invoke crt_printf, addr MensajePrintNum, _SS_MAIN_F
Label166:
Label167:
MOV EAX, _X_MAIN_F
CALL EAX
Label168:
; -- RETURN --
MOV EAX, 111
RET
Label169:
JMP Label174
Label170:
Label171:
invoke crt_printf, addr MensajePrint, addr str_171
Label172:
invoke crt_printf, addr MensajePrintNum, _VARPARALAMBDA_MAIN
Label173:
; -- RETURN --
RET
Label174:
MOV EAX, Label170
MOV _X_MAIN_F, EAX
Label175:
MOV EAX, 33
MOV _SS_MAIN_F, EAX
Label176:
CALL __F_MAIN
MOV @aux176, EAX
Label177:
MOV EAX, 100
MOV _GLOBAL_MAIN, EAX
Label178:
invoke crt_printf, addr MensajePrint, addr str_178
Label179:
invoke crt_printf, addr MensajePrintNum, _GLOBAL_MAIN
Label180:
JMP Label197
Label181:
__FUNCION_MAIN:
Label182:
MOV EAX, 11
MOV _P1_MAIN_FUNCION, EAX
Label183:
MOV EAX, 200
MOV _VARLOCAL_MAIN_FUNCION, EAX
Label184:
MOV EAX, _GLOBAL_MAIN
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux184, EAX
Label185:
MOV EAX, @aux184
MOV _GLOBAL_MAIN, EAX
Label186:
invoke crt_printf, addr MensajePrint, addr str_186
Label187:
invoke crt_printf, addr MensajePrintNum, _GLOBAL_MAIN
Label188:
MOV EAX, _GLOBAL_MAIN
MOV _VARLOCAL_MAIN_FUNCION, EAX
Label189:
invoke crt_printf, addr MensajePrint, addr str_189
Label190:
invoke crt_printf, addr MensajePrintNum, _VARLOCAL_MAIN_FUNCION
Label191:
MOV EAX, _VARLOCAL_MAIN_FUNCION
ADD EAX, 3
CMP EAX, 65535
JA ErrorOverflow
MOV @aux191, EAX
Label192:
MOV EAX, @aux191
MOV _GLOBAL_MAIN, EAX
Label193:
invoke crt_printf, addr MensajePrint, addr str_193
Label194:
invoke crt_printf, addr MensajePrintNum, _GLOBAL_MAIN
Label195:
; -- RETURN --
MOV EAX, 15
RET
Label196:
; -- RETURN --
MOV EAX, 1078523331
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
RET
Label197:
MOV EAX, 0
MOV _R1_MAIN, EAX
Label198:
MOV EAX, 50
MOV _P1_MAIN_FUNCION, EAX
Label199:
CALL __FUNCION_MAIN
MOV @aux199, EAX
Label200:
invoke crt_printf, addr MensajePrint, addr str_200
Label201:
invoke crt_printf, addr MensajePrintNum, _R1_MAIN
Label202:
invoke crt_printf, addr MensajePrint, addr str_202
Label203:
invoke crt_printf, addr MensajePrintNum, _GLOBAL_MAIN
Label204:
invoke crt_printf, addr MensajePrint, addr str_204
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
