package rinde.sim.gradientfields.packages;

import rinde.sim.core.SimulatorAPI;
import rinde.sim.core.SimulatorUser;
import rinde.sim.core.graph.Point;
import rinde.sim.core.model.RoadModel;
import rinde.sim.core.model.RoadUser;
import rinde.sim.gradientfields.taxi.Taxi;

public class Passenger implements SimulatorUser, RoadUser{
	public final String passengerID;
	private Point pickupLocation;
	private DeliveryLocation deliveryLocation;
	private Long travelTime;

	private boolean pickedUp;
	private boolean delivered;
	private SimulatorAPI simulator;
	private long deadline;

	public Passenger(String passengerID, Point pickupLocation, DeliveryLocation deliveryLocation, long deadline) {
		this.passengerID = passengerID;
		this.pickupLocation = pickupLocation;
		this.deliveryLocation = deliveryLocation;
		this.pickedUp = false;
		this.delivered = false;
		this.deadline = deadline;
	}
	
	public long getDeadline() {
		return deadline;
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
	
	public String getPassengerID(){
		return passengerID;
	}
	
	@Override
	public String toString() {
		return passengerID;
	}

	public Point getPickupLocation(){
		return pickupLocation;
	}
	
	public Point getDeliveryLocation(){
		return deliveryLocation.getPosition();
	}

	@Override
	public void setSimulator(SimulatorAPI api) {
		this.simulator = api;
	}

	public Long getTravelTime() {
		return travelTime;
	}
	
	@Override
	public void initRoadUser(RoadModel model) {
		model.addObjectAt(this, pickupLocation);
		this.travelTime = model.getTravelTime(Taxi.SPEED, this.pickupLocation, this.deliveryLocation.getPosition());
	}

}
