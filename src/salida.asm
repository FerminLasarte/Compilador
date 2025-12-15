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
_A_PROGRAMA dd 0
_E_PROGRAMA dd 0
_F_PROGRAMA dd 0
_B_PROGRAMA_FUNCION dd 0
_C_PROGRAMA_FUNCION dd 0
_D_PROGRAMA_FUNCION dd 0
.code
start:
Label0:
MOV EAX, 1
MOV _A_PROGRAMA, EAX
Label1:
MOV EAX, 1065353216
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FSTP _E_PROGRAMA
Label2:
MOV EAX, 5
MOV _F_PROGRAMA, EAX
Label3:
JMP Label10
Label4:
__FUNCION_PROGRAMA:
Label5:
MOV EAX, _A_PROGRAMA
ADD EAX, _B_PROGRAMA_FUNCION
CMP EAX, 65535
JA ErrorOverflow
MOV @aux5, EAX
Label6:
MOV EAX, @aux5
MOV _C_PROGRAMA_FUNCION, EAX
Label7:
MOV EAX, 1084227584
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FSTP _D_PROGRAMA_FUNCION
Label8:
; -- RETURN --
MOV EAX, _C_PROGRAMA_FUNCION
MOV _RET_VAL_0, EAX
Label9:
; -- RETURN --
FLD _D_PROGRAMA_FUNCION
FSTP _RET_VAL_1
RET
Label10:
MOV EAX, 4
MOV _B_PROGRAMA_FUNCION, EAX
Label11:
CALL __FUNCION_PROGRAMA
MOV @aux11, EAX
Label12:
MOV EAX, _RET_VAL_0
MOV @aux12, EAX
Label13:
MOV EAX, @aux12
MOV _A_PROGRAMA, EAX
Label14:
FLD _RET_VAL_1
FSTP @aux14
Label15:
FLD @aux14
FSTP _E_PROGRAMA
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
