package func;

public class FuncionM extends FuncionAbstracta {
	
	private long [] valoresFAux;
	private boolean [] calculadoFAux;
	
	public FuncionM() {
		super();
		this.iniciarFAux(FuncionAbstracta.TAMAGNO_BUFFER_DEFECTO);		
	}
	
	public FuncionM(int tamagnoBuffer) {
		super(tamagnoBuffer);
		this.iniciarFAux(tamagnoBuffer);
	}
	
	private void iniciarFAux(int tamagnoBuffer) {
		this.valoresFAux = new long[tamagnoBuffer];
		this.calculadoFAux = new boolean[tamagnoBuffer];
		for (int i = 0; i < tamagnoBuffer; i++) {
			this.calculadoFAux[i] = false;
		}
	}

	@Override
	protected long definicionDeFuncion(int n) {
		// La original sera M
		if (n == 0) {
			return 0;
		} else {
			return n - this.valorFuncionF((int)this.valorFuncionM(n - 1));
		}
	}
	
	private long funcionDefinicionF(int n) {
		if (n == 0) {
			return 1;
		} else {
			return n - this.valorFuncionM((int)this.valorFuncionF(n - 1));
		}
	}
	
	private long valorFuncionM(int n) {
		return super.valor(n);
	}
	
	private long valorFuncionF(int n) {
		if (n < this.valoresFAux.length) {
			// Esta dentro del rango de valores precalculados
			if (this.calculadoFAux[n]) {
				// Se habia calculado previamente, devuelve ese valor:
				return this.valoresFAux[n];				
			} else {
				// Se calcula por primera vez y se almacena
				this.valoresFAux[n] = this.funcionDefinicionF(n);
				this.calculadoFAux[n] = true;
				return this.valoresFAux[n];					
			}
		} else {
			// Esta fuera del rango, lo calcula por el metodo de la definicion
			return this.funcionDefinicionF(n);
		}
	}
}
