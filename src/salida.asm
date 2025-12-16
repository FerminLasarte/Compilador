.686
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
str_4 db "A llego a 3UI", 0
str_8 db "A llego a 5UI", 0
str_11 db "B llego a 5UI", 0
str_13 db "B no llego a 5UI", 0
str_18 db "A no llego a 5UI", 0
str_23 db "Programa finalizado exitosamente", 0
.code
start:
Label0:
MOV EAX, 8
MOV _A_PROGRAMA, EAX
Label1:
MOV EAX, 0
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
JE Label6
Label4:
invoke crt_printf, addr MensajePrint, addr str_4
Label5:
JMP Label19
Label6:
MOV EAX, _A_PROGRAMA
CMP EAX, 5
SETE AL
MOVZX EAX, AL
MOV @aux6, EAX
Label7:
MOV EAX, @aux6
CMP EAX, 0
JE Label18
Label8:
invoke crt_printf, addr MensajePrint, addr str_8
Label9:
MOV EAX, _B_PROGRAMA
CMP EAX, 5
SETE AL
MOVZX EAX, AL
MOV @aux9, EAX
Label10:
MOV EAX, @aux9
CMP EAX, 0
JE Label13
Label11:
invoke crt_printf, addr MensajePrint, addr str_11
Label12:
JMP Label14
Label13:
invoke crt_printf, addr MensajePrint, addr str_13
Label14:
MOV EAX, _B_PROGRAMA
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux14, EAX
Label15:
MOV EAX, @aux14
MOV _B_PROGRAMA, EAX
Label16:
MOV EAX, _B_PROGRAMA
CMP EAX, 5
SETBE AL
MOVZX EAX, AL
MOV @aux16, EAX
Label17:
MOV EAX, @aux16
CMP EAX, 1
JE Label9
Label18:
invoke crt_printf, addr MensajePrint, addr str_18
Label19:
MOV EAX, _A_PROGRAMA
SUB EAX, 1
JC ErrorRestaNegativa
MOV @aux19, EAX
Label20:
MOV EAX, @aux19
MOV _A_PROGRAMA, EAX
Label21:
MOV EAX, _A_PROGRAMA
CMP EAX, 3
SETAE AL
MOVZX EAX, AL
MOV @aux21, EAX
Label22:
MOV EAX, @aux21
CMP EAX, 1
JE Label2
Label23:
invoke crt_printf, addr MensajePrint, addr str_23
Label24:
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
