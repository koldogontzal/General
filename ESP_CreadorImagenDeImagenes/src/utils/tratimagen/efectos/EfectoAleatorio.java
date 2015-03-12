package utils.tratimagen.efectos;

import java.awt.image.BufferedImage;

public class EfectoAleatorio extends Efecto {
	
	private String ultimoFiltro = "";
	private int numEfectos;

	public EfectoAleatorio() {
		this(1);
	}
	
	public EfectoAleatorio(int numEfectos) {
		super(ModulacionEfecto.aleatorio());
		this.numEfectos = numEfectos;
	}

	@Override
	public BufferedImage aplicarEfecto(BufferedImage imOriginal) {
		this.ultimoFiltro = "";
		BufferedImage ret = imOriginal;
		for (int i = 0; i < this.numEfectos; i++) {
			Efecto[] lista = Efecto.getListadoEfectos();
			int rnd = (int) (Math.random() * lista.length);
			this.ultimoFiltro = this.ultimoFiltro + lista[rnd];
			ret = lista[rnd].aplicarEfecto(ret);
		}
		return ret;
	}

	@Override
	public String toString() {
		return "Efecto Aleatorio. " + this.ultimoFiltro;
	}

}
