package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class ListaPruebasEnLocal {

	private ArrayList<PruebaEnLocal> lista;
	private File dirBase;
	private ListaEjercicios listaEj;
	
	public ListaPruebasEnLocal(String dir, ListaEjercicios listaEj) {
		this.dirBase = new File(dir);
		this.listaEj = listaEj;
		
		if (this.dirBase.isDirectory()) {
			this.lista = new ArrayList<PruebaEnLocal>(1000);
			// Leer todas la pruebas
			this.leerRecursivo(this.dirBase);
		}
	}
	
	private void leerRecursivo(File dir) {
		File[] archivosDelDirectorio = dir.listFiles();
		
		for (File archivo : archivosDelDirectorio) {
			if (archivo.isDirectory()) {
				// Es un directorio, hace llamada recursiva para buscar los archivos dentro
				this.leerRecursivo(archivo);
			} else {
				// Es un archivo: hay que añadirlo al listado
				PruebaEnLocal prueba = new PruebaEnLocal(archivo);
				prueba.setEncontrado(this.listaEj.existe(prueba
						.getCodigoOpositor(), prueba.getIdentificativo()));
				this.lista.add(prueba);
			}
		}
	}
	
	@Override
	public String toString() {
		Iterator<PruebaEnLocal> i = this.lista.iterator();
		String ret = "";
		while (i.hasNext()) {
			ret = ret + i.next() + "\n";
		}
		return ret;
	}
	
}
