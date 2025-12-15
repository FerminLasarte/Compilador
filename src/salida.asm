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
str_4 db "A llego a 3UI", 0
str_9 db "A vale 5UI", 0
str_14 db "B llego a 5UI", 0
str_19 db "A: ", 0
str_21 db "B: ", 0
.code
start:
Label0:
MOV EAX, 3
MOV _A_PROGRAMA, EAX
Label1:
MOV EAX, 3
MOV _B_PROGRAMA, EAX
Label2:
MOV EAX, _A_PROGRAMA
CMP EAX, 3
SETE AL
MOVZX EAX, AL
MOV @aux2, EAX
Label3:
MOV EAX, @aux2
CMP EAX, 0
JE Label7
Label4:
invoke crt_printf, addr MensajePrint, addr str_4
Label5:
MOV EAX, _A_PROGRAMA
ADD EAX, 2
CMP EAX, 65535
JA ErrorOverflow
MOV @aux5, EAX
Label6:
MOV EAX, @aux5
MOV _A_PROGRAMA, EAX
Label7:
MOV EAX, _A_PROGRAMA
CMP EAX, 5
SETE AL
MOVZX EAX, AL
MOV @aux7, EAX
Label8:
MOV EAX, @aux7
CMP EAX, 0
JE Label12
Label9:
invoke crt_printf, addr MensajePrint, addr str_9
Label10:
MOV EAX, _B_PROGRAMA
ADD EAX, 2
CMP EAX, 65535
JA ErrorOverflow
MOV @aux10, EAX
Label11:
MOV EAX, @aux10
MOV _B_PROGRAMA, EAX
Label12:
MOV EAX, _B_PROGRAMA
CMP EAX, 5
SETE AL
MOVZX EAX, AL
MOV @aux12, EAX
Label13:
MOV EAX, @aux12
CMP EAX, 0
JE Label17
Label14:
invoke crt_printf, addr MensajePrint, addr str_14
Label15:
MOV EAX, 7
SUB EAX, 4
JC ErrorRestaNegativa
MOV @aux15, EAX
Label16:
MOV EAX, @aux15
MOV _B_PROGRAMA, EAX
Label17:
MOV EAX, 9
SUB EAX, 4
JC ErrorRestaNegativa
MOV @aux17, EAX
Label18:
MOV EAX, @aux17
MOV _A_PROGRAMA, EAX
Label19:
invoke crt_printf, addr MensajePrint, addr str_19
Label20:
invoke crt_printf, addr MensajePrintNum, _A_PROGRAMA
Label21:
invoke crt_printf, addr MensajePrint, addr str_21
Label22:
invoke crt_printf, addr MensajePrintNum, _B_PROGRAMA
Label23:
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
