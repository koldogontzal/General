package elementos;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

import constantes.Constantes;

import utils.Aleatorio;
import utils.Posicion;


public class Ligada extends Celda {
	
	private double pDes;

	public Ligada(Escenario esc, Posicion pos, int tiempoMin, int tiempoMax) {
		this(esc, pos, tiempoMin, tiempoMax, Constantes.LIGADA_P_DESHACERSE,
				Constantes.LIGADA_P_PERMANECER_IGUAL);
	}

	public Ligada(Escenario esc, Posicion pos, int tiempoMin, int tiempoMax, double pdes, double pper) {
		super(esc, pos, Aleatorio.valor(tiempoMin, tiempoMax));
		
		this.pDes = pdes / (pdes + pper);
	}

	@Override
	public void actuar() {
		boolean momento = super.pasarTiempo();
		if (momento) {
			// Hay que actuar, según las propabilidades definidas
			double p = Math.random();
			if (p < this.pDes) {
				// Intenta deshacerse en 2 Vinculos
				this.deshacerseEn2();
			} 
		}
	}

	private void deshacerseEn2() {
		// Busca otra ligada alrededor
		ArrayList<Celda> vecinos = this.getEscenario().get4Vecinos(this.getPos());		
		Iterator<Celda> i = vecinos.iterator();
		boolean encontradoLigada = false;
		Celda c = null;
		while (i.hasNext() && !encontradoLigada) {
			c = i.next();
			if (c.queSoy() == Celda.LIGADA) {
				encontradoLigada = true;
			}
		}
		// Si ha encontrado en c otra ligada alrededor, desliga ambas
		// Si no, solo desliga la actual, que es ya la ultima
		Escenario esc = super.getEscenario();
		if (encontradoLigada) {
			Celda v2 = new Vinculo(esc, c.getPos(), 
					Constantes.VINCULO_T_ACC_MIN, Constantes.VINCULO_T_ACC_MAX);

			esc.setCelda(v2.getPos(), v2);			
		} 
		Celda v1 = new Vinculo(esc, super.getPos(), 
				Constantes.VINCULO_T_ACC_MIN, Constantes.VINCULO_T_ACC_MAX);
		esc.setCelda(v1.getPos(), v1);
	}

	@Override
	public void dibujar(Graphics2D g) {
		g.drawImage(super.getEscenario().getCacheSprites().getSprite("ligada.png"),
				this.getPos().getColumna() * Constantes.TAMAGNO_PIXELS_CELDA,
				this.getPos().getFila() * Constantes.TAMAGNO_PIXELS_CELDA,
				super.getEscenario());
	}

	@Override
	public int queSoy() {
		return Celda.LIGADA;
	}

}
