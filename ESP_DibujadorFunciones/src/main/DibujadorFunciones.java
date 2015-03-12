package main;

import interfaz.PlanoRealDibujable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;

import com.koldogontzal.geometria2d.Punto;

import lienzo.LienzoEnPlanoReal;

import util.Constante;
import util.DeltaDirac;
import util.Escalon;
import util.EscalonAcotado;
import util.Funcion;
import util.LogaritmoNeperiano;
import util.Sumatorio;

public class DibujadorFunciones extends Frame implements PlanoRealDibujable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1612939151306145889L;
	
	private ArrayList<Funcion> listadoFunciones;

	private LienzoEnPlanoReal lienzo;
	
	
	public DibujadorFunciones(Punto infIzq, Punto supDrch, Color c) {
		super("Dibujador de funciones");
		this.lienzo = new LienzoEnPlanoReal(this, c, infIzq, supDrch, true);
		this.listadoFunciones = new ArrayList<Funcion>(10);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
	}
	
	public void agnadirFuncion(Funcion f) {
		this.listadoFunciones.add(f);
	}

	@Override
	public void dibujar(LienzoEnPlanoReal lienzo, Graphics g) {
		Iterator<Funcion> i = this.listadoFunciones.iterator();
		while (i.hasNext()) {
			Funcion f = i.next();
			f.dibujar(lienzo, g);
		}
	}
	
	@Override
	public String toString() {
		String retorno = "Listado de Funciones dibujadas:\n";
		Iterator<Funcion> i = this.listadoFunciones.iterator();
		while (i.hasNext()) {
			Funcion f = i.next();
			retorno = retorno + "· " + f + "\n";
		}
		return retorno;
	}
		
	public void mostrarFunciones() {		
		this.add(this.lienzo, BorderLayout.CENTER);
		this.pack();
		this.show();
	}

	
	public static void main(String[] args) {
		
		DibujadorFunciones dib = new DibujadorFunciones(new Punto(-1, -10), new Punto(20, 10), Color.BLUE);
		
		
		Sumatorio sum = new Sumatorio();
		for (int i = 1; i < 19; i++) {
			double inicio = Math.random() * 20.0;
			double duracion = Math.random() * 2.0;
			double altura = Math.pow(-1, i) * 0.5 * Math.random();
			EscalonAcotado esc = new EscalonAcotado(Funcion.NULA, inicio, new Constante(altura), inicio + duracion, Funcion.NULA);
			sum.agnadirSumando(esc);
		}
		sum.setColor(Color.MAGENTA);
		
		
	
		Funcion fAux = new LogaritmoNeperiano(1.0, Funcion.X.integral(-1));
		fAux.setColor(Color.RED);
		
		Funcion fAux1 = new Escalon(new Constante(1), 3, new Constante(2));
		fAux1.setColor(Color.BLUE);
		
		Funcion fAux2 = fAux.derivada();
		fAux2.setColor(Color.GREEN);
		
		dib.agnadirFuncion(fAux);
		dib.agnadirFuncion(fAux1);
		dib.agnadirFuncion(sum);
		dib.agnadirFuncion(sum.integral(.25).integral());
		
		dib.mostrarFunciones();
		
		System.out.println(dib);
	}
	
}
