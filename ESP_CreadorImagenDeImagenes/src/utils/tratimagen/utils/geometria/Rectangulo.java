package utils.tratimagen.utils.geometria;

public class Rectangulo extends FiguraCerrada {
	
	private int x0, y0, x1, y1;	
	private Punto p0, p1;
	
	public Rectangulo(int x0, int y0, int varX, int varY) {
		this(new Punto(x0, y0), new Punto(x0 + varX, y0 + varY));
	}
	
	public Rectangulo(Punto pun0, Punto pun1) {
		this.p0 = Punto.min(pun0, pun1);
		this.p1 = Punto.max(pun1, pun0);
		
		this.x0 = this.p0.getX();
		this.y0 = this.p0.getY();
		this.x1 = this.p1.getX();
		this.y1 = this.p1.getY();		
	}

	@Override
	public boolean contiene(int x, int y) {
		return ((x >= this.x0) && (x <= this.x1) && (y >= this.y0) && (y <= this.y1));
	}

	@Override
	public Punto[] getPuntosLimiteCajaContenedora() {
		Punto[] ret = new Punto[2];
		ret[0] = this.p0;
		ret[1] = this.p1;
		return ret;
	}
}
