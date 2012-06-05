package rinde.sim.project.agent.dmas.intention;

import java.util.List;

import rinde.sim.project.model.pheromone.Pheromone;
import rinde.sim.project.model.pheromone.PheromoneHandler;
import rinde.sim.project.model.pheromone.PheromoneInfrastructure;

public class ReservationPheromoneHandler implements PheromoneHandler{
	@Override
	public boolean handle(PheromoneInfrastructure pi, Pheromone p) {
		if(p instanceof IntentionPheromone){
			IntentionPheromone newIntention = (IntentionPheromone) p;
			List<IntentionPheromone> intentions= pi.smell(IntentionPheromone.class);
			if(!intentions.isEmpty()){
				IntentionPheromone oldIntention = intentions.get(0);
				if(oldIntention.compareTo(newIntention) == 1 && !oldIntention.equals(newIntention)){ //TODO check this
					return false;
				}
			}
			
			//TODO: insert
			return true;
		}else{
			pi.queue.add(p);
			return true;
		}
	}
}
