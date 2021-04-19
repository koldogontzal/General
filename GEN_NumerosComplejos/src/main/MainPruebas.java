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
		System.out.println("El numero " + z + " tiene de exponencial el número " + FuncionesComplejas.tanCompleja(z)); 
		}		
		
	}
	
}
