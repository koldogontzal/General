package utils.cuadricula.tiles;

import java.io.File;

public class Tile3x1 extends Tile {

	public Tile3x1(File fichero, int ancho, int alto) {
		super(fichero, ancho, alto, TILE_3x1);
	}
	
	public Tile3x1() {
		super(null, 0, 0, TILE_3x1);
	}

	@Override
	public int getEspaciosAncho() {
		return 3;
	}

	@Override
	public int getEspaciosAlto() {
		return 1;
	}

}
