package utils.tratimagen.utils;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import utils.ColorMod.CanalColor;

public class MatrizGaussianaInteger {
	
	protected class Valor implements Comparable<Valor> {
		
		private int valor;
		private int posX;
		private int posY;
		private int centroX;
		private int centroY;
		
		public Valor(int valor, int posX, int posY, int ancho, int alto) {
			this.valor = valor;
			this.posX = posX;
			this.posY = posY;
			this.centroX = (ancho - 1) / 2;
			this.centroY = (alto - 1) / 2;
		}

		@Override
		public int compareTo(Valor o) {
			if (o.valor - this.valor != 0)
				return o.valor - this.valor;
			else {
				return this.distCentro() - o.distCentro();
			}
		}
		
		private int distCentro() {
			return (this.posX - this.centroX) * (this.posX - this.centroX) + (this.posY - this.centroY) * (this.posY - this.centroY);
		}

		public int getPosX() {
			return posX;
		}


		public int getPosY() {
			return posY;
		}		
		
		public int getValor() {
			return valor;
		}
		
		@Override
		public String toString() {
			return this.valor + " ("+ this.posX +", " + this.posY+ ")";
		}
	}
	
	protected class ValoresOrdenados extends ArrayList<Valor> {
		

		/**
		 * 
		 */
		private static final long serialVersionUID = -6299870597418669173L;

		public ValoresOrdenados() {
			super();
		}
		
		public void ordenarLista() {
			Collections.sort(this);
		}
		
		@Override
		public String toString() {
			String ret = "Lista de valores\n";
			Iterator <Valor> i = this.iterator();
			while (i.hasNext()) {
				Valor v = i.next();
				ret = ret + v + "\n";
			}
			return ret;
		}

		
	}
	
	private int posX;
	private int posY;
	
	private int log2Resolucion;

	private int resolucion;
	
	private float sigma;
	private double factorAmplitud;
	private double factorExponente;
	
	private int[][] kernel;

	public MatrizGaussianaInteger(float sigma) {
		if (sigma > 0.8f) {
		// Calcula la resolución (suma) de la función Gaussiana
		this.log2Resolucion = 6;
		this.sigma = sigma;
		int radioTemp = (int) (4 * sigma);
		int anchoTemp = 1 + 2 * radioTemp;
		int[][] valTemp = new int[anchoTemp][anchoTemp];		
		this.factorExponente = -1 / (2.0 * sigma * sigma);

		boolean repetir = false;
		do {
			this.resolucion = (int) Math.pow(2, this.log2Resolucion);
			this.factorAmplitud = this.resolucion / (2.0 * Math.PI * sigma * sigma);

			for (int x = 0; x < anchoTemp; x++) {
				for (int y = 0; y < anchoTemp; y++) {
					valTemp[y][x] = this.funcionGaussianaInteger(x - radioTemp, y - radioTemp);
				}
			}
			
			if (!this.validarValoresMatriz(valTemp) && (this.log2Resolucion < 23)) {
				repetir = true;
				this.log2Resolucion++;
			} else {
				repetir = false;
			}
		} while (repetir);
		valTemp = this.corregirValoresMatriz(valTemp);
		
		this.kernel = this.recortarTamagnoMatriz(valTemp);
		
		this.asignaPosicionValorMayor();
		} else {
			this.log2Resolucion = 0;
			this.sigma = sigma;
			this.posX = 0;
			this.posY = 0;
			this.kernel = new int[1][1];
			this.kernel[0][0] = 1;
		}
		
	}
	
	private boolean validarValoresMatriz(int[][] valores) {
		int suma = 0;
		for (int y = 0; y < valores.length; y++) {
			for (int x = 0; x < valores[y].length; x++) {
				suma = suma + valores[y][x];
			}
		}

	
		if (suma < this.resolucion) {
			return (((this.resolucion - suma) / (1f * this.resolucion)) < 0.25f ? true : false);
		} else {
			return true;
		}
	}

	private void asignaPosicionValorMayor() {		
		this.posX = (this.kernel[0].length - 1) / 2;
		this.posY = (this.kernel.length - 1) / 2;
		
	}

	private int[][] recortarTamagnoMatriz(int[][] valTemp) {
		// Busca la primer fila no vacia
		int y = 0;
		int x = 0;
		int sumaFila = 0;
		do {
			for (x = 0; x < valTemp[y].length; x++) {
				sumaFila = sumaFila + valTemp[y][x];
			}
			y++;
		} while (sumaFila == 0);
		int yMin = y - 1;
		
		// Busca la ultima fila no vacia
		y = valTemp.length - 1;
		sumaFila = 0;
		do {
			for (x = 0; x < valTemp[y].length; x++) {
				sumaFila = sumaFila + valTemp[y][x];
			}
			y--;
		} while (sumaFila == 0);
		int yMax = y + 1;
		
		// Busca la primer columna no vacia
		x = 0;
		int sumaColumna = 0;
		do {
			for (y = 0; y < valTemp.length; y++) {
				sumaColumna = sumaColumna + valTemp[y][x];
			}
			x++;
		} while (sumaColumna == 0);
		int xMin = x - 1;

		// Busca la ultima columna no vacia
		x = valTemp[0].length - 1;
		sumaColumna = 0;
		do {
			for (y = 0; y < valTemp.length; y++) {
				sumaColumna = sumaColumna + valTemp[y][x];
			}
			x--;
		} while (sumaColumna == 0);
		int xMax = x + 1;
		
		
		
		int[][] ret = new int[1 + yMax - yMin][1 + xMax - xMin];
		for (x = xMin; x <= xMax; x++) {
			for (y = yMin; y <= yMax; y++) {
				ret[y - yMin][x -xMin] = valTemp[y][x];
			}
		}
		
		return ret;
	}

	private int[][] corregirValoresMatriz(int[][] valTemp) {
		ValoresOrdenados lista = new ValoresOrdenados();
		int integral = 0;
		
		for (int y = 0; y < valTemp.length; y++) {
			for (int x = 0; x < valTemp[y].length; x++) {
				if (valTemp[y][x] != 0) {
					integral = integral + valTemp[y][x];
					lista.add(new Valor(valTemp[y][x], x, y, valTemp[y].length, valTemp.length));
				}
			}
		}
		
		lista.ordenarLista();
		
		int pos = 0;
		while (integral != this.resolucion) {
			while (valTemp[lista.get(pos).getPosY()][lista.get(pos).getPosX()] == 0) {
				pos++;
				if (pos == lista.size()) {
					pos = 0;
				}
			}
			if (integral > this.resolucion) {
				valTemp[lista.get(pos).getPosY()][lista.get(pos).getPosX()]--;
				integral--;
			} else {
				valTemp[lista.get(pos).getPosY()][lista.get(pos).getPosX()]++;
				integral++;
			}
			pos++;
			if (pos == lista.size()) {
				pos = 0;
			}
			
		}
		return valTemp;
	}

	private int funcionGaussianaInteger(int x, int y) {
		return (int)(this.factorAmplitud * Math.exp(this.factorExponente * (x * x + y * y )));
	}


	
	@Override
	public String toString() {
		// estadisticas
		int suma = 0;
		int numCeros = 0;
		int max = 0;
		for (int y = 0; y < this.kernel.length; y++) {
			for (int x = 0; x < this.kernel[y].length; x++) {
				if (this.kernel[y][x] == 0) {
					numCeros++;
				} else {
					suma = suma + this.kernel[y][x];
					if (this.kernel[y][x] > max) {
						max = this.kernel[y][x];
					}
				}
			}
		}
		String ret = "";
		
		for (int y = 0; y < this.kernel.length; y++) {
			for (int x = 0; x < this.kernel[y].length; x++) {
				ret = ret + this.kernel[y][x] + "\t";
			}
			ret = ret + "\n";
		}
		
		ret = ret + "Matriz de " + this.kernel[0].length + "x" + this.kernel.length + " (" + this.kernel[0].length * this.kernel.length + ") PosX " + this.posX + ", PosY " + this.posY + "\n";
		ret = ret + "Integral: " + suma + "\n";
		ret = ret + "Num de ceros: " + numCeros + "\n";
		ret = ret + "Valor máximo: " + max + "\n";

		return ret;
	}
	
	
	public int getLog2Resolucion() {
		return log2Resolucion;
	}
	
	
	
	public float getSigma() {
		return sigma;
	}

	public int getAnchoMatriz() {
		return this.kernel[0].length;
	}
	
	public int getAltoMatriz() {
		return this.kernel.length;
	}
	
	public int[][] getArrayIntFromBufferedImage(BufferedImage img, int x, int y) {
		int[][] ret = new int[this.kernel.length][this.kernel[0].length];
		x = Math.min(img.getWidth() - 1, Math.max(0, x));
		y = Math.min(img.getHeight() - 1, Math.max(0, y));
		
		int xMin = x - this.posX;
		int xMax = xMin + this.kernel[0].length;
		int yMin = y - this.posY;
		int yMax = yMin + this.kernel.length;
		
		for (int yRel = yMin; yRel < yMax; yRel++) {
			for (int xRel = xMin; xRel < xMax; xRel++) {
				int xFin = Math.min(img.getWidth() - 1, Math.max(0, xRel));
				int yFin = Math.min(img.getHeight() - 1, Math.max(0, yRel));
				ret[yRel - yMin][xRel - xMin] = img.getRGB(xFin, yFin);
			}
		}
		
		return ret;
	}
	
	
	public int convolucionPorcionImagen(int[][] porcionImagen) {
		int ret = 0;
		int [] sumatorioPorCanal = new int[4];
		CanalColor[] canales = CanalColor.getArrayCanales();
		if ((porcionImagen.length == this.kernel.length) && (porcionImagen[0].length == this.kernel[0].length)) {
			for (int y = 0; y < this.kernel.length; y++) {
				for (int x = 0; x < this.kernel[y].length; x++) {
					for (CanalColor canal : canales) {
						int posCanal = canal.getPosicion();				
						int valCanal = (porcionImagen[y][x] & canal.getMascara()) >> canal.getPosicionesDesplazamiento();
						if (canal == CanalColor.alfa) {
							valCanal = valCanal & 0xFF;
						}
						sumatorioPorCanal[posCanal] = sumatorioPorCanal[posCanal] + valCanal * this.kernel[y][x];
					}
				}
			}
		}
		
		for (int i = 0; i < 4; i++) {
			sumatorioPorCanal[i] = sumatorioPorCanal[i] >> this.log2Resolucion;
		}
		
		for (CanalColor canal : canales) {
			ret = ret | (sumatorioPorCanal[canal.getPosicion()] << canal.getPosicionesDesplazamiento());
		}
		
		return ret;
	}

}
