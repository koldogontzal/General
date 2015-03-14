package com.koldogontzal.timestamp;

public class EjemploDeUso {

	public static void main(String[] args) {
		// Ejemplo de uso de MarcaDeTiempo
		String s = "xxxx.03.03 12_45_xx";
		System.out.println(s);

		TimeStamp m;
		try {
			// Crear una a partir de un String
			m = new TimeStamp(s);
			//long l = new TimeStampFile("").lastModified();
			//System.out.println(l);
			//m = new TimeStamp(l);

			// Aplicar distintos formatos
			System.out.println("Default: " + m);
			TimeStampFormat [] listadoFormatos = TimeStampFormat.values();
			for (TimeStampFormat f : listadoFormatos) {
				System.out.println(f.name() + ": " + m.toString(f));
			}
			
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

		f.setTimeStamp(TimeStampFormat.yyyy_MM_dd_HH_mm_ss);
		System.out.println("\n\n" + f);

	}

}
