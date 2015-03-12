package utiles;

import java.awt.Color;

import utils.EsquemaDegradadoColor;
import utils.ImagenMod;

import code.Fractal;
import code.PatronFractal;

public class EjemploFractal {
	
	private PatronFractal patron;
	private int numIteraciones;
	private String nombre;
	private Color colorFondo;
	private Color colorFractal;
	private int modoPintar;
	
	public EjemploFractal(PatronFractal patron) {
		this(patron, 10000, "Fractal", Color.WHITE, Color.BLACK, ImagenMod.MODO_PINTAR_SUPERPONER);
	}
	
	
	public EjemploFractal(PatronFractal patron, int iter, String nombre, Color cFondo, Color cFract, int modo) {
		this.patron = patron;
		this.numIteraciones = iter;
		this.nombre = nombre;
		this.colorFondo = cFondo;
		this.colorFractal = cFract;
		this.modoPintar = modo;
	}
	
	public void setNumIteraciones(int iter) {
		this.numIteraciones = iter;
	}
	
	public Fractal getFractal() {
		System.out.println("Calculando el fractal " + this.nombre);
		Fractal ret = new Fractal(this.patron, this.numIteraciones);
		System.out.println("Terminado el calculo del fractal " + this.nombre + ".");
		return ret;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public ImagenMod getImagenMod(int tamX, int tamY) {
		Fractal f = this.getFractal();
		System.out.println("Dibujando el fractal " + this.nombre + " con una resolución de " + tamX+"x" + tamY + " pixels. Esto puede tardar unos minutos.");
		ImagenMod ret = new ImagenMod(tamX, tamY, this.colorFondo);		
		f.pintar(ret, 0, 0, tamX, tamY, this.colorFractal,this.modoPintar);
		System.out.println("Terminado el dibujo del fractal " + this.nombre + ".");
		return ret;
	}
	
	public ImagenMod getImagenMod(int tamX, int tamY, EsquemaDegradadoColor esq) {
		Fractal f = this.getFractal();
		System.out.println("Dibujando el fractal " + this.nombre + " con una resolución de " + tamX+"x" + tamY + " pixels. Esto puede tardar unos minutos.");
		ImagenMod ret = f.getImagenMod(tamX, tamY, esq);
		System.out.println("Terminado el dibujo del fractal " + this.nombre + ".");
		return ret;
	}
	
	

}
