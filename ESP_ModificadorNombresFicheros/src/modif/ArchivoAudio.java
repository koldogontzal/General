package modif;

import java.io.IOException;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.tritonus.share.sampled.file.TAudioFileFormat;

public class ArchivoAudio extends Archivo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3802182960013266079L;

	public ArchivoAudio(String nom) {
		super(nom);
	}
	
	public ArchivoAudio(Archivo arch) {
		super(arch);
	}
	
	public String getArtista() {
		String artista = null;		
		
		AudioFileFormat baseFileFormat = null;
		try {
			baseFileFormat = AudioSystem.getAudioFileFormat(super.getAbsoluteFile());
			if (baseFileFormat instanceof TAudioFileFormat) {
				Map<?, ?> tags = ((TAudioFileFormat) baseFileFormat).properties();
				// Artista
				artista = (String) tags.get("mp3.id3tag.orchestra");
				if (artista == null) {
					artista = (String) tags.get("mp3.id3tag.grouping");
				}
				if (artista == null) {
					artista = (String) tags.get("author");
				}
				if (artista == null) {
					artista = (String) tags.get("mp3.id3tag.composer");
				}
				if (artista == null) {
					artista = "";
				}
			}
		} catch (UnsupportedAudioFileException e) {
			System.out.println("Error revisando archivo: " + super.getPath());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error revisando archivo: " + super.getPath());
			e.printStackTrace();
		}			

		return this.eliminarEspaciosBlancos(artista);
	}
	
	public String getAlbum() {
		String album = null;
		AudioFileFormat baseFileFormat = null;

		try {
			baseFileFormat = AudioSystem.getAudioFileFormat(super.getAbsoluteFile());
			if (baseFileFormat instanceof TAudioFileFormat) {
				@SuppressWarnings("rawtypes")
				Map tags = ((TAudioFileFormat) baseFileFormat).properties();
				// Album
				album = (String) tags.get("album");
				if (album == null) {
					album = "";
				}
			}
		} catch (UnsupportedAudioFileException e) {
			System.out.println("Error revisando archivo: " + super.getPath());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error revisando archivo: " + super.getPath());
			e.printStackTrace();
		}
		return this.eliminarEspaciosBlancos(album);
	}
	
	public int getNumeroDisco() {
		int numero = 0;

		AudioFileFormat baseFileFormat = null;
		try {
			baseFileFormat = AudioSystem.getAudioFileFormat(super.getAbsoluteFile());
			if (baseFileFormat instanceof TAudioFileFormat) {
				@SuppressWarnings("rawtypes")
				Map tags = ((TAudioFileFormat) baseFileFormat).properties();
				// Disco
				String numeroDisco = (String) tags.get("mp3.id3tag.disc");
				if (numeroDisco == null) {
					numero = 0;
				} else {
					String[] listadoDatos;
					listadoDatos = numeroDisco.split("/");
					numero = Integer.parseInt(listadoDatos[0]);
				}
			}
		} catch (UnsupportedAudioFileException e) {
			System.out.println("Error revisando archivo: " + super.getPath());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error revisando archivo: " + super.getPath());
			e.printStackTrace();
		}

		return numero;
	}
	
	public int getNumeroPista() {
		int numero = 0;

		AudioFileFormat baseFileFormat = null;
		try {
			baseFileFormat = AudioSystem.getAudioFileFormat(super.getAbsoluteFile());
			if (baseFileFormat instanceof TAudioFileFormat) {
				@SuppressWarnings("rawtypes")
				Map tags = ((TAudioFileFormat) baseFileFormat).properties();
				// Pista
				String numeroPista = (String) tags.get("mp3.id3tag.track");
				if (numeroPista == null) {
					numero = 0;
				} else {
					String[] listadoDatos;
					listadoDatos = numeroPista.split("/");
					numero = Integer.parseInt(listadoDatos[0]);
				}
			}
		} catch (UnsupportedAudioFileException e) {
			System.out.println("Error revisando archivo: " + super.getPath());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error revisando archivo: " + super.getPath());
			e.printStackTrace();
		}
		// Arregla un bug de la extraccion de pista
		if (numero == 32) {
			numero = 0;
		}
		return numero;
	}
	
	public String getTitulo() {
		String titulo = null;

		AudioFileFormat baseFileFormat = null;
		try {
			baseFileFormat = AudioSystem.getAudioFileFormat(super.getAbsoluteFile());
			if (baseFileFormat instanceof TAudioFileFormat) {
				@SuppressWarnings("rawtypes")
				Map tags = ((TAudioFileFormat) baseFileFormat).properties();
				// Titulo
				titulo = (String) tags.get("title");
				if (titulo == null) {
					titulo = "";
				}
			}
		} catch (UnsupportedAudioFileException e) {
			System.out.println("Error revisando archivo: " + super.getPath());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error revisando archivo: " + super.getPath());
			e.printStackTrace();
		}

		return this.eliminarEspaciosBlancos(titulo);	
	}
	
	private String eliminarEspaciosBlancos(String s) {
		if (s != null) {
			// La cadena no es null
			int posFinal = s.length();
			if (posFinal < 1) {
				// La cadena est� vac�a
				return s;
			} else {
				// La cadena tiene caracteres
				while ((posFinal > 0) && (s.charAt(posFinal - 1) < 33)) {
					posFinal--;
				}
				return s.substring(0, posFinal);
			}
		} else {
			// La cadena es null;
			return null;
		}
	}
	
}
