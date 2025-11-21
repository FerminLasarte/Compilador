public class Main {
    public static void main(String[] args) {
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

        if (!par.erroresSintacticos.isEmpty() || !al.getErrores().isEmpty() || !al.getErroresSemanticos().isEmpty()) {
            System.out.println("\n=======================================================");
            System.out.println("              REPORTE DE ERRORES");
            System.out.println("=======================================================");

            if (!par.erroresSintacticos.isEmpty()) {
                System.out.println("\nErrores Sintacticos:");
                for (String error : par.erroresSintacticos) {
                    System.out.println(" - " + error);
                }
            }

            if (!al.getErrores().isEmpty()) {
                System.out.println("\nErrores Lexicos:");
                for (String error : al.getErrores()) {
                    System.out.println(" - " + error);
                }
            }

            if (!al.getErroresSemanticos().isEmpty()) {
                System.out.println("\nErrores Semanticos:");
                for (String error : al.getErroresSemanticos()) {
                    System.out.println(" - " + error);
                }
            }
            System.out.println("\n=======================================================");
        }
    }
}