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
_A_PROGRAMA dd 0
_C_PROGRAMA_FUNCION dd 0
_B_PROGRAMA_FUNCION dd 0
_A_PROGRAMA_lambda_7 dd 0
str_10 db "Imprime Lambda Positivo", 0
str_12 db "Imprime Lambda Negativo", 0
.code
start:
Label0:
MOV EAX, 5
MOV _A_PROGRAMA, EAX
Label1:
JMP Label6
Label2:
__FUNCION_PROGRAMA:
Label3:
Label4:
MOV EAX, _B_PROGRAMA_FUNCION
CALL EAX
Label5:
; -- RETURN --
MOV EAX, _C_PROGRAMA_FUNCION
MOV _RET_VAL_0, EAX
RET
Label6:
JMP Label16
Label7:
Label8:
MOV EAX, _A_PROGRAMA_lambda_7
CMP EAX, 3
SETA AL
MOVZX EAX, AL
MOV @aux8, EAX
Label9:
MOV EAX, @aux8
CMP EAX, 0
JE Label12
Label10:
invoke crt_printf, addr MensajePrint, addr str_10
Label11:
JMP Label15
Label12:
invoke crt_printf, addr MensajePrint, addr str_12
Label13:
MOV EAX, _A_PROGRAMA_lambda_7
SUB EAX, 1
JC ErrorRestaNegativa
MOV @aux13, EAX
Label14:
MOV EAX, @aux13
MOV _A_PROGRAMA_lambda_7, EAX
Label15:
; -- RETURN --
RET
Label16:
MOV EAX, Label7
MOV _B_PROGRAMA_FUNCION, EAX
Label17:
MOV EAX, 5
MOV _C_PROGRAMA_FUNCION, EAX
Label18:
CALL __FUNCION_PROGRAMA
MOV @aux18, EAX
Label19:
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
