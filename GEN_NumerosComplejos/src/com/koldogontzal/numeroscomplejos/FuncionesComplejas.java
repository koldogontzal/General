package com.koldogontzal.numeroscomplejos;

public class FuncionesComplejas {
	
	public static NumeroComplejo const_0 = FuncionesComplejas.toNumeroComplejo(0);
	public static NumeroComplejo const_1 = FuncionesComplejas.toNumeroComplejo(1);
	public static NumeroComplejo const_neg1 = FuncionesComplejas.toNumeroComplejo(-1);
	public static NumeroComplejo const_i = new NumeroComplejo(0.0, 1.0);

	public static NumeroComplejo toNumeroComplejo(int x) {
		return new NumeroComplejo((double) x, 0.0);
	}
	
	public static NumeroComplejo toNumeroComplejo(float x) {
		return new NumeroComplejo((double) x, 0.0);
	}

	public static NumeroComplejo toNumeroComplejo(double x) {
		return new NumeroComplejo(x, 0.0);
	}

	public static NumeroComplejo suma(NumeroComplejo z1, NumeroComplejo z2) {
		// Suma z1 + z2
		return new NumeroComplejo(z1.getRe() + z2.getRe(), z1.getIm() + z2.getIm());
	}
	
	public static NumeroComplejo resta(NumeroComplejo z1, NumeroComplejo z2) {
		// Resta z1 - z2
		return new NumeroComplejo(z1.getRe() - z2.getRe(), z1.getIm() - z2.getIm());
	}

	public static NumeroComplejo multiplicacion(NumeroComplejo z1, NumeroComplejo z2) {
		// Multiplica z1 * z2
		//return new NumeroComplejo(false, z1.getRadio() * z2.getRadio(), z1.getAngulo(false) + z2.getAngulo(false));  // Matemáticamente esta forma es quicalente, pero es más constosa en tiempo de cálculo
		return new NumeroComplejo(z1.getRe() * z2.getRe() - z1.getIm() * z2.getIm(),
				z1.getRe() * z2.getIm() + z1.getIm() * z2.getRe());
	}

	public static NumeroComplejo inverso(NumeroComplejo z) {
		// Calcula 1/z
		return new NumeroComplejo(false, 1 / z.getRadio(), -z.getAngulo(false));
	}
	
	public static NumeroComplejo division(NumeroComplejo z1, NumeroComplejo z2) {
		// Divide z1 / z2
	return multiplicacion(z1, inverso(z2));
	}

	public static NumeroComplejo exponencial(NumeroComplejo z) {
		// e (número de Euler) elevado al valor z
		return new NumeroComplejo(false, Math.exp(z.getRe()), z.getIm());
	}
	
	public static NumeroComplejo elevadoA(NumeroComplejo z1, NumeroComplejo z2) {
		// z1 elevado a z2
		double lognepr1 = Math.log(z1.getRadio());
		return new NumeroComplejo(false, 
				Math.exp(z2.getRe() * lognepr1 - z1.getAngulo(false) * z2.getIm()),
				z1.getAngulo(false) * z2.getRe() + z2.getIm() * lognepr1);
	}

	public static NumeroComplejo sin(NumeroComplejo z) {
		// sin z
		NumeroComplejo ret;
		ret = exponencial(multiplicacion(const_i, z));
		ret = suma(ret, multiplicacion(const_neg1,
				exponencial(multiplicacion(new NumeroComplejo(0.0, -1.0), z))));
		NumeroComplejo aux = inverso(new NumeroComplejo(0.0, 2.0));
		return multiplicacion(aux, ret);
	}

	public static NumeroComplejo cos(NumeroComplejo z) {
		// cos z
		NumeroComplejo ret;
		ret = exponencial(multiplicacion(const_i, z));
		ret = suma(ret, exponencial(multiplicacion(new NumeroComplejo(0.0, -1.0), z)));
		return multiplicacion(new NumeroComplejo(0.5, 0.0), ret);
	}
	
	public static NumeroComplejo tan(NumeroComplejo z) {
		// tan z
		return division(sin(z), cos(z));
	}
}
