package eventos;

import interfaz.Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

public class GestorMenu implements ActionListener {
	
	private Menu menu;
	
	public GestorMenu (Menu menu) {
		this.menu = menu;
	}

	public void actionPerformed(ActionEvent e) {
		this.menu.descifrarBotonPulsado ((JMenuItem)e.getSource());
	}

}
