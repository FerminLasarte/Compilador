.386
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\msvcrt.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\msvcrt.lib
.data
MsgError db "ERROR FATAL: El programa no se compilo correctamente debido a errores en el codigo fuente.", 13, 10, 0
.code
start:
    invoke crt_printf, addr MsgError
    invoke ExitProcess, 1
end start
