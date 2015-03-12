package utils.tratimagen.efectos;

import java.awt.image.BufferedImage;

import utils.ColorMod;
import utils.tratimagen.utils.geometria.Circulo;
import utils.tratimagen.utils.geometria.FiguraCerrada;
import utils.tratimagen.utils.geometria.Punto;
import utils.tratimagen.utils.geometria.Rectangulo;


public class EfectoVideoBajaCalidadDigitalizado extends Efecto {

	public EfectoVideoBajaCalidadDigitalizado(ModulacionEfecto tipoModulacion) {
		super(tipoModulacion);
	}
	
	public EfectoVideoBajaCalidadDigitalizado() {
		super(ModulacionEfecto.aleatorio());
		super.aleatorioCadaAplicacion = true;
	}

	@Override
	public BufferedImage aplicarEfecto(BufferedImage imOriginal) {
		if (imOriginal != null) {
			if (this.aleatorioCadaAplicacion) {
				super.tipoModulacion = ModulacionEfecto.aleatorio();
			}
			
			Punto pOrigen = new Punto(0,0);
			Punto pMaximo = new Punto(imOriginal.getWidth() - 1, imOriginal.getHeight() - 1);
			
			FiguraCerrada[] brochazos = new FiguraCerrada[2000];
			// Define la mitad de brochazos de circulo
			int margenX = (int)(imOriginal.getWidth() * 0.05);
			int margenY = (int)(imOriginal.getHeight() * 0.05);
			int anchoCorregido = imOriginal.getWidth() + 2 * margenX;
			int altoCorregido = imOriginal.getHeight() + 2 * margenY;
			int radioMin = (int)(margenX / 5);
			int radioMax = (int)(imOriginal.getWidth() * 0.03);
			int difRadio = radioMax - radioMin;
			
			int numCirculos = brochazos.length >> 1;
			for (int i = 0; i < numCirculos; i++) {
				int centroX = (int)(Math.random() * anchoCorregido) - margenX;
				int centroY = (int)(Math.random() * altoCorregido) - margenY;
				int radio = radioMin + (int)(Math.random() * difRadio);
				
				brochazos[i] = new Circulo(centroX, centroY, radio);
			}
			// Define la otra mitad de brochazos de rectángulos
			int numRectangulos = brochazos.length;
			for (int i = numCirculos; i < numRectangulos; i++) {
				int x0 = (int)(Math.random() * anchoCorregido) - margenX;
				int y0 = (int)(Math.random() * altoCorregido) - margenY;
				int varX = radioMin + (int)(Math.random() * difRadio) << 2;
				int varY = radioMin + (int)(Math.random() * difRadio) << 1;
				
				brochazos[i] = new Rectangulo(x0, y0, Math.max(varX, varY), Math.min(varX, varY));
			}
			
			// Pinta todos los brochazos
			for (FiguraCerrada fig : brochazos) {
				
				Punto[] limites = fig.getPuntosLimiteCajaContenedora();
				
				Punto[] limitesCorregidos = new Punto[2]; 
				limitesCorregidos[0] = Punto.min(pMaximo, Punto.max(pOrigen, limites[0]));
				limitesCorregidos[1] = Punto.min(pMaximo, Punto.max(pOrigen, limites[1]));
				
				float varHue;
				switch (super.tipoModulacion) {
				case fuerte:
					varHue = (float)(Math.random() * 90.0) - 45f;
					break;
				case suave:
					varHue = (float)(Math.random() * 30.0) - 15f;
					break;
				default:
				case normal:
					varHue = (float)(Math.random() * 60.0) - 30f;
				}
				
				for (int x = limitesCorregidos[0].getX(); x <= limitesCorregidos[1].getX(); x++) {
					for (int y = limitesCorregidos[0].getY(); y <= limitesCorregidos[1].getY(); y++) {
						if (fig.contiene(x, y)) {
							
							int rgb = imOriginal.getRGB(x, y);
							ColorMod col = new ColorMod(rgb);
							float hue = col.getHue() + varHue;
							float val = col.getValue();
							float sat = col.getSaturation();							
							ColorMod intermedio = ColorMod.getColorFromHSV(hue, sat, val);
							int lumIntermedio = intermedio.getLuminance();
							if (lumIntermedio != 0) {
								val = val * col.getLuminance() / (float)lumIntermedio;
							}
							ColorMod cRet = ColorMod.getColorFromHSV(hue, sat, val);
							imOriginal.setRGB(x, y, cRet.getARGB());
//System.out.println(col.getLuminance()/(float)ret.getLuminance());							
						}
					}
				}
				
			}
			
			
		}

		return imOriginal;
	}

	@Override
	public String toString() {
		return "Efecto Vídeo de baja calidad digitalizado. Modulación " + super.tipoModulacion + ".";
	}

}
