package utils.tratimagen.utils.geometria;

public class Circulo extends FiguraCerrada {
	
	private int x;
	private int y;
	
	private int radio;
	private int radioCuad;
	
	public Circulo(int x, int y, int radio) {
		this.x = x;
		this.y = y;
		radio = Math.max(0, radio);
		this.radio = radio;
		this.radioCuad = radio * radio;
	}
	
	public Circulo(Punto p, int radio) {
		this(p.getX(), p.getY(), radio);
	}
	
	public boolean contiene(int x, int y) {
		int difX = x - this.x;
		int difY = y - this.y;
		int distCuad = difX * difX + difY * difY;
		return distCuad <= this.radioCuad; 
	}
	
	
	public int getCentroX() {
		return this.x;
	}
	
	public int getCentroY() {
		return this.y;
	}
	
	public int getRadio() {
		return this.radio;
	}
	
	public Punto[] getPuntosLimiteCajaContenedora() {
		// Devuelte un array con el primer elemento siendo el punto con coordenadas más bajas
		// y el segundo elemento el que las tiene más altas, del cuadrado que contiene al círculo
		Punto[] ret = new Punto[2];
		ret[0] = new Punto(this.x - this.radio, this.y - this.radio);
		ret[1] = new Punto(this.x + this.radio, this.y + this.radio);
		
		return ret;
	}

}
