package rinde.sim.project.agent.dmas.feasibility;

import rinde.sim.project.agent.Passenger;
import rinde.sim.project.model.DMAS;
import rinde.sim.project.model.DMASModel;
import rinde.sim.project.model.Pheromone;

public class FeasibilityPheromone extends Pheromone{

	private static final long DEFAULT_LIFETIME = 500;
	
	public final Passenger passenger;
	
	public FeasibilityPheromone(Passenger passenger){
		super(Math.round(DEFAULT_LIFETIME * DMASModel.ADAPTABILITY_RATE));
		this.passenger = passenger;
	}
}
