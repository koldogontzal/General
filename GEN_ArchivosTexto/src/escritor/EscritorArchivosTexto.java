package escritor;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class EscritorArchivosTexto {

	private PrintWriter pw;
	
	private EscritorArchivosTexto(String nombre) {
		try {
			this.pw = new PrintWriter(nombre);
		} catch (FileNotFoundException e) {
			System.out.println(nombre + ": ERROR. No pudo abrirse el archivo para escritura");
			e.printStackTrace();
		}
	}
	
	public static EscritorArchivosTexto open(String archivo) {
		// No se usa el costructor. Por motivos de simetria con la clase Lector
		// Se usa este metodo estatico para crear el objeto
		return new EscritorArchivosTexto(archivo);
	}
	
	public static boolean createFile(String archivo, String textoCompleto) {
		EscritorArchivosTexto esc = EscritorArchivosTexto.open(archivo);
		if (esc != null) {
			esc.writeLine(textoCompleto);
			esc.close();
			return true;
		} else {
			return false;
		}
	}
	
	public void writeLine(String linea) {
		this.pw.write(linea + "\n");
	}
	
	public void close() {
		this.pw.close();
	}

}
