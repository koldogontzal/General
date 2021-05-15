package old;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;

import com.koldogontzal.dibujadorfuncionescomplejas.ConversorNumerocomplejoColormod;
import com.koldogontzal.dibujadorfuncionescomplejas.ConversorPlanocomplejoPixels;
import com.koldogontzal.geometria2d.Punto;
import com.koldogontzal.numeroscomplejos.NumeroComplejo;

public class LienzoEnPlanoComplejo_old extends Canvas {

	private static final long serialVersionUID = -4609096709890051532L;

	private ConversorPlanocomplejoPixels convEspacial;
	private ConversorNumerocomplejoColormod convColor;
	
	private double unidadEje;

	// Resto
	private PlanoComplejoDibujable_old objeto;
	private boolean ejes;
	private boolean leyenda;

	public LienzoEnPlanoComplejo_old(PlanoComplejoDibujable_old objetoADibujar, NumeroComplejo infIzq,
			NumeroComplejo supDrch, boolean ejes) {

		this.objeto = objetoADibujar;
		this.ejes = ejes;
		this.leyenda = true;

		this.convEspacial = new ConversorPlanocomplejoPixels(infIzq, supDrch, 100, 100);
		this.convColor = new ConversorNumerocomplejoColormod();
		
		this.
		createBufferStrategy(1);
		
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
		
		g.setPaintMode();
		
		// Recalcular valores para el nuevo tamaño de la ventana
		Dimension size = getSize();
		this.convEspacial.CambiarTamagnoPixels(size.width, size.height);
		
		// Reparametriza el conversor de color
		// TODO: Pensar si poner aquí esta función o delegarlar al objeto PlanoComplejoDibujable
	
		//this.convColor = recalculaConversorNumerocomplejoColormod();
		
		System.out.println("LienzoEnPlanoComplejo llama al método dibujar del objeto.");

		// Dibuja el objeto incrustado
		this.objeto.dibujar(this, g);
		
		System.out.println("En el método paint del objeto: " + this.objeto);
		
		System.out.println("LienzoEnPlanoComplejo dibuja los ejes.");
		// Dibuja los ejes
		if (this.ejes) {
			this.dibujaEjes(g);
		}

		// Dibuja la leyenda
		if (this.leyenda) {
			this.dibujaLeyenda(g);
		}

		
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

		g.setXORMode(Color.black);
		
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
		
		g.setPaintMode();

	}

	private void dibujaEje(Graphics g, NumeroComplejo zini, NumeroComplejo zfin) {
		
		// TODO: Hacer la línea semi transparente (modificar la LUM del punto)
		Punto inCn = this.convPunto(zini);
		Punto fnCn = this.convPunto(zfin);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawLine((int) inCn.getX(), (int) inCn.getY(), (int) fnCn.getX(), (int) fnCn.getY());
	}
	
	
	public void dibujaPunto(Graphics g, Punto p, Color c) {
		g.setColor(c);
    	g.drawLine((int)p.getX(), (int)p.getY(), (int)p.getX(), (int)p.getY());
    }
	
	
	private void dibujaLeyenda(Graphics g) {
		
		// Pinta el fondo del cuadrado de la leyenda con un color plano (por defecto el negro)
    	Dimension size = getSize();
		g.setColor(Color.black);
    	g.fillRect(size.width - 210, 10, 200, 300);

	
	}
}
