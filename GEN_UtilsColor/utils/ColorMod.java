 package utils;

import java.awt.Color;
import java.awt.color.ColorSpace;
import java.util.ArrayList;

public class ColorMod extends Color {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4896333597506160744L;
	
	private float max;
	private float min;
	private float[] comp;
	private boolean calculadosMaxMin = false;
	
	public enum CanalColor {
		alfa,
		red,
		green, 
		blue;
		
		public int getMascara() {
			switch (this) {
			case alfa:
				return 0xFF000000;
			case red:
				return 0xFF0000;
			case green:
				return 0xFF00;
			default:
			case blue:
				return 0xFF;
			}
		}
		
		public int getPosicionesDesplazamiento() {
			switch (this) {
			case alfa:
				return 24;
			case red:
				return 16;
			case green:
				return 8;
			default:
			case blue:
				return 0;
			}
		}
		
		public static CanalColor[] getArrayCanales() {
			CanalColor[] ret = {alfa, red, green, blue};
			return ret;
		}
		
		public int getPosicion() {
			switch (this) {
			case alfa:
				return 0;
			case red:
				return 1;
			case green:
				return 2;
			default:
			case blue:
				return 3;
			}
		}
	}
	
	public ColorMod(Color c) {
		this(c.getRGB(), c.getAlpha());
	}

	public ColorMod(int rgb) {
		// Crea un color opaco
		super(rgb);
	}
	
	public ColorMod(int rgba, boolean hasAlfa) {
		super(rgba, hasAlfa);
	}
	
	public ColorMod(int r, int g, int b) {
		super(r, g, b);
	}
	
	public ColorMod(int r, int g, int b, int a) {
		super(r, g, b, a);
	}
	
	public ColorMod(ColorSpace cSpace, float[] components, float alfa) {
		super(cSpace, components, alfa);
	}

	public ColorMod(float r, float g, float b) {
		super(r, g, b);
	}
	
	public ColorMod(float r, float g, float b, float a) {
		super(r, g, b, a);
	}
	
	public ColorMod(int rgb, int alfa) {
		super((rgb & 0x00FFFFFF) | (alfa << 24), true);
	}

	public static ColorMod getColorFromHSV(float hue, float sat, float val) {
		return getColorFromHSV(hue, sat, val, 255);
	}

	public static ColorMod getColorFromHSV(float hue, float sat, float val, int alpha) {
		// Valores: hue[0.0, 360.0], sat[0.0, 100.0], val[0.0, 100.0], alpha[0, 255]
		
		// Hace que  el hue esté siempre entre 0º y 360º 
		hue = hue % 360;
		if (hue < 0f) {
			hue = hue + 360f;
		}
		
		// Limita sat, val y alpha
		sat = Math.max(0f, Math.min(100f, sat));
		val = Math.max(0f, Math.min(100f, val));
		alpha = Math.max(0, Math.min(255, alpha));
		
		// Calcula el color
		float s = sat / 100f;
		float v = val / 100f;
		
		int hMod = (int)(hue / 60f) % 6;
		
		float f = (float)(hue / 60.0) - (int)(hue / 60.0);
		float p = v * (1 - s);
		float q = v * (1 - f * s);
		float t = v * (1 - (1 - f) * s);
		
		int vf = (int)Math.round(255f * v);
		int pf = (int)Math.round(255f * p);
		int qf = (int)Math.round(255f * q);
		int tf = (int)Math.round(255f * t);
		
		switch (hMod) {
		case 0:
			return new ColorMod(vf, tf, pf, alpha);
		case 1:
			return new ColorMod(qf, vf, pf, alpha);
		case 2:
			return new ColorMod(pf, vf, tf, alpha);
		case 3:
			return new ColorMod(pf, qf, vf, alpha);
		case 4:
			return new ColorMod(tf, pf, vf, alpha);
		default:
			return new ColorMod(vf, pf, qf, alpha);
		}
	}
	
	public int getARGB() {
		int r = this.getRed() & 0xFF;
		int g = this.getGreen() & 0xFF;
		int b = this.getBlue() & 0xFF;
		int a = this.getAlpha() & 0xFF;
		int color = ((a << 8 | r) << 8 | g) << 8 | b;
		
		return color;
	}
	
	
	// *******************************
	// Luminancia
	// *******************************
	public int getLuminance() {
		// valor entre 0 y 255
		return (int)Math.round(0.2126 * super.getRed() + 0.7152 * super.getGreen() + 0.0722 * super.getBlue());
	}
	
	
	private void calculaMaxMin() {
		// Aunque se llame varias veces, sólo los calcula una vez, para agilizar los cálculos
		if (!this.calculadosMaxMin) {
			this.comp = super.getComponents(null);
			this.max = 0f;
			this.min = 1f;
			
			for (int i = 0; i < 3; i++) {
				// Busca maximo
				if (max < comp[i]) {
					max = comp[i];
				}
				// Busca minimo
				if (min > comp[i]) {
					min = comp[i];
				}			
			}
			
			this.calculadosMaxMin = true;
		}
	}
	
	// *******************************
	// Valor Saturacion del sitema HSV
	// *******************************
	public float getSaturation() {
		// Valor entre 0.0 y 100.0
		
		this.calculaMaxMin();
		
		if (this.max == 0f) {
			return 0f;
		} else {
			return 100f * (1f - this.min / this.max);
		}		 
	}

	// *******************************
	// Valor Value del sitema HSV
	// *******************************
	public float getValue() {
		// Valor entre 0.0 y 100.0
		
		this.calculaMaxMin();
		
		return 100f * this.max; 
	}
	
	// *******************************
	// Valor Hue del sitema HSV
	// *******************************
	public float getHue() {
		// Valor entre 0.0 y 360.0
		
		this.calculaMaxMin();
		float grad = 0f;
		
		// Calculos
		if (max == min) {
			// No hay color, es un gris
			grad = 0f;
		} else if (comp[0] == max) {
			// rojo maximo
			grad = 60f * (comp[1] - comp[2]) / (max - min);
		} else if (comp[1] == max) {
			// verde maximo
			grad = (60f * (comp[2] - comp[0]) / (max - min)) + 120f;
		} else if (comp[2] == max) {
			// azul maximo
			grad = (60f * (comp[0] - comp[1]) / (max - min)) + 240f;
		}
		
		// Normalizacion
		while (grad < 0f) {
			grad = grad + 360f;
		}
		while (grad > 360f) {
			grad = grad - 360f;
		}
		
		// Resultado
		return grad;
	}
	
	@Override
	public String toString() {
		return super.toString() + ",alfa=" + super.getAlpha();
	}
	
	public static ColorMod superponeColores(Color c0, Color c1) {
		int r0 = c0.getRed();
		int r1 = c1.getRed();
		int g0 = c0.getGreen();
		int g1 = c1.getGreen();
		int b0 = c0.getBlue();
		int b1 = c1.getBlue();
		double a0 = c0.getAlpha() / 255.0;
		double a1 = c1.getAlpha() / 255.0;
		
		int a = (int)Math.round(255 * (a0 + a1 - a0 * a1));
		double factor = Math.pow(a1, a0);
		int r = (int)Math.round(r0 + factor * (r1 - r0));
		int g = (int)Math.round(g0 + factor * (g1 - g0));
		int b = (int)Math.round(b0 + factor * (b1 - b0));
		
		return new ColorMod(r, g, b, a);
	}
	
	
	public static ColorMod ANDColores(Color c0, Color c1) {
		int ci0 = c0.getRGB();
		int ci1 = c1.getRGB();		
		int a0 = c0.getAlpha();
		int a1 = c1.getAlpha();	
		
		Color aux = new Color(ci0 & ci1);
		Color cFinal = getColorProporcionalRGB(c0, aux, a1 / 255.0);	
		//int alpha = a0 + (int)((a0 & a1) - a0) * 
		return new ColorMod(cFinal.getRGB(), a0);
	}
	
	public static ColorMod ORColores(Color c0, Color c1) {
		int ci0 = c0.getRGB();
		int ci1 = c1.getRGB();
		int a0 = c0.getAlpha();
		int a1 = c1.getAlpha();	
		
		Color aux = new Color(ci0 | ci1);
		Color cFinal = getColorProporcionalRGB(c0, aux, a1 / 255.0);	
		
		return new ColorMod(cFinal.getRGB(), a0);
	}
	
	public static ColorMod XORColores(Color c0, Color c1) {
		int ci0 = c0.getRGB();
		int ci1 = c1.getRGB();
		int a0 = c0.getAlpha();
		int a1 = c1.getAlpha();	
		
		Color aux = new Color(ci0 ^ ci1);
		Color cFinal = getColorProporcionalRGB(c0, aux, a1 / 255.0);	
		
		return new ColorMod(cFinal.getRGB(), a0);
	}
	
	public static ColorMod multiplicaColores(Color c0, Color c1) {
		int r0 = c0.getRed();
		int r1 = c1.getRed();
		int g0 = c0.getGreen();
		int g1 = c1.getGreen();
		int b0 = c0.getBlue();
		int b1 = c1.getBlue();		
		int a0 = c0.getAlpha();
		int a1 = c1.getAlpha();
		
		int r = (int)Math.round(r0 * r1 / 255.0);
		int g = (int)Math.round(g0 * g1 / 255.0);
		int b = (int)Math.round(b0 * b1 / 255.0);

		
		Color aux = new Color (r, g, b);
		Color cFinal = getColorProporcionalRGB(c0, aux, a1 / 255.0);	
		
		return new ColorMod(cFinal.getRGB(), a0);		
	}
	
	public static ColorMod sumaColores(Color c0, Color c1) {
		int r0 = c0.getRed();
		int r1 = c1.getRed();
		int g0 = c0.getGreen();
		int g1 = c1.getGreen();
		int b0 = c0.getBlue();
		int b1 = c1.getBlue();
		int a0 = c0.getAlpha();
		int a1 = c1.getAlpha();
		
		int r = Math.min(r0 + r1, 255);
		int g = Math.min(g0 + g1, 255);
		int b = Math.min(b0 + b1, 255);
		
		Color aux = new Color (r, g, b);
		Color cFinal = getColorProporcionalRGB(c0, aux, a1 / 255.0);	
		
		return new ColorMod(cFinal.getRGB(), a0);		
	}
	
	public static ColorMod restaColores(Color c0, Color c1) {
		int r0 = c0.getRed();
		int r1 = c1.getRed();
		int g0 = c0.getGreen();
		int g1 = c1.getGreen();
		int b0 = c0.getBlue();
		int b1 = c1.getBlue();
		int a0 = c0.getAlpha();
		int a1 = c1.getAlpha();
		
		int r = Math.max(r0 - r1, 0);
		int g = Math.max(g0 - g1, 0);
		int b = Math.max(b0 - b1, 0);
		
		Color aux = new Color (r, g, b);
		Color cFinal = getColorProporcionalRGB(c0, aux, a1 / 255.0);	
		
		return new ColorMod(cFinal.getRGB(), a0);			
	}
	
	public static ColorMod multiplicacionEscalar(Color c, double f) {
		f = Math.max(f, 0.0);
		
		int r = Math.min((int)Math.round(c.getRed() * f), 255);
		int g = Math.min((int)Math.round(c.getGreen() * f), 255);
		int b = Math.min((int)Math.round(c.getBlue() * f), 255);
		
		return new ColorMod(r, g, b);
	}
	
	public static ColorMod getColorMedio(ArrayList<Color> array) {
		Color[] ar = new Color[array.size()];
		ar = array.toArray(ar);
		return getColorMedio(ar);
	}
	
	public static ColorMod getColorMedio(Color[] array) {
		long rojo = 0;
		long verde = 0;
		long azul = 0;
		
		int numColores = 0;
		double numColoresNoOpacos = 0.0;
		
		if (array != null) {
			for (Color c : array) {
				if (c != null) {
					if (c.getAlpha() == 255) {
						rojo = rojo + c.getRed();
						verde = verde + c.getGreen();
						azul = azul + c.getBlue();
						numColores++;
					} else {
						double factor = c.getAlpha() / 255.0;
						rojo = rojo + (int)Math.round(c.getRed() * factor);
						verde = verde + (int)Math.round(c.getGreen() * factor);
						azul = azul + (int)Math.round(c.getBlue() * factor);
						numColoresNoOpacos = numColoresNoOpacos + factor;
					}
				}
			}
		}
		
		numColores = numColores + (int)Math.round(numColoresNoOpacos);
		if (numColores != 0) {
			int rf = (int) Math.round(rojo / numColores);
			int vf = (int) Math.round(verde / numColores);
			int af = (int) Math.round(azul / numColores);
			return new ColorMod(rf, vf, af);
		} else {
			return new ColorMod(0, 0, 0);
		}
	}
	
	public static ColorMod getColorProporcionalRGB(Color c0, Color c1, double f) {
		// Acota f
		if (f < 0.0) {
			f = 0.0;
		}
		if (f > 1.0)  {
			f = 1.0;
		}
		
		// Calcula el color final proporcional
		int rojo = (int)Math.round(c0.getRed() + (c1.getRed() - c0.getRed()) * f);
		int verde = (int)Math.round(c0.getGreen() + (c1.getGreen() - c0.getGreen()) * f);
		int azul = (int)Math.round(c0.getBlue() + (c1.getBlue() - c0.getBlue()) * f);
		int alfa = (int)Math.round(c0.getAlpha() + (c1.getAlpha() - c0.getAlpha()) * f);
		
		return new ColorMod(rojo, verde, azul, alfa);		
	}
	
	
	public static ColorMod getColorProporcionalHSV(Color c0, Color c1, float f) {
		//  Calcula el color proporcional, siguiendo el hue sin pasar nunca por la posición hue=0
		
		// Acota f
		if (f < 0f) {
			f = 0f;
		}
		if (f > 1f)  {
			f = 1f;
		}
		
		// Variables auxiliares
		ColorMod cm0 = new ColorMod(c0);
		ColorMod cm1 = new ColorMod(c1);
		
		// Calcula el color final proporcional
		float hue =cm0.getHue() + (cm1.getHue() - cm0.getHue()) * f;
		float sat = cm0.getSaturation() + (cm1.getSaturation() - cm0.getSaturation()) * f;
		float val = cm0.getValue() + (cm1.getValue() - cm0.getValue()) * f;
		int alpha = (int)Math.round(c0.getAlpha() + (c1.getAlpha() - c0.getAlpha()) * f);
		
		return getColorFromHSV(hue, sat, val, alpha);		
	}

	public static ColorMod getColorProporcionalHSV_GiroCorto(Color c0, Color c1, float f) {
		// Calcula el color proporcional, siguiendo el hue según el giro más corto entre c0 y c1
		
		// Variables auxiliares
		ColorMod cm0 = new ColorMod(c0);
		ColorMod cm1 = new ColorMod(c1);
		
		// Calcula el color final proporcional
		float hue0 = cm0.getHue();
		float hue1 = cm1.getHue();
		
		if (hue1 < hue0) {
			hue1 = hue1 + 360f;
		}
		
		// Si hay menos de medio círculo de diferencia -> SAR. En caso contrario, SIAR.
		if ((hue1 - hue0) < 180f) {
			return getColorProporcionalHSV_SIAR(c0, c1, f);
		} else {
			return getColorProporcionalHSV_SAR(c0, c1, f);
		}
	}
	
	public static ColorMod getColorProporcionalHSV_SIAR(Color c0, Color c1, float f) {		
		//  Calcula el color proporcional, siguiendo el hue en el Sentido Inverso a la Agujas del Reloj
		
		// Acota f
		if (f < 0f) {
			f = 0f;
		}
		if (f > 1f)  {
			f = 1f;
		}
		
		// Variables auxiliares
		ColorMod cm0 = new ColorMod(c0);
		ColorMod cm1 = new ColorMod(c1);
		
		// Calcula el color final proporcional
		float hue0 = cm0.getHue();
		float hue1 = cm1.getHue();
		if (hue1 < hue0) {
			hue1 = hue1 + 360f;
		}
		
		float hue = hue0 + (hue1 - hue0) * f;
		float sat = cm0.getSaturation() + (cm1.getSaturation() - cm0.getSaturation()) * f;
		float val = cm0.getValue() + (cm1.getValue() - cm0.getValue()) * f;
		int alpha = (int)Math.round(c0.getAlpha() + (c1.getAlpha() - c0.getAlpha()) * f);
		
		return getColorFromHSV(hue, sat, val, alpha);		
	}

	public static ColorMod getColorProporcionalHSV_SAR(Color c0, Color c1, float f) {
		//  Calcula el color proporcional, siguiendo el hue en el Sentido de las Agujas del Reloj
		
		// Acota f
		if (f < 0f) {
			f = 0f;
		}
		if (f > 1f)  {
			f = 1f;
		}
		
		// Variables auxiliares
		ColorMod cm0 = new ColorMod(c0);
		ColorMod cm1 = new ColorMod(c1);
		
		// Calcula el color final proporcional
		float hue0 = cm0.getHue();
		float hue1 = cm1.getHue();
		if (hue0 < hue1) {
			hue0 = hue0 + 360f;
		}
		
		float hue = hue0 + (hue1 - hue0) * f;
		float sat = cm0.getSaturation() + (cm1.getSaturation() - cm0.getSaturation()) * f;
		float val = cm0.getValue() + (cm1.getValue() - cm0.getValue()) * f;
		int alpha = (int)Math.round(c0.getAlpha() + (c1.getAlpha() - c0.getAlpha()) * f);
		
		return getColorFromHSV(hue, sat, val, alpha);
	}

	public static ColorMod aplicarMascara(ColorMod c0, Color c1) {
		int a0 = c0.getAlpha();
		int a1 = c1.getAlpha();
		
		int a = (int)(a0 * a1 / 255.0);
		return new ColorMod(c0.getRGB(), a);	
	}
	
	public static ColorMod aleatorio() {
		return new ColorMod((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255));
	}
	
	public static ColorMod aleatorioOscuro() {
		ColorMod c = aleatorio();
		int lum = c.getLuminance();
		while (lum > 60) {
			c = aleatorio();
			lum = c.getLuminance();
		}
		return c;
	}
	
	public static ColorMod aleatorioClaro() {
		ColorMod c = aleatorio();
		int lum = c.getLuminance();
		while (lum < 195) {
			c = aleatorio();
			lum = c.getLuminance();
		}
		return c;
	}
	
}
