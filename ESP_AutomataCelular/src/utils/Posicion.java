package utils;

public class Posicion {
	private int fila;
	private int columna;
	
	public Posicion(int fila, int columna) {
		this.fila = fila;
		this.columna = columna;
	}

	public int getColumna() {
		return this.columna;
	}

	public void setColumna(int columna) {
		this.columna = columna;
	}

	public int getFila() {
		return this.fila;
	}

	public void setFila(int fila) {
		this.fila = fila;
	}
	
	public Posicion getPosicionVecino(int posVecino) {
		// posVecino:
		//  0  1  2
		//  3  X  5
		//  6  7  8
		//
		// Siendo posVecino = 4 la posicion actual
		
		int fila = this.getFila() + (posVecino / 3) - 1;
		int columna = this.getColumna() + (posVecino % 3) - 1;
		
		return new Posicion(fila, columna);
	}
	
	@Override
	public String toString() {
		return "(" + this.fila + ", " + this.columna + ")";
	}
}
