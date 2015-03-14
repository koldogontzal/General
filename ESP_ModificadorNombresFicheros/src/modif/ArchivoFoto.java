package modif;

import java.util.Iterator;

import main.IllegalTimeStampException;
import main.TimeStamp;
import main.TimeStampFile;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;

public class ArchivoFoto extends Archivo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2737770353528530260L;
	
	private Metadata metadata = null;
	private TimeStampFile tsf = null;
	
	public ArchivoFoto(String archivo) {
		super(archivo);
		try {
			this.metadata = ImageMetadataReader.readMetadata(this);
		} catch (ImageProcessingException e) {
			System.out.println("No es posible procesar el archivo de fotos " + this);
		}
	}
	
	
	public TimeStamp getTimeStampDeMetadatos() {
		// Devuelve la fecha con formato TimeStamp
		if (this.metadata != null) {
			// Existen metadatos
			try {
				// Busca en los metadatos la fecha
				String temp = "";
				// iterate through metadata directories
				Iterator<?> directories = metadata.getDirectoryIterator();
				while (directories.hasNext()) {
					Directory directory = (Directory) directories.next();
					if (directory.getName().equals("Exif")) {
						// iterate through tags and print to System.out
						Iterator<?> tags = directory.getTagIterator();
						while (tags.hasNext()) {
							Tag tag = (Tag) tags.next();
							if (tag.getTagName().equals("Date/Time Digitized")) {
								temp = tag.getDescription();
							}
						}
					}
				}
				if ((temp != null) && (!temp.equals(""))) {
					// Ha encontrado los datos referentes a la fecha
					String temp2 = "";
					for (int i = 0; i < temp.length(); i++) {
						if ((temp.charAt(i) <= '9') && (temp.charAt(i) >= '0')) {
							temp2 = temp2 + temp.charAt(i);
						}
					}
					if (temp2.equals("")) {
						// En realidad no ha encontrado datos de fecha
						return null;
					} else {
						// Si los ha encontrado, devuelve un TimeStamp
						try {
							return new TimeStamp(temp2);
						} catch (IllegalTimeStampException e) {
							// No ha reconocido el TimeStamp
							return null;
						}
					}
				} else {
					// no ha encontrado los datos referentes a la fecha
					return null;
				}
			} catch (MetadataException e) {
				System.out.println("Ha habido un error procesando los metadatos de " + this);
				return null;
			}
		} else {
			// No existen metadatos
			return null;
		}
	}

	private boolean tieneTimeStampElNombre() {
		// Función que devuelve true si el nombre del fichero contiene un TimeStamp reconocido
		if (this.tsf == null) 
			this.tsf = new TimeStampFile(super.getPath());
		
		try {
			this.tsf.getTimeStamp();
			// Sí tiene TimeStamp reconocido, porque si no, se generaría una Exception
			return true;
		} catch (IllegalTimeStampException e) {
			// No tiene TimeStamp, porque se ha generado una Exception
			return false;
		}	
		
	}
	
	public boolean agnadirTimeStampAlNombre(int formatoTimeStamp) {
		// Sólo añade un TimeStamp en el caso en el que no exista ya uno
		String nombre = super.getNombre();
		
		if (!this.tieneTimeStampElNombre()) {
			// Seguro que el nombre no tiene MarcaDeTiempo
			TimeStamp marca = this.getTimeStampDeMetadatos();
			if (marca != null) {
				// Ha leído con éxito los metadata
				String nombreFinal = marca.toString(formatoTimeStamp) + "_" + nombre;
				
				// Añadido para manejar los ficheros de video con su archivo THM
				if (this.getExtension().toUpperCase().equals("THM")) {
					Archivo archVideo = super.CrearArchivoConExtensionNombreValido(new Directorio(this.getParent()), nombre, "AVI");
					if (!archVideo.exists()) {
						archVideo = super.CrearArchivoConExtensionNombreValido(new Directorio(this.getParent()), nombre, "MOV");
					}
					if (!archVideo.renombrar(nombreFinal)) {
						System.out.println("ERROR: No existe el archivo " + archVideo);
					}					
				}
				
				// Modifica el nombre del archivo
				return super.renombrar(nombreFinal);
			} else {
				// No ha leído los metadata
				return false;
			}
		} else {
			// El nombre ya tiene Marca de tiempo
			return true;
		}
	}
	
	public boolean quitarTimeStampAlNombre() {
		if (!this.tieneTimeStampElNombre()) {
			// Seguro que el nombre no tiene TimeStamp
			return false;
		} else {
			return this.tsf.deleteTimeStamp();
		}
	}
	
	public boolean modificarTimeStampDelNombre(long dif, int formatoTimeStamp) {
		// Se añade "dif" en milisegundos al TimeStamp

		if (this.tieneTimeStampElNombre()) {
			// Tiene un TimeStamp, con lo que lo modifica
			try {
				return this.tsf.setTimeStamp(dif, formatoTimeStamp);
			} catch (IllegalTimeStampException e) {
				// Nunca debería darse este caso
				e.printStackTrace();
				return false;
			}
		
		} else {
			// No tiene marca
			return false;
		}
	}
}
