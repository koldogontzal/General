package utils.cuadricula.tiles;

import java.io.File;

public class Tile2x3 extends Tile {

	public Tile2x3(File fichero, int ancho, int alto) {
		super(fichero, ancho, alto, TILE_2x3);
	}
	
	public Tile2x3() {
		super(null, 0, 0, TILE_2x3);
	}

	@Override
	public int getEspaciosAncho() {
		return 2;
	}

	@Override
	public int getEspaciosAlto() {
		return 3;
	}

}
