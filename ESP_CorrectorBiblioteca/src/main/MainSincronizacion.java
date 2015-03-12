package main;

import biblio.Sincronizador;

public class MainSincronizacion {
	public static void main(String[] args) {
		Sincronizador sinc = new Sincronizador();
		System.out.println("Estoy en la localización " + sinc.getNombreLocal());
		sinc.sincronizar();
		
	}

}
