package main;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

import utils.TimeStampParser;

public class TimeStamp implements Comparable<TimeStamp> {

	private GregorianCalendar fechaYHora;

	private byte precision;

	public static final byte YEAR = 0b01000000;
	public static final byte MONTH = 0b00100000;
	public static final byte DAY_OF_MONTH = 0b00010000;
	public static final byte HOUR_OF_DAY = 0b00001000;
	public static final byte MINUTE = 0b00000100;
	public static final byte SECOND = 0b00000010;

	public static final char CHAR_UNKNOWN_FIGURE = 'x';

	public static final int FormatYYYY_MM_DD_hh_mm_ss = 1;
	public static final int FormatYYYYMMDD_hhmmss = 2;
	public static final int FormatYYYYMMDDhhmmss = 3;
	public static final int FormatYYYYMMDD_hhmm = 4;
	public static final int FormatYYYYMMDDhhmm = 5;
	public static final int FormatYYYY_MM_DD = 6;
	public static final int FormatYYYYMMDD = 7;

	private static final int FormatDefault = FormatYYYYMMDD_hhmmss;

	public TimeStamp(int agno, int mes, int dia, int hora, int minuto, int segundo) {
		// el mes empieza a contar en 1==Enero, pero para GregorianCalendar,
		// 0==Enero
		this.fechaYHora = new GregorianCalendar(agno, mes - 1, dia, hora, minuto, segundo);
		this.precision = YEAR | MONTH | DAY_OF_MONTH | HOUR_OF_DAY | MINUTE | SECOND;
	}

	public TimeStamp(String s) throws IllegalTimeStampException {
		try {
			TimeStamp m = TimeStampParser.fromString(s);
			this.fechaYHora = m.fechaYHora;
			this.precision = m.precision;
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

	@Override
	public String toString() {
		return toString(FormatDefault);
	}
/*
	public String toString(TimeStampFormat formato) {
		String ret = formato.FormattedTimeStampString(this);
		if ((this.precision & YEAR) != 0) {
			int pos = formato.getFigurePosition(YEAR);
			if (pos >= 0) {
				if (pos == 0) {
					ret = ""
				} else {
					
				}
			}

		}
		return ret;
	}
*/

	public String toString(int formato) {
		TimeStampFormat a;
		String ret = null;

		if (this.fechaYHora != null) {
			ret = getFigure(YEAR);

			if ((formato == FormatYYYY_MM_DD_hh_mm_ss) || (formato == FormatYYYY_MM_DD)) {
				ret = ret + "-";
			}

			ret = ret + getFigure(MONTH);

			if ((formato == FormatYYYY_MM_DD_hh_mm_ss) || (formato == FormatYYYY_MM_DD)) {
				ret = ret + "-";
			}

			ret = ret + getFigure(DAY_OF_MONTH);

			if ((formato != FormatYYYYMMDD) && (formato != FormatYYYY_MM_DD)) {
				if (formato == FormatYYYY_MM_DD_hh_mm_ss) {
					ret = ret + " ";
				} else if (formato == FormatYYYYMMDD_hhmmss) {
					ret = ret + "_";
				} else if (formato == FormatYYYYMMDD_hhmm) {
					ret = ret + "_";
				}

				ret = ret + getFigure(HOUR_OF_DAY);

				if (formato == FormatYYYY_MM_DD_hh_mm_ss) {
					ret = ret + ".";
				}

				ret = ret + getFigure(MINUTE);

				if (formato == FormatYYYY_MM_DD_hh_mm_ss) {
					ret = ret + ".";
				}

				if ((formato != FormatYYYYMMDD_hhmm) && (formato != FormatYYYYMMDDhhmm)) {
					ret = ret + getFigure(SECOND);
				}

			}
			return ret;

		} else {

			return "null";
		}
	}

	

	public Date getDate() {
		return this.fechaYHora.getTime();
	}
	
	public String getFigure(byte identificadorCifra) {
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

}
