package analizadorHTML;

public class EtiquetaContenido extends Etiqueta {
	
	private String contenido;
	
	public EtiquetaContenido(String texto) {
		super("");
		this.contenido = texto;
	}

	
	@Override
	public String toString() {
		return this.contenido;
	}
	
}
