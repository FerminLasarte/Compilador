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
_CARLOS_AMIGOS dd 0
_ANA_AMIGOS dd 0
_SOFIA_AMIGOS dd 0
_MIGUEL_AMIGOS dd 0
_VARLUIS_AMIGOS dd 0
_VARLUCIA_AMIGOS dd 0
_MARCOS_AMIGOS dd 0
_REGALO_AMIGOS_REUNIRSE dd 0
_SALUDO_AMIGOS_REUNIRSE dd 0
_JULIA_AMIGOS dd 0
_ROBERTO_AMIGOS dd 0
_INVITADO_AMIGOS dd 0
_HORA_AMIGOS_INVITAR dd 0
_AMIGO_AMIGOS_INVITAR dd 0
str_0 db 13, 10, "    *********************************************", 13, 10, "    * *", 13, 10, "    * BIENVENIDOS AL TEST DE AMIGOS        *", 13, 10, "    * Validando Compilador Grupo 10        *", 13, 10, "    * *", 13, 10, "    *********************************************", 13, 10, "    ", 0
str_7 db 13, 10, "    [ INFO ] Realizando conversion explicita (toui)...", 13, 10, "    Resultados para VARLUIS:", 13, 10, "    ", 0
str_9 db 13, 10, "    ---------------------------------------------", 13, 10, "    [ LOOP ] Carlos esta contando (Do-While):", 13, 10, "    ---------------------------------------------", 13, 10, "    ", 0
str_17 db 13, 10, "    [ ASIG ] Probando Asignacion Multiple...", 13, 10, "    Asignando valores a Lucia y Marcos:", 13, 10, "    ", 0
str_24 db 13, 10, "        ... Dentro de la funcion REUNIRSE ...", 13, 10, "        ", 0
str_25 db "Acceso explicito al ambito de CARLOS:", 0
str_34 db 13, 10, "    ---------------------------------------------", 13, 10, "    [ FUNC ] Llamada con Retornos Multiples y CR-SE/LE", 13, 10, "    Julia y Roberto reciben valores:", 13, 10, "    ---------------------------------------------", 13, 10, "    ", 0
str_49 db 13, 10, "        [ LAMBDA ] Ejecutando funcion anonima...", 13, 10, "        ", 0
.code
start:
Label0:
invoke crt_printf, addr MensajePrint, addr str_0
Label1:
MOV EAX, 10
MOV _CARLOS_AMIGOS, EAX
Label2:
MOV EAX, 20
MOV _ANA_AMIGOS, EAX
Label3:
MOV EAX, 1095237632
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FSTP _SOFIA_AMIGOS
Label4:
MOV EAX, 1073741824
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FSTP _MIGUEL_AMIGOS
Label5:
FLD _SOFIA_AMIGOS
FISTP @aux5
Label6:
MOV EAX, @aux5
MOV _VARLUIS_AMIGOS, EAX
Label7:
invoke crt_printf, addr MensajePrint, addr str_7
Label8:
invoke crt_printf, addr MensajePrintNum, _VARLUIS_AMIGOS
Label9:
invoke crt_printf, addr MensajePrint, addr str_9
Label10:
invoke crt_printf, addr MensajePrintNum, _CARLOS_AMIGOS
Label11:
MOV EAX, _CARLOS_AMIGOS
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux11, EAX
Label12:
MOV EAX, @aux11
MOV _CARLOS_AMIGOS, EAX
Label13:
MOV EAX, _CARLOS_AMIGOS
CMP EAX, 13
SETB AL
MOVZX EAX, AL
MOV @aux13, EAX
Label14:
MOV EAX, @aux13
CMP EAX, 1
JE Label10
Label15:
MOV EAX, 0
MOV _VARLUCIA_AMIGOS, EAX
Label16:
MOV EAX, 0
MOV _MARCOS_AMIGOS, EAX
Label17:
invoke crt_printf, addr MensajePrint, addr str_17
Label18:
MOV EAX, _ANA_AMIGOS
MOV _MARCOS_AMIGOS, EAX
Label19:
MOV EAX, _CARLOS_AMIGOS
MOV _VARLUCIA_AMIGOS, EAX
Label20:
invoke crt_printf, addr MensajePrintNum, _VARLUCIA_AMIGOS
Label21:
invoke crt_printf, addr MensajePrintNum, _MARCOS_AMIGOS
Label22:
JMP Label32
Label23:
__REUNIRSE_AMIGOS:
Label24:
invoke crt_printf, addr MensajePrint, addr str_24
Label25:
invoke crt_printf, addr MensajePrint, addr str_25
Label26:
invoke crt_printf, addr MensajePrintNum, _CARLOS_AMIGOS
Label27:
MOV EAX, 100
MOV _REGALO_AMIGOS_REUNIRSE, EAX
Label28:
MOV EAX, _SALUDO_AMIGOS_REUNIRSE
ADD EAX, 1
CMP EAX, 65535
JA ErrorOverflow
MOV @aux28, EAX
Label29:
MOV EAX, @aux28
MOV _SALUDO_AMIGOS_REUNIRSE, EAX
Label30:
; -- RETURN --
MOV EAX, _REGALO_AMIGOS_REUNIRSE
MOV _RET_VAL_0, EAX
Label31:
; -- RETURN --
MOV EAX, _SALUDO_AMIGOS_REUNIRSE
MOV _RET_VAL_1, EAX
RET
Label32:
MOV EAX, 0
MOV _JULIA_AMIGOS, EAX
Label33:
MOV EAX, 10
MOV _ROBERTO_AMIGOS, EAX
Label34:
invoke crt_printf, addr MensajePrint, addr str_34
Label35:
MOV EAX, _JULIA_AMIGOS
MOV _REGALO_AMIGOS_REUNIRSE, EAX
Label36:
MOV EAX, _ROBERTO_AMIGOS
MOV _SALUDO_AMIGOS_REUNIRSE, EAX
Label37:
CALL __REUNIRSE_AMIGOS
MOV @aux37, EAX
Label38:
MOV EAX, _REGALO_AMIGOS_REUNIRSE
MOV _JULIA_AMIGOS, EAX
Label39:
MOV EAX, _SALUDO_AMIGOS_REUNIRSE
MOV _ROBERTO_AMIGOS, EAX
Label40:
MOV EAX, _RET_VAL_0
MOV @aux40, EAX
Label41:
MOV EAX, @aux40
MOV _JULIA_AMIGOS, EAX
Label42:
MOV EAX, _RET_VAL_1
MOV @aux42, EAX
Label43:
MOV EAX, @aux42
MOV _ROBERTO_AMIGOS, EAX
Label44:
invoke crt_printf, addr MensajePrintNum, _JULIA_AMIGOS
Label45:
invoke crt_printf, addr MensajePrintNum, _ROBERTO_AMIGOS
Label46:
MOV EAX, 5
MOV _INVITADO_AMIGOS, EAX
Label47:
JMP Label53
Label48:
__INVITAR_AMIGOS:
Label49:
invoke crt_printf, addr MensajePrint, addr str_49
Label50:
Label51:
MOV EAX, _AMIGO_AMIGOS_INVITAR
CALL EAX
Label52:
; -- RETURN --
MOV EAX, 1
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
