package main;

import util.FuncionesComplejas;
import util.NumeroComplejo;

public class MainPruebas {
	public static void main(String[] args) {
		
	NumeroComplejo z1 = FuncionesComplejas.const_1;
	NumeroComplejo z2 = FuncionesComplejas.multiplicacionCompleja(FuncionesComplejas.const_i, FuncionesComplejas.toNumeroComplejo(2.5));
	
	System.out.println(FuncionesComplejas.exponencialCompleja(FuncionesComplejas.sumaCompleja(z1, z2)));
	
	NumeroComplejo z;
	for (double y=-4.0; y<= 4.0; y=y+0.1) {
		z=new NumeroComplejo(y, 01);
		System.out.println("El numero " + z + " tiene de tangente el número " + FuncionesComplejas.tanCompleja(z)); 
		}				

	
	System.out.println("Funciones exponenciales:");
	z=new NumeroComplejo(1,10);
	
	System.out.println("e elevado a " + z + " da por una lado " + FuncionesComplejas.exponencialCompleja(z) +". Por otro lado da " + FuncionesComplejas.exponenteComplejo(FuncionesComplejas.toNumeroComplejo(Math.exp(1)), z));
	
	System.out.println("Cáculo del inverso de un número complejo:");
	z=new NumeroComplejo(-2,5);
	System.out.println("El número " + z + " tiene de inverso " + FuncionesComplejas.inversoComplejo(z) + " y por otro lado da " + FuncionesComplejas.exponenteComplejo(z, FuncionesComplejas.const_neg1));
	}	
	
}
