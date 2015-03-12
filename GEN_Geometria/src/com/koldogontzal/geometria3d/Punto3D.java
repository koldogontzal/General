package com.koldogontzal.geometria3d;

import com.koldogontzal.geometria2d.Punto;

public class Punto3D {
	
	private double x;
	private double y;
	private double z;
	
	public Punto3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Punto3D(Punto3D p) {
		this.x = p.getX();
		this.y = p.getY();
		this.z = p.getZ();
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}
	
	public double getCilindricasRadio() {
		// El radio se mide en el plano XY y tiene s�lo valores positivos o cero
		Punto aux = new Punto(this.getX(), this.getY());
		return aux.getRadial();		
	}
	
	public double getCilindricasAngulo() {
		// Angulo en el plano XY y tiene de valores entre -PI y +PI radianes
		Punto aux = new Punto(this.getX(), this.getY());
		return aux.getTheta();
	}
	
	public double getCilindricasAltura() {
		// Altura en el eje Z
		return this.z;
	}
	
	public double getEsfericasRadio() {
		// longitud del punto al centro de coordenadas. ES un valor positivo o cero.
		return Math.sqrt(this.getX() * this.getX() + this.getY() * this.getY() + this.getZ() * this.getZ());
	}
	
	public double getEsfericasAnguloPolar() {
		// Angulo en el plano XY. Varia entre -PI y +PI radianes
		return this.getCilindricasAngulo();
	}
	
	public double getEsfericasAzimut() {
		// Angulo desde el zenit. Varia entre 0 y +PI radianes;
		double radio = this.getEsfericasRadio();
		if (radio == 0.0) {
			return 0.0;
		} else {
			return Math.acos(this.getZ() / this.getEsfericasRadio());
		}
	}
	
	@Override
	public String toString() {
		return "[" + this.getX() + ", " + this.getY() + ", " + this.getZ() + "]";
	}

	
	public void sumar(Punto3D p) {
		this.setX(this.getX() + p.getX());
		this.setY(this.getY() + p.getY());
		this.setZ(this.getZ() + p.getZ());
	}
	
	public void restar(Punto3D p) {
		this.setX(this.getX() - p.getX());
		this.setY(this.getY() - p.getY());
		this.setZ(this.getZ() - p.getZ());
	}
	
	public void multiplicacionEscalar(double l) {
		this.setX(this.getX() * l);
		this.setY(this.getY() * l);
		this.setZ(this.getZ() * l);
	}
	
	public static Punto3D puntoMedioA2Puntos(Punto3D p1, Punto3D p2) {
		return new Punto3D((p1.getX() + p2.getX()) / 2.0 , (p1.getY() + p2.getY()) / 2.0, (p1.getZ() + p2.getZ()) / 2.0);
	}
	
	public double distanciaAlPunto(Punto3D p) {
		Punto3D aux = new Punto3D(this);
		aux.restar(p);
		return aux.getEsfericasRadio();
	}
	
	public static Punto3D puntoMinimo(Punto3D a, Punto3D b) {
		double xMin = Math.min(a.getX(), b.getX());
		double yMin = Math.min(a.getY(), b.getY());
		double zMin = Math.min(a.getZ(), b.getZ());
		
		return new Punto3D(xMin, yMin, zMin);
	}
	
	public static Punto3D puntoMaximo(Punto3D a, Punto3D b) {
		double xMax = Math.max(a.getX(), b.getX());
		double yMax = Math.max(a.getY(), b.getY());
		double zMax = Math.max(a.getZ(), b.getZ());
		
		return new Punto3D(xMax, yMax, zMax);
	}
	
	
	public static void main(String[] args) {
		Punto3D p = new Punto3DCoordenadasEsfericas(2, Math.PI / 4, -Math.PI/2);
		System.out.println(p);
		System.out.println("Cilindricas radio: " + p.getCilindricasRadio());
		System.out.println("Cilindricas angulo: " + 180.0 * p.getCilindricasAngulo() / Math.PI);
		System.out.println("Cil�ndricas altura: " + p.getCilindricasAltura());
		System.out.println("Esfericas radio: " + p.getEsfericasRadio());
		System.out.println("Esfericas angulo polar: " + 180 * p.getEsfericasAnguloPolar() / Math.PI);
		System.out.println("Esfericas azimut: " + 180 * p.getEsfericasAzimut() / Math.PI);		
	}

}
