package utils;

import main.TimeStamp;

public class TimeStampParserYYYY_MM_DD_hh_mm_ss extends TimeStampParser {

	private static final String CADENA_COMP = GRUPO_NUMERO_CARACTER_DESCONOCIDO
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO + "[^0-9]"
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO + "[^0-9]"
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO + "[^0-9]"
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO + "[^0-9]"
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO + "[^0-9]"
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO;

	protected TimeStampParserYYYY_MM_DD_hh_mm_ss(String s) {
		super(s);
	}

	@Override
	protected boolean esStringConforme() {
		/*
		 * Formato posible: AAAA_MM_DD_hh_mm_ss (19 caracteres) (Definido en
		 * CADENA_COMP)
		 * 
		 * en vez de cifras, puede usarse el caracter 'x' para indicar un dato
		 * indefinido. Por ejemplo "1978-03-xx 12.xx.xx" indicará algún día del
		 * mes de marzo de 1978 sobre las 12 del mediodía.
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
			ret = super.s.substring(11, 13);
			break;
		case TimeStamp.MINUTE:
			ret = super.s.substring(14, 16);
			break;
		case TimeStamp.SECOND:
			ret = super.s.substring(17, 19);
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
		return 19;
	}

}
