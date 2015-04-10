package main;

import utils.Archivo;

public class Pruebas {
	
	public static void main(String[] args) {
		Archivo a = new Archivo("C:\\prueba\\.fichero_(2).ext");
		System.out.println(a);
		System.out.println("Padre: " + a.getParent());
		System.out.println("Nombre: " + a.getNombreSimple());
		System.out.println("Orden: " + a.getOrden());
		System.out.println("Extension: " + a.getExtension());
		
	}

}
