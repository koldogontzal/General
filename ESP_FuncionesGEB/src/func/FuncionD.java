package func;

public class FuncionD extends FuncionAbstracta {		

	public FuncionD() {
		super();
	}
	
	public FuncionD(int tamagnoBuffer) {
		super(tamagnoBuffer);
	}
	
	@Override
	protected long definicionDeFuncion(int n) {
		if (n == 0) {
			return 0;
		} else {
			return (n - super.valor((int)super.valor(n - 1)));
		}
	}
}
