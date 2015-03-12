package utils;

import java.io.File;

public class PruebaEnLocal {

	private File archivo;
	private boolean encontrado = false;
	private Prueba pruebaCoincidencia = null;
	
	public boolean isEncontrado() {
		return this.encontrado;
	}

	public void setEncontrado(boolean encontrado) {
		this.encontrado = encontrado;
	}
	
	public void setPruebaCoincidencia(Prueba prueba) {
		this.pruebaCoincidencia = prueba;
	}

	public PruebaEnLocal(File archivo) {
		this.archivo = archivo;
	}
	
	public PruebaEnLocal(String archivo) {
		this.archivo = new File(archivo);
	}
	
	public boolean isPrueba() {
		if (this.archivo.getName().length() == 11) {
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
		return this.archivo.getParent() + ";" + this.archivo.getName()+ ";" + this.archivo.length() + "; Correspondencia: " + this.encontrado + 
			";" + (this.pruebaCoincidencia == null ? "" : this.pruebaCoincidencia) ;
	}
	
	
	public String getUsu() {
		String ret = null;
		String[] partes = this.archivo.getParent().split(File.separator + File.separator);
		int numPartes = partes.length;
		if (numPartes > 1) {
			ret = partes[numPartes - 1].toUpperCase();
			if (ret.toUpperCase().equals("TEMP")) {
				ret = partes[numPartes - 2].toUpperCase();
			}
		}
		return ret;
	}
	
	public String getAula() {
		String ret = null;
		String[] partes = this.archivo.getParent().split(File.separator + File.separator);
		int numPartes = partes.length;
		if (numPartes > 1) {
			ret = partes[numPartes - 2];
		}
		return ret;
	}
}
