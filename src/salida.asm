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
_B_PROGRAMA dd 0
.code
start:
Label0:
MOV EAX, 2139095040
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FSTP _A_PROGRAMA
Label1:
MOV EAX, -8388608
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FSTP _B_PROGRAMA
Label2:
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
