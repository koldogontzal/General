package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import eventos.GestorCeldasGraficas;

import sudoku.SudokuGrafico;
import tablero.Posicion;
import tablero.Tablero;

public class Panel extends JPanel {
	private static final long serialVersionUID = -3442956985999390958L;
	private CeldaGrafica [][] celdas = new CeldaGrafica [9][9];
	private GestorCeldasGraficas gestor;
	
	public Panel(SudokuGrafico aplicacion) {
		// Crea el JPanel
		super();
		
		// Crea el gestor
		this.gestor = new GestorCeldasGraficas (aplicacion);
		
		// Lee la variable tablero
		Tablero tablero = aplicacion.getTablero();
		
		//Crea el Layout padre, que es de tipo Border para dejar un margen entre el brode de la ventana y el tablero del sudoku
		BorderLayout padre = new BorderLayout();
		JPanel panelFicticioNorte = new JPanel();
		JPanel panelFicticioSur = new JPanel();
		JPanel panelFicticioEste = new JPanel();
		JPanel panelFicticioOeste = new JPanel();
		
		this.setLayout(padre);
		
		panelFicticioNorte.setPreferredSize(new Dimension(25,25));
		panelFicticioSur.setPreferredSize(new Dimension(25,25));
		panelFicticioEste.setPreferredSize(new Dimension(25,25));
		panelFicticioOeste.setPreferredSize(new Dimension(25,25));

		JPanel centro = new JPanel();		
		//Crea el Layout de 9 CuadradosExteriores
		GridLayout layoutTablero = new GridLayout(3, 3, 0, 0);
		JPanel[] cuadradosExteriores = new JPanel[9];
		GridLayout[] layoutCuadradoInterior = new GridLayout[9];
		// Asocia el Layout al Panel
		centro.setLayout(layoutTablero);
		// Crea los componentes, la CeldasGraficas
		for (int i = 0; i < 9; i++) {
			cuadradosExteriores[i] = new JPanel();
			layoutCuadradoInterior[i] = new GridLayout(3, 3, 0, 0);
			cuadradosExteriores[i].setLayout(layoutCuadradoInterior[i]);
			for (int j = 0; j < 9; j++) {
				Posicion pos = new Posicion(Posicion.CUADRADO, i + 1, j + 1);
				int fila = pos.getFila() - 1;
				int columna = pos.getColumna() - 1;
				this.celdas[fila][columna] = new CeldaGrafica(tablero
						.getCelda(pos));
				// Asociar la CeldaGrafica al panel
				cuadradosExteriores[i].add(this.celdas[fila][columna]);
				
				// Audita la celdaGrafica
				this.celdas[fila][columna].addMouseListener(this.gestor);
			}
			Border border = BorderFactory.createLineBorder(Color.black);
			cuadradosExteriores[i].setBorder(border);
			centro.add(cuadradosExteriores[i]);
		}
		Border border = BorderFactory.createLineBorder(Color.black, 2);
		centro.setBorder(border);
			
		this.add(panelFicticioNorte, BorderLayout.NORTH);
		this.add(panelFicticioSur, BorderLayout.SOUTH);
		this.add(panelFicticioEste, BorderLayout.EAST);
		this.add(panelFicticioOeste, BorderLayout.WEST);
		
		this.add(centro, BorderLayout.CENTER);		
	}
	
	public void actualizarTablero(Tablero tablero) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				Posicion pos = new Posicion(i + 1, j + 1);
				this.celdas[i][j].setCelda(tablero.getCelda(pos));
			}
		}
	}

	public void visualizarTablero(int modo, int valor) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				this.celdas[i][j].pintar(modo, valor);
			}
		}
	}

}
