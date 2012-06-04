package rinde.sim.project.agent.dmas.intention;

import rinde.sim.project.model.pheromone.Pheromone;
import rinde.sim.project.model.pheromone.PheromoneHandler;
import rinde.sim.project.model.pheromone.PheromoneInfrastructure;

public class ReservationPheromoneHandler implements PheromoneHandler{
	@Override
	public boolean handle(PheromoneInfrastructure pi, Pheromone p) {
		if(p instanceof IntentionPheromone){
			IntentionPheromone intention = (IntentionPheromone) p;
			return true;
		}else{
			pi.queue.add(p);
			return true;
		}
	}
}
