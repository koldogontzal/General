package utils.tratimagen.efectos;


public class SecuenciaDeEfectos {

	private class Nodo {
		private Efecto efecto;
		private Nodo sig;
	}

	private Nodo raiz;
	private Nodo fondo;

	public SecuenciaDeEfectos() {
		this.raiz = null;
		this.fondo = null;
	}

	public boolean esVacia() {
		if (raiz == null) {
			return true;
		} else {
			return false;
		}
	}

	public void insertar(Efecto efecto) {
		Nodo nuevo = new Nodo();
		nuevo.efecto = efecto;
		nuevo.sig = null;
		
		if (this.esVacia()) {
			this.raiz = nuevo;
			this.fondo = nuevo;
		} else {
			this.fondo.sig = nuevo;
			this.fondo = nuevo;
		}
	}

	public Efecto extraer() {
		if (!this.esVacia()) {
			Efecto ret = this.raiz.efecto;
			if (this.raiz == this.fondo) {
				this.raiz = null;
				this.fondo = null;
			} else {
				this.raiz = this.raiz.sig;
			}
			return ret;
		} else
			return null;
	}

	@Override
	public String toString() {
		String ret = "Listado de elementos de la cola: ";
		Nodo reco = this.raiz;
		while (reco != null) {
			ret = ret + reco.efecto + " - ";
			reco = reco.sig;
		}
		return ret;
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		SecuenciaDeEfectos ret = new SecuenciaDeEfectos();
		Nodo reco = this.raiz;
		while (reco != null) {
			ret.insertar(reco.efecto);
			reco = reco.sig;
		}
		return ret;
	}
	
	public SecuenciaDeEfectos clonar() {
		SecuenciaDeEfectos ret = null;
		try {
			ret = (SecuenciaDeEfectos)this.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return ret;
	}

}
