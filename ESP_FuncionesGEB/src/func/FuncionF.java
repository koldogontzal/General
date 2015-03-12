package func;

public class FuncionF extends FuncionAbstracta {
	
	private long [] valoresMAux;
	private boolean [] calculadoMAux;
	
	public FuncionF() {
		super();
		this.iniciarMAux(FuncionAbstracta.TAMAGNO_BUFFER_DEFECTO);		
	}
	
	public FuncionF(int tamagnoBuffer) {
		super(tamagnoBuffer);
		this.iniciarMAux(tamagnoBuffer);
	}
	
	private void iniciarMAux(int tamagnoBuffer) {
		this.valoresMAux = new long[tamagnoBuffer];
		this.calculadoMAux = new boolean[tamagnoBuffer];
		for (int i = 0; i < tamagnoBuffer; i++) {
			this.calculadoMAux[i] = false;
		}
	}

	@Override
	protected long definicionDeFuncion(int n) {
		// La original sera F
		if (n == 0) {
			return 1;
		} else {
			return n - this.valorFuncionM((int)this.valorFuncionF(n - 1));
		}
	}
	
	private long funcionDefinicionM(int n) {
		if (n == 0) {
			return 0;
		} else {
			return n - this.valorFuncionF((int)this.valorFuncionM(n - 1));
		}
	}
	
	private long valorFuncionF(int n) {
		return super.valor(n);
	}
	
	private long valorFuncionM(int n) {
		if (n < this.valoresMAux.length) {
			// Esta dentro del rango de valores precalculados
			if (this.calculadoMAux[n]) {
				// Se habia calculado previamente, devuelve ese valor:
				return this.valoresMAux[n];				
			} else {
				// Se calcula por primera vez y se almacena
				this.valoresMAux[n] = this.funcionDefinicionM(n);
				this.calculadoMAux[n] = true;
				return this.valoresMAux[n];					
			}
		} else {
			// Esta fuera del rango, lo calcula por el metodo de la definicion
			return this.funcionDefinicionM(n);
		}
	}
}
