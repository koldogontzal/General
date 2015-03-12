package utils;

public class Vector8ElementosDesordenados {
	private int[] vector;
	
	public Vector8ElementosDesordenados() {
		this.vector = new int[8];
		this.crearVector();
	}
	
	public int getPosicion(int pos) {
		return this.vector[pos];
	}
	
	private void crearVector() {
		int[] ordenado = new int[8];
		int valor = 0;
		for (int i = 0; i < 8; i++) {
			if (i == 4) {
				valor++;
			} 
			ordenado[i] = valor;
			valor++;
		}
		
		int posMax = 7;
		for (int i = 0; i < 8; i++) {
			int posElegida = (int)(Math.random() * (posMax + 1));
			this.vector[i] = ordenado[posElegida];
			ordenado[posElegida] = ordenado[posMax];
			posMax--;
		}
	}

	@Override
	public String toString() {
		String ret = "";
		for (int i = 0; i < 8; i++) {
			ret = ret + this.vector[i] + " ";
		}
		return ret;
	}
}
