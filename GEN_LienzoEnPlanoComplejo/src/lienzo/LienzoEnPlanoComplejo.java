package lienzo;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.koldogontzal.geometria2d.Punto;
import com.koldogontzal.numeroscomplejos.ConversorNumerocomplejoColormod;
import com.koldogontzal.numeroscomplejos.ConversorPlanocomplejoPixels;
import com.koldogontzal.numeroscomplejos.NumeroComplejo;

import Interfaz.PlanoComplejoDibujable;

public class LienzoEnPlanoComplejo extends Canvas {

	private static final long serialVersionUID = -4609096709890051532L;

	private ConversorPlanocomplejoPixels convEspacial;
	private ConversorNumerocomplejoColormod convColor;
	
	private double unidadEje;

	// Resto
	private PlanoComplejoDibujable objeto;
	private boolean ejes;

	public LienzoEnPlanoComplejo(PlanoComplejoDibujable objetoADibujar, NumeroComplejo infIzq,
			NumeroComplejo supDrch, boolean ejes) {

		this.objeto = objetoADibujar;
		this.ejes = ejes;

		this.convEspacial = new ConversorPlanocomplejoPixels(infIzq, supDrch, 100, 100);
		this.convColor = new ConversorNumerocomplejoColormod();
		
	}

	public Dimension getPreferredSize() {
		// Tamaño preferido de la ventana (inicial)
		return new Dimension(1200, 740);
	}

	private int convX(double x) {
		// Se introduce una coordenada en el eje X real y se devuelve el pixel al que
		// corresponde
		return this.convEspacial.convX(x);
	}

	private int convY(double y) {
		// Se introduce una coordenada en el eje Y imaginario y se devuelve el pixel al
		// que corresponde
		return this.convEspacial.convY(y);
	}

	private Punto convPunto(NumeroComplejo z) {
		// Se introduce un número en el plano complejo y se devuelve el pixel al que
		// corresponde
		return new Punto(this.convX(z.getRe()), this.convY(z.getIm()));
	}
	
	public ConversorPlanocomplejoPixels getConversorPlanocomplejoPixels() {
		return this.convEspacial;
	}

	/*
	 * Paint when the AWT tells us to...
	 */
	public void paint(Graphics g) {
		// Recalcular valores para el nuevo tamaño de la ventana
		Dimension size = getSize();
		this.convEspacial.CambiarTamagnoPixels(size.width, size.height);

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
		double xMin = this.convEspacial.getNumeroComplejoInfIzq().getRe();
		double yMin = this.convEspacial.getNumeroComplejoInfIzq().getIm();
		double xMax = this.convEspacial.getNumeroComplejoSupDrch().getRe();
		double yMax = this.convEspacial.getNumeroComplejoSupDrch().getIm();

		// calcula las unidades de distancia entre ejes
		double logFactor = Math.log10(this.convEspacial.getFactorConversionX());
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
