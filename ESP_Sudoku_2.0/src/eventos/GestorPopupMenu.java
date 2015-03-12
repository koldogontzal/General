package eventos;

import interfaz.CeldaGrafica;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import sudoku.SudokuGrafico;
import tablero.Posicion;

public class GestorPopupMenu implements ActionListener {
	
	private SudokuGrafico aplicacion;
	private CeldaGrafica celdaGrafica;

	public GestorPopupMenu(SudokuGrafico aplicacion, CeldaGrafica celdaGrafica) {
		this.aplicacion = aplicacion;
		this.celdaGrafica = celdaGrafica;
	}

	public void actionPerformed(ActionEvent e) {
		String texto = ((JMenuItem)e.getSource()).getText();
		Posicion pos = this.celdaGrafica.getCelda().getPosicion();
		if (texto.lastIndexOf("Borrar") >= 0) {
			// BOrrar valor posible
			int valor = this.celdaGrafica.getValorPresentacion();
			this.aplicacion.borrarValorPosible(pos, valor);
		}
		for (int i = 1; i < 10; i++) {
			if (texto.lastIndexOf(""+i) >= 0) {
				// Asignar celda
				this.aplicacion.agnadirValor(pos, i);
			}
		}
	}

}
