package utils.tratimagen.filtros;

import utils.EsquemaDegradadoColor;

import com.jhlabs.image.PointFilter;

public class EsquemaDegradadoFilter extends PointFilter {
	
	private EsquemaDegradadoColor esquema;
	
	private int[] mapaColor = new int[256];
	
	
	public EsquemaDegradadoFilter(EsquemaDegradadoColor esquema) {
		this.esquema = esquema;
		
		// Crea el mapa de colo que se usará para la trasformación
		for (int i = 0; i < 256 ; i++) {
			this.mapaColor[i] = this.esquema.getColorModAtPos(i / 255f).getARGB();
		}
		
		super.canFilterIndexColorModel = false;
	}

	@Override
	public int filterRGB(int x, int y, int rgb) {
		// Calcula la luminancia
		int luma = this.calculaLuminanciaRapido(rgb);
		// Devuelve el color usando la luminancia como el índice de la tabla
		return this.mapaColor[luma];
	}
	
	private int calculaLuminanciaRapido(int rgb) {
		int r = (rgb >> 16) & 0xff;
		int g = (rgb >> 8) & 0xff;
		int b = rgb & 0xff;

		int ret = (r * 77 + g * 151 + b * 28) >> 8;	// NTSC luma
		return ret;
	}

}
