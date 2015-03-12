package lector;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class LectorArchivosTextoASCII extends LectorArchivosTexto {

	private DataInputStream dis;
	
	public LectorArchivosTextoASCII(String nombre) throws FileNotFoundException {
		super(nombre);
		this.dis = new DataInputStream(new FileInputStream(new File(nombre)));
	}
	
	@Override
	public String readLine() throws EOFException {
		String linea = "";
		try {
			int caracter = this.dis.readUnsignedByte();
			while (caracter != 0x0A) { 
				// El caracter 0x0A LF (Line Feed) marca tanto en Unix como Windows
				// una nueva linea
				if (caracter != 0x0D) {
					// Adjunta todos los caracteres distintos de 0x0D CR (Retorno de Carro)
					// Ya que este puede estar presente o no dependiendo de que sea Unix o Windows
					linea = linea + (char)caracter;
				}
				caracter = dis.readUnsignedByte();
			}
			
		} catch (EOFException e) {
			// Se ha llegado al final del fichero
			if (linea.length() != 0) {
				// Aun queda la ultima linea
				return linea;
			} else {
				// No quedan ya mas lineas por leer
				throw new EOFException();
			}
		} catch (IOException e) {
			System.out.println(super.archivo + ": ERROR. Imposible leer archivo");
			e.printStackTrace();
			return null;
		} 
		
		return linea;
	}

	@Override
	public void close() {
		if (this.dis != null) {
			try {
				this.dis.close();
			} catch (IOException e) {
				System.out.println(super.archivo + ": ERROR. Imposible cerrar archivo");
			}
		}
	}

}
