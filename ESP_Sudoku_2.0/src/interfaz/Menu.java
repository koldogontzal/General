package interfaz;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

import sudoku.SudokuGrafico;
import tablero.Tablero;

import eventos.GestorMenu;

public class Menu extends JMenuBar {
	private static final long serialVersionUID = 1520859098944849135L;
	
	private SudokuGrafico aplicacion;
		
	private JMenu menuArchivo;
	private JMenu menuEditar;
	private JMenu menuVer;
	private JMenu menuHerramientas;
	private JMenu menuAyuda;
	
	private JMenuItem menuItemArchivoNuevo = new JMenuItem("Nuevo");
	private JMenuItem menuItemArchivoAbrir = new JMenuItem("Abrir...");
	private JMenuItem menuItemArchivoGuardar = new JMenuItem("Guardar");
	private JMenuItem menuItemArchivoGuardarComo = new JMenuItem("Guardar como...");
	private JMenuItem menuItemArchivoSalir = new JMenuItem("Salir");
	private JMenuItem menuItemEditarUndo = new JMenuItem("Undo");
	private JMenuItem menuItemEditarRedo = new JMenuItem("Redo");
	private ButtonGroup grupoVer;
	private JRadioButtonMenuItem menuItemVerSoloResueltos = new JRadioButtonMenuItem("Sólo celdas resueltas");
	private JRadioButtonMenuItem menuItemVerNumeroValoresPosibles = new JRadioButtonMenuItem("Número de valores posibles");
	private JRadioButtonMenuItem menuItemVerPosibles1 = new JRadioButtonMenuItem("Celdas con valor posible 1");
	private JRadioButtonMenuItem menuItemVerPosibles2 = new JRadioButtonMenuItem("Celdas con valor posible 2");
	private JRadioButtonMenuItem menuItemVerPosibles3 = new JRadioButtonMenuItem("Celdas con valor posible 3");
	private JRadioButtonMenuItem menuItemVerPosibles4 = new JRadioButtonMenuItem("Celdas con valor posible 4");
	private JRadioButtonMenuItem menuItemVerPosibles5 = new JRadioButtonMenuItem("Celdas con valor posible 5");
	private JRadioButtonMenuItem menuItemVerPosibles6 = new JRadioButtonMenuItem("Celdas con valor posible 6");
	private JRadioButtonMenuItem menuItemVerPosibles7 = new JRadioButtonMenuItem("Celdas con valor posible 7");
	private JRadioButtonMenuItem menuItemVerPosibles8 = new JRadioButtonMenuItem("Celdas con valor posible 8");
	private JRadioButtonMenuItem menuItemVerPosibles9 = new JRadioButtonMenuItem("Celdas con valor posible 9");
	private JMenuItem menuItemHerramientasPista = new JMenuItem("Obtener una pista");
	private JMenuItem menuItemHerramientasResolver = new JMenuItem("Resolver");
	private JMenuItem menuItemAyudaAyuda = new JMenuItem("Ayuda");
	
	private GestorMenu gestor;
	
	public Menu(SudokuGrafico aplicacion) {
		// Crear Componentes
		//// Barra de menu
		super();
		//// Menu Archivo
		this.menuArchivo = new JMenu("Archivo");
		this.menuArchivo.add(this.menuItemArchivoNuevo);
		this.menuArchivo.add(this.menuItemArchivoAbrir);
		this.menuArchivo.add(this.menuItemArchivoGuardar);
		this.menuArchivo.add(this.menuItemArchivoGuardarComo);
		this.menuArchivo.addSeparator();
		this.menuArchivo.add(this.menuItemArchivoSalir);
		this.add(this.menuArchivo);
		////	 Menu Editar
		this.menuEditar = new JMenu("Editar");
		this.menuEditar.add(this.menuItemEditarUndo);
		this.menuEditar.add(this.menuItemEditarRedo);
		this.add(this.menuEditar);
		//// Menu Ver
			//// Primero crea el grupo
		this.menuItemVerSoloResueltos.setSelected(true);
		this.grupoVer = new ButtonGroup();		
		this.grupoVer.add(this.menuItemVerSoloResueltos);
		this.grupoVer.add(this.menuItemVerNumeroValoresPosibles);
		this.grupoVer.add(this.menuItemVerPosibles1);
		this.grupoVer.add(this.menuItemVerPosibles2);
		this.grupoVer.add(this.menuItemVerPosibles3);
		this.grupoVer.add(this.menuItemVerPosibles4);
		this.grupoVer.add(this.menuItemVerPosibles5);
		this.grupoVer.add(this.menuItemVerPosibles6);
		this.grupoVer.add(this.menuItemVerPosibles7);
		this.grupoVer.add(this.menuItemVerPosibles8);
		this.grupoVer.add(this.menuItemVerPosibles9);
			//// Ahora el menu
		this.menuVer = new JMenu("Ver");
		this.menuVer.add(this.menuItemVerSoloResueltos);
		this.menuVer.add(this.menuItemVerNumeroValoresPosibles);
		this.menuVer.addSeparator();
		this.menuVer.add(this.menuItemVerPosibles1);
		this.menuVer.add(this.menuItemVerPosibles2);
		this.menuVer.add(this.menuItemVerPosibles3);
		this.menuVer.add(this.menuItemVerPosibles4);
		this.menuVer.add(this.menuItemVerPosibles5);
		this.menuVer.add(this.menuItemVerPosibles6);
		this.menuVer.add(this.menuItemVerPosibles7);
		this.menuVer.add(this.menuItemVerPosibles8);
		this.menuVer.add(this.menuItemVerPosibles9);
		this.add(this.menuVer);
		////	 Menu Herramientas
		this.menuHerramientas = new JMenu("Herramientas");
		this.menuHerramientas.add(this.menuItemHerramientasPista);
		this.menuHerramientas.add(this.menuItemHerramientasResolver);
		this.add(this.menuHerramientas);
		////	 Menu Ayuda
		this.menuAyuda = new JMenu("Ayuda");
		this.menuAyuda.add(this.menuItemAyudaAyuda);
		this.add(this.menuAyuda);	
		
		// Y la ventana que le contiene:
		this.aplicacion = aplicacion;
		
		//Crea el gestor del menu
		this.gestor = new GestorMenu(this);
		//Audita los menus
		this.menuItemArchivoNuevo.addActionListener(this.gestor);
		this.menuItemArchivoAbrir.addActionListener(this.gestor);
		this.menuItemArchivoGuardar.addActionListener(this.gestor);
		this.menuItemArchivoGuardarComo.addActionListener(this.gestor);
		this.menuItemArchivoSalir.addActionListener(this.gestor);
		this.menuItemEditarUndo.addActionListener(this.gestor);
		this.menuItemEditarRedo.addActionListener(this.gestor);
		this.menuItemVerSoloResueltos.addActionListener(this.gestor);
		this.menuItemVerNumeroValoresPosibles.addActionListener(this.gestor);
		this.menuItemVerPosibles1.addActionListener(this.gestor);
		this.menuItemVerPosibles2.addActionListener(this.gestor);
		this.menuItemVerPosibles3.addActionListener(this.gestor);
		this.menuItemVerPosibles4.addActionListener(this.gestor);
		this.menuItemVerPosibles5.addActionListener(this.gestor);
		this.menuItemVerPosibles6.addActionListener(this.gestor);
		this.menuItemVerPosibles7.addActionListener(this.gestor);
		this.menuItemVerPosibles8.addActionListener(this.gestor);
		this.menuItemVerPosibles9.addActionListener(this.gestor);
		this.menuItemHerramientasPista.addActionListener(this.gestor);
		this.menuItemHerramientasResolver.addActionListener(this.gestor);
		this.menuItemAyudaAyuda.addActionListener(this.gestor);
	}

	public void descifrarBotonPulsado(JMenuItem item) {
		if (item == this.menuItemEditarUndo) {
			this.aplicacion.deshacerMovimiento();
		} else if (item == this.menuItemEditarRedo) {
			this.aplicacion.rehacerMovimiento();
		} else if (item == this.menuItemVerSoloResueltos) {
			this.aplicacion.visualizarTablero(Tablero.SOLO_ASIGNADAS);
		} else if (item == this.menuItemVerNumeroValoresPosibles) {
			this.aplicacion
					.visualizarTablero(Tablero.ASIGNADAS_Y_NUM_VALORES_POSIBLES);
		} else if (item == this.menuItemVerPosibles1) {
			this.aplicacion.visualizarTablero(
					Tablero.BUSCAR_VALOR_POSIBLE_DETERMINADO, 1);
		} else if (item == this.menuItemVerPosibles2) {
			this.aplicacion.visualizarTablero(
					Tablero.BUSCAR_VALOR_POSIBLE_DETERMINADO, 2);
		} else if (item == this.menuItemVerPosibles3) {
			this.aplicacion.visualizarTablero(
					Tablero.BUSCAR_VALOR_POSIBLE_DETERMINADO, 3);
		} else if (item == this.menuItemVerPosibles4) {
			this.aplicacion.visualizarTablero(
					Tablero.BUSCAR_VALOR_POSIBLE_DETERMINADO, 4);
		} else if (item == this.menuItemVerPosibles5) {
			this.aplicacion.visualizarTablero(
					Tablero.BUSCAR_VALOR_POSIBLE_DETERMINADO, 5);
		} else if (item == this.menuItemVerPosibles6) {
			this.aplicacion.visualizarTablero(
					Tablero.BUSCAR_VALOR_POSIBLE_DETERMINADO, 6);
		} else if (item == this.menuItemVerPosibles7) {
			this.aplicacion.visualizarTablero(
					Tablero.BUSCAR_VALOR_POSIBLE_DETERMINADO, 7);
		} else if (item == this.menuItemVerPosibles8) {
			this.aplicacion.visualizarTablero(
					Tablero.BUSCAR_VALOR_POSIBLE_DETERMINADO, 8);
		} else if (item == this.menuItemVerPosibles9) {
			this.aplicacion.visualizarTablero(
					Tablero.BUSCAR_VALOR_POSIBLE_DETERMINADO, 9);
		}
		
	}
}
