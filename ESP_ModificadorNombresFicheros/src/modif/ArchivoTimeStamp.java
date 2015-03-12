package modif;

import main.IllegalTimeStampException;
import main.TimeStamp;
import main.TimeStampFile;

public class ArchivoTimeStamp extends Archivo {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5223325149130960917L;
	
	private TimeStampFile tsf;

	public ArchivoTimeStamp(String pathname) {
		super(pathname);
		this.tsf = new TimeStampFile(pathname);
	}
	
	public ArchivoTimeStamp(Archivo arch) {
		this(arch.getPath());
	}
	
	public TimeStamp getTimeStamp() throws IllegalTimeStampException {
		return this.tsf.getTimeStamp();
	}

	
}
