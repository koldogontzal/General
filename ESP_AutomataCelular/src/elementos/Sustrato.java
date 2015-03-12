package elementos;

import java.awt.Graphics2D;

import constantes.Constantes;
import utils.Aleatorio;
import utils.Posicion;

public class Sustrato extends Celda {
	
	public Sustrato(Escenario esc, Posicion pos) {
		this(esc, pos, Constantes.SUSTRATO_T_ACC_MIN, Constantes.SUSTRATO_T_ACC_MAX);
	}

	public Sustrato(Escenario esc, Posicion pos, int tiempoMin, int tiempoMax) {
		super(esc, pos, Aleatorio.valor(tiempoMin, tiempoMax));
	}

	@Override
	public void actuar() {
		boolean momento = super.pasarTiempo();
		if (momento) {
			// Hay que actuar que en este caso es siempre moverse
			super.mover();
		}
	}

	@Override
	public void dibujar(Graphics2D g) {		
		g.drawImage(super.getEscenario().getCacheSprites().getSprite("sustrato.png"),
				this.getPos().getColumna() * Constantes.TAMAGNO_PIXELS_CELDA,
				this.getPos().getFila() * Constantes.TAMAGNO_PIXELS_CELDA,
				super.getEscenario());
	}

	@Override
	public int queSoy() {
		return Celda.SUSTRATO;
	}

}
