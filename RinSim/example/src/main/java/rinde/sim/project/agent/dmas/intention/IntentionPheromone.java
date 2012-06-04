package rinde.sim.project.agent.dmas.intention;

import rinde.sim.project.model.DMASModel;
import rinde.sim.project.model.pheromone.Pheromone;

public class IntentionPheromone extends Pheromone{
	private static final long DEFAULT_LIFETIME = 500;
	
	
	public IntentionPheromone(){
		super(Math.round(DEFAULT_LIFETIME * DMASModel.ADAPTABILITY_RATE));
	}
}
