package modif;

import apliGraf.Aplicacion;

public class ExtraerFicherosSubdirectorios {
	
	private Directorio directorioOriginal;
	private Aplicacion app;
	
	public ExtraerFicherosSubdirectorios (String dir, Aplicacion app) {
		this.directorioOriginal = new Directorio (dir);
		this.app = app;		
		
		if (!this.directorioOriginal.esDirectorio()) {
			this.app.escribirLinea(this.directorioOriginal.getPath() + " no es un directorio.");
		} else {
			this.app.escribirLinea("Directorio base: " + this.directorioOriginal.getPath());
			this.extRecursivo (this.directorioOriginal);
		}
	}

	private void extRecursivo(Directorio dir) {
		Archivo [] listado = dir.listArchivos();
		
		for (Archivo archivo : listado) {
			if (archivo.esDirectorio()) {
				// Si es un directorio, lo revisa recursivamente
				this.extRecursivo(new Directorio(archivo));
			} else {				
				String nombre = archivo.getName();
				Archivo nuevo = Archivo.CrearArchivoSinExtensionNombreValido(this.directorioOriginal, nombre, false);
				boolean resultado = archivo.mover(nuevo);
				if (resultado) {
					this.app.escribirLinea("OK - EXTRAIDO: " + nuevo.getPath());
				} else {
					this.app.escribirLinea("ERROR: " + nuevo.getPath());
				}				
			}
		}
	}
}
