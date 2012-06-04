package rinde.sim.project.agent.dmas.exploration;

import rinde.sim.project.model.DMASModel;
import rinde.sim.project.model.Pheromone;

public class ExplorationPheromone extends Pheromone{
	private static final long DEFAULT_LIFETIME = 500;
	
	
	public ExplorationPheromone(){
		super(Math.round(DEFAULT_LIFETIME * DMASModel.ADAPTABILITY_RATE));
	}
}
