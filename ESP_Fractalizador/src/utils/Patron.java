package utils;

import java.util.ArrayList;

public class Patron {

	// Es una lista de vectores que definen el patrón que luego se repetirá para
	// cada vector
	private ArrayList<VectorFrac> lista;

	public Patron() {
		this.lista = new ArrayList<VectorFrac>(5);
	}

	public Patron(int elementos) {
		this.lista = new ArrayList<VectorFrac>(elementos);
	}

	public void addVector(VectorFrac elemento) {
		this.lista.add(elemento);
	}

	public int getNumeroVectores() {
		return this.lista.size();
	}

	public VectorFrac getVector(int posicion) {
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

	public VectorFrac[] recalcularConNuevoVector(VectorFrac vectorParaTransformar) {
		VectorFrac[] listaNueva = new VectorFrac[this.getNumeroVectores()];
		for (int i = 0; i < this.getNumeroVectores(); i++) {

			Punto desplazamiento = vectorParaTransformar.getPuntoOrigen();
			double rotacion = vectorParaTransformar.getAngulo();
			double escalado = vectorParaTransformar.getLongitud(); //suponemos que el original vale 1

			//Primero hay que aplicar un escalado, luego una rotación y por ultimo un desplazamiento
			// Tiene que ser en ese orden, si no, sale mal. Y tiene que hacerse de los puntos de origen
			// y de extremo por separado
			
			// Escalado
			Punto pOrigenEscalado = this.getVector(i).getPuntoOrigen().multiplicacionEscalar(escalado);
			Punto pExtremoEscalado = this.getVector(i).getPuntoExtremo().multiplicacionEscalar(escalado);
			// Rotación
			Punto pOrigenRotado = pOrigenEscalado.girarAngulo(rotacion);
			Punto pExtremoRotado = pExtremoEscalado.girarAngulo(rotacion);
			VectorFrac vectorRotado = new VectorFrac(pOrigenRotado, pExtremoRotado);
			// Desplazamiento
			VectorFrac vectorDesplazado = vectorRotado.desplazamientoVector(desplazamiento);

			listaNueva[i] = new VectorFrac(vectorDesplazado);
		}
		return listaNueva;
	}

}
