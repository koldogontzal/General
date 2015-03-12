package utils.cuadricula.tiles;

import java.io.File;

public class Tile2x2 extends Tile {

	public Tile2x2(File fichero, int ancho, int alto) {
		super(fichero, ancho, alto, TILE_2x2);
	}
	
	public Tile2x2() {
		super(null, 0, 0, TILE_2x2);
	}

	@Override
	public int getEspaciosAncho() {
		return 2;
	}

	@Override
	public int getEspaciosAlto() {
		return 2;
	}
	
	
	


}
