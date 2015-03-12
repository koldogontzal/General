package utils.tratimagen.utils;

import utils.tratimagen.utils.geometria.Linea;
import utils.tratimagen.utils.FuncionDegradadoSencillo.TipoDegradado;

public class MascaraDeImagenLinear extends MascaraDeImagen {
	
	private Linea linea;
	private int distancia;
	private TipoDegradado tipo;
	
	
	public MascaraDeImagenLinear(int anchoImg, int altoImg, int x0, int y0, 
			int x1, int y1, int dist, int valMin, int valMax, TipoDegradado tipo) {
		
		super(anchoImg, altoImg, valMin, valMax);

		this.linea = new Linea(x0, y0, x1, y1);
		this.distancia = dist;
		this.tipo = tipo;
		
		this.creaMascara();
	}

	@Override
	protected void creaMascara() {
		
		super.funcD = new FuncionDegradadoSencillo(valMax, valMin, distancia, tipo);		
		
		for (int x = 0; x < super.ancho; x++) {
			for (int y = 0; y < super.alto; y++) {
				// Calcula la distacia a la linea
				float dist = this.linea.distanceTo(x, y);
				
				super.setMascaraEnPosicion(x, y, (int)super.funcD.getValue(dist));
			}
		}
	}
}
