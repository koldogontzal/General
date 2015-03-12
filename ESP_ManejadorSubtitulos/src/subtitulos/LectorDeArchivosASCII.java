package subtitulos;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class LectorDeArchivosASCII extends LectorDeArchivos {

	private static final long serialVersionUID = -6077473019927421152L;

	public LectorDeArchivosASCII(String nombre) {
		super(nombre);
	}

	@Override
	protected int leerArchivo() {
		DataInputStream dis = null;
		try {
			dis = new DataInputStream(new FileInputStream(new File(super.nombreArchivo)));
			
			while (true) {
				int caracter = dis.readUnsignedByte();
				String linea = "";
				while (caracter != 10) {
					if (caracter != 13) {
						linea = linea + (char)caracter;
					}
					caracter = dis.readUnsignedByte();
				}
				super.add(linea);
			}
			
		} catch (FileNotFoundException e) {
			return FICHERO_NO_ENCONTRADO;
			
		} catch (EOFException e) {
			return OK;
			
		} catch (IOException e) {
			return IMPOSIBLE_LEER_FICHERO;
			
		} finally {
			if (dis != null) {
				try {
					dis.close();
				} catch (IOException e) {
					return ERROR_CERRANDO_FICHERO;
				}
			}
		}
	}

}
