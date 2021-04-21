package com.koldogontzal.numeroscomplejos;

import java.text.DecimalFormat;


public class NumeroComplejo {
	private double parteReal;
	private double parteImaginaria;

	public NumeroComplejo(double parteReal, double parteImaginaria) {
		this.parteReal = parteReal;
		this.parteImaginaria = parteImaginaria;
	}

	public NumeroComplejo(NumeroComplejo z) {
		this.parteReal = z.parteReal;
		this.parteImaginaria = z.parteImaginaria;
	}

	public NumeroComplejo(boolean coordenadasRadialesEnGrados, double radio, double angulo) {
		// si coordenadasRadialesEnGrados = true, el angulo está en grados, si no, está en radianes
		if (coordenadasRadialesEnGrados) {
			// El angulo esta en grados
			this.parteReal = radio * Math.cos(Math.toRadians(angulo));
			this.parteImaginaria = radio * Math.sin(Math.toRadians(angulo));
		} else {
			// El angulo esta en radianes
			this.parteReal = radio * Math.cos(angulo);
			this.parteImaginaria = radio * Math.sin(angulo);
		}
	}

	public double getParteReal() {
		return this.parteReal;
	}

	public double getParteImaginaria() {
		return this.parteImaginaria;
	}

	public double getRadio() {
		return Math.sqrt(this.parteReal * this.parteReal + this.parteImaginaria * this.parteImaginaria);
	}

	public double getAngulo(boolean coordenadasRadialesEnGrados) {
		// si coordenadasRadialesEnGrados = true, el angulo está en grados, si no, está en radianes
		if (coordenadasRadialesEnGrados) {
			// El angulo esta en grados
			return Math.toDegrees(Math.atan2(this.parteImaginaria, this.parteReal));

		} else {
			// El angulo esta en radianes
			return Math.atan2(this.parteImaginaria, this.parteReal);
		}
	}
	
	@Override
	public String toString() {
		// Debido a que double tiene problemas de precisión, uso estos valores auxiliares para que si alguna de
		// ambas partes es casi casi cero, la redondee a cero y elimine el término a la hora de representar el número
		// como String. Pongo el límite en que sea menor que 1.0E-16.
		double parteRealAux = (Math.abs(this.parteReal) < 1.0E-16 ? 0.0 : this.parteReal);
		double parteImaginariaAux = (Math.abs(this.parteImaginaria) < 1.0E-16 ? 0.0 : this.parteImaginaria);
		
		// Representa cada parte del numero con este formato decimal:
		DecimalFormat formateador = new DecimalFormat("#############0.#########");
		String ret = "";
		
		if (parteRealAux != 0.0) {
			ret = "" + formateador.format(this.parteReal);
		}
		if (parteImaginariaAux > 0.0) {
			if (ret.length() != 0) {
				ret = ret + " + ";
			}
			if (this.parteImaginaria != 1.0) {
				ret = ret + formateador.format(this.parteImaginaria);
			}
			ret = ret + "i";
		} else if (parteImaginariaAux < 0.0) {
			if (ret.length() != 0) {
				ret = ret + " - ";
			} else {
				ret = "-";
			}
			if (this.parteImaginaria != -1.0) {
				ret = ret + formateador.format(-this.parteImaginaria);
			}
			ret = ret + "i"; 
		}
		
		if (ret.length() == 0) {
			ret = "0";
		}
			
		return ret;
	}
}
