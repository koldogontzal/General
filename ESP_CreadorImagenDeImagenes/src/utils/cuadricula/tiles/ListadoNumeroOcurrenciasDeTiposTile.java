package utils.cuadricula.tiles;

import java.util.HashMap;

public class ListadoNumeroOcurrenciasDeTiposTile extends HashMap<Integer, Integer>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1299869274415671445L;


	public ListadoNumeroOcurrenciasDeTiposTile() {
		super();
	}
	

	public Integer añadir(int key, int value) {
		// Crea un HashMap con clave un tipo de Tile dado y valor el n�mero de
		// veces que queremos que aparezca en la CuadrillaDeEspacios

		return super.put(new Integer(key), new Integer(value));
	}
	
	public Integer getNumOcurrencias(int key) {
		Integer ret = super.get(key); 
		return (ret == null ? 0 : ret);
	}
	
	@Override
	public String toString() {
		String ret = "Listado de preferencias de ocurrencias de tipos de Tile:\n";
		for (Tile t : Tile.getListadoTiposTiles()) {
			ret = ret + "\t" + t + ". Número ocurrencias: " + this.get(t.getTipoTile()) + "\n";
		}
		return ret;
	}

}
