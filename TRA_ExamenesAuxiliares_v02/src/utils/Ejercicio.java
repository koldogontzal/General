package utils;

import java.io.File;

public class Ejercicio {

	private String grupo;
	private String usu;
	private File directorio;
	private Prueba pruebaA;
	private Prueba pruebaB;
	private Prueba pruebaC;
	private boolean tieneOtrosArchivos;
	private boolean tieneMasDeTresPruebas = false;
	private boolean opositorUnico;
	
	public Ejercicio(File directorio) {
		this.directorio = directorio;
		if (directorio.isDirectory()) {
			this.pruebaA = this.buscaPrueba("A");
			this.pruebaB = this.buscaPrueba("B");
			this.pruebaC = this.buscaPrueba("C");
			
			String[] partesDir = directorio.getPath().split(File.separator + File.separator);
			this.usu = partesDir[partesDir.length - 1].toLowerCase();
			this.grupo = partesDir[partesDir.length - 2].toLowerCase();
			
			// Calcula cuantos ejercicios hay y cuantas copias
			int numEjercicios = 0;
			int numCopias = 0;
			if (this.pruebaA != null) { 
				numEjercicios++;
				if (this.pruebaA.isTieneCopia())
					numCopias++;
				if (this.pruebaA.isTieneCopiaVersionAntigua())
					numCopias++;
			}
			if (this.pruebaB != null){ 
				numEjercicios++;
				if (this.pruebaB.isTieneCopia())
					numCopias++;
				if (this.pruebaB.isTieneCopiaVersionAntigua())
					numCopias++;
			}	
			if (this.pruebaC != null){ 
				numEjercicios++;
				if (this.pruebaC.isTieneCopia())
					numCopias++;
				if (this.pruebaC.isTieneCopiaVersionAntigua())
					numCopias++;
			}
			
			// Calcula cuantos archivos hay en el directorio
			File[] archivos = directorio.listFiles();
			int numArchivos = archivos.length;
			
			this.tieneOtrosArchivos = (numArchivos != numCopias + numEjercicios);
			
			// Comprueba que todas las pruebas sean del mismo opositor
			String[] opositores = new String[3];
			int cont = 0;
			if (this.getPruebaA() != null) {
				opositores[cont] = this.getPruebaA().getCodigoOpositor();
				cont++;
			}
			if (this.getPruebaB() != null) {
				opositores[cont] = this.getPruebaB().getCodigoOpositor();
				cont++;
			}
			if (this.getPruebaC() != null) {
				opositores[cont] = this.getPruebaC().getCodigoOpositor();
				cont++;
			}
			this.opositorUnico = true;	
			for (int i = 1; i < cont; i++) {
				this.opositorUnico = this.opositorUnico && opositores[0].equals(opositores[i]);
			}
		} else {
			System.out.println(directorio + " no es un directorio");
		}
	}
	
	private Prueba buscaPrueba(String identificativo) {
		Prueba ret = null;
		File[] archivos = this.directorio.listFiles();
		int contadorGeneral = 0;
		int contadorPruebaDeterminada = 0;
		for (File archivo : archivos) {
			String nombre = archivo.getName();
			if (nombre.length() == 11) {
				contadorGeneral++;
				if (nombre.substring(0, 1).toUpperCase().equals(identificativo)) {
					contadorPruebaDeterminada++;
					ret = new Prueba(archivo);
				}
			}			
		}
		if ((contadorGeneral > 3) || (contadorPruebaDeterminada > 1)) {
			// Hay un error: el directorio contiene más de 3 pruebas
			//System.out.println("Error en " + this.directorio + ". Hay mas de tres pruebas. Revisar manualmente.");
			this.tieneMasDeTresPruebas = true;
		}
		return ret;
	}

	public String getGrupo() {
		return grupo;
	}

	public String getUsu() {
		return usu;
	}

	public File getDirectorio() {
		return directorio;
	}

	public Prueba getPruebaA() {
		return pruebaA;
	}

	public Prueba getPruebaB() {
		return pruebaB;
	}

	public Prueba getPruebaC() {
		return pruebaC;
	}

	public boolean isTieneOtrosArchivos() {
		return tieneOtrosArchivos;
	}

	public boolean isTieneMasDeTresPruebas() {
		return tieneMasDeTresPruebas;
	}

	public boolean isOpositorUnico() {
		return opositorUnico;
	}

	public boolean isEjercicioVacio() {
		boolean ret;
		ret = this.getPruebaA() == null;
		ret = ret && this.getPruebaB() == null;
		ret = ret && this.getPruebaC() == null;
		ret = ret && !this.isTieneOtrosArchivos();
		
		return ret;
	}
	@Override
	public String toString() {
		String ret = null;
		ret = "Ejercicio " + this.getDirectorio().getPath() + ":";
		ret = ret + "\n" + this.getPruebaA();
		ret = ret + "\n" + this.getPruebaB();
		ret = ret + "\n" + this.getPruebaC();
		ret = ret + "\nOpositor unico: " + this.isOpositorUnico();
		ret = ret + "\nTiene mas de tres pruebas: " + this.isTieneMasDeTresPruebas();
		ret = ret + "\nTiene otros archivos: " + this.isTieneOtrosArchivos();
		
		return ret;
	}
	
	public String getOpositor() {
		String ret;
		if (this.isEjercicioVacio()) {
			ret = null;
		} else if (this.isOpositorUnico() && !this.isTieneMasDeTresPruebas()){
			if (this.getPruebaA() != null) {
				ret = this.getPruebaA().getCodigoOpositor();
			} else if (this.getPruebaB() != null) {
				ret = this.getPruebaB().getCodigoOpositor();
			} else if (this.getPruebaC() != null) {
				ret = this.getPruebaC().getCodigoOpositor();
			} else {
				ret = null;
			}
		} else {
			ret = null;
		}
		
		return ret;
	}

}
