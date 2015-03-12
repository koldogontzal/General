package utils;

import main.TimeStamp;

public class TimeStampParserYYYYMMDDhhmm extends TimeStampParser {

	private static final String CADENA_COMP = GRUPO_NUMERO_CARACTER_DESCONOCIDO
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO;

	protected TimeStampParserYYYYMMDDhhmm(String s) {
		super(s);
	}

	@Override
	protected boolean esStringConforme() {
		/*
		 * Formato posible: AAAAMMDDhhmm (12 caracteres) (Definido en
		 * CADENA_COMP)
		 * 
		 * en vez de cifras, puede usarse el caracter 'x' para indicar un dato
		 * indefinido. Por ejemplo "197803xx12xx" indicará algún día del mes de
		 * marzo de 1978 sobre las 12 del mediodía.
		 */

		if (super.s.length() == this.getLengthMarcaDeTiempo()) {
			return super.s.matches(CADENA_COMP);

		} else {
			return false;
		}
	}

	@Override
	protected String getStringCifra(byte identificadorCifra) {
		String ret = null;
		switch (identificadorCifra) {
		case TimeStamp.YEAR:
			ret = super.s.substring(0, 4);
			break;
		case TimeStamp.MONTH:
			ret = super.s.substring(4, 6);
			break;
		case TimeStamp.DAY_OF_MONTH:
			ret = super.s.substring(6, 8);
			break;
		case TimeStamp.HOUR_OF_DAY:
			ret = super.s.substring(8, 10);
			break;
		case TimeStamp.MINUTE:
			ret = super.s.substring(10, 12);
			break;
		case TimeStamp.SECOND:
			ret = "" + TimeStamp.CHAR_UNKNOWN_FIGURE
					+ TimeStamp.CHAR_UNKNOWN_FIGURE;
			break;
		}
		return ret;
	}

	@Override
	protected String getStringRegex() {
		return CADENA_COMP;
	}

	@Override
	protected int getLengthMarcaDeTiempo() {
		return 12;
	}

}
