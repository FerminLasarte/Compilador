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
_SS_MAIN_F dd 0
_X_MAIN_F dd 0
_VARPARALAMBDA_MAIN_lambda_219 dd 0
str_1 db "A:", 0
str_4 db "B:", 0
str_7 db "F1:", 0
str_10 db "F2:", 0
str_13 db "F3:", 0
str_16 db "X:", 0
str_19 db "XX:", 0
str_22 db "Y:", 0
str_25 db "Z:", 0
str_29 db "Z:", 0
str_33 db "X CON CONVERSION EXPLICITA:", 0
str_38 db "E:", 0
str_43 db "CONT:", 0
str_49 db "CONT:", 0
str_51 db "#ASIGNACION MULTIPLE IGUAL DE LADOS#", 0
str_54 db "A:", 0
str_56 db "B:", 0
str_58 db "IMPRIME AAAAA:", 0
str_62 db "A DENTRO DE FUNCZ", 0
str_65 db "W DE FUNCZ:", 0
str_68 db "J DE FUNCZ:", 0
str_71 db "A DE FUNCZ:", 0
str_74 db "W DE FUNCZ TOMA VALOR DE A DE FUNCZ:", 0
str_77 db "W DE FUNCZ TOMA VALOR DE MAIN.A:", 0
str_80 db "MAIN.A TOMA VALOR DE A:", 0
str_82 db "IMPRIMO A DE FUNCZ:", 0
str_88 db "W DE FUNCJ:", 0
str_91 db "P DE FUNCJ:", 0
str_95 db "A DE FUNCJ:", 0
str_98 db "W DE FUNCJ TOMA VALOR DE MAIN.A:", 0
str_101 db "FUNCZ.A TOMA VALOR DE W DE FUNCJ:", 0
str_108 db "por entrar", 0
str_110 db "entro", 0
str_116 db "por entrar dos", 0
str_118 db "entro", 0
str_124 db "PRINT GCONTADOR ", 0
str_132 db "PRINT AA ", 0
str_138 db "PRINT A ", 0
str_148 db "por entrar FUNCION IF", 0
str_150 db "ENTRO FUNCION IF", 0
str_159 db "IMPRIME BASURA 1", 0
str_166 db "IMPRIME BASURA 2", 0
str_168 db "IMPRIME VALOR", 0
str_171 db "GLOBAL:", 0
str_178 db "MAIN.GLOBAL:", 0
str_181 db "VARLOCAL:", 0
str_185 db "MAIN.GLOBAL:", 0
str_204 db "R1:", 0
str_206 db "R2:", 0
str_208 db "GLOBAL:", 0
str_213 db "IMPRIMO SS LAMBDA", 0
str_220 db "IMPRIMO VARPARALAMBDA DE LAMBDA", 0
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
MOV EAX, 10
MOV _X_MAIN, EAX
Label16:
invoke crt_printf, addr MensajePrint, addr str_16
Label17:
invoke crt_printf, addr MensajePrintNum, _X_MAIN
Label18:
MOV EAX, 1000
MOV _XX_MAIN, EAX
Label19:
invoke crt_printf, addr MensajePrint, addr str_19
Label20:
invoke crt_printf, addr MensajePrintNum, _XX_MAIN
Label21:
MOV EAX, 1103626240
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FSTP _Y_MAIN
Label22:
invoke crt_printf, addr MensajePrint, addr str_22
Label23:
FLD _Y_MAIN
sub esp, 8
fstp qword ptr [esp]
push offset MensajePrintFloat
call crt_printf
add esp, 12
Label24:
FLD _Y_MAIN
FSTP _Z_MAIN
Label25:
invoke crt_printf, addr MensajePrint, addr str_25
Label26:
FLD _Z_MAIN
sub esp, 8
fstp qword ptr [esp]
push offset MensajePrintFloat
call crt_printf
add esp, 12
Label27:
FLD _Y_MAIN
FISTP @aux27
Label28:
MOV EAX, @aux27
MOV _X_MAIN, EAX
Label29:
invoke crt_printf, addr MensajePrint, addr str_29
Label30:
FLD _Z_MAIN
sub esp, 8
fstp qword ptr [esp]
push offset MensajePrintFloat
call crt_printf
add esp, 12
Label31:
MOV EAX, 1103626240
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FISTP @aux31
Label32:
MOV EAX, @aux31
MOV _X_MAIN, EAX
Label33:
invoke crt_printf, addr MensajePrint, addr str_33
Label34:
invoke crt_printf, addr MensajePrintNum, _X_MAIN
Label35:
MOV EAX, 1103626240
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FISTP @aux35
Label36:
MOV EAX, 10
ADD EAX, @aux35
CMP EAX, 65535
JA ErrorOverflow
MOV @aux36, EAX
Label37:
MOV EAX, @aux36
MOV _E_MAIN, EAX
Label38:
invoke crt_printf, addr MensajePrint, addr str_38
Label39:
invoke crt_printf, addr MensajePrintNum, _E_MAIN
Label40:
MOV EAX, 0
MOV _CONT_MAIN, EAX
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
MOV EAX, _CONT_MAIN
CMP EAX, 5
SETB AL
MOVZX EAX, AL
MOV @aux45, EAX
Label46:
MOV EAX, @aux45
CMP EAX, 1
JE Label41
Label47:
MOV EAX, _CONT_MAIN
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux47, EAX
Label48:
MOV EAX, @aux47
MOV _CONT_MAIN, EAX
Label49:
invoke crt_printf, addr MensajePrint, addr str_49
Label50:
invoke crt_printf, addr MensajePrintNum, _CONT_MAIN
Label51:
invoke crt_printf, addr MensajePrint, addr str_51
Label52:
MOV EAX, 20
MOV _B_MAIN, EAX
Label53:
MOV EAX, 10
MOV _A_MAIN, EAX
Label54:
invoke crt_printf, addr MensajePrint, addr str_54
Label55:
invoke crt_printf, addr MensajePrintNum, _A_MAIN
Label56:
invoke crt_printf, addr MensajePrint, addr str_56
Label57:
invoke crt_printf, addr MensajePrintNum, _B_MAIN
Label58:
invoke crt_printf, addr MensajePrint, addr str_58
Label59:
invoke crt_printf, addr MensajePrintNum, _A_MAIN
Label60:
JMP Label112
Label61:
__FUNCZ_MAIN:
Label62:
invoke crt_printf, addr MensajePrint, addr str_62
Label63:
invoke crt_printf, addr MensajePrintNum, _A_MAIN
Label64:
MOV EAX, 2
MOV _W_MAIN_FUNCZ, EAX
Label65:
invoke crt_printf, addr MensajePrint, addr str_65
Label66:
invoke crt_printf, addr MensajePrintNum, _W_MAIN_FUNCZ
Label67:
MOV EAX, 3
MOV _J_MAIN_FUNCZ, EAX
Label68:
invoke crt_printf, addr MensajePrint, addr str_68
Label69:
invoke crt_printf, addr MensajePrintNum, _J_MAIN_FUNCZ
Label70:
MOV EAX, 4
MOV _A_MAIN_FUNCZ, EAX
Label71:
invoke crt_printf, addr MensajePrint, addr str_71
Label72:
invoke crt_printf, addr MensajePrintNum, _A_MAIN_FUNCZ
Label73:
MOV EAX, _A_MAIN_FUNCZ
MOV _W_MAIN_FUNCZ, EAX
Label74:
invoke crt_printf, addr MensajePrint, addr str_74
Label75:
invoke crt_printf, addr MensajePrintNum, _W_MAIN_FUNCZ
Label76:
MOV EAX, _A_MAIN
MOV _W_MAIN_FUNCZ, EAX
Label77:
invoke crt_printf, addr MensajePrint, addr str_77
Label78:
invoke crt_printf, addr MensajePrintNum, _W_MAIN_FUNCZ
Label79:
MOV EAX, _A_MAIN_FUNCZ
MOV _A_MAIN, EAX
Label80:
invoke crt_printf, addr MensajePrint, addr str_80
Label81:
invoke crt_printf, addr MensajePrintNum, _A_MAIN
Label82:
invoke crt_printf, addr MensajePrint, addr str_82
Label83:
invoke crt_printf, addr MensajePrintNum, _A_MAIN_FUNCZ
Label84:
invoke crt_printf, addr MensajePrintNum, _error_tipo
Label85:
JMP Label104
Label86:
__FUNCJ_MAIN_FUNCZ:
Label87:
MOV EAX, 22
MOV _W_MAIN_FUNCZ_FUNCJ, EAX
Label88:
invoke crt_printf, addr MensajePrint, addr str_88
Label89:
invoke crt_printf, addr MensajePrintNum, _W_MAIN_FUNCZ_FUNCJ
Label90:
MOV EAX, 33
MOV _P_MAIN_FUNCZ_FUNCJ, EAX
Label91:
invoke crt_printf, addr MensajePrint, addr str_91
Label92:
invoke crt_printf, addr MensajePrintNum, _P_MAIN_FUNCZ_FUNCJ
Label93:
invoke crt_printf, addr MensajePrintNum, _error_tipo
Label94:
MOV EAX, 44
MOV _A_MAIN_FUNCZ_FUNCJ, EAX
Label95:
invoke crt_printf, addr MensajePrint, addr str_95
Label96:
invoke crt_printf, addr MensajePrintNum, _A_MAIN_FUNCZ_FUNCJ
Label97:
MOV EAX, _A_MAIN
MOV _W_MAIN_FUNCZ_FUNCJ, EAX
Label98:
invoke crt_printf, addr MensajePrint, addr str_98
Label99:
invoke crt_printf, addr MensajePrintNum, _W_MAIN_FUNCZ_FUNCJ
Label100:
MOV EAX, _W_MAIN_FUNCZ_FUNCJ
MOV _A_MAIN_FUNCZ, EAX
Label101:
invoke crt_printf, addr MensajePrint, addr str_101
Label102:
invoke crt_printf, addr MensajePrintNum, _W_MAIN_FUNCZ_FUNCJ
Label103:
; -- RETURN --
MOV EAX, 55
RET
Label104:
MOV EAX, 0
MOV _VARIABLEOTRA_MAIN_FUNCZ, EAX
Label105:
MOV EAX, _VARIABLEOTRA_MAIN_FUNCZ
MOV _J_MAIN_FUNCZ_FUNCJ, EAX
Label106:
CALL __FUNCJ_MAIN_FUNCZ
MOV @aux106, EAX
Label107:
MOV EAX, @aux106
MOV _VARIABLEOTRA_MAIN_FUNCZ, EAX
Label108:
invoke crt_printf, addr MensajePrint, addr str_108
Label109:
invoke crt_printf, addr MensajePrintNum, _VARIABLEOTRA_MAIN_FUNCZ
Label110:
invoke crt_printf, addr MensajePrint, addr str_110
Label111:
; -- RETURN --
MOV EAX, 5
RET
Label112:
MOV EAX, 0
MOV _VARIABLEZ_MAIN, EAX
Label113:
MOV EAX, _VARIABLEZ_MAIN
MOV _Z_MAIN_FUNCZ, EAX
Label114:
CALL __FUNCZ_MAIN
MOV @aux114, EAX
Label115:
MOV EAX, @aux114
MOV _VARIABLEZ_MAIN, EAX
Label116:
invoke crt_printf, addr MensajePrint, addr str_116
Label117:
invoke crt_printf, addr MensajePrintNum, _VARIABLEZ_MAIN
Label118:
invoke crt_printf, addr MensajePrint, addr str_118
Label119:
JMP Label143
Label120:
__FUNCIONX_MAIN:
Label121:
MOV EAX, 3
MOV _AA_MAIN_FUNCIONX, EAX
Label122:
MOV EAX, 3
MOV _A_MAIN_FUNCIONX, EAX
Label123:
MOV EAX, 0
MOV _GCONTADOR_MAIN_FUNCIONX, EAX
Label124:
invoke crt_printf, addr MensajePrint, addr str_124
Label125:
invoke crt_printf, addr MensajePrintNum, _GCONTADOR_MAIN_FUNCIONX
Label126:
MOV EAX, _GCONTADOR_MAIN_FUNCIONX
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux126, EAX
Label127:
MOV EAX, @aux126
MOV _GCONTADOR_MAIN_FUNCIONX, EAX
Label128:
MOV EAX, _GCONTADOR_MAIN_FUNCIONX
CMP EAX, 3
SETB AL
MOVZX EAX, AL
MOV @aux128, EAX
Label129:
MOV EAX, @aux128
CMP EAX, 1
JE Label124
Label130:
MOV EAX, _AA_MAIN_FUNCIONX
CMP EAX, 5
SETB AL
MOVZX EAX, AL
MOV @aux130, EAX
Label131:
MOV EAX, @aux130
CMP EAX, 0
JE Label135
Label132:
invoke crt_printf, addr MensajePrint, addr str_132
Label133:
invoke crt_printf, addr MensajePrintNum, _AA_MAIN_FUNCIONX
Label134:
JMP Label136
Label135:
invoke crt_printf, addr MensajePrintNum, 100
Label136:
MOV EAX, _A_MAIN_FUNCIONX
CMP EAX, 5
SETA AL
MOVZX EAX, AL
MOV @aux136, EAX
Label137:
MOV EAX, @aux136
CMP EAX, 0
JE Label141
Label138:
invoke crt_printf, addr MensajePrint, addr str_138
Label139:
invoke crt_printf, addr MensajePrintNum, _A_MAIN_FUNCIONX
Label140:
JMP Label142
Label141:
invoke crt_printf, addr MensajePrintNum, 100
Label142:
; -- RETURN --
MOV EAX, 1000
RET
Label143:
invoke crt_printf, addr MensajePrintNum, _error_tipo
Label144:
MOV EAX, 0
MOV _VARIABLEXX_MAIN, EAX
Label145:
MOV EAX, _VARIABLEXX_MAIN
MOV _AA_MAIN_FUNCIONX, EAX
Label146:
CALL __FUNCIONX_MAIN
MOV @aux146, EAX
Label147:
MOV EAX, @aux146
MOV _VARIABLEXX_MAIN, EAX
Label148:
invoke crt_printf, addr MensajePrint, addr str_148
Label149:
invoke crt_printf, addr MensajePrintNum, _VARIABLEXX_MAIN
Label150:
invoke crt_printf, addr MensajePrint, addr str_150
Label151:
MOV EAX, 10
MOV _VALOR_MAIN, EAX
Label152:
JMP Label158
Label153:
__PROCESAR_MAIN:
Label154:
MOV EAX, 99
MOV _OUT_MAIN_PROCESAR, EAX
Label155:
MOV EAX, _INOUT_MAIN_PROCESAR
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux155, EAX
Label156:
MOV EAX, @aux155
MOV _INOUT_MAIN_PROCESAR, EAX
Label157:
; -- RETURN --
MOV EAX, 1
RET
Label158:
MOV EAX, 0
MOV _BASURA_MAIN, EAX
Label159:
invoke crt_printf, addr MensajePrint, addr str_159
Label160:
invoke crt_printf, addr MensajePrintNum, _BASURA_MAIN
Label161:
MOV EAX, _BASURA_MAIN
MOV _OUT_MAIN_PROCESAR, EAX
Label162:
MOV EAX, _VALOR_MAIN
MOV _INOUT_MAIN_PROCESAR, EAX
Label163:
CALL __PROCESAR_MAIN
MOV @aux163, EAX
Label164:
MOV EAX, _OUT_MAIN_PROCESAR
MOV _BASURA_MAIN, EAX
Label165:
MOV EAX, _INOUT_MAIN_PROCESAR
MOV _VALOR_MAIN, EAX
Label166:
invoke crt_printf, addr MensajePrint, addr str_166
Label167:
invoke crt_printf, addr MensajePrintNum, _BASURA_MAIN
Label168:
invoke crt_printf, addr MensajePrint, addr str_168
Label169:
invoke crt_printf, addr MensajePrintNum, _VALOR_MAIN
Label170:
MOV EAX, 100
MOV _GLOBAL_MAIN, EAX
Label171:
invoke crt_printf, addr MensajePrint, addr str_171
Label172:
invoke crt_printf, addr MensajePrintNum, _GLOBAL_MAIN
Label173:
JMP Label189
Label174:
__FUNCION_MAIN:
Label175:
MOV EAX, 200
MOV _VARLOCAL_MAIN_FUNCION, EAX
Label176:
MOV EAX, _GLOBAL_MAIN
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux176, EAX
Label177:
MOV EAX, @aux176
MOV _GLOBAL_MAIN, EAX
Label178:
invoke crt_printf, addr MensajePrint, addr str_178
Label179:
invoke crt_printf, addr MensajePrintNum, _GLOBAL_MAIN
Label180:
MOV EAX, _GLOBAL_MAIN
MOV _VARLOCAL_MAIN_FUNCION, EAX
Label181:
invoke crt_printf, addr MensajePrint, addr str_181
Label182:
invoke crt_printf, addr MensajePrintNum, _VARLOCAL_MAIN_FUNCION
Label183:
MOV EAX, _VARLOCAL_MAIN_FUNCION
ADD EAX, 3
CMP EAX, 65535
JA ErrorOverflow
MOV @aux183, EAX
Label184:
MOV EAX, @aux183
MOV _GLOBAL_MAIN, EAX
Label185:
invoke crt_printf, addr MensajePrint, addr str_185
Label186:
invoke crt_printf, addr MensajePrintNum, _GLOBAL_MAIN
Label187:
; -- RETURN --
MOV EAX, 12
RET
Label188:
; -- RETURN --
MOV EAX, 12
RET
Label189:
MOV EAX, 0
MOV _R1_MAIN, EAX
Label190:
MOV EAX, 0
MOV _R2_MAIN, EAX
Label191:
MOV EAX, 0
MOV _R3_MAIN, EAX
Label192:
MOV EAX, 50
MOV _P1_MAIN_FUNCION, EAX
Label193:
CALL __FUNCION_MAIN
MOV @aux193, EAX
Label194:
MOV EAX, @aux193
MOV @aux194, EAX
Label195:
MOV EAX, @aux194
MOV _R1_MAIN, EAX
Label196:
MOV EAX, 50
MOV _P1_MAIN_FUNCION, EAX
Label197:
CALL __FUNCION_MAIN
MOV @aux197, EAX
Label198:
MOV EAX, 50
MOV _P1_MAIN_FUNCION, EAX
Label199:
CALL __FUNCION_MAIN
MOV @aux199, EAX
Label200:
MOV EAX, @aux199
MOV @aux200, EAX
Label201:
MOV EAX, @aux200
MOV _R1_MAIN, EAX
Label202:
MOV EAX, @aux199
MOV @aux202, EAX
Label203:
MOV EAX, @aux202
MOV _R2_MAIN, EAX
Label204:
invoke crt_printf, addr MensajePrint, addr str_204
Label205:
invoke crt_printf, addr MensajePrintNum, _R1_MAIN
Label206:
invoke crt_printf, addr MensajePrint, addr str_206
Label207:
invoke crt_printf, addr MensajePrintNum, _R2_MAIN
Label208:
invoke crt_printf, addr MensajePrint, addr str_208
Label209:
invoke crt_printf, addr MensajePrintNum, _GLOBAL_MAIN
Label210:
MOV EAX, 5
MOV _VARPARALAMBDA_MAIN, EAX
Label211:
JMP Label218
Label212:
__F_MAIN:
Label213:
invoke crt_printf, addr MensajePrint, addr str_213
Label214:
invoke crt_printf, addr MensajePrintNum, _SS_MAIN_F
Label215:
Label216:
MOV EAX, _X_MAIN_F
CALL EAX
Label217:
; -- RETURN --
MOV EAX, 111
RET
Label218:
JMP Label223
Label219:
Label220:
invoke crt_printf, addr MensajePrint, addr str_220
Label221:
invoke crt_printf, addr MensajePrintNum, _VARPARALAMBDA_MAIN
Label222:
; -- RETURN --
RET
Label223:
MOV EAX, Label219
MOV _X_MAIN_F, EAX
Label224:
MOV EAX, 33
MOV _SS_MAIN_F, EAX
Label225:
CALL __F_MAIN
MOV @aux225, EAX
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
