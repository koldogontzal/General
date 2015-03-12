package utils.tratimagen.efectos;

import java.awt.image.BufferedImage;

import utils.tratimagen.utils.Histograma;

import com.jhlabs.image.Curve;
import com.jhlabs.image.CurvesFilter;

public class EfectoAclararImagen extends Efecto {
	
		
	public EfectoAclararImagen(ModulacionEfecto tipoModulacion) {
		super(tipoModulacion);
	}

	public EfectoAclararImagen() {
		this(ModulacionEfecto.aleatorio());
		super.aleatorioCadaAplicacion = true;
	}

	@Override
	public BufferedImage aplicarEfecto(BufferedImage imOriginal) {
		if (super.aleatorioCadaAplicacion) {
			super.tipoModulacion = ModulacionEfecto.aleatorio();
		}
		
		if (imOriginal != null) {

			int[] canales = { Histograma.RED, Histograma.GREEN, Histograma.BLUE };
			Curve[] curvas = new Curve[canales.length];

			for (int canal : canales) {

				float factorAclarado;
				switch (super.tipoModulacion) {
				case suave:
					factorAclarado = 0.6f;
					break;
				case fuerte:
					factorAclarado = 0.9f;
					break;
				default:
				case normal:
					factorAclarado = 0.75f;
				}

				// Crea una curva para modificar el histograma para cada canal
				curvas[canal] = new Curve();
				curvas[canal].addKnot(0.5f, factorAclarado);
				if (super.tipoModulacion == ModulacionEfecto.fuerte) {
					curvas[canal].addKnot(0.01f, 0.15f);
				}
			}

			// Crea el filtro de curvas
			CurvesFilter filtro = new CurvesFilter();
			filtro.setCurves(curvas);

			// Devuelve la imagen corregida
			BufferedImage ret = filtro.filter(imOriginal, null);
			return ret;
		} else {
			return null;
		}
	}
	
	
	@Override
	public String toString() {
		return "Efecto Aclarado de Imagen. Modulación " + super.tipoModulacion + ". ";
	}
	
}
