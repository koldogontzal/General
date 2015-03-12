package utils;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.Iterator;

import lector.LectorArchivosTexto;

public class ListaOpositores {

	private ArrayList<Opositor> lista = new ArrayList<Opositor>(1000);
	
	public ListaOpositores(String archivoStr) {
		LectorArchivosTexto archivoTexto = LectorArchivosTexto.open(archivoStr, 
				LectorArchivosTexto.MODO_ASCII);
		try {
			while (true) {
				String linea = archivoTexto.readLine();
				Opositor op = new Opositor(linea);
				this.lista.add(op);
			}
		} catch (EOFException e) {
			// Ha terminado de leer la lista
			//System.out.println("Lista de opositores leída.");
			//System.out.println("Hay " + this.lista.size() + " opositores");
		}

	}
	
	@Override
	public String toString() {
		String ret = null;
		Iterator<Opositor> i = this.lista.iterator();
		while (i.hasNext()) {
			Opositor op = i.next();
			ret = ret + op + "\n";
		}
		ret = ret + this.lista.size() + " opositores.";
		return ret;
	}
	
	public Opositor buscarPorCodExamen(String cod) {
		Iterator<Opositor> i  = this.lista.iterator();
		boolean encontrado = false;
		Opositor op = null;
		
		while (!encontrado && i.hasNext()) {
			op = i.next();
			encontrado = op.getCodExamen().equals(cod);
		}
		
		return op;
	}
}
