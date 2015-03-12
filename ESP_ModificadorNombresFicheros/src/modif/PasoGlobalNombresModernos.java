package modif;

import java.io.File;

import apliGraf.Aplicacion;

public class PasoGlobalNombresModernos {
	
	private File directorioBase;
	private Aplicacion app;
	
	public PasoGlobalNombresModernos(String path, Aplicacion app) {
		this.directorioBase = new File(path);	
		this.app = app;
		
		this.app.escribirLinea("Directorio base: " + path +"\n");
		
		if (!this.directorioBase.isDirectory()) {
			this.app.escribirLinea(this.directorioBase.getPath() + ": no se trata de un directorio que exista.");
		}
		
		this.empezar();
	}
	
	public void empezar() {
		this.restaurarDirectorio(this.directorioBase);		
	}
		
	private void restaurarDirectorio (File direccionInicial) {
		// Obtiene la lista de ficheros en el directorio
		File[] listado = direccionInicial.listFiles();
		// Recorrer la lista y modificarla
		for (File actual : listado) {
			if (actual.isDirectory()) {
				// Es un directorio
				this.restaurarDirectorio(actual);
			} else {
				if (ParserFileFoto.esNombreFileCorrecto(actual.getName())) {
					ParserFileFoto nombreNuevo = new ParserFileFoto(actual.getName());
										
					File fileNuevo = new File (actual.getParent() + File.separator + nombreNuevo.nombreNuevoFichero());

					if (! nombreNuevo.esCoherenteTipoExtension()) {
						this.app.escribirLinea("INCOHERENTE: " + fileNuevo.getPath());
					}
					
					actual.renameTo(fileNuevo);
					this.app.escribirLinea(fileNuevo.getPath() + ": modificado el nombre");
				} else {
					this.app.escribirLinea("INPARSEABLE: " + actual.getPath());
				}
			}
		}	
	}
}
