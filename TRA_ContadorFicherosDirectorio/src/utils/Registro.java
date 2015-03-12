package utils;

import java.io.File;

import except.NotRegistroException;

public class Registro extends File {

	private static final long serialVersionUID = 6793238583312641967L;
	
	public enum TipoRegistro {ENTRADA, SALIDA, DESCONOCIDO};
	private static final String TEXTO_SALIDA_NOMBRE_FICHERO = "SALIDA";
	private static final String TEXTO_ENTRADA_NOMBRE_FICHERO = "ENTRADA";		
	
	private TipoRegistro tipoRegistro;
	
	
	
	

	public Registro(String pathname) throws NotRegistroException {
		this(new File(pathname));		
	}
	
	public Registro(File filename) throws NotRegistroException {
		super(filename.getPath());
		
		if (filename.isFile()) {
			if (filename.getName().indexOf(TEXTO_SALIDA_NOMBRE_FICHERO) != -1) {
				// Se trata de un fichero que es un registro de salida
				this.setTipoRegistro(TipoRegistro.SALIDA);
				
			} else if (filename.getName().indexOf(TEXTO_ENTRADA_NOMBRE_FICHERO) != -1) {
				// ES UN REGISTRO DE ENTRADA
				this.setTipoRegistro(TipoRegistro.ENTRADA);
			} else {
				// no es un registro ni de entrada ni de salida
				this.setTipoRegistro(TipoRegistro.DESCONOCIDO);
				throw new NotRegistroException("ERROR: El archivo " + filename.getPath() + " no es un Registro con un formato valido.");				
			}
		} else {
			// no es un registro ni de entrada ni de salida
			this.setTipoRegistro(TipoRegistro.DESCONOCIDO);
			throw new NotRegistroException("ERROR: El archivo " + filename.getPath() + " no es un Registro con un formato valido.");
		}
		
	}

	private void setTipoRegistro(TipoRegistro tipoRegistro) {
		this.tipoRegistro = tipoRegistro;
	}

	public TipoRegistro getTipoRegistro() {
		return this.tipoRegistro;
	}

}
