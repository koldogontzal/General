package listaCircular;

/**
 * Una <code>ListaCircular</code> es un array de objetos E, de un tama�o
 * m�ximo dado con el constructor, en el que los elementos se van a�adiendo hasta un
 * m�ximo dado (el tama�o del buffer). A partir de ese momento, los elementos se
 * siguen a�adiendo a la <code>ListaCircular</code>, pero van
 * sobreescribiendo a los elementos m�s viejos de la <code>ListaCircular</code>.
 * A trav�s de los m�todos <code>actual</code>, <code>anterior</code> y
 * <code>siguiente</code> se puede recorrer la lista como si de un hist�rico
 * se tratase. Para a�adir un elemento nuevo a la <code>ListaCircular</code>
 * se utiliza el m�todo <code>add</code>.
 * 
 * 
 * @author Luis G. Castellano
 * 
 * @param <E>
 *            La clase de objetos que formar� la lista.
 */
public class ListaCircular<E> {

	private E[] elementos;
	
	private int posIni = 0; // Donde empieza el buffer de elementos. Es el m�s antiguo
	
	private int posFin = -1; // Donde acaba el buffer de elementos. Es el m�s nuevo.
								// Se inicializa con un valor ilegal para indicar
								// que la lista est� vac�a
	
	private int posAct = -1; // Posici�n actual que devuelve el elemento de la lista
								// que puede estar entre posIni y posFin
	
	@SuppressWarnings("unchecked")
	public ListaCircular(int tamagno) {
		this.elementos = (E[]) new Object[tamagno];
		for (int i = 0; i < tamagno; i++) {
			this.elementos[i] = null;
		}
	}
	
	/**
	 * M�todo que a�ade a la lista circular un nuevo elemento. Hay que pasar
	 * como par�metro un <code>.clone()</code> del Objeto
	 * <code>E elemento</code> original.
	 * 
	 * @param elemento
	 *            Tiene que ser ya un objeto clonado del original, porque si no,
	 *            este m�todo no lo clona
	 */
	public void addClone(E elemento) {
		// La lista queda truncada al alemento actual, se borrar�n los
		// siguientes a �l (los m�s nuevos)
		this.posFin = this.posAct;
		if (this.posFin == this.retrocederPos(this.posIni)) {
			// Caso en el que el buffer est� lleno y hay que avanzar posIni
			this.posIni = this.avanzarPos(this.posIni);
		}
		this.posFin = avanzarPos(this.posFin);
		this.posAct = this.posFin;
		this.elementos[this.posFin] = elemento;
		this.liberarNoUsados();
	}

	public E actual() {
		if (this.posAct != -1) {
			return this.elementos[this.posAct];
		} else {
			System.out
					.println("ERROR ListaCircular.actual(): La lista est� vac�a.");
			return null;
		}
	}

	public E anterior() {
		if ((this.posAct != -1) && (!this.esPrimero())) {
			this.posAct = retrocederPos(this.posAct);
		}
		return this.actual();
	}

	public E siguiente() {
		if ((this.posAct != -1) && (!this.esUltimo())) {
			this.posAct = avanzarPos(this.posAct);
		}
		return this.actual();
	}

	public boolean esUltimo() {
		return this.posAct == this.posFin;
	}

	public boolean esPrimero() {
		return this.posAct == this.posIni;
	}

	private int avanzarPos(int valor) {
		return ((valor + 1) % this.elementos.length);
	}

	private int retrocederPos(int valor) {
		return ((valor + this.elementos.length - 1) % this.elementos.length);
	}

	private void liberarNoUsados() {
		int contador = avanzarPos(this.posFin);
		while (contador != this.posIni) {
			this.elementos[contador] = null;
			contador = avanzarPos(contador);
		}
	}
}
