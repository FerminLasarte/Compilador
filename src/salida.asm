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
_A_PROGRAMA_PRIMERA dd 0
_B_PROGRAMA_PRIMERA dd 0
_C_PROGRAMA_PRIMERA dd 0
_E_PROGRAMA_PRIMERA_SEGUNDA dd 0
_D_PROGRAMA_PRIMERA_SEGUNDA dd 0
str_8 db "C = 5UI", 0
str_12 db "C = 25UI", 0
str_17 db "PROGRAMA.A = 5UI", 0
str_21 db "D = 25UI", 0
.code
start:
Label0:
MOV EAX, 1
MOV _A_PROGRAMA, EAX
Label1:
MOV EAX, 4
MOV _B_PROGRAMA, EAX
Label2:
JMP Label24
Label3:
__PRIMERA_PROGRAMA:
Label4:
MOV EAX, 10
MOV _A_PROGRAMA_PRIMERA, EAX
Label5:
MOV EAX, 15
MOV _B_PROGRAMA_PRIMERA, EAX
Label6:
MOV EAX, _A_PROGRAMA
ADD EAX, _B_PROGRAMA
CMP EAX, 65535
JA ErrorOverflow
MOV @aux6, EAX
Label7:
MOV EAX, @aux6
MOV _C_PROGRAMA_PRIMERA, EAX
Label8:
invoke crt_printf, addr MensajePrint, addr str_8
Label9:
invoke crt_printf, addr MensajePrintNum, _C_PROGRAMA_PRIMERA
Label10:
MOV EAX, _A_PROGRAMA_PRIMERA
ADD EAX, _B_PROGRAMA_PRIMERA
CMP EAX, 65535
JA ErrorOverflow
MOV @aux10, EAX
Label11:
MOV EAX, @aux10
MOV _C_PROGRAMA_PRIMERA, EAX
Label12:
invoke crt_printf, addr MensajePrint, addr str_12
Label13:
JMP Label23
Label14:
__SEGUNDA_PROGRAMA_PRIMERA:
Label15:
MOV EAX, 1
MOV _E_PROGRAMA_PRIMERA_SEGUNDA, EAX
Label16:
MOV EAX, 5
MOV _A_PROGRAMA, EAX
Label17:
invoke crt_printf, addr MensajePrint, addr str_17
Label18:
MOV EAX, _A_PROGRAMA
ADD EAX, _B_PROGRAMA_PRIMERA
CMP EAX, 65535
JA ErrorOverflow
MOV @aux18, EAX
Label19:
MOV EAX, @aux18
ADD EAX, _E_PROGRAMA_PRIMERA_SEGUNDA
CMP EAX, 65535
JA ErrorOverflow
MOV @aux19, EAX
Label20:
MOV EAX, @aux19
MOV _D_PROGRAMA_PRIMERA_SEGUNDA, EAX
Label21:
invoke crt_printf, addr MensajePrint, addr str_21
Label22:
; -- RETURN --
MOV EAX, _D_PROGRAMA_PRIMERA_SEGUNDA
MOV _RET_VAL_0, EAX
RET
Label23:
; -- RETURN --
MOV EAX, _C_PROGRAMA_PRIMERA
MOV _RET_VAL_0, EAX
RET
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
