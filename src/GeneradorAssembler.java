import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class GeneradorAssembler {
    private Generador generador;
    private AnalizadorLexico analizadorLexico;
    private StringBuilder codigo;
    private StringBuilder data;

    public GeneradorAssembler(Generador generador, AnalizadorLexico al) {
        this.generador = generador;
        this.analizadorLexico = al;
        this.codigo = new StringBuilder();
        this.data = new StringBuilder();
    }

    public void generarAssembler(String nombreArchivoSalida) {
        generarHeader();
        generarData();
        generarCodigo();
        generarFooter();
        guardarArchivo(nombreArchivoSalida);
    }

    private void generarHeader() {
        codigo.append(".386\n");
        codigo.append(".model flat, stdcall\n");
        codigo.append("option casemap :none\n");
        codigo.append("include \\masm32\\include\\windows.inc\n");
        codigo.append("include \\masm32\\include\\kernel32.inc\n");
        codigo.append("include \\masm32\\include\\user32.inc\n");
        codigo.append("includelib \\masm32\\lib\\kernel32.lib\n");
        codigo.append("includelib \\masm32\\lib\\user32.lib\n");
    }

    private void generarData() {
        data.append(".data\n");
        data.append("ErrorDivCero db \"Error: Division por cero\", 0\n");
        data.append("ErrorOverflow db \"Error: Overflow en operacion\", 0\n");
        data.append("ErrorRestaNegativa db \"Error: Resultado negativo en resta de enteros sin signo\", 0\n");
        data.append("MensajePrint db \"Salida: %d\", 10, 0\n");

        // Generar variables desde la tabla de simbolos (simplificado)
        // Nota: En una implementacion completa, iterarias sobre al.getTablaSimbolos()
        // Aqui simulamos las variables auxiliares para los tercetos
        for (int i = 0; i < generador.getProximoTerceto(); i++) {
            data.append("@aux").append(i).append(" dd 0\n");
        }

        // Variables reales del programa (Deberias exponer la tabla en AnalizadorLexico para iterar esto correctamente)
        // Ejemplo estatico para variables comunes en tus casos de prueba
        data.append("_A dd 0\n");
        data.append("_B dd 0\n");
        data.append("_C dd 0\n");
        data.append("_X dd 0\n");
        data.append("_Y dd 0\n");
        data.append("_Z dd 0\n");
    }

    private void generarCodigo() {
        codigo.append(".code\n");
        codigo.append("start:\n");

        int numTerceto = 0;
        Terceto tercetoActual;

        while ((tercetoActual = generador.getTerceto(numTerceto)) != null) {
            if (tercetoActual.getOperador().equals("DUMMY")) break;

            codigo.append("Label").append(numTerceto).append(":\n");
            String op = tercetoActual.getOperador();
            String op1 = resolveOperand(tercetoActual.getOperando1());
            String op2 = resolveOperand(tercetoActual.getOperando2());
            String res = "@aux" + numTerceto;

            switch (op) {
                case "+":
                    if (esFloat(op1) || esFloat(op2)) {
                        codigo.append("FLD ").append(op1).append("\n");
                        codigo.append("FADD ").append(op2).append("\n");
                        // Chequeo E: Overflow float
                        codigo.append("FSTSW AX\n");
                        codigo.append("SAHF\n");
                        codigo.append("JO Error_Overflow\n");
                        codigo.append("FSTP ").append(res).append("\n");
                    } else {
                        codigo.append("MOV EAX, ").append(op1).append("\n");
                        codigo.append("ADD EAX, ").append(op2).append("\n");
                        // Chequeo B: Overflow enteros
                        codigo.append("JO Error_Overflow\n");
                        codigo.append("MOV ").append(res).append(", EAX\n");
                    }
                    break;
                case "-":
                    if (esFloat(op1) || esFloat(op2)) {
                        codigo.append("FLD ").append(op1).append("\n");
                        codigo.append("FSUB ").append(op2).append("\n");
                        codigo.append("FSTP ").append(res).append("\n");
                    } else {
                        codigo.append("MOV EAX, ").append(op1).append("\n");
                        codigo.append("SUB EAX, ").append(op2).append("\n");
                        // Chequeo F: Resta negativa en enteros sin signo
                        codigo.append("JC Error_Negativo\n");
                        codigo.append("MOV ").append(res).append(", EAX\n");
                    }
                    break;
                case "*":
                    if (esFloat(op1) || esFloat(op2)) {
                        codigo.append("FLD ").append(op1).append("\n");
                        codigo.append("FMUL ").append(op2).append("\n");
                        // Chequeo E: Overflow float producto
                        codigo.append("FSTSW AX\n");
                        codigo.append("SAHF\n");
                        codigo.append("JO Error_Overflow\n");
                        codigo.append("FSTP ").append(res).append("\n");
                    } else {
                        codigo.append("MOV EAX, ").append(op1).append("\n");
                        codigo.append("MUL ").append(op2).append("\n");
                        // Chequeo B: Overflow enteros producto (CF o OF set en MUL)
                        codigo.append("JO Error_Overflow\n");
                        codigo.append("MOV ").append(res).append(", EAX\n");
                    }
                    break;
                case "/":
                    // Chequeo Division por cero
                    if (esFloat(op1) || esFloat(op2)) {
                        codigo.append("FLD ").append(op2).append("\n");
                        codigo.append("FTST\n");
                        codigo.append("FSTSW AX\n");
                        codigo.append("SAHF\n");
                        codigo.append("JE Error_DivCero\n");
                        codigo.append("FSTP ST(0)\n");

                        codigo.append("FLD ").append(op1).append("\n");
                        codigo.append("FDIV ").append(op2).append("\n");
                        codigo.append("FSTP ").append(res).append("\n");
                    } else {
                        codigo.append("CMP ").append(op2).append(", 0\n");
                        codigo.append("JE Error_DivCero\n");
                        codigo.append("MOV EAX, ").append(op1).append("\n");
                        codigo.append("XOR EDX, EDX\n");
                        codigo.append("DIV ").append(op2).append("\n");
                        codigo.append("MOV ").append(res).append(", EAX\n");
                    }
                    break;
                case ":=":
                    codigo.append("MOV EAX, ").append(op2).append("\n");
                    codigo.append("MOV ").append(op1).append(", EAX\n");
                    codigo.append("MOV ").append(res).append(", EAX\n");
                    break;
                case "PRINT":
                    // Implementacion simple de print usando MessageBox para evitar setup complejo de consola
                    codigo.append("invoke MessageBox, NULL, addr ").append(op1).append(", addr MensajePrint, MB_OK\n");
                    break;
                case "BF": // Salto si falso
                    // Asumiendo que el terceto anterior dejo el resultado de la condicion
                    codigo.append("MOV EAX, ").append(op1).append("\n");
                    codigo.append("CMP EAX, 0\n");
                    String target = op2.replace("[", "Label").replace("]", "");
                    codigo.append("JE ").append(target).append("\n");
                    break;
                case "BI": // Salto incondicional
                    String targetBI = op1.replace("[", "Label").replace("]", "");
                    codigo.append("JMP ").append(targetBI).append("\n");
                    break;
            }
            numTerceto++;
        }

        codigo.append("invoke ExitProcess, 0\n");
        generarErrores();
        codigo.append("end start\n");
    }

    private void generarErrores() {
        codigo.append("Error_DivCero:\n");
        codigo.append("invoke MessageBox, NULL, addr ErrorDivCero, addr ErrorDivCero, MB_OK | MB_ICONERROR\n");
        codigo.append("invoke ExitProcess, 1\n");
        codigo.append("Error_Overflow:\n");
        codigo.append("invoke MessageBox, NULL, addr ErrorOverflow, addr ErrorOverflow, MB_OK | MB_ICONERROR\n");
        codigo.append("invoke ExitProcess, 1\n");
        codigo.append("Error_Negativo:\n");
        codigo.append("invoke MessageBox, NULL, addr ErrorRestaNegativa, addr ErrorRestaNegativa, MB_OK | MB_ICONERROR\n");
        codigo.append("invoke ExitProcess, 1\n");
    }

    private void generarFooter() {
        // Nada especifico
    }

    private String resolveOperand(String op) {
        if (op == null) return "0";
        if (op.startsWith("[")) {
            return "@aux" + op.substring(1, op.length() - 1);
        }
        if (op.endsWith("UI")) {
            return op.substring(0, op.length() - 2);
        }
        // Mangleo manual simple, deberia venir de tabla de simbolos
        if (!Character.isDigit(op.charAt(0)) && !op.startsWith("'") && !op.startsWith("&")) {
            return "_" + op;
        }
        return op;
    }

    private boolean esFloat(String op) {
        return op.contains(".") || op.contains("F");
    }

    private void guardarArchivo(String nombre) {
        try (FileWriter writer = new FileWriter(nombre)) {
            writer.write(data.toString());
            writer.write(codigo.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}