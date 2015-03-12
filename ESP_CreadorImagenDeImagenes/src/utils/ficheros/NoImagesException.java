package utils.ficheros;

public class NoImagesException extends Exception {
	/**
	 *  Excepción que se produce en el caso en el que en el directorio no haya ningun archivo válido de imagen
	 */
	private static final long serialVersionUID = 4992080885451807428L;

	public NoImagesException(String directorio) {
		super("No existen archivos de imagen válidos en el directorio " + directorio);
	}

}
