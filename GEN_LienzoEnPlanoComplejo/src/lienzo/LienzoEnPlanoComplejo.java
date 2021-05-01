package lienzo;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.koldogontzal.geometria2d.Punto;
import com.koldogontzal.numeroscomplejos.ConversorPlanocomplejoPixels;
import com.koldogontzal.numeroscomplejos.NumeroComplejo;

import Interfaz.PlanoComplejoDibujable;

public class LienzoEnPlanoComplejo extends Canvas {

	private static final long serialVersionUID = -4609096709890051532L;

	private ConversorPlanocomplejoPixels conversor;
	private double unidadEje;

	// Resto
	private PlanoComplejoDibujable objeto;
	private Color color;
	private boolean ejes;

	public LienzoEnPlanoComplejo(PlanoComplejoDibujable objetoADibujar, Color color, NumeroComplejo infIzq,
			NumeroComplejo supDrch, boolean ejes) {

		this.objeto = objetoADibujar;
		this.color = color;
		this.ejes = ejes;

		this.conversor = new ConversorPlanocomplejoPixels(infIzq, supDrch, 100, 100);
	}

	public Dimension getPreferredSize() {
		// Tamaño preferido de la ventana (inicial)
		return new Dimension(1200, 740);
	}

	private int convX(double x) {
		// Se introduce una coordenada en el eje X real y se devuelve el pixel al que
		// corresponde
		return this.conversor.convX(x);
	}

	private int convY(double y) {
		// Se introduce una coordenada en el eje Y imaginario y se devuelve el pixel al
		// que corresponde
		return this.conversor.convY(y);
	}

	private Punto convPunto(NumeroComplejo z) {
		// Se introduce un número en el plano complejo y se devuelve el pixel al que
		// corresponde
		return new Punto(this.convX(z.getRe()), this.convY(z.getIm()));
	}
	
	public ConversorPlanocomplejoPixels getConversorPlanocomplejoPixels() {
		return this.conversor;
	}

	/*
	 * Paint when the AWT tells us to...
	 */
	public void paint(Graphics g) {
		// Recalcular valores para el nuevo tamaño de la ventana
		Dimension size = getSize();
		this.conversor.CambiarTamagnoPixels(size.width, size.height);

		// Dibuja el objeto incrustado
		this.objeto.dibujar(this, g);

		// Dibuja los ejes
		if (this.ejes) {
			this.dibujaEjes(g);
		}

		// Fija el color para la figura
		// g.setColor(this.color);

		
	}

	private void dibujaEjes(Graphics g) {
		double xMin = this.conversor.getNumeroComplejoInfIzq().getRe();
		double yMin = this.conversor.getNumeroComplejoInfIzq().getIm();
		double xMax = this.conversor.getNumeroComplejoSupDrch().getRe();
		double yMax = this.conversor.getNumeroComplejoSupDrch().getIm();

		// calcula las unidades de distancia entre ejes
		double logFactor = Math.log10(this.conversor.getFactorConversionX());
		logFactor = Math.round(logFactor);
		this.unidadEje = Math.pow(10.0, 2.0 - logFactor);

		// Ejes verticales
		for (double i = unidadEje * (int) (xMin / unidadEje); i <= unidadEje * (int) (xMax / unidadEje); i = i
				+ unidadEje) {
			if (i != 0) {
				g.setColor(Color.gray);
			} else {
				g.setColor(Color.cyan);
			}

			this.dibujaEje(g, new NumeroComplejo(i, yMin), new NumeroComplejo(i, yMax));
		}

		// Ejes horizontales
		for (double i = unidadEje * (int) (yMin / unidadEje); i <= unidadEje * (int) (yMax / unidadEje); i = i
				+ unidadEje) {
			if (i != 0) {
				g.setColor(Color.gray);
			} else {
				g.setColor(Color.cyan);
			}

			this.dibujaEje(g, new NumeroComplejo(xMin, i), new NumeroComplejo(xMax, i));
		}

	}

	public void dibujaEje(Graphics g, NumeroComplejo zini, NumeroComplejo zfin) {
		
		// TO DO: Hacer la línea semi transparente (modificar la LUM del punto)
		Punto inCn = this.convPunto(zini);
		Punto fnCn = this.convPunto(zfin);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawLine((int) inCn.getX(), (int) inCn.getY(), (int) fnCn.getX(), (int) fnCn.getY());
	}
	
	
	public void dibujaPunto(Graphics g, Punto p, Color c) {
		g.setColor(c);
    	g.drawLine((int)p.getX(), (int)p.getY(), (int)p.getX(), (int)p.getY());
    }
	
}
