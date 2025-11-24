import java.io.PrintStream;

public class Main {
    public static void main(String[] args) {
        try {

            // ⬅ PRIMERO: REDIRECCIÓN de salida
            PrintStream fileOut = new PrintStream("salida_parser.txt");
            System.setOut(fileOut);
            System.setErr(fileOut);

            // ⬅ AHORA recién procesamos argumentos
            if (args.length == 0) {
                System.out.println("Error: Falta ruta del archivo");
                return;
            }
            AnalizadorLexico al = new AnalizadorLexico(args[0]);
            Generador g = Generador.getInstance();
            g.setAnalizadorLexico(al);

            Parser.al = al;
            Parser.g = g;

            Parser par = new Parser(false);
            par.yyparse();

            System.out.println("\n=======================================================");
            System.out.println("## GENERACION DE ASSEMBLER ##");
            System.out.println("=======================================================");

            GeneradorAssembler ga = new GeneradorAssembler(g, al);
            ga.generarAssembler("salida.asm");
            System.out.println("Archivo salida.asm generado exitosamente (Advertencia: Puede contener errores logicos si hubo errores de compilacion).");

            // 1. Errores Sintácticos
            System.out.println("\n=======================================================");
            System.out.println("## ERRORES SINTACTICOS DETECTADOS ##");
            System.out.println("=======================================================");
            if (par.erroresSintacticos.isEmpty()) {
                System.out.println("No se encontraron errores sintacticos.");
            } else {
                for (String s : par.erroresSintacticos) {
                    System.out.println(s);
                }
            }

            // 2. Errores Léxicos
            System.out.println("\n=======================================================");
            System.out.println("## ERRORES LEXICOS DETECTADOS ##");
            System.out.println("=======================================================");
            if (al.getErrores().isEmpty()) {
                System.out.println("No se encontraron errores lexicos.");
            } else {
                for (String s : al.getErrores()) {
                    System.out.println(s);
                }
            }

            // 3. Errores Semánticos
            System.out.println("\n=======================================================");
            System.out.println("## ERRORES SEMANTICOS DETECTADOS ##");
            System.out.println("=======================================================");
            if (al.getErroresSemanticos().isEmpty()) {
                System.out.println("No se encontraron errores semanticos.");
            } else {
                for (String s : al.getErroresSemanticos()) {
                    System.out.println(s);
                }
            }

            // 4. Warnings
            System.out.println("\n=======================================================");
            System.out.println("## WARNINGS DETECTADOS ##");
            System.out.println("=======================================================");
            if (al.getWarnings() == null || al.getWarnings().isEmpty()) {
                System.out.println("No se encontraron warnings.");
            } else {
                for (String s : al.getWarnings()) {
                    System.out.println(s);
                }
            }
            g.imprimirTercetos();
            al.imprimirTablaSimbolos();

            System.out.println("=======================================================");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}