package utils.tratimagen.efectos;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Efecto {
	
	protected ModulacionEfecto tipoModulacion = ModulacionEfecto.normal;
	protected boolean aleatorioCadaAplicacion = false;
	
	public static enum ModulacionEfecto {suave, normal, fuerte;
		public static ModulacionEfecto aleatorio() {
			int op = (int)(Math.random() * 3);
			switch (op) {
			case 0:
				return suave;
			case 1:
				return fuerte;
				default:
			case 2:
				return normal;
				
			}
		}
	}

	
	public Efecto(ModulacionEfecto tipoModulacion) {
		this.tipoModulacion = tipoModulacion;
	}
	
	public abstract BufferedImage aplicarEfecto(BufferedImage imOriginal); // aplica el efecto dado a la imagen imOriginal
	
	public static Efecto[] getListadoEfectos() {
		ArrayList<Efecto> array = new ArrayList<Efecto>();
		// Añadir todos los efectos que se vayan implementando (excepto el Aleatorio)
		array.add(new EfectoBlancoYNegro());
		array.add(new EfectoHighlightColor());
		array.add(new EfectoAutoCorreccionContraste());
		array.add(new EfectoNinguno());
		array.add(new EfectoInvertirColor());
		array.add(new EfectoSepia());
		array.add(new EfectoDegradado());
		array.add(new EfectoTunelLente());
		array.add(new EfectoAberracionCromaticaLente());
		array.add(new EfectoDesenfoqueCircular());
		array.add(new EfectoDesenfoqueLineaRecta());
		array.add(new EfectoAclararImagen());
		array.add(new EfectoColorear());
		array.add(new EfectoVideoBajaCalidadDigitalizado());
		array.add(new EfectoBrochazosColor());		
		
		// Devuelve un array con todos los efectos
		Efecto[] ret = new Efecto[array.size()];
		ret = array.toArray(ret);
		return ret;
	}
	
	public abstract String toString();
	
}
