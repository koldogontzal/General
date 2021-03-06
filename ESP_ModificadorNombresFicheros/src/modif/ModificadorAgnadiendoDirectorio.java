package modif;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import com.koldogontzal.timestamp.IllegalTimeStampException;
import com.koldogontzal.timestamp.TimeStamp;

import apliGraf.Aplicacion;

public class ModificadorAgnadiendoDirectorio {

	private List<File> originales;
	private List<File> modificados;
	
	private File directorioBase;
	
	private Aplicacion app;
	
	public ModificadorAgnadiendoDirectorio (String directorio, Aplicacion app){
		this.app = app;
		this.directorioBase = new File(directorio);
		this.originales = new ArrayList<File>(50);
		this.modificados = new ArrayList<File>(50);
		
		if (!this.directorioBase.isDirectory()) {
			// Si el File no es un directorio, elige su padre como directorio de partida
			this.directorioBase = new File(this.directorioBase.getParent());
		}
		// Busca los originales y los guarda en la lista "originales"
		this.buscarOriginales();
		
		// Crea los modificados a partir de la lista de originales:		
		// Define opciones de botones personalizadas
		Object[] opciones = {"Delante", "Detrás", "Cancelar"};			
		// Muestra un dialogo y pregunta
		int respuesta = JOptionPane.showOptionDialog(this.app, "¿Dónde quieres añadir el nombre del directorio?", "Posición del directorio", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[1]);
		if (respuesta!=2) {
			// Si no se ha cancela la acción, crea los modificados e inicia el proceso de renombramiento
			this.crearModificados(respuesta);
		
			// Inicia el proceso de renombrado
			this.iniciarProceso();
		}
	}

	private void crearModificados(int posicion) {
		// posicion = 0 ADELANTE
		// posicion = 1 ATRAS
		
		Iterator<File> it = this.originales.iterator();
		while (it.hasNext()) {
			File original = (File) it.next();
			String nombre = original.getName();
			String padre = (new File(original.getParent())).getName();
			int posPunto = nombre.lastIndexOf('.');
			String nombreNuevo;
			try {
				@SuppressWarnings("unused")
				TimeStamp ts = TimeStamp.fromString(padre);
				// Se ejecuta este código si el nombre del directorio padre es un TimeStamp
				// En este caso, se añade al principio
				nombreNuevo = padre + "_" + nombre.substring(0, posPunto) + "." + nombre.substring(posPunto + 1);
			} catch (IllegalTimeStampException e) {
				// Se ejecuta este código si el nombre del directorio padre NO es un TimeStamp
				// En este caso se pregunta qué hacer, si añadir al principio o al final
				
				
				if (posicion == 0) {
					// En este caso, se añade al principio
					nombreNuevo = padre + "_" + nombre.substring(0, posPunto) + "." + nombre.substring(posPunto + 1);
					
				} else if (posicion == 1) {
					// En este caso, se añade al final
					nombreNuevo = nombre.substring(0, posPunto) + "_" + padre + "." + nombre.substring(posPunto + 1);

				} else {
					// Por defecto elegimos que se añada al final
					nombreNuevo = nombre.substring(0, posPunto) + "_" + padre + "." + nombre.substring(posPunto + 1);
				}
			}
			File modificado = new File (this.directorioBase.getPath() + File.separator + nombreNuevo);
			this.modificados.add(modificado);
		}
	}

	private void buscarOriginales() {
		File[] listadoDirectorios = this.directorioBase.listFiles();
		for (File directorio : listadoDirectorios) {
			if (directorio.isDirectory()) {
				File[] listadoArchivos = directorio.listFiles();
				for (File archivo : listadoArchivos) {
					if (archivo.isFile()) {
						this.originales.add(archivo);
					}
				}
			}
		}
	}

	private void iniciarProceso() {
		this.app.escribirLinea("Directorio base: " + this.directorioBase.getPath() + "\n\n");
		this.app.escribirLinea("Originales:");
		this.mostrarGeneral(this.originales);
		this.app.escribirLinea("\nModificados:");
		this.mostrarGeneral(this.modificados);
		
		this.cambiarNombre();
	}
	
	private void mostrarGeneral(List<File> listado) {
		Iterator<File> it=listado.iterator();
		while (it.hasNext()) {
			this.app.escribirLinea(((File)it.next()).getPath());
		}
	}
	
	public void cambiarNombre () {
		Iterator<File> itO = this.originales.iterator();
		Iterator<File> itM = this.modificados.iterator();
		while (itO.hasNext()) {
			File ori = (File)itO.next();
			File mod = (File)itM.next();
			
			ori.renameTo(mod);
		}
	}
}
