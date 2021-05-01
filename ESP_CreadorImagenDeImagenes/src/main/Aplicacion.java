package main;

import java.awt.Color;

import utils.cuadricula.CuadriculaDeEspacios;
import utils.cuadricula.tiles.ListadoNumeroOcurrenciasDeTiposTile;
import utils.cuadricula.tiles.Tile;
import utils.ficheros.NoImagesException;
import utils.tratimagen.efectos.*;
import utils.tratimagen.efectos.Efecto.ModulacionEfecto;
import utils.tratimagen.efectos.EfectoAutoCorreccionContraste.TiposCorreccion;


@SuppressWarnings("unused")
public class Aplicacion {
		
	public static void main(String[] args) {
		
		// Ejemplo usando un listado para indicar el número de veces (ocurrencias) que se
		// quiere que aparezca un tipo de Tile dado
		ListadoNumeroOcurrenciasDeTiposTile listado = new ListadoNumeroOcurrenciasDeTiposTile();		
		//listado.añadir(Tile.TILE_3x3, 5);
		//listado.añadir(Tile.TILE_3x1, 3);
		//listado.añadir(Tile.TILE_3x2, 3);
		//listado.añadir(Tile.TILE_4x3, 2);
		//listado.añadir(Tile.TILE_2x1, 3);
		//listado.añadir(Tile.TILE_1x2, 3);
		//listado.añadir(Tile.TILE_2x2, 4);
		
		// Define la secuencia de Efectos a aplicar a cada Tile 
		SecuenciaDeEfectos secuencia = new SecuenciaDeEfectos();
		//secuencia.insertar(new EfectoAutoCorreccionContraste(ModulacionEfecto.suave, TiposCorreccion.DosPuntos));
		//secuencia.insertar(new EfectoTunelLente(ModulacionEfecto.fuerte));
		//secuencia.insertar(new EfectoSepia(ModulacionEfecto.fuerte));		
		//secuencia.insertar(new EfectoAleatorio(2));
		//secuencia.insertar(new EfectoDegradado());
		//secuencia.insertar(new EfectoHighlightColor(ModulacionEfecto.suave,Color.yellow));
		//secuencia.insertar(new EfectoBlancoYNegro());
		//secuencia.insertar(new EfectoAberracionCromaticaLente());
		//secuencia.insertar(new EfectoDesenfoqueCircular());
		//secuencia.insertar(new EfectoDesenfoqueLineaRecta());
		//secuencia.insertar(new EfectoAclararImagen(ModulacionEfecto.fuerte));
		//secuencia.insertar(new EfectoDegradado());
		secuencia.insertar(new EfectoColorear());
		//secuencia.insertar(new EfectoVideoBajaCalidadDigitalizado(ModulacionEfecto.fuerte));
		secuencia.insertar(new EfectoBrochazosColor(ModulacionEfecto.suave));
		
		/*
		// Llama a la clase CuadriculaDeEspacios en portátil de casa
				try {
					new CuadriculaDeEspacios(2, 2, 
							"J:\\Luis\\Pictures\\2012_09_08, Boda de María y Javi",
							listado, 300, 1.0, 5, Color.white, 
							"C:\\Users\\Luis\\Desktop\\NuevoCollageImagenCircular.png",
							CuadriculaDeEspacios.TILES_DESCENTRADOS, secuencia);
				} catch (NoImagesException e) {
					e.printStackTrace();
				}
		*/
			
		/*
		// Llama a la clase CuadriculaDeEspacios en casa Win8
		try {
			new CuadriculaDeEspacios(4, 4, 
					"C:\\Users\\Koldo\\Desktop\\Nueva carpeta",
					listado, 200, 1.0, 5, Color.white, 
					"C:\\Users\\Koldo\\Desktop\\NuevoCollage.png",
					CuadriculaDeEspacios.TILES_DESCENTRADOS, secuencia);
		} catch (NoImagesException e) {
			e.printStackTrace();
		}
		*/
		
		/*
		// Llama a la clase CuadriculaDeEspacios en casa Win8
		try {
			new CuadriculaDeEspacios(15, 15, 
					"J:\\Luis\\Imágenes\\Fotos\\Nuevas\\2012_08_18, Extremadura y Huelva",
					listado, 300, 1.0, 3, Color.white, 
					"C:\\Users\\Koldo\\Desktop\\NuevoCollageEH.png",
					CuadriculaDeEspacios.TILES_DESCENTRADOS, secuencia);
		} catch (NoImagesException e) {
			e.printStackTrace();
		}
		*/
	
		
		// Llama a la clase CuadriculaDeEspacios en el curro
		try {
			new CuadriculaDeEspacios(6, 6, 
					"D:\\ImágenesPersonales\\2012_11_03, Aniversario en Punta Cana",
					listado, 400, 1.0, 0, Color.white, 
					"C:\\Documents and Settings\\lcastellano\\Escritorio\\Citadel\\NuevoCollage.png",
					CuadriculaDeEspacios.TILES_DESCENTRADOS, secuencia);
		} catch (NoImagesException e) {
			e.printStackTrace();
		}
		
	}

}
