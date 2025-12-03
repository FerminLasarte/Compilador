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
_CONTADOR_PRUEBA2 dd 0
_EXTERNA_PRUEBA2 dd 0
_SALIDA_PRUEBA2_FUNCTEST dd 0
_R1_PRUEBA2 dd 0
_R2_PRUEBA2 dd 0
str_0 db "Inicio Test 2: Control y Funciones", 0
str_11 db "Ciclo Do-While nro:", 0
str_20 db "Retornos recibidos:", 0
str_27 db "Valor de EXTERNA tras Copia-Resultado (Debe ser 99):", 0
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
RET
Label7:
; -- RETURN --
MOV EAX, 20
RET
Label8:
; -- RETURN --
MOV EAX, 30
RET
Label9:
MOV EAX, 0
MOV _R1_PRUEBA2, EAX
Label10:
MOV EAX, 0
MOV _R2_PRUEBA2, EAX
Label11:
invoke crt_printf, addr MensajePrint, addr str_11
Label12:
invoke crt_printf, addr MensajePrintNum, _CONTADOR_PRUEBA2
Label13:
MOV EAX, _EXTERNA_PRUEBA2
MOV _SALIDA_PRUEBA2_FUNCTEST, EAX
Label14:
CALL __FUNCTEST_PRUEBA2
MOV @aux14, EAX
Label15:
MOV EAX, _SALIDA_PRUEBA2_FUNCTEST
MOV _EXTERNA_PRUEBA2, EAX
Label16:
MOV EAX, @aux14
MOV @aux16, EAX
Label17:
MOV EAX, @aux16
MOV _R1_PRUEBA2, EAX
Label18:
MOV EAX, @aux14
MOV @aux18, EAX
Label19:
MOV EAX, @aux18
MOV _R2_PRUEBA2, EAX
Label20:
invoke crt_printf, addr MensajePrint, addr str_20
Label21:
invoke crt_printf, addr MensajePrintNum, _R1_PRUEBA2
Label22:
invoke crt_printf, addr MensajePrintNum, _R2_PRUEBA2
Label23:
MOV EAX, _CONTADOR_PRUEBA2
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux23, EAX
Label24:
MOV EAX, @aux23
MOV _CONTADOR_PRUEBA2, EAX
Label25:
MOV EAX, _CONTADOR_PRUEBA2
CMP EAX, 2
SETB AL
MOVZX EAX, AL
MOV @aux25, EAX
Label26:
MOV EAX, @aux25
CMP EAX, 1
JE Label11
Label27:
invoke crt_printf, addr MensajePrint, addr str_27
Label28:
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
