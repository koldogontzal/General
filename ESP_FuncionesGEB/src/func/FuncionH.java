package func;

public class FuncionH extends FuncionAbstracta {
	
	public FuncionH() {
		super();
	}
	
	public FuncionH(int tamagnoBuffer) {
		super(tamagnoBuffer);
	}

	@Override
	protected long definicionDeFuncion(int n) {
		if (n == 0) {
			return 0;
		} else {
			return (n - super.valor((int)super.valor((int)super.valor(n - 1))));
		}
	}

}
