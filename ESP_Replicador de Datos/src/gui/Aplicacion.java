package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
 
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import repli.ReplicadorBackground;


public class Aplicacion extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JLabel etiqOrigen;
	private JLabel etiqDestino;
	private JTextField fileOrigen;
	private JTextField fileDestino;
	private JButton botonLlamarFileChooserOrigen;
	private JButton botonLlamarFileChooserDestino;
	private JButton botonPrefijado1;
	private JButton botonPrefijado2;
	private JButton botonPrefijado3;
	private JButton botonPrefijado4;
	private JButton botonPrefijado5;
	private JButton botonPrefijado6;
	private JCheckBox checkRevisionCompleta;
	private JCheckBox checkOmitirOcultos;
	private JCheckBox checkPedirConfirmacion;
	
	private JButton botonIniciarComprobacion;
	private VisorTextoConDesplazamiento areaTexto;
	
	private Aplicacion app;
	
	private static final int BOTON_ORIGEN = 1;
	private static final int BOTON_DESTINO = 2;
	
	public Aplicacion() {
		super();
		this.app = this;
		
		this.initGUI();
		
		this.setTitle("Replicador");
		this.setSize(650, 500);
		this.setVisible(true);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	private void initGUI() {
		
		// Crear componentes
		this.etiqOrigen = new JLabel("Directorio de origen:");
		this.etiqDestino = new JLabel("Directorio de destino:");
		
		this.fileOrigen = new JTextField("");
		this.fileDestino = new JTextField("");
		
		this.botonLlamarFileChooserOrigen = new JButton("Buscar...");
		this.botonLlamarFileChooserOrigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarSelector(BOTON_ORIGEN);				
			}			
		});
		this.botonLlamarFileChooserDestino = new JButton("Buscar...");
		this.botonLlamarFileChooserDestino.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarSelector(BOTON_DESTINO);				
			}			
		});
		
		this.botonPrefijado1 = new JButton("ImgsLuis");
		this.botonPrefijado1.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				File fOrigen = new File("D:/Fotos");
				File fDestino = new File("M:/FotosLuis/Fotos");
				fileOrigen.setText(fOrigen.getAbsolutePath());
				fileDestino.setText(fDestino.getAbsolutePath());				
			}
			
		});
		
		this.botonPrefijado2 = new JButton("M\u00FAsicaLuis");
		this.botonPrefijado2.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				File fOrigen = new File("D:/Musica");
				File fDestino = new File("N:/MusicaLuis");
				fileOrigen.setText(fOrigen.getAbsolutePath());
				fileDestino.setText(fDestino.getAbsolutePath());				
			}
			
		});
		
		this.botonPrefijado3 = new JButton("DatosLuis");
		this.botonPrefijado3.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				File fOrigen = new File("D:/Documentos");
				File fDestino = new File("O:/Datos Luis");
				fileOrigen.setText(fOrigen.getAbsolutePath());
				fileDestino.setText(fDestino.getAbsolutePath());				
			}
			
		});
		
		this.botonPrefijado4 = new JButton("Programas");
		this.botonPrefijado4.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				File fOrigen = new File("D:/Downloads & Programas");
				File fDestino = new File("O:/Downloads & Programas");
				fileOrigen.setText(fOrigen.getAbsolutePath());
				fileDestino.setText(fDestino.getAbsolutePath());				
			}
			
		});
		
		this.botonPrefijado5 = new JButton("ImgsJose");
		this.botonPrefijado5.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				File fOrigen = new File("E:/Fotos");
				File fDestino = new File("M:/FotosJose");
				fileOrigen.setText(fOrigen.getAbsolutePath());
				fileDestino.setText(fDestino.getAbsolutePath());				
			}
			
		});
		
		this.botonPrefijado6 = new JButton("M\u00FAsicaJose");
		this.botonPrefijado6.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				File fOrigen = new File("E:/M\u00FAsica");
				File fDestino = new File("N:/MusicaJose");
				fileOrigen.setText(fOrigen.getAbsolutePath());
				fileDestino.setText(fDestino.getAbsolutePath());				
			}
			
		});
		
		
		this.checkOmitirOcultos = new JCheckBox("Omitir archivos ocultos", true);
		
		this.checkRevisionCompleta = new JCheckBox("Revisi\u00F3n exhaustiva (m\u00E1s lento)", false);
		
		this.checkPedirConfirmacion = new JCheckBox("Pedir confirmaci\u00F3n al sobreescribir", true);
		
		
		this.botonIniciarComprobacion = new JButton("Replicar");
		this.botonIniciarComprobacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((JButton)e.getSource()).setEnabled(false);
				ReplicadorBackground r = new ReplicadorBackground(fileOrigen.getText(), fileDestino.getText(), app);
				r.execute();
			}
		});
		
		this.areaTexto = new VisorTextoConDesplazamiento(500);
		
		// A�adir al panel
		JPanel panel = new JPanel();
		BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(layout);
		
		JPanel panelOrigen = new JPanel();
		BorderLayout layoutPanelOrigen = new BorderLayout();
		panelOrigen.setLayout(layoutPanelOrigen);
		panelOrigen.add(this.etiqOrigen, BorderLayout.NORTH);
		JPanel panelOrigenSecundario = new JPanel();
		BoxLayout poslo = new BoxLayout(panelOrigenSecundario, BoxLayout.X_AXIS);
		panelOrigenSecundario.setLayout(poslo);
		panelOrigenSecundario.add(this.fileOrigen);
		panelOrigenSecundario.add(this.botonLlamarFileChooserOrigen);		
		panelOrigen.add(panelOrigenSecundario, BorderLayout.CENTER);
		panelOrigen.add(new JLabel("\n\n\n"), BorderLayout.SOUTH);
		panelOrigen.setMaximumSize(new Dimension(1500, 80));
		
		JPanel panelDestino = new JPanel();
		BorderLayout layoutPanelDestino = new BorderLayout();
		panelDestino.setLayout(layoutPanelDestino);
		panelDestino.add(this.etiqDestino, BorderLayout.NORTH);
		JPanel panelDestinoSecundario = new JPanel();
		BoxLayout pdslo = new BoxLayout(panelDestinoSecundario, BoxLayout.X_AXIS);
		panelDestinoSecundario.setLayout(pdslo);
		panelDestinoSecundario.add(this.fileDestino);
		panelDestinoSecundario.add(this.botonLlamarFileChooserDestino);		
		panelDestino.add(panelDestinoSecundario, BorderLayout.CENTER);
		panelDestino.add(new JLabel("\n\n\n"), BorderLayout.SOUTH);
		panelDestino.setMaximumSize(new Dimension(1500, 80));
		
		panel.add(panelOrigen);
		panel.add(panelDestino);
		
		JPanel panelTextoBotonesPref = new JPanel();
		BoxLayout ptbpl = new BoxLayout(panelTextoBotonesPref, BoxLayout.X_AXIS);
		panelTextoBotonesPref.setLayout(ptbpl);		
		panelTextoBotonesPref.add(new JLabel("Ejemplos prefijados:"));
		panel.add(panelTextoBotonesPref);
		JPanel panelBotonesPrefijados = new JPanel();
		BoxLayout pbpbl = new BoxLayout(panelBotonesPrefijados, BoxLayout.X_AXIS);
		panelBotonesPrefijados.setLayout(pbpbl);
		panelBotonesPrefijados.add(this.botonPrefijado1);
		panelBotonesPrefijados.add(this.botonPrefijado2);
		panelBotonesPrefijados.add(this.botonPrefijado3);
		panelBotonesPrefijados.add(this.botonPrefijado4);
		panelBotonesPrefijados.add(this.botonPrefijado5);
		panelBotonesPrefijados.add(this.botonPrefijado6);
		panel.add(panelBotonesPrefijados);
		
		
		panel.add(new JLabel(" "));
		panel.add(new JLabel(" "));
		panel.add(this.checkRevisionCompleta);
		panel.add(this.checkPedirConfirmacion);
		panel.add(this.checkOmitirOcultos);
		
		panel.add(new JLabel(" "));
		panel.add(new JLabel(" "));
		JPanel panelBotonIniciar = new JPanel();
		BoxLayout pbil = new BoxLayout(panelBotonIniciar, BoxLayout.X_AXIS);
		panelBotonIniciar.setLayout(pbil);
		panelBotonIniciar.add(this.botonIniciarComprobacion);
		panel.add(panelBotonIniciar);		
		panel.add(new JLabel(" "));
		panel.add(new JLabel(" "));
		
		panel.add(this.areaTexto);
		
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		this.getContentPane().add(panel);

	}
	
	public void mostrarSelector(int boton){
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int opcion = jfc.showOpenDialog(this);
		if (opcion == JFileChooser.APPROVE_OPTION){
			File fichero = jfc.getSelectedFile();
			String nombreFichero = fichero.getAbsolutePath(); 
			switch (boton) {
			case BOTON_ORIGEN:
				this.fileOrigen.setText(nombreFichero);
				break;
			case BOTON_DESTINO:
				this.fileDestino.setText(nombreFichero);
				break;
			}
		}
	}
	
	public void escribirLinea(String linea) {
		this.areaTexto.escribirLinea(linea);
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				new Aplicacion();				
			}						
		});
	}

	public void activarBotonReplicar() {
		this.botonIniciarComprobacion.setEnabled(true);
	}

	public boolean esRevisionExhaustiva() {
		return this.checkRevisionCompleta.isSelected();
	}
	
	public boolean esPedirConfirmacion() {
		return this.checkPedirConfirmacion.isSelected();
	}
	
	public boolean esOmitirOcultos() {
		return this.checkOmitirOcultos.isSelected();
	}
	
}
