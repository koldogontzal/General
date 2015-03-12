package com.koldogontzal.geometria3d;

import com.koldogontzal.geometria2d.Punto;
import com.koldogontzal.geometria2d.PuntoCoordenadasPolares;

public class Punto3DCoordenadasCilindricas extends Punto3D {

	public Punto3DCoordenadasCilindricas(double radio, double angulo, double altura) {
		super(0, 0, 0);
		Punto aux = new PuntoCoordenadasPolares(radio, angulo);
		super.setX(aux.getX());
		super.setY(aux.getY());
		super.setZ(altura);
	}
}
