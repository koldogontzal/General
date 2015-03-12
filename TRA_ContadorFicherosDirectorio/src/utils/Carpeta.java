package utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

import utils.Registro.TipoRegistro;

import except.NotCarpetaException;
import except.NotRegistroException;

public class Carpeta {
	
	private static final String TEXTO_TABULADOR = "   ";
	private String[] nombreMeses = { "ENERO", "FEBRERO", "MARZO", "ABRIL",
			"MAYO", "JUNIO", "JULIO", "AGOSTO", "SEPTIEMBRE",
			"OCTUBRE", "NOVIEMBRE", "DICIEMBRE" };	

	private enum TipoListado {
		INICIAL, ITERATIVO
	};

	
	private int nivel;
	private File directorio;
	
	private ArrayList<Carpeta> listaSubCarpetas;
	private ArrayList<Registro> listaRegEntrada;
	private ArrayList<Registro> listaRegSalida;
	private ArrayList<File> listaArchivosDesconocidos;


	public Carpeta(String dir, int nivel) throws NotCarpetaException {
		this.directorio = new File(dir);
		if (!this.directorio.isDirectory()) {
			throw new NotCarpetaException("ERROR: La carpeta "
					+ this.directorio.getPath() + " no es un directorio válido");
		}
		this.nivel = nivel;
		
		// Inicializa las listas
		this.listaSubCarpetas = new ArrayList<Carpeta>(10);
		this.listaRegEntrada = new ArrayList<Registro>(10);
		this.listaRegSalida = new ArrayList<Registro>(10);
		this.listaArchivosDesconocidos = new ArrayList<File>(10);
		
		// Rellena las listas
		File[] listadoFiles = this.directorio.listFiles();
		for (File archivo : listadoFiles) {
			try {
				Carpeta subCarpeta = new Carpeta(archivo, nivel + 1);
				// Si no da exception, entonces es una subcarpeta
				this.listaSubCarpetas.add(subCarpeta);
			} catch (NotCarpetaException e1) {
				// No se trataba de una carpeta, vemos que tipo de registro es:
				try {
					Registro reg = new Registro(archivo);
					// Si llega aqui, entonces si es un registro de entrada o salida
					if (reg.getTipoRegistro() == TipoRegistro.ENTRADA) {
						this.listaRegEntrada.add(reg);
					} else {
						this.listaRegSalida.add(reg);
					}
					
				} catch (NotRegistroException e2) {
					// No se trata de un registro ni de entrada ni de salida
					this.listaArchivosDesconocidos.add(archivo);
				}
			}			
		}
	}

	public Carpeta(File dir, int nivel) throws NotCarpetaException {
		this(dir.getPath(), nivel);
	}


	public int getNumSubcarpetas() {
		return this.listaSubCarpetas.size();
	}
	
	public int getNumRegEntrada() {
		int numRegEntradaSubcarpetas = 0;
		for (Carpeta subcarpeta : this.listaSubCarpetas) {
			numRegEntradaSubcarpetas = numRegEntradaSubcarpetas + subcarpeta.getNumRegEntrada();
		}
		return numRegEntradaSubcarpetas + this.listaRegEntrada.size();
	}

	public int getNumRegSalida() {
		int numRegSalidaSubcarpetas = 0;
		for (Carpeta subcarpeta : this.listaSubCarpetas) {
			numRegSalidaSubcarpetas = numRegSalidaSubcarpetas + subcarpeta.getNumRegSalida();
		}
		return numRegSalidaSubcarpetas + this.listaRegSalida.size();
	}
	
	public int getNumFichDesconocidos() {
		int numArchDescSubcarpetas = 0;
		for (Carpeta subcarpeta : this.listaSubCarpetas) {
			numArchDescSubcarpetas = numArchDescSubcarpetas + subcarpeta.getNumFichDesconocidos();
		}
		return numArchDescSubcarpetas + this.listaArchivosDesconocidos.size();
	}
	
	public int[] getMesesRegEntrada() {
		int[] ret = this.getArrayAcumuladoMeses(this.listaRegEntrada);
		for (Carpeta subcarpeta : this.listaSubCarpetas) {
			ret = this.sumaArraysMeses(ret, subcarpeta.getMesesRegEntrada());
		}
		return ret;
	}
	
	public int[] getMesesRegSalida() {
		int[] ret = this.getArrayAcumuladoMeses(this.listaRegSalida);
		for (Carpeta subcarpeta : this.listaSubCarpetas) {
			ret = this.sumaArraysMeses(ret, subcarpeta.getMesesRegSalida());
		}
		return ret;
	}
	
	public int[] getMesesFichDescon() {
		int[] ret = this.getArrayAcumuladoMeses(this.listaArchivosDesconocidos);
		for (Carpeta subcarpeta : this.listaSubCarpetas) {
			ret = this.sumaArraysMeses(ret, subcarpeta.getMesesFichDescon());
		}
		return ret;
	}


	@Override
	public String toString() {
		return this.toString(TipoListado.INICIAL);
	}
	
	
	public String toString(TipoListado modo) {		
		String ret = "";
		
		if (modo == TipoListado.INICIAL) {
			// La primera linea, la fecha actual
			Date fechaActual = new Date();
			SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy, HH:mm:ss");
			ret = "Fecha del analisis del arbol de carpetas: "
					+ dateformat.format(fechaActual) + "\n\n";
		}
		
		// Genera el tabulador necesario, según el nivel
		String tab = "";
		for (int i = 0; i < this.nivel; i++) {
			tab = tab + Carpeta.TEXTO_TABULADOR;
		}
		
		// La segunda linea, el nombre del directorio y los numeros de registros que contiene el y sus subdirectorios
		ret = ret + tab + "-" + this.directorio.getName() + " (subdirectorios incluidos: "
				+ this.getNumRegEntrada() + " reg. entrada / " + this.getNumRegSalida() + " reg. salida / "
				+ this.getNumFichDesconocidos() + " fich. desconocidos)\n";
		
		// En tercer lugar, el análisis por meses
		int[] numRegEntrada = this.getMesesRegEntrada();
		int[] numRegSalida = this.getMesesRegSalida();
		int[] numFichDesc = this.getMesesFichDescon();
		ret = ret
				+ tab
				+ this.formateoTablaTabulada("MES:", "ENTRADA:", "SALIDA:",
						"DESCON.:", 15) + "\n";
		for (int i = 0; i < 12; i++) {
			ret = ret
					+ tab
					+ this.formateoTablaTabulada(nombreMeses[i], ""
							+ numRegEntrada[i], "" + numRegSalida[i], ""
							+ numFichDesc[i], 15) + "\n";
		}

		// Mete un salto de lines entre directorios
		ret = ret + "\n";
		
		// En cuarto lugar, llamadas recursivas a las subcarpetas
		for (Carpeta subcarpeta : this.listaSubCarpetas) {
			ret = ret + subcarpeta.toString(TipoListado.ITERATIVO);
		}

		return ret;

	}
	

		
	
	private String formateoTablaTabulada(String s1, String s2, String s3,
			String s4, int t) {
		return this.ajustarString(s1, t) + this.ajustarString(s2, t)
				+ this.ajustarString(s3, t) + this.ajustarString(s4, t);
	}

	private String ajustarString(String s1, int t) {
		String derecha = "";
		for (int i = 0 ; i < t; i++) {
			derecha = derecha + " ";
		}
		String ret = s1 + derecha;
		return ret.substring(0, t);
	}

	@SuppressWarnings("unchecked")
	private int[] getArrayAcumuladoMeses(ArrayList lista) {
		int[] ret = new int[12];
		
		Iterator i = lista.iterator();
		while (i.hasNext()) {
			File archivo = (File)i.next();
			long fecha_l = archivo.lastModified();
			Date fecha_d = new Date(fecha_l);
			Calendar c = new GregorianCalendar();
			c.setTime(fecha_d);
			int mes = c.get(Calendar.MONTH);

			ret[mes]++;			
		}
		
		/*
		
		File[] listado = this.directorio.listFiles();
		for (File archivo : listado) {
			
			try {
				// Se trata de un subdirectorio, hace llamada recursiva
				Carpeta c = new Carpeta(archivo, this.nivel + 1);
				int[] acumuladoSubdir = c.getArrayAcumuladoMeses();
				for (int i = 0; i < 12; i++) {
					ret[i] = ret[i] + acumuladoSubdir[i];
				}
			} catch (NotCarpetaException e) {
				// Si salta una excepción, no es un directorio, puede ser un archivo
				if (archivo.isFile()) {
					long fecha_l = archivo.lastModified();
					Date fecha_d = new Date(fecha_l);
					Calendar c = new GregorianCalendar();
					c.setTime(fecha_d);
					int mes = c.get(Calendar.MONTH);

					ret[mes]++;
				}
			}
		}
		*/
		return ret;
	}

	private int[] sumaArraysMeses(int[] arr1, int[] arr2) {
		int [] ret = new int[12];
		for (int i = 0; i < 12; i++) {
			ret[i] = arr1[i] + arr2[i];
		}
		return ret;
	}


}
