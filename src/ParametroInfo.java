import java.util.ArrayList;

public class ParametroInfo {
    public String nombre;
    public String tipo;
    public String pasaje; // ej: "default_cv", "cr_se", "cr_le"

    public ParametroInfo(String nombre, String tipo, String pasaje) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.pasaje = pasaje;
    }

    @Override
    public String toString() {
        return tipo + " " + nombre + " (" + pasaje + ")";
    }
}