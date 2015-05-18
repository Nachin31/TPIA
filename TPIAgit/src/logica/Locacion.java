package logica;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Locacion {
	
	protected Locacion padre;
	protected List<Locacion> sublocaciones;
	protected Locacion[] adyacentes;
	protected Point centro;
	protected int valorSenial;
	protected String nombre;
	public final static int NORTE = 0;
	public final static int NORESTE = 1;
	public final static int ESTE = 2;
	public final static int SURESTE = 3;
	public final static int SUR = 4;
	public final static int SUROESTE = 5;
	public final static int OESTE = 6;
	public final static int NOROESTE = 7;
	
	public Locacion(){
		sublocaciones = new ArrayList<Locacion>();
		adyacentes = new Locacion[8];
		centro = new Point();
		valorSenial = 0;
		nombre = "";
	}
	
	public Locacion(String nomb){
		sublocaciones = new ArrayList<Locacion>();
		adyacentes = new Locacion[8];
		centro = new Point();
		valorSenial = 0;
		nombre = nomb;
	}
	
	public void addSublocacion(Locacion l){
		l.setPadre(this);
		sublocaciones.add(l);
	}
	
	public void addAdyacente(Locacion l, int direccion){
		if(direccion >= 0 && direccion <= 7)
			adyacentes[direccion]= l;
		//else tirar excepcion
	}
	
	public Locacion getAdyacente(int dir){
		return adyacentes[dir];
	}
	
	public List<Locacion> getAdyacentes(){
		List<Locacion> locaciones = new ArrayList<Locacion>();
		for(Locacion l: adyacentes)
			if(l != null)
				locaciones.add(l);
		return locaciones;
	}
	
	public void actualizarLocacion(int x,int y,Integer cantPersonas,Boolean hayMalechor){//TODO completar
		//Busca todas las esquina dentro de s� que poseen el dado x,y (en caso de pertenecer a 2 cuadrantes)
		//y las actualiza con la cant de personas y el boolean dados; en caso de solo actualizar
		//una de ellas, la otra ser� null.
	}
	
	public void setPadre(Locacion lp){
		padre = lp;
	}
	
	public Locacion getPadre(){
		return padre;
	}
	
	public boolean getVisitada(){
		boolean allVisited = true;
		for(Locacion l : getSublocaciones())
			allVisited = allVisited && l.getVisitada();
		return allVisited;
	}
	
	public void setVisitada(boolean val){
		for(Locacion l : getSublocaciones())
			l.setVisitada(val);
	}
	
	public int calcularAltura(){
		if(padre == null)
			return 0;
		else
			return 1 + padre.calcularAltura();
	}
	
	public Point getCentro(){
		return centro;
	}
	
	public void setCentro(int x,int y){
		centro.setLocation(x,y);
	}
	
	public List<Locacion> getSublocaciones(){
		return sublocaciones;
	}
	
	public int getSenial(){
		return valorSenial;
	}
	
	public void setSenial(int val){
		valorSenial = val;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Locacion clone(Locacion parent){
		Locacion locacion = new Locacion();
		for(Locacion l : sublocaciones){
			locacion.addSublocacion(l.clone(this));
		}
		locacion.setPadre(parent);
		if(parent!=null)
			for(Locacion l : parent.getSublocaciones()){
				for(int i = 0; i<8; i++){
					if(adyacentes[i]!= null && l.getCentro().getX() == adyacentes[i].getCentro().getX()
							&& l.getCentro().getY() == adyacentes[i].getCentro().getY())
						locacion.addAdyacente(l,i);
				}
			}
		locacion.setCentro((int) getCentro().getX(),(int) getCentro().getY());
		locacion.setSenial(getSenial());
		locacion.setNombre(getNombre());
		return locacion;
	}
	
	public Locacion buscarLocacion(int x, int y, int xp, int yp, int zp){
		Locacion a= null;
		a=busLocAux(x, y, xp, yp, zp, 0);
		return a;		
	}
	
	public Locacion getLocacionBajada(){
		for(Locacion l : getSublocaciones())
			if(l.getCentro().getX() == getCentro().getX() && l.getCentro().getY() == getCentro().getY())
				return l;
		return null;
	}
	
	private Locacion busLocAux(int x, int y, int xp, int yp, int zp, int nivActual){
		Locacion locacion = null;
		if(zp == nivActual){
			if(getCentro().getX() == xp && getCentro().getY() == yp){
				for(Locacion lo : getSublocaciones())
					if(lo.getCentro().getX() == x && lo.getCentro().getY() == y){
						locacion = lo;
					    break;
					}
			}
		}
		else{
			for(Locacion l : getSublocaciones()){
				Locacion aux = l.busLocAux(x, y, xp, yp, zp, nivActual + 1);
				if (aux!= null)
					locacion = aux;
			}
		}
		return locacion;
	}
	
	@Override
	public boolean equals(Object obj){
		boolean res = true;
		Locacion loc = (Locacion) obj;
		
		res = res && (loc.getSenial() == valorSenial);
		res = res && (loc.getCentro().getX() == centro.getX() && loc.getCentro().getY() == centro.getY());
		res = res && (loc.getNombre().equals(this.nombre));
		for(int i=0;i < loc.getSublocaciones().size();i++){
			res = res && loc.getSublocaciones().get(i).equals(sublocaciones.get(i));
		}
		
		return res;
	}
	
	public void armar_ciudad_con_valores(){
		Esquina e1= new Esquina(1,1,2,false); e1.setNombre("E1");
		Esquina e2= new Esquina(1,2,0,false); e2.setNombre("E2");
		Esquina e3= new Esquina(2,2,1,true); e3.setNombre("E3");
		//Esquina e4= new Esquina(4,2,1,false); e4.setNombre("E4");
		//Esquina e5= new Esquina(3,2,3,false); e5.setNombre("E5");
		//Esquina e6= new Esquina(4,3,2,false); e6.setNombre("E6");
		e1.addAdyacente(e2,NORTE);e1.addAdyacente(e3,ESTE);
		e2.addAdyacente(e1,SUR);
		e3.addAdyacente(e1,OESTE);
		//e4.addAdyacente(e5,ESTE);e4.addAdyacente(e6,OESTE);
		//e5.addAdyacente(e4,OESTE);
		//e6.addAdyacente(e4,ESTE);
		//Nivel esquinas completo
		Locacion c1 = new Locacion();c1.setNombre("C1");c1.setCentro(1,2);c1.setSenial(90);
		Locacion sc1= new Locacion();sc1.setNombre("SC1");sc1.setCentro(1,2);sc1.setSenial(60);sc1.setPadre(c1);
		//Locacion sc2= new Locacion();sc2.setNombre("SC2");sc2.setCentro(4,2);sc2.setSenial(120);sc2.setPadre(c1);
		c1.addSublocacion(sc1);//c1.addSublocacion(sc2);
		//sc1.addAdyacente(sc2,ESTE);sc2.addAdyacente(sc1,OESTE);
		sc1.addSublocacion(e1);sc1.addSublocacion(e2);sc1.addSublocacion(e3);
		//sc2.addSublocacion(e4);sc2.addSublocacion(e5);sc2.addSublocacion(e6);
		e1.setPadre(sc1);e2.setPadre(sc1);e3.setPadre(sc1);
		//e4.setPadre(sc2);e5.setPadre(sc2);e6.setPadre(sc2);
		this.addSublocacion(c1);
	}
	
	public void armar_ciudad(){
		Esquina e1= new Esquina(1,1); e1.setNombre("E1");
		Esquina e2= new Esquina(1,2); e2.setNombre("E2");
		Esquina e3= new Esquina(2,2); e3.setNombre("E3");
	//	Esquina e4= new Esquina(4,2); e4.setNombre("E4");
	//	Esquina e5= new Esquina(3,2); e5.setNombre("E5");
	//	Esquina e6= new Esquina(4,3); e6.setNombre("E6");
		e1.addAdyacente(e2,NORTE);e1.addAdyacente(e3,ESTE);
		e2.addAdyacente(e1,SUR);
		e3.addAdyacente(e1,OESTE);
	//	e4.addAdyacente(e5,ESTE);e4.addAdyacente(e6,OESTE);
	//	e5.addAdyacente(e4,OESTE);
	//	e6.addAdyacente(e4,ESTE);
		//nivel esquinas completo
		Locacion c1 = new Locacion();c1.setNombre("C1");c1.setCentro(1,2);
		Locacion sc1= new Locacion();sc1.setNombre("SC1");sc1.setCentro(1,2);sc1.setPadre(c1);
	//	Locacion sc2= new Locacion();sc1.setNombre("SC2");sc2.setCentro(4,2);sc2.setPadre(c1);
		c1.addSublocacion(sc1);//c1.addSublocacion(sc2);
		//sc1.addAdyacente(sc2,ESTE);sc2.addAdyacente(sc1,OESTE);
		sc1.addSublocacion(e1);sc1.addSublocacion(e2);sc1.addSublocacion(e3);
	//	sc2.addSublocacion(e4);sc2.addSublocacion(e5);sc2.addSublocacion(e6);
		e1.setPadre(sc1);e2.setPadre(sc1);e3.setPadre(sc1);
	//	e4.setPadre(sc2);e5.setPadre(sc2);e6.setPadre(sc2);
		this.addSublocacion(c1);
	}
	
}
