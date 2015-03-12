package elementos;

import java.awt.Graphics2D;

import constantes.Constantes;
import utils.Posicion;

public class Vacia extends Celda {

	public Vacia(Escenario esc, Posicion pos) {
		super(esc, pos, 1000);
	}

	@Override
	public void actuar() {}

	@Override
	public void dibujar(Graphics2D g) {		
		g.drawImage(super.getEscenario().getCacheSprites().getSprite("vacia.png"),
				this.getPos().getColumna() * Constantes.TAMAGNO_PIXELS_CELDA,
				this.getPos().getFila() * Constantes.TAMAGNO_PIXELS_CELDA,
				super.getEscenario());
	}

	@Override
	public int queSoy() {
		return Celda.VACIA;
	}

	@Override
	public String toString() {
		return "VACIA: " + super.toString();
	}
}
