package modif;

import apliGraf.Aplicacion;

public class ReorganizadorArchivosMusicaPorDirectorios {

	private Directorio dirBase;
	private Directorio dirActual;
	
	private Aplicacion app;
	
	public ReorganizadorArchivosMusicaPorDirectorios(String dirBase, String dirActual, Aplicacion app) {
		this.dirBase = new Directorio(dirBase);
		this.dirActual = new Directorio(dirActual);
		this.app = app;
		
		if (this.dirBase.esDirectorio()) {
			this.app.escribirLinea("Reorganizando desde " + dirBase +"\n");
			this.ejecutaAlgoritmo();
		} else {
			this.app.escribirLinea("Error: No es un directorio: " + dirBase);
		}
	}
	
	private void ejecutaAlgoritmo () {
		Directorio [] listadoDirs = this.dirActual.listDirectorios();
		ArchivoAudio [] listadoArchAudio = this.dirActual.listArchivosAudio();
		
		// Primero recorre los archivos
		for (ArchivoAudio archivo : listadoArchAudio) {
			// 1) Artista
			String artista = archivo.getArtista();
			Directorio directorioFinal = new Directorio(Archivo.CrearArchivoSinExtensionNombreValido(this.dirBase, artista, true));
			directorioFinal.crearDirectorio();
			
			// 2) Album
			String album = archivo.getAlbum();
			directorioFinal = new Directorio(Archivo.CrearArchivoSinExtensionNombreValido(directorioFinal, album, true));
			directorioFinal.crearDirectorio();
			
			// 3) Numero de disco
			int numDisco = archivo.getNumeroDisco();
			if (numDisco > 0) {
				directorioFinal = new Directorio(Archivo.CrearArchivoSinExtensionNombreValido(directorioFinal, "CD" + numDisco, true));
				directorioFinal.crearDirectorio();
			}
			
			// Ahora lo cambia de lugar, si tiene datos para ello. Si no, no hace nada.
			Archivo archivoFinal = Archivo.CrearArchivoConExtensionNombreValido(directorioFinal, archivo.getNombre(), archivo.getExtension());
			String nombreArchivoOrig = archivo.getPath();
			boolean exitoAlMover = archivo.mover(archivoFinal);
			String linea = (exitoAlMover ? "OK   : " : "FALLO: ");
			linea = linea + nombreArchivoOrig + "   ->   " + archivoFinal.getPath();
			this.app.escribirLinea(linea);
			if (!exitoAlMover) {
				// Lo imprime por el System.out para visualizar más fácil lo que ha ido mal...
				System.out.println(linea);
			}
		}
		
		// Luego llama recursivamente al resto de directorios
		for (Directorio dirRecur : listadoDirs) {
			new ReorganizadorArchivosMusicaPorDirectorios(this.dirBase.getPath(), dirRecur.getPath(), this.app);
		}
	}	
}
