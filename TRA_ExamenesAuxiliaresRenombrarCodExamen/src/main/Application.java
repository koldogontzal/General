package main;

import util.RenombradorCodExamen;

public class Application {
	
	public static void main(String[] args) {
		RenombradorCodExamen r = new RenombradorCodExamen("C:\\XP\\lista_2010.txt", "C:\\XP\\lista_2003.txt");
		r.renombrarArchivos("C:\\XP\\Examenes2003");
		System.out.println("Finalizado");
	}
}
