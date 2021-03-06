package frsf.cidisi.exercise.drone.search.actions;

import frsf.cidisi.exercise.drone.search.*;
import frsf.cidisi.faia.agent.search.SearchAction;
import frsf.cidisi.faia.agent.search.SearchBasedAgentState;
import frsf.cidisi.faia.state.AgentState;
import frsf.cidisi.faia.state.EnvironmentState;

public class Bajar extends SearchAction {

	private static final int COSTO_BAJAR = 100;
	
    /**
     * This method updates a tree node state when the search process is running.
     * It does not updates the real world state.
     */
    @Override
    public SearchBasedAgentState execute(SearchBasedAgentState s) {
        DroneAgentState agState = (DroneAgentState) s;
        
        if(agState.getlocacion().calcularAltura() != 3
        		&& (agState.getenergiaInicial() - agState.getenergiaGastada()) >= COSTO_BAJAR
        		&& (!agState.getlocacion().getVisitada() && agState.getlocacion().getSenial() > 0)){
        	agState.setlocacion(agState.getlocacion().getLocacionBajada());
        	agState.setenergiaGastada(agState.getenergiaGastada()+COSTO_BAJAR);
        	return agState;
        }
        
        return null;
    }

    /**
     * This method updates the agent state and the real world state.
     */
    @Override
    public EnvironmentState execute(AgentState ast, EnvironmentState est) {
        DroneEnvironmentState environmentState = (DroneEnvironmentState) est;
        DroneAgentState agState = ((DroneAgentState) ast);
        
        if (agState.getlocacion().calcularAltura() != 3 
        		&& (agState.getenergiaInicial() - agState.getenergiaGastada()) >= COSTO_BAJAR
        		&& !agState.getlocacion().getVisitada() && agState.getlocacion().getSenial() > 0) {
            // Update the real world
        	environmentState.setlocacionDrone(environmentState.getlocacionDrone().getLocacionBajada());
        	environmentState.setEnergiaGastada(environmentState.getEnergiaGastada()+COSTO_BAJAR);
        	
            // Update the agent state
        	agState.setlocacion(agState.getlocacion().getLocacionBajada());
        	agState.setenergiaGastada(agState.getenergiaGastada()+COSTO_BAJAR);
            
            return environmentState;
        }

        return null;
    }

    /**
     * This method returns the action cost.
     */
    @Override
    public Double getCost() {
        return new Double(COSTO_BAJAR);
    }

    /**
     * This method is not important for a search based agent, but is essensial
     * when creating a calculus based one.
     */
    @Override
    public String toString() {
        return "Bajar";
    }
}