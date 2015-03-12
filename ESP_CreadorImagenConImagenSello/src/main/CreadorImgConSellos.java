package main;

import imgSello.Sello;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.IOException;

import utils.ColorMod;
import utils.ImagenMod;

public class CreadorImgConSellos implements ImageObserver {
	// Constantes
	public static final Color COLOR_DE_FONDO = new Color(255, 145, 25);

	public static final int NUM_DE_SELLOS = 1500;
	public static final int TAMAGNO_MAXIMO = 150;
	public static final int TAMAGNO_MINIMO = 30;
	public static final double VELOCIDAD_REDUCCION_TAMAGNO = 2.0;
	public static final Color COLOR_SELLO_OSCURO = new Color(50, 5, 15);
			//Efecto boli junto con MULTIPLICACION = new Color(39, 100, 200);
	public static final double FACTOR_CONTRASTE_PARA_SELLOS = 3.0;
	public static final double PORCENTAJE_NECESARIO_CONCIDENCIA = 0.95;
	public static final double PROBABILIDAD_MIN_PARA_PINTAR = 0.6;
	
	public static final int TIPO_PINTURA = ImagenMod.MODO_PINTAR_RESTAR;
	
	
	// Variables
	private ImagenMod original;
	private Sello sello;
	private ImagenMod lienzo;
	
	
	public CreadorImgConSellos(String fichOriginal, String fichSello, String fichSalida) {
		
		try {
			// Lee la imagen original
			this.original = ImagenMod.leerFichero(fichOriginal);
			
			// Crea el lienzo en blanco que sera la salida
			int tamX = this.original.getWidth();
			int tamY = this.original.getHeight();
			this.lienzo = new ImagenMod(tamX, tamY, COLOR_DE_FONDO);
			
			// Lee el sello
			this.sello = new Sello(fichSello);
			
			// Pinta el nuevo lienzo
			this.start(TIPO_PINTURA);

			// Guarda el lienzo en el fichero de salida
			this.lienzo.grabarFichero(fichSalida);			
			
		} catch (IOException e) {
			System.out.println(fichOriginal + ": ERROR. No pudo abirse correctamente.");
			e.printStackTrace();
		}

	}


	private void start(int tipo) {
		for (int i = 0; i < NUM_DE_SELLOS; i++) {
			// Informacion del proceso por pantalla
			System.out.println("Calculando sello " + (i + 1) + "/" + NUM_DE_SELLOS);
			
			// Calcular tamagno del nuevo sello
			int tam = TAMAGNO_MINIMO + (int)((TAMAGNO_MAXIMO - TAMAGNO_MINIMO) * Math.pow((NUM_DE_SELLOS - i) * 1.0 / NUM_DE_SELLOS, VELOCIDAD_REDUCCION_TAMAGNO));
			
			// Busca una posicion donde entre el sello
			boolean encontrada = false;
			ImagenMod imSello = null;
			int x = 0;
			int y = 0;
			while (!encontrada) {
				x = (int)(Math.random() * this.original.getWidth());
				y = (int)(Math.random() * this.original.getHeight());
				double angulo = Math.random() * 2 * Math.PI;
				
				//Calcula los colores del sello
				ColorMod cMedioArea = this.original.getColorMedioArea(x, y, tam, tam);
				System.out.print("\t" + i + ": (" + x +", " + y + ")\tColor medio: " + cMedioArea);
				Color cClaro;
				switch (tipo) {
				case ImagenMod.MODO_PINTAR_MULTIPLICAR:
				case ImagenMod.MODO_PINTAR_AND:
				case ImagenMod.MODO_PINTAR_OR:
					cClaro = Color.WHITE;
					break;
				case ImagenMod.MODO_PINTAR_SUMAR:
				case ImagenMod.MODO_PINTAR_RESTAR:	
					cClaro = ColorMod.multiplicacionEscalar(COLOR_SELLO_OSCURO, FACTOR_CONTRASTE_PARA_SELLOS);
					break;
				case ImagenMod.MODO_PINTAR_SUPERPONER:
				case ImagenMod.MODO_PINTAR_XOR:
				case ImagenMod.MODO_PINTAR_SIN_TRATAMIENTO:
				default:
					cClaro = ColorMod.restaColores(cMedioArea, COLOR_SELLO_OSCURO);
					Color cAux = ColorMod.multiplicacionEscalar(COLOR_SELLO_OSCURO, this.sello.getLuminanceRelTotal());
					cClaro = ColorMod.sumaColores(cClaro, cAux);
					cClaro = ColorMod.multiplicacionEscalar(cClaro, 1.0 / this.sello.getLuminanceRelTotal());
				}
				System.out.println("   Color claro: " + cClaro);
				
				// Obtiene la imagen del sello
				imSello = this.sello.getSello(COLOR_SELLO_OSCURO, cClaro, tam, angulo);
				
				// Comprueba si ha encontrado ya el sitio adecuado o no
				if (this.original.getPorcentajeSolapamientoDeImagenes(x, y, imSello) > PORCENTAJE_NECESARIO_CONCIDENCIA) {
					switch (tipo) {
					case ImagenMod.MODO_PINTAR_MULTIPLICAR:
					case ImagenMod.MODO_PINTAR_RESTAR:
					case ImagenMod.MODO_PINTAR_AND:
						// Busca las zonas oscuras
						double p = Math.random() * (255.0 - cMedioArea.getLuminance()) / 255.0;
						if (p > PROBABILIDAD_MIN_PARA_PINTAR) {
							encontrada = true;
						}
						break;
					case ImagenMod.MODO_PINTAR_SUMAR:
					case ImagenMod.MODO_PINTAR_OR:
						// Busca las zonas claras
						p = Math.random() * cMedioArea.getLuminance() / 255.0;
						if (p > PROBABILIDAD_MIN_PARA_PINTAR) {
							encontrada = true;
						}
						break;
					case ImagenMod.MODO_PINTAR_SUPERPONER:
					case ImagenMod.MODO_PINTAR_XOR:
					case ImagenMod.MODO_PINTAR_SIN_TRATAMIENTO:
					default:
						encontrada = true;						
					}				
				}
			}
			
			// Pinta el sello			
			switch (tipo) {
			case ImagenMod.MODO_PINTAR_MULTIPLICAR:
			case ImagenMod.MODO_PINTAR_RESTAR:
			case ImagenMod.MODO_PINTAR_SUMAR:
			case ImagenMod.MODO_PINTAR_SUPERPONER:
			case ImagenMod.MODO_PINTAR_AND:
			case ImagenMod.MODO_PINTAR_OR:
			case ImagenMod.MODO_PINTAR_XOR:
				this.lienzo.pintaImagen(x, y, imSello, tipo);
				break;
			case ImagenMod.MODO_PINTAR_SIN_TRATAMIENTO:
			default:
				Graphics2D g = this.lienzo.createGraphics();
				g.drawImage(imSello, x, y, this);
			}
		}		
	}

	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		return false;
	}
	
	public static void main(String[] args) {
		new CreadorImgConSellos("lienzo_juntos.gif", "sello.gif", "salida_luis_jose.png");
		
	}
}
