package utils.tratimagen.efectos;

import java.awt.image.BufferedImage;

import utils.tratimagen.utils.Histograma;

import com.jhlabs.image.Curve;
import com.jhlabs.image.CurvesFilter;

public class EfectoAutoCorreccionContraste extends Efecto {
	
	private final static float PERCENTIL_NORMAL = 0.025f; // Define el porcentaje mínimo de pixels de la
												// imagen corregida que serán negros o blancos.
												// Varía entre 0f y 0.5f. Si fuese más se solparían
												// Y sólo habría puntos negros o blancos. Aunque con 
												// imágenes en color puede haber efectos extraños.
												// Para el caso ModulacionEfect.normal. Para otros casos, más o menos.
	

	
	public static enum TiposCorreccion {DosPuntos, TresPuntos;
	
		public static TiposCorreccion aleatorio() {
			int op = (int) (Math.random() * 2);
			switch (op) {
			case 0:
				return TresPuntos;
			default:
			case 1:
				return DosPuntos;

			}

		}
	};

	private TiposCorreccion tipoCorreccion = TiposCorreccion.DosPuntos;
	
	
	public EfectoAutoCorreccionContraste(ModulacionEfecto tipoModulacion) {
		this(tipoModulacion, TiposCorreccion.DosPuntos);
	}
	
	public EfectoAutoCorreccionContraste(ModulacionEfecto tipoModulacion, TiposCorreccion tipo) {
		super(tipoModulacion);
		this.tipoCorreccion = tipo;
	}

	public EfectoAutoCorreccionContraste() {
		this(ModulacionEfecto.aleatorio(), TiposCorreccion.aleatorio());

	}

	@Override
	public BufferedImage aplicarEfecto(BufferedImage imOriginal) {
		if (imOriginal != null) {
			// Calcula el Histogram de la original
			Histograma h = new Histograma(imOriginal);

			int[] canales = { Histograma.RED, Histograma.GREEN, Histograma.BLUE };
			Curve[] curvas = new Curve[canales.length];

			for (int canal : canales) {
				float percentil;

				switch (super.tipoModulacion) {
				case suave:
					percentil = PERCENTIL_NORMAL / 2f;
					break;

				case fuerte:
					percentil = 3f * PERCENTIL_NORMAL;
					break;

				default:
				case normal:
					percentil = PERCENTIL_NORMAL;
				}

				int frecInferior = h.getFrecuency(canal, percentil);
				int frecSuperior = h.getFrecuency(canal, 1f - percentil);

				switch (super.tipoModulacion) {
				case suave:
					frecInferior = frecInferior >> 1;
					frecSuperior = (255 + frecSuperior) >> 1;
					break;

				default:
				case fuerte:
				case normal:
				}

				// Crea una curva para modificar el histograma para cada canal
				curvas[canal] = new Curve();
				curvas[canal].addKnot(frecInferior / 255f, 0.008f);
				if (this.tipoCorreccion == TiposCorreccion.TresPuntos) {
					// Añade un tercer punto en el centro
					float media = h.getMeanValue(canal);
					curvas[canal].addKnot(media / 255f, 0.5f);
				}
				curvas[canal].addKnot(frecSuperior / 255f, 0.992f);
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
		return "Efecto Autocorrección del contraste. Modulación " + super.tipoModulacion + ". Algoritmo: " + this.tipoCorreccion;
	}
	
}
