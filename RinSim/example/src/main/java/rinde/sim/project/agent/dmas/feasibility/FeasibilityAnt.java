package rinde.sim.project.agent.dmas.feasibility;

import rinde.sim.project.agent.DestinationAgent;
import rinde.sim.project.agent.PackageAgent;
import rinde.sim.project.agent.TruckAgent;
import rinde.sim.project.agent.dmas.AntAgent;
import rinde.sim.project.model.AntAcceptor;
import rinde.sim.project.model.Pheromone;

public class FeasibilityAnt extends AntAgent{

	private PackageAgent agent;
	public FeasibilityAnt(AntAcceptor destination) {
		super(destination,1);
	}

	@Override
	public void visit(DestinationAgent t) {
		this.getEnvironment().drop(t, getPheromone());
		terminate();
	}
	
	protected Pheromone getPheromone(){
		return new FeasibilityPheromone();
	}


}
