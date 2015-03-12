package repli;

import gui.Aplicacion;

import javax.swing.SwingWorker;

@SuppressWarnings("unchecked")
public class ReplicadorBackground extends SwingWorker {
	
	private String fileOrigen;
	private String fileDestino;
	private Aplicacion app; 
	
	public ReplicadorBackground(String o, String d, Aplicacion app) {
		this.fileOrigen = o;
		this.fileDestino = d;
		this.app = app;
	}

	@Override
	protected Object doInBackground() throws Exception {
		
		new Replicador(this.fileOrigen, this.fileDestino, this.app);
		
		return null;
	}
	
	@Override
	protected void done() {
		super.done();
		this.app.escribirLinea("--------------------------\nProceso terminado con éxito\n\n");
		this.app.activarBotonReplicar();
	}

}
