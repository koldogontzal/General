package main;

import subtitulos.ListadoSubtitulos;
import tiempo.Instante;

@SuppressWarnings("unused")
public class Aplicacion {
	
	public static void main(String[] args) {
		/*
		ListadoSubtitulos l = new ListadoSubtitulos(
				"G:\\Series\\Misfits\\Session3\\MISFITS - 3X06 .Hannibal.SP.srt", 
				ListadoSubtitulos.CODIFICACION_ASCII);
		
		l.adelantarTiempos(-17, Instante.SEGUNDOS);
		
		//l.multiplicarFactorTiempos(0.9992145);
		
		l.grabarArchivoSubtitulos("G:\\Series\\Misfits\\Session3\\MISFITS - 3X06 .Hannibal.NEW.srt");
		*/
		ListadoSubtitulos l = new ListadoSubtitulos(
				"C:\\Users\\lcastellano\\Documents\\Personal\\SubtitulosCorregidos\\Gabriel.2007.STV.DVDRip.XviD-TheWretched.ESP.wWw.Asia-Team.Tv.srt",
				ListadoSubtitulos.CODIFICACION_ASCII);
		l.multiplicarFactorTiempos((double)6339/6609);
		l.grabarArchivoSubtitulos("C:\\Users\\lcastellano\\Documents\\Personal\\SubtitulosCorregidos\\Gabriel 2007 v2.srt");
	}


}
