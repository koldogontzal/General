package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import escritor.EscritorArchivosTexto;

public class ListaEjercicios {
	
	private File directorioPpal;
	private int numGrupos;
	private ArrayList<Ejercicio> listado;
	private boolean listadoCorrecto = true;
	
	public ListaEjercicios(String directorio) {
		this.directorioPpal = new File(directorio);
		this.listado = new ArrayList<Ejercicio>(1000);
		this.numGrupos = 0;
		
		if (this.directorioPpal.isDirectory()) {
			File[] listDirLlamam = this.directorioPpal.listFiles();
			for (File grupo : listDirLlamam) {
				if (grupo.isDirectory()) {
					this.numGrupos++;
					System.out.println("Leyendo " + grupo.getName() + "...");
					File[] listDirUsu = grupo.listFiles();
					for (File usu : listDirUsu) {
						this.listado.add(new Ejercicio(usu));
					}
				} else {
					this.listadoCorrecto = false;
				}
			}			
		} else {
			this.listadoCorrecto = false;
		}
		
	}
	
	public boolean isListadoCorrecto() {
		return this.listadoCorrecto;
	}
	
	public int getNumGrupos() {
		return this.numGrupos;
	}

	public int getNumEjercicios() {
		return this.listado.size();
	}
	
	public File getDirectorioPpal() {
		return this.directorioPpal;
	}
	
	public ArrayList<Ejercicio> getListado() {
		return this.listado;
	}
	
	public boolean grabarArchivo(String archivo) {
		// Lo graba con el nombre indicado en "archivo" en el directorio raiz
		String archivoCompleto = this.directorioPpal + File.separator + archivo;
		return EscritorArchivosTexto.createFile(archivoCompleto, this.toString());
	}
	
	public boolean grabarArchivo(File archivo) {
		// lo graba en la ruta indicada por "archivo"
		return EscritorArchivosTexto.createFile(archivo.getPath(), this.toString());
	}
	
	@Override
	public String toString() {
		String ret;
		ret = "Listado de ejercicios de " + this.getDirectorioPpal().getPath() + ":";
		ret = ret + "\nGRUPO;USU;PRUEBA_A;HAY_COPIA?;PRUEBA_B;HAY_COPIA?;PRUEBA_C;HAY_COPIA?;OPOSITOR_UNICO?;NUMERO_PRUEBAS?;NUMERO_ARCHIVOS?";
		Iterator<Ejercicio> i = this.getListado().iterator();
		while (i.hasNext()) {
			Ejercicio ej = i.next();
			if (!ej.isEjercicioVacio()) {
				ret = ret + "\n" + ej.getGrupo();
				ret = ret + ";" + ej.getUsu();
				if (ej.getPruebaA() != null) {
					ret = ret + ";" + ej.getPruebaA().getArchivo().getName() + ";" + toStringSI_NO(ej.getPruebaA().isTieneCopia());
				} else {
					ret = ret + ";NO;NO";
				}
				if (ej.getPruebaB() != null) {
					ret = ret + ";" + ej.getPruebaB().getArchivo().getName() + ";" + toStringSI_NO(ej.getPruebaB().isTieneCopia());
				} else {
					ret = ret + ";NO;NO";
				}
				if (ej.getPruebaC() != null) {
					ret = ret + ";" + ej.getPruebaC().getArchivo().getName() + ";" + toStringSI_NO(ej.getPruebaC().isTieneCopia());
				} else {
					ret = ret + ";NO;NO";
				}
				ret = ret + ";" + toStringCORRECTO_INCORRECTO(ej.isOpositorUnico());
				ret = ret + ";" + toStringCORRECTO_INCORRECTO(!ej.isTieneMasDeTresPruebas());
				ret = ret + ";" + toStringCORRECTO_INCORRECTO(!ej.isTieneOtrosArchivos());
			}
		}
		return ret;
	}
	
	private String toStringSI_NO(boolean b) {
		return (b ? "SI" : "NO");
	}
	
	private String toStringCORRECTO_INCORRECTO(boolean b) {
		return (b ? "CORRECTO" : "INCORRECTO");
	}
	
	public boolean existe(String codOpositor, String tipoPrueba) {
		Iterator<Ejercicio> i = this.getListado().iterator();
		boolean encontrado = false;
		
		while (!encontrado && i.hasNext()) {
			Ejercicio ej = i.next();
			if (ej.isOpositorUnico() && !ej.isEjercicioVacio() && ej.getOpositor() != null) {
				if (ej.getOpositor().equals(codOpositor)) {
					if (tipoPrueba.equals("A")) {
						encontrado = (ej.getPruebaA() != null);
					} else if (tipoPrueba.equals("B")) {
						encontrado = (ej.getPruebaB() != null);
					} else if (tipoPrueba.equals("C")) {
						encontrado = (ej.getPruebaC() != null);
					} else {
						encontrado = false;
					}
				}
			}
		}
		
		return encontrado;
	}
}
