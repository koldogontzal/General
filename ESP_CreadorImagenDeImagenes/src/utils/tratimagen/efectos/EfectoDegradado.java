package utils.tratimagen.efectos;

import java.awt.image.BufferedImage;

import utils.EsquemaDegradadoColor;
import utils.tratimagen.filtros.EsquemaDegradadoFilter;

public class EfectoDegradado extends Efecto {
	
	private EsquemaDegradadoColor esquema;
	private boolean aleatorioCadaAplicacion = false;
	
	
	public EfectoDegradado() {
		this(EsquemaDegradadoColor.aleatorio());
		this.aleatorioCadaAplicacion = true;
	}
	
	public EfectoDegradado(EsquemaDegradadoColor esquema) {
		super(ModulacionEfecto.normal);
		
		this.esquema = esquema;
	}

	@Override
	public BufferedImage aplicarEfecto(BufferedImage imOriginal) {
		if (imOriginal != null) {
			if (aleatorioCadaAplicacion) {
				// Recalcula un Degradado nuevo para cada aplicacion del efecto
				this.esquema = EsquemaDegradadoColor.aleatorio();
			}
			EsquemaDegradadoFilter filtro = new EsquemaDegradadoFilter(this.esquema);
			return filtro.filter(imOriginal, null);
		} else {
			return null;
		}
	}

	@Override
	public String toString() {
		return "Efecto degradado de color. Degradado: " + this.esquema;
	}

}
