package subtitulos;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class LectorDeArchivos extends ArrayList<String> {
	
	public static final int OK = 0;
	public static final int FICHERO_NO_ENCONTRADO = -1;
	public static final int IMPOSIBLE_LEER_FICHERO = -2;
	public static final int ERROR_CERRANDO_FICHERO = -3;
	
	protected String nombreArchivo;
	
	public LectorDeArchivos(String nombre) {
		super(1000);
		
		this.nombreArchivo = nombre;
		
		int resultado = this.leerArchivo();
		switch (resultado) {
		case OK:
			break;
		case FICHERO_NO_ENCONTRADO:
			System.out.println(nombre + ": Fichero no encontrado");
			break;
		case IMPOSIBLE_LEER_FICHERO:
			System.out.println(nombre + ": Imposible leer el fichero");
			break;
		case ERROR_CERRANDO_FICHERO:
			System.out.println(nombre + ": Imposible cerrar el fichero");
			break;
		default:
			System.out.println(nombre + ": ERROR desconocido");
		}		
	}
	
	protected abstract int leerArchivo();
	
	@Override
	public String toString() {
		String ret = "";
		int numLinea = 1;
		Iterator i = super.iterator();
		while (i.hasNext()) {
			ret = ret + "Linea " + numLinea + ": " + i.next() + "\n";
			numLinea++;
		}
		return ret;
	}

}
