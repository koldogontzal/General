package utils.tratimagen.efectos;

import java.awt.image.BufferedImage;

import com.jhlabs.image.SaturationFilter;

import utils.ColorMod;
import utils.tratimagen.utils.geometria.Circulo;
import utils.tratimagen.utils.geometria.FiguraCerrada;
import utils.tratimagen.utils.geometria.Punto;
import utils.tratimagen.utils.geometria.Rectangulo;


public class EfectoBrochazosColor extends Efecto {

	public EfectoBrochazosColor(ModulacionEfecto tipoModulacion) {
		super(tipoModulacion);
	}
	
	public EfectoBrochazosColor() {
		super(ModulacionEfecto.aleatorio());
		super.aleatorioCadaAplicacion = true;
	}

	@Override
	public BufferedImage aplicarEfecto(BufferedImage imOriginal) {
		BufferedImage ret = null;
		
		if (imOriginal != null) {
			if (this.aleatorioCadaAplicacion) {
				super.tipoModulacion = ModulacionEfecto.aleatorio();
			}
			
			new BufferedImage(imOriginal.getWidth(), imOriginal.getHeight(), BufferedImage.TYPE_INT_ARGB);
			SaturationFilter filtro = new SaturationFilter(0.2f);
			ret = filtro.filter(imOriginal, null);
			
			/*
			 * Calcula una array con los brochazos
			 */
			Punto pOrigen = new Punto(0,0);
			Punto pMaximo = new Punto(imOriginal.getWidth() - 1, imOriginal.getHeight() - 1);
			
			FiguraCerrada[] brochazos = new FiguraCerrada[700];
			// Define la mitad de brochazos de circulo
			int margenX = (int)(imOriginal.getWidth() * 0.05);
			int margenY = (int)(imOriginal.getHeight() * 0.05);
			int anchoCorregido = imOriginal.getWidth() + 2 * margenX;
			int altoCorregido = imOriginal.getHeight() + 2 * margenY;
			int radioMin = (int)(margenX / 5);
			int radioMax = (int)(imOriginal.getWidth() * 0.03);
			int difRadio = radioMax - radioMin;
			
			
			for (int i = 0; i < brochazos.length; i++) {
				int tipoBrochazo = (int)(Math.random() * 1.5);
				
				switch (tipoBrochazo) {
				default:
				case 0:
					// Circulo
					int centroX = (int)(Math.random() * anchoCorregido) - margenX;
					int centroY = (int)(Math.random() * altoCorregido) - margenY;
					int radio = radioMin + (int)(Math.random() * difRadio);
					
					brochazos[i] = new Circulo(centroX, centroY, radio);
					break;
				case 1:
					// Rectángulo
					int x0 = (int)(Math.random() * anchoCorregido) - margenX;
					int y0 = (int)(Math.random() * altoCorregido) - margenY;
					int varX = radioMin + (int)(Math.random() * difRadio) << 2;
					int varY = radioMin + (int)(Math.random() * difRadio) << 1;
					
					brochazos[i] = new Rectangulo(x0, y0, Math.max(varX, varY), Math.min(varX, varY));
					
				}
				
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
					varHue = (float)(Math.random() * 136.0) - 68f;
					break;
				case suave:
					varHue = (float)(Math.random() * 30.0) - 15f;
					break;
				default:
				case normal:
					varHue = (float)(Math.random() * 90.0) - 45f;
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
							ret.setRGB(x, y, cRet.getARGB());						
						}
					}
				}
				
			}
			
			
		}

		return ret;
	}

	@Override
	public String toString() {
		return "Efecto Brochazos de color. Modulación " + super.tipoModulacion + ".";
	}

}
