package frsf.cidisi.exercise.drone.search;

import java.util.ArrayList;
import java.util.List;

import logica.Esquina;
import logica.Posicion;
import logica.Locacion;
import frsf.cidisi.faia.agent.Action;
import frsf.cidisi.faia.environment.Environment;

public class DroneEnvironment extends Environment {

    public DroneEnvironment() {
        // Create the environment state
        this.environmentState = new DroneEnvironmentState();
    }

    public DroneEnvironmentState getEnvironmentState() {
        return (DroneEnvironmentState) super.getEnvironmentState();
    }

    /**
     * This method is called by the simulator. Given the Agent, it creates
     * a new perception reading, for example, the agent position.
     * @param agent
     * @return A perception that will be given to the agent by the simulator.
     */
    @Override
    public  DroneAgentPerception getPercept() {
        // Create a new perception to return
        DroneAgentPerception perception = new DroneAgentPerception();
		
        DroneEnvironmentState est = (DroneEnvironmentState) environmentState;
        //gps
        Posicion pos =new Posicion();
        pos.setX((int)est.getlocacionDrone().getCentro().getX());
        pos.setY((int)est.getlocacionDrone().getCentro().getY());
        pos.setZ(est.getlocacionDrone().calcularAltura());
        perception.setgps(pos);
        //camara
        boolean[] cam = new boolean[9];
        if(est.getlocacionDrone().calcularAltura() == 3){
        	for(int i=0; i<8; i++)
        		cam[i] = ((Esquina) est.getlocacionDrone()).hayVictimario(i);
        	
        	cam[8] = ((Esquina)est.getlocacionDrone()).hayCriminal();
        }
        else{
        	for(int i=0;i<9;i++)
        		cam[i]=false;
        }
        perception.setcamara(cam);        
        //antena	
        List<Locacion> ant = new ArrayList<Locacion>();
        ant.addAll(est.getlocacionDrone().getPadre().getSublocaciones());
        perception.setantena(ant);
        // Return the perception
        return perception;
    }

    
    public String toString() {
        return environmentState.toString();
    }

    
    public boolean agentFailed(Action actionReturned) {

        DroneEnvironmentState envState = this.getEnvironmentState();
        
        int agentEnergy=1;
        if(envState != null)
        	agentEnergy = 10000 - envState.getEnergiaGastada();

        // If the agent has no energy, he failed
        if (agentEnergy <= 0)
        	return true;

        return false;
        
    }
    
    
}
