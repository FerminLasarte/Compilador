import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GeneradorAssembler {
    private Generador generador;
    private AnalizadorLexico analizadorLexico;
    private StringBuilder encabezado;
    private StringBuilder codigo;
    private StringBuilder data;

    public GeneradorAssembler(Generador generador, AnalizadorLexico al) {
        this.generador = generador;
        this.analizadorLexico = al;
        this.encabezado = new StringBuilder();
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
        encabezado.append(".386\n");
        encabezado.append(".model flat, stdcall\n");
        encabezado.append("option casemap :none\n");
        encabezado.append("include \\masm32\\include\\windows.inc\n");
        encabezado.append("include \\masm32\\include\\kernel32.inc\n");
        encabezado.append("include \\masm32\\include\\user32.inc\n");
        encabezado.append("includelib \\masm32\\lib\\kernel32.lib\n");
        encabezado.append("includelib \\masm32\\lib\\user32.lib\n");
    }

    private void generarData() {
        data.append(".data\n");
        data.append("ErrorDivCero db \"Error: Division por cero\", 0\n");
        data.append("ErrorOverflow db \"Error: Overflow en operacion\", 0\n");
        data.append("ErrorRestaNegativa db \"Error: Resultado negativo en resta de enteros sin signo\", 0\n");
        data.append("MensajePrint db \"Salida: %s\", 10, 0\n");
        data.append("MensajePrintNum db \"Salida: %d\", 10, 0\n");
        data.append("MensajePrintFloat db \"Salida: %f\", 10, 0\n");

        for (int i = 0; i < generador.getProximoTerceto(); i++) {
            data.append("@aux").append(i).append(" dd 0\n");
        }

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
                Character.isDigit(op.charAt(0)) || op.startsWith("\'") || op.startsWith("&") || op.startsWith("-")) {
            return;
        }
        if (op.endsWith("UI")) return;

        if (!Character.isLetter(op.charAt(0)) && op.contains("F") && (op.contains(".") || op.contains("+") || op.contains("-"))) return;

        String varName = resolveOperand(op);
        if (varName.equalsIgnoreCase("EAX") || varName.equalsIgnoreCase("EBX") || isNumeric(varName)) return;

        if (!declaradas.contains(varName)) {
            data.append(varName).append(" dd 0\n");
            declaradas.add(varName);
        }
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e) {
            return false;
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

            codigo.append("Label").append(numTerceto).append(":\n");

            String op = tercetoActual.getOperador();
            String rawOp1 = tercetoActual.getOperando1();
            String rawOp2 = tercetoActual.getOperando2();
            String op1 = resolveOperand(rawOp1);
            String op2 = resolveOperand(rawOp2);
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
                    if (rawOp1.startsWith("&")) {
                        String strName = "str_" + numTerceto;
                        String content = rawOp1.replace("&", "");
                        StringBuilder dbStr = new StringBuilder();
                        dbStr.append(strName).append(" db ");
                        String[] lines = content.split("\n|\\r\\n");
                        ArrayList<String> parts = new ArrayList<>();

                        for (int i = 0; i < lines.length; i++) {
                            if (!lines[i].isEmpty()) {
                                parts.add("\"" + lines[i].replace("\"", "'") + "\"");
                            }
                            if (i < lines.length - 1) {
                                parts.add("13, 10");
                            }
                        }
                        if (parts.isEmpty()) {
                            dbStr.append("0");
                        } else {
                            for (int i = 0; i < parts.size(); i++) {
                                dbStr.append(parts.get(i));
                                if (i < parts.size() - 1) dbStr.append(", ");
                            }
                            dbStr.append(", 0");
                        }
                        dbStr.append("\n");
                        data.append(dbStr);
                        codigo.append("invoke MessageBox, NULL, addr ").append(strName).append(", addr MensajePrint, MB_OK\n");
                    } else {
                        if (isNumeric(op1)) {
                            codigo.append("MOV EAX, ").append(op1).append("\n");
                            codigo.append("MOV ").append(res).append(", EAX\n");
                            codigo.append("invoke MessageBox, NULL, addr ").append(res).append(", addr MensajePrintNum, MB_OK\n");
                        } else {
                            codigo.append("invoke MessageBox, NULL, addr ").append(op1).append(", addr MensajePrintNum, MB_OK\n");
                        }
                    }
                    break;
                case "BF":
                    codigo.append("MOV EAX, ").append(op1).append("\n");
                    codigo.append("CMP EAX, 0\n");
                    String targetBF = rawOp2.replace("[", "Label").replace("]", "");
                    codigo.append("JE ").append(targetBF).append("\n");
                    break;
                case "BI":
                    String targetBI = rawOp1.replace("[", "Label").replace("]", "");
                    if (targetBI.equals("_") || targetBI.equals("__")) {
                        codigo.append("; JMP UNRESOLVED (").append(rawOp1).append(")\n");
                    } else {
                        codigo.append("JMP ").append(targetBI).append("\n");
                    }
                    break;
                case "BT":
                    codigo.append("MOV EAX, ").append(op1).append("\n");
                    codigo.append("CMP EAX, 1\n");
                    String targetBT = rawOp2.replace("[", "Label").replace("]", "");
                    codigo.append("JE ").append(targetBT).append("\n");
                    break;
                case ">":
                    codigo.append("MOV EAX, ").append(op1).append("\n");
                    codigo.append("CMP EAX, ").append(op2).append("\n");
                    codigo.append("SETA AL\n");
                    codigo.append("MOVZX EAX, AL\n");
                    codigo.append("MOV ").append(res).append(", EAX\n");
                    break;
                case "<":
                    codigo.append("MOV EAX, ").append(op1).append("\n");
                    codigo.append("CMP EAX, ").append(op2).append("\n");
                    codigo.append("SETB AL\n");
                    codigo.append("MOVZX EAX, AL\n");
                    codigo.append("MOV ").append(res).append(", EAX\n");
                    break;
                case ">=":
                    codigo.append("MOV EAX, ").append(op1).append("\n");
                    codigo.append("CMP EAX, ").append(op2).append("\n");
                    codigo.append("SETAE AL\n");
                    codigo.append("MOVZX EAX, AL\n");
                    codigo.append("MOV ").append(res).append(", EAX\n");
                    break;
                case "<=":
                    codigo.append("MOV EAX, ").append(op1).append("\n");
                    codigo.append("CMP EAX, ").append(op2).append("\n");
                    codigo.append("SETBE AL\n");
                    codigo.append("MOVZX EAX, AL\n");
                    codigo.append("MOV ").append(res).append(", EAX\n");
                    break;
                case "==":
                    codigo.append("MOV EAX, ").append(op1).append("\n");
                    codigo.append("CMP EAX, ").append(op2).append("\n");
                    codigo.append("SETE AL\n");
                    codigo.append("MOVZX EAX, AL\n");
                    codigo.append("MOV ").append(res).append(", EAX\n");
                    break;
                case "=!":
                    codigo.append("MOV EAX, ").append(op1).append("\n");
                    codigo.append("CMP EAX, ").append(op2).append("\n");
                    codigo.append("SETNE AL\n");
                    codigo.append("MOVZX EAX, AL\n");
                    codigo.append("MOV ").append(res).append(", EAX\n");
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
                    codigo.append("MOV ").append(res).append(", EAX\n");
                    break;
                case "CALL_LAMBDA":
                    codigo.append("MOV EAX, ").append(op1).append("\n");
                    codigo.append("CALL EAX\n");
                    break;
                case "PARAM":
                    if (op1.startsWith("_L")) {
                        codigo.append("PUSH OFFSET ").append(op1).append("\n");
                    } else {
                        codigo.append("PUSH ").append(op1).append("\n");
                    }
                    break;
                case "TOUI":
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
        if (op.startsWith("L")) {
            try {
                Integer.parseInt(op.substring(1));
                return "Label" + op.substring(1);
            } catch (NumberFormatException e) {
            }
        }
        if (Character.isLetter(op.charAt(0))) {
            return "_" + op.replace(".", "_");
        }

        if (op.contains("F") && (op.contains("+") || op.contains("-") || op.contains("."))) {
            String clean = op.replace("F", "E");
            try {
                double d = Double.parseDouble(clean);
                return String.valueOf((int)d);
            } catch(Exception e) {
                return "0";
            }
        }
        if (!Character.isDigit(op.charAt(0)) && !op.startsWith("'") && !op.startsWith("&") && !op.startsWith("-")) {
            return "_" + op.replace(".", "_");
        }
        return op;
    }

    private void guardarArchivo(String nombre) {
        try (FileWriter writer = new FileWriter(nombre)) {
            writer.write(encabezado.toString());
            writer.write(data.toString());
            writer.write(codigo.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}