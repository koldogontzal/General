package util;

public class LogaritmoNeperiano extends Funcion {
	
	private Funcion argumento;
	
	public LogaritmoNeperiano(double factor, Funcion funArg) {
		super(factor);
		this.argumento = funArg;
	}
	
	@Override
	public double evaluar(double t) {
		return super.getFactor() * Math.log(this.argumento.evaluar(t));
	}
	
	@Override
	public boolean esIntegrable() {
		return this.argumento.esFuncionLineal();
	}
	
	@Override
	public Funcion integral() {
		if (this.esIntegrable()) {
			if (this.esFuncionConstante()) {
				return new Monomio(super.getFactor() * this.evaluar(0), 1);
			} else {
				Multiplicatorio retorno = new Multiplicatorio(super.getFactor());
				retorno.multiplicarFactorialmente(1.0 / this.argumento.derivada().evaluar(0)); // La derivada es constante, porque el argumento es lineal
				retorno.agnadirFactor(this.argumento);
				Sumatorio aux = new Sumatorio();
				aux.agnadirSumando(new LogaritmoNeperiano(1.0, this.argumento));
				aux.agnadirSumando(new Constante(-1.0));
				retorno.agnadirFactor(aux);
				return retorno;
			}
		} else {
			return null;
		}
	}
	

	@Override
	public boolean esDerivable() {
		return (this.argumento.esDerivable());
	}
	
	@Override
	public Funcion derivada() {
		if (this.esDerivable()) {
			return new Cociente(this.argumento.derivada(), this.argumento);
		} else {
			return null;
		}
	}
	
	
	@Override
	public String toString() {
		return super.toString() + "ln(" + this.argumento + ")";
	}

	@Override
	public Funcion clonar() {
		return new LogaritmoNeperiano(super.getFactor(), this.argumento);
	}

	@Override
	public boolean esFuncionConstante() {
		return this.argumento.esFuncionConstante();
	}

	@Override
	public boolean esFuncionX() {
		return false;
	}
}
