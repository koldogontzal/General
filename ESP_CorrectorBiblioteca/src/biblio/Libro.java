package biblio;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Libro implements Comparable<Libro> {
	
	private String autor;
	private String titulo;
	private int id;
	
	public static final String ETIQUETA_LIBRO = "libro";
	public static final String ETIQUETA_AUTOR = "autor";
	public static final String ETIQUETA_TITULO = "titulo";
	public static final String ETIQUETA_ID = "id";
	
	
	public Libro(String autor, String titulo) {
		this.autor = autor;
		this.titulo = this.obtenerTextoTitulo(titulo);
		this.id = this.obtenerIdTitulo(titulo);
	}

	public Libro(String autor, String titulo, int id) {
		this.autor = autor;
		this.titulo = titulo;
		this.id = id;
	}

	public Libro(File flibro) {
		this(Libro.obtenerLibro(flibro).getAutor(), Libro.obtenerLibro(flibro)
				.getTitulo(), Libro.obtenerLibro(flibro).getId());
	}

	public String getAutor() {
		return autor;
	}

	public String getTitulo() {
		return titulo;
	}
	
	public int getId() {
		return id;
	}
	
	public static Libro obtenerLibro(File flibro) {
		if (flibro.isDirectory()) {			
			return new Libro(flibro.getParentFile().getName(), flibro.getName());
		} else {			
			return null;
		}
	}

	public static Libro obtenerLibro(String slibro) {
		return Libro.obtenerLibro(new File(slibro));
	}
	
	@Override
	public String toString() {
		return "<" + Libro.ETIQUETA_LIBRO + "><" +Libro.ETIQUETA_AUTOR +">"+ this.getAutor() + "</"
				+ Libro.ETIQUETA_AUTOR + "><" + Libro.ETIQUETA_TITULO + ">"
				+ this.getTitulo() + "</" + Libro.ETIQUETA_TITULO + "><"
				+ Libro.ETIQUETA_ID + ">" + this.getId() + "</"
				+ Libro.ETIQUETA_ID + "></"
				+ Libro.ETIQUETA_LIBRO + ">";
	}

	public static Libro fromString(String xmlLibro) {
		
		Pattern patron = Pattern.compile("<" + Libro.ETIQUETA_LIBRO + ">(.*?)</" + Libro.ETIQUETA_LIBRO + ">", Pattern.DOTALL);
		Matcher matcher = patron.matcher(xmlLibro);
		if (matcher.find()) {
			// Es un libro. Separa el String con los datos
			String xmlDatos = matcher.group(1);

			// Lee los datos en variables
			String autor = null;
			String titulo = null;
			String id = null;

			// Busca el autor
			patron = Pattern.compile("<" + Libro.ETIQUETA_AUTOR + ">([^<]*)</" + Libro.ETIQUETA_AUTOR + ">");
			matcher = patron.matcher(xmlDatos);
			if (matcher.find()) {
				//System.out.println("Es un autor");
				autor = matcher.group(1);
			}

			// Busca el título
			patron = Pattern.compile("<" + Libro.ETIQUETA_TITULO + ">([^<]*)</" + Libro.ETIQUETA_TITULO + ">");
			matcher = patron.matcher(xmlDatos);
			if (matcher.find()) {
				//System.out.println("Es un titulo");
				titulo = matcher.group(1);
			}

			// Busca el ID
			patron = Pattern.compile("<" + Libro.ETIQUETA_ID + ">([^<]*)</" + Libro.ETIQUETA_ID + ">");
			matcher = patron.matcher(xmlDatos);
			if (matcher.find()) {
				//System.out.println("Es un ID");
				id = matcher.group(1);
			}
			
			if (autor != null && titulo != null && id != null) {
				return new Libro(autor, titulo, Integer.valueOf(id));
			} else {
				return null;
			}
			
		} else {
			// No es un libro		
			return null;
		}	
	}
	
	
	public static String extraeStringPrimerLibro(String xml) {
		Pattern patron = Pattern.compile("(<" + Libro.ETIQUETA_LIBRO + ">.*?</" + Libro.ETIQUETA_LIBRO + ">)", Pattern.DOTALL);
		Matcher matcher = patron.matcher(xml);
		if (matcher.find()) {
			return matcher.group(1);
		} else {
			return null;
		}
	}
	
	private String obtenerTextoTitulo(String titulo) {
		Pattern patron = Pattern.compile("(.*) \\((\\d+)\\)");
		Matcher matcher = patron.matcher(titulo);		
		matcher.find();
		if (matcher.matches()) {
			return matcher.group(1);
		} else {
			return titulo;
		}		
	}
	
	private int obtenerIdTitulo(String titulo) {
		Pattern patron = Pattern.compile("(.*) \\((\\d+)\\)");
		Matcher matcher = patron.matcher(titulo);		
		matcher.find();
		if (matcher.matches()) {
			return Integer.valueOf(matcher.group(2));
		} else {
			return 0;
		}		
	}


	@Override
	public int compareTo(Libro o) {
		if (this.getAutor().compareTo(o.getAutor()) != 0) {
			return this.getAutor().compareTo(o.getAutor());
		} else {
			return this.getTitulo().compareTo(o.getTitulo());
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.compareTo((Libro)obj) == 0);
	}
	
	public File obtenerFileLibro(File fBiblioteca) {
		// Dándole el File de la Biblioteca (directorio raíz), devuelve el File del Libro en cuestión
		String ruta = fBiblioteca.getPath() + File.separator + this.getAutor() + File.separator + this.getTitulo();
		
		if (this.getId() != 0) {
			// El libro tiene un ID, con lo que hay que añadirlo a la ruta
			ruta = ruta + " (" + this.getId() + ")";
		}
		
		return new File (ruta);
	}

}
