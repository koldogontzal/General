package utils.tratimagen.efectos;

import java.awt.image.BufferedImage;

import utils.tratimagen.filtros.MaskFilter;
import utils.tratimagen.utils.FuncionDegradadoSencillo.TipoDegradado;
import utils.tratimagen.utils.MascaraDeImagenCircular;

public class EfectoTunelLente extends Efecto {
	
	private MascaraDeImagenCircular mascara;
	
	private boolean aleatorioCadaAplicacion = false;
	
	public EfectoTunelLente() {
		this(ModulacionEfecto.aleatorio());
		this.aleatorioCadaAplicacion = true;
	}
	
	public EfectoTunelLente(ModulacionEfecto modulacion) {
		super(modulacion);
	}

	@Override
	public BufferedImage aplicarEfecto(BufferedImage imOriginal) {
		int ancho = imOriginal.getWidth();
		int alto = imOriginal.getHeight();
		int centroX = ancho >> 1;
		int centroY = alto >> 1;
		int radio = (int)(Math.max(ancho,  alto) * 0.47);
		int valMin;
		int valMax = 255;
		TipoDegradado tipo;
		if (this.aleatorioCadaAplicacion) {
			this.tipoModulacion = ModulacionEfecto.aleatorio();
		}
		
		switch (this.tipoModulacion) {
		case suave:
			valMin = 200;
			tipo = TipoDegradado.brusco;
			break;
		case fuerte:
			valMin = 64;
			tipo = TipoDegradado.suave;
			//radio = (int)(radio * 1.01);
			break;

		default:
		case normal:
			valMin = 128;
			tipo = TipoDegradado.normal;
		}
		
		this.mascara = new MascaraDeImagenCircular(ancho, alto, centroX, centroY, radio, valMin, valMax, tipo);
		
		MaskFilter filtro = new MaskFilter(this.mascara);
		return filtro.filter(imOriginal, null);
	}

	@Override
	public String toString() {
		return "Efecto Tunel de lente. Modulación " + super.tipoModulacion;
	}

}
