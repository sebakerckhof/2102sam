package rinde.sim.project.agent.dmas.intention;

import rinde.sim.project.old.PheromoneInfrastructure;

public interface IntentionHolder {
	public PheromoneInfrastructure<IntentionPheromone> getIntentions();
	
}
