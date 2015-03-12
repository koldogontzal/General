package utilsBorrables;

import java.io.File;

public class ArreglarDirectorios {

	public static void main(String[] args) {
		File dirBase = new File("G:\\Borrar"); //J:\\Documents and Settings\\Luis\\Escritorio\\kk");
		
		File [] listado = dirBase.listFiles();
		
		int numBase = 1;
		for (File archivo : listado) {
			String nombreNuevo = dirBase.getPath() + File.separator + "dir" + numBase;
			System.out.println(archivo.getPath() + " -> " + nombreNuevo);
			archivo.renameTo(new File(nombreNuevo));
			numBase++;
		}
	}
}
