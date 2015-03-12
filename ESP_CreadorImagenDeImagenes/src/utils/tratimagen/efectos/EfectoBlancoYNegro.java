package utils.tratimagen.efectos;

import java.awt.image.BufferedImage;

import com.jhlabs.image.GrayscaleFilter;

public class EfectoBlancoYNegro extends Efecto {

	public EfectoBlancoYNegro() {
		super(ModulacionEfecto.normal);
	}

	@Override
	public BufferedImage aplicarEfecto(BufferedImage imOriginal) {
		if (imOriginal != null) {
			GrayscaleFilter filtro = new GrayscaleFilter();
			return filtro.filter(imOriginal, null);
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		return "Efecto Blanco y Negro";
	}

}
