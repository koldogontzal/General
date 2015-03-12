package localizador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.ElementIterator;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import analizadorHTML.AnalizadorHTML;
import analizadorHTML.Etiqueta;

public class Cliente {
	
	public static final int CLASE_ANALIZADOR_HTML = 1;
	public static final int CLASE_HTML_DOCUMENT = 2;
	
	// Identificador del tipo de analizador
	private int tipo;
	
	// Para analizar según mi clase AnalizadorHTML
	private AnalizadorHTML analizador;
	
	// Para analizar según la clase Java HTMLDocument
	private HTMLDocument htmlDoc;
	private HTMLEditorKit htmlKit;
	
	public Cliente(int tipo) {
		this.tipo = tipo;
		if (tipo == Cliente.CLASE_HTML_DOCUMENT) {
			this.htmlKit = new HTMLEditorKit();
			this.htmlDoc = (HTMLDocument)this.htmlKit.createDefaultDocument();
			this.htmlDoc.putProperty("IgnoreCharsetDirective", Boolean.TRUE);
		}
	}
	
	
	public void ObtenerPaginaFicheroLocal(String fichero) {
		if (this.tipo == Cliente.CLASE_ANALIZADOR_HTML) {	
			// Lee el fichero para analizarlo con mi clase AnalizadorHTML
			this.analizador = new AnalizadorHTML(new File(fichero));
		} else if (this.tipo == Cliente.CLASE_HTML_DOCUMENT){
			// Lee el fichero para usar HTMLDocument
			FileReader fr;
			try {
				fr = new FileReader(new File(fichero));
				this.htmlKit.read(fr, this.htmlDoc, 0);
				fr.close();
			} catch (FileNotFoundException e) {
				System.out.println(fichero + ": Error, fichero no encontrado.");
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}		
		}
	}
	
	public void ObtenerPaginaDeURL(String stringUrl) throws IOException {
		if (this.tipo == Cliente.CLASE_HTML_DOCUMENT) {	
			//	 Lee la página para analizarla usando la clase Java HTMLDocument
			try {
				URL url2 = new URL(stringUrl);
				HttpURLConnection httpurlconnection = (HttpURLConnection) url2.openConnection();
				httpurlconnection.setRequestMethod("GET");

				httpurlconnection.connect();
				
				InputStreamReader inputstreamreader = new InputStreamReader(httpurlconnection.getInputStream());
				this.htmlKit.read(inputstreamreader, this.htmlDoc, 0);
				
				inputstreamreader.close();
				httpurlconnection.disconnect();
			} catch (BadLocationException e) {
				// TODO Bloque catch generado automáticamente
				e.printStackTrace();
			} catch (SocketTimeoutException e) {
				System.out.println("************************************* SocketTimeout Exception *********************************");
				e.printStackTrace();
			}
					
		} else if (this.tipo == Cliente.CLASE_ANALIZADOR_HTML){	
			// Lee la página para analizarlo con mi clase AnalizadorHTML 
			URL url = new URL(stringUrl);
			HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
			conexion.setRequestMethod("GET");
			conexion.connect();
			InputStreamReader is = new InputStreamReader(conexion.getInputStream());		
			BufferedReader br = new BufferedReader(is);
			
			StringBuilder pagina = new StringBuilder(10000);
			String linea = null;
			while ((linea = br.readLine()) != null) {
				pagina.append(linea);
			}
			this.analizador = new AnalizadorHTML(pagina.toString());
			
			br.close();
			is.close();
			conexion.disconnect();
		}
		
	}
	
	public void liberarPagina() throws Exception {
		if (this.tipo == Cliente.CLASE_HTML_DOCUMENT) {
			this.htmlDoc = (HTMLDocument)this.htmlKit.createDefaultDocument();
			this.htmlDoc.putProperty("IgnoreCharsetDirective", Boolean.TRUE);
		}
	}
	
	
	@Override
	public String toString() {
		if (this.tipo == Cliente.CLASE_ANALIZADOR_HTML) {
		return this.analizador.toString();
		} else if (this.tipo == Cliente.CLASE_HTML_DOCUMENT) {
			ElementIterator iteradorElementos = new ElementIterator(this.htmlDoc);
			
			Element elemento;
			String res = "";
			while ((elemento = iteradorElementos.next()) != null) {
				res = res + elemento.toString();
			}
			return res;
		} else {
			return null;
		}
	}
	
	public boolean localizadorAlignDesaconsejado() {
		// Devuelve "true" si la pagina contiene atributos 'align' incorrectos
		// Devuelve "false" si la pagina no los contiene
		Iterator<Etiqueta> iterador = this.analizador.getArrayListEtiquetas().iterator();
		boolean esTodoCorrecto = true;
		
		while ((esTodoCorrecto) && (iterador.hasNext())) {
			Etiqueta etiqueta = iterador.next();
			if (this.esEtiquetaIncorrectaParaAlign(etiqueta)) {
				esTodoCorrecto = !etiqueta.tieneAtributo("align");
			}
		}
		return !esTodoCorrecto;		
	}
	
	private boolean esEtiquetaIncorrectaParaAlign(Etiqueta e) {
		if (
			(e.equals(Etiqueta.CAPTION)) ||
			(e.equals(Etiqueta.APPLET)) ||
			(e.equals(Etiqueta.IFRAME)) ||
			(e.equals(Etiqueta.IMG)) ||
			(e.equals(Etiqueta.INPUT)) ||
			(e.equals(Etiqueta.OBJECT)) ||
			(e.equals(Etiqueta.LEGEND)) ||
			(e.equals(Etiqueta.TABLE)) ||
			(e.equals(Etiqueta.HR)) ||
			(e.equals(Etiqueta.DIV)) ||
			(e.equals(Etiqueta.H1)) ||
			(e.equals(Etiqueta.H2)) ||
			(e.equals(Etiqueta.H3)) ||
			(e.equals(Etiqueta.H4)) ||
			(e.equals(Etiqueta.H5)) ||
			(e.equals(Etiqueta.H6)) ||
			(e.equals(Etiqueta.P))
			) {
			return true;
		} else {
			return false;
		}
	}


	public boolean localizadorEtiquetasLIfueraDeListas() {		
		// Devuelve "true" si la pagina contiene elementos <LI> que no están en una lista
		// Devuelve "false" si la pagina no los contiene
		
		Iterator<Etiqueta> iterador = this.analizador.getArrayListEtiquetas().iterator();
		boolean esTodoCorrecto = true;
		boolean dentroDeLista = false;
		int gradoAnidamiento = 0;
		
		while ((esTodoCorrecto) && (iterador.hasNext())) {
			Etiqueta etiqueta = iterador.next();
			
			// Si es una etiqueta LI, tiene que estar dentro de una lista 
			if (etiqueta.equals(Etiqueta.LI)) {
				if (!dentroDeLista) {
					esTodoCorrecto = false;
				}				
			}
			
			// si es una etiqueta de apertura de una lista
			if ((etiqueta.equals(Etiqueta.OL)) || (etiqueta.equals(Etiqueta.UL))) {
				gradoAnidamiento++;
				dentroDeLista = true;
			}
			
			// si es una etiqueta de cierre de una lista
			if ((etiqueta.equals(Etiqueta.finOL)) || (etiqueta.equals(Etiqueta.finUL))) {
				gradoAnidamiento--;
				if (gradoAnidamiento == 0) {
					dentroDeLista = false;
				}
			}
		}
		
		return !esTodoCorrecto;
	}
	
	
	
	public boolean localizadorEtiquetasAHrefConTitleInvalido() {		
		// Devuelve "true" si la pagina contiene elementos <A HREF> cuyo atributo "title"
		//		sea el mismo que el texto del enlace.
		// Devuelve "false" si la pagina no los contiene
		
		ElementIterator iteradorElementos = new ElementIterator(this.htmlDoc);		
		Element elemento;
		boolean esTodoCorrecto = true;		
		
		while ((esTodoCorrecto) && (elemento = iteradorElementos.next()) != null) {
			AttributeSet conjAtribElemA = (AttributeSet) elemento.getAttributes().getAttribute(javax.swing.text.html.HTML.Tag.A);
			if (conjAtribElemA != null) {
				String href = (String) conjAtribElemA.getAttribute(javax.swing.text.html.HTML.Attribute.HREF);
				if (href != null) {
					String title = (String) conjAtribElemA.getAttribute(javax.swing.text.html.HTML.Attribute.TITLE);
					int posOrigen = elemento.getStartOffset();
					int longitud = elemento.getEndOffset() - posOrigen;
					try {
						String texto = this.htmlDoc.getText(posOrigen, longitud);						
						if ((texto != null) && (title != null)) {
							esTodoCorrecto = !texto.toUpperCase().equals(title.toUpperCase());
/*							
							// Parche para que no detecte estos dos casos que se dan en todas las páginas
							// del site de la SS
							if (texto.equals("Ir a Contenido") || texto.equals("Preguntas Frecuentes")) {
								esTodoCorrecto = true;
							}
							// fParche
							
 */
						}
					} catch (BadLocationException e) {
						System.out.println("Excepción leyendo el texto del elemento <A>");
						e.printStackTrace();
					}
				}
			}
		}
		
		return !esTodoCorrecto;		
	}
	
	
	public boolean localizadorEtiquetasFrame() {
		// Devuelve "true" si la pagina contiene elementos <FRAME>
		// Devuelve "false" si la pagina no los contiene
		
		ElementIterator iteradorElementos = new ElementIterator(this.htmlDoc);		
		Element elemento;
		boolean esTodoCorrecto = true;		
		
		while ((esTodoCorrecto) && (elemento = iteradorElementos.next()) != null) {
			esTodoCorrecto = !(elemento.getName().equals(javax.swing.text.html.HTML.Tag.FRAME.toString()) 
					|| elemento.getName().equals(javax.swing.text.html.HTML.Tag.FRAMESET.toString()));
		}
		
		return !esTodoCorrecto;		
	}
	
	
	
	
	public boolean localizadorTablasConTDSinAtributoHeaders() {
		// Devuelve "true" si la pagina contiene tablas mal formadas (elementos TD sin atributo "headers")
		// Devuelve "false" si la pagina no contiene tablas o estas estan bien formadas
		
		ElementIterator iteradorElementos = new ElementIterator(this.htmlDoc);		
		Element elemento;
		boolean esTodoCorrecto = true;		
		
		while ((esTodoCorrecto) && (elemento = iteradorElementos.next()) != null) {
			if (elemento.getName().equals(javax.swing.text.html.HTML.Tag.TD.toString())) {
				// Así, si hay varias tablas en la misma página, puede recordar que todas sean
				// correctas. Si alguna no lo es, devuelve TRUE al final, para indicar que hay algo mal.
				
				AttributeSet att = elemento.getAttributes();
				esTodoCorrecto = att.isDefined("headers") || att.isDefined("HEADERS") || att.isDefined("Headers");
			}
		}
		
		return !esTodoCorrecto;		
	}
	
	
	public boolean localizadorTablasSinCabeceras() {
		// Devuelve "true" si la pagina contiene tablas mal formadas (que no tienen elemento TH)
		// Devuelve "false" si la pagina no contiene tablas o estas estan bien formadas
		
		ElementIterator iteradorElementos = new ElementIterator(this.htmlDoc);		
		Element elemento;
		boolean esTodoCorrecto = true;
		
		while ((esTodoCorrecto) && (elemento = iteradorElementos.next()) != null) {
			if (elemento.getName().equals(javax.swing.text.html.HTML.Tag.TABLE.toString())) {
				// Así, si hay varias tablas en la misma página, puede recordar que todas sean
				// correctas. Si alguna no lo es, devuelve TRUE al final, para indicar que hay algo mal.
				esTodoCorrecto = esTodoCorrecto && this.analizadorRecursivoElementsTABLE(elemento);
			}
		}
		return !esTodoCorrecto;
	}
	
	private boolean analizadorRecursivoElementsTABLE(Element elemento) {
		// Busca elementos TH dentro de los hijos de un elemento TABLE
		// si los encuentra, devuelve "true"
		// en caso contrario, devuelve "false"
		if (elemento.isLeaf()) {
			return elemento.getName().equals(javax.swing.text.html.HTML.Tag.TH.toString());
		} else {
			int numHijos = elemento.getElementCount();
			boolean encontrado = elemento.getName().equals(javax.swing.text.html.HTML.Tag.TH.toString());
			for (int i = 0; i < numHijos; i++) {
				Element hijo = elemento.getElement(i);
				encontrado = encontrado || this.analizadorRecursivoElementsTABLE(hijo);
			}
			return encontrado;
		}
	}



	public boolean localizadorCabecerasOrdenIncorrecto() {
		// Si las cabeceras no siguen un orden correcto, entonces devuelve TRUE
		// Si las cabeceras son correctas, entonces devuelve FALSE
		
		ElementIterator iteradorElementos = new ElementIterator(this.htmlDoc);		
		Element elemento;
		
		boolean esTodoCorrecto = true;
		int numeroCabeceraAnterior = 0;
		
		while ((esTodoCorrecto) && ((elemento = iteradorElementos.next()) != null)) {
			int numeroCabeceraActual = this.getNumeroCabecera(elemento);
			
			if (numeroCabeceraActual > 0) {
				// Se trata de una cabecera, comparamos con la anterior.
				// Los "saltos" entre cabeceras, tienen que cumplir unas reglas:
				// Si estamos en un nivel x, la siguiente puede ser o un nivel más (x+1)
				// o el mismo nivel (x) o cualquier nivel inferior (x-1, x-2 .. 1)
				// En resumen, si al nivel actual resto el nivel anterior, la diferencia
				// tiene que ser un valor menor o igual que 1
				
				int diferencia = numeroCabeceraActual - numeroCabeceraAnterior;
				if (diferencia > 1) {
					// Este salto no está permitido
					esTodoCorrecto = false;
				} else {
					// Este salto sí está permitido, actualizamos los valores
					numeroCabeceraAnterior = numeroCabeceraActual;
				}				
			}
		}
		
		return !esTodoCorrecto;
	}

	private int getNumeroCabecera(Element elemento) {
		// Devuelve el número de cabecera del elemento H1.. H6
		// Si no es un elemento H, devuelve 0;
		if (elemento.getName().equals(javax.swing.text.html.HTML.Tag.H1.toString())) {
			return 1;
		} else if (elemento.getName().equals(javax.swing.text.html.HTML.Tag.H2.toString())) {
			return 2;
		} else if (elemento.getName().equals(javax.swing.text.html.HTML.Tag.H3.toString())) {
			return 3;
		} else if (elemento.getName().equals(javax.swing.text.html.HTML.Tag.H4.toString())) {
			return 4;
		} else if (elemento.getName().equals(javax.swing.text.html.HTML.Tag.H5.toString())) {
			return 5;
		} else if (elemento.getName().equals(javax.swing.text.html.HTML.Tag.H6.toString())) {
			return 6;
		} else {
			return 0;
		}
	}
	
	public boolean localizadorBorderDesaconsejado() {
		// Devuelve "true" si la pagina contiene atributos 'border' desaconsejados
		// Devuelve "false" si la pagina no contiene atributos desaconsejados
		ElementIterator iteradorElementos = new ElementIterator(this.htmlDoc);		
		Element elemento;
		boolean esTodoCorrecto = true;
		while ((esTodoCorrecto) && (elemento = iteradorElementos.next()) != null) {
			// Elemento IMG u OBJECT
			if (elemento.getName().equals(javax.swing.text.html.HTML.Tag.IMG.toString()) || 
					elemento.getName().equals(javax.swing.text.html.HTML.Tag.OBJECT.toString())) {
				AttributeSet atributos = elemento.getAttributes();
				String border = (String)atributos.getAttribute(javax.swing.text.html.HTML.Attribute.BORDER);
				if (border != null) {
					esTodoCorrecto = false;
				}
			}
		}
		return !esTodoCorrecto;
	}


	public boolean localizadorIdNameRepetidos() {		
		// Devuelve "true" si la pagina contiene atributos 'id' o 'name' repetidos
		// Devuelve "false" si la pagina no contiene atributos repetidos
		
		ElementIterator iteradorElementos = new ElementIterator(this.htmlDoc);		
		Element elemento;
		boolean esTodoCorrecto = true;
		ArrayList<String> idYname = new ArrayList<String>(20);
		
		while ((esTodoCorrecto) && (elemento = iteradorElementos.next()) != null) {
			AttributeSet atributos = elemento.getAttributes();
														/*
														Enumeration en = atributos.getAttributeNames();
														while (en.hasMoreElements()) {
															System.out.println(en.nextElement());
														}
														System.out.println();
														*/
			if (atributos.isDefined(javax.swing.text.html.HTML.Attribute.ID)) {
				String id = (String) atributos.getAttribute(javax.swing.text.html.HTML.Attribute.ID);
				if (idYname.contains(id)) {
					esTodoCorrecto = false;
				} else {
					idYname.add(id);
				}				
			}
			/*
			if (atributos.isDefined(javax.swing.text.html.HTML.Attribute.NAME)) {
				String name = (String) atributos.getAttribute(javax.swing.text.html.HTML.Attribute.NAME);
				if ((name != null) && (idYname.contains(name))) {
					esTodoCorrecto = false;
				} else {
					idYname.add(name);
				}	
			}
			*/
			AttributeSet conjAtribElemA = (AttributeSet) atributos.getAttribute(javax.swing.text.html.HTML.Tag.A);
			if (conjAtribElemA != null) {
				String name = (String) conjAtribElemA.getAttribute(javax.swing.text.html.HTML.Attribute.NAME);
				if ((name != null) && (idYname.contains(name))) {
					esTodoCorrecto = false;
				} else {
					//if ((name != null) && !name.equals("documentoPDF")) { // Evita casos que se repiten en muchas paginas, para elaborar otro listado alternetivo filtrado
						idYname.add(name);
					//}
				}	
			}
		}
		return !esTodoCorrecto;		
	}
}
