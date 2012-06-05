package rinde.sim.project.agent.dmas.feasibility;

import rinde.sim.project.agent.Destination;
import rinde.sim.project.agent.PassengerAgent;
import rinde.sim.project.agent.Passenger;
import rinde.sim.project.agent.Taxi;
import rinde.sim.project.agent.TaxiAgent;
import rinde.sim.project.model.AntAcceptor;
import rinde.sim.project.model.AntAgent;
import rinde.sim.project.model.pheromone.Pheromone;
import rinde.sim.util.Tuple;

public class FeasibilityAnt extends AntAgent{

	private Passenger passenger;
	
	public FeasibilityAnt(Passenger passenger, AntAcceptor destination) {
		super(destination,1);
		this.passenger = passenger;
	}

	@Override
	public void visit(Destination t) {
		Tuple<Long,Long> data = rm.getTravelData(Taxi.SPEED, passenger.getPosition(), t.getPosition());
		environment.drop(t, new FeasibilityPheromone(passenger, data.getKey(), data.getValue()));
		terminate();
	}
	
}
