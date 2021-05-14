package com.koldogontzal.dibujadorfuncionescomplejas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.koldogontzal.geometria2d.Punto;
import com.koldogontzal.numeroscomplejos.FuncionComplejaMonoparametro;
import com.koldogontzal.numeroscomplejos.NumeroComplejo;

import utils.ColorMod;

public class DibujadorFuncionesPlanoComplejo extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 289576514590821745L;

	private FuncionComplejaMonoparametro func;

	private ConversorPlanocomplejoPixels convEspacial;
	private ConversorNumerocomplejoColormod convColor;

	private boolean ejes;
	private boolean leyenda;

	private double unidadEje;

	public DibujadorFuncionesPlanoComplejo(FuncionComplejaMonoparametro func, NumeroComplejo infIzq,
			NumeroComplejo supDrch, boolean ejes, boolean leyenda) {

		this.func = func;

		this.ejes = ejes;
		this.leyenda = leyenda;

		this.convEspacial = new ConversorPlanocomplejoPixels(infIzq, supDrch, 100, 100);
		this.convColor = new ConversorNumerocomplejoColormod();

	}

	public void mostrar() {
		//
		JFrame frame = new JFrame();
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(20, 20, 1000, 500);
		frame.setVisible(true);
	}

	public void paint(Graphics g) {

		g.setPaintMode();

		// Recalcular valores para el nuevo tamaño de la ventana
		Dimension size = getSize();
		this.convEspacial.CambiarTamagnoPixels(size.width, size.height);

		// Reparametriza el conversor de color
		this.recalibrarNumerocomplejoColormod();

		// Dibuja el objeto incrustado
		this.pintaFuncionCompleja(g);

		// Dibuja los ejes
		if (this.ejes) {
			this.dibujaEjes(g);
		}

		// Dibuja la leyenda
		if (this.leyenda) {
			this.dibujaLeyenda(g);
		}

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

	private void dibujaEjes(Graphics g) {
		double xMin = this.convEspacial.getNumeroComplejoInfIzq().getRe();
		double yMin = this.convEspacial.getNumeroComplejoInfIzq().getIm();
		double xMax = this.convEspacial.getNumeroComplejoSupDrch().getRe();
		double yMax = this.convEspacial.getNumeroComplejoSupDrch().getIm();

		// calcula las unidades de distancia entre ejes
		double logFactor = Math.log10(this.convEspacial.getFactorConversionX());
		logFactor = Math.round(logFactor);
		this.unidadEje = Math.pow(10.0, 2.0 - logFactor);

		boolean esEjePrincipal;

		// Ejes verticales
		for (int i = (int) (xMin / this.unidadEje); i <= (int) (xMax / this.unidadEje); i++) {
			if (i == 0) {
				esEjePrincipal = true;
			} else {
				esEjePrincipal = false;
			}
			double posicion = i * this.unidadEje;

			this.dibujaEje(g, new NumeroComplejo(posicion, yMin), new NumeroComplejo(posicion, yMax), esEjePrincipal);
		}

		// Ejes horizontales
		for (int i = (int) (yMin / this.unidadEje); i <= (int) (yMax / this.unidadEje); i++) {
			if (i == 0) {
				esEjePrincipal = true;
			} else {
				esEjePrincipal = false;
			}
			double posicion = i * this.unidadEje;

			this.dibujaEje(g, new NumeroComplejo(xMin, posicion), new NumeroComplejo(xMax, posicion), esEjePrincipal);
		}

	}

	private void dibujaEje(Graphics g, NumeroComplejo zini, NumeroComplejo zfin, boolean esEjePrincipal) {

		Punto inCn = this.convPunto(zini);
		Punto fnCn = this.convPunto(zfin);

		boolean esEjeHorizontal = false;
		int valorInicioLinea;
		int valorFinalLinea;
		int posicionEnElOtroEje;
		if (inCn.getX() == fnCn.getX()) {
			// es un eje vertical
			esEjeHorizontal = false;
			valorInicioLinea = Math.min((int) inCn.getY(), (int) fnCn.getY());
			valorFinalLinea = Math.max((int) inCn.getY(), (int) fnCn.getY());
			posicionEnElOtroEje = (int) inCn.getX();
		} else {
			// es un eje horizontal
			esEjeHorizontal = true;
			valorInicioLinea = Math.min((int) inCn.getX(), (int) fnCn.getX());
			valorFinalLinea = Math.max((int) inCn.getX(), (int) fnCn.getX());
			posicionEnElOtroEje = (int) inCn.getY();
		}

		for (int j = valorInicioLinea; j <= valorFinalLinea; j++) {
			int x, y;
			if (esEjeHorizontal) {
				x = j;
				y = posicionEnElOtroEje;
			} else {
				x = posicionEnElOtroEje;
				y = j;
			}

			// obtiene el valor del color del píxel
			ColorMod col = this.convColor.convertir(this.func.valor(this.convEspacial.convertir(x, y)));

			if (esEjePrincipal) {
				col = ColorMod.getColorProporcionalRGB(col, Color.WHITE, 0.5f);
			} else {
				col = ColorMod.getColorProporcionalRGB(col, Color.WHITE, 0.15f);
			}

			// dibuja el pixel
			this.dibujaPunto(g, new Punto(x, y), col);

		}

	}

	public void dibujaPunto(Graphics g, Punto p, Color c) {
		g.setColor(c);
		g.drawLine((int) p.getX(), (int) p.getY(), (int) p.getX(), (int) p.getY());
	}

	private void dibujaLeyenda(Graphics g) {

		// Pinta el fondo del cuadrado de la leyenda con un color plano (por defecto el
		// negro)
		Dimension size = getSize();
		g.setColor(Color.black);
		g.fillRect(size.width - 210, 10, 200, 300);

		/*
		 * int w = getSize().width; int midW = w / 2;
		 * 
		 * g.drawString("XOR Mode", 0, 30);
		 * 
		 * g.drawOval(7, 37, 50, 50);
		 * 
		 * g.setXORMode(Color.white);
		 * 
		 * for (int i = 0; i < 75; i += 3) { g.drawOval(10 + i, 40 + i, 50, 50); }
		 * 
		 * g.setPaintMode();
		 * 
		 * g.drawString("Paint Mode", midW, 30);
		 * 
		 * g.drawOval(midW + 7, 37, 50, 50);
		 * 
		 * for (int i = 0; i < 75; i += 3) { g.drawOval(midW + 10 + i, 40 + i, 50, 50);
		 * }
		 */

	}

	private void recalibrarNumerocomplejoColormod() {
		// Primero recorre la función, buscando el módulo máximo para parametrizar
		// correctamente el objeto ConversorNumerocomplejoColormod. No recorre todos los
		// puntos, sino, unicamente 1 de cada 20
		int paso = 20;
		// Establece el valor mínimo de saturación en el color. Puede variar entre 0 y
		// 100.
		this.convColor.setMinSat(30);
		this.convColor.setMaxRadio(1E-15);

		int ancho = this.getWidth();
		int alto = this.getHeight();

		for (int x = 0; x < ancho; x = x + paso) {
			for (int y = 0; y < alto; y = y + paso) {
				NumeroComplejo fz = this.func.valor(this.convEspacial.convertir(x, y));
				this.convColor.modificarMaxRadio(fz.getModulo());
			}
		}
	}

	private void pintaFuncionCompleja(Graphics g) {

		int ancho = this.getWidth();
		int alto = this.getHeight();

		// En segundo lugar,
		// Ahora recorre todos los puntos del lienzo calculando el valor de la función,
		// para pintarlos uno a uno
		for (int x = 0; x <= ancho; x = x + 1) {
			for (int y = 0; y <= alto; y = y + 1) {
				NumeroComplejo fz = this.func.valor(this.convEspacial.convertir(x, y));
				ColorMod c = this.convColor.convertir(fz);

				this.dibujaPunto(g, new Punto(x, y), c);
			}
		}
	}

}