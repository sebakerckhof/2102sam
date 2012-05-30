package rinde.sim.project.agent;

import rinde.sim.project.agent.dmas.feasibility.FeasibilityHolder;
import rinde.sim.project.agent.dmas.feasibility.FeasibilityPheromone;
import rinde.sim.project.model.PheromoneInfrastructure;

public class DestinationDMASAgent implements FeasibilityHolder{

	PheromoneInfrastructure<FeasibilityPheromone> pheromones;
	
	@Override
	public PheromoneInfrastructure<FeasibilityPheromone> getPheromoneInfrastructure() {
		// TODO Auto-generated method stub
		return null;
	}

}
