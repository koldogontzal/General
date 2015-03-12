package utils.cuadricula.tiles;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;

/*
import utils.tratimagen.Efecto.ModulacionEfecto;
import utils.tratimagen.EfectoAutoCorreccionContraste;
import utils.tratimagen.EfectoAutoCorreccionContraste.TiposCorreccion;
import utils.tratimagen.EfectoBlancoYNegro;
*/
import utils.tratimagen.efectos.Efecto;
import utils.tratimagen.efectos.SecuenciaDeEfectos;

import com.mortennobel.imagescaling.ResampleOp;

public abstract class Tile implements Comparable<Tile> {

	private static final int NUM_TIPOS_TILES = 9; // Si se añaden más Tiles a la
							// lista, hay que modificar también la funcion
							// Tile.getListadoTiposTile(),  también modificar la 
							// funcion static Tile.crearTile(,,,) y crear su clase
							// correspondiente Tilenxm, que tiene que heredar de Tile
							// y crear también una nueva constante TILE_nxm
	
	public static final int TILE_1x1 =	0b000000000001; // Sencillo
	public static final int TILE_2x2 =	0b000000001000; // Doble
	public static final int TILE_2x1 = 	0b000000000100; // Doble horizontal
	public static final int TILE_1x2 = 	0b000000000010; // Doble vertical
	public static final int TILE_3x3 =	0b000010000000; // Triple
	public static final int TILE_3x2 = 	0b000001000000; // Triple horizontal
	public static final int TILE_2x3 =	0b000000100000; // Triple vertical
	public static final int TILE_3x1 =  0b000000010000; // Triple horizontal fino
	public static final int TILE_4x3 =	0b000100000000; // Formato 4/3
	
	private int tipoTile;	
	private File fichero;	
	private int anchoPixels, altoPixels;
	
	private static Tile[] listadoTiposOrdenado = null;
	
	private boolean recorteCentrado = false;
	
	/*
	private ModulacionEfecto modulacionEfectos = ModulacionEfecto.normal;
	private boolean efectoBlancoYnegro = false;
	private boolean efectoAutocorreccionContraste = false;
	*/
	
	protected Tile(File fichero, int ancho, int alto, int tipo) {
		// Genera un Tile del File "fichero", con un tamaño final en pixels de "ancho" x "alto"
		// siendo el Tile del tipo marcado por "tipo"
		this.fichero = fichero;
		this.anchoPixels = ancho;
		this.altoPixels = alto;
		this.tipoTile = tipo;
	}
	
	protected Tile() {
		// El constructor sin argumentos se usará para crear un Tile de cada clase
		// que permita a la cuadrícula iniciar los valores de tipos de Tile posibles en 
		// cada Espacio de la CuadriculaDeEspacios
		// No he sido capaz de crear métodos estáticos de clase que se hereden de Tile
		this(null, 0, 0, 0);
	}
	
	public abstract int getEspaciosAncho(); // da el número de Espacios de ancho que ocupa el Tile	
	public abstract int getEspaciosAlto(); // da el número de Espacios de alto que ocupa el Tile
	
	public static Tile[] getListadoTiposTiles() {
		// Devuelve un Array con todos los tipos de Tiles posibles
		// Ordenado segun el tipo de Tile, del más alto al más bajo
		
		if (Tile.listadoTiposOrdenado == null) {
			// Aún no se ha iniciado la variable que contiene la lista ordenada,
			// con lo que hay que calcularla (es la primera vez que se ejecuta
			// esta función)
			ArrayList<Tile> lista = new ArrayList<Tile>(NUM_TIPOS_TILES);
			lista.add(new Tile1x1());
			lista.add(new Tile1x2());
			lista.add(new Tile2x1());
			lista.add(new Tile2x2());
			lista.add(new Tile3x3());
			lista.add(new Tile3x2());
			lista.add(new Tile2x3());
			lista.add(new Tile3x1()); 
			lista.add(new Tile4x3()); // Los futuros tipos de Tile se añadirían aquí
			
			
			// Ordena de mayor a menor
			Collections.sort(lista); // Primero de menor a mayor
			Collections.reverse(lista); // Luego invierte el orden
			
			// devuleve la lista ordenada en forma de un Array
			Tile[] ret = new Tile[NUM_TIPOS_TILES];
			ret = lista.toArray(ret);
			// La asigna a la variable estatica para que no haya que volver a calcularla
			Tile.listadoTiposOrdenado = ret;
			return ret;
		} else {
			// Ya ha sido iniciada, con lo que simplemente se devuelve esa variable
			// (es la segunda vez ó más que se ejecuta esta función)
			return Tile.listadoTiposOrdenado;
		}		
	}
		
	public static int getAnchoTileMaximo() {
		// Devuelve el tamaño de ancho mayor de todos los Tile posibles
		Tile[] tiles = Tile.getListadoTiposTiles();
		int max = 0;
		for (Tile t : tiles) {
			if (t.getEspaciosAncho() > max) {
				max = t.getEspaciosAncho();
			}
		}
		return max;
	}
	
	public static int getAltoTileMaximo() {
		// Devuelve el tamaño de alto mayor de todos los Tile posibles
		Tile[] tiles = Tile.getListadoTiposTiles();
		int max = 0;
		for (Tile t : tiles) {
			if (t.getEspaciosAlto() > max) {
				max = t.getEspaciosAlto();
			}
		}
		return max;
	}
	
	public BufferedImage getBufferedImage(SecuenciaDeEfectos secEff) {
		// Devuelve un BufferedImage del Tile o null si hay un error
		
		try {
			BufferedImage orig = ImageIO.read(this.fichero);
			//
			// Recorta la imagen para que se adecue al aspecto dado por "ancho" y "alto"
			// y la pega a la variable "intermedia"
			//
			int anchoOrig = orig.getWidth();
			int altoOrig = orig.getHeight();		
			double aspectoRatioOrig = (double) anchoOrig/(double) altoOrig;		
			double aspectoRatioModif = (double) anchoPixels / (double) altoPixels;
			int anchoIntermedio, altoIntermedio, posXIntermedia, posYIntermedia;		
			if (aspectoRatioOrig >= aspectoRatioModif) {
				// En este caso, se mantiene el tamaño en Y y se reduce el de X
				altoIntermedio = altoOrig;
				anchoIntermedio = (int)((double)altoOrig * aspectoRatioModif);
				posXIntermedia = anchoOrig - anchoIntermedio;
				if (this.recorteCentrado) {
					posXIntermedia = posXIntermedia >> 1; // Divide entre 2, es decir, lo centra
				} else {
					posXIntermedia = (int)(posXIntermedia * Math.random());
				}
				posYIntermedia = 0;
			} else {
				// En este caso, se mantiene el tamaño en X y se reduce el de Y
				anchoIntermedio = anchoOrig;
				altoIntermedio = (int)((double)anchoOrig / aspectoRatioModif);
				posYIntermedia = altoOrig - altoIntermedio;
				if (this.recorteCentrado) {
					posYIntermedia = posYIntermedia >> 1; // Divide entre 2, es decir, lo centra
				} else {
					posYIntermedia = (int)(posYIntermedia * Math.random());
				}
				posXIntermedia = 0;
			}
			BufferedImage intermedia = orig.getSubimage(posXIntermedia,
					posYIntermedia, anchoIntermedio, altoIntermedio);		
			
			/*
			// Escala la imagen intermedia para que se adecue el tamaño marcado por
			// "ancho". Usa la libreria original de Java, con algunas mejoras
			// (pocas). No hace un escalado bueno, se pierde calidad de imagen
			BufferedImage modificada = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = modificada.createGraphics();
			g.setComposite(AlphaComposite.Src);		 
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);			
			g.drawImage(intermedia, 0, 0, ancho, alto, null);		
			g.dispose();
			*/
			
			// Escala la imagen intermedia usando una librería externa, con un resultado de alta calidad y rápido
			ResampleOp resampleOp = new ResampleOp(anchoPixels, altoPixels);			
			BufferedImage modificada = resampleOp.filter(intermedia, null);
			
			// Aplica la secuencia de efectos en orden
			Efecto efecto = secEff.extraer();
			while (efecto != null) {
				modificada = efecto.aplicarEfecto(modificada);
				System.out.println("\tAplicado " + efecto);
				efecto = secEff.extraer();
				
			}
			
			
			/*
			// Transforma a Blanco y Negro si es necesario
			if (this.efectoBlancoYnegro) {
				modificada = new EfectoBlancoYNegro().aplicarEfecto(modificada);
			}
			if (this.efectoAutocorreccionContraste) {
				TiposCorreccion tipo;
				if (this.efectoBlancoYnegro) {
					// Si es en ByN, es mejor la corrección utilizando tres puntos
					tipo = TiposCorreccion.TresPuntos;
				} else {
					// Si es en color, es mejor la corrección utilizando 2 puntos porque deforma menos los colores
					tipo = TiposCorreccion.DosPuntos;
				}
				modificada = new EfectoAutoCorreccionContraste(this.modulacionEfectos, tipo).aplicarEfecto(modificada);
			}
			*/
			// Devuelve la imagen modificada
			return modificada;
			
			
			
		} catch (IOException e) {
			// Si se produce un error leyendo el archivo imagen, no se devuelve nada
			e.printStackTrace();
			return null;
		}		
		
	}
	
	public int getTipoTile() {
		return this.tipoTile;
	}
	
	@Override
	public int compareTo(Tile o) {
		return this.getEspaciosAlto() * this.getEspaciosAncho() - o.getEspaciosAlto() * o.getEspaciosAncho();
	}
	
	@Override
	public String toString() {
		String ret;
		ret = "Tile " + this.getEspaciosAncho() + "x" + this.getEspaciosAlto() + " (ID " + this.tipoTile + ")";
		if (this.fichero != null) {
			ret = ret + " - File: " + this.fichero.getPath();
		}
		return ret;
	}
	
	public static Tile crearTile(File fichero, int ancho, int alto, int tipo) {
		Tile ret;
		
		switch (tipo) {
		case TILE_3x3:
			ret = new Tile3x3(fichero, ancho, alto);
			break;
		case TILE_3x1:
			ret = new Tile3x1(fichero, ancho, alto);
			break;
		case TILE_3x2:
			ret = new Tile3x2(fichero, ancho, alto);
			break;
		case TILE_2x3:
			ret = new Tile2x3(fichero, ancho, alto);
			break;
		case TILE_2x2:
			ret = new Tile2x2(fichero, ancho, alto);
			break;
		case TILE_2x1:
			ret = new Tile2x1(fichero, ancho, alto);
			break;
		case TILE_1x2:
			ret = new Tile1x2(fichero, ancho, alto);
			break;
		default:
		case TILE_1x1:
			ret = new Tile1x1(fichero, ancho, alto);
			break;			
		}
		
		return ret;
	}

	public void setCentrado() {
		// A la hora de recortar la imagen original para generar el Tile final, centra el recorte
		this.recorteCentrado = true;
	}
	
	public void setDescentrado() {
		// A la hora de recortar la imagen original para generar el Tile final, descentra el recorte aleatoriamente
		this.recorteCentrado = false;
	}
	
/*	
	public void setColorOriginal() {
		// A la hora de recortar la imagen original para generar el Tile final, centra el recorte
		this.efectoBlancoYnegro = false;
	}
	
	public void setEfectoBlancoYNegro() {
		// A la hora de recortar la imagen original para generar el Tile final, descentra el recorte aleatoriamente
		this.efectoBlancoYnegro = true;
	}

	public void setEfectoAutocorreccionContraste() {
		// Auto corrige los colores (estira el histograma para cada canal de la imagen)
		this.efectoAutocorreccionContraste  = true;		
	}
	
	public void setModulacionEfectos(ModulacionEfecto mod) {
		this.modulacionEfectos = mod;		
	}
	
	*/
}
