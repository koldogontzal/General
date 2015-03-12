package subtitulos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class LectorDeArchivosUTF8 extends LectorDeArchivos {

	private static final long serialVersionUID = -8185559428678365906L;

	public LectorDeArchivosUTF8(String nombre) {
		super(nombre);
	}

	@Override
	protected int leerArchivo() {
		BufferedReader bReader = null;
		try {
			bReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(super.nombreArchivo))));
			
			String linea;
			while ((linea = bReader.readLine()) != null) {
				super.add(linea);
			}
			
		} catch (FileNotFoundException e) {
			return FICHERO_NO_ENCONTRADO;
			
		} catch (IOException e) {
			return IMPOSIBLE_LEER_FICHERO;
			
		} finally {
			if (bReader != null) {
				try {
					bReader.close();
				} catch (IOException e) {
					return ERROR_CERRANDO_FICHERO;
				}
			}
		}
	
	return OK;
	}

}
