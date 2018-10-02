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
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.koldogontzal.timestamp.TimeStampFormat;

import modif.ExtraerFicherosSubdirectoriosBackground;
import modif.ModificadorAgnadiendoDirectorioBackground;
import modif.ModificadorNombreArchivosAudioBackground;
import modif.ModificadorNombreArchivosTimeStamp;
import modif.ModificadorNombreArchivosTimeStampBackground;
import modif.PasoGlobalNombresModernosBackground;
import modif.ReorganizadorArchivosMusicaPorDirectoriosBackground;



public class Aplicacion extends JFrame {

	/**
	 *
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
	private JButton botonAgnadirTimeStampArchivos;
	private JButton botonQuitarTimeStampArchivos;
	private JButton botonDesplazarTimeStampAlInicio;
	private JButton botonModificarTimeStampArchivos;
	private JButton botonModificarFormatoTimeStamp;
	
	private JTextField textoSegundosModificarMarcaDeTiempo;
	
	private JComboBox<TimeStampFormat> comboElegirFormatoTimeStamp;
	
	private VisorTextoConDesplazamiento areaTexto;	
	
	private Aplicacion app;
	
	public Aplicacion() {
		super();
		
		this.app = this;
		
		this.inciarGUI();
		
		this.setTitle("Modificador de nombres de fotos");
		this.setSize(680, 600);
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
		
		this.textoDirectorio = new JTextField("C:\\Users\\Koldo\\Desktop");
		
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
		
		this.botonPasoGlobalNombresModernos = new JButton("Paso a NombresModernos");
		this.botonPasoGlobalNombresModernos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deshabilitarBotones();
				PasoGlobalNombresModernosBackground r = new PasoGlobalNombresModernosBackground(textoDirectorio.getText(), app);
				r.execute();
			}
		});

		
		this.botonAgnadirNombreDirectorio = new JButton("A\u00F1adir Nombre de Directorio");
		this.botonAgnadirNombreDirectorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deshabilitarBotones();
				ModificadorAgnadiendoDirectorioBackground r = new ModificadorAgnadiendoDirectorioBackground(textoDirectorio.getText(), app);
				r.execute();
			}
		});
		
		this.botonExtraerArchivosSubdirectorios = new JButton("Extraer Archivos de Subdirectorios");
		this.botonExtraerArchivosSubdirectorios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deshabilitarBotones();
				ExtraerFicherosSubdirectoriosBackground r = new ExtraerFicherosSubdirectoriosBackground(textoDirectorio.getText(), app);
				r.execute();
			}
		});
		
		this.botonReorganizarArchivosMP3 = new JButton("Reorganizar ArchivosMP3");
		this.botonReorganizarArchivosMP3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deshabilitarBotones();
				ReorganizadorArchivosMusicaPorDirectoriosBackground r = new ReorganizadorArchivosMusicaPorDirectoriosBackground(textoDirectorio.getText(), app);
				r.execute();
			}
		});
		
		this.botonModificarNombreFicherosMP3 = new JButton("Modificar Nombre ArchivosMP3");
		this.botonModificarNombreFicherosMP3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deshabilitarBotones();
				ModificadorNombreArchivosAudioBackground r = new ModificadorNombreArchivosAudioBackground(textoDirectorio.getText(), app);
				r.execute();
			}
		});
	
			
		this.botonAgnadirTimeStampArchivos = new JButton("A\u00F1adir un TimeStamp al nombre");
		this.botonAgnadirTimeStampArchivos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deshabilitarBotones();
				ModificadorNombreArchivosTimeStampBackground r = new ModificadorNombreArchivosTimeStampBackground(
						textoDirectorio.getText(),
						ModificadorNombreArchivosTimeStamp.AGNADIR, app);
				r.execute();
			}
		});

		this.botonQuitarTimeStampArchivos = new JButton("Quitar el TimeStamp");
		this.botonQuitarTimeStampArchivos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deshabilitarBotones();
				ModificadorNombreArchivosTimeStampBackground r = new ModificadorNombreArchivosTimeStampBackground(
						textoDirectorio.getText(),
						ModificadorNombreArchivosTimeStamp.BORRAR, app);
				r.execute();
			}
		});

		
		this.botonDesplazarTimeStampAlInicio = new JButton("Desplazar TimeStamp al inicio");
		this.botonDesplazarTimeStampAlInicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deshabilitarBotones();
				ModificadorNombreArchivosTimeStampBackground r = new ModificadorNombreArchivosTimeStampBackground(
						textoDirectorio.getText(),
						ModificadorNombreArchivosTimeStamp.DESPLAZAR_INICIO, app);
				r.execute();
			}
		});
		
		
		this.botonModificarTimeStampArchivos = new JButton("Modificar valor del TimeStamp");
		this.botonModificarTimeStampArchivos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					deshabilitarBotones();
					ModificadorNombreArchivosTimeStampBackground r = new ModificadorNombreArchivosTimeStampBackground(
								textoDirectorio.getText(),
								ModificadorNombreArchivosTimeStamp.MODIFICAR_VALOR, app);
						r.execute();
					}
				});
				
		
		this.textoSegundosModificarMarcaDeTiempo = new JTextField("0");
		this.textoSegundosModificarMarcaDeTiempo.setHorizontalAlignment(JTextField.RIGHT);
		this.textoSegundosModificarMarcaDeTiempo.setMaximumSize(new Dimension(80,40));
		
		this.comboElegirFormatoTimeStamp = new JComboBox<TimeStampFormat>();
		for (TimeStampFormat formato : TimeStampFormat.values()) {
			this.comboElegirFormatoTimeStamp.addItem(formato);
		}
		this.comboElegirFormatoTimeStamp.setMaximumSize(new Dimension(175,30));
		
		
		this.botonModificarFormatoTimeStamp = new JButton("Cambiar formato de TimeStamp");
		this.botonModificarFormatoTimeStamp.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					deshabilitarBotones();
					ModificadorNombreArchivosTimeStampBackground r = new ModificadorNombreArchivosTimeStampBackground(
								textoDirectorio.getText(),
								ModificadorNombreArchivosTimeStamp.MODIFICAR_FORMATO, app);
						r.execute();
					}
				});
		
		
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
		panelBotones3.add(this.botonAgnadirTimeStampArchivos);
		panelBotones3.add(this.botonQuitarTimeStampArchivos);
		panelBotones3.add(this.botonDesplazarTimeStampAlInicio);
		panelBotones3.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 5));
		panel.add(panelBotones3);
		
		JPanel panelBotones4 = new JPanel();
		BoxLayout pbpbl4 = new BoxLayout(panelBotones4, BoxLayout.X_AXIS);
		panelBotones4.setLayout(pbpbl4);
		panelBotones4.add(new JLabel("Segundos a avanzar (negativo para retroceder): "));
		panelBotones4.add(this.textoSegundosModificarMarcaDeTiempo);
		panelBotones4.add(this.botonModificarTimeStampArchivos);
		panelBotones4.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 5));
		panel.add(panelBotones4);
		
		JPanel panelBotones5 = new JPanel();
		BoxLayout pbpbl5 = new BoxLayout(panelBotones5, BoxLayout.X_AXIS);
		panelBotones5.setLayout(pbpbl5);
		panelBotones5.add(new JLabel("Elije el Formato preferido de TimeStamp: "));
		panelBotones5.add(this.comboElegirFormatoTimeStamp);
		panelBotones5.add(this.botonModificarFormatoTimeStamp);
		panelBotones5.setBorder(BorderFactory.createEmptyBorder(5, 5, 25, 5));
		panel.add(panelBotones5);
		
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
		this.botonAgnadirTimeStampArchivos.setEnabled(false);
		this.botonQuitarTimeStampArchivos.setEnabled(false);
		this.botonDesplazarTimeStampAlInicio.setEnabled(false);
		this.botonModificarTimeStampArchivos.setEnabled(false);
		this.botonModificarFormatoTimeStamp.setEnabled(false);
		
		this.comboElegirFormatoTimeStamp.setEnabled(false);
		
		this.botonLlamarFileChooserDirectorio.setEnabled(false);
	}
	
	public void habilitarBotones() {
		this.botonPasoGlobalNombresModernos.setEnabled(true);
		this.botonAgnadirNombreDirectorio.setEnabled(true);
		this.botonExtraerArchivosSubdirectorios.setEnabled(true);
		this.botonReorganizarArchivosMP3.setEnabled(true);
		this.botonModificarNombreFicherosMP3.setEnabled(true);
		this.botonAgnadirTimeStampArchivos.setEnabled(true);
		this.botonQuitarTimeStampArchivos.setEnabled(true);
		this.botonDesplazarTimeStampAlInicio.setEnabled(true);
		this.botonModificarTimeStampArchivos.setEnabled(true);
		this.botonModificarFormatoTimeStamp.setEnabled(true);
		
		this.comboElegirFormatoTimeStamp.setEnabled(true);
		
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
	
	public TimeStampFormat getTimeStampFormatPreferred() {
		return (TimeStampFormat)this.comboElegirFormatoTimeStamp.getSelectedItem();
	}
}
