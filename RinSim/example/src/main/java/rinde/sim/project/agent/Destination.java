package rinde.sim.project.agent;

import rinde.sim.core.SimulatorAPI;
import rinde.sim.core.SimulatorUser;
import rinde.sim.core.TickListener;
import rinde.sim.core.graph.Point;
import rinde.sim.core.model.RoadModel;
import rinde.sim.core.model.RoadUser;
import rinde.sim.project.agent.dmas.feasibility.FeasibilityDMAS;
import rinde.sim.project.agent.dmas.feasibility.FeasibilityPheromone;
import rinde.sim.project.model.AntAcceptor;
import rinde.sim.project.model.AntAgent;
import rinde.sim.project.model.DMASModel;
import rinde.sim.project.model.DMASUser;
import rinde.sim.project.old.FeasibilityHolder;
import rinde.sim.project.old.PheromoneInfrastructure;
import rinde.sim.util.Tuple;

public class Destination implements AntAcceptor, RoadUser, SimulatorUser{

	private DMASModel environment;
	private RoadModel rm;
	private SimulatorAPI simulator;
	private Passenger passenger;
	private TransportRequest request;


	public Destination(Passenger passenger, TransportRequest request){
		this.passenger = passenger;
		this.request = request;
	}
	
	public Point getPosition(){
		return request.getDepositLocation();
	}
	
	public TransportRequest getRequest() {
		return request;
	}

	public Passenger getPassenger(){
		return this.passenger;
	}

	@Override
	public void accept(AntAgent a) {
		a.visit(this);
	}


	@Override
	public void init(DMASModel model) {
		this.environment = model;
		model.addAntAcceptor(this);
	}


	@Override
	public void setSimulator(SimulatorAPI api) {
		this.simulator = api;
	}


	@Override
	public void initRoadUser(RoadModel model) {
		this.rm = model;
		rm.addObjectAt(this, request.getDepositLocation());
		Tuple<Long,Long> data = rm.getTravelData(Taxi.SPEED, request.getPickupLocation(), request.getDepositLocation());
		request.setTravelDistance(data.getKey());
		request.setTravelTime(data.getValue());
	}

	@Override
	public boolean equals(Object o){
		if(o instanceof Destination)
			return passenger.equals(((Destination) o).passenger);
		return false;
	}
}
