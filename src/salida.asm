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
_S_MAIN dd 0
_D_MAIN dd 0
_F_MAIN dd 0
_G_MAIN dd 0
_H_MAIN dd 0
_J_MAIN dd 0
_K_MAIN dd 0
.code
start:
Label0:
MOV EAX, 4
MOV _A_MAIN, EAX
Label1:
MOV EAX, 4
MOV _S_MAIN, EAX
Label2:
MOV EAX, 4
MOV _D_MAIN, EAX
Label3:
MOV EAX, 4
MOV _F_MAIN, EAX
Label4:
MOV EAX, 4
MOV _G_MAIN, EAX
Label5:
MOV EAX, 4
MOV _H_MAIN, EAX
Label6:
MOV EAX, 4
MOV _J_MAIN, EAX
Label7:
MOV EAX, 4
MOV _K_MAIN, EAX
Label8:
MOV EAX, 4
MOV _L_MAIN, EAX
Label9:
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
