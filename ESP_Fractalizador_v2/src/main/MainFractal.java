package main;

import java.awt.Color;
import java.io.IOException;

import utiles.EjemploFractal;
import utiles.ListadoEjemplosFractales;
import utils.EsquemaDegradadoColor;
import utils.EsquemaDegradadoColor.ModoDegradado;
import utils.ImagenMod;

public class MainFractal {

	public static void main(String[] args) {
		// Utiliza la clase predefinida ListadoEjemplosFractales donde he ido guardando fractales curiosos
		// No es necesario usar esta lista. Es posible crear los fractales a mano. Para ver el proceso, ver como se crean los ejemplos en la lista
		
		ListadoEjemplosFractales listado = new ListadoEjemplosFractales();
		
		// Lee uno de los ejemplos
		EjemploFractal ejemplo = listado.get(7);
		
		// Crea un esquema de degradado
		EsquemaDegradadoColor esq = new EsquemaDegradadoColor(
				new Color(255, 10, 0, 200), 
				new Color(250, 0, 50, 10),
				ModoDegradado.HSV_DIRECTO);
		esq.añadirColor(0.7f, new Color(250, 0, 100, 190));
		esq.añadirColor(0.9f, new Color(250, 10, 0, 140));
		esq.añadirColor(0.97f, new Color(250, 0, 10, 100));
		esq.añadirColor(0.997f, new Color(250, 10, 0, 50));
		
		
		// Crea la imagen correspondiente
		//ImagenMod img = ejemplo.getImagenMod(200, 200, esq); // Imagen pequeña (más rápida de calcular)
		ImagenMod img = ejemplo.getImagenMod(3200, 2400, esq); // Imagen grande (más lenta de calcular)
		
		// Graba un archivo con la imagen
		try {
			img.grabarFichero(ejemplo.getNombre() + "_deg_col.png");
		} catch (IOException e) {
			System.out.println("Error grabando el archivo");
			e.printStackTrace();
		}
		System.out.println("Terminado todo el proceso");
			
	}
}
