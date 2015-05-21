package frsf.cidisi.exercise.drone.search;

import frsf.cidisi.faia.solver.search.IStepCostFunction;
import frsf.cidisi.faia.solver.search.NTree;

/**
 * This class can be used in any search strategy like
 * Uniform Cost.
 */
public class CostFunction implements IStepCostFunction {

    /**
     * This method calculates the cost of the given NTree node.
     */
    @Override
    public double calculateCost(NTree node) {
        
        return ((DroneAgentState) node.getAgentState()).getenergiaGastada() -
       ((DroneAgentState) node.getParent().getAgentState()).getenergiaGastada();
        
    }
}
