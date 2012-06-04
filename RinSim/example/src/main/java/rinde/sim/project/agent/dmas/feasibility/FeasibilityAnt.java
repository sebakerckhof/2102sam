package rinde.sim.project.agent.dmas.feasibility;

import rinde.sim.project.agent.Destination;
import rinde.sim.project.agent.PassengerAgent;
import rinde.sim.project.agent.Passenger;
import rinde.sim.project.agent.TaxiAgent;
import rinde.sim.project.model.AntAcceptor;
import rinde.sim.project.model.AntAgent;
import rinde.sim.project.model.pheromone.Pheromone;

public class FeasibilityAnt extends AntAgent{

	private Passenger passenger;
	
	public FeasibilityAnt(Passenger passenger, AntAcceptor destination) {
		super(destination,1);
		this.passenger = passenger;
	}

	@Override
	public void visit(Destination t) {
		environment.drop(t, createPheromone());
		terminate();
	}
	
	protected Pheromone createPheromone(){
		return new FeasibilityPheromone(passenger);
	}


}
