package apliGraf;

import apliGraf.VisorTextoConDesplazamiento;

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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import modif.ExtraerFicherosSubdirectoriosBackground;
import modif.ModificadorAgnadiendoDirectorioBackground;
import modif.ModificadorNombreArchivosAudioBackground;
import modif.ModificadorNombreArchivosFotos;
import modif.ModificadorNombreArchivosFotosBackground;
import modif.PasoGlobalNombresModernosBackground;
import modif.ReorganizadorArchivosMusicaPorDirectoriosBackground;



public class Aplicacion extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8872504498857720247L;

	private JLabel etiqDirectorio;
	private JTextField textoDirectorio;
	private JButton botonLlamarFileChooserDirectorio;
	
	private JButton botonPasoGlobalNombresModernos;
	private JButton botonAgnadirNombreDirectorio;
	private JButton botonExtraerArchivosSubdirectorios;
	private JButton botonReorganizarArchivosMP3;
	private JButton botonModificarNombreFicherosMP3;
	private JButton botonAgnadirMarcaDeTiempo;
	private JButton botonQuitarMarcaDeTiempo;
	private JButton botonModificarMarcaDeTiempo;
	
	private JTextField textoSegundosModificarMarcaDeTiempo;
	
	private VisorTextoConDesplazamiento areaTexto;	
	
	private Aplicacion app;
	
	public Aplicacion() {
		super();
		
		this.app = this;
		
		this.inciarGUI();
		
		this.setTitle("Modificador de nombres de fotos");
		this.setSize(680, 500);
		this.setVisible(true);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	private void inciarGUI() {
		// Crear componentes
		this.etiqDirectorio = new JLabel("Directorio de trabajo:");
		
		this.textoDirectorio = new JTextField("J:\\Documents and Settings\\Luis\\Escritorio");
		
		this.botonLlamarFileChooserDirectorio = new JButton("Buscar...");
		this.botonLlamarFileChooserDirectorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int opcion = jfc.showOpenDialog(app);
				if (opcion == JFileChooser.APPROVE_OPTION){
					File fichero = jfc.getSelectedFile();
					String nombreFichero = fichero.getAbsolutePath(); 
					textoDirectorio.setText(nombreFichero);	
				}
			}			
		});
		
		this.botonPasoGlobalNombresModernos = new JButton("PasoNombresModernos");
		this.botonPasoGlobalNombresModernos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deshabilitarBotones();
				PasoGlobalNombresModernosBackground r = new PasoGlobalNombresModernosBackground(textoDirectorio.getText(), app);
				r.execute();
			}
		});
		
		this.botonAgnadirNombreDirectorio = new JButton("A\u00F1adirNombreDeDirectorio");
		this.botonAgnadirNombreDirectorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deshabilitarBotones();
				ModificadorAgnadiendoDirectorioBackground r = new ModificadorAgnadiendoDirectorioBackground(textoDirectorio.getText(), app);
				r.execute();
			}
		});
		
		this.botonExtraerArchivosSubdirectorios = new JButton("ExtraerArchivosDeSubdirectorios");
		this.botonExtraerArchivosSubdirectorios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deshabilitarBotones();
				ExtraerFicherosSubdirectoriosBackground r = new ExtraerFicherosSubdirectoriosBackground(textoDirectorio.getText(), app);
				r.execute();
			}
		});
		
		this.botonReorganizarArchivosMP3 = new JButton("ReorganizarArchivosMP3");
		this.botonReorganizarArchivosMP3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deshabilitarBotones();
				ReorganizadorArchivosMusicaPorDirectoriosBackground r = new ReorganizadorArchivosMusicaPorDirectoriosBackground(textoDirectorio.getText(), app);
				r.execute();
			}
		});
		
		this.botonModificarNombreFicherosMP3 = new JButton("ModificarNombreArchivosMP3");
		this.botonModificarNombreFicherosMP3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deshabilitarBotones();
				ModificadorNombreArchivosAudioBackground r = new ModificadorNombreArchivosAudioBackground(textoDirectorio.getText(), app);
				r.execute();
			}
		});
		
		this.botonAgnadirMarcaDeTiempo = new JButton("A\u00F1adirMarcaDeTiempoAFoto");
		this.botonAgnadirMarcaDeTiempo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deshabilitarBotones();
				ModificadorNombreArchivosFotosBackground r = new ModificadorNombreArchivosFotosBackground(
						textoDirectorio.getText(),
						ModificadorNombreArchivosFotos.AGNADIR, app);
				r.execute();
			}
		});

		this.botonQuitarMarcaDeTiempo = new JButton("QuitarMarcaDeTiempoAFoto");
		this.botonQuitarMarcaDeTiempo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deshabilitarBotones();
				ModificadorNombreArchivosFotosBackground r = new ModificadorNombreArchivosFotosBackground(
						textoDirectorio.getText(),
						ModificadorNombreArchivosFotos.BORRAR, app);
				r.execute();
			}
		});

		this.botonModificarMarcaDeTiempo = new JButton(
				"ModificarMarcaDeTiempoAFoto");
		this.botonModificarMarcaDeTiempo
				.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						deshabilitarBotones();
						ModificadorNombreArchivosFotosBackground r = new ModificadorNombreArchivosFotosBackground(
								textoDirectorio.getText(),
								ModificadorNombreArchivosFotos.MODIFICAR, app);
						r.execute();
					}
				});
		
		this.textoSegundosModificarMarcaDeTiempo = new JTextField("0");
		this.textoSegundosModificarMarcaDeTiempo.setMaximumSize(new Dimension(80,40));
		
		this.areaTexto = new VisorTextoConDesplazamiento(500);
		
		// Agnadir al panel
		JPanel panel = new JPanel();
		BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(layout);
		
		JPanel panelDirectorio = new JPanel();
		BorderLayout layoutPanelDirectorio = new BorderLayout();
		panelDirectorio.setLayout(layoutPanelDirectorio);
		panelDirectorio.add(this.etiqDirectorio, BorderLayout.NORTH);
		JPanel panelDirectorioSecundario = new JPanel();
		BoxLayout posld = new BoxLayout(panelDirectorioSecundario, BoxLayout.X_AXIS);
		panelDirectorioSecundario.setLayout(posld);
		panelDirectorioSecundario.add(this.textoDirectorio);
		panelDirectorioSecundario.add(this.botonLlamarFileChooserDirectorio);		
		panelDirectorio.add(panelDirectorioSecundario, BorderLayout.CENTER);
		panelDirectorio.setMaximumSize(new Dimension(1500, 42));
		panel.add(panelDirectorio);
		
		JPanel panelBotones = new JPanel();
		BoxLayout pbpbl = new BoxLayout(panelBotones, BoxLayout.X_AXIS);
		panelBotones.setLayout(pbpbl);
		panelBotones.add(this.botonPasoGlobalNombresModernos);
		panelBotones.add(this.botonAgnadirNombreDirectorio);
		panelBotones.add(this.botonExtraerArchivosSubdirectorios);
		panelBotones.setBorder(BorderFactory.createEmptyBorder(25, 0, 10, 5));
		panel.add(panelBotones);
		
		JPanel panelBotones2 = new JPanel();
		BoxLayout pbpbl2 = new BoxLayout(panelBotones2, BoxLayout.X_AXIS);
		panelBotones2.setLayout(pbpbl2);
		panelBotones2.add(this.botonReorganizarArchivosMP3);
		panelBotones2.add(this.botonModificarNombreFicherosMP3);
		panelBotones2.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 5));
		panel.add(panelBotones2);
		
		JPanel panelBotones3 = new JPanel();
		BoxLayout pbpbl3 = new BoxLayout(panelBotones3, BoxLayout.X_AXIS);
		panelBotones3.setLayout(pbpbl3);
		panelBotones3.add(this.botonAgnadirMarcaDeTiempo);
		panelBotones3.add(this.botonQuitarMarcaDeTiempo);
		panelBotones3.add(this.botonModificarMarcaDeTiempo);
		panelBotones3.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 5));
		panel.add(panelBotones3);
		
		JPanel panelBotones4 = new JPanel();
		BoxLayout pbpbl4 = new BoxLayout(panelBotones4, BoxLayout.X_AXIS);
		panelBotones4.setLayout(pbpbl4);
		panelBotones4.add(new JLabel("N\u00FAmero de segundos a a\u00F1adir a las marcas de tiempo (negativo para quitar): "));
		panelBotones4.add(this.textoSegundosModificarMarcaDeTiempo);
		panelBotones4.setBorder(BorderFactory.createEmptyBorder(10, 5, 25, 5));
		panel.add(panelBotones4);
		
		panel.add(this.areaTexto);
		
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.getContentPane().add(panel);
	}
	
	private void deshabilitarBotones() {
		this.botonPasoGlobalNombresModernos.setEnabled(false);
		this.botonAgnadirNombreDirectorio.setEnabled(false);
		this.botonExtraerArchivosSubdirectorios.setEnabled(false);
		this.botonReorganizarArchivosMP3.setEnabled(false);
		this.botonModificarNombreFicherosMP3.setEnabled(false);
		this.botonAgnadirMarcaDeTiempo.setEnabled(false);
		this.botonQuitarMarcaDeTiempo.setEnabled(false);
		this.botonModificarMarcaDeTiempo.setEnabled(false);
		
		this.botonLlamarFileChooserDirectorio.setEnabled(false);
	}
	
	public void habilitarBotones() {
		this.botonPasoGlobalNombresModernos.setEnabled(true);
		this.botonAgnadirNombreDirectorio.setEnabled(true);
		this.botonExtraerArchivosSubdirectorios.setEnabled(true);
		this.botonReorganizarArchivosMP3.setEnabled(true);
		this.botonModificarNombreFicherosMP3.setEnabled(true);
		this.botonAgnadirMarcaDeTiempo.setEnabled(true);
		this.botonQuitarMarcaDeTiempo.setEnabled(true);
		this.botonModificarMarcaDeTiempo.setEnabled(true);
		
		this.botonLlamarFileChooserDirectorio.setEnabled(true);
	}
	
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				new Aplicacion();				
			}						
		});
	}

	public void escribirLinea(String linea) {
		this.areaTexto.escribirLinea(linea);
	}
	
	public int getSegundosAdelanto() {
		int ret;
		try{
			ret = Integer.parseInt(this.textoSegundosModificarMarcaDeTiempo.getText());
		} catch (NumberFormatException e) {
			this.textoSegundosModificarMarcaDeTiempo.setText("0");
			ret = 0;
		}
		return ret;
	}
}
