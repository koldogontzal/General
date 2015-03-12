package subtitulos;

import tiempo.Instante;

public class Subtitulo {
	private Instante inicio;
	private Instante fin;
	private String texto;
	
	public Subtitulo(Instante inicio, Instante fin, String texto) {
		this.inicio = inicio;
		this.fin = fin;
		this.texto = texto;
	}


	public void convertirTextoFormato7bits() {
		char[] arrayCaracteres = this.texto.toCharArray();
		for (int i = 0; i < arrayCaracteres.length; i++) {
			int codePoint = Character.codePointAt(arrayCaracteres, i);
			
			if (codePoint >= 0x00E0 && codePoint <= 0x00E5) {
				// Es un caracter 'a' 
				arrayCaracteres[i] = 'a';
			}
			if (codePoint >= 0x00E8 && codePoint <= 0x00EB) {
				arrayCaracteres[i] = 'e';
			}
			if (codePoint >= 0x00EC && codePoint <= 0x00EF) {
				arrayCaracteres[i] = 'i';
			}
			if ((codePoint >= 0x00F2 && codePoint <= 0x00F6) || (codePoint == 0x00F8)){
				arrayCaracteres[i] = 'o';
			}
			if (codePoint >= 0x00F9 && codePoint <= 0x00FC) {
				arrayCaracteres[i] = 'u';
			}
			if (codePoint >= 0x00C0 && codePoint <= 0x00C5) {
				arrayCaracteres[i] = 'A';
			}
			if (codePoint >= 0x00C8 && codePoint <= 0x00CB) {
				arrayCaracteres[i] = 'E';
			}
			if (codePoint >= 0x00CC && codePoint <= 0x00CF) {
				arrayCaracteres[i] = 'I';
			}
			if ((codePoint >= 0x00D2 && codePoint <= 0x00D6) || (codePoint == 0x00D8)){
				arrayCaracteres[i] = 'O';
			}
			if (codePoint >= 0x00D9 && codePoint <= 0x00DC) {
				arrayCaracteres[i] = 'U';
			}
			if (codePoint == 0x00F1) {
				arrayCaracteres[i] = 'n';
			}
			if (codePoint == 0x00D1) {
				arrayCaracteres[i] = 'N';
			}
			if (codePoint == 0x00A1) {
				arrayCaracteres[i] = '!';
			}
			if (codePoint == 0x00BF) {
				arrayCaracteres[i] = '?';
			}
			if (Character.codePointAt(arrayCaracteres, i) > 0x007F) {
				// En caso de que sea cualquier otro caracter por encima del (int)127
				arrayCaracteres[i] = '*';
			}
		}
		
		this.texto = new String(arrayCaracteres);		
	}
	
	public void adelantarTiempos(long cantidad, int unidadTiempo) {
		this.inicio.modificar(cantidad, unidadTiempo);
		this.fin.modificar(cantidad, unidadTiempo);
	}

	
	public void multiplicarFactorTiempos(double factor) {
		this.inicio.modificarFactor(factor);
		this.fin.modificarFactor(factor);
	}
	
	
	@Override
	public String toString() {
		String ret = "<subtitulo>\n";
		ret = ret + "\t<inicio>\n\t\t" + this.inicio + "\n\t</inicio>\n";
		ret = ret + "\t<fin>\n\t\t" + this.fin + "\n\t</fin>\n";
		ret = ret + "\t<texto>\n\t\t" + this.texto + "\n\t</texto>\n";
		ret = ret + "</subtitulo>";
		return ret;
	}
	
	public String toStringSRT(int numOrden) {
		String ret = "" + numOrden + "\n";
		ret = ret + this.inicio.toStringSRT() + " --> " + this.fin.toStringSRT() + "\n";
		ret = ret + this.texto;
		return ret;
	}
	
	public String toStringSUB(double fps) {
		String ret = this.inicio.toStringSUB(fps) + this.fin.toStringSUB(fps);
		char [] texto = this.texto.toCharArray();
		for (int i = 0; i < this.texto.length(); i++) {
			if (texto[i] == '\n') {
				texto[i] = '|';
			}
		}
		return ret + new String(texto);
	}
	
}
