package arbol;

import interfaz.ImagenModPintable;
import interfaz.PlanoRealDibujable;

import java.awt.Color;
import java.awt.Graphics;

import com.koldogontzal.geometria2d.Punto;
import com.koldogontzal.geometria2d.PuntoCoordenadasPolares;
import com.koldogontzal.geometria2d.Vector;

import conversor.ConversorPlanorealPixels;

import lienzo.LienzoEnPlanoReal;

import utils.ColorMod;
import utils.EsquemaDegradadoColor;
import utils.ImagenMod;
import utils.Pixel;
import utils.RectanguloDegradadoColor;

public class Rama implements PlanoRealDibujable, ImagenModPintable {
	
	public static double TASA_DECRECIMIENTO = 0.999995;
	public static double LONGITUD_RAMA_ESTANDARD = 1000;
	
	private Vector origen;
	private double longitud;
	private Vector destino;
	private double tasaDecrecimiento;
	
	private boolean ramaFinal;
	
	private Rama[] ramasHijas;
	
	public Rama(Vector origen) {
		this(origen, Math.random() * LONGITUD_RAMA_ESTANDARD);
	}
	
	public Rama(Vector origen, double longitud) {
		this(origen, longitud, TASA_DECRECIMIENTO);
	}
	
	public Rama(Vector origen, double longitud, double tasaDecr) {
		this.origen = origen;
		this.longitud = longitud;
		this.tasaDecrecimiento = tasaDecr;
		
		double grosorOrigen = origen.getModulo();
		double grosorDestino = grosorOrigen * (1.0 - (1.0 - tasaDecr) * longitud);
		
		// Si el grosor de la rama en el extremo final (Destino) es menor que 1, entonces la rama no puede dividirse más
		// y es una rama final
		if (grosorDestino < 1.0) {
			this.ramaFinal = true;
		} else {
			this.ramaFinal = false;
		}
		
		// Calcula el vector del extremo final de la rama (Destino)
		this.destino = this.calcularVectorDestino();
	}
	
	private Vector calcularVectorDestino() {
		
		double angulo = this.origen.getAngulo();
		Punto puntoOrigenFinal = new PuntoCoordenadasPolares(this.longitud, angulo);
		// Este es el punto de origen del final de la rama
		puntoOrigenFinal.sumar(this.origen.getPuntoOrigen());
		
		// Calcula el grosor de la rama en el extremo final (modulo)
		double modulo = this.origen.getModulo() 
				* (1.0 - (1.0 - this.tasaDecrecimiento) * this.longitud);
		
		// vector al final de la rama
		return new Vector(puntoOrigenFinal, modulo, angulo);
		
	}
	
	public Rama[] dividirRama() {
		if (!this.ramaFinal) {
			this.ramasHijas = new Rama[2];
			
			// Los valores (angulo y %longitud) se pillan aleatoriamente
			double angulo = (Math.random()) * Math.PI / 4; // entre 0º y 45º
			double porcentajeLongitud = (1 + 2 * Math.random()) / 3; // entre 33% y 100%
			Vector[] vectores = this.destino.getComponentesNoCartesianas(angulo, porcentajeLongitud);
vectores[0].multiplicarPorEscalar(1.12); // No veo por qué tenemos que multiplicar por este escalar el grosor de las ramas
vectores[1].multiplicarPorEscalar(1.12);
			this.ramasHijas[0] = new Rama(vectores[0], this.longitud * (2 + Math.random()) / 3.0, this.tasaDecrecimiento);
			this.ramasHijas[1] = new Rama(vectores[1], this.longitud * (2 + Math.random()) / 3.0, this.tasaDecrecimiento);
			
			return ramasHijas;
			
		} else {
			return null;
		}
	}
	
	public Rama getRamaHijaMasGruesa() {
		if (this.ramasHijas != null) {
			if (this.ramasHijas[0].getOrigen().getModulo() < this.ramasHijas[1].getOrigen().getModulo()) {
				return this.ramasHijas[1];
			} else {
				return this.ramasHijas[0];
			}
		} else {
			return null;
		}
	}
	
	public Rama getRamaHijaMasFina() {
		if (this.ramasHijas != null) {
			if (this.ramasHijas[0].getOrigen().getModulo() < this.ramasHijas[1].getOrigen().getModulo()) {
				return this.ramasHijas[0];
			} else {
				return this.ramasHijas[1];
			}
		} else {
			return null;
		}
	}

	public Vector getDestino() {
		return this.destino;
	}

	public Vector getOrigen() {
		return this.origen;
	}
	
	public Punto[] getLimites() {
		Punto[] ret = new Punto[2];
		Punto pOrigen = this.origen.getPuntoOrigen();
		double grosorOrigen = this.origen.getModulo();		
		Punto pDestino = this.destino.getPuntoOrigen();
		double grosorDestino = this.destino.getModulo();
		
		ret[0] = new Punto(
				Math.min(pOrigen.getX() - grosorOrigen, pDestino.getX() - grosorDestino), 
				Math.min(pOrigen.getY() - grosorOrigen, pDestino.getY() - grosorDestino));
		ret[1] = new Punto(
				Math.max(pOrigen.getX() + grosorOrigen, pDestino.getX() + grosorDestino), 
				Math.max(pOrigen.getY() + grosorOrigen, pDestino.getY() + grosorDestino));
		
		return ret;
	}
	
	public void dibujar(LienzoEnPlanoReal lienzo, Graphics g) {
		// Dibuja arco de inicio rama
		Punto centro = this.getOrigen().getPuntoOrigen();		
		//lienzo.dibujaCruz(g, centro);
		double radio = this.getOrigen().getModulo();
		double diametro = 2 * radio;
		double angulo = this.getOrigen().getAngulo() + (Math.PI / 2);
		lienzo.rellenaArco(g, centro, diametro, diametro, angulo, Math.PI);
		// Dibuja ramas
		Punto desp0 = new PuntoCoordenadasPolares(radio, angulo);
		Punto i0 = new Punto(centro);
		i0.sumar(desp0);
		Punto i1 = new Punto(centro);
		i1.restar(desp0);
		
		// Dibuja final de rama
		Punto centro1 = this.getDestino().getPuntoOrigen();
		double radio1 = this.getDestino().getModulo();
		double angulo1 = this.getDestino().getAngulo() - (Math.PI / 2);
		double diametro1 = 2 * radio1;
		lienzo.rellenaArco(g, centro1, diametro1, diametro1, angulo1, Math.PI);
		Punto desp1 = new PuntoCoordenadasPolares(radio1, angulo1);
		Punto f1 = new Punto(centro1);
		f1.sumar(desp1);
		Punto f0 = new Punto(centro1);
		f0.restar(desp1);
		
		// Dibuja bordes
		lienzo.dibujaLinea(g, i1, f1);
		lienzo.dibujaLinea(g, i0, f0);
		
		lienzo.rellenaTrapecio(g, i0, f0, i1, f1);

	}


	public void pintar(ImagenMod img, ConversorPlanorealPixels conversor, ColorMod c, boolean relleno) {
		// Dibuja arco de inicio rama
		Punto centro0 = this.getOrigen().getPuntoOrigen();
		double radio0 = this.getOrigen().getModulo();
		double diametro0 = 2 * radio0;
		double angulo0 = this.getOrigen().getAngulo() + (Math.PI / 2);
		Pixel pxCentro0 = conversor.convertir(centro0);
		int pxDiametro0 = conversor.convX(conversor.getPuntoInfIzq().getX()
				+ diametro0);
		if (relleno) {
			img.rellenaArco(pxCentro0.getX(), pxCentro0.getY(), pxDiametro0,
					pxDiametro0, angulo0, Math.PI, c,
					ImagenMod.MODO_PINTAR_SIN_TRATAMIENTO);
		} else {
			img.dibujaArco(pxCentro0.getX(), pxCentro0.getY(), pxDiametro0,
					pxDiametro0, angulo0, Math.PI, c,
					ImagenMod.MODO_PINTAR_SIN_TRATAMIENTO);
		}

		// Calcula los Puntos de inicio de las dos rectas de la rama. Los Puntos
		// son i0 e i1
		Punto desp0 = new PuntoCoordenadasPolares(radio0, angulo0);
		Punto i0 = new Punto(centro0);
		i0.sumar(desp0);
		Punto i1 = new Punto(centro0);
		i1.restar(desp0);

		// Dibuja el arco del final de rama
		Punto centro1 = this.getDestino().getPuntoOrigen();
		double radio1 = this.getDestino().getModulo();
		double angulo1 = this.getDestino().getAngulo() - (Math.PI / 2);
		double diametro1 = 2 * radio1;
		Pixel pxCentro1 = conversor.convertir(centro1);
		int pxDiametro1 = conversor.convX(conversor.getPuntoInfIzq().getX()
				+ diametro1);
		if (relleno) {
			img.rellenaArco(pxCentro1.getX(), pxCentro1.getY(), pxDiametro1,
					pxDiametro1, angulo1, Math.PI, c,
					ImagenMod.MODO_PINTAR_SIN_TRATAMIENTO);
		} else {
			img.dibujaArco(pxCentro1.getX(), pxCentro1.getY(), pxDiametro1,
					pxDiametro1, angulo1, Math.PI, c,
					ImagenMod.MODO_PINTAR_SIN_TRATAMIENTO);
		}
		
		// Calcula los Puntos de fin de las dos rectas de la rama. Son los Puntos f0 y f1.
		Punto desp1 = new PuntoCoordenadasPolares(radio1, angulo1);
		Punto f1 = new Punto(centro1);
		f1.sumar(desp1);
		Punto f0 = new Punto(centro1);
		f0.restar(desp1);
		
		// Convierte de Puntos a Pixels
		Pixel i0px = conversor.convertir(i0);
		Pixel i1px = conversor.convertir(i1);
		Pixel f0px = conversor.convertir(f0);
		Pixel f1px = conversor.convertir(f1);
		
/*
 *  // Para depuración, encontrar errores de pixels negativos:		
 *  System.out.println("Rama: " + this)	;
 *  System.out.println("Conversor: " + conversor);
 *  System.out.println("Linea 0: " + i0px + " > " + f0px + "   Plano real: " + i0 + " > " + f0);
 *  System.out.println("Linea 1: " + i1px + " > " + f1px + "   Plano real: " + i1 + " > " + f1);
 */
		
		// Dibuja los bordes de la rama
		img.dibujaLinea(i0px.getX(), i0px.getY(), f0px.getX(), f0px.getY(), c, ImagenMod.MODO_PINTAR_SIN_TRATAMIENTO);
		img.dibujaLinea(i1px.getX(), i1px.getY(), f1px.getX(), f1px.getY(), c, ImagenMod.MODO_PINTAR_SIN_TRATAMIENTO);
		
		// Rellena el trapecio que forman ambas rectas si hay que rellenar la rama
		if (relleno) {
			img.rellenaTrapecio(i0px, f0px, i1px, f1px, c, ImagenMod.MODO_PINTAR_SIN_TRATAMIENTO);
		}
	}
	
	@Override
	public String toString() {
		String ret = "Origen: " + this.origen.getPuntoOrigen() + " (r" + this.origen.getModulo() + ")   Destino: " + this.destino.getPuntoOrigen() + " (r" + this.destino.getModulo() + ")";
		
		return ret;
	}

	@Override
	public void pintar(ImagenMod img, int xInf, int yInf, int xSup, int ySup, Color c, int modo) {
		// Pinta la rama según el "modo" sobre la imagen "img" entre los puntos (xInf,yInf) y (xSup,ySup)  

		int x0, y0, x1, y1, dx, dy;

		x0 = Math.min(xInf, xSup);
		y0 = Math.min(yInf, ySup);

		x1 = Math.max(xInf, xSup);
		y1 = Math.max(yInf, ySup);
		
		dx = x1 - x0;
		dy = y1 - y0;
		
		Punto limites[] = this.getLimites();

		ConversorPlanorealPixels conversor = new ConversorPlanorealPixels(limites[0], limites[1], dx, dy);
		
		ImagenMod aux = new ImagenMod(dx, dy);
		
		this.pintar(aux, conversor, new ColorMod(c), true);
		
		img.pintaImagen(xInf, yInf, aux, modo);
		
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
		this.pintar(ret, 0, 0, ancho, alto, Color.WHITE, ImagenMod.MODO_PINTAR_SIN_TRATAMIENTO);
		
		// Calcula el rectangulo de degradado
		Punto limites[] = this.getLimites();
		ConversorPlanorealPixels conversor = new ConversorPlanorealPixels(limites[0], limites[1], ancho, alto);
		
		Pixel px0 = conversor.convertir(this.getOrigen().getPuntoOrigen());
		Pixel px1 = conversor.convertir(this.getDestino().getPuntoOrigen());
		
		RectanguloDegradadoColor rec = new RectanguloDegradadoColor(ancho, alto, px0, px1, esq);
		
		// Los multiplica y devuelve el resultado
		ret.pintaImagen(0, 0, rec.getImagenMod(), ImagenMod.MODO_PINTAR_MULTIPLICAR);
		return ret;
	}
}
