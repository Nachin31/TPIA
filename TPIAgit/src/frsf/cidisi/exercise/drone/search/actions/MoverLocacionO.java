package frsf.cidisi.exercise.drone.search.actions;

import logica.Locacion;
import frsf.cidisi.exercise.drone.search.*;
import frsf.cidisi.faia.agent.search.SearchAction;
import frsf.cidisi.faia.agent.search.SearchBasedAgentState;
import frsf.cidisi.faia.state.AgentState;
import frsf.cidisi.faia.state.EnvironmentState;

public class MoverLocacionO extends SearchAction {

	private int COSTO_MOVERSE = 100;
	
    /**
     * This method updates a tree node state when the search process is running.
     * It does not updates the real world state.
     */
    @Override
    public SearchBasedAgentState execute(SearchBasedAgentState s) {
        DroneAgentState agState = (DroneAgentState) s;
        
        if(agState.getlocacion().getAdyacente(Locacion.OESTE) != null && (agState.getenergiaInicial() - agState.getenergiaGastada()) >= COSTO_MOVERSE){
           	System.out.println("Ejecuta: Oeste");
        	agState.setlocacion(agState.getlocacion().getAdyacente(Locacion.OESTE));
        	agState.setenergiaGastada(agState.getenergiaGastada()+COSTO_MOVERSE);
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

        if(agState.getlocacion().getAdyacente(Locacion.OESTE) != null && (agState.getenergiaInicial() - agState.getenergiaGastada()) >= COSTO_MOVERSE){
        	agState.setlocacion(agState.getlocacion().getAdyacente(Locacion.OESTE));
        	agState.setenergiaGastada(agState.getenergiaGastada()+COSTO_MOVERSE);
        	
        	environmentState.setlocacionDrone(environmentState.getlocacionDrone().getAdyacente(Locacion.OESTE));
        	environmentState.setEnergiaGastada(environmentState.getEnergiaGastada()+COSTO_MOVERSE);
            
            return environmentState;
        }

        return null;
    }

    /**
     * This method returns the action cost.
     */
    @Override
    public Double getCost() {
        return new Double(0);
    }

    /**
     * This method is not important for a search based agent, but is essensial
     * when creating a calculus based one.
     */
    @Override
    public String toString() {
        return "MoverLocacionO";
    }
}