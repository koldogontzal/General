package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Archivo {

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

	public boolean equalsNombre(Archivo a) {
		if (this.nombreSimple.equals(a.getNombreSimple()) &&
			this.extension.equals(a.getExtension())) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean equalsContenido(Archivo a) throws IOException {
		if (this.file.length() != a.getFile().length()) {
			return false;
		} else {
			// Compara byte a byte dos ficheros. Devuelve true si son iguales
			boolean ret = true;
			
			InputStream in1 = new FileInputStream(this.getFile());
			InputStream in2 = new FileInputStream(a.getFile());
			
			byte[] buf1 = new byte[1048576]; // Lee 1 MB en cada tacada
			byte[] buf2 = new byte[1048576];
	        int len1;
	        int len2;
	        while (((len1 = in1.read(buf1)) > 0) && ret) {
	        	// Lee un bloque de 1 MB en cada uno de los ficheros. 
	        	// Repite el bucle de leer bloques de 1 MB mientras sigan siendo iguales
	            len2 = in2.read(buf2);
	            if (len1 == len2) {
	            	// Si ambos bloques leídos tienen el mismo tamaño, entonces los recorre byte a byte
	            	int pos = 0;
	            	while (ret && (pos < len1)) {
	            		if (buf1[pos] == buf2[pos]) {
	            			pos++;
	            		} else {
	            			// Ha encontrado un byte diferente, con lo que los archivos no son iguales
	            			ret = false;
	            		}
	            	}
	            	
	            } else {
	            	// Los bloques leídos tienen distinto tamaño, con lo que los archivos no son iguales
	            	ret = false;
	            }
	        }	
			
	        // Cierra los flujos de entrada de los dos archivos
	        in1.close();
	        in2.close();
	        
	        // Devuelve ret que contiene true sólo si ambos archivos son iguales
			return ret;
		}
	}
/*
	@Override
	public int compareTo(Archivo arg0) {
		if ((this.nombreSimple.equals(arg0.nombreSimple)) &&
			(this.extension == arg0.extension)) {
			// Tienen el mismo nombre, entonces se buscan otras características
			// revisa que el tamaño sea distinto
			if (this.file.length() == arg0.getFile().length()) {
				// El tamaño coincide, con lo que hay que buscar diferencias en alguna otra característica
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
*/	
	private boolean cambiarDeDirectorioRecursivo(File fileDestino) throws FileAlreadyExistsException, IOException {
		if (fileDestino.exists()) {
			// Ya existe un archivo con ese nombre
			Archivo archivoDestino = new Archivo(fileDestino);
			if (this.equalsContenido(archivoDestino)) {
				// No hay que copiar nada porque es el mismo Archivo
				throw new FileAlreadyExistsException(this.file.getName(), new File(fileDestino.getParent()));
			} else {
				// Aumenta el orden para repetir el proceso recursivamente y ver si no existe en el directorio de destino
				archivoDestino.adelantarOrden();
				return cambiarDeDirectorioRecursivo(archivoDestino.getFile());
			}
		} else {
			// No existe un archivo con ese nombre, se copia
			boolean ret = false;
			ret = this.file.renameTo(fileDestino); // Usa el file original, porque si se ha adelantado el orden, el 
			if (ret) {
				this.file = fileDestino;
			}
			return ret;
		}
	}
	
	public boolean cambiarDeDirectorio(File nuevoDirectorio) throws FileAlreadyExistsException, IOException {
		if (nuevoDirectorio.isDirectory()) {
			File nuevoPath = new File (nuevoDirectorio.getPath() + File.separator + this.file.getName());
			return this.cambiarDeDirectorioRecursivo(nuevoPath);
		} else {
			return false;
		}
	}
}
