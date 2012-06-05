package rinde.sim.project.model.pheromone;

/**
 * Handles incoming pheromones dropped in a pheromoneinfrastructure
 * Decides what to do with them
 */
public interface PheromoneHandler {
	
	/**
	 * Handles the new pheromone when inserted in pheromone infrastructure
	 * 
	 * @param pi	PheromoneInfrastructure	to add pheromone in
	 * @param p		Pheromone to add to infrastructure
	 * @return	True if pheromone has been added, false otherwise
	 */
	public boolean handle(PheromoneInfrastructure pi, Pheromone p);
}
