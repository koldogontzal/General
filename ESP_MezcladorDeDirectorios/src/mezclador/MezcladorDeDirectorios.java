package mezclador;

import java.io.File;

import utils.Archivo;
import utils.FileAlreadyExistsException;

public class MezcladorDeDirectorios {
	
	private File directorioOrigen, directorioDestino;
	
	public MezcladorDeDirectorios(File directorioOrigen, File directorioDestino) {
		this.directorioOrigen = directorioOrigen;
		this.directorioDestino = directorioDestino;
		
		if (directorioOrigen.isDirectory() && this.directorioDestino.isDirectory()) {
			moverArchivosDeOrgienADestino();
		}
	}
	
	private void moverArchivosDeOrgienADestino() {
		File[] listadoOrigen = this.directorioOrigen.listFiles();
		
		for (File file : listadoOrigen) {
			Archivo archivo = new Archivo(file);
			
			boolean ret = false;
			try {
				ret = archivo.cambiarDeDirectorio(this.directorioDestino);

			} catch (FileAlreadyExistsException e) {
				e.printStackTrace();
				System.out.println(e);
			}
			
			if (ret) {
				System.out.println("EXITO: " + archivo + " movido al directorio " + this.directorioDestino);
			} else {
				System.out.println("ERROR: " + archivo + " no se movió al directorio " + this.directorioDestino);
			}
		}
	}

}
