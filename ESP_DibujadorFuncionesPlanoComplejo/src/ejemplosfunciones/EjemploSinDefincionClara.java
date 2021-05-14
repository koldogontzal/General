package ejemplosfunciones;

import com.koldogontzal.numeroscomplejos.FuncionComplejaMonoparametro;
import com.koldogontzal.numeroscomplejos.FuncionesComplejas;
import com.koldogontzal.numeroscomplejos.NumeroComplejo;

public class EjemploSinDefincionClara implements FuncionComplejaMonoparametro {

	@Override
	public NumeroComplejo valor(NumeroComplejo z) {
		NumeroComplejo ret, a, b;
		a = new NumeroComplejo(z);
		b = FuncionesComplejas.multiplicacionEscalar(2.0,
				FuncionesComplejas.raizCuadrada(FuncionesComplejas.suma(z, NumeroComplejo.const_neg1)));

		ret = FuncionesComplejas.raizCuadrada(FuncionesComplejas.suma(a, b));
		ret = FuncionesComplejas.suma(ret, FuncionesComplejas.raizCuadrada(FuncionesComplejas.resta(a, b)));
		return ret;
	}

}
