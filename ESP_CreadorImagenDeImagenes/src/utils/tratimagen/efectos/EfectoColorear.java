package utils.tratimagen.efectos;

import java.awt.Color;
import java.awt.image.BufferedImage;

import utils.ColorMod;
import utils.EsquemaDegradadoColor;
import utils.EsquemaDegradadoColor.ModoDegradado;

public class EfectoColorear extends Efecto {
	
	public enum Colorizador {
		rojo,
		naranja,
		amarillo,
		verde,
		azul,
		fucsia,
		morado;
		
		public static Colorizador aleatorio() {
			int rnd = (int)(Math.random() * 7);
			switch (rnd) {
			case 0:
				return rojo;
			case 1:
				return naranja;
			case 2:
				return amarillo;
			case 3:
				return verde;
			case 4:
				return azul;
			case 5:
				return fucsia;
			default:
			case 6:
				return morado;
			}
		}
		
		public Color getColor() {
			switch (this) {
			default:
			case rojo:
				return new Color(255, 77, 77);//ColorMod.getColorFromHSV(0, 65, 85);//
			case naranja:
				return new Color(255, 143, 57);//ColorMod.getColorFromHSV(30, 65, 85);//
			case amarillo:
				return new Color(235, 250, 0);//ColorMod.getColorFromHSV(60, 65, 85);//
			case verde:
				return new Color(99, 169, 52);//ColorMod.getColorFromHSV(120, 65, 85);//
			case azul:
				return new Color(75, 168, 255);//ColorMod.getColorFromHSV(210, 65, 85);//
			case fucsia:
				return new Color(255, 20, 140);//ColorMod.getColorFromHSV(300, 65, 85);//
			case morado:
				return new Color(155, 20, 255);//ColorMod.getColorFromHSV(260, 65, 85);//
			}
		}
	}
	
	private Colorizador colorizador;

	public EfectoColorear() {
		super(ModulacionEfecto.aleatorio());
		super.aleatorioCadaAplicacion = true;
	}
	
	public EfectoColorear(Colorizador col) {
		super(ModulacionEfecto.aleatorio());
		this.colorizador = col;
	}

	@Override
	public BufferedImage aplicarEfecto(BufferedImage imOriginal) {
		if (super.aleatorioCadaAplicacion) {
			this.colorizador = Colorizador.aleatorio();
		}
		
		BufferedImage ret = imOriginal;
		
		Efecto ef = new EfectoAutoCorreccionContraste();
		ret = ef.aplicarEfecto(ret);
		
		ef = new EfectoAclararImagen(ModulacionEfecto.fuerte);
		ret = ef.aplicarEfecto(ret);
		
		ef = new EfectoTunelLente(ModulacionEfecto.suave);
		ret = ef.aplicarEfecto(ret);
		
		ColorMod cFin = ColorMod.getColorProporcionalRGB(Color.white, this.colorizador.getColor(), 0.4f);
		ColorMod cIntermedio = new ColorMod(this.colorizador.getColor());
		ColorMod cIni = ColorMod.getColorProporcionalRGB(Color.black, this.colorizador.getColor(), 0.33f);
		EsquemaDegradadoColor esquema = new EsquemaDegradadoColor(cIni, cFin, ModoDegradado.RGB);
		float lumCIni = cIni.getLuminance();
		float lumCIntermedio = cIntermedio.getLuminance();
		float lumCFin = cFin.getLuminance();
		float posCIntermedio = (lumCIntermedio - lumCIni) / (lumCFin - lumCIni);
		esquema.añadirColor(posCIntermedio, cIntermedio);
		ef = new EfectoDegradado(esquema);
		ret = ef.aplicarEfecto(ret);

		return ret;
	}

	@Override
	public String toString() {
		return "Efecto Colorizar. Color " + this.colorizador + ".";
	}

}
