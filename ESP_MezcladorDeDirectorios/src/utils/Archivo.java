package utils;

import java.io.File;

public class Archivo extends File {

	private static final long serialVersionUID = 9125965147957807122L;
	private static final String stringAperturaOrden = "_("; // El orden se representa como "_(n)" donde n es un entero
	private static final String stringClausuraOrden = ")";  // al final del nombre del archivo
	
	private String nombre;
	private int orden; // Cuando un nombre se repite en archivos distintos, entonces se añade un orden al final 
		// del nombre, siguiendo el formato _(n) donde n = 2, 3, 4, 5...
		// Las constantes stringAperturaOrden y stringClausuraOrden definen qué caracteres encierran al orden
		// Si el nombre del archivo es único, n = 1 y no se añade _(1) al nombre
	private String extension;
	private Archivo archivoModificable;

	public Archivo(String pathname) {
		super(pathname);
		this.archivoModificable = this;
		
		// Busca el nombre, el orden y la extension
		String nombreCompleto = super.getName();
		String restoNombre;
		
		// Identifica la extension
		int posUltimoPunto = nombreCompleto.lastIndexOf(".");
		if (posUltimoPunto == -1) {
			// No tiene extensión.
			restoNombre = nombreCompleto;
			this.extension = null;
		} else if (posUltimoPunto == 0) {
			// Es sólo una extensión
			restoNombre = null;
			this.orden = 1;
			this.extension = nombreCompleto;
		} else {
			// Tiene una parte de nombre y otra de extension
			restoNombre = nombreCompleto.substring(0, posUltimoPunto);
			this.extension = nombreCompleto.substring(posUltimoPunto);
		}
		
		// Identifica el nombre y el orden
		if (restoNombre != null) {
			int posAperturaOrden = restoNombre.lastIndexOf(stringAperturaOrden);
			if (posAperturaOrden >= 0) {
				// El nombre ya incluye un orden. Hay que leerlo
				int posClausuraOrden = restoNombre.indexOf(stringClausuraOrden,posAperturaOrden);
				if (posClausuraOrden == restoNombre.length() - 1) {
					// El resto del nombre termina en _(n), con lo que es un orden correcto
					this.nombre = restoNombre.substring(0, posAperturaOrden);
					try {
						this.orden = Integer.parseInt(restoNombre.substring(posAperturaOrden + stringAperturaOrden.length(), posClausuraOrden));
					} catch (NumberFormatException e) {
						// Falsa alarma, el valor n no es un número
						this.nombre = restoNombre;
						this.orden = 1;
					}
				} else {
					// Después de _(n) sigue habiendo caracteres, así que no es un orden correcto
					this.nombre = restoNombre;
					this.orden = 1;
				}
				
			} else {
				// El nombre no incluye un orden
				this.nombre = restoNombre;
				this.orden = 1;
			}						
		} 
	}
	
	@Override
	public String toString() {
		if (this.orden == 1) {
			// Es el primero (y único) archivo con ese nombre, se olvida del orden
			return super.getParent() + File.separator + (this.nombre != null ? this.nombre : "") 
					+ (this.extension != null ? this.extension : "");
		} else {
			// Tiene un orden distinto de 1, con lo que hay que añadirlo
			return super.getParent() + File.separator + (this.nombre != null ? this.nombre : "") 
					+ stringAperturaOrden + this.orden + stringClausuraOrden 
					+ (this.extension != null ? this.extension : "");
		}
	}
	
	@Override
	public int compareTo(File pathname) {
		// TODO Auto-generated method stub
		return super.compareTo(pathname);
	}

	public String getNombreSimple() {
		return nombre;
	}
	
	public int getOrden() {
		return orden;
	}

	public Archivo adelantarOrden() {
		super.
	}
	
	public String getExtension() {
		return extension;
	}
	
	
}
