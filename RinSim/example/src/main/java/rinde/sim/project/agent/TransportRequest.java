package rinde.sim.project.agent;

import rinde.sim.core.graph.Point;

public class TransportRequest implements Comparable<TransportRequest>{
	private Point pickupLocation;
	private Point depositLocation;
	
	private long start;
	private long deadline;
	
	private long travelTime;
	private long travelDistance;
	
	public TransportRequest(Point pickupLocation, Point depositLocation,long start,long deadline){
		this.pickupLocation = pickupLocation;
		this.depositLocation = depositLocation;
		this.start = start;
		this.deadline = deadline;
	}
	
	public long getTravelDistance() {
		return travelTime;
	}


	public void setTravelDistance(long travelDistance) {
		this.travelDistance = travelDistance;
	}
	
	public long getTravelTime() {
		return travelTime;
	}


	public void setTravelTime(long travelTime) {
		this.travelTime = travelTime;
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
	
	@Override
	public int compareTo(TransportRequest r){
		long timeLeft = deadline-travelTime;
		long rTimeLeft = r.deadline - r.travelTime;
		
		if(timeLeft < rTimeLeft)
			return -1;
		else if(timeLeft == rTimeLeft)
			return 0;
		else return 1;
			
	}
}
