package main;

import java.io.File;
import java.io.IOException;

import mezclador.MezcladorDeDirectorios;

public class Pruebas {
	
	private static final int CASA 		= 1;
	private static final int CURRO 		= 2;
	
	// Selecciona donde estas ejecutando al fichero para que no se confunda con los directorios
	private static final int LUGAR_DE_EJECUCION = CASA;
	
		
	public static void main(String[] args) {

		try {
			switch (LUGAR_DE_EJECUCION) {
			case CURRO:
				new MezcladorDeDirectorios(
						new File("C:\\Users\\lcastellano\\Desktop\\Pruebas\\Origen"),
						new File("C:\\Users\\lcastellano\\Desktop\\Pruebas\\Destino"));
				break;
			case CASA:
				new MezcladorDeDirectorios(
						new File("C:\\Users\\Koldo\\Desktop\\Pruebas\\Origen"),
						new File("C:\\Users\\Koldo\\Desktop\\Pruebas\\Destino"));
				break;
			}
		} catch (IOException e) {
			System.out.println("IOException");
			e.printStackTrace();
		}
	}

}
