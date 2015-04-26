package mezclador;

import java.io.File;
import java.io.IOException;

import utils.Archivo;
import utils.FileAlreadyExistsException;

public class MezcladorDeDirectorios {
	
	private File directorioOrigen, directorioDestino;
	
	public MezcladorDeDirectorios(File directorioOrigen, File directorioDestino) throws IOException {
		this.directorioOrigen = directorioOrigen;
		this.directorioDestino = directorioDestino;
		
		if (directorioOrigen.isDirectory() && this.directorioDestino.isDirectory()) {
			moverArchivosDeOrigenADestino();
		}
	}
	
	private void moverArchivosDeOrigenADestino() throws IOException {
		File[] listadoOrigen = this.directorioOrigen.listFiles();
		
		for (File file : listadoOrigen) {
			Archivo archivo = new Archivo(file);
			
			boolean ret = false;
			try {
				ret = archivo.cambiarDeDirectorio(this.directorioDestino);

			} catch (FileAlreadyExistsException e) {
				//e.printStackTrace();
				System.out.println(e);
				// Renombra el archivo en origen para identificar que puede borrarse
				File fileOriginal = archivo.getFile();
				File fileRenombrado = new File(fileOriginal.getParent() + File.separator + "YAEXISTEENDESTINO_" + 
						fileOriginal.getName());
				fileOriginal.renameTo(fileRenombrado);
				
			}
			
			if (ret) {
				System.out.println("EXITO: " + archivo + " movido al directorio " + this.directorioDestino);
			} else {
				System.out.println("AVISO: " + archivo + " no se movió al directorio " + this.directorioDestino);
			}
		}
	}

}
