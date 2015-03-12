package utils;

public class PuntoPolares extends Punto {

	public PuntoPolares() {
		super(0, 0);
	}

	public PuntoPolares(double longitud, double angulo) {
		super(longitud * Math.cos(angulo), longitud * Math.sin(angulo));

	}

	public PuntoPolares(Punto p) {
		super(p);
	}

}
