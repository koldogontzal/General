package manejadorID;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ManejadorID extends HashMap<String, RegistroID> {
	

	private static final long serialVersionUID = 2676628178129258057L;
	
	private File archivoDatos;	
	
	private ArrayList<String> arrayID;
	
	public ManejadorID(String archivo) {
		// Como parametro de entrada, la dirección del archivo de texto que contiene el listado
		// de ID, sus ETIQUETAS y el IDde su padre.
		// Este archivo tiene que estar codificado en modo texto de WINDOWS para que lea bien los caracteres
		// Si está en UNICODE, entonces peta...
		super();
		this.archivoDatos = new File(archivo);
		this.arrayID = new ArrayList<String>();
		
		if (!this.archivoDatos.exists()) {
			System.out.println("ERROR: " + this.archivoDatos.getPath() + " el archivo de datos de ID especificado no existe.");
			
			System.exit(0);
		} else {
			System.out.println("Leyendo tabla de IDs");
			boolean resultado = this.leerTablaDeFichero();
			if (resultado) {
				System.out.println("Terminado");
			} else {
				System.out.println("ERROR leyendo el archivo de ID. No tiene el formato adecuado");
			}
		}
	}
	
	private boolean leerTablaDeFichero() {
		// Lee el archivo de texto con todos los ID. Este ha de ser un listado de líneas compuestas únicamente de los ID.
		// si todo va bien, devuelve TRUE. Si no, FALSE.
		try {
			// Creo el stream adecuado
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(this.archivoDatos)));
			
			// Leo 		
			String linea = null;
			while ((linea = br.readLine()) != null) { 
				//System.out.println(linea);
				RegistroID reg = new RegistroID(linea);
				String id = reg.getId();
				super.put(id, reg);
				this.arrayID.add(id);
			}
			
			// Ordena el Array de ID
			Collections.sort(this.arrayID);
			
			//Cierro todo
			br.close();
			
			return true;
			
		} catch (Exception e) {
			System.out.println("ERROR leyendo archivo " + this.archivoDatos.getPath());
			e.printStackTrace();
			return false;
		} 
	}
	
	public ArrayList<String> getArrayId() {
		return this.arrayID;
	}
	
	public String pathCompleto(String id) {
		String ret = "";
		RegistroID reg = super.get(id);
		if (reg != null) {
			String padre = reg.getIdPadre();
			if (padre.equals("Internet")) {
				ret = "Inicio";
			} else {
				ret = this.pathCompleto(padre);
			}
		}
		return ret + " > " + reg.getEtiqueta();
	}
}
