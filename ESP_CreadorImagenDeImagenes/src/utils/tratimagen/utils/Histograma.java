package utils.tratimagen.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.jhlabs.image.Histogram;

public class Histograma extends Histogram {
	
	public static enum TipoHistograma {linear, logaritmico}
	
	public Histograma(BufferedImage img) {
		super(Histograma.getArrayPixels(img), img.getWidth(), img.getHeight(), 0, img.getWidth());
	}
	
	
	public int getFrecuency(float percentil) {
		// devuelve la frecuencia a la que se alcanza un percentil dado de los pixels de la imagen
		int acumulado = 0;
		int frecuencia = 0;
		int limInferiorMuestras = (int)(super.getNumSamples() * percentil);
		while (acumulado < limInferiorMuestras) {
			acumulado = acumulado + super.getFrequency(frecuencia);
			frecuencia++;
		}
		return frecuencia;
	}
	
	public int getFrecuency(int channel, float percentil) {
		// devulve la frecuencia a la que se alcanza un porcentaje dado de los pixels de la imagen
		// Es decir, si percentil es 5%, devuelve la frecuencia a la que el 5% de los pixels de la imagen
		// está por debajo de dicha fracuencia
		int acumulado = 0;
		int frecuencia = 0;
		int limInferiorMuestras = (int)(super.getNumSamples() * percentil);
		while (acumulado < limInferiorMuestras) {
			acumulado = acumulado + super.getFrequency(channel, frecuencia);
			frecuencia++;
		}
		return frecuencia;
	}

	private static int[] getArrayPixels(BufferedImage img) {
		// convierte la imagen a un array de int
		int ancho = img.getWidth();
		int alto = img.getHeight();
		int[] pixels = new int[ancho * alto];
		int offset = 0;
		for (int x = 0; x < ancho; x++) {
			for (int y = 0; y < alto; y++) {
				pixels[offset] = img.getRGB(x, y);
				offset++;
			}
		}
		return pixels;
	}

	public BufferedImage getHistogramImage(TipoHistograma tipo) {
		// Devuelve una imagen con el Histograma		
		BufferedImage ret = new BufferedImage(256, 256, BufferedImage.TYPE_INT_BGR);
		// Rellena el fondo blanco
		Graphics2D g = ret.createGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, 256, 256);
		//Dibuja una barra para cada frecuencia
		int valorMax = super.getMaxFrequency();
		g.setColor(Color.black);
		for (int frec = 0; frec < 256; frec++) {
			int posY;
			if (tipo == TipoHistograma.linear) { 
				posY = 255 - (int)(255 * super.getFrequency(frec) / valorMax);
			} else {
				int valor = super.getFrequency(frec);
				if (valor == 0) {
					posY = 255;
				} else {
					posY = 255 - (int)(255 * Math.log(valor) / Math.log(valorMax));
				}
			}
			g.drawLine(frec, posY, frec, 255);
		}		
		g.dispose();
		
		return ret;
	}
	
	/*
	public static void main(String[] args) {
		try {
			BufferedImage orig = ImageIO.read(new File("C:\\Documents and Settings\\lcastellano\\Escritorio\\2012_04_28, La Virgen de la Cabeza\\Contraste\\Original.jpg"));
			for (float f = 0; f <= 2.0f; f = f + 0.25f) {
				System.out.println("Brillo " + f);
				ContrastFilter filtro = new ContrastFilter();
				filtro.setBrightness(f);
				BufferedImage img = filtro.filter(orig, null);
				ImageIO.write(img, "PNG", new File("C:\\Documents and Settings\\lcastellano\\Escritorio\\2012_04_28, La Virgen de la Cabeza\\Contraste\\" + f + ".png"));
				Histograma h = new Histograma(img);
				BufferedImage histo = h.getHistogramImage();
				ImageIO.write(histo, "PNG", new File("C:\\Documents and Settings\\lcastellano\\Escritorio\\2012_04_28, La Virgen de la Cabeza\\Contraste\\histo" + f + ".png"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	*/	
}
