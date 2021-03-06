package interfaz;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ProgressBar extends JPanel {
	private static final long serialVersionUID = 7466187301695688070L;

	private float maxValue;
	private float actualValue;
	private String unit;
	private JLabel label;
	
	public ProgressBar(){
		maxValue = 100;
		actualValue = 100;
		unit = "Porcentaje";
		initComponents();
	}
	
	public ProgressBar(float max,float act,String unidad){
		maxValue = max;
		actualValue = act;
		unit = unidad;
		initComponents();
	}
	
	private void initComponents(){
		setLayout(null);
		label = new JLabel();
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Calibri", Font.BOLD, 13));
		label.setBounds(224, 5, 91, 18);
		this.add(label);
	}

	public void paintComponent (Graphics g){
		Dimension tamanio = getSize();
		ImageIcon imagenFondo = new ImageIcon(getClass().getResource("/imagenes/progress-bar.png"));
		g.drawImage(imagenFondo.getImage(),0,0,tamanio.width,tamanio.height, null);
		
		//La cantidad de energia para poder graficarla
		float proporcionGastada = (maxValue - actualValue)/maxValue;
		float proporcionRemanente = actualValue/maxValue;
		float proporcionBarraGastada = proporcionGastada*tamanio.width;
		
		if(unit.equals("Porcentaje"))
			label.setText(""+String.format("%.2f",proporcionRemanente*100)+"%");
		else
			label.setText(""+(int)actualValue);
		
		this.setBackground(Color.black);
		g.fillRect(tamanio.width-((int)proporcionBarraGastada),0,((int)proporcionBarraGastada),tamanio.height);
		setOpaque(false);
		super.paintComponent(g);
	}

	public void changeUnit(){
		if(unit.equals("Porcentaje"))
			unit = "Unidades";
		else
			unit = "Porcentaje";
		repaint();
	}
	
	public void updateActualValue(int newVal){
		actualValue = newVal;
		repaint();
	}
	
}
