package utils.cuadricula.tiles;

import java.io.File;

public class Tile3x3 extends Tile {

	public Tile3x3(File fichero, int ancho, int alto) {
		super(fichero, ancho, alto, TILE_3x3);
	}
	
	public Tile3x3() {
		super(null, 0, 0, TILE_3x3);
	}

	@Override
	public int getEspaciosAncho() {
		return 3;
	}

	@Override
	public int getEspaciosAlto() {
		return 3;
	}

}
