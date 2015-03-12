package util;

public class Monomio extends Funcion {
	private int grado;
	
	public Monomio(double factor, int grado) {
		super(factor);
		this.grado = grado;
	}
	
	@Override
	public double evaluar(double t) {
		return super.getFactor() * Math.pow(t, this.grado);
	}
	
	@Override
	public boolean esIntegrable() {
		return true;
	}
	
	@Override
	public Funcion integral() {
		Funcion retorno;
		if (this.grado != -1) {
			retorno = new Monomio(super.getFactor() / (this.grado + 1), this.grado + 1);			
		} else {
			retorno = new LogaritmoNeperiano(super.getFactor(), Funcion.X);
		}
		if (this.esFuncionNula()) {
			retorno = Funcion.NULA;
		}
		return retorno;
	}
	
	@Override
	public boolean esDerivable() {
		return true;
	}
	
	
	@Override
	public Funcion derivada() {
		Funcion retorno;		
		if (this.grado != 0) {
			retorno = new Monomio (super.getFactor() * this.grado, this.grado - 1);			
		} else {
			retorno = Funcion.NULA;
		}		
		return retorno;
	}
	
	@Override
	public String toString() {
		String retorno = super.toString();		
		if (this.grado != 0) {
			retorno = retorno + "X";
			if (this.grado != 1) {
				retorno = retorno + " ^ " + this.grado;
			}
		} else {
			if (super.getFactor() != 1.0) {
				retorno = retorno.substring(0, retorno.length() - 3);
			} else {
				retorno = "1.0";
			}
		}
		return retorno;
	}

	@Override
	public Funcion clonar() {
		return new Monomio(super.getFactor(), this.grado);
	}

	@Override
	public boolean esFuncionConstante() {
		return (this.grado == 0);
	}


	@Override
	public boolean esFuncionX() {
		return (this.grado == 1 && super.getFactor() == 1.0);
	}
	
}
