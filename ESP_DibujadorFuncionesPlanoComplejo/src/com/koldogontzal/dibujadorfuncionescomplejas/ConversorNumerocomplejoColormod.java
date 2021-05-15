package com.koldogontzal.dibujadorfuncionescomplejas;

import com.koldogontzal.numeroscomplejos.NumeroComplejo;

import utils.ColorMod;

public class ConversorNumerocomplejoColormod {
	/*
	 * Convierte un número complejo NumeroComplejo z en un color ColorMod c.
	 * 
	 * Para ello utiliza el radio y el ángulo de z, convirtiéndolos en el hue y el
	 * sat de c.
	 * 
	 * El ángulo de z (entre -180º y 180º) se traduce al hue (entre 0º y 360º). El
	 * radio de z (entre 0.0 y maxRadio) se traslada al sat (entre 0.0 y 100.0)
	 * usando la variable maxRadio almacenda en el objeto.
	 */

	private float maxRadio;
	private float inversoMaxRadio100;
	private float minSat = 0;
	private float restoSat = 100;

	public ConversorNumerocomplejoColormod(double maxRadio) {
		this.setMaxRadio(maxRadio);
	}

	public ConversorNumerocomplejoColormod() {
		this(0.00000001);
	}

	public void setMaxRadio(double maxRadio) {
		this.setMaxRadio((float) maxRadio);
	}

	public void setMaxRadio(float maxRadio) {
		this.maxRadio = maxRadio;
		this.inversoMaxRadio100 = (float) (this.restoSat / maxRadio);
	}

	public void setMinSat(float minSat) {
		this.minSat = Math.min(100, Math.max(0, minSat));
		this.restoSat = 100 - minSat;

	}
	
	public float getMaxRadio() {
		return maxRadio;
	}

	public boolean modificarMaxRadio(double radio) {
		return modificarMaxRadio((float) radio);
	}

	public boolean modificarMaxRadio(float radio) {
		if (radio > this.maxRadio) {
			this.setMaxRadio(radio);
			return true;
		} else {
			return false;
		}
	}

	public ColorMod convertir(NumeroComplejo z) {
		ColorMod c;
		float hue = (float) z.getAngulo(true) + 180;
		float sat = this.minSat + (float) Math.min(restoSat, z.getModulo() * this.inversoMaxRadio100);
		c = ColorMod.getColorFromHSV(hue, sat, sat);
		return c;
	}

	public NumeroComplejo convertir(ColorMod c) {
		NumeroComplejo z;
		double angulo = c.getHue() - 180;
		double modulo = this.maxRadio * (c.getSaturation() - this.minSat) / this.restoSat;
		z = new NumeroComplejo(true, modulo, angulo);
		return z;
	}

}
