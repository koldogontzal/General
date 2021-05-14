package com.koldogontzal.dibujadorfuncionescomplejas;

import com.koldogontzal.numeroscomplejos.NumeroComplejo;

import conversor.ConversorPlanorealPixels;
import utils.Pixel;

public class ConversorPlanocomplejoPixels {
	
	private ConversorPlanorealPixels conv;

	/**
	 * Crea un objeto ConversorPlanocomplejoPixels que permite convertir entre objetos Pixels y objetos NumeroComplejo en el plano Complejo. Si la proporci�n entre los l�mites del plano complejor dados y el aspecto en pixels de la ventana a mostrar son distintos, recalcula los l�mites para adecuarse al aspecto de la ventana en pixels.
	 * @param infIzq
	 * NumeroComplejo que corresponde con la esquina inferior izquierda del plano complejo que se va a representar. Tanto la aparte real como la parte imaginaria deben ser menores que la parte real y la parte imaginaria de supDrch.
	 * @param supDrch
	 * NumeroComplejo que corresponde con la esquina superior derecha del plano complejo que se va a representar. Tanto la aparte real como la parte imaginaria deben ser mayores que la parte real y la parte imaginaria de infIzq.
	 * @param pixelsX
	 * Ancho, medido en pixels, que va a ocupar en la pantalla la secci�n de plano complejo a mostrar. 
	 * @param pixelsY
	 * Alto, medido en pixels, que va a ocupar en la pantalla la secci�n de plano complejo a mostrar.
	 */
	public ConversorPlanocomplejoPixels(NumeroComplejo infIzq, NumeroComplejo supDrch, int pixelsX, int pixelsY) {
		this.conv = new ConversorPlanorealPixels(infIzq.toPunto(), supDrch.toPunto(), pixelsX, pixelsY);
	}
	
	/**
	 * Permite modificar el valor de la esquina inferior izquierda del plano complejo a representar.
	 * @param infIzq
	 * NumeroComplejo que representa a la nueva esquina inferior izquierda. 
	 */
	public void CambiarNumeroComplejoInfIzq(NumeroComplejo infIzq) {
		this.conv.CambiarPuntoInfIzq(infIzq.toPunto());
	}
	
	/**
	 * Permite modificar el valor de la esquina superior derecha del plano complejo a representar.
	 * @param supDrch
	 * NumeroComplejo que representa a la nueva esquina superior derecha.
	 */
	public void CambiarNumeroComplejoSupDrch(NumeroComplejo supDrch) {
		this.conv.CambiarPuntoSupDrch(supDrch.toPunto());
	}
	
	/**
	 * Modifica el tama�o que ocupa en la pantalla la secci�n de plano complejo a mostrar. 
	 * @param pixelsX
	 * Nuevo ancho, medido en pixels.
	 * @param y
	 * Nuevo alto, medido en pixels.
	 */
	public void CambiarTamagnoPixels(int pixelsX, int pixelsY) {
		this.conv.CambiarTamagnoPixels(pixelsX, pixelsY);
	}
	
	/**
	 * Devuelve el l�mite real en el plano complejo que representa a la esquina inferior izquierda del plano complejo a representar.
	 * @return
	 * NumeroComplejo que representa a la esquina inferior izquierda.
	 */
	public NumeroComplejo getNumeroComplejoInfIzq() {
		return new NumeroComplejo(this.conv.getPuntoInfIzq());
	}
	
	/**
	 * Devuelve el l�mite real en el plano complejo que representa a la esquina superior derecha del plano complejo a representar.
	 * @return
	 * NumeroComplejo que representa a la esquina superior derecha.
	 */
	public NumeroComplejo getNumeroComplejoSupDrch() {
		return new NumeroComplejo(this.conv.getPuntoSupDrch());
	}
	
	/**
	 * 
	 * @return
	 * Obtiene el ancho, en pixels, que ocupa en la pantalla la secci�n del plano complejo a mostrar (eje X).
	 */
	public int getPixelsX() {
		return this.conv.getPixelsX();
	}
	
	/**
	 * 
	 * @return
	 * Obtiene el alto, en pixels, que ocupa en la pantalla la secci�n del plano complejo a mostrar (eje Y).
	 */
	public int getPixelsY() {
		return this.conv.getPixelsY();
	}
	
	/**
	 * Convierte un n�mero complejo en el pixel al que equivale.
	 * @param z
	 * NumeroComplejo que se quiere convertir.
	 * @return
	 * Pixel al que equivale.
	 */
	public Pixel convertir(NumeroComplejo z) {
		return this.conv.convertir(z.toPunto());		
	}
	
	/**
	 * Convierte un pixel en el n�mero complejo al que equivale.
	 * @param p
	 * Pixel que se quiere converitir.
	 * @return
	 * NumeroComplejo al que equivale.
	 */
	public NumeroComplejo convertir(Pixel p) {
		return new NumeroComplejo(this.conv.convertir(p));
	}
	
	/**
	 * Convierte un pixel (definido por sus coordenadas X e Y) en el n�mero complejo al que equivale.
	 * @param coordX
	 * Coordenada X horizontal del Pixel.
	 * @param coordY
	 * Coordenada Y vertical del Pixel.
	 * @return
	 * NumeroComplejo al que equivale.
	 */
	public NumeroComplejo convertir(int coordX, int coordY) {
		return convertir(new Pixel(coordX, coordY));
	}
	
	/**
	 * Convierte un valor dado en el eje real en una coordenada (pixel) horizontal.
	 * @param parteReal
	 * El valor horizontal en el eje real.
	 * @return
	 * La coordenada (pixel) horizontal correspondiente.
	 */
	public int convX(double parteReal) {
		return this.conv.convX(parteReal);
	}
	
	/**
	 * Convierte un valor dado en el eje imaginario en una coordenada (pixel) vertical
	 * @param parteIm
	 * El valor vertical en el eje imaginario.
	 * @return
	 * La coordenada (pixel) vertical correspondiente.
	 */
	public int convY(double parteIm) {
		return this.conv.convY(parteIm);
	}
	
	public double getFactorConversionX() {
		return this.conv.getFactorConversionX();
	}
	
	@Override
	public String toString() {
		String ret;
		ret = "Dimensiones en el plano complejo: "
				+ this.getNumeroComplejoInfIzq() + " > "
				+ this.getNumeroComplejoSupDrch()
				+ "   Para c�lculos, se usa el siguiente objeto ConversorPlanorealPixels: " + this.conv;
		return ret;
	}
		
}
