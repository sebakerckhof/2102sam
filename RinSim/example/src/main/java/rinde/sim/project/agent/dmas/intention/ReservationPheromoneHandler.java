package rinde.sim.project.agent.dmas.intention;

import java.util.List;

import rinde.sim.project.model.pheromone.Pheromone;
import rinde.sim.project.model.pheromone.PheromoneHandler;
import rinde.sim.project.model.pheromone.PheromoneInfrastructure;

/**
 * Reservation (Intention) pheromone handler
 */
public class ReservationPheromoneHandler implements PheromoneHandler{
	
	/**
	 * {@link PheromoneHandler#handle(PheromoneInfrastructure, Pheromone)}
	 *
	 * If there is no intention => add the new intention
	 * If there is already an intention from the same taxi agent => update intention
	 * If there is already an intention from another taxi agent => update if new intention is better
	 */
	@Override
	public boolean handle(PheromoneInfrastructure pi, Pheromone p) {
		
		if(p instanceof IntentionPheromone){ //We meet an intention
			IntentionPheromone newIntention = (IntentionPheromone) p;
			List<IntentionPheromone> intentions= pi.smell(IntentionPheromone.class); //retrieve current intentions
			if(!intentions.isEmpty()){	//There are already current intentions
				IntentionPheromone oldIntention = intentions.get(intentions.size()); //only last one is valid
				if(oldIntention.compareTo(newIntention) == 1 && !oldIntention.equals(newIntention)){ //When the new intention is from a different taxi and not better than current, do not allow it
					 //TODO check this
					return false;
				}
			}
			
			pi.add(p);
			return true;
		}else{	//other pheromones go straight in the queue
			pi.queue.add(p);
			return true;
		}
	}
}
