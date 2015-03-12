package utils;

import java.io.File;

public class Prueba {

	private String identificativo; // Letra "A", "B" o "C" que identifica a la prueba
	private File archivo;
	private boolean tieneCopia;
	private boolean tieneVersionAntigua;
	
	public Prueba(File archivo) {
		this.archivo = archivo;
		
		this.identificativo = "" + this.archivo.getName().charAt(0);
		this.identificativo = this.identificativo.toUpperCase();
		
		// Comprueba que exista una copia
		// Antes la copia tenía formato "Copia_Axxxxx.doc", pero ahora es "Axxxxx_copia.docx", así que hay que cambiarlo
		String nombreArchivo;
		String extensionArchivo;
		int posicionExtension = archivo.getName().lastIndexOf(".");
		if (posicionExtension > 0) {
			// Mira si existe la copia
			nombreArchivo = archivo.getName().substring(0, posicionExtension);
			extensionArchivo = archivo.getName().substring(posicionExtension, archivo.getName().length());
			
			File copia = new File (archivo.getParent() + File.separator + nombreArchivo + "_copia" + extensionArchivo);
			this.tieneCopia = copia.exists();
			
			// Mira si existe la copia en versión Office 2003
			if (extensionArchivo.toUpperCase().equals(".DOCX")) {
				File versionAntigua = new File(archivo.getParent() + File.separator + nombreArchivo + "_copia.doc");
				this.tieneVersionAntigua = versionAntigua.exists();
			} else if (extensionArchivo.toUpperCase().equals(".XLSX")) {
				File versionAntigua = new File(archivo.getParent() + File.separator + nombreArchivo + "_copia.xls");
				this.tieneVersionAntigua = versionAntigua.exists();
			} else {
				this.tieneVersionAntigua = false;
			}
			
		} else {
			this.tieneCopia = false;
			this.tieneVersionAntigua = false;
		}
		
	}
	
	public File getArchivo() {
		return this.archivo;
	}
	
	public String getIdentificativo() {
		return this.identificativo;
	}
	
	public String getUsu() {
		String[] ret = this.archivo.getParent().split(File.separator + File.separator);		
		return ret[ret.length - 1].toUpperCase();
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
	
	public boolean isTieneCopiaVersionAntigua() {
		return this.tieneVersionAntigua;
	}
	
	@Override
	public String toString() {
		return this.getGrupo() + ";" + this.getUsu() + ";" + this.getArchivo().getName() + ";" + 
			this.archivo.length() + ";¿Tiene copia? " + this.isTieneCopia() + ";¿Tiene versión Office 2003? "+ this.isTieneCopiaVersionAntigua();
	}
}
