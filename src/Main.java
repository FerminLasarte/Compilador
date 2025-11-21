public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Error: Falta ruta del archivo");
            return;
        }

        // 1. Inicializar Analizador Lexico y Generador
        AnalizadorLexico al = new AnalizadorLexico(args[0]);
        Generador g = Generador.getInstance();
        g.setAnalizadorLexico(al);

        // 2. Conectar con las variables estaticas del Parser
        Parser.al = al;
        Parser.g = g;

        // 3. Crear e iniciar el Parser
        Parser par = new Parser(false);
        par.yyparse();

        System.out.println("\n=======================================================");
        System.out.println("## GENERACION DE ASSEMBLER ##");
        System.out.println("=======================================================");

        // 4. Generar Assembler (FORZADO para pruebas, ignorando errores)
        // Nota: En produccion, descomentar el chequeo de errores.
        // if (par.erroresSintacticos.isEmpty() && al.getErrores().isEmpty() && al.getErroresSemanticos().isEmpty()) {
        GeneradorAssembler ga = new GeneradorAssembler(g, al);
        ga.generarAssembler("salida.asm");
        System.out.println("Archivo salida.asm generado exitosamente (Advertencia: Puede contener errores logicos si hubo errores de compilacion).");
        /* } else {
            System.out.println("No se genero Assembler debido a errores previos.");

            if (!par.erroresSintacticos.isEmpty()) {
                System.out.println("Errores Sintacticos: " + par.erroresSintacticos);
            }
            if (!al.getErrores().isEmpty()) {
                System.out.println("Errores Lexicos: " + al.getErrores());
            }
            if (!al.getErroresSemanticos().isEmpty()) {
                System.out.println("Errores Semanticos: " + al.getErroresSemanticos());
            }
        } */

        // Mostrar errores de todos modos para depuracion
        if (!par.erroresSintacticos.isEmpty() || !al.getErrores().isEmpty() || !al.getErroresSemanticos().isEmpty()) {
            System.out.println("\n--- Reporte de Errores (Ignorados para generar ASM) ---");
            if (!par.erroresSintacticos.isEmpty()) System.out.println("Sintacticos: " + par.erroresSintacticos);
            if (!al.getErrores().isEmpty()) System.out.println("Lexicos: " + al.getErrores());
            if (!al.getErroresSemanticos().isEmpty()) System.out.println("Semanticos: " + al.getErroresSemanticos());
        }
    }
}