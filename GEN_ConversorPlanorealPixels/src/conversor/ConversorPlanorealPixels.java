package conversor;

import com.koldogontzal.geometria2d.Punto;

import utils.Pixel;

public class ConversorPlanorealPixels {
	
	// Tamagno minimo representable
	private Punto puntoMin;
	private Punto puntoMax;
	
	// Tamagno en el plano real
	private double xMax;
	private double xMin;
	private double yMax;
	private double yMin;
	private double xAncho;
	private double yAncho;
	
	// Tamaño equivalente en pixels
	private int pantX;
	private int pantY;
	
	// Factor de multiplicación
	private double facX;
	private double facY;
	
	public ConversorPlanorealPixels(Punto infIzq, Punto supDrch, int pixelsX, int pixelsY) {
		this.asignarLimitesReales(infIzq, supDrch);
		
        this.pantX = pixelsX;
        this.pantY = pixelsY;
        
        this.recalcularValoresConversion();        
	}
	
	public ConversorPlanorealPixels(Punto infIzq, Punto supDrch) {
		this(infIzq, supDrch, 1, 1);
	}
	
	public void CambiarPuntoInfIzq(Punto infIzq) {
		this.asignarLimitesReales(infIzq, this.puntoMax);
		this.recalcularValoresConversion();
	}
	
	public void CambiarPuntoSupDrch(Punto supDrch) {
		this.asignarLimitesReales(this.puntoMin, supDrch);
		this.recalcularValoresConversion();
	}
	
	public void CambiarTamagnoPixels(int x, int y) {
		this.pantX = x;
		this.pantY = y;
		this.recalcularValoresConversion();
	}
	
	public Punto getPuntoInfIzq() {
		return new Punto(this.xMin, this.yMin);
	}
	
	public Punto getPuntoSupDrch() {
		return new Punto(this.xMax, this.yMax);
	}
	
	public int getPixelsX() {
		return this.pantX;
	}
	
	public int getPixelsY() {
		return this.pantY;
	}
	
	private void asignarLimitesReales(Punto p0, Punto p1) {
		double x0 = p0.getX();
		double y0 = p0.getY();
		double x1 = p1.getX();
		double y1 = p1.getY();

		this.puntoMin = new Punto(Math.min(x0, x1), Math.min(y0, y1));
		this.puntoMax = new Punto(Math.max(x0, x1), Math.max(y0, y1));

	}

	private void recalcularValoresConversion() {
		this.xMax = this.puntoMax.getX();
		this.yMax = this.puntoMax.getY();
		this.xMin = this.puntoMin.getX();
		this.yMin = this.puntoMin.getY();
		this.xAncho = this.xMax - this.xMin;
		this.yAncho = this.yMax - this.yMin;

		// Recalcula los limites de la rejilla para que las direcciones X e Y sean isótropas en pixeles
		double pixelsUnidadX = this.pantX / this.xAncho;
		double pixelsUnidadY = this.pantY / this.yAncho;

		if (pixelsUnidadX > pixelsUnidadY) {
			double n = ((this.pantX / pixelsUnidadY) - this.xAncho) / 2;
			this.xMax = this.xMax + n;
			this.xMin = this.xMin - n;
		} else {
			double n = ((this.pantY / pixelsUnidadX) - this.yAncho) / 2;
			this.yMax = this.yMax + n;
			this.yMin = this.yMin - n;
		}

		// Recalcular la nueva anchura y alura
		this.xAncho = this.xMax - this.xMin;
		this.yAncho = this.yMax - this.yMin;

		// Calcula el factor de multiplicación para pasar de numeros reales a pixels
		this.facX = this.pantX / this.xAncho;
		this.facY = this.pantY / this.yAncho;

	}

	public int convX(double x) {
		// Se introduce una coordenada en el eje X real y se devuelve el pixel
		// al que corresponde
		return (int) (this.facX * (x - this.xMin));
	}

	public int convY(double y) {
		// Se introduce una coordenada en el eje Y real y se devuelve el pixel
		// al que corresponde
		return (int) (this.facY * (this.yMax - y));
	}

	public Pixel convertir(Punto p) {
		// Se introduce un punto en el plano real y se devuelve el pixel al que
		// corresponde
		return new Pixel(this.convX(p.getX()), this.convY(p.getY()));
	}

	public Punto convertir(Pixel p) {
		// Devuelve el Punto en el plano real que corresponde a un Pixel dado
		return new Punto(this.xMin + p.getX() / this.facX, this.yMax - p.getY() / this.facY);
	}
	
	public double getFactorConversionX() {
		return this.facX;
	}
	
	@Override
	public String toString() {
		String ret;
		ret = "Dimensiones en el plano real: "
				+ new Punto(this.xMin, this.yMin) + " > "
				+ new Punto(this.xMax, this.yMax)
				+ "   Dimensiones originales: " + this.puntoMin + " > "
				+ this.puntoMax + "   Dimensiones en pixels: "
				+ new Pixel(this.pantX, this.pantY)
				+ "   Factor de conversión eje X: " + this.facX
				+ "   Factor de conversión eje Y: " + this.facY;
		return ret;
	}
}
