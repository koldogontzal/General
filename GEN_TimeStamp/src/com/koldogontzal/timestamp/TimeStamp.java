package com.koldogontzal.timestamp;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TimeStamp implements Comparable<TimeStamp> {

	private GregorianCalendar fechaYHora;

	private byte precision;

	public static final byte YEAR 			= 0b01000000;
	public static final byte MONTH 			= 0b00100000;
	public static final byte DAY_OF_MONTH 	= 0b00010000;
	public static final byte HOUR_OF_DAY 	= 0b00001000;
	public static final byte MINUTE 		= 0b00000100;
	public static final byte SECOND 		= 0b00000010;

	public static final char CHAR_UNKNOWN_FIGURE = 'x';

	private TimeStampFormat preferredFormat = TimeStampFormat.yyyyMMdd_HHmmss; // Preferred format (String representation) of the TimeStamp

	public TimeStamp(int agno, int mes, int dia, int hora, int minuto, int segundo) {
		// el mes empieza a contar en 1==Enero, pero para GregorianCalendar, 0==Enero
		this.fechaYHora = new GregorianCalendar(agno, mes - 1, dia, hora, minuto, segundo);
		this.precision = YEAR | MONTH | DAY_OF_MONTH | HOUR_OF_DAY | MINUTE | SECOND;
	}

	public TimeStamp(String s) throws IllegalTimeStampException {
		try {
			TimeStamp m = TimeStamp.fromString(s);
			this.fechaYHora = m.fechaYHora;
			this.precision = m.precision;
			this.preferredFormat = m.preferredFormat;
		} catch (IllegalTimeStampException e) {
			throw e;
		}
	}

	public TimeStamp(GregorianCalendar c) {
		this(c, (byte) (YEAR | MONTH | DAY_OF_MONTH | HOUR_OF_DAY | MINUTE | SECOND));
	}

	public TimeStamp(GregorianCalendar c, byte precision) {
		this.fechaYHora = c;
		this.precision = precision;
	}

	public TimeStamp(Date d) {
		this(d, (byte) (YEAR | MONTH | DAY_OF_MONTH | HOUR_OF_DAY | MINUTE | SECOND));
	}

	public TimeStamp(Date d, byte precision) {
		this.fechaYHora = new GregorianCalendar();
		this.fechaYHora.setTime(d);
		this.precision = precision;
	}

	public TimeStamp(long d) {
		this(d, (byte) (YEAR | MONTH | DAY_OF_MONTH | HOUR_OF_DAY | MINUTE | SECOND));
	}

	public TimeStamp(long d, byte precision) {
		this.fechaYHora = new GregorianCalendar();
		this.fechaYHora.setTimeInMillis(d);
		this.precision = precision;
	}

	public void addTime(long miliseconds) {
		// Adelanta la fecha tantos milisegundos como indique la variable
		// "miliseconds". Puede ser un valor negativo
		long nuevo = this.fechaYHora.getTimeInMillis() + miliseconds;
		this.fechaYHora.setTimeInMillis(nuevo);
	}

	public TimeStampFormat getPreferredFormat() {
		return this.preferredFormat;
	}
	
	public void setPreferredFormat(TimeStampFormat format) {
		this.preferredFormat = format;
	}
	
	@Override
	public String toString() {
		return toString(this.preferredFormat);
	}

	public String toString(TimeStampFormat formato) {
		// String base, según el formato especificado
		String ret = formato.FormattedTimeStampString(this);
		
		// Pone el carácter desconocido en los campos no definidos con precisión
		if ((this.precision & YEAR) == 0) {
			int pos = formato.getFigurePosition(YEAR);
			if (pos >= 0) {
				ret = ret.substring(0, pos) + CHAR_UNKNOWN_FIGURE + CHAR_UNKNOWN_FIGURE + 
						CHAR_UNKNOWN_FIGURE + CHAR_UNKNOWN_FIGURE + ret.substring(pos + 4);				
			}
		}
		
		if ((this.precision & MONTH) == 0) {
			int pos = formato.getFigurePosition(MONTH);
			if (pos >= 0) {
				ret = ret.substring(0, pos) + CHAR_UNKNOWN_FIGURE + CHAR_UNKNOWN_FIGURE + ret.substring(pos + 2);				
			}
		}
		
		if ((this.precision & DAY_OF_MONTH) == 0) {
			int pos = formato.getFigurePosition(DAY_OF_MONTH);
			if (pos >= 0) {
				ret = ret.substring(0, pos) + CHAR_UNKNOWN_FIGURE + CHAR_UNKNOWN_FIGURE + ret.substring(pos + 2);				
			}
		}
		
		if ((this.precision & HOUR_OF_DAY) == 0) {
			int pos = formato.getFigurePosition(HOUR_OF_DAY);
			if (pos >= 0) {
				ret = ret.substring(0, pos) + CHAR_UNKNOWN_FIGURE + CHAR_UNKNOWN_FIGURE + ret.substring(pos + 2);				
			}
		}
		
		if ((this.precision & MINUTE) == 0) {
			int pos = formato.getFigurePosition(MINUTE);
			if (pos >= 0) {
				ret = ret.substring(0, pos) + CHAR_UNKNOWN_FIGURE + CHAR_UNKNOWN_FIGURE + ret.substring(pos + 2);				
			}
		}
		
		if ((this.precision & SECOND) == 0) {
			int pos = formato.getFigurePosition(SECOND);
			if (pos >= 0) {
				ret = ret.substring(0, pos) + CHAR_UNKNOWN_FIGURE + CHAR_UNKNOWN_FIGURE + ret.substring(pos + 2);				
			}
		}
		
		return ret;
	}


	public Date getDate() {
		return this.fechaYHora.getTime();
	}
	
	public String getFigureString(byte identificadorCifra) {
		// Si uso, sólo lo he mantenido para futuros usos.
		String ret;
		if ((identificadorCifra & this.precision) != 0) {
			switch (identificadorCifra) {
			case YEAR:
				ret = formatearNumeroConCeros4Digitos(this.fechaYHora
						.get(Calendar.YEAR));
				break;
			case MONTH:
				ret = formatearNumeroConCeros2Digitos(this.fechaYHora
						.get(Calendar.MONTH) + 1);
				break;
			case DAY_OF_MONTH:
				ret = formatearNumeroConCeros2Digitos(this.fechaYHora
						.get(Calendar.DAY_OF_MONTH));
				break;
			case HOUR_OF_DAY:
				ret = formatearNumeroConCeros2Digitos(this.fechaYHora
						.get(Calendar.HOUR_OF_DAY));
				break;
			case MINUTE:
				ret = formatearNumeroConCeros2Digitos(this.fechaYHora
						.get(Calendar.MINUTE));
				break;
			case SECOND:
			default:
				ret = formatearNumeroConCeros2Digitos(this.fechaYHora
						.get(Calendar.SECOND));
				break;

			}
		} else {
			switch (identificadorCifra) {
			case YEAR:
				ret = "" + CHAR_UNKNOWN_FIGURE + CHAR_UNKNOWN_FIGURE
						+ CHAR_UNKNOWN_FIGURE + CHAR_UNKNOWN_FIGURE;
				break;
			case MONTH:
			case DAY_OF_MONTH:
			case HOUR_OF_DAY:
			case MINUTE:
			case SECOND:
			default:
				ret = "" + CHAR_UNKNOWN_FIGURE + CHAR_UNKNOWN_FIGURE;
				break;
			}
		}
		return ret;
	}

	private String formatearNumeroConCeros2Digitos(int n) {
		String temp = "00" + n;
		int l = temp.length();
		return temp.substring(l - 2, l);
	}

	private String formatearNumeroConCeros4Digitos(int n) {
		String temp = "0000" + n;
		int l = temp.length();
		return temp.substring(l - 4, l);
	}

	@Override
	public int compareTo(TimeStamp arg0) {
		return this.fechaYHora.compareTo(arg0.fechaYHora);
	}

	private static TimeStamp fromStringConFormatoDefinido(String s, TimeStampFormat formato) {
		int agno;
		int mes;
		int dia;
		int hora;
		int minuto;
		int segundo;

		byte precision = 0;
		int pos;

		try {
			pos = formato.getFigurePosition(YEAR);
			if (pos >= 0) {
				// Existe la figura en el formato
				agno = Integer.parseInt(s.substring(pos, pos + 4));
				precision = (byte) (precision | YEAR);
			} else {
				// No existe la figura en el formato
				agno = 1900;
			}
		} catch (NumberFormatException nfe) {
			// En el String se ha definido como una "xxxx"
			agno = 1900;
		}

		try {
			pos = formato.getFigurePosition(MONTH);
			if (pos >= 0) {
				mes = Integer.parseInt(s.substring(pos, pos + 2));
				precision = (byte) (precision | MONTH);
			} else {
				mes = 1;
			}
		} catch (NumberFormatException nfe) {
			mes = 1;
		}

		try {
			pos = formato.getFigurePosition(DAY_OF_MONTH);
			if (pos >= 0) {
				dia = Integer.parseInt(s.substring(pos, pos + 2));
				precision = (byte) (precision | DAY_OF_MONTH);
			} else {
				dia = 1;
			}
		} catch (NumberFormatException nfe) {
			dia = 1;
		}

		try {
			pos = formato.getFigurePosition(HOUR_OF_DAY);
			if (pos >= 0) {
				hora = Integer.parseInt(s.substring(pos, pos + 2));
				precision = (byte) (precision | HOUR_OF_DAY);
			} else {
				hora = 0;
			}
		} catch (NumberFormatException nfe) {
			hora = 0;
		}

		try {
			pos = formato.getFigurePosition(MINUTE);
			if (pos >= 0) {
				minuto = Integer.parseInt(s.substring(pos, pos + 2));
				precision = (byte) (precision | MINUTE);
			} else {
				minuto = 0;
			}
		} catch (NumberFormatException nfe) {
			minuto = 0;
		}

		try {
			pos = formato.getFigurePosition(SECOND);
			if (pos >= 0) {
				segundo = Integer.parseInt(s.substring(pos, pos + 2));
				precision = (byte) (precision | SECOND);
			} else {
				segundo = 0;
			}
		} catch (NumberFormatException nfe) {
			segundo = 0;
		}
		
		// Comprueba que las cifras estén entre los valores máximox y mínimos
		if 		((mes > 0) && (mes <=12) &&
				(dia > 0) && (dia <= 31) &&
				(hora >= 0) && (hora < 24) &&
				(minuto >= 0) && (minuto < 60) &&
				(segundo >= 0) && (segundo < 60)) {
			// Cifras de meses, días, horas, minutos y segundos conformes
			// El mes empieza a contar en 1==Enero, pero para GregorianCalendar,
			// 0==Enero
			GregorianCalendar c = new GregorianCalendar(agno, mes - 1, dia, hora, minuto, segundo);
			TimeStamp ret = new TimeStamp(c, precision);
			ret.setPreferredFormat(formato);
			return ret;
		} else {
			// Cifras fuera de los rangos normales, con lo que no es un TimeStamp Válido
			return null;
		}
		
	}
	
	public static TimeStamp fromString(String s) throws IllegalTimeStampException {
		/*
		 * Método estático que devuelve un TimeStamp dado un String.
		 * El String únicamente debe contener el TimeStamp y ningún otro carácter. En caso contrario se
		 * generará una Exception.
		 */
		
		// Busca un formato de TimeStamp compatible con la cadena
		int encontrado = -1;
		TimeStampFormat[] listadoFormatos = TimeStampFormat.values();		
		for (int i = 0; (i < listadoFormatos.length) && (encontrado == -1); i++) {
			String regex = listadoFormatos[i].getRegex();
			if (s.matches(regex)) {
				encontrado = i;
			}
		}
		
		if (encontrado >= 0) {
			// Ha encontrado una coincidencia entre los formatos compatibles de TimeStamp
			return fromStringConFormatoDefinido(s, listadoFormatos[encontrado]);

		} else {
			// No ha encontrado ningún formato de TimeStamp compatible
			throw new IllegalTimeStampException('"' + s + "\" no es una marca de tiempo correcta.");
		}

	}

	public static TimeStamp lookForTimeStamp(String cadena, String[] partes) throws IllegalTimeStampException {
		/*
		 * Método estático que busca un TimeStamp contenido en un String cadena.
		 * El String puede contener el TimeStamp y otros caracteres. En ese caso, se usa
		 * el parámetro partes para descomponer la cadena en 3 partes:
		 * 		· parte[0]: contiene el String con la parte anterior al TimeStamp
		 * 		· parte[1]: contiene el String con el propio TimeStamp
		 * 		· parte[2]: contiene el String con la parte posterior al TimeStamp
		 * 
		 * Si no se encuentra un TimeStamp válido, se genera un Exception y la cadena se 
		 * almacena en la parte[2]
		 * 
		 */
		
		// Busca un formato de TimeStamp compatible con la cadena
		TimeStamp ret = null;
		boolean encontrado = false;
		
		TimeStampFormat[] listadoFormatos = TimeStampFormat.values();		
		for (int i = 0; (i < listadoFormatos.length) && (!encontrado); i++) {
			// Recorre ordenadamente la lista de posibles TimeStampFormat
			String regex = listadoFormatos[i].getRegex();
			if (cadena.matches("(.*)" + regex + "(.*)")) {
				// Hay una posible coincidencia
				
				// Ha encontrado un formato compatible, Ahora busca en qué posición empieza la cadena que contiene el TimeStamp
				int tamagnoMarca = listadoFormatos[i].getRealPatternLength();
				int tamagnoCadena = cadena.length();
				int pos = 0;

				do {
					String candidataMarca = cadena.substring(pos, pos + tamagnoMarca);
					if (candidataMarca.matches(regex)) {
						// Encontrada la posición donde empieza el posible TimeStamp						
						
						// Si todo va bien, calcula el TimeStamp, si no, devulve null
						ret = fromStringConFormatoDefinido(candidataMarca, listadoFormatos[i]);
						
						// Comprueba que las cifras sean conformes: mes = [1..12], día = [1..31], hora, minuto, segundo
						if (ret == null) {
							// No era un TimeStamp con valores conformes
							pos++;
						} else {
							// Sí es un TimeStamp correcto
							encontrado = true;
							
							// Rellena la variable pasada por referencia con los string por partes
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
						}
						
					} else {
						// No la ha encontrado. Pasa a la siguiente posición.
						pos++;
					}
				} while ((ret == null) && (pos < tamagnoCadena - tamagnoMarca + 1));
			}
		}
		

		if (!encontrado) {
			// No ha encontrado una MarcaDeTiempo
			partes[0] = "";
			partes[1] = "";
			partes[2] = cadena;

			throw new IllegalTimeStampException('"' + cadena + "\" no contiene una marca de tiempo correcta.");
		} else {
			
			// Devuelve el TimeStamp encontrado
			return ret;
		}
	}
}
