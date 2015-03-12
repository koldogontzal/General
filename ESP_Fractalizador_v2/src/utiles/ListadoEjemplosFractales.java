package utiles;

import java.awt.Color;
import java.util.ArrayList;

import com.koldogontzal.geometria2d.Punto;
import com.koldogontzal.geometria2d.Vector;

import utils.ImagenMod;

import code.PatronFractal;

public class ListadoEjemplosFractales extends ArrayList<EjemploFractal> {
	
	private static final long serialVersionUID = -7275531190812353448L;

	public ListadoEjemplosFractales() {
		super();
		
		// Ejemplo 1
		PatronFractal patron01 = new PatronFractal(3);
		patron01.addVector(new Vector(new Punto(0.3, 0.01))); 
		patron01.addVector(new Vector(new Punto(0.3, 0), new Punto(0.5, 0.7))); 
		patron01.addVector(new Vector(new Punto(0.3, 0), new Punto(1, -0.15)));
		EjemploFractal ej1 = new EjemploFractal(patron01, 525000, "Helecho",
				Color.WHITE, new Color(30, 140, 10, 20),
				ImagenMod.MODO_PINTAR_SUPERPONER);
		this.añadirEjemplo(ej1);
		
		// Ejemplo 2
		PatronFractal patron2 = new PatronFractal(3);
		patron2.addVector(new Vector(new Punto(0, 0.07), new Punto(0.3, 0.07)));
		patron2.addVector(new Vector(new Punto(0.3, 0), new Punto(.9, 0.6)));
		patron2.addVector(new Vector(new Punto(0.3, 0), new Punto(0.7, -0.4)));
		EjemploFractal ej2 = new EjemploFractal(patron2, 525000, "Embrión",
				Color.WHITE, new Color(230, 80, 50, 90),
				ImagenMod.MODO_PINTAR_SUPERPONER);
		this.añadirEjemplo(ej2);
		
		
		// Ejemplo 3
		PatronFractal patron3 = new PatronFractal(3);
		patron3.addVector(new Vector(new Punto(0.3, 0.01)));
		patron3.addVector(new Vector(new Punto(0.3, 0.01), new Punto(0.5, 0.2)));
		patron3.addVector(new Vector(new Punto(0.5, 0.2), new Punto(0.5, 0.6)));
		patron3.addVector(new Vector(new Punto(0.5, 0.6), new Punto(0.1, 0.65)));
		patron3.addVector(new Vector(new Punto(0.5, 0.6), new Punto(0.9, 0.4)));
		patron3.addVector(new Vector(new Punto(0.7, 0), new Punto(0.9, -0.4)));		
		EjemploFractal ej3 = new EjemploFractal(patron3, 525000, "Nubes",
				new Color(0, 100, 180), new Color(230, 250, 210, 80),
				ImagenMod.MODO_PINTAR_SUPERPONER);
		this.añadirEjemplo(ej3);
		
		// Ejemplo 4
		PatronFractal patron4 = new PatronFractal();
		patron4.addVector(new Vector(new Punto(1, 0), new Punto(0.75, 0)));
		patron4.addVector(new Vector(new Punto(0.75, 0), new Punto(0.5, 0.5)));
		patron4.addVector(new Vector(new Punto(0.5, 0.5), new Punto(0.25, 0)));
		patron4.addVector(new Vector(new Punto(0.25, 0), new Punto(0, 0)));	
		EjemploFractal ej4 = new EjemploFractal(patron4, 525000, "Stargate",
				Color.WHITE, new Color(30, 50, 210, 20),
				ImagenMod.MODO_PINTAR_SUPERPONER);
		this.añadirEjemplo(ej4);
		
		// Ejemplo 5
		PatronFractal patron5 = new PatronFractal();
		patron5.addVector(new Vector(new Punto(1, 0), new Punto(0.08, 0.11)));
		patron5.addVector(new Vector(new Punto(0.75, -0.90), new Punto(0.95, -.85)));
		//patron.addVector(new Vector(new Punto(-0.75, 0.4), new Punto(-0.75, 0.05)));
		//patron.addVector(new Vector(new Punto(0.25, 0), new Punto(0, 0)));
		EjemploFractal ej5 = new EjemploFractal(patron5, 525000, "GalaxiaEspiral",
				Color.BLACK, new Color(230, 250, 100, 70),
				ImagenMod.MODO_PINTAR_SUPERPONER);
		this.añadirEjemplo(ej5);
		
		// Ejemplo 6						
		PatronFractal patron6 = new PatronFractal();
		patron6.addVector(new Vector(new Punto(1, 0), new Punto(.8, 0.7)));
		patron6.addVector(new Vector(new Punto(0.75, 0.70), new Punto(0.45, .45)));
		patron6.addVector(new Vector(new Punto(.45, 0.4), new Punto(0, 0.05)));
		//patron.addVector(new Vector(new Punto(0.025, 0), new Punto(0.17, -.270)));					
		EjemploFractal ej6 = new EjemploFractal(patron6, 225000, "Dragón",
				Color.WHITE, new Color(30, 150, 100, 60),
				ImagenMod.MODO_PINTAR_SUPERPONER);
		this.añadirEjemplo(ej6);
				
		// Ejemlpo 7
		PatronFractal patron7 = new PatronFractal();
		patron7.addVector(new Vector(new Punto(1, 0), new Punto(.4, 0)));
		patron7.addVector(new Vector(new Punto(0.4, 0), new Punto(0.2, .2)));
		patron7.addVector(new Vector(new Punto(.2, 0.2), new Punto(0.2, 0.4)));
		patron7.addVector(new Vector(new Punto(0.2, 0.4), new Punto(0.4, .6)));
		patron7.addVector(new Vector(new Punto(0.4, 0.6), new Punto(0.6, .6)));
		patron7.addVector(new Vector(new Punto(0.6, 0.6), new Punto(0.8, .4)));
		//patron.addVector(new Vector(new Punto(0.8, 0.4), new Punto(0.8, .2)));
		//patron.addVector(new Vector(new Punto(0.8, 0.2), new Punto(0.6, 0)));
		//patron.addVector(new Vector(new Punto(0.6, 0), new Punto(0, 0)));
		EjemploFractal ej7 = new EjemploFractal(patron7, 699000, "Margaritas",
				Color.WHITE, new Color(0, 10, 15, 59),
				ImagenMod.MODO_PINTAR_SUPERPONER);
		this.añadirEjemplo(ej7);

		//Ejemplo 8
		PatronFractal patron8 = new PatronFractal();
		patron8.addVector(new Vector(new Punto(0.2, -0.05), new Punto(.7, .3)));
		patron8.addVector(new Vector(new Punto(0.2, -.05), new Punto(0.8, -.6)));
		patron8.addVector(new Vector(new Punto(0, 0), new Punto(0.25, -0.04)));
		EjemploFractal ej8 = new EjemploFractal(patron8, 500000, "Embrión2",
				Color.WHITE, new Color(200, 15, 115, 79),
				ImagenMod.MODO_PINTAR_SUPERPONER);
		this.añadirEjemplo(ej8);
	
		// Ejemplo 9
		PatronFractal patron9 = new PatronFractal();
		patron9.addVector(new Vector(new Punto(0.2, -0.05), new Punto(.7, .3)));
		patron9.addVector(new Vector(new Punto(0.2, -.05), new Punto(0.8, -.6)));
		patron9.addVector(new Vector(new Punto(0, 0), new Punto(0.25, -0.04)));
		patron9.addVector(new Vector(new Punto(0.8, 0.4), new Punto(0.8, .2)));
		EjemploFractal ej9 = new EjemploFractal(patron9, 500000, "FloresCampestres",
				new Color(30, 140, 5), new Color(200, 250, 90, 109),
				ImagenMod.MODO_PINTAR_SUPERPONER);
		this.añadirEjemplo(ej9);
			
		// Ejemplo 10
		PatronFractal patron10 = new PatronFractal();
		patron10.addVector(new Vector(new Punto(0, -0.05), new Punto(.6, 0)));
		patron10.addVector(new Vector(new Punto(-0.50431, .3), new Punto(0.3, 0)));
		patron10.addVector(new Vector(new Punto(0.3, 0), new Punto(-0.35, -0.65)));
		EjemploFractal ej10 = new EjemploFractal(patron10, 100000, "Picho",
				Color.GRAY, new Color(240, 235, 245, 19),
				ImagenMod.MODO_PINTAR_SUPERPONER);
		this.añadirEjemplo(ej10);
		
		// Ejemplo 11
		PatronFractal patron11 = new PatronFractal();
		patron11.addVector(new Vector(new Punto(0, -0.05), new Punto(.5, .45)));
		patron11.addVector(new Vector(new Punto(-1, -.5), new Punto(-0.5, -1)));
		//patron.addVector(new Vector(new Punto(0.3, 0), new Punto(-0.35, -0.65)));
		//patron.addVector(new Vector(new Punto(0.8, 0.4), new Punto(0.8, .2)));
		EjemploFractal ej11 = new EjemploFractal(patron11, 100000, "Cruasán",
				new Color (128, 40,0), new Color(230,180,5, 200),
				ImagenMod.MODO_PINTAR_SUPERPONER);
		this.añadirEjemplo(ej11);
	
		// Ejemplo 12
		PatronFractal patron12 = new PatronFractal();
		patron12.addVector(new Vector(new Punto(0, -0.05), new Punto(.5, .45)));
		patron12.addVector(new Vector(new Punto(-1, -.5), new Punto(-0.5, -1)));
		patron12.addVector(new Vector(new Punto(0.6, -0.5), new Punto(0.1, -1)));
		EjemploFractal ej12 = new EjemploFractal(patron12, 100000, "CruasánDeconstruido",
				Color.WHITE, new Color(130, 010, 255, 109),
				ImagenMod.MODO_PINTAR_SUPERPONER);
		this.añadirEjemplo(ej12);

		// Ejemplo 13
		PatronFractal patron13 = new PatronFractal();
		patron13.addVector(new Vector(new Punto(0.5, .8), new Punto(.5, .30)));
		patron13.addVector(new Vector(new Punto(.25, .55), new Punto(0.75, .55)));
		patron13.addVector(new Vector(new Punto(.25, .8), new Punto(.75, .3)));
		patron13.addVector(new Vector(new Punto(.25, .3), new Punto(.75, .8)));
		EjemploFractal ej13 = new EjemploFractal(patron13, 600000, "EmbriónCuadriculado",
				Color.WHITE, new Color(150, 010, 255, 59),
				ImagenMod.MODO_PINTAR_SUPERPONER);
		this.añadirEjemplo(ej13);	

		// Ejemplo 14
		PatronFractal patron14 = new PatronFractal();
		patron14.addVector(new Vector(new Punto(0, .3), new Punto(.3, 0.02)));
		patron14.addVector(new Vector(new Punto(.3, 0.02), new Punto(0.6, -.01)));
		patron14.addVector(new Vector(new Punto(.6, -.01), new Punto(1, -.4)));
		patron14.addVector(new Vector(new Punto(.8, 1.2), new Punto(1.425, .62)));
		//patron.addVector(new Vector(new Punto(.47, .7), new Punto(.57, .7)));
		///patron.addVector(new Vector(new Punto(1, 0), new Punto(1.057, -.27)));
		//patron.addVector(new Vector(new Punto(.95, .0712), new Punto(.25, -.2405)));
		EjemploFractal ej14 = new EjemploFractal(patron14, 750000, "HierbaEsparcidaPorElViento",
				Color.WHITE, new Color(15, 150, 25, 59),
				ImagenMod.MODO_PINTAR_SUPERPONER);
		this.añadirEjemplo(ej14);			

		// Ejemplo 15
		PatronFractal patron15 = new PatronFractal();
		patron15.addVector(new Vector(new Punto(0.9, .1), new Punto(.053, 0.502)));
		EjemploFractal ej15 = new EjemploFractal(patron15, 100000, "Estrella7Puntas",
				Color.WHITE, new Color(15, 150, 25, 159),
				ImagenMod.MODO_PINTAR_SUPERPONER);
		this.añadirEjemplo(ej15);		
				
		// Ejemplo 16
		PatronFractal patron16 = new PatronFractal();
		patron16.addVector(new Vector(new Punto(0.9, .1), new Punto(.18235053, 0.4502)));
		patron16.addVector(new Vector(new Punto(0.18235059, .4501), new Punto(.65053, 0.6502)));
		EjemploFractal ej16 = new EjemploFractal(patron16, 150000, "ShurikenNinja",
				Color.WHITE, new Color(160, 150, 155, 120),
				ImagenMod.MODO_PINTAR_SUPERPONER);
		this.añadirEjemplo(ej16);		

		// Ejemplo 17
		PatronFractal patron17 = new PatronFractal();
		patron17.addVector(new Vector(new Punto(-0.19, .31), new Punto(.718235053, 0.04502)));
		patron17.addVector(new Vector(new Punto(0.18235059, .4501), new Punto(.65053, 0.6502)));
		EjemploFractal ej17 = new EjemploFractal(patron17, 500000, "HagoLoQueQUieroConMiPelo",
				Color.WHITE, new Color(160, 0, 55, 50),
				ImagenMod.MODO_PINTAR_SUPERPONER);
		this.añadirEjemplo(ej17);		


		//		 Ejemplo 18: Circulo perfecto
		PatronFractal patron18 = new PatronFractal();
		patron18.addVector(new Vector(new Punto(-0.19, .31), new Punto(-.19 + Math.cos(.1), .31 + Math.sin(.1))));
		//patron.addVector(new Vector(new Punto(0.18235059, .4501), new Punto(.65053, 0.6502)));
		EjemploFractal ej18 = new EjemploFractal(patron18, 100000, "CírculoPerfecto",
				Color.WHITE, new Color(160, 0, 55, 5),
				ImagenMod.MODO_PINTAR_SUPERPONER);
		this.añadirEjemplo(ej18);	
		
		//		 Ejemplo 19: Supernova
		PatronFractal patron19 = new PatronFractal();
		patron19.addVector(new Vector(new Punto(-0.19, .31), new Punto(-.19 + Math.cos(.1) * 1.001, .31 + Math.sin(.1) * 1.001)));
		//patron.addVector(new Vector(new Punto(0.18235059, .4501), new Punto(.65053, 0.6502)));
		EjemploFractal ej19 = new EjemploFractal(patron19, 100000, "Supernova",
				Color.WHITE, new Color(160, 85, 0, 155),
				ImagenMod.MODO_PINTAR_SUPERPONER);
		this.añadirEjemplo(ej19);			

		//		 Ejemplo 20: Electron
		PatronFractal patron20 = new PatronFractal();
		patron20.addVector(new Vector(new Punto(-0.19, .31), new Punto(-.19 + Math.cos(.1) * 1.0001, .31 + Math.sin(.1) * 1.0001)));
		//patron.addVector(new Vector(new Punto(0.18235059, .4501), new Punto(.65053, 0.6502)));
		EjemploFractal ej20 = new EjemploFractal(patron20, 100000, "Electrón",
				Color.WHITE, new Color(160, 85, 0, 155),
				ImagenMod.MODO_PINTAR_SUPERPONER);
		this.añadirEjemplo(ej20);			
				

		//		 Ejemplo 21: Gorro de gnomo
		PatronFractal patron21 = new PatronFractal();
		patron21.addVector(new Vector(new Punto(-0.19, .31), new Punto(-.19 + Math.cos(.1) * .9, .31 + Math.sin(.1) * .9)));
		//patron.addVector(new Vector(new Punto(0.18235059, .4501), new Punto(.65053, 0.6502)));
		EjemploFractal ej21 = new EjemploFractal(patron21, 10000, "GorroGnomo",
				Color.WHITE, new Color(240, 45, 0, 255),
				ImagenMod.MODO_PINTAR_SUPERPONER);
		this.añadirEjemplo(ej21);	

		//		 Ejemplo 22
		PatronFractal patron22 = new PatronFractal();
		patron22.addVector(new Vector(new Punto(-0.19, .31), new Punto(-.19 + Math.cos(.1) * .79, .31 - Math.sin(.1) * .79)));
		patron22.addVector(new Vector(new Punto(0.18235059, .4501), new Punto(.65053, 0.6502)));
		EjemploFractal ej22 = new EjemploFractal(patron22, 500000, "Pluma",
				Color.WHITE, new Color(240, 45, 0, 25),
				ImagenMod.MODO_PINTAR_SUPERPONER);
		this.añadirEjemplo(ej22);		
	}

	private void añadirEjemplo(EjemploFractal ej1) {
		super.add(ej1);		
	}
	
}
