package rinde.sim.project.old;

import rinde.sim.project.agent.dmas.feasibility.FeasibilityPheromone;

public interface FeasibilityHolder {
	public PheromoneInfrastructure<FeasibilityPheromone> getPheromoneInfrastructure();
}
