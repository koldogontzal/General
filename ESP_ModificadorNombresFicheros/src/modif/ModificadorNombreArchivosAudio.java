package modif;

import apliGraf.Aplicacion;

public class ModificadorNombreArchivosAudio {
	
	private Directorio directorio;
	
	private Aplicacion app;
	
	public ModificadorNombreArchivosAudio(String directorio, Aplicacion app) {
		this.directorio = new Directorio(directorio);
		this.app = app;
		
		if (!this.directorio.isDirectory()) {
			this.app.escribirLinea("ERROR: " + this.directorio.getPath() + " no se trata de un directorio");
		} else {
			Directorio [] listadoDirs = this.directorio.listDirectorios();
			ArchivoAudio [] listadoArchAudio = this.directorio.listArchivosAudio();
			
			// Primero, llamada recursiva a los directorios
			for (Directorio dirRecur : listadoDirs) {
				new ModificadorNombreArchivosAudio(dirRecur.getPath(), this.app);
			}
			
			// Segundo, cambiar el nombre de los archivos de audio
			for (ArchivoAudio archivo : listadoArchAudio) {
				this.app.escribirLinea("Archivo " + archivo.getPath());
				this.cambiarNombre(archivo);
				this.app.escribirLinea("fArchivo\n");
			}
		}		
	}
	
	private void cambiarNombre(ArchivoAudio archivo) {		
		String titulo = archivo.getTitulo();
		
		if (!titulo.equals("")) {
			// Modifica el nombre si al menos tiene titulo
			String artista = archivo.getArtista();
			String album = archivo.getAlbum();
			int pista = archivo.getNumeroPista();
			
			String nombreFinal = titulo;
			if (pista > 0) {
				String stringPista = "000" + pista;
				int longSP = stringPista.length();
				stringPista = stringPista.substring(longSP -2, longSP);
				nombreFinal = stringPista + " - " + nombreFinal;
			}
			if (!album.equals("")) {
				nombreFinal = album + " - " + nombreFinal;
			}
			if (!artista.equals("")) {
				nombreFinal = artista + " - " + nombreFinal;
			}
			
			boolean result = archivo.renombrar(nombreFinal);
			this.app.escribirLinea("\t" + result + " <- Nuevo nombre:" + nombreFinal);
			if (!result) {
				System.out.println("Fallo en " + archivo);
			}
			
		} else {
			// no hace nada
			this.app.escribirLinea("\tNo se ha modificado el nombre; faltan datos");
		}				
	}
}
