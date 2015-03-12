package utils;

import java.util.ArrayList;

public class Opositor {
	private String NIF;
	private String nombre;
	private String apellidos;
	private String codExamen;
	private String codModificado;
	private ArrayList<Ejercicio> ejerciciosServidor = new ArrayList<Ejercicio>(2);
	
	public Opositor(String nif, String nom, String ape, String cod, String cmod) {
		this.NIF = nif;
		this.nombre = nom;
		this.apellidos = ape;
		this.codExamen = cod;
		this.codModificado = cmod;
	}
	
	public Opositor(String linea) {
		String[] partes = linea.split("\"");
		if (partes.length == 8 || partes.length == 10) {
			this.NIF = partes[1];
			this.apellidos = partes[3];
			this.nombre = partes[5];
			this.codExamen = partes[7];
		} else {
			System.out.println(linea + " -> No se trata de una linea valida");
		}

		if (partes.length == 10) {
			this.codModificado = partes[9];
		}
	}

	
	public String getNIF() {
		return NIF;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public String getCodExamen() {
		return codExamen;
	}

	public String getCodModificado() {
		return codModificado;
	}

	public void agnadirEjercicioServidor(Ejercicio ej) {
		this.ejerciciosServidor.add(ej);
	}
	
	@Override
	public String toString() {
		return this.apellidos + ", " + this.nombre + " (" + this.NIF + "): COD: " + this.codExamen;
	}
}
