package main;

import analz.ArbolCaracteristicas;

public class Analizador {
	
	public static void main(String[] args) {
		
		/*
		 Lee un archivo con extensión CSV
		 Cada registro tiene 4 valores separados por punto y coma (;)
		 Los valores corresponden a:
				· Id
				· IdPadre
				· IdPropiedad
				· Puntuación
		*/
		
		// Archivo a analizar
		ArbolCaracteristicas l = new ArbolCaracteristicas("Examen Correcto 2009 extraordinario.csv");
		
		// Resultado del análisis: lo graba en un archivo de salida
		l.toArchivoCSV("Examen Correcto 2009 extraordinario_ANALIZADO.csv");
		// Resultado del análisis: lo imprime en pantalla
		System.out.println(l);
		
	}

}
