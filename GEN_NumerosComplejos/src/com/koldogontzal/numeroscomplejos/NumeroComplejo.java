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
		// si coordenadasRadialesEnGrados = true, el angulo est� en grados, si no, est� en radianes
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
		// si coordenadasRadialesEnGrados = true, el angulo est� en grados, si no, est� en radianes
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
		DecimalFormat formateador = new DecimalFormat("#############0.#########");
		String ret = "";
		if (this.parteReal != 0.0) {
			ret = "" + formateador.format(this.parteReal);
		}
		if (this.parteImaginaria > 0.0) {
			if (ret.length() != 0) {
				ret = ret + " + ";
			}
			if (this.parteImaginaria != 1.0) {
				ret = ret + formateador.format(this.parteImaginaria);
			}
			ret = ret + "i";
		} else if (this.parteImaginaria < 0.0) {
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