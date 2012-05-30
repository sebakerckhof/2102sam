package rinde.sim.project.agent.dmas.feasibility;

import rinde.sim.project.model.PheromoneInfrastructure;

public interface FeasibilityHolder {
	public PheromoneInfrastructure<FeasibilityPheromone> getPheromoneInfrastructure();
}
