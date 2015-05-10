package logica;

import java.awt.Point;

public class Esquina {

	private Point coordenadas;
	private int cantPersonas;
	private boolean hayCriminal;
	
	public Esquina(){
		coordenadas = new Point();
		hayCriminal = false;
		cantPersonas = 0;
	}
	
	public Esquina(int x,int y){
		coordenadas = new Point(x,y);
		hayCriminal = false;
		cantPersonas = 0;
	}
	
	public Esquina(int x,int y,int cantPs,boolean criminal){
		coordenadas = new Point(x,y);
		hayCriminal = criminal;
		cantPersonas = cantPs;
	}
	
	public int getCantidadPersonas(){
		return cantPersonas;
	}
	
	public int getX(){
		return (int)coordenadas.getX();
	}
	
	public int getY(){
		return (int)coordenadas.getY();
	}
	
	public boolean hayCriminal(){
		return hayCriminal;
	}
	
	public void setCantidadPersonas(int val){
		cantPersonas = val;
	}
	
	public void setCriminal(boolean val){
		hayCriminal = val;
	}
	
	
}
