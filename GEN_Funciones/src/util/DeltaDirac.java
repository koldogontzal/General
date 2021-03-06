package util;

import java.awt.Graphics;

import com.koldogontzal.geometria2d.Punto;


import lienzo.LienzoEnPlanoReal;

public class DeltaDirac extends Funcion {
	
	private double posicion;
	
	public DeltaDirac(double posicion, double factor) {
		super(factor);
		this.posicion = posicion;
	}
	
	@Override
	public double evaluar(double t) {
		if (t == this.posicion) {
			return super.getFactor() / 0.0;
		} else {
			return 0;
		}
	}
	
	@Override
	public boolean esIntegrable() {
		return true;
	}
	
	@Override
	public Funcion integral() {
		return new Escalon(Funcion.NULA, this.posicion, new Constante(super.getFactor()));
	}
	
	@Override
	public boolean esDerivable() {
		return false;
	}
	
	@Override
	public Funcion derivada() {
		return null;
	}
	
	@Override
	public String toString() {
		String retorno = super.toString();
		retorno = retorno + "Delta(X";
		if (this.posicion == 0) {
			retorno = retorno + ")";
		} else if (this.posicion < 0) {
			retorno = retorno + " + " + (-1.0 * this.posicion) + ")";
		} else {
			retorno = retorno + " - " + this.posicion + ")";
		}
		
		return retorno;
	}

	@Override
	public Funcion clonar() {
		return new DeltaDirac(this.posicion, super.getFactor());
	}

	@Override
	public boolean esFuncionConstante() {
		return false;
	}

	@Override
	public boolean esFuncionX() {
		return false;
	}
	
	@Override
	public void dibujar(LienzoEnPlanoReal lienzo, Graphics g) {
		g.setColor(super.color);
		
		Punto p0 = new Punto(this.posicion, 0);
		Punto p1 = new Punto(this.posicion, super.getFactor());
		

		lienzo.dibujaLinea(g, p0, p1);
		lienzo.dibujaCabezaFlechaUp(g, p1);
		
		
	}
	

}
