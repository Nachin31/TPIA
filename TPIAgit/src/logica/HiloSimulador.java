package logica;

import frsf.cidisi.exercise.drone.search.DroneAgent;
import frsf.cidisi.exercise.drone.search.DroneAgentMain;
import frsf.cidisi.faia.exceptions.PrologConnectorException;

public class HiloSimulador extends Thread {

	int estrategia;
	
	public void run(){
		try {
			DroneAgentMain.main(estrategia);
		} catch (PrologConnectorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setEstrategia(int strategy){
		estrategia = strategy;
	}
	
}
