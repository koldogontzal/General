package utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class ListadoArchivosBackup extends ArrayList<FileExtendido> {

	private static final long serialVersionUID = -2310994465246843826L;
	
	private File dirBackup;
	
	private int minVer;
	
	public ListadoArchivosBackup(File dirBackup) {
		super(20);
		this.dirBackup = dirBackup;
		
		File[] listado = this.dirBackup.listFiles();
		for (File archivo:listado) {
			super.add(new FileExtendido(archivo));
		}
		
		// Ordena el listado
		 Collections.reverse(this);
		
	}
	
	@Override
	public String toString() {
		Collections.sort(this);
		Iterator<FileExtendido> i = this.iterator();
		
		String ret = "";
		while (i.hasNext()) {
			ret = ret + i.next().getName() + "\n";
		}		
		return ret;
	}
	
	@Override
	public boolean add(FileExtendido arg0) {

		try {
			if (this.haHabidoCambiosUltimoBackup(arg0)) {

				FileExtendido nuevo;
				
				// Si el archivo al que se va a hacer un BackUp tiene de nombre "Adaptaciones.mdb" (fichero de nivel Alto de seguridad)
				// entonces se incluye una excepción para que los ficheros de Backup en vez de tener el formato normal, tengan uno
				// diferente en el que se añade la fecha, pero el nombre es ligeramente diferente.
				if (arg0.getName().toUpperCase().equals("ADAPTACIONES.MDB")) {
					nuevo = new FileExtendido("" + this.dirBackup.getPath() + File.separator + 
							(new MarcaDeTiempoDeFichero()) + "ADPT.mdb");
				} else {
					nuevo = new FileExtendido("" + this.dirBackup.getPath() + File.separator + 
							(new MarcaDeTiempoDeFichero()) + "_" + arg0.getName());
				}	
				arg0.copiarFile(nuevo);

				boolean ret = super.add(nuevo);
	
				Collections.reverse(this);
				
				return ret;
			} else {
				return true;
			}
		} catch (IOException e) {
			System.out.println("ERROR: Imposible hacer copia de seguridad");
			e.printStackTrace();
			return false;
		}
	}

	private boolean haHabidoCambiosUltimoBackup(FileExtendido arg0) throws IOException {
		if (this.size() != 0) {
			// Comprueba que hay cambios entre el archivo y la última copia de seguridad
			FileExtendido ultimaCopia = this.get(0);
			return (!ultimaCopia.compararContenidoFiles(arg0));
		} else {
			return true;
		}	
	}

	public void delimitarNumeroVersiones(int maxVer, int minVer, MarcaDeTiempoDeFichero fechMaxAntig) {

		this.minVer = minVer;

		Collections.sort(this);

		// Borra los elementos más antiguos cuya fecha sea mas antigua que fechMaxAntig
		while ((this.get(0).getMarcaDeTiempoDeFichero().compareTo(fechMaxAntig) < 0)
				&& (this.size() > this.minVer)) {
			this.remove(0);
		}
		// Borra los elementos mas antiguos hasta que como mucho queden maxVer
		while ((this.size() > maxVer) && (this.size() > this.minVer)) {
			this.remove(0);
		}

		Collections.reverse(this);

	}
	
	@Override
	public FileExtendido remove(int arg0) {
		FileExtendido ret = null;
		if (this.size() > this.minVer) {
			ret = super.remove(arg0);
			if (ret != null) {
				ret.delete();
			}			
		}
		return ret;
	}

}
