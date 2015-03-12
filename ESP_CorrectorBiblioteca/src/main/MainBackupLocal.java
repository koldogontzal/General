package main;

import java.io.File;

import biblio.Biblioteca;
import biblio.Sincronizador;

public class MainBackupLocal {

	public static void main(String[] args) {
		
		/*
		 * En primer lugar intenta descubrir si se está ejecutando en casa o en
		 * el INAP para definir qué fichero de biblioteca actuará como modelo y
		 * cual será el directorio local
		 */	
		int local;		
		Sincronizador sinc = new Sincronizador();
		
		local = sinc.getLocal();
		
		/*
		 * Si sabe donde se está ejecutando, hace un backup
		 */
		if (sinc.esEntornoConocido()) { // comprueba que está en el INAP o en CASA
			// Hace una copia de seguridad del fichero local
			System.out.println("HACIENDO COPIA DE SEGURIDAD DEL FICHERO BIBLIOTECA...");
			File ficheroBackup = new File(Sincronizador.FICH_BACKUP[local]);
			if (ficheroBackup.isFile()) {
				// Si ya existe una copia de seguridad, primero la borra
				boolean borrado = ficheroBackup.delete();
				if (!borrado) {
					System.err.println("ERROR: no se pudo borrar el fichero " + ficheroBackup.getPath());
				}
			}
			File fichero = new File(Sincronizador.FICH_EXPORT[local]);
			fichero.renameTo(new File(Sincronizador.FICH_BACKUP[local]));
			// Lee la biblioteca local del árbol de directorios
			System.out.println("LEYENDO LA BIBLIOTECA LOCAL...");
			Biblioteca bib = new Biblioteca(Sincronizador.RUTAS_BIB[local]);
			// Graba un fichero con la Biblioteca
			System.out.println("GRABANDO ARCHIVO DE COPIA DE LA BIBLIOTECA " + Sincronizador.FICH_EXPORT[local]);
			bib.exportarFichero(Sincronizador.FICH_EXPORT[local]);
			// Imprime información sobre la biblioteca
			Biblioteca repetidos = bib.subBibliotecaRepetidos();
			System.out.println("LIBROS REPETIDOS:\n" + repetidos + "\n");
			Biblioteca librosSinID = bib.subBibliotecaLibrosSinID();
			System.out.println("LIBROS SIN ID:\n" + librosSinID + "\n");
		}
		
	}

}
