package rinde.sim.project.agent.dmas.feasibility;

import java.util.Set;

import rinde.sim.core.model.RoadModel;
import rinde.sim.core.model.RoadUser;
import rinde.sim.project.agent.Destination;
import rinde.sim.project.agent.Passenger;
import rinde.sim.project.model.DMAS;
import rinde.sim.project.util.Utils;

/**
 * Feasibility DMAS
 * 
 * This MAS will send ants with passenger information to destination agents of other passengers in order to spread awareness of its existence.
 * In this way we can distribute the information
 */
public class FeasibilityDMAS extends DMAS implements RoadUser{
	
	/**
	 * Default interval to send feasibility ants
	 */
	public static final long DEFAULT_INTERVAL = Utils.timeConverter.min(60).toTime();
	
	/**
	 * RoadModel
	 */
	private RoadModel rm;
	
	/**
	 * Corresponding passenger
	 */
	private final Passenger passenger;
	
	/**
	 * {@link #FeasibilityDMAS(Passenger, long)}
	 */
	public FeasibilityDMAS(Passenger passenger){
		this(passenger, DEFAULT_INTERVAL);
	}
	
	/**
	 * Constructor
	 * @param passenger		Corresponding passenger
	 * @param sendInterval	Interval to send feasibility ants
	 * 
	 * {@link DMAS#DMAS(long)}
	 */
	public FeasibilityDMAS(Passenger passenger, long sendInterval) {
		super(sendInterval);
		this.passenger = passenger;
	}

	/**
	 * This will create & send feasibility ants
	 * {@link DMAS#execute(long, long)}
	 */
	@Override
	public void execute(long currentTime, long timeStep){
		//spread pickup information
		Set<Destination > destinations = rm.getObjectsNearby(passenger.getRequest().getPickupLocation(), Destination.class, 50);
		for(Destination d : destinations){
			if(!d.equals(passenger.getDestination())){
				FeasibilityAnt ant = new FeasibilityAnt(passenger,d);
				simulator.register(ant);
			}
		}
	}

	@Override
	public void initRoadUser(RoadModel model) {
		this.rm = model;
	}
}
