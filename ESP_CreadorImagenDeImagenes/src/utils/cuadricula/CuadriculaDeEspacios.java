package utils.cuadricula;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import utils.cuadricula.tiles.ListadoNumeroOcurrenciasDeTiposTile;
import utils.cuadricula.tiles.Tile;
import utils.ficheros.DispensadorImagenes;
import utils.ficheros.NoImagesException;
/*
import utils.tratimagen.Efecto.ModulacionEfecto;
import utils.tratimagen.EfectoHighlightColor;
*/
import utils.tratimagen.efectos.SecuenciaDeEfectos;

public class CuadriculaDeEspacios {
	
	public static final int TILES_CENTRADOS	= 0b00000000;
	public static final int TILES_DESCENTRADOS = 0b00000001;
	
	/*
	private static final int POS_TILE_CENTRADO =		0b00000001;
	
	public static final int IMG_COLOR = 				0b00000000;
	public static final int IMG_ByN =					0b00000010;
	private static final int POS_IMG_COLOR =			0b00000010;
	public static final int EFF_AUTOCORRECT_CONTRAST =	0b00000100;
	public static final int EFF_SUAVE =					0b00001000;
	public static final int EFF_NORMAL = 				0b00000000;
	public static final int EFF_FUERTE =				0b00010000;
	private static final int POS_EFF_FORTALEZA =		0b00011000;
	public static final int EFF_HIGHLIGHT_COLOR = 		0b00100000;
	private static final Color COLOR_EFF_HIGHLIGHTCOLOR = Color.green; // Color que se resaltará de la imagen final
	*/
	
	private Espacio[][] cuadricula;
	
	private int anchoEspacio;
	private double aspectoEspacio;
	private int marcoPixels;
	
	private int numFil; // Número de filas de la cuadrícula
	private int numCol; // Número de columnas de la cuadrícula
	
	private DispensadorImagenes listadoFiles; // Dispensador de imágenes del directorio dado
	
	private ListadoNumeroOcurrenciasDeTiposTile listadoNumOcurr;
	
	private BufferedImage imagenFinal;
	
	private boolean tileCentrado;
	private SecuenciaDeEfectos secEff;
	
	/*
	private ModulacionEfecto modulacionEfecto = ModulacionEfecto.normal;
	private boolean EfectoBlancoYnegro;
	private boolean EfectoAutocorreccionContraste;
	private boolean EfectoHighlightColor;
	*/
	
	public CuadriculaDeEspacios(int numFilas, int numCol, String dir) throws NoImagesException {
		this(numFilas, numCol, dir, 200, 1.0, 10);
	}
	
	public CuadriculaDeEspacios(int numFilas, int numCol, String dir,
			int anchoTilePixels, double aspectoTile, int borde) throws NoImagesException {
		this(numFilas, numCol, new File(dir), null, anchoTilePixels, aspectoTile, borde, Color.WHITE, "Salida.png",
				TILES_DESCENTRADOS, null);
	}
	
	public CuadriculaDeEspacios(int numFilas, int numCol, String dir,
			ListadoNumeroOcurrenciasDeTiposTile listado, int anchoTilePixels,
			double aspectoTile, int borde, Color fondo, String fileSalida,
			int flagsTileCentrado, SecuenciaDeEfectos secEff) throws NoImagesException {
		this(numFilas, numCol, new File(dir), listado, anchoTilePixels, aspectoTile, borde, fondo, fileSalida, flagsTileCentrado, secEff);
	}
	
	public CuadriculaDeEspacios(int numFilas, int numCol, File dir,
			ListadoNumeroOcurrenciasDeTiposTile listado, int anchoTilePixels,
			double aspectoTile, int borde, Color fondo, String fileSalida,
			int flags, SecuenciaDeEfectos secEff) throws NoImagesException {
		this.numFil = numFilas;
		this.numCol = numCol;
		this.listadoFiles = new DispensadorImagenes(dir);
		this.listadoNumOcurr = listado;
		this.anchoEspacio = anchoTilePixels;
		this.aspectoEspacio = aspectoTile;
		this.marcoPixels = borde;
		
		this.tileCentrado = (flags == TILES_CENTRADOS);
		this.secEff = secEff;
		
		/*
		this.EfectoBlancoYnegro = ((flags & POS_IMG_COLOR) == IMG_ByN);
		this.EfectoAutocorreccionContraste = ((flags & EFF_AUTOCORRECT_CONTRAST) != 0);
		this.EfectoHighlightColor = ((flags & EFF_HIGHLIGHT_COLOR) != 0);
		
		int flagsModulacionEfectos = flags & POS_EFF_FORTALEZA;
		switch (flagsModulacionEfectos) {
		case EFF_FUERTE:
			this.modulacionEfecto = ModulacionEfecto.fuerte;
			break;
		case EFF_SUAVE:
			this.modulacionEfecto = ModulacionEfecto.suave;
			break;
		default:
			this.modulacionEfecto = ModulacionEfecto.normal;
		}
		*/

		this.cuadricula = new Espacio[this.numFil][this.numCol];
		// Inicializa los Espacios
		this.incilizacionEspacios();
		

		// Asigna Tiles a los Espacios
		this.asignaTiles();
		
		
		// Crea la imagen final
		this.creaLienzoImagenFinal(fondo);		
		this.agnadeImagenesPequegnasAlLienzo();
				
		// Graba el resultado
		this.grabaResultado(fileSalida);
		
	}

	private void incilizacionEspacios() {
		// Primero crea los Espacios
		for (int i = 0; i < this.numFil; i++) {
			for (int j = 0; j < this.numCol; j++) {
				this.cuadricula[i][j] = new Espacio(i, j);
			}
		}
		
		// Según cada tipo de Tile, habilita cada Espacio si puede contenerlo o no
		Tile[] tiposTiles = Tile.getListadoTiposTiles();
		for (Tile t : tiposTiles) {
			int tipo = t.getTipoTile();
			for (int i = 0; i <= this.numFil - t.getEspaciosAlto(); i++) {
				// Recorre todas las filas desde la 0 hasta la más baja que
				// pueda contener un Tipo de Tile dado. Esto dependerá del
				// tamaño (alto) del tipo de Tile
				for (int j = 0; j <= this.numCol - t.getEspaciosAncho(); j++) {
					// Recorre todas las columnas desde la 0 hasta la más a la derecha que
					// pueda contener un Tipo de Tile dado. Esto dependerá del
					// tamaño (ancho) del tipo de Tile
					this.cuadricula[i][j].modifEstado(tipo);					
				}
			}
		}			
	}
	

	private void asignaTiles() throws NoImagesException {
		// Recorre la lista de tipos de Tiles y va asociando cada uno de ellos
		for (Tile t : Tile.getListadoTiposTiles()) {
			// Para cada tipo de Tile posible, busca asigna en la cuadricula tantas ocurrencias
			// como marque listadoNomOcurr					
			int tipo = t.getTipoTile();
			int disponibles = this.cuentaTilesDisponibles(tipo);
			int numOcurrencias = 0;
			if (this.listadoNumOcurr != null) {
				numOcurrencias = listadoNumOcurr.getNumOcurrencias(tipo);
			}
			if (tipo == Tile.TILE_1x1) {
				// En este caso, el numero de ocurrencias tiene que ser el
				// máximo para que rellene todos los espacios libres con Tiles sencillos
				numOcurrencias = this.numFil * this. numCol;
			}

			while ((numOcurrencias > 0) && (disponibles > 0)) {
				// busca uno libre aleatoriamente
				int[] posicion = this.buscaUnTileLibre(tipo, disponibles);
				int i = posicion[0]; // Fila
				int j = posicion[1]; // Columna
		
				// Crea el tile necesario
				Tile t_fin = Tile.crearTile(this.listadoFiles.siguiente(),
						this.anchoEspacio * t.getEspaciosAncho() + this.marcoPixels * (t.getEspaciosAncho() - 1), 
						(int)(this.anchoEspacio / this.aspectoEspacio) * t.getEspaciosAlto() + this.marcoPixels * (t.getEspaciosAlto() - 1),
						tipo);
				if (this.tileCentrado) {t_fin.setCentrado();} else {t_fin.setDescentrado();}
				/*
				if (this.EfectoBlancoYnegro) {t_fin.setEfectoBlancoYNegro();} else {t_fin.setColorOriginal();}
				if (this.EfectoAutocorreccionContraste) {t_fin.setEfectoAutocorreccionContraste();}
				t_fin.setModulacionEfectos(this.modulacionEfecto);
				*/
				
				// Asigna el Tile a al estructura de Espacios
				this.cuadricula[i][j].asociaTile(t_fin);

				// Actualiza la estructura de tiles con los nuevos que ya no pueden ser de un tipo dado.
				for (Tile posible : Tile.getListadoTiposTiles()) {
					int tipoPosible = posible.getTipoTile();
					for (int i2 = i - posible.getEspaciosAlto() + 1; 
							i2 < i + t.getEspaciosAlto();
							i2++) {
						for (int j2 = j - posible.getEspaciosAncho() + 1;
								j2 < j + t.getEspaciosAncho();
								j2++) {
							this.modificaUnEspacioQuitandoTipo(i2, j2, tipoPosible);					
						}
					}
				}
			
				// Actualiza las variables y continua el bucle
				numOcurrencias--;
				disponibles = this.cuentaTilesDisponibles(tipo);
			}
		}
	}

	private void modificaUnEspacioQuitandoTipo(int i, int j, int tipo) {
		if ((i >= 0) && (i < this.numFil) && (j >= 0) && (j < this.numCol)) {
			this.cuadricula[i][j].quitaTipoEstado(tipo);
		}
	}

	private int cuentaTilesDisponibles(int tipo) {
		// Cuenta el número de Tiles disponibles de un tipo dado
		int ret = 0;
		for (int i = 0; i < this.numFil; i++) {
			for (int j = 0; j < this.numCol; j++) {
				if (this.cuadricula[i][j].puedeTenerTileTipo(tipo)) {
					ret++;
				}
			}
		}
		return ret;

	}
	
	private int[] buscaUnTileLibre(int tipo, int tilesDisponibles) {
		// devuelve un array de enteros con la fila en la primera posición y la columna en la segunda
		// Se le da el tipo de TIle que se busca y cuántos hay de ese tipo (se ha calculado antes y así
		// se evita repetir cálculos
		int[] ret = new int[2];
		
		int tileAleatorio = (int)(Math.random() * tilesDisponibles);
		
		int fila = 0;
		int columna = 0;
		boolean encontrado = false;
		// Busca un tile libre
		for (int i = 0; (i < this.numFil) && !encontrado; i++) {
			for (int j = 0; (j < this.numCol) && !encontrado; j++) {
				if ((this.cuadricula[i][j].getEstado() & tipo) != 0) {
					// Encontrado uno
					tileAleatorio--;					
				} 
				if (tileAleatorio < 0) {
					encontrado = true;
					fila = i;
					columna = j;
				}
			}
		}
		
		// Devuelve la posición del Tile libre
		ret[0] = fila;
		ret[1] = columna;
		return ret;
	}
	
	private void creaLienzoImagenFinal(Color fondo) {
		int tamagnoY = numFil * (int)(anchoEspacio / aspectoEspacio) + (numFil - 1) * marcoPixels;
		int tamagnoX = numCol * anchoEspacio + (numCol - 1) * marcoPixels;
		
		this.imagenFinal = new BufferedImage(tamagnoX, tamagnoY, BufferedImage.TYPE_INT_BGR);
		
		Graphics2D g = this.imagenFinal.createGraphics();
		g.setColor(fondo);
		g.fillRect(0, 0, tamagnoX, tamagnoY);
		g.dispose();
	}
	
	private void agnadeImagenesPequegnasAlLienzo() {
		// Recorre todos los Espacios de la cuadrícula
		for (int i = 0; i < this.numFil; i++) {
			for (int j = 0; j < this.numCol; j++) {

				System.out.println("Completado " + (i * numCol + j) + "/" + (numFil * numCol) + "...");
				
				Tile t = this.cuadricula[i][j].getTile();
				if (t == null) {
					// No tiene Tile asociado (será un Espacio que forme parte de un Tile grande
					// En este caso no dibuja nada
				} else {
					// En el resto de casos, dibuja el Tile					
					BufferedImage img = t.getBufferedImage(this.secEff.clonar());
					int posX = j * (this.anchoEspacio + this.marcoPixels);
					int posY = i * ((int) (this.anchoEspacio / this.aspectoEspacio) + this.marcoPixels);

					Graphics2D g = this.imagenFinal.createGraphics();
					g.drawImage(img, posX, posY, null);
					g.dispose();					
				}
			}
		}
		System.out.println("Completado el 100%");
		
	}
	
	private void grabaResultado(String file) {
		// siempre graba en formato PNG, si no tiene esa extensión, se la añade
		if ((file.lastIndexOf(".") == -1) || !(file.substring(file.lastIndexOf(".") + 1).toUpperCase().equals("PNG"))) {
			file = file + ".png";
		}
		
		try {
			System.out.println("Grabando el resultado en " + file + "...");
			ImageIO.write(this.imagenFinal, "PNG", new File(file));
			System.out.println("Finalizado.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String toString(int tipo) {
		String ret ="";
		for (int i = 0; i < this.numFil; i++) {
			for (int j = 0; j < this.numCol; j++) {
				ret = ret + ((this.cuadricula[i][j].getEstado() & tipo) != 0 ? "1" : "0");
			}
			ret = ret + "\n";
		}
		return ret;
	}
}
