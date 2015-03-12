package utils;

public class Vector4ElementosDesordenados {
	private int[] vector;
	
	public Vector4ElementosDesordenados() {
		this.vector = new int[4];
		this.crearVector();
	}
	
	public int getPosicion(int pos) {
		return this.vector[pos];
	}
	
	private void crearVector() {
		int[] ordenado = new int[4];
		for (int i = 0; i < 4; i++) {
			ordenado[i] = 2 * i + 1;
		}
		
		int posMax = 3;
		for (int i = 0; i < 4; i++) {
			int posElegida = (int)(Math.random() * (posMax + 1));
			this.vector[i] = ordenado[posElegida];
			ordenado[posElegida] = ordenado[posMax];
			posMax--;
		}
	}

	@Override
	public String toString() {
		String ret = "";
		for (int i = 0; i < 4; i++) {
			ret = ret + this.vector[i] + " ";
		}
		return ret;
	}
	
}
