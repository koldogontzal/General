package utils.cuadricula.tiles;

import java.io.File;

public class Tile1x1 extends Tile {
	
	public Tile1x1(File fichero, int ancho, int alto) {
		super(fichero, ancho, alto, TILE_1x1);
	}
	
	public Tile1x1() {
		super(null, 0, 0, TILE_1x1);
	}

	@Override
	public int getEspaciosAncho() {
		return 1;
	}

	@Override
	public int getEspaciosAlto() {
		return 1;
	}

}
