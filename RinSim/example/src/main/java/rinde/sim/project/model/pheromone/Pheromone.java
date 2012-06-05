package rinde.sim.project.model.pheromone;

import rinde.sim.core.TickListener;

/**
 * Abstract Pheromone
 */
public class Pheromone implements TickListener{
	
	/**
	 * Lifetime of pheromone = time after which pheromone vaporates
	 */
	private long lifetime; 
	
	/**
	 * {@link #Pheromone(long)}
	 */
	protected Pheromone(){
		this(Long.MAX_VALUE);
	}
	
	/**
	 * Constructor
	 * @param lifetime	Lifetime of pheromone = time after which pheromone vaporates
	 */
	public Pheromone(long lifetime) {
		this.lifetime = lifetime;
	}

	/**
	 * GETTER & SETTERS
	 */
	public long getLifetime() {
		return lifetime;
	}
	
	public void setLifetime(long lifetime) {
		this.lifetime = lifetime;
	}
	
	/**
	 * Check if evaporated
	 * @return true if evaporated, false otherwise
	 */
	public boolean hasEvaporated() {
		return lifetime < 0;
	}

	/**
	 * {@link TickListener#afterTick(long, long)}
	 */
	@Override
	public void afterTick(long currentTime, long timeStep) {}

	/**
	 * {@link TickListener#tick(long, long)}
	 * 
	 * Update lifetime
	 */
	@Override
	public void tick(long currentTime, long timeStep) {
		lifetime -= timeStep;
	}
}
