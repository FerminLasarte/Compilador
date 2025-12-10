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
_VARLEON_ZOOLOGICO dd 0
_TIGRE_ZOOLOGICO dd 0
_AGUILA_ZOOLOGICO dd 0
_HALCON_ZOOLOGICO dd 0
_PINGUINO_ZOOLOGICO dd 0
_JIRAFA_ZOOLOGICO dd 0
_ELEFANTE_ZOOLOGICO dd 0
_COMIDA_ZOOLOGICO_ALIMENTAR dd 0
_AGUA_ZOOLOGICO_ALIMENTAR dd 0
_OSO_ZOOLOGICO dd 0
_KOALA_ZOOLOGICO dd 0
_PACIENTE_ZOOLOGICO dd 0
_MEDICINA_ZOOLOGICO_CUIDAR dd 0
_VETERINARIO_ZOOLOGICO_CUIDAR dd 0
_PACIENTE_ZOOLOGICO_lambda_53 dd 0
str_0 db " @Numero de linea: 2", 13, 10, "    === INICIO TEST ZOOLOGICO === @Numero de linea: 3", 13, 10, "    ", 0
str_7 db "Conversion toui (PINGUINO):", 0
str_9 db "Do-While (VARLEON):", 0
str_17 db "Asignacion Multiple (JIRAFA, ELEFANTE):", 0
str_24 db "Dentro de ALIMENTAR", 0
str_25 db "Acceso explicito a ambito:", 0
str_34 db "Llamada a funcion con retornos multiples y CR-SE/LE:", 0
str_48 db "Ejecutando Lambda (VETERINARIO)...", 0
str_54 db "Valor en Lambda (PACIENTE):", 0
str_60 db "Probando chequeos runtime (operaciones validas):", 0
str_70 db "=== FIN TEST ZOOLOGICO ===", 0
.code
start:
Label0:
invoke crt_printf, addr MensajePrint, addr str_0
Label1:
MOV EAX, 10
MOV _VARLEON_ZOOLOGICO, EAX
Label2:
MOV EAX, 20
MOV _TIGRE_ZOOLOGICO, EAX
Label3:
MOV EAX, 1085276160
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FSTP _AGUILA_ZOOLOGICO
Label4:
MOV EAX, 1073741824
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FSTP _HALCON_ZOOLOGICO
Label5:
FLD _AGUILA_ZOOLOGICO
FISTP @aux5
Label6:
MOV EAX, @aux5
MOV _PINGUINO_ZOOLOGICO, EAX
Label7:
invoke crt_printf, addr MensajePrint, addr str_7
Label8:
invoke crt_printf, addr MensajePrintNum, _PINGUINO_ZOOLOGICO
Label9:
invoke crt_printf, addr MensajePrint, addr str_9
Label10:
invoke crt_printf, addr MensajePrintNum, _VARLEON_ZOOLOGICO
Label11:
MOV EAX, _VARLEON_ZOOLOGICO
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux11, EAX
Label12:
MOV EAX, @aux11
MOV _VARLEON_ZOOLOGICO, EAX
Label13:
MOV EAX, _VARLEON_ZOOLOGICO
CMP EAX, 13
SETB AL
MOVZX EAX, AL
MOV @aux13, EAX
Label14:
MOV EAX, @aux13
CMP EAX, 1
JE Label10
Label15:
MOV EAX, 0
MOV _JIRAFA_ZOOLOGICO, EAX
Label16:
MOV EAX, 0
MOV _ELEFANTE_ZOOLOGICO, EAX
Label17:
invoke crt_printf, addr MensajePrint, addr str_17
Label18:
MOV EAX, _TIGRE_ZOOLOGICO
MOV _ELEFANTE_ZOOLOGICO, EAX
Label19:
MOV EAX, _VARLEON_ZOOLOGICO
MOV _JIRAFA_ZOOLOGICO, EAX
Label20:
invoke crt_printf, addr MensajePrintNum, _JIRAFA_ZOOLOGICO
Label21:
invoke crt_printf, addr MensajePrintNum, _ELEFANTE_ZOOLOGICO
Label22:
JMP Label32
Label23:
__ALIMENTAR_ZOOLOGICO:
Label24:
invoke crt_printf, addr MensajePrint, addr str_24
Label25:
invoke crt_printf, addr MensajePrint, addr str_25
Label26:
invoke crt_printf, addr MensajePrintNum, _VARLEON_ZOOLOGICO
Label27:
MOV EAX, 100
MOV _COMIDA_ZOOLOGICO_ALIMENTAR, EAX
Label28:
MOV EAX, _AGUA_ZOOLOGICO_ALIMENTAR
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux28, EAX
Label29:
MOV EAX, @aux28
MOV _AGUA_ZOOLOGICO_ALIMENTAR, EAX
Label30:
; -- RETURN --
MOV EAX, _COMIDA_ZOOLOGICO_ALIMENTAR
MOV _RET_VAL_0, EAX
Label31:
; -- RETURN --
MOV EAX, _AGUA_ZOOLOGICO_ALIMENTAR
MOV _RET_VAL_1, EAX
RET
Label32:
MOV EAX, 0
MOV _OSO_ZOOLOGICO, EAX
Label33:
MOV EAX, 10
MOV _KOALA_ZOOLOGICO, EAX
Label34:
invoke crt_printf, addr MensajePrint, addr str_34
Label35:
MOV EAX, _OSO_ZOOLOGICO
MOV _COMIDA_ZOOLOGICO_ALIMENTAR, EAX
Label36:
MOV EAX, _KOALA_ZOOLOGICO
MOV _AGUA_ZOOLOGICO_ALIMENTAR, EAX
Label37:
CALL __ALIMENTAR_ZOOLOGICO
MOV @aux37, EAX
Label38:
MOV EAX, _COMIDA_ZOOLOGICO_ALIMENTAR
MOV _OSO_ZOOLOGICO, EAX
Label39:
MOV EAX, _AGUA_ZOOLOGICO_ALIMENTAR
MOV _KOALA_ZOOLOGICO, EAX
Label40:
MOV EAX, _RET_VAL_0
MOV @aux40, EAX
Label41:
MOV EAX, @aux40
MOV _OSO_ZOOLOGICO, EAX
Label42:
MOV EAX, _RET_VAL_1
MOV @aux42, EAX
Label43:
MOV EAX, @aux42
MOV _KOALA_ZOOLOGICO, EAX
Label44:
invoke crt_printf, addr MensajePrintNum, _OSO_ZOOLOGICO
Label45:
MOV EAX, 65
MOV _PACIENTE_ZOOLOGICO, EAX
Label46:
JMP Label52
Label47:
__CUIDAR_ZOOLOGICO:
Label48:
invoke crt_printf, addr MensajePrint, addr str_48
Label49:
Label50:
MOV EAX, _VETERINARIO_ZOOLOGICO_CUIDAR
CALL EAX
Label51:
; -- RETURN --
MOV EAX, 1
MOV _RET_VAL_0, EAX
RET
Label52:
JMP Label57
Label53:
Label54:
invoke crt_printf, addr MensajePrint, addr str_54
Label55:
invoke crt_printf, addr MensajePrintNum, _PACIENTE_ZOOLOGICO
Label56:
; -- RETURN --
RET
Label57:
MOV EAX, Label53
MOV _VETERINARIO_ZOOLOGICO_CUIDAR, EAX
Label58:
MOV EAX, 50
MOV _MEDICINA_ZOOLOGICO_CUIDAR, EAX
Label59:
CALL __CUIDAR_ZOOLOGICO
MOV @aux59, EAX
Label60:
invoke crt_printf, addr MensajePrint, addr str_60
Label61:
MOV EAX, _VARLEON_ZOOLOGICO
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux61, EAX
Label62:
MOV EAX, @aux61
MOV _VARLEON_ZOOLOGICO, EAX
Label63:
MOV EAX, _VARLEON_ZOOLOGICO
CMP EAX, 0
SETA AL
MOVZX EAX, AL
MOV @aux63, EAX
Label64:
MOV EAX, @aux63
CMP EAX, 0
JE Label67
Label65:
MOV EAX, _VARLEON_ZOOLOGICO
SUB EAX, 1
JC ErrorRestaNegativa
MOV @aux65, EAX
Label66:
MOV EAX, @aux65
MOV _VARLEON_ZOOLOGICO, EAX
Label67:
FLD _AGUILA_ZOOLOGICO
FLD _HALCON_ZOOLOGICO
FMUL
FLD ST(0)
FABS
FCOMP MaxFloatValue
FSTSW AX
SAHF
JA ErrorOverflow
FSTP @aux67
Label68:
FLD @aux67
FSTP _AGUILA_ZOOLOGICO
Label69:
FLD _AGUILA_ZOOLOGICO
sub esp, 8
fstp qword ptr [esp]
push offset MensajePrintFloat
call crt_printf
add esp, 12
Label70:
invoke crt_printf, addr MensajePrint, addr str_70
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
