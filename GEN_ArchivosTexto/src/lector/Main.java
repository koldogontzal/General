package lector;

import java.io.EOFException;

import javax.imageio.ImageIO;

import escritor.EscritorArchivosTexto;

@SuppressWarnings("unused")
public class Main {
	public static void main(String[] args) {
		
		//String txt = "sdlfkj sdf  sdfsdf\nsdffdsgdfgdflgjdfg dgd\ndfgdfgdfg\t �df�gldfgk�sd glhdfg ��";
		//EscritorArchivosTexto.createFile("Libro3.txt", txt);
		
		String texto = LectorArchivosTexto.readFile("Libro1.txt",
				LectorArchivosTexto.MODO_UTF8);
		System.out.print(texto);
		
		String[] tipos = ImageIO.getWriterFormatNames();
		for (String tipo :  tipos) {
			System.out.println(tipo);
		}
	}
	

}
