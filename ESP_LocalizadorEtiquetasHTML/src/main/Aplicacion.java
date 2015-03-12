package main;

import localizador.Localizador;

public class Aplicacion {	
	
	public static void main(String[] args) {
		// Especificamos aquí donde queremos tener el fichero con los datos de salida
		// Y esto arranca ya automáticamente con el proceso de análisis
 
/*
		//Ejemplo 1
		new Localizador(				
				"resultados_align_desaconsejado.txt", 
				Localizador.ANALIZAR_FICHERO, 
				"C:/Documents and Settings/99GU4697/Escritorio/prueba.html", 
				Localizador.ATR_ALIGN_DESACONSEJADO);
*/
		
		
		//Ejemplo 2
		new Localizador(
				"resultados_align_desaconsejado2.txt", 
				Localizador.ANALIZAR_TODO_SITE, 
				"", 
				Localizador.TIPO_ATR_ALIGN_DESACONSEJADO);

		
/*		
		//Ejemplo 3
		new Localizador(
				"resultados_localizador.txt", 
				Localizador.ANALIZAR_DIR_WEB, 
				"http://www.seg-social.es/Internet_1/ssNODELINK/27789", 
				Localizador.A_HERF_TITLE_INCORRECTO);	
*/	
	}

}
