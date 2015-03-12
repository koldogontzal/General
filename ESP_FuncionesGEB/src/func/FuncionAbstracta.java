package func;

public abstract class FuncionAbstracta {
	
	private long [] valores;
	private boolean [] calculado;
	
	public static int TAMAGNO_BUFFER_DEFECTO = 29000;
	
	public FuncionAbstracta() {
		this(TAMAGNO_BUFFER_DEFECTO);
	}
	
	public FuncionAbstracta(int tamagnoBuffer) {
		this.valores = new long[tamagnoBuffer];
		this.calculado = new boolean[tamagnoBuffer];
		for (int i = 0; i < tamagnoBuffer; i++) {
			this.calculado[i] = false;
		}
	}
	
	protected abstract long definicionDeFuncion(int n);
	
	public long valor(int n) {
		if (n < this.valores.length) {
			// Esta dentro del rango de valores precalculados
			if (this.calculado[n]) {
				// Se habia calculado ya previamente, devuelve ese valor:
				return this.valores[n];				
			} else {
				// Se calcula por primera vez y se almacena
				this.valores[n] = this.definicionDeFuncion(n);
				this.calculado[n] = true;
				return this.valores[n];					
			}
		} else {
			// Esta fuera del rango, lo calcula por el metodo de la definicion
			return this.definicionDeFuncion(n);
		}
	}
}
