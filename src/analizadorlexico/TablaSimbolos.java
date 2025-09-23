package analizadorlexico;

import java.util.ArrayList;
import java.util.HashMap;

public class TablaSimbolos {
    private HashMap<String, ArrayList<Integer>> map = new HashMap<>();
    // String ambitos = ".global";

    public TablaSimbolos() {
        this.map = new HashMap<>();
        this.inicializarTablaSimbolos();
    }

    private void inicializarTablaSimbolos() {
        ArrayList<Integer> lista;

        lista = new ArrayList<>();
        lista.add();
        lista.add(0);
        map.put("identificador", lista);

        lista = new ArrayList<>();
        lista.add();
        lista.add(0);
        map.put("cte_entero_sin_signo", lista);

        lista = new ArrayList<>();
        lista.add();
        lista.add(0);
        map.put("cte_float", lista);

        lista = new ArrayList<>();
        lista.add();
        lista.add(0);
        map.put("cadena_multilinea", lista);

        lista = new ArrayList<>();
        lista.add();
        lista.add(0);
        map.put("if", lista);

        lista = new ArrayList<>();
        lista.add();
        lista.add(0);
        map.put("else", lista);

        lista = new ArrayList<>();
        lista.add();
        lista.add(0);
        map.put("endif", lista);

        lista = new ArrayList<>();
        lista.add();
        lista.add(0);
        map.put("print", lista);

        lista = new ArrayList<>();
        lista.add();
        lista.add(0);
        map.put("return", lista);

        lista = new ArrayList<>();
        lista.add();
        lista.add(0);
        map.put("var", lista);

        lista = new ArrayList<>();
        lista.add();
        lista.add(0);
        map.put("do", lista);

        lista = new ArrayList<>();
        lista.add();
        lista.add(0);
        map.put("while", lista);

        lista = new ArrayList<>();
        lista.add();
        lista.add(0);
        map.put("lambda", lista);
    }


    public void agregarToken(String lexema, ArrayList<Integer> atributos){
        map.put(lexema, atributos);
    }

	public Integer getToken(String lexema){
		return map.get(lexema).get(0);
	}

	public boolean estaToken(String lexema){
        return map.containsKey(lexema);
    }

    public HashMap<String, ArrayList<Integer>> getMap() {
        return map;
    }

    /*
	public ArrayList<Integer> getValor(String lexema) {
		return map.get(lexema);
	}

	public String getAmbitos() {
		return ambitos;
	}

	public String buscarVariable(String lexema){

		if(lexema.matches("\\[T\\d+\\]")){
			return "Terceto";
		}

		if (this.estaToken(lexema) && this.getTipo(lexema)<=3 && (this.getToken(lexema)!=258)) {
			return lexema;
		}

		String ambito = this.ambitos;
		String variable = lexema+ambito;

		while(!variable.equals(lexema)) {
			if(this.estaToken(variable)){
				if(this.estaToken(lexema)) {
					this.map.remove(lexema);
				}
				return variable;
			}else{
				int lastIndex = variable.lastIndexOf(".");
				if(lastIndex==-1){
					break;
				}
				variable=variable.substring(0, lastIndex);
			}
		}

		if(this.estaToken(lexema)) {
			this.map.remove(lexema);
		}

		return null;
	}

	public String getUltimoAmbito() {
		if (this.ambitos != null && !this.ambitos.isEmpty()) {
			String[] partes = ambitos.split("\\.");  // Divide la cadena por el separador '.'
			String funcion = partes[partes.length - 1];
			for (int i=1; i<partes.length-1; i++){
				funcion=funcion+"."+partes[i];
			}
			return funcion;  // Retorna el último elemento
		}
		return "";  // Retorna una cadena vacía si el ámbito es nulo o vacío
	}

	public void addAmbitos(String ambitos) {
		this.ambitos = this.ambitos + "."  + ambitos;
	}

	public void eliminarAmbito() {

		if (!this.ambitos.equals(".global")) {
			int posicionUltimoPunto = this.ambitos.lastIndexOf(".");

			this.ambitos = (posicionUltimoPunto != -1) ? this.ambitos.substring(0, posicionUltimoPunto) : this.ambitos;

		}
	}

	public void editarLexema(String oldKey, String newKey) {
		if(!oldKey.equals(newKey)){
			map.put(newKey, this.getValor(oldKey));
			map.remove(oldKey);
		}
	}

	public void editarTipo(String lexema, Integer tipo){
		map.get(lexema).set(1, tipo);
	}

	public void agregarUso(String lexema, Integer uso){
		map.get(lexema).add(2, uso);
	}
	public Integer getUso(String lexema){
		if (map.containsKey(lexema)&& map.get(lexema).size()>=3)
			return map.get(lexema).get(2);
		else return null;
	}

	public void agregarTipoParam(String lexema, Integer tipo){
		map.get(lexema).add(3, tipo);
	}

	public Integer getTipoParam(String lexema){
		if (map.containsKey(lexema))
			return map.get(lexema).get(3);
		else return null;
	}

	public Integer getTipo(String lexema){
		if (map.containsKey(lexema))
			return map.get(lexema).get(1);
		else return null;
	}

	public void imprimirTabla(){
		System.out.println("----------------------");
		System.out.println("Tabla de Simbolos");
		System.out.println("");
		for (String i: map.keySet() ){
			System.out.println("Lexema: " + i);
			System.out.println("Token: " + map.get(i).get(0));
			System.out.println("Tipo: " + map.get(i).get(1));
			if (map.get(i).size()>2) {
				System.out.println("Uso: " + map.get(i).get(2));
			}
			System.out.println("----------------------");
		}
	}
    */
}
