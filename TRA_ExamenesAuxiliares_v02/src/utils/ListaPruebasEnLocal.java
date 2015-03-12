package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import escritor.EscritorArchivosTexto;

public class ListaPruebasEnLocal {

	private ArrayList<PruebaEnLocal> lista;
	private File dirBase;
	private ListaEjercicios listaEj;
	
	public ListaPruebasEnLocal(String dir, ListaEjercicios listaEj) {
		this.dirBase = new File(dir);
		this.listaEj = listaEj;
		
		if (this.dirBase.isDirectory()) {
			this.lista = new ArrayList<PruebaEnLocal>(2000);
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
				prueba.setEncontrado(this.listaEj.existe(prueba)); //prueba.getCodigoOpositor(), prueba.getIdentificativo(), prueba.getUsu()));
				prueba.setPruebaCoincidencia(this.listaEj.getUltimaCoincidencia());
				this.lista.add(prueba);
			}
		}
	}
	
	@Override
	public String toString() {	
		Iterator<PruebaEnLocal> i = this.lista.iterator();
		String ret = "PATH LOCAL;ARCHIVO;TAMAÑO;CORRESPONDENCIA;LLAMAMIENTO;USU;NOMBRE;TAMAÑO;TIENE COPIA;TIENE OFFICE 2003\n";
		int contador = 0;
		while (i.hasNext()) {
			contador++;
			if (contador % 200 == 0) System.out.println("Contador: " + contador++ + "/" + this.lista.size());			
			String ss = i.next().toString();
			ret = ret + ss + "\n";
		}	
		return ret;
	}
	
	public boolean grabarArchivo(File archivo) {	
		return EscritorArchivosTexto.createFile(archivo.getPath(), this.toString());
	}
	
}
