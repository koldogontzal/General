package com.koldogontzal.timestamp;

import java.io.File;

public class TimeStampFile extends File {

	private static final long serialVersionUID = -4736566271369957954L;

	private String StringPreMarca;
	private String StringMarca;
	private String StringPosMarca;
	private String StringExtension; // Incluye el punto '.'

	private TimeStamp marca;

	public TimeStampFile(String pathname) {
		super(pathname);
		this.StringExtension = this.getExtensionDelNombre();

		String nombre = this.getNombreSinExtension();
		String[] nombreDescompuesto = new String[3];
		try {
			// El File tiene una TimeStamp valido
			this.marca = TimeStamp.lookForTimeStamp(nombre, nombreDescompuesto);
			this.StringPreMarca = nombreDescompuesto[0];
			this.StringMarca = nombreDescompuesto[1];
			this.StringPosMarca = nombreDescompuesto[2];
		} catch (IllegalTimeStampException e) {
			// El File no tiene un TimeStamp
			this.marca = null;
			this.StringPreMarca = "";
			this.StringMarca = "";
			this.StringPosMarca = nombre;
		}
	}

	public String getStringPreTimeStamp() {
		return this.StringPreMarca;
	}

	public TimeStamp getTimeStamp() throws IllegalTimeStampException {
		if (this.marca != null) {
			return this.marca;
		} else {
			throw new IllegalTimeStampException(super.getPath()
					+ " no es un archivo con un TimeStamp reconocido.");
		}
	}

	public String getStringPosTimeStamp() {
		return this.StringPosMarca;
	}

	public String getStringExtension() {
		return this.StringExtension;
	}

	public boolean setStringPreTimeStamp(String s) {
		// Modifica la parte del nombre antes de la MarcaDeTiempo y renombra el
		// fichero File
		this.StringPreMarca = s;
		return this.actualizarFile();
	}

	public boolean setTimeStamp(TimeStamp nuevaMarca) {
		// Sustituye la MarcaDeTiempo por una nueva y renombra el fichero File
		this.StringMarca = nuevaMarca.toString();
		this.marca = nuevaMarca;
		// Añade un '_' al principio de StringPosMarca y al final de StringPreMarca si no existen
		if (!this.StringPosMarca.startsWith("_") && (this.StringPosMarca.length() != 0)) {
			this.StringPosMarca = "_" + this.StringPosMarca;
		}
		if (this.StringPreMarca.endsWith("_") && (this.StringPreMarca.length() != 0)) {
			this.StringPreMarca = this.StringPreMarca + "_";
		}
		
		return this.actualizarFile();
	}


	public boolean setTimeStampFormat(TimeStampFormat formatoMarcaDeTiempo) throws IllegalTimeStampException {
		// Cambia el formato de la MarcaDeTiempo y renombra el fichero File
		if (this.marca != null) {
			this.marca.setPreferredFormat(formatoMarcaDeTiempo);
			this.StringMarca = this.marca.toString();
			return this.actualizarFile();
		} else {
			// No existe TimeStamp para cambiarle el formato
			throw new IllegalTimeStampException("El archivo " + this.toString() + " no " +
					"contiene un TimeStamp reconocible.");
		}
		
	}

	public boolean setTimeStampAddTime(long variacionTiempoMilisegundos) throws IllegalTimeStampException {
		// Adelanta la MarcaDeTiempo una cantidad de milisegundos dada y renombra el fichero File
		if (this.marca != null) {
			this.marca.addTime(variacionTiempoMilisegundos);
			this.StringMarca = this.marca.toString();
			return this.actualizarFile();
		} else {
			// No existe un TimeStamp válido para variar la 
			throw new IllegalTimeStampException("El archivo " + this.toString() + " no " +
					"contiene un TimeStamp reconocible.");
		}
	}

	public boolean hasTimeStamp() {
		return (this.marca != null);
	}
	
	public boolean deleteTimeStamp() {
		// Elimina el TimeStamp del nombre del archivo
		this.marca = null;
		// Elimina el '_' de StringPosMarca si lo hubiera
		if (this.StringPosMarca.startsWith("_")) {
			this.StringPosMarca = this.StringPosMarca.substring(1);
		}
		// Elimina el '_' de StringPreMarca si lo hubiera
		if (this.StringPreMarca.endsWith("_")) {
			int len = this.StringPreMarca.length();
			this.StringPreMarca = this.StringPreMarca.substring(0, len - 1);
		}
		
		if ((this.StringPreMarca.length() != 0) && (this.StringPosMarca.length() == 0)) {
			this.StringPosMarca = this.StringPreMarca + "_" + this.StringPosMarca;
		} else {
			this.StringPosMarca = this.StringPreMarca + this.StringPosMarca;
		}
		this.StringPreMarca = "";
		this.StringMarca = "";
		
		if (this.StringPosMarca.length() == 0) {
			// El archivo se queda sin nombre, se inventa uno aleatorio de 6 caracteres,
			// empezando por el carácter 'r' para evitar que si se añade a un TimeStamp
			// de formato yyyyMMdd un nombre aleatorio que empiece por 4 dígitos (números)
			// sea confundido con un formato yyyyMMdd_HHmm.
			char[] src = new char[6];
			src[0] = 'r';
			for (int i = 1; i < src.length; i++) {
				int valor = (int)(Math.random() * 36);
				if (valor < 10) {
					// Número 0-9. El carácter '0' tiene el código ASCII 48.
					src[i] = (char)(valor + 48);
				} else {
					// Letra a-z. El carácter 'a' tiene el código ASCII 97. Por eso suma 97 - 10 = 87
					src[i] = (char)(valor + 87);
				}
			}
			this.StringPosMarca = String.valueOf(src);
		}
		
		return this.actualizarFile();
	}
	
	public boolean setStringPosTimeStamp(String s) {
		// Modifica la parte del nombre después de la MarcaDeTiempo y antes de
		// la Extension y renombra el fichero File
		this.StringPosMarca = s;
		return this.actualizarFile();
	}

	@Override
	public String toString() {
		// TODO Pensar en una forma mejor de presentar el Objeto en una String
		return super.toString() + "\nMarcaDeTiempo: " + this.marca
				+ "\nStringPre: " + this.StringPreMarca + "\nStringMarca: "
				+ this.StringMarca + "\nStringPosMarca: " + this.StringPosMarca
				+ "\nStringExtension: " + this.StringExtension + "\n"
				+ super.getParent() + File.separator + this.StringPreMarca
				+ this.StringMarca + this.StringPosMarca + this.StringExtension;
	}

	private String getNombreSinExtension() {
		String nombreCompleto = super.getName();
		int posicionPunto = nombreCompleto.lastIndexOf(".");
		if (posicionPunto > 0) {
			String ext = nombreCompleto.substring(posicionPunto);
			if (!ext.matches(".[0-9][0-9]")) {
				// Se trata de una extensión propiamente dicha, devuelve el
				// resto
				return nombreCompleto.substring(0, posicionPunto);
			} else {
				// Acaba en p. ej. .34 y eso no es una extensión valida, sino
				// quizás el final de un TimeStamp. No separar en nombre y
				// extensión.
				return nombreCompleto;
			}
		} else {
			return nombreCompleto;
		}
	}

	private String getExtensionDelNombre() {
		String nombreCompleto = super.getName();
		int posicionPunto = nombreCompleto.lastIndexOf(".");
		if (posicionPunto > 0) {
			String ret = nombreCompleto.substring(posicionPunto);
			if (!ret.matches(".[0-9][0-9]")) {
				// Se trata de una extensión propiamente dicha
				return ret;
			} else {
				// Si acaba por .34 (por ejemplo) entonces no es una extensión,
				// sino quizás la parte final de un TimeStamp. No separa en
				// nombre y extension.
				return "";
			}
		} else {
			return "";
		}

	}

	private boolean actualizarFile() {
		String nuevoPath = this.getParent() + File.separator
				+ this.StringPreMarca + this.StringMarca + this.StringPosMarca
				+ this.StringExtension;
		return super.renameTo(new File(nuevoPath));
	}

}
