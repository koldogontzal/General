package analz;

public class Caracteristica {
	private int id;
	private int idPadre;
	private int idPropiedad;
	private int puntuacion;
	
	public Caracteristica(int id, int idPadre, int idPropiedad, int puntuacion) {
		this.id = id;
		this.idPadre = idPadre;
		this.idPropiedad = idPropiedad;
		this.puntuacion = puntuacion;
	}
	
	public Caracteristica(String linea) {
		int posColon1 = linea.indexOf(";");
		int posColon2 = linea.indexOf(";", posColon1 + 1);
		int posColon3 = linea.indexOf(";", posColon2 + 1);
		
		this.id = Integer.valueOf(linea.substring(0, posColon1));
		this.idPadre = Integer.valueOf(linea.substring(posColon1 + 1, posColon2));
		this.idPropiedad = Integer.valueOf(linea.substring(posColon2 + 1, posColon3));
		this.puntuacion = Integer.valueOf(linea.substring(posColon3 + 1));
	}
	
	@Override
	public String toString() {
		return "(" + this.id + ")" +this.idPadre + " " + this.idPropiedad + " " + this.puntuacion;
	}

	public int getId() {
		return this.id;
	}
	
	public int getIdPadre() {
		return this.idPadre;
	}
	
	public int getPropiedad() {
		return this.idPropiedad;
	}
	
	public int getPuntuacion() {
		return this.puntuacion;
	}
}
