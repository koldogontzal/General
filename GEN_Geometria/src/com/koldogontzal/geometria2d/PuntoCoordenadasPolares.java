package com.koldogontzal.geometria2d;

public class PuntoCoordenadasPolares extends Punto {

	public PuntoCoordenadasPolares(double radial, double theta) {
		super(radial * Math.cos(theta), radial * Math.sin(theta));
	}

}
