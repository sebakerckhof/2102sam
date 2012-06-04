package rinde.sim.project.old;

import rinde.sim.core.SimulatorAPI;
import rinde.sim.core.SimulatorUser;
import rinde.sim.core.graph.Point;
import rinde.sim.core.model.RoadModel;
import rinde.sim.core.model.RoadUser;

public class Package implements SimulatorUser, RoadUser{
	
	public final String packageID;
	private Point pickupLocation;
	private DeliveryLocation deliveryLocation;
	private boolean pickedUp;
	private boolean delivered;
	private SimulatorAPI simulator;
	private long start;
	private long deadline;

	public Package(String packageID, Point pickupLocation, DeliveryLocation deliveryLocation,long start,long deadline) {
		this.packageID = packageID;
		this.pickupLocation = pickupLocation;
		this.deliveryLocation = deliveryLocation;
		this.pickedUp = false;
		this.delivered = false;
		this.start = start;
		this.deadline = deadline;
	}
	
	public boolean needsPickUp(){
		return !pickedUp;
	}

	public boolean delivered(){
		return delivered;
	}
	
	public void pickup(){
		this.pickedUp = true;
		this.simulator.unregister(this);
	}
	
	public void deliver(){
		this.delivered = true;
		this.simulator.unregister(deliveryLocation);
	}
	
	public String getPackageID(){
		return packageID;
	}
	
	@Override
	public String toString() {
		return packageID;
	}

	public Point getPickupLocation(){
		return pickupLocation;
	}
	
	public Point getDeliveryLocation(){
		return deliveryLocation.getPosition();
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
	public boolean equals(Object o){
		if(o instanceof Package)
			return ((Package) o).packageID.equals(this.packageID);
		
		return false;
	}

	@Override
	public void setSimulator(SimulatorAPI api) {
		this.simulator = api;
	}

	@Override
	public void initRoadUser(RoadModel model) {
		model.addObjectAt(this, pickupLocation);
	}

}
