package lector;

import java.io.EOFException;
import java.io.FileNotFoundException;

public abstract class LectorArchivosTexto {
	
	public static final int MODO_ASCII = 1001;
	public static final int MODO_UTF8 = 1002;
	public static final int MODO_AUTOMATICO = 1003;
	
	
	protected String archivo;
	
	protected LectorArchivosTexto(String archivo) {
		this.archivo = archivo;
	}
	
	public static LectorArchivosTexto open(String nombre, int tipo) {
		// Esta es la manera de crear un objeto de la clase
		// El Constructor no se usa, pues es una clase abstracta
		LectorArchivosTexto l = null;
		try {
			switch (tipo) {
			case MODO_ASCII:
				l = new LectorArchivosTextoASCII(nombre);
				break;
			case MODO_UTF8:
				l = new LectorArchivosTextoUTF8(nombre); 
				break;
			case MODO_AUTOMATICO:
				// TODO: implementar la deteccion automatica
			}
		} catch (FileNotFoundException e) {
			System.out.println(nombre + ": ERROR. Archivo no encontrado.");
			e.printStackTrace();
			return null;
		}
		return l;		
	}
	
	public static String readFile(String nombre, int tipo) {
		String ret = "";
		LectorArchivosTexto lec = LectorArchivosTexto.open(nombre, tipo);
		if (lec != null) {
			try {
				while (true) {
					String linea = lec.readLine();
					ret = ret + linea + "\n";
				}
			} catch (EOFException e) {
				lec.close();
				return ret;
			}
		} else {
			return null;
		}
	}
	
	public abstract String readLine() throws EOFException;
	
	public abstract void close();

}
