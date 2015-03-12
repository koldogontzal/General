package utils;

public class Punto {
	private int x;
	private int y;
	
	public Punto(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public Punto restar(Punto p) {
		return new Punto (this.x - p.getX(), this.y - p.getY());
	}
	
	public static Punto puntoMedioA2Puntos(Punto p1, Punto p2) {
		return new Punto ((p1.getX() + p2.getX()) / 2 , (p1.getY() + p2.getY()) / 2);
	}
	
	public double distanciaAlPunto(Punto p2) {
		int difX = this.x - p2.getX();
		int difY = this.y - p2.getY();
		
		return Math.sqrt(difX * difX + difY * difY);
	}

	@Override
	public String toString() {
		return "[" + this.x + ", " + this.y + "]";
	}

}
