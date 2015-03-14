package modif;

import com.koldogontzal.timestamp.IllegalTimeStampException;
import com.koldogontzal.timestamp.TimeStamp;
import com.koldogontzal.timestamp.TimeStampFormat;

import apliGraf.Aplicacion;

public class ModificadorNombreArchivosTimeStamp {
	public static int AGNADIR = 1;
	public static int BORRAR = 2;
	public static int MODIFICAR = 3;

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
				TimeStamp ts = archivo.getTimeStamp();
				if (archivo.añadirTimeStampAlNombre(ts, TimeStampFormat.yyyyMMdd_HHmmss)) {
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
			
			if (this.accion == MODIFICAR) {
				try {
					if (archivo.modificarTimeStampDelNombre(seg, TimeStampFormat.yyyyMMdd_HHmmss)) {
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
		}
	}
}
