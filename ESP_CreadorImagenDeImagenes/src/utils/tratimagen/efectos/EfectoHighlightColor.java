package utils.tratimagen.efectos;

import java.awt.Color;
import java.awt.image.BufferedImage;

import utils.ColorMod;
import utils.tratimagen.filtros.HighlightColorFilter;

public class EfectoHighlightColor extends Efecto {	

	private float hue;
	
	private boolean aleatorioCadaAplicacion = false;
	
	public EfectoHighlightColor(ModulacionEfecto tipoModulacion, Color c) {
		super(tipoModulacion);
		this.hue = new ColorMod(c).getHue();
	}
	
	public EfectoHighlightColor(ModulacionEfecto tipoMod) {
		this(tipoMod, ColorMod.aleatorio());
		this.aleatorioCadaAplicacion = true;
	}
	
	public EfectoHighlightColor(Color c) {
		this(ModulacionEfecto.normal, c);
	}
	
	public EfectoHighlightColor() {
		this(ColorMod.aleatorio());		
		this.aleatorioCadaAplicacion = true;
	}
	

	@Override
	public BufferedImage aplicarEfecto(BufferedImage imOriginal) {
		if (imOriginal != null) {
			
			if (this.aleatorioCadaAplicacion) {
				ColorMod c = ColorMod.aleatorio();
				this.hue = c.getHue();
			}
			
			HighlightColorFilter filtro;

			switch (super.tipoModulacion) {
			case suave:
				filtro = new HighlightColorFilter(this.hue, 0.40f);
				break;
			case fuerte:
				filtro = new HighlightColorFilter(this.hue, 0.10f);
				break;
			default:
			case normal:
				filtro = new HighlightColorFilter(this.hue, 0.20f);
				break;
			}
			BufferedImage ret = filtro.filter(imOriginal, null);

			return ret;
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		return "Efecto Resaltado de color. Modulación " + super.tipoModulacion + ". Hue del color: " + this.hue + "º";
	}



	
}
