package analizadorHTML;

import java.util.ArrayList;
import java.util.Iterator;

public class ListadoAtributos {
	
	private ArrayList<Atributo> listado;
	
	public ListadoAtributos(String texto) {
		this.listado = new ArrayList<Atributo>(5);
		
		// Busca los atributos en la cadena 'texto' y los mete en el ArrayList
		texto = this.eliminaEspaciosEnLosExtremos(texto);
		texto = this.eliminaEspaciosAlrededorIgual(texto);
		boolean [] esTextoEntrecomillado = this.obtenerArrayPosicionesTextoEntrecomillado(texto);
		int posInicio = 0;
		int posEspBlanco = texto.indexOf(' ');
		while (posEspBlanco != -1) {
			while ((posEspBlanco != -1) && esTextoEntrecomillado[posEspBlanco]) {
				posEspBlanco = texto.indexOf(' ', posEspBlanco + 1);
			}
			 
			if (posEspBlanco != -1) {
				Atributo att = new Atributo(texto.substring(posInicio, posEspBlanco));
				posInicio = posEspBlanco;
				posEspBlanco = texto.indexOf(' ', posEspBlanco + 1);
				this.listado.add(att);
			} 			
		}
		Atributo att = new Atributo(texto.substring(posInicio, texto.length()));
		this.listado.add(att);
	}
	
	private String eliminaEspaciosAlrededorIgual(String texto) {
		
		if (texto != null) {
			boolean [] esTextoEntrecomillado = this.obtenerArrayPosicionesTextoEntrecomillado(texto);
			boolean [] espaciosABorrar = new boolean[texto.length()];
			for (int i = 0; i < texto.length(); i++) {
				espaciosABorrar[i] = false;
			}
			int posInicio = 0;
			
			while (posInicio < texto.length()) {
				int posIgual = texto.indexOf('=', posInicio);
				if (posIgual != -1) {
					// Hay un igual
					
					// Busca espacios a la derecha
					int posBorrar = posIgual - 1;
					while ((posBorrar >= 0) && (texto.charAt(posBorrar) == ' ')) {
						espaciosABorrar[posBorrar] = true;
						posBorrar--;
					}
					// Busca espacios a la izquierda
					posBorrar = posIgual + 1;
					while ((posBorrar < texto.length()) && (texto.charAt(posBorrar) == ' ')) {
						espaciosABorrar[posBorrar] = true;
						posBorrar++;
					}
					posInicio = posIgual + 1;
				} else {
					// No hay un igual
					posInicio = texto.length();
				}
			}
			
			// Crea el nuevo String eliminando los espacios que no valen
			StringBuffer bf = new StringBuffer();
			for (int i = 0; i < texto.length(); i ++) {
				if (esTextoEntrecomillado[i] | !espaciosABorrar[i]) {
					bf.append(texto.charAt(i));
				}
			}
			return bf.toString();
		}
		return null;		
	}

/*
private String conversion(boolean [] array) {
	String ret = "";
	for (int i = 0; i < array.length; i ++) {
		if (array[i]) {
			ret = ret + "1";
		} else {
			ret = ret + "0";
		}
	}
	return ret;
}
*/
	
	private boolean [] obtenerArrayPosicionesTextoEntrecomillado(String texto) {
		if (texto != null) {
			boolean [] ret = new boolean[texto.length()];
			boolean estadoComillaDoble = false;
			boolean estadoComillaSimple = false;
			for (int i = 0; i < texto.length(); i++) {
				char caracter = texto.charAt(i);
				if (caracter == '"') {
					estadoComillaDoble = !estadoComillaDoble;
				}
				if (caracter == '\'') {
					estadoComillaSimple = !estadoComillaSimple;
				}
				ret[i] = (estadoComillaDoble | estadoComillaSimple);
			}
		return ret;
		} else {
			return null;
		}
	}
	
	private String eliminaEspaciosEnLosExtremos(String texto) {
		if (texto != null) {
			int posIni = 0;
			int posFin = texto.length() - 1;
			if (posFin >= 0) {
				while ((texto.charAt(posIni) == ' ') && (posIni < posFin)) {
					posIni++;
				}
				while ((texto.charAt(posFin) == ' ') && (posFin > posIni)) {
					posFin--;
				}				
				return texto.substring(posIni, posFin + 1);
			} else {
				return texto;
			}			
		} else {
			return null;
		}
	}
	
	@Override
	public String toString() {
		String ret = "";		
		Iterator<Atributo> i = this.listado.iterator();
		while (i.hasNext()) {
			Atributo att = i.next();
			ret = ret + att.toString() + " ";
		}		
		return ret;
	}

	public boolean hayAtributo(String nombreAtributo) {
		Iterator<Atributo> i = this.listado.iterator();
		boolean encontrado = false;
		while (!encontrado && i.hasNext()) {
			Atributo att = i.next();
			encontrado = att.esAtributo(nombreAtributo);
		}
		return encontrado;
	}
}
