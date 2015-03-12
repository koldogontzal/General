package main;

import java.io.EOFException;

import tiempo.Instante;

import escritor.EscritorArchivosTexto;
import lector.LectorArchivosTexto;

public class ManejadorSubtitulosIDX {
	
	private LectorArchivosTexto lector;
	private EscritorArchivosTexto escritor;
	
	private Instante modificarInstante(Instante i) {
		i.modificar(-38, Instante.SEGUNDOS);
		i.modificar(-404, Instante.MILISEGUNDOS);
		i.modificarFactor(0.95886776406137);
		i.modificar(32, Instante.SEGUNDOS);
		i.modificar(817, Instante.MILISEGUNDOS);
		return i;
	}
	
	public ManejadorSubtitulosIDX(String fichEntrada, String fichSalida) {
		this.lector = LectorArchivosTexto.open(fichEntrada, LectorArchivosTexto.MODO_ASCII);
		this.escritor = EscritorArchivosTexto.open(fichSalida);
		
		this.replicarYAnalizar();
	}
	
	private void replicarYAnalizar() {
		try {
			String linea;
			String lineaEscribir;
			while (true) {
				linea = this.lector.readLine();
				if ((linea.length() > 23)
						&& (linea.substring(0, 9).equals("timestamp"))) {
					// Línea a modificar
					lineaEscribir = null;
					Instante i = Instante.parseInstante(linea.substring(11, 23));
					i = this.modificarInstante(i);
					lineaEscribir = "timestamp: " + i.toStringIDX() + linea.substring(23);
				} else {
					// Linea que no hay que modificar
					lineaEscribir = linea;
				}
				this.escritor.writeLine(lineaEscribir);
			}
			
		} catch (EOFException e) {
			this.escritor.close();
			this.lector.close();
		}
		
		
	}
	
	
	public static void main(String[] args) {
		ManejadorSubtitulosIDX s = new ManejadorSubtitulosIDX("Were The World Mine (2008).idx", "Were The World Mine_2 (2008).idx");
	}

}
