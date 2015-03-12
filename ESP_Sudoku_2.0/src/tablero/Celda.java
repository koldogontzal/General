package tablero;

import java.io.Serializable;

import exceptions.CeldaNoAsignadaException;
import exceptions.ValorFueraRangoException;
import exceptions.ValorNoValidoException;

/**
 * Clase que implementa una <code>Celda</code> del juego del sudoku. Una <code>Celda</code> tiene asociada
 * una Coordenada en el tablero, una lista de valores posibles y si se asigna,
 * un valor determinado.
 */
public class Celda implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5719284673651662758L;
	private Posicion pos; // Esta variable contiene la posición de la Celda en
	// el tablero del juego

	private int valorCelda; // Esta variable contiene el valor que tiene la
	// celda una vez que ha sido asignada. Hasta ese momento vale -1

	private boolean[] valoresPosibles; // Tabla de booleans que indica si el
	// valor de la posición en la tabla (+1) es un valor posible dentro de esta
	// Celda

	private int numeroValoresPosibles; // Variable que lleva cuanta de cuántos
	// valores de la tabla valoresPosibles son true.

	private boolean valPosibForz; // variable que indica si se ha forzalo la
	// lista da valores posibles a un unico valor

	/**
	 * Constructor, a partir de una Coordenada que indica la posición de la
	 * <code>Celda</code> en el tablero.
	 * 
	 * @param pos
	 *            Coordenada de la <code>Celda</code>.
	 */
	public Celda(Posicion pos) {
		this.pos = pos;
		valorCelda = -1;
		valoresPosibles = new boolean[9];
		for (int i = 0; i < 9; i++) {
			valoresPosibles[i] = true; // En principio, todos los valores son
			// posibles
		}
		numeroValoresPosibles = 9;
		valPosibForz = false;
	}

	/**
	 * Método que devuelve el valor de la <code>Celda</code>, en el caso de que esta haya
	 * sido ya asignada. Si aún no lo ha sido, genera un error por pantalla.
	 * 
	 * @return int Valor asinado a la <code>Celda</code>. Su rango es [1-9], ambos inclusive.
	 *         Si aún no ha sido asignada, devuelve -1.
	 * @see Celda#putValor
	 */
	public int getValor() throws CeldaNoAsignadaException {
		// Devuelve, si la celda está asignada, el valor que contiene
		if (valorCelda != -1) {
			return valorCelda;
		} else {
			throw new CeldaNoAsignadaException(
					"ERROR: Celda.getValor(): La celda " + this.getPosicion()
							+ " aún no tiene un valor asignado.");
		}
	}

	/**
	 * Método que asigna un único valor determinado a la <code>Celda</code>. Para poder
	 * asignarlo, el valor tiene que estar dentro de la lista de valores
	 * posibles de la <code>Celda</code>. En caso de que no sea así, se genera por pantalla
	 * un mensaje de error.
	 * 
	 * @param valor
	 *            Valor que se asignará a la <code>Celda</code>. Su rango es [1-9], ambos
	 *            inclusive.
	 * @see Celda#getValor
	 */
	public void putValor(int valor) throws ValorNoValidoException,
			ValorFueraRangoException {
		if ((valor > 0) && (valor < 10)) {
			if (valoresPosibles[valor - 1]) {
				// Rango de valor correcto y además es un valor posible
				valorCelda = valor;
				for (int i = 0; i < 9; i++) {
					valoresPosibles[i] = false;
				}
				numeroValoresPosibles = 0;
				valPosibForz = false;
			} else {
				throw new ValorNoValidoException(
						"ERROR Celda.putValor(): El numero " + valor
								+ " no puede ir en la celda "
								+ this.getPosicion() + ".");
			}
		} else {
			throw new ValorFueraRangoException(
					"ERROR Celda.putValor(): El numero " + valor
							+ " esta fuera del rango [1-9].");
		}
	}

	/**
	 * Método que dice si la <code>Celda</code> ha sido ya asignada o no.
	 * 
	 * @return boolean Indica el estado de la <code>Celda</code>, si ha sido asignada.
	 */
	public boolean esCeldaAsignada() {
		return (valorCelda != -1);
	}

	/**
	 * Método que dice si un determinado valor está dentro de la lista de
	 * valores permitidos de la <code>Celda</code>
	 * 
	 * @return boolean Indica si es cierto que valor sea un valor posible en la
	 *         <code>Celda</code>
	 */
	public boolean esValorPosible(int valor) {
		return valoresPosibles[valor - 1];
	}

	/**
	 * Método que devuelve el número de valores posibles de la <code>Celda</code>
	 * 
	 * @return int Si la <code>Celda</code> no ha sido asignada, devuelve el número de
	 *         valores posibles en la <code>Celda</code>. Su rango es [1-9], ambos inclusive.
	 *         Si la <code>Celda</code> ha sido asignada, devuelve 0.
	 */
	public int numValoresPosibles() {
		return numeroValoresPosibles;
	}

	/**
	 * Método que quita un valor de la lista de valores posibles de la
	 * <code>Celda</code>. Genera un error si el valor está fuera de rango.
	 * 
	 * @param valor
	 *            Valor a quitar de la lista de valores posibles. Su rango es
	 *            [1-9], ambos inclusive.
	 */
	public void quitarValorPosible(int valor) throws ValorNoValidoException,
			ValorFueraRangoException {
		if ((valor > 0) && (valor < 10)) {
			if (valoresPosibles[valor - 1]) {
				// el valor está en el rango permitido y además, aún es un valor
				// posible de la Celda
				if (numeroValoresPosibles > 1) {
					// Aún hay más valores posibles dentro de la lista, con lo
					// que quitamos éste sin problema.
					valoresPosibles[valor - 1] = false;
					numeroValoresPosibles--;
				} else {
					// Serio problema, porque estamos intentando quitar el
					// último valor posible. Esto no debería ocurrir nunca.
					// Si ocurre, significa que alguno de los valores que 
					// ha ido introduciendo el usuario es erróneo y fuerza
					// al tablero a intentar quitar el último valor posible
					// de la celda
					throw new ValorNoValidoException(
							"ERROR Celda.quitarValorPosible(): El número "
									+ valor
									+ " es el único que queda como valor posible en "
									+ this.getPosicion() + "y no puede"
									+ " quitarse de la lista.");
				}
			} 
		} else {
			throw new ValorFueraRangoException(
					"ERROR Celda.quitarValorPosible(): El numero " + valor
							+ " esta fuera del rango [1-9].");
		}
	}

	/**
	 * Método que dice si en una <code>Celda</code> sin asignar hay un único valor posible
	 * 
	 * @return boolean Cierto si la <code>Celda</code> está aún sin asignar y tiene un único
	 *         valor posible. En otro caso, falso.
	 */
	public boolean esSoloUnValorPosible() {
		return (numeroValoresPosibles == 1);
	}

	/**
	 * Método que, en caso de que sólo haya un único valor posible para la
	 * <code>Celda</code>, devuelve dicho valor.
	 * 
	 * @return int Único valor posible para la <code>Celda</code>, que aún debe estar sin
	 *         asignar. Su rango es [1-9], ambos inclusive. Si existen más de un
	 *         único valor posible, genera un error por pantalla y devuelve -1.
	 *         Si la <code>Celda</code> está asignada, genera un error por pantalla y
	 *         devuelve 0.
	 */
	public int unicoValorPosible() {
		if (esCeldaAsignada()) {
			System.out
					.println("ERROR Celda.unicoValorPosible(): La celda ya esta asignada");
			return 0;
		}
		int retorno = 0;
		for (int i = 0; i < 9; i++) {
			if (valoresPosibles[i]) {
				retorno = i + 1;
			}
		}
		if (esSoloUnValorPosible()) {
			return retorno;
		} else {
			System.out
					.println("ERROR Celda.unicoValorPosible(): Se intenta leer el valor de una celda que tiene mas de un unico valor posible");
			return -1;
		}
	}

	/**
	 * Método que fuerza la lista de valores posibles a un único valor. La <code>Celda</code>
	 * ha de estar sin asignar y dicho valor, debe estar entre la lista de
	 * valores posibles. En caso contrario, genera un mensaje de error por la
	 * pantalla.
	 * 
	 * @param valor
	 *            Valor al que se fuerza la lista de valores posibles. Su rango
	 *            es [1-9], ambos inclusive.
	 */
	public void fuerzaUnValorPosible(int valor) throws ValorNoValidoException, ValorFueraRangoException {
		if (esCeldaAsignada()) {
			System.out
					.println("ERROR Celda.fuerzaUnValorPosible(): La celda " + this.getPosicion() + " ya está asignada y en consecuencia no se puede forzar sus valores posibles.");
		} else if ((valor > 0) && (valor <= 9)) {
			if (valoresPosibles[valor - 1]) {
				for (int i = 0; i < 9; i++) {
					valoresPosibles[i] = false;
				}
				valoresPosibles[valor - 1] = true;
				numeroValoresPosibles = 1;
				valPosibForz = true;
			} else {
				throw new ValorNoValidoException(
						"ERROR Celda.fuerzaUnValorPosible(): El valor "
								+ valor
								+ " que se quiere forzar como valor posible, no es valido en la celda "
								+ this.getPosicion() + ".");
			}
		} else {
			throw new ValorFueraRangoException ("ERROR Celda.fuerzaUnValorPosible(): El valor "
								+ valor
								+ " está fuera del rango [1-9].");
		}
	}

	/**
	 * Método que dice si la lista de valores posibles ha sido forzada a un
	 * único valor.
	 * 
	 * @return Boolean true si la lista ha sido forzada a contener un único
	 *         valor posible mediante el método fuerzaUnValorPosible().
	 */
	public boolean esValoresPosiblesForzada() {
		return valPosibForz;
	}

	public void escribirValoresPosibles() {
		for (int i = 0; i < 9; i++) {
			if (valoresPosibles[i]) {
				System.out.print(i + 1);
			}
		}
	}

	/**
	 * Método que devuelve la Coordenada de la <code>Celda</code>.
	 * @return Coordenada Coordenada de la <code>Celda</code>.
	 */
	public Posicion getPosicion() {
		return pos;
	}
	
	public Celda clone() {
		// Con esto clonamos la posición y además creamos la nueva Celda con su constructor
		Celda retorno = new Celda(this.getPosicion());
		// Clonar valorCelda
		retorno.valorCelda = this.valorCelda;
		// Clonar valoresPosibles
		for (int i = 0; i < 9; i++) {
			retorno.valoresPosibles[i] = this.valoresPosibles[i];
		}
		// Clonar numeroValoresPosibles
		retorno.numeroValoresPosibles = this.numeroValoresPosibles;
		// Clonar valPosibForz
		retorno.valPosibForz = this.valPosibForz;
		
		return retorno;
	}
}
