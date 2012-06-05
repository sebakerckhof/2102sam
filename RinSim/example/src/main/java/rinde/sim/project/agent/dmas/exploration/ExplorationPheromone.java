package rinde.sim.project.agent.dmas.exploration;

import rinde.sim.project.agent.Plan;
import rinde.sim.project.model.DMASModel;
import rinde.sim.project.model.pheromone.Pheromone;

/**
 * Exploration pheromone is left at the originating taxi agent to report the found paths
 */
public class ExplorationPheromone extends Pheromone implements Comparable<ExplorationPheromone>{
	
	/**
	 * Default lifetime of this pheromone
	 */
	private static final long DEFAULT_LIFETIME = ExplorationDMAS.DEFAULT_INTERVAL;
	
	/**
	 * The plan the ant explored
	 */
	public final Plan plan;
	
	/**
	 * constructor
	 * @param plan	Explored plan
	 */
	public ExplorationPheromone(Plan plan){
		super(Math.round(DEFAULT_LIFETIME));
		this.plan = plan;
	}
	
	/**
	 * {@link Comparable#compareTo(Object)}
	 */
	@Override
	public int compareTo(ExplorationPheromone p){
		return plan.compareTo(p.plan);
	}
}
