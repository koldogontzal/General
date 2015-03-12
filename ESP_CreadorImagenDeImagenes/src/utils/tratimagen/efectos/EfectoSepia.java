package utils.tratimagen.efectos;

import java.awt.image.BufferedImage;

import utils.tratimagen.utils.Histograma;

import com.jhlabs.image.Curve;
import com.jhlabs.image.CurvesFilter;

public class EfectoSepia extends Efecto {

	private CurvesFilter filtro;
	private boolean aleatorioCadaAplicacion = false;
	
	public EfectoSepia() {
		this(ModulacionEfecto.aleatorio());
		this.aleatorioCadaAplicacion = true;
	}

	public EfectoSepia(ModulacionEfecto tipoModulacion) {
		super(tipoModulacion);

		// Calcula el filtro
		this.calculaFiltro();
	}

	@Override
	public BufferedImage aplicarEfecto(BufferedImage imOriginal) {
		if (imOriginal != null) {
			// Primer paso, transforma en ByN
			EfectoBlancoYNegro eff = new EfectoBlancoYNegro();
			BufferedImage ret = eff.aplicarEfecto(imOriginal);

			if (this.aleatorioCadaAplicacion) { // recalcula el filtro para que si es en modo aleatorio, se distinto en cada aplicacion
				this.calculaFiltro();
			}
			
			// Devuelve la imagen corregida
			ret = this.filtro.filter(ret, null);
			return ret;
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		return "Filtro Sepia. Modulación " + super.tipoModulacion;
	}

	private void calculaFiltro( ){
		// Calcula el filtro para el efecto sepia modulado, modificando las
		// curvas RGB
		Curve[] curvas = new Curve[3];
		float subidaRojoNormal = 0.05f; // Sirve como base para el resto
												// de modulaciones
		switch (super.tipoModulacion) {
		case suave:
			float subidaRojoSuave = subidaRojoNormal / 2f;
			curvas[Histograma.RED] = new Curve();
			curvas[Histograma.RED].addKnot(0.5f, 0.5f + subidaRojoSuave);
			curvas[Histograma.GREEN] = new Curve();
			curvas[Histograma.BLUE] = new Curve();
			curvas[Histograma.BLUE].addKnot(0.5f, 0.5f - subidaRojoSuave);
			break;

		case fuerte:
			float subidaRojoFuerte = subidaRojoNormal * 2f;
			curvas[Histograma.RED] = new Curve();
			curvas[Histograma.RED].addKnot(0.5f, 0.5f + subidaRojoFuerte);
			curvas[Histograma.GREEN] = new Curve();
			curvas[Histograma.BLUE] = new Curve();
			curvas[Histograma.BLUE].addKnot(0.5f, 0.5f - subidaRojoFuerte);
			break;

		case normal:
			curvas[Histograma.RED] = new Curve();
			curvas[Histograma.RED].addKnot(0.5f, 0.5f + subidaRojoNormal);
			curvas[Histograma.GREEN] = new Curve();
			curvas[Histograma.BLUE] = new Curve();
			curvas[Histograma.BLUE].addKnot(0.5f, 0.5f - subidaRojoNormal);
		}

		// Crea el filtro de curvas
		this.filtro = new CurvesFilter();
		this.filtro.setCurves(curvas);
		
	}
}
