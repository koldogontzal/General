package utils;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MarcaDeTiempoDeFichero implements Comparable<MarcaDeTiempoDeFichero> {
	
	private GregorianCalendar fechaYHora;
	
	public MarcaDeTiempoDeFichero() {
		// Sin parámetros calcula la fecha y hora de hoy
		this.fechaYHora = new GregorianCalendar();
		//this(new Date().getTime());
	}
	
	public MarcaDeTiempoDeFichero(int agno, int mes, int dia, int hora, int minuto, int segundo) {
		// el mes empieza a contar en 1==Enero, pero para GregorianCalendar, 0==Enero
		this.fechaYHora = new GregorianCalendar(agno, mes - 1, dia, hora, minuto, segundo);
	}
	
	public MarcaDeTiempoDeFichero(String s) {
		if (MarcaDeTiempoDeFichero.StringConforme(s)) {
			int agno = Integer.parseInt(s.substring(0, 4));
			int mes = Integer.parseInt(s.substring(4, 6));
			int dia = Integer.parseInt(s.substring(6, 8));
			int hora = Integer.parseInt(s.substring(8, 10));
			int minuto = Integer.parseInt(s.substring(10, 12));
			
			// el mes empieza a contar en 1==Enero, pero para GregorianCalendar, 0==Enero
			this.fechaYHora = new GregorianCalendar(agno, mes - 1, dia, hora, minuto, 0);
		} else {
			System.out.println('"' + s + "\" no es una marca de tiempo correcta.");
		}
	}
	
	public MarcaDeTiempoDeFichero(Date d) {
		this.fechaYHora = new GregorianCalendar();
		this.fechaYHora.setTime(d);
	}
	
	public MarcaDeTiempoDeFichero(long d) {
		this.fechaYHora = new GregorianCalendar();
		this.fechaYHora.setTimeInMillis(d);
	}
	
	
	@Override
	public String toString() {
		int agno = this.fechaYHora.get(Calendar.YEAR);
		int mes = this.fechaYHora.get(Calendar.MONTH) + 1;
		int dia = this.fechaYHora.get(Calendar.DAY_OF_MONTH);
		int hora = this.fechaYHora.get(Calendar.HOUR_OF_DAY);
		int minuto = this.fechaYHora.get(Calendar.MINUTE);

		return "" + formatearNumeroConCeros4Digitos(agno)
				+ formatearNumeroConCeros2Digitos(mes)
				+ formatearNumeroConCeros2Digitos(dia)
				+ formatearNumeroConCeros2Digitos(hora)
				+ formatearNumeroConCeros2Digitos(minuto);
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
	
	public static boolean StringConforme(String s) {
		// La cadena tiene que ser conforme a AAAAMMDDhhmm
		if (s.length() != 12) {
			return false;
		} else {
			boolean ret = true;
			int i = 0;
			while ((ret == true) && (i < 12)) {
				 ret = ((s.charAt(i) >= '0') && (s.charAt(i) <= '9'));
				 i++;
			}
			return ret;
		}
	}

	public static MarcaDeTiempoDeFichero parseMarcaDeTiempoDeFichero(String nombreCompleto) {
		String principioNombre;
		if (nombreCompleto.length() > 12) {
			principioNombre = nombreCompleto.substring(0,12);
		} else {
			principioNombre = nombreCompleto;
		}
		
		if (MarcaDeTiempoDeFichero.StringConforme(principioNombre))  {
			return new MarcaDeTiempoDeFichero(principioNombre);
		} else {
			return null;
		}
		
	}

	private Date getDate() {
		return fechaYHora.getTime();
	}
	
	@Override
	public int compareTo(MarcaDeTiempoDeFichero arg0) {
		Date fecha1 = this.getDate();
		Date fecha2 = arg0.getDate();
		
		return fecha1.compareTo(fecha2);
	}
}
