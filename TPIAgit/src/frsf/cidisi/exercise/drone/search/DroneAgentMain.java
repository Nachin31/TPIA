package frsf.cidisi.exercise.drone.search;

import frsf.cidisi.faia.exceptions.PrologConnectorException;
import frsf.cidisi.faia.simulator.SearchBasedAgentSimulator;

public class DroneAgentMain {

    public static void main(String[] args) throws PrologConnectorException {
        DroneAgent agent = new DroneAgent();

        DroneEnvironment environment = new DroneEnvironment();

        SearchBasedAgentSimulator simulator =
                new SearchBasedAgentSimulator(environment, agent);
        
        simulator.start();
    }

}
