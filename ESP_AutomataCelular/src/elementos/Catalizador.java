package elementos;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

import constantes.Constantes;

import utils.Aleatorio;
import utils.Posicion;

public class Catalizador extends Celda {
	
	private double pMov;
	private double pProd;

	public Catalizador(Escenario esc, Posicion pos, int tiempoMin, int tiempoMax) {	
		this(esc, pos, tiempoMin, tiempoMax, 
				Constantes.CATALIZADOR_P_MOV, Constantes.CATALIZADOR_P_PROD);
	}
	
	public Catalizador(Escenario esc, Posicion pos, int tiempoMin, int tiempoMax, double pMov, double pProd) {	
		super(esc, pos, Aleatorio.valor(tiempoMin, tiempoMax));
		
		double pTotal = pMov + pProd;
		this.pMov = pMov / pTotal;
		this.pProd = pProd / pTotal;
	}

	@Override
	public void actuar() {
		boolean momento = super.pasarTiempo();
		if (momento) {
			// Hay que actuar, según las propabilidades definidas
			double p = Math.random();
			if (p < pMov) {
				super.mover();
			} else {
				this.producir();
			}
		}
	}

	private void producir() {
		Celda s1 = null;
		Celda s2 = null;
		
		ArrayList<Celda> vecinos = super.getEscenario().get8Vecinos(super.getPos());
		
		Iterator<Celda> i = vecinos.iterator();
		boolean encontradoSustratos = false;
		while (i.hasNext() && !encontradoSustratos) {
			Celda c = i.next();
			if (c.queSoy() == Celda.SUSTRATO) {
				if (s1 == null) {
					s1 = c;
				} else if (s2 == null) {
					s2 = c;
					encontradoSustratos = true;
				}
			}
		}
		
		if (encontradoSustratos) {
			Escenario escenario = super.getEscenario();
			
			Celda c1 = new Vinculo(escenario, s1.getPos(), 
					Constantes.VINCULO_T_ACC_MIN, Constantes.VINCULO_T_ACC_MAX);
			Celda c2 = new Vacia(escenario, s2.getPos());
			
			escenario.setCelda(s1.getPos(), c1);
			escenario.setCelda(s2.getPos(), c2);
		}
	}

	@Override
	public int queSoy() {
		return Celda.CATALIZADOR;
	}

	@Override
	public String toString() {
		return "CATALIZADOR: " + super.toString() + "Prob.Mov:" + this.pMov + " Prob.Prod:" + this.pProd;
	}

	@Override
	public void dibujar(Graphics2D g) {		
		g.drawImage(super.getEscenario().getCacheSprites().getSprite("catalizador.png"),
				this.getPos().getColumna() * Constantes.TAMAGNO_PIXELS_CELDA,
				this.getPos().getFila() * Constantes.TAMAGNO_PIXELS_CELDA,
				super.getEscenario());
	}
}
