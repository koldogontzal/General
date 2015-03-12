package utils;

import main.TimeStamp;

public class TimeStampParserYYYY_MM_DD extends TimeStampParser {

	private static final String CADENA_COMP = GRUPO_NUMERO_CARACTER_DESCONOCIDO
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO + "[^0-9]"
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO + "[^0-9]"
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO;

	protected TimeStampParserYYYY_MM_DD(String s) {
		super(s);
	}

	@Override
	protected boolean esStringConforme() {
		/*
		 * Formato posible: AAAA_MM_DD (10 caracteres) (Definido en CADENA_COMP)
		 * 
		 * en vez de cifras, puede usarse el caracter 'x' para indicar un dato
		 * indefinido. Por ejemplo "1978-03-xx" indicar� alg�n d�a del mes de
		 * marzo de 1978.
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
			ret = super.s.substring(5, 7);
			break;
		case TimeStamp.DAY_OF_MONTH:
			ret = super.s.substring(8, 10);
			break;
		case TimeStamp.HOUR_OF_DAY:
		case TimeStamp.MINUTE:
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
		return 10;
	}

}
