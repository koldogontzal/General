package eventos;

import interfaz.CeldaGrafica;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import sudoku.SudokuGrafico;
import tablero.Celda;
import tablero.Tablero;

public class GestorCeldasGraficas implements MouseListener {
	
	private SudokuGrafico aplicacion;
	
	public GestorCeldasGraficas(SudokuGrafico aplicacion){
		this.aplicacion = aplicacion;
	}

	public void mouseClicked(MouseEvent e) {
		CeldaGrafica celdaGrafica = (CeldaGrafica)e.getSource();
		Celda celdaLogica = celdaGrafica.getCelda();
		
		GestorPopupMenu gestor = new GestorPopupMenu(this.aplicacion, celdaGrafica);
		
		if (celdaGrafica.getModoPresentacion() == Tablero.BUSCAR_VALOR_POSIBLE_DETERMINADO) {
			// Tengo que presentar las dos opciones de borrar dicho valor
			// posible o borrarlo
			JPopupMenu menu = new JPopupMenu();
			JMenuItem cabecera = new JMenuItem("Elige acción para el valor posible:");
			menu.add(cabecera);
			menu.addSeparator();
			JMenuItem menuItemBorrar = new JMenuItem("   Borrar");
			if (!celdaLogica.esValorPosible(celdaGrafica.getValorPresentacion())) {
				menuItemBorrar.setEnabled(false);
			}
			menu.add(menuItemBorrar);
					// Auditar
			menuItemBorrar.addActionListener(gestor);
			JMenuItem menuItemDejar = new JMenuItem("   Dejar");
			menu.add(menuItemDejar);
					// Auditar
			menuItemDejar.addActionListener(gestor);
			//Muestra el PopupMenu
			menu.show(e.getComponent(), e.getX(), e.getY());			
		} else {
			// Presentar todos los valores posibles para que elija uno y asignar
			// la celda a dicho valor
			JPopupMenu menu = new JPopupMenu();
			JMenuItem[] menuItems = new JMenuItem[9];
			JMenuItem cabecera = new JMenuItem("Elige valor para asignar:");
			menu.add(cabecera);
			menu.addSeparator();
			for (int i = 0; i < 9; i++) {
				menuItems[i] = new JMenuItem("   Valor " + (i + 1));
				if (!celdaLogica.esValorPosible(i + 1)) {
					menuItems[i].setEnabled(false);
				}
				menu.add(menuItems[i]);
					// Auditar
				menuItems[i].addActionListener(gestor);
			}
			//Muestra el PopupMenu
			menu.show(e.getComponent(), e.getX(), e.getY());
		}

	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
	
	}

	public void mousePressed(MouseEvent e) {
		
	}

	public void mouseReleased(MouseEvent e) {
		
	}

}
