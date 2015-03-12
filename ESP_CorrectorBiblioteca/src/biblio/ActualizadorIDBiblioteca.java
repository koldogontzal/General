package biblio;

import java.io.File;
import java.util.Iterator;

public class ActualizadorIDBiblioteca {
	
	private File rutaBibliotecaLocal;
	private Biblioteca bibliotecaModelo;
	
	private Biblioteca librosModeloSinUsar;
	private Biblioteca librosLocalSinId;
	

	public ActualizadorIDBiblioteca(String sruta, Biblioteca bib) {
		// Actualiza los ID de la biblioteca almacenada en local en el directorio
		// "sruta" usando la Biblioteca "bib" como modelo.
		this(new File(sruta), bib);
	}
	
	public ActualizadorIDBiblioteca(File fruta, Biblioteca bib) {
		// Actualiza los ID de la biblioteca almacenada en local en el directorio
		// "fruta" usando la Biblioteca "bib" como modelo.
		Biblioteca bLocal = new Biblioteca(fruta);
		this.rutaBibliotecaLocal = fruta;
		this.bibliotecaModelo = bib;
		this.librosModeloSinUsar = new Biblioteca();
		
		Iterator<Libro> i = this.bibliotecaModelo.iterator();
		while (i.hasNext()) {
			Libro modeloAct = i.next();
			if (modeloAct.getId() != 0) {
				// Sólo intenta actualizar la local si el modelo tiene ID
				int indiceLocal = bLocal.indexOf(modeloAct);
				if (indiceLocal == -1) {
					// No existe el Libro en la Biblioteca Local
					this.librosModeloSinUsar.add(modeloAct);
				} else {
					// Sí existe el Libro en la Biblioteca Local: cambia su ID
					Libro localAct = bLocal.get(indiceLocal);
					if (localAct.getId() != modeloAct.getId()) {
						File localOld = localAct
								.obtenerFileLibro(this.rutaBibliotecaLocal);
						File localNew = modeloAct
								.obtenerFileLibro(this.rutaBibliotecaLocal);

						localOld.renameTo(localNew);

					}
				}
			}
		}
		
		Biblioteca nuevaLocal = new Biblioteca (fruta);
		this.librosLocalSinId = nuevaLocal.subBibliotecaLibrosSinID();
	}

	public Biblioteca getLibrosModeloSinUsar() {
		return librosModeloSinUsar;
	}

	public Biblioteca getLibrosLocalSinId() {
		return librosLocalSinId;
	}
}
