package utils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

public class EsquemaDegradadoColor {
	
	// Atributos
	private ArrayList<PosicionDegradado> lista;
	private ModoDegradado modo;
	
	// Clase privada auxiliar que se usa únicamente para simplificar el manejo de la lista de colores del degradado 
	private class PosicionDegradado implements Comparable<PosicionDegradado> {
		private float pos;
		private Color c;
		
		public PosicionDegradado(float pos, Color c) {
			this.pos = Math.min(1f, Math.max(0f, pos));
			this.c = c;
		}

		public float getPosicion() {
			return this.pos;
		}
		
		public Color getColor() {
			return this.c;
		}
		
		@Override
		public int compareTo(PosicionDegradado pos) {
			Double p0 = new Double(this.pos);
			Double p1 = new Double(pos.getPosicion());
			return p0.compareTo(p1);
		}
	}
	
	// Enumeración pública con los tipos diferentes de degradado
	public enum ModoDegradado {
		RGB,
		HSV_DIRECTO,
		HSV_CAMINO_MAS_CORTO;
		
		public static ModoDegradado aleatorio() {
			int op = (int)(Math.random() * 3);
			switch (op) {
			case 0:
				return HSV_DIRECTO;
			case 1:
				return HSV_CAMINO_MAS_CORTO;
			default:
			case 2:
				return RGB;
			}
		}
	}
	
	// Constructores
	public EsquemaDegradadoColor(Color cIni, Color cFin, ModoDegradado modo) {
		this.lista = new ArrayList<PosicionDegradado>(4);
		this.añadirColor(0f, cIni);
		this.añadirColor(1f, cFin);
		this.modo = modo;		
	}
	
	// Métodos
	public void añadirColor(float pos, Color c) {
		// Añade el Color c en la posición pos del degradado. El degradado va desde la pos 0.0 hasta 1.0. Cualquier punto introducido fuera de esos límites, modificará el color del borde respectivo
		PosicionDegradado p = new PosicionDegradado(pos, new ColorMod(c));
		this.lista.add(p);
		Collections.sort(this.lista);
	}
	
	public ColorMod getColorModAtPos(float pos) {
		// Obtiene el ColorMod de la posicion pos del Degradado
		
		// delimita pos entre 0.0 y 1.0
		pos = Math.min(1f, Math.max(0f, pos));
		
		// Busca entre qué dos Posiciones de Degradado, p0 y p1,cae "pos"
		int numPosDeg = 0;
		PosicionDegradado p0;
		PosicionDegradado p1;
		do {	
			p0 = this.lista.get(numPosDeg);
			p1 = this.lista.get(numPosDeg + 1);
			numPosDeg++; 
		} while ((p0.getPosicion() > pos) || (pos > p1.getPosicion()));
		
		// Calcula el ColorMod que devolverá, en función del modo de degradado
		ColorMod ret;
		float posRelativa = (pos - p0.getPosicion()) / (p1.getPosicion() - p0.getPosicion());
				// Suaviza la curva de la transformada para que el degradado sea más suave
		posRelativa = (1f - (float)Math.cos(Math.PI * posRelativa)) / 2f; 
		switch (this.modo) {
		case RGB:
			ret = ColorMod.getColorProporcionalRGB(p0.getColor(), p1.getColor(), posRelativa);
			break;
		case HSV_DIRECTO:
			ret = ColorMod.getColorProporcionalHSV(p0.getColor(), p1.getColor(), posRelativa);
			break;
		case HSV_CAMINO_MAS_CORTO:
			ret = ColorMod.getColorProporcionalHSV_GiroCorto(p0.getColor(), p1.getColor(), posRelativa);
			break;
		default:
			// No se debe dar este caso
			ret = null;
		}
		
		return ret;
	}
	
	public void cambiarModo(ModoDegradado modo) {
		this.modo = modo;
	}

	public ModoDegradado getModoDegradado() {
		return this.modo;
	}
	
	public static EsquemaDegradadoColor aleatorio() {
		// Devuelve un esquema de degradado de color aleatorio con 2 colores (sencillo)
		return new EsquemaDegradadoColor(ColorMod.aleatorioOscuro(), ColorMod.aleatorioClaro(), ModoDegradado.aleatorio());
	}

}
