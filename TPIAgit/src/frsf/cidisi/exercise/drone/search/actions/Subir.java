package frsf.cidisi.exercise.drone.search.actions;

import frsf.cidisi.exercise.drone.search.*;
import frsf.cidisi.faia.agent.search.SearchAction;
import frsf.cidisi.faia.agent.search.SearchBasedAgentState;
import frsf.cidisi.faia.state.AgentState;
import frsf.cidisi.faia.state.EnvironmentState;

public class Subir extends SearchAction {

	private int COSTO_SUBIR = 100;
	
    /**
     * This method updates a tree node state when the search process is running.
     * It does not updates the real world state.
     */
    @Override
    public SearchBasedAgentState execute(SearchBasedAgentState s) {
        DroneAgentState agState = (DroneAgentState) s;
        //System.out.println(agState.getlocacion().getNombre());
        if(agState.getlocacion().calcularAltura() != 1 && (agState.getenergiaInicial() - agState.getenergiaGastada()) >= COSTO_SUBIR){
           	System.out.println("Ejecuta: Subir" + " ;Locacion: " + agState.getlocacion().getNombre());
        	agState.setlocacion(agState.getlocacion().getPadre());
        	agState.setenergiaGastada(agState.getenergiaGastada()+COSTO_SUBIR);
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

        if (agState.getlocacion().calcularAltura() != 1 && (agState.getenergiaInicial() - agState.getenergiaGastada()) >= COSTO_SUBIR) {
            // Update the real world
        	environmentState.setlocacionDrone(environmentState.getlocacionDrone().getPadre());
        	environmentState.setEnergiaGastada(environmentState.getEnergiaGastada()+COSTO_SUBIR);
        	
            // Update the agent state
        	agState.setlocacion(agState.getlocacion().getPadre());
        	agState.setenergiaGastada(agState.getenergiaGastada()+COSTO_SUBIR);
            
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
        return "Subir";
    }
}