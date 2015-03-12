package util;

import java.awt.Color;
import java.awt.Graphics;

import com.koldogontzal.geometria2d.Punto;


import lienzo.LienzoEnPlanoReal;
import interfaz.PlanoRealDibujable;

public abstract class Funcion implements PlanoRealDibujable  {

	public final static Funcion NULA = new Constante(0.0);
	public final static Funcion UNIDAD = new Constante(1.0);
	public final static Funcion X = new Monomio(1.0, 1);

	private double factor;
	protected Color color;
	
	public Funcion(double factor) {
		this.factor = factor;
		this.color = Color.BLACK;
	}
	
	protected double getFactor() {
		return this.factor;
	}

	protected void setFactor(double factor) {
		this.factor = factor;
	}
	
	public abstract double evaluar(double t);
	
	public abstract boolean esIntegrable();
	
	public abstract Funcion integral();
	
	public abstract boolean esDerivable();

	public abstract Funcion derivada();
	
	public abstract boolean esFuncionConstante();
	
	public abstract boolean esFuncionX();
	
	public abstract Funcion clonar();	
	
	public void multiplicarFactorialmente(double c) {
		// c es la constante de multiplicaci�n
		this.factor = this.factor * c;
	}
	
	public Funcion integral(double c) {
		// c es la constante de integracion
		Sumatorio retorno = new Sumatorio();		
		retorno.agnadirSumando(new Constante(c));
		retorno.agnadirSumando(this.integral());
		return retorno;
	}
	
	public boolean esFuncionLineal() {
		return (this.esDerivable() && this.derivada().esFuncionConstante());
	}
	
	public boolean esFuncionNula() {
		return (this.getFactor() == 0.0);
	}
	
	public boolean esFuncionUnidad() {
		return (this.esFuncionConstante() && this.evaluar(0.0) == 1.0);
	}
	
	@Override
	public String toString() {
		String retorno = "";		
		if (this.factor != 1.0) {
			if (this.factor < 0.0) {
				retorno = "(";
			}
			retorno = retorno + this.factor;
			if (this.factor < 0.0) {
				retorno = retorno + ")";
			}
			retorno = retorno + " * ";
		}
		return retorno;
	}

	@Override
	public void dibujar(LienzoEnPlanoReal lienzo, Graphics g) {
		g.setColor(this.color);
		double paso = (lienzo.getXMaxReal() - lienzo.getXMinReal()) / (1 * lienzo.getAnchoPixels());
		
		for (double x = lienzo.getXMinReal(); x <= lienzo.getXMaxReal(); x = x + paso) {
			double y = this.evaluar(x);
			double y0 = this.evaluar(x - paso);
			
			if (!Double.isNaN(y) && !Double.isNaN(y0)) {
				Punto p = new Punto(x, y);
				Punto p0 = new Punto(x - paso, y0);
				lienzo.dibujaLinea(g, p0, p);
			}
		}		
	}
	
	public void setColor(Color c) {
		this.color = c;
	}

}
