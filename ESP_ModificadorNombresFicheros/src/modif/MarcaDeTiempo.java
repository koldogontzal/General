package modif;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MarcaDeTiempo {
	
	private GregorianCalendar fechaYHora;
	
	private byte precision;
	
	public static final byte AGNO =	 			0b00100000;
	public static final byte MES = 				0b00010000;
	public static final byte DIA_DEL_MES =		0b00001000;
	public static final byte HORA_DEL_DIA =		0b00000100;
	public static final byte MINUTO =			0b00000010;
	public static final byte SEGUNDO =			0b00000001;
	
	public MarcaDeTiempo(int agno, int mes, int dia, int hora, int minuto, int segundo) {
		// el mes empieza a contar en 1==Enero, pero para GregorianCalendar, 0==Enero
		this.fechaYHora = new GregorianCalendar(agno, mes - 1, dia, hora, minuto, segundo);
		this.precision = AGNO | MES | DIA_DEL_MES | HORA_DEL_DIA | MINUTO | SEGUNDO;
	}
	
	public MarcaDeTiempo(String s) {
		
		if (MarcaDeTiempo.StringConforme(s)) {
			int agno;
			int mes;
			int dia;
			int hora;
			int minuto;
			int segundo;

			try {
				agno = Integer.parseInt(getStringCifra(s, AGNO));
				this.precision = (byte)(this.precision | AGNO);
			} catch (NumberFormatException nfe) {
				agno = 1900;
			}
			
			try {
				mes = Integer.parseInt(getStringCifra(s, MES));
				this.precision = (byte)(this.precision | MES);
			} catch (NumberFormatException nfe) {
				mes = 1;
			}
			
			try {
				dia = Integer.parseInt(getStringCifra(s, DIA_DEL_MES));
				this.precision = (byte)(this.precision | DIA_DEL_MES);
			} catch (NumberFormatException nfe) {
				dia = 1;
			}

			try {
				hora = Integer.parseInt(getStringCifra(s, HORA_DEL_DIA));
				this.precision = (byte)(this.precision | HORA_DEL_DIA);
			} catch (NumberFormatException nfe) {
				hora = 0;
			}
			
			try {
				minuto = Integer.parseInt(getStringCifra(s, MINUTO));
				this.precision = (byte)(this.precision | MINUTO);
			} catch (NumberFormatException nfe) {
				minuto = 0;
			}

			try {
				segundo = Integer.parseInt(getStringCifra(s, SEGUNDO));
				this.precision = (byte)(this.precision | SEGUNDO);
			} catch (NumberFormatException nfe) {
				segundo = 0;
			}
			
			// el mes empieza a contar en 1==Enero, pero para GregorianCalendar, 0==Enero
			this.fechaYHora = new GregorianCalendar(agno, mes - 1, dia, hora, minuto, segundo);
			
		} else {
			System.out.println('"' + s + "\" no es una marca de tiempo correcta.");
		}
	}
	
	public MarcaDeTiempo(Date d) {
		this.fechaYHora.setTime(d);
		this.precision = AGNO | MES | DIA_DEL_MES | HORA_DEL_DIA | MINUTO | SEGUNDO;
	}
	
	public MarcaDeTiempo(long d) {
		this.fechaYHora.setTimeInMillis(d);
		this.precision = AGNO | MES | DIA_DEL_MES | HORA_DEL_DIA | MINUTO | SEGUNDO;
	}
	
	public void agnadirLapso(long agn) {
		// añade tantos milisegundos como indique la variable "agn" 
		long nuevo = this.fechaYHora.getTimeInMillis() + agn;
		this.fechaYHora.setTimeInMillis(nuevo);
	}
	
	@Override
	public String toString() {
		if (this.fechaYHora != null) {
			return getCifra(AGNO) + getCifra(MES) + getCifra(DIA_DEL_MES)
					+ "_"
					+ getCifra(HORA_DEL_DIA) + getCifra(MINUTO) + getCifra(SEGUNDO);
		} else {
			return "null";
		}
	}
	
	public String getCifra(byte identificadorCifra) {
		String ret;
		if ((identificadorCifra & this.precision) != 0) {
			switch (identificadorCifra) {
			case AGNO:
				ret = formatearNumeroConCeros4Digitos(this.fechaYHora.get(Calendar.YEAR));
				break;
			case MES:
				ret = formatearNumeroConCeros2Digitos(this.fechaYHora.get(Calendar.MONTH) + 1);
				break;
			case DIA_DEL_MES:
				ret = formatearNumeroConCeros2Digitos(this.fechaYHora.get(Calendar.DAY_OF_MONTH));
				break;
			case HORA_DEL_DIA:
				ret = formatearNumeroConCeros2Digitos(this.fechaYHora.get(Calendar.HOUR_OF_DAY));
				break;
			case MINUTO:
				ret = formatearNumeroConCeros2Digitos(this.fechaYHora.get(Calendar.MINUTE));
				break;
			case SEGUNDO:
			default:
				ret = formatearNumeroConCeros2Digitos(this.fechaYHora.get(Calendar.SECOND));
				break;
			}
		} else {
			switch (identificadorCifra) {
			case AGNO:
				ret = "xxxx";
				break;
			case MES:
			case DIA_DEL_MES:
			case HORA_DEL_DIA:
			case MINUTO:
			case SEGUNDO:
			default:
				ret = "xx";
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
	public static String getStringCifra(String s, byte identificadorCifra) {
		/* Formatos posibles:
		 * aaaammddhhmmss (14 caracteres)
		 * aaaammdd_hhmmss (15 caracteres)
		 * aaaa-mm-dd hh.mm.ss (19 caracteres)
		 * 
		 * En vez de cifras, puede usarse el caracter 'x' para indicar un dato indefinido. 
		 * Por ejemplo "1978-03-xx 12.xx.xx" indicará algún día del mes de marzo de 1978 sobre las 12 del mediodía.
		 * 
		 */
		
		String ret = null;
		
		if (s.length() == 14) {
			// Formato: aaaammddhhmmss
			switch (identificadorCifra) {
			case AGNO:
				ret = s.substring(0, 4);
				break;
			case MES:
				ret = s.substring(4, 6);
				break;
			case DIA_DEL_MES:
				ret = s.substring(6, 8);
				break;
			case HORA_DEL_DIA:
				ret = s.substring(8, 10);
				break;
			case MINUTO:
				ret = s.substring(10, 12);
				break;
			case SEGUNDO:
				ret = s.substring(12, 14);
				break;
			}
			
		} else if (s.length() == 15) {
			// Formato: aaaamm_ddhhmmss
			switch (identificadorCifra) {
			case AGNO:
				ret = s.substring(0, 4);
				break;
			case MES:
				ret = s.substring(4, 6);
				break;
			case DIA_DEL_MES:
				ret = s.substring(6, 8);
				break;
			case HORA_DEL_DIA:
				ret = s.substring(9, 11);
				break;
			case MINUTO:
				ret = s.substring(11, 13);
				break;
			case SEGUNDO:
				ret = s.substring(13, 15);
				break;
			}
			
		} else if (s.length() == 19) {
			// Formato: aaaa-mm-dd hh.mm.ss
			switch (identificadorCifra) {
			case AGNO:
				ret = s.substring(0, 4);
				break;
			case MES:
				ret = s.substring(5, 7);
				break;
			case DIA_DEL_MES:
				ret = s.substring(8, 10);
				break;
			case HORA_DEL_DIA:
				ret = s.substring(11, 13);
				break;
			case MINUTO:
				ret = s.substring(14, 16);
				break;
			case SEGUNDO:
				ret = s.substring(17, 19);
				break;
			}
		}
		return ret;
	}

	public static boolean StringConforme(String s) {
		/* Formatos posibles:
		 * aaaammddhhmmss (14 caracteres)
		 * aaaammdd_hhmmss (15 caracteres)
		 * aaaa-mm-dd hh.mm.ss (19 caracteres)
		 * 
		 * en vez de cifras, puede usarse el caracter 'x' para indicar un dato indefinido. Por ejemplo "1978-03-xx 12.xx.xx" indicará algún día del mes de marzo de 1978 sobre las 12 del mediodía.
		 * 
		 */
		
		if (s.length() == 14) {
			return s.matches("[0-9x][0-9x][0-9x][0-9x][0-9x][0-9x][0-9x][0-9x][0-9x][0-9x][0-9x][0-9x][0-9x][0-9x]");
			
		} else if (s.length() == 15) {
			return s.matches("[0-9x][0-9x][0-9x][0-9x][0-9x][0-9x][0-9x][0-9x]_[0-9x][0-9x][0-9x][0-9x][0-9x][0-9x]");
			
		} else if (s.length() == 19) {
			return s.matches("[0-9x][0-9x][0-9x][0-9x][^0-9][0-9x][0-9x][^0-9][0-9x][0-9x][^0-9][0-9x][0-9x][^0-9][0-9x][0-9x][^0-9][0-9x][0-9x]");
			
		} else {
			return false;
		}
	}

/*
	public static void main(String[] args) {
		String s = "xxxx-03-03 xx.xx.xx";
		System.out.println(s);
		MarcaDeTiempo m = new MarcaDeTiempo(s);
		System.out.println(m);
		long añadirUnDia = 1000 * (60 * 60 * 24 + 1);
		m.agnadirLapso(añadirUnDia);
		System.out.println("Un día más: " + m);
	}
*/
}
