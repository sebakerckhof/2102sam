package rinde.sim.project.model.pheromone;

import rinde.sim.core.TickListener;
import rinde.sim.core.model.communication.Message;

public class Pheromone implements TickListener{
	private long lifetime; //time after which pheromone vaporates
	
	protected Pheromone(){
		this(Long.MAX_VALUE);
	}
	
	public Pheromone(long lifetime) {
		this.lifetime = lifetime;
	}


	public long getLifetime() {
		return lifetime;
	}
	
	protected void setLifetime(long lifetime) {
		this.lifetime = lifetime;
	}
	
	public boolean hasEvaporated() {
		return lifetime < 0;
	}

	@Override
	public void afterTick(long currentTime, long timeStep) {}

	@Override
	public void tick(long currentTime, long timeStep) {
		lifetime -= timeStep;
	}
}
