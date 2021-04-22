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

	public static NumeroComplejo sumaCompleja(NumeroComplejo z1, NumeroComplejo z2) {
		// Suma z1 + z2
		return new NumeroComplejo(z1.getParteReal() + z2.getParteReal(),
				z1.getParteImaginaria() + z2.getParteImaginaria());
	}

	public static NumeroComplejo multiplicacionCompleja(NumeroComplejo z1, NumeroComplejo z2) {
		// Multiplica z1 * z2
		//return new NumeroComplejo(false, z1.getRadio() * z2.getRadio(), z1.getAngulo(false) + z2.getAngulo(false));  // Matemáticamente esta forma es quicalente, pero es más constosa en tiempo de cálculo
		return new NumeroComplejo(z1.getParteReal() * z2.getParteReal() - z1.getParteImaginaria() * z2.getParteImaginaria(),
				z1.getParteReal() * z2.getParteImaginaria() + z1.getParteImaginaria() * z2.getParteReal());
	}

	public static NumeroComplejo inversoComplejo(NumeroComplejo z) {
		// Calcula 1/z
		return new NumeroComplejo(false, 1 / z.getRadio(), -z.getAngulo(false));
	}

	public static NumeroComplejo exponencialCompleja(NumeroComplejo z) {
		// e (número de Euler) elevado al valor z
		return new NumeroComplejo(false, Math.exp(z.getParteReal()), z.getParteImaginaria());
	}
	
	public static NumeroComplejo exponenteComplejo(NumeroComplejo z1, NumeroComplejo z2) {
		// z1 elevado a z2
		double lognepr1 = Math.log(z1.getRadio());
		return new NumeroComplejo(false, 
				Math.exp(z2.getParteReal() * lognepr1 - z1.getAngulo(false) * z2.getParteImaginaria()),
				z1.getAngulo(false) * z2.getParteReal() + z2.getParteImaginaria() * lognepr1);
	}

	public static NumeroComplejo sinComplejo(NumeroComplejo z) {
		// sin z
		NumeroComplejo ret;
		ret = exponencialCompleja(multiplicacionCompleja(const_i, z));
		ret = sumaCompleja(ret, multiplicacionCompleja(const_neg1,
				exponencialCompleja(multiplicacionCompleja(new NumeroComplejo(0.0, -1.0), z))));
		NumeroComplejo aux = inversoComplejo(new NumeroComplejo(0.0, 2.0));
		return multiplicacionCompleja(aux, ret);
	}

	public static NumeroComplejo cosComplejo(NumeroComplejo z) {
		// cos z
		NumeroComplejo ret;
		ret = exponencialCompleja(multiplicacionCompleja(const_i, z));
		ret = sumaCompleja(ret, exponencialCompleja(multiplicacionCompleja(new NumeroComplejo(0.0, -1.0), z)));
		return multiplicacionCompleja(new NumeroComplejo(0.5, 0.0), ret);
	}
	
	public static NumeroComplejo tanCompleja(NumeroComplejo z) {
		// tan z
		return multiplicacionCompleja(sinComplejo(z), inversoComplejo(cosComplejo(z)));
	}
}
