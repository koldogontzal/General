package aplicacion;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;

import constantes.Constantes;

import elementos.Escenario;

import utils.CacheSprites;

public class Aplicacion extends Canvas {

	private static final long serialVersionUID = 1L;
	
	private CacheSprites cacheSprites; 
	private Escenario escenario;
	
	private BufferStrategy strategy;
	
	public Aplicacion() {
		super();
		
		this.cacheSprites = new CacheSprites();
		
		this.escenario = new Escenario(Constantes.ESCENARIO_NUM_FILAS, 
				Constantes.ESCENARIO_NUM_COLUMNAS, this.cacheSprites);
		
		JFrame ventana = new JFrame("Aut\u00F3mata celular");
		JPanel panel = (JPanel)ventana.getContentPane();
		
		int tamX = Constantes.ESCENARIO_NUM_COLUMNAS * Constantes.TAMAGNO_PIXELS_CELDA;
		int tamY = Constantes.ESCENARIO_NUM_FILAS * Constantes.TAMAGNO_PIXELS_CELDA;
		
		this.setBounds(0, 0, tamX, tamY);
		panel.setPreferredSize(new Dimension(tamX, tamY));
		panel.setLayout(null);
		panel.add(this);
		ventana.setBounds(0, 0, tamX, tamY);
		ventana.setVisible(true);
		ventana.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		//ventana.setResizable(false);		
		
		super.createBufferStrategy(2);
		this.strategy = super.getBufferStrategy();
		super.requestFocus();
		
		super.setIgnoreRepaint(true);
	}

	public static void main(String[] args) {
		Aplicacion app = new Aplicacion();
		app.iniciarJuego();
	}
	
	public void iniciarJuego() {
		// Iniciar el escenario
		this.escenario.iniciar(Escenario.DOS_CATALIZADORES_CON_SUSTRATO_EN_EL_CENTRO);
		
		while (this.isVisible()) {
			
			this.actualizarMundo();
			this.pintarMundo();
		}		
	}

	private void pintarMundo() {
		Graphics2D g = (Graphics2D) this.strategy.getDrawGraphics();
		this.escenario.pintar(g);
		this.strategy.show();
	}

	private void actualizarMundo() {
		this.escenario.actualizar();
	}
	
}
