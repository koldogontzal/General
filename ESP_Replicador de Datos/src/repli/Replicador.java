/**
 * 
 */
package repli;

import gui.Aplicacion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * @author Luis
 *
 */
public class Replicador {

	/**
	 * @param args
	 */
	
	private File directorioBaseOriginal;	
	private File directorioBaseBackUp;
	
	private List<String> listadoOriginales;
	private List<String> listadoBackUp;
	
	private Aplicacion app; //
	
	public Replicador (String nombreOriginal, String nombreBackUp, Aplicacion app) {
		this.app = app;
		
		this.directorioBaseOriginal = new File (nombreOriginal);
		this.directorioBaseBackUp = new File (nombreBackUp);
		
		this.listadoOriginales = new ArrayList<String>();
		this.listadoBackUp = new ArrayList<String>();
		
		// Si no son directorios, los ignora y si no hace lo suyo
		if (this.directorioBaseOriginal.isDirectory() && this.directorioBaseBackUp.isDirectory()) {
			
			
			System.out.println(this.directorioBaseOriginal.lastModified() +" " + this.directorioBaseBackUp.lastModified());
			
			
			if ((this.directorioBaseOriginal.lastModified() != this.directorioBaseBackUp.lastModified()) 
					|| (this.app.esRevisionExhaustiva())){
				// Si las fechas son distintas, hay que revisar todo, sin duda
				// O si se selcciona una revision exhaustiva.
				this.buscarOriginales();
				this.buscarBackUp();
				this.buscarDiferenciasYCorregir();
				
				this.directorioBaseBackUp.setLastModified(this.directorioBaseOriginal.lastModified());
				
			} else {
				// Si las fechas son iguales, hay que revisar sólo los subdirectorios.
				// Además, los subdirectorios tienen que ser iguales (los mismos nombres)
				// en el origen y en el destino, porque si no, las fechas serían distintas.
				// La única posibilidad es que hayan cambiado sus fechas, pero NUNCA
				// habrá subdirectorios con nombres distintos
				this.app.escribirLinea("OK: " + this.directorioBaseOriginal.getPath());
				this.buscarOriginales();
				this.buscarDiferenciasYCorregirSoloDirectorios();
			}
		}		
	}

	
	private void buscarDiferenciasYCorregirSoloDirectorios() {
		// Recorremos los Files sólo en la lista original
		Iterator<String> it = this.listadoOriginales.iterator();
		while (it.hasNext()) {
			// Obtiene el nombre en el listado de Originales
			String nombreOriginal = (String) it.next();
			File fileActual = new File (this.directorioBaseOriginal, nombreOriginal);
			
			// Se interesa sólo por los directorios
			if (fileActual.isDirectory()) {
				// Tiene que estar también en el de BackUp, porque las fechas son iguales
				File fileActualBackUp = new File (this.directorioBaseBackUp, nombreOriginal);
				if (!fileActualBackUp.exists()) {
					// Situación que no debería darse nunca, pero por si acaso, pongo un
					// mensaje de error en la pantalla
					this.app.escribirLinea("\n\n\n\tERROR: " + fileActualBackUp.getPath() + " no existe.\n\n\n");
				} else {
					// situación normal, ambos existen:
					// llamada recursiva a revisarlos
					new Replicador (fileActual.getPath(), fileActualBackUp.getPath(), this.app);
				}
			}
		}		
	}


	private void buscarDiferenciasYCorregir() {
		// Recorremos los Files en la lista original y si están o no en la de BackUp se actúa en consecuencia
		Iterator<String> it = this.listadoOriginales.iterator();
		while (it.hasNext()) {
			// Obtiene el nombre en el listado de Originales
			String nombreOriginal = (String) it.next();
			File fileActual = new File (this.directorioBaseOriginal, nombreOriginal);
			
			// Lo busca en el listado de BackUp
			if (this.listadoBackUp.contains(nombreOriginal))
			{
				this.app.escribirLinea("Ok: " + fileActual.getPath());
				// Lo contiene: si es un directorio hace una llamada recursiva. Si es un fichero, lo borra de la lista de BackUp
				if (fileActual.isDirectory()) {
					//Es un directorio
					File fileActualBackUp = new File (this.directorioBaseBackUp, nombreOriginal);
					new Replicador (fileActual.getPath(), fileActualBackUp.getPath(), this.app);
					this.listadoBackUp.remove(nombreOriginal);
				} else {
					// Es un archivo, así que lo borra de la  lista de BackUp aún por revisar
					// Porque está en ambos sitios. Tambien mira si el tamaño de ambos es el mismo y
					// si no lo es, entonces pregunta si quieres copiarlo también
					this.listadoBackUp.remove(nombreOriginal);		

					File fileActualBackUp = new File (this.directorioBaseBackUp, nombreOriginal);
					long tamOrigLong = fileActual.length();
					long tamBackLong = fileActualBackUp.length();
					if (tamOrigLong != tamBackLong) {
						// Los ficheros tienen el mismo nombre, pero son distintos
						int tamOrig = (int) (tamOrigLong / 1024);
						int tamBack = (int) (tamBackLong / 1024);
						
						String texto = "¿Desea sutituir el fichero\n\t(" + tamBack + "KB) " + 
								fileActualBackUp.getPath() + "\npor el fichero\n\t(" + tamOrig + "KB) " +
								fileActual.getPath() + " ?";						
						int respuesta;
						if (this.app.esPedirConfirmacion()) {
							respuesta = JOptionPane.showConfirmDialog(this.app, texto, "Aviso", JOptionPane.YES_NO_OPTION);
						} else {
							respuesta = JOptionPane.OK_OPTION;
						}
						
						
						if (respuesta == JOptionPane.OK_OPTION) {
							// En este caso quiere sobreescribirlo
							this.app.escribirLinea("Copiando " + fileActual.getPath());
							try {
								this.copiaFichero (fileActual, fileActualBackUp);
							} catch (IOException e) {
								this.app.escribirLinea("Error copiando el archivo " + fileActual.getPath());
								e.printStackTrace();
							}
							
						} else {
							this.app.escribirLinea("IGNORADO: " + fileActual.getPath());
						}
							
					}
				}
			} else {
				// NO lo contiene
				File fileActualBackUp = new File (this.directorioBaseBackUp, nombreOriginal);
				this.app.escribirLinea("Copiando " + fileActual.getPath());
				if (fileActual.isDirectory()) {
					//Si es un directorio, lo crea en BackUp y hace llamada recursiva
					fileActualBackUp.mkdir();
					new Replicador (fileActual.getPath(), fileActualBackUp.getPath(), this.app);
					
					fileActualBackUp.setLastModified(fileActual.lastModified());
				
				} else {
					// Es un archivo, entonces hay que copiarlo
					try {
						this.copiaFichero (fileActual, fileActualBackUp);
					} catch (IOException e) {
						this.app.escribirLinea("Error copiando el archivo " + fileActual.getPath());
						e.printStackTrace();
					}
				}
			}
		}
		
		// SI la lista de BackUp no está vacía, entonces hay cosas que hay que borrar
		if (!this.listadoBackUp.isEmpty()) {
			this.borrarFicherosBackUp();
		}	
	}

	
	private void borrarFicherosBackUp() {
		// Recorremos la lista de loss BackUp que aún quedan y los borramos
		Iterator<String> it = this.listadoBackUp.iterator();
		while (it.hasNext()) {
			// Obtiene el nombre en el listado de los BackUps
			String nombreBackUp = (String) it.next();
			File fileActual = new File (this.directorioBaseBackUp, nombreBackUp);
			if (fileActual.isFile()) {
				// Si es un fichero lo borra
				fileActual.delete();
			} else {
				// si es un directorio, llama a borrar directorios
				this.borrarDirectorioRecursivo(fileActual);
				fileActual.delete();
			}
			this.app.escribirLinea("Borrando " + fileActual.getPath());
		}		
	}
	
	private void borrarDirectorioRecursivo (File directorio) {
		File [] listado = directorio.listFiles();
		for (File archivo: listado) {
			if (archivo.isFile()) {
				archivo.delete();
			}
			else {
				this.borrarDirectorioRecursivo(archivo);
				archivo.delete();
			}
			this.app.escribirLinea("Borrando " + archivo.getPath());
		}
	}


	public void copiaFichero(File src, File dst) throws IOException {
		/*
		 * 
		 *  // Forma antigua de copiar dos archivos
		 *  
		 * InputStream in = new FileInputStream(src); OutputStream out = new
		 * FileOutputStream(dst);
		 * 
		 * 
		 * byte[] buf = new byte[10240]; int len; while ((len = in.read(buf)) >
		 * 0) { out.write(buf, 0, len); }
		 * 
		 * in.close(); out.close();
		 * 
		 * dst.setLastModified(src.lastModified());
		 */
		

		// Crea el canal channel sobre el original
		FileChannel srcChannel = new FileInputStream(src).getChannel();

		// Crea el cana channel sobre el destino
		FileChannel dstChannel = new FileOutputStream(dst).getChannel();

		// Coopia el contenido del archivo del original al destino
		dstChannel.transferFrom(srcChannel, 0, srcChannel.size());

		// Cierra los canales channels
		srcChannel.close();
		dstChannel.close();
		
		// Copia los atributos (en la versión 7 de Java será posible usar nuevas
		// clases -DosFileAttributeView- que permitirán una manejo mejor de los
		// atributos de NTFS/DOS)
		dst.setLastModified(src.lastModified());
		dst.setExecutable(src.canExecute());
		dst.setReadable(src.canRead());
		dst.setWritable(src.canWrite());
		if (src.canRead() && !src.canWrite()) {
			dst.setReadOnly();
		}
		
    } 
	

	private void buscarBackUp() {
		File[] listadoDirectorios = this.directorioBaseBackUp.listFiles();
		for (File archivo : listadoDirectorios) {
			if (!(archivo.isHidden() && this.app.esOmitirOcultos())) {
				this.listadoBackUp.add(archivo.getName());
			}
		}	
	}

	
	private void buscarOriginales() {
		File[] listadoDirectorios = this.directorioBaseOriginal.listFiles();
		for (File archivo : listadoDirectorios) {
			if (!(archivo.isHidden() && this.app.esOmitirOcultos())) {
				this.listadoOriginales.add(archivo.getName());
			}
		}		
	}
}

