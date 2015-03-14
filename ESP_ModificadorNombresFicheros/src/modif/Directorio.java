package modif;

import java.io.File;

public class Directorio extends Archivo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3852365642357895818L;

	public Directorio(String dir) {
		super(dir);
	}
	
	public Directorio(Archivo arch) {
		super(arch);
	}
	
	public boolean crearDirectorio() {
		if (super.isDirectory()) {
			// Ya existe el directorio, no hace nada
			return true;
		} else {
			// No existe el directorio
			if (super.exists()) {
				// Esto ocurre cuando existe un archivo que se llama igual que el nuevo directorio
				System.out.println("AVISO/PELIGRO: Ya existe un archivo con el mismo nombre que el directorio " + super.getPath() + "\nNo se ha creado el directorio\n");
				return false;
			} else {
				// Se crea el directorio
				return super.mkdir();
			}
		}
	}
	
	public boolean renombrar(String nombreNuevo) {
		Archivo archivoNuevo = Archivo.CrearArchivoSinExtensionNombreValido(new Directorio(super.getParent()), nombreNuevo, true);
		boolean resultado = false;
		if (!archivoNuevo.isDirectory()) {
			// El Archivo nuevo no existe y renombra el actual a ese nuevo nombre 
			resultado = super.renameTo(archivoNuevo);
		} else {
			// El Archivo ya existe... No hace nada
			resultado = true;
		}
		return resultado;
	}

	public String getExtension() {
		return null;
	}
	
	public String getNombre() {
		return super.getName();
	}
	
	public Archivo[] listArchivos() {
		Archivo[] listadoArchivos;
		File[] listadoFiles = super.listFiles();
		
		listadoArchivos = new Archivo[listadoFiles.length];
		int posicion = 0;
		for (File file : listadoFiles) {
			listadoArchivos[posicion] = new Archivo(file.getPath());
			posicion++;
		}
		return listadoArchivos;
	}
	
	public Directorio [] listDirectorios() {
		Directorio [] listadoArchivos;
		File [] listadoFiles = super.listFiles();
		
		listadoArchivos = new Directorio[listadoFiles.length];
		int posicion = 0;
		for (File file : listadoFiles) {
			Archivo temp = new Archivo(file.getPath());
			if (temp.esDirectorio()) {
				listadoArchivos[posicion] = new Directorio(temp);
				posicion++;
			}
		}
		
		// Hay que devolver una estructura que no tenga nulls, si no peta.
		Directorio [] listadoFinalDirectorios = new Directorio[posicion];
		if (posicion > 0) {			
			for (int i = 0; i < posicion; i++) {
				listadoFinalDirectorios[i] = listadoArchivos[i];
			}
		} 
		return listadoFinalDirectorios;
	}
	
	public ArchivoAudio [] listArchivosAudio() {
		ArchivoAudio [] listadoArchivos;
		File [] listadoFiles = super.listFiles();
		
		listadoArchivos = new ArchivoAudio[listadoFiles.length];
		int posicion = 0;
		for (File file : listadoFiles) {
			Archivo temp = new Archivo(file.getPath());
			if (temp.esArchivoAudio()) {
				listadoArchivos[posicion] = new ArchivoAudio(file.getPath());
				posicion++;
			}
		}
		// Hay que devolver una estructura que no tenga nulls, si no peta.
		ArchivoAudio [] listadoFinal = new ArchivoAudio[posicion];
		if (posicion > 0) {
			for (int i = 0; i < posicion; i ++) {
				listadoFinal[i] = listadoArchivos[i];
			}
		}
		return listadoFinal;
	}
	
	public ArchivoFoto [] listArchivosFoto() {
		ArchivoFoto [] listadoArchivos;
		File [] listadoFiles = super.listFiles();
		
		listadoArchivos = new ArchivoFoto[listadoFiles.length];
		int posicion = 0;
		for (File file : listadoFiles) {
			Archivo temp = new Archivo(file.getPath());
			if (temp.esArchivoFoto()) {
				listadoArchivos[posicion] = new ArchivoFoto(file.getPath());
				posicion++;
			}
		}
		// Hay que devolver una estructura que no tenga nulls, si no peta.
		ArchivoFoto [] listadoFinal = new ArchivoFoto[posicion];
		if (posicion > 0) {
			for (int i = 0; i < posicion; i ++) {
				listadoFinal[i] = listadoArchivos[i];
			}
		}
		return listadoFinal;
	}
	
	public ArchivoTimeStamp [] listArchivosTimeStamp() {
		ArchivoTimeStamp [] listadoArchivos;
		File [] listadoFiles = super.listFiles();
		
		listadoArchivos = new ArchivoTimeStamp[listadoFiles.length];
		int posicion = 0;
		for (File file : listadoFiles) {
			Archivo temp = new Archivo(file.getPath());
			if (!temp.esDirectorio()) {
				listadoArchivos[posicion] = new ArchivoTimeStamp(file.getPath());
				posicion++;
			}
		}
		
		// Hay que devolver una estructura que no tenga nulls, si no peta.
		ArchivoTimeStamp [] listadoFinal = new ArchivoTimeStamp[posicion];
		if (posicion > 0) {
			for (int i = 0; i < posicion; i ++) {
				listadoFinal[i] = listadoArchivos[i];
			}
		}
		return listadoFinal;
	}
}
