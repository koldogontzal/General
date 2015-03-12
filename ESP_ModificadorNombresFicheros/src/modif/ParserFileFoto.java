package modif;

public class ParserFileFoto {
	// Es un cadena de tres letras, IMG o MVI, parte del nombre
	private String tipo;
	// N�mero, es una cadena de 4 d�gitos
	private String numero;
	// Resto del nombre
	private String resto;
	// Extensi�n: JPG, AVI o THM
	private String extension;
	// �Se modifica en algo el nombre?
	private boolean modificado = true;
	// Vuelta actual de los 10000 numeros de la c�mara
	private final String vueltaActual = "2";
	
	public ParserFileFoto(String nombre) {
		if (nombre.matches("[0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]_[A-Z][A-Z][A-Z]*.*")) {
			// Es un nombre de archivo original de la c�mara, con formato antiguo
			this.tipo = nombre.substring(9, 12).toUpperCase();
			// Los archivos con estos numeros, iban desde el '0'0001 hasta el '1'6295
			// con lo cual, su quinto d�gito es '0' � '1'.
			// Es igual al valor del primer char del nombre, menos 1
			int auxValor = Integer.parseInt(nombre.substring(0,1));
			auxValor --;
			this.numero = auxValor + nombre.substring(4, 8);
			int posPunto = nombre.lastIndexOf('.');
			if (posPunto != 12) {
				this.resto = nombre.substring(12, posPunto);
			} else {
				this.resto = "";
			}
			this.extension = nombre.substring(posPunto + 1).toUpperCase();
			
		} else if (nombre.matches("[A-Z][A-Z][A-Z]_[0-9][0-9][0-9][0-9]*.*")) {
			// Es un nombre de archivo original de la c�mara, con formato nuevo
			this.tipo = nombre.substring(0, 3).toUpperCase();
			/* Antes era as�, antes de la conversi�n de todas las fotos
				// Los archivos con estos numeros, iban desde el '1'6296 hasta el '2'1908
				// con lo cual, su quinto d�gito puede ser '1' � '2'
				this.numero = nombre.substring(4, 8);
				if (this.numero.compareTo("6200") < 0) {
					this.numero = "2" + this.numero;
				} else {
					this.numero = "1" + this.numero;
				}
			*/
			// Ahora
			this.numero = this.vueltaActual + nombre.substring(4, 8);
			int posPunto = nombre.lastIndexOf('.');
			if (posPunto != 8) {
				this.resto = nombre.substring(8, posPunto);
			} else {
				this.resto = "";
			}
			this.extension = nombre.substring(posPunto + 1).toUpperCase();
			
		} else if (nombre.matches("[0-9][0-9][0-9][0-9][0-9]_[A-Z][A-Z][A-Z]*.*")) {
			// Es un nombre de archivo completamente nuevo, NO de los originales de la c�mara
			this.modificado = false;
			this.tipo = nombre.substring(6, 9).toUpperCase();
			this.numero = nombre.substring(0, 5);
			int posPunto = nombre.lastIndexOf('.');
			if (posPunto != 9) {
				this.resto = nombre.substring(9, posPunto);
			} else {
				this.resto = "";
			}
			this.extension = nombre.substring(posPunto + 1).toUpperCase();
						
		} else {
			// Es un nombre de archivo que no sigue ninguna convenci�n
			this.modificado = false;
			this.tipo = "";
			this.numero = "";
			this.resto = "";
			this.extension = "";
		}
	}
	
	public String nombreNuevoFichero () {
		if (this.esNombreFileCorrecto()) {
			return (this.numero + "_" + this.tipo + this.resto + "." + this.extension);
		} else {
			return null;
		}
	}
	
	public boolean esNombreFileCorrecto () {
		return (!this.tipo.equals("") && !this.numero.equals(""));
	}
	
	public static boolean esNombreFileCorrecto (String nombre) {
		ParserFileFoto aux = new ParserFileFoto(nombre);
		return aux.esNombreFileCorrecto();
	}
	
	public boolean esCoherenteTipoExtension () {
		boolean aux = false;
		if (this.tipo.equals("IMG") && this.extension.equals("JPG")) {
			aux = true;
		}
		if (this.tipo.equals("MVI") && (this.extension.equals("AVI") || this.extension.equals("THM"))) {
			aux = true;
		}
		return aux;
	}
	
	public boolean esNombreFuturoDistintoOriginal () {
		return this.modificado;
	}
}


