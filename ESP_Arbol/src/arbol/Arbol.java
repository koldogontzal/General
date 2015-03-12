package arbol;

import interfaz.PlanoRealDibujable;
import interfaz.ImagenModPintable;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import com.koldogontzal.geometria2d.Punto;

import conversor.ConversorPlanorealPixels;

import lienzo.LienzoEnPlanoReal;

import utils.ColorMod;
import utils.EsquemaDegradadoColor;
import utils.ImagenMod;
import utils.Pixel;
import utils.RectanguloDegradadoColor;
import utils.EsquemaDegradadoColor.ModoDegradado;

public class Arbol implements PlanoRealDibujable, ImagenModPintable {
	
	// Atributos
	private ArrayList<Rama> arbol;	
	private TiposArbol tipoArbol;
	
	private Punto pMinimo;
	private Punto pMaximo;
	
	private Rama tronco;
	
	private EsquemaDegradadoColor esq = null;
	
	// Constantes
	private static final Color COLOR_TRONCO_GRADIENTE = Color.BLACK;
	
	// Enumaración de tipos de árbol
	public enum TiposArbol {
		RAMAS_OPACAS,
		RAMAS_TRANSLUCIDAS,
		RAMAS_ESQUEMA_DEGRADADO_LONGITUD_RAMA,
		RAMAS_ESQUEMA_DEGRADADO_GROSOR_RAMA,
		RAMAS_VACIADAS
	}
	
	// Constructores
	public Arbol(Rama tronco, TiposArbol tipo) {
		
		this.arbol = new ArrayList<Rama>(5000);
		this.tipoArbol = tipo;
		
		this.pMinimo = new Punto(9999999, 9999999);
		this.pMaximo = new Punto(-9999999, -9999999);
		
		this.agnadirRama(tronco);
		
		this.tronco = tronco;
		
		int pos = 0;
		while (pos < this.arbol.size()) {
			Rama[] ramas = this.arbol.get(pos).dividirRama();
			if (ramas != null) {
				this.agnadirRama(ramas[0]);
				this.agnadirRama(ramas[1]);
			}
			pos++;
		}
	}
	
	private void agnadirRama(Rama r) {
		this.arbol.add(r);
		
		Punto[] limites = r.getLimites();
		
		this.pMinimo = Punto.min(limites[0], this.pMinimo);
		this.pMaximo = Punto.max(limites[1], this.pMaximo);
				
	}
	
	public Punto[] getLimites(double porcentajeAmpliacion) {
		// El porcentaje de ampliacion si es el 10% el valor es 0.10
		double xLong = this.pMaximo.getX() - this.pMinimo.getX();
		double yLong = this.pMaximo.getY() - this.pMinimo.getY();
		
		xLong = xLong * porcentajeAmpliacion / 2;
		yLong = yLong * porcentajeAmpliacion / 2;
		Punto ampliacion = new Punto(xLong, yLong);
		
		Punto[] ret = new Punto[2];
		ret[1] = new Punto(this.pMaximo);
		ret[1].sumar(ampliacion);
		
		ampliacion.multiplicarPorEscalar(-1.0);
		ret[0] = new Punto(this.pMinimo);
		ret[0].sumar(ampliacion);
		
		return ret;		
	}
	

	@Override
	public String toString() {
		return "Numero de ramas: " + this.arbol.size() + "\nMin: " + this.pMinimo + "\nMax: " + this.pMaximo;
	}
	
	public void dibujar(LienzoEnPlanoReal lienzo, Graphics g) {
		Iterator<Rama> i = this.arbol.iterator();
		while (i.hasNext()) {
			Rama rama = (Rama)i.next();
			rama.dibujar(lienzo, g);
		}
	}

	public void pintar(ImagenMod img, int xInf, int yInf, int xSup, int ySup, Color c0, int modo) {
		// Pinta el árbol según el "modo" sobre la imagen "img" entre los puntos (xInf,yInf) y (xSup,ySup)  

		int x0, y0, x1, y1, dx, dy;

		x0 = Math.min(xInf, xSup);
		y0 = Math.min(yInf, ySup);

		x1 = Math.max(xInf, xSup);
		y1 = Math.max(yInf, ySup);
		
		dx = x1 - x0;
		dy = y1 - y0;

		// Calcula una ImagenMod auxiliar con el nuevo árbol entre x0,y0,x1,y1 y el Conversor para pintar en ella el árbol
		ImagenMod auxArbol = new ImagenMod(dx, dy);
		Punto[] limites = this.getLimites(0.1);
		ConversorPlanorealPixels conversor = new ConversorPlanorealPixels(limites[0], limites[1], dx, dy);
		
		// Si no existe, calcula un Esquema de gradiente
		if (this.esq == null) {
			this.esq = new EsquemaDegradadoColor(COLOR_TRONCO_GRADIENTE, c0, ModoDegradado.RGB);
		}
		
		// Itera rama a rama, pintándola en auxArbol
		Iterator<Rama> i = this.arbol.iterator();
		while (i.hasNext()) {
			Rama rama = (Rama)i.next();
			
			// Obtiene los límites del cuadrado que delimita a la rama
			limites = rama.getLimites();
			
			// Redondea los límites en el plano real para que coincidan con la
			// posición exacta del píxel. Aumenta en un pixel los límites, para
			// evitar luego valores negativos de pixels
			Pixel pixInfIzq = conversor.convertir(limites[0]);
			pixInfIzq = new Pixel(pixInfIzq.getX()  - 1, pixInfIzq.getY() + 1);
			limites[0] = conversor.convertir(pixInfIzq);
			Pixel auxPixel = conversor.convertir(limites[1]);
			Pixel pixSupDrch = new Pixel(1 + auxPixel.getX(), auxPixel.getY() - 1);
			limites[1] = conversor.convertir(pixSupDrch);
			
			// Calcula los pixeles finales de los nuevos límites en el plano real modificados
			Pixel[] limPixels= new Pixel[2];
			limPixels[0] = new Pixel(Math.min(pixSupDrch.getX(), pixInfIzq.getX()), Math.min(pixSupDrch.getY(), pixInfIzq.getY()));
			limPixels[1] = new Pixel(Math.max(pixSupDrch.getX(), pixInfIzq.getX()), Math.max(pixSupDrch.getY(), pixInfIzq.getY()));
			
			int ancho =1 +  limPixels[1].getX() - limPixels[0].getX();
			int alto = 1 + limPixels[1].getY() - limPixels[0].getY();
			
			// Crea el conversor que se le pasará a la rama como parámetro
			ConversorPlanorealPixels convRama = new ConversorPlanorealPixels (limites[0], limites[1], ancho, alto);
			
			// Crea una imagen del tamaño necesario para que encaje sólo la rama que se pintará
			ImagenMod imgRama = new ImagenMod(ancho, alto);
			
			// Pinta la rama en su imagen
			ColorMod c;
			boolean relleno = true;
			switch (this.tipoArbol) {
			case RAMAS_TRANSLUCIDAS:
				c = new ColorMod(c0.getRed(), c0.getGreen(), c0.getBlue(), 100);
				break;
			case RAMAS_ESQUEMA_DEGRADADO_LONGITUD_RAMA:
				double longitudTronco = tronco.getOrigen().getPuntoOrigen().distanciaA(tronco.getDestino().getPuntoOrigen());
				float porcLongRama = (float)(1.0 - (rama.getDestino().getPuntoOrigen().distanciaA(rama.getOrigen().getPuntoOrigen()) / longitudTronco));
				c = this.esq.getColorModAtPos(porcLongRama);
				break;
			case RAMAS_ESQUEMA_DEGRADADO_GROSOR_RAMA:
				c = new ColorMod(Color.WHITE);
				break;
			case RAMAS_VACIADAS:
				relleno = false;
			case RAMAS_OPACAS:
			default:
				c = new ColorMod(c0.getRed(), c0.getGreen(), c0.getBlue(), 255);
				break;				
			}
			rama.pintar(imgRama, convRama, c, relleno);
			
			// Pinta la imagen de la rama en la imagen del árbol
			//auxArbol.pintaImagen(limPixels[0].getX(), limPixels[0].getY(), imgRama, ImagenMod.MODO_PINTAR_SUPERPONER);
			

			if (this.tipoArbol != TiposArbol.RAMAS_ESQUEMA_DEGRADADO_GROSOR_RAMA) {
				auxArbol.pintaImagen(limPixels[0].getX(), limPixels[0].getY(), imgRama, ImagenMod.MODO_PINTAR_SUPERPONER);
			} else {
				if (this.esq == null) {
					// No hay esquema de degradado, no puede usarse
					auxArbol.pintaImagen(limPixels[0].getX(), limPixels[0].getY(), imgRama, ImagenMod.MODO_PINTAR_RESTAR);
					
				} else {
					// Sí hay esquema de degradado definido

					// Calcula los pixels origen y fin donde aplicar el esquema
					// de degradado
					Pixel px0 = convRama.convertir(rama.getOrigen().getPuntoOrigen());
					Pixel px1 = convRama.convertir(rama.getDestino().getPuntoOrigen());

					// Calcula el grosor de la rama en cada punto para calcular el punto del esquema de degradado
					float grTronco = (float)this.tronco.getOrigen().getModulo();
					float gr0 = (float)rama.getOrigen().getModulo();
					float gr1;
					Rama ramaHija = rama.getRamaHijaMasGruesa();
					if (ramaHija != null) {
						gr1 = (float)ramaHija.getOrigen().getModulo();
					} else {
						gr1 = 0f;
					}

					// Convierte a posiciones (entre 0.0 y 1.0) del esquema de degradado
					float pos0 = 1f - gr0 / grTronco;
					float pos1 = 1f - gr1 / grTronco;

					// Crea nuevo esquema de degradado para la rama
					EsquemaDegradadoColor esqAux = new EsquemaDegradadoColor(
							this.esq.getColorModAtPos(pos0), 
							this.esq.getColorModAtPos(pos1), 
							this.esq.getModoDegradado());

					// Crea una imagen con un rectángulo con el nuevo esquema, para la rama
					RectanguloDegradadoColor rec = new RectanguloDegradadoColor(ancho, alto, px0, px1, esqAux);
					ImagenMod imgDeg = rec.getImagenMod();
					
					// Multiplica la imagen de la rama con el esquema degradado, para tener la imagen final (aplica una máscara)
					imgDeg.pintaImagen(0, 0, imgRama, ImagenMod.MODO_APLICAR_MASCARA);	
					
					// Pinta la imagen de la rama en la imagen del arbol
					auxArbol.pintaImagen(limPixels[0].getX(), limPixels[0].getY(), imgDeg, ImagenMod.MODO_PINTAR_SUPERPONER);
				}
			}			
		}		
		
		// Pinta el árbol en la imagen original
		img.pintaImagen(x0, y0, auxArbol, modo);

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
		
		this.tipoArbol = TiposArbol.RAMAS_ESQUEMA_DEGRADADO_GROSOR_RAMA;
		this.esq = esq;
		
		this.pintar(ret, 0, 0, ancho, alto, Color.BLACK, ImagenMod.MODO_PINTAR_SIN_TRATAMIENTO);		
		
		return ret;
	}
	
	public void setEsquemaDegradadoColor(EsquemaDegradadoColor esq) {
		this.esq = esq;
	}


}
