package rinde.sim.project.agent.dmas.exploration;

import rinde.sim.project.agent.Plan;
import rinde.sim.project.model.DMASModel;
import rinde.sim.project.model.pheromone.Pheromone;

public class ExplorationPheromone extends Pheromone implements Comparable<ExplorationPheromone>{
	private static final long DEFAULT_LIFETIME = ExplorationDMAS.DEFAULT_INTERVAL;
	
	public final Plan plan;
	public ExplorationPheromone(Plan plan){
		super(Math.round(DEFAULT_LIFETIME * (1 / DMASModel.ADAPTABILITY_RATE)));
		this.plan = plan;
	}
	
	@Override
	public int compareTo(ExplorationPheromone p){
		return plan.compareTo(p.plan);
	}
}
