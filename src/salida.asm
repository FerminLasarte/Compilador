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
@aux34 dd 0
@aux35 dd 0
@aux36 dd 0
@aux37 dd 0
@aux38 dd 0
@aux39 dd 0
@aux40 dd 0
@aux41 dd 0
@aux42 dd 0
@aux43 dd 0
@aux44 dd 0
@aux45 dd 0
@aux46 dd 0
@aux47 dd 0
@aux48 dd 0
@aux49 dd 0
@aux50 dd 0
@aux51 dd 0
@aux52 dd 0
@aux53 dd 0
@aux54 dd 0
@aux55 dd 0
@aux56 dd 0
@aux57 dd 0
@aux58 dd 0
@aux59 dd 0
@aux60 dd 0
@aux61 dd 0
@aux62 dd 0
@aux63 dd 0
@aux64 dd 0
@aux65 dd 0
@aux66 dd 0
@aux67 dd 0
@aux68 dd 0
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
_CONTADOR_CASOSINERRORES dd 0
_NUMFLOAT_CASOSINERRORES dd 0
_RES_CASOSINERRORES dd 0
_VARD_CASOSINERRORES dd 0
_SALIDA_CASOSINERRORES_FUNCSOLOESCRITURA dd 0
_VALOR_CASOSINERRORES_EJECUTORLAMBDA dd 0
_FUNCL_CASOSINERRORES_EJECUTORLAMBDA dd 0
_A_CASOSINERRORES dd 0
_B_CASOSINERRORES dd 0
_X_CASOSINERRORES_FUNCRETORNODOBLE dd 0
_CONV_CASOSINERRORES dd 0
_X_CASOSINERRORES dd 0
_X_CASOSINERRORES_lambda_61 dd 0
str_6 db "  [FUNC] Ejecutando FUNCSOLOESCRITURA...", 0
str_11 db "  [FUNC] Ejecutando FUNCRETORNODOBLE...", 0
str_16 db "  [FUNC] Dentro de EJECUTORLAMBDA. Invocando lambda...", 0
str_20 db "=== INICIO DEL PROGRAMA DE PRUEBA ===", 0
str_21 db "--- Prueba 1: Parametros CR SE ---", 0
str_26 db "Valor de VARD despues de la funcion (Esperado: 100):", 0
str_28 db "--- Prueba 2: Asignacion Multiple ---", 0
str_37 db "Valores recibidos de funcion (Esperado: 50, 9.99):", 0
str_42 db "Valores asignados manualmente (Esperado: 10, 205.0):", 0
str_45 db "--- Prueba 3: Conversion TOUI ---", 0
str_49 db "Resultado de toui(20.5):", 0
str_51 db "--- Prueba 4: Bucle DO-WHILE ---", 0
str_52 db "Iteracion numero:", 0
str_58 db "--- Prueba 5: Lambdas ---", 0
str_62 db "    [LAMBDA] Recibi:", 0
str_68 db "=== FIN DEL PROGRAMA === @Numero de linea: 113", 13, 10, "           Ejecucion Exitosa", 0
.code
start:
Label0:
MOV EAX, 3
MOV _CONTADOR_CASOSINERRORES, EAX
Label1:
MOV EAX, 1103626240
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FSTP _NUMFLOAT_CASOSINERRORES
Label2:
MOV EAX, 0
MOV _RES_CASOSINERRORES, EAX
Label3:
MOV EAX, 0
MOV _VARD_CASOSINERRORES, EAX
Label4:
JMP Label9
Label5:
__FUNCSOLOESCRITURA_CASOSINERRORES:
Label6:
invoke crt_printf, addr MensajePrint, addr str_6
Label7:
MOV EAX, 100
MOV _SALIDA_CASOSINERRORES_FUNCSOLOESCRITURA, EAX
Label8:
; -- RETURN --
MOV EAX, 1
MOV _RET_VAL_0, EAX
RET
Label9:
JMP Label14
Label10:
__FUNCRETORNODOBLE_CASOSINERRORES:
Label11:
invoke crt_printf, addr MensajePrint, addr str_11
Label12:
; -- RETURN --
MOV EAX, 50
MOV _RET_VAL_0, EAX
Label13:
; -- RETURN --
MOV EAX, 1120272384
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FSTP _RET_VAL_1
RET
Label14:
JMP Label20
Label15:
__EJECUTORLAMBDA_CASOSINERRORES:
Label16:
invoke crt_printf, addr MensajePrint, addr str_16
Label17:
Label18:
MOV EAX, _FUNCL_CASOSINERRORES_EJECUTORLAMBDA
CALL EAX
Label19:
; -- RETURN --
MOV EAX, 0
MOV _RET_VAL_0, EAX
RET
Label20:
invoke crt_printf, addr MensajePrint, addr str_20
Label21:
invoke crt_printf, addr MensajePrint, addr str_21
Label22:
MOV EAX, _VARD_CASOSINERRORES
MOV _SALIDA_CASOSINERRORES_FUNCSOLOESCRITURA, EAX
Label23:
CALL __FUNCSOLOESCRITURA_CASOSINERRORES
MOV @aux23, EAX
Label24:
MOV EAX, _SALIDA_CASOSINERRORES_FUNCSOLOESCRITURA
MOV _VARD_CASOSINERRORES, EAX
Label25:
MOV EAX, @aux23
MOV _RES_CASOSINERRORES, EAX
Label26:
invoke crt_printf, addr MensajePrint, addr str_26
Label27:
invoke crt_printf, addr MensajePrintNum, _VARD_CASOSINERRORES
Label28:
invoke crt_printf, addr MensajePrint, addr str_28
Label29:
MOV EAX, 0
MOV _A_CASOSINERRORES, EAX
Label30:
MOV EAX, 0
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FSTP _B_CASOSINERRORES
Label31:
MOV EAX, 2
MOV _X_CASOSINERRORES_FUNCRETORNODOBLE, EAX
Label32:
CALL __FUNCRETORNODOBLE_CASOSINERRORES
MOV @aux32, EAX
Label33:
MOV EAX, _RET_VAL_0
MOV @aux33, EAX
Label34:
MOV EAX, @aux33
MOV _A_CASOSINERRORES, EAX
Label35:
FLD _RET_VAL_1
FSTP @aux35
Label36:
FLD @aux35
FSTP _B_CASOSINERRORES
Label37:
invoke crt_printf, addr MensajePrint, addr str_37
Label38:
invoke crt_printf, addr MensajePrintNum, _A_CASOSINERRORES
Label39:
FLD _B_CASOSINERRORES
sub esp, 8
fstp qword ptr [esp]
push offset MensajePrintFloat
call crt_printf
add esp, 12
Label40:
MOV EAX, 1129119744
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FSTP _B_CASOSINERRORES
Label41:
MOV EAX, 10
MOV _A_CASOSINERRORES, EAX
Label42:
invoke crt_printf, addr MensajePrint, addr str_42
Label43:
invoke crt_printf, addr MensajePrintNum, _A_CASOSINERRORES
Label44:
FLD _B_CASOSINERRORES
sub esp, 8
fstp qword ptr [esp]
push offset MensajePrintFloat
call crt_printf
add esp, 12
Label45:
invoke crt_printf, addr MensajePrint, addr str_45
Label46:
MOV EAX, 0
MOV _CONV_CASOSINERRORES, EAX
Label47:
FLD _B_CASOSINERRORES
FISTP @aux47
Label48:
MOV EAX, @aux47
MOV _CONV_CASOSINERRORES, EAX
Label49:
invoke crt_printf, addr MensajePrint, addr str_49
Label50:
invoke crt_printf, addr MensajePrintNum, _CONV_CASOSINERRORES
Label51:
invoke crt_printf, addr MensajePrint, addr str_51
Label52:
invoke crt_printf, addr MensajePrint, addr str_52
Label53:
invoke crt_printf, addr MensajePrintNum, _CONTADOR_CASOSINERRORES
Label54:
MOV EAX, _CONTADOR_CASOSINERRORES
SUB EAX, 1
JC ErrorRestaNegativa
MOV @aux54, EAX
Label55:
MOV EAX, @aux54
MOV _CONTADOR_CASOSINERRORES, EAX
Label56:
MOV EAX, _CONTADOR_CASOSINERRORES
CMP EAX, 0
SETA AL
MOVZX EAX, AL
MOV @aux56, EAX
Label57:
MOV EAX, @aux56
CMP EAX, 1
JE Label52
Label58:
invoke crt_printf, addr MensajePrint, addr str_58
Label59:
MOV EAX, 987
MOV _X_CASOSINERRORES, EAX
Label60:
JMP Label65
Label61:
Label62:
invoke crt_printf, addr MensajePrint, addr str_62
Label63:
invoke crt_printf, addr MensajePrintNum, _X_CASOSINERRORES
Label64:
; -- RETURN --
RET
Label65:
MOV EAX, Label61
MOV _FUNCL_CASOSINERRORES_EJECUTORLAMBDA, EAX
Label66:
MOV EAX, 777
MOV _VALOR_CASOSINERRORES_EJECUTORLAMBDA, EAX
Label67:
CALL __EJECUTORLAMBDA_CASOSINERRORES
MOV @aux67, EAX
Label68:
invoke crt_printf, addr MensajePrint, addr str_68
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
