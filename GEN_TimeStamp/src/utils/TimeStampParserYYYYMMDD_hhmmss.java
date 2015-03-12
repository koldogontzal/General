package utils;

import main.TimeStamp;

public class TimeStampParserYYYYMMDD_hhmmss extends TimeStampParser {

	private static final String CADENA_COMP = GRUPO_NUMERO_CARACTER_DESCONOCIDO
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO + "[^0-9]"
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO
			+ GRUPO_NUMERO_CARACTER_DESCONOCIDO;

	protected TimeStampParserYYYYMMDD_hhmmss(String s) {
		super(s);
	}

	@Override
	protected boolean esStringConforme() {
		/*
		 * Formato posible: AAAAMMDD_hhmmss (15 caracteres, el caracter
		 * separador puede ser cualquiera menos un número) (Definido en
		 * CADENA_COMP)
		 * 
		 * en vez de cifras, puede usarse el caracter 'x' para indicar un dato
		 * indefinido. Por ejemplo "197803xx_12xxxx" indicará algún día del mes
		 * de marzo de 1978 sobre las 12 del mediodía.
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
			ret = super.s.substring(9, 11);
			break;
		case TimeStamp.MINUTE:
			ret = super.s.substring(11, 13);
			break;
		case TimeStamp.SECOND:
			ret = super.s.substring(13, 15);
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
		return 15;
	}

}
