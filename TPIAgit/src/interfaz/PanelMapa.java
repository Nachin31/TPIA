package interfaz;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import excepciones.CoordenadasSinEsquinaException;
import logica.Esquina;
import logica.Locacion;

/**Esta clase maneja el panel que muestra el mapa
 * 
 * @author Nacho G�mez
 *
 */
public class PanelMapa extends JPanel {
	private static final long serialVersionUID = 3275005094743419907L;
	private List<Esquina> esquinas;
	private int diametroEsquina;
	private List<Polygon> cuadrantes= new ArrayList<Polygon>();
	private Polygon cuadranteAdibujar = null;
	private List<Polygon> subcuadrantes= new ArrayList<Polygon>();
	private Polygon subcuadranteAdibujar = null;
	private Polygon plg=null;//Enmarca la ciudad
	private Polygon droneLocation=null;
	private int x;
	private int y;
	
	//TODO Le paso la altura; si es 1, busca en la lista de cuadrantes, si es 2 en la de 
	//Sublocaciones; si es 3 en esquina.
	//Se usan los m�todos ya exitentes para graficar, solo q sin depender del mouseover.
	//Manejar variable "insimulation" que indicar� cu�l de las dos formas de graficar usamos.
	
	
	public PanelMapa(List<Esquina> esqs,int diam){
		diametroEsquina = diam;
		esquinas = esqs;
		crearCuadrantesGraficos();
		crearSubcuadrantesGraficos();
		
		//Enmarca la ciudad
		plg = new Polygon();
		plg.addPoint(145,2);
		plg.addPoint(458,79);
		plg.addPoint(386,375);
		plg.addPoint(70,293);
		
		this.addMouseMotionListener(new MouseMotionListener(){
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void mouseMoved(MouseEvent e) {
				x=e.getX();
				y=e.getY();
				for(Polygon p : cuadrantes){
					if(p.contains(x,y)){
						cuadranteAdibujar= p;
					}
					//Para q se limpie el subcuadrante al cambiar a otro cuadrante
					subcuadranteAdibujar = null;
				}
				for(Polygon s : subcuadrantes)
					if(s.contains(x,y)){
						subcuadranteAdibujar = s;
					}
				if(!plg.contains(x,y)){
					cuadranteAdibujar =null;
					subcuadranteAdibujar = null;
				}
				repaint();
			}
		});
	}
	
	public void paint (Graphics g){
		super.paint(g);
		Dimension tamanio = getSize();
		setOpaque(false);
		
		//Dibujamos el mapa
		ImageIcon imagenFondo = new ImageIcon(getClass().getResource("/imagenes/Mapatotal.png"));
		g.drawImage(imagenFondo.getImage(),0,0,tamanio.width,tamanio.height, null);
		
		//Delimitamos el �rea donde pueden editarse las esquinas
		g.setColor(Color.RED);
		((Graphics2D) g).setStroke(new BasicStroke(2));
		
		if(plg!=null)
			((Graphics2D)g).draw(plg);
		
		if(cuadranteAdibujar != null){
			((Graphics2D)g).draw(cuadranteAdibujar);
		}
		if(subcuadranteAdibujar != null){
			((Graphics2D)g).draw(subcuadranteAdibujar);
		}
		
		//Se grafican las esquinas para ver si estan bien ubicadas
		for(Esquina e : esquinas){
			if(e.getCantidadPersonas()==0)
				g.setColor(Color.BLUE);
			else if(e.hayCriminal())
				g.setColor(Color.RED);
			else
				g.setColor(Color.GREEN);
			g.fillOval(e.getX(),e.getY(),diametroEsquina,diametroEsquina);
			
			try {
				//Si el mouse esta sobre una esquina, el m�todo devolver� esa esquina
				Esquina bajoMouse = getEsquina(x,y);
				g.setColor(Color.ORANGE);
				g.fillOval(bajoMouse.getX(),bajoMouse.getY(),diametroEsquina,diametroEsquina);
			} catch (CoordenadasSinEsquinaException e1) {
				//De lo contrario, tirar� esta excepci�n, y debemos hacer nada
			}
			
			if(droneLocation != null){
				g.setColor(new Color(132,255,9));
				((Graphics2D)g).draw(droneLocation);
				
			}
				

		}
			
	}

	public Esquina getEsquina(int x,int y) throws CoordenadasSinEsquinaException{
		Esquina esq = null;
		for(Esquina e : esquinas){
			if(x > e.getX() && x < (e.getX()+diametroEsquina) && y > e.getY() && y < (e.getY()+diametroEsquina)){
				esq = e;
				break;
			}
		}
		if(esq == null)
			throw new CoordenadasSinEsquinaException();
		return esq;
	}
	
	private void crearCuadrantesGraficos(){
		Polygon poly = new Polygon();
		poly.addPoint(145,2);//p1
		poly.addPoint(301,40); //p2
		poly.addPoint(264,193);//p3
		poly.addPoint(107,152);//p4
		cuadrantes.add(poly);
		
		poly = new Polygon();
		poly.addPoint(301,40);//p1
		poly.addPoint(458,79);//p2
		poly.addPoint(420,233);//p3
		poly.addPoint(264,193);//p4
		cuadrantes.add(poly);

		poly = new Polygon();
		poly.addPoint(264,193);//p1
		poly.addPoint(420,233);//p2
		poly.addPoint(386,375);//p3
		poly.addPoint(227,334);//p4
		cuadrantes.add(poly);

		poly = new Polygon();
		poly.addPoint(107,152);//p1
		poly.addPoint(264,193);//p2
		poly.addPoint(227,334);//p3
		poly.addPoint(70,293);//p4
		cuadrantes.add(poly);
	}
	
	private void crearSubcuadrantesGraficos(){
		//Subccuadrantes del Cuadrante A1
		Polygon poly = new Polygon();
		poly.addPoint(145,2); //p1
		poly.addPoint(221,20); //p2
		poly.addPoint(204,89); //p3
		poly.addPoint(128,70); //p4
		subcuadrantes.add(poly);
		
		poly = new Polygon();
		poly.addPoint(221,20); //p1
		poly.addPoint(301,40); //p2
		poly.addPoint(284,109); //p3
		poly.addPoint(204,89); //p4
		subcuadrantes.add(poly);
		
		poly = new Polygon();
		poly.addPoint(204,89); //p1
		poly.addPoint(284,109); //p2
		poly.addPoint(264,193); //p3
		poly.addPoint(183,172); //p4
		subcuadrantes.add(poly);
		
		poly = new Polygon();
		poly.addPoint(128,70); //p1
		poly.addPoint(204,89); //p2
		poly.addPoint(183,172); //p3
		poly.addPoint(107,152); //p4
		subcuadrantes.add(poly);
		
		//Subccuadrantes del Cuadrante A2
		poly = new Polygon();
		poly.addPoint(301,40); //p1
		poly.addPoint(376,58); //p2
		poly.addPoint(359,128); //p3
		poly.addPoint(285,109); //p4
		subcuadrantes.add(poly);
		
		poly = new Polygon();
		poly.addPoint(376,58); //p1
		poly.addPoint(458,79); //p2
		poly.addPoint(440,149); //p3
		poly.addPoint(359,128); //p4
		subcuadrantes.add(poly);
		
		poly = new Polygon();
		poly.addPoint(359,128); //p1
		poly.addPoint(440,149); //p2
		poly.addPoint(420,233); //p3
		poly.addPoint(339,212); //p4
		subcuadrantes.add(poly);
		
		poly = new Polygon();
		poly.addPoint(285,109); //p1
		poly.addPoint(359,128); //p2
		poly.addPoint(339,212); //p3
		poly.addPoint(264,193); //p4
		subcuadrantes.add(poly);
		
		//Subccuadrantes del Cuadrante A3
		poly = new Polygon();
		poly.addPoint(107,152);//p1
		poly.addPoint(183,172); //p2
		poly.addPoint(168,231);//p3
		poly.addPoint(92,211);//p4
		subcuadrantes.add(poly);
		
		poly = new Polygon();
		poly.addPoint(183,172);//p1
		poly.addPoint(264,193); //p2
		poly.addPoint(249,252);//p3
		poly.addPoint(168,231);//p4
		subcuadrantes.add(poly);
		
		poly = new Polygon();
		poly.addPoint(92,211);//p1
		poly.addPoint(168,231); //p2
		poly.addPoint(148,313);//p3
		poly.addPoint(70,293);//p4
		subcuadrantes.add(poly);
		
		poly = new Polygon();
		poly.addPoint(168,231);//p1
		poly.addPoint(249,252);//p2
		poly.addPoint(227,334);//p3
		poly.addPoint(148,313);//p4
		subcuadrantes.add(poly);
		
		//Subccuadrantes del Cuadrante A4
		poly = new Polygon();
		poly.addPoint(264,193);//p1
		poly.addPoint(339,212);//p2
		poly.addPoint(324,272);//p3
		poly.addPoint(249,252);//p4
		subcuadrantes.add(poly);
		
		poly = new Polygon();
		poly.addPoint(339,212);//p1
		poly.addPoint(420,233);//p2
		poly.addPoint(406,293);//p3
		poly.addPoint(324,272);//p4
		subcuadrantes.add(poly);
		
		poly = new Polygon();
		poly.addPoint(249,252);//p1
		poly.addPoint(324,272);//p2
		poly.addPoint(306,354);//p3
		poly.addPoint(227,334);//p4
		subcuadrantes.add(poly);
		
		poly = new Polygon();
		poly.addPoint(324,272);//p1
		poly.addPoint(406,293);//p2
		poly.addPoint(386,375);//p3
		poly.addPoint(306,354);//p4
		subcuadrantes.add(poly);
		
	}
	
	public void highlightDroneLocation(Locacion locacion){
		if(locacion.calcularAltura() == 1)
			for(Polygon p : cuadrantes){
				if(p.contains(locacion.getCentro().getX(),locacion.getCentro().getY())){
					droneLocation = p;
				}
				//Para q se limpie el subcuadrante al cambiar a otro cuadrante
				subcuadranteAdibujar = null;
			}
		else if(locacion.calcularAltura() == 2)
			for(Polygon s : subcuadrantes){
				if(s.contains(locacion.getCentro().getX(),locacion.getCentro().getY())){
					droneLocation = s;
				}
			}
		else{
			droneLocation = new Polygon();
			droneLocation.addPoint((int)(locacion.getCentro().getX() - 5),(int)(locacion.getCentro().getY() - 5));
			droneLocation.addPoint((int)(locacion.getCentro().getX() + diametroEsquina + 5),(int)(locacion.getCentro().getY() - 5));
			droneLocation.addPoint((int)(locacion.getCentro().getX() + diametroEsquina + 5),(int)(locacion.getCentro().getY() + diametroEsquina + 5));
			droneLocation.addPoint((int)(locacion.getCentro().getX() - 5),(int)(locacion.getCentro().getY() + diametroEsquina + 5));
		}
			
					
	}
	
}