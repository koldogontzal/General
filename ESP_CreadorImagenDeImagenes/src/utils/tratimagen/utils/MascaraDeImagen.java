package utils.tratimagen.utils;

import java.awt.image.BufferedImage;

public abstract class MascaraDeImagen {
	
	protected int ancho;
	private int anchoMenosUno;
	protected int alto;
	private int altoMenosUno;
	protected int valMin;
	protected int valMax;
	protected FuncionDegradadoSencillo funcD;
	
	private int[][] mascara; // Organizado por [filas][columnas], es decir [y][x], o [alto][ancho]
	
	
	
	
	public MascaraDeImagen(int ancho, int alto, int valMin, int valMax) {
		this.ancho = Math.max(1, ancho);
		this.alto = Math.max(1, alto);
		this.anchoMenosUno = Math.max(0, ancho - 1);
		this.altoMenosUno = Math.max(0, alto - 1);
		this.mascara = new int[alto][ancho];
		this.valMin = Math.max(0, Math.min(255, valMin));
		this.valMax = Math.max(0, Math.min(255, valMax));
	}
	
	public int getMascaraEnPosicion(int x, int y) {
		x = Math.max(0, Math.min(x, this.anchoMenosUno));
		y = Math.max(0, Math.min(y, this.altoMenosUno));
		
		return this.mascara[y][x];
	}
	
	protected void setMascaraEnPosicion(int x, int y, int valor) {
		x = Math.max(0, Math.min(x, this.anchoMenosUno));
		y = Math.max(0, Math.min(y, this.altoMenosUno));
		
		this.mascara[y][x] = valor;
	}
	
	protected abstract void creaMascara();
	
	public int[][] getMascara() {
		return this.mascara;
	}
	
	public BufferedImage getBufferedImage() {
		BufferedImage ret = new BufferedImage(this.ancho, this.alto, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < this.ancho; x++) {
			for (int y = 0; y < this.alto; y++) {
				int c = this.getMascaraEnPosicion(x, y);
				int argb = ((0xFF00 | c) << 8 | c) << 8 | c;
				ret.setRGB(x, y, argb);
			}
		}		
		return ret;
	}

}
