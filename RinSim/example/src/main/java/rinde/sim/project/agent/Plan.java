package rinde.sim.project.agent;

import java.util.LinkedList;
import java.util.List;

import rinde.sim.core.model.RoadModel;
import rinde.sim.project.agent.heuristic.Heuristic;
import rinde.sim.util.Tuple;

/**
 * This represents one possible plan for a taxi
 */
public class Plan implements Comparable<Plan>{
	
	/**
	 * List with passengers to pick-up (in the order given in the path)
	 */
	private final List<Passenger> path;
	
	/**
	 * List of distances corresponding to the path's passengers (destination of previous passenger to pick-up location of this passenger
	 */
	private final List<Long> distances;
	
	/**
	 * List of travel times corresponding to the path's passengers (destination of previous passenger to pick-up location of this passenger
	 */
	private final List<Long> travelTimes;

	/**
	 * The taxi this path is destined for
	 */
	private final Taxi taxi;
	
	/**
	 * The heuristic to calculate the cost (Strategy pattern)
	 */
	private Heuristic heuristic;
	
	/**
	 * The cost of this plan for the given taxi
	 */
	private float cost;
	
	/**
	 * RoadModel (to help with updating costs)
	 */
	private final RoadModel rm;
	
	/**
	 * Constructor
	 * 
	 * @param rm			RoadModel (to help with updating costs)
	 * @param taxi			Taxi this plan is destined for
	 * @param path		 	List with passengers to pick-up (in the order given in the path)
	 * @param distances		List of distances corresponding to the path's passengers (destination of previous passenger to pick-up location of this passenger
	 * @param travelTimes	List of travel times corresponding to the path's passengers (destination of previous passenger to pick-up location of this passenger
	 * @param heuristic		The heuristic to calculate the cost
	 */
	public Plan(RoadModel rm, Taxi taxi, List<Passenger> path, List<Long> distances, List<Long> travelTimes, Heuristic heuristic){
		this.path = path;
		this.taxi = taxi;
		this.heuristic = heuristic;
		this.distances = distances;
		this.travelTimes = travelTimes;
		this.rm = rm;
	}

	/**
	 * COST LOGIC
	 */
	public void updateCost(){
		//Adjust taxi location
		Tuple<Long,Long> data = rm.getTravelData(taxi.getSpeed(), taxi.getPosition(), path.get(0).getPosition());
		this.distances.set(0, data.getKey());
		this.travelTimes.set(0, data.getValue());
		calculateCost();
	}
	
	protected void calculateCost(){
		this.cost = heuristic.execute(path, distances, travelTimes);
	}

	/**
	 * GETTERS & SETTERS
	 */
	public float getCost(){
		if(cost == 0)
			calculateCost();
		return cost;
	}
	
	public void setCost(float cost){
		this.cost = cost;
	}
	
	public List<Long> getDistances() {
		return distances;
	}

	public List<Long> getTravelTimes() {
		return travelTimes;
	}

	public List<Passenger> getPath(){
		return new LinkedList<Passenger>(path);
	}
	
	public Taxi getTaxi(){
		return taxi;
	}
	
	public int size(){
		return path.size();
	}
	
	public void addPassenger(Passenger passenger, long distance, long time){
		path.add(passenger);
		distances.add(distance);
		travelTimes.add(time);
	}
	
	
	/**
	 * @see {@link Comparable<Plan>#compareTo(Plan)}
	 */
	@SuppressWarnings("javadoc")
	@Override
	public int compareTo(Plan p){
		if(p.getCost() < this.getCost())
			return -1;
		if(p.getCost() == this.getCost())
			return 0;
		else return 1;
	}
	
	public Plan clone(){
		Plan clone = new Plan(rm,taxi,new LinkedList<Passenger>(path),new LinkedList<Long>(distances), new LinkedList<Long>(travelTimes), heuristic);
		clone.cost = cost;
		return clone;
	}


}
