package modif;

import java.io.File;

public class Crear2DirectoriosNombreGrupoYDisco {

	private File directorioBase;
	private File[] listado;
	
	
	public Crear2DirectoriosNombreGrupoYDisco(String dir) {
		this.directorioBase = new File(dir);
		this.listado = this.directorioBase.listFiles();
	}

	
	public static void main(String[] args) {
		Crear2DirectoriosNombreGrupoYDisco app = new Crear2DirectoriosNombreGrupoYDisco("H:/Incoming/ordenadas");
		app.start();
	}

	private void start() {
		for (File f : this.listado) {
			String nombCompleto = f.getName();
			if (this.tiene2Partes(nombCompleto) && f.isDirectory()) {
				String grupo = this.getParte(nombCompleto, 1);
				String disco = this.getParte(nombCompleto, 2);
				String dirFinal = this.directorioBase.getPath() + File.separator 
					+ grupo + File.separator + disco;
				
				File intermedio1 = new File (this.directorioBase.getPath() + File.separator + grupo);
				if (!intermedio1.exists()) {
					intermedio1.mkdir();
				}

				File fin = new File(dirFinal);
				f.renameTo(fin);
				
			}
		}
	}


	private boolean tiene2Partes(String s) {
		return (s.indexOf("-") != -1);
	}

	private String getParte(String s, int i) {
		String devolver;
		int pos = s.indexOf("-");
		if (i == 1) {
			devolver = s.substring(0, pos);
		} else {
			devolver = s.substring(pos + 1, s.length());
		}
		
		return this.eliminarEspacios(devolver);
	}


	private String eliminarEspacios(String s) {
		String devolver = s;
		if (devolver.charAt(0) == ' ') {
			devolver = this.eliminarEspacios(devolver.substring(1, devolver.length()));
		}
		if (devolver.charAt(devolver.length() - 1) == ' ') {
			devolver = this.eliminarEspacios(devolver.substring(0, devolver.length() - 1));
		}
		
		return devolver;
	}

}
