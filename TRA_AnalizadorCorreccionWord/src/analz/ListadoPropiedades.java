package analz;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import utils.LectorArchivosTexto;
import utils.LectorArchivosTextoASCII;

public class ListadoPropiedades extends ArrayList<Propiedad> {

	private static final long serialVersionUID = 1L;

	public ListadoPropiedades(String fichero) {
		super(200);
		try {
			LectorArchivosTexto lector = new LectorArchivosTextoASCII(fichero);
			while (true) {
				String linea = lector.readLine();
				Propiedad p = new Propiedad(linea);
				super.add(p);
			}
		} catch (FileNotFoundException e) {
		System.out.println("No existe el archivo de Propiedades");
		} catch (EOFException e) {
			// Se termino el fichero
			System.out.println("Fichero leido");
		}
	}

	@Override
	public String toString() {
		String ret = "";
		for (Propiedad p:this) {
			ret = ret + p + "\n";
		}
		
		return ret;
	}
	
	public static void main(String[] args) {
		ListadoPropiedades l = new ListadoPropiedades("Propiedades.csv");
		System.out.println(l);
	}
	
	public Propiedad buscar(int id) {
		Propiedad ret = null;
		for (Propiedad p:this) {
			if (p.getId()==id) {
				ret = p;
			}
		}
		return ret;
	}
}
