package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.koldogontzal.geometria2d.Punto;
import com.koldogontzal.numeroscomplejos.ConversorNumerocomplejoColormod;
import com.koldogontzal.numeroscomplejos.ConversorPlanocomplejoPixels;
import com.koldogontzal.numeroscomplejos.FuncionesComplejas;
import com.koldogontzal.numeroscomplejos.NumeroComplejo;

import Interfaz.PlanoComplejoDibujable;
import lienzo.LienzoEnPlanoComplejo;
import utils.ColorMod;

public class DibujadorFuncionesPlanoComplejo extends Frame implements PlanoComplejoDibujable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4899006624806631337L;

	private LienzoEnPlanoComplejo lienzo;

	public DibujadorFuncionesPlanoComplejo(NumeroComplejo infIzq, NumeroComplejo supDrch) {
		super("Dibujador de funciones en el plano complejo");
		this.lienzo = new LienzoEnPlanoComplejo(this, infIzq, supDrch, true);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	@SuppressWarnings("deprecation")
	public void mostrarLienzo() {
		this.add(this.lienzo, BorderLayout.CENTER);
		this.pack();
		this.show();
	}

	@Override
	public void dibujar(LienzoEnPlanoComplejo lienzo, Graphics g) {

		// Primero recorre la función, buscando el módulo máximo para parametrizar
		// correctamente el objeto ConversorNumerocomplejoColormod. No recorre todos los
		// puntos, sino, unicamente 1 de cada 20
		int paso = 20;
		ConversorNumerocomplejoColormod convColor = new ConversorNumerocomplejoColormod();
		// Establece el valor mínimo de saturación en el color. Puede variar entre 0 y 100. 
		convColor.setMinSat(30);
		
		ConversorPlanocomplejoPixels convNumComplej = this.lienzo.getConversorPlanocomplejoPixels();
		int ancho = this.getWidth();
		int alto = this.getHeight();

		for (int x = 0; x < ancho; x = x + paso) {
			for (int y = 0; y < alto; y = y + paso) {
				NumeroComplejo fz = funcionComplejaParaDibujar(convNumComplej.convertir(x, y));
				convColor.modificarMaxRadio(fz.getModulo());
			}
		}

		// En segundo lugar, 
		// Ahora recorre todos los puntos del lienzo calculando el valor de la función, para pintarlos uno a uno
		for (int x = 0; x <= ancho; x = x + 1) {
			for (int y = 0; y <= alto; y = y + 1) {
				NumeroComplejo fz = funcionComplejaParaDibujar(convNumComplej.convertir(x, y));
				ColorMod c = convColor.convertir(fz);

				this.lienzo.dibujaPunto(g, new Punto(x, y), c);
			}
		}
	}

	
	// Esta es la función que se quiere dibujar
	private NumeroComplejo funcionComplejaParaDibujar(NumeroComplejo z) {
		/*
		NumeroComplejo ret, a, b;
		a = new NumeroComplejo(z);
		b = FuncionesComplejas.multiplicacionEscalar(2.0,
				FuncionesComplejas.raizCuadrada(FuncionesComplejas.suma(z, FuncionesComplejas.const_neg1)));

		ret = FuncionesComplejas.raizCuadrada(FuncionesComplejas.suma(a, b));
		ret = FuncionesComplejas.suma(ret, FuncionesComplejas.raizCuadrada(FuncionesComplejas.resta(a, b)));
		return ret;
		*/
		
		return FuncionesComplejas.logaritmoBaseZ(new NumeroComplejo(2, -1), z);
	}

	
	
	
	public static void main(String[] args) {
		DibujadorFuncionesPlanoComplejo dib = new DibujadorFuncionesPlanoComplejo(new NumeroComplejo(-9, -1),
				new NumeroComplejo(8, 1));

		dib.mostrarLienzo();

		System.out.println(dib);

	}
}
