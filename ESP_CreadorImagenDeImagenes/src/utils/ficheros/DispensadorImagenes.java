package utils.ficheros;

import java.io.File;

import javax.imageio.ImageIO;

public class DispensadorImagenes {
	/**
	 * Esta clase genera un listado de los File que son imagenes validas en un directorio dado
	 * y los va dispensando de uno en uno (aleatoriamente) mediante la funcion .siguiente()
	 */
	
	private File[] listadoFiles;
	private int pos;	
	private int numImagenes;	
	private static final String[] listadoExtensionesValidas = ImageIO.getWriterFormatNames();
	
	private String directorio;
	
	
	public DispensadorImagenes(String directorio) throws NoImagesException {
		this(new File(directorio));
	}
	
	
	public DispensadorImagenes() throws NoImagesException {
		this("." + File.separator + "fotos");
	}
	
	
	public DispensadorImagenes(File directorio) throws NoImagesException {
		this.directorio = directorio.getPath();
		
		// Lee los archivos del directorio
		this.listadoFiles = directorio.listFiles();
		
		// Los desordena
		this.desordenaListadoFiles();
		
		// Pone en primer lugar a los destacados
		this.priorizaDestacados();
		
		// Inicializa el dispensador
		this.pos = 0;
		
		// Cuenta el número de imágenes. Si no hay ninguna, lanza una excepción
		if (this.cuentaNumImagenes()==0) {
			NoImagesException e = new NoImagesException(this.directorio);
			throw e;
		}		
	}
	
	public File siguiente() throws NoImagesException {
		if (this.numImagenes == 0) {
			NoImagesException e = new NoImagesException(this.directorio);
			throw e;
		}
		File ret = null;
		if (this.esImagen(this.listadoFiles[this.pos])) {
			ret = this.listadoFiles[this.pos];
			this.pos++;
			if (this.pos >= this.listadoFiles.length) {
				this.desordenaListadoFiles();
				this.pos = 0;
			}
		} else {
			this.pos++;
			ret = this.siguiente();
		}
		
		return ret;
	}
	
	
	private void desordenaListadoFiles() throws NoImagesException {
		if (this.listadoFiles == null) {
			throw new NoImagesException(this.directorio);
		}
		
		int [] orden = new int[this.listadoFiles.length];
		for (int i = 0; i < orden.length; i ++) {
			orden[i] = i;			
		}
		for (int i = 0; i < orden.length; i ++) {
			int j = (int)(Math.random() * orden.length);
			int intermedio = orden[i];
			orden[i] = orden[j];
			orden[j] = intermedio;
		}		
		File[] listadoAlternativo = new File[orden.length];
		for (int i = 0; i < orden.length; i ++) {
			listadoAlternativo[i] = this.listadoFiles[orden[i]];
		}		
		this.listadoFiles = listadoAlternativo;
	}
	
	private void priorizaDestacados() {
		int posDestacado = 0;
		for (int i = 0; i < this.listadoFiles.length; i++) {
			if (this.esDestacado(this.listadoFiles[i])) {
				File intermedio = this.listadoFiles[i];
				this.listadoFiles[i] = this.listadoFiles[posDestacado];
				this.listadoFiles[posDestacado] = intermedio;
				posDestacado++;
			}

		}
		
	}
	
	private boolean esDestacado(File archivo) {
		return (archivo.getName().toUpperCase().indexOf("DESTACA") != -1);		
	}
	
	
	private boolean esImagen(File archivo) {
		
		String extension = archivo.getName().substring(archivo.getName().lastIndexOf(".") + 1);
		boolean encontrado = false;
		for (String formato : listadoExtensionesValidas) {
			if (extension.equals(formato)) {
				encontrado = true;
			}
		}
		return encontrado;		
	}
	
	private int cuentaNumImagenes() {
		this.numImagenes = 0;
		for (File archivo : this.listadoFiles) {
			if (this.esImagen(archivo)) {
				this.numImagenes++;
			}
		}
		return this.numImagenes;
	}

}
