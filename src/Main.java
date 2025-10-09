import analizadorlexico.AnalizadorLexico;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        AnalizadorLexico al = new AnalizadorLexico("/Users/ferminlasarte/Documents/GitHub/Compilador/pruebas/TP1/caso_3.txt");
        int tokenFinPrograma = 0;
        int token = -1;
        while(token != tokenFinPrograma){
            token = al.yylex();
        }

        System.out.println("\n");
        if(al.getWarnings() != null) {
            for(String s : al.getWarnings()){
                System.out.println(s);
            }
        }
        System.out.println("=========================================================");
        if(al.getErrores() != null) {
            for(String s : al.getErrores()){
                System.out.println(s);
            }
        }
    }
}