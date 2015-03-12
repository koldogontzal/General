package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileExtendido extends File {

	private static final long serialVersionUID = -1740164433739954147L;
	
	public FileExtendido(String arg0) {
		super(arg0);
	}
	
	public FileExtendido(File f) {
		super(f.getPath());
	}

	public boolean compararContenidoFiles(FileExtendido f2) throws IOException {
		// Compara byte a byte dos ficheros. Devuelve true si son iguales
		boolean ret = true;
		
		InputStream in1 = new FileInputStream(this);
		InputStream in2 = new FileInputStream(f2);
		
		byte[] buf1 = new byte[10240];
		byte[] buf2 = new byte[10240];
        int len1;
        int len2;
        while (((len1 = in1.read(buf1)) > 0) && ret) {
            len2 = in2.read(buf2);
            if (len1 == len2) {
            	int pos = 0;
            	while (ret && (pos < len1)) {
            		if (buf1[pos] == buf2[pos]) {
            			pos++;
            		} else {
            			ret = false;
            		}
            	}
            	
            } else {
            	ret = false;
            }
        }	
		
		return ret;
	}
	
	public void copiarFile(File nuevoFile) throws IOException {
		
	        InputStream 	in = new FileInputStream(this);
	        OutputStream 	out = new FileOutputStream(nuevoFile);
	        
	        
	        byte[] buf = new byte[10240];
	        int len;
	        while ((len = in.read(buf)) > 0) {
	            out.write(buf, 0, len);
	        }
	        
	        in.close();
	        out.close();
	        
	        nuevoFile.setLastModified(this.lastModified());
	    } 

	public MarcaDeTiempoDeFichero getMarcaDeTiempoDeFichero() {
		String nombre = this.getName();
		return MarcaDeTiempoDeFichero.parseMarcaDeTiempoDeFichero(nombre);
	}
	

	public int compareTo(FileExtendido arg0) {
		// Compara dos ficheros en funcion de su MarcaDeTiempoDeFichero (para ordenarlos por fecha)
		return this.getMarcaDeTiempoDeFichero().compareTo(arg0.getMarcaDeTiempoDeFichero());
	}
	
	public boolean agnadirMarcaDeTiempo() {
		//
		// No funciona muy bien, parece que hay en retardo entre que termina el metodo y efectivamente cambia el file
		//
		String directorio = this.getParent();
		String archivo = this.getName();
		MarcaDeTiempoDeFichero marca = new MarcaDeTiempoDeFichero();
		String nombreFinal = directorio + File.separator + marca + "_" + archivo;
		return this.renameTo(new FileExtendido(nombreFinal));
	}
	
	@Override
	public String toString() {
		return this.getPath();
	}
}
