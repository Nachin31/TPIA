package interfaz;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
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
import frsf.cidisi.exercise.drone.search.DroneAgent;

import java.awt.TextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import logica.Esquina;
import logica.HiloSimulador;
import logica.Locacion;

public class VentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 6826713966571345080L;
	private JTextField textField;
	private JCheckBox chckbxHayLadrn;
	private JButton btnNewButton;
	private List<Esquina> esquinas;
	private final int DIAMETRO_ESQUINA=7;
	private Esquina esquinaEnEdicion;
	private static float energiaGastada;
	private static float energiaTotal;
	private static float porcentajeRemanente;
	private static JLabel label;
	private static ProgressBar progressBar;
	private static Locacion ciudad;
	private static Locacion ciudadVacia;
	static PanelMapa panel_4;
	
	//TODO usar variable "insimulation" que deshabilite el bot�n hasta que termina.
	
	public static void main(String[] args){
		new VentanaPrincipal();
	}
	
	public VentanaPrincipal(){
		getContentPane().setLayout(null);
		setBounds(100,100,725,678);
		esquinas = new ArrayList<Esquina>();
		
		// CIUDAD
		ciudad = initCiudad();
		ciudadVacia = ciudad.clone(null);
		ciudadVacia.vaciar();
		
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
		
		final JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(10, 24, 130, 27);
		panel_1.add(comboBox);
		comboBox.addItem("En anchura");
		comboBox.addItem("En profundidad");
		comboBox.addItem("Por costo uniforme");
		comboBox.addItem("A*");
		comboBox.setFont(new Font("Calibri", Font.BOLD, 13));
		
		JButton btnComenzar = new JButton("COMENZAR");
		btnComenzar.setBounds(10, 62, 130, 35);
		panel_1.add(btnComenzar);
		
		btnComenzar.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String item = (String)comboBox.getSelectedItem();
				HiloSimulador ejecucionAgente = new HiloSimulador();
				if(item.equals("En anchura"))						
					ejecucionAgente.setEstrategia(DroneAgent.ANCHURA);
				else if(item.equals("En profundidad"))
					ejecucionAgente.setEstrategia(DroneAgent.PROFUNDIDAD);
				else if(item.equals("Por costo uniforme"))
					ejecucionAgente.setEstrategia(DroneAgent.COSTO_UNIFORME);
				else
					ejecucionAgente.setEstrategia(DroneAgent.A_ASTERISCO);
				ciudad.calcularSeniales();
				ejecucionAgente.start();
			}
		});
		
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
		
		panel_4 = new PanelMapa(esquinas,DIAMETRO_ESQUINA);
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
		energiaGastada = 0;
		energiaTotal = 1000;
		progressBar = new ProgressBar(energiaTotal,energiaTotal,"Porcentaje");
		progressBar.setBackground(Color.black);
		progressBar.setBounds(171, 0, 539, 29);
		jPanelConsola.add(progressBar);
		
		
		btnNewButton = new JButton("U");
		btnNewButton.setFont(new Font("Calibri", Font.BOLD, 11));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(btnNewButton.getText()=="U")
					btnNewButton.setText("%");
				else
					btnNewButton.setText("U");
				progressBar.changeUnit();
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
		
		setResizable(false);
		setVisible(true);
		
		
	}
	
	public static void updateEnerg�a(int gasto){
		progressBar.updateActualValue(((int)energiaTotal-gasto));
	}
	
	public static void updateEsquina(double d,double e,int senial){
		ciudadVacia.actualizarEsquina(d,e,senial);
	}
	
	public static void updateLocacionDrone(Locacion locacion){
		panel_4.highlightDroneLocation(locacion);
		panel_4.repaint();
	}
	
	private Locacion initCiudad(){
		
		// CIUDAD
		Locacion ciudad = new Locacion();
		
		// Cuadrante A1
		Locacion a1 = new Locacion(191,21,"A1",ciudad);
		
		// Subcuadrante A1M1
		Locacion a1m1 = new Locacion(191,21,"A1M1",a1);
		Esquina a1m1b1 = new Esquina(148,10,"A1M1B1",a1m1);
		Esquina a1m1b2 = new Esquina(191,21,"A1M1B2",a1m1);
		Esquina a1m1b3 = new Esquina(215,28,"A1M1B3",a1m1);
		Esquina a1m1b4 = new Esquina(202,78,"A1M1B4",a1m1);
		Esquina a1m1b5 = new Esquina(180,72,"A1M1B5",a1m1);
		Esquina a1m1b6 = new Esquina(163,68,"A1M1B6",a1m1);
		Esquina a1m1b7 = new Esquina(134,60,"A1M1B7",a1m1);
		a1m1b1.addAdyacente(a1m1b2, Locacion.ESTE);a1m1b1.addAdyacente(a1m1b7, Locacion.SUR);
		a1m1b2.addAdyacente(a1m1b1, Locacion.OESTE);a1m1b2.addAdyacente(a1m1b3, Locacion.ESTE);a1m1b2.addAdyacente(a1m1b5, Locacion.SUR);
		a1m1b3.addAdyacente(a1m1b2, Locacion.OESTE);
		a1m1b4.addAdyacente(a1m1b5, Locacion.OESTE);
		a1m1b5.addAdyacente(a1m1b4, Locacion.ESTE);a1m1b5.addAdyacente(a1m1b6, Locacion.OESTE);a1m1b5.addAdyacente(a1m1b2, Locacion.NORTE);
		a1m1b6.addAdyacente(a1m1b5, Locacion.ESTE);a1m1b6.addAdyacente(a1m1b7, Locacion.OESTE);
		a1m1b7.addAdyacente(a1m1b6, Locacion.ESTE);a1m1b7.addAdyacente(a1m1b1, Locacion.NORTE);		
		a1m1.addSublocacion(a1m1b1);
		a1m1.addSublocacion(a1m1b2);
		a1m1.addSublocacion(a1m1b3);
		a1m1.addSublocacion(a1m1b4);		
		a1m1.addSublocacion(a1m1b5);
		a1m1.addSublocacion(a1m1b6);
		a1m1.addSublocacion(a1m1b7);		
		esquinas.add(a1m1b1);
		esquinas.add(a1m1b2);
		esquinas.add(a1m1b3);
		esquinas.add(a1m1b4);
		esquinas.add(a1m1b5);
		esquinas.add(a1m1b6);
		esquinas.add(a1m1b7);

		// Subcuadrante A1M2
		Locacion a1m2 = new Locacion(255,67,"A1M2",a1);
		Esquina a1m2b1 = new Esquina(260,40,"A1M2B1",a1m2);
		Esquina a1m2b2 = new Esquina(255,67,"A1M2B2",a1m2);
		Esquina a1m2b3 = new Esquina(247,89,"A1M2B3",a1m2);
		Esquina a1m2b4= new Esquina(215,28,"A1M2B4",a1m2);
		Esquina a1m2b5 = new Esquina(202,78,"A1M2B5",a1m2);
		a1m2b1.addAdyacente(a1m2b2, Locacion.SUR);a1m2b1.addAdyacente(a1m2b4, Locacion.OESTE);
		a1m2b2.addAdyacente(a1m2b1, Locacion.NORTE);a1m2b2.addAdyacente(a1m2b3, Locacion.SUR);
		a1m2b3.addAdyacente(a1m2b2, Locacion.NORTE);a1m2b3.addAdyacente(a1m2b5, Locacion.OESTE);
		a1m2b4.addAdyacente(a1m2b1, Locacion.ESTE);a1m2b4.addAdyacente(a1m2b5, Locacion.SUR);
		a1m2b5.addAdyacente(a1m2b4, Locacion.NORTE);a1m2b5.addAdyacente(a1m2b3, Locacion.ESTE);
		a1m2.addSublocacion(a1m2b1);
		a1m2.addSublocacion(a1m2b2);
		a1m2.addSublocacion(a1m2b3);
		a1m2.addSublocacion(a1m2b4);		
		a1m2.addSublocacion(a1m2b5);
		esquinas.add(a1m2b1);
		esquinas.add(a1m2b2);
		esquinas.add(a1m2b3);
		esquinas.add(a1m2b4);
		esquinas.add(a1m2b5);
		
		// Subcuadrante A1M3
		Locacion a1m3 = new Locacion(169,126,"A1M3",a1);
		Esquina a1m3b1 = new Esquina(130,84,"A1M3B1",a1m3);
		Esquina a1m3b2 = new Esquina(176,98,"A1M3B2",a1m3);
		Esquina a1m3b3 = new Esquina(191,129,"A1M3B3",a1m3);
		Esquina a1m3b4 = new Esquina(169,126,"A1M3B4",a1m3);
		Esquina a1m3b5 = new Esquina(124,114,"A1M3B5",a1m3);
		a1m3b1.addAdyacente(a1m3b2,Locacion.ESTE);a1m3b1.addAdyacente(a1m3b5,Locacion.SUR);
		a1m3b2.addAdyacente(a1m3b1,Locacion.OESTE);a1m3b2.addAdyacente(a1m3b4,Locacion.SUR);a1m3b2.addAdyacente(a1m3b3,Locacion.SURESTE);
		a1m3b3.addAdyacente(a1m3b2,Locacion.NOROESTE);a1m3b3.addAdyacente(a1m3b4,Locacion.OESTE);
		a1m3b4.addAdyacente(a1m3b2,Locacion.NORTE);a1m3b4.addAdyacente(a1m3b3,Locacion.ESTE);a1m3b4.addAdyacente(a1m3b5,Locacion.OESTE);
		a1m3b5.addAdyacente(a1m3b1,Locacion.NORTE);a1m3b5.addAdyacente(a1m3b4,Locacion.ESTE);
		a1m3.addSublocacion(a1m3b1);
		a1m3.addSublocacion(a1m3b2);
		a1m3.addSublocacion(a1m3b3);
		a1m3.addSublocacion(a1m3b4);		
		a1m3.addSublocacion(a1m3b5);
		esquinas.add(a1m3b1);
		esquinas.add(a1m3b2);
		esquinas.add(a1m3b3);
		esquinas.add(a1m3b4);
		esquinas.add(a1m3b5);
		
		// Subcuadrante A1M4
		Locacion a1m4 = new Locacion(237,143,"A1M4",a1);
		Esquina a1m4b1 = new Esquina(248,104,"A1M4B1",a1m4);
		Esquina a1m4b2 = new Esquina(237,143,"A1M4B2",a1m4);
		Esquina a1m4b3 = new Esquina(191,129,"A1M4B3",a1m4);
		a1m4b1.addAdyacente(a1m4b2,Locacion.SUR);
		a1m4b2.addAdyacente(a1m4b1,Locacion.NORTE);a1m4b2.addAdyacente(a1m4b3,Locacion.OESTE);
		a1m4b3.addAdyacente(a1m4b2,Locacion.ESTE);
		a1m4.addSublocacion(a1m4b1);
		a1m4.addSublocacion(a1m4b2);
		a1m4.addSublocacion(a1m4b3);
		esquinas.add(a1m4b1);
		esquinas.add(a1m4b2);
		esquinas.add(a1m4b3);
		
		a1m1.addAdyacente(a1m2, Locacion.ESTE);a1m1.addAdyacente(a1m3, Locacion.SUR);
		a1m2.addAdyacente(a1m1, Locacion.OESTE);a1m2.addAdyacente(a1m4, Locacion.SUR);
		a1m3.addAdyacente(a1m1, Locacion.NORTE);a1m3.addAdyacente(a1m4, Locacion.ESTE);
		a1m4.addAdyacente(a1m2, Locacion.NORTE);a1m4.addAdyacente(a1m3, Locacion.OESTE);
		a1.addSublocacion(a1m1);a1.addSublocacion(a1m2);a1.addSublocacion(a1m3);a1.addSublocacion(a1m4);
		
		// Cuadrante A2
		Locacion a2 = new Locacion(332,89,"A2",ciudad);
		
		// Subcuadrante A2M1
		Locacion a2m1 = new Locacion(332,89,"A2M1",a2);
		Esquina a2m1b1 = new Esquina(306,47,"A2M1B1",a2m1);
		Esquina a2m1b2 = new Esquina(340,56,"A2M1B2",a2m1);
		Esquina a2m1b3 = new Esquina(374,65,"A2M1B3",a2m1);
		Esquina a2m1b4 = new Esquina(366,98,"A2M1B4",a2m1);
		Esquina a2m1b5 = new Esquina(332,89,"A2M1B5",a2m1);
		Esquina a2m1b6 = new Esquina(298,80,"A2M1B6",a2m1);
		a2m1b1.addAdyacente(a2m1b2,Locacion.ESTE);a2m1b1.addAdyacente(a2m1b6,Locacion.SUR);
		a2m1b2.addAdyacente(a2m1b1,Locacion.OESTE);a2m1b2.addAdyacente(a2m1b3,Locacion.ESTE);a2m1b2.addAdyacente(a2m1b5,Locacion.SUR);
		a2m1b3.addAdyacente(a2m1b2,Locacion.OESTE);a2m1b3.addAdyacente(a2m1b4,Locacion.SUR);
		a2m1b4.addAdyacente(a2m1b3,Locacion.NORTE);a2m1b4.addAdyacente(a2m1b5,Locacion.OESTE);
		a2m1b5.addAdyacente(a2m1b2,Locacion.NORTE);a2m1b5.addAdyacente(a2m1b6,Locacion.OESTE);a2m1b5.addAdyacente(a2m1b4,Locacion.ESTE);
		a2m1b6.addAdyacente(a2m1b5,Locacion.ESTE);a2m1b6.addAdyacente(a2m1b1,Locacion.NORTE);
		a2m1.addSublocacion(a2m1b1);
		a2m1.addSublocacion(a2m1b2);
		a2m1.addSublocacion(a2m1b3);
		a2m1.addSublocacion(a2m1b4);		
		a2m1.addSublocacion(a2m1b5);
		a2m1.addSublocacion(a2m1b6);
		esquinas.add(a2m1b1);
		esquinas.add(a2m1b2);
		esquinas.add(a2m1b3);
		esquinas.add(a2m1b4);
		esquinas.add(a2m1b5);
		esquinas.add(a2m1b6);
		
		// Subcuadrante A2M2
		Locacion a2m2 = new Locacion(399,107,"A2M2",a2);
		Esquina a2m2b1 = new Esquina(406,73,"A2M2B1",a2m2);
		Esquina a2m2b2 = new Esquina(440,83,"A2M2B2",a2m2);
		Esquina a2m2b3 = new Esquina(432,116,"A2M2B3",a2m2);
		Esquina a2m2b4 = new Esquina(399,107,"A2M2B4",a2m2);
		Esquina a2m2b5 = new Esquina(366,98,"A2M2B5",a2m2);
		Esquina a2m2b6 = new Esquina(374,65,"A2M2B6",a2m2);
		a2m2b1.addAdyacente(a2m2b2, Locacion.ESTE);a2m2b1.addAdyacente(a2m2b4, Locacion.SUR);a2m2b1.addAdyacente(a2m2b6, Locacion.OESTE);
		a2m2b2.addAdyacente(a2m2b1, Locacion.OESTE);a2m2b2.addAdyacente(a2m2b3, Locacion.SUR);
		a2m2b3.addAdyacente(a2m2b2, Locacion.NORTE);a2m2b3.addAdyacente(a2m2b4, Locacion.OESTE);
		a2m2b4.addAdyacente(a2m2b1, Locacion.NORTE);a2m2b4.addAdyacente(a2m2b3, Locacion.ESTE);a2m2b4.addAdyacente(a2m2b6, Locacion.OESTE);
		a2m2b5.addAdyacente(a2m2b6, Locacion.NORTE);a2m2b5.addAdyacente(a2m2b4, Locacion.ESTE);
		a2m2b6.addAdyacente(a2m2b5, Locacion.SUR);a2m2b6.addAdyacente(a2m2b1, Locacion.ESTE);
		a2m2.addSublocacion(a2m2b1);
		a2m2.addSublocacion(a2m2b2);
		a2m2.addSublocacion(a2m2b3);
		a2m2.addSublocacion(a2m2b4);
		a2m2.addSublocacion(a2m2b5);
		a2m2.addSublocacion(a2m2b6);
		esquinas.add(a2m2b1);
		esquinas.add(a2m2b2);
		esquinas.add(a2m2b3);
		esquinas.add(a2m2b4);
		esquinas.add(a2m2b5);
		esquinas.add(a2m2b6);
		
		// Subcuadrante A2M3
		Locacion a2m3 = new Locacion(314,163,"A2M3",a2);
		Esquina a2m3b1 = new Esquina(289,117,"A2M3B1",a2m3);
		Esquina a2m3b2 = new Esquina(323,124,"A2M3B2",a2m3);
		Esquina a2m3b3 = new Esquina(358,135,"A2M3B3",a2m3);
		Esquina a2m3b4 = new Esquina(346,172,"A2M3B4",a2m3);
		Esquina a2m3b5 = new Esquina(314,163,"A2M3B5",a2m3);
		Esquina a2m3b6 = new Esquina(280,154,"A2M3B6",a2m3);
		Esquina a2m3b7 = new Esquina(339,211,"A2M3B7",a2m3);
		Esquina a2m3b8 = new Esquina(304,200,"A2M3B8",a2m3);
		Esquina a2m3b9 = new Esquina(270,192,"A2M3B9",a2m3);
		a2m3b1.addAdyacente(a2m3b2, Locacion.ESTE);a2m3b1.addAdyacente(a2m3b6, Locacion.SUR);
		a2m3b2.addAdyacente(a2m3b1, Locacion.OESTE);a2m3b2.addAdyacente(a2m3b3, Locacion.ESTE);a2m3b2.addAdyacente(a2m3b5, Locacion.SUR);
		a2m3b3.addAdyacente(a2m3b2, Locacion.OESTE);a2m3b3.addAdyacente(a2m3b6, Locacion.SUR);
		a2m3b4.addAdyacente(a2m3b3, Locacion.NORTE);a2m3b4.addAdyacente(a2m3b5, Locacion.OESTE);a2m3b1.addAdyacente(a2m3b7, Locacion.SUR);
		a2m3b5.addAdyacente(a2m3b2, Locacion.NORTE);a2m3b5.addAdyacente(a2m3b4, Locacion.ESTE);a2m3b5.addAdyacente(a2m3b6, Locacion.OESTE);a2m3b5.addAdyacente(a2m3b7, Locacion.SUR);
		a2m3b6.addAdyacente(a2m3b1, Locacion.NORTE);a2m3b6.addAdyacente(a2m3b9, Locacion.SUR);a2m3b6.addAdyacente(a2m3b5, Locacion.ESTE);
		a2m3b7.addAdyacente(a2m3b4, Locacion.NORTE);a2m3b7.addAdyacente(a2m3b8, Locacion.OESTE);
		a2m3b8.addAdyacente(a2m3b7, Locacion.ESTE);a2m3b8.addAdyacente(a2m3b5, Locacion.NORTE);a2m3b8.addAdyacente(a2m3b9, Locacion.OESTE);
		a2m3b9.addAdyacente(a2m3b6, Locacion.NORTE);a2m3b9.addAdyacente(a2m3b8, Locacion.ESTE);
		a2m3.addSublocacion(a2m3b1);
		a2m3.addSublocacion(a2m3b2);
		a2m3.addSublocacion(a2m3b3);
		a2m3.addSublocacion(a2m3b4);		
		a2m3.addSublocacion(a2m3b5);
		a2m3.addSublocacion(a2m3b6);
		a2m3.addSublocacion(a2m3b7);		
		a2m3.addSublocacion(a2m3b8);
		a2m3.addSublocacion(a2m3b9);
		esquinas.add(a2m3b1);
		esquinas.add(a2m3b2);
		esquinas.add(a2m3b3);
		esquinas.add(a2m3b4);
		esquinas.add(a2m3b5);
		esquinas.add(a2m3b6);
		esquinas.add(a2m3b7);
		esquinas.add(a2m3b8);
		esquinas.add(a2m3b9);
		
		// Subcuadrante A2M4
		Locacion a2m4 = new Locacion(381,183,"A2M4",a2);
		Esquina a2m4b1 = new Esquina(389,142,"A2M4B1",a2m4);
		Esquina a2m4b2 = new Esquina(422,151,"A2M4B2",a2m4);
		Esquina a2m4b3 = new Esquina(414,190,"A2M4B3",a2m4);
		Esquina a2m4b4 = new Esquina(381,183,"A2M4B4",a2m4);
		Esquina a2m4b5 = new Esquina(346,172,"A2M4B5",a2m4);
		Esquina a2m4b6 = new Esquina(407,229,"A2M4B6",a2m4);
		Esquina a2m4b7 = new Esquina(373,219,"A2M4B7",a2m4);
		Esquina a2m4b8 = new Esquina(339,211,"A2M4B8",a2m4);
		Esquina a2m4b9 = new Esquina(358,135,"A2M4B9",a2m4);
		a2m4b1.addAdyacente(a2m4b2, Locacion.ESTE);a2m4b1.addAdyacente(a2m4b4, Locacion.SUR);a2m4b1.addAdyacente(a2m4b9, Locacion.OESTE);
		a2m4b2.addAdyacente(a2m4b1, Locacion.OESTE);a2m4b2.addAdyacente(a2m4b3, Locacion.SUR);
		a2m4b3.addAdyacente(a2m4b2, Locacion.NORTE);a2m4b3.addAdyacente(a2m4b4, Locacion.OESTE);a2m4b3.addAdyacente(a2m4b6, Locacion.SUR);
		a2m4b4.addAdyacente(a2m4b1, Locacion.NORTE);a2m4b4.addAdyacente(a2m4b5, Locacion.OESTE);a2m4b4.addAdyacente(a2m4b3, Locacion.ESTE);a2m4b4.addAdyacente(a2m4b7, Locacion.SUR);
		a2m4b5.addAdyacente(a2m4b4, Locacion.ESTE);a2m4b5.addAdyacente(a2m4b8, Locacion.SUR);a2m4b5.addAdyacente(a2m4b9, Locacion.NORTE);
		a2m4b6.addAdyacente(a2m4b3, Locacion.NORTE);a2m4b6.addAdyacente(a2m4b7, Locacion.OESTE);
		a2m4b7.addAdyacente(a2m4b4, Locacion.NORTE);a2m4b7.addAdyacente(a2m4b6, Locacion.ESTE);a2m4b7.addAdyacente(a2m4b8, Locacion.OESTE);
		a2m4b8.addAdyacente(a2m4b5, Locacion.NORTE);a2m4b8.addAdyacente(a2m4b7, Locacion.ESTE);
		a2m4b9.addAdyacente(a2m4b1, Locacion.ESTE);a2m4b9.addAdyacente(a2m4b5, Locacion.SUR);
		a2m4.addSublocacion(a2m4b1);
		a2m4.addSublocacion(a2m4b2);
		a2m4.addSublocacion(a2m4b3);
		a2m4.addSublocacion(a2m4b4);		
		a2m4.addSublocacion(a2m4b5);
		a2m4.addSublocacion(a2m4b6);
		a2m4.addSublocacion(a2m4b7);		
		a2m4.addSublocacion(a2m4b8);
		a2m4.addSublocacion(a2m4b9);
		esquinas.add(a2m4b1);
		esquinas.add(a2m4b2);
		esquinas.add(a2m4b3);
		esquinas.add(a2m4b4);
		esquinas.add(a2m4b5);
		esquinas.add(a2m4b6);
		esquinas.add(a2m4b7);
		esquinas.add(a2m4b8);
		esquinas.add(a2m4b9);
		
		a2m1.addAdyacente(a2m2, Locacion.ESTE);a2m1.addAdyacente(a2m3, Locacion.SUR);
		a2m2.addAdyacente(a2m1, Locacion.OESTE);a2m2.addAdyacente(a2m4,Locacion.SUR);
		a2m3.addAdyacente(a2m1, Locacion.NORTE);a2m3.addAdyacente(a2m4, Locacion.ESTE);
		a2m4.addAdyacente(a2m2, Locacion.NORTE);a2m4.addAdyacente(a2m3, Locacion.OESTE);
		a2.addSublocacion(a2m1);a2.addSublocacion(a2m2);a2.addSublocacion(a2m3);a2.addSublocacion(a2m4);

		// CUADRANTE A3
		Locacion a3 = new Locacion(146,213,"A3",ciudad);
		
		// Subcuadrante A3M1
		Locacion a3m1 = new Locacion(146,213,"A3M1",a3);
		Esquina a3m1b1 = new Esquina(111,161,"A3M1B1",a3m1);
		Esquina a3m1b2 = new Esquina(157,172,"A3M1B2",a3m1);
		Esquina a3m1b3 = new Esquina(180,178,"A3M1B3",a3m1);
		Esquina a3m1b4 = new Esquina(167,218,"A3M1B4",a3m1);
		Esquina a3m1b5 = new Esquina(146,213,"A3M1B5",a3m1);
		Esquina a3m1b6 = new Esquina(102,200,"A3M1B6",a3m1);
		a3m1b1.addAdyacente(a3m1b2, Locacion.ESTE);a3m1b1.addAdyacente(a3m1b6, Locacion.SUR);
		a3m1b2.addAdyacente(a3m1b1, Locacion.OESTE);a3m1b2.addAdyacente(a3m1b3, Locacion.ESTE);a3m1b2.addAdyacente(a3m1b5, Locacion.SUR);
		a3m1b3.addAdyacente(a3m1b2, Locacion.OESTE);a3m1b3.addAdyacente(a3m1b4, Locacion.SUR);
		a3m1b4.addAdyacente(a3m1b3, Locacion.NORTE);a3m1b4.addAdyacente(a3m1b5, Locacion.OESTE);
		a3m1b5.addAdyacente(a3m1b2, Locacion.NORTE);a3m1b5.addAdyacente(a3m1b4, Locacion.ESTE);a3m1b5.addAdyacente(a3m1b6, Locacion.OESTE);
		a3m1b6.addAdyacente(a3m1b1, Locacion.NORTE);a3m1b6.addAdyacente(a3m1b5, Locacion.ESTE);
		a3m1.addSublocacion(a3m1b1);
		a3m1.addSublocacion(a3m1b2);
		a3m1.addSublocacion(a3m1b3);
		a3m1.addSublocacion(a3m1b4);		
		a3m1.addSublocacion(a3m1b5);
		a3m1.addSublocacion(a3m1b6);
		esquinas.add(a3m1b1);
		esquinas.add(a3m1b2);
		esquinas.add(a3m1b3);
		esquinas.add(a3m1b4);
		esquinas.add(a3m1b5);
		esquinas.add(a3m1b6);

		// Subcuadrante A3M2
		Locacion a3m2 = new Locacion(216,231,"A3M2",a3);
		Esquina a3m2b1 = new Esquina(180,178,"A3M2B1",a3m2);
		Esquina a3m2b2 = new Esquina(223,189,"A3M2B2",a3m2);
		Esquina a3m2b3 = new Esquina(248,241,"A3M2B3",a3m2);
		Esquina a3m2b4 = new Esquina(216,231,"A3M2B4",a3m2);
		Esquina a3m2b5 = new Esquina(167,218,"A3M2B5",a3m2);
		a3m2b1.addAdyacente(a3m2b2, Locacion.ESTE);a3m2b1.addAdyacente(a3m2b5, Locacion.SUR);
		a3m2b2.addAdyacente(a3m2b1, Locacion.OESTE);a3m2b2.addAdyacente(a3m2b4, Locacion.SUR);
		a3m2b3.addAdyacente(a3m2b4, Locacion.OESTE);
		a3m2b4.addAdyacente(a3m2b5, Locacion.OESTE);a3m2b4.addAdyacente(a3m2b2, Locacion.NORTE);a3m2b4.addAdyacente(a3m2b3, Locacion.ESTE);
		a3m2b5.addAdyacente(a3m2b1, Locacion.NORTE);a3m2b5.addAdyacente(a3m2b4, Locacion.ESTE);
		a3m2.addSublocacion(a3m2b1);
		a3m2.addSublocacion(a3m2b2);
		a3m2.addSublocacion(a3m2b3);
		a3m2.addSublocacion(a3m2b4);		
		a3m2.addSublocacion(a3m2b5);
		esquinas.add(a3m2b1);
		esquinas.add(a3m2b2);
		esquinas.add(a3m2b3);
		esquinas.add(a3m2b4);
		esquinas.add(a3m2b5);
		
		// Subcuadrante A3M3
		Locacion a3m3 = new Locacion(138,253,"A3M3",a3);
		Esquina a3m3b1 = new Esquina(91,243,"A3M3B1",a3m3);
		Esquina a3m3b2 = new Esquina(138,253,"A3M3B2",a3m3);
		Esquina a3m3b3 = new Esquina(158,259,"A3M3B3",a3m3);
		Esquina a3m3b4 = new Esquina(147,305,"A3M3B4",a3m3);
		Esquina a3m3b5 = new Esquina(123,297,"A3M3B5",a3m3);
		Esquina a3m3b6 = new Esquina(78,285,"A3M3B6",a3m3);
		a3m3b1.addAdyacente(a3m3b2, Locacion.ESTE);a3m3b1.addAdyacente(a3m3b6, Locacion.SUR);
		a3m3b2.addAdyacente(a3m3b1, Locacion.OESTE);a3m3b2.addAdyacente(a3m3b3, Locacion.ESTE);a3m3b2.addAdyacente(a3m3b5, Locacion.SUR);
		a3m3b3.addAdyacente(a3m3b2, Locacion.OESTE);a3m3b3.addAdyacente(a3m3b4, Locacion.SUR);
		a3m3b4.addAdyacente(a3m3b5, Locacion.OESTE);a3m3b4.addAdyacente(a3m3b3, Locacion.NORTE);
		a3m3b5.addAdyacente(a3m3b2, Locacion.NORTE);a3m3b5.addAdyacente(a3m3b4, Locacion.ESTE);a3m3b5.addAdyacente(a3m3b6, Locacion.OESTE);
		a3m3b6.addAdyacente(a3m3b1, Locacion.NORTE);a3m3b6.addAdyacente(a3m3b5, Locacion.ESTE);
		a3m3.addSublocacion(a3m3b1);
		a3m3.addSublocacion(a3m3b2);
		a3m3.addSublocacion(a3m3b3);
		a3m3.addSublocacion(a3m3b4);		
		a3m3.addSublocacion(a3m3b5);
		a3m3.addSublocacion(a3m3b6);
		esquinas.add(a3m3b1);
		esquinas.add(a3m3b2);
		esquinas.add(a3m3b3);
		esquinas.add(a3m3b4);
		esquinas.add(a3m3b5);
		esquinas.add(a3m3b6);
		
		// Subcuadrante A3M4
		Locacion a3m4 = new Locacion(205,272,"A3M4",a3);
		Esquina a3m4b1 = new Esquina(158,259,"A3M4B1",a3m4);
		Esquina a3m4b2 = new Esquina(205,272,"A3M4B2",a3m4);
		Esquina a3m4b3 = new Esquina(238,282,"A3M4B3",a3m4);
		Esquina a3m4b4 = new Esquina(229,325,"A3M4B4",a3m4);
		Esquina a3m4b5 = new Esquina(193,317,"A3M4B5",a3m4);
		Esquina a3m4b6 = new Esquina(147,305,"A3M3B6",a3m4);
		a3m4b1.addAdyacente(a3m4b2, Locacion.ESTE);a3m4b1.addAdyacente(a3m4b6, Locacion.SUR);
		a3m4b2.addAdyacente(a3m4b1, Locacion.OESTE);a3m4b2.addAdyacente(a3m4b3, Locacion.ESTE);a3m4b2.addAdyacente(a3m4b5, Locacion.SUR);
		a3m4b3.addAdyacente(a3m4b2, Locacion.OESTE);a3m4b3.addAdyacente(a3m4b4, Locacion.SUR);
		a3m4b4.addAdyacente(a3m4b5, Locacion.OESTE);a3m4b4.addAdyacente(a3m4b3, Locacion.NORTE);
		a3m4b5.addAdyacente(a3m4b2, Locacion.NORTE);a3m4b5.addAdyacente(a3m4b4, Locacion.ESTE);a3m4b5.addAdyacente(a3m4b6, Locacion.OESTE);
		a3m4b6.addAdyacente(a3m4b1, Locacion.NORTE);a3m4b6.addAdyacente(a3m4b5, Locacion.ESTE);
		a3m4.addSublocacion(a3m4b1);
		a3m4.addSublocacion(a3m4b2);
		a3m4.addSublocacion(a3m4b3);
		a3m4.addSublocacion(a3m4b4);		
		a3m4.addSublocacion(a3m4b5);
		a3m4.addSublocacion(a3m4b6);
		esquinas.add(a3m4b1);
		esquinas.add(a3m4b2);
		esquinas.add(a3m4b3);
		esquinas.add(a3m4b4);
		esquinas.add(a3m4b5);
		esquinas.add(a3m4b6);
		
		a3m1.addAdyacente(a3m2, Locacion.ESTE);a3m1.addAdyacente(a3m3, Locacion.SUR);
		a3m2.addAdyacente(a3m1, Locacion.OESTE);a3m2.addAdyacente(a3m4, Locacion.SUR);
		a3m3.addAdyacente(a3m1, Locacion.NORTE);a3m3.addAdyacente(a3m4, Locacion.ESTE);
		a3m4.addAdyacente(a3m2, Locacion.NORTE);a3m4.addAdyacente(a3m3, Locacion.OESTE);
		a3.addSublocacion(a3m1);a3.addSublocacion(a3m2);a3.addSublocacion(a3m3);a3.addSublocacion(a3m4);
		
		//CUADRANTE A4
		Locacion a4 = new Locacion(297,234,"A4",ciudad);
		
		// Subcuadrante A4M1
		Locacion a4m1 = new Locacion(297,234,"A4M1",a4);
		Esquina a4m1b1 = new Esquina(270,192,"A4M1B1",a4m1);
		Esquina a4m1b2 = new Esquina(304,200,"A4M1B2",a4m1);
		Esquina a4m1b3 = new Esquina(339,211,"A4M1B3",a4m1);
		Esquina a4m1b4 = new Esquina(330,243,"A4M1B4",a4m1);
		Esquina a4m1b5 = new Esquina(297,234,"A4M1B5",a4m1);
		Esquina a4m1b6 = new Esquina(265,227,"A4M1B6",a4m1);
		Esquina a4m1b7 = new Esquina(290,253,"A4M1B7",a4m1);
		Esquina a4m1b8 = new Esquina(248,241,"A4M1B8",a4m1);
		a4m1b1.addAdyacente(a4m1b2, Locacion.ESTE);a4m1b1.addAdyacente(a4m1b8, Locacion.SUR);
		a4m1b2.addAdyacente(a4m1b1, Locacion.OESTE);a4m1b2.addAdyacente(a4m1b3, Locacion.ESTE);a4m1b2.addAdyacente(a4m1b5, Locacion.SUR);
		a4m1b3.addAdyacente(a4m1b2, Locacion.OESTE);a4m1b3.addAdyacente(a4m1b4, Locacion.SUR);
		a4m1b4.addAdyacente(a4m1b3, Locacion.NORTE);a4m1b4.addAdyacente(a4m1b5, Locacion.OESTE);
		a4m1b5.addAdyacente(a4m1b2, Locacion.NORTE);a4m1b5.addAdyacente(a4m1b4, Locacion.OESTE);a4m1b5.addAdyacente(a4m1b6, Locacion.ESTE);a4m1b5.addAdyacente(a4m1b7, Locacion.SUR);
		a4m1b6.addAdyacente(a4m1b1, Locacion.NORTE);a4m1b6.addAdyacente(a4m1b5, Locacion.ESTE);a4m1b6.addAdyacente(a4m1b8, Locacion.SUR);
		a4m1b7.addAdyacente(a4m1b5, Locacion.NORTE);a4m1b7.addAdyacente(a4m1b8, Locacion.OESTE);
		a4m1b8.addAdyacente(a4m1b6, Locacion.NORTE);a4m1b8.addAdyacente(a4m1b7, Locacion.ESTE);
		a4m1.addSublocacion(a4m1b1);
		a4m1.addSublocacion(a4m1b2);
		a4m1.addSublocacion(a4m1b3);
		a4m1.addSublocacion(a4m1b4);		
		a4m1.addSublocacion(a4m1b5);
		a4m1.addSublocacion(a4m1b6);
		a4m1.addSublocacion(a4m1b7);
		a4m1.addSublocacion(a4m1b8);
		esquinas.add(a4m1b1);
		esquinas.add(a4m1b2);
		esquinas.add(a4m1b3);
		esquinas.add(a4m1b4);
		esquinas.add(a4m1b5);
		esquinas.add(a4m1b6);
		esquinas.add(a4m1b7);
		esquinas.add(a4m1b8);
		
		// Subcuadrante A4M2
		Locacion a4m2 = new Locacion(364,253,"A4M2",a4);
		Esquina a4m2b1 = new Esquina(339,211,"A4M2B1",a4m2);
		Esquina a4m2b2 = new Esquina(373,219,"A4M2B2",a4m2);
		Esquina a4m2b3 = new Esquina(407,229,"A4M2B3",a4m2);
		Esquina a4m2b4 = new Esquina(399,259,"A4M2B4",a4m2);
		Esquina a4m2b5 = new Esquina(364,253,"A4M2B5",a4m2);
		Esquina a4m2b6 = new Esquina(330,243,"A4M2B6",a4m2);		
		a4m2b1.addAdyacente(a4m2b2, Locacion.ESTE);a4m2b1.addAdyacente(a4m2b6, Locacion.SUR);
		a4m2b2.addAdyacente(a4m2b1, Locacion.OESTE);a4m2b2.addAdyacente(a4m2b3, Locacion.ESTE);a4m2b2.addAdyacente(a4m2b5, Locacion.SUR);
		a4m2b3.addAdyacente(a4m2b2, Locacion.OESTE);a4m2b3.addAdyacente(a4m2b4, Locacion.SUR);
		a4m2b4.addAdyacente(a4m2b3, Locacion.NORTE);a4m2b4.addAdyacente(a4m2b5, Locacion.OESTE);
		a4m2b5.addAdyacente(a4m2b2, Locacion.NORTE);a4m2b5.addAdyacente(a4m2b4, Locacion.ESTE);a4m2b5.addAdyacente(a4m2b6, Locacion.OESTE);
		a4m2b6.addAdyacente(a4m2b1, Locacion.NORTE);a4m2b6.addAdyacente(a4m2b5, Locacion.ESTE);
		a4m2.addSublocacion(a4m2b1);
		a4m2.addSublocacion(a4m2b2);
		a4m2.addSublocacion(a4m2b3);
		a4m2.addSublocacion(a4m2b4);		
		a4m2.addSublocacion(a4m2b5);
		a4m2.addSublocacion(a4m2b6);
		esquinas.add(a4m2b1);
		esquinas.add(a4m2b2);
		esquinas.add(a4m2b3);
		esquinas.add(a4m2b4);
		esquinas.add(a4m2b5);
		esquinas.add(a4m2b6);
		
		// Subcuadrante A4M3
		Locacion a4m3 = new Locacion(283,292,"A4M3",a4);
		Esquina a4m3b1 = new Esquina(238,282,"A4M3B1",a4m3);
		Esquina a4m3b2 = new Esquina(262,286,"A4M3B2",a4m3);
		Esquina a4m3b3 = new Esquina(283,292,"A4M3B3",a4m3);
		Esquina a4m3b4 = new Esquina(288,269,"A4M3B4",a4m3);
		Esquina a4m3b5 = new Esquina(320,280,"A4M3B5",a4m3);
		Esquina a4m3b6 = new Esquina(314,301,"A4M3B6",a4m3);
		Esquina a4m3b7 = new Esquina(307,336,"A4M3B7",a4m3);
		Esquina a4m3b8 = new Esquina(306,347,"A4M3B8",a4m3);
		Esquina a4m3b9 = new Esquina(270,337,"A4M3B9",a4m3);
		Esquina a4m3b10 = new Esquina(229,325,"A4M3B10",a4m3);
		a4m3b1.addAdyacente(a4m3b2, Locacion.ESTE);a4m3b1.addAdyacente(a4m3b10, Locacion.SUR);
		a4m3b2.addAdyacente(a4m3b1, Locacion.OESTE);a4m3b2.addAdyacente(a4m3b3, Locacion.ESTE);a4m3b2.addAdyacente(a4m3b9, Locacion.SURESTE);
		a4m3b3.addAdyacente(a4m3b4, Locacion.NORTE);a4m3b3.addAdyacente(a4m3b6, Locacion.ESTE);a4m3b3.addAdyacente(a4m3b2, Locacion.OESTE);a4m3b3.addAdyacente(a4m3b9, Locacion.SUR);a4m3b3.addAdyacente(a4m3b7, Locacion.SURESTE);
		a4m3b4.addAdyacente(a4m3b5, Locacion.ESTE);a4m3b4.addAdyacente(a4m3b3, Locacion.SUR);
		a4m3b5.addAdyacente(a4m3b4, Locacion.OESTE);a4m3b5.addAdyacente(a4m3b6, Locacion.SUR);
		a4m3b6.addAdyacente(a4m3b3, Locacion.OESTE);a4m3b6.addAdyacente(a4m3b5, Locacion.NORTE);a4m3b6.addAdyacente(a4m3b7, Locacion.SUR);
		a4m3b7.addAdyacente(a4m3b6, Locacion.NORTE);a4m3b7.addAdyacente(a4m3b8, Locacion.SUR);a4m3b7.addAdyacente(a4m3b3, Locacion.NOROESTE);
		a4m3b8.addAdyacente(a4m3b7, Locacion.NORTE);a4m3b8.addAdyacente(a4m3b9, Locacion.OESTE);
		a4m3b9.addAdyacente(a4m3b3, Locacion.NORTE);a4m3b9.addAdyacente(a4m3b8, Locacion.ESTE);a4m3b9.addAdyacente(a4m3b2, Locacion.NOROESTE);a4m3b9.addAdyacente(a4m3b10, Locacion.OESTE);
		a4m3b10.addAdyacente(a4m3b1, Locacion.NORTE);a4m3b10.addAdyacente(a4m3b9, Locacion.ESTE);
		a4m3.addSublocacion(a4m3b1);
		a4m3.addSublocacion(a4m3b2);
		a4m3.addSublocacion(a4m3b3);
		a4m3.addSublocacion(a4m3b4);		
		a4m3.addSublocacion(a4m3b5);
		a4m3.addSublocacion(a4m3b6);
		a4m3.addSublocacion(a4m3b7);
		a4m3.addSublocacion(a4m3b8);		
		a4m3.addSublocacion(a4m3b9);
		a4m3.addSublocacion(a4m3b10);
		esquinas.add(a4m3b1);
		esquinas.add(a4m3b2);
		esquinas.add(a4m3b3);
		esquinas.add(a4m3b4);
		esquinas.add(a4m3b5);
		esquinas.add(a4m3b6);
		esquinas.add(a4m3b7);
		esquinas.add(a4m3b8);
		esquinas.add(a4m3b9);
		esquinas.add(a4m3b10);
		
		// Subcuadrante A4M4
		Locacion a4m4 = new Locacion(347,325,"A4M4",a4);
		Esquina a4m4b1 = new Esquina(320,280,"A4M4B1",a4m4);
		Esquina a4m4b2 = new Esquina(355,287,"A4M4B2",a4m4);
		Esquina a4m4b3 = new Esquina(390,297,"A4M4B3",a4m4);
		Esquina a4m4b4 = new Esquina(380,327,"A4M4B4",a4m4);
		Esquina a4m4b5 = new Esquina(347,325,"A4M4B5",a4m4);
		Esquina a4m4b6 = new Esquina(315,314,"A4M4B6",a4m4);
		Esquina a4m4b7 = new Esquina(314,301,"A4M3B7",a4m4);
		Esquina a4m4b8 = new Esquina(307,336,"A4M4B8",a4m4);
		Esquina a4m4b9 = new Esquina(306,347,"A4M4B9",a4m4);;
		Esquina a4m4b10 = new Esquina(321,351,"A4M4B10",a4m4);
		Esquina a4m4b11 = new Esquina(339,355,"A4M4B11",a4m4);
		Esquina a4m4b12 = new Esquina(371,364,"A4M4B12",a4m4);
		a4m4b1.addAdyacente(a4m4b2, Locacion.ESTE);a4m4b1.addAdyacente(a4m4b7, Locacion.SUR);
		a4m4b2.addAdyacente(a4m4b1, Locacion.OESTE);a4m4b2.addAdyacente(a4m4b3, Locacion.ESTE);a4m4b2.addAdyacente(a4m4b5, Locacion.SUR);
		a4m4b3.addAdyacente(a4m4b2, Locacion.OESTE);a4m4b3.addAdyacente(a4m4b4, Locacion.SUR);
		a4m4b4.addAdyacente(a4m4b3, Locacion.NORTE);a4m4b4.addAdyacente(a4m4b5, Locacion.OESTE);a4m4b4.addAdyacente(a4m4b12, Locacion.SUR);
		a4m4b5.addAdyacente(a4m4b2, Locacion.NORTE);a4m4b5.addAdyacente(a4m4b4, Locacion.ESTE);a4m4b5.addAdyacente(a4m4b11, Locacion.SUR);a4m4b5.addAdyacente(a4m4b6, Locacion.OESTE);
		a4m4b6.addAdyacente(a4m4b7, Locacion.NORTE);a4m4b6.addAdyacente(a4m4b8, Locacion.SUR);a4m4b6.addAdyacente(a4m4b5, Locacion.ESTE);
		a4m4b7.addAdyacente(a4m4b1, Locacion.NORTE);a4m4b7.addAdyacente(a4m4b6, Locacion.SUR);
		a4m4b8.addAdyacente(a4m4b6, Locacion.NORTE);a4m4b8.addAdyacente(a4m4b9, Locacion.SUR);a4m4b8.addAdyacente(a4m4b10, Locacion.SURESTE);
		a4m4b9.addAdyacente(a4m4b8, Locacion.NORTE);a4m4b9.addAdyacente(a4m4b10, Locacion.ESTE);
		a4m4b10.addAdyacente(a4m4b8, Locacion.NOROESTE);a4m4b10.addAdyacente(a4m4b11, Locacion.ESTE);a4m4b10.addAdyacente(a4m4b9, Locacion.OESTE);
		a4m4b11.addAdyacente(a4m4b5, Locacion.NORTE);a4m4b11.addAdyacente(a4m4b12, Locacion.ESTE);a4m4b11.addAdyacente(a4m4b10, Locacion.OESTE);
		a4m4b12.addAdyacente(a4m4b4, Locacion.NORTE);a4m4b12.addAdyacente(a4m4b11, Locacion.OESTE);
		a4m4.addSublocacion(a4m4b1);
		a4m4.addSublocacion(a4m4b2);
		a4m4.addSublocacion(a4m4b3);
		a4m4.addSublocacion(a4m4b4);		
		a4m4.addSublocacion(a4m4b5);
		a4m4.addSublocacion(a4m4b6);
		a4m4.addSublocacion(a4m4b7);
		a4m4.addSublocacion(a4m4b8);		
		a4m4.addSublocacion(a4m4b9);
		a4m4.addSublocacion(a4m4b10);
		a4m4.addSublocacion(a4m4b11);
		a4m4.addSublocacion(a4m4b12);
		esquinas.add(a4m4b1);
		esquinas.add(a4m4b2);
		esquinas.add(a4m4b3);
		esquinas.add(a4m4b4);
		esquinas.add(a4m4b5);
		esquinas.add(a4m4b6);
		esquinas.add(a4m4b7);
		esquinas.add(a4m4b8);
		esquinas.add(a4m4b9);
		esquinas.add(a4m4b10);
		esquinas.add(a4m4b11);
		esquinas.add(a4m4b12);
		
		a4m1.addAdyacente(a4m2, Locacion.ESTE);a4m1.addAdyacente(a4m3, Locacion.SUR);
		a4m2.addAdyacente(a4m1, Locacion.OESTE);a4m2.addAdyacente(a4m4, Locacion.SUR);
		a4m3.addAdyacente(a4m1, Locacion.NORTE);a4m3.addAdyacente(a4m4, Locacion.ESTE);
		a4m4.addAdyacente(a4m2, Locacion.NORTE);a4m4.addAdyacente(a4m3, Locacion.OESTE);
		a4.addSublocacion(a4m1);a4.addSublocacion(a4m2);a4.addSublocacion(a4m3);a4.addSublocacion(a4m4);
		
		a1.addAdyacente(a2, Locacion.ESTE);a1.addAdyacente(a3, Locacion.SUR);
		a2.addAdyacente(a1, Locacion.OESTE);a2.addAdyacente(a4, Locacion.SUR);
		a3.addAdyacente(a1, Locacion.NORTE);a3.addAdyacente(a4, Locacion.ESTE);
		a4.addAdyacente(a2, Locacion.NORTE);a4.addAdyacente(a3, Locacion.OESTE);
		
		ciudad.addSublocacion(a1);
		ciudad.addSublocacion(a2);
		ciudad.addSublocacion(a3);
		ciudad.addSublocacion(a4);
		
		return ciudad;
	}
	
	public static Locacion getCiudad(){
		return ciudad;
	}
	
	public static Locacion getCiudadVacia(){
		return ciudadVacia;
	}
	
}
