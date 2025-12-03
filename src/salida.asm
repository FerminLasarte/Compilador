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
_DATA_PRUEBA3 dd 0
_VALOR_PRUEBA3_EJECUTARLAMBDA dd 0
_VARL_PRUEBA3_EJECUTARLAMBDA dd 0
_X_PRUEBA3 dd 0
_X_PRUEBA3_lambda_11 dd 0
str_0 db " @3", 13, 10, "    ----------------------------- @4", 13, 10, "    Inicio Test 3: Lambdas @5", 13, 10, "    y Strings Multilinea @6", 13, 10, "    ----------------------------- @7", 13, 10, "    ", 0
str_4 db "Dentro de funcion, invocando lambda...", 0
str_8 db "Llamando con lambda que imprime si es > 2", 0
str_12 db "Valor recibido en Lambda:", 0
str_14 db "Condicion Lambda Cumplida (X > 2)", 0
str_16 db "Condicion Lambda NO Cumplida", 0
str_18 db " @35", 13, 10, "    ----------------------------- @36", 13, 10, "    Fin del Test 3 @37", 13, 10, "    ----------------------------- @38", 13, 10, "    ", 0
.code
start:
Label0:
invoke crt_printf, addr MensajePrint, addr str_0
Label1:
MOV EAX, 5
MOV _DATA_PRUEBA3, EAX
Label2:
JMP Label8
Label3:
__EJECUTARLAMBDA_PRUEBA3:
Label4:
invoke crt_printf, addr MensajePrint, addr str_4
Label5:
Label6:
MOV EAX, _VARL_PRUEBA3_EJECUTARLAMBDA
CALL EAX
Label7:
; -- RETURN --
MOV EAX, 1
RET
Label8:
invoke crt_printf, addr MensajePrint, addr str_8
Label9:
MOV EAX, 5
MOV _X_PRUEBA3, EAX
Label10:
JMP Label18
Label11:
Label12:
invoke crt_printf, addr MensajePrint, addr str_12
Label13:
invoke crt_printf, addr MensajePrintNum, _X_PRUEBA3_lambda_11
Label14:
invoke crt_printf, addr MensajePrint, addr str_14
Label15:
JMP Label17
Label16:
invoke crt_printf, addr MensajePrint, addr str_16
Label17:
; -- RETURN --
RET
Label18:
invoke crt_printf, addr MensajePrint, addr str_18
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
