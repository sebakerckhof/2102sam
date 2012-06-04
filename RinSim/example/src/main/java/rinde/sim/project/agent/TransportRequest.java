package rinde.sim.project.agent;

import rinde.sim.core.graph.Point;

public class TransportRequest {
	private Point pickupLocation;
	private Point depositLocation;
	
	private long start;
	private long deadline;
	public TransportRequest(Point pickupLocation, Point depositLocation,long start,long deadline){
		this.pickupLocation = pickupLocation;
		this.depositLocation = depositLocation;
		this.start = start;
		this.deadline = deadline;
	}
	
	public Point getPickupLocation(){
		return pickupLocation;
	}
	
	public Point getDepositLocation(){
		return depositLocation;
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
	
}
