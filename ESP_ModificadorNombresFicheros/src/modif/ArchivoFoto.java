package modif;

import java.util.Iterator;

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
	
	private Metadata metadata = null;;
	
	public ArchivoFoto(String archivo) {
		super(archivo);
		try {
			this.metadata = ImageMetadataReader.readMetadata(this);
		} catch (ImageProcessingException e) {
			System.out.println("No es posible procesar el archivo de fotos " + this);
		}
	}
	
	
	public MarcaDeTiempo getMarcaDeTiempo() {
		// Devuelve la fecha con formato MarcaDeTiempo
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
						// En realidad no ha encontrado una marca de tiempo
						return null;
					} else {
						// Si la ha encontrado
						return new MarcaDeTiempo(temp2);
					}
				} else {
					// no ha encotrado los datos referentes a la fecha
					return null;
				}
			} catch (MetadataException e) {
				System.out.println("Ha habido un error procesando los metadatos de " + this);
				return null;
			}
		} else {
			// No existen Metadatos
			return null;
		}
	}

	private int tieneMarcaDeTiempoElNombre() {
		// Función que devuelve el número de caracteres que ocupa la marca de tiempo al inicio del nombre del archivo (14, 15 ó 19)
		// Devuelve 0 si el nombre no empieza por una marca de tiempo reconocida
		String nombre = super.getNombre();
		
		if (nombre.length() >= 14) {
			// Prueba con el formato aaaammddhhmmss
			if (MarcaDeTiempo.StringConforme(nombre.substring(0, 14))) {
				return 14;
			} else if (nombre.length() >= 15) {
				// Prueba con el formato aaaammdd_hhmmss
				if (MarcaDeTiempo.StringConforme(nombre.substring(0, 15))) {
					return 15;
				} else if (nombre.length() >=19) {
					// Prueba con el formato aaaa-mm-dd hh.mm.ss
					if (MarcaDeTiempo.StringConforme(nombre.substring(0, 19))) {
						return 19;
					} else {
						// El nombre del archivo no empieza por un formato de marca de tiempo reconocida
						return 0;
					}
				}
			} 
		}
		//return !((nombre.length() <= 14) || (!MarcaDeTiempo.StringConforme(nombre.substring(0, 14))));
		return 0;
	}
	
	public boolean agnadirMarcaDeTiempoAlNombre() {
		String nombre = super.getNombre();
		
		if (this.tieneMarcaDeTiempoElNombre() == 0) {
			// Seguro que el nombre no tiene MarcaDeTiempo
			MarcaDeTiempo marca = this.getMarcaDeTiempo();
			if (marca != null) {
				// Ha leido con exito los metadata
				String nombreFinal = marca.toString() + "_" + nombre;
				
				// Agnadido para manejar los ficheros de video con su archivo THM
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
				// NO ha leido los metadata
				return false;
			}
		} else {
			// El nombre ya tiene Marca de tiempo
			return true;
		}
	}
	
	public boolean quitarMarcaDeTiempoAlNombre() {
		int tamagnoMarcaTiempo = this.tieneMarcaDeTiempoElNombre();
		if (tamagnoMarcaTiempo == 0) {
			// Seguro que el nombre no tiene MarcaDeTiempo
			return false;
		} else {
			// El nombre ya tiene Marca de tiempo
			String nombre = super.getNombre();
			int longitudNombre = nombre.length();
			if (longitudNombre > tamagnoMarcaTiempo) {
				return super.renombrar(nombre.substring(tamagnoMarcaTiempo + 1, longitudNombre));
			} else {
				return false;
			}
		}
	}
	
	public boolean modificarMarcaDeTiempoDelNombre(long dif) {
		// se añade "dif" en milisegundos
		int tamagnoMarcaTiempo = this.tieneMarcaDeTiempoElNombre();
//System.out.println(tamagnoMarcaTiempo);		
		if (tamagnoMarcaTiempo != 0) {
			// Tiene marca de tiempo
			String nombre = super.getNombre();
			int longitudNombre = nombre.length();
			MarcaDeTiempo marca = new MarcaDeTiempo(nombre.substring(0, tamagnoMarcaTiempo));
			marca.agnadirLapso(dif);
			String nombreFinal;
			if (longitudNombre > tamagnoMarcaTiempo) {
				nombreFinal = marca.toString() + "_" + nombre.substring(tamagnoMarcaTiempo + 1, longitudNombre);
			} else {
				nombreFinal = marca.toString();
			}
			return super.renombrar(nombreFinal);			
		} else {
			// No tiene marca
			return false;
		}
	}
}
