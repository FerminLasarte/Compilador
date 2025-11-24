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
_A dd 0
_B dd 0
_FOP1 dd 0
_C dd 0
_FOP2 dd 0
_FRESPROD dd 0
_FDIV dd 0
_AE dd 0
_BE dd 0
_CE dd 0
_JE dd 0
str_0 db "Prueba de Operaciones", 0
str_7 db "Suma A + B:", 0
str_11 db "C es mayor a 25", 0
str_17 db "Fin del programa", 0
str_41 db "FUNCION", 0
.code
start:
Label0:
invoke crt_printf, addr MensajePrint, addr str_0
Label1:
MOV EAX, 20
MOV _A, EAX
Label2:
MOV EAX, 10
MOV _B, EAX
Label3:
MOV EAX, 1351942905
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FSTP _FOP1
Label4:
FLD _FOP1
sub esp, 8
fstp qword ptr [esp]
push offset MensajePrintFloat
call crt_printf
add esp, 12
Label5:
MOV EAX, _A
ADD EAX, _B
JC ErrorOverflow
MOV @aux5, EAX
Label6:
MOV EAX, @aux5
MOV _C, EAX
Label7:
invoke crt_printf, addr MensajePrint, addr str_7
Label8:
invoke crt_printf, addr MensajePrintNum, _C
Label9:
MOV EAX, _C
CMP EAX, 100
SETA AL
MOVZX EAX, AL
MOV @aux9, EAX
Label10:
MOV EAX, @aux9
CMP EAX, 0
JE Label12
Label11:
invoke crt_printf, addr MensajePrint, addr str_11
Label12:
invoke crt_printf, addr MensajePrintNum, _B
Label13:
MOV EAX, _B
SUB EAX, 1
JC ErrorRestaNegativa
MOV @aux13, EAX
Label14:
MOV EAX, @aux13
MOV _B, EAX
Label15:
MOV EAX, _B
CMP EAX, 5
SETA AL
MOVZX EAX, AL
MOV @aux15, EAX
Label16:
MOV EAX, @aux15
CMP EAX, 1
JE Label12
Label17:
invoke crt_printf, addr MensajePrint, addr str_17
Label18:
MOV EAX, 1351942905
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FSTP _FOP1
Label19:
MOV EAX, 1217559552
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FSTP _FOP2
Label20:
FLD _FOP1
FLD _FOP2
FMUL
FSTSW AX
TEST AX, 8
JNZ ErrorOverflow
FSTP @aux20
Label21:
FLD @aux20
FSTP _FRESPROD
Label22:
FLD _FOP2
FTST
FSTSW AX
SAHF
JE Error_DivCero
FSTP ST(0)
FLD _FOP1
FLD _FOP2
FDIV
FSTP @aux22
Label23:
FLD @aux22
FSTP _FDIV
Label24:
FLD _FDIV
sub esp, 8
fstp qword ptr [esp]
push offset MensajePrintFloat
call crt_printf
add esp, 12
Label25:
FLD _FRESPROD
sub esp, 8
fstp qword ptr [esp]
push offset MensajePrintFloat
call crt_printf
add esp, 12
Label26:
MOV EAX, 1
MOV _AE, EAX
Label27:
MOV EAX, 2
MOV _BE, EAX
Label28:
MOV EAX, 3
MOV _CE, EAX
Label29:
invoke crt_printf, addr MensajePrintNum, _AE
Label30:
invoke crt_printf, addr MensajePrintNum, _BE
Label31:
invoke crt_printf, addr MensajePrintNum, _CE
Label32:
MOV EAX, 30
MOV _CE, EAX
Label33:
MOV EAX, 20
MOV _BE, EAX
Label34:
MOV EAX, 10
MOV _AE, EAX
Label35:
invoke crt_printf, addr MensajePrintNum, _AE
Label36:
invoke crt_printf, addr MensajePrintNum, _BE
Label37:
invoke crt_printf, addr MensajePrintNum, _CE
Label38:
MOV EAX, 0
MOV _JE, EAX
Label39:
; JMP UNRESOLVED (_)
Label40:
__FUNCR:
Label41:
invoke crt_printf, addr MensajePrint, addr str_41
Label42:
RET
Label43:
PUSH 11
Label44:
CALL __FUNCR
MOV @aux44, EAX
Label45:
MOV EAX, 20
MOV _BE, EAX
Label46:
MOV EAX, @aux44
MOV _AE, EAX
Label47:
invoke crt_printf, addr MensajePrintNum, _AE
Label48:
invoke crt_printf, addr MensajePrintNum, _BE
Label49:
PUSH 11
Label50:
CALL __FUNCR
MOV @aux50, EAX
Label51:
MOV EAX, @aux50
MOV _AE, EAX
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
