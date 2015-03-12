package utils.tratimagen.filtros;

import utils.tratimagen.utils.MascaraDeImagen;

import com.jhlabs.image.PointFilter;

public class MaskFilter extends PointFilter {
	
	private MascaraDeImagen mascara;
	
	public MaskFilter(MascaraDeImagen mascara) {
		super();
		this.mascara = mascara;
		canFilterIndexColorModel = false;
	}

	@Override
	public int filterRGB(int x, int y, int rgb) {
		int a = rgb & 0xff000000;
		int r = (rgb >> 16) & 0xff;
		int g = (rgb >> 8) & 0xff;
		int b = rgb & 0xff;		
		int valMascara = this.mascara.getMascaraEnPosicion(x, y);
		
		r = this.multiplicacionBytes(r, valMascara);
		g = this.multiplicacionBytes(g, valMascara);
		b = this.multiplicacionBytes(b, valMascara);			
		
		return a | (r << 16) | (g << 8) | b;
	}
	
	private int multiplicacionBytes(int n, int m) {
		return (n * m) >> 8;
	}

}
