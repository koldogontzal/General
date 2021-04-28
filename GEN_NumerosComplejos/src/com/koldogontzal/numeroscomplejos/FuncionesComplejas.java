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

	/**
	 * 
	 * @param z
	 * 	Numero complejo original
	 * @return
	 * Devuelve el conjugado del número z, de forma que si z = a + ib, el conjugado será a - ib.
	 */
	public static NumeroComplejo conjugado(NumeroComplejo z) {
		return new NumeroComplejo(z.getRe(), -z.getIm());
	}
	
	/**
	 * 
	 * @param z1
	 *            Primer sumando.
	 * @param z2
	 *            Segundo sumando.
	 * @return Devuelve el valor de la suma z1 + z2.
	 */
	public static NumeroComplejo suma(NumeroComplejo z1, NumeroComplejo z2) {
		// Suma z1 + z2
		return new NumeroComplejo(z1.getRe() + z2.getRe(), z1.getIm() + z2.getIm());
	}

	/**
	 * 
	 * @param z1
	 *            Sumando.
	 * @param z2
	 *            Restando.
	 * @return Devuelve el valor de la resta z1 - z2.
	 */
	public static NumeroComplejo resta(NumeroComplejo z1, NumeroComplejo z2) {
		return new NumeroComplejo(z1.getRe() - z2.getRe(), z1.getIm() - z2.getIm());
	}

	/**
	 * 
	 * @param z
	 *            Número del que se quiere obtener el opuesto.
	 * @return Devuelve el opuesto del número z, es decir, -z, de manera que z + el
	 *         opuesto (-z) = 0.
	 */
	public static NumeroComplejo opuesto(NumeroComplejo z) {
		return new NumeroComplejo(-z.getRe(), -z.getIm());
	}

	/**
	 * 
	 * @param z1
	 *            Primer factor.
	 * @param z2
	 *            Segundo factor.
	 * @return
	 * 
	 * 		Devuelve el resultado de multiplicar z1 * z2.
	 */
	public static NumeroComplejo multiplicacion(NumeroComplejo z1, NumeroComplejo z2) {
		// return new NumeroComplejo(false, z1.getRadio() * z2.getRadio(),
		// z1.getAngulo(false) + z2.getAngulo(false)); // Matemáticamente esta forma es
		// equivalente, pero es más costosa en tiempo de cálculo, así que la desecho.
		return new NumeroComplejo(z1.getRe() * z2.getRe() - z1.getIm() * z2.getIm(),
				z1.getRe() * z2.getIm() + z1.getIm() * z2.getRe());
	}

	/**
	 * 
	 * @param a
	 *            Número real escalar para ser el primer factor. Debe ser de tipo
	 *            double.
	 * @param z
	 *            Número complejo para ser el segundo factor.
	 * @return Devuelve el resultado de multiplicar a * z.
	 */
	public static NumeroComplejo multiplicacionEscalar(double a, NumeroComplejo z) {
		return multiplicacion(new NumeroComplejo(a, 0.0), z);
	}

	/**
	 * 
	 * @param z
	 *            Número del que se quiere obtener el inverso.
	 * @return Devuelve el inverso del número z, es decir 1/z. De manera que al
	 *         multiplicar z por el inverso se obtiene el valor 1.
	 */
	public static NumeroComplejo inverso(NumeroComplejo z) {
		// Calcula 1/z
		return new NumeroComplejo(false, 1.0 / z.getModulo(), -z.getAngulo(false));
	}

	/**
	 * 
	 * @param z1
	 *            Dividendo.
	 * @param z2
	 *            Divisor.
	 * @return
	 *
	 * 		Devuelve el resultado de dividir z1 por z2, es decir z1 / z2.
	 */
	public static NumeroComplejo division(NumeroComplejo z1, NumeroComplejo z2) {
		return multiplicacion(z1, inverso(z2));
	}

	/**
	 * 
	 * @param z
	 *            Exponente.
	 * @return
	 *
	 * 		Devuelve e (número de Euler) elevado al valor z.
	 */
	public static NumeroComplejo exponencial(NumeroComplejo z) {
		return new NumeroComplejo(false, Math.exp(z.getRe()), z.getIm());
	}

	/**
	 * 
	 * @param z1
	 *            Base de la potencia.
	 * @param z2
	 *            Exponente de la potencia.
	 * @return
	 *
	 * 		Devuelve el numero z1 elevado a la potencia de z2.
	 */
	public static NumeroComplejo potencia(NumeroComplejo z1, NumeroComplejo z2) {
		double log_nep_radio_z1 = Math.log(z1.getModulo());
		double angulo_z1 = z1.getAngulo(false);
		return new NumeroComplejo(false, Math.exp(z2.getRe() * log_nep_radio_z1 - angulo_z1 * z2.getIm()),
				angulo_z1 * z2.getRe() + z2.getIm() * log_nep_radio_z1);
	}

	/**
	 * @param z
	 *            Número del que se quiere obtener la raíz cuadrada.
	 * @return
	 * 
	 * 		Devuelve la primera solución de la raíz cuadrada de z, a. De forma
	 *         que a ^ 2 = z.
	 */
	public static NumeroComplejo raizCuadrada(NumeroComplejo z) {
		return raiz(z, 2, 0);
	}

	/**
	 * 
	 * @param z
	 *            Número del que se quiere obtener la raíz cúbica.
	 * @return
	 * 
	 * 		Devuelve la primera solución de la raíz cúbica de z, a. De forma que
	 *         a ^ 3 = z
	 * 
	 */
	public static NumeroComplejo raizCubica(NumeroComplejo z) {
		return raiz(z, 3, 0);
	}

	/**
	 * 
	 * @param z
	 *            Número del que se quiere obtener la raíz.
	 * @param n
	 *            Orden de la raíz, siendo siempre un número entero mayor o igual
	 *            que 2.
	 * @param m
	 *            Índice de la solución obtenida, siendo m=0, 1, 2 ... n-1.
	 * @return
	 * 
	 * 		Devuelve la raíz de orden n de z, de forma de si a es la solución
	 *         devuelta, se cumple que a ^ n = z. En general para n>=2 hay n
	 *         soluciones distintas: a0, a1, a2 ... a(n-1). Para obtener a la
	 *         primera habrá que usar el parámetro m=0. Para obtener las siguientes
	 *         soluciones, m=0, 1, ..., n-1. Si m=n, entonces la solución an es
	 *         igual a la obtenida si m=0 (a0).
	 */
	public static NumeroComplejo raiz(NumeroComplejo z, int n, int m) {
		return new NumeroComplejo(false, Math.pow(z.getModulo(), 1.0 / (double) n),
				(z.getAngulo(false) + (2 * Math.PI * m)) / (double) n);
	}

	/**
	 * 
	 * @param z
	 *            Número complejo del que se quiere calcular el seno, en radianes.
	 * @return Devulve el seno complejo del número z, sin(z).
	 */
	public static NumeroComplejo sin(NumeroComplejo z) {
		NumeroComplejo ret;
		ret = exponencial(multiplicacion(const_i, z));
		ret = suma(ret, multiplicacion(const_neg1, exponencial(multiplicacion(new NumeroComplejo(0.0, -1.0), z))));
		NumeroComplejo aux = inverso(new NumeroComplejo(0.0, 2.0));
		return multiplicacion(aux, ret);
	}

	/**
	 * 
	 * @param z
	 *            Número complejo del que se quiere calcular el coseno, en radianes.
	 * @return Devulve el coseno complejo del número z, cos(z).
	 */
	public static NumeroComplejo cos(NumeroComplejo z) {
		NumeroComplejo ret;
		ret = exponencial(multiplicacion(const_i, z));
		ret = suma(ret, exponencial(multiplicacion(new NumeroComplejo(0.0, -1.0), z)));
		return multiplicacion(new NumeroComplejo(0.5, 0.0), ret);
	}

	/**
	 * 
	 * @param z
	 *            Número complejo del que se quiere calcular la tangente, en
	 *            radianes.
	 * @return Devulve la tangente compleja del número z, tan(z).
	 */
	public static NumeroComplejo tan(NumeroComplejo z) {
		return division(sin(z), cos(z));
	}
}
