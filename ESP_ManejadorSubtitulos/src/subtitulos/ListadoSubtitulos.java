package subtitulos;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import tiempo.Instante;

public class ListadoSubtitulos extends ArrayList<Subtitulo> {
	
	public static final int CODIFICACION_UTF8 = 1001;
	public static final int CODIFICACION_ASCII = 1002;
	
	private static final long serialVersionUID = 1517901967278274228L;
	
	private String archivo;	
	private LectorDeArchivos lectorDeArchivos;

	
	public ListadoSubtitulos(String archivo, int tipoCodificacion) {
		super(600);		
		this.archivo = archivo;
		
		if (tipoCodificacion == CODIFICACION_UTF8) {
			this.lectorDeArchivos = new LectorDeArchivosUTF8(archivo);
		} else if (tipoCodificacion == CODIFICACION_ASCII) {
			this.lectorDeArchivos = new LectorDeArchivosASCII(archivo);
		} else {
			this.lectorDeArchivos = null;
		}	
		
		if (this.esArchivo("srt")) {
			this.leeArchivoSRT();
		} else if (this.esArchivo("sub")) {
			this.leeArchivoSUB();
		} else if (this.esArchivo("ssa")) {
			this.leeArchivoSSA();
		} else {
			System.out.println(archivo + ": Archivo con formato de subtítulos desconocido.");
		}
			
	}
	
	public void adelantarTiempos(long cantidad, int unidadTiempo) {
		Iterator i = super.iterator();
		while (i.hasNext()) {
			Subtitulo sub = (Subtitulo)i.next();
			sub.adelantarTiempos(cantidad, unidadTiempo);
		}
	}
	
	public void multiplicarFactorTiempos(double factor) {
		Iterator i = super.iterator();
		while (i.hasNext()) {
			Subtitulo sub = (Subtitulo)i.next();
			sub.multiplicarFactorTiempos(factor);
		}
	}
	
	public void convertirASCII7bits() {
		Iterator i = super.iterator();
		while (i.hasNext()) {
			Subtitulo sub = (Subtitulo)i.next();
			sub.convertirTextoFormato7bits();
		}
	}
	
	public boolean grabarArchivoSubtitulos(String archivo) {
		this.archivo = archivo;
		
		if (this.esArchivo("srt")) {
			this.grabarArchivoSubtitulosSRT();
		} else if (this.esArchivo("sub")){
			this.grabarArchivoSubtitulosSUB(23.976);
		} else {
			System.out.println(archivo + ": Extensión de archivo desconocida");
			return false;
		}
		return true;
	}

	public void grabarArchivoSubtitulosSUB(double fps) {
		// TODO Auto-generated method stub
		
	}

	private void grabarArchivoSubtitulosSRT() {
		
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(this.archivo);
			int contador = 1;
			Iterator i = super.iterator();
			while (i.hasNext()) {
				Subtitulo sub = (Subtitulo)i.next();
				pw.write(sub.toStringSRT(contador) + "\n\n");
				contador++;
			}
		} catch (IOException e) {
			System.out.println(this.archivo + ": Error abriendo el archivo para escritura.");
		} finally {
			if (pw != null) {
				pw.close();
			}
		}		
	}

	private void leeArchivoSSA() {
		// TODO Auto-generated method stub
		
	}

	private void leeArchivoSUB() {
		if (this.lectorDeArchivos != null) {
			Iterator i = this.lectorDeArchivos.iterator();
			
			// La primera linea tiene la cadena "{1}{1}" y despues el numero de fps
			String linea;
			double fps = 0.0;
			if (i.hasNext()) {
				linea = (String)i.next();
				if ((linea.length() > 6) && linea.substring(0, 6).equals("{1}{1}")) {
					fps = Double.parseDouble(linea.substring(6, linea.length()));
				}
			}
			
			// Sigue leyendo el resto de lineas que son los subtitulos
			if (fps != 0.0) {
				while (i.hasNext()) {
					linea = (String)i.next();
					
					int posAbrir = linea.indexOf("{");
					int posCerrar = linea.indexOf("}");
					Instante i0 = this.analizarInstanteSUB(linea.substring(posAbrir + 1, posCerrar), fps);
					
					posAbrir = linea.indexOf("{", posCerrar + 1);
					posCerrar = linea.indexOf("}", posCerrar + 1);					
					Instante i1 = this.analizarInstanteSUB(linea.substring(posAbrir + 1, posCerrar), fps);
					
					String texto = linea.substring(posCerrar + 1, linea.length());
					texto = texto.replace('|', '\n');
					
					Subtitulo sub = new Subtitulo(i0, i1, texto);
					super.add(sub);
				}	
			}
		}	
	}

	private Instante analizarInstanteSUB(String numero, double fps) {
		try {
			long frame = Long.parseLong(numero);
			long milisegundos = (long)((frame * 1000L) / fps);
			Instante i = new Instante();
			i.modificar(milisegundos, Instante.MILISEGUNDOS);
			return i;
		} catch (NumberFormatException e) {
			return Instante.INSTANTE_NO_VALIDO;
		}
	}

	private void leeArchivoSRT() {
		if (this.lectorDeArchivos != null) {
			// Busca las lineas vacias para identificar bloques (== lineas entre 2 lineas vacias)
			int [] posicionesLineasVacias = new int[this.lectorDeArchivos.size()];
			int altura = 0;
			Iterator i = this.lectorDeArchivos.iterator();
			int posIterator = -1;
			while (i.hasNext()) {				
				String linea = (String)i.next();
				posIterator++;
				if (this.esLineaVacia(linea)) {
					posicionesLineasVacias[altura] = posIterator;
					altura++;
				}
			}
			
			// Busca los bloques que sí pueden contener subtitulos correctos
			//		Primer bloque
			if (posicionesLineasVacias[0] != 0) {
				this.analizaBloqueSRT(0, posicionesLineasVacias[0] - 1);
			}
			//		Bloques del medio
			for (int pos = 0; pos < altura - 1; pos++) {
				this.analizaBloqueSRT(posicionesLineasVacias[pos] + 1, posicionesLineasVacias[pos + 1] - 1);
			}
			//		Ultimo bloque
			if (posicionesLineasVacias[altura - 1] < super.size() - 1) {
				this.analizaBloqueSRT(posicionesLineasVacias[altura - 1] + 1, super.size() - 1);
			}			
		}		
	}
	
	private void analizaBloqueSRT(int l0, int l1) {
		Instante i0 = null;
		Instante i1 = null;
		String texto = "";
		
		int tamLineasBloque = l1 - l0;
		if (tamLineasBloque >= 2) {		
			boolean esBloqueCorrecto = true;
			// Comprobaciones de formato del bloque
			//		La primera linea es un numero entero
			if (Integer.parseInt(this.lectorDeArchivos.get(l0)) < 1) {
				esBloqueCorrecto = false;
			}
			// 		La segunda linea tiene el instante de incio y el de fin
			String lineaTiempos = this.lectorDeArchivos.get(l0 + 1);
			if (lineaTiempos.length() < 29) {
				esBloqueCorrecto = false;
			} else {
				i0 = Instante.parseInstante(lineaTiempos.substring(0, 12));
				if (i0.equals(Instante.INSTANTE_NO_VALIDO)) {
					esBloqueCorrecto = false;
				}
				i1 = Instante.parseInstante(lineaTiempos.substring(17, 29));
				if (i1.equals(Instante.INSTANTE_NO_VALIDO)) {
					esBloqueCorrecto = false;
				}
			}
			// 		La tercera linea y siguiente, tienen el texto
			for (int i = l0 + 2; i <= l1; i++) {
				texto = texto + this.lectorDeArchivos.get(i);
				if (i != l1) {
					texto = texto + "\n";
				}
			}
			
			// Si todo es correcto, crea el Subtitulo
			if (esBloqueCorrecto) {
				Subtitulo sub = new Subtitulo(i0, i1, texto);
				super.add(sub);
			}
		}
	}

	private boolean esLineaVacia(String s) {
		if (s.length() == 0) {
			return true;
		} else {
			boolean ret = true;
			for (int i = 0; i < s.length(); i++) {
				char c = s.charAt(i);
				if (c != '\n' && c != '\r' && c != ' ' && c !='\t') {
					ret = false;
				}
			}
			return ret;
		}
	}

	private boolean esArchivo(String extension) {
		int longitud = this.archivo.length();
		if (longitud < 5) {
			return false;
		} else {
			String extArchivo = this.archivo.substring(longitud - 3, longitud);
			return (extArchivo.compareToIgnoreCase(extension) == 0);
		}		
	}
	
	@Override
	public String toString() {
		String ret = "";
		Iterator i = super.iterator();
		while (i.hasNext()) {
			Subtitulo sub = (Subtitulo)i.next();
			ret = ret + sub + "\n\n";
		}
		return ret;
	}
}
