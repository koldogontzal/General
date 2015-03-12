package utils;

import java.io.File;

public class Prueba {

	private String identificativo; // Letra "A", "B" o "C" que identifica a la prueba
	private File archivo;
	private boolean tieneCopia;
	
	public Prueba(File archivo) {
		this.archivo = archivo;
		
		this.identificativo = "" + this.archivo.getName().charAt(0);
		this.identificativo = this.identificativo.toUpperCase();
		
		File copia = new File (archivo.getParent() + File.separator + "Copia_" + archivo.getName());
		this.tieneCopia = copia.exists();
	}
	
	public File getArchivo() {
		return this.archivo;
	}
	
	public String getIdentificativo() {
		return this.identificativo;
	}
	
	public String getUsu() {
		String[] ret = this.archivo.getParent().split(File.separator + File.separator);		
		return ret[ret.length - 1];
	}
	
	public String getGrupo() {
		String[] ret = this.archivo.getParent().split(File.separator + File.separator);		
		return ret[ret.length - 2];
	}
	
	public String getCodigoOpositor() {
		return this.archivo.getName().substring(1, 6);
	}
	
	public boolean isTieneCopia() {
		return this.tieneCopia;
	}
	
	@Override
	public String toString() {
		return this.getGrupo() + "; " + this.getUsu() + "; " + this.getArchivo().getName() + "; ¿Tiene copia? " + this.isTieneCopia();
	}
}
