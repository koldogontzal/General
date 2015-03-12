package func;

public class FuncionQ extends FuncionAbstracta {
	
	public FuncionQ() {
		super();
	}
	
	public FuncionQ(int tamagnoBuffer) {
		super(tamagnoBuffer);
	}

	@Override
	protected long definicionDeFuncion(int n) {
		if (n > 2) {
			return super.valor(n - (int)super.valor(n - 1)) + super.valor(n - (int)super.valor(n - 2));
		} else if (n <= 0) {
			return 0;
		} else {
			return 1;
		}
	}

}
