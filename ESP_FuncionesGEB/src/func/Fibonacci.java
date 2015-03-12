package func;

public class Fibonacci extends FuncionAbstracta {
	
	public Fibonacci() {
		super();
	}
	
	public Fibonacci(int tamagnoBuffer) {
		super(tamagnoBuffer);
	}

	@Override
	protected long definicionDeFuncion(int n) {
		if (n <= 0) {
			return 0;
		} else if (n <= 2) {
			return 1;
		} else {
			return super.valor(n - 1) + super.valor(n - 2);
		}
	}

}
