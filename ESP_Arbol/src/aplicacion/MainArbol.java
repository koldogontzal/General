package aplicacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import com.koldogontzal.geometria2d.Punto;
import com.koldogontzal.geometria2d.Vector;

import lienzo.LienzoEnPlanoReal;

import utils.EsquemaDegradadoColor;
import utils.ImagenMod;
import utils.Pixel;
import utils.EsquemaDegradadoColor.ModoDegradado;
import arbol.Arbol;
import arbol.Arbol.TiposArbol;
import arbol.Rama;

public class MainArbol {
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		// Crea el arbol
		Punto origen = new Punto(0, 0);
		Vector destino = new Vector(origen, new Punto(0, 180));		
		Rama tronco = new Rama(destino, 1000);
		
		System.out.println("Calculando árbol");
		Arbol arbol = new Arbol(tronco, TiposArbol.RAMAS_TRANSLUCIDAS);
		Punto[] limites = arbol.getLimites(0.1);
		
		System.out.println("Voy a pintar el Arbol en pantalla");		
		// pinta el arbol en la pantalla
		Frame f = new Frame("Arbol");
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		// Pongo False en los ejes para que no los dibuje
		LienzoEnPlanoReal lienzo = new LienzoEnPlanoReal(arbol, Color.BLACK, limites[0], limites[1], false);
		
		f.add(lienzo, BorderLayout.CENTER);
		f.pack();
		f.show();
		
		System.out.println("Límites del árbol");
		// Imprime datos
		System.out.println(arbol);		
		System.out.println(limites[0] + " " + limites[1]);
		
		// Crea imagen
		ImagenMod img = new ImagenMod(1600, 1200, Color.WHITE);
		

		// Pinta el fondo
	
		System.out.println("Pinta el fondo de la imagen");
		EsquemaDegradadoColor fondo = new EsquemaDegradadoColor(new Color(250, 80,0), new Color(0,100,0), ModoDegradado.RGB);
		fondo.añadirColor(0.749f, Color.YELLOW);
		fondo.añadirColor(0.75f, new Color(80, 150, 0));
		img.rellenaDegradadoColor(0, 0, 1600, 1200, new Pixel(800,0), new Pixel(820, 1200), fondo, ImagenMod.MODO_PINTAR_SIN_TRATAMIENTO);
		
		
		// Pinta el árbol
		System.out.println("Pinta el árbol");
		EsquemaDegradadoColor esq = new EsquemaDegradadoColor(new Color(80, 0, 0), new Color(0,200, 100, 30), ModoDegradado.HSV_DIRECTO);
		esq.añadirColor(0.75f, new Color(255, 127, 0, 220));
		ImagenMod imgArbol = arbol.getImagenMod(1600, 1200, esq);
		img.pintaImagen(0, 0, imgArbol,ImagenMod.MODO_PINTAR_SUPERPONER);
		
		// Graba el resultado
		System.out.println("Graba el resultado");
		try {
			img.grabarFichero("Prueba.png");
		} catch (IOException e1) {
			System.out.print("No fue posible grabar el fichero");
			e1.printStackTrace();
		}
		System.out.println("Terminado");
	}
	
}
