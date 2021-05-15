package main;

import com.koldogontzal.dibujadorfuncionescomplejas.DibujadorFuncionesPlanoComplejo;
import com.koldogontzal.numeroscomplejos.FuncionesComplejas;
import com.koldogontzal.numeroscomplejos.NumeroComplejo;

import ejemplosfunciones.*;

public class Pruebas {
	public static void main(String[] args) {
		DibujadorFuncionesPlanoComplejo dib = new DibujadorFuncionesPlanoComplejo(
				new EjemploSinDefincionClara(), 
				new NumeroComplejo(-800, -800), new NumeroComplejo(800, 800),
				true, true);

		dib.mostrar();

		NumeroComplejo z = new NumeroComplejo(-30, -0.2);
		System.out.println("Logaritmo neperiano de " + z + ": " + FuncionesComplejas.logaritmo(z));

	}

}
