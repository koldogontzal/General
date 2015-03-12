package principal;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import utils.Punto;
import utils.Recta;

public class Aplicacion {
	
	private BufferedImage imagen;
	private File archivo;
	
	private int centroX;
	private int centroY;
	private int radio;
	
	private int difYenCentroX;
	
	public Aplicacion(String fichero, Punto horizonte1, Punto horizonte2, Punto horizonte3) {
		this.archivo = new File(fichero);
		try {
			this.imagen = ImageIO.read(archivo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.calcularCentroCircunferencia (horizonte1, horizonte2, horizonte3);
		
		this.difYenCentroX = this.difY(this.centroX);
		
		this.start();
		
	}

	private void calcularCentroCircunferencia(Punto horizonte1,
			Punto horizonte2, Punto horizonte3) {

		Recta r1 = Recta.rectaEquidistanteA2Puntos(horizonte1, horizonte2);
		Recta r2 = Recta.rectaEquidistanteA2Puntos(horizonte2, horizonte3);
		
		Punto centro = r1.interseccionConRecta(r2);
		
		this.centroX = centro.getX();
		this.centroY = centro.getY();
		
		this.radio = (int)centro.distanciaAlPunto(horizonte1);
		
	}

	public static void main(String[] args) {
		
		new Aplicacion("D:/Documentos/Mis im�genes/Fotos/Mi C�mara/Nuevas2/2007_11_23, Rabat/Kasbah_1.jpg", 
				new Punto(26, 519), 
				new Punto(1835, 354),
				new Punto(5484, 615));
	}
	
	public void start() {
		
		int[] columnaPixels = new int[this.imagen.getHeight()];
		for (int x = 0; x < this.imagen.getWidth(); x++) {
			int difY = this.alteracionYFinal(x);
			this.imagen.getRGB(x, 0, 1, this.imagen.getHeight(), columnaPixels, 0, 1);
			
			if (difY < 0) {
				this.imagen.setRGB(x, 0, 1, this.imagen.getHeight() + difY, columnaPixels, -difY, 1);
				
			} else if (difY > 0) {
				this.imagen.setRGB(x, difY, 1, this.imagen.getHeight() - difY, columnaPixels, 0, 1);
				
			} else {
				// Es igual a 0 y no hay que hacer nada
			}			
		}
		
		try {
			ImageIO.write(this.imagen, "JPG", new File("Salida.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private int difY(int x) {
		int xCorregida = x - this.centroX;
		
		double angulo = Math.acos((double)xCorregida / (double)this.radio);
		
		int yCorregida = (int)((double)this.radio * Math.sin(angulo));
		
		return this.centroY - yCorregida;
	}
	
	private int alteracionYFinal(int x) {
		return this.difYenCentroX - this.difY(x);
	}
	
	public BufferedImage getImagen() {
		return this.imagen;
	}
}
