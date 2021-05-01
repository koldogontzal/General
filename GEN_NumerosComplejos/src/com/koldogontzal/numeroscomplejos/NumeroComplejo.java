package com.koldogontzal.numeroscomplejos;

import java.text.DecimalFormat;

import com.koldogontzal.geometria2d.Punto;


public class NumeroComplejo {
	private double parteRe;
	private double parteIm;
	
	/**
	 * Crea un nuevo objeto NumeroComplejo.
	 * 
	 * @param parteReal
	 * Parte real (Re) del n�mero.
	 * @param parteImaginaria
	 * Parte imaginaria (Im) del n�mero.
	 */
	public NumeroComplejo(double parteReal, double parteImaginaria) {
		this.parteRe = parteReal;
		this.parteIm = parteImaginaria;
	}

	/**
	 * Crea un nuevo objeto NumeroComplejo que es igual al NumeroComplejo z del par�metro.
	 * 
	 * @param z
	 * NumeroComplejo que se va a duplicar.
	 */
	public NumeroComplejo(NumeroComplejo z) {
		this.parteRe = z.parteRe;
		this.parteIm = z.parteIm;
	}

	/**
	 * Crea un nuevo objeto NumeroComplejo a partir de sus coordenadas radiales.
	 * @param coordenadasRadialesEnGrados
	 * Boolean que indica si el �ngulo se expresa en grados (true) o en radianes (false).
	 * @param modulo
	 * M�dulo del n�mero complejo.
	 * @param angulo
	 * Angulo del n�mero complejo.
	 */
	public NumeroComplejo(boolean coordenadasRadialesEnGrados, double modulo, double angulo) {
		// si coordenadasRadialesEnGrados = true, el angulo est� en grados, si no, est� en radianes
		if (coordenadasRadialesEnGrados) {
			// El angulo esta en grados
			this.parteRe = modulo * Math.cos(Math.toRadians(angulo));
			this.parteIm = modulo * Math.sin(Math.toRadians(angulo));
		} else {
			// El angulo esta en radianes
			this.parteRe = modulo * Math.cos(angulo);
			this.parteIm = modulo * Math.sin(angulo);
		}
	}

	/**
	 * Crea un nuevo objeto NumeroComplejo que es equivalente al objeto Punto p del par�metro. El eje X equivale a la parte real y el eje Y equivale a la parte imaginaria.
	 * 
	 * @param p
	 * Objeto Punto equivalente.
	 */
	public NumeroComplejo(Punto p) {
		this.parteRe = p.getX();
		this.parteIm = p.getY();
	}
	
	/**
	 * M�todo que devuelve la parte real (Re) del n�mero complejo desde el que se invoca.
	 * @return
	 * La parte real (Re) del n�mero complejo.
	 */
	public double getRe() {
		return this.parteRe;
	}

	/**
	 * M�todo que devuelve la parte imaginaria (Im) del n�mero complejo desde el que se invoca.
	 * @return
	 * La parte imaginaria (Im) del n�mero complejo.
	 */
	public double getIm() {
		return this.parteIm;
	}

	/**
	 * M�todo que devuelve el m�dulo (o radio) del n�mero complejo desde el que se invoca.
	 * @return
	 * El m�dulo del n�mero complejo.
	 */
	public double getModulo() {
		return Math.sqrt(this.parteRe * this.parteRe + this.parteIm * this.parteIm);
	}

	/**
	 * M�todo que devuelve el �ngulo del n�mero complejo desde el que se invoca.
	 * @param coordenadasRadialesEnGrados
	 * Boolean que indica si el �ngulo se expresa en grados (true) o en radianes (false).
	 * @return
	 * El �ngulo del n�mero complejo.
	 */
	public double getAngulo(boolean coordenadasRadialesEnGrados) {
		// si coordenadasRadialesEnGrados = true, el angulo est� en grados, si no, est� en radianes
		if (coordenadasRadialesEnGrados) {
			// El angulo esta en grados
			return Math.toDegrees(Math.atan2(this.parteIm, this.parteRe));

		} else {
			// El angulo esta en radianes
			return Math.atan2(this.parteIm, this.parteRe);
		}
	}
	
	/**
	 * M�todo que devuelve un objeto Punto, definido en el proyecto GEN_Geometria, para su posterior representaci�n en el Conversor.
	 * @return
	 * El objeto Punto que equivale al n�mero zomplejo.
	 */
	public Punto toPunto() {
		return new Punto(this.parteRe, this.parteIm);
	}
	
	/**
	 * 
	 * @param d
	 * N�mero real
	 * 
	 * @return
	 * Devuelve el NumeroComplejo que equivale al n�mero real d. 
	 */
	public static NumeroComplejo cast(double d) {
		return new NumeroComplejo(d, 0);
	}

	/**
	 * 
	 * @param d
	 * N�mero real
	 * 
	 * @return
	 * Devuelve el NumeroComplejo que equivale al n�mero real d. 
	 */
	public static NumeroComplejo cast(float d) {
		return new NumeroComplejo(d, 0);
	}

	/**
	 * 
	 * @param d
	 * N�mero real
	 * 
	 * @return
	 * Devuelve el NumeroComplejo que equivale al n�mero real d. 
	 */
	public static NumeroComplejo cast(int d) {
		return new NumeroComplejo(d, 0);
	}

	@Override
	public String toString() {
		// Debido a que double tiene problemas de precisi�n, uso estos valores auxiliares para que si alguna de
		// ambas partes es casi casi cero, la redondee a cero y elimine el t�rmino a la hora de representar el n�mero
		// como String. Pongo el l�mite en que sea menor que 1.0E-16.
		double parteRealAux = (Math.abs(this.parteRe) < 1.0E-16 ? 0.0 : this.parteRe);
		double parteImaginariaAux = (Math.abs(this.parteIm) < 1.0E-16 ? 0.0 : this.parteIm);
		
		// Representa cada parte del numero con este formato decimal:
		DecimalFormat formateador = new DecimalFormat("0.#########");
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
