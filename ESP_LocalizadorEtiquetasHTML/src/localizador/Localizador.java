package localizador;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.text.BadLocationException;

import manejadorID.ManejadorID;

public class Localizador {
	
	private Cliente cliente;	
	private DataOutputStream descrFich;
	private int tipoAnalisis;
	
	// Objetivos de análisis
	public static final int ANALIZAR_TODO_SITE = -1;
	public static final int ANALIZAR_DIR_WEB = -2;
	public static final int ANALIZAR_FICHERO = -3;
	
	// Página de inicio en el caso de analizar todo el site. Si es "", entonces analiza todo el site.
	// Si es distitno de "", entonces empieza a analizar a partir de esa pagina. 
	// Esto puede utlizarse en el caso de parar un análisis de todo el site a mitad o para cuando
	// se queda colgado
	private static final String PAGINA_INICIO_ANALISIS_TODO_EL_SITE_SS = "";
	
	// Tipos de analisis
	// Analisis usando la clase AnalidadorHTML (valores < FRONTERA_TIPOS)
	public static final int TIPO_LI_FUERA_DE_LISTA = 1001;
	public static final int TIPO_ATR_ALIGN_DESACONSEJADO = 1002;
	// Frontera entre clases
	public static final int FRONTERA_TIPOS = 2000;
	// Analisis usando la clase HTMLDocument (valores > FRONTERA_TIPOS)
	public static final int TIPO_A_HERF_TITLE_INCORRECTO = 2001;
	public static final int TIPO_LOCALIZADOR_FRAME = 2002;
	public static final int TIPO_H1_H6_ORDEN_ERRONEO = 2003;
	public static final int TIPO_TD_SIN_HEADER = 2004;
	public static final int TIPO_TABLE_SIN_TH = 2005;
	public static final int TIPO_ID_O_NAME_REPETIDOS = 2006;
	public static final int TIPO_ATR_BORDER_DESACONSEJADO = 2007;
	
	// Path del fichero que contiene el listado de ID del site
	public static final String PATH_FICHERO_IDs = "C:/tmp/ID_secciones_y_padres_20080721_v2_a.txt";
	
	
	//============================================================================
	// El constructor tiene el nombre del fichero de salida como parametro de entrada 
	// Como entrada, el nombre del fichero de entrada
	// La constante del tipo de analisis
	// Si es necesario, el String con la dirección de la web a analizar o el fichero .HTM a analizar
	//============================================================================
	public Localizador(String ficheroSalida, int objetivoAnalisis, String origen, int tipoAnalisis) {
		this.tipoAnalisis = tipoAnalisis;
		try {
			// Según el tipo de analisis, crea un cliente u otro
			if (tipoAnalisis < Localizador.FRONTERA_TIPOS) {
				this.cliente = new Cliente(Cliente.CLASE_ANALIZADOR_HTML);
			} else {
				this.cliente = new Cliente(Cliente.CLASE_HTML_DOCUMENT);
			}
			
			//Fichero de salida
			this.descrFich = new DataOutputStream(new FileOutputStream(ficheroSalida));		 
			
			switch (objetivoAnalisis) {
				case ANALIZAR_TODO_SITE:
					this.analizarTodoElSiteSS();
					break;
				case ANALIZAR_DIR_WEB:
					this.analizarURL(origen);
					break;
				case ANALIZAR_FICHERO:
					this.analizarFile(origen);
					break;
			}
			
			// System.out.println(this.cliente); // Para depuracion
			
	    	this.descrFich.close();
	    	System.out.println("Fin");
	  			
	  	} catch(FileNotFoundException e) {
	  		System.out.println(ficheroSalida + ": Error abriendo el fichero para guardar la salida.");
			e.printStackTrace();
	  	} catch (IOException e) {
			System.out.println(origen + ": Error leyendo la pagina de origen.");
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}		
	}
	
	

	// =======================================================
	// Analizar la pagina segun el tipo de análisis prefijado
	//=========================================================
	private void analizar(String id, String path) {
		// Procesa la pagina
		boolean analisisCorrecto;
		
		switch (this.tipoAnalisis) {
		case TIPO_LI_FUERA_DE_LISTA:
			//====================================================================================
			// Analiza una página buscando etiquetas <LI> que no están correctamente delimitadas
			// por una lista <OL> o <UL>
			//====================================================================================	
			analisisCorrecto = !this.cliente.localizadorEtiquetasLIfueraDeListas();
			if (analisisCorrecto) {
				System.out.println("\tEtiquetas <LI> correctas dentro de listas");
			} else {					
				System.out.println("\t==================ATENCIÓN================ Encontrada etiqueta <LI> fuera de lista");
				this.crearFicheroResultados(id, "LI_fuera_de_lista", path, "");
			}
			break;
		case TIPO_A_HERF_TITLE_INCORRECTO:
			//====================================================================================
			// Analiza una página buscando etiquetas <A HREF> cuyos atributos 'title' tengan un texto
			// igual al texto del propio enlace en sí.
			//====================================================================================
			analisisCorrecto = !this.cliente.localizadorEtiquetasAHrefConTitleInvalido();
			if (analisisCorrecto) {
				System.out.println("\tEtiquetas <A HREF> con 'title' correcto");
			} else {
				System.out.println("\t==================ATENCIÓN================ Encontrada etiqueta <A HREF> con 'title' incorrecto");
				this.crearFicheroResultados(id, "A_HREF_title_incorrecto", path, "");
			}
			break;
		case TIPO_LOCALIZADOR_FRAME:
			//====================================================================================
			// Analiza una página buscando etiquetas <FRAME>
			//====================================================================================
			analisisCorrecto = !this.cliente.localizadorEtiquetasFrame();
			if (analisisCorrecto) {
				System.out.println("\tNo usa etiquetas <FRAME>");
			} else {
				System.out.println("\t==================ATENCIÓN================ Encontrada etiqueta <FRAME>");
				this.crearFicheroResultados(id, "FRAME", path, "");
			}
			break;
		case TIPO_H1_H6_ORDEN_ERRONEO:			
			//====================================================================================
			// Analiza una página buscando saltos ilegales entre las Cabeceras (H1..H6)
			//====================================================================================
			analisisCorrecto = !this.cliente.localizadorCabecerasOrdenIncorrecto();
			if (analisisCorrecto) {
				System.out.println("\tCabeceras correctas");
			} else {
				System.out.println("\t==================ERROR================ Orden de cabeceras incorrecto");
				this.crearFicheroResultados(id, "H1..H6_orden_incorrecto", path, "");
			}
			break;
		case TIPO_TD_SIN_HEADER:			
			//====================================================================================
			// Analiza una página buscando Tablas incorrectas: elementos <TD> que no tienen el atributo "headers"
			//====================================================================================
			analisisCorrecto = !this.cliente.localizadorTablasConTDSinAtributoHeaders();
			if (analisisCorrecto) {
				System.out.println("\tTablas correctas");
			} else {
				System.out.println("\t==================ERROR================ Tablas incorrectas");
				this.crearFicheroResultados(id, "TD_sin_atributo_'headers'", path, "");
			}
			break;
		case TIPO_TABLE_SIN_TH:			
			//====================================================================================
			// Analiza una página buscando Tablas incorrectas: Tablas que no incluyen elementos <TH>
			//====================================================================================
			analisisCorrecto = !this.cliente.localizadorTablasSinCabeceras();
			if (analisisCorrecto) {
				System.out.println("\tTablas correctas");
			} else {
				System.out.println("\t==================ERROR================ Tablas incorrectas");
				this.crearFicheroResultados(id, "TABLE_sin_TH", path, "");
			}
			break;
		case TIPO_ID_O_NAME_REPETIDOS:			
			//====================================================================================
			// Analiza una página buscando atributos 'id' o 'name' repetidos
			// Ambos atributos comaprten el mismo espacio de nombre y no puede haber duplicados en
			// la misma pagina
			//====================================================================================
			analisisCorrecto = !this.cliente.localizadorIdNameRepetidos();
			if (analisisCorrecto) {
				System.out.println("\tAtributos 'id' y/o 'name' sin repetir");
			} else {
				System.out.println("\t==================ERROR================ Atributos 'id' y/o 'name' repetidos");
				this.crearFicheroResultados(id, "ID_NAME_Repetidos", path, "");
			}
			break;
		case TIPO_ATR_BORDER_DESACONSEJADO:			
			//====================================================================================
			// Analiza una página buscando atributos 'border' desaconsejados
			// Esto ocurre cuando se usa con los elementos <IMG> u <OBJECT>
			//====================================================================================
			analisisCorrecto = !this.cliente.localizadorBorderDesaconsejado();
			if (analisisCorrecto) {
				System.out.println("\tSin atributos 'border' desaconsejados");
			} else {
				System.out.println("\t==================ERROR================ Atributo 'border' desaconsejado");
				this.crearFicheroResultados(id, "BORDER_desaconsejado", path, "");
			}
			break;
		case TIPO_ATR_ALIGN_DESACONSEJADO:			
			//====================================================================================
			// Analiza una página buscando atributos 'align' desaconsejados
			// Esto ocurre cuando se usa con los elementos <CAPTION>, <APPLET>, <IFRAME>, <IMG>,
			// <INPUT>, <OBJECT>, <LEGEND>, <TABLE>, <HR>, <DIV>, <H1>, <H2>, <H3>, <H4>, <H5>, <H6> y <P>
			//====================================================================================
			analisisCorrecto = !this.cliente.localizadorAlignDesaconsejado();
			if (analisisCorrecto) {
				System.out.println("\tAtributos 'align' correctos");
			} else {
				System.out.println("\t==================ERROR================ Atributo 'align' desaconsejado");
				this.crearFicheroResultados(id, "ALIGN_desaconsejado", path, "");
			}
			break;
		}		
	}
	
	
	
	// =======================================================
	// Tres objetivos de analisis diferentes, un archivo, una dir. web o todo el site de la SS
	// =======================================================
	private void analizarFile(String origen) {
		this.cliente.ObtenerPaginaFicheroLocal(origen);
		this.analizar("", origen);	
	}

	private void analizarURL(String origen) throws IOException, BadLocationException {
		this.cliente.ObtenerPaginaDeURL(origen);
		this.analizar("", origen);
	}

	private void analizarTodoElSiteSS() throws Exception {
		// Carga el fichero con los ID.
		ManejadorID manejadorID = new ManejadorID(PATH_FICHERO_IDs);
		ArrayList<String> listadoID = manejadorID.getArrayId();
		
		// recorre toda la lista de ID
		Iterator<String> iterator = listadoID.iterator();
		int contador = 0;
		int numSecciones = listadoID.size();
		
		// Este bloque es para empezar el análisis a partir de un ID determinado
		// definido en la constante PAGINA_INICIO_ANALISIS_TODO_EL_SITE
		if (!PAGINA_INICIO_ANALISIS_TODO_EL_SITE_SS.equals("")) {
			while (iterator.hasNext() && !(iterator.next()).equals(PAGINA_INICIO_ANALISIS_TODO_EL_SITE_SS)) {
				// avanza la lista hasta la posicion deseada
				contador++;
			}
		}
		// Bucle lector de todas las páginas
		while (iterator.hasNext()) {
			String id = (String)iterator.next();
						
			// Imprime información del porcentaje realizado del analisis
			double porcentaje = (double)(contador++) * 100.0 / (double)numSecciones;
			System.out.print("(" + String.format("%.2f", porcentaje) + "%) ");
			
			// Genera la URL en base al ID
			String 	url = "http://www.seg-social.es/Internet_1/ssNODELINK/" + id;
			
			// Obtiene la url solicitada.
			System.out.println(url);
			this.cliente.ObtenerPaginaDeURL(url);
			
			// analiza la página
			this.analizar(id, manejadorID.pathCompleto(id));
			
			// Libera la página
			this.cliente.liberarPagina();
		}
		
		// Escribe linea de finalizacion en el fichero de salida
		this.crearFicheroResultados("---", "Fin del análisis", "", "");
	}
	
	
	//====================================================================================
	// Escribir en un fichero los resultados de los registros encontrados.
	//====================================================================================
	private void crearFicheroResultados(String id, String Elemento, String path, String grupo) {
		try {
			this.descrFich.writeBytes(id + "\t" + Elemento + "\t" + path + "\t" + grupo + "\n");
		} catch (IOException e) {
			System.out.println("ERROR: Imposible grabar en el fichero de salida de datos");
			e.printStackTrace();
		}
	}

}
