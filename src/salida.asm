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
_GLOBALU_PRUEBA1 dd 0
_GLOBALF_PRUEBA1 dd 0
_ACUMULADOR_PRUEBA1 dd 0
_VARLOCALI_PRUEBA1_PROCESARCOMPLEJO dd 0
_PLAMBDA_PRUEBA1_PROCESARCOMPLEJO dd 0
_PVAL_PRUEBA1_PROCESARCOMPLEJO dd 0
_RESF_PRUEBA1 dd 0
_ITERACION_PRUEBA1_lambda_21 dd 0
str_0 db "=== PRUEBA DIFICIL ===", 0
str_6 db "[FUNC] Entrando a funcion compleja...", 0
str_18 db "1. Probando Scope y Lambdas anidadas:", 0
str_22 db "  [LAMBDA] Iteracion:", 0
str_24 db "  [LAMBDA] Calculo > 25 (Correcto)", 0
str_26 db "  [LAMBDA] Calculo <= 25 (Error logico)", 0
.code
start:
Label0:
invoke crt_printf, addr MensajePrint, addr str_0
Label1:
MOV EAX, 10
MOV _GLOBALU_PRUEBA1, EAX
Label2:
MOV EAX, 1103888384
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FSTP _GLOBALF_PRUEBA1
Label3:
MOV EAX, 0
MOV _ACUMULADOR_PRUEBA1, EAX
Label4:
JMP Label18
Label5:
__PROCESARCOMPLEJO_PRUEBA1:
Label6:
invoke crt_printf, addr MensajePrint, addr str_6
Label7:
MOV EAX, 0
MOV _VARLOCALI_PRUEBA1_PROCESARCOMPLEJO, EAX
Label8:
Label9:
MOV EAX, _PLAMBDA_PRUEBA1_PROCESARCOMPLEJO
CALL EAX
Label10:
MOV EAX, _PVAL_PRUEBA1_PROCESARCOMPLEJO
ADD EAX, 10
CMP EAX, 65535
JA ErrorOverflow
MOV @aux10, EAX
Label11:
MOV EAX, @aux10
MOV _PVAL_PRUEBA1_PROCESARCOMPLEJO, EAX
Label12:
MOV EAX, _VARLOCALI_PRUEBA1_PROCESARCOMPLEJO
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux12, EAX
Label13:
MOV EAX, @aux12
MOV _VARLOCALI_PRUEBA1_PROCESARCOMPLEJO, EAX
Label14:
MOV EAX, _VARLOCALI_PRUEBA1_PROCESARCOMPLEJO
CMP EAX, 3
SETB AL
MOVZX EAX, AL
MOV @aux14, EAX
Label15:
MOV EAX, @aux14
CMP EAX, 1
JE Label8
Label16:
; -- RETURN --
MOV EAX, _PVAL_PRUEBA1_PROCESARCOMPLEJO
MOV _RET_VAL_0, EAX
Label17:
; -- RETURN --
MOV EAX, 1120390349
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FSTP _RET_VAL_1
RET
Label18:
invoke crt_printf, addr MensajePrint, addr str_18
Label19:
MOV EAX, 0
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FSTP _RESF_PRUEBA1
Label20:
; JMP UNRESOLVED (_)
Label21:
Label22:
invoke crt_printf, addr MensajePrint, addr str_22
Label23:
invoke crt_printf, addr MensajePrintNum, _ITERACION_PRUEBA1_lambda_21
Label24:
invoke crt_printf, addr MensajePrint, addr str_24
Label25:
JMP Label27
Label26:
invoke crt_printf, addr MensajePrint, addr str_26
Label27:
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
