/**
 * Curso B�sico de desarrollo de Juegos en Java - Invaders
 * 
 * (c) 2004 Planetalia S.L. - Todos los derechos reservados. Prohibida su reproducci�n
 * 
 * http://www.planetalia.com
 * 
 */

package utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public abstract class CacheRecursos {
	protected HashMap<String, Object> recursos;

	public CacheRecursos() {
		this.recursos = new HashMap<String, Object>();
	}

	protected Object cargarRecurso(String nombre) {
		URL url = null;
		File f = new File(nombre);
		try {
			url = f.toURI().toURL();
		} catch (MalformedURLException e) {
			System.out.println("El nombre del archivo " + nombre
					+ " no puede cargarse bien");
			e.printStackTrace();
		}

		return cargarRecurso(url);
	}

	protected Object getRecurso(String nombre) {
		Object res = recursos.get(nombre);
		if (res == null) {
			res = cargarRecurso("imgs/" + nombre);
			this.recursos.put(nombre, res);
		}
		return res;
	}

	protected abstract Object cargarRecurso(URL url);

}
