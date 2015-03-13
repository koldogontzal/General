package modif;

import apliGraf.Aplicacion;

public class ModificadorNombreArchivosFotos {
	public static int AGNADIR = 1;
	public static int BORRAR = 2;
	public static int MODIFICAR = 3;

	private Directorio directorio;
	private int accion;
	private Aplicacion app;
	
	public ModificadorNombreArchivosFotos(String directorio, int accion, Aplicacion app) {
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
			new ModificadorNombreArchivosFotos(dir.getPath(), this.accion, this.app);
		}
		// Analiza los archivos
		ArchivoFoto[] listado = this.directorio.listArchivosFoto();
		for (ArchivoFoto archivo : listado) {
			if (this.accion == AGNADIR) {
				if (archivo.agnadirMarcaDeTiempoAlNombre()) {
					this.app.escribirLinea("OK:\tA\u00F1adiendo MarcaDeTiempo a " + archivo);
				} else {
					this.app.escribirLinea("ERROR:\tNo se pudo a\u00F1adir MarcaDeTiempo a " + archivo);
					System.out.println("ERROR:\tNo se pudo a\u00F1adir MarcaDeTiempo a " 	+ archivo);
				}
			}
			
			if (this.accion == BORRAR) {
				if (archivo.quitarMarcaDeTiempoAlNombre()) {
					this.app.escribirLinea("OK:\tQuitando MarcaDeTiempo a " + archivo);
				} else {
					this.app.escribirLinea("ERROR:\tNo se pudo quitar MarcaDeTiempo a " + archivo);
					System.out.println("ERROR:\tNo se pudo quitar MarcaDeTiempo a " + archivo);
				}
			}
			
			if (this.accion == MODIFICAR) {
				if (archivo.modificarMarcaDeTiempoDelNombre(seg)) {
					this.app.escribirLinea("OK:\tModificando MarcaDeTiempo a " + archivo);
				} else {
					this.app.escribirLinea("ERROR:\tNo se pudo modificar MarcaDeTiempo a " + archivo);
					System.out.println("ERROR:\tNo se pudo modificar MarcaDeTiempo a " + archivo);
				}
			}
		}
	}
}
