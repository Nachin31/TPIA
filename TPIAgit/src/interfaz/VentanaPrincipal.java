package interfaz;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.border.LineBorder;

import excepciones.CoordenadasSinEsquinaException;

import java.awt.TextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import logica.Esquina;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 6826713966571345080L;
	private JMenuBar jMenuBar=new JMenuBar();
	private JMenu jMenuArchivo=new JMenu("Archivo");
	private JTextField textField;
	private JCheckBox chckbxHayLadrn;
	private JButton btnNewButton;
	private List<Esquina> esquinas;
	private final int DIAMETRO_ESQUINA=7;
	private Esquina esquinaEnEdicion;
	private float energiaGastada;
	private float energiaTotal;
	private float porcentajeRemanente;
	private JLabel label;
	
	public static void main(String[] args){
		new VentanaPrincipal();
	}
	
	public VentanaPrincipal(){
		getContentPane().setLayout(null);
		jMenuBar.setBounds(0, 0, 0, 0);
		jMenuBar.add(jMenuArchivo);
		this.getContentPane().add(jMenuBar);
		setBounds(100,100,725,678);
		
		esquinas = new ArrayList<Esquina>();
		
		// Subcuadrante A1M1
		esquinas.add(new Esquina(148,10,"A1M1B1"));
		esquinas.add(new Esquina(191,21,"A1M1B2"));
		esquinas.add(new Esquina(215,28,"A1M1B3"));
		esquinas.add(new Esquina(202,78,"A1M1B4"));
		esquinas.add(new Esquina(180,72,"A1M1B5"));
		esquinas.add(new Esquina(163,68,"A1M1B6"));
		esquinas.add(new Esquina(134,60,"A1M1B7"));

		// Subcuadrante A1M2
		esquinas.add(new Esquina(260,40,"A1M1B1"));
		esquinas.add(new Esquina(255,67,"A1M2B2"));
		esquinas.add(new Esquina(247,89,"A1M2B3"));
		esquinas.add(new Esquina(215,28,"A1M2B4"));
		esquinas.add(new Esquina(202,78,"A1M2B5"));
		
		// Subcuadrante A2M1
		esquinas.add(new Esquina(306,47,"A2M1B1"));
		esquinas.add(new Esquina(340,56,"A2M1B2"));
		esquinas.add(new Esquina(374,65,"A2M1B3"));
		esquinas.add(new Esquina(366,98,"A2M1B4"));
		esquinas.add(new Esquina(332,89,"A2M1B5"));
		esquinas.add(new Esquina(298,80,"A2M1B6"));

		// Subcuadrante A2M2
		esquinas.add(new Esquina(406,73,"A2M2B1"));
		esquinas.add(new Esquina(440,83,"A2M2B2"));
		esquinas.add(new Esquina(432,116,"A2M2B3"));
		esquinas.add(new Esquina(399,107,"A2M2B4"));
		esquinas.add(new Esquina(366,98,"A2M2B5"));
		esquinas.add(new Esquina(374,65,"A2M2B6"));
		
		// Subcuadrante A1M3
		esquinas.add(new Esquina(130,84,"A1M3B1"));
		esquinas.add(new Esquina(176,98,"A1M3B2"));
		esquinas.add(new Esquina(191,129,"A1M3B3"));
		esquinas.add(new Esquina(169,126,"A1M3B4"));
		esquinas.add(new Esquina(124,114,"A1M3B5"));
		
		// Subcuadrante A1M4
		esquinas.add(new Esquina(248,104,"A1M4B1"));
		esquinas.add(new Esquina(237,143,"A1M4B2"));
		esquinas.add(new Esquina(191,129,"A1M4B3"));
		
		// Subcuadrante A2M3
		esquinas.add(new Esquina(289,117,"A2M3B1"));
		esquinas.add(new Esquina(323,124,"A2M3B2"));
		esquinas.add(new Esquina(358,135,"A2M3B3"));
		esquinas.add(new Esquina(346,172,"A2M3B4"));
		esquinas.add(new Esquina(314,163,"A2M3B5"));
		esquinas.add(new Esquina(280,154,"A2M3B6"));
		esquinas.add(new Esquina(346,172,"A2M3B4"));
		esquinas.add(new Esquina(339,211,"A2M3B7"));
		esquinas.add(new Esquina(304,200,"A2M3B8"));
		esquinas.add(new Esquina(270,192,"A2M3B9"));
		
		// Subcuadrante A2M4
		esquinas.add(new Esquina(389,142,"A2M4B1"));
		esquinas.add(new Esquina(422,151,"A2M4B2"));
		esquinas.add(new Esquina(414,190,"A2M4B3"));
		esquinas.add(new Esquina(381,183,"A2M4B4"));
		esquinas.add(new Esquina(346,172,"A2M4B5"));
		esquinas.add(new Esquina(407,229,"A2M4B6"));
		esquinas.add(new Esquina(373,219,"A2M4B7"));
		esquinas.add(new Esquina(339,211,"A2M4B8"));

		// Subcuadrante A3M1
		esquinas.add(new Esquina(111,161,"A3M1B1"));
		esquinas.add(new Esquina(157,172,"A3M1B2"));
		esquinas.add(new Esquina(180,178,"A3M1B3"));
		esquinas.add(new Esquina(167,218,"A3M1B4"));
		esquinas.add(new Esquina(146,213,"A3M1B5"));
		esquinas.add(new Esquina(102,200,"A3M1B6"));
		
		// Subcuadrante A3M2
		esquinas.add(new Esquina(180,178,"A3M2B1"));
		esquinas.add(new Esquina(223,189,"A3M2B2"));
		esquinas.add(new Esquina(248,241,"A3M2B3"));
		esquinas.add(new Esquina(216,231,"A3M2B4"));
		esquinas.add(new Esquina(167,218,"A3M2B5"));
		
		// Subcuadrante A4M1
		esquinas.add(new Esquina(270,192,"A4M1B1"));
		esquinas.add(new Esquina(304,200,"A4M1B2"));
		esquinas.add(new Esquina(339,211,"A4M1B3"));
		esquinas.add(new Esquina(330,243,"A4M1B4"));
		esquinas.add(new Esquina(297,234,"A4M1B5"));
		esquinas.add(new Esquina(265,227,"A4M1B6"));
		esquinas.add(new Esquina(290,253,"A4M1B7"));
		esquinas.add(new Esquina(248,241,"A4M1B8"));

		// Subcuadrante A4M2
		esquinas.add(new Esquina(339,211,"A4M2B1"));
		esquinas.add(new Esquina(373,219,"A4M2B2"));
		esquinas.add(new Esquina(407,229,"A4M2B3"));
		esquinas.add(new Esquina(399,259,"A4M2B4"));
		esquinas.add(new Esquina(364,253,"A4M2B5"));
		esquinas.add(new Esquina(330,243,"A4M2B6"));		
		
		// Subcuadrante A3M3
		esquinas.add(new Esquina(91,243,"A3M3B1"));
		esquinas.add(new Esquina(138,253,"A3M3B2"));
		esquinas.add(new Esquina(158,259,"A3M3B3"));
		esquinas.add(new Esquina(147,305,"A3M3B4"));
		esquinas.add(new Esquina(123,297,"A3M3B5"));
		esquinas.add(new Esquina(78,285,"A3M3B6"));

		// Subcuadrante A3M4
		esquinas.add(new Esquina(158,259,"A3M4B1"));
		esquinas.add(new Esquina(205,272,"A3M4B2"));
		esquinas.add(new Esquina(238,282,"A3M4B3"));
		esquinas.add(new Esquina(229,325,"A3M4B4"));
		esquinas.add(new Esquina(193,317,"A3M4B5"));
		esquinas.add(new Esquina(147,305,"A3M3B6"));
		
		// Subcuadrante A4M3
		esquinas.add(new Esquina(238,282,"A4M3B1"));
		esquinas.add(new Esquina(262,286,"A4M3B2"));
		esquinas.add(new Esquina(283,292,"A4M3B3"));
		esquinas.add(new Esquina(288,269,"A4M3B4"));
		esquinas.add(new Esquina(320,280,"A4M3B5"));
		esquinas.add(new Esquina(314,301,"A4M3B6"));
		esquinas.add(new Esquina(307,336,"A4M3B7"));
		esquinas.add(new Esquina(306,347,"A4M3B8"));
		esquinas.add(new Esquina(270,337,"A4M3B9"));
		esquinas.add(new Esquina(229,325,"A4M3B10"));
		
		// Subcuadrante A4M4
		esquinas.add(new Esquina(320,280,"A4M4B1"));
		esquinas.add(new Esquina(355,287,"A4M4B2"));
		esquinas.add(new Esquina(390,297,"A4M4B3"));
		esquinas.add(new Esquina(380,327,"A4M4B4"));
		esquinas.add(new Esquina(347,325,"A4M4B5"));
		esquinas.add(new Esquina(315,314,"A4M4B6"));
		esquinas.add(new Esquina(314,301,"A4M3B7"));
		esquinas.add(new Esquina(307,336,"A4M4B8"));
		esquinas.add(new Esquina(306,347,"A4M4B9"));
		esquinas.add(new Esquina(321,351,"A4M4B9"));
		esquinas.add(new Esquina(321,351,"A4M4B10"));
		esquinas.add(new Esquina(339,355,"A4M4B11"));
		esquinas.add(new Esquina(371,364,"A4M4B12"));
		
		JPanel jPanelConsolaIzquierda = new JPanel();
		jPanelConsolaIzquierda.setBounds(0, 0, 170, 400);
		jPanelConsolaIzquierda.setLayout(null);
		//jPanelConsolaIzquierda.setBackground(Color.blue);
		this.getContentPane().add(jPanelConsolaIzquierda);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Config Esquina", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		panel.setBounds(10, 143, 150, 125);
		jPanelConsolaIzquierda.add(panel);
		panel.setLayout(null);
		
		JLabel lblCantidadDePersonas = new JLabel("Cantidad de personas");
		lblCantidadDePersonas.setBounds(10, 23, 130, 28);
		panel.add(lblCantidadDePersonas);
		
		textField = new JTextField();
		textField.setBounds(10, 51, 130, 28);
		panel.add(textField);
		textField.setColumns(10);
		
		chckbxHayLadrn = new JCheckBox("Hay criminal");
		chckbxHayLadrn.setBounds(10, 86, 134, 28);
		panel.add(chckbxHayLadrn);
		
		textField.setEnabled(false);
		chckbxHayLadrn.setEnabled(false);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Estrategia de b�squeda", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 20, 150, 112);
		jPanelConsolaIzquierda.add(panel_1);
		panel_1.setLayout(null);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(10, 24, 130, 27);
		panel_1.add(comboBox);
		comboBox.addItem("En anchura");
		comboBox.addItem("En profundidad");
		comboBox.addItem("Por menor costo");
		comboBox.addItem("AVARA");
		comboBox.setFont(new Font("Calibri", Font.BOLD, 13));
		
		JButton btnComenzar = new JButton("COMENZAR");
		btnComenzar.setBounds(10, 62, 130, 35);
		panel_1.add(btnComenzar);
		
		JPanel panel_2 = new JPanel(){
			private static final long serialVersionUID = -7651674100274809872L;

			public void paintComponent (Graphics g){
				Dimension tamanio = getSize();
				ImageIcon imagenFondo = new ImageIcon(getClass().getResource("/imagenes/Mapatotal.png"));
				g.drawImage(imagenFondo.getImage(),0,0,tamanio.width,tamanio.height, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_2.setBounds(10, 279, 150, 110);
		jPanelConsolaIzquierda.add(panel_2);
		panel_2.setLayout(null);
		
		JPanel jPanelConsolaMapa = new JPanel();
		jPanelConsolaMapa.setBounds(170, 0, 549, 400);
		jPanelConsolaMapa.setLayout(null);
		//jPanelConsolaMapa.setBackground(Color.red);
		this.getContentPane().add(jPanelConsolaMapa);
		
		final PanelMapa panel_4 = new PanelMapa(esquinas,DIAMETRO_ESQUINA);
		panel_4.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_4.setBounds(10, 10, 529, 380);
		jPanelConsolaMapa.add(panel_4);
		panel_4.setLayout(null);
		
		panel_4.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int x=e.getX(),y=e.getY();
				//System.out.println("esquinas.add(new Esquina(" + x +"," + y"));");
				if(SwingUtilities.isLeftMouseButton(e) && e.getClickCount()==1){
					//Habilitar campos y cargar info de esquina
					try{
						esquinaEnEdicion = panel_4.getEsquina(x,y);
						textField.setText(""+esquinaEnEdicion.getCantidadPersonas());
						chckbxHayLadrn.setSelected(esquinaEnEdicion.hayCriminal());
						textField.setEnabled(true);
						chckbxHayLadrn.setEnabled(true);
					}catch(CoordenadasSinEsquinaException e1){}
				}
				else{
					//Limpiar info de esquina y deshabilitar campos
					try{
						esquinaEnEdicion.setCriminal(chckbxHayLadrn.isSelected());
						esquinaEnEdicion.setCantidadPersonas(Integer.parseInt(textField.getText()));
						textField.setText("");
						chckbxHayLadrn.setSelected(false);
						textField.setEnabled(false);
						chckbxHayLadrn.setEnabled(false);
						esquinaEnEdicion = null;
						panel_4.repaint();
					}catch(NumberFormatException e2){
					}catch(NullPointerException e3){}
					
				}
			}
		});
		
		JPanel jPanelConsola = new JPanel();
		//jPanelConsola.setBackground(Color.GREEN);
		jPanelConsola.setBounds(0, 400, 725, 228);
		getContentPane().add(jPanelConsola);
		jPanelConsola.setLayout(null);
		
		//Aqui comienza la progress bar
		//Tendria que tener el valor de "energia total" y "energia gastada"
		energiaGastada = 650;
		energiaTotal = 10000;
		JPanel panel_3 = new JPanel(){
			private static final long serialVersionUID = -7651674100274809872L;

			public void paintComponent (Graphics g){
				Dimension tamanio = getSize();
				ImageIcon imagenFondo = new ImageIcon(getClass().getResource("/imagenes/progress-bar.png"));
				g.drawImage(imagenFondo.getImage(),0,0,tamanio.width,tamanio.height, null);
				
				//La cantidad de energia para poder graficarla
				float proporcionGastada = energiaGastada/energiaTotal;
				float proporcionBarraGastada = proporcionGastada*tamanio.width;
				
				this.setBackground(Color.black);
				g.fillRect(tamanio.width-((int)proporcionBarraGastada),0,((int)proporcionBarraGastada),tamanio.height);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		panel_3.setBackground(Color.black);
		panel_3.setBounds(171, 0, 539, 29);
		jPanelConsola.add(panel_3);
		panel_3.setLayout(null);
		
		porcentajeRemanente = (1 -(energiaGastada/energiaTotal))*100;
		label = new JLabel(""+String.format("%.2f", porcentajeRemanente)+"%");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Calibri", Font.BOLD, 13));
		label.setBounds(224, 5, 91, 18);
		panel_3.add(label);
		//Terminar la progress bar
		
		btnNewButton = new JButton("U");
		btnNewButton.setFont(new Font("Calibri", Font.BOLD, 11));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(btnNewButton.getText()=="U"){
					btnNewButton.setText("%");
					label.setText(""+(int)(energiaTotal-energiaGastada));
				}
				else{
					btnNewButton.setText("U");
					label.setText(""+String.format("%.2f", porcentajeRemanente)+"%");
				}
			}
		});
		btnNewButton.setBounds(46, 0, 46, 29);
		jPanelConsola.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Energ�a");
		lblNewLabel.setBounds(102, 3, 60, 23);
		jPanelConsola.add(lblNewLabel);
		
		TextArea textArea = new TextArea();
		textArea.setBounds(10, 40, 700, 178);
		textArea.setEditable(false);
		jPanelConsola.add(textArea);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);
		
		JMenuItem mntmCargarDeArchivo = new JMenuItem("Cargar de archivo...");
		mnArchivo.add(mntmCargarDeArchivo);
		
		JMenuItem mntmGuardarAArchivo = new JMenuItem("Guardar a archivo...");
		mnArchivo.add(mntmGuardarAArchivo);
		
		JMenuItem mntmGuardarSalidaDe = new JMenuItem("Guardar salida de consola");
		mnArchivo.add(mntmGuardarSalidaDe);
		setResizable(false);
		setVisible(true);
		
		
	}
	
}
