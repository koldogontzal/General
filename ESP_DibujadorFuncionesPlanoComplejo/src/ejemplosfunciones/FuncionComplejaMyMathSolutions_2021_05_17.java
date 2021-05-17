package ejemplosfunciones;

import com.koldogontzal.numeroscomplejos.FuncionComplejaMonoparametro;
import com.koldogontzal.numeroscomplejos.FuncionesComplejas;
import com.koldogontzal.numeroscomplejos.NumeroComplejo;

public class FuncionComplejaMyMathSolutions_2021_05_17 implements FuncionComplejaMonoparametro {

	@Override
	public NumeroComplejo valor(NumeroComplejo z) {
		NumeroComplejo z1 = NumeroComplejo.const_1;
		NumeroComplejo z2, z3;		
		z2 = FuncionesComplejas.potencia(NumeroComplejo.cast(3), FuncionesComplejas.multiplicacionEscalar(0.5, z));
		z3 = FuncionesComplejas.opuesto(FuncionesComplejas.potencia(NumeroComplejo.cast(2), z));
		return FuncionesComplejas.suma(z1, FuncionesComplejas.suma(z2, z3));
	}

}
