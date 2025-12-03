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
@aux28 dd 0
@aux29 dd 0
@aux30 dd 0
@aux31 dd 0
@aux32 dd 0
@aux33 dd 0
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
_CONTADOR_PRUEBA2 dd 0
_EXTERNA_PRUEBA2 dd 0
_SALIDA_PRUEBA2_FUNCTEST dd 0
_R1_PRUEBA2 dd 0
_R2_PRUEBA2 dd 0
_R3_PRUEBA2 dd 0
str_0 db "Inicio Test 2: Control y Funciones", 0
str_12 db "Ciclo Do-While nro:", 0
str_23 db "Retornos recibidos:", 0
str_24 db "R1:", 0
str_26 db "R2:", 0
str_32 db "Valor de EXTERNA tras Copia-Resultado (Debe ser 99):", 0
.code
start:
Label0:
invoke crt_printf, addr MensajePrint, addr str_0
Label1:
MOV EAX, 0
MOV _CONTADOR_PRUEBA2, EAX
Label2:
MOV EAX, 10
MOV _EXTERNA_PRUEBA2, EAX
Label3:
JMP Label9
Label4:
__FUNCTEST_PRUEBA2:
Label5:
MOV EAX, 99
MOV _SALIDA_PRUEBA2_FUNCTEST, EAX
Label6:
; -- RETURN --
MOV EAX, 10
MOV _RET_VAL_0, EAX
Label7:
; -- RETURN --
MOV EAX, 20
MOV _RET_VAL_1, EAX
Label8:
; -- RETURN --
MOV EAX, 30
MOV _RET_VAL_2, EAX
RET
Label9:
MOV EAX, 0
MOV _R1_PRUEBA2, EAX
Label10:
MOV EAX, 0
MOV _R2_PRUEBA2, EAX
Label11:
MOV EAX, 0
MOV _R3_PRUEBA2, EAX
Label12:
invoke crt_printf, addr MensajePrint, addr str_12
Label13:
invoke crt_printf, addr MensajePrintNum, _CONTADOR_PRUEBA2
Label14:
MOV EAX, _EXTERNA_PRUEBA2
MOV _SALIDA_PRUEBA2_FUNCTEST, EAX
Label15:
CALL __FUNCTEST_PRUEBA2
MOV @aux15, EAX
Label16:
MOV EAX, _SALIDA_PRUEBA2_FUNCTEST
MOV _EXTERNA_PRUEBA2, EAX
Label17:
MOV EAX, _RET_VAL_0
MOV @aux17, EAX
Label18:
MOV EAX, @aux17
MOV _R1_PRUEBA2, EAX
Label19:
MOV EAX, _RET_VAL_1
MOV @aux19, EAX
Label20:
MOV EAX, @aux19
MOV _R2_PRUEBA2, EAX
Label21:
MOV EAX, _RET_VAL_2
MOV @aux21, EAX
Label22:
MOV EAX, @aux21
MOV _R3_PRUEBA2, EAX
Label23:
invoke crt_printf, addr MensajePrint, addr str_23
Label24:
invoke crt_printf, addr MensajePrint, addr str_24
Label25:
invoke crt_printf, addr MensajePrintNum, _R1_PRUEBA2
Label26:
invoke crt_printf, addr MensajePrint, addr str_26
Label27:
invoke crt_printf, addr MensajePrintNum, _R2_PRUEBA2
Label28:
MOV EAX, _CONTADOR_PRUEBA2
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux28, EAX
Label29:
MOV EAX, @aux28
MOV _CONTADOR_PRUEBA2, EAX
Label30:
MOV EAX, _CONTADOR_PRUEBA2
CMP EAX, 2
SETB AL
MOVZX EAX, AL
MOV @aux30, EAX
Label31:
MOV EAX, @aux30
CMP EAX, 1
JE Label12
Label32:
invoke crt_printf, addr MensajePrint, addr str_32
Label33:
invoke crt_printf, addr MensajePrintNum, _EXTERNA_PRUEBA2
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
