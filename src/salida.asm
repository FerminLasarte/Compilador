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
_A dd 0
_B dd 0
_FOP1 dd 0
_C dd 0
_FOP2 dd 0
_FRESPROD dd 0
_FDIV dd 0
str_0 db "Prueba de Operaciones", 0
str_7 db "Suma A + B:", 0
str_10 db "C es mayor a 25", 0
str_16 db "Fin del programa", 0
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
CMP EAX, 59
SETA AL
MOVZX EAX, AL
MOV @aux9, EAX
Label10:
invoke crt_printf, addr MensajePrint, addr str_10
Label11:
invoke crt_printf, addr MensajePrintNum, _B
Label12:
MOV EAX, _B
SUB EAX, 1
JC ErrorRestaNegativa
MOV @aux12, EAX
Label13:
MOV EAX, @aux12
MOV _B, EAX
Label14:
MOV EAX, _B
CMP EAX, 7
SETA AL
MOVZX EAX, AL
MOV @aux14, EAX
Label15:
MOV EAX, @aux14
CMP EAX, 1
JE Label11
Label16:
invoke crt_printf, addr MensajePrint, addr str_16
Label17:
MOV EAX, 1351942905
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FSTP _FOP1
Label18:
MOV EAX, 1217559552
PUSH EAX
FLD DWORD PTR [ESP]
ADD ESP, 4
FSTP _FOP2
Label19:
FLD _FOP1
FLD _FOP2
FMUL
FSTSW AX
TEST AX, 8
JNZ ErrorOverflow
FSTP @aux19
Label20:
FLD @aux19
FSTP _FRESPROD
Label21:
FLD _FOP2
FTST
FSTSW AX
SAHF
JE Error_DivCero
FSTP ST(0)
FLD _FOP1
FLD _FOP2
FDIV
FSTP @aux21
Label22:
FLD @aux21
FSTP _FDIV
Label23:
FLD _FDIV
sub esp, 8
fstp qword ptr [esp]
push offset MensajePrintFloat
call crt_printf
add esp, 12
Label24:
FLD _FRESPROD
sub esp, 8
fstp qword ptr [esp]
push offset MensajePrintFloat
call crt_printf
add esp, 12
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
