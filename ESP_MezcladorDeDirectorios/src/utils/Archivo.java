package utils;

import java.io.File;

public class Archivo implements Comparable<Archivo> {

	private static final String stringAperturaOrden = "_("; // El orden se representa como "_(n)" donde n es un entero
	private static final String stringClausuraOrden = ")";  // al final del nombre del archivo
	
	private File file;
	private String nombreSimple; // Nombre del archivo, sin contar con el posible orden que lleve
	private int orden; // Cuando un nombre se repite en archivos distintos, entonces se añade un orden al final 
		// del nombre, siguiendo el formato _(n) donde n = 2, 3, 4, 5...
		// Las constantes stringAperturaOrden y stringClausuraOrden definen qué caracteres encierran al orden
		// Si el nombre del archivo es único, n = 1 y no se añade _(1) al nombre
	private String extension;

	public Archivo(File file) {
		this.file = file;
		rellenaDatosNombre(file.getName());
		
	}
	
	public Archivo(String pathname) {
		this(new File(pathname));
	}
	
	private void rellenaDatosNombre(String nombreCompleto) {
		// Busca el nombre, el orden y la extension
		String restoNombre;
		
		// Identifica la extension
		int posUltimoPunto = nombreCompleto.lastIndexOf(".");
		if (posUltimoPunto == -1) {
			// No tiene extensión.
			restoNombre = nombreCompleto;
			this.extension = null;
		} else if (posUltimoPunto == 0) {
			// Es sólo una extensión
			restoNombre = null;
			this.orden = 1;
			this.extension = nombreCompleto;
		} else {
			// Tiene una parte de nombre y otra de extension
			restoNombre = nombreCompleto.substring(0, posUltimoPunto);
			this.extension = nombreCompleto.substring(posUltimoPunto);
		}
		
		// Identifica el nombre y el orden
		if (restoNombre != null) {
			int posAperturaOrden = restoNombre.lastIndexOf(stringAperturaOrden);
			if (posAperturaOrden >= 0) {
				// El nombre ya incluye un orden. Hay que leerlo
				int posClausuraOrden = restoNombre.indexOf(stringClausuraOrden,posAperturaOrden);
				if (posClausuraOrden == restoNombre.length() - 1) {
					// El resto del nombre termina en _(n), con lo que es un orden correcto
					this.nombreSimple = restoNombre.substring(0, posAperturaOrden);
					try {
						this.orden = Integer.parseInt(restoNombre.substring(posAperturaOrden + stringAperturaOrden.length(), posClausuraOrden));
					} catch (NumberFormatException e) {
						// Falsa alarma, el valor n no es un número
						this.nombreSimple = restoNombre;
						this.orden = 1;
					}
				} else {
					// Después de _(n) sigue habiendo caracteres, así que no es un orden correcto
					this.nombreSimple = restoNombre;
					this.orden = 1;
				}
			
			} else {
				// El nombre no incluye un orden
				this.nombreSimple = restoNombre;
				this.orden = 1;
			}						
		} 
	}
	

	
	@Override
	public String toString() {
		if (this.orden == 1) {
			// Es el primero (y único) archivo con ese nombre, se olvida del orden
			return this.file.getParent() + File.separator + (this.nombreSimple != null ? this.nombreSimple : "") 
					+ (this.extension != null ? this.extension : "");
		} else {
			// Tiene un orden distinto de 1, con lo que hay que añadirlo
			return this.file.getParent() + File.separator + (this.nombreSimple != null ? this.nombreSimple : "") 
					+ stringAperturaOrden + this.orden + stringClausuraOrden 
					+ (this.extension != null ? this.extension : "");
		}
	}
	
	public File getFile() {
		return this.file;
	}

	public String getNombreSimple() {
		return nombreSimple;
	}
	
	public int getOrden() {
		return orden;
	}

	public void adelantarOrden() {
		this.orden++;
		this.file = new File(this.toString());
	}
	
	public String getExtension() {
		return extension;
	}

	@Override
	public int compareTo(Archivo arg0) {
		if ((this.nombreSimple.equals(arg0.nombreSimple)) &&
			(this.extension == arg0.extension)) {
			// Tienen el mismo nombre, entonces se buscan otras características
			// revisa que el tamaño sea distinto
			if (this.file.length() == arg0.getFile().length()) {
				// El tamaño coincide, con lo que hay que buscar diferencias en alguna otra cracterística
				// TODO: Comparar los archivos byte a byte
				return 0;
			} else {
				// Devuelve la comparación de tamaños
				return (int) (this.file.length() - arg0.getFile().length());
			}
		} else {
			// Los nombre son distintos, con lo que los compara lexicológicamente y devuelve ese valor
			return (this.nombreSimple + this.extension).compareTo(arg0.nombreSimple + arg0.extension);
		}
	}
	
	public boolean cambiarDeDirectorio(File nuevoDirectorio) throws FileAlreadyExistsException {
		if (nuevoDirectorio.isDirectory()) {
			File nuevoPath = new File (nuevoDirectorio.getPath() + File.separator + this.file.getName());
			if (nuevoPath.exists()) {
				// Ya existe un archivo con ese nombre
				Archivo nuevo = new Archivo(nuevoPath);
				if (this.equals(nuevo)) {
					// No hay que copiar nada porque es el mismo Archivo
					throw new FileAlreadyExistsException(this.file.getName(), nuevoDirectorio);
				} else {
					// Aumenta el orden para repetir el proceso recursivamente
					this.adelantarOrden();
					return cambiarDeDirectorio(nuevoDirectorio);
				}
			} else {
				boolean ret = false;
				ret = this.file.renameTo(nuevoPath);
				if (ret) {
					this.file = nuevoPath;
				}
				return ret;
			}
		} else {
			return false;
		}
	}
}
