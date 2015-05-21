package frsf.cidisi.exercise.drone.search;

import logica.Esquina;
import logica.Locacion;
import frsf.cidisi.faia.solver.search.IEstimatedCostFunction;
import frsf.cidisi.faia.solver.search.NTree;

/**
 * This class allows to define a function to be used by any
 * informed search strategy, like A Star or Greedy.
 */
public class Heuristic implements IEstimatedCostFunction {

    /**
     * It returns the estimated cost to reach the goal from a NTree node.
     */
    @Override
    public double getEstimatedCost(NTree node) {
        DroneAgentState agState = (DroneAgentState) node.getAgentState();
        
        int heuristic=0;
        if(agState.getlocacion().calcularAltura() == 3 && agState.getdireccionVictimario()[8] != true){
	        for(int i=0;i<8;i++){
	        	//Si posee alguna direcci�n con vistimarios, el costo de llegar ser� menor mientras
	        	//se mantenga en esa direccion
	        	if(agState.getdireccionVictimario()[i]){
	        		heuristic += 50 * ((Esquina)agState.getlocacion()).cantEsquinasDireccion(i);
	        	}
	        	//Si no hay direcci�n con victimario, se estima en 100 * cantidad de pasos en
	        	//cada direcci�n
	        	else
	        		heuristic += 100 * ((Esquina)agState.getlocacion()).cantEsquinasDireccion(i);
	        }
        }
        else{
        	//LO que debe bajar hasta el nivel inferior
        	heuristic += 100 * (3 - agState.getlocacion().calcularAltura());
        	
        	//Lo que debe moverse hasta el cuadrante con mayor se�al
        	Locacion aux = null;
        	int maxValSenial = 0;
        	for(Locacion l : agState.getlocacion().getPadre().getSublocaciones()){
        		if(l.getSenial() >= maxValSenial){
        			aux = l;
        			maxValSenial = l.getSenial();
        		}
        	}
        	if(aux.getCentro().getX() == agState.getlocacion().getCentro().getX()
        			&& aux.getCentro().getY() == agState.getlocacion().getCentro().getY())
        		heuristic += 0;
        	else{
        		heuristic += 200; //No es adyacente
        		for(Locacion l : agState.getlocacion().getAdyacentes())
        			if(aux.getCentro().getX() == l.getCentro().getX() 
        					&& aux.getCentro().getY() == l.getCentro().getY())
        				heuristic -=100; //Saco 100 porqu si lo era
        	}
        	        	
        }
        return heuristic;
    }
}
