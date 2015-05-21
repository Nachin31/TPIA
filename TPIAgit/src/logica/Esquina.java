package logica;

import java.awt.Point;

public class Esquina extends Locacion{

	private boolean hayCriminal;
	private boolean visitada;
		
	public Esquina(){
		super();
		hayCriminal = false;
		visitada = false;
	}
	
	public Esquina(int x,int y){
		super();
		centro = new Point(x,y);
		hayCriminal = false;
		visitada = false;
	}
	
	public Esquina(int x,int y,String name){
		super();
		centro = new Point(x,y);
		hayCriminal = false;
		visitada = false;
		nombre = name;
	}
	
	public Esquina(int x,int y,int cantPs,boolean criminal){
		super();
		centro = new Point(x,y);
		hayCriminal = criminal;
		valorSenial = cantPs;
		visitada = false;
	}
	
	public void setVisitada(boolean valor){
		visitada = valor;
	}
	
	public boolean getVisitada(){
		return visitada;
	}
	
	public int getCantidadPersonas(){
		return valorSenial;
	}
	
	public int getX(){
		return (int)centro.getX();
	}
	
	public int getY(){
		return (int)centro.getY();
	}
	
	public boolean hayCriminal(){
		return hayCriminal;
	}
	
	public void setCantidadPersonas(int val){
		valorSenial = val;
	}
	
	public void setCriminal(boolean val){
		hayCriminal = val;
	}
	
	public boolean hayVictimario(int direccion){
		if(adyacentes[direccion] == null)
			return false;
		else
			return ((Esquina)adyacentes[direccion]).hayVictimarioR(direccion);
	}
	
	private boolean hayVictimarioR(int direccion){
		if(adyacentes[direccion] == null)
			return hayCriminal();
		else
			return hayCriminal() || ((Esquina) adyacentes[direccion]).hayVictimarioR(direccion);
	}
	
	public int cantEsquinasDireccion(int direccion){
		int cant=0;
		if(adyacentes[direccion]==null)
			return 0;
		else
			cant += 1 +((Esquina)adyacentes[direccion]).cantEsquinasDireccion(direccion);
		return cant;
	}
	
	public void actualizarEsquina(int x,int y,Integer cantPersonas,Boolean hayMalechor){
		if(getX() == x && getY() == y){
			this.setCantidadPersonas(cantPersonas);
			this.setCriminal(hayMalechor);
		}
	}
	
	public Esquina clone(Locacion parent){
		Esquina esq = new Esquina();
		for(Locacion l : parent.getSublocaciones()){
			for(int i = 0; i<8; i++){
				if(adyacentes[i]!= null && l.getCentro().getX() == adyacentes[i].getCentro().getX()
						&& l.getCentro().getY() == adyacentes[i].getCentro().getY())
					esq.addAdyacente(l,i);
			}
		}
		esq.setVisitada(getVisitada());
		esq.setCentro((int) getCentro().getX(),(int) getCentro().getY());
		esq.setNombre(getNombre());
		esq.setCantidadPersonas(getCantidadPersonas());
		esq.setCriminal(hayCriminal());

		return esq;
	}
	public boolean equals(Object obj){// 2 esquinas son iguales si tiene el mismo nombre, el mismo nombre de padre, la misma se�al y el mismo valor de hayVictimario
		if(obj instanceof Esquina){
			boolean esigualLocacion = super.equals(obj);
			Esquina aux = (Esquina) obj;
			esigualLocacion = esigualLocacion && aux.hayCriminal() == hayCriminal;
			esigualLocacion = esigualLocacion && aux.getVisitada() == visitada;
			return esigualLocacion;
		}
		else
			return false;
	}
	
}
