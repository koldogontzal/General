package utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;

import com.koldogontzal.geometria2d.Punto;


public class RectanguloDegradadoColor implements ImageObserver {
	
	private int ancho, alto;
	private Pixel p0, p1;
	private  EsquemaDegradadoColor esqDeg;
	
	public RectanguloDegradadoColor(int ancho, int alto, Pixel p0, Pixel p1, EsquemaDegradadoColor deg) {
		// Crea un reactángulo de ancho x alto de tamaño
		// con un degradado de color deg
		// que empieza en el pixel p0 y termina en el pixel p1
		this.ancho = ancho;
		this.alto = alto;
		this.p0 = p0;
		this.p1 = p1;
		this.esqDeg = deg;		
	}
	
	public ImagenMod getImagenMod() {
		int anchoAux, altoAux;
		
		int diagonalImagen = 1 + (int)(new Punto(ancho, alto)).getRadial();
		int distanciaDegradado = (int)(new Punto(p0.getX(), p0.getY()).distanciaA(new Punto(p1.getX(), p1.getY())));
		
		anchoAux = 2 * diagonalImagen + distanciaDegradado;
		altoAux = 2 * diagonalImagen;
		
		// Crea la imagen Aux que contiene el degradado sin girar
		ImagenMod aux = new ImagenMod(anchoAux, altoAux);
		aux.rellenaRectangulo(0, 0, diagonalImagen, altoAux, 
				this.esqDeg.getColorModAtPos(0f), 
				ImagenMod.MODO_PINTAR_SIN_TRATAMIENTO);
		aux.rellenaRectangulo(anchoAux - diagonalImagen, 0, diagonalImagen, altoAux, 
				this.esqDeg.getColorModAtPos(1f),
				ImagenMod.MODO_PINTAR_SIN_TRATAMIENTO);
		for (int i = 0; i <= (distanciaDegradado + 1); i++) {
			Color c = this.esqDeg.getColorModAtPos(i / (float)distanciaDegradado);
			aux.dibujaLinea(diagonalImagen + i, 0, diagonalImagen + i, altoAux, c, ImagenMod.MODO_PINTAR_SIN_TRATAMIENTO);
		}
	
		// Calcula la transformada necesaria
		AffineTransform xform = new AffineTransform();
		xform.translate(p0.getX() - diagonalImagen, p0.getY() - diagonalImagen);
		xform.rotate(-this.p0.anguloCon(p1), diagonalImagen, diagonalImagen);
	
		// Calcula la imagen que se devolverá
		ImagenMod ret = new ImagenMod(this.ancho, this.alto);
		Graphics2D g = ret.createGraphics();
g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(aux, xform, this);
		
		return ret;
	}

	@Override
	public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		// Auto-generated method stub
		return false;
	}

}
