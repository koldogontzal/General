package main;

import com.koldogontzal.numeroscomplejos.FuncionesComplejas;
import com.koldogontzal.numeroscomplejos.NumeroComplejo;

public class MainPruebas {
	public static void main(String[] args) {

		NumeroComplejo z1 = FuncionesComplejas.const_1;
		NumeroComplejo z2 = FuncionesComplejas.multiplicacion(FuncionesComplejas.const_i,
				FuncionesComplejas.toNumeroComplejo(2.5));

		System.out.println(FuncionesComplejas.exponencial(FuncionesComplejas.suma(z1, z2)));

		NumeroComplejo z;
		for (double y = -1.0; y <= 3.5; y = y + 0.01) {
			z = new NumeroComplejo(y, -1.0);
			System.out.println("El número " + z + " tiene de seno el número " + FuncionesComplejas.sin(z));
			System.out.println("El número " + z + " tiene de coseno el número " + FuncionesComplejas.cos(z));
			System.out.println("El número " + z + " tiene de tangente el número " + FuncionesComplejas.tan(z));
		}

		System.out.println("Funciones exponenciales:");
		z = new NumeroComplejo(1, 10);

		System.out.println(
				"e elevado a " + z + " da por una lado " + FuncionesComplejas.exponencial(z) + ". Por otro lado da "
						+ FuncionesComplejas.potencia(FuncionesComplejas.toNumeroComplejo(Math.exp(1)), z));

		System.out.println("Cáculo del inverso de un número complejo:");
		z = new NumeroComplejo(-0, 1);
		System.out.println("El número " + z + " tiene de inverso " + FuncionesComplejas.inverso(z)
				+ " y por otro lado da " + FuncionesComplejas.potencia(z, FuncionesComplejas.const_neg1));
		System.out.println("El inverso del inverso es: "
				+ FuncionesComplejas.potencia(FuncionesComplejas.inverso(z), FuncionesComplejas.const_neg1));
		System.out.println("En esta caso, la parte real es "
				+ FuncionesComplejas.potencia(FuncionesComplejas.inverso(z), FuncionesComplejas.const_neg1).getRe());

		System.out.println("Ejemplo: " + FuncionesComplejas.const_0);
		System.out.println("Ejemplo: " + FuncionesComplejas.const_1);
		System.out.println("Ejemplo: " + FuncionesComplejas.const_neg1);
		System.out.println("Ejemplo: " + FuncionesComplejas.const_i);
		System.out.println("Ejemplo: " + new NumeroComplejo(02, -1));

		int tamanoArray = 2000;
		double x0 = -4.0;
		double x1 = 8.0;
		double y0 = -2.0;
		double y1 = 2.0;

		double dx = (x1 - x0) / (double) (tamanoArray - 1);
		double dy = (y1 - y0) / (double) (tamanoArray - 1);

		double margenError = 3 * Math.min(dx, dy);
		System.out.println("Eje X (parte real) desde " + x0 + " hasta " + x1 + " con una paso de " + dx + ".");
		System.out.println("Eje Y (parte imaginaria) desde " + y0 + " hasta " + y1 + " con una paso de " + dy + ".");

		NumeroComplejo[][] z3 = new NumeroComplejo[tamanoArray][tamanoArray];
		NumeroComplejo minVal, minPos, maxVal, maxPos;
		double minValRadio = 0, maxValRadio = 0;
		minVal = FuncionesComplejas.const_0;
		maxVal = FuncionesComplejas.const_0;
		minPos = FuncionesComplejas.const_0;
		maxPos = FuncionesComplejas.const_0;

		for (int i = 0; i < tamanoArray; i++) {
			double parteReal = x0 + i * dx;
			for (int j = 0; j < tamanoArray; j++) {

				double parteImaginaria = y0 + j * dy;
				z = new NumeroComplejo(parteReal, parteImaginaria);
				z3[i][j] = new NumeroComplejo(funcionComplejaMyMathSolutions_2021_04_19(z));
				double radio = z3[i][j].getModulo();

				// Busca los valores máximo y mínimo
				if ((i == 0) && (j == 0)) {
					minVal = new NumeroComplejo(z3[i][j]);
					minValRadio = minVal.getModulo();
					minPos = new NumeroComplejo(z);
					maxVal = new NumeroComplejo(z3[i][j]);
					maxValRadio = maxVal.getModulo();
					maxPos = new NumeroComplejo(z);
				} else {
					if (radio < minValRadio) {
						minVal = new NumeroComplejo(z3[i][j]);
						minValRadio = radio;
						minPos = new NumeroComplejo(z);
					}
					if (radio > maxValRadio) {
						maxVal = new NumeroComplejo(z3[i][j]);
						maxValRadio = radio;
						maxPos = new NumeroComplejo(z);
					}
				}

				// Busca posibles ceros
				if (radio < margenError) {
					System.out.println("Posible cero (" + z3[i][j] + ") en z = " + z + ".");
				}
			}
		}

		System.out.println("El valor mínimo es " + minVal + " (de radio " + minValRadio + ") situado en " + minPos);
		System.out.println("El valor máximo es " + maxVal + " (de radio " + maxValRadio + ") situado en " + maxPos);

		System.out.println("Hecho.");

		for (double x = -10.0; x <= 3.5; x = x + 0.1) {
			z = new NumeroComplejo(x, 0);
			System.out.println("El número " + z + " tiene de función de MyMathSolutions "
					+ funcionComplejaMyMathSolutions_2021_04_24(z));
			;
		}

		z= new NumeroComplejo(-8, 6);
		System.out.println("El número " + z + ", tiene de raíz cuadrada el valor " + FuncionesComplejas.raizCuadrada(z) + ".");
		
		z= FuncionesComplejas.conjugado(z);
		System.out.println("El número " + z + ", tiene de raíz cuadrada el valor " + FuncionesComplejas.raizCuadrada(z) + ".");
	
	}

	private static NumeroComplejo funcionComplejaMyMathSolutions_2021_04_19(NumeroComplejo z) {

		NumeroComplejo ret = FuncionesComplejas.exponencial(FuncionesComplejas.sin(z));
		ret = FuncionesComplejas.suma(ret,
				FuncionesComplejas.multiplicacion(FuncionesComplejas.const_neg1, FuncionesComplejas.exponencial(
						FuncionesComplejas.multiplicacion(FuncionesComplejas.const_neg1, FuncionesComplejas.sin(z)))));
		ret = FuncionesComplejas.suma(ret, new NumeroComplejo(-4, 0));
		return ret;

	}

	private static NumeroComplejo funcionComplejaMyMathSolutions_2021_04_24(NumeroComplejo z) {
		NumeroComplejo ret, a, b;
		a = new NumeroComplejo(z);
		b = FuncionesComplejas.multiplicacionEscalar(2.0,
				FuncionesComplejas.raizCuadrada(FuncionesComplejas.suma(z, FuncionesComplejas.const_neg1)));

		ret = FuncionesComplejas.raizCuadrada(FuncionesComplejas.suma(a, b));
		ret = FuncionesComplejas.suma(ret, FuncionesComplejas.raizCuadrada(FuncionesComplejas.resta(a, b)));
		return ret;
	}
}
