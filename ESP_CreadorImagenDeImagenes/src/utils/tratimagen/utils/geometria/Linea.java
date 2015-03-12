package utils.tratimagen.utils.geometria;

public class Linea {
	
	private int x0;
	private int y0;
	private float pendiente;
	private float pendientePerpendicular;
	private float valorYenX0;

	public Linea(Punto p, float pendiente) {
		this.x0 = p.getX();
		this.y0 = p.getY();

		this.pendiente = pendiente;
		if (pendiente == 0f) {
			this.pendientePerpendicular = Float.POSITIVE_INFINITY;
		} else if (pendiente == Float.POSITIVE_INFINITY) {
			this.pendientePerpendicular = 0f;
		} else {
			this.pendientePerpendicular = -1f / pendiente;
		}
		this.valorYenX0 = this.y0 - this.pendiente * this.x0;
	}

	public Linea(Punto p0, Punto p1) {
		this(p0.getX(), p0.getY(), p1.getX(), p1.getY());
	}
	
	public Linea(int x0, int y0, int x1, int y1) {
		this.x0 = x0;
		this.y0 = y0;

		if (x1 == x0) {
			this.pendiente = Float.POSITIVE_INFINITY;
		} else {
			this.pendiente = ((y1 - y0) / (float) (x1 - x0));
		}

		if (this.pendiente == 0f) {
			this.pendientePerpendicular = Float.POSITIVE_INFINITY;
		} else if (this.pendiente == Float.POSITIVE_INFINITY) {
			this.pendientePerpendicular = 0f;
		} else {
			this.pendientePerpendicular = -1f / this.pendiente;
		}
		this.valorYenX0 = this.y0 - this.pendiente * this.x0;
	}

	public float getPendiente() {
		return this.pendiente;
	}

	public float distanceTo(int x, int y) {
		return this.distaceTo(new Punto(x, y));
	}
	

	public float distaceTo(Punto p) {
		Linea linAux = new Linea(p, this.pendientePerpendicular);
		Punto punAux = this.PuntoInterseccionCon(linAux);

		return punAux.distanceTo(p);
	}

	private Punto PuntoInterseccionCon(Linea l) {
		float valorYEnX0Recta1 = this.valorYenX0;
		float valorYEnX0Recta2 = l.valorYenX0;

		float x = (valorYEnX0Recta2 - valorYEnX0Recta1)
				/ (this.getPendiente() - l.getPendiente());
		float y = this.getPendiente() * x + valorYEnX0Recta1;

		return new Punto((int) x, (int) y);
	}

	@Override
	public String toString() {
		return "Línea en (" + this.x0 + ", " + this.y0 + ") con pendiente "
				+ this.pendiente;
	}

}
