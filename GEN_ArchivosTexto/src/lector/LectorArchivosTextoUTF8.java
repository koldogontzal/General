package lector;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class LectorArchivosTextoUTF8 extends LectorArchivosTexto {
	
	private BufferedReader bReader;
	
	public LectorArchivosTextoUTF8(String nombre) throws FileNotFoundException {
		super(nombre);
		this.bReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(nombre))));
	}

	
	@Override
	public String readLine() throws EOFException {
		// Lee la linea
		String linea = null;
		try {
			linea = bReader.readLine();
				
		} catch (IOException e) {
			System.out.println(super.archivo + ": ERROR. Imposible leer archivo");
			e.printStackTrace();
			return null;
		}
		
		if (linea != null) {
			// devuelve la linea leida
			return linea;
		} else {
			// Si la linea es "null" entonces estamos en el EOF
			throw new EOFException();
		}
	}
	

	@Override
	public void close() {
		if (this.bReader != null) {
			try {
				this.bReader.close();
			} catch (IOException e) {
				System.out.println(super.archivo + ": ERROR. Imposible cerrar archivo");
			}
		}
	}



}
