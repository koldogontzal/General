package utils;

import java.util.GregorianCalendar;

import main.IllegalTimeStampException;
import main.TimeStamp;

public abstract class TimeStampParser {

	protected String s;

	protected static final String GRUPO_NUMERO_CARACTER_DESCONOCIDO = "[0-9"
			+ TimeStamp.CHAR_UNKNOWN_FIGURE + "]";

	protected TimeStampParser(String s) {
		this.s = s;
	}

	protected abstract boolean esStringConforme();

	protected abstract String getStringCifra(byte identificadorCifra);

	protected abstract String getStringRegex();

	protected abstract int getLengthMarcaDeTiempo();

	protected boolean contieneStringConforme() {
		return this.s.matches("(.*)" + getStringRegex() + "(.*)");
	}

	protected boolean empiezaPorStringConforme() {
		return this.s.matches(getStringRegex() + "(.*)");
	}

	public static TimeStamp fromString(String s)
			throws IllegalTimeStampException {

		boolean encontrado = true;
		TimeStampParser p;
		// Prueba formatos del Parser del más completo al más sencillo
		// Primero: AAAA_MM_DD_hh_mm_ss
		p = new TimeStampParserYYYY_MM_DD_hh_mm_ss(s);
		if (!p.esStringConforme()) {
			// Si no, prueba AAAAMMDD_hhmmss
			p = new TimeStampParserYYYYMMDD_hhmmss(s);
			if (!p.esStringConforme()) {
				// Si no, prueba AAAAMMDDhhmmss
				p = new TimeStampParserYYYYMMDDhhmmss(s);
				if (!p.esStringConforme()) {
					// Si no, prueba AAAAMMDD_hhmm
					p = new TimeStampParserYYYYMMDD_hhmm(s);
					if (!p.esStringConforme()) {
						// Si no, prueba AAAAMMDDhhmm
						p = new TimeStampParserYYYYMMDDhhmm(s);
						if (!p.esStringConforme()) {
							// Si no, prueba AAAA_MM_DD
							p = new TimeStampParserYYYY_MM_DD(s);
							if (!p.esStringConforme()) {
								// Si no, por último prueba AAAAMMDD
								p = new TimeStampParserYYYYMMDD(s);
								encontrado = p.esStringConforme();
							}
						}
					}
				}
			}
		}

		if (encontrado) {
			int agno;
			int mes;
			int dia;
			int hora;
			int minuto;
			int segundo;

			byte precision = 0;

			try {
				agno = Integer.parseInt(p.getStringCifra(TimeStamp.YEAR));
				precision = (byte) (precision | TimeStamp.YEAR);
			} catch (NumberFormatException nfe) {
				agno = 1900;
			}

			try {
				mes = Integer.parseInt(p.getStringCifra(TimeStamp.MONTH));
				precision = (byte) (precision | TimeStamp.MONTH);
			} catch (NumberFormatException nfe) {
				mes = 1;
			}

			try {
				dia = Integer
						.parseInt(p.getStringCifra(TimeStamp.DAY_OF_MONTH));
				precision = (byte) (precision | TimeStamp.DAY_OF_MONTH);
			} catch (NumberFormatException nfe) {
				dia = 1;
			}

			try {
				hora = Integer
						.parseInt(p.getStringCifra(TimeStamp.HOUR_OF_DAY));
				precision = (byte) (precision | TimeStamp.HOUR_OF_DAY);
			} catch (NumberFormatException nfe) {
				hora = 0;
			}

			try {
				minuto = Integer.parseInt(p.getStringCifra(TimeStamp.MINUTE));
				precision = (byte) (precision | TimeStamp.MINUTE);
			} catch (NumberFormatException nfe) {
				minuto = 0;
			}

			try {
				segundo = Integer.parseInt(p.getStringCifra(TimeStamp.SECOND));
				precision = (byte) (precision | TimeStamp.SECOND);
			} catch (NumberFormatException nfe) {
				segundo = 0;
			}

			// el mes empieza a contar en 1==Enero, pero para GregorianCalendar,
			// 0==Enero
			GregorianCalendar c = new GregorianCalendar(agno, mes - 1, dia,
					hora, minuto, segundo);
			return new TimeStamp(c, precision);

		} else {
			throw new IllegalTimeStampException('"' + s
					+ "\" no es una marca de tiempo correcta.");
		}

	}

	public static TimeStamp lookForTimeStamp(String cadena, String[] partes)
			throws IllegalTimeStampException {
		// Va buscando en cadena partes que puedan ser una MarcaDeTiempo
		TimeStampParser p;
		boolean encontrado = true;

		// Empieza con la MarcaDeTiempo más completa y sigue a las más sencillas
		// Primero: AAAA_MM_DD_hh_mm_ss
		p = new TimeStampParserYYYY_MM_DD_hh_mm_ss(cadena);
		if (!p.contieneStringConforme()) {
			// Si no, prueba AAAAMMDD_hhmmss
			p = new TimeStampParserYYYYMMDD_hhmmss(cadena);
			if (!p.contieneStringConforme()) {
				// Si no, prueba AAAAMMDDhhmmss
				p = new TimeStampParserYYYYMMDDhhmmss(cadena);
				if (!p.contieneStringConforme()) {
					// Si no, prueba AAAAMMDD_hhmm
					p = new TimeStampParserYYYYMMDD_hhmm(cadena);
					if (!p.contieneStringConforme()) {
						// Si no, prueba AAAAMMDDhhmm
						p = new TimeStampParserYYYYMMDDhhmm(cadena);
						if (!p.contieneStringConforme()) {
							// Si no, prueba AAAA_MM_DD
							p = new TimeStampParserYYYY_MM_DD(cadena);
							if (!p.contieneStringConforme()) {
								// Si no, por último prueba AAAAMMDD
								p = new TimeStampParserYYYYMMDD(cadena);
								encontrado = p.contieneStringConforme();
							}
						}
					}
				}
			}
		}

		TimeStamp ret = null;

		if (encontrado) {
			String regex = p.getStringRegex();
			int tamagnoMarca = p.getLengthMarcaDeTiempo();
			int tamagnoCadena = cadena.length();

			int pos = 0;
			do {
				String candidataMarca = cadena.substring(pos, pos
						+ tamagnoMarca);
				if (candidataMarca.matches(regex)) {
					try {
						ret = TimeStampParser.fromString(candidataMarca);
					} catch (IllegalTimeStampException e) {
						System.err
								.println("ERROR: Se ha producido una Excepción que nunca debiera haberse producido\n"
										+ "Candidata: "
										+ candidataMarca
										+ "\nRegex: " + regex);
					}
				} else {

					pos++;
				}
			} while (ret == null);

			// Rellena la variable pasada por referencia con los string por
			// partes
			if (pos > 0) {
				// Hay una parte de texto previa a la MarcaDeTiempo
				partes[0] = cadena.substring(0, pos);
			} else {
				partes[0] = "";
			}

			// La String de la MarcaDeTiempo está aquí
			partes[1] = cadena.substring(pos, tamagnoMarca + pos);

			if (pos < tamagnoCadena - tamagnoMarca) {
				// Hay una parte de texto después de la MarcaDeTiempo
				partes[2] = cadena.substring(tamagnoMarca + pos, tamagnoCadena);
			} else {
				partes[2] = "";
			}

		} else {
			// No ha encontrado una MarcaDeTiempo
			partes[0] = cadena;
			partes[1] = "";
			partes[2] = "";

			throw new IllegalTimeStampException('"' + cadena
					+ "\" no contiene una marca de tiempo correcta.");
		}

		return ret;
	}

}
