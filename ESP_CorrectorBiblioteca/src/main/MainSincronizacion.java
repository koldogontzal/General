package main;

import biblio.Sincronizador;

public class MainSincronizacion {
	public static void main(String[] args) {
		Sincronizador sinc = new Sincronizador();
		System.out.println("Estoy en la localizaci�n " + sinc.getNombreLocal());
		sinc.sincronizar();
		
	}

}
