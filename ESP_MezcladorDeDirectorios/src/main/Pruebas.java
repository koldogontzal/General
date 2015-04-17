package main;

import java.io.File;

import mezclador.MezcladorDeDirectorios;

public class Pruebas {
	
	public static void main(String[] args) {

		new MezcladorDeDirectorios(
				new File("C:\\Users\\lcastellano\\Desktop\\Pruebas\\Origen"),
				new File("C:\\Users\\lcastellano\\Desktop\\Pruebas\\Destino"));
	}

}
