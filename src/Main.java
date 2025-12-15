import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Redirigimos la salida estándar a un archivo para el log del parser
            PrintStream fileOut = new PrintStream("salida_parser.txt");
            System.setOut(fileOut);
            System.setErr(fileOut);

            if (args.length == 0) {
                System.out.println("Error: Falta ruta del archivo");
                System.exit(1);
            }

            // Generamos el archivo .lst (Listing)
            generarArchivoListing(args[0]);

            // Inicialización de componentes
            AnalizadorLexico al = new AnalizadorLexico(args[0]);
            Generador g = Generador.getInstance();
            g.setAnalizadorLexico(al);

            Parser.al = al;
            Parser.g = g;

            // Ejecución del Parser
            Parser par = new Parser(false);
            par.yyparse();

            // --- REPORTE DE ERRORES (Se guardan en salida_parser.txt) ---

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

            // --- LÓGICA DE GENERACIÓN ---

            // Verificamos si la compilación fue exitosa (cero errores)
            if (par.erroresSintacticos.isEmpty() && al.getErrores().isEmpty() && al.getErroresSemanticos().isEmpty()) {
                System.out.println("\n=======================================================");
                System.out.println("## GENERACION DE ASSEMBLER ##");
                System.out.println("=======================================================");

                GeneradorAssembler ga = new GeneradorAssembler(g, al);
                ga.generarAssembler("salida.asm");
                System.out.println("Archivo salida.asm generado exitosamente.");

                g.imprimirTercetos();
                al.imprimirTablaSimbolos();

                System.out.println("=======================================================");
                System.exit(0);

            } else {
                // CASO CON ERRORES
                System.out.println("\n=======================================================");
                System.out.println("## RESULTADO DE LA COMPILACION: FALLIDO ##");
                System.out.println("=======================================================");
                System.out.println("La compilacion fallo. Se generara un assembler placeholder para evitar errores de linkeo.");

                // Generamos un "salida.asm" que solo imprime el error al ejecutarse
                generarArchivoStub("salida.asm");

                al.imprimirTablaSimbolos();
                System.out.println("=======================================================");

                // Retornamos 1 para indicar error, aunque el archivo .asm existe
                System.exit(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    // Método para generar un ASM que avisa del error en tiempo de ejecución
    private static void generarArchivoStub(String nombreArchivo) {
        try (FileWriter writer = new FileWriter(nombreArchivo)) {
            writer.write(".386\n");
            writer.write(".model flat, stdcall\n");
            writer.write("option casemap :none\n");
            writer.write("include \\masm32\\include\\windows.inc\n");
            writer.write("include \\masm32\\include\\kernel32.inc\n");
            writer.write("include \\masm32\\include\\msvcrt.inc\n");
            writer.write("includelib \\masm32\\lib\\kernel32.lib\n");
            writer.write("includelib \\masm32\\lib\\msvcrt.lib\n");
            writer.write(".data\n");
            // Mensaje que verá el usuario si intenta ejecutar el programa fallido
            writer.write("MsgError db \"ERROR FATAL: El programa no se compilo correctamente debido a errores en el codigo fuente.\", 13, 10, 0\n");
            writer.write(".code\n");
            writer.write("start:\n");
            writer.write("    invoke crt_printf, addr MsgError\n");
            writer.write("    invoke ExitProcess, 1\n");
            writer.write("end start\n");

            System.out.println(">> Se genero 'salida.asm' (STUB) para notificar el error.");
        } catch (IOException e) {
            System.out.println("Error al generar stub: " + e.getMessage());
        }
    }

    private static void generarArchivoListing(String rutaEntrada) {
        File archivoEntrada = new File(rutaEntrada);
        String directorio = archivoEntrada.getParent();
        String nombreArchivo = archivoEntrada.getName();
        int puntoIndex = nombreArchivo.lastIndexOf('.');
        String nombreBase = (puntoIndex != -1) ? nombreArchivo.substring(0, puntoIndex) : nombreArchivo;
        String nombreSalida = nombreBase + ".lst";
        File archivoSalida = (directorio != null) ? new File(directorio, nombreSalida) : new File(nombreSalida);

        try (BufferedReader br = new BufferedReader(new FileReader(archivoEntrada));
             PrintWriter pw = new PrintWriter(new FileWriter(archivoSalida))) {

            String linea;
            int nroLinea = 1;
            pw.println("LISTADO DE COMPILACION: " + nombreArchivo);
            pw.println("----------------------------------------");

            while ((linea = br.readLine()) != null) {
                pw.printf("%4d: %s%n", nroLinea, linea);
                nroLinea++;
            }
            System.out.println(">> Archivo de listado generado: " + archivoSalida.getPath());

        } catch (IOException e) {
            System.out.println("Error al generar archivo listing: " + e.getMessage());
        }
    }
}