package utils.cuadricula.tiles;

import java.io.File;

public class Tile3x2 extends Tile {

	public Tile3x2(File fichero, int ancho, int alto) {
		super(fichero, ancho, alto, TILE_3x2);
	}
	
	public Tile3x2() {
		super(null, 0, 0, TILE_3x2);
	}

	@Override
	public int getEspaciosAncho() {
		return 3;
	}

	@Override
	public int getEspaciosAlto() {
		return 2;
	}

}
