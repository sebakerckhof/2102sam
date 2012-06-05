package rinde.sim.project.agent.dmas.feasibility;

import rinde.sim.project.agent.Destination;
import rinde.sim.project.agent.Passenger;
import rinde.sim.project.agent.Taxi;
import rinde.sim.project.model.AntAcceptor;
import rinde.sim.project.model.AntAgent;
import rinde.sim.util.Tuple;

/**
 * Ant that spreads information about a passenger in other destination nodes
 */
public class FeasibilityAnt extends AntAgent{

	/**
	 * The passenger sending this ant
	 */
	private final Passenger passenger;
	
	/**
	 * 
	 * @param passenger		The passenger sending this ant
	 * @param destination	The destination we want to travel this ant to
	 */
	public FeasibilityAnt(Passenger passenger, AntAcceptor destination) {
		super(destination,1);
		this.passenger = passenger;
	}

	/**
	 * {@link #visit(Destination)}
	 * Spread PheasibilityPheromone containing a reference to the passenger and the travel information to help exploration ants
	 */
	@Override
	public void visit(Destination t) {
		Tuple<Long,Long> data = rm.getTravelData(Taxi.SPEED, passenger.getPosition(), t.getPosition());
		environment.drop(t, new FeasibilityPheromone(passenger, data.getKey(), data.getValue()));
		terminate();
	}
	
}
