package main;

import com.koldogontzal.dibujadorfuncionescomplejas.DibujadorFuncionesPlanoComplejo;
import com.koldogontzal.numeroscomplejos.FuncionesComplejas;
import com.koldogontzal.numeroscomplejos.NumeroComplejo;

import ejemplosfunciones.FuncionComplejaMyMathSolutions_2021_04_19;
import ejemplosfunciones.FuncionComplejaMyMathSolutions_2021_04_24;

public class Pruebas {
	public static void main(String[] args) {
		DibujadorFuncionesPlanoComplejo dib = new DibujadorFuncionesPlanoComplejo(
				new FuncionComplejaMyMathSolutions_2021_04_19(), 
				new NumeroComplejo(-8, -8), new NumeroComplejo(8, 8),
				true, true);

		dib.mostrar();

		NumeroComplejo z = new NumeroComplejo(-30, -0.2);
		System.out.println("Logaritmo neperiano de " + z + ": " + FuncionesComplejas.logaritmo(z));

	}

}
