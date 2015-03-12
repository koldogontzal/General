package apliGraf;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class VisorTextoConDesplazamiento extends JScrollPane {
	
	private static final long serialVersionUID = -1903212075601652635L;
	
	private JTextArea texto = new JTextArea();

	private int maxNumLineas;
	private int numLineas;
	
	public VisorTextoConDesplazamiento() {
		this(100);
	}
	
	public VisorTextoConDesplazamiento(int numLineas) {
		super();
		this.texto = new JTextArea();
		this.texto.setEditable(false);
		
		this.setViewportView(this.texto);
		
		this.numLineas = 0;
		this.maxNumLineas = numLineas - 1;

	}
	
	public void escribirLinea(String linea) {
		String textoAntiguo = this.texto.getText();
		String textoNuevo = textoAntiguo + linea + "\n";
		if (this.numLineas < this.maxNumLineas) {
			this.numLineas++;
		} else {
			String[] arrayLineas = textoNuevo.split("\n", 2);
			textoNuevo = arrayLineas[1];
		}
		this.texto.setText(textoNuevo);
	}
	
	public void borrarTexto() {
		this.texto.setText("");
		this.numLineas = 0;
	}

}
