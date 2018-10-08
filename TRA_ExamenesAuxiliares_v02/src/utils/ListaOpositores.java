package utils;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.Iterator;

import lector.LectorArchivosTexto;

public class ListaOpositores {

	private ArrayList<Opositor> lista = new ArrayList<Opositor>(2000);
	
	public ListaOpositores(String archivoStr) {
		LectorArchivosTexto archivoTexto = LectorArchivosTexto.open(archivoStr, 
				LectorArchivosTexto.MODO_UTF8);
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
	
	public Opositor buscarPorNIF(String nif) {
		Iterator<Opositor> i  = this.lista.iterator();
		boolean encontrado = false;
		Opositor op = null;
		
		while (!encontrado && i.hasNext()) {
			op = i.next();
			// A veces GESOPO da el NIF con 9 números en vez de los 8 correctos. Esto lo corrige, cogiendo los 8 + letra de la derecha
			String NIFtmp = op.getNIF();
			if (NIFtmp.length() == 10) {
				NIFtmp = NIFtmp.substring(1, 10);
			}
			encontrado = NIFtmp.equals(nif);
		}		
		if (encontrado)
			return op;
		else
			return null;
	}
	
	
	
	public Iterator<Opositor> iterator() {
		return this.lista.iterator();
	}
}
