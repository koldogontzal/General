package modif;

import apliGraf.Aplicacion;

import javax.swing.SwingWorker;

public class ModificadorNombreArchivosFotosBackground extends SwingWorker {
	
	private String directorio;
	private int accion;
	private Aplicacion app; 
	
	public ModificadorNombreArchivosFotosBackground(String directorio, int accion, Aplicacion app) {
		this.directorio = directorio;
		this.accion = accion;
		this.app = app;
	}

	@Override
	protected Object doInBackground() throws Exception {		
		new ModificadorNombreArchivosFotos(this.directorio, this.accion, this.app);
		
		return null;
	}
	
	@Override
	protected void done() {
		super.done();
		this.app.escribirLinea("--------------------------\nProceso terminado con \u00E9xito\n\n");
		this.app.habilitarBotones();
	}

}
