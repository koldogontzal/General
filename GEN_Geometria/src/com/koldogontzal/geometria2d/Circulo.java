package com.koldogontzal.geometria2d;

public class Circulo {
	private Punto centro;
	private double radio;
	
	public Circulo(Punto centro, double radio) {
		this.centro = centro;
		this.radio = radio;
	}
	
	public Circulo(Circulo c) {
		this.centro = c.getCentro();
		this.radio = c.getRadio();
	}
	
	public Circulo(Punto p1, Punto p2, Punto p3) {
		Recta r1 = Recta.rectaEquidistanteA2Puntos(p1, p2);
		Recta r2 = Recta.rectaEquidistanteA2Puntos(p2, p3);
		
		this.centro = r1.interseccionConRecta(r2);
		
		this.radio = this.centro.distanciaA(p1);
	}

	public Punto getCentro() {
		return centro;
	}

	public double getRadio() {
		return radio;
	}
	
	public Punto getPuntoCircunferencia(double angulo) {
		Punto aux = new PuntoCoordenadasPolares(this.radio, angulo);
		aux.sumar(this.centro);
		return aux;
	}

}
