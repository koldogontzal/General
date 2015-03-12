package sudoku;

import interfaz.Ventana;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import exceptions.ForzarUndoException;
import listaCircular.ListaCircular;
import tablero.*;

public class SudokuGrafico {
	private Tablero tablero = new Tablero();
	private ListaCircular<Tablero> historico = new ListaCircular<Tablero>(10);
	private Ventana ventana;
			
	public SudokuGrafico() {
		//String dataFile = "Sudoku.sdk";
		
		// Inicia el historico
		this.historico.addClone(tablero.clone());
		
		//		 Crea la ventana
		this.ventana = new Ventana(this);

		// Iniciacion aleatoria de un tablero (para probar, luego borrar estas lineas)

		this.agnadirValor(new Posicion(3, 4), 4);
		this.agnadirValor(new Posicion(5, 5), 6);
		this.agnadirValor(new Posicion(1, 5), 7);
		this.agnadirValor(new Posicion(5, 8), 1);
		this.agnadirValor(new Posicion(3, 9), 9);
		this.agnadirValor(new Posicion(3, 6), 8);
		this.agnadirValor(new Posicion(2, 2), 2);
		this.agnadirValor(new Posicion(2, 5), 3);
		this.agnadirValor(new Posicion(1, 4), 5);
		this.agnadirValor(new Posicion(1, 6), 2);
		this.agnadirValor(new Posicion(9, 7), 2);
		this.agnadirValor(new Posicion(8, 1), 9);
		this.agnadirValor(new Posicion(7, 4), 9);
		this.agnadirValor(new Posicion(4, 2), 4);
		this.agnadirValor(new Posicion(6, 3), 5);
		
		
		/*
		boolean salir = false;
		while (!salir) {
			int[] comInt = lc.leeComando();

			switch (comInt[0]) {
			case 0:
				this.tablero.mostrar(Tablero.SOLO_ASIGNADAS);
				break;
			case 1:
				this.tablero.mostrar(Tablero.SOLO_ASIGNADAS_Y_UN_SOLO_VALOR_POSIBLE);
				break;
			case 2:
				this.tablero.mostrar(Tablero.ASIGNADAS_Y_NUM_VALORES_POSIBLES);
				break;
			case 3:
				this.tablero.mostrar(Tablero.BUSCAR_VALOR_POSIBLE_DETERMINADO,
						comInt[1]);
				break;
			case 4:
				try {
					// Puede ser que el valor introducido por el jugador, lleve
					// al tablero a una situaci�n sin soluci�n, en ese caso,
					// habr� que hacer un undo del movimiento
					this.tablero.setValorCelda(new Posicion(comInt[1], comInt[2]), comInt[3]);
					this.historico.addClone(this.tablero.clone());
				} catch (ForzarUndoException ex) {
					// Hay que forzar el undo, porque el tablero ha quedado inestable
					this.tablero = this.historico.actual().clone();
				}
				break;
			case 5:
				try {
					// Puede ser que el quitar un valor posible lleve al tablero
					// a una situaci�n inestable
					Posicion pos = new Posicion(comInt[1], comInt[2]);
					this.tablero.quitarValorPosible(pos, comInt[3]);
					// Revisa si ese cambio ha podido afectar a otras celdas del
					// tablero
					this.tablero.revisarValoresPosibles(pos);
					// Y ahora mete el cambio en el historico
					this.historico.addClone(this.tablero.clone());
				} catch (ForzarUndoException ex) {
					// Hay que forzar el undo, porque el tablero ha quedado inestable
					this.tablero = this.historico.actual().clone();
				}
				break;
			case 6:
				this.tablero.mostrarListaResueltas(false);
				break;
			case 7: 
				// resolver
				try {
					// Puede ser que valores introducidos por el jugador, lleven
					// al tablero a una situaci�n sin soluci�n, en ese caso,
					// habr� que hacer un undo del movimiento y avisar.
					this.tablero.resolver();
					this.historico.addClone(this.tablero.clone());
				} catch (ForzarUndoException ex) {
					// Hay que forzar el undo, porque el tablero ha quedado inestable
					this.tablero = this.historico.actual().clone();
					System.out.println("\n�AVISO!\nAlguna de las acciones anteriores no es correcta" 
							+ " y lleva al tablero\na un bloqueo sin soluci�n. Desh�gala usando el comando DESHACER.");
				}
	
				break;
			case 8:
				salir = true;
				break;
			case 9:
				this.tablero = this.historico.anterior().clone();
				break;
			case 10:
				this.tablero = this.historico.siguiente().clone();
				break;
			case 11:
				ObjectOutputStream out = null;
				try {
					// Grabar
					out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(dataFile)));
					out.writeObject(tablero);
					out.close();
				} catch (IOException ex) {
					System.out.println("No se pudo grabar el fichero \"" + dataFile + "\".");
					System.out.println(ex.getStackTrace());
				} finally {
					 if (out != null) {
		                try {
							out.close();
						} catch (IOException ex) {
							System.out.println("No se pudo cerrar el fichero \"" + dataFile + "\" en escritura.");
							System.out.println(ex.getStackTrace());
						} 
		            }
				}
				break;
			case 12:
				ObjectInputStream in = null;
				try {
					// Leer
					in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(dataFile)));
					this.tablero = (Tablero) in.readObject();
					in.close();
					// Mete el nuevo tablero en el hist�rico, para que esta acci�n pueda deshacerse
					this.historico.addClone(this.tablero.clone());
				} catch (IOException ex) {
					System.out.println("No se pudo leer el fichero \"" + dataFile + "\".");
					System.out.println(ex.getStackTrace());
				} catch (ClassNotFoundException ex) {
					System.out.println("No se pudo hacer la conversi�n a la clase Tablero.");
					this.tablero = this.historico.actual().clone();
				} finally {
					 if (in != null) {
		                try {
							in.close();
						} catch (IOException ex) {
							System.out.println("No se pudo cerrar el fichero \"" + dataFile + "\" en lectura.");
							System.out.println(ex.getStackTrace());
						}
		            }
				}
				break;
			case 13:
				lc.muestraComandos();
				break;
			}

			System.out.println();
		}
		*/
	}
	
	public Tablero getTablero() {
		return this.tablero;
	}
	
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		SudokuGrafico s = new SudokuGrafico();
	}

	public void deshacerMovimiento() {
		this.tablero = this.historico.anterior().clone();
		this.ventana.actualizarTablero(this.tablero);
		
	}

	public void rehacerMovimiento() {
		this.tablero = this.historico.siguiente().clone();
		this.ventana.actualizarTablero(this.tablero);
	}
	
	public void agnadirValor(Posicion pos, int valor) {
		try {
			// Puede ser que el valor introducido por el jugador, lleve
			// al tablero a una situaci�n sin soluci�n, en ese caso,
			// habr� que hacer un undo del movimiento
			this.tablero.setValorCelda(pos, valor);
			this.historico.addClone(this.tablero.clone());
		} catch (ForzarUndoException ex) {
			// Hay que forzar el undo, porque el tablero ha quedado inestable
			this.tablero = this.historico.actual().clone();
		}
		//		 Por ultimo, pinta el tablero
		this.ventana.actualizarTablero(this.tablero);
	}

	public void visualizarTablero(int modo) {
		this.ventana.visualizarTablero(modo, 0);
		
	}

	public void visualizarTablero(int modo, int valor) {
		this.ventana.visualizarTablero(modo, valor);
		
	}

	public void borrarValorPosible(Posicion pos, int valor) {
		try {
			// Puede ser que el quitar un valor posible lleve al tablero
			// a una situaci�n inestable
			this.tablero.quitarValorPosible(pos, valor);
			// Revisa si ese cambio ha podido afectar a otras celdas del
			// tablero
			this.tablero.revisarValoresPosibles(pos);
			// Y ahora mete el cambio en el historico
			this.historico.addClone(this.tablero.clone());
		} catch (ForzarUndoException ex) {
			// Hay que forzar el undo, porque el tablero ha quedado inestable
			this.tablero = this.historico.actual().clone();
		}
		// Por ultimo, pinta el tablero
		this.ventana.actualizarTablero(this.tablero);
	}
}
