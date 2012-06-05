package rinde.sim.project.agent.dmas.intention;

import rinde.sim.project.agent.Taxi;
import rinde.sim.project.model.DMASModel;
import rinde.sim.project.model.pheromone.Pheromone;

public class IntentionPheromone extends Pheromone implements Comparable<IntentionPheromone>{
	
	/**
	 * Default lifetime of this pheromone
	 */
	private static final long DEFAULT_LIFETIME = IntentionDMAS.DEFAULT_INTERVAL;
	
	public final float cost;
	public final Taxi taxi;
	
	public IntentionPheromone(Taxi taxi, float cost){
		super(Math.round(DEFAULT_LIFETIME));
		this.cost = cost;
		this.taxi = taxi;
	}
	
	//TODO check this
	@Override
	public int compareTo(IntentionPheromone t){
		if(t.cost < cost)
			return -1;
		else if(t.cost == cost)
			return 0;
		else return 1;
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof IntentionPheromone){
			return ((IntentionPheromone) o).taxi.equals(taxi);
		}
		return false;
	}
}
