package utils.tratimagen.efectos;

import java.awt.image.BufferedImage;

import utils.tratimagen.utils.FuncionDegradadoSencillo;
import utils.tratimagen.utils.FuncionDegradadoSencillo.TipoDegradado;

public class EfectoDesenfoqueCircular extends EfectoDesenfoque {
	
	private float posXrel, posYrel, radioRel;
	
	public EfectoDesenfoqueCircular() {
		super(ModulacionEfecto.aleatorio());
		super.aleatorioCadaAplicacion = true;
	}
	
	public EfectoDesenfoqueCircular(float posXrelativa, float posYrelativa, float radioRelativo, ModulacionEfecto tipo) {
		super(tipo);
		this.posXrel = posXrelativa;
		this.posYrel = posYrelativa;
		this.radioRel = radioRelativo;
		
	}

	@Override
	public BufferedImage aplicarEfecto(BufferedImage imOriginal) {
		
		if (super.aleatorioCadaAplicacion) {
			this.posXrel = (0.75f + (float)Math.random()) / 2.5f;
			this.posYrel = (0.75f + (float)Math.random()) / 2.5f;
			
			super.tipoModulacion = ModulacionEfecto.aleatorio();
		}
		
		// Crea la mascara
		TipoDegradado tipoDeg;
		float valExterior, valInterior;
		valInterior = 0.65f;
		switch (super.tipoModulacion) {
		case suave:
			valExterior = 1.5f;
			this.radioRel = 0.65f;
			tipoDeg = TipoDegradado.brusco;
			break;
		case fuerte:
			valExterior = 5f;
			this.radioRel = 0.4f;
			tipoDeg = TipoDegradado.suave;
			break;
		default:
		case normal:
			valExterior = 2.25f;
			this.radioRel = 0.55f;
			tipoDeg = TipoDegradado.normal;
			break;
		}
		
		super.inciarListaMatrices(valInterior, valExterior);
		
		float posCambio = imOriginal.getWidth() * this.radioRel;
		float centroX = imOriginal.getWidth() * this.posXrel;
		float centroY = imOriginal.getHeight() * this.posYrel;
		
		FuncionDegradadoSencillo degradado = new FuncionDegradadoSencillo(valInterior, valExterior, posCambio, tipoDeg);
		
		// Calcula la imagen resultante final
		BufferedImage ret = new BufferedImage(imOriginal.getWidth(), imOriginal.getHeight(), BufferedImage.TYPE_INT_ARGB);
		

		for (int x = 0; x < imOriginal.getWidth(); x++) {
			float varX = x - centroX;
			for (int y = 0; y < imOriginal.getHeight(); y++) {
				float varY = y - centroY;
				float dist = (float)Math.sqrt(varX * varX + varY * varY);
				float sigma = degradado.getValue(dist);		
				int argb = super.lista.convolucionPorcionImagen(sigma, imOriginal, x, y);
				ret.setRGB(x, y, argb);
			}				
		}
		return ret;
	}

	@Override
	public String toString() {
		return "Efecto Desenfoque Circular. Modulación " + super.tipoModulacion +". Centro relativo: (" 
				+ this.posXrel + ", " + this.posYrel + ") Radio relativo: " + this.radioRel;
	}

}
