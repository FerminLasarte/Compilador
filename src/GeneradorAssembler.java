import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
        data.append("MensajePrint db \"Salida: %s\", 10, 0\n");
        data.append("MensajePrintNum db \"Salida: %d\", 10, 0\n");
        data.append("MensajePrintFloat db \"Salida: %f\", 10, 0\n");

        // Variables auxiliares para tercetos
        for (int i = 0; i < generador.getProximoTerceto(); i++) {
            data.append("@aux").append(i).append(" dd 0\n");
        }

        // Deteccion y declaracion automatica de variables
        Set<String> variablesDeclaradas = new HashSet<>();
        for (int i = 0; i < generador.getProximoTerceto(); i++) {
            Terceto t = generador.getTerceto(i);
            if (t != null) {
                checkAndAddVariable(t.getOperando1(), variablesDeclaradas);
                checkAndAddVariable(t.getOperando2(), variablesDeclaradas);
            }
        }
    }

    private void checkAndAddVariable(String op, Set<String> declaradas) {
        if (op == null || op.equals("_") || op.startsWith("[") || op.startsWith("L") ||
                Character.isDigit(op.charAt(0)) || op.startsWith("\'") || op.startsWith("&")) {
            return;
        }
        // Verificar si es una constante float o uint
        if (op.contains(".") || op.endsWith("UI")) return;

        String varName = resolveOperand(op);
        // Evitar declarar registros o nombres reservados
        if (varName.equalsIgnoreCase("EAX") || varName.equalsIgnoreCase("EBX")) return;

        if (!declaradas.contains(varName)) {
            data.append(varName).append(" dd 0\n");
            declaradas.add(varName);
        }
    }

    private void generarCodigo() {
        codigo.append(".code\n");
        codigo.append("start:\n");

        int numTerceto = 0;
        Terceto tercetoActual;

        while ((tercetoActual = generador.getTerceto(numTerceto)) != null) {
            if (tercetoActual.getOperador().equals("DUMMY")) {
                numTerceto++;
                continue;
            }

            // Etiqueta para el terceto actual
            codigo.append("Label").append(numTerceto).append(":\n");

            String op = tercetoActual.getOperador();
            String op1 = resolveOperand(tercetoActual.getOperando1());
            String op2 = resolveOperand(tercetoActual.getOperando2());
            String res = "@aux" + numTerceto;

            switch (op) {
                case "+":
                    codigo.append("MOV EAX, ").append(op1).append("\n");
                    codigo.append("ADD EAX, ").append(op2).append("\n");
                    codigo.append("MOV ").append(res).append(", EAX\n");
                    break;
                case "-":
                    codigo.append("MOV EAX, ").append(op1).append("\n");
                    codigo.append("SUB EAX, ").append(op2).append("\n");
                    codigo.append("MOV ").append(res).append(", EAX\n");
                    break;
                case "*":
                    codigo.append("MOV EAX, ").append(op1).append("\n");
                    codigo.append("MUL ").append(op2).append("\n");
                    codigo.append("MOV ").append(res).append(", EAX\n");
                    break;
                case "/":
                    codigo.append("MOV EAX, ").append(op1).append("\n");
                    codigo.append("XOR EDX, EDX\n");
                    codigo.append("CMP ").append(op2).append(", 0\n");
                    codigo.append("JE Error_DivCero\n");
                    codigo.append("DIV ").append(op2).append("\n");
                    codigo.append("MOV ").append(res).append(", EAX\n");
                    break;
                case ":=":
                    codigo.append("MOV EAX, ").append(op2).append("\n");
                    codigo.append("MOV ").append(op1).append(", EAX\n");
                    break;
                case "PRINT":
                    if (op1.startsWith("&")) {
                        // String literal
                        String strName = "str_" + numTerceto;
                        data.append(strName).append(" db \"").append(op1.replace("&", "")).append("\", 0\n");
                        codigo.append("invoke MessageBox, NULL, addr ").append(strName).append(", addr MensajePrint, MB_OK\n");
                    } else {
                        // Asumimos numero para simplificar
                        codigo.append("invoke MessageBox, NULL, addr ").append(op1).append(", addr MensajePrintNum, MB_OK\n");
                    }
                    break;
                case "BF": // Salto si Falso
                    codigo.append("MOV EAX, ").append(op1).append("\n");
                    codigo.append("CMP EAX, 0\n");
                    // Extraer numero de etiqueta del formato [N]
                    String targetBF = op2.replace("[", "Label").replace("]", "");
                    codigo.append("JE ").append(targetBF).append("\n");
                    break;
                case "BI": // Salto Incondicional
                    String targetBI = op1.replace("[", "Label").replace("]", "");
                    codigo.append("JMP ").append(targetBI).append("\n");
                    break;
                case "BT": // Salto si Verdadero (Usado en DO-WHILE segun gramatica)
                    codigo.append("MOV EAX, ").append(op1).append("\n");
                    codigo.append("CMP EAX, 1\n");
                    String targetBT = op2.replace("[", "Label").replace("]", "");
                    codigo.append("JE ").append(targetBT).append("\n");
                    break;
                case ">":
                case "<":
                case ">=":
                case "<=":
                case "==":
                case "=!":
                    codigo.append("MOV EAX, ").append(op1).append("\n");
                    codigo.append("CMP EAX, ").append(op2).append("\n");
                    // Logica simple: setear 1 si true, 0 si false en res
                    // Se requeriria saltos condicionales especificos (JG, JL, etc)
                    codigo.append("MOV ").append(res).append(", 0\n"); // Placeholder
                    break;
                case "FUNC_LABEL":
                    codigo.append(op1).append(":\n");
                    break;
                case "RETURN":
                case "RET_LAMBDA":
                    codigo.append("RET\n");
                    break;
                case "CALL":
                    codigo.append("CALL ").append(op1).append("\n");
                    codigo.append("MOV ").append(res).append(", EAX\n"); // Guardar retorno
                    break;
                case "CALL_LAMBDA":
                    // Call indirecto
                    codigo.append("MOV EAX, ").append(op1).append("\n");
                    codigo.append("CALL EAX\n");
                    break;
                case "PARAM":
                    codigo.append("PUSH ").append(op1).append("\n");
                    break;
                case "TOUI":
                    // Conversion simple (truncado)
                    codigo.append("FLD ").append(op1).append("\n");
                    codigo.append("FISTP ").append(res).append("\n");
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
        codigo.append("invoke MessageBox, NULL, addr ErrorDivCero, addr ErrorDivCero, MB_OK\n");
        codigo.append("invoke ExitProcess, 1\n");
    }

    private void generarFooter() { }

    private String resolveOperand(String op) {
        if (op == null) return "0";
        if (op.startsWith("[")) {
            return "@aux" + op.substring(1, op.length() - 1);
        }
        if (op.endsWith("UI")) {
            return op.substring(0, op.length() - 2);
        }
        if (op.endsWith("F")) { // Simplificacion float
            // Para MASM float literal es complejo, retornamos var o int truncado por ahora
            return op.substring(0, op.indexOf("."));
        }
        // Manejo de punteros y nombres con puntos
        if (!Character.isDigit(op.charAt(0)) && !op.startsWith("'") && !op.startsWith("&")) {
            return "_" + op.replace(".", "_");
        }
        return op;
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