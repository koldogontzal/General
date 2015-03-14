package modif;

import com.koldogontzal.timestamp.IllegalTimeStampException;
import com.koldogontzal.timestamp.TimeStamp;
import com.koldogontzal.timestamp.TimeStampFile;
import com.koldogontzal.timestamp.TimeStampFormat;

public class ArchivoTimeStamp extends Archivo {

	private static final long serialVersionUID = -5223325149130960917L;
	
	private TimeStampFile tsf;

	public ArchivoTimeStamp(String pathname) {
		super(pathname);
		this.tsf = new TimeStampFile(pathname);
	}
	
	public ArchivoTimeStamp(Archivo arch) {
		this(arch.getPath());
	}
	
	public TimeStamp getTimeStamp() {		
		try {
			return this.tsf.getTimeStamp();
		} catch (IllegalTimeStampException e) {
			// El archivo no tiene un TimeStamp en el nombre. Devuelve uno creado o de los metadatos o de la fecha de creación.
			TimeStamp ts = null;
			if (super.esArchivoFoto()) {
				// Intenta leer los metadatos de la imagen y crear un TimeStamp
				ArchivoFoto af = new ArchivoFoto(this.tsf.getPath());
				ts = af.getTimeStampDeMetadatos();
			} 
			
			if (ts == null) {
				// Lee la fecha de la fecha de creación del archivo y crea un TimeStamp				
				ts = new TimeStamp(super.lastModified());			
			}
			
			return ts;
		}
	}
	
	public boolean añadirTimeStampAlNombre(TimeStamp t, TimeStampFormat formatoTimeStamp) {
		return this.tsf.setTimeStamp(t, formatoTimeStamp);
	}
	
	public boolean quitarTimeStampAlNombre() {
		return this.tsf.deleteTimeStamp();		
	}

	public boolean modificarTimeStampDelNombre(long dif, TimeStampFormat formatoTimeStamp) throws IllegalTimeStampException {
		// Se añade dif en milisegundos
		return this.tsf.setTimeStamp(dif, formatoTimeStamp);
	}
	
}
