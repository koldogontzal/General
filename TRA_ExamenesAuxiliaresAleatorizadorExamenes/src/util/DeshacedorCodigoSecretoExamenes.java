package util;

import java.io.EOFException;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lector.LectorArchivosTexto;

public class DeshacedorCodigoSecretoExamenes {
	// Deshace el código secreto de una prueba de examen, siguiendo la asociación entre
	// IdOpositor y NumeroSecreto definida en un Excel dado
	
	private final static String dirPadreDefault = "C:\\Users\\lcastellano\\Desktop\\Pruebas GESOPO totales\\Desaleatorizar";
	private static final String dirDestinoDefault = "Ficheros desaleatorizados";
	private static final String nomListadoDefault = "ListadoClaves.csv";
	
	private String directorioPadre;
	private String directorioDestino;	
	private String nombreListado;
	
	private final static String SEPARADOR_CELDAS = ";";
	
	private Map<Long, Long> claves = new HashMap<Long, Long>(); // NumeroSecreto e IdOpositor
	
	public DeshacedorCodigoSecretoExamenes() {
		this(dirPadreDefault, dirDestinoDefault, nomListadoDefault);
	}
	
	public DeshacedorCodigoSecretoExamenes(String dirPadre) {
		this(dirPadre, dirDestinoDefault, nomListadoDefault);
	}
	
	public DeshacedorCodigoSecretoExamenes(String dirPadre, String nomListado) {
		this(dirPadre, dirDestinoDefault, nomListado);
	}
	
	public DeshacedorCodigoSecretoExamenes(String dirPadre, String dirDestino, String nomListado) {
		
		this.directorioPadre = dirPadre;
		this.directorioDestino = dirDestino;
		this.nombreListado = nomListado;
		
		// Carga el mapa de Claves
		boolean resultado = cargarMapaDeClaves();
		
		if (resultado) {
			
			// Crear el directorio de destino 
			File nuevoDirectorio = new File(this.directorioPadre + "\\" + this.directorioDestino);
			if (!nuevoDirectorio.exists()) {
				nuevoDirectorio.mkdir();
				
				// Leer listado de archivos en el directorio padre, sólo los que tienen formato "RYxxxxx.eee(e)"
				File[] listadoCompletoArchivos = (new File(this.directorioPadre)).listFiles();
				ArrayList<File> listadoSeleccionado = new ArrayList<File>(200);
				for (File archivo:listadoCompletoArchivos) {
					String nombre = archivo.getName();
					if (nombre.matches("R[A-C][0-9][0-9][0-9][0-9][0-9].*")) {
						listadoSeleccionado.add(archivo);
					}
				}
				
				
				// Buscar los que tienen nombre "RYxxxxx.eee(e)" y cambiarlo por "Yxxxxx.eee(e)"
				for (File archivo:listadoSeleccionado) {
					String nombre = archivo.getName();
					String letra = nombre.substring(1, 2);
					String codigoSecreto = nombre.substring(2, 7);
					String extension = nombre.substring(7, nombre.length());
					
					Long idOpositorLong = this.claves.get(new Long(codigoSecreto));
					
					String idOpositor = "0000" + idOpositorLong;
					idOpositor = idOpositor.substring(idOpositor.length() - 5);
					
					// Mover los nuevos ficheros al directorio de destino con el IdOpositor
					File nombreDestino = new File(archivo.getParent() + File.separator + this.directorioDestino +
							File.separator + letra + idOpositor + extension);
					archivo.renameTo(nombreDestino);
					
				}
								
				System.out.println("Pruebas desaleatorizadas en \"" + nuevoDirectorio.getPath() + "\"");
				
			} else {
				System.out.println("Ya existe el directorio \"" + nuevoDirectorio.getPath() + 
						"\".\nBórrelo y vuelva a ejecutar la aplicación.");
			} 
				
			System.out.println("Fin de la ejecución");
		}
		
	}
	
	private boolean cargarMapaDeClaves() {
		File fileListado = new File(this.directorioPadre + File.separator + this.nombreListado);
		if (fileListado.exists()) {			
			// Lee el archivo
			LectorArchivosTexto lec = LectorArchivosTexto.open(fileListado.getPath(), LectorArchivosTexto.MODO_UTF8);
			// Línea a línea
			try {
				do {
					String fila = lec.readLine();
					String [] celdas = fila.split(SEPARADOR_CELDAS);
					if (celdas.length == 2) {
						// el primer valor es el NumeroSecreto, el segundo es el IdOpositor
						Long numSecreto = new Long(celdas[0]);
						Long idOpositor = new Long(celdas[1]);
						
						this.claves.put(numSecreto, idOpositor);
						
					} else {
						// NO se trata de una fila con dos valores numéricos separados por un SEPARADOR_CELDAS
						System.out.println("La línea \"" + fila + "\" no se puede parsear por una pareja NumeroSecreto//IdOpositor");
					}
				} while (true);
			} catch (EOFException e) {
				// Fin del archivo
				return true;
			}
		} else {
			return false;
		}
		
	}

}
