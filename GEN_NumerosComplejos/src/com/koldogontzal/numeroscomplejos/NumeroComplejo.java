package com.koldogontzal.numeroscomplejos;

import java.text.DecimalFormat;


public class NumeroComplejo {
	private double parteRe;
	private double parteIm;

	public NumeroComplejo(double parteReal, double parteImaginaria) {
		this.parteRe = parteReal;
		this.parteIm = parteImaginaria;
	}

	public NumeroComplejo(NumeroComplejo z) {
		this.parteRe = z.parteRe;
		this.parteIm = z.parteIm;
	}

	public NumeroComplejo(boolean coordenadasRadialesEnGrados, double radio, double angulo) {
		// si coordenadasRadialesEnGrados = true, el angulo está en grados, si no, está en radianes
		if (coordenadasRadialesEnGrados) {
			// El angulo esta en grados
			this.parteRe = radio * Math.cos(Math.toRadians(angulo));
			this.parteIm = radio * Math.sin(Math.toRadians(angulo));
		} else {
			// El angulo esta en radianes
			this.parteRe = radio * Math.cos(angulo);
			this.parteIm = radio * Math.sin(angulo);
		}
	}

	public double getRe() {
		return this.parteRe;
	}

	public double getIm() {
		return this.parteIm;
	}

	public double getRadio() {
		return Math.sqrt(this.parteRe * this.parteRe + this.parteIm * this.parteIm);
	}

	public double getAngulo(boolean coordenadasRadialesEnGrados) {
		// si coordenadasRadialesEnGrados = true, el angulo está en grados, si no, está en radianes
		if (coordenadasRadialesEnGrados) {
			// El angulo esta en grados
			return Math.toDegrees(Math.atan2(this.parteIm, this.parteRe));

		} else {
			// El angulo esta en radianes
			return Math.atan2(this.parteIm, this.parteRe);
		}
	}
	
	@Override
	public String toString() {
		// Debido a que double tiene problemas de precisión, uso estos valores auxiliares para que si alguna de
		// ambas partes es casi casi cero, la redondee a cero y elimine el término a la hora de representar el número
		// como String. Pongo el límite en que sea menor que 1.0E-16.
		double parteRealAux = (Math.abs(this.parteRe) < 1.0E-16 ? 0.0 : this.parteRe);
		double parteImaginariaAux = (Math.abs(this.parteIm) < 1.0E-16 ? 0.0 : this.parteIm);
		
		// Representa cada parte del numero con este formato decimal:
		DecimalFormat formateador = new DecimalFormat("#############0.#########");
		String ret = "";
		
		if (parteRealAux != 0.0) {
			ret = "" + formateador.format(this.parteRe);
		}
		if (parteImaginariaAux > 0.0) {
			if (ret.length() != 0) {
				ret = ret + " + ";
			}
			if (this.parteIm != 1.0) {
				ret = ret + formateador.format(this.parteIm);
			}
			ret = ret + "i";
		} else if (parteImaginariaAux < 0.0) {
			if (ret.length() != 0) {
				ret = ret + " - ";
			} else {
				ret = "-";
			}
			if (this.parteIm != -1.0) {
				ret = ret + formateador.format(-this.parteIm);
			}
			ret = ret + "i"; 
		}
		
		if (ret.length() == 0) {
			ret = "0";
		}
			
		return ret;
	}
}
