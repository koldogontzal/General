package utils.tratimagen.efectos;

import java.awt.image.BufferedImage;

import utils.tratimagen.filtros.InvertirColorFilter;

public class EfectoInvertirColor extends Efecto {

	public EfectoInvertirColor() {
		super(ModulacionEfecto.normal);
	}

	@Override
	public BufferedImage aplicarEfecto(BufferedImage imOriginal) {
		if (imOriginal != null) {
			InvertirColorFilter filtro = new InvertirColorFilter();
			BufferedImage ret = filtro.filter(imOriginal, null);
			return ret;
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		return "Efecto Invertir Color";
	}

}
