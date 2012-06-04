package rinde.sim.project.agent.dmas.feasibility;

import java.util.List;
import java.util.Set;

import rinde.sim.core.model.RoadModel;
import rinde.sim.core.model.RoadUser;
import rinde.sim.project.agent.Destination;
import rinde.sim.project.agent.Passenger;
import rinde.sim.project.agent.PassengerAgent;
import rinde.sim.project.model.DMAS;

public class FeasibilityDMAS extends DMAS implements RoadUser{
	
	public static final int DEFAULT_INTERVAL = 500;
	private RoadModel rm;
	private PassengerAgent agent;
	
	public FeasibilityDMAS(PassengerAgent passenger){
		this(passenger, DEFAULT_INTERVAL);
	}
	
	public FeasibilityDMAS(PassengerAgent agent, int sendInterval) {
		super(sendInterval);
		this.agent = agent;
	}

	@Override
	public void execute(){
		//spread pickup information
		Set<Destination > destinations = rm.getObjectsNearby(agent.getPassenger().getPickupLocation(), Destination.class, 50);
		for(Destination d : destinations){
			FeasibilityAnt ant = new FeasibilityAnt(agent.getPassenger(),d);
			environment.deploy(ant);
		}
	}

	@Override
	public void initRoadUser(RoadModel model) {
		this.rm = model;
	}
}
