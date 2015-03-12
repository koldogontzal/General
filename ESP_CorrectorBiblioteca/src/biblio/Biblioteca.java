package biblio;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lector.LectorArchivosTexto;
import escritor.EscritorArchivosTexto;

public class Biblioteca extends ArrayList<Libro>{
	
	private static final long serialVersionUID = -7442532738345372447L;
	private Date fecha = new Date();
	
	
	public static final String ETIQUETA_BIBLIOTECA = "biblioteca";	
	public static final String ETIQUETA_FECHA = "fecha";
	

	public Biblioteca() {
		super(500);
	}
	
	public Biblioteca(String sbiblioteca) {
		// Lee una biblioteca a partir del String de la ruta del directorio que la contiene
		this(new File(sbiblioteca));	
	}
	
	public Biblioteca(File fbiblioteca) {
		// Lee una biblioteca a partir del File de la ruta del directorio que la contiene
		this();
		if (fbiblioteca.isDirectory()) {
			File [] fautores = fbiblioteca.listFiles();
			for (File fautor:fautores) {
				if (fautor.isDirectory()) {
					File [] flibros = fautor.listFiles();
					for (File flibro:flibros) {
						if (flibro.isDirectory()) {
							super.add(new Libro(flibro));
						}
					}
				}
			}
		} else {
			System.err.println("ERROR: La ruta "+fbiblioteca.getPath() +" no se trata de una ruta válida para una Biblioteca.");
		}
	}
	
	@Override
	public String toString() {
		// Devuelve un String pseudo XML con la Biblioteca
		String ret = "<" + Biblioteca.ETIQUETA_BIBLIOTECA + ">\n";

		// Añade la fecha. Fija un formato de fecha dado para luego poder leerlo del fichero
		if (this.fecha != null) {
			DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
					DateFormat.MEDIUM, Locale.US);
			ret = ret + "<" + ETIQUETA_FECHA + ">" + df.format(this.fecha)
					+ "</" + ETIQUETA_FECHA + ">\n";
		}

		// Bucle con todos los libros
		Iterator<Libro> i = super.iterator();
		while (i.hasNext()) {
			ret = ret + i.next() + "\n";
		}

		// Devuelve la pseudo cadena XML
		ret = ret + "</" + Biblioteca.ETIQUETA_BIBLIOTECA + ">\n";
		return ret;
	}

	public static Biblioteca fromString(String xmlBiblio) {
		Pattern patron = Pattern.compile("<" + Biblioteca.ETIQUETA_BIBLIOTECA + ">(.*?)</" + Biblioteca.ETIQUETA_BIBLIOTECA + ">", Pattern.DOTALL);
		Matcher matcher = patron.matcher(xmlBiblio);
		if (matcher.find()) {
			// Se trata de una Biblioteca. Extrae los datos
			String xmlDatos = matcher.group(1);
			Biblioteca ret = new Biblioteca();
			ret.setFecha(null);
			// Primero, busca y extrae la fecha
			Pattern patFecha = Pattern.compile("<" + Biblioteca.ETIQUETA_FECHA + ">(.*?)</" + Biblioteca.ETIQUETA_FECHA + ">");
			Matcher matFecha = patFecha.matcher(xmlDatos);
			if (matFecha.find()) {
				DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
						DateFormat.MEDIUM, Locale.US);
				try {
					ret.setFecha(df.parse(matFecha.group(1)));
				} catch (ParseException e) {
					ret.setFecha(null);
				}
			}
			Pattern patFecha2 = Pattern.compile("(<" + Biblioteca.ETIQUETA_FECHA + ">.*?</" + Biblioteca.ETIQUETA_FECHA + ">)");
			Matcher matFecha2 = patFecha2.matcher(xmlDatos);
			if (matFecha2.find()) {
				int tamFecha = matFecha2.group(1).length();
				xmlDatos = xmlDatos.substring(tamFecha + 2);
			}
			
			// Bucle para leer libro a libro
			String libroExtraido = Libro.extraeStringPrimerLibro(xmlDatos);
			while (libroExtraido != null) {
				Libro lib = Libro.fromString(libroExtraido);

				
/*	// Para debugging 			
if (lib.getId()==809 || lib.getId()==807) {
	System.out.println("TEXTO: " + libroExtraido);
	System.out.println("LIBRO: "+ lib); 
	System.out.println("LONGITUD TEXTO: "+ libroExtraido.length());
	System.out.println("DATOS: " + xmlDatos.substring(0, 150) + "...");
	System.out.println("LONGITUD DATOS: " + xmlDatos.length());
	System.out.println("LONGITUD DATOS QUITADO TEXTO: " + xmlDatos.substring(libroExtraido.length()).length());
	System.out.println("SIGUIENTE LIBRO: " +  Libro.extraeStringPrimerLibro(xmlDatos));
	System.out.println("-----");
}
*/			
				ret.add(lib);
				xmlDatos = xmlDatos.substring(libroExtraido.length() + 1); // Quita 1 caracter más: el retorno de línea
				libroExtraido = Libro.extraeStringPrimerLibro(xmlDatos);
			}
			
			return ret;
		} else {		
			return null;
		}
	}
	
	private void setFecha(Date fecha) {
		this.fecha = fecha;		
	}
	

	public Biblioteca subBibliotecaRepetidos() {
		// Devuelve una Biblioteca con los libros repetidos (el mismo Autor y el mismo Título) de esta Biblioteca
		Biblioteca ret = new Biblioteca();
		
		for (int i = 0; i < this.size(); i++) {
			boolean encontrado = false;
			for (int j = i + 1; (j < this.size() && !encontrado); j++) {
				if (this.get(i).compareTo(this.get(j)) == 0) {
					ret.add(this.get(i));
					ret.add(this.get(j));
					encontrado = true;
				}
			}
		}
		
		return ret;
	}
	
	public Biblioteca subBibliotecaLibrosSinID() {
		// Devuelve una Biblioteca con los libros que no tienen ID de esta Biblioteca
		Biblioteca ret = new Biblioteca();
		
		Iterator<Libro> i = this.iterator();
		while (i.hasNext()) {
			Libro lib = i.next();
			if (lib.getId() == 0) {
				ret.add(lib);
			}
		}
		
		return ret;
	}
	
	public boolean tieneRepetidos() {
		// Boolean que es cierto cuando en la Biblioteca hay algún libro repetido (el mismo Autor y el mismo Título)
		Biblioteca aux = this.subBibliotecaRepetidos();
		return !aux.isEmpty();
	}

	public boolean exportarFichero(String sfile) {
		// Exporta la Biblioteca a un fichero de texto XML
		return EscritorArchivosTexto.createFile(
				sfile,
				"<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"
						+ this.toString());
	}

	public static Biblioteca importarFichero(String sfile) {
		// Importa una Biblioteca de un fichero de texto XML
		String xml = LectorArchivosTexto.readFile(sfile, LectorArchivosTexto.MODO_UTF8);
		return Biblioteca.fromString(xml);
	}
}

