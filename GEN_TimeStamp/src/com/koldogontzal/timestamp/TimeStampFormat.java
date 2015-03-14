package com.koldogontzal.timestamp;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public enum TimeStampFormat {
	/*
	 * Sigue la codificación usada por la clase SimpleDateFormat.
	 * 
	 * Hay otros caracteres, pero los usados en TimeStampFormat para definir los formatos serán:
	 * 		yyyy	Año 			Siempre 4 cifras. Valores positivos
	 * 		MM		Mes 			Siempre 2 cifras. Valores: [1-12]
	 * 		dd		Día del mes 	Siempre 2 cifras. Valores: [1-31]
	 * 		HH		Hora del día	Siempre 2 cifras. Valores: [0-23]
	 * 		mm		Minutos			Siempre 2 cifras. Valores: [0-59]
	 * 		ss		Segundos  		Siempre 2 cifras. Valores: [0-59]
	 *
	 *
	 * Para añadir un formato nuevo, se añade a la lista de debajo una nueva línea con el nombre
	 * de la enumeración (p. ej. yyyy_MM_dd_HH_mm_ss) seguida de una cadena entre paréntesis que
	 * define cómo se van a formatear los TimeStamp al pasarlos a String (p. ej. "yyyy-MM-dd HH.mm.ss").
	 * Conviene que la lista esté ordenada de más largos a menos largos para la que la búsqueda automática
	 * de coincidencias funcione correctamente.
	 * 
	 */
	yyyy_MM_dd_HH_mm_ss("yyyy-MM-dd HH.mm.ss"),
	dd_MM_yyyy_HH_mm_ss("dd-MM-yyyy HH.mm.ss"),
	yyyy_MM_dd_HH_mm("yyyy-MM-dd HH.mm"),
	dd_MM_yyyy_HH_mm("dd-MM-yyyy HH.mm"),
	yyyyMMdd_HHmmss("yyyyMMdd_HHmmss"),
	yyyyMMddHHmmss("yyyyMMddHHmmss"),
	yyyyMMdd_HHmm("yyyyMMdd_HHmm"),
	yyyyMMddHHmm("yyyyMMddHHmm"),
	yyyy_MM_dd("yyyy-MM-dd"),
	dd_MM_yyyy("dd-MM-yyyy"),
	yyyyMMdd("yyyyMMdd");
	
	
	private static final String REGEX_NUMERO_O_CARACTER_DESCONOCIDO = "[0-9" + TimeStamp.CHAR_UNKNOWN_FIGURE + "]";
	private static final String REGEX_OTROS_CARACTERES = "[^0-9]";
	
	private String pattern;
	private String regex;
	private Map<Byte, Integer> pos = new HashMap<Byte, Integer>(10);
	
	private TimeStampFormat(String pattern) {
		this.pattern = pattern;
		
		// Crea la expresión Regex
		this.regex = "";
		char [] secuencia = pattern.toCharArray();
		for (char c : secuencia) {
			switch (c) {
			case 'y':
			case 'M':
			case 'd':
			case 'H':
			case 'm':
			case 's':
				this.regex = this.regex + REGEX_NUMERO_O_CARACTER_DESCONOCIDO;
				break;
			default:
				this.regex = this.regex + REGEX_OTROS_CARACTERES;
				break;
			}
		}
		
		// Rellena el HashMap con las posiciones de cada elemento
		this.pos.put(TimeStamp.YEAR, pattern.indexOf("yyyy"));
		this.pos.put(TimeStamp.MONTH, pattern.indexOf("MM"));
		this.pos.put(TimeStamp.DAY_OF_MONTH, pattern.indexOf("dd"));
		this.pos.put(TimeStamp.HOUR_OF_DAY, pattern.indexOf("HH"));
		this.pos.put(TimeStamp.MINUTE, pattern.indexOf("mm"));
		this.pos.put(TimeStamp.SECOND, pattern.indexOf("ss"));		
	}
	
	public String getPattern() {
		return this.pattern;
	}
	
	public String getRegex() {
		return this.regex;
	}
	
	public int getFigurePosition(byte identificadorCifra) {
		return this.pos.get(identificadorCifra);
	}
	
	public String FormattedTimeStampString(TimeStamp ts) {
		SimpleDateFormat sdf = new SimpleDateFormat(this.getPattern());
		return sdf.format(ts.getDate());
	}
	
	@Override
	public String toString() {
		return this.pattern;
	}

}
