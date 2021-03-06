package frsf.cidisi.exercise.drone.search.actions;

import logica.Esquina;
import frsf.cidisi.exercise.drone.search.*;
import frsf.cidisi.faia.agent.search.SearchAction;
import frsf.cidisi.faia.agent.search.SearchBasedAgentState;
import frsf.cidisi.faia.state.AgentState;
import frsf.cidisi.faia.state.EnvironmentState;

public class IdentificarVictimario extends SearchAction {

    /**
     * This method updates a tree node state when the search process is running.
     * It does not updates the real world state.
     */
    @Override
    public SearchBasedAgentState execute(SearchBasedAgentState s) {
        DroneAgentState agState = (DroneAgentState) s;

        if(agState.getlocacion().calcularAltura() == 3 && !((Esquina)agState.getlocacion()).getVisitada()){
        	if(((Esquina)agState.getlocacion()).hayCriminal())
        		agState.setvictimariosEncontrados(agState.getvictimariosEncontrados()+1);
        	else
        		((Esquina) agState.getlocacion()).setVisitada(true);
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

        if(agState.getlocacion().calcularAltura() == 3 && !((Esquina)agState.getlocacion()).getVisitada()){
        	if(((Esquina)agState.getlocacion()).hayCriminal())
        		agState.setvictimariosEncontrados(agState.getvictimariosEncontrados()+1);
        	else
        		((Esquina) agState.getlocacion()).setVisitada(true);
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
        return "IdentificarVictimario";
    }
}