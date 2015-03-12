package modif;

import java.io.File;

public class Archivo extends File {

	public static String separadorDeDirectorios = File.separator;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3340945803320633695L;
	
	public Archivo(String pathname) {
		super(pathname);
	}
	
	public Archivo(Archivo arch) {
		super(arch.getPath());
	}
		
	public boolean renombrar(String nombreNuevo) {
		
		Archivo archivoNuevo = Archivo.CrearArchivoConExtensionNombreValido(new Directorio(super.getParent()), nombreNuevo, this.getExtension());
		
		return this.mover(archivoNuevo);
	}
	
	public boolean mover(Archivo nombreNuevo) {
		boolean resultado = false;
		if (!nombreNuevo.exists()) {
			// El Archivo nuevo no existe y renombra el actual a ese nuevo nombre 
			resultado = super.renameTo(nombreNuevo);
		} else {
			// El Archivo ya existe... Que hacer?
			long tamActual = super.length();
			long tamNuevo = nombreNuevo.length();
			if (tamActual == tamNuevo) {
				// Son el mismo archivo, no hay que hacer nada
				resultado = true;
			} else {
				// Son archivos distintos, hay que cambiar el nombre del nuevo agnadiendole un numero aleatorio
				String nombre = this.getNombre();
				nombre = nombre + "_" + (int)(Math.random() * 1000);
				Archivo archRecursivo = Archivo.CrearArchivoConExtensionNombreValido
					(new Directorio(super.getParent()), nombre, this.getExtension());
				resultado = this.mover(archRecursivo);				
			}
		}
		return resultado;
	}
	
	public boolean esDirectorio() {
		return super.isDirectory();
	}
	
	public boolean esArchivoFoto() {
		return ((super.exists()) && (this.esArchivoJPG() || this.esArchivoTHM() || this.esArchivoAVI() || 
				this.esArchivoMP4() || this.esArchivoM4V() || this.esArchivoMTS() || this.esArchivoMOV()));
	}
	
	private boolean esArchivoJPG() {
		String extension = this.getExtension();
		if (extension == null) {
			return false;
		} else {
			return (extension.toLowerCase().equals("jpg") || extension.toLowerCase().equals("jpeg"));
		}
	}
	
	private boolean esArchivoTHM() {
		String extension = this.getExtension();
		if (extension == null) {
			return false;
		} else {
			return extension.toLowerCase().equals("thm");
		}
	}
	
	private boolean esArchivoAVI() {
		String extension = this.getExtension();
		if (extension == null) {
			return false;
		} else {
			return extension.toLowerCase().equals("avi");
		}
	}
	
	private boolean esArchivoMP4() {
		String extension = this.getExtension();
		if (extension == null) {
			return false;
		} else {
			return extension.toLowerCase().equals("mp4");
		}
	}
	
	private boolean esArchivoM4V() {
		String extension = this.getExtension();
		if (extension == null) {
			return false;
		} else {
			return extension.toLowerCase().equals("m4v");
		}
	}
	
	private boolean esArchivoMTS() {
		String extension = this.getExtension();
		if (extension == null) {
			return false;
		} else {
			return extension.toLowerCase().equals("mts");
		}
	}
	
	private boolean esArchivoMOV() {
		String extension = this.getExtension();
		if (extension == null) {
			return false;
		} else {
			return extension.toLowerCase().equals("mov");
		}
	}
	
	public boolean esArchivoAudio() {
		return ((super.exists()) && (this.esArchivoMP3() || this.esArchivoWMA() || this.esArchivoM4A()));
	}
	
	private boolean esArchivoMP3() {
		String extension = this.getExtension();
		if (extension == null) {
			return false;
		} else {
			return extension.toLowerCase().equals("mp3");
		}
	}
	
	private boolean esArchivoWMA() {
		return false;
		// habra que mirar luego .equals("wma");
	}
	
	private boolean esArchivoM4A() {
		return false;
		// habra que mirar luego .equals("m4a");
	}
	
	public String getExtension() {
		String extension = super.getName();
		int posicionPunto = extension.lastIndexOf(".");
		if (posicionPunto > 0) {
			extension = extension.substring(posicionPunto + 1, extension.length());
		} else {
			extension = null;
		}
		return extension;
	}
	
	public String getNombre() {
		String nombre = super.getName();
		int posicionPunto = nombre.lastIndexOf(".");
		if (posicionPunto > 0) {
			nombre = nombre.substring(0, posicionPunto);
		} else {
			// No hay punto, no modifica la variable nombre
		}
		return nombre;
	}
	
	public static Archivo CrearArchivoSinExtensionNombreValido(Directorio dirBase, String nombre, boolean formatNombre) {
		if ((nombre == null) || (nombre.equals(""))) {
			// El nombre esta vacio, devuelve dirBase
			return dirBase;
		} else {
			// El nombre no esta vacio
			if (formatNombre) {
				nombre = Archivo.formatearNombre(nombre);
			}

			return new Archivo(dirBase.getPath() + Archivo.separadorDeDirectorios + nombre);
		}
	}
	
	public static Archivo CrearArchivoConExtensionNombreValido(Directorio dirBase, String nombre, String extension) {		
		if (extension == null) {
			// Si no hay extension, usa el metodo sin extension
			return Archivo.CrearArchivoSinExtensionNombreValido(dirBase, nombre, true);
		} else {		
			if (nombre.equals("")) {
				// El nombre esta vacio, devuelve dirBase
				return dirBase;
			} else {
				// El nombre no esta vacio
				nombre = Archivo.formatearNombre(nombre);
				
				return new Archivo(dirBase.getPath()
						+ Archivo.separadorDeDirectorios + nombre + "."
						+ extension);
			}
		}
	}
	
	private static String formatearNombre(String nombre) {
		
		// El nombre no puede tener m�s de 167 caracteres de largo, con extension.
		// Serian 163 sin extension. 
		// Por seguridad, ponemos 162, para extensiones de 4 caracteres
		int longitud = nombre.length();
		int posicionPrimerNuevoCaracter = 0;
		if (longitud > 162) {
			posicionPrimerNuevoCaracter = longitud - 162;
		}
		while (nombre.charAt(posicionPrimerNuevoCaracter) == ' ') {
			posicionPrimerNuevoCaracter++;			
		}
		
		// Si es demasiado largo el nombre, corta la parte izquierda
		nombre = nombre.substring(posicionPrimerNuevoCaracter, longitud);
		
		// Ahora elimina caracteres no permitidos y/o peligrosos
		nombre = Archivo.formatearQuitandoCaracter(nombre, '?');
		nombre = Archivo.formatearQuitandoCaracter(nombre, '*');
		nombre = Archivo.formatearQuitandoCaracter(nombre, '/');
		nombre = Archivo.formatearQuitandoCaracter(nombre, '\\');
		nombre = Archivo.formatearQuitandoCaracter(nombre, ':');
		nombre = Archivo.formatearQuitandoCaracter(nombre, '<');
		nombre = Archivo.formatearQuitandoCaracter(nombre, '>');
		nombre = Archivo.formatearQuitandoCaracter(nombre, '|');
		nombre = Archivo.formatearQuitandoCaracter(nombre, '"');
		nombre = Archivo.formatearQuitandoCaracter(nombre, '.');
		
		return nombre;
	}
	
	private static String formatearQuitandoCaracter(String nombre, char caracter) {
		nombre = nombre.replace(caracter, '_');
		return nombre;
	}
	
	public static void escribirStringEnteros(String s) {
		System.out.println(s);
		if (s != null) {
			for (int i = 0; i < s.length(); i++) {
				char c = s.charAt(i);
				int n = c;
				System.out.print(n + " - ");
			}
			System.out.println("\n");
		}
	}	

	@Override
	public String toString() {
		return super.getPath();
	}
}


