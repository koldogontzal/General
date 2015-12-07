package util;

import java.io.EOFException;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import lector.LectorArchivosTexto;

public class DeshacedorCodigoSecretoExamenes {
	// Deshace el código secreto de una prueba de examen, siguiendo la asociación entre
	// IdOpositor y NumeroSecreto definida en un Excel dado
	
	private final static String dirPad = "C:\\Users\\koldo\\Desktop\\Nueva carpeta";
	private static final String dirDes = "Ficheros desaleatorizados";
	private static final String nomList = "ListadoClaves.csv";
	
	private String directorioPadre;
	private String directorioDestino;	
	private String nombreListado;
	
	private final static String SEPARADOR_CELDAS = ";";
	
	private Map<Long, Long> claves = new HashMap<Long, Long>(); // NumeroSecreto e IdOpositor
	
	public DeshacedorCodigoSecretoExamenes() {
		this(dirPad, dirDes, nomList);
	}
	
	public DeshacedorCodigoSecretoExamenes(String dirPadre) {
		this(dirPadre, dirDes, nomList);
	}
	
	public DeshacedorCodigoSecretoExamenes(String dirPadre, String nomListado) {
		this(dirPadre, dirDes, nomListado);
	}
	
	public DeshacedorCodigoSecretoExamenes(String dirPadre, String dirDestino, String nomListado) {
		
		this.directorioPadre = dirPadre;
		this.directorioDestino = dirDestino;
		this.nombreListado = nomListado;
		
		// Carga el mapa de Claves
		boolean resultado = cargarMapaDeClaves();
		
		if (resultado) {
			// TODO seguir el algoritmo tras cargar las claves
			
			// Crear el directorio de destino 
			
			// Leer listado de archivos en el directorio padre
			
			// Buscar los que tienen nombre "RYxxxxx.eee(e)" y cambiarlo por "Yxxxxx.eee(e)"
			
			// Mover los nuevos ficheros al directorio de destino 
			
			

			
			
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
