package code;

import java.util.ArrayList;

import com.koldogontzal.geometria2d.Punto;
import com.koldogontzal.geometria2d.Vector;


public class PatronFractal {

	// Es una lista de vectores que definen el patrón que luego se repetirá para cada vector
	private ArrayList<Vector> lista;

	// Constructores
	public PatronFractal() {
		this.lista = new ArrayList<Vector>(5);
	}

	public PatronFractal(int elementos) {
		this.lista = new ArrayList<Vector>(elementos);
	}

	// Métodos
	public void addVector(Vector elemento) {
		this.lista.add(elemento);
	}

	public int getNumeroVectores() {
		return this.lista.size();
	}

	public Vector getVector(int posicion) {
		return this.lista.get(posicion);
	}

	@Override
	public String toString() {
		String cadenaFinal = "";
		for (int i = 0; i < this.getNumeroVectores(); i++) {
			cadenaFinal = cadenaFinal + this.getVector(i) + "\n";
		}
		return cadenaFinal;
	}

	public Vector[] recalcularConNuevoVector(Vector vectorParaTransformar) {
		Vector[] listaNueva = new Vector[this.getNumeroVectores()];
		for (int i = 0; i < this.getNumeroVectores(); i++) {

			Punto desplazamiento = vectorParaTransformar.getPuntoOrigen();
			double rotacion = vectorParaTransformar.getAngulo();
			double escalado = vectorParaTransformar.getModulo(); //suponemos que el original vale 1

			//Primero hay que aplicar un escalado, luego una rotación y por ultimo un desplazamiento
			// Tiene que ser en ese orden, si no, sale mal. Y tiene que hacerse de los puntos de origen
			// y de extremo por separado
			
			// Escalado
			Punto pOrigenEscalado = Punto.multiplicacionEscalar(this.getVector(i).getPuntoOrigen(), escalado);
			Punto pFinalEscalado = Punto.multiplicacionEscalar(this.getVector(i).getPuntoFinal(), escalado);
			// Rotación
			Punto pOrigenRotado = Punto.giraAngulo(pOrigenEscalado, rotacion);
			Punto pExtremoRotado = Punto.giraAngulo(pFinalEscalado, rotacion);
			Vector vectorRotado = new Vector(pOrigenRotado, pExtremoRotado);
			// Desplazamiento
			Vector vectorDesplazado = Vector.desplazamiento(vectorRotado, desplazamiento);

			listaNueva[i] = new Vector(vectorDesplazado);
		}
		return listaNueva;
	}

}
