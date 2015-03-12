package utils.cuadricula;

import utils.cuadricula.tiles.Tile;

public class Espacio {
	
	private int fila;	// Posici�n de fila que ocupa el Espacio en la CuadriculaDeEspacios
	private int columna; // Posici�n de columna que ocupa el Espacio en la CuadriculaDeEspacios
	
	private int estado; // Listado de posibles tipos de Tiles que pueden ocupar este Espacio
						// Cada posici�n del n�mero repersenta a un tipo de Tile diferente.
						// Ver en Tile la deficini�n de cada uno de los tipos de Tile
	
	private Tile tileAsociado; // Tile asociado al espacio (si es que lo hay)
	
	public Espacio(int fila, int columna) {
		this.fila = fila;
		this.columna = columna;
		this.estado = 0;
		this.tileAsociado = null;
	}
	
	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	public int getEstado() {
		return this.estado;
	}
	
	public void modifEstado(int variacionEstado) {
		// Modifica el estado usando el operador de bit XOR. As� si en variaci�n
		// de estado hay un tipo de Tile dado, si en el estado orignial no era
		// posible, tras la modificaci�n lo ser�. Y viceversa, si en el estado
		// inicial s� era posible, tras la modificaci�n dejar� de serlo.

		this.estado = this.estado ^ variacionEstado;
	}
	
	public void quitaTipoEstado(int tipo) {
		// Quita un tipo de Tile dado del estado
		this.estado = this.estado & ~tipo;
	}
	
	public boolean puedeTenerTileTipo(int tipo) {
		// Usar los tipos definidos en Tile.
		// Devuelve s� o no, si este Espacio puede contener un Tile del "tipo" dado
		return (this.estado & tipo) != 0;
	}
	
	public void asociaTile(Tile t) {
		// Asocia un Tile determinado a este Espacio
		this.tileAsociado = t;
	}
	
	public Tile getTile() {
		return this.tileAsociado;
	}
	
	@Override
	public String toString() {
		String ret = "Espacio en posici�n " + this.fila + "(fila), " + this.columna + "(columna)";
		if (this.tileAsociado != null) {
			ret = ret + ". Tiene asociado: " + this.tileAsociado;
		}
		return ret;
	}

}
