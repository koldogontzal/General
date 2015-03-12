package code;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import utils.Patron;
import utils.Punto;
import utils.VectorFrac;

public class Fractalizador {

	/**
	 * @param args
	 */
	private Patron patron;

	private ArrayList<VectorFrac> vectoresPorAnalizar;

	private Punto supDrch = new Punto(1, 0); // Punto superior derecho del fractal

	private Punto infIzq = new Punto(0, 0); // Punto inferior izquierdo del fractal

	private int iteraciones;

	public Fractalizador(Patron patron, int niveles) {
		this.patron = patron;
		this.vectoresPorAnalizar = new ArrayList<VectorFrac>(100);
		this.iteraciones = niveles;

		this.calcularListaVectoresYaVistos();
	}

	public Fractalizador(Patron patron) {
		this(patron, 100);
	}

	public void setNiveles(int niveles) {
		// Define cuantas iteraciones se harían sobre la línea original y recalcula todo
		this.iteraciones = niveles;

		this.calcularListaVectoresYaVistos();
	}

	private void calcularListaVectoresYaVistos() {
		// Lo primero, empieza con un vector único sobre el cual se aplica el
		// Patron tantas veces como
		// indique "niveles". EL vector inicial es el (0,0)->(1,0), es decir la
		// unidad en el eje X.
		this.vectoresPorAnalizar.add(new VectorFrac(new Punto(1, 0)));

		int contadorElementos = 1;
		int i = 0;

		// Recorre la lista de vectores por analizar hasta que no hay más
		// elementos o se ha alcanzado ya el límite marcado por this.iteraciones
		while ((contadorElementos < this.iteraciones)
				&& (i < this.vectoresPorAnalizar.size())) {
			VectorFrac vectorParaTransformar = this.vectoresPorAnalizar.get(i);

			VectorFrac[] nuevosVectores = this.patron
					.recalcularConNuevoVector(vectorParaTransformar);
			for (VectorFrac nuevoVector : nuevosVectores) {
				this.vectoresPorAnalizar.add(nuevoVector);
				contadorElementos++;
				this.actualizarExtremosFractal(nuevoVector);
			}
			i++;

			// System.out.println(contadorElementos);
		}

		System.out.println("Ya está. Hay " + contadorElementos + " elementos.");
	}

	private void actualizarExtremosFractal(VectorFrac vector) {
		if (vector.maxX() > this.supDrch.getRectangularesX()) {
			this.supDrch.setRectangularesX(vector.maxX());
		}
		if (vector.maxY() > this.supDrch.getRectangularesY()) {
			this.supDrch.setRectangularesY(vector.maxY());
		}
		if (vector.minY() < this.infIzq.getRectangularesY()) {
			this.infIzq.setRectangularesY(vector.minY());
		}
		if (vector.minX() < this.infIzq.getRectangularesX()) {
			this.infIzq.setRectangularesX(vector.minX());
		}
	}

	@Override
	public String toString() {
		String resultado = "";
		for (int i = 0; i < this.vectoresPorAnalizar.size(); i++) {
			resultado = resultado + this.vectoresPorAnalizar.get(i) + "\n";
		}
		return resultado;
	}

	@SuppressWarnings("deprecation")
	public void paintFractalizador(Color color) {
		Frame f = new Frame("Fractalizador");
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		// Le pongo "0" para que no dibuje los ejes, otro valor para que sí
		f.add(new FractalizadorCanvas(color, this.vectoresPorAnalizar,
				this.infIzq, this.supDrch, 0), BorderLayout.CENTER);
		f.pack();
		f.show();
	}

	public static void main(String[] args) {

/*
 *  // Ejemplo 1 // Crear un Patron Patron patron = new Patron(3);
 * patron.addVector(new Vector(new Punto(0.3, 0.01))); patron.addVector(new
 * Vector(new Punto(0.3, 0), new Punto(0.5, 0.7))); patron.addVector(new
 * Vector(new Punto(0.3, 0), new Punto(1, -0.15))); Fractalizador f = new
 * Fractalizador(patron, 525000); // System.out.println(f);
 * f.paintFractalizador(Color.green);
 */		
	
/*		
		// Ejemplo 1.5
		// Crear un Patron
		Patron patron = new Patron(3);
		patron.addVector(new Vector(new Punto(0, 0.07), new Punto(0.3, 0.07)));
		patron.addVector(new Vector(new Punto(0.3, 0), new Punto(.9, 0.6)));
		patron.addVector(new Vector(new Punto(0.3, 0), new Punto(0.7, -0.4)));
		Fractalizador f = new Fractalizador(patron, 525000);
		// System.out.println(f);
		f.paintFractalizador(Color.red);
*/
	
/*
		// Ejemplo 2
		// Crear un Patron
		Patron patron = new Patron(3);
		patron.addVector(new Vector(new Punto(0.3, 0.01)));
		patron.addVector(new Vector(new Punto(0.3, 0.01), new Punto(0.5, 0.2)));
		patron.addVector(new Vector(new Punto(0.5, 0.2), new Punto(0.5, 0.6)));
		patron.addVector(new Vector(new Punto(0.5, 0.6), new Punto(0.1, 0.65)));
		patron.addVector(new Vector(new Punto(0.5, 0.6), new Punto(0.9, 0.4)));
		patron.addVector(new Vector(new Punto(0.7, 0), new Punto(0.9, -0.4)));		
		Fractalizador f = new Fractalizador(patron, 525000);
		// System.out.println(f);
		f.paintFractalizador(Color.gray);
		
*/

/*

		// Ejemplo 3
		// Crear un Patron
		Patron patron = new Patron(3);
		patron.addVector(new Vector(new Punto(1, 0), new Punto(0.75, 0)));
		patron.addVector(new Vector(new Punto(0.75, 0), new Punto(0.5, 0.5)));
		patron.addVector(new Vector(new Punto(0.5, 0.5), new Punto(0.25, 0)));
		patron.addVector(new Vector(new Punto(0.25, 0), new Punto(0, 0)));		
		Fractalizador f = new Fractalizador(patron, 525000);
		f.paintFractalizador(Color.blue);
*/

		
/*
		  
		 //		 Ejemplo 4
		// Crear un Patron
		Patron patron = new Patron(3);
		patron.addVector(new Vector(new Punto(1, 0), new Punto(0.08, 0.11)));
		patron.addVector(new Vector(new Punto(0.75, -0.90), new Punto(0.95, -.85)));
		//patron.addVector(new Vector(new Punto(-0.75, 0.4), new Punto(-0.75, 0.05)));
		//patron.addVector(new Vector(new Punto(0.25, 0), new Punto(0, 0)));		
		Fractalizador f = new Fractalizador(patron, 525000);
		f.paintFractalizador(Color.red);
*/

		
/*
		//		 Ejemplo 5
		// Crear un Patron
		Patron patron = new Patron(3);
		patron.addVector(new Vector(new Punto(1, 0), new Punto(.8, 0.7)));
		patron.addVector(new Vector(new Punto(0.75, 0.70), new Punto(0.45, .45)));
		patron.addVector(new Vector(new Punto(.45, 0.4), new Punto(0, 0.05)));
		//patron.addVector(new Vector(new Punto(0.025, 0), new Punto(0.17, -.270)));		
		Fractalizador f = new Fractalizador(patron, 225000);
		f.paintFractalizador(Color.magenta);
*/
	
/*
		//		 Ejemplo 6
		// Crear un Patron
		Patron patron = new Patron(5);
		patron.addVector(new Vector(new Punto(1, 0), new Punto(.4, 0)));
		patron.addVector(new Vector(new Punto(0.4, 0), new Punto(0.2, .2)));
		patron.addVector(new Vector(new Punto(.2, 0.2), new Punto(0.2, 0.4)));
		patron.addVector(new Vector(new Punto(0.2, 0.4), new Punto(0.4, .6)));
		patron.addVector(new Vector(new Punto(0.4, 0.6), new Punto(0.6, .6)));
		patron.addVector(new Vector(new Punto(0.6, 0.6), new Punto(0.8, .4)));
		//patron.addVector(new Vector(new Punto(0.8, 0.4), new Punto(0.8, .2)));
		//patron.addVector(new Vector(new Punto(0.8, 0.2), new Punto(0.6, 0)));
		//patron.addVector(new Vector(new Punto(0.6, 0), new Punto(0, 0)));
		Fractalizador f = new Fractalizador(patron, 699000);
		f.paintFractalizador(Color.black);
*/

/*
		//		 Ejemplo 7
		// Crear un Patron
		Patron patron = new Patron(3);
		patron.addVector(new Vector(new Punto(0.2, -0.05), new Punto(.7, .3)));
		patron.addVector(new Vector(new Punto(0.2, -.05), new Punto(0.8, -.6)));
		patron.addVector(new Vector(new Punto(0, 0), new Punto(0.25, -0.04)));

		Fractalizador f = new Fractalizador(patron, 500000);
		f.paintFractalizador(Color.pink);
*/
		
/*		
				//		 Ejemplo 8
				// Crear un Patron
				Patron patron = new Patron(3);
				patron.addVector(new Vector(new Punto(0.2, -0.05), new Punto(.7, .3)));
				patron.addVector(new Vector(new Punto(0.2, -.05), new Punto(0.8, -.6)));
				patron.addVector(new Vector(new Punto(0, 0), new Punto(0.25, -0.04)));

				patron.addVector(new Vector(new Punto(0.8, 0.4), new Punto(0.8, .2)));
				
				Fractalizador f = new Fractalizador(patron, 500000);
				f.paintFractalizador(Color.cyan);
*/			

/*		
		//		 Ejemplo 9
		// Crear un Patron
		Patron patron = new Patron(3);
		patron.addVector(new Vector(new Punto(0, -0.05), new Punto(.6, 0)));
		patron.addVector(new Vector(new Punto(-0.50431, .3), new Punto(0.3, 0)));
		patron.addVector(new Vector(new Punto(0.3, 0), new Punto(-0.35, -0.65)));

		Fractalizador f = new Fractalizador(patron, 100000);
		f.paintFractalizador(new Color(255,200,0));
*/	
		
/*		
		//		 Ejemplo 10
		// Crear un Patron
		Patron patron = new Patron(3);
		patron.addVector(new Vector(new Punto(0, -0.05), new Punto(.5, .45)));
		patron.addVector(new Vector(new Punto(-1, -.5), new Punto(-0.5, -1)));
		//patron.addVector(new Vector(new Punto(0.3, 0), new Punto(-0.35, -0.65)));

		//patron.addVector(new Vector(new Punto(0.8, 0.4), new Punto(0.8, .2)));
		
		Fractalizador f = new Fractalizador(patron, 100000);
		f.paintFractalizador(new Color(130,200,255));
*/	
		
	
		
/*		
		//		 Ejemplo 11
		// Crear un Patron
		Patron patron = new Patron(3);
		patron.addVector(new Vector(new Punto(0, -0.05), new Punto(.5, .45)));
		patron.addVector(new Vector(new Punto(-1, -.5), new Punto(-0.5, -1)));
		patron.addVector(new Vector(new Punto(0.6, -0.5), new Punto(0.1, -1)));

		Fractalizador f = new Fractalizador(patron, 100000);
		f.paintFractalizador(new Color(130,010,255));
*/	

/*		
		//		 Ejemplo 12
		// Crear un Patron
		Patron patron = new Patron(3);
		patron.addVector(new Vector(new Punto(0.5, .8), new Punto(.5, .30)));
		patron.addVector(new Vector(new Punto(.25, .55), new Punto(0.75, .55)));
		
		patron.addVector(new Vector(new Punto(.25, .8), new Punto(.75, .3)));
		patron.addVector(new Vector(new Punto(.25, .3), new Punto(.75, .8)));
		
	
		Fractalizador f = new Fractalizador(patron, 600000);
		f.paintFractalizador(new Color(150,0,255));
*/
		
		/*		
		//		 Ejemplo 13
		// Crear un Patron
		Patron patron = new Patron(3);
		patron.addVector(new Vector(new Punto(0, .3), new Punto(.3, 0.02)));
		patron.addVector(new Vector(new Punto(.3, 0.02), new Punto(0.6, -.01)));
		
		patron.addVector(new Vector(new Punto(.6, -.01), new Punto(1, -.4)));
		
		patron.addVector(new Vector(new Punto(.8, 1.2), new Punto(1.425, .62)));
		
		//patron.addVector(new Vector(new Punto(.47, .7), new Punto(.57, .7)));
		///patron.addVector(new Vector(new Punto(1, 0), new Punto(1.057, -.27)));
		
		//patron.addVector(new Vector(new Punto(.95, .0712), new Punto(.25, -.2405)));
		
		Fractalizador f = new Fractalizador(patron, 750000);
		f.paintFractalizador(new Color(15,150,25));
*/
		
		/*		
		//		 Ejemplo 14: Estrella de 7 puntas
		// Crear un Patron
		Patron patron = new Patron(3);
		patron.addVector(new Vector(new Punto(0.9, .1), new Punto(.053, 0.502)));
		
		Fractalizador f = new Fractalizador(patron, 100000);
		f.paintFractalizador(new Color(15,150,25));
*/
	
		
	/*
		//		 Ejemplo 15: Cuchillas molinillo
		// Crear un Patron
		Patron patron = new Patron(3);
		patron.addVector(new Vector(new Punto(0.9, .1), new Punto(.18235053, 0.4502)));
		patron.addVector(new Vector(new Punto(0.18235059, .4501), new Punto(.65053, 0.6502)));
		
		Fractalizador f = new Fractalizador(patron, 100000);
		f.paintFractalizador(new Color(15,150,25));
*/

		/*
		//		 Ejemplo 16:Espiral
		// Crear un Patron
		Patron patron = new Patron(3);
		patron.addVector(new Vector(new Punto(-0.19, .31), new Punto(.718235053, 0.04502)));
		//patron.addVector(new Vector(new Punto(0.18235059, .4501), new Punto(.65053, 0.6502)));
		
		Fractalizador f = new Fractalizador(patron, 500000);
		f.paintFractalizador(new Color(15,150,25));
*/
		
		/*
		//		 Ejemplo 17: Hago lo que quiero con mi pelo
		// Crear un Patron
		Patron patron = new Patron(3);
		patron.addVector(new Vector(new Punto(-0.19, .31), new Punto(.818235053, 0.04502)));
		patron.addVector(new Vector(new Punto(0.18235059, .4501), new Punto(.65053, 0.6502)));
		
		Fractalizador f = new Fractalizador(patron, 500000);
		f.paintFractalizador(new Color(15,150,25));
*/
		
		/*
		//		 Ejemplo 18: Circulo perfecto
		// Crear un Patron
		Patron patron = new Patron(3);
		patron.addVector(new Vector(new Punto(-0.19, .31), new Punto(-.19 + Math.cos(.1), .31 + Math.sin(.1))));
		//patron.addVector(new Vector(new Punto(0.18235059, .4501), new Punto(.65053, 0.6502)));
		
		Fractalizador f = new Fractalizador(patron, 100000);
		f.paintFractalizador(new Color(15,150,25));
*/
		
		/*
		//		 Ejemplo 19: Supernova
		// Crear un Patron
		Patron patron = new Patron(3);
		patron.addVector(new Vector(new Punto(-0.19, .31), new Punto(-.19 + Math.cos(.1) * 1.001, .31 + Math.sin(.1) * 1.001)));
		//patron.addVector(new Vector(new Punto(0.18235059, .4501), new Punto(.65053, 0.6502)));
		
		Fractalizador f = new Fractalizador(patron, 100000);
		f.paintFractalizador(new Color(15,150,25));
		*/

		
		//		 Ejemplo 20: Electron
		// Crear un Patron
		Patron patron = new Patron(3);
		patron.addVector(new VectorFrac(new Punto(-0.19, .31), new Punto(-.19 + Math.cos(.1) * 1.0001, .31 + Math.sin(.1) * 1.0001)));
		//patron.addVector(new Vector(new Punto(0.18235059, .4501), new Punto(.65053, 0.6502)));
		
		Fractalizador f = new Fractalizador(patron, 100000);
		f.paintFractalizador(new Color(15,150,25));
		

		/*
		//		 Ejemplo 22: Gorro de gnomo
		// Crear un Patron
		Patron patron = new Patron(3);
		patron.addVector(new Vector(new Punto(-0.19, .31), new Punto(-.19 + Math.cos(.1) * .9, .31 + Math.sin(.1) * .9)));
		//patron.addVector(new Vector(new Punto(0.18235059, .4501), new Punto(.65053, 0.6502)));
		
		Fractalizador f = new Fractalizador(patron, 100000);
		f.paintFractalizador(new Color(15,150,25));
		*/

		/*
		//		 Ejemplo 23
		// Crear un Patron
		Patron patron = new Patron(3);
		patron.addVector(new Vector(new Punto(-0.19, .31), new Punto(-.19 + Math.cos(.1) * .79, .31 - Math.sin(.1) * .79)));
		//patron.addVector(new Vector(new Punto(0.18235059, .4501), new Punto(.65053, 0.6502)));
		
		Fractalizador f = new Fractalizador(patron, 100000);
		f.paintFractalizador(new Color(15,150,25));
		 */

	
	}

}
