package interfaz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.Border;

import exceptions.CeldaNoAsignadaException;

import tablero.Celda;
import tablero.Tablero;

public class CeldaGrafica extends JTextField {
	private static final long serialVersionUID = -7526043640935122018L;
	private Celda celda;
	private static final Color[] fondos = new Color[11];
	
	private int modoPresentacion;
	private int valorPresentacion;
	
	public CeldaGrafica (Celda celda) {
		// La etiqueta
		super();
		Dimension tamagno = new Dimension(35,35);
		this.setPreferredSize(tamagno);
		Font f = new Font("Arial", Font.BOLD, 24);
		this.setFont(f);
		this.setEditable(false);
		Border border = BorderFactory.createLineBorder(Color.black);;
		this.setBorder(border);
		this.setHorizontalAlignment(JTextField.CENTER);	
		
		// Los fondos
		fondos[0] = new Color(228, 228, 228);
		fondos[1] = new Color(0, 255, 0);
		for (int i = 2; i < 10; i++) {
			fondos[i] = new Color(0, (9 - i) * 255 / 7, 255);
		}
		fondos[10] = new Color(255, 255, 0);
		
		//La celda
		this.celda = celda;
		
		//Pintarla por primera vez
		this.modoPresentacion = Tablero.SOLO_ASIGNADAS;
		this.valorPresentacion = 0;
		this.pintar(this.modoPresentacion, this.valorPresentacion);
	}

	public void pintar(int modo, int valor) {
		this.modoPresentacion = modo;
		this.valorPresentacion = valor;
		if (modo != Tablero.BUSCAR_VALOR_POSIBLE_DETERMINADO) {
			try {
				this.setBackground(fondos[0]);
				this.setText("" + this.celda.getValor());
			} catch (CeldaNoAsignadaException e) {
				switch (modo) {
				case Tablero.ASIGNADAS_Y_NUM_VALORES_POSIBLES:
					this.setBackground(fondos[this.celda.numValoresPosibles()]);
					this.setText("");
					break;
				case Tablero.SOLO_ASIGNADAS:
					this.setBackground(fondos[0]);
					this.setText("");
					break;
				}
			}
		} else {
			if (this.celda.esValorPosible(valor)) {
				this.setBackground(fondos[10]);
				this.setText("" + valor);
			} else {
				this.setBackground(fondos[0]);
				this.setText("");
			}
		}
	}
	
	public void setCelda (Celda celda) {
		this.celda = celda;
		this.pintar(this.modoPresentacion, this.valorPresentacion);
	}

	public int getModoPresentacion() {
		return this.modoPresentacion;
	}

	public Celda getCelda() {
		return this.celda;
	}

	public int getValorPresentacion() {
		return this.valorPresentacion;
	}
}
