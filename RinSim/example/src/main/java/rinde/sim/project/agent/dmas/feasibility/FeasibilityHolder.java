package rinde.sim.project.agent.dmas.feasibility;

import rinde.sim.project.old.PheromoneInfrastructure;

public interface FeasibilityHolder {
	public PheromoneInfrastructure<FeasibilityPheromone> getPheromoneInfrastructure();
}
