package interfaz;

import java.awt.Color;

import utils.EsquemaDegradadoColor;
import utils.ImagenMod;

public interface ImagenModPintable {

	// Método para pintar un objeto dado en una imagen determinada img, dentro del recuadro marcado por los pixels (xInf, yInf) y (xSup, ySup) segun el modo de pintar.
	public abstract void pintar(ImagenMod img, int xInf, int yInf, int xSup, int ySup, Color c,  int modo);
		
	// Método que devuelve una imagen de un objeto dado. El fondo será transparente.
	public ImagenMod getImagenMod(int ancho, int alto, Color c);
	
	// Método que devuelve una imagen del objeto dado usando el esquema de degradado de color dado
	public ImagenMod getImagenMod(int ancho, int alto, EsquemaDegradadoColor esq);
	
}
