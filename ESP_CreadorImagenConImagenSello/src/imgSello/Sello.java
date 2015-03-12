package imgSello;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import utils.ColorMod;
import utils.ImagenMod;

public class Sello implements ImageObserver {
	
	private BufferedImage original;
	private int ancho;
	private int alto;
	private double lumRelTotal;
	
	public Sello(String fichero) {
		try {
			BufferedImage aux = ImageIO.read(new File(fichero));			
			this.ancho = aux.getWidth();
			this.alto = aux.getHeight();
			// Asi aseguro que this.original tiene canal alfa independientemente del tipo de fichero de entrada
			this.original = new BufferedImage(this.ancho, this.alto, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = this.original.createGraphics();
			g.drawImage(aux, 0, 0, this);
			
			this.lumRelTotal = this.calcularLumRelTotal();
			
			
		} catch (IOException e) {
			System.out.println(fichero + ": ERROR. Archivo no valido");
			e.printStackTrace();
		}
	}
	
	public WritableRaster getAlphaRaster() {
		return this.original.getAlphaRaster();
	}

	public double getLuminanceRelTotal() {
		// Devuelve [0.0, 1.0]
		return this.lumRelTotal;
	}
	
	private double calcularLumRelTotal() {
		// Devuelve [0.0, 1.0]
		
		// inicializa variables
		ArrayList<Color> listado = new ArrayList<Color>(this.ancho * this.alto / 2);
		
		// Recorre el lienzo
		WritableRaster wr = this.original.getAlphaRaster();
		for (int x = 0; x < this.ancho; x++) {
			for (int y = 0; y < this.alto; y++) {
				int alfa = wr.getSample(x, y, 0);
				if (alfa != 0) {
					Color colPunt = new Color(this.original.getRGB(x, y));
					listado.add(colPunt);
				}
			}
		}
		
		// Color medio
		ColorMod c = ColorMod.getColorMedio(listado);
		
		return c.getLuminance() / 255.0;
		
	}

	public ImagenMod getSello(Color cOscuro, Color cClaro, int ancho, double angulo) {
		double escala = 1.0 * ancho / this.ancho;		
		int tam = (int)(Math.sqrt(this.ancho * this.ancho + this.alto * this.alto) * 2.0 * escala) + 2;
		
		// Calcula la transformada necesaria
		AffineTransform xform = new AffineTransform();
		xform.translate(tam / 2.0, tam / 2.0);
		xform.scale(escala, escala);
		xform.rotate(angulo);
		
		// Primer lienzo sobredimensionado
		BufferedImage lienzo = new BufferedImage(tam, tam, BufferedImage.TYPE_INT_ARGB);				
		Graphics2D g = lienzo.createGraphics();
		g.drawImage(this.original, xform, this);
		
		// Calcula el tamagno del lienzo final, elimiando las zonas con alfa = 0;
		int xMin = tam;
		int yMin = tam;
		int xMax = 0;
		int yMax = 0;
		long puntosTotales = tam * tam;
		long posGeneral = 0;
		boolean encontrado1 = false;
		boolean encontrado2 = false;
		boolean encontrado3 = false;
		boolean encontrado4 = false;
		WritableRaster wr = lienzo.getAlphaRaster(); 
		while ((posGeneral < puntosTotales) && !(encontrado1 & encontrado2 & encontrado3 & encontrado4)) {
			int rapida = (int) (posGeneral % tam);
			int lenta = (int) (posGeneral / tam);
			// Buscar xMin
			if (!encontrado1) {
				if (wr.getSample(lenta, rapida, 0) != 0) {
					encontrado1 = true;
					xMin = lenta;
					//System.out.println("1: " + posGeneral + " " + lenta + " " +rapida);
				}
			}
			// Buscar yMin
			if (!encontrado2) {
				if (wr.getSample(rapida, lenta, 0) != 0) {
					encontrado2 = true;
					yMin = lenta;
					//System.out.println("2: " + posGeneral + " " + rapida + " " +lenta);
				}
			}
			// Buscar xMax
			if (!encontrado3) {
				if (wr.getSample(tam - 1 -lenta, rapida, 0) != 0) {
					encontrado3 = true;
					xMax = tam - 1 -lenta;
					//System.out.println("3: " + posGeneral + " " + (tam - 1 - lenta) + " " +rapida);
				}
			}
			// Buscar yMax
			if (!encontrado4) {
				if (wr.getSample(rapida, tam - 1 - lenta, 0) != 0) {
					encontrado4 = true;
					yMax = tam - 1 - lenta;
					//System.out.println("4: " + posGeneral + " " + rapida + " " +(tam - 1 - lenta));
				}
			}			
			posGeneral++;
		}

		// Reduce el lienzo a los valores encontrados
		BufferedImage lienzoFinal = lienzo.getSubimage(xMin, yMin, xMax - xMin, yMax - yMin);
		
		// Colorea el lienzo
		wr = lienzoFinal.getAlphaRaster();
		for (int x = 0; x < lienzoFinal.getWidth(); x++) {
			for (int y = 0; y < lienzoFinal.getHeight(); y++) {
				int alfa = wr.getSample(x, y, 0);
				if (alfa != 0) {
					ColorMod colPunt = new ColorMod(lienzoFinal.getRGB(x,y));
					double lumRel = colPunt.getLuminance() / 255.0;
					Color colFinal = ColorMod.getColorProporcionalRGB(cOscuro, cClaro, lumRel);
					
					lienzoFinal.setRGB(x, y, colFinal.getRGB());
				}				
			}
		}
		
		return new ImagenMod(lienzoFinal);
	}

	public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		//  Auto-generated method stub
		return false;
	}

	

}
