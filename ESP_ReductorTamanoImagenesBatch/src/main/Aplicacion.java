package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

//import com.mortennobel.imagescaling.ResampleOp; // Antigua biblioteca para escalar imágenes



public class Aplicacion {
	
	private File dir; // Directorio con las imágenes a reducir
	private TiposReduccion tipo; // Tipo de reduccion
	private int tam; // Tamaño de las nuevas imágenes
	
	public static enum TiposReduccion {
		LadoMaximo, Ancho, Alto
	}
	
	public Aplicacion(String dir, int tam) {
		this(dir, TiposReduccion.LadoMaximo, tam);
	}
	
	
	public Aplicacion(String dir, TiposReduccion tipo, int tam) {
		this.dir = new File(dir);
		this.tipo = tipo;
		this.tam = tam;
		
		// Recorre el directorio
		File[] listado = this.dir.listFiles();
		int numArchivosTratados = 0;
		int numArchivosTotal = listado.length;
		for (File archivo : listado) {
			if (this.esImagen(archivo)) {
				BufferedImage orig = null;
				try {
					orig = ImageIO.read(archivo);
				} catch (IOException e) {
					break;
				}
				
				int anchoOrig = orig.getWidth();
				int altoOrig = orig.getHeight();
				int anchoReduc, altoReduc;
				
				// Calcula las nuevas medidas
				switch (this.tipo) {				
				case Ancho:
					anchoReduc = this.tam;
					altoReduc = this.tam * altoOrig / anchoOrig;
					break;
				case Alto:
					altoReduc = this.tam;
					anchoReduc = this.tam * anchoOrig / altoOrig;
					break;		
				default:
				case LadoMaximo:
					if (anchoOrig > altoOrig) {
						anchoReduc = this.tam;
						altoReduc = this.tam * altoOrig / anchoOrig;
					} else {
						altoReduc = this.tam;
						anchoReduc = this.tam * anchoOrig / altoOrig;
					}
					break;
				}
				/*
				 * Modelo antiguo: usa la librería de mortennobel y graba con Java
				 * 
				// Genera la nueva imagen y la graba
				ResampleOp resampleOp = new ResampleOp(anchoReduc, altoReduc);
				BufferedImage modificada = resampleOp.filter(orig, null);
				
				try {
					ImageIO.write(modificada, "jpeg", archivo);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				*/
				
				 
				// Genera la nueva imagen y la graba
				// Usa la librería de coobird tanto para escalar como para grabar en JPG
				try {
					Thumbnails.of(orig)
					        .size(anchoReduc, altoReduc)
					        .toFile(archivo);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				

			}
			numArchivosTratados++;
			System.out.println("Hecho el " + (int)100*numArchivosTratados/numArchivosTotal + "%");
			
		}

	}

	private boolean esImagen(File archivo) {
		boolean ret;
		try {
			ImageIO.read(archivo);
			ret = true;
		} catch (IOException e) {
			ret = false;
		}
		return ret;
	}

	public static void main(String[] args) {
		new Aplicacion("C:\\Documents and Settings\\lcastellano\\Escritorio\\Pruebas",
				TiposReduccion.Alto,
				150);
	}
	
}
