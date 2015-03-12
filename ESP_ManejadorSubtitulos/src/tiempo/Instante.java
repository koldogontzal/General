package tiempo;

public class Instante implements Comparable {
	// tiempo transcurrido en milisegundos desde el instante 0
	private long tiempo;
	
	// Constates de la clase
	public static final Instante INSTANTE_NO_VALIDO = new Instante(0, 0, 0, -1);
	
	public static final int HORAS = 0;
	public static final int MINUTOS = 1;
	public static final int SEGUNDOS = 2;
	public static final int MILISEGUNDOS = 3;
	
	// Constructores
	public Instante() {
		this.tiempo = 0L;
	}
	
	public Instante(int horas, int minutos, int segundos, int milisegundos) {
		this.tiempo = ((((horas * 60L) + minutos) * 60L) + segundos) * 1000L + milisegundos;
	}
	
	public Instante(Instante i) {
		this.tiempo = i.tiempo;
	}
	
	private Instante(long tiempo) {
		this.tiempo = tiempo;
	}
	
	public static Instante parseInstante(String s) {
		String [] datosDescompuestos = s.split(":");
		if (datosDescompuestos.length == 4) {
			return parseInstante(datosDescompuestos[0] + ":" + 
					datosDescompuestos[1] + ":" +
					datosDescompuestos[2] + "," +
					datosDescompuestos[3]);
		} else if (datosDescompuestos.length == 3){
			int horas = Integer.parseInt(datosDescompuestos[0]);
			int minutos = Integer.parseInt(datosDescompuestos[1]);
			
			int posSeparador = datosDescompuestos[2].indexOf('.');
			if (posSeparador == -1) {
				posSeparador = datosDescompuestos[2].indexOf(',');
			}
			
			int segundos;
			int milisegundos;
			if (posSeparador == -1) {
				segundos = Integer.parseInt(datosDescompuestos[2]);
				milisegundos = 0;
			} else {
				int longitud = datosDescompuestos[2].length();
				String p1 = datosDescompuestos[2].substring(0, posSeparador);
				String pAux = datosDescompuestos[2].substring(posSeparador + 1, longitud);
				String p2 = (pAux + "000").substring(0, 3);
				segundos = Integer.parseInt(p1);
				milisegundos = Integer.parseInt(p2);
			}
			
			Instante i = new Instante(horas, minutos, segundos, milisegundos);
			return i;
		} else {
			return INSTANTE_NO_VALIDO;
		}
	}
	
	@Override
	public String toString() {
		if (!this.equals(INSTANTE_NO_VALIDO)) {
			int [] val = this.arrayHMSM();	
			
			return "" + val[HORAS] + ":" +
				this.pasarAString2Char(val[MINUTOS]) + ":" +
				this.pasarAString2Char(val[SEGUNDOS]) + "," +
				this.pasarAString3Char(val[MILISEGUNDOS]);
		} else {
			return "Instante no valido";
		}
	}
	
	public String toStringSRT() {
		if (!this.equals(INSTANTE_NO_VALIDO)) {
			int [] val = this.arrayHMSM();	
			
			return this.pasarAString2Char(val[HORAS]) + ":" +
				this.pasarAString2Char(val[MINUTOS]) + ":" +
				this.pasarAString2Char(val[SEGUNDOS]) + "," +
				this.pasarAString3Char(val[MILISEGUNDOS]);
		} else {
			return "Instante no valido";
		}
	}
	
	public String toStringIDX() {
		if (!this.equals(INSTANTE_NO_VALIDO)) {
			int [] val = this.arrayHMSM();	
			
			return this.pasarAString2Char(val[HORAS]) + ":" +
				this.pasarAString2Char(val[MINUTOS]) + ":" +
				this.pasarAString2Char(val[SEGUNDOS]) + ":" +
				this.pasarAString3Char(val[MILISEGUNDOS]);
		} else {
			return "Instante no valido";
		}
	}
	
	public String toStringSSA() {
		if (!this.equals(INSTANTE_NO_VALIDO)) {
			int [] val = this.arrayHMSM();	
			
			// Transforma los milisegundos en centisegundos
			val[MILISEGUNDOS] = val[MILISEGUNDOS] / 10;
			
			return "" + val[HORAS] + ":" +
				this.pasarAString2Char(val[MINUTOS]) + ":" +
				this.pasarAString2Char(val[SEGUNDOS]) + "." +
				this.pasarAString2Char(val[MILISEGUNDOS]);
		} else {
			return "Instante no valido";
		}
	}
	
	public String toStringSUB(double fps) {
		if (!this.equals(INSTANTE_NO_VALIDO)) {
			double segundos = this.getValor(SEGUNDOS);	
			
			long frame = (long)(segundos * fps);
			
			return "{" + frame + "}";
		} else {
			return "Instante no valido";
		}
	}
	
	private int [] arrayHMSM() {
		// ret[0] = Horas; ret[3] = Milisegundos
		int [] ret = new int[4];
		long resto = this.tiempo;
		ret[MILISEGUNDOS] = (int) (resto % 1000);
		resto = resto / 1000L;
		ret[SEGUNDOS] = (int) (resto % 60);
		resto = resto / 60L;
		ret[MINUTOS] = (int) (resto % 60);
		resto = resto / 60L;
		ret[HORAS] = (int) resto;
		
		return ret;
		
	}
	
	private String pasarAString2Char (int num) {
		String ret = "0" + num;
		return ret.substring(ret.length() - 2, ret.length());
	}
	
	private String pasarAString3Char (int num) {
		String ret = "00" + num;
		return ret.substring(ret.length() - 3, ret.length());
	}
	
	// Operaciones	
	@Override
	public boolean equals(Object arg0) {
		Instante i = (Instante) arg0;
		return (this.tiempo == i.tiempo);
	}
	
	public int compareTo(Object arg0) {
		Long l = new Long(this.tiempo);
		Long l0 = new Long(((Instante) arg0).tiempo);
		return l.compareTo(l0);
	}

	public void modificar(long cantidad, int unidadTiempo) {
		long factor = 0L;
		switch (unidadTiempo) {
		case MILISEGUNDOS:
			factor = 1L;
			break;
		case SEGUNDOS:
			factor = 1000L;
			break;
		case MINUTOS:
			factor = 60000L;
			break;
		case HORAS:
			factor = 3600000L;
			break;
		}
		this.tiempo = this.tiempo + (factor * cantidad);
		if (this.tiempo < 0) {
			this.tiempo = -1L;
		}
		
	}
	
	public void modificarFactor(double factor) {
		if (factor < 0) {
			factor = 1.0;
		}
		if (!this.equals(Instante.INSTANTE_NO_VALIDO)) {
			this.tiempo = (long)(this.tiempo * factor);
		}
	}
	
	public Instante suma(Instante sumando) {
		long resultado = this.tiempo + sumando.tiempo;
		return new Instante(resultado);
	}
	
	public Instante resta(Instante restando) {
		long resultado = this.tiempo - restando.tiempo;
		if (resultado < 0) {
			return INSTANTE_NO_VALIDO;
		} else {
			return new Instante(resultado);
		}
	}
	
	public double getValor(int unidadTiempo) {
		double factor = 1.0;
		switch (unidadTiempo) {
		case MILISEGUNDOS:
			factor = 1.0;
			break;
		case SEGUNDOS:
			factor = 1000.0;
			break;
		case MINUTOS:
			factor = 60000.0;
			break;
		case HORAS:
			factor = 3600000.0;
			break;
		}
		
		return (double)this.tiempo / factor;
	}

}
