
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
        encabezado.append("include \\masm32\\include\\msvcrt.inc\n");
        encabezado.append("includelib \\masm32\\lib\\kernel32.lib\n");
        encabezado.append("includelib \\masm32\\lib\\user32.lib\n");
        encabezado.append("includelib \\masm32\\lib\\msvcrt.lib\n");
    }

    private void generarData() {
        data.append(".data\n");
        data.append("MsgErrorDivCero db \"Error: Division por cero\", 10, 0\n");
        data.append("MsgErrorOverflow db \"Error: Overflow en operacion\", 10, 0\n");
        data.append("MsgErrorRestaNegativa db \"Error: Resultado negativo en resta de enteros sin signo\", 10, 0\n");
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
                String op = t.getOperador();
                if (op.equals("FUNC_LABEL") || op.equals("CALL")) continue;

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

        if (isFloatLiteral(op)) return;

        String varName = resolveOperand(op);
        if (varName.equalsIgnoreCase("EAX") || varName.equalsIgnoreCase("EBX") || isNumeric(varName)) return;

        if (!declaradas.contains(varName)) {
            data.append(varName).append(" dd 0\n");
            declaradas.add(varName);
        }
    }

    private boolean isFloatLiteral(String op) {
        return !Character.isLetter(op.charAt(0)) && op.contains("F") && (op.contains(".") || op.contains("+") || op.contains("-"));
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
            String tipoTerceto = tercetoActual.getTipo();

            boolean isFloatOp = tipoTerceto.equals("float");
            if (!isFloatOp && rawOp1 != null) {
                String t1 = generador.getTipo(rawOp1);
                if (t1.equals("float")) isFloatOp = true;
            }

            switch (op) {
                case "+":
                    if (isFloatOp) {
                        loadToFPU(op1);
                        loadToFPU(op2);
                        codigo.append("FADD\n");
                        codigo.append("FSTP ").append(res).append("\n");
                    } else {
                        codigo.append("MOV EAX, ").append(op1).append("\n");
                        codigo.append("ADD EAX, ").append(op2).append("\n");
                        codigo.append("JC ErrorOverflow\n");
                        codigo.append("MOV ").append(res).append(", EAX\n");
                    }
                    break;
                case "-":
                    if (isFloatOp) {
                        loadToFPU(op1);
                        loadToFPU(op2);
                        codigo.append("FSUB\n");
                        codigo.append("FSTP ").append(res).append("\n");
                    } else {
                        codigo.append("MOV EAX, ").append(op1).append("\n");
                        codigo.append("SUB EAX, ").append(op2).append("\n");
                        codigo.append("JC ErrorRestaNegativa\n");
                        codigo.append("MOV ").append(res).append(", EAX\n");
                    }
                    break;
                case "*":
                    if (isFloatOp) {
                        loadToFPU(op1);
                        loadToFPU(op2);
                        codigo.append("FMUL\n");
                        codigo.append("FSTSW AX\n");
                        codigo.append("TEST AX, 8\n");
                        codigo.append("JNZ ErrorOverflow\n");
                        codigo.append("FSTP ").append(res).append("\n");
                    } else {
                        codigo.append("MOV EAX, ").append(op1).append("\n");
                        codigo.append("MUL ").append(op2).append("\n");
                        codigo.append("CMP EDX, 0\n");
                        codigo.append("JNE ErrorOverflow\n");
                        codigo.append("MOV ").append(res).append(", EAX\n");
                    }
                    break;
                case "/":
                    if (isFloatOp) {
                        loadToFPU(op2);
                        codigo.append("FTST\n");
                        codigo.append("FSTSW AX\n");
                        codigo.append("SAHF\n");
                        codigo.append("JE Error_DivCero\n");
                        codigo.append("FSTP ST(0)\n");

                        loadToFPU(op1);
                        loadToFPU(op2);
                        codigo.append("FDIV\n");
                        codigo.append("FSTP ").append(res).append("\n");
                    } else {
                        codigo.append("MOV EAX, ").append(op1).append("\n");
                        codigo.append("XOR EDX, EDX\n");
                        codigo.append("CMP ").append(op2).append(", 0\n");
                        codigo.append("JE Error_DivCero\n");
                        codigo.append("DIV ").append(op2).append("\n");
                        codigo.append("MOV ").append(res).append(", EAX\n");
                    }
                    break;
                case ":=":
                    if (isFloatOp) {
                        loadToFPU(op2);
                        codigo.append("FSTP ").append(op1).append("\n");
                    } else {
                        codigo.append("MOV EAX, ").append(op2).append("\n");
                        codigo.append("MOV ").append(op1).append(", EAX\n");
                    }
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
                        codigo.append("invoke crt_printf, addr MensajePrint, addr ").append(strName).append("\n");
                    } else {
                        String tipo = generador.getTipo(rawOp1);
                        if (tipo.equals("float")) {
                            loadToFPU(op1);
                            codigo.append("sub esp, 8\n");
                            codigo.append("fstp qword ptr [esp]\n");
                            codigo.append("push offset MensajePrintFloat\n");
                            codigo.append("call crt_printf\n");
                            codigo.append("add esp, 12\n");
                        } else {
                            codigo.append("invoke crt_printf, addr MensajePrintNum, ").append(op1).append("\n");
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
                case "<":
                case ">=":
                case "<=":
                case "==":
                case "=!":
                    if (generador.getTipo(rawOp1).equals("float") || generador.getTipo(rawOp2).equals("float")) {
                        loadToFPU(op2);
                        loadToFPU(op1);
                        codigo.append("FCOMIP ST(0), ST(1)\n");
                        codigo.append("FSTP ST(0)\n");
                        String jump = "";
                        switch(op) {
                            case ">": jump = "SETA"; break;
                            case "<": jump = "SETB"; break;
                            case ">=": jump = "SETAE"; break;
                            case "<=": jump = "SETBE"; break;
                            case "==": jump = "SETE"; break;
                            case "=!": jump = "SETNE"; break;
                        }
                        codigo.append(jump).append(" AL\n");
                        codigo.append("MOVZX EAX, AL\n");
                        codigo.append("MOV ").append(res).append(", EAX\n");
                    } else {
                        codigo.append("MOV EAX, ").append(op1).append("\n");
                        codigo.append("CMP EAX, ").append(op2).append("\n");
                        String set = "";
                        switch(op) {
                            case ">": set = "SETA"; break;
                            case "<": set = "SETB"; break;
                            case ">=": set = "SETAE"; break;
                            case "<=": set = "SETBE"; break;
                            case "==": set = "SETE"; break;
                            case "=!": set = "SETNE"; break;
                        }
                        codigo.append(set).append(" AL\n");
                        codigo.append("MOVZX EAX, AL\n");
                        codigo.append("MOV ").append(res).append(", EAX\n");
                    }
                    break;
                case "FUNC_LABEL":
                    codigo.append("_").append(op1).append(":\n");
                    break;
                case "RETURN":
                case "RET_LAMBDA":
                    codigo.append("RET\n");
                    break;
                case "CALL":
                    codigo.append("CALL ").append("_").append(op1).append("\n");
                    codigo.append("MOV ").append(res).append(", EAX\n");
                    break;
                case "CALL_LAMBDA":
                    codigo.append("MOV EAX, ").append(op1).append("\n");
                    codigo.append("CALL EAX\n");
                    break;
                case "PARAM":
                    // Corregido: Asignación directa a la variable del parámetro en lugar de PUSH
                    // op1 es el valor a pasar, op2 es el nombre de la variable del parámetro formal
                    if (op1.startsWith("_L")) {
                        codigo.append("MOV EAX, OFFSET ").append(op1).append("\n");
                        codigo.append("MOV ").append(op2).append(", EAX\n");
                    } else if (isFloatOp) {
                        loadToFPU(op1);
                        codigo.append("FSTP ").append(op2).append("\n");
                    } else {
                        codigo.append("MOV EAX, ").append(op1).append("\n");
                        codigo.append("MOV ").append(op2).append(", EAX\n");
                    }
                    break;
                case "TOUI":
                    loadToFPU(op1);
                    codigo.append("FISTP ").append(res).append("\n");
                    break;
            }
            numTerceto++;
        }

        codigo.append("invoke ExitProcess, 0\n");
        generarErrores();
        codigo.append("end start\n");
    }

    private void loadToFPU(String op) {
        if (isNumeric(op)) {
            codigo.append("MOV EAX, ").append(op).append("\n");
            codigo.append("PUSH EAX\n");
            codigo.append("FLD DWORD PTR [ESP]\n");
            codigo.append("ADD ESP, 4\n");
        } else {
            codigo.append("FLD ").append(op).append("\n");
        }
    }

    private void generarErrores() {
        codigo.append("Error_DivCero:\n");
        codigo.append("invoke crt_printf, addr MsgErrorDivCero\n");
        codigo.append("invoke ExitProcess, 1\n");
        codigo.append("ErrorOverflow:\n");
        codigo.append("invoke crt_printf, addr MsgErrorOverflow\n");
        codigo.append("invoke ExitProcess, 1\n");
        codigo.append("ErrorRestaNegativa:\n");
        codigo.append("invoke crt_printf, addr MsgErrorRestaNegativa\n");
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

        if (isFloatLiteral(op)) {
            try {
                String clean = op.replace("F", "E");
                float f = Float.parseFloat(clean);
                int bits = Float.floatToIntBits(f);
                return String.valueOf(bits);
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
