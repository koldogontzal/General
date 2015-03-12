package utils.tratimagen.efectos;

import java.awt.image.BufferedImage;

public class EfectoNinguno extends Efecto {

	public EfectoNinguno() {
		super(ModulacionEfecto.normal);
	}

	@Override
	public BufferedImage aplicarEfecto(BufferedImage imOriginal) {
		return imOriginal;
	}

	@Override
	public String toString() {
		return "Sin efecto";
	}

}
