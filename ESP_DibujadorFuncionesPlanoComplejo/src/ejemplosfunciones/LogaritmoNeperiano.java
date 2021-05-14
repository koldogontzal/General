package ejemplosfunciones;

import com.koldogontzal.numeroscomplejos.FuncionComplejaMonoparametro;
import com.koldogontzal.numeroscomplejos.FuncionesComplejas;
import com.koldogontzal.numeroscomplejos.NumeroComplejo;

public class LogaritmoNeperiano implements FuncionComplejaMonoparametro {

	@Override
	public NumeroComplejo valor(NumeroComplejo z) {
		return FuncionesComplejas.logaritmo(z);
	}

}
