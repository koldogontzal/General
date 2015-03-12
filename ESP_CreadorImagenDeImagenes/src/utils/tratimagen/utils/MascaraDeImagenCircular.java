package utils.tratimagen.utils;

import utils.tratimagen.utils.FuncionDegradadoSencillo.TipoDegradado;

public class MascaraDeImagenCircular extends MascaraDeImagen {
	
	private int centroX, centroY;
	private int radio;
	private TipoDegradado tipo;
	
	public MascaraDeImagenCircular(int anchoImg, int altoImg, int centroX, int centroY, 
			int radio, int valExterno, int valInterno, TipoDegradado tipo) {
		
		super(anchoImg, altoImg, valExterno, valInterno);

		this.centroX = centroX;
		this.centroY = centroY;
		this.radio = radio;
		this.tipo = tipo;
		
		this.creaMascara();
	}

	@Override
	protected void creaMascara() {
		
		super.funcD = new FuncionDegradadoSencillo(valMax, valMin, radio, tipo);		
		
		for (int x = 0; x < super.ancho; x++) {
			for (int y = 0; y < super.alto; y++) {
				// Calcula la distacia al centro del circulo
				int variacionX = x - this.centroX;
				int variacionY = y - this.centroY;
				float dist = (float)Math.sqrt(variacionX * variacionX + variacionY * variacionY);
				
				super.setMascaraEnPosicion(x, y, (int)super.funcD.getValue(dist));
			}
		}
	}
}
