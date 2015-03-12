package elementos;

import java.awt.image.ImageObserver;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

import constantes.Constantes;

import utils.CacheSprites;
import utils.Posicion;
import utils.Vector4ElementosDesordenados;
import utils.Vector8ElementosDesordenados;

public class Escenario implements ImageObserver {
	
	public static final int UN_CATALIZADOR_CENTRADO_RODEADO_DE_SUSTRATO = 1;
	public static final int UN_CATALIZADOR_CON_SUSTRATO_ALEATORIO = 2;
	public static final int DOS_CATALIZADORES_CON_SUSTRATO_EN_EL_CENTRO = 3;
	
	private Celda[][] rejilla;
	
	private CacheSprites cacheSprites;
	
	private int numMaxFilas;
	private int numMaxColumnas;
	
	public Escenario (int numFilas, int numColumnas, CacheSprites cache) {
		this.numMaxFilas = numFilas;
		this.numMaxColumnas = numColumnas;
		
		this.rejilla = new Celda[numFilas][numColumnas];
		
		this.cacheSprites = cache;
	}
	
	public CacheSprites getCacheSprites() {
		return cacheSprites;
	}

	public void iniciar(int tipoIniciacion) {
		switch (tipoIniciacion) {
		case UN_CATALIZADOR_CENTRADO_RODEADO_DE_SUSTRATO:
			{
				for (int i = 0; i < this.numMaxFilas; i++) {
					for (int j = 0; j < this.numMaxColumnas; j++) {
						Posicion p = new Posicion(i, j);
						Celda cel = new Sustrato(this, p, 
								Constantes.SUSTRATO_T_ACC_MIN, Constantes.SUSTRATO_T_ACC_MAX);
						this.setCelda(p, cel);
					}
				}
				Posicion p = new Posicion(this.numMaxFilas / 2, this.numMaxColumnas / 2);
				Celda cel = new Catalizador(this, p, 
						Constantes.CATALIZADOR_T_ACC_MIN, Constantes.CATALIZADOR_T_ACC_MAX);
				this.setCelda(p, cel);
			}
			break;
		case UN_CATALIZADOR_CON_SUSTRATO_ALEATORIO:
			{
				for (int i = 0; i < this.numMaxFilas; i++) {
					for (int j = 0; j < this.numMaxColumnas; j++) {
						Posicion p = new Posicion(i, j);
						Celda cel;
						if (Math.random() < .5) {
							cel = new Sustrato(this, p, 
								Constantes.SUSTRATO_T_ACC_MIN, Constantes.SUSTRATO_T_ACC_MAX);
						} else {
							cel = new Vacia(this, p);
						}
						this.setCelda(p, cel);
					}
				}
				Posicion p = new Posicion(this.numMaxFilas / 2, this.numMaxColumnas / 2);
				Celda cel = new Catalizador(this, p, 
						Constantes.CATALIZADOR_T_ACC_MIN, Constantes.CATALIZADOR_T_ACC_MAX);
				this.setCelda(p, cel);
			}
			break;
		case DOS_CATALIZADORES_CON_SUSTRATO_EN_EL_CENTRO:
			{
				for (int i = 0; i < this.numMaxFilas; i++) {
					for (int j = 0; j < this.numMaxColumnas; j++) {
						Posicion p = new Posicion(i, j);
						Celda cel = new Vacia(this, p);
						this.setCelda(p, cel);
					}
				}
				
				for (int i = (int)(0.15 * this.numMaxFilas); i < (int)(0.85 * this.numMaxFilas); i++) {
					for (int j = (int)(0.15 * this.numMaxColumnas); j < (int)(0.85 * this.numMaxColumnas); j++) {
						Posicion p = new Posicion(i, j);
						Celda cel;
						cel = new Sustrato(this, p, 
								Constantes.SUSTRATO_T_ACC_MIN, Constantes.SUSTRATO_T_ACC_MAX);
						this.setCelda(p, cel);
					}
				}
				Posicion p = new Posicion(0, 0);
				Celda cel = new Catalizador(this, p, 
						Constantes.CATALIZADOR_T_ACC_MIN, Constantes.CATALIZADOR_T_ACC_MAX);
				this.setCelda(p, cel);
				
				p = new Posicion(this.numMaxFilas - 1, this.numMaxColumnas - 1);
				cel = new Catalizador(this, p, 
						Constantes.CATALIZADOR_T_ACC_MIN, Constantes.CATALIZADOR_T_ACC_MAX);
				this.setCelda(p, cel);
			}
			break;
		}
	}

	public void setCelda(Posicion pos, Celda cel) {
		this.rejilla[pos.getFila()][pos.getColumna()] = cel;
	}

	public Celda getCelda(Posicion pos) {
		return this.rejilla[pos.getFila()][pos.getColumna()];
	}
	
	public ArrayList<Celda> get8Vecinos(Posicion pos) {
		ArrayList<Celda> listado = new ArrayList<Celda>(8);
		Vector8ElementosDesordenados vector = new Vector8ElementosDesordenados();
		
		for (int i = 0; i < 8; i++) {
			Posicion pVecino = pos.getPosicionVecino(vector.getPosicion(i));
			if (this.estaEntreLosLimites(pVecino)) {
				listado.add(this.getCelda(pVecino));
			}
		}
		
		return listado;
	}
	
	public ArrayList<Celda> get4Vecinos(Posicion pos) {
		ArrayList<Celda> listado = new ArrayList<Celda>(4);
		Vector4ElementosDesordenados vector = new Vector4ElementosDesordenados();
		
		for (int i = 0; i < 4; i++) {
			Posicion pVecino = pos.getPosicionVecino(vector.getPosicion(i));
			if (this.estaEntreLosLimites(pVecino)) {
				listado.add(this.getCelda(pVecino));
			}
		}
		
		return listado;
	}

	private boolean estaEntreLosLimites(Posicion p) {
		return ((p.getFila() >= 0) && (p.getColumna() >= 0) && (p.getFila() < this.numMaxFilas) && (p.getColumna() < this.numMaxColumnas));
	}
	
	public void intercambiarCeldas(Celda c1, Celda c2) {
		Posicion posIntercambio = c2.getPos();
		c2.setPos(c1.getPos());
		c1.setPos(posIntercambio);
		
		this.setCelda(c1.getPos(), c1);
		this.setCelda(c2.getPos(), c2);
	}

	public void actualizar() {
		for (int i = 0; i < this.numMaxFilas; i++) {
			for (int j = 0; j < this.numMaxColumnas; j++) {
				this.getCelda(new Posicion(i,j)).actuar();
			}
		}		
	}

	public void pintar(Graphics2D g) {
		for (int i = 0; i < this.numMaxFilas; i++) {
			for (int j = 0; j < this.numMaxColumnas; j++) {
				this.getCelda(new Posicion(i,j)).dibujar(g);
			}
		}	
	}

	public boolean imageUpdate(Image arg0, int arg1, int arg2, int arg3, int arg4, int arg5) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
}
