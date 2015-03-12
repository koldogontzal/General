package com.koldogontzal.geometria3d;

public class Direccion3D extends Punto3DCoordenadasEsfericas {
	// Una direcci�n es un vector unitario en la direcci�n marcada entre el eje de coordenadas y otro punto

	public static final Punto3D EJE_X = new Punto3D(1, 0, 0);
	public static final Punto3D EJE_Y = new Punto3D(0, 1, 0);
	public static final Punto3D EJE_Z = new Punto3D(0, 0, 1);
	
	
	public Direccion3D(Punto3D p) {
		super(0, 0, 0);
		if (p.getEsfericasRadio() != 0.0) {
			Punto3D aux = new Punto3DCoordenadasEsfericas(1, p.getEsfericasAnguloPolar(), p.getEsfericasAzimut());
			super.setX(aux.getX());
			super.setY(aux.getY());
			super.setZ(aux.getZ());
		}
	}

}
