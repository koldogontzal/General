package utils;

import com.koldogontzal.geometria2d.Punto;

public class Pixel {
	
	private int x;
	private int y;
	
	public Pixel(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Pixel(Pixel p) {
		this.x = p.getX();
		this.y = p.getY();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public double distanciaA(Pixel p) {
		int dx = this.getX() - p.getX();
		int dy = this.getY() - p.getY();
		return Math.sqrt(dx * dx + dy * dy);
	}
	
	public double anguloCon(Pixel p) {
		Punto puntoAux = new Punto(p.getX() - this.x, p.getY() - this.y);
		return (-puntoAux.getTheta());		
	}
	
	public void restar(Pixel r) {
		this.x = this.x - r.getX();
		this.y = this.y - r.getY();
	}
	
	public static Pixel calcularPixelIntermedio(Pixel p0, Pixel p1, double factor) {
		double posX = (1 - factor) * p0.getX() + factor * p1.getX();
		double posY = (1 - factor) * p0.getY() + factor * p1.getY();
		
		return new Pixel((int)posX, (int) posY);
	}
	
	public static Pixel getPixelMin(Pixel[] lista) {
		int xMin = Integer.MAX_VALUE;
		int yMin = Integer.MAX_VALUE;
		// Recorre la lista de Pixel para encontrar los valores mínimos de X y de Y
		for (Pixel p:lista) {
			xMin = Math.min(xMin, p.getX());
			yMin = Math.min(yMin, p.getY());
		}
		
		return new Pixel(xMin, yMin);
	}

	public static Pixel getPixelMax(Pixel[] lista) {
		int xMax = Integer.MIN_VALUE;
		int yMax = Integer.MIN_VALUE;
		// Recorre la lista de Pixel para encontrar los valores máximos de X y de Y
		for (Pixel p:lista) {
			xMax = Math.max(xMax, p.getX());
			yMax = Math.max(yMax, p.getY());
		}
		
		return new Pixel(xMax, yMax);
	}
	
	@Override
	public String toString() {
		return "(" + this.x + "p, " + this.y + "p)";
	}

}
