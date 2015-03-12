package analizadorHTML;

public class Atributo {
	private String nombre;
	private String atributoNormalizado;
	private String valor;

	public static final String ATRIBUTO_SIN_VALOR = null;
	
	
	public Atributo(String nombre, String valor) {
		this.rellenarCampos(nombre, valor);
	}
	
	public Atributo(String texto) {
		// 'texto' es una cadena del estilo "name='Ir a Contenidos'"
		int posCharIgual = texto.indexOf('=');
		if (posCharIgual != -1) {
			String nombre = texto.substring(0,posCharIgual);
			String valor = texto.substring(posCharIgual + 1, texto.length());
			this.rellenarCampos(nombre, valor);
		} else {
			// 'texto' no contiene un carácter '='
			this.rellenarCampos(texto, ATRIBUTO_SIN_VALOR);
		}
	}
	
	private void rellenarCampos(String nombre, String valor) {
		this.nombre = this.eliminarEspacios(nombre);
		this.atributoNormalizado = this.nombre.toLowerCase();
		this.valor = this.eliminarEspacios(valor);
	}
	
	private String eliminarEspacios(String texto) {
		if (texto != null) {
			int posIni = 0;
			int posFin = texto.length() - 1;
			if (posFin >= 0) {
				while ((texto.charAt(posIni) == ' ') && (posIni < posFin)) {
					posIni++;
				}
				while ((texto.charAt(posFin) == ' ') && (posFin > posIni)) {
					posFin--;
				}				
				return texto.substring(posIni, posFin + 1);
			} else {
				return texto;
			}			
		} else {
			return null;
		}
	}
	
	public String getValor() {
		return this.valor;
	}
	
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	public boolean esAtributo(String atributo) {
		return this.eliminarEspacios(atributo).toLowerCase().equals(this.atributoNormalizado);
	}

	@Override
	public String toString() {
		String ret = this.nombre;
		if (this.valor != ATRIBUTO_SIN_VALOR) {
			ret = ret + "=" + this.valor;
		}
		return ret;
	}
}
