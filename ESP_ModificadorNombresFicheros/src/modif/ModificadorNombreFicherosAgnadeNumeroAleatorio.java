package modif;

import java.io.File;
import java.util.Random;

public class ModificadorNombreFicherosAgnadeNumeroAleatorio {

	public static void main(String[] args) {
		File directorio = new File ("/home/luis/Escritorio/Fotos_caras_todos");
		
		if (!directorio.isDirectory()) {
			System.out.println("No se trata de un directorio.");
			System.exit(-1);
		}
		// Obtiene la lista de ficheros en el directorio
		String [] listadoFicheros = directorio.list();
		
		// Recorrer la lista y modificarla
		for (String nombre: listadoFicheros) {
			if (nombre != null) {
				String nombreFormateado = nombre;
				if (nombre.matches("[0-9][0-9][0-9]*.*")) {
					nombreFormateado = nombre.substring(4);
				}
			
				String nombreCompletoOriginal = directorio.getAbsolutePath() + File.separator + nombre;
				File original = new File (nombreCompletoOriginal);
				
				Random rnd = new Random();
				String numeroAleatorio = String.format("%03d", rnd.nextInt(1000));
				String nombreCompletoModificado = directorio.getAbsolutePath()
						+ File.separator + numeroAleatorio + "_" + nombreFormateado;
				File renombrado = new File (nombreCompletoModificado);
				
				original.renameTo(renombrado);
				
			}
		}
	}
}
