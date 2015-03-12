package util;

import java.util.ArrayList;
import java.util.Iterator;

public class Multiplicatorio extends Funcion {
	
	private ArrayList<Funcion> factores;
	
	public Multiplicatorio(double factor) {
		super(factor);
		this.factores = new ArrayList<Funcion>();
	}
	
	public final void agnadirFactor(Funcion f) {
		if (!f.esFuncionConstante()) {
			this.factores.add(f.clonar());
		} else {
			super.multiplicarFactorialmente(f.evaluar(0.0));
		}
		if (this.esFuncionNula()) {
			// Borra los factores metidos
			this.factores = new ArrayList<Funcion>();
		}
	}
	
	@Override
	public double evaluar(double t) {
		double retorno = super.getFactor();
		Iterator<Funcion> i = this.factores.iterator();		
		while (i.hasNext()) {
			retorno = retorno * i.next().evaluar(t);
		}		
		return retorno;
	}
	
	@Override
	public boolean esIntegrable() {
		return (this.esFuncionConstante() || (this.factores.size() == 1 && this.factores.get(0).esIntegrable()));
	}
	
	@Override
	public Funcion integral() {
		if (this.esIntegrable()) {
			if (this.factores.size() == 0) {
				return Funcion.NULA;
			} else {
				Funcion retorno = this.factores.get(0).integral();
				retorno.multiplicarFactorialmente(super.getFactor());
				return retorno;
			}
		} else {
			return null;
		}
	}
	
	@Override
	public boolean esDerivable() {
		return true;
	}
	
	@Override
	public Funcion derivada() {
		if (this.esDerivable()) {
			if (this.esFuncionNula()) {
				return Funcion.NULA;
			} else if (this.factores.size() == 1) {
				Multiplicatorio retorno = new Multiplicatorio(this.getFactor());
				retorno.agnadirFactor(this.factores.get(0).derivada());
				return retorno;
			} else {
				Iterator<Funcion> i = this.factores.iterator();
				Funcion f = i.next();
				Multiplicatorio g = new Multiplicatorio(1.0);
				while (i.hasNext()) {
					g.agnadirFactor(i.next());
				}
				Sumatorio retorno = new Sumatorio();
				Multiplicatorio sumando1 = new Multiplicatorio(1.0);
					sumando1.agnadirFactor(f);
					sumando1.agnadirFactor(g.derivada());
				Multiplicatorio sumando2 = new Multiplicatorio(1.0);
					sumando2.agnadirFactor(f.derivada());
					sumando2.agnadirFactor(g);
				retorno.agnadirSumando(sumando1);
				retorno.agnadirSumando(sumando2);
				retorno.multiplicarFactorialmente(this.getFactor());
				return retorno;
			}
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		if (this.esFuncionConstante()) {
			return "" + this.evaluar(0);
		} else {
			String retorno = super.toString();
			Iterator<Funcion> i = this.factores.iterator();		
			while (i.hasNext()) {
				retorno = retorno + i.next();
				if (i.hasNext()) {
					retorno = retorno + " * ";
				}
			}	
			return retorno;
		}
	}

	@Override
	public Funcion clonar() {
		Multiplicatorio retorno = new Multiplicatorio(super.getFactor());
		Iterator<Funcion> i = this.factores.iterator();		
		while (i.hasNext()) {
			Funcion f = i.next();
			retorno.agnadirFactor(f.clonar());
		}		
		return retorno;
	}

	@Override
	public boolean esFuncionConstante() {	
		return (this.esFuncionNula() || this.factores.size() == 0);
	}

	@Override
	public boolean esFuncionX() {
		return (super.getFactor() == 1.0 && this.factores.size() == 1 && this.factores.get(0).esFuncionX());
	}
}
