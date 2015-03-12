package utils.cuadricula.tiles;

import java.io.File;

public class Tile1x2 extends Tile {
	
	public Tile1x2(File fichero, int ancho, int alto) {
		super(fichero, ancho, alto, TILE_1x2);
	}
	
	public Tile1x2() {
		super(null, 0, 0, TILE_1x2);
	}

	@Override
	public int getEspaciosAncho() {
		return 1;
	}

	@Override
	public int getEspaciosAlto() {
		return 2;
	}
	
	
	


}
