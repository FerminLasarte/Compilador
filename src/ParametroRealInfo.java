public class ParametroRealInfo {
    public String operando; // El terceto o variable (ej: "[10]", "VAR_A")
    public String nombreFormal; // El nombre al que apunta (ej: "X")

    public ParametroRealInfo(String operando, String nombreFormal) {
        this.operando = operando;
        this.nombreFormal = nombreFormal;
    }
}