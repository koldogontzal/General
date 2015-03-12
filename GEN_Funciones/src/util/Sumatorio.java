package util;

import java.util.ArrayList;
import java.util.Iterator;

public class Sumatorio extends Funcion {
	
	private ArrayList<Funcion> sumandos;

	public Sumatorio() {
		super(1.0);
		this.sumandos = new ArrayList<Funcion>(10);
	}
	
	public final void agnadirSumando(Funcion f) {
		if (!f.esFuncionNula()) {
			this.sumandos.add(f.clonar());
		}
	}
	
	public double evaluar(double t) {
		double retorno = 0.0;
		Iterator<Funcion> i = this.sumandos.iterator();		
		while (i.hasNext()) {
			retorno = retorno + i.next().evaluar(t);
		}
		return retorno;
	}
	
	public boolean esIntegrable() {
		boolean retorno = true;
		Iterator<Funcion> i = this.sumandos.iterator();		
		while (i.hasNext()) {
			Funcion f = i.next();
			retorno = retorno && f.esIntegrable();
		}
		return retorno;
	}
	
	public Funcion integral() {
		if (this.esIntegrable()) {
			Sumatorio retorno = new Sumatorio();			
			Iterator<Funcion> i = this.sumandos.iterator();		
			while (i.hasNext()) {
				retorno.agnadirSumando(i.next().integral());
			}
			return retorno;
		} else {
			return null;
		}
	}
	
	public boolean esDerivable() {
		boolean retorno = true;
		Iterator<Funcion> i = this.sumandos.iterator();		
		while (i.hasNext()) {
			Funcion aux = i.next();
			retorno = retorno && aux.esDerivable();
		}
		return retorno;
	}
	
	public Funcion derivada() {
		if (this.esDerivable()) {
			Sumatorio retorno = new Sumatorio();			
			Iterator<Funcion> i = this.sumandos.iterator();		
			while (i.hasNext()) {
				retorno.agnadirSumando(i.next().derivada());
			}
			return retorno;
		} else {
			return null;
		}
	}
	
	@Override
	public String toString() {
		String retorno = super.toString();
		if (this.sumandos.size() > 1) {
			retorno = retorno + "(";
		}
		int contadorSumandos = 0;
		Iterator<Funcion> i = this.sumandos.iterator();		
		while (i.hasNext()) {
			retorno = retorno + i.next().toString() + " + ";
			contadorSumandos++;
		}
		
		if (contadorSumandos == 0) {
			retorno = retorno + "0";
		} else {
			retorno = retorno.substring(0, retorno.length() - 3);
		}
		if (this.sumandos.size() > 1) {
			retorno = retorno + ")";
		}
		return retorno;
	}

	@Override
	public Funcion clonar() {
		Sumatorio retorno = new Sumatorio();
		Iterator<Funcion> i = this.sumandos.iterator();		
		while (i.hasNext()) {
			Funcion f = i.next();
			retorno.agnadirSumando(f.clonar());
		}		
		return retorno;
	}

	@Override
	public boolean esFuncionConstante() {
		return ((this.sumandos.size() == 1 && this.sumandos.get(0).esFuncionConstante()) || this.esFuncionNula());
	}

	@Override
	public boolean esFuncionX() {
		return (this.sumandos.size() == 1 && this.sumandos.get(0).esFuncionX());
	}
	
	@Override
	public boolean esFuncionNula() {
		boolean retorno = true;
		Iterator<Funcion> i = this.sumandos.iterator();		
		while (i.hasNext()) {
			Funcion f = i.next();
			retorno = retorno && f.esFuncionNula();
		}	
		return (super.esFuncionNula() || this.sumandos.size() == 0 || retorno);
	}
	
	@Override
	public void multiplicarFactorialmente(double c) {
		Iterator<Funcion> i = this.sumandos.iterator();		
		while (i.hasNext()) {
			Funcion f = i.next();
			f.multiplicarFactorialmente(c);
		}	
	}

}
