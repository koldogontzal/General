package ejemplosfunciones;

import com.koldogontzal.numeroscomplejos.FuncionComplejaMonoparametro;
import com.koldogontzal.numeroscomplejos.FuncionesComplejas;
import com.koldogontzal.numeroscomplejos.NumeroComplejo;

public class FuncionComplejaMyMathSolutions_2021_04_19 implements FuncionComplejaMonoparametro {

	@Override
	public NumeroComplejo valor(NumeroComplejo z) {
		NumeroComplejo ret = FuncionesComplejas.exponencial(FuncionesComplejas.sin(z));
		ret = FuncionesComplejas.suma(ret,
				FuncionesComplejas.multiplicacion(NumeroComplejo.const_neg1, FuncionesComplejas.exponencial(
						FuncionesComplejas.multiplicacion(NumeroComplejo.const_neg1, FuncionesComplejas.sin(z)))));
		ret = FuncionesComplejas.suma(ret, new NumeroComplejo(-4, 0));
		return ret;
	}

}
