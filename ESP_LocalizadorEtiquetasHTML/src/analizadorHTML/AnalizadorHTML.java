package analizadorHTML;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
 
public class AnalizadorHTML {
	private ArrayList<Etiqueta> listaEtiquetas = new ArrayList<Etiqueta>(100);

	private String textoHTML;
	
	public AnalizadorHTML(String texto) {
		this.textoHTML = texto;
		this.calcularListaEtiquetas();
	}
	
	public AnalizadorHTML(File archivo) {		
		try {
			// Asocia fichero al stream
			FileReader fr = new FileReader(archivo);
			
			// Creo el stream adecuado
			BufferedReader br = new BufferedReader(fr);
			
			// Leo el fichero
			String linea = null;
			StringBuilder texto = new StringBuilder(10000);
			while ((linea = br.readLine()) != null) {
				texto.append(linea);
			}
			
			// Asigno variables
			this.textoHTML = texto.toString();
			this.calcularListaEtiquetas();
			
			// Cieroo el Reader
			br.close();
			
		} catch (FileNotFoundException e) {
			System.out.println(archivo.getPath() + ": Archivo no encontrado");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(archivo.getPath() + ": Error leyendo el archivo");
			e.printStackTrace();
		}		
	}
	
	private void calcularListaEtiquetas() {
		int pos = 0;
		int posLT, posGT;
		
		while (pos < this.textoHTML.length()) {
			posLT = this.textoHTML.indexOf('<', pos);
			posGT = this.textoHTML.indexOf('>', pos);
			
			// Primero busca una etiqueta
			if ((posLT == -1) || (posGT == -1)) {
				// Caso en el que ya no hay más etiquetas
				pos = this.textoHTML.length();
			} else if (posLT < posGT) {
				// Busca las estiquetas
				Etiqueta etiqueta = new Etiqueta(this.textoHTML.substring(posLT + 1, posGT));
				this.listaEtiquetas.add(etiqueta);
				pos = posGT + 1;
			} /* else {
				// Para asegurar que el bucle termina siempre
				pos++;
			} */
			
			// Segundo, busca que despues de la etiqueta haya texto y no otra etiqueta
			if ((pos < this.textoHTML.length()) && (this.textoHTML.charAt(pos) != '<')) {
				posLT = this.textoHTML.indexOf('<', pos);
				
				if (posLT > pos) {
					// Esta condición es necesaria para evitar una excepcion en el caso de estar justo despues
					// de la ultima etiqueta.
					Etiqueta contenido = new EtiquetaContenido(this.textoHTML.substring(pos, posLT));
					this.listaEtiquetas.add(contenido);
					pos = posLT;
				}				
			}
		}
	}
	
	public ArrayList<Etiqueta> getArrayListEtiquetas() {
		return this.listaEtiquetas;
	}
	
	
	@Override
	public String toString() {
		Iterator<Etiqueta> i = this.listaEtiquetas.iterator();
		int numero = 1;
		String resultado = "";
		while (i.hasNext()) {
			resultado = resultado + numero + ". " + i.next() + "\n";
			numero++;
		}
		return resultado;
	}

}
