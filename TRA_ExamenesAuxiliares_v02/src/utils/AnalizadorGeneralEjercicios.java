package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import escritor.EscritorArchivosTexto;

public class AnalizadorGeneralEjercicios {
	
	private File dirPpal;
	private ListaOpositores listaOpositores;
	private ListaEjercicios listaEjercicios;
	private ListaPruebasEnLocal listaPruebasEnLocal;
	private ArrayList<Ejercicio> listadoEjSinOpositor;
	
	
	public AnalizadorGeneralEjercicios(String dirPpal, String archAnalisis, String archEjSinOpositor) {
		this.dirPpal = new File(dirPpal);
		if (this.dirPpal.isDirectory()) {
			// Lee la lista de opositores
			System.out.println("Leyendo lista de opositores...");
			this.listaOpositores = new ListaOpositores(dirPpal + File.separator + "lista.txt");
			
			// Lee la lista de ejercicios del servidor
			System.out.println("Leyendo el listado de ejercicios del servidor...");
			this.listaEjercicios = new ListaEjercicios(dirPpal + File.separator + "Servidor");
			
			// Genera un archivo CSV de salida
			System.out.println("Grabando archivo " + archAnalisis + " de resultados...");
			this.listaEjercicios.grabarArchivo(new File(dirPpal + File.separator + archAnalisis));
			
			// Asocia a cada opositor un listado de ejercicios
			System.out.println("Asociando ejercicios a cada opositor...");
			this.asociarEjerciciosAOpositor(archEjSinOpositor);
		
			// Lee el directorio en Local
			System.out.println("Leyendo las copias grabadas en local...");
			this.listaPruebasEnLocal = new ListaPruebasEnLocal(dirPpal + File.separator + "Local",
					this.listaEjercicios);

			this.listaPruebasEnLocal.grabarArchivo(new File(dirPpal + File.separator + "ArchivoLOCAL.csv"));
			//System.out.println(this.listaPruebasEnLocal);
			
			
			System.out.println("Terminado.");
		} else {
			System.out.println(dirPpal + " no es un directorio valido.");
		}
		
	}


	private void asociarEjerciciosAOpositor(String arch) {
		if (this.listaEjercicios.isListadoCorrecto()) {
			ArrayList<Ejercicio> listado = this.listaEjercicios.getListado();
			Iterator<Ejercicio> i = listado.iterator();
			this.listadoEjSinOpositor = new ArrayList<Ejercicio>(100);
			
			while (i.hasNext()) {
				Ejercicio ej = i.next();
				String strOpositor = ej.getOpositor();
				if (strOpositor != null) {
					Opositor opositor = this.listaOpositores.buscarPorCodExamen(strOpositor);
					opositor.añadirEjercicioServidor(ej);
				} else {
					// Genera el listado de ejercicios no nulos sin un opositor claro					
					if (!ej.isEjercicioVacio()) {
						this.listadoEjSinOpositor.add(ej);
					}
				}
			}
			
			// General el archivo de Ejercicios sin opositor
			String ret = "";
			Iterator<Ejercicio> ieso = this.listadoEjSinOpositor.iterator();
			while (ieso.hasNext()) {
				Ejercicio ej = ieso.next();
				ret = ret + ej + "\n=============================================================\n\n";
			}
			EscritorArchivosTexto.createFile(this.dirPpal.getPath() + File.separator + arch, ret);			
		}
		
		
	}

}
