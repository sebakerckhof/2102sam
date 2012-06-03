package rinde.sim.project.old;

import rinde.sim.project.agent.dmas.intention.IntentionPheromone;

public interface IntentionHolder {
	public PheromoneInfrastructure<IntentionPheromone> getIntentions();
	
}
