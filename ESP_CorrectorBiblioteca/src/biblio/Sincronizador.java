package biblio;

import java.io.File;


public class Sincronizador {
	
	private static final int NUMERO_IDS = 4;
	
	public static final int ID_INAP = 0;
	public static final int ID_CASA_ULISES_W7 = 1;
	public static final int ID_CASA_ULISES_W8 = 2;
	public static final int ID_CASA_TELEMACO_W7 = 3;
	


	public static final int ID_LOCALIZACION_DESCONOCIDA = -1;

	public static final String[] RUTAS_BIB = {
			"D:\\Mis Documentos\\GoogleDrive3\\Google Drive\\Bibliotheka",
			"J:\\Luis\\Documentos\\Google Drive\\Bibliotheka",
			"C:\\Users\\Koldo\\Google Drive\\Bibliotheka",
			"J:\\Luis\\Documents\\Google Drive\\Bibliotheka" };

	public static final String[] FICH_EXPORT = { 
			"BibliotecaINAP.xml",
			"BibliotecaCASA-W7.xml",
			"BibliotecaCASA-W8.xml",
			"BibliotecaTELEMACO-W7.xml" };
	
	public static final String[] FICH_BACKUP = { 
			"BibliotecaINAP.old",
			"BibliotecaCASA-W7.old",
			"BibliotecaCASA-W8.old",
			"BibliotecaTELEMACO-W7.old" };
	
	private static final String[] LOCALIZACION = { 
			"INAP",
			"CASA-ULISES-W7",
			"CASA-ULISES-W8",
			"CASA-TELEMACO-W7" };

	private int local;
	private int modelo;
	
	public Sincronizador() {
		// Sincroniza con el modelo más nuevo.
		/*
		 * En primer lugar intenta descubrir si se está ejecutando en casa o en
		 * el INAP para definir qué fichero de biblioteca actuará como modelo y
		 * cual será el directorio local
		 */	
		File pruebaINAP = new File(RUTAS_BIB[ID_INAP]);
		File pruebaCASA_UW7 = new File(RUTAS_BIB[ID_CASA_ULISES_W7]);
		File pruebaCASA_UW8 = new File(RUTAS_BIB[ID_CASA_ULISES_W8]);
		File pruebaCASA_TW7 = new File(RUTAS_BIB[ID_CASA_TELEMACO_W7]);
		
		// Calcula el local, en qué ordenador se está ejecutando el programa
		if (pruebaINAP.isDirectory() && !pruebaCASA_UW7.isDirectory() && !pruebaCASA_UW8.isDirectory() && !pruebaCASA_TW7.isDirectory()) {
			// El objeto se está ejecutando en el ordenador del INAP
			this.local = ID_INAP;
		} else if (!pruebaINAP.isDirectory() && pruebaCASA_UW7.isDirectory() && !pruebaCASA_UW8.isDirectory() && !pruebaCASA_TW7.isDirectory()) {
			// El objeto se está ejecutando en el ordenador de CASA-ULISES-W7
			this.local = ID_CASA_ULISES_W7;
		} else if (!pruebaINAP.isDirectory() && pruebaCASA_UW7.isDirectory() && pruebaCASA_UW8.isDirectory() && !pruebaCASA_TW7.isDirectory()) {
			// El objeto se está ejecutando en el ordenador de CASA-ULISES-W8
			// En este caso, ambos directorios existen (En J: y en este caso en C:)
			// De estar en W7, únicamente existiría el de J:
			this.local = ID_CASA_ULISES_W8;
		} else if (!pruebaINAP.isDirectory() && !pruebaCASA_UW7.isDirectory() && !pruebaCASA_UW8.isDirectory() && pruebaCASA_TW7.isDirectory()) {
			// El objeto se está ejecutando en el portátil de CASA-TELEMACO-W7
			this.local = ID_CASA_TELEMACO_W7;
		} else {
			// No se está ejecutando en ninguno de los sitios conocidos
			this.local = ID_LOCALIZACION_DESCONOCIDA;
			this.modelo = ID_LOCALIZACION_DESCONOCIDA;
		}
		
		
		// Calcula el modelo, es decir, de las otras localizaciones, 
		// aquella con la fecha más moderna
		if (this.esEntornoConocido()) {
			if (this.local == 0) {
				// local es la primera opción, con lo que el modelo tiene que ser 1 ó los siguientes
				this.modelo = 1;
			} else {
				// local no es la primera opción, con lo que el modelo tiene que empezar a buscarse desde 0
				this.modelo = 0;
			}
			
			for (int i = this.modelo; i < NUMERO_IDS; i++) {
				if (i != this.local) {
					File fModelo = new File(FICH_EXPORT[this.modelo]);
					File fComparar = new File(FICH_EXPORT[i]);
					
					/*
					Date fecha1 = new Date(fModelo.lastModified());
					Date fecha2 = new Date(fComparar.lastModified());
					System.out.print("("+fModelo.lastModified()+") " + fecha1 + " < ("+ fComparar.lastModified() +") "+ fecha2 + " ? ");
					*/
					
					if (fModelo.lastModified() < fComparar.lastModified()) {
						this.modelo = i;
						//System.out.println("Sí");
					} else {
						//System.out.println("No");
					}
				}
			}
		}
		
		//System.out.println("Modelo: " + this.modelo);
		
	}
	
	public Sincronizador(int modelo) {
		// Sincroniza con un modelo determinado
		this();
		this.modelo = modelo;
	}
	
	public void sincronizar() {
		/*
		 * Una vez aclarado donde se está ejecutando, actualiza la biblioteca
		 * local usando el modelo. Esta es la función que hay que ejecutar
		 * para sincronizar efectivamente la biblioteca
		 */
	
		if (this.esEntornoConocido()) { // comprueba que está en el INAP o en CASA
			// Lee el modelo
			System.out.println("LEYENDO EL ARCHIVO CON EL MODELO: " + FICH_EXPORT[this.modelo] + "...");
			Biblioteca modeloBib = Biblioteca.importarFichero(FICH_EXPORT[this.modelo]);

			// Hace una copia de seguridad de la Biblioteca local
			System.out.println("HACIENDO COPIA DE SEGURIDAD DE LA BIBLIOTECA LOCAL: " + FICH_BACKUP[this.local] + "...");
			Biblioteca localBib = new Biblioteca(RUTAS_BIB[this.local]);
			localBib.exportarFichero(FICH_BACKUP[this.local]);

			// Actualiza la biblioteca local usando el modelo
			System.out.println("ACTUALIZANDO LOS ID DE LA BIBLIOTECA LOCAL USANDO EL MODELO " + LOCALIZACION[this.modelo] +"...");
			ActualizadorIDBiblioteca actualizador = new ActualizadorIDBiblioteca(
					RUTAS_BIB[this.local], modeloBib);
			
			// Actualiza el fichero de la Biblioteca local
			System.out.println("ACTUALIZANDO EL FICHERO DE LA BIBLIOTECA LOCAL: " + FICH_EXPORT[this.local] + "...");
			localBib = new Biblioteca(RUTAS_BIB[this.local]);
			localBib.exportarFichero(FICH_EXPORT[this.local]);
			
			// Imprime información en pantalla
			System.out.println("LIBROS EN LOCAL SIN ID:\n" + actualizador.getLibrosLocalSinId());
			System.out.println();
			System.out.println("LIBROS DEL MODELO SIN EQUIVALENTE EN LOCAL:\n" + actualizador.getLibrosModeloSinUsar());
		}
	}
	
	public boolean esEntornoConocido() {
		return (this.local != Sincronizador.ID_LOCALIZACION_DESCONOCIDA);
	}
	
	public int getLocal() {
		return this.local;
	}
	
	public String getNombreLocal() {
		if (this.esEntornoConocido()) {
			return LOCALIZACION[this.getLocal()];
		} else {
			return "DESCONOCIDA";
		}		
	}
}
