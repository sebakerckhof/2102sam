package rinde.sim.project.model.pheromone;

public class DefaultHandler implements PheromoneHandler{

	@Override
	public boolean handle(PheromoneInfrastructure pi, Pheromone p) {
		pi.queue.add(p);
		return true;
	}

}
