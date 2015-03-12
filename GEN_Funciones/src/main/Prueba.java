package main;

import util.*;

public class Prueba {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

		
		//Funcion f = new Cociente(new Constante(3).integral(-1), Funcion.X.integral(1));
		
		Funcion f = new LogaritmoNeperiano(1, new Constante(2).integral(-1));
		//f.multiplicarFactorialmente(1.5);
		

		//f.multiplicarFactorialmente(0);
		//f.agnadirSumando(f2.integral(-4));
		
		
		System.out.println("Integral: " + f.integral());
		System.out.println("Funcion: " + f);
		System.out.println("Derivada: " + f.derivada());
		//System.out.println("¿Integrable? " + f.derivada().esIntegrable());
		//System.out.println("Integral de la derivada: " + f.derivada().integral(1));
		Funcion dup = f.clonar();
		dup.multiplicarFactorialmente(2);
		System.out.println("Duplicada: " + dup);
		System.out.println("Original: " + f);
		System.out.println("Es nula?" + f.esFuncionNula());
		System.out.println("Es constante?" + f.esFuncionConstante());
		System.out.println("Es funcionX?" + f.esFuncionX());
		
		
		for (double x = .45; x <= .55; x = x + 0.01) {
			System.out.println("f(" + x + ") = " + f.evaluar(x));
		}
	}
}
