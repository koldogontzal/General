package com.koldogontzal.geometria3d;

public class Punto3DCoordenadasEsfericas extends Punto3D {

	public Punto3DCoordenadasEsfericas(double radio, double anguloPolar, double zenit) {
		super(radio * Math.sin(zenit) * Math.cos(anguloPolar),
				radio * Math.sin(zenit) * Math.sin(anguloPolar), 
				radio * Math.cos(zenit));		
	}

}
