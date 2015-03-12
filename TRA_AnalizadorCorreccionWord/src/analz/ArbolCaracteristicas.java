package analz;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

import utils.EscritorArchivosTexto;
import utils.LectorArchivosTexto;
import utils.LectorArchivosTextoASCII;

public class ArbolCaracteristicas extends ArrayList<Caracteristica> {

	private static final long serialVersionUID = 1L;
	
	private static ListadoPropiedades defProp = new ListadoPropiedades("Propiedades.csv");

	public ArbolCaracteristicas(String fichero) {
		super(600);
		try {
			LectorArchivosTexto lector = new LectorArchivosTextoASCII(fichero);
			while (true) {
				String linea = lector.readLine();
				Caracteristica c = new Caracteristica(linea);
				super.add(c);
			}
		} catch (FileNotFoundException e) {
		System.out.println("No existe el archivo de Caracteristicas");
		} catch (EOFException e) {
			// Se termino el fichero
			System.out.println("Fichero leido");
		}
	}

	@Override
	public String toString() {
		String ret = "";
		for (Caracteristica c:this) {
			ret = ret + this.getJerarquiaCompleta(c) + " " + this.getDescripcionCompleta(c) + " " + c + "\n";
		}
		
		return ret;
	}
	
	public boolean toArchivoCSV(String archivo) {
		String texto = "Jerarquía;Descripción;esHoja;Puntuación\n";
		for (Caracteristica c : this) {
			texto = texto + this.getJerarquiaCompleta(c) + ";"
					+ this.getDescripcionCompleta(c) + ";" + this.esHoja(c)
					+ ";" + c.getPuntuacion() + "\n";
		}
		return EscritorArchivosTexto.createFile(archivo, texto);
	}
	
	public String getDescripcionCompleta(Caracteristica c) {
		String ret = "";
		int idPadre = c.getIdPadre();
		if (idPadre == 0) {
			Propiedad p = defProp.buscar(c.getPropiedad());
			ret = "" + p;
		} else {
			Caracteristica padre = this.buscar(idPadre);			
			ret = this.getDescripcionCompleta(padre) + " > " + defProp.buscar(c.getPropiedad());
		}
				
		return ret;
	}
	
	public String getJerarquiaCompleta(Caracteristica c) {
		String ret = "";
		int idPadre = c.getIdPadre();
		if (idPadre == 0) {
			ret = "" + c.getId();
		} else {
			Caracteristica padre = this.buscar(idPadre);			
			ret = this.getJerarquiaCompleta(padre) + "." + c.getId();
		}				
		return ret;
	}
	
	public Caracteristica buscar(int id) {
		Caracteristica ret = null;
		for (Caracteristica c:this) {
			if (c.getId()==id) {
				ret = c;
			}
		}
		return ret;
	}
	
	public boolean esHoja(Caracteristica c) {
		boolean tieneHijos = false;
		int id = c.getId();
		Iterator<Caracteristica> i = this.iterator();
		Caracteristica aux = null;
		while ((i.hasNext()) && (!tieneHijos)) {
			aux = i.next();
			if (aux.getIdPadre() == id) {
				tieneHijos = true;
			}
		}
		return !tieneHijos;
	}
}
