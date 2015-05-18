package interfaz;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import excepciones.CoordenadasSinEsquinaException;
import logica.Esquina;

/**Esta clase maneja el panel que muestra el mapa
 * 
 * @author Nacho G�mez
 *
 */
public class PanelMapa extends JPanel {
	private static final long serialVersionUID = 3275005094743419907L;
	private List<Esquina> esquinas;
	private int diametroEsquina;
	
	public PanelMapa(List<Esquina> esqs,int diam){
		diametroEsquina = diam;
		esquinas = esqs;
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
		Polygon plg = new Polygon();
		plg.addPoint(145,2);
		plg.addPoint(458,79);
		plg.addPoint(386,375);
		plg.addPoint(70,293);
		((Graphics2D)g).draw(plg);
		
		//Se grafican las esquinas para ver si estan bien ubicadas
		for(Esquina e : esquinas){
			if(e.getCantidadPersonas()==0)
				g.setColor(Color.BLUE);
			else if(e.hayCriminal())
				g.setColor(Color.RED);
			else
				g.setColor(Color.GREEN);
			g.fillOval(e.getX(),e.getY(),diametroEsquina,diametroEsquina);
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
	
}