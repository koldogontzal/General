package utils.tratimagen.efectos;

import java.awt.image.BufferedImage;

import com.mortennobel.imagescaling.ResampleOp;

public class EfectoAberracionCromaticaLente extends Efecto {
	

	
	private boolean aleatorioCadaAplicacion = false;
	
	public EfectoAberracionCromaticaLente() {
		this(ModulacionEfecto.aleatorio());
		this.aleatorioCadaAplicacion = true;
	}
	
	public EfectoAberracionCromaticaLente(ModulacionEfecto modulacion) {
		super(modulacion);
	}

	@Override
	public BufferedImage aplicarEfecto(BufferedImage imOriginal) {
		
		if (this.aleatorioCadaAplicacion) {
			this.tipoModulacion = ModulacionEfecto.aleatorio();
		}

		float factor;
		switch (this.tipoModulacion) {
		case suave:
			factor = 0.002f;
			break;
		case fuerte:
			factor = 0.006f;
			break;
		default:
		case normal:
			factor = 0.004f;
		}
		
		int ancho = imOriginal.getWidth();
		int alto = imOriginal.getHeight();
		
		int factorAnchoUnLado = Math.max(1, (int)(ancho * factor));
		int factorAltoUnLado = Math.max(1, (int)(alto * factor));
		
		// La imagen azul es la original
		// La imagen verde es algo más grande (lo mardcado por factor)
		ResampleOp resampleOpG = new ResampleOp(ancho + 2 * factorAnchoUnLado, 
				alto + 2 * factorAltoUnLado);			
		BufferedImage modificadaG = resampleOpG.filter(imOriginal, null);
		
		// La imagen roja es la más grande
		ResampleOp resampleOpR = new ResampleOp(ancho + 4 * factorAnchoUnLado, 
				alto + 4 * factorAltoUnLado);			
		BufferedImage modificadaR = resampleOpR.filter(imOriginal, null);
		
		// Imagen nueva
		int factorAnchoDoble = 2 * factorAnchoUnLado;
		int factorAltoDoble = 2 * factorAltoUnLado;
		BufferedImage ret = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < ancho; x++) {
			for (int y = 0; y < alto; y++) {
				int r = modificadaR.getRGB(x + factorAnchoDoble, y + factorAltoDoble) & 0xFF0000;
				int g = modificadaG.getRGB(x + factorAnchoUnLado, y + factorAltoUnLado) & 0xFF00;
				int b = imOriginal.getRGB(x, y) & 0xFF;
				
				int rgb = r | g | b;
				ret.setRGB(x, y, rgb);
			}
		}
		
		return ret;
	}

	@Override
	public String toString() {
		return "Efecto Desplazamiento cromático. Modulación " + super.tipoModulacion;
	}

}
