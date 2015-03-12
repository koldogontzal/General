package manejadorID;

public class RegistroID {
	
	private String id;
	private String etiqueta;
	private String idPadre;
	
	public RegistroID(String id, String etiqueta, String idPadre) {
		this.id = id;
		this.etiqueta = etiqueta;
		this.idPadre = idPadre;
	}
	
	public RegistroID(String linea) {
		String [] vectorDatos;
		vectorDatos = linea.split("%");
		for (int i = 0; i < vectorDatos.length; i++) {
			vectorDatos[i] = this.quitarEspaciosPrincipioYFinal(vectorDatos[i]);
		}
		this.id = vectorDatos[0];
		this.etiqueta =  vectorDatos[1];
		this.idPadre = vectorDatos[2];		
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getEtiqueta() {
		return this.etiqueta;
	}
	
	public String getIdPadre() {
		return this.idPadre;
	}
	
	private String quitarEspaciosPrincipioYFinal(String s) {
		int posIni = 0;
		int posFin = s.length();
		
		// Quita los espacios del inicio
		while ((posIni < posFin) && this.esCaracterEnBlanco(s.charAt(posIni))) {
			posIni++;
		}
		
		//Quita los espacios del final
		while ((posIni < posFin) && this.esCaracterEnBlanco(s.charAt(posFin - 1))) {
			posFin--;
		}
		
		return s.substring(posIni, posFin);		
	}
	
	private boolean esCaracterEnBlanco(char c) {
		if ((c == ' ') || (c == '\t') || (c == '\n')) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return this.id + "|" + this.etiqueta + "|" + this.idPadre;
	}

}
