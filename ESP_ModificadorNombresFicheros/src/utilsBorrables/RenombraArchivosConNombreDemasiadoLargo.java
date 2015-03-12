package utilsBorrables;

import java.io.File;

public class RenombraArchivosConNombreDemasiadoLargo {

	public static void main(String[] args) {
		File dirBase = new File("D:\\Musica\\Música clásica\\Bach\\L'Offrande Musicale, Cantate Ich Habe Genug BWV 1079 & BWV 82");
		
		File [] listado = dirBase.listFiles();
		for (File archivo : listado) {
			String nombre = archivo.getName();
			int longitud = nombre.length();
			if (longitud > 167) {
				// Si es demasiado largo el nombre, corta la parte izquierda
				nombre = nombre.substring(longitud - 167, longitud);
				// Lo renombra
				File nombreNuevo = new File (archivo.getParent() + File.separator + nombre);
				archivo.renameTo(nombreNuevo);
			}
		}
	}
}
