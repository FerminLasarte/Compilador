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
_C_PROGRAMA_FUNCION dd 0
_B_PROGRAMA_FUNCION dd 0
_A_PROGRAMA_lambda_6 dd 0
str_9 db "Imprime Lambda Positivo", 0
str_11 db "Imprime Lambda Negativo", 0
.code
start:
Label0:
JMP Label5
Label1:
__FUNCION_PROGRAMA:
Label2:
MOV EAX, _C_PROGRAMA_FUNCION
MOV ECX, EAX
Label3:
MOV EAX, _B_PROGRAMA_FUNCION
CALL EAX
Label4:
invoke crt_printf, addr MensajePrintNum, _C_PROGRAMA_FUNCION
Label5:
JMP Label13
Label6:
MOV _A_PROGRAMA_lambda_6, ECX
Label7:
MOV EAX, _A_PROGRAMA_lambda_6
CMP EAX, 3
SETA AL
MOVZX EAX, AL
MOV @aux7, EAX
Label8:
MOV EAX, @aux7
CMP EAX, 0
JE Label11
Label9:
invoke crt_printf, addr MensajePrint, addr str_9
Label10:
JMP Label12
Label11:
invoke crt_printf, addr MensajePrint, addr str_11
Label12:
; -- RETURN --
RET
Label13:
MOV EAX, Label6
MOV _B_PROGRAMA_FUNCION, EAX
Label14:
MOV EAX, 5
MOV _C_PROGRAMA_FUNCION, EAX
Label15:
CALL __FUNCION_PROGRAMA
MOV @aux15, EAX
Label16:
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
