package modif;

import apliGraf.Aplicacion;

import javax.swing.SwingWorker;

public class PasoGlobalNombresModernosBackground extends SwingWorker {
	
	private String directorio;
	private Aplicacion app; 
	
	public PasoGlobalNombresModernosBackground(String directorio, Aplicacion app) {
		this.directorio = directorio;
		this.app = app;
	}

	@Override
	protected Object doInBackground() throws Exception {
		
		new PasoGlobalNombresModernos(this.directorio, this.app);
		
		return null;
	}
	
	@Override
	protected void done() {
		super.done();
		this.app.escribirLinea("--------------------------\nProceso terminado con exito\n\n");
		this.app.habilitarBotones();
	}

}
