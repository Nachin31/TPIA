package frsf.cidisi.exercise.drone.search;

import interfaz.VentanaPrincipal;

import java.util.ArrayList;
import java.util.List;

import logica.Esquina;
import logica.Locacion;
import logica.Posicion;
import frsf.cidisi.faia.agent.Agent;
import frsf.cidisi.faia.agent.Perception;
import frsf.cidisi.faia.environment.Environment;

public class DroneAgentPerception extends Perception {

	//TODO: Setup Statics
    //public static int UNKNOWN_PERCEPTION = -1;   	
	
	private Posicion gps;
	private List<Locacion> antena;
	private boolean[] camara; 

    public  DroneAgentPerception() {
    	gps = new Posicion();
    	antena = new ArrayList<Locacion>();
    	camara = new boolean[9];
    	
    }

    public DroneAgentPerception(Agent agent, Environment environment) {
        super(agent, environment);
    }

    /** This method creates the initial perception of the agent.
     * 
     */
    @Override
    public void initPerception(Agent agentIn, Environment environmentIn) {
        
    	
        DroneAgent agent = (DroneAgent) agentIn;
        DroneEnvironment environment = (DroneEnvironment) environmentIn;
        DroneEnvironmentState environmentState = environment.getEnvironmentState();
        
        for(Locacion l : environmentState.getciudad().getSublocaciones())
        	if(l.getNombre().equals("A1"))
        		environmentState.setlocacionDrone(l);
        Locacion locacionDrone = environmentState.getlocacionDrone();
        int alturaDrone = locacionDrone.calcularAltura();
        
        //Percepcion del gps
        gps.setX((int)locacionDrone.getCentro().getX());
        gps.setY((int)locacionDrone.getCentro().getY());
        gps.setZ(alturaDrone);
        
        //Percepcion de la antena
        antena.addAll(locacionDrone.getPadre().getSublocaciones());
        
        //Percepcion de la camara
        if(alturaDrone == 3){
        	for(int i=0; i<8; i++){
        		camara[i] = ((Esquina)locacionDrone).hayVictimario(i);
        	}
        	camara[8] = ((Esquina)locacionDrone).hayCriminal();
        }
        
    }
    
    @Override
    public String toString() {      
        String str = "";
        
        str = str + "Percepciones del Drone: \n-- Posicion (gps):";
        
    	str = str + "\nX: " + gps.getX();
    	str = str + "\nY: " + gps.getY();
    	str = str + "\nZ: " + gps.getZ();
    	
    	str = str + "\n-- Antena:";
        for(int i=0;i<antena.size();i++){
        	str = str + "\n" + antena.get(i).getNombre() + " :  " + antena.get(i).getSenial();
        }
        
        if(gps.getZ() == 3){
        	str = str + "\nCamara (direccion victimario)";
        	str = str + "\nNORTE: " + camara[0];
        	str = str + "\nNORESTE: " + camara[1];
        	str = str + "\nESTE: " + camara[2];
        	str = str + "\nSURESTE: " + camara[3];
        	str = str + "\nSUR: " + camara[4];
        	str = str + "\nSUROESTE: " + camara[5];
        	str = str + "\nOESTE: " + camara[6];
        	str = str + "\nNOROESTE: " + camara[7];
        	str = str + "\nESQUINA ACTUAL: " + camara[8] + "\n";
        }
        
        VentanaPrincipal.writeConsole(str);
        
        return str;
    }

    // The following methods are agent-specific:
     public Posicion getgps(){
        return gps;
     }
     public void setgps(Posicion arg){
        this.gps = arg;
     }
     public List<Locacion> getantena(){
        return antena;
     }
     public void setantena(List<Locacion> arg){
        this.antena = arg;
     }
     public boolean[] getcamara(){
        return camara;
     }
     public void setcamara(boolean[] arg){
        this.camara = arg;
     }
	
   
}
