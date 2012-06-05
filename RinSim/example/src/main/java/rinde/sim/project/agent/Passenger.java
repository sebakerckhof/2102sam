package rinde.sim.project.agent;

import rinde.sim.core.SimulatorAPI;
import rinde.sim.core.SimulatorUser;
import rinde.sim.core.TickListener;
import rinde.sim.core.graph.Point;
import rinde.sim.core.model.RoadModel;
import rinde.sim.core.model.RoadUser;
import rinde.sim.project.agent.dmas.feasibility.FeasibilityDMAS;
import rinde.sim.project.agent.dmas.intention.IntentionPheromone;
import rinde.sim.project.agent.dmas.intention.ReservationPheromoneHandler;
import rinde.sim.project.model.AntAcceptor;
import rinde.sim.project.model.AntAgent;
import rinde.sim.project.model.DMASModel;
import rinde.sim.project.old.DeliveryLocation;
import rinde.sim.project.old.Package;

public class Passenger implements AntAcceptor, RoadUser, SimulatorUser{
	
	public final String passengerID;

	private boolean pickedUp;
	private boolean deposited;
	
	private TransportRequest request;
	private Destination destination;
	
	private SimulatorAPI simulator;
	private DMASModel environment;
	private RoadModel rm;

	public Passenger(String passengerID, TransportRequest request) {
		this.passengerID = passengerID;

		this.pickedUp = false;
		this.deposited = false;
		
		this.request = request;
		
		this.destination = new Destination(this,request);
	}
	
	public TransportRequest getRequest(){
		return this.request;
	}
	
	public boolean needsPickUp(){
		return !pickedUp;
	}

	public boolean deposited(){
		return deposited;
	}
	
	public void pickup(){
		this.pickedUp = true;
	}
	
	public void deposit(){
		this.deposited = true;
	}
	
	public String getPackageID(){
		return passengerID;
	}
	
	@Override
	public String toString() {
		return passengerID;
	}


	public Destination getDestination(){
		return destination;
	}
	


	@Override
	public void accept(AntAgent a) {
		a.visit(this);
	}


	@Override
	public void init(DMASModel model) {
		this.environment = model;
		model.addAntAcceptor(this, new ReservationPheromoneHandler());
	}


	@Override
	public void initRoadUser(RoadModel model) {
		this.rm = model;
		rm.addObjectAt(this, this.request.getPickupLocation());
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Passenger)
			return ((Passenger) o).passengerID.equals(this.passengerID);
		
		return false;
	}

	@Override
	public void setSimulator(SimulatorAPI api) {
		this.simulator = api;
		simulator.register(destination);
	}

	public Point getPosition() {
		return request.getPickupLocation();
	}

}
