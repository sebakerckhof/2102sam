package rinde.sim.project.model.pheromone;

public class DefaultHandler implements PheromoneHandler{

	/**
	 * {@link PheromoneHandler#handle(PheromoneInfrastructure, Pheromone)}
	 * 
	 * Adds the new pheromone to the queue waiting to be flushed
	 */
	@Override
	public boolean handle(PheromoneInfrastructure pi, Pheromone p) {
		pi.queue.add(p);
		return true;
	}

}
