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
ErrorDivCero db "Error: Division por cero", 10, 0
ErrorOverflow db "Error: Overflow en operacion", 10, 0
ErrorRestaNegativa db "Error: Resultado negativo en resta de enteros sin signo", 10, 0
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
MOV EAX, 2147483647
MOV _FOP1, EAX
Label4:
fld _FOP1
sub esp, 8
fstp qword ptr [esp]
push offset MensajePrintFloat
call crt_printf
add esp, 12
Label5:
MOV EAX, _A
ADD EAX, _B
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
CMP EAX, 25
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
MOV EAX, 300000
MOV _FOP2, EAX
Label18:
MOV EAX, _FOP1
MUL _FOP2
MOV @aux18, EAX
Label19:
MOV EAX, @aux18
MOV _FRESPROD, EAX
Label20:
MOV EAX, _FOP1
XOR EDX, EDX
CMP _FOP2, 0
JE Error_DivCero
DIV _FOP2
MOV @aux20, EAX
Label21:
MOV EAX, @aux20
MOV _FDIV, EAX
Label22:
fld _FDIV
sub esp, 8
fstp qword ptr [esp]
push offset MensajePrintFloat
call crt_printf
add esp, 12
Label23:
fld _FRESPROD
sub esp, 8
fstp qword ptr [esp]
push offset MensajePrintFloat
call crt_printf
add esp, 12
invoke ExitProcess, 0
Error_DivCero:
invoke crt_printf, addr ErrorDivCero
invoke ExitProcess, 1
end start
