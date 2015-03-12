package utils.tratimagen.utils;

import java.util.ArrayList;
import java.util.Collections;

public class FuncionDegradadoSencillo {
	
	public enum TipoDegradado {
		suave, 
		normal, 
		brusco;
		
		public static TipoDegradado aleatorio() {
			int op = (int)(Math.random() * 3);
			switch (op) {
			case 0:
				return suave;
			case 1:
				return brusco;
			default:
			case 2:
				return normal;
			}
		}
		
		public float factorEnsanchamiento() {
			switch (this) {
			case suave:
				return 0.55f;
			case brusco:
				return 0.2f;
			default:
			case normal:
				return 0.4f;
			}
		}		
	}
	
	private class Nodo implements Comparable<Nodo> {
		private float posicion;
		private float valor;
		
		public Nodo(float pos, float val) {
			this.posicion = Math.max(0f,  pos);
			this.valor = val;
		}
		
		@Override
		public int compareTo(Nodo arg0) {
			return (int)(this.posicion - arg0.posicion);
		}

		public float getValor() {
			return this.valor;
		}
		
		public float getPos() {
			return this.posicion;
		}
	}
	
	private ArrayList<Nodo> lista = new ArrayList<Nodo>(4);
	
	public FuncionDegradadoSencillo(float valIni, float valFin, float posCambio, TipoDegradado tipo) {
		this.lista.add(new Nodo(0f, valIni));
		this.lista.add(new Nodo(Integer.MAX_VALUE, valFin));
		float variacion = posCambio * tipo.factorEnsanchamiento();
		float pos0 = posCambio - variacion;
		float pos1 = posCambio + variacion;
		this.lista.add(new Nodo(pos0, valIni));
		this.lista.add(new Nodo(pos1, valFin));
		
		Collections.sort(this.lista);
	}
	
	public void add(float pos, int val) {
		this.lista.add(new Nodo(pos, val));
		Collections.sort(this.lista);
	}
	
	public float getValue(float pos) {
		pos = Math.max(0f, pos);
		int indiceInferior = 0;
		int indiceSuperior = 1;
		while (this.lista.get(indiceSuperior).getPos() < pos) {
			indiceInferior++;
			indiceSuperior++;
		}
		float pos0 = this.lista.get(indiceInferior).getPos();
		float val0 = this.lista.get(indiceInferior).getValor();
		float pos1 = this.lista.get(indiceSuperior).getPos();
		float val1 = this.lista.get(indiceSuperior).getValor();
		float tangente = (val1 - val0) / (pos1 - pos0);
		
		return (pos - pos0) * tangente + val0;	
	}
}

