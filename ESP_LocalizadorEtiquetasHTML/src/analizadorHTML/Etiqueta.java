package analizadorHTML;

public class Etiqueta implements Comparable<Etiqueta> {

	public static final Etiqueta A = new Etiqueta("A");
	public static final Etiqueta ABBR = new Etiqueta("ABBR");
	public static final Etiqueta ACRONYM = new Etiqueta("ACRONYM");
	public static final Etiqueta APPLET = new Etiqueta("APPLET");
	public static final Etiqueta AREA = new Etiqueta("AREA");
	public static final Etiqueta B = new Etiqueta("B");
	public static final Etiqueta BASE = new Etiqueta("BASE");
	public static final Etiqueta BASEFONT = new Etiqueta("BASEFONT");
	public static final Etiqueta BDO = new Etiqueta("BDO");
	public static final Etiqueta BIG = new Etiqueta("BIG");
	public static final Etiqueta BLOCKQUOTE = new Etiqueta("BLOCKQUOTE");
	public static final Etiqueta BODY = new Etiqueta("BODY");
	public static final Etiqueta BR = new Etiqueta("BR");
	public static final Etiqueta BUTTON = new Etiqueta("BUTTON");
	public static final Etiqueta CAPTION = new Etiqueta("CAPTION");
	public static final Etiqueta CENTER = new Etiqueta("CENTER");
	public static final Etiqueta CITE = new Etiqueta("CITE");
	public static final Etiqueta CODE = new Etiqueta("CODE");
	public static final Etiqueta COL = new Etiqueta("COL");
	public static final Etiqueta COLGROUP = new Etiqueta("COLGROUP");
	public static final Etiqueta DD = new Etiqueta("DD");
	public static final Etiqueta DEL = new Etiqueta("DEL");	
	public static final Etiqueta DFN = new Etiqueta("DFN");	
	public static final Etiqueta DIR = new Etiqueta("DIR");
	public static final Etiqueta DIV = new Etiqueta("DIV");
	public static final Etiqueta DL = new Etiqueta("DL");
	public static final Etiqueta DT = new Etiqueta("DT");
	public static final Etiqueta EM = new Etiqueta("EM");
	public static final Etiqueta FIELDSET = new Etiqueta("FIELDSET");
	public static final Etiqueta FONT = new Etiqueta("FONT");
	public static final Etiqueta FORM = new Etiqueta("FORM");
	public static final Etiqueta FRAME = new Etiqueta("FRAME");
	public static final Etiqueta FRAMESET = new Etiqueta("FRAMESET");
	public static final Etiqueta HEAD = new Etiqueta("HEAD");
	public static final Etiqueta HR = new Etiqueta("HR");
	public static final Etiqueta HTML = new Etiqueta("HTML");
	public static final Etiqueta H1 = new Etiqueta("H1");	
	public static final Etiqueta H2 = new Etiqueta("H2");
	public static final Etiqueta H3 = new Etiqueta("H3");	
	public static final Etiqueta H4 = new Etiqueta("H4");
	public static final Etiqueta H5 = new Etiqueta("H5");	
	public static final Etiqueta H6 = new Etiqueta("H6");
	public static final Etiqueta I = new Etiqueta("I");
	public static final Etiqueta IFRAME = new Etiqueta("IFRAME");	
	public static final Etiqueta IMG = new Etiqueta("IMG");	
	public static final Etiqueta INPUT = new Etiqueta("INPUT");
	public static final Etiqueta INS = new Etiqueta("INS");
	public static final Etiqueta ISINDEX = new Etiqueta("ISINDEX");
	public static final Etiqueta KBD = new Etiqueta("KBD");
	public static final Etiqueta LABEL = new Etiqueta("LABEL");	
	public static final Etiqueta LEGEND = new Etiqueta("LEGEND");	
	public static final Etiqueta LI = new Etiqueta("LI");
	public static final Etiqueta LINK = new Etiqueta("LINK");
	public static final Etiqueta MAP = new Etiqueta("MAP");
	public static final Etiqueta MENU = new Etiqueta("MENU");
	public static final Etiqueta META = new Etiqueta("META");
	public static final Etiqueta NOFRAMES = new Etiqueta("NOFRAMES");
	public static final Etiqueta NOSCRIPT = new Etiqueta("NOSCRIPT");
	public static final Etiqueta OBJECT = new Etiqueta("OBJECT");
	public static final Etiqueta OL = new Etiqueta("OL");
	public static final Etiqueta OPTGROUP = new Etiqueta("OPTGROUP");
	public static final Etiqueta OPTION = new Etiqueta("OPTION");	
	public static final Etiqueta P = new Etiqueta("P");
	public static final Etiqueta PARAM = new Etiqueta("PARAM");
	public static final Etiqueta PRE = new Etiqueta("PRE");
	public static final Etiqueta Q = new Etiqueta("Q");
	public static final Etiqueta S = new Etiqueta("S");
	public static final Etiqueta SAMP = new Etiqueta("SAMP");
	public static final Etiqueta SCRIPT = new Etiqueta("SCRIPT");
	public static final Etiqueta SELECT = new Etiqueta("SELECT");
	public static final Etiqueta SMALL = new Etiqueta("SMALL");
	public static final Etiqueta SPAN = new Etiqueta("SPAN");
	public static final Etiqueta STRIKE = new Etiqueta("STRIKE");
	public static final Etiqueta STRONG = new Etiqueta("STRONG");
	public static final Etiqueta STYLE = new Etiqueta("STYLE");
	public static final Etiqueta SUB = new Etiqueta("SUB");
	public static final Etiqueta TABLE = new Etiqueta("TABLE");
	public static final Etiqueta TBODY = new Etiqueta("TBODY");
	public static final Etiqueta TD = new Etiqueta("TD");
	public static final Etiqueta TEXTAREA = new Etiqueta("TEXTAREA");
	public static final Etiqueta TFOOT = new Etiqueta("TFOOT");
	public static final Etiqueta TH = new Etiqueta("TH");
	public static final Etiqueta THEAD = new Etiqueta("THEAD");
	public static final Etiqueta TITLE = new Etiqueta("TITLE");
	public static final Etiqueta TR = new Etiqueta("TR");
	public static final Etiqueta TT = new Etiqueta("TT");
	public static final Etiqueta U = new Etiqueta("U");	
	public static final Etiqueta UL = new Etiqueta("UL");
	public static final Etiqueta VAR = new Etiqueta("VAR");
	
	public static final Etiqueta finOL = new Etiqueta("/OL");
	public static final Etiqueta finUL = new Etiqueta("/UL");
	
	
	private String elemento;
	private ListadoAtributos atributos;
	
	public Etiqueta(String etiqueta) {	
		// Busca el primer espacio, que indica el fin del elemento y el inicio de los posibles atributos
		int posEspacio = etiqueta.indexOf(' ');
		// Condición para detectar etiquetas del tipo <!--  -->
		if ((etiqueta.length() > 0) && (etiqueta.charAt(0) == '!')) {
			posEspacio = -1;
		}
		// Separa entre elemento y atributos
		if (posEspacio == -1) {
			// No hay separación, todo es elemento
			this.elemento = etiqueta;
			this.atributos = null;
		} else {
			// Sí hay separacion, hay parte elemento, y parte atributos
			this.elemento = etiqueta.substring(0, posEspacio);
			this.atributos = new ListadoAtributos(etiqueta.substring(posEspacio + 1, etiqueta.length()));
		}
	}
	
	@Override
	public String toString() {
		String ret = "<" + this.elemento;
		if (this.atributos != null) {
			ret = ret + " " + this.atributos.toString();
		}
		ret = ret + ">";
		
		return ret;
	}

	
	public boolean esEtiquetaDeCierre() {
		return (this.elemento.charAt(0) == '/');
	}
	
	public int compareTo(Etiqueta o) {
		String e1 = this.elemento.toUpperCase();
		String e2 = o.elemento.toUpperCase();
		return e1.compareTo(e2);
	}
	
	@Override
	public boolean equals(Object o) {
		return (this.compareTo((Etiqueta)o) == 0);
	}
	
	public boolean mismoElementoIgnoraAperturaCierre(Etiqueta e) {
		String elemento1 = new String(this.elemento);
		String elemento2 = new String(e.elemento);
		
		if (!this.esEtiquetaDeCierre()) {
			elemento1 = "/" + elemento1;
		}
		if (!e.esEtiquetaDeCierre()) {
			elemento2 = "/" + elemento2;
		}
		
		return elemento1.equals(elemento2);
	}
	
	public boolean tieneAtributo(String nomAtt) {
		if (this.atributos != null) {
			return this.atributos.hayAtributo(nomAtt);
		} else {
			return false;
		}
	}
}
