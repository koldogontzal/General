package utils.tratimagen.utils.geometria;

public abstract class FiguraCerrada {
	
	public abstract boolean contiene(int x, int y);
	
	public boolean contiene(Punto p) {
		return contiene(p.getX(), p.getY());
	}
	
	public abstract Punto[] getPuntosLimiteCajaContenedora();

}
