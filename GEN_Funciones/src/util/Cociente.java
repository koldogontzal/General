package util;

public class Cociente extends Funcion {
	
	private Funcion numerador;
	private Funcion denominador;

	public Cociente(Funcion numerador, Funcion denominador) {
		super(1.0);
		this.numerador = numerador.clonar();
		this.denominador = denominador.clonar();
	}
	
	@Override
	public void multiplicarFactorialmente(double c) {
		this.numerador.multiplicarFactorialmente(c);
	}

	@Override
	public Funcion clonar() {
		return new Cociente(this.numerador, this.denominador);
	}

	@Override
	public Funcion derivada() {
		if (this.esDerivable()) {
			Sumatorio auxNum = new Sumatorio();
				Multiplicatorio auxNum1 = new Multiplicatorio(1.0);
				auxNum1.agnadirFactor(this.numerador.derivada());
				auxNum1.agnadirFactor(this.denominador);
				
				Multiplicatorio auxNum2 = new Multiplicatorio(-1.0);
				auxNum2.agnadirFactor(this.numerador);
				auxNum2.agnadirFactor(this.denominador.derivada());
				
				auxNum.agnadirSumando(auxNum1);
				auxNum.agnadirSumando(auxNum2);
			
			Multiplicatorio auxDen = new Multiplicatorio(1.0);
				auxDen.agnadirFactor(this.denominador);
				auxDen.agnadirFactor(this.denominador);
			return new Cociente(auxNum, auxDen);
		} else {
			return null;
		}
	}

	@Override
	public boolean esDerivable() {
		return (this.numerador.esDerivable() && this.denominador.esDerivable() && !this.denominador.esFuncionNula());
	}

	@Override
	public boolean esFuncionConstante() {
		return (this.numerador.esFuncionConstante() && this.denominador.esFuncionConstante());
	}

	@Override
	public boolean esFuncionX() {
		return false;
	}

	@Override
	public boolean esIntegrable() {
		return false;
	}

	@Override
	public double evaluar(double t) {
		return this.numerador.evaluar(t) / this.denominador.evaluar(t);
	}

	@Override
	public Funcion integral() {
		return null;
	}
	
	@Override
	public boolean esFuncionNula() {
		return (this.numerador.esFuncionNula());
	}
	
	@Override
	public String toString() {
		return "" + this.numerador + " / " + this.denominador;
	}
}
