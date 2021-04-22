package main;

import com.koldogontzal.numeroscomplejos.FuncionesComplejas;
import com.koldogontzal.numeroscomplejos.NumeroComplejo;

public class MainPruebas {
	public static void main(String[] args) {
		
	NumeroComplejo z1 = FuncionesComplejas.const_1;
	NumeroComplejo z2 = FuncionesComplejas.multiplicacionCompleja(FuncionesComplejas.const_i, FuncionesComplejas.toNumeroComplejo(2.5));
	
	System.out.println(FuncionesComplejas.exponencialCompleja(FuncionesComplejas.sumaCompleja(z1, z2)));
	
	NumeroComplejo z;
	for (double y=-1.0; y<= 3.5; y=y+0.01) {
		z=new NumeroComplejo(y, -1.0);
		System.out.println("El número " + z + " tiene de seno el número " + FuncionesComplejas.sinComplejo(z)); 
		System.out.println("El número " + z + " tiene de coseno el número " + FuncionesComplejas.cosComplejo(z)); 
		System.out.println("El número " + z + " tiene de tangente el número " + FuncionesComplejas.tanCompleja(z)); 
		}				

	
	System.out.println("Funciones exponenciales:");
	z=new NumeroComplejo(1,10);
	
	System.out.println("e elevado a " + z + " da por una lado " + FuncionesComplejas.exponencialCompleja(z) +". Por otro lado da " + FuncionesComplejas.exponenteComplejo(FuncionesComplejas.toNumeroComplejo(Math.exp(1)), z));
	
	System.out.println("Cáculo del inverso de un número complejo:");
	z=new NumeroComplejo(-0,1);
	System.out.println("El número " + z + " tiene de inverso " + FuncionesComplejas.inversoComplejo(z) + " y por otro lado da " + FuncionesComplejas.exponenteComplejo(z, FuncionesComplejas.const_neg1));
	System.out.println("El inverso del inverso es: "  + FuncionesComplejas.exponenteComplejo(FuncionesComplejas.inversoComplejo(z), FuncionesComplejas.const_neg1));
	System.out.println("En esta caso, la parte real es " + FuncionesComplejas.exponenteComplejo(FuncionesComplejas.inversoComplejo(z), FuncionesComplejas.const_neg1).getParteReal());
	
	System.out.println("Ejemplo: " + FuncionesComplejas.const_0);
	System.out.println("Ejemplo: " + FuncionesComplejas.const_1);
	System.out.println("Ejemplo: " + FuncionesComplejas.const_neg1);
	System.out.println("Ejemplo: " + FuncionesComplejas.const_i);
	System.out.println("Ejemplo: " + new NumeroComplejo(02, -1));
	
	NumeroComplejo [][] z3 = new NumeroComplejo[1000][1000];
		for (int i = 0; i < z3.length; i++) {
			for (int j = 0; j < z3[0].length; j++) {
				z=new NumeroComplejo((i / 100.0) - 5.0, (j / 100.0) - 5.0);
				z3[i][j] = new NumeroComplejo(funcionComplejaEjemplo(z));
				
				if (z3[i][j].getRadio() < 1.0E-2) {
					System.out.println("Posible cero (" + z3[i][j] + ") en z = " + z + ".");
				}
			}
		}
		
	System.out.println("Hecho.");

	}
	
	private static NumeroComplejo funcionComplejaEjemplo(NumeroComplejo z) {
		NumeroComplejo ret = FuncionesComplejas
				.exponencialCompleja(FuncionesComplejas.sinComplejo(z));
		ret = FuncionesComplejas.sumaCompleja(ret, FuncionesComplejas
				.multiplicacionCompleja(FuncionesComplejas.const_neg1,
						FuncionesComplejas
								.exponencialCompleja(FuncionesComplejas
										.multiplicacionCompleja(
												FuncionesComplejas.const_neg1,
												FuncionesComplejas
														.sinComplejo(z)))));
		ret = FuncionesComplejas.sumaCompleja(ret, new NumeroComplejo(-4, 0));
		return ret;
	}
}
