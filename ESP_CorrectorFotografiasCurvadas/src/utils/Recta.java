package utils;

public class Recta {
	private Punto punto;
	private double pendiente;
	
	public Recta(Punto p, double pendiente) {
		this.punto = p;
		this.pendiente = pendiente;
	}
	
	public Punto getPunto() {
		return this.punto;
	}

	public Recta(Punto p1, Punto p2) {
		this.punto = p1;
		
		Punto resta = p1.restar(p2);
		this.pendiente = (double)(resta.getY()) / (double)(resta.getX());
	}
	
	public double getPendiente() {
		return this.pendiente;
	}
	
	public static Recta rectaEquidistanteA2Puntos(Punto p1, Punto p2) {
		Punto puntoMedio = Punto.puntoMedioA2Puntos(p1, p2);
		
		Recta aux = new Recta(p1, p2);
		
		double pendiente = Math.tan(Math.atan(aux.getPendiente()) + (Math.PI / 2.0));

		return new Recta(puntoMedio, pendiente);
	}
	
	public Punto interseccionConRecta(Recta r) {
		double valorYEnX0Recta1 = this.valorYenX0();
		double valorYEnX0Recta2 = r.valorYenX0();
		
		double x = (valorYEnX0Recta2 - valorYEnX0Recta1) / (this.getPendiente() - r.getPendiente());
		double y = this.getPendiente() * x + valorYEnX0Recta1;
		
		return new Punto((int)x, (int)y);
	}
	
	public double valorYenX0() {
		return (double)this.punto.getY() - this.pendiente * (double)this.punto.getX();
	}

	@Override
	public String toString() {
		return "Recta que pasa por el punto " + this.punto + " con pendiente " + this.pendiente;
	}
}
