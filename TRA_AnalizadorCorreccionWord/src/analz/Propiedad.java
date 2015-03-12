package analz;

public class Propiedad {
	private int id;
	private String descripcion;
	
	public Propiedad(int id, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
	}
	
	public Propiedad(String linea) {
		int posColon = linea.indexOf(";");
		//System.out.println(linea + " " + posColon + " - " + linea.substring(0, posColon));
		
		this.id = Integer.valueOf(linea.substring(0, posColon));
		this.descripcion = linea.substring(posColon + 1);
	}
	
	@Override
	public String toString() {
		return this.descripcion;
	}

	public int getId() {
		return this.id;
	}
}
