package utils;

import java.io.File;

public class PruebaEnLocal {

	private File archivo;
	private boolean encontrado = false;
	
	public boolean isEncontrado() {
		return this.encontrado;
	}

	public void setEncontrado(boolean encontrado) {
		this.encontrado = encontrado;
	}

	public PruebaEnLocal(File archivo) {
		this.archivo = archivo;
	}
	
	public PruebaEnLocal(String archivo) {
		this.archivo = new File(archivo);
	}
	
	public boolean isPrueba() {
		if (this.archivo.getName().length() == 10) {
			boolean ret = true;
			char[] caracteres = this.archivo.getName().toLowerCase().toCharArray();
			// Primer caracter: 'a', 'b' o 'c'
			ret = ret && (caracteres[0] >= 'a');
			ret = ret && (caracteres[0] <= 'c');
			// siguientes 5 caracteres: numeros
			for (int i = 1; i < 6; i++) {
				ret = ret && (caracteres[i] >= '0');
				ret = ret && (caracteres[i] <= '9');
			}
			// Siguiente: igual a '.'
			ret = ret && (caracteres[6] == '.');
			
			return ret;
		} else {
			return false;
		}
	}
	
	public String getCodigoOpositor() {
		if (this.isPrueba()) {
			return this.archivo.getName().substring(1, 6);
		} else {
			return null;
		}
	}
	
	public String getIdentificativo() {
		if (this.isPrueba()) {
			return this.archivo.getName().substring(0, 1).toUpperCase();
		} else {
			return null;
		}
	}
	
	
	public File getArchivo() {
		return this.archivo;
	}
	
	@Override
	public String toString() {
		return this.archivo.getPath() + ";" + this.encontrado;
	}
	
}
