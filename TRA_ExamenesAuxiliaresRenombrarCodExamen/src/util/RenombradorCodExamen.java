package util;

import java.io.File;
import java.util.Iterator;

import utils.ListaOpositores;
import utils.Opositor;


public class RenombradorCodExamen {
	
	private ListaOpositores lista_inicial;
	private ListaOpositores lista_modificadora;
	
	public RenombradorCodExamen(String pathLista0, String pathLista1) {
		this.lista_inicial = new ListaOpositores(pathLista0);
		this.lista_modificadora = new ListaOpositores(pathLista1);
		
		Iterator<Opositor> i = this.lista_inicial.iterator();
		while (i.hasNext()) {
			Opositor opo0 = i.next();
			String nif = opo0.getNIF();
			
			Opositor opo1 = this.lista_modificadora.buscarPorNIF(nif);
			opo0.setCodModificado(opo1.getCodExamen());			
		}
		
	}
	
	public void renombrarArchivos(String directorioPadre) {
		this.renombrarArchivos(new File(directorioPadre));
	}
	
	public void renombrarArchivos(File directorioPadre) {
		// Hace dos pasadas
		// La primera cambia los codigos y añade una letra R al principio, para que no se superpongan los nuevos con los viejos
		// La segunda quita la letra R añadida en el paso anterior
		this.renombrarArchivosRecursivo(directorioPadre);
		this.renombrarArchivosRecursivo(directorioPadre);		
	}
	
	private void renombrarArchivosRecursivo(File directorioPadre) {		
		if (directorioPadre.isDirectory()) {
			File[] listado = directorioPadre.listFiles();
			for (File archivo : listado) {
				if (archivo.isDirectory()) {
					this.renombrarArchivos(archivo);
				} else {
					// Hay que renombrar el archivo. Comprueba que tenga el formato de nombre [[T]][ABC]00000_copia.xxx
					// Siendo la extensión en este caso la de Office 2003, .DOC o .XLS
					String nombre = archivo.getName().toUpperCase();
					String padre = archivo.getParent();
					
					if (nombre.matches("[ABC][0-9][0-9][0-9][0-9][0-9]_COPIA.[A-Z][A-Z][A-Z]")) {
						// Tiene el formato para renombrar cambiando el codigo y añadiendo una T al principio
						String tipoPrueba = nombre.substring(0,1);
						String extension = nombre.substring(12, 16);
						
						String codigo0 = nombre.substring(1, 6);						
						Opositor opo = this.lista_inicial.buscarPorCodExamen(codigo0);
						
						if (opo == null) {
							// No existe el codigo de examen en el listado de opositores dado
							System.out.println("AVISO: No existe el opositor con codigo " + codigo0 + " en el listado inicial");
						} else {
							// opositor encontrado, existe el código de examen
							String codigo1 = opo.getCodModificado();
							
							if (codigo1 == null) {
								// El opositor no tiene un codigo de examen alternativo
								System.out.println("AVISO: El opositor con codigo " + codigo0 + " no tiene definido un codigo alternativo. No se renombrara el fichero");
							} else {
								// El opositor sí tiene un codigo de examen alternativo
								String path = padre + File.separator + "T" + tipoPrueba + codigo1 + extension;
								archivo.renameTo(new File(path));
							}
						}					
					}
					
					if (nombre.matches("T[ABC][0-9][0-9][0-9][0-9][0-9].[A-Z][A-Z][A-Z]")) {
						// Tiene el formato para renombrar quitando la R del principio
						String nuevoNombre = nombre.substring(1, 11);
						
						String path = padre + File.separator + nuevoNombre;
						archivo.renameTo(new File(path));
					}					
				}
			}
		} 
	}
}
