package utils.cuadricula.tiles;

import java.io.File;

public class Tile4x3 extends Tile {

	public Tile4x3(File fichero, int ancho, int alto) {
		super(fichero, ancho, alto, TILE_4x3);
	}
	
	public Tile4x3() {
		super(null, 0, 0, TILE_4x3);
	}

	@Override
	public int getEspaciosAncho() {
		return 4;
	}

	@Override
	public int getEspaciosAlto() {
		return 3;
	}

}
