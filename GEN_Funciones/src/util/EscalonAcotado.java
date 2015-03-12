package util;

public class EscalonAcotado extends Funcion {
	
	private Funcion f1;
	private double limInf;
	private Funcion f2;
	private double limSup;
	private Funcion f3;
	
	private Sumatorio funcEquv;

	public EscalonAcotado(Funcion f1, double limInf, Funcion f2, double limSup, Funcion f3) {
		super(1.0);
		
		if (limInf > limSup) {
			double aux = limSup;
			limSup = limInf;
			limInf = aux;
		}
		
		this.funcEquv = new Sumatorio();
		this.funcEquv.agnadirSumando(new Escalon(f1, limInf, f2));
		
		Sumatorio aux = new Sumatorio();
		aux.agnadirSumando(f3);
		Funcion f2Aux = f2.clonar();
		f2Aux.multiplicarFactorialmente(-1.0);
		aux.agnadirSumando(f2Aux);
		
		this.funcEquv.agnadirSumando(new Escalon(new Constante(0), limSup, aux));
		
		this.f1 = f1.clonar();
		this.limInf = limInf;
		this.f2 = f2.clonar();
		this.limSup = limSup;
		this.f3 = f3.clonar();
	}
	
	@Override
	public String toString() {
		String retorno = "";
		
		retorno = "[ \u00A7 X < " + this.limInf + "  \u00BB  " + this.f1 + "    ||    "
			+ "\u00A7 "+ limInf + " <= X < " + this.limSup + "  \u00BB  " + this.f2 + "    ||    "
			+ "\u00A7 "+ limSup + " <= X  \u00BB  " + this.f3 + "]";
		
		return retorno;
	}

	@Override
	public Funcion clonar() {
		return new EscalonAcotado(this.f1.clonar(), this.limInf,
				this.f2.clonar(), this.limSup, this.f3.clonar());
	}

	@Override
	public Funcion derivada() {
		return this.funcEquv.derivada();
	}

	@Override
	public boolean esDerivable() {
		return this.funcEquv.esDerivable();
	}

	@Override
	public boolean esFuncionConstante() {
		return this.funcEquv.esFuncionConstante();
	}

	@Override
	public boolean esFuncionX() {
		return this.funcEquv.esFuncionX();
	}

	@Override
	public boolean esIntegrable() {
		return this.funcEquv.esIntegrable();
	}

	@Override
	public double evaluar(double t) {
		return this.funcEquv.evaluar(t);
	}

	@Override
	public Funcion integral() {
		return this.funcEquv.integral();
	}
	
	@Override
	public void multiplicarFactorialmente(double c) {
		this.f1.multiplicarFactorialmente(c);
		this.f2.multiplicarFactorialmente(c);
		this.f3.multiplicarFactorialmente(c);
		
		this.funcEquv.multiplicarFactorialmente(c);
	}
}
