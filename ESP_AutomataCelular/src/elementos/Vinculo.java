package elementos;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

import constantes.Constantes;
import utils.Aleatorio;
import utils.Posicion;

public class Vinculo extends Celda {
	
	private double p1AcMov;
	private double p2AcDeshacerse;

	public Vinculo(Escenario esc, Posicion pos, int tiempoMin, int tiempoMax) {
		this(esc, pos, tiempoMin, tiempoMax, 
				Constantes.VINCULO_P_MOV, Constantes.VICULO_P_DESHACERSE, Constantes.VINCULO_P_LIGARSE);
	}
	
	public Vinculo(Escenario esc, Posicion pos, int tiempoMin, int tiempoMax, double pam, double pad, double pal) {
		super(esc, pos, Aleatorio.valor(tiempoMin, tiempoMax));
		
		double pTotal = pam + pad + pal;
		this.p1AcMov = pam / pTotal;
		this.p2AcDeshacerse = (pam + pad) / pTotal;
	}

	@Override
	public void actuar() {
		boolean momento = super.pasarTiempo();
		if (momento) {
			// Hay que actuar, según las propabilidades definidas
			double p = Math.random();
			if (p < this.p1AcMov) {
				// Intenta moverse
				super.mover();
			} else if ((p >= this.p1AcMov) && (p < this.p2AcDeshacerse)) {
				// Intenta deshacerse en 2 Sustratos
				this.deshacerseEn2();
			} else {
				// Intenta ligarse a otro vinculo
				this.ligarseConOtroVinculo();
			}
		}
	} 
	
	private void ligarseConOtroVinculo() {
		// Busca otro vinculo pero sólo entre sus 4 vecinos (no valen diagonales)
		ArrayList<Celda> vecinos = this.getEscenario().get4Vecinos(this.getPos());		
		Iterator<Celda> i = vecinos.iterator();
		boolean encontradoVinculo = false;
		Celda c = null;
		while (i.hasNext() && !encontradoVinculo) {
			c = i.next();
			if ((c.queSoy() == Celda.VINCULO) || (c.queSoy() == Celda.LIGADA)){
				encontradoVinculo = true;
			}
		}
		
		// El vinculo esta, si existe, en la variable c
		if (encontradoVinculo) {
			Celda l1 = new Ligada(super.getEscenario(), super.getPos(), 
					Constantes.LIGADA_T_ACC_MIN, Constantes.LIGADA_T_ACC_MAX);
			Celda l2 = new Ligada(super.getEscenario(), c.getPos(), 
					Constantes.LIGADA_T_ACC_MIN, Constantes.LIGADA_T_ACC_MAX);
			super.getEscenario().setCelda(l1.getPos(), l1);
			super.getEscenario().setCelda(l2.getPos(), l2);
		}
	}

	private void deshacerseEn2() {
		// Busca un hueco alrededor
		ArrayList<Celda> vecinos = this.getEscenario().get8Vecinos(this.getPos());		
		Iterator<Celda> i = vecinos.iterator();
		boolean encontradoVacio = false;
		Celda c = null;
		while (i.hasNext() && !encontradoVacio) {
			c = i.next();
			if (c.queSoy() == Celda.VACIA) {
				encontradoVacio = true;
			}
		}
		
		if (encontradoVacio) {			
			// El hueco está en la variable c, ahora crea dos nuevos Sustratos uno en la celda actual, otro en la vacia, para sustituir al Vinculo actual
			Escenario esc = super.getEscenario();
			Celda s1 = new Sustrato(esc, super.getPos());
			Celda s2 = new Sustrato(esc, c.getPos());
			
			esc.setCelda(s1.getPos(), s1);
			esc.setCelda(s2.getPos(), s2);			
		} else {
			// no puede deshacerse porque no hay sitio libre a su alrededor, el vínculo se "estresa" y aumenta la probabilidad de deshacerse
			this.p1AcMov = this.p1AcMov * 0.75;
			this.p2AcDeshacerse = 1.0 - (1.0 - this.p2AcDeshacerse) * 0.75;
		}
	}

	@Override
	public void dibujar(Graphics2D g) {		
		g.drawImage(super.getEscenario().getCacheSprites().getSprite("vinculo.png"),
				this.getPos().getColumna() * Constantes.TAMAGNO_PIXELS_CELDA,
				this.getPos().getFila() * Constantes.TAMAGNO_PIXELS_CELDA,
				super.getEscenario());
	}

	@Override
	public int queSoy() {
		return Celda.VINCULO;
	}
	
	@Override
	public String toString() {
		return "VINCULO: " + super.toString() + " Prob.mov:" + this.p1AcMov
				+ " Prov.deshacerse:" + (this.p2AcDeshacerse - this.p1AcMov)
				+ " Prov.ligarse:" + (1.0 - this.p2AcDeshacerse);
	}

}
