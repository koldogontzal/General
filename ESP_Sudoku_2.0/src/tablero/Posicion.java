package tablero;

import java.io.Serializable;

/**
 * Clase que implementa una <code>Posicion</code> del tablero del juego del
 * sudoku. Una Coordenada es una fila y una columna. Los valores permitidos para
 * ambos parámetros son [1-9], ambos inclusive. También se puede representar con
 * dos valores que serían cuadradoExterior y cuadradoInterior. El tablero del
 * sudoku se divide a su vez en 9 cuadradosExteriores de 3x3 Celdas. Estos
 * cuadradosExteriores se numeran de izquierda a derecha y de arriba a abajo
 * como:
 * <p>
 * 1 2 3
 * <br>
 * 4 5 6
 * <br>
 * 7 8 9
 * <p>
 * A su vez, dentro de las 3x3 casillas de un cuadradoExterior, se repite el
 * esquema de antes y el valor cuadradoInterior representa una Celda en
 * cuestión, que van numeradas con el mismo esquema:
 * <p>
 * 1 2 3
 * <br>
 * 4 5 6
 * <br>
 * 7 8 9
 */
public class Posicion implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4838237170145297900L;
	//Las variables internas se guardan entre [0-8], para facilitar los cálculos
	// Por eso llevan un cero añadido al final, para indicar la diferencia de rangos.
	private int fila0; 
	private int columna0;

	public static final int FILA = 1;

	public static final int COLUMNA = 2;

	public static final int CUADRADO = 3;

	/**
	 * Constructor con parámetros la fila y la columna de la
	 * <code>Posicion</code>.
	 * 
	 * @param fila
	 *            Valor de la fila. Su rango es entre [1-9], ambos inclusive.
	 * @param columna
	 *            Valor de la columna. Su rango es entre [1-9], ambos inclusive.
	 */
	public Posicion(int fila, int columna) {
		this(FILA, fila, columna);
	}
	
	/**
	 * Constructor con parámetros la fila y la columna de la
	 * <code>Posicion</code>.
	 * 
	 * @param tipo
	 *            Tipo de las coordenadas, definido como las constantes
	 *            estáticas <code>FILA</code>, <code>COLUMNA</code> y
	 *            <code>CUADRADO</code>.
	 *            <P>
	 *            Para el tipo <code>FILA</code> la primera coordenada será la
	 *            fila y la segunda será la columna.
	 *            <P>
	 *            Para el tipo <code>COLUMNA</code> la primera coordenada será
	 *            la columna y la segunda será la fila.
	 *            <p>
	 *            Para el tipo <code>CUADRADO</code> la primera coordenada
	 *            será el cuadrado exterior y la segunda será el cuadrado
	 *            interior.
	 * @param coordenada1
	 *            Según el tipo, representará un valor u otro. Su rango es entre
	 *            [1-9], ambos inclusive.
	 * @param corrdenada2
	 *            Según el tipo, representará un valor u otro. Su rango es entre
	 *            [1-9], ambos inclusive.
	 */
	public Posicion(int tipo, int coordenada1, int coordenada2) {
		// Se les resta 1, porque internamente es más cómodo que su rango sea
		// [0-8] en vez de [1-9]. Se hace el módulo para que sólo pueda tomar
		// valores entre [0-8].
		coordenada1 = (coordenada1 - 1) % 9;
		coordenada2 = (coordenada2 - 1) % 9;
		switch (tipo) {
		case FILA:
			this.fila0 = coordenada1;
			this.columna0 = coordenada2;
			break;
		case COLUMNA:
			this.fila0 = coordenada2;
			this.columna0 = coordenada1;
			break;
		default:
			// Por default, el caso de CUADRADO
			this.fila0 = 3 * (coordenada1 / 3) + (coordenada2 / 3);
			this.columna0 = 3 * (coordenada1 % 3) + (coordenada2 % 3);
			break;
		}
	}

	/**
	 * Método que devuelve el valor de la fila de la <code>Posicion</code>.
	 * 
	 * @return int Valor de la fila de la Coordenada. Su rango es entre [0-8],
	 *         ambos inclusive.
	 */
	public int getFila() {
		return this.fila0 + 1;
	}

	public int getColumna() {
		return this.columna0 + 1;
	}

	public int getCuadradoExt() {
		return 1 + (3 * (this.fila0 / 3) + (this.columna0 / 3));
	}

	public int getCuadradoInt() {
		int difFilas = this.fila0 - 3 * (this.fila0 / 3);
		int difColumnas = this.columna0 - 3 * (this.columna0 / 3);
		return 1 + (3 * difFilas + difColumnas);
	}

	public String toString() {
		return "(" + (this.fila0 + 1) + "," + (this.columna0 + 1) + ")";
	}
}
