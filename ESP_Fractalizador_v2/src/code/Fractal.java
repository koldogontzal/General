package code;

import interfaz.ImagenModPintable;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

import com.koldogontzal.geometria2d.Punto;
import com.koldogontzal.geometria2d.Vector;

import conversor.ConversorPlanorealPixels;

import utils.EsquemaDegradadoColor;
import utils.ImagenMod;
import utils.Pixel;

public class Fractal implements  ImagenModPintable {

	private PatronFractal patron;

	private ArrayList<Vector> vectores;

	private Punto supDrch = new Punto(1, 0); // Punto superior derecho del fractal

	private Punto infIzq = new Punto(0, 0); // Punto inferior izquierdo del fractal

	private int iteraciones;

	public Fractal(PatronFractal patron, int niveles) {
		this.patron = patron;
		this.vectores = new ArrayList<Vector>(100);
		this.iteraciones = niveles;

		this.recalcularListaVectores();
	}

	public Fractal(PatronFractal patron) {
		this(patron, 100);
	}

	public void setNiveles(int niveles) {
		// Define cuantas iteraciones se harían sobre la línea original y recalcula todo
		this.iteraciones = niveles;

		this.recalcularListaVectores();
	}

	private void recalcularListaVectores() {
		// Lo primero, empieza con un vector único sobre el cual se aplica el
		// Patron tantas veces como
		// indique "niveles". EL vector inicial es el (0,0)->(1,0), es decir la
		// unidad en el eje X.
		this.vectores.add(new Vector(new Punto(1, 0)));

		int contadorElementos = 1;
		int i = 0;

		// Recorre la lista de vectores por analizar hasta que no hay más
		// elementos o se ha alcanzado ya el límite marcado por this.iteraciones
		while ((contadorElementos < this.iteraciones) && (i < this.vectores.size())) {
			
			Vector vectorParaTransformar = this.vectores.get(i);

			Vector[] nuevosVectores = this.patron	.recalcularConNuevoVector(vectorParaTransformar);
			
			for (Vector nuevoVector : nuevosVectores) {
				this.vectores.add(nuevoVector);
				contadorElementos++;
				this.actualizarExtremosFractal(nuevoVector);
			}
			i++;

			// System.out.println(contadorElementos);
		}
	}

	private void actualizarExtremosFractal(Vector vector) {	
		this.infIzq = Punto.min(vector.getPuntoMin(), this.infIzq);
		this.supDrch = Punto.max(vector.getPuntoMax(), this.supDrch);
	}

	@Override
	public String toString() {
		String resultado = "";
		for (int i = 0; i < this.vectores.size(); i++) {
			resultado = resultado + this.vectores.get(i) + "\n";
		}
		return resultado;
	}
	

	@Override
	public void pintar(ImagenMod img, int xInf, int yInf, int xSup, int ySup, Color c,  int modo) {
		// Calcula las medidas en pixels del dibujo del fractal
		int ancho = xSup - xInf;
		int alto = ySup - yInf;
		
		// Define el conversor para traducir posiciones en el plano real a pixels de la imagen
		ConversorPlanorealPixels conversor = new ConversorPlanorealPixels(this.infIzq, this.supDrch, ancho, alto);
		
		// recorre la lista de Vectores y los dibuja uno a uno
		Iterator<Vector> i = this.vectores.iterator();
		while (i.hasNext()) {
			Vector v = i.next();
			Pixel p0 = conversor.convertir(v.getPuntoOrigen());
			Pixel p1 = conversor.convertir(v.getPuntoFinal());
			
			img.dibujaLinea(p0.getX(), p0.getY(), p1.getX(), p1.getY(), c, modo);
		}		
	}

	@Override
	public ImagenMod getImagenMod(int ancho, int alto, Color c) {
		ImagenMod ret = new ImagenMod(ancho, alto);
		this.pintar(ret, 0, 0, ancho, alto, c, ImagenMod.MODO_PINTAR_SIN_TRATAMIENTO);		
		return ret;
	}

	@Override
	public ImagenMod getImagenMod(int ancho, int alto, EsquemaDegradadoColor esq) {
		ImagenMod ret = new ImagenMod(ancho, alto);
		
		// Define el conversor para traducir posiciones en el plano real a pixels de la imagen
		ConversorPlanorealPixels conversor = new ConversorPlanorealPixels(this.infIzq, this.supDrch, ancho, alto);
		
		// recorre la lista de Vectores y los dibuja uno a uno
		Iterator<Vector> i = this.vectores.iterator();
		while (i.hasNext()) {
			Vector v = i.next();
			Pixel p0 = conversor.convertir(v.getPuntoOrigen());
			Pixel p1 = conversor.convertir(v.getPuntoFinal());
			
			ret.dibujaLinea(p0.getX(), p0.getY(), p1.getX(), p1.getY(), esq.getColorModAtPos(1f - (float)v.getPuntoOrigen().distanciaA(v.getPuntoFinal())), ImagenMod.MODO_PINTAR_SUPERPONER);
		}		

		return ret;
	}


}