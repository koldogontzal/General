package main;

import com.koldogontzal.dibujadorfuncionescomplejas.DibujadorFuncionesPlanoComplejo;
import com.koldogontzal.numeroscomplejos.FuncionesComplejas;
import com.koldogontzal.numeroscomplejos.NumeroComplejo;

import ejemplosfunciones.*;

public class Pruebas {
	public static void main(String[] args) {
		DibujadorFuncionesPlanoComplejo dib = new DibujadorFuncionesPlanoComplejo(
				new FuncionComplejaMyMathSolutions_2021_05_17(), 
				new NumeroComplejo(-3, -15), new NumeroComplejo(3, 30),
				true, true);

		dib.mostrar();

		NumeroComplejo z = new NumeroComplejo(-30, -0.2);
		System.out.println("Logaritmo neperiano de " + z + ": " + FuncionesComplejas.logaritmo(z));

	}

}
