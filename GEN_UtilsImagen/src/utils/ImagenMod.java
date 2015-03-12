package utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ImagenMod extends BufferedImage implements ImageObserver {
	
	public static final int MODO_PINTAR_SUPERPONER = 1001;
	public static final int MODO_PINTAR_MULTIPLICAR = 1002;
	public static final int MODO_PINTAR_SUMAR = 1003;
	public static final int MODO_PINTAR_AND = 1004;
	public static final int MODO_PINTAR_OR = 1005;
	public static final int MODO_PINTAR_XOR = 1006;
	public static final int MODO_PINTAR_RESTAR = 1007;
	public static final int MODO_PINTAR_SIN_TRATAMIENTO = 1008;
	public static final int MODO_APLICAR_MASCARA = 1009;
	
	public ImagenMod(int ancho, int alto) {
		// Crea una imagen con el fondo transparente
		super(ancho, alto, BufferedImage.TYPE_INT_ARGB);		
	}

	public ImagenMod(int ancho, int alto, Color bgColor) {
		// Crea una imagen con el color de fondo especificado
		this(ancho, alto);
		this.rellenaRectangulo(0, 0, ancho, alto, bgColor, MODO_PINTAR_SIN_TRATAMIENTO);
	}
	
	public ImagenMod(BufferedImage im) {
		// Crea una imagen de otra imagen
		this(im.getWidth(), im.getHeight());
		Graphics2D g = super.createGraphics();
		g.drawImage(im, 0, 0 , this);
	}
	
	public static ImagenMod leerFichero(String archivo) throws IOException {
		BufferedImage aux = ImageIO.read(new File(archivo));

		int ancho = aux.getWidth();
		int alto = aux.getHeight();
				
		ImagenMod ret = new ImagenMod(ancho, alto);
		Graphics2D g = ret.createGraphics();
		g.drawImage(aux, 0, 0, ret);
	
		return ret;
	}

	
	public boolean grabarFichero(String archivo) throws IOException {
		File file = new File(archivo);
		String extension = "PNG";
		// Busca automaticamente la extension del archivo de salida 
		String nombre = file.getName();
		int posUltimoPunto = nombre.lastIndexOf('.');
		if ((posUltimoPunto != -1) && (posUltimoPunto + 1 < nombre.length())) {
			extension = nombre.substring(posUltimoPunto + 1, nombre.length()).toUpperCase();
		}
		// genera el archivo
		if (!extension.equals("PNG")) {
			// Para cualquier otro tipo de archivos que no sean PNG, no hay
			// soporte correcto del canal alfa, asi que
			// para que podamos grabar bien el archivo, es necesario que la imagen
			// sea de tipo INT_RGB. Como la original es INT_ARGB, hacemos este cambio
			BufferedImage im = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
			Graphics2D g = im.createGraphics();
			g.drawImage(this, 0, 0, this);
			return ImageIO.write(im, extension, file);
		} else {
			// PNG es el unico formato de archivo que soporta bien el canal alfa
			return ImageIO.write(this, extension, file);
		}
	}

	
	public ColorMod getColorPunto(int x, int y) {
		WritableRaster alfa = super.getAlphaRaster();
		int c = this.getRGB(x, y);
		int a = alfa.getSample(x, y, 0);
		return new ColorMod(c, a);
	}
	
	public ColorMod getColorMedioArea(int x, int y, int ancho, int alto) {
		ArrayList<Color> listado = new ArrayList<Color>(ancho * alto);
		WritableRaster alfa = super.getAlphaRaster();
		
		int maxX = Math.min(x + ancho, super.getWidth());
		int maxY = Math.min(y + alto, super.getHeight());
		for (int i = x; i < maxX; i++) {
			for (int j = y; j < maxY; j++) {
				if (alfa.getSample(i, j, 0) != 0) {
					listado.add(this.getColorPunto(i, j));
				}
			}
		}
	
		return ColorMod.getColorMedio(listado);
	}
	
	public double getPorcentajeSolapamientoDeImagenes(int x, int y, BufferedImage img1) {
		// Se calcula para aquellos puntos en los que el canal alfa es != de cero en ambas imagenes
		// Colocando la segunda de ellas (img1) en la posicion (x, y) de este objeto
		int tam1X = img1.getWidth();
		int tam1Y = img1.getHeight();
		
		long casosCoincidentes = 0;
		long casosTotales = 0;
		
		WritableRaster alfa0 = super.getAlphaRaster(); 
		WritableRaster alfa1 = img1.getAlphaRaster();
		
		int maxX = Math.min(x + tam1X, super.getWidth());
		int maxY = Math.min(y + tam1Y, super.getHeight());
		for (int i = x; i < maxX; i++) {
			for (int j = y; j < maxY; j++) {
				if (alfa1.getSample(i - x, j - y, 0) > 0) {
					casosTotales++;
					if (alfa0.getSample(i, j, 0) > 0) {
						casosCoincidentes++;
					}
				}
			}
		}
		
		return (casosCoincidentes * 1.0 / Math.max(1, casosTotales));
	}

	
	private void setPuntoColor(int x, int y, ColorMod c1) {
		int color = c1.getARGB();
		super.setRGB(x, y, color);
	}
	
	
	public void pintaPuntoColor(int x, int y, Color c1, int modo) {
		ColorMod c0 = this.getColorPunto(x, y);
		
		ColorMod res = null;
		switch (modo) {
		case MODO_PINTAR_SUPERPONER:
			res = ColorMod.superponeColores(c0, c1);
			break;
		case MODO_PINTAR_MULTIPLICAR:
			res = ColorMod.multiplicaColores(c0, c1);
			break;
		case MODO_PINTAR_SUMAR:
			res = ColorMod.sumaColores(c0, c1);
			break;
		case MODO_PINTAR_RESTAR:
			res = ColorMod.restaColores(c0, c1);
			break;
		case MODO_PINTAR_AND:
			res = ColorMod.ANDColores(c0, c1);
			break;
		case MODO_PINTAR_OR:
			res = ColorMod.ORColores(c0, c1);
			break;
		case MODO_PINTAR_XOR:
			res = ColorMod.XORColores(c0, c1);
			break;
		case MODO_APLICAR_MASCARA:
			res = ColorMod.aplicarMascara(c0, c1);
			break;
		case MODO_PINTAR_SIN_TRATAMIENTO:
		default:
			res = new ColorMod(c1);
		}
		
		this.setPuntoColor(x, y, res);	
	}
	
	public void dibujaLinea(int x0, int y0, int x1, int y1, Color c, int modo) {
		int xMin = Math.min(x0, x1);
		int xMax = Math.max(x0, x1);
		int yMin = Math.min(y0, y1);
		int yMax = Math.max(y0, y1);
		
		int ancho = 1 +  xMax - xMin;
		int alto = 1 +  yMax - yMin;
		
		if (modo == ImagenMod.MODO_PINTAR_SIN_TRATAMIENTO) {
			// No hay que hacer ningún ptratamiento del color, con lo que se dibuja directamente
			Graphics2D g = this.createGraphics();
g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(c);
			g.drawLine(x0, y0, x1, y1);
		} else {
			// Sí hay que hacer algún tratamiento del color, se pinta en un Graphics auxiliar
			ImagenMod aux = new ImagenMod(ancho, alto);
			Graphics2D g = aux.createGraphics();
g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(c);
			g.drawLine(x0 - xMin, y0 - yMin, x1 - xMin, y1 - yMin);
			this.pintaImagen(xMin, yMin, aux, modo);
		}
	}
	
	public void dibujaRectangulo(int x, int y, int ancho, int alto, Color c, int modo) {	
		if (modo == ImagenMod.MODO_PINTAR_SIN_TRATAMIENTO) {
			// No hay que hacer ningún ptratamiento del color, con lo que se dibuja directamente
			Graphics2D g = this.createGraphics();
g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);			
			g.setColor(c);
			g.drawRect(x, y, ancho, alto);			
		} else {
			ImagenMod aux = new ImagenMod(ancho, alto);
			Graphics2D g = aux.createGraphics();
g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(c);
			g.drawRect(0, 0, ancho - 1, alto - 1);
			this.pintaImagen(x, y, aux, modo);
		}
	}
	
	public void dibujaOvalo(int x, int y, int ancho, int alto, Color c, int modo) {	
		if (modo == ImagenMod.MODO_PINTAR_SIN_TRATAMIENTO) {
			// No hay que hacer ningún ptratamiento del color, con lo que se dibuja directamente
			Graphics2D g = this.createGraphics();
g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(c);
			g.drawOval(x, y, ancho, alto);					
		} else {
			ImagenMod aux = new ImagenMod(ancho, alto);
			Graphics2D g = aux.createGraphics();
g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(c);
			g.drawOval(0, 0, ancho - 1, alto - 1);
			this.pintaImagen(x, y, aux, modo);
		}
	}
	
	public void rellenaRectangulo(int x, int y, int ancho, int alto, Color c, int modo) {	
		if (modo == ImagenMod.MODO_PINTAR_SIN_TRATAMIENTO) {
			// No hay que hacer ningún ptratamiento del color, con lo que se dibuja directamente
			Graphics2D g = this.createGraphics();
g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(c);
			g.fillRect(x, y, ancho, alto);				
		} else {
			int maxX = Math.min(x + ancho, super.getWidth());
			int maxY = Math.min(y + alto, super.getHeight());

			for (int i = x; i < maxX; i++) {
				for (int j = y; j < maxY; j++) {
					this.pintaPuntoColor(i, j, c, modo);
				}
			}
		}
	}
	
	public void rellenaOvalo(int x, int y, int ancho, int alto, Color c, int modo) {
		if (modo == ImagenMod.MODO_PINTAR_SIN_TRATAMIENTO) {
			// No hay que hacer ningún ptratamiento del color, con lo que se dibuja directamente
			Graphics2D g = this.createGraphics();
g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(c);
			g.drawOval(x, y, ancho, alto);					
		} else {
			ImagenMod aux = new ImagenMod(ancho, alto);
			Graphics2D g = aux.createGraphics();
g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(c);
			g.fillOval(0, 0, ancho, alto);
			this.pintaImagen(x, y, aux, modo);
		}
	}
	
	
	public void pintaImagen(int x, int y, ImagenMod img, int modo) {
		int tam0X = super.getWidth();
		int tam0Y = super.getHeight();
		int tam1X = img.getWidth();
		int tam1Y = img.getHeight();
		
		
		if (modo == MODO_APLICAR_MASCARA) {
			// En este modo, un alpha=0 sí implica algo
			int maxX = Math.min(x + tam1X, tam0X);
			int maxY = Math.min(y + tam1Y, tam0Y);
			for (int i = x; i < maxX; i++) {
				for (int j = y; j < maxY; j++) {
					Color c1 = img.getColorPunto(i - x, j - y);
					this.pintaPuntoColor(i, j, c1, MODO_APLICAR_MASCARA);
				}
			}			
		} else if (modo == MODO_PINTAR_SIN_TRATAMIENTO) {
			// en este modo, se pinta la imagen tal cual
			Graphics2D g = this.createGraphics();
g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.drawImage(img, x, y, this);
		} else {
			// En estos modos, un alpha=0 implica no hacer nada
			WritableRaster alfa1 = img.getAlphaRaster();
			int maxX = Math.min(x + tam1X, tam0X);
			int maxY = Math.min(y + tam1Y, tam0Y);
			for (int i = x; i < maxX; i++) {
				for (int j = y; j < maxY; j++) {
					if (alfa1.getSample(i - x, j - y, 0) != 0) {
						Color c1 = img.getColorPunto(i - x, j - y);
						this.pintaPuntoColor(i, j, c1, modo);
					}
				}
			}
		}
		
/*	
		if (modo != MODO_APLICAR_MASCARA) {
			// En estos modos, un alpha=0 implica no hacer nada
			WritableRaster alfa1 = img.getAlphaRaster();
			int maxX = Math.min(x + tam1X, tam0X);
			int maxY = Math.min(y + tam1Y, tam0Y);
			for (int i = x; i < maxX; i++) {
				for (int j = y; j < maxY; j++) {
					if (alfa1.getSample(i - x, j - y, 0) != 0) {
						Color c1 = img.getColorPunto(i - x, j - y);
						this.pintaPuntoColor(i, j, c1, modo);
					}
				}
			}
		} else {
			// En este modo, un alpha=0 sí implica algo
			int maxX = Math.min(x + tam1X, tam0X);
			int maxY = Math.min(y + tam1Y, tam0Y);
			for (int i = x; i < maxX; i++) {
				for (int j = y; j < maxY; j++) {
					Color c1 = img.getColorPunto(i - x, j - y);
					this.pintaPuntoColor(i, j, c1, MODO_APLICAR_MASCARA);
				}
			}
		}
*/
		
	}
	
	public void rellenaDegradadoColor(int x, int y, int ancho, int alto, Pixel pxIniRelativo, Pixel pxFinRelativo, EsquemaDegradadoColor esq, int modo) {
		// Pinta el degradado de color definido por el esquema deg, en un recuadro de posición superior izquierda (x, y) de tamaño
		// (anch, alto), teniendo en cuenta que el degradado se empieza aplica en el pixel pxIniRelativo y termina en el pixel pxFinRelativo
		// siendo ambos pixels relativos al punto (x, y) y no al inicio de la imagen (0, 0).
		
		RectanguloDegradadoColor rec = new RectanguloDegradadoColor(ancho, alto, pxIniRelativo, pxFinRelativo, esq);
		ImagenMod img = rec.getImagenMod();
		this.pintaImagen(x, y, img, modo);
		
	}
	
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		//  Apedice de método generado automaticamente
		return false;
	}
	
	@Override
	public String toString() {
		String ret;
		ret = "Tamagno: " + this.getWidth() + ", " + this.getHeight() + "\n";
		ret = ret + "Color Medio: " + this.getColorMedioArea(0, 0, this.getWidth(), this.getHeight()) + "\n";
		ret = ret + "30 puntos aleatorios:\n";
		for (int i = 0; i < 30; i++) {
			int x = (int)(this.getWidth() * Math.random());
			int y = (int)(this.getHeight() * Math.random());
			ret = ret + "(" + x + ", " + y + ") - " + this.getColorPunto(x, y) + "\n";
		}
		return ret;
	}

	public void rellenaArco(int centX, int centY, int diamX, int diamY, double ang0, double arco, Color c, int modo) {
		this.dibujaArcoPrivado(centX, centY, diamX, diamY, ang0, arco, c, modo, true);		
	}	
	    
    public void dibujaArco(int centX, int centY, int diamX, int diamY, double ang0, double arco, Color c, int modo) {
    	this.dibujaArcoPrivado(centX, centY, diamX, diamY, ang0, arco, c, modo,  false);
    }
    
    private void dibujaArcoPrivado(int centX, int centY, int diamX, int diamY, double ang0, double arco, Color c, int modo, boolean relleno) {    	
    	ImagenMod aux = new ImagenMod(diamX + 2, diamY + 2);
		Graphics2D g = aux.createGraphics();
g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(c);
		
		int intAng0 = (int)Math.round(ang0 * 180.0 / Math.PI);
    	int intArco = (int)Math.round(arco * 180.0 / Math.PI);
    	
    	g.drawArc(0, 0, diamX , diamY, intAng0, intArco);
    	if (relleno) {
    		g.fillArc(0, 0, diamX , diamY, intAng0, intArco);
    	}
    	aux.cambiarColorImagen(new ColorMod(c));
		this.pintaImagen(centX - (diamX / 2), centY - (diamY / 2), aux, modo);
    	
    	
    }

    public void dibujaTrapecio(Pixel i0, Pixel f0, Pixel i1, Pixel f1, Color c, int modo) {
    	// Calcula la lista de Pixels que forma el trapecio
		Pixel[] lista = new Pixel[4];
		lista[0] = i0;
		lista[1] = f0;
		lista[2] = f1;
		lista[3] = i1;
		// Dibuja el trapecio
		this.dibujaPoligono(lista, c, modo);
    }

	public void rellenaTrapecio(Pixel i0, Pixel f0, Pixel i1, Pixel f1, Color c, int modo) {		
		// Calcula la lista de Pixels que forma el trapecio
		Pixel[] lista = new Pixel[4];
		lista[0] = i0;
		lista[1] = f0;
		lista[2] = f1;
		lista[3] = i1;
		// Dibuja el trapecio
		this.rellenaPoligono(lista, c, modo);
		
	}
	
	public void dibujaPoligono(Pixel[] lista, Color c, int modo) {
		Polygon poligono = new Polygon();
		for (Pixel pix:lista) {
			poligono.addPoint(pix.getX(), pix.getY());
		}
		this.dibujaPoligonoPrivado(poligono, c, modo, false);
	}
	
	public void rellenaPoligono(Pixel[] lista, Color c, int modo) {
		Polygon poligono = new Polygon();
		for (Pixel pix:lista) {
			poligono.addPoint(pix.getX(), pix.getY());
		}
		this.dibujaPoligonoPrivado(poligono, c, modo, true);
	}
	
	public void dibujaPoligono(Polygon p, Color c, int modo) {
		this.dibujaPoligonoPrivado(p, c, modo, false);
	}

	public void rellenaPoligono(Polygon p, Color c, int modo) {
		this.dibujaPoligonoPrivado(p, c, modo, true);
	}

	private void dibujaPoligonoPrivado(Polygon p, Color c, int modo, boolean relleno) {
		// Calcula los Límites del recuadro que enmarca al polígono
		Pixel min = new Pixel(p.getBounds().x, p.getBounds().y);
		int ancho = 1 + p.getBounds().width;
		int alto = 1 + p.getBounds().height;
		
		// Crea una imagen auxiliar reducida con el tamaña necesario para contener sólo el trapecio
		ImagenMod aux = new ImagenMod(ancho, alto);
		Graphics2D g = aux.createGraphics();
g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(c);
		
		// Crea una copia desplazada del polígono
		Polygon pDesp = new Polygon(p.xpoints, p.ypoints, p.npoints);
		pDesp.translate(-min.getX(), -min.getY());
		
		// Dibuja el polígono en la imagen auxiliar
		g.drawPolygon(pDesp);
		if (relleno) {
			g.fillPolygon(pDesp);
		}
		
		// Pinta la imagen auxiliar en la imagen completa
		aux.cambiarColorImagen(new ColorMod(c));
    	this.pintaImagen(min.getX(), min.getY(), aux, modo);
	}
	
    private void cambiarColorImagen(ColorMod c) {
    	// cambia todos los puntos de la imagen distintos de (x,x,x,0) por el color que marque c
    	WritableRaster alfa = this.getAlphaRaster();
    	
    	for (int x = 0; x < this.getWidth(); x++) {
    		for (int y = 0; y < this.getHeight(); y++) {
    			if (alfa.getSample(x, y, 0) != 0) {
    				this.setPuntoColor(x, y, c);
    			}    			
    		}
    	}
    }
}
