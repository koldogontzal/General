package modif;

import com.koldogontzal.timestamp.IllegalTimeStampException;
import com.koldogontzal.timestamp.TimeStamp;

import apliGraf.Aplicacion;

public class ModificadorNombreArchivosTimeStamp {
	public static final int AGNADIR 			= 1;
	public static final int BORRAR 				= 2;
	public static final int DESPLAZAR_INICIO	= 3;
	public static final int MODIFICAR_VALOR 	= 4;
	public static final int MODIFICAR_FORMATO 	= 5;

	private Directorio directorio;
	private int accion;
	private Aplicacion app;
	
	public ModificadorNombreArchivosTimeStamp(String directorio, int accion, Aplicacion app) {
		this.directorio = new Directorio(directorio);
		this.accion = accion;
		this.app = app;

		if (!this.directorio.isDirectory()) {
			app.escribirLinea("ERROR: " + this.directorio
					+ " no existe como directorio");
		} else {
			this.analizar();
		}				
	}

	private void analizar() {
		long seg = 1000L * this.app.getSegundosAdelanto();	

		// Llamadas recursivas a los subdirectorios
		Directorio[] listadoDir = this.directorio.listDirectorios();
		for (Directorio dir : listadoDir) {
			new ModificadorNombreArchivosTimeStamp(dir.getPath(), this.accion, this.app);
		}
		// Analiza los archivos
		ArchivoTimeStamp[] listado = this.directorio.listArchivosTimeStamp();
		for (ArchivoTimeStamp archivo : listado) {
			if (this.accion == AGNADIR) {
				TimeStamp ts = archivo.obtainTimeStamp();
				ts.setPreferredFormat(this.app.getTimeStampFormatPreferred());
				if (archivo.añadirTimeStampAlNombre(ts)) {
					this.app.escribirLinea("OK:\tA\u00F1adiendo TimeStamp a " + archivo);
				} else {
					this.app.escribirLinea("ERROR:\tNo se pudo a\u00F1adir TimeStamp a " + archivo);
					System.out.println("ERROR:\tNo se pudo a\u00F1adir TimeStamp a " 	+ archivo);
				}
			}
			
			if (this.accion == BORRAR) {
				if (archivo.quitarTimeStampAlNombre()) {
					this.app.escribirLinea("OK:\tQuitando TimeStamp a " + archivo);
				} else {
					this.app.escribirLinea("ERROR:\tNo se pudo quitar TimeStamp a " + archivo);
					System.out.println("ERROR:\tNo se pudo quitar TimeStamp a " + archivo);
				}
			}
			
			// TODO: Añadir la acción DESPLAZAR_INICIO
			
			
			
			
			
			
			if (this.accion == MODIFICAR_VALOR) {
				try {
					if (archivo.modificarTimeStampDelNombre(seg)) {
						this.app.escribirLinea("OK:\tModificando TimeStamp a " + archivo);
					} else {
						this.app.escribirLinea("ERROR:\tNo se pudo modificar TimeStamp a " + archivo);
						System.out.println("ERROR:\tNo se pudo modificar TimeStamp a " + archivo);
					}
				} catch (IllegalTimeStampException e) {
					this.app.escribirLinea("ERROR:\tNo se pudo modificar TimeStamp a " + archivo);
					System.out.println("ERROR:\tNo se pudo modificar TimeStamp a " + archivo);
				}
			}
			
			if (this.accion == MODIFICAR_FORMATO) {
				try {
					if (archivo.modificarTimeStampFormatDelNombre(this.app.getTimeStampFormatPreferred())) {
						this.app.escribirLinea("OK:\tModificando el formato del TimeStamp a " + archivo);
					} else {
						this.app.escribirLinea("ERROR:\tNo se pudo modificar el formato de TimeStamp a " + archivo);
						System.out.println("ERROR:\tNo se pudo modificar el formato de TimeStamp a " + archivo);
					}
				} catch (IllegalTimeStampException e) {
					this.app.escribirLinea("ERROR:\tNo se pudo modificar el formato de TimeStamp a " + archivo);
					System.out.println("ERROR:\tNo se pudo modificar el formato de TimeStamp a " + archivo);
				}
			}
		}
	}
}
