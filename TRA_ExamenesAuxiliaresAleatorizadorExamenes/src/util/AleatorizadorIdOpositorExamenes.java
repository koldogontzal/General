package util;

import java.io.File;
import java.util.ArrayList;

public class AleatorizadorIdOpositorExamenes {
	
	// Modifica aleatoriamente el IdOpositor de todos los ficheros contenidos en un directorio definido,
	// empezando a partir de un IdOpositor inicial dado.
	// Si se da una letra, sólo se modifica el Id de las pruebas con dicha letra.
	// Si se dan dos letras de prueba, modifica la letra de las pruebas con dicha letra.
	
	private final String directorio = "C:\\Users\\koldo\\Desktop\\Nueva carpeta";
	private final int codigoInicial = 0;
	private final String nombreDestino = "Ficheros aleatorizados";
	
	private String letra;
	private ArrayList<File> listaArchivos;
	
	public AleatorizadorIdOpositorExamenes (String letra) {
		this(letra, letra);
	}
	
	public AleatorizadorIdOpositorExamenes(String letraPruebas, String nuevaLetra) {
		this.letra = nuevaLetra;

		File[] listado = (new File(this.directorio)).listFiles();
		this.listaArchivos = new ArrayList<File>(200);
		for (File archivo:listado) {
			String nombre = archivo.getName();
			if (nombre.matches(letraPruebas + "[0-9][0-9][0-9][0-9][0-9].*")) {
				this.listaArchivos.add(archivo);
			}
		}
		
		
		if (this.listaArchivos.size() != 0) {
			// Crear nuevo directorio
			File nuevoDirectorio = new File(this.directorio + "\\" + nombreDestino);
			if (!nuevoDirectorio.exists()) {
				nuevoDirectorio.mkdir();
				
				copiarAleatoriamenteArchivos();
				
				System.out.println("Pruebas aleatorizadas en \"" + nuevoDirectorio.getPath() + "\"");
			} else {
				System.out.println("Ya existe el directorio \"" + nuevoDirectorio.getPath() + 
						"\".\nBórrelo y vuelva a ejecutar la aplicación.");
			}
		}

	}
	
	private void copiarAleatoriamenteArchivos() {
		int idExamen = this.codigoInicial;
		
		while (!this.listaArchivos.isEmpty()) {
			// Elige uno al azar
			int posicion = (int)(Math.random() * (double)this.listaArchivos.size());
			File elegido = this.listaArchivos.remove(posicion);
			
			// Lo mueve al nuevo directorio y lo renombra
			int posExtension = elegido.getName().indexOf(".");
			int tamNombre = elegido.getName().length();
			String extension = elegido.getName().substring(posExtension, tamNombre);
			
			String idExamenString = "00000" + idExamen;
			idExamenString = idExamenString.substring(idExamenString.length() - 5, idExamenString.length());
			
			File nuevoNombre = new File(this.directorio + File.separator + this.nombreDestino + File.separator +
					this.letra + idExamenString + extension);
			elegido.renameTo(nuevoNombre);
			
			idExamen++;
			
		}
		
	}

}
