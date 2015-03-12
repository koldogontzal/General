package sudoku;

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
import shell.Shell;

class SudokuConsola {
	private Tablero tablero = new Tablero();
	
	private ListaCircular<Tablero> historico = new ListaCircular<Tablero>(10);
			
	public void jugar() {
//		Scanner key = new Scanner(System.in).useDelimiter("\r\n");
		
		String [] comandos = {"ma", "m1p", "mnp", "mvp", "av", "qv", "p", "r", "s", "ud", "rd", "save", "load", "h"};
		int [] numParametros = {0, 0, 0, 1, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0};
		String [] descripcion = {"Mostrar solo las celdas que han sido asignadas.",
								"Mostrar las celdas que unicamente tienen un valor po-\n\t\t\tsible y estan aun sin asignar.",
								"Mostrar el numero de valores posibles de cada celda,\n\t\t\tusando letras: a = \"1 valor\", b = \"2 valores\", .. ,\n\t\t\ti = \"9 valores\".",
								"Mostrar las celdas que tienen el parametro como un va-\n\t\t\tlor posible.",
								"Asignar un valor a una celda. Tres parametros separados\n\t\t\tpor comas, cuyo orden es fila, columna, valor.",
								"Quitar un valor determinado de la lista de valores po-\n\t\t\tsibles de una celda. Tres parametros separados por\n\t\t\tcomas, cuyo orden es fila, columna, valor.",
								"Pista.",
								"Resolver el sudoku.",
								"Salir del programa.",
								"Deshacer movimiento.",
								"Rehacer movimiento.",
								"Guardar el tablero.",
								"Cargar un tablero.",
								"Mostrar este menu de ayuda."
								};
		
		String dataFile = "Sudoku.sdk";
		
		// Inicia el historico
		this.historico.addClone(tablero.clone());

		Shell lc = new Shell(comandos, numParametros, descripcion);
		lc.muestraComandos();
		
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
					// al tablero a una situación sin solución, en ese caso,
					// habrá que hacer un undo del movimiento
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
					// a una situación inestable
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
					// al tablero a una situación sin solución, en ese caso,
					// habrá que hacer un undo del movimiento y avisar.
					this.tablero.resolver();
					this.historico.addClone(this.tablero.clone());
				} catch (ForzarUndoException ex) {
					// Hay que forzar el undo, porque el tablero ha quedado inestable
					this.tablero = this.historico.actual().clone();
					System.out.println("\n¡AVISO!\nAlguna de las acciones anteriores no es correcta" 
							+ " y lleva al tablero\na un bloqueo sin solución. Deshágala usando el comando DESHACER.");
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
					// Mete el nuevo tablero en el histórico, para que esta acción pueda deshacerse
					this.historico.addClone(this.tablero.clone());
				} catch (IOException ex) {
					System.out.println("No se pudo leer el fichero \"" + dataFile + "\".");
					System.out.println(ex.getStackTrace());
				} catch (ClassNotFoundException ex) {
					System.out.println("No se pudo hacer la conversión a la clase Tablero.");
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

	}

	public static void main(String[] args) {
		SudokuConsola s = new SudokuConsola();
		s.jugar();
	}	
}
