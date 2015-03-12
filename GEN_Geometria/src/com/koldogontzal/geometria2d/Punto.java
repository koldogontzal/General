package com.koldogontzal.geometria2d;

public class Punto {
	private double x;
	private double y;
	
	public Punto(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Punto(Punto p) {
		this.x = p.getX();
		this.y = p.getY();
	}

	public double getX() {
		return this.x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return this.y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public double getRadial() {
		return Math.sqrt(this.x * this.x + this.y * this.y);
	}
	
	public double getTheta() {
		return Math.atan2(this.y, this.x);
	}
	
	public void sumar(Punto p) {
		this.setX(this.getX() + p.getX());
		this.setY(this.getY() + p.getY());
	}
	
	public static Punto suma(Punto p1, Punto p2) {
		Punto aux = new Punto(p1);
		aux.sumar(p2);
		return aux;
	}
	
	public void restar(Punto p) {
		this.setX(this.getX() - p.getX());
		this.setY(this.getY() - p.getY());
	}
	
	public static Punto resta(Punto p1, Punto p2) {
		Punto aux = new Punto(p1);
		aux.restar(p2);
		return aux;
	}
	
	public void multiplicarPorEscalar(double l) {
		this.setX(this.getX() * l);
		this.setY(this.getY() * l);
	}
	
	public static Punto multiplicacionEscalar(Punto p1, double escalar) {
		Punto aux = new Punto(p1);
		aux.multiplicarPorEscalar(escalar);
		return aux;
	}
	
	public void girarAngulo(double rotacion) { 
		Punto aux =  new PuntoCoordenadasPolares(this.getRadial(), this.getTheta() + rotacion);
		this.setX(aux.getX());
		this.setY(aux.getY());
	}
	
	public static Punto giraAngulo(Punto p1, double rotacion) {
		Punto aux = new Punto(p1);
		aux.girarAngulo(rotacion);
		return aux;
	}
	
	public static Punto puntoMedio(Punto p1, Punto p2) {
		return Punto.puntoProporcional(p1, p2, 0.5);
	}
	
    public static Punto puntoProporcional(Punto ini, Punto fin, double proporcion) {
    	double xProporcional = ini.getX() + proporcion * (fin.getX() - ini.getX());
    	double yProporcional = ini.getY() + proporcion * (fin.getY() - ini.getY());
    	return new Punto(xProporcional, yProporcional);
    }
	
	public double distanciaA(Punto p2) {
		double difX = this.x - p2.getX();
		double difY = this.y - p2.getY();
		
		return Math.sqrt(difX * difX + difY * difY);
	}

	public static double distanciaEntre(Punto p1, Punto p2) {
		return p1.distanciaA(p2);
	}
	
	public static Punto min(Punto a, Punto b) {
		double xMin = Math.min(a.getX(), b.getX());
		double yMin = Math.min(a.getY(), b.getY());
		return new Punto(xMin, yMin);
	}
	
	public static Punto max(Punto a, Punto b) {
		double xMax = Math.max(a.getX(), b.getX());
		double yMax = Math.max(a.getY(), b.getY());
		return new Punto(xMax, yMax);
	}
	
	@Override
	public String toString() {
		return "[" + this.x + ", " + this.y + "]";
	}

}
