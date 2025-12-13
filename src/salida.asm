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
_C_PROGRAMA dd 0
.code
start:
Label0:
MOV EAX, 10
MOV _A_PROGRAMA, EAX
Label1:
JMP Label11
Label2:
__FUNCION_PROGRAMA:
Label3:
MOV EAX, 5
MOV _C_PROGRAMA_FUNCION, EAX
Label4:
MOV EAX, _A_PROGRAMA
MOV _B_PROGRAMA_FUNCION, EAX
Label5:
invoke crt_printf, addr MensajePrintNum, _B_PROGRAMA_FUNCION
Label6:
MOV EAX, _A_PROGRAMA
ADD EAX, _B_PROGRAMA_FUNCION
CMP EAX, 65535
JA ErrorOverflow
MOV @aux6, EAX
Label7:
invoke crt_printf, addr MensajePrintNum, @aux6
Label8:
MOV EAX, _A_PROGRAMA
ADD EAX, _B_PROGRAMA_FUNCION
CMP EAX, 65535
JA ErrorOverflow
MOV @aux8, EAX
Label9:
invoke crt_printf, addr MensajePrintNum, @aux8
Label10:
; -- RETURN --
MOV EAX, _B_PROGRAMA_FUNCION
MOV _RET_VAL_0, EAX
RET
Label11:
MOV EAX, _A_PROGRAMA
MOV _B_PROGRAMA_FUNCION, EAX
Label12:
CALL __FUNCION_PROGRAMA
MOV @aux12, EAX
Label13:
MOV EAX, @aux12
MOV _C_PROGRAMA, EAX
Label14:
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
