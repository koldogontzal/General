package interfaz;

import javax.swing.JFrame;

import sudoku.SudokuGrafico;
import tablero.Tablero;

public class Ventana extends JFrame {
	private static final long serialVersionUID = -1750263709662734291L;
	private Panel panel;
	private Menu menu;

	public Ventana (SudokuGrafico aplicacion) {
		// La ventana
		super("Sudoku 2.0");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
			
		// El panel
		this.panel = new Panel(aplicacion);
		
		// Asociar panel a la ventana
		this.setContentPane(this.panel);
		
		// El menu
		this.menu = new Menu(aplicacion);
		super.setJMenuBar(this.menu);

		// Hacer visible la ventana
		this.pack();
		this.setVisible(true);
	}
	
	public void actualizarTablero (Tablero tablero) {
		this.panel.actualizarTablero(tablero);
	}
	
	public void visualizarTablero(int modo, int valor) {
		this.panel.visualizarTablero (modo, valor);
	}
}
