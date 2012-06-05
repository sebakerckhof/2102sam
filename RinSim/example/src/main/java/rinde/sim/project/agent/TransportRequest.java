package rinde.sim.project.agent;

import rinde.sim.core.graph.Point;

/**
 * Transport Request
 * Contains all information for a taxi call
 */
public class TransportRequest implements Comparable<TransportRequest>{
	
	/**
	 * Request to be picked up at this location
	 */
	private final Point pickupLocation;
	
	/**
	 * Request to be deposited at this location
	 */
	private final LocationAgent destination;
	
	/**
	 * Request to be picked up starting from this time
	 */
	private final long start;
	
	/**
	 * Request to be deposited before this time
	 */
	private final long deadline;
	
	/**
	 * Travel time of shortest path between pickup & deposit location, based on standard taxi speed {@link Taxi#SPEED}
	 */
	private long travelTime;
	
	/**
	 * Length of shortest path between pickup & deposit location
	 */
	private long travelDistance;
	
	/**
	 * Constructor
	 * @param pickupLocation	Request to be picked up at this location
	 * @param destination		Request to be deposited at this location
	 * @param start				Request to be picked up starting from this time
	 * @param deadline			Request to be deposited before this time
	 */		
	public TransportRequest(Point pickupLocation, LocationAgent destination,long start,long deadline){
		this.pickupLocation = pickupLocation;
		this.destination = destination;
		this.start = start;
		this.deadline = deadline;
	}
	
	/**
	 * GETTERS & SETTERS
	 */
	public long getTravelDistance() {
		return travelDistance;
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
	
	public LocationAgent getDestination(){
		return destination;
	}

	public long getStart() {
		return start;
	}

	public long getDeadline() {
		return deadline;
	}


	/**
	 * EXTRA METHODS
	 */
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
