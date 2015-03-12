package com.koldogontzal.geometria2d;

public class Vector {
	private Punto origen;
	private Punto destino;
	
	public Vector() {
		this(new Punto(1, 0));
	}
	
	public Vector(Punto destino) {
		this(new Punto(0, 0), destino);
	}
	
	public Vector(Punto orig, Punto dest) {
		this.origen = orig;
		this.destino = dest;
	}	
	
	public Vector(Punto orig, double longitud, double angulo) {
		this.origen = orig;
		Punto dest = new PuntoCoordenadasPolares(longitud, angulo);
		dest.sumar(orig);
		this.destino = dest;		
	}
	
	public Vector(Vector vect) {
		this(new Punto(vect.getPuntoOrigen()), new Punto(vect.getPuntoFinal()));
	}
	
	public Punto getPuntoOrigen() {
		return this.origen;
	}
	
	public Punto getPuntoFinal() {
		return this.destino;
	}
	
	public Punto getDesplazamiento() {
		Punto ret = new Punto(this.destino);
		ret.restar(this.origen);
		return ret;
	}
	
	public double getModulo() {
		Punto desp = this.getDesplazamiento();
		return desp.getRadial();
	}
	
	public double getAngulo() {
		Punto desp = this.getDesplazamiento();
		return desp.getTheta();
	}
	
	public void sumar(Punto desplazamiento) {
		this.destino.sumar(desplazamiento);
	}
	
	public void sumar(Vector vect) {
		this.sumar(vect.getDesplazamiento());
	}
	
	public static Vector suma(Vector v1, Vector v2) {
		Vector aux = new Vector(v1);
		aux.sumar(v2);
		return aux;
	}
	
	public void restar(Punto desplazamiento) {
		this.destino.restar(desplazamiento);
	}
	
	public void restar(Vector vect) {
		this.restar(vect.getDesplazamiento());
	}
	
	public static Vector resta(Vector v1, Vector v2) {
		Vector aux = new Vector(v1);
		aux.restar(v2);
		return aux;
	}
	
	public void rotar(double anguloGiro) {
		Punto desp = this.getDesplazamiento();
		double modulo = desp.getRadial();
		double angulo = desp.getTheta() + anguloGiro;
		Punto despNuevo = new PuntoCoordenadasPolares(modulo, angulo);
		despNuevo.sumar(this.origen);
		this.destino = despNuevo;
	}
	
	public static Vector rotacionAngular(Vector v, double ang) {
		Vector aux = new Vector(v);
		aux.rotar(ang);
		return aux;
	}
	
	public void multiplicarPorEscalar(double escalar) {
		Punto desp = this.getDesplazamiento();
		double modulo = desp.getRadial() * escalar;
		double angulo = desp.getTheta();
		Punto despNuevo = new PuntoCoordenadasPolares(modulo, angulo);
		despNuevo.sumar(this.origen);
		this.destino = despNuevo;
	}
	
	public static Vector multiplicacionEscalar(Vector v, double escalar) {
		Vector aux = new Vector(v);
		aux.multiplicarPorEscalar(escalar);
		return aux;
	}
	
	public void desplazar(Punto desplazamiento) {
		this.origen.sumar(desplazamiento);
		this.destino.sumar(desplazamiento);
	}
	
	public static Vector desplazamiento(Vector v, Punto desplazamiento) {
		Vector aux = new Vector(v);
		aux.desplazar(desplazamiento);
		return aux;
	}

	public Vector[] getComponentesNoCartesianas(double anguloGiroComponente1, double porcentajeLong1) {
		// Obtiene las dos componentes del vector actual siendo la primera de ellas un vector con tamaño (módulo)
		// relativo con respecto al original el porcentaje marcado por porcentajeLong1 y girado el número de 
		// grados marcado por anguloGiroComponente1
		
		Vector[] ret = new Vector[2];
		
		ret[0] = new Vector(this.origen, 
				this.getModulo() * porcentajeLong1, 
				this.getAngulo() - anguloGiroComponente1);
		
		ret[1] = new Vector(this);
		ret[1].restar(ret[0]);
		
		return ret;
	}
	
	public Punto getPuntoMin() {
		// Devuelve el punto inferior izquierdo del cuadrado que forma el vector
		return Punto.min(this.getPuntoOrigen(), this.getPuntoFinal());
	}
	
	public Punto getPuntoMax() {
		// Devuelve el punto superior derecho del cuadrado que forma el vector
		return Punto.max(this.getPuntoOrigen(), this.getPuntoFinal());
	}
	
	@Override
	public String toString() {
		return this.getPuntoOrigen() + " -> " + this.getPuntoFinal();
	}
	
}
