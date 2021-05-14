package main;

import com.koldogontzal.dibujadorfuncionescomplejas.DibujadorFuncionesPlanoComplejo;
import com.koldogontzal.numeroscomplejos.FuncionesComplejas;
import com.koldogontzal.numeroscomplejos.NumeroComplejo;

public class Pruebas {
	public static void main(String[] args) {
		DibujadorFuncionesPlanoComplejo dib = new DibujadorFuncionesPlanoComplejo(new NumeroComplejo(0.2,-2), new NumeroComplejo(3,1), true, true);
		
		dib.mostrar();
		
		NumeroComplejo z = new NumeroComplejo(-30,-0.1);
		System.out.println("Logaritmo neperiano de " + z + ": " + FuncionesComplejas.logaritmo(z));
				
	}

}
