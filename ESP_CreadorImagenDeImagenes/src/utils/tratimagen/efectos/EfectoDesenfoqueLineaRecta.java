package utils.tratimagen.efectos;

import java.awt.image.BufferedImage;

import utils.tratimagen.utils.FuncionDegradadoSencillo;
import utils.tratimagen.utils.geometria.Linea;
import utils.tratimagen.utils.geometria.Punto;
import utils.tratimagen.utils.FuncionDegradadoSencillo.TipoDegradado;

public class EfectoDesenfoqueLineaRecta extends EfectoDesenfoque {
	
	private float x0, y0, x1, y1;	// son todos valores relativos entre 0.0 y 1.0
	private float distanciaSinDesenfocar; // son todos valores relativos entre 0.0 y 1.0
	
	public EfectoDesenfoqueLineaRecta() {
		super(ModulacionEfecto.aleatorio());
		super.aleatorioCadaAplicacion = true;
	}
	
	public EfectoDesenfoqueLineaRecta(float x0, float y0, float x1, float y1, float distanciaSinDesenfocar, ModulacionEfecto tipo) {
		super(tipo);
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
		
		this.distanciaSinDesenfocar = distanciaSinDesenfocar;
		
	}

	@Override
	public BufferedImage aplicarEfecto(BufferedImage imOriginal) {
		if (super.aleatorioCadaAplicacion) {			
			super.tipoModulacion = ModulacionEfecto.aleatorio();
			
			this.x0 = (float)Math.random();
			this.x1 = (float)Math.random();
			this.y0 = (float)Math.random();
			this.y1 = (float)Math.random();
		}

		// Crea la máscara
		TipoDegradado tipoDeg;
		float valExterior, valInterior;
		valInterior = 0.65f;
		switch (super.tipoModulacion) {
		case suave:
			valExterior = 1.5f;
			this.distanciaSinDesenfocar = 0.65f;
			tipoDeg = TipoDegradado.brusco;
			break;
		case fuerte:
			valExterior = 5f;
			this.distanciaSinDesenfocar = 0.4f;
			tipoDeg = TipoDegradado.suave;
			break;
		default:
		case normal:
			valExterior = 2.25f;
			this.distanciaSinDesenfocar = 0.55f;
			tipoDeg = TipoDegradado.normal;
			break;
		}
		
		super.inciarListaMatrices(valInterior, valExterior);
		
		float posCambio = imOriginal.getWidth() * this.distanciaSinDesenfocar;
		int X0 = (int)(imOriginal.getWidth() * this.x0);
		int Y0 = (int)(imOriginal.getHeight() * this.y0);
		int X1 = (int)(imOriginal.getWidth() * this.x1);
		int Y1 = (int)(imOriginal.getHeight() * this.y1);
		
		Punto p0 = new Punto(X0, Y0);
		Punto p1 = new Punto(X1, Y1);
		Linea l = new Linea(p0, p1);	
		FuncionDegradadoSencillo degradado = new FuncionDegradadoSencillo(valInterior, valExterior, posCambio, tipoDeg);
		
		// Calcula la imagen resultante final
		BufferedImage ret = new BufferedImage(imOriginal.getWidth(), imOriginal.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < imOriginal.getWidth(); x++) {
			for (int y = 0; y < imOriginal.getHeight(); y++) {
				float sigma = degradado.getValue(l.distanceTo(x, y));		
				int argb = super.lista.convolucionPorcionImagen(sigma, imOriginal, x, y);
				ret.setRGB(x, y, argb);
			}
		}

		return ret;
	}


	
	@Override
	public String toString() {
		return "Efecto Desenfoque Linea Recta. Modulación " + super.tipoModulacion + ".";
	}

}
