package utils;

public class Punto {

	private double x;

	private double y;

	public Punto() {
		this.x = 0;
		this.y = 0;
	}

	public Punto(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Punto(Punto p) {
		this.x = p.x;
		this.y = p.y;
	}

	@Override
	public String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}

	public Punto sumaDePuntos(Punto sumando) {
		return new Punto(this.x + sumando.x, this.y + sumando.y);
	}

	public Punto restaDePuntos(Punto restando) {
		return new Punto(this.x - restando.x, this.y - restando.y);
	}

	public double getRectangularesX() {
		return this.x;
	}

	public double getRectangularesY() {
		return this.y;
	}

	public double getPolaresLongitud() { // Devuelve el radio en coordenadas polares
		return Math.sqrt(this.x * this.x + this.y * this.y);
	}

	public double getPolaresAngulo() { // Devuelve el ángulo en radianes en coordenadas polares
		return Math.atan2(this.y, this.x); // Según el manual, el primer parámetro es la Y y luego la X
	}

	public static void main(String[] args) {
		Punto a = new Punto(1, 0);
		System.out.println(a);
		System.out.println(a.getPolaresAngulo());
	}

	public Punto multiplicacionEscalar(double escalado) {
		return new Punto(escalado * this.x, escalado * this.y);
	}

	public Punto girarAngulo(double rotacion) { 
		return new PuntoPolares(this.getPolaresLongitud(), this
				.getPolaresAngulo() + rotacion);
	}

	public void setRectangularesX(double valor) {
		this.x = valor;
	}

	public void setRectangularesY(double valor) {
		this.y = valor;
	}
}
