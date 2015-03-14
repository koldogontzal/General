package main;

import java.io.File;

public class EjemploDeUso {

	public static void main(String[] args) {
		// Ejemplo de uso de MarcaDeTiempo
		String s = "2015.03.03";
		System.out.println(s);

		TimeStamp m;
		try {
			// Crear una a partir de un String
			m = new TimeStamp(s);
			long l = new File("").lastModified();
			System.out.println(l);
			m = new TimeStamp(l);

			// Aplicar distintos formatos
			System.out.println(m);
			System.out.println(m.toString(TimeStamp.FormatYYYY_MM_DD_hh_mm_ss));
			System.out.println(m.toString(TimeStamp.FormatYYYYMMDD_hhmmss));
			System.out.println(m.toString(TimeStamp.FormatYYYYMMDDhhmmss));
			System.out.println(m.toString(TimeStamp.FormatYYYYMMDD_hhmm));
			System.out.println(m.toString(TimeStamp.FormatYYYYMMDDhhmm));
			System.out.println(m.toString(TimeStamp.FormatYYYY_MM_DD));
			System.out.println(m.toString(TimeStamp.FormatYYYYMMDD));

			// Modificar la fecha de una MarcaDeTiempo
			long añadirUnDia = 1000 * (60 * 60 * 24 + 1);
			m.addTime(añadirUnDia);
			System.out.println("Un dia mas: " + m);

		} catch (IllegalTimeStampException e) {
			// El String no es válido, se produce una excepción
			e.printStackTrace();

		}

		String path = "C:\\Prueba\\amparo\\2013-56-34 76.34.98";

		TimeStampFile f = new TimeStampFile(path);
		System.out.println(f);

		f.setTimeStamp(TimeStamp.FormatYYYY_MM_DD_hh_mm_ss);
		System.out.println("\n\n" + f);

	}

}
