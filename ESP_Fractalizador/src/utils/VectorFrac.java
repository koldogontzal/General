package utils;

public class VectorFrac {

	private Punto origen;

	private Punto extremo;

	private double longitud;

	private double angulo; // Medido en radianes

	public VectorFrac(Punto puntoOrigen, double longitud, double angulo) {
		this.origen = puntoOrigen;
		PuntoPolares extremoAbsoluto = new PuntoPolares(longitud, angulo);
		this.extremo = this.origen.sumaDePuntos(extremoAbsoluto);

		this.longitud = longitud;
		this.angulo = angulo;
	}

	public VectorFrac(Punto puntoOrigen, Punto puntoExtremo) {
		this.origen = puntoOrigen;
		this.extremo = puntoExtremo;

		Punto vectorAbsoluto = puntoExtremo.restaDePuntos(puntoOrigen);
		this.longitud = vectorAbsoluto.getPolaresLongitud();
		this.angulo = vectorAbsoluto.getPolaresAngulo();

	}

	public VectorFrac(Punto puntoExtremo) {
		// Crea un vector con origen en (0,0) y extremo en puntoExtremo
		this(new Punto(0, 0), puntoExtremo);
	}

	public VectorFrac(VectorFrac vector) {
		this(new Punto(vector.getPuntoOrigen()), new Punto(vector
				.getPuntoExtremo()));
	}

	public Punto getPuntoOrigen() {
		return this.origen;
	}

	public Punto getPuntoExtremo() {
		return this.extremo;
	}

	public double getLongitud() {
		return this.longitud;
	}

	public double getAngulo() {
		return this.angulo;
	}

	@Override
	public String toString() {
		return this.origen + " -> " + this.getPuntoExtremo() + " l:"
				+ this.getLongitud() + " r(�):"
				+ (180 * (this.getAngulo() / Math.PI));
	}

	public static void main(String[] args) {
		VectorFrac a = new VectorFrac(new Punto(1, -1), 1, 0.75 * Math.PI);
		System.out.println(a);
		System.out.println("Longitud: " + a.getLongitud());
		System.out.println("Angulo (grados): " + a.getAngulo() * 180 / Math.PI);
	}

	public VectorFrac rotacionVector(double rotacion) {
		return new VectorFrac(this.getPuntoOrigen(), this.getLongitud(), this
				.getAngulo()
				+ rotacion);
	}

	public VectorFrac desplazamientoVector(Punto desplazamiento) {
		return new VectorFrac(this.getPuntoOrigen().sumaDePuntos(desplazamiento),
				this.getLongitud(), this.getAngulo());
	}

	public double maxX() {
		double valOri = this.origen.getRectangularesX();
		double valExt = this.extremo.getRectangularesX();
		return (valOri > valExt ? valOri : valExt);
	}

	public double maxY() {
		double valOri = this.origen.getRectangularesY();
		double valExt = this.extremo.getRectangularesY();
		return (valOri > valExt ? valOri : valExt);
	}

	public double minY() {
		double valOri = this.origen.getRectangularesY();
		double valExt = this.extremo.getRectangularesY();
		return (valOri < valExt ? valOri : valExt);
	}

	public double minX() {
		double valOri = this.origen.getRectangularesX();
		double valExt = this.extremo.getRectangularesX();
		return (valOri < valExt ? valOri : valExt);
	}

}
