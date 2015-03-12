package main;

import func.*;

public class EjemploDePruebaDeFunciones {
	
	public static void main(String[] args) {
		FuncionAbstracta f1 = new FuncionD();
		FuncionAbstracta f2 = new FuncionH();
		FuncionAbstracta f3 = new FuncionF();
		FuncionAbstracta f4 = new FuncionM();
		FuncionAbstracta f5 = new FuncionQ();
		FuncionAbstracta f6 = new Fibonacci();
		
		// Imprime todos los valores
		System.out.println("n\tD(n)\tH(n)\tF(n)\tM(n)\tIgual\tQ(n)\tFibo");
		for (int n = 0; n < FuncionAbstracta.TAMAGNO_BUFFER_DEFECTO; n++) {
			System.out.println(n + "\t" + f1.valor(n) + "\t" + f2.valor(n)
					+ "\t" + f3.valor(n) + "\t" + f4.valor(n) + "\t"
					+ (f3.valor(n) == f4.valor(n)) + "\t"
					+ f5.valor(n) + "\t" + f6.valor(n));
		}
		
		// Imprime solo aquellos en los que F(n) != M(n)
		System.out.println();
		System.out.println("n\tD(n)\tH(n)\tF(n)\tM(n)\tIgual\tQ(n)\tFibo");
		for (int n = 0; n < FuncionAbstracta.TAMAGNO_BUFFER_DEFECTO; n++) {
			if (f3.valor(n) != f4.valor(n)) {
				System.out.println(n + "\t" + f1.valor(n) + "\t" + f2.valor(n)
						+ "\t" + f3.valor(n) + "\t" + f4.valor(n) + "\t"
						+ (f3.valor(n) == f4.valor(n)) + "\t"
						+ f5.valor(n) + "\t" + f6.valor(n));
			}
			
		}
		
	}
}
