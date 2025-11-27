# ⚙️ Trabajo de Diseño de Compiladores - UNICEN - 2025

Este proyecto corresponde al **Trabajo de Diseño de Compiladores** de la Universidad Nacional del Centro de la Provincia de Buenos Aires (**UNICEN**). Se trata de un compilador desarrollado completamente en **Java**.

---

## 👨‍💻 Integrantes

* **de Cáseres, Gonzalo**
* **Halty, Héctor Manuel**
* **Lasarte, Fermin**

---

## 🌳 Repositorio

La rama principal (`main`) contiene la **entrega final** del proyecto, que incluye:

* El **Analizador Léxico**.
* El **Parser** generado con Yacc.
* La generación de **Código Intermedio**.
* La generación de **Código Assembler**.
* El **caso de prueba** asociado.

---

## 🚀 Ejecución

Para compilar el proyecto, generar el código ensamblador y crear el ejecutable final, sigue la siguiente secuencia de comandos en tu terminal.

> **Nota:** Asegúrate de tener instalado **Masm32** en la ruta `C:\masm32` para los pasos de ensamblado y linkeo.

### 1. Generación y Compilación del Compilador
Genera el analizador sintáctico y compila los archivos Java:

```bash
yacc -J -v gramatica.y  
javac *.java
java Main ..\pruebas\caso_final.txt
C:\masm32\bin\ml /c /Zd /coff salida.asm
C:\masm32\bin\Link /SUBSYSTEM:CONSOLE salida.obj
.\salida.exe
