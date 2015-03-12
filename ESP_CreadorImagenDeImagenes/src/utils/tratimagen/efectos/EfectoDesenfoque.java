package utils.tratimagen.efectos;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import utils.ColorMod.CanalColor;
import utils.tratimagen.utils.MatrizGaussianaInteger;

public abstract class EfectoDesenfoque extends Efecto {
	
	private static final int NUM_MATRICES_GAUSSIANAS = 10;
	
	private class Nodo {
		private float sigma;
		private MatrizGaussianaInteger matriz;
		
		public Nodo(float sigma) {
			this.sigma = sigma;
			this.matriz = new MatrizGaussianaInteger(sigma);
		}
		
		public float getSigma() {
			return this.sigma;
		}
		
		public MatrizGaussianaInteger getMatriz() {
			return this.matriz;
		}
	}
	
	protected class ListaMatrices extends ArrayList<Nodo> {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 8635950639234795775L;

		public ListaMatrices(float valMin, float valMax) {
			super(NUM_MATRICES_GAUSSIANAS);
			
			for (int i = 0; i < NUM_MATRICES_GAUSSIANAS; i++) {
				float sigma = valMin + (valMax - valMin) * i / (NUM_MATRICES_GAUSSIANAS - 1);
				Nodo nodo = new Nodo(sigma);
				super.add(nodo);
			}

		}
		
		public int convolucionPorcionImagen(float sigma, BufferedImage img, int x, int y) {
			int pos = 0;
			// Busca el nodo
			while ((pos < (NUM_MATRICES_GAUSSIANAS - 2)) && (this.get(pos + 1).getSigma() < sigma)) {
				pos++;
			}
			int[][] trozoImagen0 = this.get(pos).getMatriz().getArrayIntFromBufferedImage(img, x, y);
			float sigma0 = this.get(pos).getSigma();
			int argb0 = this.get(pos).getMatriz().convolucionPorcionImagen(trozoImagen0);
			int ancho0 = this.get(pos).getMatriz().getAnchoMatriz();
			int alto0 = this.get(pos).getMatriz().getAltoMatriz();
			
			pos++;
			int ancho1 = this.get(pos).getMatriz().getAnchoMatriz();
			int alto1 = this.get(pos).getMatriz().getAltoMatriz();
			int[][] trozoImagen1;
			if ((ancho0 != ancho1) || (alto0 != alto1)) {
				trozoImagen1 = this.get(pos).getMatriz().getArrayIntFromBufferedImage(img, x, y);
			} else {
				trozoImagen1 = trozoImagen0;
			}
			float sigma1 = this.get(pos).getSigma();
			int argb1 = this.get(pos).getMatriz().convolucionPorcionImagen(trozoImagen1);
			
			int ret = 0;
			// hacer la media ponderada entre argb0 y argb1 por canales con sigma variando entre sigma0 y sigma1
			CanalColor[] canales = CanalColor.getArrayCanales();
			for (CanalColor canal : canales) {			
				int valCanal0 = (argb0 & canal.getMascara()) >> canal.getPosicionesDesplazamiento();
				if (canal == CanalColor.alfa) {
					valCanal0 = valCanal0 & 0xFF;
				}
				int valCanal1 = (argb1 & canal.getMascara()) >> canal.getPosicionesDesplazamiento();
				if (canal == CanalColor.alfa) {
					valCanal1 = valCanal1 & 0xFF;
				}
				int valDef = (this.getValorEnteroPonderado(sigma, sigma0, valCanal0, sigma1, valCanal1) & 0xFF) << canal.getPosicionesDesplazamiento();
				ret = ret | valDef;
			}

			return ret;
		}

		private int getValorEnteroPonderado(float sigma, float sigma0, int val0, float sigma1, int val1) {
			
			return (int)(val0 + (sigma - sigma0) * (val1 - val0) / (sigma1 - sigma0));
		}
		
	}
	
	protected ListaMatrices lista;
	
	public void inciarListaMatrices(float sigmaMin, float sigmaMax) {
		this.lista = new ListaMatrices(sigmaMin, sigmaMax);
	}
	
	public EfectoDesenfoque(ModulacionEfecto tipo) {
		super(tipo);
	}
/*
	public EfectoDesenfoque(ModulacionEfecto tipoModulacion, float sigmaMin, float sigmaMax) {
		super(tipoModulacion);
		this.inciarListaMatrices(sigmaMin, sigmaMax);
		//
	}
*/
}
