package rinde.sim.project.model;

import rinde.sim.core.TickListener;
import rinde.sim.core.model.communication.Message;

public class Pheromone implements TickListener{
	private long ete; //estimated time of evaporation
	private boolean evaporated = false; 
	
	protected Pheromone(){
		
	}
	
	public Pheromone(long ete) {
		this.ete = ete;
	}


	public long getEte() {
		return ete;
	}
	
	protected void setEte(long ete) {
		this.ete = ete;
	}
	
	public boolean hasEvaporated() {
		return evaporated;
	}

	@Override
	public void afterTick(long currentTime, long timeStep) {
		
	}

	@Override
	public void tick(long currentTime, long timeStep) {
		//evaporate
	}
}
