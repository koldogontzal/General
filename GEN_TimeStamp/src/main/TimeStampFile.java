package main;

import java.io.File;

import utils.TimeStampParser;

public class TimeStampFile extends File {

	private static final long serialVersionUID = -4736566271369957954L;
	private static final int FormatoDefault = TimeStamp.FormatYYYYMMDD_hhmmss;

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
			this.marca = TimeStampParser.lookForTimeStamp(nombre,
					nombreDescompuesto);
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

	public boolean setTimeStamp(TimeStamp nuevaMarca, int formatoMarcaDeTiempo) {
		// Sustituye la MarcaDeTiempo por una nueva con un formato dado y
		// renombra el fichero File
		this.StringMarca = nuevaMarca.toString(formatoMarcaDeTiempo);
		this.marca = nuevaMarca;
		return this.actualizarFile();
	}

	public boolean setTimeStamp(TimeStamp nuevaMarca) {
		// Sustituye la MarcaDeTiempo por una nueva y renombra el fichero File
		return this.setTimeStamp(nuevaMarca, FormatoDefault);
	}

	public boolean setTimeStamp(int formatoMarcaDeTiempo) {
		// Cambia el formato de la MarcaDeTiempo y renombra el fichero File
		return this.setTimeStamp(this.marca, formatoMarcaDeTiempo);
	}

	public boolean setTimeStamp(long variacionTiempoMilisegundos,
			int formatoMarcaDeTiempo) throws IllegalTimeStampException {
		// Adelanta la MarcaDeTiempo una cantidad de milisegundos dada con un
		// formato dado y renombra el fichero File
		if (this.marca != null) {
			this.marca.addTime(variacionTiempoMilisegundos);
			return this.setTimeStamp(this.marca, formatoMarcaDeTiempo);
		} else {
			throw new IllegalTimeStampException("El archivo " + this.toString() + " no " +
					"contiene un TimeStamp reconocible.");
		}
	}

	public boolean setTimeStamp(long variacionTiempoMilisegundos) throws IllegalTimeStampException {
		// Adelanta la MarcaDeTiempo una cantidad de milisegundos dada y
		// renombra el fichero File
		return this.setTimeStamp(variacionTiempoMilisegundos, FormatoDefault);
	}

	public boolean deleteTimeStamp() {
		// Elimina el TimeStamp del nombre del archivo
		this.marca = null;
		this.StringPosMarca = this.StringPreMarca + this.StringPosMarca;
		this.StringPreMarca = "";
		this.StringMarca = "";
		
		if (this.StringPosMarca.length() == 0) {
			// El archivo se queda sin nombre, se inventa uno aleatorio de 6 caracteres
			char[] src = new char[6];
			for (int i = 0; i < src.length; i++) {
				int valor = (int)(Math.random() * 36);
				if (valor < 10) {
					// Número 0-9
					src[i] = (char)(valor + 48);
				} else {
					// letra a-z
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
				// quizás el final de un TimeStamp. No seprar en nombre y
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

	
	public static void main(String[] args) {
		TimeStampFile f = new TimeStampFile("C:\\hora\\2014_10_04.doc");
		f.deleteTimeStamp();
	}
}
