package utils;

import java.io.File;

public class FileAlreadyExistsException extends Exception {

	/**
	 *  Exception que salta al intentar mover al directorio de destino un Archivo que ya existe en él 
	 */
	private static final long serialVersionUID = -4354461340483996579L;
	
	public FileAlreadyExistsException(String nombreArchivo, File directorioDestino) {
		super("AVISO: El archivo " + nombreArchivo + " ya existe en el directorio de destino " + directorioDestino);
	}

}
