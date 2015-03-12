package elementos;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

import utils.Posicion;

public abstract class Celda {
	private Escenario escenario;
	
	private Posicion pos;
	private int tiempoReaccion;
	private int tiempo;
	
	public static final int CATALIZADOR = 101;
	public static final int VACIA = 102;
	public static final int SUSTRATO = 103;
	public static final int VINCULO = 104;
	public static final int LIGADA = 105;
	
	
	public Celda(Escenario esc, Posicion pos, int tiempo) {
		this.escenario = esc;
		this.pos = pos;
		this.tiempoReaccion = tiempo;
		this.tiempo = tiempo;
	}

	public Posicion getPos() {
		return this.pos;
	}

	public void setPos(Posicion pos) {
		this.pos = pos;
	}
	
	private void resetearTiempo() {
		this.tiempo = this.tiempoReaccion;
	}
	
	protected boolean pasarTiempo() {
		this.tiempo = this.tiempo - 1;
		
		if (this.tiempo == 0) {
			this.resetearTiempo();
			return true;
		} else {
			return false;
		}
	}
	
	public abstract void dibujar(Graphics2D g);
	
	public abstract void actuar();

	public abstract int queSoy();

	public Escenario getEscenario() {
		return escenario;
	}
	
	@Override
	public String toString() {
		return "Posicion:" + this.pos + " Tiempo reaccion:" + this.tiempoReaccion + " ";
	}
	
	protected void mover() {
		ArrayList<Celda> vecinos = this.getEscenario().get8Vecinos(this.getPos());
		
		Iterator<Celda> i = vecinos.iterator();
		boolean encontradoVacio = false;
		while (i.hasNext() && !encontradoVacio) {
			Celda c = i.next();
			if (c.queSoy() == Celda.VACIA) {
				this.getEscenario().intercambiarCeldas(this, c);
				encontradoVacio = true;
			}
		}
	}
}
