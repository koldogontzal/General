package util;

import java.awt.Graphics;

import com.koldogontzal.geometria2d.Punto;


import lienzo.LienzoEnPlanoReal;

public class Escalon extends Funcion {
	
	private Funcion f1;
	private double posicion;
	private Funcion f2;

	public Escalon(Funcion f1, double pos, Funcion f2) {
		super(1.0);
		this.f1 = f1;
		this.posicion = pos;
		this.f2 = f2;
	}
	
	@Override
	public double evaluar(double t) {
		if (t < this.posicion) {
			return this.f1.evaluar(t);
		} else {
			return this.f2.evaluar(t);
		}
	}
	
	@Override
	public void multiplicarFactorialmente(double c) {
		this.f1.multiplicarFactorialmente(c);
		this.f2.multiplicarFactorialmente(c);
	}
	
	
	@Override
	public boolean esIntegrable() {
		return this.f1.esIntegrable() && this.f2.esIntegrable();
	}
	
	@Override
	public Funcion integral() {
		Funcion retorno = null;
		
		if (this.esIntegrable()) {
			Funcion auxF1 = this.f1.integral();
			Funcion auxF2a = this.f2.integral();
			double constante = auxF1.evaluar(this.posicion) - auxF2a.evaluar(this.posicion);
			Funcion auxF2 = this.f2.integral(constante);
			
			retorno = new Escalon(auxF1, this.posicion, auxF2);
		}
		
		return retorno;
	}
	
	@Override
	public boolean esDerivable() {
		return this.f1.esDerivable() && this.f2.esDerivable();
	}
	
	@Override
	public Funcion derivada() {		
		if (this.esDerivable()) {
			Funcion auxF1 = this.f1.derivada();
			Funcion auxF2 = this.f2.derivada();
			double constanteDelta = this.f2.evaluar(this.posicion) - this.f1.evaluar(this.posicion);
			if (constanteDelta == 0.0) {
				return new Escalon(auxF1, this.posicion, auxF2);
			} else {
				Sumatorio retorno = new Sumatorio();
				retorno.agnadirSumando(new Escalon(auxF1, this.posicion, auxF2));
				retorno.agnadirSumando(new DeltaDirac(this.posicion, constanteDelta));
				return retorno;
			}
		} else {
			return null;
		}
	}
	
	@Override
	public String toString() {
		return "[ \u00A7 X < " + this.posicion + "  \u00BB  " + this.f1 + "    ||    \u00A7 X >= " + this.posicion + "  \u00BB  " + this.f2 + "]";
	}

	@Override
	public Funcion clonar() {
		return new Escalon(this.f1.clonar(), this.posicion, this.f2.clonar());
	}

	@Override
	public boolean esFuncionConstante() {
		return (this.f1.esFuncionConstante() && this.f2.esFuncionConstante() && this.f1.evaluar(0) == this.f2.evaluar(0));
	}

	@Override
	public boolean esFuncionX() {
		return (this.f1.esFuncionX() && this.f2.esFuncionX());
	}
	
	@Override
	public boolean esFuncionNula() {
		return (this.f1.esFuncionNula() && this.f2.esFuncionNula());
	}
	
	@Override
	public void dibujar(LienzoEnPlanoReal lienzo, Graphics g) {
		g.setColor(this.color);
		double paso = (lienzo.getXMaxReal() - lienzo.getXMinReal()) / (1 * lienzo.getAnchoPixels());
		
		boolean estoyEnF1 = (lienzo.getXMinReal() < this.posicion);
		
		for (double x = lienzo.getXMinReal(); x <= lienzo.getXMaxReal(); x = x + paso) {
			double y = this.evaluar(x);
			double y0 = this.evaluar(x - paso);
			
			if (!Double.isNaN(y) && !Double.isNaN(y0)) {
				Punto p = new Punto(x, y);
				Punto p0 = new Punto(x - paso, y0);
				if (estoyEnF1 && x > this.posicion) {
					estoyEnF1 = false;
				} else {
					lienzo.dibujaLinea(g, p0, p);
				}
			}
		}		
	
		if (this.f1.evaluar(this.posicion) != this.f2.evaluar(this.posicion)) {
			Punto p = new Punto(this.posicion, this.f2.evaluar(this.posicion));
			lienzo.dibujaCruz(g, p);
		}
	}
}
