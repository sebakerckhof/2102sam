package rinde.sim.project.agent;

import rinde.sim.core.SimulatorAPI;
import rinde.sim.core.SimulatorUser;
import rinde.sim.core.TickListener;
import rinde.sim.core.graph.Point;
import rinde.sim.core.model.RoadModel;
import rinde.sim.core.model.RoadUser;
import rinde.sim.project.agent.dmas.feasibility.FeasibilityDMAS;
import rinde.sim.project.model.AntAcceptor;
import rinde.sim.project.model.AntAgent;
import rinde.sim.project.model.DMASModel;
import rinde.sim.project.old.DeliveryLocation;
import rinde.sim.project.old.Package;

public class Passenger implements AntAcceptor, RoadUser, SimulatorUser{
	
	public final String passengerID;
	private Point pickupLocation;
	private Destination destination;
	
	private long start;
	private long deadline;

	private boolean pickedUp;
	private boolean deposited;
	
	private SimulatorAPI simulator;
	private DMASModel environment;
	private RoadModel rm;
	
	private FeasibilityDMAS fDmas;

	public Passenger(String passengerID, Point pickupLocation, Point depositLocation,long start,long deadline) {
		this.passengerID = passengerID;
		this.pickupLocation = pickupLocation;
		this.pickedUp = false;
		this.deposited = false;
		this.start = start;
		this.deadline = deadline;
		
		this.destination = new Destination(this,depositLocation);
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

	public Point getPickupLocation(){
		return pickupLocation;
	}
	
	public Destination getDestination(){
		return destination;
	}
	
	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getDeadline() {
		return deadline;
	}

	public void setDeadline(long deadline) {
		this.deadline = deadline;
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
	public void initRoadUser(RoadModel model) {
		this.rm = model;
		rm.addObjectAt(this, this.getPickupLocation());
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

}
