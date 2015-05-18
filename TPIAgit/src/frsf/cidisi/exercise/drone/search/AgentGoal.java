

package frsf.cidisi.exercise.drone.search;

import frsf.cidisi.faia.agent.search.GoalTest;
import frsf.cidisi.faia.state.AgentState;

public class AgentGoal extends GoalTest {

    @Override
    public boolean isGoalState (AgentState agentState) {
    
        if  (((DroneAgentState) agentState).getvictimariosEncontrados() == 1
        		|| ((DroneAgentState) agentState).getlocacion().getVisitada()){
            return true;
        }
        return false;
	}
}