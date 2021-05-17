package main;

import com.koldogontzal.numeroscomplejos.FuncionesComplejas;
import com.koldogontzal.numeroscomplejos.NumeroComplejo;

import ejemplosfunciones.FuncionComplejaMyMathSolutions_2021_05_17;

public class PruebasSoloCalculos {
	public static void main(String[] args) {
		
		for (double i = 2.5; i<=3; i=i+0.01) {
			NumeroComplejo z1, z2;
			z1 = FuncionesComplejas.potencia(NumeroComplejo.cast(3), FuncionesComplejas.multiplicacionEscalar(i/2, NumeroComplejo.const_i));

			z2 = FuncionesComplejas.opuesto(FuncionesComplejas.potencia(NumeroComplejo.cast(2), FuncionesComplejas.multiplicacionEscalar(i, NumeroComplejo.const_i)));
		
			System.out.println("Para x=" + new NumeroComplejo(0,i) + " el segundo semando da " + z1 + " y el tercer sumando da " + z2 + " siendo la suma total " + FuncionesComplejas.suma(NumeroComplejo.const_1, FuncionesComplejas.suma(z1, z2)));
		}
	
		
		
	}
}
