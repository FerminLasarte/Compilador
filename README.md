# ⚙️ Trabajo de Diseño de Compiladores - UNICEN - 2025

Este proyecto corresponde al **Trabajo de Diseño de Compiladores** de la Universidad Nacional del Centro de la Provincia de Buenos Aires (**UNICEN**). Se trata de un compilador desarrollado completamente en **Java**.

---

## 👨‍💻 Integrantes

* **de Cáseres, Gonzalo**
* **Halty, Héctor Manuel**
* **Lasarte, Fermin**

---

## 🌳 Repositorio

La rama principal (`main`) contiene la **entrega actual** del proyecto, que incluye:

* El **Parser** (Analizador Sintáctico).
* Los **casos de prueba** asociados.
* Un consumidor de **Tokens** para visualizar la salida del Analizador Léxico.

---

## 🚀 Ejecución

Para ejecutar los distintos componentes del compilador, utiliza los siguientes comandos en tu terminal.

### Para la compilación del proyecto
```bash
javac *.java
```

**Nota:** Reemplaza `<ruta>` con la ruta asociada a donde se descargue el proyecto.

### Analizador Léxico (Lexer)

```bash
java Main.java "ruta/pruebas/TP1/caso_tp1.txt"
```

### Analizador Sintáctico (Parser)

```bash
java Parser.java "ruta/pruebas/TP2/caso_tp2.txt"
```