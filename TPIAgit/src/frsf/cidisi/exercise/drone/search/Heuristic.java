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
        
        int heuristic = 0;
        
        if(agState.getlocacion().calcularAltura() == 3 && agState.getdireccionVictimario()[8] != true){
        	
        	boolean hayVict = false;
        	
        	heuristic += (agState.getenergiaInicial() - agState.getenergiaGastada())/(1+agState.getlocacion().getSenial());
        	        	
        	for(int i=0;i<8;i++)
        		hayVict = hayVict || agState.getdireccionVictimario()[i];
        	
        	if(!hayVict)
        		heuristic += 500;
        }
        
        return heuristic;
    }
}
