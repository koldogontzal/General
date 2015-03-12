package main;

import except.NotCarpetaException;
import utils.Carpeta;

public class Main {

	public static void main(String[] args) {
		// Primer argumento: el directorio a analizar
		// Segundo argumento: "0" para listado compacto, otro valor para Extendido
		
		if ((args == null) || (args.length == 0)) {
			args = new String[2];
			args[0] = "C:\\dell";
			args[1] = "2";
		}
		
		try {
			Carpeta c = new Carpeta(args[0], 1);
			System.out.println(c.toString());

		} catch (NotCarpetaException e) {
			System.err.println(e.getMessage());			
		}
		
				
	}

}
