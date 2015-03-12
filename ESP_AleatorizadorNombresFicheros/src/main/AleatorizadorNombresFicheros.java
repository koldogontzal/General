package main;

import java.io.File;

public class AleatorizadorNombresFicheros {
	
	private File directorio;
	private int numMax;
	private int numMaxPosiciones;
	
	public AleatorizadorNombresFicheros(String dir) {
		this(dir, 1000, 3);
	}
	
	public AleatorizadorNombresFicheros(String dir, int numMax, int numMaxPosiciones) {
		this.directorio = new File(dir);
		if (!this.directorio.isDirectory()) {
			System.out.println("ERROR: el directorio indicado no es un directorio válido.");
		}
		this.numMax = numMax;
		this.numMaxPosiciones = numMaxPosiciones;
	}
	
		
	public boolean aleatorizar() {
		
		if (this.directorio.isDirectory()) {
			
			boolean todoOk = true;
			
			File [] listado = this.directorio.listFiles();
			
			for (File archivo:listado) {
				String	extension;
				String 	nuevoNombre;
				File	nuevoFile;
				int		numAleat;
				
				boolean numLibreEncontrado = false;
				do {
					numAleat = (int)(Math.random() * (double)this.numMax);
					nuevoNombre = this.getDerecha("00000000000000000000000000000000000000" + numAleat, this.numMaxPosiciones);
				
					extension = this.getDerecha(archivo.getPath(), archivo.getPath().length() - archivo.getPath().lastIndexOf(".") - 1);
					nuevoFile = new File(archivo.getParent() + File.separator + nuevoNombre + "." + extension);
					
					if (!nuevoFile.exists()) {
						todoOk = todoOk && archivo.renameTo(nuevoFile);
						numLibreEncontrado = true;
					}					
				} while (!numLibreEncontrado);			
			}			
			return todoOk;
			
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
		AleatorizadorNombresFicheros anf = new AleatorizadorNombresFicheros("C:\\Users\\Luis\\Desktop\\fotocompilacion\\fotosDesordenadas", 10000, 4);
		System.out.println("Aleatorizacion correcta: " + anf.aleatorizar());
	}
	
	private String getDerecha(String s, int num) {
		return s.substring(s.length() - num, s.length());		
	}

}
