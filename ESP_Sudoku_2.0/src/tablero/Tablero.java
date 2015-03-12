package tablero;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import exceptions.CeldaNoAsignadaException;
import exceptions.ForzarUndoException;
import exceptions.ValorFueraRangoException;
import exceptions.ValorNoValidoException;

/**
 * Clase que implementa un tablero del juego del sudoku. Un tablero está formado
 * principalmente por 81 Celdas dispuestas en un cuadrado de 9x9, aunque también
 * está formado por distintas formaciones de asociaciones de esas mismas Celdas,
 * como son las filas, las columnas y los cuadradosExteriores.
 */
public class Tablero implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1382987722905321273L;

	private Celda[][] tablero = new Celda[9][9];

	private List<Celda> listaResueltas = new ArrayList<Celda>(81);

	public static final int SOLO_ASIGNADAS = 0;

	public static final int SOLO_ASIGNADAS_Y_UN_SOLO_VALOR_POSIBLE = 1;

	public static final int SOLO_ASIGNADAS_Y_VALOR_CUANDO_UN_SOLO_VALOR_POSIBLE = 2;

	public static final int ASIGNADAS_Y_NUM_VALORES_POSIBLES = 3;

	public static final int ASIGNADAS_Y_TODOS_LOS_VALORES_POSIBLES = 4; // Sólo para depuración 

	public static final int BUSCAR_VALOR_POSIBLE_DETERMINADO = 5;

	/**
	 * Crea las 81 celdas del tablero y las guarda en varias estructuras últiles
	 * para su uso para el juego.
	 */
	public Tablero() {
		// Crear la tabla principal de celdas
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				this.tablero[i][j] = new Celda(new Posicion(i + 1, j + 1));
				// Cada Celda tiene también su Coordenada asociada
			}
		}
	}

	public Celda getCelda(Posicion pos) {
		return this.tablero[pos.getFila() - 1][pos.getColumna() - 1];
	}

	private void setCelda(Posicion pos, Celda celda) {
		this.tablero[pos.getFila() - 1][pos.getColumna() - 1] = celda;
	}
	
	public void quitarValorPosible (Posicion pos, int valor) {
		Celda celda = this.getCelda(pos);
		try {
		celda.quitarValorPosible(valor);
		} catch (ValorNoValidoException ex) {
			System.out.println(ex.getMessage());
		} catch (ValorFueraRangoException ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void setValorCelda(Posicion pos, int valor) throws ForzarUndoException {
		setValorCeldaRecursivo(pos, valor, 0);
	}

	private void setValorCeldaRecursivo(Posicion pos, int valor, int accion) throws ForzarUndoException {
		// accion = 0: Es la primera vez que se llama al método, mete el valor
		// 				en la Celda
		// accion = 1: Es una llamada recursiva, solo fuerza los valores
		// 				posibles
		Celda celda = getCelda(pos);
		try {
			if (accion == 0) {
				celda.putValor(valor);
				if (this.listaResueltas.contains(celda)) {
					this.listaResueltas.remove(celda);
				}
			} else {
				celda.fuerzaUnValorPosible(valor);
				if (!this.listaResueltas.contains(celda)) {
					this.listaResueltas.add(celda);
				}
			}
			// Quita el valor de los posibles valores de la fila, columna y cuadrado de la Posicion pos.
			quitaValorPosibleDeFilaColumnaCuadrado(pos, valor);
	
			// Revisa si han quedado Celdas con un único valor posible
			BuscaCeldasUnUnicoValorPosible(Posicion.FILA, pos.getFila());
			BuscaCeldasUnUnicoValorPosible(Posicion.COLUMNA, pos.getColumna());
			BuscaCeldasUnUnicoValorPosible(Posicion.CUADRADO, pos.getCuadradoExt());
	
			// Revisa si han quedado Celdas que o bien en su fila, o columna o cuadrado, son las únicas que tienen un determinado valor posible
			// Revisa las filas
			int fila = pos.getFila() - 1;
			if ((fila % 3) == 0) {
				BuscaCeldaUnicaEnGrupoConUnValorPosibleDado(Posicion.FILA, fila + 2);
				BuscaCeldaUnicaEnGrupoConUnValorPosibleDado(Posicion.FILA, fila + 3);
			} else if ((fila % 3) == 1) {
				BuscaCeldaUnicaEnGrupoConUnValorPosibleDado(Posicion.FILA, fila);
				BuscaCeldaUnicaEnGrupoConUnValorPosibleDado(Posicion.FILA, fila + 2);
			} else {
				BuscaCeldaUnicaEnGrupoConUnValorPosibleDado(Posicion.FILA, fila);
				BuscaCeldaUnicaEnGrupoConUnValorPosibleDado(Posicion.FILA, fila - 1);
			}
			// Revisa las columnas
			int columna = pos.getColumna() - 1;
			if ((columna % 3) == 0) {
				BuscaCeldaUnicaEnGrupoConUnValorPosibleDado(Posicion.COLUMNA, columna + 2);
				BuscaCeldaUnicaEnGrupoConUnValorPosibleDado(Posicion.COLUMNA, columna + 3);
			} else if ((columna % 3) == 1) {
				BuscaCeldaUnicaEnGrupoConUnValorPosibleDado(Posicion.COLUMNA, columna);
				BuscaCeldaUnicaEnGrupoConUnValorPosibleDado(Posicion.COLUMNA, columna + 2);
			} else {
				BuscaCeldaUnicaEnGrupoConUnValorPosibleDado(Posicion.COLUMNA, columna);
				BuscaCeldaUnicaEnGrupoConUnValorPosibleDado(Posicion.COLUMNA, columna - 1);
			}
			
			// Revisa los cuadrados exteriores como columna
			int cuadrado = pos.getCuadradoExt() - 1;
			if ((cuadrado % 3) == 0) {
				BuscaCeldaUnicaEnGrupoConUnValorPosibleDado(Posicion.CUADRADO, cuadrado + 2);
				BuscaCeldaUnicaEnGrupoConUnValorPosibleDado(Posicion.CUADRADO, cuadrado + 3);
			} else if ((cuadrado % 3) == 1) {
				BuscaCeldaUnicaEnGrupoConUnValorPosibleDado(Posicion.CUADRADO, cuadrado);
				BuscaCeldaUnicaEnGrupoConUnValorPosibleDado(Posicion.CUADRADO, cuadrado + 2);
			} else {
				BuscaCeldaUnicaEnGrupoConUnValorPosibleDado(Posicion.CUADRADO, cuadrado);
				BuscaCeldaUnicaEnGrupoConUnValorPosibleDado(Posicion.CUADRADO, cuadrado - 1);
			}
			// Revisa los cuadrados exteriores como fila
			if ((cuadrado / 3) == 0) {
				BuscaCeldaUnicaEnGrupoConUnValorPosibleDado(Posicion.CUADRADO, cuadrado + 7);
				BuscaCeldaUnicaEnGrupoConUnValorPosibleDado(Posicion.CUADRADO, cuadrado + 4);
			} else if ((cuadrado / 3) == 1) {
				BuscaCeldaUnicaEnGrupoConUnValorPosibleDado(Posicion.CUADRADO, cuadrado - 2);
				BuscaCeldaUnicaEnGrupoConUnValorPosibleDado(Posicion.CUADRADO, cuadrado + 4);
			} else {
				BuscaCeldaUnicaEnGrupoConUnValorPosibleDado(Posicion.CUADRADO, cuadrado - 5);
				BuscaCeldaUnicaEnGrupoConUnValorPosibleDado(Posicion.CUADRADO, cuadrado - 2);
			}
			
		
		} catch (ValorNoValidoException ex) {
			if (accion ==0) {
				// Se trata de que el jugador intentó poner un valor en una
				// casilla en la que ese valor no está en la lista de posibles
				System.out.println("\n" + ex.getMessage());
				System.out.println("No se realizó ninguna acción.");
			} else {
				// Se trata de una llamada recursiva, que indica que el valor
				// que se metió en alguna llamada anterior, lleva al tablero a
				// una situación de duplicidad de valores en alguna fila o
				// columna o cuadrado. Esto es malo y se avisa al jugador para
				// que haga un "undo".
				System.out.println("\n\n¡AVISO!");
				System.out.println("\tLa acción solicitada, ha llevado al tablero a un estado inestable," 
							+ "\nen el que su solución obliga a que haya alguna fila o columna o cuadrado"
							+ "\ncon algún valor repetido. Esto significa que la acción que intentaba rea-"
							+ "\nlizar no es correcta.\nNo se realizó ninguna acción.");
				throw new ForzarUndoException ();
			}
			
		} catch (ValorFueraRangoException ex) {
			System.out.println("\n" + ex.getMessage());
			System.out.println("No se realizó ninguna acción.");	
		}
	}

	private void BuscaCeldaUnicaEnGrupoConUnValorPosibleDado(int tipo,
			int coordenada) throws ForzarUndoException {
		// Primero mira cuántas veces se repite cada uno de los valores posibles
		int[] valores = new int[9];
		Celda [] celdas = new Celda[9];
		for (int i = 1; i <= 9; i++) {
			Celda celda = getCelda(new Posicion(tipo, coordenada, i));
			if (!celda.esCeldaAsignada()) {
				for (int j = 0; j < 9; j++) {
					if (celda.esValorPosible(j + 1)) {
						valores[j]++;
						celdas[j] = celda;
					}
				}
			}
		}
		// Busca sólo aquellos valores posibles que solo se repiten una vez
		// y los fuerza como únicos valores posibles
		for (int i=0; i<9; i++){
			if (valores[i] == 1) {
				if (!celdas[i].esValoresPosiblesForzada()) {
					this.setValorCeldaRecursivo(celdas[i].getPosicion(), i + 1, 1);
				}
			}
		}
	}

	private void BuscaCeldasUnUnicoValorPosible(int tipo, int coordenada) throws ForzarUndoException {
		Celda celda;
		for (int i = 1; i <= 9; i++) {
			celda = getCelda(new Posicion(tipo, coordenada, i));
			if (celda.esSoloUnValorPosible() && !celda.esValoresPosiblesForzada()) {
				setValorCeldaRecursivo(celda.getPosicion(), celda
						.unicoValorPosible(), 1);
			}
		}
	}

	private void quitaValorPosibleDeFilaColumnaCuadrado(Posicion pos, int valor) throws ValorNoValidoException {
		// Quita el valor de la Fila
		quitaValorPosibleGrupo(Posicion.FILA, pos, valor);
		// Quita el valor de la Columna
		quitaValorPosibleGrupo(Posicion.COLUMNA, pos, valor);
		// Quita el valor del Cuadrado
		quitaValorPosibleGrupo(Posicion.CUADRADO, pos, valor);
	}

	private void quitaValorPosibleGrupo (int tipo, Posicion pos, int valor) throws ValorNoValidoException {
		Celda celda;
		int coordenada;
		int actual;
		switch (tipo) {
		case Posicion.FILA:
			coordenada = pos.getFila();
			actual = pos.getColumna();
			break;
		case Posicion.COLUMNA:
			coordenada = pos.getColumna();
			actual = pos.getFila();
			break;
		default:
			coordenada = pos.getCuadradoExt();
			actual = pos.getCuadradoInt();
			break;
		}
		
		for (int i = 1; i <= 9; i++) {
			if (i != actual) {
				// Así evito intentar quitar el valor posible de la celda
				// actual. Esto no es necesario en el caso en que la celda
				// actual haya sido actualizada con un valor final. Pero si lo
				// que se intenta es forzar un único valor posible, al pasar
				// luego por aquí, intentaría borrarlo, siendo el único
				celda = this.getCelda(new Posicion(tipo, coordenada, i));
				try {
					celda.quitarValorPosible(valor);
				} catch (ValorFueraRangoException ex) {
					System.out.println(ex.getMessage());
				}
			}
		}
	}

	
	/**
	 * Método que muestra por pantalla el tablero actual
	 * 
	 * @param modo
	 *            Entero definido en las constantes public static definidas con
	 *            la clase, que dice cómo mostrar el tablero
	 */
	public void mostrar(int modo) {
		this.mostrar(modo, 0);
	}
	

	/**
	 * Método que muestra por pantalla el tablero actual
	 * 
	 * @param modo
	 *            Entero definido en las constantes public static definidas con
	 *            la clar, que dice cómo mostrar el tablero.
	 * @param valor
	 *            Entero que queremos buscar entre los valores posibles de las
	 *            Celdas del tablero
	 */
	public void mostrar(int modo, int valor) {
		// Si es cierto, muestra una letra en cada celda, para indicar cuántos
		// valores posibles hay.
		System.out.println("      c o l u m n a   ");
		System.out.println("    1 2 3 4 5 6 7 8 9 ");
		System.out.println("   -------------------");
		for (int i = 0; i < 9; i++) {
			if (i == 2) {
				System.out.print("f ");
			} else if (i == 3) {
				System.out.print("i ");
			} else if (i == 5) {
				System.out.print("l ");
			} else if (i == 6) {
				System.out.print("a ");
			} else {
				System.out.print("  ");
			}
			System.out.print((i + 1) + "|");
			for (int j = 0; j < 9; j++) {
				Celda celda = this.getCelda(new Posicion(i + 1, j + 1));
				if (celda.esCeldaAsignada()) {
					// Si la Celda está asignada, escribe dependiendo del modo
					if (modo==BUSCAR_VALOR_POSIBLE_DETERMINADO) {
						System.out.print(" ");
					} else {
						try {
							System.out.print(celda.getValor());
						} catch (CeldaNoAsignadaException ex) {
							System.out.println("\n" + ex.getMessage());
						}
					}
				} else {
					// Si la Celda no está asignada, entonces depende de modo

					switch (modo) {
					case BUSCAR_VALOR_POSIBLE_DETERMINADO:
						if (celda.esValorPosible(valor)) {
							System.out.print(valor);
						} else {
							System.out.print(" ");
						}
						break;
					case SOLO_ASIGNADAS:
						System.out.print(" ");
						break;

					case SOLO_ASIGNADAS_Y_UN_SOLO_VALOR_POSIBLE:
						if (celda.esSoloUnValorPosible()) {
							System.out.print("X");
						} else {
							System.out.print(" ");
						}
						break;

					case SOLO_ASIGNADAS_Y_VALOR_CUANDO_UN_SOLO_VALOR_POSIBLE:
						if (celda.esSoloUnValorPosible()) {
							System.out.print(celda.unicoValorPosible());
						} else {
							System.out.print(" ");
						}
						break;

					case ASIGNADAS_Y_NUM_VALORES_POSIBLES:
						char letraPosible = (char) (96 + celda
								.numValoresPosibles());
						System.out.print(letraPosible);
						break;

					case ASIGNADAS_Y_TODOS_LOS_VALORES_POSIBLES:
						celda.escribirValoresPosibles();
						System.out.print("-");
						break;
					}
				}
				if ((j % 3) != 2) {
					System.out.print(" ");
				} else {
					System.out.print("|");
				}
			}
			System.out.println();
			if ((i % 3) == 2) {
				System.out.println("   -------------------");
			}
		}
	}	

		
	/**
	 * Método que resuelve el tablero del sudoku hasta que ya no encuentra
	 * más Celdas posbiles que descubrir
	 * 
	 */
	public void resolver() throws ForzarUndoException {
		boolean salir;
		do {
			salir = true;
			for (int i = 1; i <= 9; i++) {
				for (int j = 1; j <= 9; j++) {
					Celda celda = this.getCelda(new Posicion(i, j));
					if ((!celda.esCeldaAsignada())
							&& (celda.esSoloUnValorPosible())) {
						salir = false;
						int valor = celda.unicoValorPosible();
						this.setValorCelda(new Posicion(i, j), valor);
						
					}
				}
			}
		} while (!salir);
	}
	
			
	public void mostrarListaResueltas(boolean todas) {
		Iterator it = this.listaResueltas.iterator();
		if (it.hasNext()) {
			if (todas) {
				// Escribe la lista ordenada de todas las celdas y su valor
				while (it.hasNext()) {
					Celda celda = (Celda) it.next();
					System.out.print(celda.getPosicion() + "-"
							+ celda.unicoValorPosible() + " ");
				}
				System.out.println();
			} else {
				// Escribe sólo una celda cuya solución es posible
				Celda celda = (Celda) it.next();
				System.out.print("Pista: la Celda " + celda.getPosicion() + " sólo tiene un valor posible.");
			}
		} else {
			System.out.println("No hay ayuda posible");
		}
	}
	
	public Tablero clone() {
		Tablero retorno = new Tablero();
		// Clona las celdas del tablero
		for (int i = 1; i <= 9; i++) {
			for (int j = 1; j <= 9; j++) {
				Posicion pos = new Posicion(i, j);
				Celda celdaOriginal = this.getCelda(pos);
				Celda celdaClonada = celdaOriginal.clone();
				retorno.setCelda(pos, celdaClonada);
			}
		}
		// Clona la lista de celdas resueltas, listaResueltas
		Iterator it=this.listaResueltas.iterator();
		while (it.hasNext()) {
			Celda celdaOriginal = (Celda)it.next();
			Posicion pos = celdaOriginal.getPosicion();
			Celda celdaClonada = retorno.getCelda(pos);
			retorno.listaResueltas.add(celdaClonada);
		}
		
		return retorno;
	}

	public void revisarValoresPosibles(Posicion pos) throws ForzarUndoException {
		Celda celda = this.getCelda(pos);
		if (celda.esSoloUnValorPosible()) {
			int valor = celda.unicoValorPosible();
			this.setValorCeldaRecursivo(pos, valor, 1);
		}
		// Revisa la fila
		BuscaCeldaUnicaEnGrupoConUnValorPosibleDado(Posicion.FILA, pos.getFila());
		// Revisa la fila
		BuscaCeldaUnicaEnGrupoConUnValorPosibleDado(Posicion.COLUMNA, pos.getColumna());
		// Revisa la fila
		BuscaCeldaUnicaEnGrupoConUnValorPosibleDado(Posicion.COLUMNA, pos.getCuadradoExt());		
	}
}
