package main;

import java.io.File;

import utils.FileExtendido;
import utils.ListadoArchivosBackup;
import utils.MarcaDeTiempoDeFichero;

public class AutomatizadorBackups {
	
	private File archivo;
	private File dirBackups;
	
	public AutomatizadorBackups(File f, String dirBackups) {
		if (f.isFile()) {
			this.archivo = f;			
			this.dirBackups = new File(f.getParent() + File.separator + dirBackups);
			if (!this.dirBackups.exists()) {
				// Si no existe el directorio de backup, lo crea
				this.dirBackups.mkdir();
			}
					
		} else {

			System.out.println("ERROR: el archivo " + f.getPath()
					+ " no existe o no es un archivo.");

		}
	}

	public void hacerCopia(int maxVer, int minVer,
			MarcaDeTiempoDeFichero fechMaxAntig) {

		System.out.println("Haciendo copia de seguridad de " + this.archivo	+ " en el directorio " + this.dirBackups);
		ListadoArchivosBackup listado = new ListadoArchivosBackup(this.dirBackups);
		listado.add(new FileExtendido(this.archivo));

		System.out.println("\tParametros: #minCopias=" + minVer + " #maxCopias="
						+ maxVer + " limiteAntiguedad=" + fechMaxAntig + "\n");
		listado.delimitarNumeroVersiones(maxVer, minVer, fechMaxAntig);
	}
	
	public static void main(String[] args) {
		String error = "ERROR: Los parametros no son correctos\n\nAutomatizadorBackups " +
			"archivo [-m minVer] [-M maxVer] [-F fechAntig] [-D directorio]\n\n" + 
			"\tarchivo\t\tRuta del archivo a copiar.\n" + 
			"\t-m minVer\tNumero minimo de versiones antiguas de seguridad. Por defecto \"1\".\n" + 
			"\t-M maxVer\tNumero maximo de versiones antiguas de seguridad. Por defecto \"30000\".\n" + 
			"\t-F fechAntig\tFecha maxima de las versiones antiguas de seguridad. Formato AAAAMMDDhhmm. Por defecto \"190001010000\".\n" + 
			"\t-D directorio\tNombre del directorio de las copias de seguridad. Estara en el mismo directorio que el archivo. Por defecto \"Backups\"\n";
		File archivoCopiar;
		int maxVer = 30000;
		int minVer = 1;
		MarcaDeTiempoDeFichero fechMaxAntig = new MarcaDeTiempoDeFichero("190001010000");
		String dirBackups = "Backups";

		// Comprueba que existan argumentos de entrada
		if (args.length == 0) {
			System.out.println(error);
		} else {
			// Define el fichero a copiar
			archivoCopiar = new File(args[0]);
						
			// comprueba si hay más argumentos de entrada
			if (args.length != 1) {
				// el resto de argumentos tiene que ir en bloques de 2 en 2, con lo que el total es impar
				if ((args.length % 2) == 0) {
					System.out.println(error);
				} else {
					//Va leyendo argumentos hasta que termina todos
					int numArg = 1;
					while (args.length > (numArg * 2 - 1)) {
						String parametro = args[numArg * 2 - 1];
						String valor = args[numArg * 2];
						if  (parametro.equals("-M")) {
							maxVer = Integer.parseInt(valor);
						} else if (parametro.equals("-m")) {
							minVer = Integer.parseInt(valor);
						} else if (parametro.equals("-F")) {
							fechMaxAntig = new MarcaDeTiempoDeFichero(valor);
						} else if (parametro.equals("-D")) {
							dirBackups = valor;
						} else {
							System.out.println("Parámetro " + parametro + " no válido");
						}
						numArg++;
					}	
				}
			}
			
			// Crea el objeto replicador		
			AutomatizadorBackups a = new AutomatizadorBackups(archivoCopiar, dirBackups);
			a.hacerCopia(maxVer, minVer, fechMaxAntig);
		}
	}

}
